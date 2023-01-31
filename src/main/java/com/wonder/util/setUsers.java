package com.wonder.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wonder.util.getToken;

public class setUsers {

    //OpenID：微信用户在当前公众号的唯一标识
    public JSONArray getUserOpenId(String  weChatAppId,String  weChatSecret) {
        //获取access_token
        String  receiveAccessToken= getToken.pushAccessToken(weChatAppId,weChatSecret);
        //生成网址字符串
        String userGetUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ receiveAccessToken;
        //System.out.println(userGetUrl);
        //访问网址之后返还的数据
        /*
            {
            "total": 2,
            "count": 2,
            "data": {
                "openid":
                        [
                            "o-ena6Zm3UH_cX6c89H_7qgUONMw",
                            "o-ena6Zh781HWLxf65RcMGvcRL8w"
                        ]
                    },
            "next_openid": "o-ena6Zh781HWLxf65RcMGvcRL8w"
            }

         */

        JSONObject jsonObject = getToken.getResponse(userGetUrl);
        //关注该公众账号的总用户数
        //Integer total =  jsonObject.getInteger("total");

        //获取关注公众号用户的id 微信号
        JSONObject openIdDdta = (JSONObject)jsonObject.get("data");
        JSONArray openIdArray= (JSONArray) openIdDdta.get("openid");

        //以JSONArray的形式返还数据
        return openIdArray;
    }
}
