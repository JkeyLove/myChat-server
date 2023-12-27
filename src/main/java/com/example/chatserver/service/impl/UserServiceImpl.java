package com.example.chatserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.entity.User;
import com.example.chatserver.domain.enums.AppHttpCodeEnum;
import com.example.chatserver.domain.request.UpdateUserRequest;
import com.example.chatserver.domain.request.UserLoginRequest;
import com.example.chatserver.domain.request.UserRegisterRequest;
import com.example.chatserver.domain.vo.UserLoginVO;
import com.example.chatserver.mapper.UserMapper;
import com.example.chatserver.service.UserService;
import com.example.chatserver.utils.BeanCopyUtils;
import com.example.chatserver.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisCache redisCache;
    @Override
    public UserLoginVO login(UserRegisterRequest userLoginRequest) {
        User user = userMapper.queryByUsername(userLoginRequest.getUsername());
        if ((user.getPassword()).equals(userLoginRequest.getPassword())){   //判断账号密码相等
            UserLoginVO userLoginVO = BeanCopyUtils.copyBean(user, UserLoginVO.class);
            //设置用户登录状态，1代表该用户已登录
            redisCache.setCacheObject("username:" + userLoginVO.getUsername(),1);
            return userLoginVO;
        }else {
            throw new RuntimeException("用户名或密码错误");
        }
    }

    @Override
    public ResponseResult register(UserRegisterRequest userLoginRequest) {
        try {
            String username = userMapper.queryByUsername(userLoginRequest.getUsername()).getUsername();
            log.info("用户名重复");
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }catch (Exception e){//查不到改用户执行保存用户信息
            save(new User(null,userLoginRequest.getUsername(),userLoginRequest.getPassword(),0));

            log.info("注册成功");
        }
        return ResponseResult.okResult();
    }

    @Override
    public void logout(String username) {
        redisCache.setCacheObject("username:" + username,0);
    }

    @Override
    public ResponseResult adminLogin(UserLoginRequest userLoginRequest) {
        User user = userMapper.queryByUsername(userLoginRequest.getUsername());
        if (user.getIsVip() == 2 &&  (user.getPassword()).equals(userLoginRequest.getPassword())){   //判断账号密码相等
            UserLoginVO userLoginVO = BeanCopyUtils.copyBean(user, UserLoginVO.class);
            //设置用户登录状态，1代表该用户已登录
            redisCache.setCacheObject("username:" + userLoginVO.getUsername(),1);
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
    }

    @Override
    public ResponseResult getAllUser() {
        List<UserLoginVO> userInfoVOList = BeanCopyUtils.copyBeanList(list(), UserLoginVO.class);
        return ResponseResult.okResult(userInfoVOList);
    }

    @Override
    public ResponseResult updateUser(UpdateUserRequest updateUserRequest) {
        userMapper.updateUser(updateUserRequest.getUsername(),updateUserRequest.getStatus());
        return ResponseResult.okResult();
    }
}
