package com.tencent.wxcloudrun.controller;

import com.alibaba.dashscope.utils.JsonUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * 支付宝消息收发控制器
 */
@RestController

public class WxLoginController {

  final Logger logger;

  public WxLoginController() {
    this.logger = LoggerFactory.getLogger(WxLoginController.class);
  }


  /**
   * 获取当前登录用户信息
   * @return API response json
   */
  @PostMapping(value = "/common/getUserInfo")
  String getUserInfo(@RequestBody String info, @RequestHeader Map<String, String> header) {
//    logger.info("info string = "+ info);

    String cloudId = JsonUtils.parse(info).get("info").getAsString();
    String appId = header.get("x-wx-from-appid");
    String openId = header.get("x-wx-from-openid");


    if ((StringUtils.isEmpty(appId))){
      logger.info("x-wx-from-appid为空,尝试从header中获取x-wx-appid");
      appId = header.get("x-wx-appid");
    }

    if ((StringUtils.isEmpty(openId))){
      logger.info("x-wx-from-openid为空,尝试从header中获取x-wx-openid");
      openId = header.get("x-wx-openid");
    }

    logger.info("/common/getUserInfo 收到请求,appId=" + appId + ",openId=" + openId + ",cloudId=" + cloudId);

    // 生成一段排序好的回复
    try{
      getUserInfoFromCloudId(appId,openId,cloudId);
    }catch (Exception e) {
      logger.error("有异常",e);
    }

    return openId;
  }

  private  String getUserInfoFromCloudId(String appId,String openId,String cloudId) throws Exception{

    HttpURLConnection con = null;
    BufferedReader buffer = null;
    StringBuffer resultBuffer = new StringBuffer();
    PrintWriter out = null;
    URL url = url = new URL("http://api.weixin.qq.com/wxa/getopendata?from_appid="+appId+"&openid="+openId);
    con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Accept", "*/*");
    con.setUseCaches(false);
    con.setDoInput(true);
    con.setDoOutput(true);

    JSONObject msgBody = new JSONObject();
    String[] cloudIdList = {cloudId};
    msgBody.put("cloudid_list",cloudIdList);
    out = new PrintWriter(con.getOutputStream());
    out.print(msgBody.toString());
    out.flush();

    int responseCode = con.getResponseCode();

    logger.info("responseCode = "+ responseCode);

    if (responseCode == HttpURLConnection.HTTP_OK) {
      //得到响应流
      InputStream inputStream = con.getInputStream();
      //将响应流转换成字符串
      resultBuffer = new StringBuffer();
      String line;
      buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
      while ((line = buffer.readLine()) != null) {
        resultBuffer.append(line);
      }
    }

    logger.info("resultBuffer = "+ resultBuffer.toString());

    return resultBuffer.toString();
  }


  public static void main(String[] args) {
    String jsonString = "{\n" +
            "  \"info\": \"87_YDX-ALdQCgI0G9VXBBv-3Ih0DRQ8xThmVXLt1s5ePRgIOECGK0DLndVU92Y\"\n" +
            "}";
    String cloudId = JsonUtils.parse(jsonString).get("info").getAsString();
    System.out.println(cloudId);
  }
  
}