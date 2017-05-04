package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递业务 结清货款 确认付款 请求的参数DTO
 * @author 243921-zhangtingting
 * @date 2016-04-15 上午11:14:48
 *
 */
public class EcsPaymentSettlementRequestDto implements Serializable {
	
	//序列号
	private static final long serialVersionUID = 1L;
	
	//当前登录员工工号
	private String empCode;
		
	//当前登录员工姓名
	private String empName;
		
	//当前登录部门编码
	private String currentDeptCode;
		
	//当前登录部门名称
	private String currentDeptName;
	
	//运单号
	private String waybillNo;
	
	//是否临欠/月结
	private String isMonthStatement;
	
	//付款方式
	private String paymentType;
	
	//结清方式
	private String settleApproach;

	//发生业务日期 (结清货款时间)
	private Date businessDate;
	
	//实收代收货款费用
	private BigDecimal codFee;
	
	//实收到付运费
	private BigDecimal toPayFee;
	
	//币种
	private String currencyCode;
	
	//来源单号-存放（到达实收单号）结清货款付款编号
	private String sourceBillNo;
	
	//汇款编号(结清货款的款项认领编号)
	private String paymentNo;
	
	//POS串号
	private String posSerialNum;
	
	//银行交易流水号
	private String batchNo;
	
	//派送快递员工号
	private String deliverExpressCode;
	
	//派送快递员名称
	private String deliverExpressName;
	
	//是否合伙人运单
	private String isPtp;
	
	//客户编码
	private String customerCode;
	
	//客户名称
	private String customerName;
	
	public String getIsPtp() {
		return isPtp;
	}

	public void setIsPtp(String isPtp) {
		this.isPtp = isPtp;
	}

	public String getDeliverExpressCode() {
		return deliverExpressCode;
	}

	public void setDeliverExpressCode(String deliverExpressCode) {
		this.deliverExpressCode = deliverExpressCode;
	}

	public String getDeliverExpressName() {
		return deliverExpressName;
	}

	public void setDeliverExpressName(String deliverExpressName) {
		this.deliverExpressName = deliverExpressName;
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

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getIsMonthStatement() {
		return isMonthStatement;
	}

	public void setIsMonthStatement(String isMonthStatement) {
		this.isMonthStatement = isMonthStatement;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSettleApproach() {
		return settleApproach;
	}

	public void setSettleApproach(String settleApproach) {
		this.settleApproach = settleApproach;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public BigDecimal getCodFee() {
		return codFee;
	}

	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	public BigDecimal getToPayFee() {
		return toPayFee;
	}

	public void setToPayFee(BigDecimal toPayFee) {
		this.toPayFee = toPayFee;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getPosSerialNum() {
		return posSerialNum;
	}

	public void setPosSerialNum(String posSerialNum) {
		this.posSerialNum = posSerialNum;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	
}
