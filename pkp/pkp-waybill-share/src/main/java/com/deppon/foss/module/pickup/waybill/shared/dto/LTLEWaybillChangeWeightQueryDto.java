
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 批量修改重量体积查询DTO（零担）
 * 
 */
public class LTLEWaybillChangeWeightQueryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//客户编码
	private String customerCode;
	
	//起始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;
	
	//更改状态
	private String changeStatus;
	
	//运单号
	private String waybillNo;
	
	//是否是批量更改的运单
	private Boolean isBatchChangeWaybill;
	
	//当前登录部门
	private String currentDeptCode;
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public String getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Boolean getIsBatchChangeWaybill() {
		return isBatchChangeWaybill;
	}

	public void setIsBatchChangeWaybill(Boolean isBatchChangeWaybill) {
		this.isBatchChangeWaybill = isBatchChangeWaybill;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
}