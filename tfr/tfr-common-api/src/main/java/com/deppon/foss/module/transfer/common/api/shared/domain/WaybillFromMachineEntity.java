package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class WaybillFromMachineEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String waybillNo;
	
	private Date createTime;
	
	private Date finishTime;
	
	private String status;
	
	private String isOverWeight;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsOverWeight() {
		return isOverWeight;
	}

	public void setIsOverWeight(String isOverWeight) {
		this.isOverWeight = isOverWeight;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
