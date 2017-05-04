package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableApplicationEntity;

/**
 * 申请作废还款单resultDto
 *
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-20 上午11:11:36
 */
public class BillRepaymentDisableResultDto implements Serializable{

	
	private static final long serialVersionUID = 8189705297111847780L;
	/**
	 * 总条数
	 */
	private int totalCount;
	
	/**
	 * 总核销金额
	 */
	private BigDecimal totalVerifyAmount;
	
	/**
	 * 申请作废
	 */
	private BillRepaymentDisableDto billRepaymentDisableDto;
	
	/**
	 * 申请作废集合
	 */
	private List<RepaymentDisableApplicationEntity> applys;
	
	/**还款单总金额*/
	private BigDecimal totalRepayAmount;
	/**申请作废总金额*/
	private BigDecimal totalApplyAmount;

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the totalVerifyAmount
	 */
	public BigDecimal getTotalVerifyAmount() {
		return totalVerifyAmount;
	}

	/**
	 * @param totalVerifyAmount the totalVerifyAmount to set
	 */
	public void setTotalVerifyAmount(BigDecimal totalVerifyAmount) {
		this.totalVerifyAmount = totalVerifyAmount;
	}

	/**
	 * @return the billRepaymentDisableDto
	 */
	public BillRepaymentDisableDto getBillRepaymentDisableDto() {
		if(billRepaymentDisableDto == null) {
			billRepaymentDisableDto = new BillRepaymentDisableDto();
		}
		return billRepaymentDisableDto;
	}

	/**
	 * @param billRepaymentDisableDto the billRepaymentDisableDto to set
	 */
	public void setBillRepaymentDisableDto(
			BillRepaymentDisableDto billRepaymentDisableDto) {
		this.billRepaymentDisableDto = billRepaymentDisableDto;
	}

	/**
	 * @return the applys
	 */
	public List<RepaymentDisableApplicationEntity> getApplys() {
		return applys;
	}

	/**
	 * @param applys the applys to set
	 */
	public void setApplys(List<RepaymentDisableApplicationEntity> applys) {
		this.applys = applys;
	}

	/**
	 * @return the totalRepayAmount
	 */
	public BigDecimal getTotalRepayAmount() {
		return totalRepayAmount;
	}

	/**
	 * @param totalRepayAmount the totalRepayAmount to set
	 */
	public void setTotalRepayAmount(BigDecimal totalRepayAmount) {
		this.totalRepayAmount = totalRepayAmount;
	}

	/**
	 * @return the totalApplyAmount
	 */
	public BigDecimal getTotalApplyAmount() {
		return totalApplyAmount;
	}

	/**
	 * @param totalApplyAmount the totalApplyAmount to set
	 */
	public void setTotalApplyAmount(BigDecimal totalApplyAmount) {
		this.totalApplyAmount = totalApplyAmount;
	}
}
