/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 偏线实体
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:15:31
 */
public class ExternalBillEntity extends BaseEntity {

	private static final long serialVersionUID = -8102274328009581810L;

	/**
	 * 外发单号
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String externalBillNo;

	/**
	 * 外发员工号
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String externalUserCode;

	/**
	 * 外发员姓名
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String externalUserName;

	/**
	 * 外发代理费
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private BigDecimal externalAgencyFee;

	/**
	 * 代理送货费
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private BigDecimal deliveryFee;

	/**
	 * 外发成本总额
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private BigDecimal costAmount;

	/**
	 * 实收代理费
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private BigDecimal receiveAgencyFee;

	/**
	 * 实付代理费
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private BigDecimal payAgencyFee;

	/**
	 * 自动核销申请
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String isWriteOff;

	/**
	 * 录入日期
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private Date registerTime;

	/**
	 * 录入人工号
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String registerUserCode;

	/**
	 * 外发部门编号
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String externalOrgCode;

	/**
	 * 外发部门名称
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String externalOrgName;

	/**
	 * 审核状态
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String auditStatus;

	/**
	 * 备注
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String notes;

	/**
	 * 运单号
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String waybillNo;

	/**
	 * 偏线代理编号
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String agentCompanyCode;

	/**
	 * 偏线代理名称
	 * 
	 * @mbggenerated Wed Oct 10 08:40:40 CST 2012
	 */
	private String agentCompanyName;

	/**
	 * 是否中转外发
	 */
	private String transferExternal;
	/**
	 * 交接单号
	 */
	private String handoverNo;
	/**
	 * 币制
	 */
	private String currencyCode;
	/**
	 * 录入人员部门
	 */
	private String registerOrgCode;
	/**
	 * 录入人员对应外场
	 */
	private String transCenterCode;
	
	/**
	 * 是否是迁移数据
	 */
	private String isInit;

	/**
	 * 其他金额
	 */
	private String otherFee;
	/**
	 * 获取 外发单号.
	 * 
	 * @return the 外发单号
	 */
	public String getExternalBillNo() {
		return externalBillNo;
	}

	/**
	 * 设置 外发单号.
	 * 
	 * @param externalBillNo
	 *            the new 外发单号
	 */
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	/**
	 * 获取 外发员工号.
	 * 
	 * @return the 外发员工号
	 */
	public String getExternalUserCode() {
		return externalUserCode;
	}

	/**
	 * 设置 外发员工号.
	 * 
	 * @param externalUserCode
	 *            the new 外发员工号
	 */
	public void setExternalUserCode(String externalUserCode) {
		this.externalUserCode = externalUserCode;
	}

	/**
	 * 获取 外发员姓名.
	 * 
	 * @return the 外发员姓名
	 */
	public String getExternalUserName() {
		return externalUserName;
	}

	/**
	 * 设置 外发员姓名.
	 * 
	 * @param externalUserName
	 *            the new 外发员姓名
	 */
	public void setExternalUserName(String externalUserName) {
		this.externalUserName = externalUserName;
	}

	/**
	 * 获取 外发代理费.
	 * 
	 * @return the 外发代理费
	 */
	public BigDecimal getExternalAgencyFee() {
		return externalAgencyFee;
	}

	/**
	 * 设置 外发代理费.
	 * 
	 * @param externalAgencyFee
	 *            the new 外发代理费
	 */
	public void setExternalAgencyFee(BigDecimal externalAgencyFee) {
		this.externalAgencyFee = externalAgencyFee;
	}

	/**
	 * 获取 代理送货费.
	 * 
	 * @return the 代理送货费
	 */
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	/**
	 * 设置 代理送货费.
	 * 
	 * @param deliveryFee
	 *            the new 代理送货费
	 */
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	/**
	 * 获取 外发成本总额.
	 * 
	 * @return the 外发成本总额
	 */
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	/**
	 * 设置 外发成本总额.
	 * 
	 * @param costAmount
	 *            the new 外发成本总额
	 */
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	/**
	 * 获取 实收代理费.
	 * 
	 * @return the 实收代理费
	 */
	public BigDecimal getReceiveAgencyFee() {
		return receiveAgencyFee;
	}

	/**
	 * 设置 实收代理费.
	 * 
	 * @param receiveAgencyFee
	 *            the new 实收代理费
	 */
	public void setReceiveAgencyFee(BigDecimal receiveAgencyFee) {
		this.receiveAgencyFee = receiveAgencyFee;
	}

	/**
	 * 获取 实付代理费.
	 * 
	 * @return the 实付代理费
	 */
	public BigDecimal getPayAgencyFee() {
		return payAgencyFee;
	}

	/**
	 * 设置 实付代理费.
	 * 
	 * @param payAgencyFee
	 *            the new 实付代理费
	 */
	public void setPayAgencyFee(BigDecimal payAgencyFee) {
		this.payAgencyFee = payAgencyFee;
	}

	/**
	 * 获取 自动核销申请.
	 * 
	 * @return the 自动核销申请
	 */
	public String getIsWriteOff() {
		return isWriteOff;
	}

	/**
	 * 设置 自动核销申请.
	 * 
	 * @param isWriteOff
	 *            the new 自动核销申请
	 */
	public void setIsWriteOff(String isWriteOff) {
		this.isWriteOff = isWriteOff;
	}

	/**
	 * 获取 录入日期.
	 * 
	 * @return the 录入日期
	 */
	@DateFormat
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * 设置 录入日期.
	 * 
	 * @param registerTime
	 *            the new 录入日期
	 */
	@DateFormat
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * 获取 录入人工号.
	 * 
	 * @return the 录入人工号
	 */
	public String getRegisterUserCode() {
		return registerUserCode;
	}

	/**
	 * 设置 录入人工号.
	 * 
	 * @param registerUserCode
	 *            the new 录入人工号
	 */
	public void setRegisterUserCode(String registerUserCode) {
		this.registerUserCode = registerUserCode;
	}

	/**
	 * 获取 外发部门编号.
	 * 
	 * @return the 外发部门编号
	 */
	public String getExternalOrgCode() {
		return externalOrgCode;
	}

	/**
	 * 设置 外发部门编号.
	 * 
	 * @param externalOrgCode
	 *            the new 外发部门编号
	 */
	public void setExternalOrgCode(String externalOrgCode) {
		this.externalOrgCode = externalOrgCode;
	}

	/**
	 * 获取 外发部门名称.
	 * 
	 * @return the 外发部门名称
	 */
	public String getExternalOrgName() {
		return externalOrgName;
	}

	/**
	 * 设置 外发部门名称.
	 * 
	 * @param externalOrgName
	 *            the new 外发部门名称
	 */
	public void setExternalOrgName(String externalOrgName) {
		this.externalOrgName = externalOrgName;
	}

	/**
	 * 获取 审核状态.
	 * 
	 * @return the 审核状态
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * 设置 审核状态.
	 * 
	 * @param auditStatus
	 *            the new 审核状态
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * 获取 备注.
	 * 
	 * @return the 备注
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置 备注.
	 * 
	 * @param notes
	 *            the new 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 获取 运单号.
	 * 
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 * 
	 * @param waybillNo
	 *            the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 偏线代理编号.
	 * 
	 * @return the 偏线代理编号
	 */
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	/**
	 * 设置 偏线代理编号.
	 * 
	 * @param agentCompanyCode
	 *            the new 偏线代理编号
	 */
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	/**
	 * 获取 偏线代理名称.
	 * 
	 * @return the 偏线代理名称
	 */
	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	/**
	 * 设置 偏线代理名称.
	 * 
	 * @param agentCompanyName
	 *            the new 偏线代理名称
	 */
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	/**
	 * 获取 是否中转外发.
	 * 
	 * @return the 是否中转外发
	 */
	public String getTransferExternal() {
		return transferExternal;
	}

	/**
	 * 设置 是否中转外发.
	 * 
	 * @param transferExternal
	 *            the new 是否中转外发
	 */
	public void setTransferExternal(String transferExternal) {
		this.transferExternal = transferExternal;
	}

	/**
	 * 获取 交接单号.
	 * 
	 * @return the 交接单号
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * 设置 交接单号.
	 * 
	 * @param handoverNo
	 *            the new 交接单号
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 币制.
	 * 
	 * @return the 币制
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币制.
	 * 
	 * @param currencyCode
	 *            the new 币制
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRegisterOrgCode() {
		return registerOrgCode;
	}

	public void setRegisterOrgCode(String registerOrgCode) {
		this.registerOrgCode = registerOrgCode;
	}

	public String getTransCenterCode() {
		return transCenterCode;
	}

	public void setTransCenterCode(String transCenterCode) {
		this.transCenterCode = transCenterCode;
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

	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	
}