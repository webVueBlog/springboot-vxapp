package com.wxapp.app.entity;

import java.util.Date;

public class Login {
    private int id;
    private String phone;
    private String code;
    private byte role;
    private Date ctime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getRole() {
        return role;
    }

    public void setRole(byte role) {
        this.role = role;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "Login [id=" + id + ", phone=" + phone + ", code=" + code + ", ctime=" + ctime + ", getId()=" + getId() + ", getPhone()=" + getPhone() + ", getCode()=" + getCode() + ", getCtime()=" + getCtime() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
