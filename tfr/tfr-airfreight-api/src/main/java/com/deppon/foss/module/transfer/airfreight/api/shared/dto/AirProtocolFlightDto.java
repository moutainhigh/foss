package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;

public class AirProtocolFlightDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3445851947504732779L;

    private String flightNo;//航班号
    
    private String departCity;//始发站
    
    private String arriveCity;//目的站
    
    private String currMonthGoodsAmount;//当月货量(公斤)
    
    private String currTimeOptGoodsAmount;//当日配载货量(公斤)
    
    private String currMonthTotalOptGoodsAmount;//本月累计已配载货量(公斤)
    
    private String currMonthTimeschedule;//本月时间进度
    
    private String currMonthGoodschedule;//本月货量进度
    
    private String reDateOptGoodsAmount;//剩余日需货量(公斤)

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

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public String getCurrMonthGoodsAmount() {
		return currMonthGoodsAmount;
	}

	public void setCurrMonthGoodsAmount(String currMonthGoodsAmount) {
		this.currMonthGoodsAmount = currMonthGoodsAmount;
	}

	public String getCurrTimeOptGoodsAmount() {
		return currTimeOptGoodsAmount;
	}

	public void setCurrTimeOptGoodsAmount(String currTimeOptGoodsAmount) {
		this.currTimeOptGoodsAmount = currTimeOptGoodsAmount;
	}

	public String getCurrMonthTotalOptGoodsAmount() {
		return currMonthTotalOptGoodsAmount;
	}

	public void setCurrMonthTotalOptGoodsAmount(String currMonthTotalOptGoodsAmount) {
		this.currMonthTotalOptGoodsAmount = currMonthTotalOptGoodsAmount;
	}

	public String getCurrMonthTimeschedule() {
		return currMonthTimeschedule;
	}

	public void setCurrMonthTimeschedule(String currMonthTimeschedule) {
		this.currMonthTimeschedule = currMonthTimeschedule;
	}

	public String getCurrMonthGoodschedule() {
		return currMonthGoodschedule;
	}

	public void setCurrMonthGoodschedule(String currMonthGoodschedule) {
		this.currMonthGoodschedule = currMonthGoodschedule;
	}

	public String getReDateOptGoodsAmount() {
		return reDateOptGoodsAmount;
	}

	public void setReDateOptGoodsAmount(String reDateOptGoodsAmount) {
		this.reDateOptGoodsAmount = reDateOptGoodsAmount;
	}

	
	
	
}
