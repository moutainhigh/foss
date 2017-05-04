package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 接送货司机接货单号打印查询条件DTO
 *
 */
public class DriverPickupBillPrintDto implements Serializable {

	// 序列化版本号
	private static final long serialVersionUID = 1L;

	// 开单时间begin
	private Date pickupTimeBegin;

	// 开单时间end
	private Date pickupTimeEnd;

	// 接货司机 工号
	private String driverCode;

	// 接货司机 姓名
	private String driverName;

	// 运单号
	private String waybillNo;
	
	//登入用户部门编码
	private String orgCode;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getPickupTimeBegin() {
		return pickupTimeBegin;
	}

	public void setPickupTimeBegin(Date pickupTimeBegin) {
		this.pickupTimeBegin = pickupTimeBegin;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getPickupTimeEnd() {
		return pickupTimeEnd;
	}

	public void setPickupTimeEnd(Date pickupTimeEnd) {
		this.pickupTimeEnd = pickupTimeEnd;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

}