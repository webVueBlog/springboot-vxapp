package com.wxapp.app.config.exception;

/**
 * 访问权限不走的接口？
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("用户无此接口权限");
    }
}
