package com.example.chatserver.controller;

import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.enums.AppHttpCodeEnum;
import com.example.chatserver.domain.request.UserRegisterRequest;
import com.example.chatserver.domain.vo.UserLoginVO;
import com.example.chatserver.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    ResponseResult Login(@RequestBody UserRegisterRequest userLoginRequest){

        try{
            UserLoginVO userLoginVO = userService.login(userLoginRequest);
            System.out.println("登录成功");
            return ResponseResult.okResult(userLoginVO);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserRegisterRequest userLoginRequest){
        return userService.register(userLoginRequest);
    }

    @PostMapping("/logout/{username}")
    public ResponseResult logout(@PathVariable("username") String username){

        userService.logout(username);
        return null;
    }


}
