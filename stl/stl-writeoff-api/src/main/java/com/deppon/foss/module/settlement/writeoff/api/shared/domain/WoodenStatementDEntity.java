package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class WoodenStatementDEntity extends BaseEntity {
	 
	private static final long serialVersionUID = 1L;
	/**
	 * 业务日期
	 */
	private Date businessDate;
	/**
	 * 单号
	 */
	private String payableNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 包装部门编码
	 */
	private String billOrgCode;
	/**
	 * 包装部门名称
	 */
	private String billOrgName;
	/**
	 * 应付部门编码
	 */
	private String payableOrgCode;
	/**
	 * 应付部门名称
	 */
	private String payableOrgName;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 已核销
	 */
	private BigDecimal verifyAmount;
	/**
	 * 未核销
	 */
	private BigDecimal unverifyAmount;
	/**
	 * 打木架体积
	 */
	private BigDecimal actualFrameVolume;
	/**
	 * 打木箱体积
	 */
	private BigDecimal actualWoodenVolume;
	/**
	 * 打木托体积
	 */
	private BigDecimal actualMaskNumber;
	/**
	 * 包带根数
	 */
	private BigDecimal bagBeltNum;
	/**
	 * 木条长度
	 */
	private BigDecimal woodenBarLong;
	/**
	 * 气泡膜体积
	 */
	private BigDecimal bubbVelamenVolume;
	/**
	 * 缠绕膜体积
	 */
	private BigDecimal bindVelamenVolume;
	/**
	 * 单据父类型
	 */
	private String billParentType;
	/**
	 * 包装类型
	 */
	private String packType;
	
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public String getPayableNo() {
		return payableNo;
	}
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getBillOrgCode() {
		return billOrgCode;
	}
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}
	public String getBillOrgName() {
		return billOrgName;
	}
	public void setBillOrgName(String billOrgName) {
		this.billOrgName = billOrgName;
	}
	public String getPayableOrgCode() {
		return payableOrgCode;
	}
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}
	public String getPayableOrgName() {
		return payableOrgName;
	}
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}
	public BigDecimal getActualFrameVolume() {
		return actualFrameVolume;
	}
	public void setActualFrameVolume(BigDecimal actualFrameVolume) {
		this.actualFrameVolume = actualFrameVolume;
	}
	public BigDecimal getActualWoodenVolume() {
		return actualWoodenVolume;
	}
	public void setActualWoodenVolume(BigDecimal actualWoodenVolume) {
		this.actualWoodenVolume = actualWoodenVolume;
	}
	public BigDecimal getActualMaskNumber() {
		return actualMaskNumber;
	}
	public void setActualMaskNumber(BigDecimal actualMaskNumber) {
		this.actualMaskNumber = actualMaskNumber;
	}
	public BigDecimal getBagBeltNum() {
		return bagBeltNum;
	}
	public void setBagBeltNum(BigDecimal bagBeltNum) {
		this.bagBeltNum = bagBeltNum;
	}
	public BigDecimal getWoodenBarLong() {
		return woodenBarLong;
	}
	public void setWoodenBarLong(BigDecimal woodenBarLong) {
		this.woodenBarLong = woodenBarLong;
	}
	public BigDecimal getBubbVelamenVolume() {
		return bubbVelamenVolume;
	}
	public void setBubbVelamenVolume(BigDecimal bubbVelamenVolume) {
		this.bubbVelamenVolume = bubbVelamenVolume;
	}
	public BigDecimal getBindVelamenVolume() {
		return bindVelamenVolume;
	}
	public void setBindVelamenVolume(BigDecimal bindVelamenVolume) {
		this.bindVelamenVolume = bindVelamenVolume;
	}
	public String getBillParentType() {
		return billParentType;
	}
	public void setBillParentType(String billParentType) {
		this.billParentType = billParentType;
	}
	public String getPackType() {
		return packType;
	}
	public void setPackType(String packType) {
		this.packType = packType;
	}

}
