package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 应收单编辑dto 继承 应收单实体
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-10-31 上午8:02:34
 * @since
 * @version
 */

public class BillReceivableDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 179165103502598325L;

	/**
	 * 应收单集合
	 */
	private List<BillReceivableEntity> billReceivables;

	/**
	 * 限制条件状态（审核时，设置为未审核；反审核时，设置为已审核）
	 */
	private String conApproveStatus;

	/**
	 * 审批状态
	 */
	private String approveStatus;

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
	 * 解锁时间
	 */
	private Date unlockDateTime;

	/**
	 * 锁定客户编码
	 */
	private String lockCustomerCode;

	/**
	 * 锁定客户名称
	 */
	private String lockCustomerName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 单据类型
	 */
	private String billType;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 标记/反标记
	 */
	private String stamp;
	/**
	 * @return billReceivables
	 */
	public List<BillReceivableEntity> getBillReceivables() {
		return billReceivables;
	}

	/**
	 * @param billReceivables
	 */
	public void setBillReceivables(List<BillReceivableEntity> billReceivables) {
		this.billReceivables = billReceivables;
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
	 * @return unlockDateTime
	 */
	public Date getUnlockDateTime() {
		return unlockDateTime;
	}

	/**
	 * @param unlockDateTime
	 */
	public void setUnlockDateTime(Date unlockDateTime) {
		this.unlockDateTime = unlockDateTime;
	}

	/**
	 * @return lockCustomerCode
	 */
	public String getLockCustomerCode() {
		return lockCustomerCode;
	}

	/**
	 * @param lockCustomerCode
	 */
	public void setLockCustomerCode(String lockCustomerCode) {
		this.lockCustomerCode = lockCustomerCode;
	}

	/**
	 * @return lockCustomerName
	 */
	public String getLockCustomerName() {
		return lockCustomerName;
	}

	/**
	 * @param lockCustomerName
	 */
	public void setLockCustomerName(String lockCustomerName) {
		this.lockCustomerName = lockCustomerName;
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
	 * @GET
	 * @return stamp
	 */
	public String getStamp() {
		/*
		 *@get
		 *@ return stamp
		 */
		return stamp;
	}

	/**
	 * @SET
	 * @param stamp
	 */
	public void setStamp(String stamp) {
		/*
		 *@set
		 *@this.stamp = stamp
		 */
		this.stamp = stamp;
	}

}
