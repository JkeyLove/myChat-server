package com.example.chatserver.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.entity.Message;
import com.example.chatserver.mapper.MessageMapper;
import com.example.chatserver.service.MessageService;
import com.example.chatserver.service.impl.MessageServiceImpl;
import com.example.chatserver.utils.SpringContextUtil;
import com.example.chatserver.utils.UuidUtil;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.example.chatserver.utils.UuidUtil.generateNumericUUID;
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
@ServerEndpoint("/endPoint/{screen}/{username}")
public class WebSocketServer {

    /**
     * 建立连接成功调用 (Session + 场景)
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("screen") String screen,@PathParam("username") String username,EndpointConfig config) throws IOException {
        log.info("-----------------------------------");
        log.info("[onOpen] session: {} 接入,用户名: {}, screen: {}", session, username ,screen);

        WebSocketServerPool.addDataConnect(session, screen);

        //群发消息：xx用户已进入聊天室
        Message message1 = Message.builder()
                .id(generateNumericUUID(18))
                .username(username)
                .content(username + "进入聊天室")
                .screen(screen)
                .createTime(LocalDateTime.now())
                .build();
        transmit(screen,username,message1);

        //WebSocketServerPool.sendMessage(session, configurationScreenService.queryAllJsonById(screen));
    }

    /**
     * 关闭连接时调用
     * @param session 连接
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason,@PathParam("screen") String screen,@PathParam("username") String username) {

        //群发消息：xx用户已离开聊天室
        Message message1 = Message.builder()
                .id(generateNumericUUID(18))
                .username(username)
                .content(username + "离开了聊天室")
                .screen(screen)
                .createTime(LocalDateTime.now())
                .build();
        transmit(screen,username,message1);



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
        log.info("[onClose]session: {} 发生异常", session, throwable);
        WebSocketServerPool.removeConnect(session);
    }

    /**
     * 收到客户端信息后，根据接收到的信息进行处理
     * @param session 连接
     * @param message 数据消息
     */
    @OnMessage
    public void onMessage(Session session, String message,@PathParam("username") String username) throws IOException {

        log.info("-----------------------------------");
        log.info("[onMessage] 用户: {} 接收到一条消息:{}", username, message);
        // TODO: 2022/5/18 对于客户端发送的指令信息，解析后进行对应的逻辑处理

        //找到该客户端session对应的房间screen
        String screen = getDataConnect().get(session);

        //把消息存入数据库
        MessageService messageService = SpringContextUtil.getContext().getBean(MessageServiceImpl.class);
        Message message1 = Message.builder()
                .username(username)
                .content(message)
                .screen(screen)
                .build();
        messageService.saveMessage(message1);

        //向对应房间screen,发送信息
        transmit(screen,username,message1);

    }
    public void transmit(String screen, String username, Message message1){
        getDataConnect().entrySet().stream()
                .filter(entry->entry.getValue().equals(screen))
                .forEach(entry->
                        {
                            try {
                                log.info("-----------------------------------");
                                log.info("用户: {} --> 房间号： {} 客户端：{} 发送消息：{}",username,entry.getValue(),entry.getKey(),message1.getContent());

                                ResponseResult<Object> responseResult = new ResponseResult<>();

                                List<Message> message = new ArrayList<>();
                                message.add(message1);
                                sendMessage(entry.getKey(),toJSONString(responseResult.ok(message)));
//                                sendMessage();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }


}
