package com.gitee.code4fun.facerecognition.common.util;

import com.gitee.code4fun.facerecognition.common.Settings;
import com.gitee.code4fun.facerecognition.common.entity.HbaseColumn;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author yujingze
 * @data 18/6/15
 */
public class ImageUtils {

    /**
     * 把 Base64 编码的字符串表示的图片保存到传入的目录 directory 下，图片的名字为 baseName，不包含后缀，
     * 图片的后缀从 base64ImageString 中解析得到
     *
     * @param directory         要保存图片的目录
     * @param baseName          图片的名字
     * @param base64ImageString 图片的 Base64 编码的字符串
     * @throws IOException
     */
    public static void saveBase64ImageStringToImage(String directory, String baseName, String base64ImageString) throws IOException {
        // 图片的格式为 data:image/png;base64,iVBORw0KGgoAAAA...
        // 逗号的前面为图片的格式，逗号后们为图片二进制数据的 Base64 编码字符串
        int commaIndex = base64ImageString.indexOf(",");
        String extension = base64ImageString.substring(0, commaIndex).replaceAll("data:image/(.+);base64", "$1");
        byte[] content = Base64.decodeBase64(base64ImageString.substring(commaIndex));
        FileUtils.writeByteArrayToFile(new File(directory, baseName + "." + extension), content);
    }

    public static String getImgStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }


    public static String saveImage(String imageMessage,String category) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String rowKey = uuid + "_" +category;
        HbaseUtils.put("t_images", rowKey,
                Arrays.asList(new HbaseColumn("cf".getBytes(), "data".getBytes(), imageMessage.getBytes())));
        return rowKey;
    }

    public static String getImage(String imageCode) throws Exception {
        List<String> result = HbaseUtils.get("t_images", imageCode, "cf:data");
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

}
