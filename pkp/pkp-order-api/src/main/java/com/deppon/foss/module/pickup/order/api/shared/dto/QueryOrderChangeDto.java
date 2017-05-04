package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QueryOrderChangeDto  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5710738279013515598L;
	 // 变更Id
	private String changeId;
	// 产品编码
	private String productCode;
	// 有效起始时间
	private Date startTime;
	// 有效截止时间
	private Date endTime;
	// 查询条数
	private int num;
	//订单类型
	private String order_type;
	//标记
	private String job_id;
	//最早接货时间
	private Date earliestPickupTime;
	//运输性质集合 10.14
	private List<String> productCodes;
	
	
	public List<String> getProductCodes() {
		return productCodes;
	}
	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getChangeId() {
		return changeId;
	}
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}
	public Date getEarliestPickupTime() {
		return earliestPickupTime;
	}
	public void setEarliestPickupTime(Date earliestPickupTime) {
		this.earliestPickupTime = earliestPickupTime;
	}
	
}
