package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CubcExternalBillDto implements Serializable{


	/**
	 *@author 310248
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号WAYBILL_NO
	 */
	private String waybillNo;

	/**
	 * 付款方式PAID_METHOD
	 */
	private String paidMethod;

	/**
	 * 外发部门//制单人所在的部门
	 */
	private String waifabumen;

	/**
	 * 外发部门//制单人所在的部门名称
	 */
	private String waifabumenName;

	/**
	 * 外发单号
	 */
	private String externalBillNo;

	/**
	 * 外发代理费
	 */
	private BigDecimal externalAgencyFee;

	/**
	 * 代理送货费
	 */
	private BigDecimal deliveryFee;

	/**
	 * 外发成本总额
	 */
	private BigDecimal costAmount;

	/**
	 * 实收代理费
	 */
	private BigDecimal receiveAgencyFee;

	/**
	 * 实付代理费
	 */
	private BigDecimal payAgencyFee;

	/**
	 * 自动核销申请
	 */
	private String isWriteOff;

	/**
	 * 审核状态
	 */
	private String auditStatus;

	/**
	 * 偏线代理编码 --
	 */
	private String agentCompanyCode;

	/**
	 * 偏线代理名称--
	 */
	private String agentCompanyName;

	/**
	 * 是否中转外发
	 */
	private String transferExternal;

	/**
	 * 币种 ---
	 */
	private String currencyCode;

	/**
	 * 业务日期(当前业务操作时间)---
	 */
	private Date businessDate;

	/**
	 * 外发单创建时间(录入日期)
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 单据状态
	 */
	private String status;
	
/*********************************************运单信息*****************************************************/	
	
	/**
	 * 运单Id
	 */
	private String waybillId;
	
	/**
	 * 出发部门编码
	 */
	private String receiveOrgCode;//-对应运单的收货部门(出发部门)
	
	/**
	 * 到达部门编码
	 */
	private String lastLoadOrgCode;//-对应运单的最终配置部门
	
	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;
	
	/**
	 * 总费用
	 */
    private BigDecimal totalFee;
    
    /**
     * 代收货款费用
     */
    private BigDecimal codAmount;
    
    /**
     * 是否初始化
     */
    private String isInit;

//----------增加字段，用于判断对应收或者应付进行操作-----------
    /**
     * 是否修改外发成本
     */
    private String isModifyCostAmount;
    
    /**
     * 是否中转外发
     */
	private String isModifyTransferExternal;
    
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the paidMethod
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	
	/**
	 * @param paidMethod the paidMethod to set
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	
	/**
	 * @return  the waifabumen
	 */
	public String getWaifabumen() {
		return waifabumen;
	}

	
	/**
	 * @param waifabumen the waifabumen to set
	 */
	public void setWaifabumen(String waifabumen) {
		this.waifabumen = waifabumen;
	}

	
	/**
	 * @return  the waifabumenName
	 */
	public String getWaifabumenName() {
		return waifabumenName;
	}

	
	/**
	 * @param waifabumenName the waifabumenName to set
	 */
	public void setWaifabumenName(String waifabumenName) {
		this.waifabumenName = waifabumenName;
	}

	
	/**
	 * @return  the externalBillNo
	 */
	public String getExternalBillNo() {
		return externalBillNo;
	}

	
	/**
	 * @param externalBillNo the externalBillNo to set
	 */
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	
	/**
	 * @return  the externalAgencyFee
	 */
	public BigDecimal getExternalAgencyFee() {
		return externalAgencyFee;
	}

	
	/**
	 * @param externalAgencyFee the externalAgencyFee to set
	 */
	public void setExternalAgencyFee(BigDecimal externalAgencyFee) {
		this.externalAgencyFee = externalAgencyFee;
	}

	
	/**
	 * @return  the deliveryFee
	 */
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	
	/**
	 * @param deliveryFee the deliveryFee to set
	 */
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	
	/**
	 * @return  the costAmount
	 */
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	
	/**
	 * @param costAmount the costAmount to set
	 */
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	
	/**
	 * @return  the receiveAgencyFee
	 */
	public BigDecimal getReceiveAgencyFee() {
		return receiveAgencyFee;
	}

	
	/**
	 * @param receiveAgencyFee the receiveAgencyFee to set
	 */
	public void setReceiveAgencyFee(BigDecimal receiveAgencyFee) {
		this.receiveAgencyFee = receiveAgencyFee;
	}

	
	/**
	 * @return  the payAgencyFee
	 */
	public BigDecimal getPayAgencyFee() {
		return payAgencyFee;
	}

	
	/**
	 * @param payAgencyFee the payAgencyFee to set
	 */
	public void setPayAgencyFee(BigDecimal payAgencyFee) {
		this.payAgencyFee = payAgencyFee;
	}

	
	/**
	 * @return  the isWriteOff
	 */
	public String getIsWriteOff() {
		return isWriteOff;
	}

	
	/**
	 * @param isWriteOff the isWriteOff to set
	 */
	public void setIsWriteOff(String isWriteOff) {
		this.isWriteOff = isWriteOff;
	}

	
	/**
	 * @return  the auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	
	/**
	 * @param auditStatus the auditStatus to set
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	
	/**
	 * @return  the agentCompanyCode
	 */
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	
	/**
	 * @param agentCompanyCode the agentCompanyCode to set
	 */
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	
	/**
	 * @return  the agentCompanyName
	 */
	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	
	/**
	 * @param agentCompanyName the agentCompanyName to set
	 */
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	
	/**
	 * @return  the transferExternal
	 */
	public String getTransferExternal() {
		return transferExternal;
	}

	
	/**
	 * @param transferExternal the transferExternal to set
	 */
	public void setTransferExternal(String transferExternal) {
		this.transferExternal = transferExternal;
	}

	
	/**
	 * @return  the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	
	/**
	 * @return  the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	
	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	
	/**
	 * @return  the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	/**
	 * @return  the status
	 */
	public String getStatus() {
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	/**
	 * @return  the waybillId
	 */
	public String getWaybillId() {
		return waybillId;
	}

	
	/**
	 * @param waybillId the waybillId to set
	 */
	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
	}

	
	/**
	 * @return  the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	
	/**
	 * @param receiveOrgCode the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	
	/**
	 * @return  the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	
	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to set
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	
	/**
	 * @return  the deliveryGoodsFee
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	
	/**
	 * @param deliveryGoodsFee the deliveryGoodsFee to set
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	
	/**
	 * @return  the totalFee
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	
	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	
	/**
	 * @return  the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	
	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}


	
	/**
	 * @return  the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}


	
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	/**
	 * @return the isInit
	 */
	public String getIsInit() {
		return isInit;
	}


	/**
	 * @param isInit the isInit to set
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}


	/**
	 * @return the isModifyCostAmount
	 */
	public String getIsModifyCostAmount() {
		return isModifyCostAmount;
	}


	/**
	 * @param isModifyCostAmount the isModifyCostAmount to set
	 */
	public void setIsModifyCostAmount(String isModifyCostAmount) {
		this.isModifyCostAmount = isModifyCostAmount;
	}


	/**
	 * @return the isModifyTransferExternal
	 */
	public String getIsModifyTransferExternal() {
		return isModifyTransferExternal;
	}


	/**
	 * @param isModifyTransferExternal the isModifyTransferExternal to set
	 */
	public void setIsModifyTransferExternal(String isModifyTransferExternal) {
		this.isModifyTransferExternal = isModifyTransferExternal;
	}
	
	

}
