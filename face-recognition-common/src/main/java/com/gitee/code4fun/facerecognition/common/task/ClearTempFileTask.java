package com.gitee.code4fun.facerecognition.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.TimerTask;

/**
 * @author yujingze
 * @data 2018/9/11
 */
public class ClearTempFileTask extends TimerTask {

    private static Logger logger = LoggerFactory.getLogger(ClearTempFileTask.class);

    private String filePath;

    private String taskName;

    private static final long DIFFTIME = 3 * 1000;

    public ClearTempFileTask(String taskName, String filePath) {
        this.taskName = taskName;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        int count = 0;
        long checkTime = System.currentTimeMillis();
        try {
            File dir = new File(filePath);
            File[] files = dir.listFiles();
            if (files.length > 0) {
                for (File tempFile : files) {
                    long modifyTime = tempFile.lastModified();
                    if ((checkTime - modifyTime) > DIFFTIME) {
                        tempFile.delete();
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("ClearTempFileTask[" + taskName + "] was failed", e);
        }
        logger.info("ClearTempFileTask[" + taskName + "] delete " + count + " files");
    }

}
