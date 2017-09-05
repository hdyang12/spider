 /**  
 *@Description:     
 */ 
package com.yh.novel.zongheng.service;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.novel.zongheng.dao.ZonghengDao;
import com.yh.novel.zongheng.model.CrawlListInfo;
import com.yh.novel.zongheng.thread.IntroPageThread;
import com.yh.novel.zongheng.thread.ReadPageThread;
import com.yh.novel.zongheng.thread.UpdateListThread;
  
@Service
public class CrawlStart {
	//更新列表页
	private static boolean booleanCrawlList = false;
	//简介页
	private static boolean booleanCrawlIntro = false;
	//阅读页
	private static boolean booleanCrawlRead = false;
	
	//简介页线程数目
	private static int crawlIntroThreadNum = 2;
	//阅读页线程数目
	private static int crawlReadThreadNum = 10;
	
	@Autowired
	private ZonghengDao zonghengDao;
	
	/**
	 * @Author:lulei  
	 * @Description:启动更新列表页采集线程
	 */
	public void startCrawlList() {
		if (booleanCrawlList) {
			return;
		}
		booleanCrawlList = true;
		List<CrawlListInfo> infos = zonghengDao.getCrawlListInfos();
		if (infos == null) {
			return;
		}
		for (CrawlListInfo info : infos) {
			if (info.getUrl() == null || "".equals(info.getUrl())) {
				continue;
			}
			UpdateListThread thread = new UpdateListThread(info.getInfo(), info.getUrl(), info.getFrequency());
			thread.start();
		}
	}
	
	/**
	 * @Author:lulei  
	 * @Description: 启动简介页采集线程
	 */
	public void startCrawlIntro () {
		if (booleanCrawlIntro) {
			return;
		}
		booleanCrawlIntro = true;
		for (int i = 0; i < crawlIntroThreadNum; i++) {
			IntroPageThread thread = new IntroPageThread("novel info page " + i);
			thread.start();
		}
	}
	
	/**
	 * @Author:lulei  
	 * @Description:启动阅读页采集线程
	 */
	public void startCrawlRead () {
		if (booleanCrawlRead) {
			return;
		}
		booleanCrawlRead = true;
		for (int i = 0; i < crawlReadThreadNum; i++) {
			ReadPageThread thread = new ReadPageThread("novel read page " + i);
			thread.start();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub  
		CrawlStart start = new CrawlStart();
		start.startCrawlList();
		start.startCrawlIntro();
		start.startCrawlRead();
	}

}
