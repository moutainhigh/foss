package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * TODO(时效方案详细信息实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:wenwuneng,date:2013-11-05 下午4:22:31,content:TODO </p>
 * @author wenwuneng
 * @date 2013-11-05 下午4:22:31
 * @since
 * @version
 */
public class EffectivePlanDetailEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *产品ID
	 */
	private String productId;
	/**
	 * 产品CODE
	 */
	private String productCode;
	/**
	 * 时效方案主信息ID
	 */
	private String effectivePlanId;
	/**
	 * 始发区域ID
	 */
	private String deptRegionId;
	/**
	 * 始发区域CODE
	 */
	private String deptRegionCode;
	/**
	 * 目的区域ID
	 */
	private String arrvRegionId;
	/**
	 *目的区域CODE
	 */
	private String arrvRegionCode;
	/**
	 *承诺最长时间
	 */
	private int maxTime;
	/**
	 *承诺最长时间单位
	 */
	private String maxTimeUnit;
	/**
	 *承诺最短时间
	 */
	private int minTime;
	/**
	 *承诺最短时间单位
	 */
	private String minTimeUnit;
	/**
	 *派送承诺需加天数
	 */
	private int addDay;
	/**
	 *派送承诺时间
	 */
	private String deliveryTime;
	/**
	 *是否有驻地部门
	 */
	private String hasSalesDept;
	/**
	 *长短途
	 */
	private String longOrShort;
	/**
	 *操作标示
	 */
	private String operflag;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getEffectivePlanId() {
		return effectivePlanId;
	}
	public void setEffectivePlanId(String effectivePlanId) {
		this.effectivePlanId = effectivePlanId;
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
	public int getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
	public String getMaxTimeUnit() {
		return maxTimeUnit;
	}
	public void setMaxTimeUnit(String maxTimeUnit) {
		this.maxTimeUnit = maxTimeUnit;
	}
	public int getMinTime() {
		return minTime;
	}
	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}
	public String getMinTimeUnit() {
		return minTimeUnit;
	}
	public void setMin_time_unit(String minTimeUnit) {
		this.minTimeUnit = minTimeUnit;
	}
	public int getAddDay() {
		return addDay;
	}
	public void setAddDay(int addDay) {
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
	public String getOperflag() {
		return operflag;
	}
	public void setOperflag(String operflag) {
		this.operflag = operflag;
	}
	
	
	
	
	
}
