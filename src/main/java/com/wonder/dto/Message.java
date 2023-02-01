package com.wonder.dto;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
//指定xml的根节点
@XmlRootElement(name = "xml")
//指定Xml映射的生效范围
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    /**
     * 开发者微信号
     */
    //指定Xml映射节点名
    @XmlElement(name = "ToUserName")
    String toUserName;
    /**
     * 发送方账号（一个OpenID）
     */
    @XmlElement(name = "FromUserName")
    String fromUserName;
    /**
     * 消息类型，文本为text
     */
    @XmlElement(name = "MsgType")
    String msgType;
    /**
     * 消息id，64位整型
     */
    @XmlElement(name = "MsgId")
    String msgId;
    /**
     * 消息的数据ID（消息如果来自文章时才有）
     */
    @XmlElement(name = "MsgDataId")
    String msgDataId;
    /**
     * 多图文时第几篇文章，从1开始（消息如果来自文章时才有）
     */
    @XmlElement(name = "Idx")
    String idx;
    /**
     * 消息创建时间 （整型）
     */
    @XmlElement(name = "CreateTime")
    long createTime;
    /**
     * 文本消息内容
     */
    @XmlElement(name = "Content")
    String content;
    
    /**
     * 图片消息内容
     */
    @XmlElement(name = "PicUrl")
    String picUrl;
}