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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/QueryBillCacilateValueAddDto.java
 * 
 * FILE NAME        	: QueryBillCacilateValueAddDto.java
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
import java.util.Map;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;


/**
 * 
 * 提供GUI客户端开单服务-查询产品价格增值服务计算的DTO
 * @author DP-Foss-YueHongJie
 * @date 2012-11-12 下午6:33:52
 */
public class QueryBillCacilateValueAddDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7369429955549354376L;
    private String waybillNo;
    
	//保费是否经行后台折扣---206860
	private boolean isCalDiscount;	
	
	public boolean isCalDiscount() {
		return isCalDiscount;
	}
	public void setCalDiscount(boolean isCalDiscount) {
		this.isCalDiscount = isCalDiscount;
	}
	
    //是否获取保价费率---206860
    private Boolean isGetInsuranceRate;

    public Boolean getIsGetInsuranceRate() {
		return isGetInsuranceRate;
	}
	public void setIsGetInsuranceRate(Boolean isGetInsuranceRate) {
		this.isGetInsuranceRate = isGetInsuranceRate;
	}
	
	 /**
     * 最低费率--目前只有保费使用
     */
    private BigDecimal minFeeRate;
    
    /**
     * 最高费率--目前只有保费使用
     */
    private BigDecimal maxFeeRate;
    public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}
	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
	}
	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}
	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}

    
    //客户端传入参数
    /** 
     * 出发部门CODE 
     */
    private String originalOrgCode;
    /** 
     * 到达部门CODE 
     */
    private String destinationOrgCode;
    /** 
     * 产品CODE 
     */
    private String productCode;
    /**  
     * 营业部收货日期（可选，无则表示当前日期） 
     */
    private Date receiveDate;
    /**
     * 重量 
     */
    private BigDecimal weight;
    /** 
     * 体积
     */
    private BigDecimal volume;
    /** 
     * 原始费用 
     */
    private BigDecimal originnalCost;
    
    /** 
     * 公里计费 
     */
    private BigDecimal kilom;
     /**
      * 是否接货
      */
    private String isReceiveGoods;
    /** 
     * 航班号 
     */
    private String flightShift;
    /** 
     * 长途 还是短途  参考  PricingConstants 常量 LONG_OR_SHORT_L  LONG_OR_SHORT_S 
     */
    private String longOrShort;
    /** 
     * 子类型   对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单）
	 * 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）
     */
    private String subType;
    /**
     * 币种 
     */
    private String currencyCdoe;
    
    //根据前台参数处理后的特殊参数,前台不需要传入,以下参数由业务处理
    /** 
     * 始发价格区域 ID 
     */
    private String deptRegionId;
    /** 
     * 到达价格区域 ID
     */
    private String arrvRegionId;
    /** 
     * 计费规则类型
     */
    private String type;
    
  //liyongfei 新增计费方式
    private  String caculateType;
    /** 
     * 激活标志
     */
    private String active;
    /**
     *  货物类型code
     */
    private String goodsTypeCode;
    /** 
     * 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义
     */
    private String pricingEntryCode;
    /**
     * 计价名称
     */
    private String pricingEntryName;
    /**
     * 全网点
     */
    private String allNet;
    /**
     * customer code
     */
    private String customerCode;
    
    /**
     * 渠道code
     */
    private String channelCode;
    
    /**
     * industrul code
     */
    private String industrulCode;
    
    /**
     *  打木箱
     */
    private BigDecimal volumeBox;
    /**
     * 打木架
     */
    private BigDecimal volumeFrame;
    /**
     * 大礼包code
     */
    private String cityMarketCode;
    
    /** 费率  目前只有整车计算保价费需要使用  */
    private BigDecimal feeRate;
    
    
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
    /**
     * 行业code
     */
    private String industryCode;
    
    /**
     * 业务发生时间
     */
    private String businessTime;
    //是否为PDA计算运费
    private boolean isPda = false;
    
    
    //是否需要提示未享有活动折扣
    private boolean isAlertForAct=false;    
    
  //是否图片开单
    private Boolean isPicWayBill;   
    

	public Boolean getIsPicWayBill() {
		return isPicWayBill;
	}

	public void setIsPicWayBill(Boolean isPicWayBill) {
		this.isPicWayBill = isPicWayBill;
	}

	public String getIsReceiveGoods() {
		return isReceiveGoods;
	}

	public void setIsReceiveGoods(String isReceiveGoods) {
		this.isReceiveGoods = isReceiveGoods;
	}

	public boolean isAlertForAct() {
		return isAlertForAct;
	}

	public String getCaculateType() {
		return caculateType;
	}

	
	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
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

    
    
    public String getCityMarketCode() {
		return cityMarketCode;
	}


	public void setCityMarketCode(String cityMarketCode) {
		this.cityMarketCode = cityMarketCode;
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
     * 设置 渠道code.
     *
     * @param channelCode the new 渠道code
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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
	 * 获取 全网点.
	 *
	 * @return the 全网点
	 */
	public String getAllNet() {
        return allNet;
    }
    
    /**
     * 设置 全网点.
     *
     * @param allNet the new 全网点
     */
    public void setAllNet(String allNet) {
        this.allNet = allNet;
    }



    /**
     * 获取 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义.
     *
     * @return the 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义
     */
    public String getPricingEntryCode() {
        return pricingEntryCode;
    }


    
    /**
     * 设置 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义.
     *
     * @param pricingEntryCode the new 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义
     */
    public void setPricingEntryCode(String pricingEntryCode) {
        this.pricingEntryCode = pricingEntryCode;
    }


    
    /**
     * 获取 计价名称.
     *
     * @return the 计价名称
     */
    public String getPricingEntryName() {
        return pricingEntryName;
    }


    
    /**
     * 设置 计价名称.
     *
     * @param pricingEntryName the new 计价名称
     */
    public void setPricingEntryName(String pricingEntryName) {
        this.pricingEntryName = pricingEntryName;
    }
    
    /**
     * 获取 货物类型code.
     *
     * @return the 货物类型code
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }

    
    /**
     * 设置 货物类型code.
     *
     * @param goodsTypeCode the new 货物类型code
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }

    /**
     * 设置 激活标志.
     *
     * @param active the new 激活标志
     */
    public void setActive(String active) {
        this.active = active;
    }
   
    /**
     * 获取 激活标志.
     *
     * @return the 激活标志
     */
    public String getActive() {
        return active;
    }

    /**
     * 获取 计费规则类型.
     *
     * @return the 计费规则类型
     */
    public String getType() {
        return type;
    }


    
    /**
     * 设置 计费规则类型.
     *
     * @param type the new 计费规则类型
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取 出发部门CODE.
     *
     * @return the 出发部门CODE
     */
    public String getOriginalOrgCode() {
        return originalOrgCode;
    }
    
    /**
     * 设置 出发部门CODE.
     *
     * @param originalOrgCode the new 出发部门CODE
     */
    public void setOriginalOrgCode(String originalOrgCode) {
        this.originalOrgCode = originalOrgCode;
    }
    
    /**
     * 获取 到达部门CODE.
     *
     * @return the 到达部门CODE
     */
    public String getDestinationOrgCode() {
        return destinationOrgCode;
    }
    
    /**
     * 设置 到达部门CODE.
     *
     * @param destinationOrgCode the new 到达部门CODE
     */
    public void setDestinationOrgCode(String destinationOrgCode) {
        this.destinationOrgCode = destinationOrgCode;
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
     * 获取 营业部收货日期（可选，无则表示当前日期）.
     *
     * @return the 营业部收货日期（可选，无则表示当前日期）
     */
    public Date getReceiveDate() {
        return receiveDate;
    }
    
    /**
     * 设置 营业部收货日期（可选，无则表示当前日期）.
     *
     * @param receiveDate the new 营业部收货日期（可选，无则表示当前日期）
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
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
     * 获取 原始费用.
     *
     * @return the 原始费用
     */
    public BigDecimal getOriginnalCost() {
        return originnalCost;
    }
    
    /**
     * 设置 原始费用.
     *
     * @param originnalCost the new 原始费用
     */
    public void setOriginnalCost(BigDecimal originnalCost) {
        this.originnalCost = originnalCost;
    }
    
    /**
     * 获取 航班号.
     *
     * @return the 航班号
     */
    public String getFlightShift() {
        return flightShift;
    }
    
    /**
     * 设置 航班号.
     *
     * @param flightShift the new 航班号
     */
    public void setFlightShift(String flightShift) {
        this.flightShift = flightShift;
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
    
    /**
     * 获取 子类型   对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）.
     *
     * @return the 子类型   对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）
     */
    public String getSubType() {
        return subType;
    }
    
    /**
     * 设置 子类型   对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）.
     *
     * @param subType the new 子类型   对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）
     */
    public void setSubType(String subType) {
        this.subType = subType;
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
     * 获取 公里计费.
     *
     * @return the 公里计费
     */
    public BigDecimal getKilom() {
        return kilom;
    }


    
    /**
     * 设置 公里计费.
     *
     * @param kilom the new 公里计费
     */
    public void setKilom(BigDecimal kilom) {
        this.kilom = kilom;
    }


	/**
	 * 获取 打木箱.
	 *
	 * @return the 打木箱
	 */
	public BigDecimal getVolumeBox() {
		return volumeBox;
	}


	/**
	 * 设置 打木箱.
	 *
	 * @param volumeBox the new 打木箱
	 */
	public void setVolumeBox(BigDecimal volumeBox) {
		this.volumeBox = volumeBox;
	}


	/**
	 * 获取 打木架.
	 *
	 * @return the 打木架
	 */
	public BigDecimal getVolumeFrame() {
		return volumeFrame;
	}


	/**
	 * 设置 打木架.
	 *
	 * @param volumeFrame the new 打木架
	 */
	public void setVolumeFrame(BigDecimal volumeFrame) {
		this.volumeFrame = volumeFrame;
	}

	/**
	 * 获取费率  目前只有整车计算保价费需要使用
	 * @return
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}

	/**
	 * 设置费率  目前只有整车计算保价费需要使用
	 * @param feeRate
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	//定价项目POP-407foss没有将接货金额、保费金额5个字段传给规则引擎
	 /**
     * 代收货款和保险声明价值 
     * */
    private Map valuedtos;
	/**
	 * 将纸纤包装界面vo的值传入
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-30下午16:35
	 */
	//纸箱一号客户
	private Integer paperBoxOne;
	//纸箱二号客户
	private Integer paperBoxTwo;
	//纸箱三号客户
	private Integer paperBoxThree;
	//纸箱四号客户
	private Integer paperBoxFour;
	//纤袋1号 蓝
	private Integer fibelBagBlueOne;
	//纤袋2号 蓝
	private Integer fibelBagBlueTwo;
	//纤袋3号 蓝
	private Integer fibelBagBlueThree;
	//纤袋4号 蓝
	private Integer fibelBagBlueFour;
	//纤袋1号 白
	private Integer fibelBagWhiteOne;
	//纤袋2号 白
	private Integer fibelBagWhiteTwo;
	//纤袋3号 白
	private Integer fibelBagWhiteThree;
	//纤袋4号 白
	private Integer fibelBagWhiteFour;
	//纤袋5号 布匹 白
	private Integer fibelBagWhiteFiveCloth;
	//纤袋6号 布匹 白
	private Integer fibelBagWhiteSixCloth;

	public Integer getPaperBoxOne() {
		return paperBoxOne;
	}

	public void setPaperBoxOne(Integer paperBoxOne) {
		this.paperBoxOne = paperBoxOne;
	}

	public Integer getPaperBoxTwo() {
		return paperBoxTwo;
	}

	public void setPaperBoxTwo(Integer paperBoxTwo) {
		this.paperBoxTwo = paperBoxTwo;
	}

	public Integer getPaperBoxThree() {
		return paperBoxThree;
	}

	public void setPaperBoxThree(Integer paperBoxThree) {
		this.paperBoxThree = paperBoxThree;
	}

	public Integer getFibelBagBlueOne() {
		return fibelBagBlueOne;
	}

	public void setFibelBagBlueOne(Integer fibelBagBlueOne) {
		this.fibelBagBlueOne = fibelBagBlueOne;
	}

	public Integer getFibelBagBlueTwo() {
		return fibelBagBlueTwo;
	}

	public void setFibelBagBlueTwo(Integer fibelBagBlueTwo) {
		this.fibelBagBlueTwo = fibelBagBlueTwo;
	}

	public Integer getFibelBagBlueThree() {
		return fibelBagBlueThree;
	}

	public void setFibelBagBlueThree(Integer fibelBagBlueThree) {
		this.fibelBagBlueThree = fibelBagBlueThree;
	}

	public Integer getFibelBagBlueFour() {
		return fibelBagBlueFour;
	}

	public void setFibelBagBlueFour(Integer fibelBagBlueFour) {
		this.fibelBagBlueFour = fibelBagBlueFour;
	}

	public Integer getFibelBagWhiteOne() {
		return fibelBagWhiteOne;
	}

	public void setFibelBagWhiteOne(Integer fibelBagWhiteOne) {
		this.fibelBagWhiteOne = fibelBagWhiteOne;
	}

	public Integer getFibelBagWhiteTwo() {
		return fibelBagWhiteTwo;
	}

	public void setFibelBagWhiteTwo(Integer fibelBagWhiteTwo) {
		this.fibelBagWhiteTwo = fibelBagWhiteTwo;
	}

	public Integer getFibelBagWhiteThree() {
		return fibelBagWhiteThree;
	}

	public void setFibelBagWhiteThree(Integer fibelBagWhiteThree) {
		this.fibelBagWhiteThree = fibelBagWhiteThree;
	}

	public Integer getFibelBagWhiteFour() {
		return fibelBagWhiteFour;
	}

	public void setFibelBagWhiteFour(Integer fibelBagWhiteFour) {
		this.fibelBagWhiteFour = fibelBagWhiteFour;
	}

	public Integer getFibelBagWhiteFiveCloth() {
		return fibelBagWhiteFiveCloth;
	}

	public void setFibelBagWhiteFiveCloth(Integer fibelBagWhiteFiveCloth) {
		this.fibelBagWhiteFiveCloth = fibelBagWhiteFiveCloth;
	}

	public Integer getFibelBagWhiteSixCloth() {
		return fibelBagWhiteSixCloth;
	}

	public void setFibelBagWhiteSixCloth(Integer fibelBagWhiteSixCloth) {
		this.fibelBagWhiteSixCloth = fibelBagWhiteSixCloth;
	}

	public Integer getPaperBoxFour() {
		return paperBoxFour;
	}

	public void setPaperBoxFour(Integer paperBoxFour) {
		this.paperBoxFour = paperBoxFour;
	}

	//菜鸟新需求返货折扣
    private Boolean isCainiao;//是否满足条件
    private String returnWaybillNo;//原始单号
    private String oldreceiveCustomerCode;//原收货人的客户编码
    private Date returnbilltime;//原始开单时间
    private BigDecimal returnTransportFee;//原单公布价费
    private BigDecimal returnInsuranceFee;//原单保价费
    private String OriginalReceiveOrgCode;//原单号收货部门编码
     
	
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
	/**
	 * 设置代收货款和保险声明价值 .
	 *
	 * @param woodenVolume the new 木架体积
	 */
	public Map getValuedtos() {
		return valuedtos;
	}
	/**
	 * 设置代收货款和保险声明价值 .
	 *
	 * @param woodenVolume the new 木架体积
	 */
	public void setValuedtos(Map valuedtos) {
		this.valuedtos = valuedtos;
	}
	 /**
   	 * 内部员工发货
   	 * dp-foss-dongjialing
   	 * 225131
   	 */
   	//内部发货类型
   	private String  internalDeliveryType;
   	//员工号
   	private String  employeeNo;
   	//特殊增值服务类型
   	private String specialValueAddedServiceType;
   	
   	public String getSpecialValueAddedServiceType() {
		return specialValueAddedServiceType;
	}
	public void setSpecialValueAddedServiceType(String specialValueAddedServiceType) {
		this.specialValueAddedServiceType = specialValueAddedServiceType;
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
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}