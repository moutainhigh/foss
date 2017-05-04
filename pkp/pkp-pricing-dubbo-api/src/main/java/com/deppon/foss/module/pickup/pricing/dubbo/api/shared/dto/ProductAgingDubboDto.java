package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-24 上午9:29:07,content: </p>
 * @author 316759 
 * @date 2017-2-24 上午9:29:07
 * @since
 * @version
 */
public class ProductAgingDubboDto implements Serializable {
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer promiseSlowTime;
	
    private String unitPromiseOfSlowTime;
    
    private Integer promiseQuickTime;
    
    private String unitOfPromiseQuickTime;
    
    private String promiseArriveTime;
    
    private Integer deliveryAddDays;
    
    private String promiseDeliveryTime;
    
    private Boolean isDepartment;
    
    private String longShort;
    
    private String goodTypeCode;
    
    private String goodTypeName;

	public Integer getPromiseSlowTime() {
		return promiseSlowTime;
	}

	public void setPromiseSlowTime(Integer promiseSlowTime) {
		this.promiseSlowTime = promiseSlowTime;
	}

	public String getUnitPromiseOfSlowTime() {
		return unitPromiseOfSlowTime;
	}

	public void setUnitPromiseOfSlowTime(String unitPromiseOfSlowTime) {
		this.unitPromiseOfSlowTime = unitPromiseOfSlowTime;
	}

	public Integer getPromiseQuickTime() {
		return promiseQuickTime;
	}

	public void setPromiseQuickTime(Integer promiseQuickTime) {
		this.promiseQuickTime = promiseQuickTime;
	}

	public String getUnitOfPromiseQuickTime() {
		return unitOfPromiseQuickTime;
	}

	public void setUnitOfPromiseQuickTime(String unitOfPromiseQuickTime) {
		this.unitOfPromiseQuickTime = unitOfPromiseQuickTime;
	}

	public String getPromiseArriveTime() {
		return promiseArriveTime;
	}

	public void setPromiseArriveTime(String promiseArriveTime) {
		this.promiseArriveTime = promiseArriveTime;
	}

	public Integer getDeliveryAddDays() {
		return deliveryAddDays;
	}

	public void setDeliveryAddDays(Integer deliveryAddDays) {
		this.deliveryAddDays = deliveryAddDays;
	}

	public String getPromiseDeliveryTime() {
		return promiseDeliveryTime;
	}

	public void setPromiseDeliveryTime(String promiseDeliveryTime) {
		this.promiseDeliveryTime = promiseDeliveryTime;
	}

	public Boolean getIsDepartment() {
		return isDepartment;
	}

	public void setIsDepartment(Boolean isDepartment) {
		this.isDepartment = isDepartment;
	}

	public String getLongShort() {
		return longShort;
	}

	public void setLongShort(String longShort) {
		this.longShort = longShort;
	}

	public String getGoodTypeCode() {
		return goodTypeCode;
	}

	public void setGoodTypeCode(String goodTypeCode) {
		this.goodTypeCode = goodTypeCode;
	}

	public String getGoodTypeName() {
		return goodTypeName;
	}

	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
	}

}
