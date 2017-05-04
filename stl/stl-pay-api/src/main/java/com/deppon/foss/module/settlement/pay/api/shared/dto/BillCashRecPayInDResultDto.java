package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptDEntity;

/**
 * 现金收入缴款报表明细，ResultDto
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午4:37:13
 */
public class BillCashRecPayInDResultDto implements Serializable {

	/**
	 * 现金收入报表序列化
	 */
	private static final long serialVersionUID = 5964149361721513141L;

	/**
	 * 现金收入缴款报表列表
	 */
	private List<CashCollectionRptDEntity> cashRpDList;

	/**
	 * 现金收入报表，总条数
	 */
	private int totalCount;

	/**
	 * 应缴款总金额
	 */
	private BigDecimal totalAmount = BigDecimal.ZERO;

	/**
	 * 总金额已缴款
	 */
	private BigDecimal totalPaidAmount = BigDecimal.ZERO;

	/**
	 * 总金额未交款
	 */
	private BigDecimal totalOverdueAmount = BigDecimal.ZERO;

	/**
	 * @return cashRpDList
	 */
	public List<CashCollectionRptDEntity> getCashRpDList() {
		return cashRpDList;
	}

	/**
	 * @param cashRpDList
	 */
	public void setCashRpDList(List<CashCollectionRptDEntity> cashRpDList) {
		this.cashRpDList = cashRpDList;
	}


	/**
	 * Get
	 * @return totalAmount
	 */
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Set
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return totalPaidAmount
	 */
	public BigDecimal getTotalPaidAmount() {
		return totalPaidAmount;
	}

	/**
	 * @param totalPaidAmount
	 */
	public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	/**
	 * @return totalOverdueAmount
	 */
	public BigDecimal getTotalOverdueAmount() {
		return totalOverdueAmount;
	}

	/**
	 * @param totalOverdueAmount
	 */
	public void setTotalOverdueAmount(BigDecimal totalOverdueAmount) {
		this.totalOverdueAmount = totalOverdueAmount;
	}

	/**
	 * Get
	 * @return totalCount
	 */
	
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * Set
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
