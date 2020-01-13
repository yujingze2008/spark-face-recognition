package com.gitee.code4fun.facerecognition.gui.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author yujingze
 * @data 18/3/27
 */
public interface IFileUploadService{

    /**
     * 文件保存到本地临时目录用于展示，同时保存到hdfs用于留存于处理
     * @param multipartFile
     * @return
     */
    String saveImg(MultipartFile multipartFile) throws Exception;

}
