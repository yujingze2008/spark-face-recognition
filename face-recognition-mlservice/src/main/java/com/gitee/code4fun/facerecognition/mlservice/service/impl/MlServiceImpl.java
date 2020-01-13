package com.gitee.code4fun.facerecognition.mlservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gitee.code4fun.facerecognition.common.util.ClientUtils;
import com.gitee.code4fun.facerecognition.mlservice.service.IMlService;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yujingze
 * @data 18/4/8
 */
@Service
public class MlServiceImpl implements IMlService {

    private static Logger logger = LoggerFactory.getLogger(MlServiceImpl.class);

    @Autowired
    private Environment env;

    public String predict(String predictFileName) throws Exception {
        logger.info(predictFileName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String outputFileName = sdf.format(new Date()) + ".txt";
        SparkLauncher launcher = new SparkLauncher();
        Process process = launcher.setSparkHome(env.getProperty("spark.home"))
                .setAppResource(env.getProperty("spark.appresource"))
                .setMainClass("com.gitee.code4fun.facerecognition.ml.spark.predictor.NaiveBayesPredictor")
                .addAppArgs(env.getProperty("spark.master"))
                .addAppArgs(env.getProperty("hdfs.naivebayes.model.path"))
                .addAppArgs(env.getProperty("hdfs.ml.predict.path") + predictFileName)
                .addAppArgs(env.getProperty("hdfs.naivebayes.predict.result.path") + outputFileName)
                .launch();
        logger.info("submit spark-job");

        printProcess(process);

        return outputFileName;
    }

    public String batchTrain(String hdfsImagesPath,String category) throws Exception {

        //调用opencv接口生成训练样本
        String opencvServiceUrl = env.getProperty("opencv.service.url") + "/api/batchCreateFaceVec";
        JSONObject params = new JSONObject();
        params.put("hdfsImagesPath", hdfsImagesPath);
        params.put("category", category);
        String response = ClientUtils.post(opencvServiceUrl, params.toJSONString());
        JSONObject responseJson = JSONObject.parseObject(response);
        if ("0000".equals(responseJson.getString("code"))) {
            //训练样本
            SparkLauncher launcher = new SparkLauncher();
            Process process = launcher.setSparkHome(env.getProperty("spark.home"))
                    .setAppResource(env.getProperty("spark.appresource"))
                    .setMainClass("com.gitee.code4fun.facerecognition.ml.spark.classifier.NaiveBayesClassifier")
                    .addAppArgs(env.getProperty("spark.master"))
                    .addAppArgs(env.getProperty("hdfs.sample.vec.path"))
                    .addAppArgs(env.getProperty("hdfs.naivebayes.model.path"))
                    .launch();

            printProcess(process);

            return "success";
        }

        return null;
    }

    private void printProcess(Process proc){
        BufferedReader infoReader = null;
        BufferedReader errorReader = null;
        try {
            infoReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String info;
            while ((info = infoReader.readLine()) != null) {
                logger.info(info);
            }
            errorReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String error;
            while ((error = errorReader.readLine()) != null) {
                logger.info(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (infoReader != null) {
                try {
                    infoReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (errorReader != null) {
                try {
                    errorReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
