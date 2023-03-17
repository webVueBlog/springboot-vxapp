package com.example.app.pojo.dto;

import com.example.app.model.WxUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String gender;
    private String phoneNumber;
    private String background;
    private String avatar_url;
    private String openId;
    private String unionId;

    //dto拓展属性
    private String token;
    List<String> permissions;
    List<String> roles;
    // 验证码
    private String code;

    public void from(WxUserInfo wxUserInfo) {
        this.nickname = wxUserInfo.getNickName();
        this.avatar_url = wxUserInfo.getAvatarUrl();
        this.username = "";
        this.password = "";
        this.phoneNumber = "";
        this.gender = wxUserInfo.getGender();
        this.openId = wxUserInfo.getOpenId();
        this.unionId = wxUserInfo.getUnionId();
    }
}
