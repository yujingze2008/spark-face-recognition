package com.gitee.code4fun.facerecognition.common.util;

/**
 * @author yujingze
 * @data 18/4/8
 */
public enum ResponseStatus {

    SUCCESS("0000", "处理成功"),
    ERROR_OPENCV_SYSTEMERROR("0100", "调用OpenCV处理图像异常"),
    ERROR_OPENCV_NOFACE_FOUND("0101", "未识别出人脸"),
    ERROR_OPENCV_BATCH_CREATE_VEC_ERROR("0102", "批量生成人脸向量失败"),
    ERROR_ML_PREDICTERROR("0200","调用ml预测人脸异常"),
    ERROR_ML_TRAINERROR("0201","调用ml训练模型失败"),
    ERROR_UPLOAD_FILETYPE_ERROR("0300","文件类型有误"),
    ERROR_UPLOAD_FILEUPLOAD_ERROR("0301","上传文件异常");


    private String msg;
    private String code;

    private ResponseStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }

}
