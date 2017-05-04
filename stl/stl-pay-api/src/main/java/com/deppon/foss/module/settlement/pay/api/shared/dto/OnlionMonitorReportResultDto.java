package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 返回结果dto
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-26 下午8:55:12
 */
public class OnlionMonitorReportResultDto implements Serializable {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -4155912530663294114L;

	/**
	 * 总条数
	 */
	private int totalCount;

	/**
	 * 应收金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 在线支付金额
	 */
	private BigDecimal onlionPayTotalAmount;

	/**
	 * 查询结果列表
	 */
	private List<OnlionMonitorReportListDto> list;

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
	 * @return list
	 */
	public List<OnlionMonitorReportListDto> getList() {
		return list;
	}

	/**
	 * @param list
	 */
	public void setList(List<OnlionMonitorReportListDto> list) {
		this.list = list;
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
	 * @return onlionPayTotalAmount
	 */
	public BigDecimal getOnlionPayTotalAmount() {
		return onlionPayTotalAmount;
	}

	/**
	 * @param onlionPayTotalAmount
	 */
	public void setOnlionPayTotalAmount(BigDecimal onlionPayTotalAmount) {
		this.onlionPayTotalAmount = onlionPayTotalAmount;
	}

}
