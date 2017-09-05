 /**  
 *@Description:     
 */ 
package com.yh.novel.zongheng.thread;  

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yh.novel.util.regular.UpdateList;
import com.yh.novel.zongheng.dao.ZonghengDao;
  
@Component
public class UpdateListThread extends Thread{
	private boolean flag = false;
	private String url = "";
	private int frequency;
	
	@Autowired
	private ZonghengDao zonghengDao;
	
	/**
	* @param name 线程名
	* @param url 更新列表页url
	* @param frequency 采集频率
	 */
	public UpdateListThread(String name, String url, int frequency) {
		super(name);
		this.url = url;
		this.frequency = frequency;
	}
	

	@Override
	public void run() {
		flag = true;
		while (flag) {
			try {
				UpdateList updateList = new UpdateList(this.url);
				//获取更新列表页URL信息
				List<String> urls = updateList.getPageUrl(true);
				zonghengDao.saveInfoUrls(urls);
				TimeUnit.SECONDS.sleep(this.frequency);
			} catch (Exception e) {
				// TODO Auto-generated catch block  
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub  
		UpdateListThread thread = new UpdateListThread("lll", "http://book.zongheng.com/store/c0/c0/b9/u0/p1/v0/s9/t0/ALL.html", 30);
		thread.start();
	}

}
