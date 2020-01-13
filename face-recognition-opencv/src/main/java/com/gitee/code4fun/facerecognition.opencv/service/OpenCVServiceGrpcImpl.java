package com.gitee.code4fun.facerecognition.opencv.service;

import com.gitee.code4fun.facerecognition.common.Settings;
import com.gitee.code4fun.facerecognition.common.rpc.stub.OpenCVServiceGrpc;
import com.gitee.code4fun.facerecognition.common.rpc.stub.OpenCVServiceProto;
import com.gitee.code4fun.facerecognition.common.util.ImageUtils;
import com.gitee.code4fun.facerecognition.opencv.util.OpenCVUtils;
import io.grpc.stub.StreamObserver;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.UUID;

/**
 * @author yujingze
 * @data 18/7/30
 */
public class OpenCVServiceGrpcImpl extends OpenCVServiceGrpc.OpenCVServiceImplBase{

    @Override
    public void getFaceTagImage(OpenCVServiceProto.OpenCVBasicRequest request, StreamObserver<OpenCVServiceProto.ImageResponse> responseObserver) {
        String newImageCode = null;
        OpenCVServiceProto.ImageResponse.Builder builder = OpenCVServiceProto.ImageResponse.newBuilder();
        try {
            String imageCode = request.getImageCode();
            String imageString = ImageUtils.getImage(imageCode);
            String tempImageFileName = UUID.randomUUID().toString();
            ImageUtils.saveBase64ImageStringToImage(Settings.OPENCV_TEMP_IMAGE_FILEPATH,tempImageFileName,imageString);
            Mat mat = Imgcodecs.imread(Settings.OPENCV_TEMP_IMAGE_FILEPATH + tempImageFileName + ".png");

            mat = OpenCVUtils.faceDetectionAndMark(mat);

            String tempTagImageFilePath = Settings.OPENCV_TEMP_IMAGE_FILEPATH + tempImageFileName + "_tag.png";
            Imgcodecs.imwrite(tempTagImageFilePath,mat);
            String newImageString = ImageUtils.getImgStr(tempTagImageFilePath);
            newImageCode = ImageUtils.saveImage(newImageString, com.gitee.code4fun.facerecognition.common.Settings.IMAGE_TYPE_TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.setImageCode(newImageCode);
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getFaceImages(OpenCVServiceProto.OpenCVBasicRequest request, StreamObserver<OpenCVServiceProto.ImageResponse> responseObserver) {
        String newImageCode = null;
        OpenCVServiceProto.ImageResponse.Builder builder = OpenCVServiceProto.ImageResponse.newBuilder();
        try {
            String imageCode = request.getImageCode();
            String imageString = ImageUtils.getImage(imageCode);
            String tempImageFileName = UUID.randomUUID().toString();
            ImageUtils.saveBase64ImageStringToImage(Settings.OPENCV_TEMP_IMAGE_FILEPATH,tempImageFileName,imageString);
            Mat mat = Imgcodecs.imread(Settings.OPENCV_TEMP_IMAGE_FILEPATH + tempImageFileName + ".png");

            mat = OpenCVUtils.clipFaceFromImage(mat);

            String tempTagImageFilePath = Settings.OPENCV_TEMP_IMAGE_FILEPATH + tempImageFileName + "_face.png";
            Imgcodecs.imwrite(tempTagImageFilePath,mat);
            String newImageString = ImageUtils.getImgStr(tempTagImageFilePath);
            newImageCode = ImageUtils.saveImage(newImageString, Settings.IMAGE_TYPE_FACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.setImageCode(newImageCode);
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getFaceVector(OpenCVServiceProto.OpenCVBasicRequest request, StreamObserver<OpenCVServiceProto.VectorResponse> responseObserver) {
        super.getFaceVector(request, responseObserver);
    }

}
