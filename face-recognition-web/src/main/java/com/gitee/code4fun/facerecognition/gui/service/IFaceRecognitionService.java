package com.gitee.code4fun.facerecognition.gui.service;

import com.alibaba.fastjson.JSONObject;
import com.gitee.code4fun.facerecognition.gui.vo.UserVO;

import java.util.List;

/**
 * @author yujingze
 * @data 18/3/27
 */
public interface IFaceRecognitionService {

    String faceDetection(String imageFilePath) throws Exception;

    UserVO faceRecognition(String faceFilePath) throws Exception;

    String getFaceTagImage(String imageCode) throws Exception;
}
