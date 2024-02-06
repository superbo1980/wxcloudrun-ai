package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class MessagePushRequest {

  private String toUserName;

  private String fromUserName;

  private String createTime;

  private String msgType;

  private String content;

  private String msgId;



}
