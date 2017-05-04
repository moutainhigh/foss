package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * 欠款额度返回结果
 * 
 * @author dp-huangxb
 * @date 2012-10-19 下午6:37:27
 */
public class DebitForCUBCDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 49998567534921231L;

	/**
	 * 能否欠款信息 国际化后,使用异常编码
	 */
	private String msg;

	/**
	 * 能否欠款标记
	 */
	private boolean isSuccess=true;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean success) {
		isSuccess = success;
	}


}
