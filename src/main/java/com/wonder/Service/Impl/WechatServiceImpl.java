package com.wonder.Service.Impl;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wonder.Service.WechatService;
import com.wonder.dto.Message;
import com.wonder.util.BaiDuOcrUtils.*;
import com.wonder.util.ocrWeChat;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;

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
            String getUserMessage = requestMessage.getContent();
            String XiaoAiTalk="https://api.wya6.cn/api/Xiao_Ai?apiKey=3d9b9758e7946eb2352f446552e38615&message="+getUserMessage;
            //得到json数据
            String res = cn.hutool.http.HttpUtil.get(XiaoAiTalk);
            //解析json字符串
            JSONObject jsonObject = JSONObject.parseObject(res);
            //使用的是 无边api接口 https://api.wya6.cn/
            //获取无边api接口中小爱机器人回复的 data的数据
            String dataString = jsonObject.getString("data");
            //System.out.println(dataString);
            Map maps = (Map) JSON.parse(dataString);
            String textString = (String) maps.get("text");
            System.out.println("用户:"+getUserMessage);
            System.out.println("小爱:"+textString);
            //这个是响应消息内容
            responseMessage.setContent(textString);
        }

        //获取 图片地址
        String getPicUrl=requestMessage.getPicUrl();
        if (getPicUrl!=null)
        {
            //请求图片需经过base64编码及urlencode后传入：图片的base64编码指将一副图片数据编码成一串字符串，
            // 使用该字符串代替图像地址。您可以首先得到图片的二进制，然后去掉编码头后再进行urlencode。
            /**
             * 重要提示代码中所需工具类
             * FileUtil,Base64Util,HttpUtil,GsonUtils请从
             * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
             * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
             * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
             * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
             * 下载
             */
            System.out.println(getPicUrl);
            //得到保存图片的路径
            //String imgPath=ocrWeChat.getImg(getPicUrl);
            //System.out.println(imgPath);
            //得到图片的 字符流
            byte[]  byteImg= ocrWeChat.getImgByte(getPicUrl).readAllBytes();
            //byte[] byteImg = FileUtil.readFileByBytes(imgPath);
            //System.out.println(byteImg);
            //System.out.println("byteImg: "+byteImg);
            //得到百度云的accessToken
            String accessToken=ocrWeChat.getBaiDuOcrToken();
            //System.out.println("BDYaccessToken: "+accessToken);
            //处理得到的图片
            String imgStr = Base64Util.encode(byteImg);
            //System.out.println(imgStr);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;

            //上传图片到百度进行ocr识别
            String baiduOcrApi="https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
            String result = HttpUtil.post(baiduOcrApi, accessToken, param);
            //System.out.println("result----"+result);

            cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(result);
            String wordsResult= jsonObject.getStr("words_result");
            int getWords_result_num =jsonObject.getInt("words_result_num");
            String message="" ,tempMessage;
            //初始时间
            long startTime = System.currentTimeMillis();
            //System.out.println("+++++++++++++++++++++++++"+wordsResult);
                if(getWords_result_num>1)
                {
                    //message = "1";
                    wordsResult = wordsResult.substring(1,wordsResult.length() - 1);
                    String[] results = wordsResult.split(",");
                    for (String s : results) {
                        //System.out.println(results[i]);
                        Map maps = (Map) JSON.parse(s);
                        tempMessage = (String) maps.get("words");
                        message = message + "\n" + tempMessage;
                    }
                }else if(getWords_result_num==1){
                    wordsResult = wordsResult.substring(1,wordsResult.length() - 1);
                    Map maps=(Map) JSON.parse(wordsResult);
                    message = (String) maps.get("words");
                }else if(getWords_result_num==0)
                {
                    message="得~识别不出来。。。。好尴尬啊";
                }


            //结束时间
            long endTime = System.currentTimeMillis();
            long resTime =endTime - startTime;

            System.out.println("程序运行时间：" + resTime + "ms");
            if(resTime>4600)
            {
                responseMessage.setContent("微信有回复5秒限制，百度ocr处理不过来啊！！！");
            }
            else
            {
                responseMessage.setContent(message);
            }
        }
        return responseMessage;
    }
}
