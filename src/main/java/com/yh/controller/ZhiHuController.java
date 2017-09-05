package com.yh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ZhiHuController {
	
	//http://localhost:9600/spider/zhihu
    @RequestMapping("/zhihu")
    @ResponseBody
    String home() {
//    	System.out.println(hqinfoRepository.findByStkcode("000591.SZ"));
        return "ZhiHu Welcome!";
    }

}
