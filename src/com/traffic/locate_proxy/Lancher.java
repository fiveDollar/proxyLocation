package com.traffic.locate_proxy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.traffic.location.LocationUtil;
import com.traffic.location.MyProxy;

public class Lancher {
	private final static String PROXY_PATH = "data/proxy.html";

	public static void main(String[] args) {
		ProxyGeter gp = new ProxyGeter();
		List<MyProxy> proxyList = gp.getProxyList();
		final CountDownLatch doneSignal = new CountDownLatch(proxyList.size());
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (final MyProxy myProxy : proxyList) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					LocationUtil.getLocation(myProxy);
					doneSignal.countDown();
				}
			});
		}

		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		writeProxy(proxyList);

	}

	public static void writeProxy(List<MyProxy> proxyList) {
		File f = new File(PROXY_PATH);
		OutputStream out = null;
		OutputStreamWriter ow = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(f,false);
			ow = new OutputStreamWriter(out);
			bw = new BufferedWriter(ow);
			for (MyProxy myProxy : proxyList) {
				bw.write(myProxy.getIp() + ":" + myProxy.getPort() + ","
						+ myProxy+"<br/>");
				bw.newLine();
			}
			bw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (ow != null)
					ow.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
