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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/EffectivePlanDto.java
 * 
 * FILE NAME        	: EffectivePlanDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.util.Date;


/**
 *  时效dto
 * @author zhangdongping
 * @date 2012-10-25 下午5:43:34
 * @since
 * @version
 */
public class EffectiveExpressPlanDto {
    
    /**
     *  EffectivePlanDetailEntity的 id 
     */
    private String id;
    
    /**
     *  产品CODE
     */
    private String productCode;
    /**
     * 产品name
     */
    private String productName; 
    
    /**
     * 始发区域Id
     */
    private String deptRegionId;
    /**
     * 始发区域code
     */
    private String deptRegionCode;
    /**
     * 到达区域id
     */
    private String arrvRegionId;
    /**
     * 到达区域code
     */
    private String arrvRegionCode;
    /**
     * 到达区域Name
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
     *  承诺最短时间单位
     */
    private String minTimeUnit;

    /**
     * 承诺到达营业部时间
     */
    private String arriveTime;

    /**
     *  派送承诺需加天数
     */
    private Integer addDay;

    /**
     *  派送承诺时间
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
     * 开单日期
     */
    private Date billDate;
    
    /**
     * 是否激活
     */
    private String active; 
 
    
    
    /**
     * 获取 是否激活.
     *
     * @return the 是否激活
     */
    public String getActive() {
        return active;
    }


    /**
     * 设置 是否激活.
     *
     * @param active the new 是否激活
     */
    public void setActive(String active) {
        this.active = active;
    }



    /**
     * 获取 开单日期.
     *
     * @return the 开单日期
     */
    public Date getBillDate() {
        return billDate;
    }


    
    /**
     * 设置 开单日期.
     *
     * @param billDate the new 开单日期
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }


    /**
     * 获取 effectivePlanDetailEntity的 id.
     *
     * @return the effectivePlanDetailEntity的 id
     */
    public String getId() {
        return id;
    }

    
    /**
     * 设置 effectivePlanDetailEntity的 id.
     *
     * @param id the new effectivePlanDetailEntity的 id
     */
    public void setId(String id) {
        this.id = id;
    }

    
	/**
	 * 获取 产品CODE.
	 *
	 * @return the 产品CODE
	 */
	public String getProductCode() {
        return productCode;
    }


    
    /**
     * 设置 产品CODE.
     *
     * @param productCode the new 产品CODE
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    
    /**
     * 获取 产品name.
     *
     * @return the 产品name
     */
    public String getProductName() {
        return productName;
    }


    
    /**
     * 设置 产品name.
     *
     * @param productName the new 产品name
     */
    public void setProductName(String productName) {
        this.productName = productName;
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
     * 获取 始发区域Id.
     *
     * @return the 始发区域Id
     */
    public String getDeptRegionId() {
		return deptRegionId;
	}


	/**
	 * 设置 始发区域Id.
	 *
	 * @param deptRegionId the new 始发区域Id
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}


	/**
	 * 获取 始发区域code.
	 *
	 * @return the 始发区域code
	 */
	public String getDeptRegionCode() {
		return deptRegionCode;
	}


	/**
	 * 设置 始发区域code.
	 *
	 * @param deptRegionCode the new 始发区域code
	 */
	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}


	/**
	 * 获取 到达区域id.
	 *
	 * @return the 到达区域id
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}


	/**
	 * 设置 到达区域id.
	 *
	 * @param arrvRegionId the new 到达区域id
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}


	/**
	 * 获取 到达区域code.
	 *
	 * @return the 到达区域code
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}


	/**
	 * 设置 到达区域code.
	 *
	 * @param arrvRegionCode the new 到达区域code
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}


	/**
	 * 获取 到达区域Name.
	 *
	 * @return the 到达区域Name
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 设置 到达区域Name.
	 *
	 * @param arrvRegionName the new 到达区域Name
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}      
	
}