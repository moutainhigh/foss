package com.deppon.foss.module.transfer.common.api.shared.dto;



public class TaobaoTrackingsRequestDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//ID
	private String id;
	
	//运单号WAYBILL_NO
	private String waybillNo;
	
	//渠道单号CHANNEL_ORDER
	private String channelOrder;
	
	//发生时间 OPERATIME 格式：yyyy_mm_dd hh:mm:ss.ms
	private String operaTime;
	
	/**
	 * 轨迹状态EVENT_TYPE：GOT -揽货；SENT_SCAN-派件扫描；SIGNSUCCESS-签收成功；FAILED-签收失败/拒签； TRUCK_DEPARTURE-干线开始； 
		CANCEL_TRUCK_ARRIVAL-干线结束；
	 */
	private String eventType;
	
	/**
	 * 流水号
	* @fields SERIAL_NO
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:14:55
	* @version V1.0
	*/
	private String serialNo;
	
	/**
	 * 订单来源
	* @fields ORDER_CHANNEL
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:13
	* @version V1.0
	*/
	private String orderChannel;
	
	/**
	 * 派送方式
	* @fields RECEIVE_METHOD
	* @author 14022-foss-songjianlong
	* @update 2016-7-11 21:31:49
	* @version V1.0
	*/
	private String receiveMethod;

	/**
	 * 备注
	* @fields Remark
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

	public String getOperaTime() {
		return operaTime;
	}

	public void setOperaTime(String operaTime) {
		this.operaTime = operaTime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
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
