package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
* @description 快递响应 foss同步卸车差异列表任务接口实体
* @version 1.0
* @author 313352-foss-gouyangyang
* @update 2016年4月29日 下午8:13:38
*/
public class UnloadingDifferenceResponseDto implements Serializable {
	
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
