package com.wonder.Service;



import com.wonder.dto.Message;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;


public interface WechatService {
     Object userMessageHandle(Message requestMessage) throws Exception;
}
