package com.gitee.code4fun.facerecognition.gui.websocket;

import com.alibaba.fastjson.JSONObject;
import com.gitee.code4fun.facerecognition.common.Settings;
import com.gitee.code4fun.facerecognition.common.util.ImageUtils;
import com.gitee.code4fun.facerecognition.gui.service.IFaceRecognitionService;
import com.gitee.code4fun.facerecognition.gui.service.impl.FaceRecognitionServiceImpl;
import com.gitee.code4fun.facerecognition.gui.util.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yujingze
 * @data 18/6/15
 */
@ServerEndpoint(value = "/websocket")
@Component
public class ImageStreamWebSocket {

    private static Logger logger = LoggerFactory.getLogger(ImageStreamWebSocket.class);

    private static CopyOnWriteArraySet<ImageStreamWebSocket> webSocketSet = new CopyOnWriteArraySet<ImageStreamWebSocket>();

    private Session session;

    public static Map<String, String> faceResult = Collections.synchronizedMap(new HashMap<String, String>());

    @OnMessage(maxMessageSize = 1024 * 1024 * 10)
    public void onMessage(String message, Session session) {
        IFaceRecognitionService faceRecognitionService = (IFaceRecognitionService) SpringContextUtils.getBean("faceRecognitionServiceImpl");
        logger.info("[" + session.getId() + "]客户端的发送消息:[" + (message.length() > 50 ? message.substring(0, 50) : message) + "]");
        String imageCode = null;
        try {
            imageCode = ImageUtils.saveImage(message, Settings.IMAGE_TYPE_SOURCE);
        } catch (Exception e) {
            logger.error("保存原始图片失败", e);
        }
        logger.info("[" + session.getId() + "]保存原始图片成功");
        try {
            imageCode = faceRecognitionService.getFaceTagImage(imageCode);
        } catch (Exception e) {
            logger.error("检测原始图片人脸失败", e);
        }
        String newImageMessage = null;
        try {
            newImageMessage = ImageUtils.getImage(imageCode);
        } catch (Exception e) {
            logger.error("获取人脸图片失败", e);
        }
        JSONObject ret = new JSONObject();
        ret.put("imageData", newImageMessage);
        try {
            session.getBasicRemote().sendText(ret.toString());
        } catch (IOException e) {
            logger.error("[" + session.getId() + "]发送图片到前台失败");
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }

}
