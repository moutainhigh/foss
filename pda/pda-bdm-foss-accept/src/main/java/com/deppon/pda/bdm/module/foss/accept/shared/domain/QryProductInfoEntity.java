package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file QryProductInfoEntity.java
 * @description 产品信息返回实体
 * @author ChenLiang
 * @created 2012-12-31 下午3:02:42
 * @version 1.0
 */
public class QryProductInfoEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运输性质
	 */
	private String deliveryType;

	/**
	 * 重货价格
	 */
	private double weightGoodPrice;

	/**
	 * 轻货价格
	 */
	private double lightGoodPrice;

	/**
	 * 取货时间
	 */
	private Date pickTime;

	/**
	 * 营运时效
	 */
	private String operationsAging;

	/**
	 * 长短途
	 */

	private String longDistance;

	/**
	 * 运费
	 */
	private double freight;

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public double getWeightGoodPrice() {
		return weightGoodPrice;
	}

	public void setWeightGoodPrice(double weightGoodPrice) {
		this.weightGoodPrice = weightGoodPrice;
	}

	public double getLightGoodPrice() {
		return lightGoodPrice;
	}

	public void setLightGoodPrice(double lightGoodPrice) {
		this.lightGoodPrice = lightGoodPrice;
	}

	public Date getPickTime() {
		return pickTime;
	}

	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}

	public String getOperationsAging() {
		return operationsAging;
	}

	public void setOperationsAging(String operationsAging) {
		this.operationsAging = operationsAging;
	}

	public String getLongDistance() {
		return longDistance;
	}

	public void setLongDistance(String longDistance) {
		this.longDistance = longDistance;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

}
