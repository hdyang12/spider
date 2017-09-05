package com.yh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yh.novel.zongheng.service.CrawlStart;

@Controller
public class NovelController {
	
	@Autowired
	private CrawlStart crawlStart;
	
	//http://localhost:9600/spider/zhihu
    @RequestMapping("/novel")
    @ResponseBody
    String home() {
    	crawlStart.startCrawlList();
    	crawlStart.startCrawlIntro();
    	crawlStart.startCrawlRead();
        return "正在爬取!";
    }

}
