package com.gitee.code4fun.facerecognition.mlservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.gitee.code4fun.facerecognition.common.util.ResponseBuilder;
import com.gitee.code4fun.facerecognition.common.util.ResponseStatus;
import com.gitee.code4fun.facerecognition.mlservice.service.IMlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author yujingze
 * @data 18/4/8
 */
@Controller
@RequestMapping("api")
@Api(value="机器学习服务接口")
public class MlController {

    private static Logger logger = LoggerFactory.getLogger(MlController.class);

    @Autowired
    IMlService mlService;

    /**
     * 预测人脸
     * @param params
     * @return
     */
    @PostMapping("predict")
    @ResponseBody
    @ApiOperation(value="预测人脸", notes="输入参数为样本向量名(已存于HDFS指定路径,无需全路径)")
    public JSONObject predict(@RequestBody Map<String, Object> params) throws Exception {
        logger.info("accept predict request");
        ResponseBuilder response = new ResponseBuilder();
        try{
            String predictFileName = (String) params.get("predictFileName");
            String outputFileName = mlService.predict(predictFileName);
            response.success()
                    .putData("outputFileName",outputFileName);
        }catch (Exception e){
            response.failure(ResponseStatus.ERROR_ML_PREDICTERROR.getCode(),
                    ResponseStatus.ERROR_ML_PREDICTERROR.getMsg());
            logger.error("ml preidct fail",e);
        }

        return response.build();
    }

    /**
     * 批量训练样本
     *
     * @param params
     * @return
     */
    @PostMapping("batchTrain")
    @ResponseBody
    @ApiOperation(value="训练样本", notes="输入参数为hdfs的图片路径")
    public JSONObject batchTrain(@RequestBody Map<String, Object> params) {
        ResponseBuilder response = new ResponseBuilder();
        try{
            String hdfsImagesPath = (String) params.get("hdfsImagesPath");
            String category = (String) params.get("category");
            String accuracy = mlService.batchTrain(hdfsImagesPath,category);
            if(accuracy != null){
                response.success()
                        .putData("accuracy",accuracy);
            }else{
                response.failure(ResponseStatus.ERROR_ML_TRAINERROR.getCode(),
                        ResponseStatus.ERROR_ML_TRAINERROR.getMsg());
            }
        }catch (Exception e){
            response.failure(ResponseStatus.ERROR_ML_TRAINERROR.getCode(),
                    ResponseStatus.ERROR_ML_TRAINERROR.getMsg());
            logger.error("ml train fail",e);
        }
        return response.build();
    }

}
