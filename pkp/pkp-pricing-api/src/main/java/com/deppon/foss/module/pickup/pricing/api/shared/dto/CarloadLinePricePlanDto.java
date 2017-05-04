package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CarloadLinePricePlanDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private String id;
	/**
	 * 始发城市code
	 */
	private String departureCityCode;
	/**
	 * 始发城市名称
	 */
	private String departureCityName;
	/**
	 * 到达城市code
	 */
	private String arrivalCityCode;
	/**
	 * 到达城壕名称
	 */
	private String arrivalCityName;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 类别:按重量(WEIGHT)、按体积(VOLUME)
	 */
	private String type;
	/**
	 * 发票标记 INVOICE_TYPE_01—运输专票11% INVOICE_TYPE_02—非运输专票6%
	 */
	private String invoiceType;
	/**
	 * 上限
	 */
	private BigDecimal upLimit;
	/**
	 * 下限
	 */
	private BigDecimal downLimit;
	/**
	 * 收费标准
	 */
	private BigDecimal chargeStandard;
	/**
	 * 备注
	 */
	private String remark;
	private Date createDate;
	private String createUser;
	private Date modifyDate;
	private String modifyUser;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepartureCityCode() {
		return departureCityCode;
	}
	public void setDepartureCityCode(String departureCityCode) {
		this.departureCityCode = departureCityCode;
	}
	public String getDepartureCityName() {
		return departureCityName;
	}
	public void setDepartureCityName(String departureCityName) {
		this.departureCityName = departureCityName;
	}
	public String getArrivalCityCode() {
		return arrivalCityCode;
	}
	public void setArrivalCityCode(String arrivalCityCode) {
		this.arrivalCityCode = arrivalCityCode;
	}
	public String getArrivalCityName() {
		return arrivalCityName;
	}
	public void setArrivalCityName(String arrivalCityName) {
		this.arrivalCityName = arrivalCityName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public BigDecimal getUpLimit() {
		return upLimit;
	}
	public void setUpLimit(BigDecimal upLimit) {
		this.upLimit = upLimit;
	}
	public BigDecimal getDownLimit() {
		return downLimit;
	}
	public void setDownLimit(BigDecimal downLimit) {
		this.downLimit = downLimit;
	}
	public BigDecimal getChargeStandard() {
		return chargeStandard;
	}
	public void setChargeStandard(BigDecimal chargeStandard) {
		this.chargeStandard = chargeStandard;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
