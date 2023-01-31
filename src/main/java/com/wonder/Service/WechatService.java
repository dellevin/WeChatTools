package com.wonder.Service;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WechatService {
    public static Map<String, String> parseXmlData2Map(HttpServletRequest req) {
        HashMap<String, String> msgMap = new HashMap<>();

        try {
            ServletInputStream inputStream = req.getInputStream();

            // dom4j 用于读取 XML 文件输入流的类
            SAXReader saxReader = new SAXReader();
            // 读取 XML 文件输入流, XML 文档对象
            Document document = saxReader.read(inputStream);
            // XML 文件的根节点
            Element root = document.getRootElement();
            // 所有的子节点
            List<Element> childrenElement = root.elements();
            for (Element element : childrenElement) {
                msgMap.put(element.getName(), element.getStringValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgMap;
    }
}
