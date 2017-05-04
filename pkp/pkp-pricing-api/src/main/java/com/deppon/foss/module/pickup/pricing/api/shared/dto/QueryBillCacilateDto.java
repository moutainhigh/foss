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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/QueryBillCacilateDto.java
 * 
 * FILE NAME        	: QueryBillCacilateDto.java
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

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 提供GUI客户端开单服务-查询产品价格计算的DTO
 * @author DP-Foss-YueHongJie
 * @date 2012-11-12 下午6:33:52
 */
public class QueryBillCacilateDto implements Serializable {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 7053344817924117244L;
     
    private String waybillNo;
    //是否上门发货
  	private boolean homeDelivery;
  	
    public boolean isHomeDelivery() {
		return homeDelivery;
	}
	public void setHomeDelivery(boolean homeDelivery) {
		this.homeDelivery = homeDelivery;
	}

	/**
     * 出发部门 
     */
    private String originalOrgCode;
    /** 
     * 到达部门 
     */
    private String destinationOrgCode;
    /** 
     * 产品 
     */
    private String productCode;
    /** 
     * 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
     */
    private String goodsCode;
    /**
	 * 伙伴开单
	 */
	private String partnerBillingLogo;
	
    public String getPartnerBillingLogo() {
		return partnerBillingLogo;
	}
	public void setPartnerBillingLogo(String partnerBillingLogo) {
		this.partnerBillingLogo = partnerBillingLogo;
	}

	/**
	 * 货物类型name
	 */

	private String goodsTypeName; 
    public String getGoodsTypeName() {
		return goodsTypeName;
	}
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}


	/** 
     * 营业部收货日期（可选，无则表示当前日期）,即开单日期 **/
    private Date receiveDate;
    /** 
     * 是否接货
     */
    private String isReceiveGoods;
    
    //判断是否接货字段是否变更
    private String isReceiveGoodsChange;
    
	public String getIsReceiveGoodsChange() {
		return isReceiveGoodsChange;
	}
	public void setIsReceiveGoodsChange(String isReceiveGoodsChange) {
		this.isReceiveGoodsChange = isReceiveGoodsChange;
	}
    
    
    /**
     * 是否自提
     */
    private String isSelfPickUp;
    /** 
     * 重量 
     */
    private BigDecimal weight;
    /** 
     * 体积
     */
    private BigDecimal volume;
    /**
     * 航班班次 
     */
    private String flightShift;
    /** 
     * 币种 
     */
    private String currencyCdoe;
    /** 
     * 始发价格区域 ID 
     */
    private String deptRegionId;
    /** 
     * 到达价格区域 ID 
     */
    private String arrvRegionId;
    /** 
     * 长途 还是短途  参考  PricingConstants 常量 LONG_OR_SHORT_L  LONG_OR_SHORT_S 
     */
    private String longOrShort;
    /**
     * customer code
     */
    private String customerCode;
    /**
     * industrul code
     */
    private String industrulCode;
    
    /**
     * 是否月结
     */
    private String isMonthlyDate;
    
    /**
     * 最后一个组织编码
     */
    private String lastOrgCode;
    /**
     * 大礼包方案CODE
     */
    private String cityMarketCode;
    /**
     * 偏线网点名称
     */
    private String agentDeptName;
    
	/**
     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140507 MANA-1253 新增
    
    /**
     * 判断是否是当前合同客户的当前价格版本
     * */
    private String isCurrContract;//需求：降价发券    新增
    
    

	public String getIsCurrContract() {
		return isCurrContract;
	}


	public void setIsCurrContract(String isCurrContract) {
		this.isCurrContract = isCurrContract;
	}


	public String getAgentDeptName() {
		return agentDeptName;
	}


	public void setAgentDeptName(String agentDeptName) {
		this.agentDeptName = agentDeptName;
	}


	public String getLastOrgCode() {
		return lastOrgCode;
	}


	public void setLastOrgCode(String lastOrgCode) {
		this.lastOrgCode = lastOrgCode;
	}


	public String getIsMonthlyDate() {
		return isMonthlyDate;
	}


	public void setIsMonthlyDate(String isMonthlyDate) {
		this.isMonthlyDate = isMonthlyDate;
	}


	/**
     * 获取 渠道code.
     *
     * @return the 渠道code
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * 经济自提件
     */
    @SuppressWarnings("unused")
	private String economySince;
    
    //营销活动DTO
    private MarkActivitiesQueryConditionDto activeDto;
    
    //是否要计算营销活动折扣（默认为否）
    private boolean isCalActiveDiscount = false;
    
    //活动编码
    private String activeCode;
    //开单品名
    private String goodsName;
    //发货客户编码
    private String deliveryCustomerCode; 
    //专业市场活动客户编码 316759-王瑞鹏 2017年02月21日 17:21:15
    private String activeCustomerCode;
    //订单来源
    private String orderChannel;
    //收货部门
    private String receiveOrgCode;
    //出发外场
    private String loadOrgCode;
    //到达外场
    private String lastOutLoadOrgCode;
    //到达部门
    private String customerPickupOrgCode;
    //开单时间
    private Date billTime;
    //纯运费
    private BigDecimal transportFee;
    //重量
    private BigDecimal goodsWeightTotal;
    //体积
    private BigDecimal goodsVolumeTotal;
    
    //是否为PDA计算运费
    private boolean isPda = false;
    
    //是否需要提示未享有活动折扣
    private boolean isAlertForAct=false;    
    
	public boolean isAlertForAct() {
		return isAlertForAct;
	}

	public void setAlertForAct(boolean isAlertForAct) {
		this.isAlertForAct = isAlertForAct;
	}
    
	public boolean isPda() {
		return isPda;
	}

	public void setPda(boolean isPda) {
		this.isPda = isPda;
	}


	public String getActiveCode() {
		return activeCode;
	}


	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}


	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getActiveCustomerCode() {
		return activeCustomerCode;
	}
	
	public void setActiveCustomerCode(String activeCustomerCode) {
		this.activeCustomerCode = activeCustomerCode;
	}
	
	public String getOrderChannel() {
		return orderChannel;
	}


	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}


	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}


	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}


	public String getLoadOrgCode() {
		return loadOrgCode;
	}


	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	public String getLastOutLoadOrgCode() {
		return lastOutLoadOrgCode;
	}

	public void setLastOutLoadOrgCode(String lastOutLoadOrgCode) {
		this.lastOutLoadOrgCode = lastOutLoadOrgCode;
	}


	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}


	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}


	public Date getBillTime() {
		return billTime;
	}


	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}


	public BigDecimal getTransportFee() {
		return transportFee;
	}


	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}


	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}


	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}


	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}


	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}


	public boolean isCalActiveDiscount() {
		return isCalActiveDiscount;
	}

	public void setCalActiveDiscount(boolean isCalActiveDiscount) {
		this.isCalActiveDiscount = isCalActiveDiscount;
	}
    
	public MarkActivitiesQueryConditionDto getActiveDto() {
		return activeDto;
	}

	public void setActiveDto(MarkActivitiesQueryConditionDto activeDto) {
		this.activeDto = activeDto;
	}


    /**
     * 经济自提件
     */
	public String getEconomySince() {
//		return economySince;
		//自提件取消上线，设置为不显示
		return FossConstants.NO;
	}


    /**
     * 经济自提件
     */
	public void setEconomySince(String economySince) {
//		this.economySince = economySince;
		//自提件取消上线，设置为不显示
		this.economySince = FossConstants.NO;
	}

	/**
     * 设置 渠道code.
     *
     * @param channelCode the new 渠道code
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * 渠道code
     */
    private String channelCode;
    
    /**
     * 获取 customer code.
     *
     * @return the customer code
     */
    public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 设置 customer code.
	 *
	 * @param customerCode the new customer code
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
    
	/**
	 * 获取 industrul code.
	 *
	 * @return the industrul code
	 */
	public String getIndustrulCode() {
		if(StringUtil.isEmpty(industrulCode))
		{
			return PricingConstants.ALL;
		}else
		{
			return industrulCode;
		}
		
	}

	/**
	 * 设置 industrul code.
	 *
	 * @param industrulCode the new industrul code
	 */
	public void setIndustrulCode(String industrulCode) {
		this.industrulCode = industrulCode;
	}

	/**
	 * 获取 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getOriginalOrgCode() {
        return originalOrgCode;
    }
    
    /**
     * 设置 出发部门.
     *
     * @param originalOrgCode the new 出发部门
     */
    public void setOriginalOrgCode(String originalOrgCode) {
        this.originalOrgCode = originalOrgCode;
    }
    
    /**
     * 获取 到达部门.
     *
     * @return the 到达部门
     */
    public String getDestinationOrgCode() {
        return destinationOrgCode;
    }
    
    /**
     * 设置 到达部门.
     *
     * @param destinationOrgCode the new 到达部门
     */
    public void setDestinationOrgCode(String destinationOrgCode) {
        this.destinationOrgCode = destinationOrgCode;
    }
    
    /**
     * 获取 产品.
     *
     * @return the 产品
     */
    public String getProductCode() {
        return productCode;
    }
    
    /**
     * 设置 产品.
     *
     * @param productCode the new 产品
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    /**
     * 获取 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）.
     *
     * @return the 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
     */
    public String getGoodsCode() {
        return goodsCode;
    }
    
    /**
     * 设置 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）.
     *
     * @param goodsCode the new 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
    
    /**
     * 获取 营业部收货日期（可选，无则表示当前日期）,即开单日期 *.
     *
     * @return the 营业部收货日期（可选，无则表示当前日期）,即开单日期 *
     */
    public Date getReceiveDate() {
        return receiveDate;
    }
    
    /**
     * 设置 营业部收货日期（可选，无则表示当前日期）,即开单日期 *.
     *
     * @param receiveDate the new 营业部收货日期（可选，无则表示当前日期）,即开单日期 *
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
    
    /**
     * 获取 是否接货.
     *
     * @return the 是否接货
     */
    public String getIsReceiveGoods() {
        return isReceiveGoods;
    }
    
    /**
     * 设置 是否接货.
     *
     * @param isReceiveGoods the new 是否接货
     */
    public void setIsReceiveGoods(String isReceiveGoods) {
        this.isReceiveGoods = isReceiveGoods;
    }
    
    /**
     * 获取 重量.
     *
     * @return the 重量
     */
    public BigDecimal getWeight() {
        return weight;
    }
    
    /**
     * 设置 重量.
     *
     * @param weight the new 重量
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    /**
     * 获取 体积.
     *
     * @return the 体积
     */
    public BigDecimal getVolume() {
        return volume;
    }
    
    /**
     * 设置 体积.
     *
     * @param volume the new 体积
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    
    /**
     * 获取 航班班次.
     *
     * @return the 航班班次
     */
    public String getFlightShift() {
        return flightShift;
    }
    
    /**
     * 设置 航班班次.
     *
     * @param flightShift the new 航班班次
     */
    public void setFlightShift(String flightShift) {
        this.flightShift = flightShift;
    }
    
    /**
     * 获取 币种.
     *
     * @return the 币种
     */
    public String getCurrencyCdoe() {
        return currencyCdoe;
    }
    
    /**
     * 设置 币种.
     *
     * @param currencyCdoe the new 币种
     */
    public void setCurrencyCdoe(String currencyCdoe) {
        this.currencyCdoe = currencyCdoe;
    }


	/**
	 * 获取 始发价格区域 ID.
	 *
	 * @return the 始发价格区域 ID
	 */
	public String getDeptRegionId() {
		return deptRegionId;
	}


	/**
	 * 设置 始发价格区域 ID.
	 *
	 * @param deptRegionId the new 始发价格区域 ID
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}


	/**
	 * 获取 到达价格区域 ID.
	 *
	 * @return the 到达价格区域 ID
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}


	/**
	 * 设置 到达价格区域 ID.
	 *
	 * @param arrvRegionId the new 到达价格区域 ID
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}


	/**
	 * 获取 长途 还是短途  参考  PricingConstants 常量 LONG_OR_SHORT_L  LONG_OR_SHORT_S.
	 *
	 * @return the 长途 还是短途  参考  PricingConstants 常量 LONG_OR_SHORT_L  LONG_OR_SHORT_S
	 */
	public String getLongOrShort() {
		return longOrShort;
	}


	/**
	 * 设置 长途 还是短途  参考  PricingConstants 常量 LONG_OR_SHORT_L  LONG_OR_SHORT_S.
	 *
	 * @param longOrShort the new 长途 还是短途  参考  PricingConstants 常量 LONG_OR_SHORT_L  LONG_OR_SHORT_S
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}


	
	public String getIsSelfPickUp() {
	    return isSelfPickUp;
	}


	
	public void setIsSelfPickUp(String isSelfPickUp) {
	    this.isSelfPickUp = isSelfPickUp;
	}


	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}


	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}
	public String getCityMarketCode() {
		return cityMarketCode;
	}


	public void setCityMarketCode(String cityMarketCode) {
		this.cityMarketCode = cityMarketCode;
	}
	
	//菜鸟新需求返货折扣
    private Boolean isCainiao;//是否满足条件
    private String returnWaybillNo;//原始单号
    private String oldreceiveCustomerCode;//原收货人的客户编码
    private Date returnbilltime;//原始开单时间
    private BigDecimal returnTransportFee;//原单公布价费
    private BigDecimal returnInsuranceFee;//原单保价费
    private String OriginalReceiveOrgCode;//原单号收货部门编码
     
    /**
   	 * 内部员工发货
   	 * dp-foss-dongjialing
   	 * 225131
   	 */
   	//内部发货类型
   	private String  internalDeliveryType;
   	//员工号
   	private String  employeeNo;
    //当前月份的优惠总额
  	private BigDecimal amount;
  	
   //当前月份的发货总金额
  	private BigDecimal totalAmount;
  	
  	//更改前的折扣率
  	private BigDecimal originalRate;
  	public BigDecimal getOriginalRate() {
		return originalRate;
	}
	public void setOriginalRate(BigDecimal originalRate) {
		this.originalRate = originalRate;
	}
	public BigDecimal getTotalAmount() {
  		return totalAmount;
  	}
  	public void setTotalAmount(BigDecimal totalAmount) {
  		this.totalAmount = totalAmount;
  	}
  	public BigDecimal getAmount() {
  		return amount;
  	}

  	public void setAmount(BigDecimal amount) {
  		this.amount = amount;
  	}
   	public String getInternalDeliveryType() {
   		return internalDeliveryType;
   	}

   	public void setInternalDeliveryType(String internalDeliveryType) {
   		this.internalDeliveryType = internalDeliveryType;
   	}

   	public String getEmployeeNo() {
   		return employeeNo;
   	}

   	public void setEmployeeNo(String employeeNo) {
   		this.employeeNo = employeeNo;
   	}
	public BigDecimal getReturnTransportFee() {
		return returnTransportFee;
	}

	public void setReturnTransportFee(BigDecimal returnTransportFee) {
		this.returnTransportFee = returnTransportFee;
	}

	

	public BigDecimal getReturnInsuranceFee() {
		return returnInsuranceFee;
	}

	public void setReturnInsuranceFee(BigDecimal returnInsuranceFee) {
		this.returnInsuranceFee = returnInsuranceFee;
	}
	public Date getReturnbilltime() {
		return returnbilltime;
	}

	public void setReturnbilltime(Date returnbilltime) {
		this.returnbilltime = returnbilltime;
	}

    
   	public String getOldreceiveCustomerCode() {
   		return oldreceiveCustomerCode;
   	}

   	public void setOldreceiveCustomerCode(String oldreceiveCustomerCode) {
   		this.oldreceiveCustomerCode = oldreceiveCustomerCode;
   	}
    
	public Boolean getIsCainiao() {
		return isCainiao;
	}


	public void setIsCainiao(Boolean isCainiao) {
		this.isCainiao = isCainiao;
	}


	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}


	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}


	public String getOriginalReceiveOrgCode() {
		return OriginalReceiveOrgCode;
	}


	public void setOriginalReceiveOrgCode(String originalReceiveOrgCode) {
		OriginalReceiveOrgCode = originalReceiveOrgCode;
	}
	private Boolean isExhibitCargo;

	public Boolean getIsExhibitCargo() {
		return isExhibitCargo;
	}
	public void setIsExhibitCargo(Boolean isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}