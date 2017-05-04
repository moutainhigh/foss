package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;

/**
 * 现金收入缴款报表，ResultDto
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午4:37:13
 */
public class BillCashRecPayInResultDto implements Serializable {

	/**
	 * 现金收入报表序列化
	 */
	private static final long serialVersionUID = -1373352910325420759L;

	/**
	 * 现金收入缴款报表列表
	 */
	private List<CashCollectionRptEntity> cashRpList;

	/**
	 * 现金收入报表，总条数
	 */
	private int totalCount=0;

	/**
	 * 应缴款总金额
	 */
	private BigDecimal totalDuesAmount = BigDecimal.ZERO;

	/**
	 * 未交款总金额
	 */
	private BigDecimal totalPaidAmount = BigDecimal.ZERO;

	/**
	 * 已缴款总金额
	 */
	private BigDecimal totalOverdueAmount = BigDecimal.ZERO;

	/**
	 * 累计未交账现金营业款
	 */
	private BigDecimal totalClerksAmount= BigDecimal.ZERO;

	/**
	 * 累计未交账现金预收款
	 */
	private BigDecimal totalPrecollectedAmount = BigDecimal.ZERO;

	/**
	 * @return cashRpList
	 */
	public List<CashCollectionRptEntity> getCashRpList() {
		return cashRpList;
	}

	/**
	 * @param cashRpList
	 */
	public void setCashRpList(List<CashCollectionRptEntity> cashRpList) {
		this.cashRpList = cashRpList;
	}

	/**
	 * @return totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return totalDuesAmount
	 */
	public BigDecimal getTotalDuesAmount() {
		return totalDuesAmount;
	}

	/**
	 * @param totalDuesAmount
	 */
	public void setTotalDuesAmount(BigDecimal totalDuesAmount) {
		this.totalDuesAmount = totalDuesAmount;
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
	 * @return totalPrecollectedAmount
	 */
	public BigDecimal getTotalPrecollectedAmount() {
		return totalPrecollectedAmount;
	}

	/**
	 * @param totalPrecollectedAmount
	 */
	public void setTotalPrecollectedAmount(BigDecimal totalPrecollectedAmount) {
		this.totalPrecollectedAmount = totalPrecollectedAmount;
	}

	
	/**
	 * @return  totalClerksAmount
	 */
	public BigDecimal getTotalClerksAmount() {
		return totalClerksAmount;
	}

	
	/**
	 * @param  totalClerksAmount
	 */
	public void setTotalClerksAmount(BigDecimal totalClerksAmount) {
		this.totalClerksAmount = totalClerksAmount;
	}
}
