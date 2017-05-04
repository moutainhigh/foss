package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class GGReturnTypeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7078288843542114739L;
	/**
	 * 渠道单号
	 */
	private String channelNumber;
	
	/**
	 * 返回类型 Y N
	 */
	private String isReturn;

	/**
	 * 
	 * @return
	 */
	public String getIsReturn() {
		return isReturn;
	}

	/**
	 * 
	 * @param isReturn
	 */
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

}
