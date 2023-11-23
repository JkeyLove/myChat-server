package com.example.chatserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试WebSocket连接
 * WebSocket在线测试工具：
 * http://www.easyswoole.com/wstool.html
 * 测试连接
 * 服务地址：ws://127.0.0.1：8848/endPoint/1
 * 服务启动的IP：127.0.0.1
 * 服务端口：8848
 * WS的URl：/endPoint
 * 入参：1
 */
@SpringBootApplication
@MapperScan
public class ChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }

}
