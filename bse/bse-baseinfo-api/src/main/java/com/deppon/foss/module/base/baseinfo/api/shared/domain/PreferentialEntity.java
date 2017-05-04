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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/PreferentialEntity.java
 * 
 * FILE NAME        	: PreferentialEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 客户优惠信息实体类.
 *
 * @author 094463-foss-xieyantao
 * @date 2012-11-22 上午10:04:32
 * @since
 * @version
 */
public class PreferentialEntity extends BaseEntity{
	
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6136651772406817246L;
    
    
    /**
     * 是否精准包裹
     * date:20160808
     * author:268984
     */
	 private String isAccuratePackage;
	 /**
	  * 零担重泡比参数
	  */
	 private BigDecimal lttWeightBubbleRate;
    /**
     * 是否启用.
     */
    private String active;

    /**
     * 运费折扣费率.
     */
    private BigDecimal chargeRebate;

    /**
     * 代收货款费率.
     */
    private BigDecimal agentGathRate;

    /**
     * 保价费率.
     */
    private BigDecimal insureDpriceRate;

    /**
     * 接货费率.
     */
    private BigDecimal receivePriceRate;

    /**
     * 送货费率.
     */
    private BigDecimal deliveryFeeRate;

    /**
     * 优惠信息对应的合同ID.
     */
    private BigDecimal cusBargainId;
    
    /**
     * 执行起始日期.
     */
    private Date effectiveDate;
    
    /**
     * 执行到期日期.
     */
    private Date expirationDate;

    /**
     * 在CRM中fid.
     */
    private BigDecimal crmId; 
    
    /**
     * 优惠类型(扩展).
     */
    private String preferentialType;
    
    /**
     * 所属部门标杆编码(扩展).
     */
    private String unifiedCode;
    
    /**
     * 结算方式（扩展）.
     */
    private String chargeType;
    
    
    /**
	 * 优惠所属类型(取值LTT或者EXPRESS,分别表示零担和快递)
	 */
	private String ftype;
	  
	 /**
	 * 超重货操作费折扣
	 */
	private BigDecimal overweightOperatDiscount;
	
	/**
	 * 包装费折扣
	 */
	private BigDecimal packingChargesDiscount;
	
	/**
	 * 签收单返单费折
	 */
	private BigDecimal singleSignDiscount;
	
	/**
	 * 132599-2014-10-29
	 * 代收最低手续费
	 */
	private BigDecimal lowestCharge;
	
	 /**
	  * 重泡比值
	  */
	private BigDecimal weightBubbleRate;
	
	/**
	 * 大件上楼费折扣
	 * @author 218392  张永雪
	 * @date 创建时间：2014-12-27 下午3:39:04
	 *
	 */
	private BigDecimal bigUprate;
	
	/**
	 * 续重最低费率
	 * 
	 */
	private BigDecimal continueHeavyLowestRate;
	
	/**
	 * 续重折扣
	 */
	private BigDecimal continueHeavyDiscount;
	
	/**
	 * 快递返货运费折扣类型
	 */
	private String expBackFreghtType;
	
	/**
	 * 快递返货保价折扣类型
	 */
	private String expBackCollPreType;
	
	/**
	 * 返货运费固定值
	 */
	private BigDecimal backFreghtFixed;
	
	/**
	 * 返货保价固定值
	 */
	private BigDecimal backCollFixed;
	
	/**
	 * 单票代收手续费
	 */
	private BigDecimal sinTicketCollCharge;
	
	/**
	 * 单票保价手续费
	 */
	private BigDecimal sinTicketSurePriceCharge;
	
	/**
	 * 单票包装费
	 */
	private BigDecimal sinTicketPackCharge;
	
	/**
	 * 代收退费
	 */
	private String collReturnCharge;
	
	/**
	 * 进仓派送费折扣
	 * @author 218392 zhangyongxue
	 * @date 2015-05-15 08:27:26
	 */
	private BigDecimal intoHouseDeliverDiscount;
		
	public BigDecimal getIntoHouseDeliverDiscount() {
		return intoHouseDeliverDiscount;
	}

	public void setIntoHouseDeliverDiscount(BigDecimal intoHouseDeliverDiscount) {
		this.intoHouseDeliverDiscount = intoHouseDeliverDiscount;
	}

	/**
     *  获取大件上楼费折扣
     */
    public BigDecimal getBigUprate() {
		return bigUprate;
	}

    /**
     *  设置大件上楼费折扣
     */
	public void setBigUprate(BigDecimal bigUprate) {
		this.bigUprate = bigUprate;
	}

	public BigDecimal getWeightBubbleRate() {
		return weightBubbleRate;
	}

	public void setWeightBubbleRate(BigDecimal weightBubbleRate) {
		this.weightBubbleRate = weightBubbleRate;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	/**
     * 获取 结算方式（扩展）.
     *
     * @return  the chargeType
     */
    public String getChargeType() {
        return chargeType;
    }



    
    /**
     * 设置 结算方式（扩展）.
     *
     * @param chargeType the chargeType to set
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }



    /**
     * 获取 优惠类型.
     *
     * @return  the preferentialType
     */
    public String getPreferentialType() {
        return preferentialType;
    }


    
    /**
     * 设置 优惠类型.
     *
     * @param preferentialType the preferentialType to set
     */
    public void setPreferentialType(String preferentialType) {
        this.preferentialType = preferentialType;
    }


    /**
     * 获取 执行起始日期.
     *
     * @return  the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    
    /**
     * 设置 执行起始日期.
     *
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    
    /**
     * 获取 执行到期日期.
     *
     * @return  the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    
    /**
     * 设置 执行到期日期.
     *
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * 获取 是否启用.
     *
     * @return  the active 是否启用
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set 是否启用
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 运费折扣费率.
     *
     * @return  the chargeRebate 运费折扣费率
     */
    public BigDecimal getChargeRebate() {
        return chargeRebate;
    }
    
    /**
     * 设置 运费折扣费率.
     *
     * @param chargeRebate the chargeRebate to set 运费折扣费率
     */
    public void setChargeRebate(BigDecimal chargeRebate) {
        this.chargeRebate = chargeRebate;
    }
    
    /**
     * 获取 代收货款费率.
     *
     * @return  the agentGathRate 代收货款费率
     */
    public BigDecimal getAgentGathRate() {
        return agentGathRate;
    }
    
    /**
     * 设置 代收货款费率.
     *
     * @param agentGathRate the agentGathRate to set 代收货款费率
     */
    public void setAgentGathRate(BigDecimal agentGathRate) {
        this.agentGathRate = agentGathRate;
    }
    
    /**
     * 获取 保价费率.
     *
     * @return  the insureDpriceRate 代收货款费率
     */
    public BigDecimal getInsureDpriceRate() {
        return insureDpriceRate;
    }
    
    /**
     * 设置 保价费率.
     *
     * @param insureDpriceRate the insureDpriceRate to set 代收货款费率
     */
    public void setInsureDpriceRate(BigDecimal insureDpriceRate) {
        this.insureDpriceRate = insureDpriceRate;
    }
    
    /**
     * 获取 接货费率.
     *
     * @return  the receivePriceRate 接货费率
     */
    public BigDecimal getReceivePriceRate() {
        return receivePriceRate;
    }
    
    /**
     * 设置 接货费率.
     *
     * @param receivePriceRate the receivePriceRate to set 接货费率
     */
    public void setReceivePriceRate(BigDecimal receivePriceRate) {
        this.receivePriceRate = receivePriceRate;
    }
    
    /**
     * 获取 送货费率.
     *
     * @return  the deliveryFeeRate 送货费率
     */
    public BigDecimal getDeliveryFeeRate() {
        return deliveryFeeRate;
    }
    
    /**
     * 设置 送货费率.
     *
     * @param deliveryFeeRate the deliveryFeeRate to set 送货费率
     */
    public void setDeliveryFeeRate(BigDecimal deliveryFeeRate) {
        this.deliveryFeeRate = deliveryFeeRate;
    }

	/**
	 * 获取 优惠信息对应的合同ID.
	 *
	 * @return cusBargainId
	 */
	public BigDecimal getCusBargainId() {
		return cusBargainId;
	}

	/**
	 * 设置 优惠信息对应的合同ID.
	 *
	 * @param cusBargainId the new 优惠信息对应的合同ID
	 */
	public void setCusBargainId(BigDecimal cusBargainId) {
		this.cusBargainId = cusBargainId;
	}

	/**
	 * 获取 在CRM中fid.
	 *
	 * @return crmId
	 */
	public BigDecimal getCrmId() {
		return crmId;
	}

	/**
	 * 设置 在CRM中fid.
	 *
	 * @param crmId the new 在CRM中fid
	 */
	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}
	
	/**
	 * 获取超重货操作费折扣
	 * @return
	 */
	public BigDecimal getOverweightOperatDiscount() {
		return overweightOperatDiscount;
	}
	
	/**
	 * 设置超重货操作费折扣
	 * @param overweightOperatDiscount
	 */
	public void setOverweightOperatDiscount(BigDecimal overweightOperatDiscount) {
		this.overweightOperatDiscount = overweightOperatDiscount;
	}
	
	/**
	 * 获取包装费折扣
	 * @return
	 */
	public BigDecimal getPackingChargesDiscount() {
		return packingChargesDiscount;
	}
	
	/**
	 * 设置包装费折扣
	 * @param packingChargesDiscount
	 */
	public void setPackingChargesDiscount(BigDecimal packingChargesDiscount) {
		this.packingChargesDiscount = packingChargesDiscount;
	}
	
	/**
	 * 获取签收单返单费折扣
	 * @return
	 */
	public BigDecimal getSingleSignDiscount() {
		return singleSignDiscount;
	}
	
	/**
	 * 设置签收单返单费折扣
	 * @param singleSignDiscount
	 */
	public void setSingleSignDiscount(BigDecimal singleSignDiscount) {
		this.singleSignDiscount = singleSignDiscount;
	}

	
	/**
     * 获取 所属部门标杆编码(扩展).
     *
     * @return  the unifiedCode
     */
    public String getUnifiedCode() {
        return unifiedCode;
    }
    
    /**
     * 设置 所属部门标杆编码(扩展).
     *
     * @param unifiedCode the unifiedCode to set
     */
    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
    }
    
    /**
     * 获取代收最低手续费
     */
	public BigDecimal getLowestCharge() {
		return lowestCharge;
	}
	
	/**
	 * 设置代收最低手续费
	 * @param lowestCharge
	 */
	public void setLowestCharge(BigDecimal lowestCharge) {
		this.lowestCharge = lowestCharge;
	}
	
	/**
	 * 获取续重最低费率
	 * @return
	 */
	public BigDecimal getContinueHeavyLowestRate() {
		return continueHeavyLowestRate;
	}
	
	/**
	 * 设置自续重最低费率
	 * @param continueHeavyLowestRate
	 */
	public void setContinueHeavyLowestRate(BigDecimal continueHeavyLowestRate) {
		this.continueHeavyLowestRate = continueHeavyLowestRate;
	}
	
	/**
	 * 获取续重折扣
	 * @return
	 */
	public BigDecimal getContinueHeavyDiscount() {
		return continueHeavyDiscount;
	}
	
	/**
	 * 设置续重折扣
	 * @param continueHeavyDiscount
	 */
	public void setContinueHeavyDiscount(BigDecimal continueHeavyDiscount) {
		this.continueHeavyDiscount = continueHeavyDiscount;
	}


	public BigDecimal getBackFreghtFixed() {
		return backFreghtFixed;
	}

	public void setBackFreghtFixed(BigDecimal backFreghtFixed) {
		this.backFreghtFixed = backFreghtFixed;
	}

	public BigDecimal getBackCollFixed() {
		return backCollFixed;
	}

	public void setBackCollFixed(BigDecimal backCollFixed) {
		this.backCollFixed = backCollFixed;
	}

	public BigDecimal getSinTicketCollCharge() {
		return sinTicketCollCharge;
	}

	public void setSinTicketCollCharge(BigDecimal sinTicketCollCharge) {
		this.sinTicketCollCharge = sinTicketCollCharge;
	}

	public BigDecimal getSinTicketSurePriceCharge() {
		return sinTicketSurePriceCharge;
	}

	public void setSinTicketSurePriceCharge(BigDecimal sinTicketSurePriceCharge) {
		this.sinTicketSurePriceCharge = sinTicketSurePriceCharge;
	}

	public BigDecimal getSinTicketPackCharge() {
		return sinTicketPackCharge;
	}

	public void setSinTicketPackCharge(BigDecimal sinTicketPackCharge) {
		this.sinTicketPackCharge = sinTicketPackCharge;
	}


	public String getExpBackFreghtType() {
		return expBackFreghtType;
	}

	public void setExpBackFreghtType(String expBackFreghtType) {
		this.expBackFreghtType = expBackFreghtType;
	}

	public String getExpBackCollPreType() {
		return expBackCollPreType;
	}

	public void setExpBackCollPreType(String expBackCollPreType) {
		this.expBackCollPreType = expBackCollPreType;
	}

	public String getCollReturnCharge() {
		return collReturnCharge;
	}

	public void setCollReturnCharge(String collReturnCharge) {
		this.collReturnCharge = collReturnCharge;
	}


	public String getIsAccuratePackage() {
		return isAccuratePackage;
	}

	public void setIsAccuratePackage(String isAccuratePackage) {
		this.isAccuratePackage = isAccuratePackage;
	}

	public BigDecimal getLttWeightBubbleRate() {
		return lttWeightBubbleRate;
	}

	public void setLttWeightBubbleRate(BigDecimal lttWeightBubbleRate) {
		this.lttWeightBubbleRate = lttWeightBubbleRate;
	}	
}