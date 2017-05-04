package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;

/**
 * 还款单Result DTO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
public class BillRepaymentManageResultDto implements Serializable {

	/**
	 * 还款单服务序列号
	 */
	private static final long serialVersionUID = 1180462420580989135L;

	// 总条数
	private int totalCount;

	// 总金额
	private BigDecimal totalAmount = BigDecimal.ZERO;

	// 实际总金额
	private BigDecimal totalTrueAmount = BigDecimal.ZERO;

	// 反核销总金额
	private BigDecimal totalBVerifyAmount = BigDecimal.ZERO;

	// 还款单
	private List<BillRepaymentManageDto> billRepaymentQueryList;

	// 页面总条数
	private Long repayTotalRows;

	/**
	 * 核销明细总条数
	 */
	private Long totalWriteoffCount;

	/**
	 * 核销明细总金额
	 */
	private BigDecimal totalWriteoffAmount;

	/**
	 * 核销明细实体列表
	 */
	private List<BillWriteoffEntity> billWriteoffEntityList = new ArrayList<BillWriteoffEntity>();

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
	 * @return totalTrueAmount
	 */
	public BigDecimal getTotalTrueAmount() {
		return totalTrueAmount;
	}

	/**
	 * @param totalTrueAmount
	 */
	public void setTotalTrueAmount(BigDecimal totalTrueAmount) {
		this.totalTrueAmount = totalTrueAmount;
	}

	/**
	 * @return totalBVerifyAmount
	 */
	public BigDecimal getTotalBVerifyAmount() {
		return totalBVerifyAmount;
	}

	/**
	 * @param totalBVerifyAmount
	 */
	public void setTotalBVerifyAmount(BigDecimal totalBVerifyAmount) {
		this.totalBVerifyAmount = totalBVerifyAmount;
	}

	/**
	 * @return billRepaymentQueryList
	 */
	public List<BillRepaymentManageDto> getBillRepaymentQueryList() {
		return billRepaymentQueryList;
	}

	/**
	 * @param billRepaymentQueryList
	 */
	public void setBillRepaymentQueryList(
			List<BillRepaymentManageDto> billRepaymentQueryList) {
		this.billRepaymentQueryList = billRepaymentQueryList;
	}

	/**
	 * @return repayTotalRows
	 */
	public Long getRepayTotalRows() {
		return repayTotalRows;
	}

	/**
	 * @param repayTotalRows
	 */
	public void setRepayTotalRows(Long repayTotalRows) {
		this.repayTotalRows = repayTotalRows;
	}

	/**
	 * @return totalWriteoffCount
	 */
	public Long getTotalWriteoffCount() {
		return totalWriteoffCount;
	}

	/**
	 * @param totalWriteoffCount
	 */
	public void setTotalWriteoffCount(Long totalWriteoffCount) {
		this.totalWriteoffCount = totalWriteoffCount;
	}

	/**
	 * @return totalWriteoffAmount
	 */
	public BigDecimal getTotalWriteoffAmount() {
		return totalWriteoffAmount;
	}

	/**
	 * @param totalWriteoffAmount
	 */
	public void setTotalWriteoffAmount(BigDecimal totalWriteoffAmount) {
		this.totalWriteoffAmount = totalWriteoffAmount;
	}

	/**
	 * @return billWriteoffEntityList
	 */
	public List<BillWriteoffEntity> getBillWriteoffEntityList() {
		return billWriteoffEntityList;
	}

	/**
	 * @param billWriteoffEntityList
	 */
	public void setBillWriteoffEntityList(
			List<BillWriteoffEntity> billWriteoffEntityList) {
		this.billWriteoffEntityList = billWriteoffEntityList;
	}

}
