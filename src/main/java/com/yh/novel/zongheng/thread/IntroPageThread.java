 /**  
 *@Description:     
 */ 
package com.yh.novel.zongheng.thread;  

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yh.novel.util.regular.ChapterPage;
import com.yh.novel.util.regular.IntroPage;
import com.yh.novel.zongheng.dao.ZonghengDao;
import com.yh.novel.zongheng.model.NovelChapterModel;
import com.yh.novel.zongheng.model.NovelInfoModel;

@Component
public class IntroPageThread extends Thread{
	private boolean flag = false;
	
	@Autowired
	private ZonghengDao zonghengDao;
	
	public IntroPageThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		flag = true;
		try {
			while (flag) {
				//获取可以采集的简介页URL
				String url = zonghengDao.getRandIntroPageUrl(1);
				if (url != null) {
					IntroPage intro = new IntroPage(url);
					//获取简介页信息
					NovelInfoModel bean = intro.analyzer();
					if (bean != null) {
						ChapterPage chapterPage = new ChapterPage(bean.getChapterListUrl());
						//获取章节列表页信息
						List<NovelChapterModel> chapters = chapterPage.analyzer();
						//写入小说章节个数
						bean.setChapterCount(chapters == null ? 0 : chapters.size());
						//保存简介页信息
						zonghengDao.updateNovelInfo(bean);
						//保存章节列表页信息
						zonghengDao.saveNovelChpter(chapters, bean.getId());
					}
					TimeUnit.MILLISECONDS.sleep(500);
				} else {
					TimeUnit.MILLISECONDS.sleep(1000);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub  
		IntroPageThread thread = new IntroPageThread("novelinfo");
		thread.start();
	}

}
