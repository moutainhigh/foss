package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//协议航班查询Dto
public class AirProtocolFlightQueryDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7070079617338994031L;

	private String flightNo;//航班号
    
    private String departCity;//始发站
    
    private String arriveCity;//目的站
	
	private String currQueryTime;//截止时间	
	
	//private String orgCode;//总调code

	private String isProtocol;//是否为协议航班
	
	private int days;//本月天数
	
	private List<String> orgCodeList=new ArrayList<String>();
	
	//set and get
	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getCurrQueryTime() {
		return currQueryTime;
	}

	public void setCurrQueryTime(String currQueryTime) {
		this.currQueryTime = currQueryTime;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public String getIsProtocol() {
		return isProtocol;
	}

	public void setIsProtocol(String isProtocol) {
		this.isProtocol = isProtocol;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	
	
	
	
}
