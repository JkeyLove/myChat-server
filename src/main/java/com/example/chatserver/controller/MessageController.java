package com.example.chatserver.controller;

import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.entity.Message;
import com.example.chatserver.mapper.MessageMapper;
import com.example.chatserver.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;


@RestController
@Slf4j
public class MessageController {


    @Resource
    private MessageService messageService;


    @GetMapping("/chat/{screen}/{username}")
    public ResponseResult messageInitController(@PathVariable("screen") String screen,@PathVariable("username") String username) {
        // 从数据库获取消息
        //List<Message> messageList = messageMapper.queryMessage(screen);

        List<Message> messageList = messageService.queryMessage(screen,username);

        return ResponseResult.okResult(messageList);


    }
}
