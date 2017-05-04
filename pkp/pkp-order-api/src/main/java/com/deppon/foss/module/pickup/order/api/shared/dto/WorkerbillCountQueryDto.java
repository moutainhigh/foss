package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WorkerbillCountQueryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 起始時間
	 * */
	private Date startDate;
	/*
	 *结束时间
	 */
	private Date endDate;
	/*
	 * 员工集合
	 * */
	private List<CourierSignDto> courierSignDtoList;
	
	/*
	 * 产品类型
	 */
	private List<String> productCode;
	/*
	 *状态集合
	 */
	private List<String> status;
	
	/** 
	 * 所属收派大区名称
	 */
	private String bigZoneName;
	
	/** 
	 * 所属收派小区名称
	 */
	private String smallZoneName;
	

	public List<String> getProductCode() {
		return productCode;
	}
	public void setProductCode(List<String> productCode) {
		this.productCode = productCode;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getBigZoneName() {
		return bigZoneName;
	}
	public void setBigZoneName(String bigZoneName) {
		this.bigZoneName = bigZoneName;
	}
	public String getSmallZoneName() {
		return smallZoneName;
	}
	public void setSmallZoneName(String smallZoneName) {
		this.smallZoneName = smallZoneName;
	}
	public List<CourierSignDto> getCourierSignDtoList() {
		return courierSignDtoList;
	}
	public void setCourierSignDtoList(List<CourierSignDto> courierSignDtoList) {
		this.courierSignDtoList = courierSignDtoList;
	}
	
}
