package com.gitee.code4fun.facerecognition.opencv.util;

import com.gitee.code4fun.facerecognition.common.Settings;
import com.gitee.code4fun.facerecognition.common.util.ImageUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author yujingze
 * @data 2018/9/11
 */
public class VectorUtils {

    //样本向量
    public static String getSampleVector(String category,String imageCode) throws Exception{
        String sampleVector = null;
        String imageString = ImageUtils.getImage(imageCode);
        String tempImageFileName = UUID.randomUUID().toString();
        imageString = "data:image/png;base64,"+imageString;
        ImageUtils.saveBase64ImageStringToImage(Settings.OPENCV_TEMP_IMAGE_FILEPATH, tempImageFileName, imageString);
        Mat mat = Imgcodecs.imread(Settings.OPENCV_TEMP_IMAGE_FILEPATH + tempImageFileName + ".png");
        MatOfRect matOfRect = OpenCVUtils.faceDetection(mat);
        Mat[] faces = OpenCVUtils.clipSampleFromFace(mat,matOfRect);
        List<float[]> hogList = new ArrayList<float[]>();
        if(faces != null){
            for(Mat face : faces){
                float[] hogFloat = OpenCVUtils.hogCompute4Float(face);
                hogList.add(hogFloat);
            }
        }
        for(float[] fls : hogList){
            StringBuffer buffer = new StringBuffer();
            buffer.append(category);
            buffer.append(",");
            for(float fl : fls){
                buffer.append(fl);
                buffer.append(" ");
            }
            sampleVector= buffer.toString();
        }
        return sampleVector;
    }

    //预测向量
    public static String getPreditVector(String imageCode) throws Exception{
        String preditVector = null;
        String imageString = ImageUtils.getImage(imageCode);
        String tempImageFileName = UUID.randomUUID().toString();
        ImageUtils.saveBase64ImageStringToImage(Settings.OPENCV_TEMP_IMAGE_FILEPATH, tempImageFileName, imageString);
        Mat mat = Imgcodecs.imread(Settings.OPENCV_TEMP_IMAGE_FILEPATH + tempImageFileName + ".png");
        Mat face = OpenCVUtils.gray(mat);
        float[] fls = OpenCVUtils.hogCompute4Float(face);
        StringBuffer buffer = new StringBuffer();
        for(float fl :fls){
            BigDecimal bg = new BigDecimal(fl);
            buffer.append(bg.doubleValue());
            buffer.append(" ");
        }
        preditVector = buffer.toString();
        return preditVector;
    }

}
