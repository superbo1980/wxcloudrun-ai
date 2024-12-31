package com.tencent.wxcloudrun.controller;


import com.alibaba.dashscope.aigc.conversation.Conversation;
import com.alibaba.dashscope.aigc.conversation.ConversationParam;
import com.alibaba.dashscope.aigc.conversation.ConversationResult;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.tencent.wxcloudrun.dto.MessagePushRequest;
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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * 公众号消息收发控制器
 */
@RestController

public class AIProxyController {

  final Logger logger;

  private ConversationParam.ConversationParamBuilder builder ;

  private Conversation conversation;

  public AIProxyController() {
    this.logger = LoggerFactory.getLogger(AIProxyController.class);
    conversation = new Conversation();
    builder =  ConversationParam.builder();
    builder.apiKey("sk-acc123223beb481d9e0180bbf5d730db");
    builder.model(Conversation.Models.QWEN_TURBO);
  }


  /**
   * 获取当前计数
   * @return API response json
   */
  @PostMapping(value = "/ai/msg")
  String get(@RequestBody MessagePushRequest request) {

    logger.info("/ai/msg 获得公众号消息转发请求");

    logger.info("ToUser=" + request.getToUserName() + ",FromUser=" + request.getFromUserName() + ",Content=" + request.getContent() + ",MsgId=" + request.getMsgId() + ",MsgType=" + request.getMsgType());

    // 调用AI模型生成聊天内容
    asynExecute(request);
    // 生成一段排序好的回复
    try{
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("ToUserName", request.getFromUserName());
      jsonObject.put("FromUserName", request.getToUserName());
      jsonObject.put("MsgType", "text");
      jsonObject.put("Content", "亲！请稍等片刻，【智顾小宝】正在思考中...");
      jsonObject.put("CreateTime", System.currentTimeMillis());
      return jsonObject.toString();
    }catch (Exception e) {
      logger.error("有异常",e);
    }

    // 先实时返回给公众号默认响应
    try{
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("ToUserName", request.getFromUserName());
      jsonObject.put("FromUserName", request.getToUserName());
      jsonObject.put("MsgType", "text");
      jsonObject.put("Content", "亲！请稍等片刻，【智顾小宝】正在思考中...");
      jsonObject.put("CreateTime", System.currentTimeMillis());
      return jsonObject.toString();
    }catch (Exception e) {
      logger.error("有异常",e);
    }
    return "success";

  }

  /**
   * 异步执行AI模型调用
   * @param request
   */
  private void asynExecute(MessagePushRequest request) {


    new Thread(new Runnable() {
      @Override
      public void run() {
        logger.info("异步线程Start");
        try {
          Thread.sleep(200);
          logger.info("异步线程休眠500ms后开始正式执行");
          String answer = callAIModelService(request.getContent());
          JSONObject wxRequest = buildWxAPIRequest(request.getFromUserName(), answer);
          String wxResponse = wxMsgReply(wxRequest);
          logger.info("异步线程End:"+wxResponse);
        } catch (Exception e) {
          logger.error("异步线程执行异常", e);
        }
      }
    }).start();
  }

  private JSONObject buildWxAPIRequest(String fromUserName, String answer) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("touser", fromUserName);
    jsonObject.put("msgtype", "text");
    Map<String,String> textValueMap = new HashMap<>();
    textValueMap.put("content", answer);
    jsonObject.put("text", textValueMap);
    return jsonObject;
  }


  private  String callAIModelService(String prompt) throws Exception{
    builder.prompt(prompt);
    ConversationResult result = conversation.call(builder.build());
    GenerationOutput output = result.getOutput();
    return output.getText();

  }

  private  String wxMsgReply(JSONObject msgBody) throws Exception{

    HttpURLConnection con = null;
    BufferedReader buffer = null;
    StringBuffer resultBuffer = new StringBuffer();
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
      }
      return resultBuffer.toString();
  }

//
  public static void main(String[] args) {
    try {
      System.out.println("你好");
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  
}