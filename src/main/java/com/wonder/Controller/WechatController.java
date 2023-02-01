package com.wonder.Controller;


import com.wonder.Service.Impl.WechatServiceImpl;
import com.wonder.Service.WechatService;
import com.wonder.dto.Message;

import com.wonder.util.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object message(@RequestBody Message requestMessage) throws Exception {
        WechatService wechatService = new WechatServiceImpl();
        Object obj = wechatService.userMessageHandle(requestMessage);
        return obj;
    }



}
