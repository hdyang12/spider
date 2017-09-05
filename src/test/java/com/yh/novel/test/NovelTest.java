package com.yh.novel.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.yh.controller.SampleController;
import com.yh.novel.zongheng.thread.IntroPageThread;
import com.yh.novel.zongheng.thread.ReadPageThread;
import com.yh.novel.zongheng.thread.UpdateListThread;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleController.class)
public class NovelTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Autowired
	private UpdateListThread updateListThread;
	
	@Autowired
	private IntroPageThread introPageThread;
	
	@Autowired
	private ReadPageThread readPageThread;
	
	@Before
	public void setupMockMvc() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void UpdateListThreadTest(){
		updateListThread.setParam("lll", "http://book.zongheng.com/store/c0/c0/b9/u0/p1/v0/s9/t0/ALL.html", 30);
		updateListThread.start();
	}
	
	@Test
	public void IntroPageThreadTest(){
		introPageThread.setName("novelinfo");
		introPageThread.start();
	}
	
	@Test
	public void ReadPageThreadTest(){
		readPageThread.setName("readPage");
		readPageThread.start();
	}

}
