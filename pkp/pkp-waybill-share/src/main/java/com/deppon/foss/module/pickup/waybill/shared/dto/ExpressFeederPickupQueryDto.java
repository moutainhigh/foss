package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * PDA快递接驳对应查询Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2015-4-23 15:08:41
 */
public class ExpressFeederPickupQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;

	//对应的司机工号
	private String driverCode;
	
	//对应接驳点快递员工号
	private String expressEmpCode;
	
	//快递员是否 已经完成接货
	private String handOverStatus;
	
	//TaskNo
	private String taskNo;
	
	private List<String> waybillNoList;

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getExpressEmpCode() {
		return expressEmpCode;
	}

	public void setExpressEmpCode(String expressEmpCode) {
		this.expressEmpCode = expressEmpCode;
	}

	public String getHandOverStatus() {
		return handOverStatus;
	}

	public void setHandOverStatus(String handOverStatus) {
		this.handOverStatus = handOverStatus;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
}