package com.traffic.location;

public class MyProxy {
	private String ip;
	private int port;
	private String country;
	private String province;
	private String district;
	private String isp;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	@Override
	public String toString() {
		return "MyProxy [ip=" + ip + ", port=" + port + ", country=" + country
				+ ", province=" + province + ", district=" + district
				+ ", isp=" + isp + "]";
	}
	
	
}
