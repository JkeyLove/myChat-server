package com.example.chatserver.hander;

import com.example.chatserver.utils.RedisCache;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        String username = request.getParameter("username");
        if( (Integer)(redisCache.getCacheObject("username:" + username)) == 1 ){    //如果已经登录
            return true;
        } else{
            response.getWriter().print("你访问的资源需要登录");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }
}
