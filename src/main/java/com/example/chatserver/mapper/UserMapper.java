package com.example.chatserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.chatserver.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-14 10:56:58
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select  * from chat_server.user where username = #{username}")
    User queryByUsername(String username);


    @Select("select is_vip from user where username = #{username}")
    Integer queryVip(String username);
}

