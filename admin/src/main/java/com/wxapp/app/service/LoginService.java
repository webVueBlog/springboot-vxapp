package com.wxapp.app.service;

import com.alibaba.fastjson.JSONObject;
import com.wxapp.app.dao.LoginDao;
import com.wxapp.app.config.exception.CommonJsonException;
import com.wxapp.app.dto.session.SessionUserInfo;
import com.wxapp.app.utils.CommonUtil;
import com.wxapp.app.utils.constants.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {
    @Autowired
    private LoginDao loginDao;
    /**
     * 登录提交
     */
    public JSONObject authLogin(JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");
        JSONObject info = new JSONObject();
        JSONObject user = loginDao.checkUser(phone, code);
        if (user == null) {
            throw new CommonJsonException(ErrorEnum.E_10010);
        }
        String successMsg = "登录成功";
        info.put("msg", successMsg);
        return CommonUtil.successJson(info);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    public JSONObject getInfo(JSONObject jsonObject) {
        //从session获取用户信息
        String phone = jsonObject.getString("phone");
        //SessionUserInfo userInfo = tokenService.getUserInfo();
        SessionUserInfo userInfo = loginDao.getUserInfo(phone);
        log.info(userInfo.toString());
        return CommonUtil.successJson(userInfo);
    }

    /**
     * 推出登录
     */
    public JSONObject logout() {
        JSONObject info = new JSONObject();
        String successMsg = "登出成功";
        info.put("msg", successMsg);
        return CommonUtil.successJson(info);
    }
}
