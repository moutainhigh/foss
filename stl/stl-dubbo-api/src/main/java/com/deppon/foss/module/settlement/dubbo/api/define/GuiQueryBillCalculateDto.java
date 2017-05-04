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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PdaQueryBillCalculateDto.java
 * 
 * FILE NAME        	: PdaQueryBillCalculateDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.dubbo.api.define;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.util.define.FossConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiQueryBillCalculateDto.
 *
 * @Description: 提供GUI客户端开单服务-查询产品价格计算的DTO
 * PdaQueryBillCalculateDto.java Create on 2013-1-14 上午10:11:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class GuiQueryBillCalculateDto implements Serializable { 
    
    /** The Constant serialVersionUID. */
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

	//保费是否经行后台折扣---206860
  	private boolean isCalDiscount;	
  	
  	public boolean isCalDiscount() {
  		return isCalDiscount;
  	}
  	public void setCalDiscount(boolean isCalDiscount) {
  		this.isCalDiscount = isCalDiscount;
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

	/**
     * 该接口的使用者(官网：OWS CC:CC)
     */
    private String users;
    
    /**
     * 出发区县code(目前官网使用)
     */
    private String startCountyCode;
    
    /**
     * 出发城市code(目前官网使用)
     */
    private String startCityCode;
    /**
     * 出发省份code(目前官网使用)
     */
    private String startProvCode;
    /**
     * 到达区县code(目前官网使用)
     */
    private String arriveCountyCode;
    /**
     * 到达城市code(目前官网使用)
     */
    private String arriveCityCode;
    /**
     * 到达省份code(目前官网使用)
     */
    private String arriveProvCode;
    
    
    /** 出发部门. */
    private String originalOrgCode;
    
    /** 到达部门. */
    private String destinationOrgCode;
    
    /** 产品编号. */
    private String productCode;
    
    /** 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）. */
    private String goodsCode;
    
    /** 营业部收货日期（可选，无则表示当前日期）,即开单日期. */
    private Date receiveDate;
    
    /** 是否接货. */
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
    
    /** 重量. */
    private BigDecimal weight;
    
    /** 体积. */
    private BigDecimal volume;
    
    /** 航班班次. */
    private String flightShift;
    
    /** 币种. */
    private String currencyCdoe;
    
    /** 客户编码. */
    private String customerCode;
    
    /** 所属行业编码. */
    private String industrulCode;
    
    /** 渠道code. */
    private String channelCode;
    
    /** 计价条目列表. */
	private List<GuiQueryBillCalculateSubDto> priceEntities;
    //***********************************************
    /** 出发区域ID. */
    private String deptRegionId;
    
    /** 到达区域ID. */
    private String arrvRegionId;
    
    /** 长短途. */
    private String longOrShort;
    
    /** 计价条目. */
    private String pricingEntryCode;
    
    /** 子类型. */
    private String subType;
    
    /** 原始费用. */
    private BigDecimal originnalCost; 
    
    /** 打木架体积. */
    private BigDecimal woodenVolume; 
    
    /** 公里计费. */
    private BigDecimal kilom;
    
    /** 是否经济自提件. */
    private String economySince;
    /**
     * 大礼包方案CODE
     */
    private String cityMarketCode;

    /** 最后一个自有公司OrgCode. */
    private String lastOrgCode; 
    
    /** 费率  目前只有整车计算保价费需要使用  */
    private BigDecimal feeRate;
    
	 /**
     * 合票类型
     */
    private String combBillTypeCode;	//zxy 20140507 MANA-1253 新增
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
    
    //菜鸟新需求返货折扣
    private Boolean isCainiao;//是否满足条件
    private String returnWaybillNo;//原始单号
    private String oldreceiveCustomerCode;//原收货人的客户编码
    private Date returnbilltime;//原始开单时间
    private BigDecimal returnTransportFee;//原单公布价费
    private BigDecimal returnInsuranceFee;//原单保价费
    private String OriginalReceiveOrgCode;//原单号收货部门编码


	//封装是否接货和送货的字段，精准电商使用
	private String centralizePickupResult;
	private String centralizeDeliveryResult;

	public String getCentralizePickupResult() {
		return centralizePickupResult;
	}

	public void setCentralizePickupResult(String centralizePickupResult) {
		this.centralizePickupResult = centralizePickupResult;
	}

	public String getCentralizeDeliveryResult() {
		return centralizeDeliveryResult;
	}

	public void setCentralizeDeliveryResult(String centralizeDeliveryResult) {
		this.centralizeDeliveryResult = centralizeDeliveryResult;
	}
	//定价项目POP-407foss没有将接货金额、保费金额5个字段传给规则引擎
    /**
     * 代收货款和保险声明价值 
     * */
    private Map valuedtos;
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

	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}

	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
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
    //liyongfei 新增计费方式
    private  String caculateType;
    
    /**
     * 判断是否是当前合同客户的当前价格版本
     * */
    private String isCurrContract;//需求：降价发券    新增
    
	public String getUsers() {
		return users;
	}


	public void setUsers(String users) {
		this.users = users;
	}


	public String getStartCountyCode() {
		return startCountyCode;
	}


	public void setStartCountyCode(String startCountyCode) {
		this.startCountyCode = startCountyCode;
	}


	public String getStartCityCode() {
		return startCityCode;
	}


	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}


	public String getStartProvCode() {
		return startProvCode;
	}


	public void setStartProvCode(String startProvCode) {
		this.startProvCode = startProvCode;
	}


	public String getArriveCountyCode() {
		return arriveCountyCode;
	}


	public void setArriveCountyCode(String arriveCountyCode) {
		this.arriveCountyCode = arriveCountyCode;
	}


	public String getArriveCityCode() {
		return arriveCityCode;
	}


	public void setArriveCityCode(String arriveCityCode) {
		this.arriveCityCode = arriveCityCode;
	}


	public String getArriveProvCode() {
		return arriveProvCode;
	}


	public void setArriveProvCode(String arriveProvCode) {
		this.arriveProvCode = arriveProvCode;
	}


	public String getCaculateType() {
		return caculateType;
	}


	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}


    
	public String getIsCurrContract() {
		return isCurrContract;
	}


	public void setIsCurrContract(String isCurrContract) {
		this.isCurrContract = isCurrContract;
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


	/**
	 * Gets the last org code.
	 *
	 * @return the last org code
	 */
	public String getLastOrgCode() {
		return lastOrgCode;
	}


	public String getCityMarketCode() {
		return cityMarketCode;
	}


	public void setCityMarketCode(String cityMarketCode) {
		this.cityMarketCode = cityMarketCode;
	}


	/**
	 * Sets the last org code.
	 *
	 * @param lastOrgCode the new last org code
	 */
	public void setLastOrgCode(String lastOrgCode) {
		this.lastOrgCode = lastOrgCode;
	}


	/**
	 * Gets the economy since.
	 *
	 * @return the economy since
	 */
	public String getEconomySince() {
//		return economySince;
		//自提件取消上线，设置为不显示
		return FossConstants.NO;
	}


	/**
	 * Sets the economy since.
	 *
	 * @param economySince the new economy since
	 */
	public void setEconomySince(String economySince) {
//		this.economySince = economySince;
		//自提件取消上线，设置为不显示
		this.economySince = FossConstants.NO;
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

    /** 全网点. */
    private String allNet;
    
    
    
	
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
	 * 获取 产品编号.
	 *
	 * @return the 产品编号
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * 设置 产品编号.
	 *
	 * @param productCode the new 产品编号
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
	 * 获取 营业部收货日期（可选，无则表示当前日期）,即开单日期.
	 *
	 * @return the 营业部收货日期（可选，无则表示当前日期）,即开单日期
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}
	
	/**
	 * 设置 营业部收货日期（可选，无则表示当前日期）,即开单日期.
	 *
	 * @param receiveDate the new 营业部收货日期（可选，无则表示当前日期）,即开单日期
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
	



	public String getIsSelfPickUp() {
		return isSelfPickUp;
	}


	public void setIsSelfPickUp(String isSelfPickUp) {
		this.isSelfPickUp = isSelfPickUp;
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
	 * 获取 客户编码.
	 *
	 * @return the 客户编码
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	
	/**
	 * 设置 客户编码.
	 *
	 * @param customerCode the new 客户编码
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	/**
	 * 获取 所属行业编码.
	 *
	 * @return the 所属行业编码
	 */
	public String getIndustrulCode() {
		if(StringUtil.isEmpty(industrulCode)) {
			 return "ALL";
		} else {
			return industrulCode;
		}
	}
	
	/**
	 * 设置 所属行业编码.
	 *
	 * @param industrulCode the new 所属行业编码
	 */
	public void setIndustrulCode(String industrulCode) {
		this.industrulCode = industrulCode;
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
	 * 获取 计价条目列表.
	 *
	 * @return the 计价条目列表
	 */
	public List<GuiQueryBillCalculateSubDto> getPriceEntities() {
		return priceEntities;
	}

	/**
	 * 设置 计价条目列表.
	 *
	 * @param priceEntities the new 计价条目列表
	 */
	public void setPriceEntities(List<GuiQueryBillCalculateSubDto> priceEntities) {
		this.priceEntities = priceEntities;
	}

	/**
	 * 获取 出发区域ID.
	 *
	 * @return the 出发区域ID
	 */
	public String getDeptRegionId() {
		return deptRegionId;
	}

	/**
	 * 设置 出发区域ID.
	 *
	 * @param deptRegionId the new 出发区域ID
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	/**
	 * 获取 到达区域ID.
	 *
	 * @return the 到达区域ID
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}

	/**
	 * 设置 到达区域ID.
	 *
	 * @param arrvRegionId the new 到达区域ID
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
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
	 * 获取 计价条目.
	 *
	 * @return the 计价条目
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	/**
	 * 设置 计价条目.
	 *
	 * @param pricingEntryCode the new 计价条目
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

	/**
	 * 获取 子类型.
	 *
	 * @return the 子类型
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * 设置 子类型.
	 *
	 * @param subType the new 子类型
	 */
	public void setSubType(String subType) {
		this.subType = subType;
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
	 * 获取 打木架体积.
	 *
	 * @return the 打木架体积
	 */
	public BigDecimal getWoodenVolume() {
		return woodenVolume;
	}

	/**
	 * 设置 打木架体积.
	 *
	 * @param woodenVolume the new 打木架体积
	 */
	public void setWoodenVolume(BigDecimal woodenVolume) {
		this.woodenVolume = woodenVolume;
	}

	/**
	 * 获取费率 目前只有整车计算保价费需要使用
	 * @return
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}

	/**
	 * 设置费率 目前只有整车计算保价费需要使用
	 * @param feeRate
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}


	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}

	public String getOriginalReceiveOrgCode() {
		return OriginalReceiveOrgCode;
	}

	public void setOriginalReceiveOrgCode(String originalReceiveOrgCode) {
		OriginalReceiveOrgCode = originalReceiveOrgCode;
	}
	
	/**
	 * Dmana-9885运单渠道
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-05
	 */
	private String waybillChannel;


	public String getWaybillChannel() {
		return waybillChannel;
	}


	public void setWaybillChannel(String waybillChannel) {
		this.waybillChannel = waybillChannel;
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
	//当前月份的优惠总额
	private BigDecimal amount;
	//当前月份的发货总金额
	private BigDecimal totalAmount;
	
	
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