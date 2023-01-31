package com.wonder.testDemo;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//用户解析传输过来的xml数据
public class parseRequest {
    public static Map<String, String> main(InputStream is) {
        Map<String,String> map=new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            //读取输入流，获取文档对象
            Document document=reader.read(is);
            //提交文档对象获取根节点
            Element root=document.getRootElement();
            //获取根节点的所有的子节点
            List<Element> element=root.elements();
            for(Element e:element){
                map.put(e.getName(), e.getStringValue());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;
    }

}
