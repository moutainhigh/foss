package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 更新缴款信息DTO
 * @author 045738-foss-maojianqiang
 * @date 2013-1-7 下午3:00:44
 */
public class UpdateCashIncomerptDto implements Serializable {

	/**
	 * 缴款信息DTO流水号
	 */
	private static final long serialVersionUID = 7467232722929184517L;
	
	/**
	 * 缴款流水号
	 */
	private String serialNO;
	
	/**
	 * 付款金额
	 */
	private BigDecimal payAmount;
	
	/**
	 * 缴款部门
	 */
	private String deptCd;
	
	/**
	 * 缴款部门标杆编码 ADD
	 */
	private String unifiedCode;
	
	/**
	 * @return the unifiedCode
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}


	/**
	 * @param unifiedCode the unifiedCode to set
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}


	/**
	 * ISSUE-3360 ADD
	 * 付款方式
	 */
	private String payMethod;
	
	/**
	 * @return serialNO
	 */
	public String getSerialNO() {
		return serialNO;
	}
	
	
	/**
	 * @param serialNO
	 */
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}

	
	/**
	 * @return payAmount
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	
	/**
	 * @param payAmount
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	
	/**
	 * @return deptCd
	 */
	public String getDeptCd() {
		return deptCd;
	}

	
	/**
	 * @param deptCd
	 */
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}


	/**
	 * @return the payMethod
	 */
	public String getPayMethod() {
		return payMethod;
	}


	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	
	
}
