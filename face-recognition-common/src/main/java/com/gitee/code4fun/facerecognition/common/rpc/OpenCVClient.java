package com.gitee.code4fun.facerecognition.common.rpc;

import com.gitee.code4fun.facerecognition.common.rpc.stub.OpenCVServiceGrpc;
import com.gitee.code4fun.facerecognition.common.rpc.stub.OpenCVServiceProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author yujingze
 * @data 2018/7/31
 */
public class OpenCVClient {

    final ManagedChannel channel;

    final OpenCVServiceGrpc.OpenCVServiceBlockingStub blockingStub;

    public OpenCVClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 5666)
                .usePlaintext(true)
                .build();
        blockingStub = OpenCVServiceGrpc.newBlockingStub(channel);
    }

    public void close() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public String getFaceTagImage(String imageCode){

        OpenCVServiceProto.OpenCVBasicRequest request = OpenCVServiceProto.OpenCVBasicRequest.newBuilder()
                .setImageCode(imageCode).build();
        OpenCVServiceProto.ImageResponse response = blockingStub.getFaceTagImage(request);

        return response.getImageCode();

    }

    public String getFaceImage(String imageCode){

        OpenCVServiceProto.OpenCVBasicRequest request = OpenCVServiceProto.OpenCVBasicRequest.newBuilder()
                .setImageCode(imageCode).build();
        OpenCVServiceProto.ImageResponse response = blockingStub.getFaceImages(request);

        return response.getImageCode();

    }

}
