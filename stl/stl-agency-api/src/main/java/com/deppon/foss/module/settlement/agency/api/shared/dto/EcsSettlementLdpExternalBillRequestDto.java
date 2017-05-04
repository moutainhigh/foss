package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递业务 外发单 请求的参数DTO
 * @author 243921-zhangtingting
 * @date 2016-04-20 下午14:56:09
 *
 */
public class EcsSettlementLdpExternalBillRequestDto implements Serializable {

	//序列号
	private static final long serialVersionUID = 1L;

	//外发员工号
	private String empCode;
			
	//外发员姓名
	private String empName;
			
	//外发员部门编码
	private String currentDeptCode;
			
	//外发员部门名称
	private String currentDeptName;
	
	//运单ID
	private String waybillId;
	
	// 运单号
	private String waybillNo;

	//外发单号
	private String externalBillNo;
	
	//落地配代理编码
	private String agentCompanyCode;
	
	//落地配代理名称
	private String agentCompanyName;
	
	//落地配代理网点
	private String agentOrgCode;
	
	//出发部门编码 -对应运单的收货部门(出发部门)
	private String receiveOrgCode;
	
	//到达部门编码
	private String lastLoadOrgCode;//-对应运单的最终配置部门
	
	//外发部门//制单人所在的部门
	private String waifabumen;

	//外发部门//制单人所在的部门名称
	private String waifabumenName;
	
	/**
	 * 是否中转外发
	 * （”Y”：即中转外发；”N”否则为非中转外发，此时外发成本必须大于0）
	 */
	private String transferExternal;
	
	//总费用
    private BigDecimal totalFee;
    
    //外发成本总额【应付费用】
	private BigDecimal costAmount;
	
	//代收货款费用
    private BigDecimal codAmount;
    
    //代收货款手续费
	private BigDecimal codAgencyFee;
    
	//外发运费
	private BigDecimal externalAgencyFee;
	
	//保价费
	private BigDecimal externalInsuranceFee;
	
	//币种
	private String currencyCode;
	
	//付款方式P
	private String paidMethod;

	//审核状态
	private String auditStatus;

	//业务日期(当前业务操作时间)
	private Date businessDate;
	
	//外发单创建时间(录入日期)
	private Date createTime;
	
	//快递代理公司核销方式
	private String verificationMethods;
	
	//add by 329757 判断是否是异常
	private boolean wukong;

	public boolean getWukong() {
		return wukong;
	}

	public void setWukong(boolean wukong) {
		this.wukong = wukong;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public String getWaybillId() {
		return waybillId;
	}

	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getExternalBillNo() {
		return externalBillNo;
	}

	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	public String getAgentOrgCode() {
		return agentOrgCode;
	}

	public void setAgentOrgCode(String agentOrgCode) {
		this.agentOrgCode = agentOrgCode;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getWaifabumen() {
		return waifabumen;
	}

	public void setWaifabumen(String waifabumen) {
		this.waifabumen = waifabumen;
	}

	public String getWaifabumenName() {
		return waifabumenName;
	}

	public void setWaifabumenName(String waifabumenName) {
		this.waifabumenName = waifabumenName;
	}

	public String getTransferExternal() {
		return transferExternal;
	}

	public void setTransferExternal(String transferExternal) {
		this.transferExternal = transferExternal;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getCodAgencyFee() {
		return codAgencyFee;
	}

	public void setCodAgencyFee(BigDecimal codAgencyFee) {
		this.codAgencyFee = codAgencyFee;
	}

	public BigDecimal getExternalAgencyFee() {
		return externalAgencyFee;
	}

	public void setExternalAgencyFee(BigDecimal externalAgencyFee) {
		this.externalAgencyFee = externalAgencyFee;
	}

	public BigDecimal getExternalInsuranceFee() {
		return externalInsuranceFee;
	}

	public void setExternalInsuranceFee(BigDecimal externalInsuranceFee) {
		this.externalInsuranceFee = externalInsuranceFee;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getVerificationMethods() {
		return verificationMethods;
	}

	public void setVerificationMethods(String verificationMethods) {
		this.verificationMethods = verificationMethods;
	}
	
}
