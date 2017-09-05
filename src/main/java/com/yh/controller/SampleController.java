package com.yh.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 静态首页 http://localhost:8080/springboot/
 * classpath:/resources/index.html
 * 静态首页会和/index冲突
 * @author yh
 *
 */
@Controller
//@SpringBootApplication	//组合注解;包含@EnableAutoConfiguration;@Configurable;@ComponentScan
@ComponentScan("com.yh")
@MapperScan("com.yh")
@EnableAutoConfiguration//让SpringBoot根据类路径中的jar包依赖为当前项目进行自动配置
@EnableMongoRepositories(value= {"com.yh.mongo"})
public class SampleController {
	
	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleController.class, args);
	}
	
	//http://localhost:9600/spider/home
    @RequestMapping("/home")
    @ResponseBody
    String home() {
//    	System.out.println(hqinfoRepository.findByStkcode("000591.SZ"));
        return "Hello World!";
    }
    
}
