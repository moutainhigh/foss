package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 用于同步WDGU的请求 的派送自提entity
 * 
 * @author Administrator
 * 
 */
public class PickAreaAndDeliveryAreaEntity extends BaseEntity {
	/**
	* 	 
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 部门编码
	 */
	private String code;
	/**
	 * 可自提
	 */
	private String pickupSelf;
	/**
	 * 可派送
	 */
	private String delivery;
	/**
	 * 单件重量上限
	 */
	private Integer singlePieceLimitkg;

	/**
	 * 单票重量上限
	 */
	private Integer singleBillLimitkg;

	/**
	 * 单件体积上限
	 */
	private Integer singlePieceLimitvol;

	/**
	 * 单票体积上限
	 */
	private Integer singleBillLimitvol;

	/**
	 * 自提区域描述
	 */
	private String pickupAreaDesc;

	/**
	 * 派送区域描述
	 */
	private String deliveryAreaDesc;
	/**
	 * 提货网点编码，为4位数字，当为到达部门时必填
	 */
	private String stationNumber;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPickupSelf() {
		return pickupSelf;
	}

	public void setPickupSelf(String pickupSelf) {
		this.pickupSelf = pickupSelf;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public Integer getSinglePieceLimitkg() {
		return singlePieceLimitkg;
	}

	public void setSinglePieceLimitkg(Integer singlePieceLimitkg) {
		this.singlePieceLimitkg = singlePieceLimitkg;
	}

	public Integer getSingleBillLimitkg() {
		return singleBillLimitkg;
	}

	public void setSingleBillLimitkg(Integer singleBillLimitkg) {
		this.singleBillLimitkg = singleBillLimitkg;
	}

	public Integer getSinglePieceLimitvol() {
		return singlePieceLimitvol;
	}

	public void setSinglePieceLimitvol(Integer singlePieceLimitvol) {
		this.singlePieceLimitvol = singlePieceLimitvol;
	}

	public Integer getSingleBillLimitvol() {
		return singleBillLimitvol;
	}

	public void setSingleBillLimitvol(Integer singleBillLimitvol) {
		this.singleBillLimitvol = singleBillLimitvol;
	}

	public String getPickupAreaDesc() {
		return pickupAreaDesc;
	}

	public void setPickupAreaDesc(String pickupAreaDesc) {
		this.pickupAreaDesc = pickupAreaDesc;
	}

	public String getDeliveryAreaDesc() {
		return deliveryAreaDesc;
	}

	public void setDeliveryAreaDesc(String deliveryAreaDesc) {
		this.deliveryAreaDesc = deliveryAreaDesc;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

}
