package com.wxapp.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxapp.app.handler.NoAuth;
import com.wxapp.app.service.LoginService;
import com.wxapp.app.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
    @NoAuth
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    //@PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject requestJSON) {
        CommonUtil.hasAllRequired(requestJSON, "phone,code");
        return loginService.authLogin(requestJSON);
    }

    /**
     * 查询当前登录用户的信息
     */
    @NoAuth
    @PostMapping("/getInfo")
    public JSONObject getInfo(@RequestBody JSONObject requestJSON) {
        return loginService.getInfo(requestJSON);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public JSONObject logout() {
        return loginService.logout();
    }
}
