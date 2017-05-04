package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;

/**
 * 预付单Result DTO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
public class BillAdvanceResultDto implements Serializable {
	/**
	 * 预付单服务序列号
	 */
	private static final long serialVersionUID = 1180462420580989135L;
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 预付单
	 */
	private List<BillAdvancedPaymentEntity> queryBillAdvancedPayList;
	/**
	 * 总条数
	 */
	private int countNum;
	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return queryBillAdvancedPayList
	 */
	public List<BillAdvancedPaymentEntity> getQueryBillAdvancedPayList() {
		return queryBillAdvancedPayList;
	}
	/**
	 * @param queryBillAdvancedPayList
	 */
	public void setQueryBillAdvancedPayList(
			List<BillAdvancedPaymentEntity> queryBillAdvancedPayList) {
		this.queryBillAdvancedPayList = queryBillAdvancedPayList;
	}
	/**
	 * @return countNum
	 */
	public int getCountNum() {
		return countNum;
	}
	/**
	 * @param countNum
	 */
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
}
