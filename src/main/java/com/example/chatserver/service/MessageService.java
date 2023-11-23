package com.example.chatserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.chatserver.domain.entity.Message;

import java.util.List;


/**
 * (Message)表服务接口
 *
 * @author makejava
 * @since 2023-11-22 22:43:11
 */
public interface MessageService extends IService<Message> {

    public void saveMessage(Message message);

    public List<Message> queryMessage(String username);

}

