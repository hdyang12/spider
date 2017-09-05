package com.yh.zhihu.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.yh.util.CrawlerUtils;
import com.yh.zhihu.model.ZhiHuUserBean;

public class ParseUrlTest {
	
	@Test
	public void zhiHuTest() {
		String url = "https://www.zhihu.com/people/yanwenya/answers";
		 Pattern pattern;
         Matcher matcher;
         //Jsoup解析网页
         Element userUrlContent = null;

         //解析网页
         String context = CrawlerUtils.getUrlContent(url, false);
         userUrlContent = Jsoup.parse(context);
         String userContent = userUrlContent.text();
         ZhiHuUserBean user = new ZhiHuUserBean();
       //姓名
         String name = userUrlContent.select(".ProfileHeader-name").first().text();
         user.setName(name);
         //行业 公司 职位
         int size = userUrlContent.select(".ProfileHeader-infoItem").size();
         if (size == 2) {
             String string1 = userUrlContent.select(".ProfileHeader-infoItem").first().text();
             if (string1 != null && string1 != "") {
                 String[] a = string1.split(" ");
                 //行业
                 for (int i = 0; i < a.length; i++) {
                     if (a.length > 0) {
                         user.setBusiness(a[0]);
                     }
                     //公司
                     if (a.length > 1) {
                         user.setEmployment(a[1]);
                     }
                     //职位
                     if (a.length > 2) {
                         user.setPosition(a[2]);
                     }
                 }
             }
             String string2 = userUrlContent.select(".ProfileHeader-infoItem").get(1).text();
             if (string2 != null && string2 != "") {
                 String[] a = string2.split(" ");
                 //学校
                 if (a.length > 0) {
                     user.setEducation(a[0]);
                 }
                 //专业
                 if (a.length > 1) {
                     user.setMajor(a[1]);
                 }
             }
         }

         //看‘关注他’中有无关键字，判断性别
         String sexString = userUrlContent.select(".ProfileHeader-contentFooter button[icon]").first().text();
         if (sexString.contains("他")) {
             user.setSex(0);
         } else if (sexString.contains("她")) {
             user.setSex(1);
         } else {
             user.setSex(2);
         }

         // 回答数量
//         String answersNum = userUrlContent.select(".ProfileMain-header").first().select("li").get(1).text().substring(2);
         user.setAnswersNum(Integer.parseInt(userContent.substring(userContent.indexOf("回答")+2,userContent.indexOf("分享")-1)));

         // 被赞同数
//         Elements e1 = userUrlContent.select(".IconGraf");
//         String starsNumString = e1.get(e1.size() - 2).text();
//         String starsNum = starsNumString.substring(starsNumString.indexOf("得") + 2, starsNumString.indexOf("次") - 1);

 user.setStarsNum(Long.parseLong(userContent.substring(userContent.indexOf("获得")+3,userContent.indexOf("次赞同")-1)));
 user.setThxNum(Long.parseLong(userContent.substring(userContent.lastIndexOf("获得")+3,userContent.indexOf("次感谢")-1)));

         // 被感谢数
//         Elements e2 = userUrlContent.select(".Profile-sideColumnItemValue");
//         String thxNumString = e2.get(e2.size() - 1).text();
//         String thxNum = thxNumString.substring(thxNumString.indexOf("得") + 2, thxNumString.indexOf("次") - 1);


         //关注的人
         String followingNum = userUrlContent.select(".NumberBoard-value").first().text();
         user.setFollowingNum(followingNum);

         //关注者数量
         String followersNum = userUrlContent.select(".NumberBoard-value").get(1).text();
         user.setFollowersNum(followersNum);

         //打印用户信息
         System.out.println("爬取成功：" + user);
		
	}

}
