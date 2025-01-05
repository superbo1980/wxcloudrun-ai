package com.tencent.wxcloudrun.wxpay;


import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WxPayAutoCertificateConfig {

    @Resource
    private WxPayConfig wxPayConfig;


    public RSAAutoCertificateConfig getRSAAutoCertificateConfig() {


        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(wxPayConfig.getMerchantId())
                .merchantSerialNumber(wxPayConfig.getMerchantSerialNumber())
                .privateKeyFromPath(wxPayConfig.getPrivateKeyFromPath())
                .apiV3Key(wxPayConfig.getApiV3Key())
                .build();
        return config;
    }

//    public static void main(String[] args) {
//
//        System.out.println(WxPayConfig);
//    }



}
