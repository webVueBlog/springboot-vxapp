package com.example.springbootswagger2.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户添加 DTO")
public class UserAddDTO {

    @ApiModelProperty(value = "账号", required = true, example = "admin")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

    public String getUsername() {
        return username;
    }

    public UserAddDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAddDTO setPassword(String password) {
        this.password = password;
        return this;
    }

}