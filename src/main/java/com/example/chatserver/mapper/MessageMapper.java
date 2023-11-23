package com.example.chatserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.chatserver.domain.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;


/**
 * (Message)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-22 22:43:05
 */
@Mapper
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface MessageMapper extends BaseMapper<Message> {

}

