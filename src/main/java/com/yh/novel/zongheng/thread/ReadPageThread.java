 /**  
 *@Description:     
 */ 
package com.yh.novel.zongheng.thread;  

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yh.novel.util.regular.ReadPage;
import com.yh.novel.zongheng.dao.ZonghengDao;
import com.yh.novel.zongheng.model.NovelChapterModel;
import com.yh.novel.zongheng.model.NovelReadModel;
  
@Component
public class ReadPageThread extends Thread{
	private boolean flag = false;
	
	@Autowired
	private ZonghengDao zonghengDao;
	
	public ReadPageThread (String name) {
		super(name);
	}
	

	@Override
	public void run() {
		flag = true;
		while (flag) {
			try {
				//获取可以采集的章节信息
				NovelChapterModel chapter = zonghengDao.getRandChapter(1);
				if (chapter != null) {
					ReadPage readPage = new ReadPage(chapter.getUrl());
					//获取小说阅读页信息
					NovelReadModel novel = readPage.analyzer();
					if (novel == null) {
						continue;
					}
					//写入小说章节序号
					novel.setChapterId(chapter.getChapterId());
					//写入章节发布时间
					novel.setChapterTime(chapter.getChapterTime());
					//保存小说阅读页信息
					zonghengDao.saveNovelRead(novel);
					//将小说的章节设置成已采集
					zonghengDao.updateChapterState(chapter.getId(), 0);
					TimeUnit.MILLISECONDS.sleep(500);
				} else {
					TimeUnit.MILLISECONDS.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**  
	 * @param args
	 * @Author:lulei  
	 * @Description:  
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub  
		ReadPageThread thread = new ReadPageThread("readPage");
		thread.start();
	}

}
