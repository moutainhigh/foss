package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.List;

public class OverdueSFPaymentApplyResultDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6919125067905074008L;
	// 申请单list
	List<OverdueSFPaymentApplyDto> OverdueSFPaymentApplyDtos;
	// 总数
	int totalCount;

	/**
	 * @return the overdueSFPaymentApplyDtos
	 */
	public List<OverdueSFPaymentApplyDto> getOverdueSFPaymentApplyDtos() {
		return OverdueSFPaymentApplyDtos;
	}

	/**
	 * @param overdueSFPaymentApplyDtos
	 *            the overdueSFPaymentApplyDtos to set
	 */
	public void setOverdueSFPaymentApplyDtos(List<OverdueSFPaymentApplyDto> overdueSFPaymentApplyDtos) {
		OverdueSFPaymentApplyDtos = overdueSFPaymentApplyDtos;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
