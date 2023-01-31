package com.wonder.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WechatUtils {
    public static boolean checkSignature(String signature , String timestamp, String nonce,String token) {
// 开发者通过检验 signature 对请求进行校验（下面有校验方式）。

//1）将token、timestamp、nonce三个参数进行字典序排序
//
//2）将三个参数字符串拼接成一个字符串进行sha1加密
//
//3）开发者获得加密后的字符串可与 signature 对比，标识该请求来源于微信

        String[] array =new String[3];
        array[0] = token;
        array[1] = timestamp;
        array[2] = nonce;
        //将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(array);

        //字典序排序完毕后拼接
        StringBuffer arraySortAppend = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            arraySortAppend.append(array[i]);
        }
        //将三个参数字符串拼接成一个字符串进行sha1加密  arraySortAppend
        String sha1String = getSha1(String.valueOf(arraySortAppend));
        if (sha1String.equalsIgnoreCase(signature)) {
            return true;
        }else {
            return false;
        }
        /*
        ArrayList<String> list = new ArrayList();
        list.add(nonce);
        list.add(timestamp);
        list.add(token);
        //将token、timestamp、nonce三个参数进行字典序排序
        Collections.sort(list);

        //拼接
        StringBuilder sb= new StringBuilder();
        list.forEach(sb::append);
        String result = sb.toString();
        //加密
        String sha1 = DigestUtils.sha1Hex(result);
        System.out.println(String.format("sha1:%s,signature:%s",sha1,signature));
        boolean booleanResult = signature.equals(sha1);
        return booleanResult;
        */
    }
    //sha1 加密算法
    //参考: https://www.jianshu.com/p/58826471deed
    public static String getSha1(String str) {

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

}
