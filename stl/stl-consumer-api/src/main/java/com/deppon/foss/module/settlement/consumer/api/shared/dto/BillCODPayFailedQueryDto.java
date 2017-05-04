package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.List;

/**
 * 
 * 代收货款付款失败查询DTO
 * 
 * @author dp-zengbinwen
 * @date 2012-10-30 上午9:41:42
 */
public class BillCODPayFailedQueryDto {

	/**
	 * 代收货款状态
	 */
	private List<String> statuses;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 分页大小
	 */
	private int limit;

	/**
	 * 分页开始页号
	 */
	private int offset;

	/**
	 * 应付单是否有效
	 */
	private String payableActive;

	/**
	 * 应付单类型
	 */
	private String payableBillType;

	/**
	 * 退款路径
	 */
	private String refundPath;
	
	/**
	 * 核销类型
	 */
	private String writeoffType;

	/**
	 * @return statuses
	 */
	public List<String> getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses
	 */
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return payableActive
	 */
	public String getPayableActive() {
		return payableActive;
	}

	/**
	 * @param payableActive
	 */
	public void setPayableActive(String payableActive) {
		this.payableActive = payableActive;
	}

	/**
	 * @return payableBillType
	 */
	public String getPayableBillType() {
		return payableBillType;
	}

	/**
	 * @param payableBillType
	 */
	public void setPayableBillType(String payableBillType) {
		this.payableBillType = payableBillType;
	}

	/**
	 * @return refundPath
	 */
	public String getRefundPath() {
		return refundPath;
	}

	/**
	 * @param refundPath
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}

	/**
	 * @return writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}
	
	

}
