package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODBatchEntity;

/**
 * 
 * 代收货款批次号DTO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-12-5 下午7:40:40
 */
public class CODBatchDto extends CODBatchEntity {

	private static final long serialVersionUID = 1929687736031536705L;
	/**
	 * 历史状态
	 */
	private String oldStatus;

	/**
	 * @return oldStatus
	 */
	public String getOldStatus() {
		return oldStatus;
	}

	/**
	 * @param oldStatus
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

}
