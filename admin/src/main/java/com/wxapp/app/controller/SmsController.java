package com.wxapp.app.controller;

import cn.hutool.json.JSONException;
import com.wxapp.app.entity.Sms;
import com.wxapp.app.handler.NoAuth;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/sms")
@Slf4j
public class SmsController {
    @NoAuth
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public void sendCode(@RequestBody Sms sms) {
        int appid = 0;
        String appkey = "";
        int templateId = 0;
        String smsSign = "";

        try{
            String[] params = {sms.getCode()}; //短信中的参数
            SmsSingleSender ssender = new SmsSingleSender(appid,appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86",sms.getPhoneNumber(),templateId,
                    params,smsSign,"","");
            System.out.println(result);
        }catch (HTTPException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}