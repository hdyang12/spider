package com.yh.zhihu.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.yh.util.CrawlerUtils;
import com.yh.util.HttpsUtils;

/**
 * 爬虫测试
 * @author yh
 *
 */
public class CrawlerTest {
	
	@Test
	public void baiduResultTest1(){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(null, true);
		if (map.get(null)){
			System.out.println("ok");
		}
	}
	
	@Test
	public void baiduResultTest(){
		String url = "http://192.168.216.82:8081/hexin?method=quote&datetime=16384(20170814-20170814)&fuquan=Q&append=Y&sortby=10&sorttype=select&codelist=169();170();185();186();&sortappend=Y&formula=period:16384;ID:7615;NAME:_3140311;source:select count(( vol*5>fivedayvol*10 and c>ref(hhv(h,5),1)),1)>=1";
//		System.out.println(CrawlerUtils.getUrlContent(url, false));//false不走代理
		try {
			System.out.println(HttpsUtils.post(url, null, null, null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void literatureTest(){
		String url = "http://apps.webofknowledge.com/Search.do?product=WOS&SID=2BjWTNP68uIp9OmNVg5&search_mode=GeneralSearch&prID=657a67dd-d723-4bdc-9e98-b1e2c93985ff";
		System.out.println(CrawlerUtils.getUrlContent(url, false));
	}
	
	@Test
	public void basicHttpsGetIgnoreCertificateValidation() throws Exception {
	     
	    String url = "https://www.zhihu.com/people/yanwenya";
	     
	    // Create a trust manager that does not validate certificate chains
	    TrustManager[] trustAllCerts = new TrustManager[] {
	        new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                // don't check
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                // don't check
	            }
	        }
	    };
	     
	    SSLContext ctx = SSLContext.getInstance("TLS");
	    ctx.init(null, trustAllCerts, null);
	     
	    LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
	     
	    CloseableHttpClient httpclient = HttpClients.custom()
	            .setSSLSocketFactory(sslSocketFactory)
	            .build();
	     
	    HttpGet request = new HttpGet(url);
	    request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
	     
	    CloseableHttpResponse response = httpclient.execute(request);
	    String content = EntityUtils.toString(response.getEntity());
	    System.out.println(content);
	}
	
	@Test
	public void basicHttpsGet() throws Exception {
	     
	    String url = "https://www.zhihu.com/people/yanwenya/answers";
	    URL obj = new URL(url);
	 
	    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	    con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
	    con.setRequestProperty("Accept-Language","en-US,en;q=0.5");
	    con.setRequestMethod("GET");
	 
	    String responseBody = readResponseBody(con.getInputStream());
	    System.out.println(responseBody);
	}
	
	// 读取输入流中的数据
	private String readResponseBody(InputStream inputStream) throws IOException {
	 
	    BufferedReader in = new BufferedReader(
	            new InputStreamReader(inputStream));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	 
	    while((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();
	     
	    return response.toString();
	}
	
}
