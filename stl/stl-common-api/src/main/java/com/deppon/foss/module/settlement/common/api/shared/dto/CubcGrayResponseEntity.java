package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2016/12/30
 * @author 353654
 *
 */
public class CubcGrayResponseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<VestBatchResult> vestBatchResult;

	public List<VestBatchResult> getVestBatchResult() {
		return vestBatchResult;
	}

	public void setVestBatchResult(List<VestBatchResult> vestBatchResult) {
		this.vestBatchResult = vestBatchResult;
	}
	
}
