package com.example.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("tbl_weixin")
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //用户唯一标识
    private String openId;

    //昵称
    private String nickName;

    private String password;

    //性别
    private String gender;

    //城市
    private String city;

    //省份
    private String province;

    //国家
    private String country;

    //头像
    private String avatarUrl;

    //用户在开放平台的唯一标识
    private String unionId;

    //是否授权
    private Integer isAuth;

    //创建时间
    private LocalDateTime createTime;

    //修改时间
    private LocalDateTime updateTime;
}
