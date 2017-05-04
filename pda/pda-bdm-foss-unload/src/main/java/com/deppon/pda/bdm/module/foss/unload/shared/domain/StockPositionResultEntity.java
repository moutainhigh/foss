package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.io.Serializable;

public class StockPositionResultEntity implements Serializable{
    /**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	private String positionResult;

	public String getPositionResult() {
		return positionResult;
	}

	public void setPositionResult(String positionResult) {
		this.positionResult = positionResult;
	}
    
    
    
}
