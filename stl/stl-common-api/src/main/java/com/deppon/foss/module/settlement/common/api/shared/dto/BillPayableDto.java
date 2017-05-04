package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * 应付单：编辑和修改使用dto 【1、批量审核审核空运应付单；2、】
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-1 上午8:05:40
 * @since
 * @version
 */
public class BillPayableDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7139816172385094447L;

	/**
	 * Id
	 */
	private String id;

	/**
	 * 应付单号
	 */
	private String payableNo;

	/**
	 * 付款单号
	 */
	private String paymentNo;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 签收日期
	 */
	private Date signDate;

	/**
	 * 生效日期
	 */
	private Date effectiveDate;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 生效状态
	 */
	private String effectiveStatus;

	/**
	 * 生效人名称
	 */
	private String effectiveUserName;

	/**
	 * 生效人编码
	 */
	private String effectiveUserCode;

	/**
	 * 冻结状态
	 */
	private String frozenStatus;

	/**
	 * 冻结时间
	 */
	private Date frozenTime;

	/**
	 * 冻结人名称
	 */
	private String frozenUserName;

	/**
	 * 冻结人编码
	 */
	private String frozenUserCode;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 付款状态
	 */
	private String paymentStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 审核人编码
	 */
	private String auditUserCode;

	/**
	 * 审核人名称
	 */
	private String auditUserName;

	/**
	 * 审核日期
	 */
	private Date auditDate;

	/**
	 * 审批状态
	 */
	private String approveStatus;

	/**
	 * 付款备注
	 */
	private String paymentNotes;// PAYMENT_NOTES

	/**
	 * 付款金额
	 */
	private BigDecimal paymentAmount;// PAYMENT_AMOUNT

	/**
	 * 应付单集合
	 */
	private List<BillPayableEntity> billPayables;

	/**
	 * 限制条件状态（审核时，设置为未审核；反审核时，设置为已审核）
	 */
	private String conApproveStatus;

	/**
	 * 限制支付状态（支付时，设置为未支付；取消支付时，设置为已支付）
	 */
	private String conPayStatus;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 业务日期
	 */
	private Date businessDate;
	
	/**
	 * 来源单据编号
	 */
	private String sourceBillNo;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 应付类型
	 */
	private String payableType;
	
	/**
	 * 产品类型
	 */
	private String productCode;

	/**
	 * 税金
	 */
	private BigDecimal taxAmount;
	
	/**
	 * 税后金额
	 */
	private BigDecimal tax;
	
	/**
	 * 临时租车业务发生日期
	 */
	private Date businessOfDate;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 租车用途
	 */
	private String rentCarUseType;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机联系方式
	 */
	private String driverPhone;
	
	/**
	 * 工作流号
	 */
	private String workFlowNo;
	
	/**
	 * 费用承担部门
	 */
	private String expenseBearCode;

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
	 * @return payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	/**
	 * @param payableNo
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	/**
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
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

	/**
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * @return effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return effectiveStatus
	 */
	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	/**
	 * @param effectiveStatus
	 */
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	/**
	 * @return effectiveUserName
	 */
	public String getEffectiveUserName() {
		return effectiveUserName;
	}

	/**
	 * @param effectiveUserName
	 */
	public void setEffectiveUserName(String effectiveUserName) {
		this.effectiveUserName = effectiveUserName;
	}

	/**
	 * @return effectiveUserCode
	 */
	public String getEffectiveUserCode() {
		return effectiveUserCode;
	}

	/**
	 * @param effectiveUserCode
	 */
	public void setEffectiveUserCode(String effectiveUserCode) {
		this.effectiveUserCode = effectiveUserCode;
	}

	/**
	 * @return frozenStatus
	 */
	public String getFrozenStatus() {
		return frozenStatus;
	}

	/**
	 * @param frozenStatus
	 */
	public void setFrozenStatus(String frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	/**
	 * @return frozenTime
	 */
	public Date getFrozenTime() {
		return frozenTime;
	}

	/**
	 * @param frozenTime
	 */
	public void setFrozenTime(Date frozenTime) {
		this.frozenTime = frozenTime;
	}

	/**
	 * @return frozenUserName
	 */
	public String getFrozenUserName() {
		return frozenUserName;
	}

	/**
	 * @param frozenUserName
	 */
	public void setFrozenUserName(String frozenUserName) {
		this.frozenUserName = frozenUserName;
	}

	/**
	 * @return frozenUserCode
	 */
	public String getFrozenUserCode() {
		return frozenUserCode;
	}

	/**
	 * @param frozenUserCode
	 */
	public void setFrozenUserCode(String frozenUserCode) {
		this.frozenUserCode = frozenUserCode;
	}

	/**
	 * @return payStatus
	 */
	public String getPayStatus() {
		return payStatus;
	}

	/**
	 * @param payStatus
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * @return paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return auditUserCode
	 */
	public String getAuditUserCode() {
		return auditUserCode;
	}

	/**
	 * @param auditUserCode
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	/**
	 * @return auditUserName
	 */
	public String getAuditUserName() {
		return auditUserName;
	}

	/**
	 * @param auditUserName
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	/**
	 * @return auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * @param auditDate
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * @return approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/**
	 * @return paymentNotes
	 */
	public String getPaymentNotes() {
		return paymentNotes;
	}

	/**
	 * @param paymentNotes
	 */
	public void setPaymentNotes(String paymentNotes) {
		this.paymentNotes = paymentNotes;
	}

	/**
	 * @return paymentAmount
	 */
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount
	 */
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return billPayables
	 */
	public List<BillPayableEntity> getBillPayables() {
		return billPayables;
	}

	/**
	 * @param billPayables
	 */
	public void setBillPayables(List<BillPayableEntity> billPayables) {
		this.billPayables = billPayables;
	}

	/**
	 * @return conApproveStatus
	 */
	public String getConApproveStatus() {
		return conApproveStatus;
	}

	/**
	 * @param conApproveStatus
	 */
	public void setConApproveStatus(String conApproveStatus) {
		this.conApproveStatus = conApproveStatus;
	}

	/**
	 * @return conPayStatus
	 */
	public String getConPayStatus() {
		return conPayStatus;
	}

	/**
	 * @param conPayStatus
	 */
	public void setConPayStatus(String conPayStatus) {
		this.conPayStatus = conPayStatus;
	}

	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @GET
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		/*
		 *@get
		 *@ return sourceBillNo
		 */
		return sourceBillNo;
	}

	/**
	 * @SET
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		/*
		 *@set
		 *@this.sourceBillNo = sourceBillNo
		 */
		this.sourceBillNo = sourceBillNo;
	}

	/**
	 * @GET
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		/*
		 *@get
		 *@ return waybillNo
		 */
		return waybillNo;
	}

	/**
	 * @SET
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		/*
		 *@set
		 *@this.waybillNo = waybillNo
		 */
		this.waybillNo = waybillNo;
	}

	/**
	 * @GET
	 * @return payableType
	 */
	public String getPayableType() {
		/*
		 *@get
		 *@ return payableType
		 */
		return payableType;
	}

	/**
	 * @SET
	 * @param payableType
	 */
	public void setPayableType(String payableType) {
		/*
		 *@set
		 *@this.payableType = payableType
		 */
		this.payableType = payableType;
	}

	/**
	 * @GET
	 * @return productCode
	 */
	public String getProductCode() {
		/*
		 *@get
		 *@ return productCode
		 */
		return productCode;
	}

	/**
	 * @SET
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		/*
		 *@set
		 *@this.productCode = productCode
		 */
		this.productCode = productCode;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public Date getBusinessOfDate() {
		return businessOfDate;
	}

	public void setBusinessOfDate(Date businessOfDate) {
		this.businessOfDate = businessOfDate;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getRentCarUseType() {
		return rentCarUseType;
	}

	public void setRentCarUseType(String rentCarUseType) {
		this.rentCarUseType = rentCarUseType;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getWorkFlowNo() {
		return workFlowNo;
	}

	public void setWorkFlowNo(String workFlowNo) {
		this.workFlowNo = workFlowNo;
	}

	public String getExpenseBearCode() {
		return expenseBearCode;
	}

	public void setExpenseBearCode(String expenseBearCode) {
		this.expenseBearCode = expenseBearCode;
	}
	
}
