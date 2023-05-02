package com.wxapp.app.controller;

import com.wxapp.app.common.Result;
import com.wxapp.app.handler.NoAuth;
import com.wxapp.app.model.WXAuth;
import com.wxapp.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    //getSessionId
    @GetMapping("/getSessionId")
    @NoAuth
    public Result getSessionId(String code) {
        return userService.getSessionId(code);
    }

    @PostMapping("/authLogin")
    @NoAuth
    public Result authLogin(@RequestBody WXAuth wxAuth) {
        Result result = userService.authLogin(wxAuth);
        log.info("{}", result);
        return result;
    }

    @GetMapping("userinfo")
    public Result userinfo(Boolean refresh) {
        return userService.userinfo(refresh);
    }
}
