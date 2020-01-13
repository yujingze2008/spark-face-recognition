package com.gitee.code4fun.facerecognition.gui;

import com.gitee.code4fun.facerecognition.gui.util.SpringContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author yujingze
 * @data 18/3/27
 */
@SpringBootApplication
public class FaceRecognitionApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(FaceRecognitionApplication.class, args);
        SpringContextUtils.setApplicationContext(context);
    }


}
