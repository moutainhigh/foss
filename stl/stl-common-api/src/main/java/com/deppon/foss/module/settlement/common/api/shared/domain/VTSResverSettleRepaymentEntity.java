package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author 218392 zhangyongxue
 * @date 2016-06-17 07:34:05
 * 到达付款信息实体
 */
public class VTSResverSettleRepaymentEntity implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * 1.ID
	 */
	private String id;
	
	/**
	 * 2.付款编号
	 */
	private String repaymentNo;
	
	/**
	 * 3.付款方式
	 */
	private String paymentType;
	
	/**
	 * 4.实付运费：实收到付运费
	 */
	private BigDecimal actualFreight;
	
	/**
	 * 5.代收货款：实收代收货款
	 */
	private BigDecimal codAmount;
	
	/**
	 * 6.提货客户客户名称
	 */
	private String consigneeName;
	
	/**
	 * 7.付款时间
	 */
	private Date paymentTime;
	
	/*****************************************************   以上是反签收查询界面要的东西     **************************************************************/
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 客户编码
	 */
	private String consigneeCode;
	
	/**
	 * 款项认领编码
	 */
	private String claimNo;

	/**
	 * 仓储费
	 */
	private BigDecimal storageFee;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/** 
	 * 操作人编码
	 */
	private String operatorCode;
	
	/** 
	 * 操作部门
	 */
	private String operateOrgName;
	
	/** 
	 * 操作部门编码
	 */
	private String operateOrgCode;
	
	/** 
	 * 币种 RMB
	 */
	private String currencyCode;
	
	/** 
	 * 是否审批中
	 */
	private String isRfcing;
	
	/** 
	 * 是否已有财务单据
	 */
	private String stlbillGeneratedStatus;
	
	/** 
	 * job id 
	 */
	private String jobId;
	
	/**
	 * PDA串号
	 */
	private String pdaSerial;
	
	/**
	 * 银行交易流水号
	 */
	private String bankTradeSerail;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepaymentNo() {
		return repaymentNo;
	}

	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getActualFreight() {
		return actualFreight;
	}

	public void setActualFreight(BigDecimal actualFreight) {
		this.actualFreight = actualFreight;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getConsigneeCode() {
		return consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public BigDecimal getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(BigDecimal storageFee) {
		this.storageFee = storageFee;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getIsRfcing() {
		return isRfcing;
	}

	public void setIsRfcing(String isRfcing) {
		this.isRfcing = isRfcing;
	}

	public String getStlbillGeneratedStatus() {
		return stlbillGeneratedStatus;
	}

	public void setStlbillGeneratedStatus(String stlbillGeneratedStatus) {
		this.stlbillGeneratedStatus = stlbillGeneratedStatus;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getPdaSerial() {
		return pdaSerial;
	}

	public void setPdaSerial(String pdaSerial) {
		this.pdaSerial = pdaSerial;
	}

	public String getBankTradeSerail() {
		return bankTradeSerail;
	}

	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}

//	public boolean isIfSuccess() {
//		return ifSuccess;
//	}
//
//	public void setIfSuccess(boolean ifSuccess) {
//		this.ifSuccess = ifSuccess;
//	}
//
//	public String getErrorMsg() {
//		return errorMsg;
//	}
//
//	public void setErrorMsg(String errorMsg) {
//		this.errorMsg = errorMsg;
//	}

}
