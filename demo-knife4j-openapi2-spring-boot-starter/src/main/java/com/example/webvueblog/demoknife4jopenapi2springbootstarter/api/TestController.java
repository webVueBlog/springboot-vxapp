package com.example.webvueblog.demoknife4jopenapi2springbootstarter.api;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
@Api(tags="测试api")
public class TestController {
    @GetMapping("time")
    @ApiOperation("time")
    public String time() {
        log.info("time: {}", DateUtil.date());
        return DateUtil.date().toString();
    }

    @GetMapping("time2")
    @ApiOperation("time2")
    public String time2() {
        log.info("time: {}", DateUtil.date());
        return DateUtil.date().toString();
    }
}
