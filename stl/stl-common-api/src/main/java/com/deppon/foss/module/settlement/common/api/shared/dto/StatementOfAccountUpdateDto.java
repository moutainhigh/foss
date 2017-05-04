package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 1、根据制作日期、客户信息、对账单号、运单号查询对账单DTO 2、在进入对账单的应收单、应付单、预收单、预付单在对账单之外参与核销、红冲、反核销操作时，
 * 更改对账单及对账单明细信息DTO
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-6 下午2:50:54
 */
public class StatementOfAccountUpdateDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 应收单集合
	 */
	private List<BillReceivableEntity> receivableEntityList;

	/**
	 * 应付单集合
	 */
	private List<BillPayableEntity> payableEntityList;

	/**
	 * 预收单集合
	 */
	private List<BillDepositReceivedEntity> depositReceivedEntityList;

	/**
	 * 预付单集合
	 */
	private List<BillAdvancedPaymentEntity> advancePaymentEntityList;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 账期开始日期
	 */
	private Date periodBeginDate;

	/**
	 * 账期结束日期
	 */
	private Date periodEndDate;

	/**
	 * 对账单确认状态
	 */
	private String confirmStatus;

	/**
	 * 明细单据编号或运单号
	 */
	private String[] billDetailNos;

	/**
	 * 登录用户所属部门编码
	 */
	private String orgCode;

	/**
	 * 结清状态
	 */
	private String settleStatus;

	/**
	 * 设置对账单确认常量,对账单已确认和未确认
	 */
	private String statementSettleStatus;

	private String statementUnSettleStatus;
	
	/**
	 * 是否申请发票
	 */
	private String applyInvoice;
	
	/**
	 * 发票申请状态
	 */
	private String invoiceStatus;
	/**
	 * 可查询部门列表
	 */
	private List<String> orgCodeList;
	
	/**
	 * 当前登录人编码
	 */
	private String empCode;

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return receivableEntityList
	 */
	public List<BillReceivableEntity> getReceivableEntityList() {
		return receivableEntityList;
	}

	/**
	 * @param receivableEntityList
	 */
	public void setReceivableEntityList(
			List<BillReceivableEntity> receivableEntityList) {
		this.receivableEntityList = receivableEntityList;
	}

	/**
	 * @return payableEntityList
	 */
	public List<BillPayableEntity> getPayableEntityList() {
		return payableEntityList;
	}

	/**
	 * @param payableEntityList
	 */
	public void setPayableEntityList(List<BillPayableEntity> payableEntityList) {
		this.payableEntityList = payableEntityList;
	}

	/**
	 * @return depositReceivedEntityList
	 */
	public List<BillDepositReceivedEntity> getDepositReceivedEntityList() {
		return depositReceivedEntityList;
	}

	/**
	 * @param depositReceivedEntityList
	 */
	public void setDepositReceivedEntityList(
			List<BillDepositReceivedEntity> depositReceivedEntityList) {
		this.depositReceivedEntityList = depositReceivedEntityList;
	}

	/**
	 * @return advancePaymentEntityList
	 */
	public List<BillAdvancedPaymentEntity> getAdvancePaymentEntityList() {
		return advancePaymentEntityList;
	}

	/**
	 * @param advancePaymentEntityList
	 */
	public void setAdvancePaymentEntityList(
			List<BillAdvancedPaymentEntity> advancePaymentEntityList) {
		this.advancePaymentEntityList = advancePaymentEntityList;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return periodBeginDate
	 */
	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	/**
	 * @param periodBeginDate
	 */
	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	/**
	 * @return periodEndDate
	 */
	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	/**
	 * @param periodEndDate
	 */
	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	/**
	 * @return confirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}

	/**
	 * @param confirmStatus
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	/**
	 * @return billDetailNos
	 */
	public String[] getBillDetailNos() {
		return billDetailNos;
	}

	/**
	 * @param billDetailNos
	 */
	public void setBillDetailNos(String[] billDetailNos) {
		this.billDetailNos = billDetailNos;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return settleStatus
	 */
	public String getSettleStatus() {
		return settleStatus;
	}

	/**
	 * @param settleStatus
	 */
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	/**
	 * @return statementSettleStatus
	 */
	public String getStatementSettleStatus() {
		return statementSettleStatus;
	}

	/**
	 * @param statementSettleStatus
	 */
	public void setStatementSettleStatus(String statementSettleStatus) {
		this.statementSettleStatus = statementSettleStatus;
	}

	/**
	 * @return statementUnSettleStatus
	 */
	public String getStatementUnSettleStatus() {
		return statementUnSettleStatus;
	}

	/**
	 * @param statementUnSettleStatus
	 */
	public void setStatementUnSettleStatus(String statementUnSettleStatus) {
		this.statementUnSettleStatus = statementUnSettleStatus;
	}

	public String getApplyInvoice() {
		return applyInvoice;
	}

	public void setApplyInvoice(String applyInvoice) {
		this.applyInvoice = applyInvoice;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

}
