package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ArriveSheetLogEntity extends BaseEntity {

	private static final long serialVersionUID = 2247937873649161514L;

	// 运单号
	private String waybillNo;
	// 操作内容
	private String operateContent;
	// 到达联编号
	private String arriveSheetNo;

	private String operator;

	private String operatorCode;

	private String operateOrgName;

	private String operateOrgCode;

	private Date operateTime;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getArriveSheetNo() {
		return arriveSheetNo;
	}

	public void setArriveSheetNo(String arriveSheetNo) {
		this.arriveSheetNo = arriveSheetNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}