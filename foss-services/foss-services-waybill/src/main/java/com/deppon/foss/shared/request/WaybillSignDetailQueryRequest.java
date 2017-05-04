package com.deppon.foss.shared.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
 * @author 272311
 *
 */
public class WaybillSignDetailQueryRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3783907209424379670L;

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
	
	/**
	 * 起始页
	 */
	private int start ;
	/**
	 * 每页显示记录数
	 */
	private int limit ;
	
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

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "WaybillSignDetailQueryRequest [deliveryCustomerCode="
				+ deliveryCustomerCode + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", start=" + start + ", limit="
				+ limit + "]";
	}

}
