package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;

/**
 * PDA调用FOSS返回Dto
 * @author 332209
 * @date 2017-03-24 下午 16:36
 */
public class PDADubboResultDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1450968692126059993L;

	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 异常消息
	 */
	private String exceptionMsg;
	
	/**
	 * 月台虚拟编码
	 */
	private String plateformVirtualCode;
	
	public String getPlateformVirtualCode() {
		return plateformVirtualCode;
	}

	public void setPlateformVirtualCode(String plateformVirtualCode) {
		this.plateformVirtualCode = plateformVirtualCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}
