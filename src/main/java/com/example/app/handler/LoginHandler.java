package com.example.app.handler;

import com.alibaba.fastjson.JSON;
import com.example.app.common.RedisKey;
import com.example.app.common.Result;
import com.example.app.pojo.dto.UserDto;
import com.example.app.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器的使用 要放入 mvc配置当中
@Component
public class LoginHandler implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return HandlerInterceptor.super.preHandle(request, response, handler);
        //方法执行之前 进行拦截
        /**
         * 1.判断请求是否是请求controller方法
         * 2.有些接口是不需要登录拦截，需要开放自定义的注解 @NoAuth 此注解标识的 不需要登录
         * 3.拿到token
         * 4.token认证 -> user信息
         * 5.如果redis认证通过 就放行 认证不通过 返回未登录
         * 6.得到了用户信息，放入ThreadLocal当中
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.hasMethodAnnotation(NoAuth.class)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        boolean verify = JWTUtils.verify(token);
        if (!verify) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.FAIL("未登录")));
            return false;
        }
        String userJson = redisTemplate.opsForValue().get(RedisKey.TOKEN + token);
        if (StringUtils.isBlank(userJson)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.FAIL("未登录")));
            return false;
        }
        UserDto userDto = JSON.parseObject(userJson, UserDto.class);
        // 得到了用户信息，放入ThreadLocal当中
        UserThreadLocal.put(userDto);
        return true;
    }
}
