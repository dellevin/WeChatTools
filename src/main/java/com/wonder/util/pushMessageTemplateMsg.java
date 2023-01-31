package com.wonder.util;

import java.util.HashMap;
import java.util.Map;

public class pushMessageTemplateMsg {
    //拼接base参数
    public static Map<String, Object> pushTemplate(Object openID , String templateID , String url, String topcolor, Map<String, WeChatTemplateMsg> msgMap) {
        Map<String, Object> sendBody = new HashMap<>();
        sendBody.put("touser",openID);               // openId
        sendBody.put("template_id", templateID);      // 模板Id -9_RN6Xer0kRavc4t_3n8YpfQDYHhDLoALcMWRJ0d3U
        sendBody.put("url", url);         // 点击模板信息跳转地址
        sendBody.put("topcolor", topcolor);          // 顶色
        sendBody.put("data", msgMap);                // 模板参数
        return sendBody;
    }
}
