package com.wonder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration
//扫描类
//@ComponentScan("com.wonder.Controller")
@SpringBootApplication
public class WeChatToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatToolsApplication.class, args);
    }


}
