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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PricePlanDetailDto.java
 * 
 * FILE NAME        	: PricePlanDetailDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * 价格管理界面计价明细dto
 * PricePlanDetailDto
 * 
 * DP-Foss-YueHongJie
 * 2012-12-1 上午10:40:15.
 *
 * @version 1.0.0
 */
public class PricePlanDetailDto implements Serializable{
    
    /** 序列化. */
    private static final long serialVersionUID = -3944187002491517170L;
    
    /** 计价明细ID. */
    private String priceDetailId;
    
    /** 计费规则ID. */
    private String valuationId;
    
    /** 价格方案ID. */
    private String pricePlanId;
    
    /** 计费规则ID. */
    private String pricingValuationId;
    
    /** 目的区域ID. */
    private String arrvRegionId;
    
    /** 目的区域NAME. */
    private String arrvRegionName;
    
    /** 创建时间. */
    private Date createTime;
    
    /** 产品条目ID. */
    private String productItemId;
    
    /** 产品条目CODE. */
    private String productItemCode;
    
    /** 产品条目名称. */
    private String productItemName;
    
    /** 是否集中接货. */
    private String centralizePickup;
    
    /** 是否集中送货货.2016.07.08添加：用于首续重价格方案 */
    private String centralizeDelivery;
    
    /** 重货价格. */
    private BigDecimal heavyPrice;
    
    /** 轻货价格. */
    private BigDecimal lightPrice;
    
    /** 最低一票. */
    private Long minimumOneTicket;
    
    /** 数据状态. */
    private String active;
    
    /** 备注. */
    private String remark;
    
   
    
    /** 空运货物类型ID. */
    private String goodsTypeId;
    
    /** 空运货物类型CODE. */
    private String goodsTypeCode;
    
    /** 空运货物类型NAME. */
    private String goodsTypeName;
    
    /** 空运航班类型（早，中，晚班）. */
    private String flightTypeCode;
    
    /** 空运航班类型名称. */
    private String flightTypeName;
    
    /**
     * 是否自提（Y/N）.
     */
    private String selfPickUp;
    
    /**
     * 重量上线(首重).
     */
    private BigDecimal weightOnline;
    
    /**
     * 重量下线（首重）.
     */
    private BigDecimal weightDownline;
    
    /**
     * 价格（首重）.
     */
    private BigDecimal firstFee;
    
    /**
     * 重量上线(续重1).
     */
    private BigDecimal weightOnlineOne;
    
    /**
     * 重量下线(续重1).
     */
    private BigDecimal weightDownlineOne;
    
    
    /**
     * 费率(续重1).
     */
    private BigDecimal feeRateOne;
    
    /**
     * 重量上线(续重2).
     */
    private BigDecimal weightOnlineTwo;
    
    /**
     * 重量下线(续重2).
     */
    private BigDecimal weightDownlineTwo;
    
    /**
     * 费率(续重2).
     */
    private BigDecimal feeRateTwo;
    
    /**
     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140428 MANA-1253 新增
    
    /**
     * 合票类型名称
     */
    private String combBillTypeName;  //zxy 20140428 MANA-1253 新增
    
    /**
     * 计费类别
     */
    private String caculateType;
    /**
     * 开始范围
     */
    private BigDecimal leftRange ;
    /**
     * 结束范围
     */
    private BigDecimal rightRange;
    /**
     * 重量开始范围
     */
    private BigDecimal weightLeftRange;
    /**
     * 重量结束范围
     */
    private BigDecimal weightRightRange;
    /**
     * 体积开始范围
     */
    private BigDecimal volumeLeftRange;
    /**
     * 体积结束范围
     */
    private BigDecimal volumeRightRange;
    /**
     * 固定费用
     */
    private BigDecimal fixedCosts;
    
    /**
     * 大票货价格
     */
    private BigDecimal prices ;
    /**
     * 分段数ID
     * @author POP-Team-LuoMengxiang
     * @date 2014/10/23
     */
     private String sectionID;
     /**
      * 重货价格2
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
     private BigDecimal heavyPrice2;
     /**
      * 重货价格3
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
     private BigDecimal heavyPrice3;
     /**
      * 重货价格4
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
     private BigDecimal heavyPrice4;
     /**
      * 重货价格5
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
     private BigDecimal heavyPrice5;
     /**
      * 重货价格6
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
     private BigDecimal heavyPrice6;
     /**
      * 重货临界值
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
     private BigDecimal heavyCriticalVal;
	/**
     * 重货临界值2
     * @author POP-Team-LuoMengXiang
     * @date  2014/10/24
     */
    private BigDecimal heavyCriticalVal2;
    /**
     * 重货临界值3
     * @author POP-Team-LuoMengXiang
     * @date  2014/10/24
     */
    private BigDecimal heavyCriticalVal3;
    /**
     * 重货临界值4
     * @author POP-Team-LuoMengXiang
     * @date  2014/10/24
     */
    private BigDecimal heavyCriticalVal4;
    /**
     * 重货临界值5
     * @author POP-Team-LuoMengXiang
     * @date  2014/10/24
     */
    private BigDecimal heavyCriticalVal5;
    /**
     * 重货临界值6
     * @author POP-Team-LuoMengXiang
     * @date  2014/10/24
     */
    private BigDecimal heavyCriticalVal6;
    /**
     * 轻货价格2
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
    private BigDecimal lightPrice2;
    /**
     * 轻货价格3
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
    private BigDecimal lightPrice3;
    /**
     * 轻货价格4
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
    private BigDecimal lightPrice4;
    /**
     * 轻货价格5
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
    private BigDecimal lightPrice5;
    /**
     * 轻货价格6
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
    private BigDecimal lightPrice6;
    /**
     * 轻货临界值
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
    private BigDecimal lightCriticalVal;
	/**
      * 轻货临界值2
      * @author POP-Team-LuoMengXiang
      * @date   2014/10/24
      */
     private BigDecimal lightCriticalVal2;
     /**
      * 轻货临界值3
      * @author POP-Team-LuoMengXiang
      * @date   2014/10/24
      */
     private BigDecimal lightCriticalVal3;
     /**
      * 轻货临界值4
      * @author POP-Team-LuoMengXiang
      * @date   2014/10/24
      */
     private BigDecimal lightCriticalVal4;
     /**
      * 轻货临界值5
      * @author POP-Team-LuoMengXiang
      * @date   2014/10/24
      */
     private BigDecimal lightCriticalVal5;
     /**
      * 轻货临界值6
      * @author POP-Team-LuoMengXiang
      * @date   2014/10/24
      */
     private BigDecimal lightCriticalVal6;
     /**
      * 规则明细Id
      * @author POP-Team-LuoMengXiang
      * @date 2014/10/27
      */
     private String criteriaDetailId;
     
     /**
      * 出发区域名字
      */
     private String deptRegionName;
     
     /**
      * 出发区域名字
      */
     public String getDeptRegionName() {
		return deptRegionName;
	}
     /**
      * 出发区域名字
      */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}

     
     /**
      * 获得重货价格2
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
    public BigDecimal getHeavyPrice2() {
		return heavyPrice2;
	}
    /**
     * 设置重货价格2
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setHeavyPrice2(BigDecimal heavyPrice2) {
		this.heavyPrice2 = heavyPrice2;
	}
	 /**
     * 获得重货价格3
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getHeavyPrice3() {
		return heavyPrice3;
	}
	 /**
     * 设置重货价格3
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setHeavyPrice3(BigDecimal heavyPrice3) {
		this.heavyPrice3 = heavyPrice3;
	}
	 /**
     * 获得重货价格4
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getHeavyPrice4() {
		return heavyPrice4;
	}
	 /**
     * 设置重货价格4
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setHeavyPrice4(BigDecimal heavyPrice4) {
		this.heavyPrice4 = heavyPrice4;
	}
	 /**
     * 获得重货价格5
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getHeavyPrice5() {
		return heavyPrice5;
	}
	 /**
     * 设置重货价格5
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setHeavyPrice5(BigDecimal heavyPrice5) {
		this.heavyPrice5 = heavyPrice5;
	}
	 /**
     * 获得重货价格6
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getHeavyPrice6() {
		return heavyPrice6;
	}
	 /**
     * 设置重货价格6
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setHeavyPrice6(BigDecimal heavyPrice6) {
		this.heavyPrice6 = heavyPrice6;
	}
	 /**
     * 获得轻货价格2
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
     public BigDecimal getLightPrice2() {
		return lightPrice2;
	}
     /**
      * 设置轻货价格2
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
	public void setLightPrice2(BigDecimal lightPrice2) {
		this.lightPrice2 = lightPrice2;
	}
	/**
     * 获得轻货价格3
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getLightPrice3() {
		return lightPrice3;
	}
	/**
     * 设置轻货价格3
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setLightPrice3(BigDecimal lightPrice3) {
		this.lightPrice3 = lightPrice3;
	}
	/**
     * 获得轻货价格4
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getLightPrice4() {
		return lightPrice4;
	}
	/**
     * 设置轻货价格4
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setLightPrice4(BigDecimal lightPrice4) {
		this.lightPrice4 = lightPrice4;
	}
	/**
     * 获得轻货价格5
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getLightPrice5() {
		return lightPrice5;
	}
	/**
     * 设置轻货价格5
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setLightPrice5(BigDecimal lightPrice5) {
		this.lightPrice5 = lightPrice5;
	}
	/**
     * 获得轻货价格6
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getLightPrice6() {
		return lightPrice6;
	}
	/**
     * 设置轻货价格6
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setLightPrice6(BigDecimal lightPrice6) {
		this.lightPrice6 = lightPrice6;
	}

     /**
      * 获得规则明细Id
      * @return
      */
     public String getCriteriaDetailId() {
		return criteriaDetailId;
	}
	/**
	 * 设置规则明细Id
	 * @param criteriaDetailId
	 */
	public void setCriteriaDetailId(String criteriaDetailId) {
		this.criteriaDetailId = criteriaDetailId;
	}

	/**
      * 获取分段数ID
      */
     public String getSectionID() {
		return sectionID;
	}

	/**
	 * 设置分段数ID
	 * @param sectionID
	 */
     public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}
     /**
      * 获得重货临界值
      * @author Pop-Team-Luomengxiang
      * @date 2014.11.1
      */
	public BigDecimal getHeavyCriticalVal() {
		return heavyCriticalVal;
	}
	/**
     * 设置重货临界值
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setHeavyCriticalVal(BigDecimal heavyCriticalVal) {
		this.heavyCriticalVal = heavyCriticalVal;
	}
	 /**
     * 获得重货临界值2
     * @param heavyCriticalVal
     */
	public BigDecimal getHeavyCriticalVal2() {
		return heavyCriticalVal2;
	}
	 /**
     * 设置重货临界值2
     * @param heavyCriticalVal
     */
	public void setHeavyCriticalVal2(BigDecimal heavyCriticalVal2) {
		this.heavyCriticalVal2 = heavyCriticalVal2;
	}
	 /**
     * 获得重货临界值3
     * @param heavyCriticalVal
     */
	public BigDecimal getHeavyCriticalVal3() {
		return heavyCriticalVal3;
	}
	 /**
     * 设置重货临界值3
     * @param heavyCriticalVal
     */
	public void setHeavyCriticalVal3(BigDecimal heavyCriticalVal3) {
		this.heavyCriticalVal3 = heavyCriticalVal3;
	}
	 /**
     * 获得重货临界值4
     * @param heavyCriticalVal
     */
	public BigDecimal getHeavyCriticalVal4() {
		return heavyCriticalVal4;
	}
	 /**
     * 设置重货临界值4
     * @param heavyCriticalVal
     */
	public void setHeavyCriticalVal4(BigDecimal heavyCriticalVal4) {
		this.heavyCriticalVal4 = heavyCriticalVal4;
	}
	 /**
     * 获得重货临界值5
     * @param heavyCriticalVal
     */
	public BigDecimal getHeavyCriticalVal5() {
		return heavyCriticalVal5;
	}
	 /**
     * 设置重货临界值5
     * @param heavyCriticalVal
     */
	public void setHeavyCriticalVal5(BigDecimal heavyCriticalVal5) {
		this.heavyCriticalVal5 = heavyCriticalVal5;
	}
	 /**
     * 获得重货临界值6
     * @param heavyCriticalVal
     */
	public BigDecimal getHeavyCriticalVal6() {
		return heavyCriticalVal6;
	}
	 /**
     * 设置重货临界值6
     * @param heavyCriticalVal
     */
	public void setHeavyCriticalVal6(BigDecimal heavyCriticalVal6) {
		this.heavyCriticalVal6 = heavyCriticalVal6;
	}
	 /**
     * 获得轻货临界值
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public BigDecimal getLightCriticalVal() {
		return lightCriticalVal;
	}
	/**
     * 设置轻货临界值
     * @author Pop-Team-Luomengxiang
     * @date 2014.11.1
     */
	public void setLightCriticalVal(BigDecimal lightCriticalVal) {
		this.lightCriticalVal = lightCriticalVal;
	}
	 /**
     * 获得轻货临界值2
     * @param heavyCriticalVal
     */
	public BigDecimal getLightCriticalVal2() {
		return lightCriticalVal2;
	}
	 /**
     * 设置轻货临界值2
     * @param heavyCriticalVal
     */
	public void setLightCriticalVal2(BigDecimal lightCriticalVal2) {
		this.lightCriticalVal2 = lightCriticalVal2;
	}
	 /**
     * 获得轻货临界值3
     * @param heavyCriticalVal
     */
	public BigDecimal getLightCriticalVal3() {
		return lightCriticalVal3;
	}
	 /**
     * 设置轻货临界值3
     * @param heavyCriticalVal
     */
	public void setLightCriticalVal3(BigDecimal lightCriticalVal3) {
		this.lightCriticalVal3 = lightCriticalVal3;
	}
	 /**
     * 获得轻货临界值4
     * @param heavyCriticalVal
     */
	public BigDecimal getLightCriticalVal4() {
		return lightCriticalVal4;
	}
	 /**
     * 设置轻货临界值4
     * @param heavyCriticalVal
     */
	public void setLightCriticalVal4(BigDecimal lightCriticalVal4) {
		this.lightCriticalVal4 = lightCriticalVal4;
	}
	 /**
     * 获得轻货临界值5
     * @param heavyCriticalVal
     */
	public BigDecimal getLightCriticalVal5() {
		return lightCriticalVal5;
	}
	 /**
     * 设置轻货临界值5
     * @param heavyCriticalVal
     */
	public void setLightCriticalVal5(BigDecimal lightCriticalVal5) {
		this.lightCriticalVal5 = lightCriticalVal5;
	}
	 /**
     * 获得轻货临界值6
     * @param heavyCriticalVal
     */
	public BigDecimal getLightCriticalVal6() {
		return lightCriticalVal6;
	}
	 /**
     * 设置轻货临界值6
     * @param heavyCriticalVal
     */
	public void setLightCriticalVal6(BigDecimal lightCriticalVal6) {
		this.lightCriticalVal6 = lightCriticalVal6;
	}

	/**
     * Gets the flight type name.
     *
     * @return the flight type name
     */
    public String getFlightTypeName() {
        return flightTypeName;
    }

    /**
     * Sets the flight type name.
     *
     * @param flightTypeName the new flight type name
     */
    public void setFlightTypeName(String flightTypeName) {
        this.flightTypeName = flightTypeName;
    }

    /**
     * Gets the goods type id.
     *
     * @return the goods type id
     */
    public String getGoodsTypeId() {
        return goodsTypeId;
    }
    
    /**
     * Sets the goods type id.
     *
     * @param goodsTypeId the new goods type id
     */
    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    /**
     * Gets the goods type code.
     *
     * @return the goods type code
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }

    /**
     * Sets the goods type code.
     *
     * @param goodsTypeCode the new goods type code
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }
    
    /**
     * Gets the flight type code.
     *
     * @return the flight type code
     */
    public String getFlightTypeCode() {
        return flightTypeCode;
    }

    
    /**
     * Sets the flight type code.
     *
     * @param flightTypeCode the new flight type code
     */
    public void setFlightTypeCode(String flightTypeCode) {
        this.flightTypeCode = flightTypeCode;
    }

   
    /**
     * Gets the price detail id.
     *
     * @return the price detail id
     */
    public String getPriceDetailId() {
        return priceDetailId;
    }
    
    /**
     * Sets the price detail id.
     *
     * @param priceDetailId the new price detail id
     */
    public void setPriceDetailId(String priceDetailId) {
        this.priceDetailId = priceDetailId;
    }

    /**
     * Gets the valuation id.
     *
     * @return the valuation id
     */
    public String getValuationId() {
        return valuationId;
    }

    /**
     * Sets the valuation id.
     *
     * @param valuationId the new valuation id
     */
    public void setValuationId(String valuationId) {
        this.valuationId = valuationId;
    }

    /**
     * Gets the centralize pickup.
     *
     * @return the centralize pickup
     */
    public String getCentralizePickup() {
        return centralizePickup;
    }

    /**
     * Sets the centralize pickup.
     *
     * @param centralizePickup the new centralize pickup
     */
    public void setCentralizePickup(String centralizePickup) {
        this.centralizePickup = centralizePickup;
    }


    /**
     * Gets the pricing valuation id.
     *
     * @return the pricing valuation id
     */
    public String getPricingValuationId() {
        return pricingValuationId;
    }

    
    /**
     * Sets the pricing valuation id.
     *
     * @param pricingValuationId the new pricing valuation id
     */
    public void setPricingValuationId(String pricingValuationId) {
        this.pricingValuationId = pricingValuationId;
    }

    /**
     * Gets the price plan id.
     *
     * @return the price plan id
     */
    public String getPricePlanId() {
        return pricePlanId;
    }

    /**
     * Sets the price plan id.
     *
     * @param pricePlanId the new price plan id
     */
    public void setPricePlanId(String pricePlanId) {
        this.pricePlanId = pricePlanId;
    }
    
    /**
     * Gets the product item name.
     *
     * @return the product item name
     */
    public String getProductItemName() {
        return productItemName;
    }

    
    /**
     * Sets the product item name.
     *
     * @param productItemName the new product item name
     */
    public void setProductItemName(String productItemName) {
        this.productItemName = productItemName;
    }
    
    /**
     * Gets the product item code.
     *
     * @return the product item code
     */
    public String getProductItemCode() {
        return productItemCode;
    }
    
    /**
     * Sets the product item code.
     *
     * @param productItemCode the new product item code
     */
    public void setProductItemCode(String productItemCode) {
        this.productItemCode = productItemCode;
    }
    
    /**
     * Gets the product item id.
     *
     * @return the product item id
     */
    public String getProductItemId() {
        return productItemId;
    }

    
    /**
     * Sets the product item id.
     *
     * @param productItemId the new product item id
     */
    public void setProductItemId(String productItemId) {
        this.productItemId = productItemId;
    }
    
    /**
     * Gets the arrv region id.
     *
     * @return the arrv region id
     */
    public String getArrvRegionId() {
        return arrvRegionId;
    }
    
    /**
     * Sets the arrv region id.
     *
     * @param arrvRegionId the new arrv region id
     */
    public void setArrvRegionId(String arrvRegionId) {
        this.arrvRegionId = arrvRegionId;
    }
    
    /**
     * Gets the arrv region name.
     *
     * @return the arrv region name
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }
    
    /**
     * Sets the arrv region name.
     *
     * @param arrvRegionName the new arrv region name
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }
    
    /**
     * Gets the creates the time.
     *
     * @return the creates the time
     */
    public Date getCreateTime() {
        return createTime;
    }
    
    /**
     * Sets the creates the time.
     *
     * @param createTime the new creates the time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * Gets the heavy price.
     *
     * @return the heavy price
     */
    public BigDecimal getHeavyPrice() {
        return heavyPrice;
    }
    
    /**
     * Sets the heavy price.
     *
     * @param heavyPrice the new heavy price
     */
    public void setHeavyPrice(BigDecimal heavyPrice) {
        this.heavyPrice = heavyPrice;
    }
    
    /**
     * Gets the light price.
     *
     * @return the light price
     */
    public BigDecimal getLightPrice() {
        return lightPrice;
    }
    
    /**
     * Sets the light price.
     *
     * @param lightPrice the new light price
     */
    public void setLightPrice(BigDecimal lightPrice) {
        this.lightPrice = lightPrice;
    }
    
    /**
     * Gets the minimum one ticket.
     *
     * @return the minimum one ticket
     */
    public Long getMinimumOneTicket() {
        return minimumOneTicket;
    }
    
    /**
     * Sets the minimum one ticket.
     *
     * @param minimumOneTicket the new minimum one ticket
     */
    public void setMinimumOneTicket(Long minimumOneTicket) {
        this.minimumOneTicket = minimumOneTicket;
    }
    
    /**
     * Gets the active.
     *
     * @return the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * Sets the active.
     *
     * @param active the new active
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * Gets the remark.
     *
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * Sets the remark.
     *
     * @param remark the new remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Gets the goods type name.
     *
     * @return the goods type name
     */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    
    /**
     * Sets the goods type name.
     *
     * @param goodsTypeName the new goods type name
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    
    public String getSelfPickUp() {
        return selfPickUp;
    }

    
    public void setSelfPickUp(String selfPickUp) {
        this.selfPickUp = selfPickUp;
    }

    
    public BigDecimal getWeightOnline() {
        return weightOnline;
    }

    
    public void setWeightOnline(BigDecimal weightOnline) {
        this.weightOnline = weightOnline;
    }

    
    public BigDecimal getWeightDownline() {
        return weightDownline;
    }

    
    public void setWeightDownline(BigDecimal weightDownline) {
        this.weightDownline = weightDownline;
    }

    
    public BigDecimal getFirstFee() {
        return firstFee;
    }

    
    public void setFirstFee(BigDecimal firstFee) {
        this.firstFee = firstFee;
    }

    
    public BigDecimal getWeightOnlineOne() {
        return weightOnlineOne;
    }

    
    public void setWeightOnlineOne(BigDecimal weightOnlineOne) {
        this.weightOnlineOne = weightOnlineOne;
    }

    
    public BigDecimal getWeightDownlineOne() {
        return weightDownlineOne;
    }

    
    public void setWeightDownlineOne(BigDecimal weightDownlineOne) {
        this.weightDownlineOne = weightDownlineOne;
    }

    
    public BigDecimal getFeeRateOne() {
        return feeRateOne;
    }

    
    public void setFeeRateOne(BigDecimal feeRateOne) {
        this.feeRateOne = feeRateOne;
    }

    
    public BigDecimal getWeightOnlineTwo() {
        return weightOnlineTwo;
    }

    
    public void setWeightOnlineTwo(BigDecimal weightOnlineTwo) {
        this.weightOnlineTwo = weightOnlineTwo;
    }

    
    public BigDecimal getWeightDownlineTwo() {
        return weightDownlineTwo;
    }

    
    public void setWeightDownlineTwo(BigDecimal weightDownlineTwo) {
        this.weightDownlineTwo = weightDownlineTwo;
    }

    
    public BigDecimal getFeeRateTwo() {
        return feeRateTwo;
    }

    
    public void setFeeRateTwo(BigDecimal feeRateTwo) {
        this.feeRateTwo = feeRateTwo;
    }

	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}

	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}

	public String getCombBillTypeName() {
		return combBillTypeName;
	}

	public void setCombBillTypeName(String combBillTypeName) {
		this.combBillTypeName = combBillTypeName;
	}

	
	public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}

	public BigDecimal getLeftRange() {
		return leftRange;
	}

	public void setLeftRange(BigDecimal leftRange) {
		this.leftRange = leftRange;
	}

	public BigDecimal getRightRange() {
		return rightRange;
	}

	public void setRightRange(BigDecimal rightRange) {
		this.rightRange = rightRange;
	}

	public BigDecimal getFixedCosts() {
		return fixedCosts;
	}

	public void setFixedCosts(BigDecimal fixedCosts) {
		this.fixedCosts = fixedCosts;
	}

	public BigDecimal getPrices() {
		return prices;
	}

	public void setPrices(BigDecimal prices) {
		this.prices = prices;
	}

	public BigDecimal getWeightLeftRange() {
		return weightLeftRange;
	}

	public void setWeightLeftRange(BigDecimal weightLeftRange) {
		this.weightLeftRange = weightLeftRange;
	}

	public BigDecimal getWeightRightRange() {
		return weightRightRange;
	}

	public void setWeightRightRange(BigDecimal weightRightRange) {
		this.weightRightRange = weightRightRange;
	}

	public BigDecimal getVolumeLeftRange() {
		return volumeLeftRange;
	}

	public void setVolumeLeftRange(BigDecimal volumeLeftRange) {
		this.volumeLeftRange = volumeLeftRange;
	}

	public BigDecimal getVolumeRightRange() {
		return volumeRightRange;
	}

	public void setVolumeRightRange(BigDecimal volumeRightRange) {
		this.volumeRightRange = volumeRightRange;
	}
	//zb modify start
    /** 始发区域ID. */
    private String origRegionId;
    
    /** 始发区域NAME. */
    private String origRegionName;
   

	public String getOrigRegionId() {
		return origRegionId;
	}
	public void setOrigRegionId(String origRegionId) {
		this.origRegionId = origRegionId;
	}
	public String getOrigRegionName() {
		return origRegionName;
	}
	public void setOrigRegionName(String origRegionName) {
		this.origRegionName = origRegionName;
	}
	public String getCentralizeDelivery() {
		return centralizeDelivery;
	}
	public void setCentralizeDelivery(String centralizeDelivery) {
		this.centralizeDelivery = centralizeDelivery;
	}
	
	
	/**
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
    
}