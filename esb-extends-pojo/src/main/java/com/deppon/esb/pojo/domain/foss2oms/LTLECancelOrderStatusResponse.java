package com.deppon.esb.pojo.domain.foss2oms;

import java.io.Serializable;

public class LTLECancelOrderStatusResponse  implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderNo;
	
	private String waybillNo;
	
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

}
