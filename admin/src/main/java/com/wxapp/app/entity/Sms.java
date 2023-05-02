package com.wxapp.app.entity;

public class Sms {
    private String phoneNumber;
    private String code;
    private int min;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public  void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
}
