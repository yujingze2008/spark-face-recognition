package com.gitee.code4fun.facerecognition.common.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yujingze
 * @data 18/3/28
 */
public class ClientUtils {

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static String post(String url,String json) throws IOException{
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = RequestBody.create(JSON,json);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] args) throws IOException{

        JSONObject params = new JSONObject();
        params.put("predictFileName","predict.txt");

        System.out.println(ClientUtils.post("http://localhost:7073/api/predict",params.toJSONString()));

    }

}
