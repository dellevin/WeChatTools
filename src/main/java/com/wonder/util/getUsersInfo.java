package com.wonder.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import static java.lang.System.out;

public class getUsersInfo {

    public JSONObject usersInfo() throws IOException {
        //调用微信官方的接口获取关注用户详细信息
        //https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        Properties properties = new Properties();
        InputStream in = new FileInputStream("src/main/resources/config.properties");
        properties.load(in);
        String weChatAppId = properties.getProperty("weChatAppId");
        //System.err.println(weChatAppId);
        String weChatSecret = properties.getProperty("weChatSecret");

        String  receiveAccessToken= getToken.pushAccessToken(weChatAppId,weChatSecret);

        setUsers setUsers = new setUsers();
        JSONArray arrays = setUsers.getUserOpenId(weChatAppId,weChatSecret);//list数组的形式
        int i=0;
        for (Object array : arrays) {
            i++;
            out.println("第"+i+"个用户的openid："+array);
        }
        out.println("输入用户序号：");
        Scanner scanner = new Scanner(System.in);
        int ii = scanner.nextInt();
        if (ii<=0) out.println("别搞，写大于0的");


        String getUsersInfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+
                receiveAccessToken
                +"&openid="+
                arrays.get(ii-1)
                +"&lang=zh_CN";

        JSONObject jsonObject  = getToken.getResponse(getUsersInfo);

        //打印和返回关注用户详细信息
        out.println(jsonObject);
        return jsonObject;

    }


}
