package com.example.chatserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.entity.User;
import com.example.chatserver.domain.request.UpdateUserRequest;
import com.example.chatserver.domain.request.UserLoginRequest;
import com.example.chatserver.domain.request.UserRegisterRequest;
import com.example.chatserver.domain.vo.UserLoginVO;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-12-14 10:57:00
 */
public interface UserService extends IService<User> {

    UserLoginVO login(UserRegisterRequest userLoginRequest);

    ResponseResult register(UserRegisterRequest userLoginRequest);

    void logout(String username);

    ResponseResult adminLogin(UserLoginRequest userLoginRequest);

    ResponseResult getAllUser();

    ResponseResult updateUser(UpdateUserRequest updateUserRequest);
}

