package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 返回列表对象
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-26 下午9:00:08
 */
public class OnlionMonitorReportListDto implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -9083754368161422477L;

	/**
	 * 运单\对账单
	 */
	private String number;
	
	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 支付编号
	 */
	private String payNumber;

	/**
	 * 事业部
	 */
	private String businessDivision;

	/**
	 * 大区
	 */
	private String largeArea;

	/**
	 * 小区
	 */
	private String smallArea;

	/**
	 * 部门
	 */
	private String deptName;

	/**
	 * 客户编码
	 */
	private String custNumber;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 登录用户名
	 */
	private String userName;

	/**
	 * 应收总额
	 */
	private BigDecimal totalAmount;

	/**
	 * 在线支付总额
	 */
	private BigDecimal onlinePayAmount;

	/**
	 * 支付类型
	 */
	private int payType;

	/**
	 * 支付状态
	 */
	private int payStatus;

	/**
	 * 支付时间
	 */
	private Date onlinePayTime;

	/**
	 * 核销状态
	 */
	private int verifyStatus;

	/**
	 * 核销时间
	 */
	private Date verifyTime;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 是否在途中
	 */
	private String isOnway;

	/**
	 * @return number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return payNumber
	 */
	public String getPayNumber() {
		return payNumber;
	}

	/**
	 * @param payNumber
	 */
	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	/**
	 * @return businessDivision
	 */
	public String getBusinessDivision() {
		return businessDivision;
	}

	/**
	 * @param businessDivision
	 */
	public void setBusinessDivision(String businessDivision) {
		this.businessDivision = businessDivision;
	}

	/**
	 * @return largeArea
	 */
	public String getLargeArea() {
		return largeArea;
	}

	/**
	 * @param largeArea
	 */
	public void setLargeArea(String largeArea) {
		this.largeArea = largeArea;
	}

	/**
	 * @return smallArea
	 */
	public String getSmallArea() {
		return smallArea;
	}

	/**
	 * @param smallArea
	 */
	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
	}

	/**
	 * @return deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return custNumber
	 */
	public String getCustNumber() {
		return custNumber;
	}

	/**
	 * @param custNumber
	 */
	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	/**
	 * @return custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return onlinePayAmount
	 */
	public BigDecimal getOnlinePayAmount() {
		return onlinePayAmount;
	}

	/**
	 * @param onlinePayAmount
	 */
	public void setOnlinePayAmount(BigDecimal onlinePayAmount) {
		this.onlinePayAmount = onlinePayAmount;
	}

	/**
	 * @return onlinePayTime
	 */
	public Date getOnlinePayTime() {
		return onlinePayTime;
	}

	/**
	 * @param onlinePayTime
	 */
	public void setOnlinePayTime(Date onlinePayTime) {
		this.onlinePayTime = onlinePayTime;
	}

	/**
	 * @return verifyTime
	 */
	public Date getVerifyTime() {
		return verifyTime;
	}

	/**
	 * @param verifyTime
	 */
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	/**
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return isOnway
	 */
	public String getIsOnway() {
		return isOnway;
	}

	/**
	 * @param isOnway
	 */
	public void setIsOnway(String isOnway) {
		this.isOnway = isOnway;
	}

	/**
	 * @return payType
	 */
	public int getPayType() {
		return payType;
	}

	/**
	 * @param payType
	 */
	public void setPayType(int payType) {
		this.payType = payType;
	}

	/**
	 * @return payStatus
	 */
	public int getPayStatus() {
		return payStatus;
	}

	/**
	 * @param payStatus
	 */
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * @return verifyStatus
	 */
	public int getVerifyStatus() {
		return verifyStatus;
	}

	/**
	 * @param verifyStatus
	 */
	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	/**
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
