package com.wonder.testDemo;

import org.junit.Test;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}
