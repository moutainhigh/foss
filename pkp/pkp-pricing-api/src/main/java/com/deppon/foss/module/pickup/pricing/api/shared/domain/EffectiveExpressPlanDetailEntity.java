/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/EffectivePlanDetailEntity.java
 * 
 * FILE NAME        	: EffectivePlanDetailEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 时效明细实体
 * @author DP-Foss-YueHongJie
 * @date 2012-11-10 上午11:35:28
 */
public class EffectiveExpressPlanDetailEntity extends BaseEntity {

	/**
     * 
     */
    private static final long serialVersionUID = 5953291766089262144L;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 产品编码
     */
    private String productCode; 
    /**
     * 时效主方案ID
     */
    private String effectivePlanId;
    /**
     * 始发区域ID
     */
    private String deptRegionId;
    /**
     * 始发区域编码
     */
    private String deptRegionCode;
    /**
     * 目的地区域ID
     */
    private String arrvRegionId;
    /**
     * 目的地区域编码
     */
    private String arrvRegionCode;
    /**
     * 目的地区域名称
     */
    private String arrvRegionName;
    /**
     * 承诺最长时间
     */
    private Integer maxTime;
    /**
     * 承诺最长时间单位
     */
    private String maxTimeUnit;
    /**
     * 承诺最短时间
     */
    private Integer minTime;
    /**
     * 承诺最短时间单位
     */
    private String minTimeUnit;
    /**
     * 承诺到达营业部时间
     */
    private String arriveTime;
    /**
     * 派送承诺需加天数
     */
    private Integer addDay;
    /**
     * 派送承诺时间
     */
    private String deliveryTime;
    /**
     * 是否有驻地部门
     */
    private String hasSalesDept;
    /**
     * 长短途
     */
    private String longOrShort;
    /**
     * 备注
     */
    private String description;
    
    /**
     * 修改备注
     */
    private String upDesTemp;
    
    /**
     * 数据版本
     */
    private Long versionNo;
    /**
     * 创建组织
     */
    private String createOrgCode;
    /**
     * 更新组织
     */
    private String modifyOrgCode;
    /**
     * 数据状态
     */
    private String active;
    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 获取 产品名称.
     *
     * @return the 产品名称
     */
    public String getProductName() {
        return productName;
    }

    
    /**
     * 获取 目的地区域名称.
     *
     * @return the 目的地区域名称
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }

    
    /**
     * 设置 目的地区域名称.
     *
     * @param arrvRegionName the new 目的地区域名称
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }

    /**
     * 设置 产品名称.
     *
     * @param productName the new 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取 产品ID.
     *
     * @return the 产品ID
     */
    public String getProductId() {
        return productId;
    }

    
    /**
     * 设置 产品ID.
     *
     * @param productId the new 产品ID
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    
    /**
     * 获取 产品编码.
     *
     * @return the 产品编码
     */
    public String getProductCode() {
        return productCode;
    }

    
    /**
     * 设置 产品编码.
     *
     * @param productCode the new 产品编码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    
    /**
     * 获取 时效主方案ID.
     *
     * @return the 时效主方案ID
     */
    public String getEffectivePlanId() {
        return effectivePlanId;
    }

    
    /**
     * 设置 时效主方案ID.
     *
     * @param effectivePlanId the new 时效主方案ID
     */
    public void setEffectivePlanId(String effectivePlanId) {
        this.effectivePlanId = effectivePlanId;
    }

    /**
     * 获取 始发区域ID.
     *
     * @return the 始发区域ID
     */
    public String getDeptRegionId() {
        return deptRegionId;
    }

    /**
     * 设置 始发区域ID.
     *
     * @param deptRegionId the new 始发区域ID
     */
    public void setDeptRegionId(String deptRegionId) {
        this.deptRegionId = deptRegionId;
    }

    /**
     * 获取 始发区域编码.
     *
     * @return the 始发区域编码
     */
    public String getDeptRegionCode() {
        return deptRegionCode;
    }

    /**
     * 设置 始发区域编码.
     *
     * @param deptRegionCode the new 始发区域编码
     */
    public void setDeptRegionCode(String deptRegionCode) {
        this.deptRegionCode = deptRegionCode;
    }

    /**
     * 获取 目的地区域ID.
     *
     * @return the 目的地区域ID
     */
    public String getArrvRegionId() {
        return arrvRegionId;
    }

    /**
     * 设置 目的地区域ID.
     *
     * @param arrvRegionId the new 目的地区域ID
     */
    public void setArrvRegionId(String arrvRegionId) {
        this.arrvRegionId = arrvRegionId;
    }

    /**
     * 获取 目的地区域编码.
     *
     * @return the 目的地区域编码
     */
    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    /**
     * 设置 目的地区域编码.
     *
     * @param arrvRegionCode the new 目的地区域编码
     */
    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode;
    }

    /**
     * 获取 承诺最长时间.
     *
     * @return the 承诺最长时间
     */
    public Integer getMaxTime() {
        return maxTime;
    }

    /**
     * 设置 承诺最长时间.
     *
     * @param maxTime the new 承诺最长时间
     */
    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    /**
     * 获取 承诺最长时间单位.
     *
     * @return the 承诺最长时间单位
     */
    public String getMaxTimeUnit() {
        return maxTimeUnit;
    }

    /**
     * 设置 承诺最长时间单位.
     *
     * @param maxTimeUnit the new 承诺最长时间单位
     */
    public void setMaxTimeUnit(String maxTimeUnit) {
        this.maxTimeUnit = maxTimeUnit;
    }

    /**
     * 获取 承诺最短时间.
     *
     * @return the 承诺最短时间
     */
    public Integer getMinTime() {
        return minTime;
    }

    /**
     * 设置 承诺最短时间.
     *
     * @param minTime the new 承诺最短时间
     */
    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    /**
     * 获取 承诺最短时间单位.
     *
     * @return the 承诺最短时间单位
     */
    public String getMinTimeUnit() {
        return minTimeUnit;
    }

    /**
     * 设置 承诺最短时间单位.
     *
     * @param minTimeUnit the new 承诺最短时间单位
     */
    public void setMinTimeUnit(String minTimeUnit) {
        this.minTimeUnit = minTimeUnit;
    }

    /**
     * 获取 承诺到达营业部时间.
     *
     * @return the 承诺到达营业部时间
     */
    public String getArriveTime() {
        return arriveTime;
    }

    /**
     * 设置 承诺到达营业部时间.
     *
     * @param arriveTime the new 承诺到达营业部时间
     */
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * 获取 派送承诺需加天数.
     *
     * @return the 派送承诺需加天数
     */
    public Integer getAddDay() {
        return addDay;
    }

    /**
     * 设置 派送承诺需加天数.
     *
     * @param addDay the new 派送承诺需加天数
     */
    public void setAddDay(Integer addDay) {
        this.addDay = addDay;
    }

    /**
     * 获取 派送承诺时间.
     *
     * @return the 派送承诺时间
     */
    public String getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * 设置 派送承诺时间.
     *
     * @param deliveryTime the new 派送承诺时间
     */
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * 获取 是否有驻地部门.
     *
     * @return the 是否有驻地部门
     */
    public String getHasSalesDept() {
        return hasSalesDept;
    }

    /**
     * 设置 是否有驻地部门.
     *
     * @param hasSalesDept the new 是否有驻地部门
     */
    public void setHasSalesDept(String hasSalesDept) {
        this.hasSalesDept = hasSalesDept;
    }

    /**
     * 获取 长短途.
     *
     * @return the 长短途
     */
    public String getLongOrShort() {
        return longOrShort;
    }

    /**
     * 设置 长短途.
     *
     * @param longOrShort the new 长短途
     */
    public void setLongOrShort(String longOrShort) {
        this.longOrShort = longOrShort;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 备注.
     *
     * @param description the new 备注
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 数据版本.
     *
     * @return the 数据版本
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 数据版本.
     *
     * @param versionNo the new 数据版本
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 创建组织.
     *
     * @return the 创建组织
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建组织.
     *
     * @param createOrgCode the new 创建组织
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 更新组织.
     *
     * @return the 更新组织
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 更新组织.
     *
     * @param modifyOrgCode the new 更新组织
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }

    /**
     * 获取 数据状态.
     *
     * @return the 数据状态
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 数据状态.
     *
     * @param active the new 数据状态
     */
    public void setActive(String active) {
        this.active = active;
    }


	public String getUpDesTemp() {
		return upDesTemp;
	}


	public void setUpDesTemp(String upDesTemp) {
		this.upDesTemp = upDesTemp;
	}


}