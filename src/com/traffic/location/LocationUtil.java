package com.traffic.location;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class LocationUtil {
	public final static int tryCount = 30; 
	
	public static void getLocation(MyProxy p){
		String location = "";
		int tempCount = tryCount;
		while(location==null||location.equals("")){
			if(tempCount--<0){
				return;
			}
			location = getwebcon("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+p.getIp(),null,0);
		}
		
		String[] ip_info = location.split(",");
		String location_Str = "";
		
		for (String string : ip_info) {
			if(string.contains("country")||string.contains("province")||string.contains("city")||string.contains("district")||string.contains("isp")){
				String lc = string.split(":")[1].replace("\"", "");
				if(lc==null||lc.length()==0){
					continue;
				}
				lc = ascii2native(lc);
				if(string.contains("country")){
					p.setCountry(lc);
				}else if(string.contains("province")){
					p.setProvince(lc);
				}else if(string.contains("city")){
					p.setCountry(lc);
				}else if(string.contains("district")){
					p.setDistrict(lc);
				}else if(string.contains("isp")){
					p.setIsp(lc);
				}
			}
		}
		
		if(location_Str.length()>0){
			location_Str = location_Str.substring(0, location_Str.length()-1);
		}
	}
	

	public static String getwebcon(String url,String ip,int port) {
		HttpURLConnection	connection=null;
		ByteArrayOutputStream	bos=null;
		InputStream in = null;
		try {
			if(ip!=null&&ip.length()>0) {
				connection = (HttpURLConnection)new URL(url).openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));
			} else {
				connection = (HttpURLConnection)new URL(url).openConnection();
			}
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 4 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
			connection.setReadTimeout(3000);
			connection.setConnectTimeout(3000);
			in = connection.getInputStream();
			byte[] temp = new byte[1024 * 1024];
			bos = new ByteArrayOutputStream();
			int size = in.read(temp);
			while (size > 0) {
				bos.write(temp, 0, size);
				try {
					size = in.read(temp);
				} catch (Exception e) {
					size = 0;
				}
			}
			String webcon = bos.toString("utf-8");
			return webcon;
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(in!=null){
					in.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			connection.disconnect();
		}
		return null;
	}
	
	
	public static String ascii2native(String ascii) {
		try {
		    int n = ascii.length() / 6;  
		    StringBuilder sb = new StringBuilder(n);  
		    for (int i = 0, j = 2; i < n; i++, j += 6) {  
		        String code = ascii.substring(j, j + 4);  
		        char ch = (char) Integer.parseInt(code, 16);  
		        sb.append(ch);  
		    }

		    return sb.toString();  
		} catch (Exception e) {
			return ascii;
		}
	}
	
	public static void main(String[] args) {
		MyProxy mp = new MyProxy();
		mp.setIp("58.210.233.234");
		mp.setPort(123456);
		LocationUtil.getLocation(mp);
		System.out.println(mp);
	}
	
}
