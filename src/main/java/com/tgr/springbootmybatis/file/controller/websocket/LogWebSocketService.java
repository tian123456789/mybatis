package com.tgr.springbootmybatis.file.controller.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 日志实时查看，仅限测试环境使用
 */
@Component
@ServerEndpoint("/log")
public class LogWebSocketService {

    private static final Logger logger = LoggerFactory.getLogger(LogWebSocketService.class);

    private Process process;
    private InputStream inputStream;

    @OnOpen
    public void onOpen(Session session) {
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            // 执行tail -f命令
        	System.err.println("进入webSocket  webSocket  webSocket  webSocket  webSocket  webSocket");
            process = Runtime.getRuntime().exec("tail -f " + message);
        	//process = Runtime.getRuntime().exec("tail -f " + "G://appname_IS_UNDEFINED_2019-10-28.log");
            inputStream = process.getInputStream();

            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (Exception e) {
            logger.error("日志输出失败", e);
        }
    }

    @OnClose
    public void onClose() {
        try {
            if (inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            logger.error("流关闭失败", e);
        }
        if (process != null)
            process.destroy();
    }

    @OnError
    public void onError(Throwable t) {
        logger.error("webSocket连接失败", t);
    }
}

class TailLogThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(TailLogThread.class);

    private BufferedReader reader;
    private Session session;

    public TailLogThread(InputStream in, Session session) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.session = session;
    }

    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                session.getBasicRemote().sendText(HtmlUtils.htmlEscape(line) + "<br>");
            }
        } catch (IOException e) {
            logger.error("输出失败", e);
        }
    }
}