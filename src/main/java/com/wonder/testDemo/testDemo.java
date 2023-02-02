package com.wonder.testDemo;

import com.alibaba.fastjson.JSON;
import org.junit.Test;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class testDemo {
    @Test
    public  void getTime() {
        Date date=new Date();
        //System.out.println("当前的日期是------>"+date);
        //SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
        System.out.println("格式化后的时间------->"+format.format(date));
    }
    @Test
    public void getcity(){

    }
    //测试获取配置类
    @Test
    public  void  testProperties() throws IOException {
        Properties properties = new Properties();
        InputStream in = new FileInputStream("src/main/resources/config.properties");
        properties.load(in);
        String weChatAppId = properties.getProperty("weChatAppId");
        System.err.println(weChatAppId);
        String weChatSecret = properties.getProperty("weChatSecret");
        System.err.println(weChatSecret);
    }
    @Test
    public  void result() {
        //
        // {"words_result":
        // [{"words":"base.apk.1"}, {"words":"28.2M"}, {"words":"微信电脑版"},{"words":"昨天156"}, {"words":"迄今所有人生都大写着失败但不妨碍我继续向前"}],"words_result_num":5,"log_id":1621058160735632335}
        String tt="",ttemp="";
        String temp = "{\"words\":\"base.apk.1\"},{\"words\":\"28.2M\"},{\"words\":\"微信电脑版\"},{\"words\":\"昨天156\"},{\"words\":\"迄今所有人生都大写着失败但不妨碍我继续向前\"}";
        String[] results = temp.split(",");
        for(int i = 0; i < results.length; i++) {
            //System.out.println(results[i]);
            Map maps=(Map) JSON.parse(results[i]);
            tt = (String) maps.get("words");
            ttemp = tt+"\n"+ttemp;
        }
        System.out.println(ttemp);

        //[{"words":"迄今所有人生都大写着失败，但不妨碍我继续向前"}]
    }
}
