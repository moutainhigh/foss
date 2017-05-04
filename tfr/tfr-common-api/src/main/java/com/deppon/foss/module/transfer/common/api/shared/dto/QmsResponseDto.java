package com.deppon.foss.module.transfer.common.api.shared.dto;

public class QmsResponseDto {
	//补码员工号
	private String complementOperatorCode; 
	//补码员姓名
	private String complementOperatorName; 
	//补码部门经理工号
	private String complementManagerCode;
	//补码部门经理姓名
	private String complementManagerName;
	//交接单号/配载单号
	private String handoverNo;
	//运单经手上一部门
	private String previousPassOrgCode;
	//运单经手上一部门名称
	private String previousPassOrgName;
	//经手部门
	private String passOrgCode;
	//经手部门名称
	private String passOrgName;
	//上一环节装车部门
	private String previousloadingOrgCode;
	//上一环节装车部门名称
	private String previousloadingOrgName;
	/*//外发部门负责人工号
	private String externalOrgPrincipalNo;
	//外发部门负责人姓名
	private String externalOrgPrincipalName;
	//快递中转场负责人工号
	private String transferCenterPrincipalNo;
	//快递中转场负责人姓名
	private String transferCenterPrincipalName;*/
	//K违规返货负责人工号
	private String illegalReturnPrincipalNo;
	//K违规返货负责人姓名
	private String illegalReturnPrincipalName;
	//最后一个打印标签的人工号
	private String lastPrintUserCode;
	//最后一个打印标签的人姓名
	private String lastPrintUserName;
	public String getComplementOperatorCode() {
		return complementOperatorCode;
	}
	public void setComplementOperatorCode(String complementOperatorCode) {
		this.complementOperatorCode = complementOperatorCode;
	}
	public String getComplementOperatorName() {
		return complementOperatorName;
	}
	public void setComplementOperatorName(String complementOperatorName) {
		this.complementOperatorName = complementOperatorName;
	}
	public String getComplementManagerCode() {
		return complementManagerCode;
	}
	public void setComplementManagerCode(String complementManagerCode) {
		this.complementManagerCode = complementManagerCode;
	}
	public String getComplementManagerName() {
		return complementManagerName;
	}
	public void setComplementManagerName(String complementManagerName) {
		this.complementManagerName = complementManagerName;
	}
	public String getHandoverNo() {
		return handoverNo;
	}
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
	public String getPreviousPassOrgCode() {
		return previousPassOrgCode;
	}
	public void setPreviousPassOrgCode(String previousPassOrgCode) {
		this.previousPassOrgCode = previousPassOrgCode;
	}
	public String getPassOrgCode() {
		return passOrgCode;
	}
	public void setPassOrgCode(String passOrgCode) {
		this.passOrgCode = passOrgCode;
	}
	public String getPreviousloadingOrgCode() {
		return previousloadingOrgCode;
	}
	public void setPreviousloadingOrgCode(String previousloadingOrgCode) {
		this.previousloadingOrgCode = previousloadingOrgCode;
	}
	/*public String getExternalOrgPrincipalNo() {
		return externalOrgPrincipalNo;
	}
	public void setExternalOrgPrincipalNo(String externalOrgPrincipalNo) {
		this.externalOrgPrincipalNo = externalOrgPrincipalNo;
	}
	public String getExternalOrgPrincipalName() {
		return externalOrgPrincipalName;
	}
	public void setExternalOrgPrincipalName(String externalOrgPrincipalName) {
		this.externalOrgPrincipalName = externalOrgPrincipalName;
	}
	public String getTransferCenterPrincipalNo() {
		return transferCenterPrincipalNo;
	}
	public void setTransferCenterPrincipalNo(String transferCenterPrincipalNo) {
		this.transferCenterPrincipalNo = transferCenterPrincipalNo;
	}
	public String getTransferCenterPrincipalName() {
		return transferCenterPrincipalName;
	}
	public void setTransferCenterPrincipalName(String transferCenterPrincipalName) {
		this.transferCenterPrincipalName = transferCenterPrincipalName;
	}*/
	public String getLastPrintUserCode() {
		return lastPrintUserCode;
	}
	public void setLastPrintUserCode(String lastPrintUserCode) {
		this.lastPrintUserCode = lastPrintUserCode;
	}
	public String getLastPrintUserName() {
		return lastPrintUserName;
	}
	public void setLastPrintUserName(String lastPrintUserName) {
		this.lastPrintUserName = lastPrintUserName;
	}
	public String getPreviousPassOrgName() {
		return previousPassOrgName;
	}
	public void setPreviousPassOrgName(String previousPassOrgName) {
		this.previousPassOrgName = previousPassOrgName;
	}
	public String getPassOrgName() {
		return passOrgName;
	}
	public void setPassOrgName(String passOrgName) {
		this.passOrgName = passOrgName;
	}
	public String getPreviousloadingOrgName() {
		return previousloadingOrgName;
	}
	public void setPreviousloadingOrgName(String previousloadingOrgName) {
		this.previousloadingOrgName = previousloadingOrgName;
	}
	public String getIllegalReturnPrincipalNo() {
		return illegalReturnPrincipalNo;
	}
	public void setIllegalReturnPrincipalNo(String illegalReturnPrincipalNo) {
		this.illegalReturnPrincipalNo = illegalReturnPrincipalNo;
	}
	public String getIllegalReturnPrincipalName() {
		return illegalReturnPrincipalName;
	}
	public void setIllegalReturnPrincipalName(String illegalReturnPrincipalName) {
		this.illegalReturnPrincipalName = illegalReturnPrincipalName;
	}
	
}
