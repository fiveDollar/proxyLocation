package com.traffic.locate_proxy;

import com.traffic.location.LocationUtil;
import com.traffic.location.MyProxy;

public class GetLocationThread implements Runnable{
	private MyProxy mp;
	public GetLocationThread(MyProxy mp) {
		this.mp = mp;
	}

	@Override
	public void run() {
		LocationUtil.getLocation(mp);
	}
	
}
