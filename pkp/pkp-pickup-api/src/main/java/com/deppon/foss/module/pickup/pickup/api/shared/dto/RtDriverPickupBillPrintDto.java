package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *	接送货司机接货单号打印查询结果DTO
 *
 */
public class RtDriverPickupBillPrintDto implements Serializable{

	//序列化版本号
	private static final long serialVersionUID = 1L;
	//id 序号
	private String id;
	
	//开单时间begin
	private Date pickupTime;
	
	//运单号
	private String waybillNo;
	
	//接货司机工号
	private String driverCode;

	//接货司机姓名
	private String driverName;
	
	//营业部(名称)
	private String createOrgName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Date getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	

}