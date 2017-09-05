package com.yh.novel.zongheng.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yh.novel.zongheng.model.CrawlListInfo;
import com.yh.novel.zongheng.model.NovelChapterModel;
import com.yh.novel.zongheng.model.NovelInfoModel;
import com.yh.novel.zongheng.model.NovelReadModel;


public interface InfoMapper {
	
	@Select({"select * from crawllist where `state` = '1'"})
	public List<CrawlListInfo> getCrawlListInfos();
	
	@Select({"select url from novelinfo where state= #{state} order by rand() limit 1"})
	public String getRandIntroPageUrl(int state);
	
	@Select({"select * from novelchapter where state = #{state} order by rand() limit 1"})
	public NovelChapterModel getRandChapter(int state);

	@Insert("insert into novelchapterdetail (id,url,title,wordcount,chapterid,content,chaptertime,createtime,updatetime) values ( #{bean.id}, #{bean.url}, #{bean.title}, #{bean.wordCount}, #{bean.chapterId}, #{bean.content}, #{bean.chapterTime}, #{time}, #{time} )")
	public void insertNovelReadModel(NovelReadModel bean, long time);
	
	@Insert("insert into novelchapter (id,novelid,url,title,wordcount,chapterid,chaptertime,createtime,state) values ( #{bean.id}, #{novelId}, #{bean.url}, #{bean.title}, #{bean.wordCount}, #{bean.chapterId}, #{bean.chapterTime}, #{time}, '1' )")
	public void insertNovelChapter(NovelChapterModel bean, String novelId, long time);
	
	@Select({"select count(*) from novelchapterdetail where id= #{id} "})
	public int getNCDetailCount(String id);
	
	@Select({"select count(*) from novelchapter where id= #{id} "})
	public int getNChapterCount(String id);
	
	@Select({"select count(*) from novelinfo where id= #{id} "})
	public int getNovelInfoCount(String id);
	
	@Update("update novelinfo set (name = #{bean.name}, author = #{bean.author}, description = #{bean.desc}, type = #{bean.type}, lastchapter = #{bean.lastChapter}, chapterlisturl = #{bean.chapterListUrl}, wordcount = #{bean.wordCount}, keywords = #{bean.keyWords}, updatetime = #{time}, state = '0') WHERE id = #{bean.id}")
	public void updateNovelInfo(NovelInfoModel bean, long time);
	
	@Update("update novelchapter set `state`= #{state} where id = #{id} ")
	public void updateChapterState(String id, int state);
	
	@Update("update novelinfo set `state`= #{state} where id = #{id} ")
	public void updateInfoState(String id, int state);
	
	@Insert("insert into novelinfo (id,url,createtime,updatetime,state) values ( #{id}, #{url}, #{time}, #{time}, 1 )")
	public void insertInfoUrl(String id, String url, long time);
}
