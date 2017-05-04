package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class OuterEffectivePlanDto implements Serializable {
	private static final long serialVersionUID = 3270657552674154464L;
	/**
	 * ID
	 */
	private String outerEffectivePlanId;
	/**
	 * 偏线目的站
	 */
	private String partialLineCode;
	/**
	 * 目的站名称
	 */
	private String partialLineName;
	/**
	 * 外场Code
	 */
	private String outFieldCode;
	/**
	 * 外场名称
	 */
	private String outFieldName;
	/**
	 * 产品类型
	 */
	private String productCode;
	/**
	 * 激活状态
	 */
	private String active;
	/**
	 * 偏线时效方案名称
	 */
	private String name;
	/**
	 * 修改日期
	 */
	private Date modifyDate;
	/**
	 * 修改人
	 */
	private String modifyUser;
	/**
	 * 承诺最大时间或日期
	 */
	private Integer maxTime;
	/**
	 * 时间单位DAY/HOUR
	 */
	private String minTimeUnit;
	/**
	 * 承诺最小时间或日期
	 */
	private Integer minTime;
	/**
	 * 时间单位DAY/HOUR
	 */
	private String maxTimeUnit;
	/**
	 * 到达偏线承诺时间点
	 */
	private String arriveOuterBranchTime;
	/**
	 * 承诺派送时间
	 */
	private String deliveryTime;
	/**
	 * 派送增加天数
	 */
	private String addDay;	
	/**
	 * 修改人组织
	 */
	private String modifyOrgCode;
	/**
	 * 省份name
	 */
	private String provName;
	/**
	 * 省份code
	 */
	private String provCode;
	/**
	 * 城市name
	 */
	private String cityName;
	/**
	 * 城市code
	 */
	private String CityCode;
	/**
	 * 区县code
	 */
	private String countyCode;
	/**
	 * 区县name
	 */
	private String countyName;
	/**
	 * 激活开始时间
	 */
	private Date beginTime;
	/**
	 * 激活结束时间
	 */
	private Date endTime;
	/**
	 * 版本号
	 */
	private String versionNo;

	//get、set方法
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}	
	public Integer getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}
	public String getMinTimeUnit() {
		return minTimeUnit;
	}
	public void setMinTimeUnit(String minTimeUnit) {
		this.minTimeUnit = minTimeUnit;
	}
	public Integer getMinTime() {
		return minTime;
	}
	public void setMinTime(Integer minTime) {
		this.minTime = minTime;
	}
	public String getMaxTimeUnit() {
		return maxTimeUnit;
	}
	public void setMaxTimeUnit(String maxTimeUnit) {
		this.maxTimeUnit = maxTimeUnit;
	}
	public String getArriveOuterBranchTime() {
		return arriveOuterBranchTime;
	}
	public void setArriveOuterBranchTime(String arriveOuterBranchTime) {
		this.arriveOuterBranchTime = arriveOuterBranchTime;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getAddDay() {
		return addDay;
	}
	public void setAddDay(String addDay) {
		this.addDay = addDay;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartialLineName() {
		return partialLineName;
	}
	public void setPartialLineName(String partialLineName) {
		this.partialLineName = partialLineName;
	}
	public String getOutFieldName() {
		return outFieldName;
	}
	public void setOutFieldName(String outFieldName) {
		this.outFieldName = outFieldName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return CityCode;
	}

	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getOuterEffectivePlanId() {
		return outerEffectivePlanId;
	}

	public void setOuterEffectivePlanId(String outerEffectivePlanId) {
		this.outerEffectivePlanId = outerEffectivePlanId;
	}

	public String getPartialLineCode() {
		return partialLineCode;
	}

	public void setPartialLineCode(String partialLineCode) {
		this.partialLineCode = partialLineCode;
	}

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
