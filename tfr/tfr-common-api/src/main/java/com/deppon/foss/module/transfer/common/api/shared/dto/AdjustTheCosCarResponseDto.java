package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * @author 313352   gouyangyang
 *
 */
public class AdjustTheCosCarResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int  Status;
	
	private String ExMsg;

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getExMsg() {
		return ExMsg;
	}

	public void setExMsg(String exMsg) {
		ExMsg = exMsg;
	}
	
	
	
	

}
