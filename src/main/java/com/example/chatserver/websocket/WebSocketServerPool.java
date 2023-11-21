package com.example.chatserver.websocket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author HFL
 * @date 2022/5/16 9:39
 * Websocket连接池、对连接池内连接操作 和数据推送方法
 */
@Slf4j
public class WebSocketServerPool {

    /**
     * WebSocket连接池
     */
    private static ConcurrentMap<Session, String> dataConnect = new ConcurrentHashMap<>();

    /**
     * 将websocket连接，放入连接池
     * @param session websocket连接
     * @param screen 场景ID
     */
    public static void addDataConnect(Session session, String screen){
        dataConnect.put(session, screen);
        Iterator<Map.Entry<Session, String>> iterator = dataConnect.entrySet().iterator();
        synchronized (iterator){
            //移除失效连接
            while(iterator.hasNext()){
                Map.Entry<Session, String> entry = iterator.next();
                Session sessionNew = entry.getKey();
                Map<String, Object> userProperties = sessionNew.getUserProperties();
                if(null != userProperties && null != userProperties.get("ReadyState") && "0" != String.valueOf(userProperties.get("ReadyState"))){
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 将websocket连接从连接池中移除
     * @param session websocket连接
     */
    public static void removeConnect(Session session){
        Iterator<Map.Entry<Session, String>> iterator = dataConnect.entrySet().iterator();
        synchronized (iterator){
            //主动移除连接
            while (iterator.hasNext()){
                if(session.equals(iterator.next().getKey())){
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 获取连接池中所有连接
     * @return 连接池所有数据
     */
    public static ConcurrentMap<Session, String> getDataConnect(){
        return dataConnect;
    }

    /**
     * Websocket消息推送：将消息推送给客户端
     * @param session 连接
     * @param message 消息主体
     * @throws IOException I/O异常
     */
    public static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
