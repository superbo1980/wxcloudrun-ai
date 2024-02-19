package com.tencent.wxcloudrun.controller;


import com.alibaba.dashscope.aigc.conversation.Conversation;
import com.alibaba.dashscope.aigc.conversation.ConversationParam;
import com.alibaba.dashscope.aigc.conversation.ConversationResult;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.utils.JsonUtils;
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

//  private final String openaiKey = "";
//
//  private final  String baseUrl = "https://api.openai-proxy.com/v1";
//
//  private final  String model = "gpt-3.5-turbo";

//  private OpenAiChatModel chatModel;

  private ConversationParam.ConversationParamBuilder builder ;

  private Conversation conversation;

  public AIProxyController() {
    this.logger = LoggerFactory.getLogger(AIProxyController.class);
    conversation = new Conversation();
    builder =  ConversationParam.builder();
    builder.apiKey("sk-acc123223beb481d9e0180bbf5d730db");
    builder.model(Conversation.Models.QWEN_TURBO);

//    OpenAiChatModel.OpenAiChatModelBuilder builder =  OpenAiChatModel.builder();
//    builder.baseUrl(baseUrl);
//    builder.apiKey(openaiKey);
//    builder.modelName(model);
//    this.chatModel = builder.build();

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
//      String answer = chatModel.generate(request.getContent());

      builder.prompt(request.getContent());
      ConversationResult result = conversation.call(builder.build());
      GenerationOutput output = result.getOutput();
      jsonObject.put("Content", output.getText());
      return jsonObject.toString();

    }catch (Exception e) {
      logger.error("有异常",e);
      return "success";
    }

  }
//
//  public static void main(String[] args) {
//
//    try {
//      AIProxyController aiProxyController = new AIProxyController();
//      aiProxyController.builder.prompt("你是一名文学专家，请帮我梳理一下作品《红楼梦》中的人物关系");
//      ConversationResult result = aiProxyController.conversation.call(aiProxyController.builder.build());
//      GenerationOutput output = result.getOutput();
//      System.out.println(output.getText());
//    }catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  
}