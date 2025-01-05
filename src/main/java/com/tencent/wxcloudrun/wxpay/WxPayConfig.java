package com.tencent.wxcloudrun.wxpay;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WxPayConfig {

    private String appId = "wx41d948fc8f0f64d3";
    private String merchantId = "1453790202";
    private String merchantSerialNumber = "130E303509CC6C7ED2B852C3A7531ED3D951D73C";
    private String apiV3Key = "12345678abcdefgh87654321hgfedcba";
    private String notifyUrl = "https://prod-9g8bk31o2707e168-1324294718.tcloudbaseapp.com/wxpay/payNotify";
//    private String privateKeyFromPath = "/apiclient_key.pem";
    private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDAomfLpHcr/apK" +
        "bpK3Nsl1zUjQ6iaelSH2/WbZeva9CtfmKqud4x9VxlcPEYm9rqfxvL6VFdYpuc/D" +
        "iSg5turOxwLWuj8UHNIdCIyx5HiwStA1PKzpIecAHlRn+9pC2tskdY4K+EmAHoEj" +
        "Q+sGCgLTzA8ZWh2URNjg4/TPo1r+wc66xRpskiLnxhvZQOtI43QE0cWKYszgv3js" +
        "ahURx6fWU3J182XK5aqqASe1sHbaE/wPo4k9FiERh4v/JGqTElT9PY10We4VrYb8" +
        "7W1c6MCthTNfPR1SkGq+Wt1oN3FLNxxx2w6wbx63SjyIXWbf6V/GgHmNdRj25DkH" +
        "5Dt8VqVLAgMBAAECggEATGbddgZoAbmdNeI/LI19P8zIyZtk79bEXtrIVQ+RFw9a" +
        "6/4WjQuM3Q9Kbj6Ne/7uu1EZ4bWEeI4q2innG2TyIksKVDIQXKDFsSA39UJhn8I8" +
        "bDEBf4GjxgdBgPCB86wr6Hz7dlxdgoQWwe7pIz8LOgegIXT9cFYSErg227e1mn6B" +
        "gEdyJRPOOCal4t3ldRaCnFuUgVz3FEImBO86zsN/ht76aZfQTiVPWeyZPBFSQqav" +
        "9oQcAuftlB+3wk2/bN8nYHpSEDU2ds8yrKEkb4+V8kPfBGKrH+cHHYWOt8CQHe7l" +
        "x49GbXKYZtwwU6yOiES9GkB957nizY4wskB8fMpraQKBgQDmuxjoeQcT5zkOS2BH" +
        "h1nHaKm87omuC+UwpYzanjLN77ii1FA3/Lb52HajA+nolZsNInhwzm6sLmrcySjz" +
        "xZ6VbkfnpA/kGbgzNy9Vcdj9OQZdf0GebyZmPkv+teSDzn+GdzKlAgeybhDq/uss" +
        "zew9FKdmaiJ9yYg9+xWcUN29RwKBgQDVuzbQGSqHDLcvjpvDh0XHkUWFtXhXesXz" +
        "oCGccnmx9dN1Zp8Dje68zV2dtrRShl8LasJ033uOQdW+n8Di0/dijMk0XrC8WIpY" +
        "QV3XJUIJYg9uzf3iRuAu8XNa23/AOh6O1ZEUxIEmSTzfQLG4VWvA74FmLlNrHFSu" +
        "IufyEwdJ3QKBgCfjfKDkG4cjWEfSXCjBi5Hf1EhnbMH8KHlKhZld9CaKJP9xeRTU" +
        "ycVT2N7B1HRzOMUCipPl/FgkBjF5TW0nYw0ubZvsTxr+H0wrZDF/ZyYP77vGPk/n" +
        "o1nVt0mnpIPs9TsH7Gd+fFoqkR4vR/UM/KRqp64a9y+L4dPlTo0hpdQNAoGAbcNk" +
        "xgB2p76vATxy4DrDalc/5ggpUnvZ12FAgbEEsJE31M4sEc49cjbhwhJHjy40948P" +
        "aKgxMwC6fdhbXeJP5Qu6dSvABipOej6vq8MVo+35UQV3QlL15QXZISsi85ylfuNN" +
        "S+YTEY+BNIJsAufTm9Uklk5qRtLY5klE5zxAfP0CgYAGsfERqbCa+si+c0pR6GOe" +
        "ksJWg7rDmoYq86PQRuLc0dHJ8M7A97OGgz94Ee+dJMNfdTyDeVY86LVTBrKZEVjc" +
        "aYG0XYlvTWDyXEbSCx5BL6x5qOb2Q2KzVbLHplzoA9RYHOZ2Jqgg00AEkVM+7wRd" +
        "7Ty+Q3kWtuwAOwIdL516LQ==";

//    static {
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        privateKeyFromPath = classloader.getResource("apiclient_key.pem").getPath();
//    }

}
