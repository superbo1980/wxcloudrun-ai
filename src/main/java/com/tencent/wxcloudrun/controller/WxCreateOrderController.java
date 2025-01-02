package com.tencent.wxcloudrun.controller;

import com.alibaba.dashscope.utils.JsonUtils;
import com.tencent.wxcloudrun.config.R;
import com.tencent.wxcloudrun.wxpay.WxPayAutoCertificateConfig;
import com.tencent.wxcloudrun.wxpay.WxPayConfig.WxPayConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.model.Transaction;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 微信支付创建订单接口
 */
@RestController
public class WxCreateOrderController {

  final Logger logger;

  @Resource
  private WxPayConfig wxPayConfig;
  @Resource
  private WxPayAutoCertificateConfig wxPayAutoCertificateConfig;

  public WxCreateOrderController() {
    this.logger = LoggerFactory.getLogger(WxCreateOrderController.class);
  }


  /**
   * 获取当前登录用户信息
   * @return API response json
   */
  @PostMapping(value = "/wxpay/createOrder")
  R createPrepayOrder(@RequestHeader Map<String, String> header) {

    JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(wxPayAutoCertificateConfig.getRSAAutoCertificateConfig()).build();

    PrepayRequest prepayRequest = new PrepayRequest();
    prepayRequest.setAppid(wxPayConfig.getAppId());
    prepayRequest.setMchid(wxPayConfig.getMerchantId());
    prepayRequest.setOutTradeNo("123"+System.currentTimeMillis()+"");
    prepayRequest.setDescription("平台履约保障金");
    prepayRequest.setNotifyUrl(wxPayConfig.getNotifyUrl());

    // 订单金额
    Amount amount = new Amount();
    amount.setCurrency("CNY");
    amount.setTotal(1);
    prepayRequest.setAmount(amount);

    // 付款人openid
    Payer payer = new Payer();
    payer.setOpenid(header.get("x-wx-openid"));
    prepayRequest.setPayer(payer);

    logger.info("创建预支付订单,request="+ JsonUtils.toJson(prepayRequest));

    try{
      PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(prepayRequest);
      logger.info("创建预支付订单,response="+ JsonUtils.toJson(response));
      return R.ok(response);
    }catch (Exception e) {
      logger.error("有异常",e);
    }
    return R.error();
  }


  @PostMapping(value = "/wxpay/payNotify")
  String payNotify(@RequestBody String body, HttpServletRequest request) {

    String signature = request.getHeader("Wechatpay-Signature");
    String signatureType = request.getHeader("Wechatpay-Signature-Type");
    String timestamp = request.getHeader("Wechatpay-Timestamp");
    String nonce = request.getHeader("Wechatpay-Nonce");
    String serial = request.getHeader("Wechatpay-Serial");

    RequestParam requestParam = new RequestParam.Builder()
            .serialNumber(serial)
            .nonce(nonce)
            .signature(signature)
            .timestamp(timestamp)
            .signType(signatureType)
            .body(body).build();

    NotificationParser notificationParser = new NotificationParser(wxPayAutoCertificateConfig.getRSAAutoCertificateConfig());
    Transaction transaction = notificationParser.parse(requestParam,Transaction.class);
    Map<String,String> resultMap = new HashMap<>();

    if(Transaction.TradeStateEnum.SUCCESS!=transaction.getTradeState()){
      resultMap.put("code","FAIL");
      resultMap.put("message","失败");
    }

    resultMap.put("code","SUCCESS");
    resultMap.put("message","成功");
    return JSONObject.valueToString(resultMap);


  }

  
}