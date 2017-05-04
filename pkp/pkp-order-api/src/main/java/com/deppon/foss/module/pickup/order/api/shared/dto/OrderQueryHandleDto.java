package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 处理小件订单区域dto.
 *
 * @author wangfei
 */
public class OrderQueryHandleDto implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4228346161199231696L;
	
	/** 登录人所属快递大区下的所有行政区域（市一级单位）. */
	private List<String> expressOrderCityCodes;
	
	/** 登录人所属快递大区下的所有行政区域（区县一级单位）. */
	private List<String> expressOrderCountyCodes;
	
	/** 登录人查询录入的行政区域（区县一级单位，必须在所属对应的快递大区下）. */
	private List<String> expressOrderNewCountyCodes;
	
	/** 快递大区下所有行政区域(以逗号隔开). */
	private String countyCodes;

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * Gets the express order city codes.
	 *
	 * @return the express order city codes
	 */
	public List<String> getExpressOrderCityCodes() {
		return expressOrderCityCodes;
	}


	/**
	 * Sets the express order city codes.
	 *
	 * @param expressOrderCityCodes the new express order city codes
	 */
	public void setExpressOrderCityCodes(List<String> expressOrderCityCodes) {
		this.expressOrderCityCodes = expressOrderCityCodes;
	}


	/**
	 * Gets the express order county codes.
	 *
	 * @return the express order county codes
	 */
	public List<String> getExpressOrderCountyCodes() {
		return expressOrderCountyCodes;
	}


	/**
	 * Sets the express order county codes.
	 *
	 * @param expressOrderCountyCodes the new express order county codes
	 */
	public void setExpressOrderCountyCodes(List<String> expressOrderCountyCodes) {
		this.expressOrderCountyCodes = expressOrderCountyCodes;
	}


	/**
	 * Gets the express order new county codes.
	 *
	 * @return the express order new county codes
	 */
	public List<String> getExpressOrderNewCountyCodes() {
		return expressOrderNewCountyCodes;
	}


	/**
	 * Sets the express order new county codes.
	 *
	 * @param expressOrderNewCountyCodes the new express order new county codes
	 */
	public void setExpressOrderNewCountyCodes(List<String> expressOrderNewCountyCodes) {
		this.expressOrderNewCountyCodes = expressOrderNewCountyCodes;
	}


	/**
	 * Gets the county codes.
	 *
	 * @return the county codes
	 */
	public String getCountyCodes() {
		return countyCodes;
	}


	/**
	 * Sets the county codes.
	 *
	 * @param countyCodes the new county codes
	 */
	public void setCountyCodes(String countyCodes) {
		this.countyCodes = countyCodes;
	}
}
