package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 汇款查询结果Dto
 * 
 * @author foss-wangxuemin
 * @date Dec 12, 2012 3:52:21 PM
 */
public class RemitTransferQueryResultDto implements Serializable {

	/**
	 * 汇款查询结果Dto
	 */
	private static final long serialVersionUID = -2901291581135972934L;

	/**
	 * 汇款人姓名
	 */
	private String remitName;

	/**
	 * 汇款日期
	 */
	private Date remitDate;

	/**
	 * 认领状态
	 */
	private String claimState;

	/**
	 * 认领部门编码
	 */
	private String claimDeptNo;

	/**
	 * 认领部门名称
	 */
	private String claimDeptName;

	/**
	 * 未核销金额
	 */
	private BigDecimal noCancelAmount;

	/**
	 * @return remitName
	 */
	public String getRemitName() {
		return remitName;
	}

	/**
	 * @param remitName
	 */
	public void setRemitName(String remitName) {
		this.remitName = remitName;
	}

	/**
	 * @return remitDate
	 */
	public Date getRemitDate() {
		return remitDate;
	}

	/**
	 * @param remitDate
	 */
	public void setRemitDate(Date remitDate) {
		this.remitDate = remitDate;
	}

	/**
	 * @return claimState
	 */
	public String getClaimState() {
		return claimState;
	}

	/**
	 * @param claimState
	 */
	public void setClaimState(String claimState) {
		this.claimState = claimState;
	}

	/**
	 * @return claimDeptNo
	 */
	public String getClaimDeptNo() {
		return claimDeptNo;
	}

	/**
	 * @param claimDeptNo
	 */
	public void setClaimDeptNo(String claimDeptNo) {
		this.claimDeptNo = claimDeptNo;
	}

	/**
	 * @return claimDeptName
	 */
	public String getClaimDeptName() {
		return claimDeptName;
	}

	/**
	 * @param claimDeptName
	 */
	public void setClaimDeptName(String claimDeptName) {
		this.claimDeptName = claimDeptName;
	}

	/**
	 * @return noCancelAmount
	 */
	public BigDecimal getNoCancelAmount() {
		return noCancelAmount;
	}

	/**
	 * @param noCancelAmount
	 */
	public void setNoCancelAmount(BigDecimal noCancelAmount) {
		this.noCancelAmount = noCancelAmount;
	}

}
