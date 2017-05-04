/** 
 * @Title 			NotifyMultibillDto.java
 * @Package 		com.deppon.foss.module.pickup.predeliver.api.shared.dto 
 * @Copyright 		Copyright (c)2013 
 * @Company 		Deppon 
 * @author 			mujun 
 * @date 			2014-4-10 上午9:38:58 
 * @version 		1.0 
 */

package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 ************************************************************ 
 * @ClassName		NotifyMultibillDto
 * @Description 	多票信息查询结果dto
 * @Copyright 		Copyright (c)2013 
 * @Company 		Deppon 
 * @author 			mujun
 * @date 			2014-4-10 上午9:38:58 
 * @version 		1.0 
 ************************************************************
 */
public class NotifyMultibillDto implements Serializable {
	

	/**
	 */
	
	private static final long serialVersionUID = 9164269555695799452L;

	private String receiveCustomerCode;  //收获客户编码
	
	private String waybillNo;  //运单号
	
	private String status;  //运单状态
	
	private String  paidMethod;  //付款方式
	
	private String receiveCustomerContact;  //收货人
	
	private String receiveCustomerMobilephone; //手机
	
	private String receiveCustomerPhone;  //收货人电话
	
	private String togetherSendCode; //合送编码
	
	private String togetherSendMark;  //合送标示
	
	private int stockGoodsQty;  //库存件数
	
	private String receiveCustomerProvCode;  //省份
	
	private String receiveCustomerCityCode;  //城市
	
	private String receiveCustomerAddress;  //地址
	
	private String receiveCustomerDistCode;  //区县
	
	private Date planArriveTime;  // 预计到达时间
	
	private String taskStatus; //车辆状态
	
	private String receiveMethod ;//派送方式
	
	private String receiveCustomerAddressNote;  //地址备注

	/**
	 * 处理类型:"PDA_ACTIVE"--PDA已补录 ，"PC_ACTIVE"--暂存已开单
	 */
	private String pendingType;
	
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getTogetherSendCode() {
		return togetherSendCode;
	}

	public void setTogetherSendCode(String togetherSendCode) {
		this.togetherSendCode = togetherSendCode;
	}

	public String getTogetherSendMark() {
		return togetherSendMark;
	}

	public void setTogetherSendMark(String togetherSendMark) {
		this.togetherSendMark = togetherSendMark;
	}

	public int getStockGoodsQty() {
		return stockGoodsQty;
	}

	public void setStockGoodsQty(int stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}

	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getPendingType() {
		return pendingType;
	}

	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
}
