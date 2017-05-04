package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyResultDto;

public class OverdueSFPaymentApplyVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3543394426873930447L;
	private OverdueSFPaymentApplyQueryDto overdueSFPaymentApplyQueryDto;
	private OverdueSFPaymentApplyResultDto overdueSFPaymentApplyResultDto;

	/**
	 * @return the overdueSFPaymentApplyQueryDto
	 */
	public OverdueSFPaymentApplyQueryDto getOverdueSFPaymentApplyQueryDto() {
		return overdueSFPaymentApplyQueryDto;
	}

	/**
	 * @param overdueSFPaymentApplyQueryDto
	 *            the overdueSFPaymentApplyQueryDto to set
	 */
	public void setOverdueSFPaymentApplyQueryDto(OverdueSFPaymentApplyQueryDto overdueSFPaymentApplyQueryDto) {
		this.overdueSFPaymentApplyQueryDto = overdueSFPaymentApplyQueryDto;
	}

	/**
	 * @return the overdueSFPaymentApplyResultDto
	 */
	public OverdueSFPaymentApplyResultDto getOverdueSFPaymentApplyResultDto() {
		return overdueSFPaymentApplyResultDto;
	}

	/**
	 * @param overdueSFPaymentApplyResultDto
	 *            the overdueSFPaymentApplyResultDto to set
	 */
	public void setOverdueSFPaymentApplyResultDto(OverdueSFPaymentApplyResultDto overdueSFPaymentApplyResultDto) {
		this.overdueSFPaymentApplyResultDto = overdueSFPaymentApplyResultDto;
	}

}
