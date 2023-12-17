package com.example.chatserver.controller;

import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.entity.User;
import com.example.chatserver.domain.enums.AppHttpCodeEnum;
import com.example.chatserver.domain.request.UserLoginRequest;
import com.example.chatserver.domain.vo.UserLoginVO;
import com.example.chatserver.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    ResponseResult Login(@RequestBody UserLoginRequest userLoginRequest){

        try{

            UserLoginVO userLoginVO = userService.login(userLoginRequest);
            System.out.println("登录成功");
            return ResponseResult.okResult(userLoginVO);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
    }
}
