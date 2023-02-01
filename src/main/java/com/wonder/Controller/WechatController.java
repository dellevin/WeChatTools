package com.wonder.Controller;


import com.wonder.Service.WechatService;
import com.wonder.dto.Message;
import com.wonder.util.WechatUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
//控制层
@RestController
//@RequestMapping("/wechat")
public class WechatController {

//    public static void main(String[] args) throws IOException {
//        //获取appid和密钥
//        Properties properties = new Properties();
//        InputStream in = new FileInputStream("src/main/resources/config.properties");
//        properties.load(in);
//        final String weChatAppId = properties.getProperty("weChatAppId");
//        final String weChatSecret = properties.getProperty("weChatSecret");
//
//    }
    @RequestMapping("/hello")
    public String test()
    {
        return "hello";
    }
    /**
     * 微信接口验证
     * @param request 请求参数
     * @return String
     */
    //改成GetMapping
    @GetMapping
    public String check(HttpServletRequest request){
        System.out.println("get方法");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //MDpgWKeQxj4yTf 是自己的token
        if (WechatUtils.checkSignature(signature, timestamp, nonce,"MDpgWKeQxj4yTf")) {
            System.out.println("检验通过");
            return echostr;
        }else {
            System.out.println("检验不通过");
            return "校验不通过";
        }
    }

    /**
     * 消息处理
     * @param requestMessage 请求消息
     * @return 响应消息或者“success”
     */
    //改成PostMapping用来接收POST请求，produces指定响应的类型为xml，RequestBody和实体类Message的Xml注解一起实现直接接收xml请求
    @RequestMapping(method = RequestMethod.POST,consumes = "text/xml", produces = "text/xml;charset=utf-8")
    public Object message(@RequestBody Message requestMessage,HttpServletRequest request){

        System.out.println("post方法入参："+requestMessage+" request.getMethod()"+request.getMethod());
        String fromUserName = requestMessage.getFromUserName();
        String toUserName = requestMessage.getToUserName();
        //新建一个响应对象
        Message responseMessage = new Message();
        //消息来自谁
        responseMessage.setFromUserName(toUserName);
        //消息发送给谁
        responseMessage.setToUserName(fromUserName);
        //消息类型，返回的是文本
        responseMessage.setMsgType("text");
        //消息创建时间，当前时间
        responseMessage.setCreateTime(System.currentTimeMillis());
        //这个是响应消息内容，直接复制收到的内容做演示，甚至整个响应对象都可以直接使用原请求参数对象，只需要换下from和to就可以了哈哈哈
        responseMessage.setContent(requestMessage.getContent());
        return responseMessage;
    }

    //测试的方法
    /*
    @RequestMapping("/postAndGet")
    public String checkValid(String signature, String timestamp,
                             String nonce, String echostr, HttpServletRequest req) {
        String requestMethod = req.getMethod();
        if (requestMethod.equals("POST")) { // 处理 POST 请求
            Map<String, String> msgMap = WechatService.parseXmlData2Map(req);
            System.out.println(msgMap);
            return "OK";
        } else if (requestMethod.equals("GET")) { // 处理 GET 请求
            return WechatUtils.checkSignature(signature,timestamp, nonce,"MDpgWKeQxj4yTf" ) ? echostr
                    : "校验失败";
        } else {
            return "不是 GET 和 POST";
        }
    }
     */

}
