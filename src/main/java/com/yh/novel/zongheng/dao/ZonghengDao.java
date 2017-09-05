package com.yh.novel.zongheng.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yh.novel.util.ParseMD5;
import com.yh.novel.zongheng.dao.mybatis.InfoMapper;
import com.yh.novel.zongheng.model.CrawlListInfo;
import com.yh.novel.zongheng.model.NovelChapterModel;
import com.yh.novel.zongheng.model.NovelInfoModel;
import com.yh.novel.zongheng.model.NovelReadModel;

@Repository
public class ZonghengDao {
	
	@Autowired
	private InfoMapper infoMapper;
	
	/**
	 * @Description: 获取更新列表页地址信息
	 */
	public List<CrawlListInfo> getCrawlListInfos(){
		return infoMapper.getCrawlListInfos();
	}
	
	/**
	 * @param state
	 * @return
	 * @Author:lulei  
	 * @Description: 随机获取简介页url
	 */
	public String getRandIntroPageUrl(int state) {
		return infoMapper.getRandIntroPageUrl(state);
	}
	
	/**
	 * @param state
	 * @return
	 * @Author:lulei  
	 * @Description:随机获取章节信息
	 */
	public NovelChapterModel getRandChapter(int state) {
		return infoMapper.getRandChapter(state);
	}
	
	/**
	 * @param urls
	 * @Author:lulei  
	 * @Description: 将采集到的简介页url信息保存到数据库中
	 */
	public void saveInfoUrls(List<String> urls) {
		if(urls == null || urls.size() < 1) {
			return;
		}
		for (String url : urls) {
			String id = ParseMD5.parseStrToMD5(url);
			if (haveNovelInfo(id)) {
				updateInfoState(id, 1);
			} else {
				insertInfoUrl(id, url);
			}
		}
	}
	
	/**
	 * @param beans
	 * @param novelId
	 * @Author:lulei  
	 * @Description: 将采集到的章节信息保存到数据库中
	 */
	public void saveNovelChpter(List<NovelChapterModel> beans, String novelId){
		if (beans == null) {
			return;
		}
		for (NovelChapterModel bean : beans) {
			if (!haveNovelChapter(bean.getId())) {
				inserNovelChapter(bean, novelId);
			}
		}
	}
	
	/**
	 * @param bean
	 * @Author:lulei  
	 * @Description: 将采集到的阅读页信息保存到数据库中
	 */
	public void saveNovelRead(NovelReadModel bean){
		if (bean == null) {
			return;
		}
		if (haveNovelRead(bean.getId())) {
			return;
		}
		infoMapper.insertNovelReadModel(bean, System.currentTimeMillis());
	}
	
	/**
	 * @param id
	 * @return
	 * @Author:lulei  
	 * @Description: 判断阅读页信息是否存在
	 */
	private boolean haveNovelRead(String id) {
		return infoMapper.getNCDetailCount(id) > 0;
	}
	
	/**
	 * @param id
	 * @return
	 * @Author:lulei  
	 * @Description: 判断章节信息时候存在
	 */
	private boolean haveNovelChapter(String id) {
		return infoMapper.getNChapterCount(id) > 0;
	}
	
	/**
	 * @param bean
	 * @param novelId
	 * @Author:lulei  
	 * @Description: 将章节信息保存到数据库中
	 */
	private void inserNovelChapter(NovelChapterModel bean, String novelId) {
		if(bean == null) {
			return;
		}
		infoMapper.insertNovelChapter(bean, novelId, System.currentTimeMillis());
	}
	
	/**
	 * @param bean
	 * @Author:lulei  
	 * @Description: 更新小说的简介信息
	 */
	public void updateNovelInfo(NovelInfoModel bean) {
		if (bean == null) {
			return;
		}
		infoMapper.updateNovelInfo(bean, System.currentTimeMillis());
	}
	
	/**
	 * @param id
	 * @param state
	 * @Author:lulei  
	 * @Description:更新章节信息的state值
	 */
	public void updateChapterState(String id, int state) {
		infoMapper.updateChapterState(id, state);
	}
	
	/**
	 * @param id
	 * @param state
	 * @Author:lulei  
	 * @Description: 更新简介信息的state值
	 */
	public void updateInfoState(String id, int state) {
		infoMapper.updateInfoState(id, state);
	}
	
	/**
	 * @param id
	 * @return
	 * @Author:lulei  
	 * @Description: 判断小说简介信息是否存在
	 */
	private boolean haveNovelInfo(String id) {
		return infoMapper.getNovelInfoCount(id) > 0;
	}
	
	/**
	 * @param id
	 * @param url
	 * @Author:lulei  
	 * @Description:将采集到的简介页url保存到数据库中
	 */
	private void insertInfoUrl(String id, String url) {
		infoMapper.insertInfoUrl(id, url, System.currentTimeMillis());
	}

}
