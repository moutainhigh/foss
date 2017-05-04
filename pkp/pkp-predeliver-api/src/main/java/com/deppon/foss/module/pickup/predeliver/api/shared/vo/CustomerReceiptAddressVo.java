package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;

/** 
 * @ClassName: CustomerReceiptAddressVo 
 * @Description: 客户收货地址 Vo
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-14 下午2:31:59 
 *  
 */
public class CustomerReceiptAddressVo extends CustomerReceiptAddressEntity {

	/**
	 * 客户收货地址 Vo 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址省份名称
	 */
	private String receiveProvName;
	
	/**
	 * 收货地址城市名称
	 */
	private String receiveCityName;
	
	/**
	 * 收货名称区域名称
	 */
	private String receiveDistName;

	/**
	 * 获取receiveProvName
	 * @return the receiveProvName
	 */
	public String getReceiveProvName() {
		return receiveProvName;
	}

	/**
	 * 设置receiveProvName
	 * @param receiveProvName 要设置的receiveProvName
	 */
	public void setReceiveProvName(String receiveProvName) {
		this.receiveProvName = receiveProvName;
	}

	/**
	 * 获取receiveCityName
	 * @return the receiveCityName
	 */
	public String getReceiveCityName() {
		return receiveCityName;
	}

	/**
	 * 设置receiveCityName
	 * @param receiveCityName 要设置的receiveCityName
	 */
	public void setReceiveCityName(String receiveCityName) {
		this.receiveCityName = receiveCityName;
	}

	/**
	 * 获取receiveDistName
	 * @return the receiveDistName
	 */
	public String getReceiveDistName() {
		return receiveDistName;
	}

	/**
	 * 设置receiveDistName
	 * @param receiveDistName 要设置的receiveDistName
	 */
	public void setReceiveDistName(String receiveDistName) {
		this.receiveDistName = receiveDistName;
	}
	
}
