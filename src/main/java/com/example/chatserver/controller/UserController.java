package com.example.chatserver.controller;

import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.enums.AppHttpCodeEnum;
import com.example.chatserver.domain.request.UpdateUserRequest;
import com.example.chatserver.domain.request.UserLoginRequest;
import com.example.chatserver.domain.request.UserRegisterRequest;
import com.example.chatserver.domain.vo.UserLoginVO;
import com.example.chatserver.service.UserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/adminLogin")
    public ResponseResult adminLogin(@RequestBody UserLoginRequest userLoginRequest){

        return  userService.adminLogin(userLoginRequest);
    }

    @PostMapping("/logout/{username}")
    public ResponseResult logout(@PathVariable("username") String username){

        userService.logout(username);
        return null;
    }

    @GetMapping("/user")
    public ResponseResult getAllUser(){

        return userService.getAllUser();
    }

    @PostMapping("/user")
    public ResponseResult updateUser(@RequestBody UpdateUserRequest updateUserRequest){

        return userService.updateUser(updateUserRequest);
    }


}
