package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CUBCSignChangeRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//来源单据编号
	private String sourceNo;
	
	//操作部门编码
	private String operateOrgCode;
	
	//操作部门名称
	private String operateOrgName;
	
	//操作员工编码
	private String operatorCode;
	
	//操作员工名称
	private String operatorName;
	
	//客户信息编码
	private String customerCode;
		
	//客户信息名称
	private String customerName;
	
	//变更时间
	private Date requestDate;
	
	//备注
	private String notes;
	
	//操作标记
	private String operationTag;
	
	//外发单号
	private List<String> externalWaybillNos;
	
	//签收情况
	private String signSituation;
	
	//签收情况
	private String oldSignSituation;
	
	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public String getOldSignSituation() {
		return oldSignSituation;
	}

	public void setOldSignSituation(String oldSignSituation) {
		this.oldSignSituation = oldSignSituation;
	}

	public List<String> getExternalWaybillNos() {
		return externalWaybillNos;
	}

	public void setExternalWaybillNos(List<String> externalWaybillNos) {
		this.externalWaybillNos = externalWaybillNos;
	}

	public String getOperationTag() {
		return operationTag;
	}

	public void setOperationTag(String operationTag) {
		this.operationTag = operationTag;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
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

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}
