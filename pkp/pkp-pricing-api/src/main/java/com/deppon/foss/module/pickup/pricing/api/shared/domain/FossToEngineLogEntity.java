package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class FossToEngineLogEntity implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 61632942665L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 费用类型
	 */
	private String priceEntityCode;
	/**
	 * 操作类型
	 */
	private String operationType;
	/**
	 * 调用开始时间
	 */
	private Date starTime;
	/**
	 * 调用结束时间
	 */
	private Date endTime;
	/**
	 * 调用规则引擎使用时间
	 */
	private long lasTime;
	/**
	 * 请求json
	 */
	private String request;
	/**
	 * 返回json
	 */
	private String response;
	/**
	 * 是否异常
	 */
	private String isError;
	 /**
	  * 异常信息
	  */
	private String errorMessage;
	/**
	 * 1表示第一次调用 2表示折上折
	 */
	private String isOverLay;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getStarTime() {
		return starTime;
	}

	public void setStarTime(Date starTime) {
		this.starTime = starTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getLasTime() {
		return lasTime;
	}

	public void setLasTime(long lasTime) {
		this.lasTime = lasTime;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPriceEntityCode() {
		return priceEntityCode;
	}

	public void setPriceEntityCode(String priceEntityCode) {
		this.priceEntityCode = priceEntityCode;
	}

	public String getIsOverLay() {
		return isOverLay;
	}

	public void setIsOverLay(String isOverLay) {
		this.isOverLay = isOverLay;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
