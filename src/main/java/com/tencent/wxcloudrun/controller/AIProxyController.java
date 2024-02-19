package com.tencent.wxcloudrun.controller;


import com.alibaba.dashscope.aigc.conversation.Conversation;
import com.alibaba.dashscope.aigc.conversation.ConversationParam;
import com.alibaba.dashscope.aigc.conversation.ConversationResult;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.utils.JsonUtils;
import com.tencent.wxcloudrun.dto.MessagePushRequest;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


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

      jsonObject.put("touser", request.getFromUserName());
      jsonObject.put("msgtype", "text");
      Map<String,String> textValueMap = new HashMap<>();
      textValueMap.put("content", "API主动回复消息");
      jsonObject.put("text", textValueMap);
      customSend(jsonObject);


//      jsonObject.put("CreateTime", System.currentTimeMillis());

//      jsonObject.put("ToUserName", request.getFromUserName());
//      jsonObject.put("FromUserName", request.getToUserName());
//      jsonObject.put("MsgType", "text");
//      jsonObject.put("CreateTime", System.currentTimeMillis());

      // 调用chatModel生成聊天内容
//      String answer = chatModel.generate(request.getContent());

//      builder.prompt(request.getContent());
//      ConversationResult result = conversation.call(builder.build());
//      GenerationOutput output = result.getOutput();
//      jsonObject.put("Content", output.getText());
//      return jsonObject.toString();

    }catch (Exception e) {
      logger.error("有异常",e);
    }
    return "success";

  }

  private  void customSend(JSONObject msgBody) throws Exception{

    HttpURLConnection con = null;
    BufferedReader buffer = null;
    StringBuffer resultBuffer = null;
    PrintWriter out = null;
    URL url = null;

      url = new URL("http://api.weixin.qq.com/cgi-bin/message/custom/send");
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Accept", "*/*");
      con.setUseCaches(false);
      con.setDoInput(true);
      con.setDoOutput(true);
      out = new PrintWriter(con.getOutputStream());
      out.print(msgBody.toString());
      out.flush();

      int responseCode = con.getResponseCode();
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
        System.out.println("result:" + resultBuffer.toString());
      }
  }

//
  public static void main(String[] args) {

    try {

      JSONObject jsonObject = new JSONObject();
      jsonObject.put("touser", "openid-001");
      jsonObject.put("msgtype", "text");
      Map<String,String> textValueMap = new HashMap<>();
      textValueMap.put("content", "API主动回复消息");
      jsonObject.put("text", textValueMap);
      System.out.println(jsonObject.toString());

//      AIProxyController aiProxyController = new AIProxyController();
//      aiProxyController.builder.prompt("你是一名文学专家，请帮我梳理一下作品《红楼梦》中的人物关系");
//
//      Flowable<ConversationResult> result = aiProxyController.conversation.streamCall(aiProxyController.builder.build());
//      result.blockingForEach(msg->{
//
//        PrintWriter out = new PrintWriter(System.out, true);
//        out.println(msg.getOutput().getText());
//        System.out.print(msg.getOutput().getText());
//     });

//      System.exit(0);


//      ConversationResult result = aiProxyController.conversation.call(aiProxyController.builder.build());
//      GenerationOutput output = result.getOutput();
//      System.out.println(output.getText());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  
}