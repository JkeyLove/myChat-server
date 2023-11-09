package com.example.chatserver.service;

import com.example.chatserver.websocket.WebSocketServer;
import lombok.Data;

import javax.annotation.Resource;
import javax.websocket.Session;

@Data
public class SoloRoom {

    private Session session1;
    private Session session2;

    @Resource
    private WebSocketServer webSocketServer;

    public SoloRoom(){



    }



}
