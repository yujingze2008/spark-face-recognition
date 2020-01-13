package com.gitee.code4fun.facerecognition.mlservice.service;

/**
 * @author yujingze
 * @data 18/4/8
 */
public interface IMlService {

    String predict(String predictFileName) throws Exception;

    String batchTrain(String hdfsImagesPath,String category) throws Exception;

}
