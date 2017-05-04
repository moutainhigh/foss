package com.deppon.foss.module.transfer.unload.api.shared.dto;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;

public class QuerySerialListByTskNumDto extends OrderSerialNoDetailEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long serialcounts;

	public Long getSerialcounts() {
		return serialcounts;
	}

	public void setSerialcounts(Long serialcounts) {
		this.serialcounts = serialcounts;
	}

	
}
