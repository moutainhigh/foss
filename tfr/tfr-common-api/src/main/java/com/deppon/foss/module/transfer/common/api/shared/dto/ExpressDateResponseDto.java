package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
/**
 * @author 313352   gouyangyang
 * 临时租车快递运单(按单号查询)
 */
public class ExpressDateResponseDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 接口返回成功失败信息   0:失败,1:成功
	 */
	private int status;
	
	/**
	 * 错误信息
	 */
	private String exMsg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}
	
	

}
