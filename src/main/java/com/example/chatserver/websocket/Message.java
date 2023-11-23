package com.example.chatserver.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String username;

    private String content;


    //每个房间的消息,<房间号,消息集合>
    public static Map<String,List<String>> messageMap;

    //消息集合
    public static List<String> messageList;

    public static Map<String,List<String>> getMessageMap(){
        return messageMap;
    }

    //根据房间号获取消息集合
    public static List<String> messageList(String screen){
        return messageMap.get(screen);
    }

}
