package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;


/**
 * 
 *
 * 中转库存Dto
 * @author 043258-foss-zhaobin
 * @date 2013-4-24 上午10:57:58
 * @since
 * @version
 */
public class SerialNoDto implements Serializable {

	private static final long serialVersionUID = -2731217099657318563L;
	/** 
	 * 流水号
	 */
	private String serialNo;
	
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to see
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
