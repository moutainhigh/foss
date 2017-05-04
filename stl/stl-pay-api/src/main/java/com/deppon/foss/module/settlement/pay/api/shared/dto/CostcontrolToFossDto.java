package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 返回付款结果dto
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-5 下午4:59:50
 */
public class CostcontrolToFossDto implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4860883145369669968L;

	/**
	 * 付款单集合
	 */
	private String paymentBillNo;

	/**
	 * 审批金额
	 */
	private BigDecimal amount;

	/**
	 * 工作流号
	 */
	private String workflowNo;

	/**
	 * 工作流类型
	 */
	private String workflowtype;

	/**
	 * 付款部门编码
	 */
	private String depno;

	/**
	 * 审批意见
	 */
	private String examineConment;

	/**
	 * 返回审批结果
	 */
	private String examineResult;

	/**
	 * @return paymentBillNo
	 */
	public String getPaymentBillNo() {
		return paymentBillNo;
	}

	/**
	 * @param paymentBillNo
	 */
	public void setPaymentBillNo(String paymentBillNo) {
		this.paymentBillNo = paymentBillNo;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	 * @return workflowtype
	 */
	public String getWorkflowtype() {
		return workflowtype;
	}

	/**
	 * @param workflowtype
	 */
	public void setWorkflowtype(String workflowtype) {
		this.workflowtype = workflowtype;
	}

	/**
	 * @return depno
	 */
	public String getDepno() {
		return depno;
	}

	/**
	 * @param depno
	 */
	public void setDepno(String depno) {
		this.depno = depno;
	}

	/**
	 * @return examineConment
	 */
	public String getExamineConment() {
		return examineConment;
	}

	/**
	 * @param examineConment
	 */
	public void setExamineConment(String examineConment) {
		this.examineConment = examineConment;
	}

	/**
	 * @return examineResult
	 */
	public String getExamineResult() {
		return examineResult;
	}

	/**
	 * @param examineResult
	 */
	public void setExamineResult(String examineResult) {
		this.examineResult = examineResult;
	}

}
