package com.wxapp.app.dto.session;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 保存在session中的用户信息
 */
@Data
public class SessionUserInfo {
    private int id;
    private String phone;
    private String username;
    private String nickname;
    private List<Integer> roleIds;
    private Set<String> menuList;
    private Set<String> permissionList;
}
