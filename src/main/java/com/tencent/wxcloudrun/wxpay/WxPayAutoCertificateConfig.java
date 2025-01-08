package com.tencent.wxcloudrun.wxpay;


import com.wechat.pay.java.core.RSAPublicKeyConfig;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class WxPayAutoCertificateConfig {

    @Resource
    private WxPayConfig wxPayConfig;


    public RSAPublicKeyConfig getRSAPublicKeyConfig() {


        RSAPublicKeyConfig config = new RSAPublicKeyConfig.Builder()
                .merchantId(wxPayConfig.getMerchantId())
                .privateKey(wxPayConfig.getPrivateKey())
                .publicKey(wxPayConfig.getPublicKey())
                .publicKeyId(wxPayConfig.getPublicKeyId())
                .merchantSerialNumber(wxPayConfig.getMerchantSerialNumber())
                .apiV3Key(wxPayConfig.getApiV3Key())
                .build();
        return config;
    }

//    public RSAAutoCertificateConfig getRSAAutoCertificateConfig2() {
//        Config config = new RSAAutoCertificateConfig.Builder()
//                .merchantId(wxPayConfig.getMerchantId())
//                .merchantSerialNumber(wxPayConfig.getMerchantSerialNumber())
//                .privateKey(wxPayConfig.getPrivateKey())
//                .publicKey(wxPayConfig.getPublicKey())
////                .privateKeyFromPath(wxPayConfig.getPrivateKeyFromPath())
//                .apiV3Key(wxPayConfig.getApiV3Key())
//                .build();
//        return config;
//    }

//    public static void main(String[] args) {
//
//        System.out.println(WxPayConfig);
//    }



}
