package com.wonder.util;


import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static com.wonder.util.getToken.getResponse;

public class ocrWeChat {
    public static String getBaiDuOcrToken() throws IOException {
        Properties properties = new Properties();
        InputStream in = new FileInputStream("src/main/resources/config.properties");
        properties.load(in);
        String baiDuYunOcrAk = properties.getProperty("baiDuYunOcrAk");
        //System.err.println(weChatAppId);
        String baiDuYunOcrSk = properties.getProperty("baiDuYunOcrSk");

        String token = null;
        //https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=Va5yQRHlA4Fq5eR3LT0vuXV4&client_secret=0rDSjzQ20XUj5itV6WRtznPQSzr5pVw2&
        String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" +
                baiDuYunOcrAk
                +"&client_secret="
                +baiDuYunOcrSk;
        JSONObject jsonObject = getResponse(url);
        //获取百度云OCR的access_token
        String accessToken = jsonObject.getString("access_token");

        return accessToken;
    }

    public static String getImg(String imgUrl) throws Exception {
        //设置url
        URL url = new URL(imgUrl+".jpg");
        // 打开连接
        URLConnection openUrl = url.openConnection();
        //通过输入流获取图片数据
        InputStream is = openUrl.getInputStream();
        //创建一个文件对象用来保存图片，默认保存当前工程根目录，起名叫Copy.jpg
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        //需要生成几位
        int n = 1;
        //构造随机字符串
        String str = "";
        for (int i = 0; i < 6; i++) {
            str = str + (char)(Math.random()*26+'a');
        }
        String temp ;
        temp=format.format(date)+"-"+str;

        String savePath="src/main/resources/imgOcrDownload/"+temp+".jpg";
        //System.out.println("savePath:"+savePath);

        File imageFile = new File(savePath);
        //得到图片的数据
        byte[] data = readInputStream(is);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流，释放资源
        outStream.close();
        return savePath;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[6024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

}
