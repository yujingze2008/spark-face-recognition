package com.gitee.code4fun.facerecognition.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yujingze
 * @data 18/4/8
 */
public class ResponseBuilder {

    private HashMap<String,Object> data = new HashMap<String, Object>();

    public ResponseBuilder success(){
        this.setCode(ResponseStatus.SUCCESS.getCode());
        this.setMsg(ResponseStatus.SUCCESS.getMsg());
        return this;
    }

    public ResponseBuilder failure(String code,String message){
        this.setCode(code);
        this.setMsg(message);
        return this;
    }

    public ResponseBuilder setCode(String code){
        this.putData("code",code);
        return this;
    }

    public ResponseBuilder setMsg(String msg){
        this.putData("msg",msg);
        return this;
    }

    public ResponseBuilder putData(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public JSONObject build(){
        JSONObject obj = new JSONObject();
        for(Map.Entry<String,Object> entry : data.entrySet()){
            obj.put(entry.getKey(),entry.getValue());
        }
        return obj;
    }

    public static void main(String[] args){

        JSONObject obj = new ResponseBuilder().setCode("123")
                .setMsg("abc")
                .putData("aaa","aaa")
                .putData("bbb","bbb")
                .build();
        System.out.println(obj.toJSONString());
    }

}
