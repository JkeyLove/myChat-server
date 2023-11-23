package com.example.chatserver.controller;

import com.example.chatserver.domain.entity.Message;
import com.example.chatserver.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class TestController {

    @Resource
    private MessageMapper messageMapper;

    @GetMapping("/test")
    public String testController(){
        log.info("请求成功");
        return "Get请求成功";
    }

    @GetMapping("/save")
    public String saveController(){
        log.info("保存消息");
        //messageMapper.insert(new Message(1,"爱哥","评论",null));
        return "保存消息";
    }


}
