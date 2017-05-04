package com.deppon.foss.module.transfer.lostwarning.api.shared.dto;

import java.util.Date;

public class LostGoodsFindOrgDto {
	
	//运单号
	private String wayBillNo;
	
	//流水号
	private String serialNo;
	
	//操作人编码
	private String operatorCode;
	
	//操作人名称
	private String operatorName;
	
	//操作部门编码
	private String orgCode;
	
	//操作部门名称
	private String orgName;
	
	//入库类型 （可为空）
	private String inStockType;
	
	//创建时间
	private Date createTime;

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getInStockType() {
		return inStockType;
	}

	public void setInStockType(String inStockType) {
		this.inStockType = inStockType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
