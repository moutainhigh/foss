package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * No.347069 小票 异常货处理提供给QMS需要的
 * 
 * @author zhang
 * @date 347069
 */
public class AbnormalBillAmountCalculatedDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 2536390382203382233L;
	/**
	 * 编号
	 */
	private String codeNum;
	/**
	 * 金额
	 */
	private BigDecimal claimAmount;
	/**
	 * 提示信息
	 */
	private String failReason;
	/**
	 * 1代表成功
	 */
	private String isSuccess = "0";

	public AbnormalBillAmountCalculatedDto() {
	}

	public AbnormalBillAmountCalculatedDto(String failReason) {
		super();
		this.failReason = failReason;
	}

	public String getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}

	public BigDecimal getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(BigDecimal claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public String toString() {
		return "AbnormalBillAmountCalculatedDto [codeNum=" + codeNum
				+ ", claimAmount=" + claimAmount + ", failReason=" + failReason
				+ ", isSuccess=" + isSuccess + "]";
	}
}
