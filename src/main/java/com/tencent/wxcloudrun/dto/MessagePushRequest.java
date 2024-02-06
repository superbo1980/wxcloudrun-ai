package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class MessagePushRequest {

  private String ToUserName;

  private String FromUserName;

  private String CreateTime;

  private String MsgType;

  private String Content;

  private String MsgId;


}
