package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class StatementOfAccountQueryResultDto implements Serializable  {
	private static final long serialVersionUID = 1L;
	private long totalCount;
	private BigDecimal unPaidAmount;
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getUnPaidAmount() {
		return unPaidAmount;
	}

	public void setUnPaidAmount(BigDecimal unPaidAmount) {
		this.unPaidAmount = unPaidAmount;
	}
	
}
