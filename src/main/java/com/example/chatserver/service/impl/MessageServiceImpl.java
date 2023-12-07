package com.example.chatserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.chatserver.domain.entity.Message;
import com.example.chatserver.mapper.MessageMapper;
import com.example.chatserver.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Message)表服务实现类
 *
 * @author makejava
 * @since 2023-11-22 22:43:11
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {


    @Override
    public void saveMessage(Message message) {

        save(message);
    }

    @Override
    public List<Message> queryMessage(String screen) {
        return null;
    }


}

