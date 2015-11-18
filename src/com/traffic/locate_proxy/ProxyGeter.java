package com.traffic.locate_proxy;

import java.util.ArrayList;
import java.util.List;

import com.traffic.databases.RemoteDatabaseforall;
import com.traffic.location.MyProxy;

public class ProxyGeter {
	private RemoteDatabaseforall rdb = new RemoteDatabaseforall("222.92.117.87", "page_monitor", "remote", "Iknowthat");
	public List<MyProxy> getProxyList(){
		List<MyProxy> myProxyList = new ArrayList<MyProxy>();
		String sql = "select ip,port from proxy where anonymous = 1 order by search_anonymous desc";
		try {
			ArrayList<Object[]> datas = rdb.selectall(sql);
			for (Object[] objects : datas) {
				MyProxy mp = new MyProxy();
				if(((String)objects[0]).contains("?"))
					mp.setIp(((String)objects[0]).replace("?", ""));
				else
					mp.setIp((String)objects[0]);
				mp.setPort((int) objects[1]);
//				datas.add(objects);
				myProxyList.add(mp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myProxyList;
	}
	public static void main(String[] args) {
	}
}
