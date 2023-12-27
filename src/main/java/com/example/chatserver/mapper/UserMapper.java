package com.example.chatserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.chatserver.domain.entity.User;
import com.example.chatserver.domain.request.UpdateUserRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


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

    @Update("update chat_server.user set user.is_vip = #{status}  where username = #{username} ")
    void updateUser(String username,Integer status);
}

