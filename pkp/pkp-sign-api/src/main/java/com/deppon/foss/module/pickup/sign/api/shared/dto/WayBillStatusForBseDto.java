/**
 *  initial comments.
 */
/*
 * 
 */
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * WayBillStatusForBseDto
 * @author ibm-wangfei
 * @date Dec 24, 2012 5:00:34 PM
 */
public class WayBillStatusForBseDto implements Serializable {
	private static final long serialVersionUID = -8015560704742156769L;

	/**
	 * 货物状态
	 */
	private String goodsStatus;
	/**
	 * 件数
	 */
	private Integer goodsQty;
	/**
	 * 当前位置
	 */
	private String currentLocation;
	/**
	 * 预计到达下一部门时间
	 */
	private Date preArriveNextOrgTime;
	/**
	 * 下一部门：下一部门的名称
	 */
	private String nextArriveOrgCode;
	/**
	 * 预计派送/提货时间
	 */
	private Date preDeliveryTime;
	/**
	 * 是否可提货
	 */
	private String isDelivery;
	/**
	 * 操作时间
	 */
	private Date operatorDate;
	/**
	 * 流水号集合
	 */
	private List<String> serialNos;

	/**
	 * @return the goodsStatus
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}

	/**
	 * @param goodsStatus the goodsStatus to see
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	/**
	 * @return the currentLocation
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation the currentLocation to see
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the preArriveNextOrgTime
	 */
	public Date getPreArriveNextOrgTime() {
		return preArriveNextOrgTime;
	}

	/**
	 * @param preArriveNextOrgTime the preArriveNextOrgTime to see
	 */
	public void setPreArriveNextOrgTime(Date preArriveNextOrgTime) {
		this.preArriveNextOrgTime = preArriveNextOrgTime;
	}

	/**
	 * @return the nextArriveOrgCode
	 */
	public String getNextArriveOrgCode() {
		return nextArriveOrgCode;
	}

	/**
	 * @param nextArriveOrgCode the nextArriveOrgCode to see
	 */
	public void setNextArriveOrgCode(String nextArriveOrgCode) {
		this.nextArriveOrgCode = nextArriveOrgCode;
	}

	/**
	 * @return the preDeliveryTime
	 */
	public Date getPreDeliveryTime() {
		return preDeliveryTime;
	}

	/**
	 * @param preDeliveryTime the preDeliveryTime to see
	 */
	public void setPreDeliveryTime(Date preDeliveryTime) {
		this.preDeliveryTime = preDeliveryTime;
	}

	/**
	 * @return the isDelivery
	 */
	public String getIsDelivery() {
		return isDelivery;
	}

	/**
	 * @param isDelivery the isDelivery to see
	 */
	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getGoodsQty() {
		return goodsQty == null ? 0 : goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public Date getOperatorDate() {
		return operatorDate;
	}

	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}

	public List<String> getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}
	
}