package com.gitee.code4fun.facerecognition.gui.controller;

import com.alibaba.fastjson.JSONObject;
import com.gitee.code4fun.facerecognition.common.util.ResponseBuilder;
import com.gitee.code4fun.facerecognition.common.util.ResponseStatus;
import com.gitee.code4fun.facerecognition.gui.service.IFaceRecognitionService;
import com.gitee.code4fun.facerecognition.gui.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yujingze
 * @data 18/3/27
 */
@Controller
@RequestMapping("face")
//@Api(value="人脸识别服务接口")
public class FaceRecognitionController {

    private static Logger logger = LoggerFactory.getLogger(FaceRecognitionController.class);

    @Autowired
    private IFaceRecognitionService faceRecognitionService;

    /**
     * 人脸检测
     * @param imageFilePath
     * @param req
     * @return
     */
    //@ApiOperation(value="人脸检测", notes="输入参数为原始图片名(已存于HDFS指定路径,无需全路径)")
    @PostMapping("faceDetection")
    @ResponseBody
    public JSONObject faceDetection(@RequestParam("imageFilePath") String imageFilePath, HttpServletRequest req) {
        ResponseBuilder response = new ResponseBuilder();
        try{
            String faceFileName = faceRecognitionService.faceDetection(imageFilePath);
            String facePath = req.getContextPath() + "/face/" + faceFileName;
            if(facePath == null){
                response.failure(ResponseStatus.ERROR_OPENCV_SYSTEMERROR.getCode(),
                        ResponseStatus.ERROR_OPENCV_SYSTEMERROR.getMsg());
            }else{
                response.success().putData("facePath",facePath);
            }

        }catch (Exception e){
            response.failure(ResponseStatus.ERROR_OPENCV_SYSTEMERROR.getCode(),
                    ResponseStatus.ERROR_OPENCV_SYSTEMERROR.getMsg());
            logger.error("face detection fail",e);
        }
        return response.build();
    }

    /**
     * 人脸识别
     * @return
     */
    @PostMapping("faceRecognition")
    @ResponseBody
    //@ApiOperation(value="人脸识别", notes="输入参数为人脸图片名(已存于HDFS指定路径,无需全路径)")
    public JSONObject faceRecognition(@RequestParam("faceFilePath") String faceFilePath){
        ResponseBuilder response = new ResponseBuilder();
        try{
            UserVO userVO = faceRecognitionService.faceRecognition(faceFilePath);
            if(userVO == null){
                response.failure(ResponseStatus.ERROR_ML_PREDICTERROR.getCode(),
                        ResponseStatus.ERROR_ML_PREDICTERROR.getMsg());
            }else{
                response.success()
                        .putData("userId",userVO.getUserId())
                        .putData("userName",userVO.getUserName())
                        .putData("userPosition",userVO.getUserPosition())
                        .putData("userDepartment",userVO.getUserDepartment());
            }
        }catch (Exception e){
            response.failure(ResponseStatus.ERROR_ML_PREDICTERROR.getCode(),
                    ResponseStatus.ERROR_ML_PREDICTERROR.getMsg());
            logger.error("face detection fail",e);
        }
        return response.build();
    }
}
