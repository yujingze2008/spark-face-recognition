package com.gitee.code4fun.facerecognition.gui.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gitee.code4fun.facerecognition.common.rpc.OpenCVClient;
import com.gitee.code4fun.facerecognition.common.util.ClientUtils;
import com.gitee.code4fun.facerecognition.common.util.HdfsUtils;
import com.gitee.code4fun.facerecognition.gui.dao.IUserDAO;
import com.gitee.code4fun.facerecognition.gui.service.IFaceRecognitionService;
import com.gitee.code4fun.facerecognition.gui.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author yujingze
 * @data 18/3/27
 */
@Service
public class FaceRecognitionServiceImpl implements IFaceRecognitionService {

    private static Logger logger = LoggerFactory.getLogger(FaceRecognitionServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    IUserDAO userDAO;

    public String faceDetection(String imageFilePath) throws Exception {
        String imageFileName = imageFilePath.replace("/ori/", "");
        String opencvServiceUrl = env.getProperty("opencv.service.url") + "/api/faceDetection";
        JSONObject params = new JSONObject();
        params.put("imageFileName", imageFileName);
        logger.info("invoke remote service [{}]",opencvServiceUrl);
        String response = ClientUtils.post(opencvServiceUrl, params.toJSONString());
        JSONObject responseJson = JSONObject.parseObject(response);
        if ("0000".equals(responseJson.getString("code"))) {
            String faceFileName = responseJson.getString("faceFileName");
            //从hdfs下载脸部文件到本地
            String localFacePath = env.getProperty("local.images.face.path");
            String hdfsFacePath = env.getProperty("hdfs.images.face.path");
            HdfsUtils.copyFileToLocal(hdfsFacePath + faceFileName,
                    localFacePath + faceFileName);
            return faceFileName;
        } else {
            return null;
        }
    }

    public UserVO faceRecognition(String faceFilePath) throws Exception {
        //调用opencv服务生成特征向量
        String faceFileName = faceFilePath.replace("/face/", "");
        String opencvServiceUrl = env.getProperty("opencv.service.url") + "/api/createFaceVec";
        JSONObject params = new JSONObject();
        params.put("faceFileName", faceFileName);
        logger.info("invoke remote service [{}]",opencvServiceUrl);
        String response = ClientUtils.post(opencvServiceUrl, params.toJSONString());
        JSONObject responseJson = JSONObject.parseObject(response);
        if ("0000".equals(responseJson.getString("code"))) {
            //调用ml服务预测
            String predictFileName = responseJson.getString("predictFileName");
            String mlServiceUrl = env.getProperty("ml.service.url") + "/api/predict";
            JSONObject mlParams = new JSONObject();
            mlParams.put("predictFileName", predictFileName);
            logger.info("invoke remote service [{}]",mlServiceUrl);
            String mlResponse = ClientUtils.post(mlServiceUrl, mlParams.toJSONString());
            JSONObject mlResponseJson = JSONObject.parseObject(mlResponse);
            if ("0000".equals(mlResponseJson.getString("code"))) {
                String outputFileName = mlResponseJson.getString("outputFileName");
                String outputPath = env.getProperty("hdfs.naivebayes.predict.result.path");
                String resultData = HdfsUtils.readFile(outputPath + outputFileName);
                String userId = userDAO.getUserIdByMapping(String.valueOf(Double.valueOf(resultData).intValue()));
                UserVO vo = userDAO.getUserById(userId);
                return vo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getFaceTagImage(String imageCode) throws Exception {
        return new OpenCVClient().getFaceTagImage(imageCode);
    }
}
