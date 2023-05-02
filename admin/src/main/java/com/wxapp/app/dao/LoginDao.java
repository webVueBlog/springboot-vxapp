package com.wxapp.app.dao;

import com.alibaba.fastjson.JSONObject;
import com.wxapp.app.dto.session.SessionUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface LoginDao {
    JSONObject checkUser(@Param("phone") String phone, @Param("code") String code);
    //Login login(@Param("phone") String phone, @Param("code") String code);

    SessionUserInfo getUserInfo(String phone);

    Set<String> getAllMenu();

    Set<String> getAllPermissionCode();
}
