package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 现金收入报表明细返回结果：Dto
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-7 下午3:14:05
 * @since
 * @version
 */
public class CashIncomeStatementsResultDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3754080310832221185L;

	/**
	 * 存放现金收入报表明细集合
	 */
	private List<CashIncomeStatementsDto> list = new ArrayList<CashIncomeStatementsDto>();

	/**
	 * 总行数
	 */
	private Long totalCount=0l;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 现金总金额
	 */
	private BigDecimal chTotalAmount;

	/**
	 * 银行卡总金额
	 */
	private BigDecimal cdTotalAmount;

	/**
	 * 电汇总金额
	 */
	private BigDecimal ttTotalAmount;

	/**
	 * 支票总金额
	 */
	private BigDecimal ntTotalAmount;
	
	/**
	 *网上支付总金额
	 */
	private BigDecimal olTotalAmount;

	/**
	 * @return the list
	 */
	public List<CashIncomeStatementsDto> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<CashIncomeStatementsDto> list) {
		this.list = list;
	}

	/**
	 * @return the totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the chTotalAmount
	 */
	public BigDecimal getChTotalAmount() {
		return chTotalAmount;
	}

	/**
	 * @param chTotalAmount
	 *            the chTotalAmount to set
	 */
	public void setChTotalAmount(BigDecimal chTotalAmount) {
		this.chTotalAmount = chTotalAmount;
	}

	/**
	 * @return the cdTotalAmount
	 */
	public BigDecimal getCdTotalAmount() {
		return cdTotalAmount;
	}

	/**
	 * @param cdTotalAmount
	 *            the cdTotalAmount to set
	 */
	public void setCdTotalAmount(BigDecimal cdTotalAmount) {
		this.cdTotalAmount = cdTotalAmount;
	}

	/**
	 * @return the ttTotalAmount
	 */
	public BigDecimal getTtTotalAmount() {
		return ttTotalAmount;
	}

	/**
	 * @param ttTotalAmount
	 *            the ttTotalAmount to set
	 */
	public void setTtTotalAmount(BigDecimal ttTotalAmount) {
		this.ttTotalAmount = ttTotalAmount;
	}

	/**
	 * @return the ntTotalAmount
	 */
	public BigDecimal getNtTotalAmount() {
		return ntTotalAmount;
	}

	/**
	 * @param ntTotalAmount
	 *            the ntTotalAmount to set
	 */
	public void setNtTotalAmount(BigDecimal ntTotalAmount) {
		this.ntTotalAmount = ntTotalAmount;
	}

	/**
	 * @return  the olTotalAmount
	 */
	public BigDecimal getOlTotalAmount() {
		return olTotalAmount;
	}

	/**
	 * @param olTotalAmount the olTotalAmount to set
	 */
	public void setOlTotalAmount(BigDecimal olTotalAmount) {
		this.olTotalAmount = olTotalAmount;
	}


}
