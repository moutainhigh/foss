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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/CustomerContractDiscount.java
 * 
 * FILE NAME        	: CustomerContractDiscount.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.server.service.DiscountTypeInterface;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountResultDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.CustomerContractDiscountException;

/**
 * 查询客户折扣信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-12-21 上午10:14:16
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-12-21 上午10:14:16
 * @since
 * @version
 */
public class CustomerContractDiscount implements DiscountTypeInterface {
    
    /**
     * 客户优惠信息Service接口
     */
    private IPreferentialService preferentialService;
    
    /**
     * 据客户合同CRM_ID查询合同适用部门信息
     */
    private IBargainAppOrgService bargainAppOrgService;
    /**
     * 组织行政区域管理类
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 产品定义
     */
    private IProductService productService;
    
    public void setProductService(IProductService productService) {
		this.productService = productService;
	}
    
    /**
     * 设置 客户优惠信息Service接口.
     *
     * @param preferentialService the new 客户优惠信息Service接口
     */
    public void setPreferentialService(IPreferentialService preferentialService) {
        this.preferentialService = preferentialService;
    }
    
    public void setBargainAppOrgService(IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
     * <p>根据传入的参数查询客户合同折扣信息</p> 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-21 上午10:41:25
     * 
     * @param parm
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.DiscountTypeInterface#doDiscount(com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto)
     */
    @Override
    public DiscountResultDto doDiscount(DiscountParmDto parm) {
	
		if (null != parm) {
			if (StringUtil.isBlank(parm.getCustomCode())) {
				return null;
			} else if (null == parm.getReceiveDate()) {
				throw new CustomerContractDiscountException("开单日期不允许为空！");
			} else {
				//整车不看优惠
				if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(parm.getProductCode())){
					return null;
				}
				// 获取客户合同优惠信息
				String productCodeTemp = null;
				if(productService.onlineDetermineIsExpressByProductCode(parm.getProductCode(), new Date())){
					productCodeTemp = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
				}
				PreferentialEntity entity = preferentialService
						.queryPreferentialInfo(parm.getCustomCode(),
								parm.getReceiveDate(),productCodeTemp);
				/**
				 * 业务规则
				 * 1、	月发越送非月结客户，可以在全国任意部门开单，享受月发越送优惠；
				 * 2、	月发越送月结客户，只能在月结的绑定部门享受月发越送优惠； 
				 * 3、	非月发越送的月结客户，只能在月结的绑定部门享受月结优惠； 
				 * 4、	非月发越送的非月结客户，只能在月结的绑定部门享受月结优惠； 				
				 */
				// 折扣结果
				DiscountResultDto dto = new DiscountResultDto();
				if (null != entity) {
					List<String> unifiedCodeList=new ArrayList<String>();
					String unCode=orgAdministrativeInfoService.queryUnifiedCodeByCode(parm.getOriginalOrgCode());
					if(unCode!=null){
						unifiedCodeList.add(unCode);
					}
					List<BargainAppOrgEntity> bargainAppOrgEntities = bargainAppOrgService.queryAppOrgByBargainCrmId(entity.getCusBargainId(),unifiedCodeList);
					boolean isDiscount = false;
					if(CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
						for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
							BargainAppOrgEntity bargainAppOrgEntity =  bargainAppOrgEntities.get(i);
							String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
							OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
							if(orgAdministrativeInfoEntity == null) {
								continue;
							}
							String orgCode = orgAdministrativeInfoEntity.getCode();
							if(StringUtils.equals(parm.getOriginalOrgCode(), orgCode)) {
								isDiscount = true;
							}
						}
					}
					//非月结客户
					if(StringUtils.equals(entity.getChargeType(), DictionaryValueConstants.CLEARING_TYPE_NO_MONTH)) {
						//月发越送客户可不受绑定营业部限制
						if(!StringUtils.equals(entity.getPreferentialType(), DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
							if(!isDiscount) {
								return null;
							}
						}
					//月结客户
					} else {
						if(!isDiscount) {
							return null;
						}
					}
					
					if (StringUtil.isBlank(parm.getPricingEntryCode())) { 
						throw new CustomerContractDiscountException(
								"增值服务费用代码不允许为空！");
					} else {
						dto.setId(entity.getId());
						dto.setContractType(entity.getPreferentialType());
						if (StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_FRT,
								parm.getPricingEntryCode())) {
							// 运费折扣
							if(entity.getChargeRebate() == null) {
								return null;
							} else {
								dto.setDiscountRate(entity.getChargeRebate());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
							}
						} else if (StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_BF,
								parm.getPricingEntryCode())) {
							if(entity.getInsureDpriceRate() == null) {
								return null;
							} else {
								// 保价费率
								dto.setDiscountRate(entity.getInsureDpriceRate());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						} else if (StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_HK,
								parm.getPricingEntryCode())) {
							if(entity.getAgentGathRate() == null) {
								return null;
							} else {
								// 代收货款费率
								dto.setDiscountRate(entity.getAgentGathRate());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						} else if (StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_JH,
								parm.getPricingEntryCode())) {
							if(entity.getReceivePriceRate() == null) {
								return null;
							} else {
								// 接货费率
								dto.setDiscountRate(entity.getReceivePriceRate());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						} else if (StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_SH,
								parm.getPricingEntryCode())) {
							if(entity.getDeliveryFeeRate() == null) {
								return null;
							} else {
								// 送货费率
								dto.setDiscountRate(entity.getDeliveryFeeRate());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						}else if(StringUtil.equals(
								PriceEntityConstants.QT_CODE_CZHCZFWF,
								parm.getSubType())){
							if(entity.getOverweightOperatDiscount()==null){
								return null;
							}else{
								// 超重货超重费
								dto.setDiscountRate(entity.getOverweightOperatDiscount());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						}else if(StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_QS,
								parm.getPricingEntryCode())){
							if(entity.getSingleSignDiscount()==null){
								return null;
							}else{
								// 签收单返单费折扣
								dto.setDiscountRate(entity.getSingleSignDiscount());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						}else if(StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_BZ,
								parm.getPricingEntryCode())){
							if(entity.getPackingChargesDiscount()==null){
								return null;
							}else{
								// 包装折扣折扣
								dto.setDiscountRate(entity.getPackingChargesDiscount());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						}
						/**
						 * 增加大件上楼优惠(等综合确定entity中的大件上楼字段)
						 * @author:218371-foss-zhaoyanjun
						 * @dateL2014-12-17下午14:56
						 */
						else if(StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_DJSL,
								parm.getPricingEntryCode())){
							if(entity.getBigUprate()==null){
								return null;
							}else{
								// 包装折扣折扣
								dto.setDiscountRate(entity.getBigUprate());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_DJSL);
							}
						}
						/**
						 * @需求：DN201503240002(等综合确定entity中的送货进仓字段)
						 * @功能：增加送货进仓优惠
						 * @author:218371-foss-zhaoyanjun
						 * @date:2015-04-18上午10:39
						 */
						else if(StringUtil.equals(
								PriceEntityConstants.PRICING_CODE_SHJC,
								parm.getSubType())){
							if(entity.getIntoHouseDeliverDiscount()==null){
								return null;
							}else{
								// 送货进仓折扣
								dto.setDiscountRate(entity.getIntoHouseDeliverDiscount());
								dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
							}
						}
						else {
							return null;
						}
					}
					return dto;
				} else {
					return null;
				}
			}
		} else {
			throw new CustomerContractDiscountException("传入的参数不允许为空！");
		}
	}

    
	/**
     * <p>快递根据传入的参数查询客户合同折扣信息</p> 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-21 上午10:41:25
     * 
     * @param 
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.DiscountTypeInterface#doDiscount(com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto)
     */
	@Override
	public DiscountResultDto doExpressDiscount(DiscountParmDto parm) {
		
		if (null != parm) {
			if (StringUtil.isBlank(parm.getCustomCode())) {
				return null;
			} else if (null == parm.getReceiveDate()) {
				throw new CustomerContractDiscountException("开单日期不允许为空！");
			} else {
				//整车不看优惠
				if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(parm.getProductCode())){
					return null;
				}
				// 获取客户合同优惠信息
				String productCodeTemp = null;
				if(productService.onlineDetermineIsExpressByProductCode(parm.getProductCode(), new Date())){
					productCodeTemp = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
				}
				List<BargainAppOrgEntity> bargainAppOrgEntities=null;
				/**
				 * 如果是菜鸟先计算菜鸟返货折扣		 
				 */
				 if(parm.getIsCainiao()!=null && parm.getIsCainiao() ){
					 /**
						 * 业务规则
						 * 1、	月发越送非月结客户，可以在全国任意部门开单，享受月发越送优惠；
						 * 2、	月发越送月结客户，只能在月结的绑定部门享受月发越送优惠； 
						 * 3、	非月发越送的月结客户，只能在月结的绑定部门享受月结优惠； 
						 * 4、	非月发越送的非月结客户，只能在月结的绑定部门享受月结优惠； 				
						 */
					  PreferentialInfoDto entity = preferentialService
								.queryPreferentialInfo(parm.getOldreceiveCustomerCode(),
										parm.getReturnbilltime(),productCodeTemp);
						// 折扣结果
						DiscountResultDto dto = new DiscountResultDto();
						if (null != entity) { 
							OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(parm.getOriginalReceiveOrgCode());
							if(orgAdministrativeInfoEntity!=null){
								bargainAppOrgEntities = bargainAppOrgService
										.queryAppOrgByBargainCrmIdAndUnifiedCode(entity.getCusBargainId(),orgAdministrativeInfoEntity.getUnifiedCode());
							}	
							//非月结客户
							if(StringUtils.equals(entity.getExPayWay(), DictionaryValueConstants.CLEARING_TYPE_NO_MONTH)) {
								//月发越折客户可不受绑定营业部限制
								if(!StringUtils.equals(entity.getExPreferentialType(), DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_REBATE)) {
									if(CollectionUtils.isEmpty(bargainAppOrgEntities)) {
										return null;
									}
								}
							//月结客户
							} else {
								if(CollectionUtils.isEmpty(bargainAppOrgEntities)) {
									return null;
								}
							}
							
							if (StringUtil.isBlank(parm.getPricingEntryCode())) { 
								throw new CustomerContractDiscountException(
										"增值服务费用代码不允许为空！");
							} else {
								dto.setId(entity.getId());
								dto.setContractType(entity.getExPreferentialType());
								if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_FRT,
										parm.getPricingEntryCode())) {
									// 运费折扣
									if(entity.getExpBackFreghtType()!= null 
										  && 
										PriceEntityConstants.OLD_PREFE.equals(entity.getExpBackFreghtType())
										  &&
										  entity.getChargeRebate()!=null 
										) {
										dto.setDiscountRate(entity.getChargeRebate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
										//续重
										dto.setRenewalDiscountRate(entity.getContinueHeavyDiscount());
									}
									
									else if(entity.getExpBackFreghtType()!= null 
											  && 
											PriceEntityConstants.NEW_PREFE.equals(entity.getExpBackFreghtType())
											  &&
											  entity.getBackFreghtFixed()!=null 
											) {
											dto.setDiscountRate(entity.getBackFreghtFixed());
											//dto.setOriginnalCost(entity.getBackFreghtFixed());
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
										} 
									
									else if(entity.getExpBackFreghtType()!= null 
											  && 
											  PriceEntityConstants.OLD_PRICE_PREFE.equals(entity.getExpBackFreghtType())
											  &&
											  entity.getBackFreghtFixed()!=null 
											) {
											dto.setDiscountRate(new BigDecimal(1));
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
											dto.setOriginnalCost(entity.getBackFreghtFixed().multiply(parm.getReturnTransportFee()));
										} 
									else if(entity.getExpBackFreghtType()!= null 
											  && 
											PriceEntityConstants.FIXED_PRICE.equals(entity.getExpBackFreghtType())
											  &&
											  entity.getBackFreghtFixed()!=null 
											) {
											dto.setDiscountRate(new BigDecimal(1));
											dto.setOriginnalCost(entity.getBackFreghtFixed());
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
										} 
									
									else if(entity.getExpBackFreghtType()!= null 
											  && 
											PriceEntityConstants.OLD_PRICE.equals(entity.getExpBackFreghtType())
											  &&
											  parm.getReturnTransportFee()!=null 
											) {
											dto.setDiscountRate(new BigDecimal(1));
											dto.setOriginnalCost(parm.getReturnTransportFee());
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
										} 
									else {
											return null;
									}
									
									
								} else if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_BF,
										parm.getPricingEntryCode())) {

									// 保费率
									if(entity.getExpBackCollPreType()!= null 
										  && 
										PriceEntityConstants.OLD_PREFE.equals(entity.getExpBackCollPreType())
										  &&
										  entity.getInsureDpriceRate()!=null 
										) {
										dto.setDiscountRate(entity.getInsureDpriceRate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
									} 
									
									else if(entity.getExpBackCollPreType()!= null 
											  && 
											PriceEntityConstants.NEW_PREFE.equals(entity.getExpBackCollPreType())
											  &&
											  entity.getBackCollFixed()!=null 
											) {
											dto.setDiscountRate(entity.getBackCollFixed());
											//dto.setOriginnalCost(entity.getBackCollFixed());
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
										}   
									else if(entity.getExpBackCollPreType()!= null 
											  && 
											  PriceEntityConstants.OLD_PRICE_PREFE.equals(entity.getExpBackCollPreType())
											  &&
											  entity.getBackCollFixed()!=null 
											) {
											dto.setDiscountRate(new BigDecimal(1));
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
											dto.setOriginnalCost(entity.getBackCollFixed().multiply(parm.getReturnInsuranceFee()));
										}
									else if(entity.getExpBackCollPreType()!= null 
											  && 
											PriceEntityConstants.FIXED_PRICE.equals(entity.getExpBackCollPreType())
											  &&
											  entity.getBackCollFixed()!=null 
											) {
											dto.setDiscountRate(new BigDecimal(1));
											dto.setOriginnalCost(entity.getBackCollFixed());
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
										}  
									
									else if(entity.getExpBackCollPreType()!= null 
											  && 
											PriceEntityConstants.OLD_PRICE.equals(entity.getExpBackCollPreType())
											  &&
											  parm.getReturnInsuranceFee()!=null 
											) {
											dto.setDiscountRate(new BigDecimal(1));
											dto.setOriginnalCost(parm.getReturnInsuranceFee());
											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
										} 
									else {
											return null;
									}
									
									/*if(entity.getInsureDpriceRate() == null) {
										return null;
									} else {
										// 保价费率
										dto.setDiscountRate(entity.getInsureDpriceRate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
									}*/
								}else {
									return null;
								}
							}
							return dto;
						} else {
							return null;
						}
				 }else{
					 /**
						 * 业务规则
						 * 1、	月发越送非月结客户，可以在全国任意部门开单，享受月发越送优惠；
						 * 2、	月发越送月结客户，只能在月结的绑定部门享受月发越送优惠； 
						 * 3、	非月发越送的月结客户，只能在月结的绑定部门享受月结优惠； 
						 * 4、	非月发越送的非月结客户，只能在月结的绑定部门享受月结优惠； 				
						 */
					  
					 PreferentialInfoDto entity = preferentialService
								.queryPreferentialInfo(parm.getCustomCode(),
										parm.getReceiveDate(),productCodeTemp);
						 
						// 折扣结果
						DiscountResultDto dto = new DiscountResultDto();
						if (null != entity) {
							OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(parm.getOriginalOrgCode());
							if(orgAdministrativeInfoEntity!=null){
								bargainAppOrgEntities = bargainAppOrgService
										.queryAppOrgByBargainCrmIdAndUnifiedCode(entity.getCusBargainId(),orgAdministrativeInfoEntity.getUnifiedCode());
							}
							
							//非月结客户
							if(StringUtils.equals(entity.getExPayWay(), DictionaryValueConstants.CLEARING_TYPE_NO_MONTH)) {
								//月发越折客户可不受绑定营业部限制
								if(!StringUtils.equals(entity.getExPreferentialType(), DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_REBATE)) {
									if(CollectionUtils.isEmpty(bargainAppOrgEntities)) {
										return null;
									}
								}
							//月结客户
							} else {
								if(CollectionUtils.isEmpty(bargainAppOrgEntities)) {
									return null;
								}
							}
							
							if (StringUtil.isBlank(parm.getPricingEntryCode())) { 
								throw new CustomerContractDiscountException(
										"增值服务费用代码不允许为空！");
							} else {
								dto.setId(entity.getId());
								dto.setContractType(entity.getExPreferentialType());
								if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_FRT,
										parm.getPricingEntryCode())) {
									// 运费折扣
									if(entity.getChargeRebate() == null) {
										return null;
									} else {
										dto.setDiscountRate(entity.getChargeRebate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
									}
								} else if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_BF,
										parm.getPricingEntryCode())) {
									if(entity.getInsureDpriceRate() == null 
											&& entity.getSinTicketSurePriceCharge() == null) {
										return null;
									} else {
										// 保价费率
										dto.setDiscountRate(entity.getInsureDpriceRate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
										//单票保价手续费
										dto.setSinTicketCharge(entity.getSinTicketSurePriceCharge());
									}
								} else if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_HK,
										parm.getPricingEntryCode())) {
									if(entity.getAgentGathRate() == null 
											&& entity.getSinTicketCollCharge() == null
											&& entity.getLowestCharge() == null) {
										return null;
									} else{
										// 代收货款费率
										dto.setDiscountRate(entity.getAgentGathRate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
										//代收最低手续费
										dto.setLowestCharge(entity.getLowestCharge());
										//单票代收手续费
										dto.setSinTicketCharge(entity.getSinTicketCollCharge());
									}
								} else if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_JH,
										parm.getPricingEntryCode())) {
									if(entity.getReceivePriceRate() == null) {
										return null;
									} else {
										// 接货费率
										dto.setDiscountRate(entity.getReceivePriceRate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
									}
								} else if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_SH,
										parm.getPricingEntryCode())) {
									if(entity.getDeliveryFeeRate() == null) {
										return null;
									} else {
										// 送货费率
										dto.setDiscountRate(entity.getDeliveryFeeRate());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
									}
								}else if (StringUtil.equals(
										PriceEntityConstants.PRICING_CODE_QS,
										parm.getPricingEntryCode())) {	
									if(entity.getSingleSignDiscount() == null ) {
										//首重 折扣
//										if(entity.getChargeRebate() != null){
//											dto.setDiscountRate(entity.getChargeRebate());
//											dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
//										}else
										return null;
									}else{
										// 签收单返回单 折扣率
										dto.setDiscountRate(entity.getSingleSignDiscount());
										dto.setDiscountMode(PriceEntityConstants.PRICING_CODE_VALUEADDED);
									}
								} else {
									return null;
								}
							}
							return dto;
						} else {
							return null;
						} 
				 }
				
			}
		} else {
			throw new CustomerContractDiscountException("传入的参数不允许为空！");
		}
	}
}