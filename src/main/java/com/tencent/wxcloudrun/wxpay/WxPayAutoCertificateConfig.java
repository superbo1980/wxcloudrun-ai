package com.tencent.wxcloudrun.wxpay;


import com.tencent.wxcloudrun.wxpay.WxPayConfig.WxPayConfig;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
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


}
