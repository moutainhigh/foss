package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author 272311
 *
 */
public class WaybillSignDetailQueryVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  发货客户编码
	 */
	private String deliveryCustomerCode;
	
	/**
	 * 开始查询时间
	 */
	private Date startTime;
	
	/**
	 * 结束查询时间
	 */
	private Date endTime;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	@Override
	public String toString() {
		return "WaybillSignDetailQueryVo [deliveryCustomerCode="
				+ deliveryCustomerCode + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	

}
