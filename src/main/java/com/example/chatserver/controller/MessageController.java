package com.example.chatserver.controller;

import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.entity.Message;
import com.example.chatserver.mapper.MessageMapper;
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
    private MessageMapper messageMapper;

    @GetMapping("/chat/{screen}")
    public ResponseResult messageInitController(@PathVariable("screen") String screen) {
        // 从数据库获取消息
        List<Message> messageList = messageMapper.queryMessage(screen);
        return ResponseResult.okResult(messageList);
    }
}
