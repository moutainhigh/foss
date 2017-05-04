package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;

/**
 * 空运预付单Dto
 * 
 * @author foss-pengzhen
 * @date 2012-10-22 下午1:49:05
 * @since
 * @version
 */
public class BillAdvancedPaymentEntityDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2693567952685619576L;

	/**
	 * 已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal verifyAmount;

	/**
	 * 未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 来源单号集合
	 */
	private List<String> advancesNos;

	/**
	 * 来源单号
	 */
	private String advancesNo;

	/**
	 * 是否有效
	 */
	private String active;

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
	 * 付款备注
	 */
	private String paymentNotes;// PAYMENT_NOTES

	/**
	 * 付款金额
	 */
	private BigDecimal paymentAmount;// PAYMENT_AMOUNT

	/**
	 * 预付单集合
	 */
	private List<BillAdvancedPaymentEntity> billAdvancedPayment;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * @return advancesNos
	 */
	public List<String> getAdvancesNos() {
		return advancesNos;
	}

	/**
	 * @param advancesNos
	 */
	public void setAdvancesNos(List<String> advancesNos) {
		this.advancesNos = advancesNos;
	}

	/**
	 * @return advancesNo
	 */
	public String getAdvancesNo() {
		return advancesNo;
	}

	/**
	 * @param advancesNo
	 */
	public void setAdvancesNo(String advancesNo) {
		this.advancesNo = advancesNo;
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
	 * @return billAdvancedPayment
	 */
	public List<BillAdvancedPaymentEntity> getBillAdvancedPayment() {
		return billAdvancedPayment;
	}

	/**
	 * @param billAdvancedPayment
	 */
	public void setBillAdvancedPayment(
			List<BillAdvancedPaymentEntity> billAdvancedPayment) {
		this.billAdvancedPayment = billAdvancedPayment;
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

}
