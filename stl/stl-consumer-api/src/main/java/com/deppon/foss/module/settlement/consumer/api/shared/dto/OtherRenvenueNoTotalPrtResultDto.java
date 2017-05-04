package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;

/**
 * 小票打印DTO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-29 上午9:20:43
 */
public class OtherRenvenueNoTotalPrtResultDto implements Serializable {

	private static final long serialVersionUID = -2661038262111831167L;

	/**
	 * 存放小票查询结果集
	 */
	private List<OtherRevenueEntity> list;

	/**
	 * 总行数
	 */
	private Long totalCount;

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
	 * 月结总金额
	 */
	private BigDecimal ctTotalAmount;

	/**
	 * 临欠总金额
	 */
	private BigDecimal dtTotalAmount;

	/**
	 * @return list
	 */
	public List<OtherRevenueEntity> getList() {
		return list;
	}

	/**
	 * @param list
	 */
	public void setList(List<OtherRevenueEntity> list) {
		this.list = list;
	}

	/**
	 * @return totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(Long totalCount) {
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
	 * @return chTotalAmount
	 */
	public BigDecimal getChTotalAmount() {
		return chTotalAmount;
	}

	/**
	 * @param chTotalAmount
	 */
	public void setChTotalAmount(BigDecimal chTotalAmount) {
		this.chTotalAmount = chTotalAmount;
	}

	/**
	 * @return cdTotalAmount
	 */
	public BigDecimal getCdTotalAmount() {
		return cdTotalAmount;
	}

	/**
	 * @param cdTotalAmount
	 */
	public void setCdTotalAmount(BigDecimal cdTotalAmount) {
		this.cdTotalAmount = cdTotalAmount;
	}

	/**
	 * @return ctTotalAmount
	 */
	public BigDecimal getCtTotalAmount() {
		return ctTotalAmount;
	}

	/**
	 * @param ctTotalAmount
	 */
	public void setCtTotalAmount(BigDecimal ctTotalAmount) {
		this.ctTotalAmount = ctTotalAmount;
	}

	/**
	 * @return dtTotalAmount
	 */
	public BigDecimal getDtTotalAmount() {
		return dtTotalAmount;
	}

	/**
	 * @param dtTotalAmount
	 */
	public void setDtTotalAmount(BigDecimal dtTotalAmount) {
		this.dtTotalAmount = dtTotalAmount;
	}

}
