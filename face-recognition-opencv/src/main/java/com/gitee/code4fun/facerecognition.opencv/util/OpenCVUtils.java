package com.gitee.code4fun.facerecognition.opencv.util;

import jodd.datetime.JStopWatch;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author yujingze
 * @data 18/3/23
 * opencv工具类
 */
public class OpenCVUtils {

    private static Logger logger = LoggerFactory.getLogger(OpenCVUtils.class);

    public static final int SAMPLE_SIZE_WIDTH = 64;

    public static final int SAMPLE_SZIE_HEIGHT = 64;

    /**
     * 加载opencv库
     */
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * 加载opencv分类器
     */
    private static CascadeClassifier FACE_DETECTOR = new CascadeClassifier(System.getProperty("user.dir") + "/face-recognition-opencv/extconf/lbpcascade_frontalface.xml");

    /**
     * 脸部检测
     * @param image
     * @return
     */
    public static MatOfRect faceDetection(Mat image){
        JStopWatch stopWatch = new JStopWatch();
        stopWatch.start();

        MatOfRect faceDetections = new MatOfRect();
        FACE_DETECTOR.detectMultiScale(image, faceDetections);

        logger.info(String.format("Detected %s faces",
                faceDetections.toArray().length));

        stopWatch.stop();

        logger.info("Detected Elapsed time :" + stopWatch.elapsed());

        return faceDetections;
    }

    /**
     * 脸部检测并在原图标注
     * @param image
     * @return
     */
    public static Mat faceDetectionAndMark(Mat image){
        MatOfRect faceDetections = faceDetection(image);
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
                    + rect.width, rect.y + rect.height), new Scalar(0, 0, 255));
        }
        return image;
    }

    /**
     * 从原图裁剪脸部样本，并做灰度处理
     * @param image
     * @param matOfRect
     * @return
     */
    public static Mat[] clipSampleFromFace(Mat image,MatOfRect matOfRect){
        Rect[] rects = matOfRect.toArray();
        if(rects.length > 0){
            Mat[] mats = new Mat[rects.length];
            for(int i=0;i<rects.length;i++){
                Rect rect = rects[i];
                Mat ori = new Mat(image,rect);
                Mat dist = new Mat();
                ori.copyTo(dist);
                //dist = gray(dist);
                Imgproc.resize(ori,dist,new Size(SAMPLE_SIZE_WIDTH, SAMPLE_SZIE_HEIGHT));
                mats[i] = dist;
            }
            return mats;
        }
        return null;
    }

    /**
     * 灰度处理
     * @param image
     * @return
     */
    public static Mat gray(Mat image){
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        return gray;
    }

    /**
     * 计算图片的hog值，已double形式表示
     * @param image
     */
    public static float[] hogCompute4Float(Mat image){

        HOGDescriptor hog = new HOGDescriptor(new Size(32,32),
                new Size(16,16),
                new Size(8,8),
                new Size(8,8),9);

        MatOfFloat matOfFloat = new MatOfFloat();
        hog.compute(image,matOfFloat);

        return matOfFloat.toArray();
    }

    /**
     * 从路径读取图片
     * @param filePath
     * @return
     */
    public static Mat readImage(String filePath){
        return Imgcodecs.imread(filePath);
    }

    /**
     * 从路径读取图片，并裁剪脸部后返回脸部图片列表
     * @param imagePath
     */
    public static List<String> clipFaceFromImage(String imagePath,String targetPath){
        List<String> faceNames = new ArrayList<String>();
        Mat mat = readImage(imagePath);
        MatOfRect matOfRect = faceDetection(mat);
        Mat[] faces = clipSampleFromFace(mat,matOfRect);
        if(faces != null && faces.length > 0){
            for(Mat face : faces){
                String fileName = UUID.randomUUID().toString().replaceAll("-","") + ".jpg";
                String facePath = targetPath + fileName;
                Imgcodecs.imwrite(facePath,face);
                faceNames.add(fileName);
            }
        }
        return faceNames;
    }

    public static Mat clipFaceFromImage(Mat mat){
        MatOfRect matOfRect = faceDetection(mat);
        Mat[] faces = clipSampleFromFace(mat,matOfRect);
        if(faces != null && faces.length > 0){
            return faces[0];
        }
        return null;
    }

    public static void main(String[] args){
        Mat mat = Imgcodecs.imread("123.png");
        System.out.println(123);
    }

}
