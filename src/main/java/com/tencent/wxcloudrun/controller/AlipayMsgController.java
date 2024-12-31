package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dto.MessagePushRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 支付宝消息收发控制器
 */
@RestController

public class AlipayMsgController {

  final Logger logger;

  public AlipayMsgController() {
    this.logger = LoggerFactory.getLogger(AlipayMsgController.class);
  }


  /**
   * 获取当前计数
   * @return API response json
   */
  @PostMapping(value = "/alipay/msg")
  String get(@RequestBody MessagePushRequest request) {

    logger.info("/alipay/msg 收到支付宝异步通知消息");

    // 更新本地订单为已支付

    // 计算收取费用、调用微信转账接口进行转账

    // 更新本地订单状态为已打款

    logger.info("ToUser=" + request.getToUserName() + ",FromUser=" + request.getFromUserName() + ",Content=" + request.getContent() + ",MsgId=" + request.getMsgId() + ",MsgType=" + request.getMsgType());

    // 生成一段排序好的回复
    try{

    }catch (Exception e) {
      logger.error("有异常",e);
    }

    return "success";

  }


  
}