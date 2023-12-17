package com.example.chatserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.chatserver.domain.entity.User;
import com.example.chatserver.domain.request.UserLoginRequest;
import com.example.chatserver.domain.vo.UserLoginVO;
import com.example.chatserver.mapper.UserMapper;
import com.example.chatserver.service.UserService;
import com.example.chatserver.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public UserLoginVO login(UserLoginRequest userLoginRequest) {
        User user = userMapper.queryByUsername(userLoginRequest.getUsername());
        if ((user.getPassword()).equals(userLoginRequest.getPassword())){   //

            UserLoginVO userLoginVO = BeanCopyUtils.copyBean(user, UserLoginVO.class);
            return userLoginVO;
        }else {
            throw new RuntimeException("用户名或密码错误");
        }

    }
}
