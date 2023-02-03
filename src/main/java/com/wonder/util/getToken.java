package com.wonder.util;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;

public class getToken {
    public static String pushAccessToken(String  weChatAppId,String  weChatSecret) {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + weChatAppId
                +"&secret="
                + weChatSecret;
        JSONObject jsonObject = getResponse(url);
        //获取微信的access_token
        String accessToken = jsonObject.getString("access_token");
        //将accessToken发送给调用方法
        return accessToken;
    }
    static JSONObject getResponse(String url) {
        //调用工具类获通过url获取到html页面的数据，也就是json数据
        String res = HttpUtil.get(url);
        //返回数据，将JSON格式的字符串解析为原始JavaScript值或对象
        return JSONObject.parseObject(res);
    }

}
