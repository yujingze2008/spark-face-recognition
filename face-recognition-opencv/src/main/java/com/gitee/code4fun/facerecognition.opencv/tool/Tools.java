package com.gitee.code4fun.facerecognition.opencv.tool;

import com.gitee.code4fun.facerecognition.common.Settings;
import com.gitee.code4fun.facerecognition.common.entity.HbaseColumn;
import com.gitee.code4fun.facerecognition.common.util.HbaseUtils;
import com.gitee.code4fun.facerecognition.common.util.ImageUtils;
import com.gitee.code4fun.facerecognition.opencv.util.VectorUtils;
import org.opencv.core.Core;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author yujingze
 * @data 2018/9/12
 */
public class Tools {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void loadImageFromDisk(String filePath, String category) throws Exception {
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        int i = 0;
        for (File imageFile : files) {
            String imageMessage = ImageUtils.getImgStr(imageFile.getAbsolutePath());
            String imageCode = ImageUtils.saveImage(imageMessage, Settings.IMAGE_TYPE_SOURCE);
            String sampleVector = VectorUtils.getSampleVector(category, imageCode);
            if(sampleVector != null){
                String rowKey = UUID.randomUUID().toString().replaceAll("-", "") + "_" +category;
                HbaseUtils.put("t_vector", rowKey, Arrays.asList(new HbaseColumn("cf".getBytes(), "data".getBytes(), sampleVector.getBytes())));
                System.out.println("deal:" + i);
                i++;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        /**
         * curry 0
         * kobe 1
         * james 2
         * other 3
         */
        //Tools.loadImageFromDisk("/Users/yujingze/develop/ml-images/curry/", "0");
        //Tools.loadImageFromDisk("/Users/yujingze/develop/ml-images/kobe/", "1");
        //Tools.loadImageFromDisk("/Users/yujingze/develop/ml-images/james/", "2");
        //Tools.loadImageFromDisk("/Users/yujingze/develop/ml-images/other/", "3");
    }

}
