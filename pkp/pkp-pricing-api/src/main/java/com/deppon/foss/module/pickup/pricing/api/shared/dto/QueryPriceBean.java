package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryPriceBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4887103488629510213L;
	/**
	 * 价格方案ID
	 */
	private String planId;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 操作时间
	 */
	private Date currentDateTime;
    /**
     * 激活状态
     */
	private String active;
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Date getCurrentDateTime() {
		return currentDateTime;
	}
	public void setCurrentDateTime(Date currentDateTime) {
		this.currentDateTime = currentDateTime;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
}