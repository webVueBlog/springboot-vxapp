package com.wxapp.app.config.exception;

import com.alibaba.fastjson.JSONObject;
import com.wxapp.app.utils.CommonUtil;
import com.wxapp.app.utils.constants.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常拦截
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ExceptionHandler(value = Exception.class)
    public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) {
        String errorPosition ="";
        //如果错误堆栈信息存在
        if (e.getStackTrace().length > 0) {
            StackTraceElement element = e.getStackTrace()[0];
            String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
            int lineNumber = element.getLineNumber();
            errorPosition = "位置：" + fileName + "行" + lineNumber;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ErrorEnum.E_400.getErrorCode());
        jsonObject.put("msg", ErrorEnum.E_400.getErrorMsg());
        JSONObject errorObject = new JSONObject();
        errorObject.put("errorPosition", errorPosition);
        jsonObject.put("info", errorObject);
        logger.error("[" + req.getRemoteAddr() + "]找不到要处理的异常![" + e);
        return jsonObject;
    }

    /**
     * GET/POST请求方法错误的拦截器
     * 因为开发时可能比较常见，而且发生在进入controller之前，上面的拦截器拦截不到这个错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JSONObject httpRequestMethodHandler() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ErrorEnum.E_500.getErrorCode());
        jsonObject.put("msg", ErrorEnum.E_500.getErrorMsg());
        logger.error("POST/GET请求不能在此处处理!");
        return jsonObject;
    }

    /**
     * 本系统自定义错误的拦截器
     * 拦截到此错误之后，就返回这个类里面的的json给前端
     * 常见使用常见时参数检验失败，抛出此错，返回错误信息给前端
     */
    @ExceptionHandler(CommonJsonException.class)
    public JSONObject commonJsonExceptionHandler(CommonJsonException commonJsonException) {
        return commonJsonException.getResultJson();
    }

    /**
     * 权限不足报错拦截
     */
    @ExceptionHandler(UnauthorizedException.class)
    public JSONObject unauthorizedExceptionHandler() {
        return CommonUtil.errorJson(ErrorEnum.E_502);
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口，而连登录都还没登录的时候，会报此错
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public JSONObject unauthenticatedException() {
        return CommonUtil.errorJson(ErrorEnum.E_20011);
    }
}
