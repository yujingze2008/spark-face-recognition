package com.gitee.code4fun.facerecognition.gui.service.impl;

import com.gitee.code4fun.facerecognition.common.util.HdfsUtils;
import com.gitee.code4fun.facerecognition.gui.service.IFileUploadService;
import jodd.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author yujingze
 * @data 18/3/27
 */
@Service
public class FileUploadServiceImpl implements IFileUploadService {

    private static Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Autowired
    private Environment env;

    public String saveImg(MultipartFile multipartFile) throws Exception{
        String tempFileName;
        //文件读取到本地临时目录
        String fileName = multipartFile.getOriginalFilename();
        String oriPath = env.getProperty("local.images.ori.path");
        String[] fileNameSplit = fileName.split("\\.");
        tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileNameSplit[1];
        File tempFile = new File(oriPath + tempFileName);
        if (!tempFile.exists()) {
            FileUtil.touch(tempFile);
        }
        multipartFile.transferTo(tempFile);
        logger.info("save img :{} to local path :{} with name {}", fileName, oriPath, tempFileName);
        //本地文件上传到hdfs
        String hdfsOriPath = env.getProperty("hdfs.images.ori.path");
        HdfsUtils.copyFileFromLocal(tempFile.getAbsolutePath(),
                hdfsOriPath + tempFileName);
        logger.info("save img :{} to hdfs path :{} with name {}", fileName, hdfsOriPath, tempFileName);
        return tempFileName;
    }
}
