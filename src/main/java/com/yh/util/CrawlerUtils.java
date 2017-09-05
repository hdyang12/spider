package com.yh.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class CrawlerUtils {
	private static final String IP = "172.20.201.1";
	private static final int PORT = 3128;
	private static Proxy proxy;
	
	/**
	 * 设置代理
	 * @throws Exception
	 */
	public static void getProxy() throws Exception {
		// 设置代理 地址和密码
		InetSocketAddress socketAddress = new InetSocketAddress(
				InetAddress.getByName(IP), PORT);
		proxy = new Proxy(Proxy.Type.HTTP, socketAddress);

		// 设置代理的密码验证
		Authenticator auth = new Authenticator() {
			private PasswordAuthentication pa = new PasswordAuthentication(
					"username", "password".toCharArray());

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return pa;
			}
		};
		Authenticator.setDefault(auth);
	}
	
	/**
	 * 
	 * @param requestUrl	请求地址
	 * @param neeedProxy	是否走代理;true为走代理
	 * @return
	 */
	public static String getUrlContent(String requestUrl, boolean neeedProxy) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(requestUrl);
			URLConnection conn = null;
			if(neeedProxy){
				conn = url.openConnection(proxy);// 这个要走代理
			}else{
				conn = url.openConnection();//这个不用走代理
			}
			conn.setDefaultUseCaches(false);
			conn.setReadTimeout(30000);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
