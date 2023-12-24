package com.example.chatserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.chatserver.common.ResponseResult;
import com.example.chatserver.domain.entity.User;
import com.example.chatserver.domain.enums.AppHttpCodeEnum;
import com.example.chatserver.domain.request.UserRegisterRequest;
import com.example.chatserver.domain.vo.UserLoginVO;
import com.example.chatserver.mapper.UserMapper;
import com.example.chatserver.service.UserService;
import com.example.chatserver.utils.BeanCopyUtils;
import com.example.chatserver.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        if ((user.getPassword()).equals(userLoginRequest.getPassword())){   //

            UserLoginVO userLoginVO = BeanCopyUtils.copyBean(user, UserLoginVO.class);
            //1代表该用户已登录
            redisCache.setCacheObject("username:" + userLoginVO.getUsername(),1);
            Object cacheObject = redisCache.getCacheObject("username:" + userLoginVO.getUsername());

            System.out.println(cacheObject);
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

}
