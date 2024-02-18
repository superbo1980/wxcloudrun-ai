package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.dto.MessagePushRequest;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 公众号消息收发控制器
 */
@RestController

public class AIProxyController {

  final Logger logger;

  private final String openaiKey = "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";

  private final  String baseUrl = "https://api.openai-proxy.com/v1";

  private final  String model = "gpt-3.5-turbo";

  private OpenAiChatModel chatModel;

  public AIProxyController() {
    this.logger = LoggerFactory.getLogger(AIProxyController.class);
    OpenAiChatModel.OpenAiChatModelBuilder builder =  OpenAiChatModel.builder();
    builder.baseUrl(baseUrl);
    builder.apiKey(openaiKey);
    builder.modelName(model);
    this.chatModel = builder.build();
  }


  /**
   * 获取当前计数
   * @return API response json
   */
  @PostMapping(value = "/ai/msg")
  String get(@RequestBody MessagePushRequest request) {

    logger.info("/ai/msg 获得公众号消息转发请求");

    logger.info("ToUser=" + request.getToUserName() + ",FromUser=" + request.getFromUserName() + ",Content=" + request.getContent() + ",MsgId=" + request.getMsgId() + ",MsgType=" + request.getMsgType());

    JSONObject jsonObject = new JSONObject();
    try{
      jsonObject.put("ToUserName", request.getFromUserName());
      jsonObject.put("FromUserName", request.getToUserName());
      jsonObject.put("MsgType", "text");
      jsonObject.put("CreateTime", System.currentTimeMillis());

      // 调用chatModel生成聊天内容
      String answer = chatModel.generate("hello");

      jsonObject.put("Content", answer);
      return jsonObject.toString();

    }catch (Exception e) {
      logger.error("JSON对象组装异常",e);
      return "success";
    }

  }


//  /**
//   * 更新计数，自增或者清零
//   * @param request {@link CounterRequest}
//   * @return API response json
//   */
//  @PostMapping(value = "/api/count")
//  ApiResponse create(@RequestBody CounterRequest request) {
//    logger.info("/api/count post request, action: {}", request.getAction());
//
//    Optional<Counter> curCounter = counterService.getCounter(1);
//    if (request.getAction().equals("inc")) {
//      Integer count = 1;
//      if (curCounter.isPresent()) {
//        count += curCounter.get().getCount();
//      }
//      Counter counter = new Counter();
//      counter.setId(1);
//      counter.setCount(count);
//      counterService.upsertCount(counter);
//      return ApiResponse.ok(count);
//    } else if (request.getAction().equals("clear")) {
//      if (!curCounter.isPresent()) {
//        return ApiResponse.ok(0);
//      }
//      counterService.clearCount(1);
//      return ApiResponse.ok(0);
//    } else {
//      return ApiResponse.error("参数action错误");
//    }
//  }
  
}