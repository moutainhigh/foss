package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 定义市场活动类
 * 
 * @author 078816
 * @date   2014-03-31
 *
 */
public class MarkActivitiesEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8989368951348771558L;
	
	//活动名称
	private String name;
	
	//活动编码
	private String code;
	
	//活动开始时间
	private Date  activeStartTime;
	
	//活动结束时间
	private Date  activeEndTime;
	
	//活动类别
	private String activityCategory;
	
	//活动类型
	private String activityType;
	
	//是否启用
	private String active;
	
	//发货金额最小值
	private BigDecimal minCargoAmount;
	
	//发货金额最大值
	private BigDecimal maxCargoAmount;
	
	//发货重量最小值
	private BigDecimal minCargoWeight;
	
	//发货重量最大值
	private BigDecimal maxCargoWeight;
	
	//货物体积最小值
	private BigDecimal minCargoVolume;
	
	//货物体积最大值
	private BigDecimal maxCargoVolume;
	
	//crmId
	private BigDecimal activeCrmId;
	
	//是否展会货 
	private String isExhibitionGoods;
	
	public String getIsExhibitionGoods() {
		return isExhibitionGoods;
	}
	public void setIsExhibitionGoods(String isExhibitionGoods) {
		this.isExhibitionGoods = isExhibitionGoods;
	}
	public Date getActiveStartTime() {
		return activeStartTime;
	}
	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}
	public Date getActiveEndTime() {
		return activeEndTime;
	}
	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}
	public String getActivityCategory() {
		return activityCategory;
	}
	public void setActivityCategory(String activityCategory) {
		this.activityCategory = activityCategory;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public BigDecimal getMinCargoAmount() {
		return minCargoAmount;
	}
	public void setMinCargoAmount(BigDecimal minCargoAmount) {
		this.minCargoAmount = minCargoAmount;
	}
	public BigDecimal getMaxCargoAmount() {
		return maxCargoAmount;
	}
	public void setMaxCargoAmount(BigDecimal maxCargoAmount) {
		this.maxCargoAmount = maxCargoAmount;
	}
	public BigDecimal getMinCargoWeight() {
		return minCargoWeight;
	}
	public void setMinCargoWeight(BigDecimal minCargoWeight) {
		this.minCargoWeight = minCargoWeight;
	}
	public BigDecimal getMaxCargoWeight() {
		return maxCargoWeight;
	}
	public void setMaxCargoWeight(BigDecimal maxCargoWeight) {
		this.maxCargoWeight = maxCargoWeight;
	}
	public BigDecimal getMinCargoVolume() {
		return minCargoVolume;
	}
	public void setMinCargoVolume(BigDecimal minCargoVolume) {
		this.minCargoVolume = minCargoVolume;
	}
	public BigDecimal getMaxCargoVolume() {
		return maxCargoVolume;
	}
	public void setMaxCargoVolume(BigDecimal maxCargoVolume) {
		this.maxCargoVolume = maxCargoVolume;
	}
	public BigDecimal getActiveCrmId() {
		return activeCrmId;
	}
	public void setActiveCrmId(BigDecimal activeCrmId) {
		this.activeCrmId = activeCrmId;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
