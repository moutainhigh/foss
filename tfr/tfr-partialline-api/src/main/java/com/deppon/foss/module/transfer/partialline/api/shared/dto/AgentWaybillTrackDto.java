package com.deppon.foss.module.transfer.partialline.api.shared.dto;

/**
 * @author niuly
 * @function 代理运单轨迹
 * @date 2015年2月2日上午10:19:59
 */
public class AgentWaybillTrackDto{
	//行政区域编码
	private String areaCode;
	//行政区域名称
	private String areaName;
	//内容
	private String context;
	//格式化后时间
	private String ftime;
	//时间原始格式
	private String time;
	//轨迹状态 ：0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单
	private String status;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFtime() {
		return ftime;
	}
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}	
