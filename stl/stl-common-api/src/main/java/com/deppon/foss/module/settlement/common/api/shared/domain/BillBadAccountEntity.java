package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 坏账单(记录公司在一定期限内无法收回的应收账款信息)
 * 
 * @author zhangjiheng
 * @date 2012-10-11 下午5:05:55
 */
public class BillBadAccountEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 2080176938387718146L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 坏账单编号
	 */
	private String badDebatBillNo;

	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;

	/**
	 * 申请部门名称
	 */
	private String applyOrgName;

	/**
	 * 申请人工号
	 */
	private String applyUserCode;

	/**
	 * 申请人名称
	 */
	private String applyUserName;

	/**
	 * 差错处理编号
	 */
	private String errorHandingNo;

	/**
	 * 申请事由
	 */
	private String applyReason;

	/**
	 * 应收单号
	 */
	private String receivableNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 出发部门名称
	 */
	private String origOrgName;

	/**
	 * 收货部门编码
	 */
	private String destOrgCode;

	/**
	 * 收货部门名称
	 */
	private String destOrgName;

	/**
	 * 发货客户编码
	 */
	private String fcustomerCode;

	/**
	 * 发货客户名称
	 */
	private String fcustomerName;

	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerPhone;

	/**
	 * 目的站
	 */
	private String arrvRegionCode;

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerName;

	/**
	 * 总运费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal totalFee;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 收款方式
	 */
	private String paymentType;

	/**
	 * 坏账金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal badAmount;

	/**
	 * 审核状态
	 */
	private String auditStatus;

	/**
	 * 工作流号
	 */
	private String workflowNo;

	/**
	 * 创建部门编码
	 */
	private String createOrgCode;

	/**
	 * 创建部门名称
	 */
	private String createOrgName;

	/**
	 * 创建人工号
	 */
	private String createUserCode;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 是否核销
	 */
	private String verifyStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 冲账方式
	 */
	private String billType;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return badDebatBillNo
	 */
	public String getBadDebatBillNo() {
		return badDebatBillNo;
	}

	/**
	 * @param badDebatBillNo
	 */
	public void setBadDebatBillNo(String badDebatBillNo) {
		this.badDebatBillNo = badDebatBillNo;
	}

	/**
	 * @return applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * @param applyOrgCode
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * @return applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}

	/**
	 * @param applyOrgName
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	/**
	 * @return applyUserCode
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}

	/**
	 * @param applyUserCode
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}

	/**
	 * @return applyUserName
	 */
	public String getApplyUserName() {
		return applyUserName;
	}

	/**
	 * @param applyUserName
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	/**
	 * @return errorHandingNo
	 */
	public String getErrorHandingNo() {
		return errorHandingNo;
	}

	/**
	 * @param errorHandingNo
	 */
	public void setErrorHandingNo(String errorHandingNo) {
		this.errorHandingNo = errorHandingNo;
	}

	/**
	 * @return applyReason
	 */
	public String getApplyReason() {
		return applyReason;
	}

	/**
	 * @param applyReason
	 */
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	/**
	 * @return receivableNo
	 */
	public String getReceivableNo() {
		return receivableNo;
	}

	/**
	 * @param receivableNo
	 */
	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return fcustomerCode
	 */
	public String getFcustomerCode() {
		return fcustomerCode;
	}

	/**
	 * @param fcustomerCode
	 */
	public void setFcustomerCode(String fcustomerCode) {
		this.fcustomerCode = fcustomerCode;
	}

	/**
	 * @return fcustomerName
	 */
	public String getFcustomerName() {
		return fcustomerName;
	}

	/**
	 * @param fcustomerName
	 */
	public void setFcustomerName(String fcustomerName) {
		this.fcustomerName = fcustomerName;
	}

	/**
	 * @return deliveryCustomerPhone
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * @param deliveryCustomerPhone
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * @return arrvRegionCode
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	/**
	 * @param arrvRegionCode
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}

	/**
	 * @return receiveCustomerCode
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * @param receiveCustomerCode
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * @return receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return totalFee
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	 * @return badAmount
	 */
	public BigDecimal getBadAmount() {
		return badAmount;
	}

	/**
	 * @param badAmount
	 */
	public void setBadAmount(BigDecimal badAmount) {
		this.badAmount = badAmount;
	}

	/**
	 * @return auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return workflowNo
	 */
	public String getWorkflowNo() {
		return workflowNo;
	}

	/**
	 * @param workflowNo
	 */
	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * @param createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return verifyStatus
	 */
	public String getVerifyStatus() {
		return verifyStatus;
	}

	/**
	 * @param verifyStatus
	 */
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

}
