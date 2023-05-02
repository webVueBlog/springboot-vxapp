package com.wxapp.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxapp.app.config.annotation.RequiresPermissions;
import com.wxapp.app.handler.NoAuth;
import com.wxapp.app.service.ArticleService;
import com.wxapp.app.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 查询文章列表
     */
    @NoAuth
    @RequiresPermissions("article:list")
    @GetMapping("/listArticle")
    public JSONObject listArticle(HttpServletRequest request) {
        return articleService.listArticle(CommonUtil.request2Json(request));
    }
    /**
     * 新增文章
     */
    @NoAuth
    @RequiresPermissions("article:add")
    @PostMapping("/addArticle")
    public JSONObject addArticle(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "content");
        return articleService.addArticle(requestJson);
    }
    /**
     * 修改文章
     */
    @RequiresPermissions("article:update")
    @PostMapping("/updateArticle")
    public JSONObject updateArticle(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id,content");
        return articleService.updateArticle(requestJson);
    }
}
