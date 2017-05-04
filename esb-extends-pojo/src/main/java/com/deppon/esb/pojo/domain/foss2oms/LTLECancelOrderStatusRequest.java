package com.deppon.esb.pojo.domain.foss2oms;

import java.io.Serializable;

public class LTLECancelOrderStatusRequest  implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 结果
	 */
	private String result;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
