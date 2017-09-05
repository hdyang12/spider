package com.yh.novel.zongheng.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.yh.novel.zongheng.model.CrawlListInfo;


public interface InfoMapper {
	
	@Select({"select * from crawllist where `state` = '1'"})
	public List<CrawlListInfo> getCrawlListInfos();


}
