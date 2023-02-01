package com.wonder.Service.Impl;

import com.wonder.Service.WechatService;
import com.wonder.dto.Message;
import com.wonder.util.ocrWeChat;
import org.apache.logging.log4j.util.Base64Util;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {


    @Override
    public Object userMessageHandle(Message requestMessage) throws Exception {
        System.out.println("post方法入参："+requestMessage);
        //获取 发送方账号（一个OpenID）
        String fromUserName = requestMessage.getFromUserName();
        //获取 开发者微信号
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

        String getMessage =  requestMessage.getContent();

        if(getMessage!=null)
        {
            //<xml>
            //  <ToUserName><![CDATA[toUser]]></ToUserName>
            //  <FromUserName><![CDATA[fromUser]]></FromUserName>
            //  <CreateTime>12345678</CreateTime>
            //  <MsgType><![CDATA[text]]></MsgType>
            //  <Content><![CDATA[你好]]></Content>
            //</xml>

            //这个是响应消息内容，直接复制收到的内容做演示，甚至整个响应对象都可以直接使用原请求参数对象，只需要换下from和to就可以了哈哈哈
            responseMessage.setContent(requestMessage.getContent());
        }
        //获取 图片地址
        String getPicUrl=requestMessage.getPicUrl();

        if (getPicUrl!=null)
        {
            System.out.println(getPicUrl);
            //得到保存图片的路径
            String imgPath=ocrWeChat.getImg(getPicUrl);
            //得到百度云的accessToken
            String accessToken=ocrWeChat.getBaiDuOcrToken();

            System.out.println("BDYaccessToken:"+accessToken);



            String baiduOcrApi="https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
            //上传图片到百度进行ocd识别

            responseMessage.setContent("2222");
        }


        return responseMessage;
    }
}
