package com.wxapp.app.handler;

import com.alibaba.fastjson.JSON;
import com.wxapp.app.common.RedisKey;
import com.wxapp.app.common.Result;
import com.wxapp.app.pojo.dto.UserDto;
import com.wxapp.app.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器的使用 要放入 mvc配置当中
// 用于拦截请求并进行身份认证和授权的操作。
@Component
public class LoginHandler implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *
     * @param request HTTP 请求对象
     * @param response HTTP 响应对象
     * @param handler 表示待处理的目标对象
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return HandlerInterceptor.super.preHandle(request, response, handler);
        //方法执行之前 进行拦截
        /**
         * 1.判断请求是否是请求 controller 方法
         * 2.有些接口是不需要登录拦截，需要开放自定义的注解 @NoAuth 此注解标识的 不需要登录
         * 3.拿到token
         * 4.token认证 -> user信息
         * 5.如果 redis 认证通过 就放行 认证不通过 返回未登录
         * 6.得到了用户信息，放入 ThreadLocal 当中
         */
        // 判断 handler 是否为 HandlerMethod 的实例。如果不是，则直接返回 true，表示放行。
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 将 handler 强制转换为 HandlerMethod 对象，以便后面能够获取处理该请求的 Controller 方法及其注解信息。
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 判断该 Controller 方法是否被 @NoAuth 注解标记，如果有，则直接返回 true，表示放行。
        if (handlerMethod.hasMethodAnnotation(NoAuth.class)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        // 从 HTTP 请求头中获取认证 Token，由于 Token 一般以 Bearer  开头，所以需要将其去掉。

        boolean verify = JWTUtils.verify(token);
        // 使用 JWT 进行 Token 认证，如果认证失败，则返回错误信息并直接返回 false，表示拦截该请求。
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
        UserThreadLocal.put(userDto); // 得到了用户信息，放入ThreadLocal当中
        return true;
    }
}
