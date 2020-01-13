package com.gitee.code4fun.facerecognition.gui.controller;

import com.alibaba.fastjson.JSONObject;
import com.gitee.code4fun.facerecognition.common.util.ResponseBuilder;
import com.gitee.code4fun.facerecognition.common.util.ResponseStatus;
import com.gitee.code4fun.facerecognition.gui.service.IFileUploadService;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yujingze
 * @data 18/3/27
 */
@Controller
@RequestMapping("upload")
//@ApiIgnore()
public class FileUploadController {

    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * 图片上传
     * @param multipartFile
     * @param req
     * @return
     */
    @PostMapping("img")
    @ResponseBody
    public JSONObject uploadImg(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest req) {
        ResponseBuilder response = new ResponseBuilder();
        try{
            if (multipartFile.isEmpty() || StringUtil.isBlank(multipartFile.getOriginalFilename())) {
                response.failure(ResponseStatus.ERROR_UPLOAD_FILETYPE_ERROR.getCode(),
                        ResponseStatus.ERROR_UPLOAD_FILETYPE_ERROR.getMsg());
                return response.build();
            }
            String contentType = multipartFile.getContentType();
            if (!contentType.contains("")) {
                response.failure(ResponseStatus.ERROR_UPLOAD_FILETYPE_ERROR.getCode(),
                        ResponseStatus.ERROR_UPLOAD_FILETYPE_ERROR.getMsg());
                return response.build();
            }
            String tempFileName = fileUploadService.saveImg(multipartFile);
            String oriPath = req.getContextPath() + "/ori/" + tempFileName;
            response.success().putData("oriPath",oriPath);
        }catch (Exception e){
            response.failure(ResponseStatus.ERROR_UPLOAD_FILEUPLOAD_ERROR.getCode(),
                    ResponseStatus.ERROR_UPLOAD_FILEUPLOAD_ERROR.getMsg());
            logger.error("upload img fail",e);
        }
        return response.build();
    }

}
