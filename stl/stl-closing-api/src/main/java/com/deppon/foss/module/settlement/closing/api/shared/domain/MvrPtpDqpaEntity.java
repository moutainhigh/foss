package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人德启代付月报表entity
 * 
 * @author gpz
 * @date 2016年3月17日
 */
public class MvrPtpDqpaEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1038900079333461930L;

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 期间
	 */
	private String period;
	
	/**
	 * 产品类型
	 */
	private String productCode;
	
	/**
	 * 客户名称(合伙人名称)
	 */
	private String customerName;
	
	/**
	 * 客户编码(合伙人编码)
	 */
	private String customerCode;
	
	/**
	 * 始发部门编码
	 */
	private String origOrgCode;
	
	/**
	 * 始发部门名称
	 */
	private String origOrgName;
	
	/**
	 * 到达部门编码
	 */
	private String destOrgCode;
	
	/**
	 * 到达部门名称
	 */
	private String destOrgName;
	
	/**
	 * 应收部门编码
	 */
	private String recOrgCode;
	
	/**
	 * 应收部门名称
	 */
	private String recOrgName;
	
	/**
	 * 应付部门编码
	 */
	private String payOrgCode;
	
	/**
	 * 应付部门编码
	 */
	private String payOrgName;
	
	/**
	 * 预收部门编码
	 */
	private String depOrgCode;
	
	/**
	 * 预收部门名称
	 */
	private String depOrgName;
	
	/**
	 * 凭证开始日期
	 */
	private Date voucherBeginTime;
	
	/**
	 * 凭证结束日期
	 */
	private Date voucherEndTime;
	
	/**
	 * 退预收付款申请
	 */
	private BigDecimal rdDrPayApply;
	
	/**
	 * 到达提成付款申请
	 */
	private BigDecimal arrivePayApply;
	
	/**
	 * 委托派费代付申请
	 */
	private BigDecimal deAdvanceApply;
	
	/**
	 * 到付运费代付申请
	 */
	private BigDecimal destAdvanceApply;
	
	/**
	 * 奖励付款申请
	 */
	private BigDecimal adPayApply;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return the recOrgCode
	 */
	public String getRecOrgCode() {
		return recOrgCode;
	}

	/**
	 * @param recOrgCode the recOrgCode to set
	 */
	public void setRecOrgCode(String recOrgCode) {
		this.recOrgCode = recOrgCode;
	}

	/**
	 * @return the recOrgName
	 */
	public String getRecOrgName() {
		return recOrgName;
	}

	/**
	 * @param recOrgName the recOrgName to set
	 */
	public void setRecOrgName(String recOrgName) {
		this.recOrgName = recOrgName;
	}

	/**
	 * @return the payOrgCode
	 */
	public String getPayOrgCode() {
		return payOrgCode;
	}

	/**
	 * @param payOrgCode the payOrgCode to set
	 */
	public void setPayOrgCode(String payOrgCode) {
		this.payOrgCode = payOrgCode;
	}

	/**
	 * @return the payOrgName
	 */
	public String getPayOrgName() {
		return payOrgName;
	}

	/**
	 * @param payOrgName the payOrgName to set
	 */
	public void setPayOrgName(String payOrgName) {
		this.payOrgName = payOrgName;
	}

	/**
	 * @return the depOrgCode
	 */
	public String getDepOrgCode() {
		return depOrgCode;
	}

	/**
	 * @param depOrgCode the depOrgCode to set
	 */
	public void setDepOrgCode(String depOrgCode) {
		this.depOrgCode = depOrgCode;
	}

	/**
	 * @return the depOrgName
	 */
	public String getDepOrgName() {
		return depOrgName;
	}

	/**
	 * @param depOrgName the depOrgName to set
	 */
	public void setDepOrgName(String depOrgName) {
		this.depOrgName = depOrgName;
	}

	/**
	 * @return the voucherBeginTime
	 */
	public Date getVoucherBeginTime() {
		return voucherBeginTime;
	}

	/**
	 * @param voucherBeginTime the voucherBeginTime to set
	 */
	public void setVoucherBeginTime(Date voucherBeginTime) {
		this.voucherBeginTime = voucherBeginTime;
	}

	/**
	 * @return the voucherEndTime
	 */
	public Date getVoucherEndTime() {
		return voucherEndTime;
	}

	/**
	 * @param voucherEndTime the voucherEndTime to set
	 */
	public void setVoucherEndTime(Date voucherEndTime) {
		this.voucherEndTime = voucherEndTime;
	}

	/**
	 * @return the rdDrPayApply
	 */
	public BigDecimal getRdDrPayApply() {
		return rdDrPayApply;
	}

	/**
	 * @param rdDrPayApply the rdDrPayApply to set
	 */
	public void setRdDrPayApply(BigDecimal rdDrPayApply) {
		this.rdDrPayApply = rdDrPayApply;
	}

	/**
	 * @return the arrivePayApply
	 */
	public BigDecimal getArrivePayApply() {
		return arrivePayApply;
	}

	/**
	 * @param arrivePayApply the arrivePayApply to set
	 */
	public void setArrivePayApply(BigDecimal arrivePayApply) {
		this.arrivePayApply = arrivePayApply;
	}

	/**
	 * @return the deAdvanceApply
	 */
	public BigDecimal getDeAdvanceApply() {
		return deAdvanceApply;
	}

	/**
	 * @param deAdvanceApply the deAdvanceApply to set
	 */
	public void setDeAdvanceApply(BigDecimal deAdvanceApply) {
		this.deAdvanceApply = deAdvanceApply;
	}

	/**
	 * @return the destAdvanceApply
	 */
	public BigDecimal getDestAdvanceApply() {
		return destAdvanceApply;
	}

	/**
	 * @param destAdvanceApply the destAdvanceApply to set
	 */
	public void setDestAdvanceApply(BigDecimal destAdvanceApply) {
		this.destAdvanceApply = destAdvanceApply;
	}

	/**
	 * @return the adPayApply
	 */
	public BigDecimal getAdPayApply() {
		return adPayApply;
	}

	/**
	 * @param adPayApply the adPayApply to set
	 */
	public void setAdPayApply(BigDecimal adPayApply) {
		this.adPayApply = adPayApply;
	}
	
}
