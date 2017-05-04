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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/InTransitEntity.java
 * 
 *  FILE NAME     :InTransitEntity.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 货量预测在途中表entity
 */
public class InTransitEntity extends BaseEntity{
    
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -5401921097974186961L;
	/**
	 * 主键ID
	 */
	private String intransitId;
    /**
     * 货量预测表ID
     */
    private String forecastQuantityId;
    /**
     * 所属部门
     */
    private String belongOrgCode;
    
    private String belongOrgCodeName;
    
    /**
     * 所属区域
     */
    private String region;
    
    private String regionName;
    /**
     * 关联部门
     */
    private String relevantOrgCode;
    
    private String relevantOrgCodeName;
    /**
     * 在途车牌号
     */
    private String intransitVehicleNo;
    /**
     * 在途重量
     */
    private BigDecimal intransitWeight;
    /**
     * 在途体积
     */
    private BigDecimal intransitVolume;
    /**
     * 在途票数
     */
    private Integer intransitQty;
    /**
     * 卡航重量
     */
    private BigDecimal gpsEnabledResWeight;
    /**
     * 卡航体积
     */
    private BigDecimal gpsEnabledResVolume;
    /**
     * 卡航票数
     */
    private Integer gpsEnabledResQty;
    /**
     * 城运重量
     */
    private BigDecimal precisionIfsWeight;
    /**
     * 城运体积
     */
    private BigDecimal precisionIfsVolume;
    /**
     * 城运票数
     */
    private Integer precisionIfsQty;
    /**
     * 快递重量
     */
    private BigDecimal expressWeight;
    /**
     * 快递体积
     */
    private BigDecimal expressVolume;
    /**
     * 快递票数
     */
    private Integer expressQty;
    /**
     * 预测发起时间
     */
    private Date statisticsTime;
    /**
     * 预测类型 出发/到达
     */
    private String type;

    /**
     * 获取 主键ID.
     *
     * @return the 主键ID
     */
    public String getIntransitId() {
        return intransitId;
    }

    /**
     * 设置 主键ID.
     *
     * @param intransitId the new 主键ID
     */
    public void setIntransitId(String intransitId) {
        this.intransitId = intransitId;
    }

    /**
     * 获取 货量预测表ID.
     *
     * @return the 货量预测表ID
     */
    public String getForecastQuantityId() {
		return forecastQuantityId;
	}

	/**
	 * 设置 货量预测表ID.
	 *
	 * @param forecastQuantityId the new 货量预测表ID
	 */
	public void setForecastQuantityId(String forecastQuantityId) {
		this.forecastQuantityId = forecastQuantityId;
	}

	/**
	 * 获取 所属部门.
	 *
	 * @return the 所属部门
	 */
	public String getBelongOrgCode() {
        return belongOrgCode;
    }

    /**
     * 设置 所属部门.
     *
     * @param belongOrgCode the new 所属部门
     */
    public void setBelongOrgCode(String belongOrgCode) {
        this.belongOrgCode = belongOrgCode;
    }

    /**
     * 获取 所属区域.
     *
     * @return the 所属区域
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置 所属区域.
     *
     * @param region the new 所属区域
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 获取 关联部门.
     *
     * @return the 关联部门
     */
    public String getRelevantOrgCode() {
		return relevantOrgCode;
	}

	/**
	 * 设置 关联部门.
	 *
	 * @param relevantOrgCode the new 关联部门
	 */
	public void setRelevantOrgCode(String relevantOrgCode) {
		this.relevantOrgCode = relevantOrgCode;
	}

	/**
	 * 获取 在途车牌号.
	 *
	 * @return the 在途车牌号
	 */
	public String getIntransitVehicleNo() {
        return intransitVehicleNo;
    }

    /**
     * 设置 在途车牌号.
     *
     * @param intransitVehicleNo the new 在途车牌号
     */
    public void setIntransitVehicleNo(String intransitVehicleNo) {
        this.intransitVehicleNo = intransitVehicleNo;
    }

    /**
     * 获取 在途重量.
     *
     * @return the 在途重量
     */
    public BigDecimal getIntransitWeight() {
        return intransitWeight;
    }

    /**
     * 设置 在途重量.
     *
     * @param intransitWeight the new 在途重量
     */
    public void setIntransitWeight(BigDecimal intransitWeight) {
        this.intransitWeight = intransitWeight;
    }

    /**
     * 获取 在途体积.
     *
     * @return the 在途体积
     */
    public BigDecimal getIntransitVolume() {
        return intransitVolume;
    }

    /**
     * 设置 在途体积.
     *
     * @param intransitVolume the new 在途体积
     */
    public void setIntransitVolume(BigDecimal intransitVolume) {
        this.intransitVolume = intransitVolume;
    }

    /**
     * 获取 在途票数.
     *
     * @return the 在途票数
     */
    public Integer getIntransitQty() {
        return intransitQty;
    }

    /**
     * 设置 在途票数.
     *
     * @param intransitQty the new 在途票数
     */
    public void setIntransitQty(Integer intransitQty) {
        this.intransitQty = intransitQty;
    }

    /**
     * 获取 卡航重量.
     *
     * @return the 卡航重量
     */
    public BigDecimal getGpsEnabledResWeight() {
        return gpsEnabledResWeight;
    }

    /**
     * 设置 卡航重量.
     *
     * @param gpsEnabledResWeight the new 卡航重量
     */
    public void setGpsEnabledResWeight(BigDecimal gpsEnabledResWeight) {
        this.gpsEnabledResWeight = gpsEnabledResWeight;
    }

    /**
     * 获取 卡航体积.
     *
     * @return the 卡航体积
     */
    public BigDecimal getGpsEnabledResVolume() {
        return gpsEnabledResVolume;
    }

    /**
     * 设置 卡航体积.
     *
     * @param gpsEnabledResVolume the new 卡航体积
     */
    public void setGpsEnabledResVolume(BigDecimal gpsEnabledResVolume) {
        this.gpsEnabledResVolume = gpsEnabledResVolume;
    }

    /**
     * 获取 卡航票数.
     *
     * @return the 卡航票数
     */
    public Integer getGpsEnabledResQty() {
        return gpsEnabledResQty;
    }

    /**
     * 设置 卡航票数.
     *
     * @param gpsEnabledResQty the new 卡航票数
     */
    public void setGpsEnabledResQty(Integer gpsEnabledResQty) {
        this.gpsEnabledResQty = gpsEnabledResQty;
    }

    /**
     * 获取 城运重量.
     *
     * @return the 城运重量
     */
    public BigDecimal getPrecisionIfsWeight() {
        return precisionIfsWeight;
    }

    /**
     * 设置 城运重量.
     *
     * @param precisionIfsWeight the new 城运重量
     */
    public void setPrecisionIfsWeight(BigDecimal precisionIfsWeight) {
        this.precisionIfsWeight = precisionIfsWeight;
    }

    /**
     * 获取 城运体积.
     *
     * @return the 城运体积
     */
    public BigDecimal getPrecisionIfsVolume() {
        return precisionIfsVolume;
    }

    /**
     * 设置 城运体积.
     *
     * @param precisionIfsVolume the new 城运体积
     */
    public void setPrecisionIfsVolume(BigDecimal precisionIfsVolume) {
        this.precisionIfsVolume = precisionIfsVolume;
    }

    /**
     * 获取 城运票数.
     *
     * @return the 城运票数
     */
    public Integer getPrecisionIfsQty() {
        return precisionIfsQty;
    }

    /**
     * 设置 城运票数.
     *
     * @param precisionIfsQty the new 城运票数
     */
    public void setPrecisionIfsQty(Integer precisionIfsQty) {
        this.precisionIfsQty = precisionIfsQty;
    }

    public BigDecimal getExpressWeight() {
		return expressWeight;
	}

	public void setExpressWeight(BigDecimal expressWeight) {
		this.expressWeight = expressWeight;
	}

	public BigDecimal getExpressVolume() {
		return expressVolume;
	}

	public void setExpressVolume(BigDecimal expressVolume) {
		this.expressVolume = expressVolume;
	}

	public Integer getExpressQty() {
		return expressQty;
	}

	public void setExpressQty(Integer expressQty) {
		this.expressQty = expressQty;
	}

	/**
     * 获取 预测发起时间.
     *
     * @return the 预测发起时间
     */
    @DateFormat
    public Date getStatisticsTime() {
        return statisticsTime;
    }

    /**
     * 设置 预测发起时间.
     *
     * @param statisticsTime the new 预测发起时间
     */
    @DateFormat
    public void setStatisticsTime(Date statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

	/**
	 * 获取 预测类型 出发/到达.
	 *
	 * @return the 预测类型 出发/到达
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 预测类型 出发/到达.
	 *
	 * @param type the new 预测类型 出发/到达
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getBelongOrgCodeName() {
		return belongOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param belongOrgCodeName 
	 */
	public void setBelongOrgCodeName(String belongOrgCodeName) {
		this.belongOrgCodeName = belongOrgCodeName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * 
	 *
	 * @param regionName 
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getRelevantOrgCodeName() {
		return relevantOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param relevantOrgCodeName 
	 */
	public void setRelevantOrgCodeName(String relevantOrgCodeName) {
		this.relevantOrgCodeName = relevantOrgCodeName;
	}

	/**
	 * 版本号
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}