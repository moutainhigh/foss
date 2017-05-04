package com.deppon.foss.module.trackings.api.shared.dto;

import java.util.Date;


public class TaobaoTrackingsRequestDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//id
	private String id;

	//运单号
	private String waybillNo;
	
	//渠道单号
	private String channelOrder;
	
	//发生时间 格式：yyyy_mm_dd hh:mm:ss
	private String operaTime;
	
	/**
	 * 轨迹状态：GOT -揽货；SENT_SCAN-派件扫描；SIGNSUCCESS-签收成功；FAILED-签收失败/拒签； TRUCK_DEPARTURE-干线开始； 
		CANCEL_TRUCK_ARRIVAL-干线结束；
	 */
	private String eventType;
	
	/**
	 * 流水号
	* @fields serialNo
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:14:55
	* @version V1.0
	*/
	private String serialNo;
	
	/**
	 * 订单来源
	* @fields orderChannel
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:13
	* @version V1.0
	*/
	private String orderChannel;
	
	/**
	 * 派送方式
	* @fields receiveMethod
	* @author 14022-foss-songjianlong
	* @update 2016-7-11 21:31:49
	* @version V1.0
	*/
	private String receiveMethod;

	/**
	 * 备注
	* @fields expressIs
	* @author 322610-foss-songjianlong
	* @update 2016年7月4日15:33:55
	* @version V1.0
	*/
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getChannelOrder() {
		return channelOrder;
	}

	public void setChannelOrder(String channelOrder) {
		this.channelOrder = channelOrder;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getOperaTime() {
		return operaTime;
	}

	public void setOperaTime(String operaTime) {
		this.operaTime = operaTime;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
