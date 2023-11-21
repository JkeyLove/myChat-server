package com.example.chatserver.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.chatserver.websocket.WebSocketServerPool.*;


/**
 * @author HFL
 * @date 2022/5/16 15:17
 * Websocket应用实现:
 *  1.建立连接，连接放入连接池
 *  2.关闭连接，连接移出连接池
 *  3.接收客户端发送的消息，并做出相应处理
 *  4.注入业务层的service
 *  [注意：Spring管理的Bean是单例模式的，而WebSocket不是单例，注入时需要处理一下]
 *  5.异常处理，连接移除连接池
*/
@Slf4j
@Component
@ServerEndpoint("/endPoint/{screen}")
public class WebSocketServer {

    /**
     * 建立连接成功调用 (Session + 场景)
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("screen") String screen,EndpointConfig config) throws IOException {
        log.info("[onOpen] session:{} 接入, screen: {}", session, screen);
        WebSocketServerPool.addDataConnect(session, screen);


        //WebSocketServerPool.sendMessage(session, configurationScreenService.queryAllJsonById(screen));
    }

    /**
     * 关闭连接时调用
     * @param session 连接
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("[onClose] session:{} 连接关闭。关闭原因是({})}", session, closeReason);
        WebSocketServerPool.removeConnect(session);
    }

    /**
     * 错误时调用
     * @param session 连接
     * @param throwable 异常
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info("[onClose]session:{} 发生异常", session, throwable);
        WebSocketServerPool.removeConnect(session);
    }

    /**
     * 收到客户端信息后，根据接收到的信息进行处理
     * @param session 连接
     * @param message 数据消息
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        log.info("[onOpen] session:{} 接收到一条消息:{}", session, message);
        // TODO: 2022/5/18 对于客户端发送的指令信息，解析后进行对应的逻辑处理
        //找到该客户端session对应的房间screen
        String screen = getDataConnect().get(session);
        log.info("房间号:{}",screen);


        //向对应房间screen,发送信息
        getDataConnect().entrySet().stream()
                        .filter(entry->entry.getValue().equals(screen))
                                .forEach(entry->
                                        {
                                            try {
                                                log.info("服务端-->客户端{} 发送消息：{},房间号：{}",entry.getKey(),message,entry.getValue());
                                                sendMessage(entry.getKey(),"服务端向客户端发送："+message);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                        );
        // sendMessage(session,"服务端向客户端发送："+message);
        //sendMessage(session,"服务端向客户端发送："+message);

    }

}
