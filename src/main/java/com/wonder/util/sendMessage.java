package com.wonder.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.*;

public class sendMessage {

    public static void main(String[] args) throws IOException {
        //调用微信官方发送消息
        //https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN

        Properties properties = new Properties();
        InputStream in = new FileInputStream("src/main/resources/config.properties");
        properties.load(in);
        String weChatAppId = properties.getProperty("weChatAppId");
        //System.err.println(weChatAppId);
        String weChatSecret = properties.getProperty("weChatSecret");
        //System.err.println(weChatSecret);

        if(weChatAppId==""||weChatSecret=="")
        {
            System.out.println("请输入APPID 或者 密钥");
        }

        //获取access_token
        String  receiveAccessToken= getToken.pushAccessToken(weChatAppId,weChatSecret);
        //生成网址字符串
        String userPostUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+receiveAccessToken;
        //out.println(userPostUrl);

        setUsers setUsers = new setUsers();
        JSONArray arrays = setUsers.getUserOpenId(weChatAppId,weChatSecret);//list数组的形式

        /*
        	{{date.DATA}}
        	城市：{{city.DATA}}
        	天气：{{weather.DATA}}
            最低气温: {{min_temperature.DATA}}
            最高气温: {{max_temperature.DATA}}
         */

        // 模板参数
        Map<String, WeChatTemplateMsg> sendMag = new HashMap<String, WeChatTemplateMsg>();
        //这里和设置的模板参数相对应
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
        String dateShow =format.format(date);
        sendMag.put("date", new WeChatTemplateMsg(dateShow));


        sendMag.put("city", new WeChatTemplateMsg("222"));
        sendMag.put("weather", new WeChatTemplateMsg("333"));
        sendMag.put("min_temperature", new WeChatTemplateMsg("444"));
        sendMag.put("max_temperature", new WeChatTemplateMsg("555"));



        int i=0;
        for (Object array : arrays) {
            i++;
            out.println("第"+i+"个用户的openid："+array);
        }
        out.println("输入用户序号：");
        Scanner scanner = new Scanner(System.in);
        int ii = scanner.nextInt();
        if (ii<=0) out.println("别搞，写大于0的");

        //调用方法生成map类型的数据
        Map<String, Object> sendBody =pushMessageTemplateMsg.pushTemplate(
                                                                            arrays.get(ii-1),
                                                                            "-9_RN6Xer0kRavc4t_3n8YpfQDYHhDLoALcMWRJ0d3U",//这里可以自定义选择发送模板
                                                                            "https://www.baidu.com/",//定义发送链接
                                                                            "#FF0000",//顶部颜色
                                                                            sendMag
                                                                            );

        //将map类型数据通过JSONObject转换成json数据
        String jsonMap = String.valueOf(new JSONObject(sendBody));

        //通过 HttpUtil.post 发送消息 并且保留返还的json数据，这里的json是string类型的，map的人家不识别
        String returnMsg = HttpUtil.post(userPostUrl, jsonMap);
        out.println(jsonMap);

        //判断消息是否发送成功
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(returnMsg);
        String errorMsg= jsonObject.getStr("errmsg");
        if(!StrUtil.equals("ok",errorMsg)) System.out.println(errorMsg);

    }
}
