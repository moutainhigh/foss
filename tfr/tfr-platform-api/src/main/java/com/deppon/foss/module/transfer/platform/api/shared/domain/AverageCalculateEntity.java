/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/AverageCalculateEntity.java
 * 
 *  FILE NAME     :AverageCalculateEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.platform.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计算平均重量体积entity
 */
public class AverageCalculateEntity extends BaseEntity{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 4024841856041771795L;
	/**
	 * 平均总量体积ID
	 */
	private String averageCalculateId;
	/**
	 * 部门编码
	 */
    private String orgCode;
    /**
     * 总重量
     */
    private BigDecimal totalWeight;
    /**
     * 总体积
     */
    private BigDecimal totalVolume;
    /**
     * 总票数
     */
    private Integer totalWaybillQty;
    /**
     * 总件数
     */
    private Integer totalGoodsQty;
    /**
     * 平均重量
     */
    private BigDecimal averageWeight;
    /**
     * 平均体积
     */
    private BigDecimal averageVolume;
    /**
     * 每票件数
     */
    private Integer averageGoodsQty;
    /**
     * 修改时间
     */
    private Date changeTime;

    /**
     * 获取 平均总量体积ID.
     *
     * @return the 平均总量体积ID
     */
    public String getAverageCalculateId() {
        return averageCalculateId;
    }

    /**
     * 设置 平均总量体积ID.
     *
     * @param averageCalculateId the new 平均总量体积ID
     */
    public void setAverageCalculateId(String averageCalculateId) {
        this.averageCalculateId = averageCalculateId;
    }

    /**
     * 获取 部门编码.
     *
     * @return the 部门编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 部门编码.
     *
     * @param orgCode the new 部门编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取 总重量.
     *
     * @return the 总重量
     */
    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    /**
     * 设置 总重量.
     *
     * @param totalWeight the new 总重量
     */
    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    /**
     * 获取 总体积.
     *
     * @return the 总体积
     */
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    /**
     * 设置 总体积.
     *
     * @param totalVolume the new 总体积
     */
    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    /**
     * 获取 总票数.
     *
     * @return the 总票数
     */
    public Integer getTotalWaybillQty() {
        return totalWaybillQty;
    }

    /**
     * 设置 总票数.
     *
     * @param totalWaybillQty the new 总票数
     */
    public void setTotalWaybillQty(Integer totalWaybillQty) {
        this.totalWaybillQty = totalWaybillQty;
    }

    /**
     * 获取 总件数.
     *
     * @return the 总件数
     */
    public Integer getTotalGoodsQty() {
        return totalGoodsQty;
    }

    /**
     * 设置 总件数.
     *
     * @param totalGoodsQty the new 总件数
     */
    public void setTotalGoodsQty(Integer totalGoodsQty) {
        this.totalGoodsQty = totalGoodsQty;
    }

    /**
     * 获取 平均重量.
     *
     * @return the 平均重量
     */
    public BigDecimal getAverageWeight() {
        return averageWeight;
    }

    /**
     * 设置 平均重量.
     *
     * @param averageWeight the new 平均重量
     */
    public void setAverageWeight(BigDecimal averageWeight) {
        this.averageWeight = averageWeight;
    }

    /**
     * 获取 平均体积.
     *
     * @return the 平均体积
     */
    public BigDecimal getAverageVolume() {
        return averageVolume;
    }

    /**
     * 设置 平均体积.
     *
     * @param averageVolume the new 平均体积
     */
    public void setAverageVolume(BigDecimal averageVolume) {
        this.averageVolume = averageVolume;
    }

    /**
     * 获取 每票件数.
     *
     * @return the 每票件数
     */
    public Integer getAverageGoodsQty() {
        return averageGoodsQty;
    }

    /**
     * 设置 每票件数.
     *
     * @param averageGoodsQty the new 每票件数
     */
    public void setAverageGoodsQty(Integer averageGoodsQty) {
        this.averageGoodsQty = averageGoodsQty;
    }

    /**
     * 获取 修改时间.
     *
     * @return the 修改时间
     */
    public Date getChangeTime() {
        return changeTime;
    }

    /**
     * 设置 修改时间.
     *
     * @param changeTime the new 修改时间
     */
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

	
	/**
	 * 
	 * 版本号
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}