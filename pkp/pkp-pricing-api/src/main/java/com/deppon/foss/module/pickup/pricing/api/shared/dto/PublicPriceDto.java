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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PublicPriceDto.java
 * 
 * FILE NAME        	: PublicPriceDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.math.BigDecimal;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PublicPriceDto.
 */
public class PublicPriceDto {
    
    /** EffectivePlanDetailEntity的 id. */
    private String id;

    /** 产品CODE 第3级的产品代码. */
    private String productCode;
    
    /** 产品name. */
    private String productName;
    
    /** 时效始发区域Id. */
    private String deptRegionId;
    
    /** 时效始发区域code. */
    private String deptRegionCode;
    
    /** 时效始发区域Name. */
    private String deptRegionName;
    
    /** 价格始发区域Id. */
    private String deptPriceRegionId;
    
    /** 价格始发区域code. */
    private String deptPriceRegionCode;
    
    /** 价格始发区域Name. */
    private String deptPriceRegionName;
    
    /** 时效到达区域id. */
    private String arrvRegionId;
    
    /** 时效到达区域code. */
    private String arrvRegionCode;
    
    /** 时效到达区域Name. */
    private String arrvRegionName;
    
    /** 价格到达区域id. */
    private String arrvPriceRegionId;
    
    /** 价格到达区域code. */
    private String arrvPriceRegionCode;
    
    /** 价格到达区域Name. */
    private String arrvPriceRegionName;
	
	/** 承诺最长时间. */
    private Integer maxTime;

    /** 承诺最长时间单位. */
    private String maxTimeUnit;

    /** 承诺最短时间. */
    private Integer minTime;

    /** 承诺最短时间单位. */
    private String minTimeUnit;

    /** 承诺到达营业部时间. */
    private String arriveTime;
    
    /** 取货时间. */
    private String pickTime;

    /** 派送承诺需加天数. */
    private Integer addDay;

    /** 派送承诺时间. */
    private String deliveryTime;

    /** 是否有驻地部门. */
    private String hasSalesDept;

    /** 长短途. */
    private String longOrShort;
    
    
    /** 货物类型CODE. */
    private String goodsTypeCode; 
    
    
    /** 货物类型name. */
    private String goodsTypeName; 
    
    
    /** 是否接货. */
    private String centralizePickup;
    
    
    /** 轻货费率（小数）或者单价（分）. */
     
    private BigDecimal lightFeeRate;
    
    /** 重货费率（小数）或者单价（分）. */
    private BigDecimal heavyFeeRate;

	/** 最低费用. */
    private BigDecimal minFee;
    
    
    /** 接送货轻货费率（小数）或者单价（分）. */
     
    private BigDecimal lightFeeRatePickUpYes;
    
    /** 接送货重货费率（小数）或者单价（分）. */
    private BigDecimal heavyFeeRatePickUpYes;
    
    /** 非接送货轻货费率（小数）或者单价（分）. */
    private BigDecimal lightFeeRatePickUpNo;
    
    /** 非接送货重货费率（小数）或者单价（分）. */
    private BigDecimal heavyFeeRatePickUpNo;
    
    /** 接送货最低一票. */
    private BigDecimal minFeePickUpYes;
    
    /** 非接送货最低一票. */
    private BigDecimal minFeePickUpNo;
    
    /** 航班班次. */
    private String flightShiftNo;
    
    /** 时效始发网点集合. */
    private List<String> startDeptCodes;
    
    /** 时效到达网点集合. */
    private List<String> deptCodes;
    
    /** 价格始发网点集合. */
    private List<String> priceStartDeptCodes;
    
    /** 价格到达网点集合. */
    private List<String> priceArrvDeptCodes;
    
    /** 区域类型. */
    private String regionNature;
    
    /**
     * 合票类型名称
     */
    private String combBillTypeName;  //zxy 20140428 MANA-1253 新增
    
    /**
     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140428 MANA-1253 新增
     
    /**
     * 轻货费率（小数）或者单价（分）
     */
     
    private String lightFeeRateStr;//yangkang 20140708 新增
    
    /**
     * 重货费率（小数）或者单价（分）
     */
    private String heavyFeeRateStr;//yangkang 20140708 新增
    
    /**
     * 计价明细的信息list
     * @author 219413-Luomengxiang
     * @date 2014-11-24
     */
    private List<PopPublicPriceDto> popPublicPriceDtoList;
    
    /**
     * 获取popPublicPriceDtoList
     * @return popPublicPriceDtoList
     */
    public List<PopPublicPriceDto> getPopPublicPriceDtoList() {
		return popPublicPriceDtoList;
	}

 /**
  * 设置popPublicPriceDtoList
  * @param popPublicPriceDtoList
  */
	public void setPopPublicPriceDtoList(
			List<PopPublicPriceDto> popPublicPriceDtoList) {
		this.popPublicPriceDtoList = popPublicPriceDtoList;
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
     * 获取 产品CODE 第3级的产品代码.
     *
     * @return the 产品CODE 第3级的产品代码
     */
    public String getProductCode() {
        return productCode;
    }

    
    /**
     * 设置 产品CODE 第3级的产品代码.
     *
     * @param productCode the new 产品CODE 第3级的产品代码
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
     * 获取 货物类型CODE.
     *
     * @return the 货物类型CODE
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }

    
    /**
     * 设置 货物类型CODE.
     *
     * @param goodsTypeCode the new 货物类型CODE
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }

    
    /**
     * 获取 货物类型name.
     *
     * @return the 货物类型name
     */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    
    /**
     * 设置 货物类型name.
     *
     * @param goodsTypeName the new 货物类型name
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    
    /**
     * 获取 是否接货.
     *
     * @return the 是否接货
     */
    public String getCentralizePickup() {
        return centralizePickup;
    }

    
    /**
     * 设置 是否接货.
     *
     * @param centralizePickup the new 是否接货
     */
    public void setCentralizePickup(String centralizePickup) {
        this.centralizePickup = centralizePickup;
    }

    
    /**
     * 获取 轻货费率（小数）或者单价（分）.
     *
     * @return the 轻货费率（小数）或者单价（分）
     */
    public BigDecimal getLightFeeRate() {
        return lightFeeRate;
    }

    
    /**
     * 设置 轻货费率（小数）或者单价（分）.
     *
     * @param lightFeeRate the new 轻货费率（小数）或者单价（分）
     */
    public void setLightFeeRate(BigDecimal lightFeeRate) {
        this.lightFeeRate = lightFeeRate;
    }

    
    /**
     * 获取 重货费率（小数）或者单价（分）.
     *
     * @return the 重货费率（小数）或者单价（分）
     */
    public BigDecimal getHeavyFeeRate() {
        return heavyFeeRate;
    }

    
    /**
     * 设置 重货费率（小数）或者单价（分）.
     *
     * @param heavyFeeRate the new 重货费率（小数）或者单价（分）
     */
    public void setHeavyFeeRate(BigDecimal heavyFeeRate) {
        this.heavyFeeRate = heavyFeeRate;
    }

    
    /**
     * 获取 最低费用.
     *
     * @return the 最低费用
     */
    public BigDecimal getMinFee() {
        return minFee;
    }

    
    /**
     * 设置 最低费用.
     *
     * @param minFee the new 最低费用
     */
    public void setMinFee(BigDecimal minFee) {
        this.minFee = minFee;
    }


	/**
	 * 获取 接送货轻货费率（小数）或者单价（分）.
	 *
	 * @return the 接送货轻货费率（小数）或者单价（分）
	 */
	public BigDecimal getLightFeeRatePickUpYes() {
		return lightFeeRatePickUpYes;
	}


	/**
	 * 设置 接送货轻货费率（小数）或者单价（分）.
	 *
	 * @param lightFeeRatePickUpYes the new 接送货轻货费率（小数）或者单价（分）
	 */
	public void setLightFeeRatePickUpYes(BigDecimal lightFeeRatePickUpYes) {
		this.lightFeeRatePickUpYes = lightFeeRatePickUpYes;
	}


	/**
	 * 获取 接送货重货费率（小数）或者单价（分）.
	 *
	 * @return the 接送货重货费率（小数）或者单价（分）
	 */
	public BigDecimal getHeavyFeeRatePickUpYes() {
		return heavyFeeRatePickUpYes;
	}


	/**
	 * 设置 接送货重货费率（小数）或者单价（分）.
	 *
	 * @param heavyFeeRatePickUpYes the new 接送货重货费率（小数）或者单价（分）
	 */
	public void setHeavyFeeRatePickUpYes(BigDecimal heavyFeeRatePickUpYes) {
		this.heavyFeeRatePickUpYes = heavyFeeRatePickUpYes;
	}


	/**
	 * 获取 非接送货轻货费率（小数）或者单价（分）.
	 *
	 * @return the 非接送货轻货费率（小数）或者单价（分）
	 */
	public BigDecimal getLightFeeRatePickUpNo() {
		return lightFeeRatePickUpNo;
	}


	/**
	 * 设置 非接送货轻货费率（小数）或者单价（分）.
	 *
	 * @param lightFeeRatePickUpNo the new 非接送货轻货费率（小数）或者单价（分）
	 */
	public void setLightFeeRatePickUpNo(BigDecimal lightFeeRatePickUpNo) {
		this.lightFeeRatePickUpNo = lightFeeRatePickUpNo;
	}


	/**
	 * 获取 非接送货重货费率（小数）或者单价（分）.
	 *
	 * @return the 非接送货重货费率（小数）或者单价（分）
	 */
	public BigDecimal getHeavyFeeRatePickUpNo() {
		return heavyFeeRatePickUpNo;
	}


	/**
	 * 设置 非接送货重货费率（小数）或者单价（分）.
	 *
	 * @param heavyFeeRatePickUpNo the new 非接送货重货费率（小数）或者单价（分）
	 */
	public void setHeavyFeeRatePickUpNo(BigDecimal heavyFeeRatePickUpNo) {
		this.heavyFeeRatePickUpNo = heavyFeeRatePickUpNo;
	}


	/**
	 * 获取 接送货最低一票.
	 *
	 * @return the 接送货最低一票
	 */
	public BigDecimal getMinFeePickUpYes() {
		return minFeePickUpYes;
	}


	/**
	 * 设置 接送货最低一票.
	 *
	 * @param minFeePickUpYes the new 接送货最低一票
	 */
	public void setMinFeePickUpYes(BigDecimal minFeePickUpYes) {
		this.minFeePickUpYes = minFeePickUpYes;
	}


	/**
	 * 获取 非接送货最低一票.
	 *
	 * @return the 非接送货最低一票
	 */
	public BigDecimal getMinFeePickUpNo() {
		return minFeePickUpNo;
	}


	/**
	 * 设置 非接送货最低一票.
	 *
	 * @param minFeePickUpNo the new 非接送货最低一票
	 */
	public void setMinFeePickUpNo(BigDecimal minFeePickUpNo) {
		this.minFeePickUpNo = minFeePickUpNo;
	}

	/**
	 * 获取 始发网点集合.
	 *
	 * @return the 始发网点集合
	 */
	public List<String> getStartDeptCodes() {
		return startDeptCodes;
	}


	/**
	 * 设置 始发网点集合.
	 *
	 * @param startDeptCodes the new 始发网点集合
	 */
	public void setStartDeptCodes(List<String> startDeptCodes) {
		this.startDeptCodes = startDeptCodes;
	}


	/**
	 * 获取 到达网点集合.
	 *
	 * @return the 到达网点集合
	 */
	public List<String> getDeptCodes() {
		return deptCodes;
	}


	/**
	 * 设置 到达网点集合.
	 *
	 * @param deptCodes the new 到达网点集合
	 */
	public void setDeptCodes(List<String> deptCodes) {
		this.deptCodes = deptCodes;
	}

	/**
	 * 获取 始发区域Name.
	 *
	 * @return the 始发区域Name
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}


	/**
	 * 设置 始发区域Name.
	 *
	 * @param deptRegionName the new 始发区域Name
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
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


	/**
	 * 获取 取货时间.
	 *
	 * @return the 取货时间
	 */
	public String getPickTime() {
		return pickTime;
	}


	/**
	 * 设置 取货时间.
	 *
	 * @param pickTime the new 取货时间
	 */
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}


	/**
	 * 获取 航班班次.
	 *
	 * @return the 航班班次
	 */
	public String getFlightShiftNo() {
		return flightShiftNo;
	}


	/**
	 * 设置 航班班次.
	 *
	 * @param flightShiftNo the new 航班班次
	 */
	public void setFlightShiftNo(String flightShiftNo) {
		this.flightShiftNo = flightShiftNo;
	}


	/**
	 * Gets the region nature.
	 *
	 * @return the region nature
	 */
	public String getRegionNature() {
		return regionNature;
	}


	/**
	 * Sets the region nature.
	 *
	 * @param regionNature the new region nature
	 */
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}


	/**
	 * Gets the dept price region id.
	 *
	 * @return the dept price region id
	 */
	public String getDeptPriceRegionId() {
		return deptPriceRegionId;
	}


	/**
	 * Sets the dept price region id.
	 *
	 * @param deptPriceRegionId the new dept price region id
	 */
	public void setDeptPriceRegionId(String deptPriceRegionId) {
		this.deptPriceRegionId = deptPriceRegionId;
	}


	/**
	 * Gets the dept price region code.
	 *
	 * @return the dept price region code
	 */
	public String getDeptPriceRegionCode() {
		return deptPriceRegionCode;
	}


	/**
	 * Sets the dept price region code.
	 *
	 * @param deptPriceRegionCode the new dept price region code
	 */
	public void setDeptPriceRegionCode(String deptPriceRegionCode) {
		this.deptPriceRegionCode = deptPriceRegionCode;
	}


	/**
	 * Gets the dept price region name.
	 *
	 * @return the dept price region name
	 */
	public String getDeptPriceRegionName() {
		return deptPriceRegionName;
	}


	/**
	 * Sets the dept price region name.
	 *
	 * @param deptPriceRegionName the new dept price region name
	 */
	public void setDeptPriceRegionName(String deptPriceRegionName) {
		this.deptPriceRegionName = deptPriceRegionName;
	}


	/**
	 * Gets the arrv price region id.
	 *
	 * @return the arrv price region id
	 */
	public String getArrvPriceRegionId() {
		return arrvPriceRegionId;
	}


	/**
	 * Sets the arrv price region id.
	 *
	 * @param arrvPriceRegionId the new arrv price region id
	 */
	public void setArrvPriceRegionId(String arrvPriceRegionId) {
		this.arrvPriceRegionId = arrvPriceRegionId;
	}


	/**
	 * Gets the arrv price region code.
	 *
	 * @return the arrv price region code
	 */
	public String getArrvPriceRegionCode() {
		return arrvPriceRegionCode;
	}


	/**
	 * Sets the arrv price region code.
	 *
	 * @param arrvPriceRegionCode the new arrv price region code
	 */
	public void setArrvPriceRegionCode(String arrvPriceRegionCode) {
		this.arrvPriceRegionCode = arrvPriceRegionCode;
	}


	/**
	 * Gets the arrv price region name.
	 *
	 * @return the arrv price region name
	 */
	public String getArrvPriceRegionName() {
		return arrvPriceRegionName;
	}


	/**
	 * Sets the arrv price region name.
	 *
	 * @param arrvPriceRegionName the new arrv price region name
	 */
	public void setArrvPriceRegionName(String arrvPriceRegionName) {
		this.arrvPriceRegionName = arrvPriceRegionName;
	}


	public List<String> getPriceStartDeptCodes() {
		return priceStartDeptCodes;
	}


	public void setPriceStartDeptCodes(List<String> priceStartDeptCodes) {
		this.priceStartDeptCodes = priceStartDeptCodes;
	}


	public List<String> getPriceArrvDeptCodes() {
		return priceArrvDeptCodes;
	}


	public void setPriceArrvDeptCodes(List<String> priceArrvDeptCodes) {
		this.priceArrvDeptCodes = priceArrvDeptCodes;
	}


	public String getCombBillTypeName() {
		return combBillTypeName;
	}


	public void setCombBillTypeName(String combBillTypeName) {
		this.combBillTypeName = combBillTypeName;
	}


	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}


	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}


	public String getLightFeeRateStr() {
		return lightFeeRateStr;
	}


	public void setLightFeeRateStr(String lightFeeRateStr) {
		this.lightFeeRateStr = lightFeeRateStr;
	}


	public String getHeavyFeeRateStr() {
		return heavyFeeRateStr;
	}


	public void setHeavyFeeRateStr(String heavyFeeRateStr) {
		this.heavyFeeRateStr = heavyFeeRateStr;
	}

}