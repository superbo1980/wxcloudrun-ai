package com.tencent.wxcloudrun.wxpay.WxPayConfig;

import lombok.Data;

@Data
public class WxPayConfig {

    private String appId = "wx41d948fc8f0f64d3";
    private String merchantId = "1453790202";
    private String merchantSerialNumber = "130E303509CC6C7ED2B852C3A7531ED3D951D73C";
    private String apiV3Key = "12345678abcdefgh87654321hgfedcba";
    private String notifyUrl = "https://prod-9g8bk31o2707e168-1324294718.tcloudbaseapp.com/wxpay/payNotify";

    static public String privateKeyFromPath;
    static {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        privateKeyFromPath = classloader.getResource("apiclient_key.pem").getPath();
    }

}
