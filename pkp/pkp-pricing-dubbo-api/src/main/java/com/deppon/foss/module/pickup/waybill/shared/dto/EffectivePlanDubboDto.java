package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 产品时效详情
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-316759-wangruipeng,date:2017-3-30 下午7:04:50,content:产品时效详情 </p>
 * @author Foss-316759-wangruipeng 
 * @date 2017-3-30 下午7:04:50
 * @since
 * @version
 */
public class EffectivePlanDubboDto extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** EffectivePlanDetailEntity的 id */
	private String id;

	/** 产品CODE */
	private String productCode;

	/** 产品name */
	private String productName;

	/** 始发区域Id */
	private String deptRegionId;

	/** 始发区域code */
	private String deptRegionCode;

	/** 到达区域id */
	private String arrvRegionId;

	/** 到达区域code */
	private String arrvRegionCode;

	/** 到达区域Name */
	private String arrvRegionName;

	/** 承诺最长时间 */
	private Integer maxTime;

	/** 承诺最长时间单位 */
	private String maxTimeUnit;

	/** 承诺最短时间 */
	private Integer minTime;

	/** 承诺最短时间单位 */
	private String minTimeUnit;

	/** 承诺到达营业部时间 */
	private String arriveTime;

	/** 派送承诺需加天数 */
	private Integer addDay;

	/** 派送承诺时间 */
	private String deliveryTime;

	/** 是否有驻地部门 */
	private String hasSalesDept;

	/** 长短途 */
	private String longOrShort;

	/** 开单日期 */
	private Date billDate;

	/** 是否激活 */
	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDeptRegionId() {
		return deptRegionId;
	}

	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	public String getDeptRegionCode() {
		return deptRegionCode;
	}

	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}

	public String getArrvRegionId() {
		return arrvRegionId;
	}

	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}

	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}

	public String getArrvRegionName() {
		return arrvRegionName;
	}

	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	public Integer getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}

	public String getMaxTimeUnit() {
		return maxTimeUnit;
	}

	public void setMaxTimeUnit(String maxTimeUnit) {
		this.maxTimeUnit = maxTimeUnit;
	}

	public Integer getMinTime() {
		return minTime;
	}

	public void setMinTime(Integer minTime) {
		this.minTime = minTime;
	}

	public String getMinTimeUnit() {
		return minTimeUnit;
	}

	public void setMinTimeUnit(String minTimeUnit) {
		this.minTimeUnit = minTimeUnit;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Integer getAddDay() {
		return addDay;
	}

	public void setAddDay(Integer addDay) {
		this.addDay = addDay;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getHasSalesDept() {
		return hasSalesDept;
	}

	public void setHasSalesDept(String hasSalesDept) {
		this.hasSalesDept = hasSalesDept;
	}

	public String getLongOrShort() {
		return longOrShort;
	}

	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
