/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/CommonUtils.java
 * 
 * FILE NAME        	: CommonUtils.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.utils
 * FILE    NAME: CommonUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.utils;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillInfoOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;


/**
 * 提货给运单、更改单模块公用的工具方法
 * @author 026123-foss-lifengteng
 * @date 2013-1-26 下午3:38:41
 */
public class CommoForFeeUtils {
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(CommoForFeeUtils.class); 

	
	/**
	 * 
	 * 比较折扣优惠表格中是否已经存在相同的项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-27 下午05:57:52
	 * @return
	 */
							
	public  static void add(GuiResultDiscountDto dto, List<WaybillDiscountVo> data , WaybillPanelVo bean) {
		
		WaybillDiscountVo vo = new WaybillDiscountVo();
		// 折扣ID
		vo.setDiscountId(dto.getDiscountId());
		// 费用类型id
		vo.setChargeDetailId(dto.getChargeDetailId());
		//如果是其他费用，则显示子类型-----定价体系优化项目POP-423
		if(PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntryCode())
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(dto.getPriceEntryCode())){
			// 优惠折扣项目
			vo.setFavorableItemName(dto.getSubTypeName());
			// 优惠项目CODE
			vo.setFavorableItemCode(dto.getSubType());
		}else{
			// 优惠折扣项目
			vo.setFavorableItemName(dto.getPriceEntryName());
			// 优惠项目CODE
			vo.setFavorableItemCode(dto.getPriceEntryCode());
		}
		// 优惠折扣类型
		vo.setFavorableTypeName(dto.getDiscountTypeName());
		// 优惠折扣类型
		vo.setFavorableTypeCode(dto.getDiscountType());
		// 优惠折扣子类型
		vo.setFavorableSubTypeName(dto.getSaleChannelName());
		// 优惠折扣子类型
		vo.setFavorableSubTypeCode(dto.getSaleChannelCode());
		//营销活动编码
		vo.setActiveCode(dto.getActiveCode());
		//营销活动名称
		vo.setActiveName(dto.getActiveName());
		//营销活动开始时间
		vo.setActiveStartTime(dto.getActiveStartTime());
		//营销活动结束时间
		vo.setActiveEndTime(dto.getActiveEndTime());
		//营销活动折扣对应的CRM_ID
		vo.setOptionsCrmId(dto.getOptionsCrmId());
		if (dto.getDiscountRate() != null) {
			// 优惠折扣率
			vo.setFavorableDiscount(dto.getDiscountRate().toString());
		} else {
			// 优惠折扣率
			vo.setFavorableDiscount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableDiscount"));
		}
		if (dto.getRenewalDiscountRate()!= null) {
			//设置快递续重折扣率
			vo.setContinueFavorableDiscount(dto.getRenewalDiscountRate().toString());
		} 
		// 优惠折扣金额
		if (dto.getReduceFee() != null) {
			//优惠金额
			vo.setFavorableAmount(dto.getReduceFee().toString());			
		} else {
			   vo.setFavorableAmount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableAmount"));
		}
		data.add(vo);

	}
	
	
	/**
	 * 费用归集			
	 * @author WangQianJin
	 * @date 2013-8-3 下午12:19:46
	 */
	public static void feeCollection(List<OtherChargeVo> voList, WaybillPanelVo bean) {
		/** 
		 * 业务要求
		 * 超远派送加收(归集到送货费中)
		 * 对外投保保费收入(归集到保费中)
		 * 空运运费冲减(归集到运费中) **/
		if(voList!=null && voList.size()>0 && bean!=null){
			//超远派送加收
			BigDecimal ccddjsFee=BigDecimal.ZERO;
			//对外投保保费收入
			BigDecimal dwtbfFee=BigDecimal.ZERO;
			//空运运费冲减
			BigDecimal kyyfcjFee=BigDecimal.ZERO;
			/**
			 * DMANA-3563 整车开单判断是否盈利
			 * 新增整车运费冲减字段到bean中
			 * 方便提交整车运单时校验
			 */
 			BigDecimal wholeVehicleFeeChange = BigDecimal.ZERO;
			
			//循环获取各种归集费用
			for(OtherChargeVo otherVo:voList){	
				if(otherVo!=null && otherVo.getMoney()!=null && !"".equals(otherVo.getMoney())){
					if(PriceEntityConstants.PRICING_CODE_CCDDJS.equals(otherVo.getCode())){
						ccddjsFee = ccddjsFee.add(new BigDecimal(otherVo.getMoney()));						
					}
	                if(PriceEntityConstants.PRICING_CODE_DWTBF.equals(otherVo.getCode())){
	                	dwtbfFee = dwtbfFee.add(new BigDecimal(otherVo.getMoney()));	                	
	                }
	                if(PriceEntityConstants.PRICING_CODE_KYYFCJ.equals(otherVo.getCode())){
	                	kyyfcjFee = kyyfcjFee.add(new BigDecimal(otherVo.getMoney()));
	                }
	                //新增整车运费冲减费用
	                if(PriceEntityConstants.PRICING_CODE_ZCYFCJ.equals(otherVo.getCode())){
	                	wholeVehicleFeeChange = wholeVehicleFeeChange.add(new BigDecimal(otherVo.getMoney()));
	                	bean.setWholeVehicleFeeChange(wholeVehicleFeeChange);
	                }
				}		
			}	
			//设置超远派送加收
			setCcddjsFee(ccddjsFee,bean);
			//设置对外投保保费收入
			setDwtbfFee(dwtbfFee,bean);
			//设置空运运费冲减
			setKyyfcjFee(kyyfcjFee,bean);		
		}
	}
	
	/**
	 * 设置超远派送加收
	 * @author WangQianJin
	 * @date 2013-8-3 下午1:58:50
	 */
	public static void setCcddjsFee(BigDecimal ccddjsFee,WaybillPanelVo bean){
		if(ccddjsFee.intValue()!=0){
			//将超远派送加收添加至送货费
			if(bean.getDeliveryGoodsFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgCcddjsFee())){
					bean.setDeliveryGoodsFee(bean.getDeliveryGoodsFee().add(ccddjsFee));
					bean.setFalgCcddjsFee(FossConstants.YES);
//				}				
			}else{
				bean.setDeliveryGoodsFee(ccddjsFee);
			}	
			bean.setDeliveryGoodsFeeCanvas(bean.getDeliveryGoodsFee().toString());
			bean.setCalculateDeliveryGoodsFee(bean.getDeliveryGoodsFee());
			//从其他费用中减去超远派送加收
			if(bean.getOtherFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgOtherFeeForCcddjsFee())){
					bean.setOtherFee(bean.getOtherFee().subtract(ccddjsFee));
					bean.setOtherFeeCanvas(bean.getOtherFee().toString());
					bean.setFalgOtherFeeForCcddjsFee(FossConstants.YES);
//				}				
			}
		}
	}
	
	/**
	 * 设置对外投保保费收入
	 * @author WangQianJin
	 * @date 2013-8-3 下午1:58:50
	 */
	public static void setDwtbfFee(BigDecimal dwtbfFee,WaybillPanelVo bean){
		if(dwtbfFee.intValue()!=0){
			//将对外投保保费收入添加至保费
			if(bean.getInsuranceFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgDwtbfFee())){
					bean.setInsuranceFee(bean.getInsuranceFee().add(dwtbfFee));	
					bean.setFalgDwtbfFee(FossConstants.YES);
//				}			
			}else{
				bean.setInsuranceFee(dwtbfFee);
			}			
			//从其他费用中减去对外投保保费收入
			if(bean.getOtherFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgOtherFeeForDwtbfFee())){
					bean.setOtherFee(bean.getOtherFee().subtract(dwtbfFee));
					bean.setOtherFeeCanvas(bean.getOtherFee().toString());
					bean.setFalgOtherFeeForDwtbfFee(FossConstants.YES);
//				}				
			}
		}
	}
	
	/**
	 * 设置空运运费冲减
	 * @author WangQianJin
	 * @date 2013-8-3 下午1:58:50
	 */
	public static void setKyyfcjFee(BigDecimal kyyfcjFee,WaybillPanelVo bean){
		if(kyyfcjFee.intValue()!=0){
			//将空运运费冲减添加至运费
			if(bean.getTransportFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgKyyfcjFee())){
					bean.setTransportFee(bean.getTransportFee().add(kyyfcjFee));	
					bean.setFalgKyyfcjFee(FossConstants.YES);
//				}				
			}else{
				bean.setTransportFee(kyyfcjFee);
			}	
			bean.setTransportFeeCanvas(bean.getTransportFee().toString());
			// 获取费率
			BigDecimal unitPrice = bean.getUnitPrice();
			// 费率 = 最新运费（运费+装卸费）/重量或体积
			unitPrice = bean.getTransportFee().divide(getWeightOrVolume(bean), 2, BigDecimal.ROUND_HALF_DOWN);
			// 设置新的费率
			bean.setUnitPrice(unitPrice);
			//从其他费用中减去空运运费冲减
			if(bean.getOtherFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgOtherFeeForKyyfcjFee())){
					bean.setOtherFee(bean.getOtherFee().subtract(kyyfcjFee));
					bean.setOtherFeeCanvas(bean.getOtherFee().toString());
					bean.setFalgOtherFeeForKyyfcjFee(FossConstants.YES);
//				}				
			}
		}
	}
	
	/**
	 * 获取重量或者体积
	 * @author WangQianJin
	 * @date 2013-8-3 下午1:58:50
	 */
	public static BigDecimal getWeightOrVolume(WaybillPanelVo bean) {
		// 判断是否按重量计费
		if (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode())) {
			return bean.getGoodsWeightTotal();
		} else {
			// 按体积计费
			ProductEntityVo productVo = bean.getProductCode();
			// 判断是否空运
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				// 是空运则返回计费重量
				return bean.getBillWeight();
			} else {
				// 是汽运则返回体积
				return bean.getGoodsVolumeTotal();
			}
		}
	}
	
	/**
	 * 
	 * 累计其他费用面板所有费用
	 * @author 025000-FOSS-helong
	 * @date 2013-4-18 下午04:17:58
	 */
	public static void otherPanelSumFee(List<OtherChargeVo> data,WaybillPanelVo bean)
	{
		BigDecimal otherCharge = BigDecimal.ZERO;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				otherCharge = otherCharge.add(new BigDecimal(otherVo.getMoney()));
			}
		}
		bean.setOtherFee(otherCharge);
		bean.setOtherFeeCanvas(otherCharge.toString());
	}
	
	/**
	 * 判断是否需要重新计算运费	
	 * @author WangQianJin
	 * @date 2013-8-3 下午12:19:46
	 */
	public static boolean isResetCalTransportFee(List<OtherChargeVo> voList,WaybillPanelVo bean) {
		boolean isResetCal=false;
		//其他费用
		if(voList!=null && voList.size()>0){			
			//判断其他费用中是否包含空运运费冲减
			for(OtherChargeVo otherVo:voList){	
				if(otherVo!=null && otherVo.getMoney()!=null && !"".equals(otherVo.getMoney())){					
	                if(PriceEntityConstants.PRICING_CODE_KYYFCJ.equals(otherVo.getCode())){
	                	if(otherVo.getMoney()!=null){
	                		bean.setOldKyyfcjFee(new BigDecimal(otherVo.getMoney()));	
	                	}	                	
	                	isResetCal=true;
	                	break;
	                }	                
				}		
			}					
		}
		return isResetCal;
	}
	
	/**
	 * 设置超远派送加收(更改单)
	 * @author WangQianJin
	 * @date 2013-8-3 下午1:58:50
	 */
	public static void setCcddjsFeeForRfc(BigDecimal ccddjsFee,WaybillPanelVo bean){
		if(ccddjsFee.intValue()!=0){
			List<DeliverChargeEntity> deliverChargeList = bean.getDeliverList();
			BigDecimal deliverCharge = BigDecimal.ZERO;
			if(deliverChargeList != null && !deliverChargeList.isEmpty())
			{
				for(int i=0;i<deliverChargeList.size();i++)
				{
					DeliverChargeEntity entity = deliverChargeList.get(i);
					deliverCharge = deliverCharge.add(entity.getAmount());
				}
			}

			
			
			//将超远派送加收添加至送货费
			if(bean.getDeliveryGoodsFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgCcddjsFee())){
					bean.setDeliveryGoodsFee(deliverCharge.add(ccddjsFee));
					bean.setFalgCcddjsFee(FossConstants.YES);
//				}				
			}else{
				bean.setDeliveryGoodsFee(ccddjsFee);
			}	
			bean.setDeliveryGoodsFeeCanvas(bean.getDeliveryGoodsFee().toString());
			//bean.setCalculateDeliveryGoodsFee(bean.getDeliveryGoodsFee());
			//从其他费用中减去超远派送加收
			if(bean.getOtherFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgOtherFeeForCcddjsFee())){
					bean.setOtherFee(bean.getOtherFee().subtract(ccddjsFee));
					bean.setOtherFeeCanvas(bean.getOtherFee().toString());
					bean.setFalgOtherFeeForCcddjsFee(FossConstants.YES);
//				}				
			}
		}
	}
	
	/**
	 * 设置空运运费冲减(更改单)
	 * @author WangQianJin
	 * @date 2013-8-3 下午1:58:50
	 */
	public static void setKyyfcjFeeForRfc(BigDecimal kyyfcjFee,WaybillPanelVo bean){
		if(kyyfcjFee.intValue()!=0){
			//将空运运费冲减添加至运费
			if(bean.getTransportFee()!=null){
				//如果计算了公布价，则需要重新归集空运运费冲减
				if(FossConstants.YES.equals(bean.getIsCalTraFee())){
					bean.setTransportFee(bean.getTransportFee().add(kyyfcjFee));
				}else{					
					if(FossConstants.YES.equals(bean.getFalgKyyfcjFee())){
						BigDecimal oldKyyfcj = BigDecimal.ZERO;
						if(bean.getOldKyyfcjFee()==null){
							oldKyyfcj=BigDecimal.ZERO;
						}else{
							oldKyyfcj=bean.getOldKyyfcjFee();
						}
						//如果上次已经有空运运费冲减，则要减去上次的，加上这次的
						bean.setTransportFee(bean.getTransportFee().subtract(oldKyyfcj).add(kyyfcjFee));
						bean.setOldKyyfcjFee(kyyfcjFee);
					}else{
						//如果上次没有空运运费冲减，则第一次直接加上
						bean.setTransportFee(bean.getTransportFee().add(kyyfcjFee));
						bean.setOldKyyfcjFee(kyyfcjFee);
						bean.setFalgKyyfcjFee(FossConstants.YES);
					}
				}								
			}else{
				bean.setTransportFee(kyyfcjFee);
			}	
			bean.setTransportFeeCanvas(bean.getTransportFee().toString());
			// 获取费率
			BigDecimal unitPrice = bean.getUnitPrice();
			// 费率 = 最新运费（运费+装卸费）/重量或体积
			unitPrice = bean.getTransportFee().divide(getWeightOrVolume(bean), 2, BigDecimal.ROUND_HALF_DOWN);
			// 设置新的费率
			bean.setUnitPrice(unitPrice);
			//从其他费用中减去空运运费冲减
			if(bean.getOtherFee()!=null){
//				if(!FossConstants.YES.equals(bean.getFalgOtherFeeForKyyfcjFee())){
					bean.setOtherFee(bean.getOtherFee().subtract(kyyfcjFee));
					bean.setOtherFeeCanvas(bean.getOtherFee().toString());
					bean.setFalgOtherFeeForKyyfcjFee(FossConstants.YES);
//				}				
			}
		}
	}
	
	/**
	 * 费用归集	(更改单)
	 * @author WangQianJin
	 * @date 2013-8-3 下午12:19:46
	 */
	public static void feeCollectionForRfc(List<OtherChargeVo> voList, WaybillPanelVo bean) {
		/** 
		 * 业务要求
		 * 超远派送加收(归集到送货费中)
		 * 对外投保保费收入(归集到保费中)
		 * 空运运费冲减(归集到运费中) **/
		if(voList!=null && voList.size()>0 && bean!=null){
			//超远派送加收
			BigDecimal ccddjsFee=BigDecimal.ZERO;
			//对外投保保费收入
			BigDecimal dwtbfFee=BigDecimal.ZERO;
			//空运运费冲减
			BigDecimal kyyfcjFee=BigDecimal.ZERO;
			/**
			 * DMANA-3563 整车开单判断是否盈利
			 * 新增整车运费冲减字段到bean中
			 * 方便提交整车运单时校验
			 */
 			BigDecimal wholeVehicleFeeChange = BigDecimal.ZERO;
 			
			//循环获取各种归集费用
			for(OtherChargeVo otherVo:voList){	
				if(otherVo!=null && otherVo.getMoney()!=null && !"".equals(otherVo.getMoney())){
					if(PriceEntityConstants.PRICING_CODE_CCDDJS.equals(otherVo.getCode())){
						ccddjsFee = ccddjsFee.add(new BigDecimal(otherVo.getMoney()));						
					}
	                if(PriceEntityConstants.PRICING_CODE_DWTBF.equals(otherVo.getCode())){
	                	dwtbfFee = dwtbfFee.add(new BigDecimal(otherVo.getMoney()));	                	
	                }
	                if(PriceEntityConstants.PRICING_CODE_KYYFCJ.equals(otherVo.getCode())){
	                	kyyfcjFee = kyyfcjFee.add(new BigDecimal(otherVo.getMoney()));
	                }	
	                //新增整车运费冲减费用
	                if(PriceEntityConstants.PRICING_CODE_ZCYFCJ.equals(otherVo.getCode())){
	                	wholeVehicleFeeChange = wholeVehicleFeeChange.add(new BigDecimal(otherVo.getMoney()));
	                	bean.setWholeVehicleFeeChange(wholeVehicleFeeChange);
	                }
				}		
			}	
			//设置超远派送加收
			setCcddjsFeeForRfc(ccddjsFee,bean);
			//设置对外投保保费收入
			setDwtbfFee(dwtbfFee,bean);
			//设置空运运费冲减
			setKyyfcjFeeForRfc(kyyfcjFee,bean);		
		}
	}


	public static void add(PriceDiscountDto dto, List<WaybillDiscountVo> data,
			WaybillPanelVo bean) {
		WaybillDiscountVo vo = new WaybillDiscountVo();
		// 折扣ID
		vo.setDiscountId(dto.getDiscountId());
		// 费用类型id
		vo.setChargeDetailId(dto.getChargeDetailId());
		// 优惠折扣项目
		vo.setFavorableItemName(dto.getPriceEntryName());
		// 优惠项目CODE
		vo.setFavorableItemCode(dto.getPriceEntryCode());
		// 优惠折扣类型
		vo.setFavorableTypeName(dto.getTypeName());
		// 优惠折扣类型
		vo.setFavorableTypeCode(dto.getType());
		// 优惠折扣子类型
		vo.setFavorableSubTypeName(dto.getSaleChannelName());
		// 优惠折扣子类型
		vo.setFavorableSubTypeCode(dto.getSaleChannelCode());
		//营销活动编码
		vo.setActiveCode(dto.getActiveCode());
		//营销活动名称
		vo.setActiveName(dto.getActiveName());
		//营销活动开始时间
		vo.setActiveStartTime(dto.getActiveStartTime());
		//营销活动结束时间
		vo.setActiveEndTime(dto.getActiveEndTime());
		//营销活动折扣对应的CRM_ID
		vo.setOptionsCrmId(dto.getOptionsCrmId());
		if (dto.getDiscountRate() != null) {
			// 优惠折扣率
			vo.setFavorableDiscount(dto.getDiscountRate().toString());
		} else {
			// 优惠折扣率
			vo.setFavorableDiscount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableDiscount"));
		}
		// 优惠折扣金额
		if (dto.getReduceFee() != null) {
			//优惠金额
			vo.setFavorableAmount(dto.getReduceFee().toString());			
		} else {
			vo.setFavorableAmount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableAmount"));
		}

		data.add(vo);
		
	}
	
	/**
	 * 合伙人 保存手工修改前的各项费用 零担 (暂有漏洞，后续更新)--漏洞已解 -sangwenhao
	 * @param voList 
	 * @param bean 
	 * @author 272311-sangwenhao
	 * @date 2016-1-21
	 */
	public static void keepStandardFee(List<OtherChargeVo> voList, WaybillPanelVo bean){
		PtpWaybillOrgVo ptpWaybillOrgVo = bean.getPtpWaybillOrgVo();
		if(ptpWaybillOrgVo == null){
			ptpWaybillOrgVo = PtpWaybillOrgVo.init() ;
			bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
		}
		
		//保存 原始值 
		bean.setSupportFee(bean.getInsuranceFee()) ;
		bean.setCollectingFee(bean.getCodFee());
		//2016年1月15日 15:46:14 葛亮亮 
		ptpWaybillOrgVo.setPackageFee(bean.getPackageFee().toString());//包装费
		ptpWaybillOrgVo.setDeliveryGoodsFee(bean.getDeliveryGoodsFee().toString()) ;//送货费(界面)
		ptpWaybillOrgVo.setBoxCharge(bean.getBoxCharge());//打木箱货物费用
		ptpWaybillOrgVo.setStandCharge(bean.getStandCharge()) ;//打木架货物费用
		ptpWaybillOrgVo.setSalverGoodsCharge(bean.getSalverGoodsCharge()) ; //打木托货物费用
		ptpWaybillOrgVo.setOtherFee(bean.getOtherFee()) ;//其他费用
		ptpWaybillOrgVo.setCouponFee(bean.getFossToPtpCouponFree());//优惠券金额
		
		BigDecimal transportFee = null != bean.getTransportFee() ? bean.getTransportFee() : BigDecimal.ZERO; //公布运价
		BigDecimal pickUpFee = null != bean.getPickupFee() ? bean.getPickupFee() : BigDecimal.ZERO;//接货费	
		BigDecimal insuranceFee = null != bean.getInsuranceFee() ? bean.getInsuranceFee() : BigDecimal.ZERO;//保价费	
		BigDecimal codFee = null != bean.getCodFee() ? bean.getCodFee() : BigDecimal.ZERO;//代收货款手续费
		BigDecimal totalFee = null != bean.getTotalFee() ? bean.getTotalFee() : BigDecimal.ZERO;//总费用
		//公布价无论重算还是不重算，加收费以及加收费率都会以当前的提货网点是否加收重新计算，所以每次更改都需要重新向折前表存入当前的加收信息
		BigDecimal overTransportFee = null != bean.getOverTransportFee() ? bean.getOverTransportFee() : BigDecimal.ZERO;//加收金额		
		BigDecimal overTransportRate = null != bean.getOverTransportRate() ? bean.getOverTransportRate() : BigDecimal.ZERO;//加收费率
		
		//折前表
		BigDecimal zqtransportFee = null != bean.getZqTransportFee() ? bean.getZqTransportFee() : BigDecimal.ZERO; //公布运价
		BigDecimal zqpickUpFee = null != bean.getZqPickUpFee() ? bean.getZqPickUpFee() : BigDecimal.ZERO;//接货费	
		BigDecimal zqinsuranceFee = null != bean.getZqInsuranceFee() ? bean.getZqInsuranceFee() : BigDecimal.ZERO;//保价费	
		BigDecimal partialTransportFee=null !=bean.getPartialTransportFee() ? bean.getPartialTransportFee() : BigDecimal.ZERO;//偏线费
		//代收货款如果清空
		if(bean.getIsCodZero()){
			totalFee = totalFee.subtract(codFee); //总费用减去页面获取的值（因为代收货款清空应该为0）
			codFee = BigDecimal.ZERO;
		}
		ptpWaybillOrgVo.setCodFee(codFee);

		//保价费和接货费使用开单时提交费用覆盖
		if(bean.getIsBeginFee()){
			zqpickUpFee = zqpickUpFee.subtract(pickUpFee); //接货费
			zqinsuranceFee = zqinsuranceFee.subtract(insuranceFee); //保价费
			totalFee = totalFee.add(zqpickUpFee); //总费用加上接货费差值
			totalFee = totalFee.add(zqinsuranceFee); //总费用加上保价费差值
			pickUpFee = null != bean.getZqPickUpFee() ? bean.getZqPickUpFee() : BigDecimal.ZERO;// 折前接货费
			insuranceFee = null != bean.getZqInsuranceFee() ? bean.getZqInsuranceFee() : BigDecimal.ZERO;//折前保价费	
		}
		ptpWaybillOrgVo.setPickUpFee(pickUpFee.toString());
		ptpWaybillOrgVo.setInsuranceFee(insuranceFee);
		
		//如果公布运价费没有重新计算获取折前表中的数据
		if(!FossConstants.YES.equals(bean.getIsCalTraFee()) && FossConstants.YES.equals(bean.getIsChanged())){
			zqtransportFee = zqtransportFee.subtract(transportFee);//用折前费用减去本次页面获取的值
			totalFee = totalFee.add(zqtransportFee); //总费用加上差值
			transportFee = null != bean.getZqTransportFee() ? bean.getZqTransportFee() : BigDecimal.ZERO; //公布运价运价费应该为折前			
			partialTransportFee  = null != bean.getZqPartialTransportFee() ? bean.getZqPartialTransportFee() : BigDecimal.ZERO; //偏线费用应为折前
		}
		//偏线费用
		ptpWaybillOrgVo.setPartialTransportFee(partialTransportFee);
		
		ptpWaybillOrgVo.setTransportFee(transportFee);
		ptpWaybillOrgVo.setOverTransportFee(overTransportFee); //合伙人到达加收金额
		ptpWaybillOrgVo.setOverTransportRate(overTransportRate);//合伙人到达加收费率
		
		//总费用
		ptpWaybillOrgVo.setTotalFee(totalFee) ;
		
		List<DeliverChargeEntity> lists = bean.getDeliverList() ;
		if(lists!=null && !lists.isEmpty()){
			for(DeliverChargeEntity dce : lists){
				if(PriceEntityConstants.PRICING_CODE_CY.equals(dce.getCode())){//超远派送费 
					ptpWaybillOrgVo.setOverDistanceFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_CCDDJS.equals(dce.getCode())){//超远派送费加收
					ptpWaybillOrgVo.setOverDistanceFee(ptpWaybillOrgVo.getOverDistanceFee().add(dce.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
				}else if(PriceEntityConstants.PRICING_CODE_DJSL.equals(dce.getCode())){//大件上楼费
					ptpWaybillOrgVo.setBigGoodsUpFloorFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_QS.equals(dce.getCode())){//签收回单
					ptpWaybillOrgVo.setSignBillFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(dce.getCode())){//送货进仓费
					ptpWaybillOrgVo.setDeliveryWareHouseFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SHAZ.equals(dce.getCode())){//送货安装费
					ptpWaybillOrgVo.setPickupToDoorJZFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SH.equals(dce.getCode())){//纯送货费
					ptpWaybillOrgVo.setBaseDeliveryGoodsFee(dce.getAmount().toPlainString());
				}else if(PriceEntityConstants.PRICING_CODE_ZZ.equals(dce.getCode())){//异常操作服务费/中转费
					ptpWaybillOrgVo.setExceptionOpreationFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(dce.getCode())){//送货上楼费(纯上楼费)
					ptpWaybillOrgVo.setUpFloorFee(dce.getAmount().toPlainString()) ;
				}
			}
		}
		//其他费用 分解
		//获取其他费用
		//新增一个标记，记录当前异常操作费的数量，2016年11月1日下午9:37:03/LianHe
		int expFeeCount = 0;
		if(CollectionUtils.isNotEmpty(voList)){
			for(OtherChargeVo vo : voList){
				if(StringUtils.isNotBlank(vo.getMoney())){
					if(PriceEntityConstants.PRICING_CODE_CY.equals(vo.getCode())){//超远派送费 
						ptpWaybillOrgVo.setOverDistanceFee(ptpWaybillOrgVo.getOverDistanceFee().add(new BigDecimal(vo.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
					}else if(PriceEntityConstants.PRICING_CODE_CCDDJS.equals(vo.getCode())){//超远派送费加收
						ptpWaybillOrgVo.setOverDistanceFee(ptpWaybillOrgVo.getOverDistanceFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_DJSL.equals(vo.getCode())){//大件上楼费
						ptpWaybillOrgVo.setBigGoodsUpFloorFee(ptpWaybillOrgVo.getBigGoodsUpFloorFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_QS.equals(vo.getCode())){//签收回单
						ptpWaybillOrgVo.setSignBillFee(ptpWaybillOrgVo.getSignBillFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(vo.getCode())){//送货进仓费
						ptpWaybillOrgVo.setDeliveryWareHouseFee(ptpWaybillOrgVo.getDeliveryWareHouseFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_SHAZ.equals(vo.getCode())){//送货安装费
						ptpWaybillOrgVo.setPickupToDoorJZFee(ptpWaybillOrgVo.getPickupToDoorJZFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_SH.equals(vo.getCode())){//纯送货费
						ptpWaybillOrgVo.setBaseDeliveryGoodsFee(BigDecimal.valueOf(Double.valueOf(ptpWaybillOrgVo.getBaseDeliveryGoodsFee())).add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
					}else if(PriceEntityConstants.PRICING_CODE_ZZ.equals(vo.getCode())){//异常操作服务费/中转费
						ptpWaybillOrgVo.setExceptionOpreationFee(ptpWaybillOrgVo.getExceptionOpreationFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
						expFeeCount++;
					}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(vo.getCode())){//送货上楼费(纯上楼费)
						ptpWaybillOrgVo.setUpFloorFee(BigDecimal.valueOf(Double.valueOf(ptpWaybillOrgVo.getUpFloorFee())).add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()) ;
					}
					
				}
			}
		}
		// ================优化内容:更改单推送至PTP的异常操作费传折前费用/时间:2016年11月1日下午9:37:03/LianHe/start================
		//当合伙人时，需要以下逻辑，重新设置ptpWaybillOrgVo中的异常操作费,因为合伙人要求推送给PTH系统计算的异常操作费
		if (BZPartnersJudge.IS_PARTENER && FossConstants.YES.equals(bean.getIsChanged())) {
			/**由于异常操作费是在点击“总运费按钮”前计算，且合伙人可以修改，传给PTP的其他费用的折前费用需要处理。
			  *其他费用其余费用项都是在点击“总运费按钮”钱计算，且页面其他费用明细中有的费用项需要重新归集，
			  *例如超远派送费需要归集到送货费，所以只能在现有的其他费用中减去当前的异常操作费后再加上“折前异常操作费”
			  *同样也需要修改推送PTP的总费用
			*/
			//当前总费用
			BigDecimal totalFeeOrg = null != ptpWaybillOrgVo.getTotalFee() ? ptpWaybillOrgVo.getTotalFee() : BigDecimal.ZERO;
			//当前其他费用
			BigDecimal otherFee = null != ptpWaybillOrgVo.getOtherFee() ? ptpWaybillOrgVo.getOtherFee() : BigDecimal.ZERO;
			//+++++总费用中去除当前其他费用
			totalFeeOrg = totalFeeOrg.subtract(otherFee);
			//当前异常操作费
			BigDecimal exceptionOpreationFee = null != ptpWaybillOrgVo.getExceptionOpreationFee() ? ptpWaybillOrgVo.getExceptionOpreationFee() : BigDecimal.ZERO;
			//1.当前其他费用先减去当前的异常操作费
			otherFee = otherFee.subtract(exceptionOpreationFee);
			
			//获取当前导入新的目的站时计算的异常操作费
			BigDecimal newZqExceptionOperateFee = (null != bean.getNewZqExceptionOperateFee() ? bean.getNewZqExceptionOperateFee() : BigDecimal.ZERO);
			//健壮性判定OldZqExceptionOperateFee不可以为null
			if (bean.getOldZqExceptionOperateFee() == null ) {
				bean.setOldZqExceptionOperateFee(BigDecimal.ZERO);
			}
			//判断当前共有几个异常操作费
			if (expFeeCount == 1) {//异常操作费数量只有一个，传当前折前异常操作费
				//考虑到普通更改单时，没有修改转运目的站的情况，需要推送的就应该是原来的折前异常操作费
				if (0 == newZqExceptionOperateFee.compareTo(BigDecimal.ZERO)) {//推送的就应该是原来的折前异常操作费
					ptpWaybillOrgVo.setExceptionOpreationFee(bean.getOldZqExceptionOperateFee());
				}else {//推送的应该是当前的折前异常操作费
					ptpWaybillOrgVo.setExceptionOpreationFee(newZqExceptionOperateFee);
				}
			} else if(expFeeCount > 1){//异常操作费数量大于一个时，传当前折前异常操作费与原折前异常操作费的和
				ptpWaybillOrgVo.setExceptionOpreationFee(newZqExceptionOperateFee.add(bean.getOldZqExceptionOperateFee()));
			}//异常操作费数量低于1个时，不处理
			
			//2.当处理完最终“折前异常操作费”后，其他费用再累加上去
			BigDecimal exceptionOpreationFeeFinal = null != ptpWaybillOrgVo.getExceptionOpreationFee() ? ptpWaybillOrgVo.getExceptionOpreationFee() : BigDecimal.ZERO;
			otherFee = otherFee.add(exceptionOpreationFeeFinal);
			ptpWaybillOrgVo.setOtherFee(otherFee);
			//++++总费用中再加上新的其他费用
			totalFeeOrg = totalFeeOrg.add(otherFee);
			ptpWaybillOrgVo.setTotalFee(totalFeeOrg);
		}
		// ================优化内容:更改单推送至PTP的异常操作费传折前费用/时间:2016年11月1日下午9:37:08/LianHe/end================
		//开单时使用
		PtpWaybillInfoOrgVo ptpWaybillInfoOrgVo = new PtpWaybillInfoOrgVo();
		ptpWaybillInfoOrgVo.setTargetOrgCode(bean.getTargetOrgCode()) ;
		ptpWaybillInfoOrgVo.setReceiveMethod(bean.getReceiveMethod().getValueCode()) ;
		ptpWaybillInfoOrgVo.setFibre(bean.getFibre()) ;
		ptpWaybillInfoOrgVo.setMembrane(bean.getMembrane()) ;
		ptpWaybillInfoOrgVo.setPaper(bean.getPaper()) ;
		ptpWaybillInfoOrgVo.setSalver(bean.getSalver()) ;
		ptpWaybillInfoOrgVo.setWood(bean.getWood()) ;
		
		bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
		bean.setPtpWaybillInfoOrgVo(ptpWaybillInfoOrgVo);
		
	}
	
	/**
	 * 合伙人 保存手工修改前的各项费用 快递 (暂有漏洞，后续更新)--漏洞已解  -sangwenhao
	 * @param voList 
	 * @param bean 
	 * @author 272311-sangwenhao
	 * @date 2016-1-21
	 */
	public static void keepStandardExpFee(List<OtherChargeVo> voList, WaybillPanelVo bean){
		PtpWaybillOrgVo ptpWaybillOrgVo = bean.getPtpWaybillOrgVo();
		if(ptpWaybillOrgVo == null){
			ptpWaybillOrgVo = PtpWaybillOrgVo.init() ; 
			bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
		}
		//保存 原始值 
		//2016年1月15日 15:46:14 葛亮亮 
		ptpWaybillOrgVo.setPickUpFee(bean.getPickupFee().toString());//接货费	
		ptpWaybillOrgVo.setPackageFee(bean.getPackageFee().toString());//包装费
		ptpWaybillOrgVo.setDeliveryGoodsFee(bean.getDeliveryGoodsFee().toString()) ;//送货费(界面)
		ptpWaybillOrgVo.setOtherFee(bean.getOtherFee()) ;//其他费用
		ptpWaybillOrgVo.setInsuranceFee(bean.getInsuranceFee()) ;//保价费
		ptpWaybillOrgVo.setCodFee(bean.getCodFee()) ;//代收货款手续费
		
		BigDecimal transportFee = null != bean.getTransportFee() ? bean.getTransportFee() : BigDecimal.ZERO; //公布运价
		BigDecimal totalFee = null != bean.getTotalFee() ? bean.getTotalFee() : BigDecimal.ZERO;//总费用
		//折前表
		BigDecimal zqtransportFee = null != bean.getZqTransportFee() ? bean.getZqTransportFee() : BigDecimal.ZERO; //公布运价
		
		//如果公布运价费没有重新计算获取折前表中的数据
		if(!FossConstants.YES.equals(bean.getIsCalTraFee()) && FossConstants.YES.equals(bean.getIsChanged())){
			zqtransportFee = zqtransportFee.subtract(transportFee);//用折前费用减去本次页面获取的值
			totalFee = totalFee.add(zqtransportFee); //总费用加上差值
			transportFee = null != bean.getZqTransportFee() ? bean.getZqTransportFee() : BigDecimal.ZERO; //公布运价运价费应该为折前
		}
		ptpWaybillOrgVo.setTransportFee(transportFee);
		
		//总费用
		ptpWaybillOrgVo.setTotalFee(totalFee) ;
		
		List<DeliverChargeEntity> lists = bean.getDeliverList() ;
		if(lists!=null && !lists.isEmpty()){
			for(DeliverChargeEntity dce : lists){
				if(PriceEntityConstants.PRICING_CODE_CY.equals(dce.getCode())){//超远派送费 
					ptpWaybillOrgVo.setOverDistanceFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_CCDDJS.equals(dce.getCode())){//超远派送费加收
					ptpWaybillOrgVo.setOverDistanceFee(ptpWaybillOrgVo.getOverDistanceFee().add(dce.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
				}else if(PriceEntityConstants.PRICING_CODE_DJSL.equals(dce.getCode())){//大件上楼费
					ptpWaybillOrgVo.setBigGoodsUpFloorFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_QS.equals(dce.getCode())){//签收回单
					ptpWaybillOrgVo.setSignBillFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(dce.getCode())){//送货进仓费
					ptpWaybillOrgVo.setDeliveryWareHouseFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SHAZ.equals(dce.getCode())){//送货安装费
					ptpWaybillOrgVo.setPickupToDoorJZFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SH.equals(dce.getCode())){//纯送货费
					ptpWaybillOrgVo.setBaseDeliveryGoodsFee(dce.getAmount().toPlainString());
				}else if(PriceEntityConstants.PRICING_CODE_ZZ.equals(dce.getCode())){//异常操作服务费/中转费
					ptpWaybillOrgVo.setExceptionOpreationFee(dce.getAmount());
				}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(dce.getCode())){//送货上楼费(纯上楼费)
					ptpWaybillOrgVo.setUpFloorFee(dce.getAmount().toPlainString()) ;
				}
			}
		}
		//其他费用 分解
		//获取其他费用
		if(CollectionUtils.isNotEmpty(voList)){
			for(OtherChargeVo vo : voList){
				if(StringUtils.isNotBlank(vo.getMoney())){
					if(PriceEntityConstants.PRICING_CODE_CY.equals(vo.getCode())){//超远派送费 
						ptpWaybillOrgVo.setOverDistanceFee(ptpWaybillOrgVo.getOverDistanceFee().add(new BigDecimal(vo.getMoney())).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
					}else if(PriceEntityConstants.PRICING_CODE_CCDDJS.equals(vo.getCode())){//超远派送费加收
						ptpWaybillOrgVo.setOverDistanceFee(ptpWaybillOrgVo.getOverDistanceFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_DJSL.equals(vo.getCode())){//大件上楼费
						ptpWaybillOrgVo.setBigGoodsUpFloorFee(ptpWaybillOrgVo.getBigGoodsUpFloorFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_QS.equals(vo.getCode())){//签收回单
						ptpWaybillOrgVo.setSignBillFee(ptpWaybillOrgVo.getSignBillFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(vo.getCode())){//送货进仓费
						ptpWaybillOrgVo.setDeliveryWareHouseFee(ptpWaybillOrgVo.getDeliveryWareHouseFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_SHAZ.equals(vo.getCode())){//送货安装费
						ptpWaybillOrgVo.setPickupToDoorJZFee(ptpWaybillOrgVo.getPickupToDoorJZFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_SH.equals(vo.getCode())){//纯送货费
						ptpWaybillOrgVo.setBaseDeliveryGoodsFee(BigDecimal.valueOf(Double.valueOf(ptpWaybillOrgVo.getBaseDeliveryGoodsFee())).add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
					}else if(PriceEntityConstants.PRICING_CODE_ZZ.equals(vo.getCode())){//异常操作服务费/中转费
						ptpWaybillOrgVo.setExceptionOpreationFee(ptpWaybillOrgVo.getExceptionOpreationFee().add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(vo.getCode())){//送货上楼费(纯上楼费)
						ptpWaybillOrgVo.setUpFloorFee(BigDecimal.valueOf(Double.valueOf(ptpWaybillOrgVo.getUpFloorFee())).add(BigDecimal.valueOf(Double.valueOf(vo.getMoney()))).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()) ;
					}
					
				}
			}
		}
		
		//开单时使用
		PtpWaybillInfoOrgVo ptpWaybillInfoOrgVo = new PtpWaybillInfoOrgVo();
		ptpWaybillInfoOrgVo.setTargetOrgCode(bean.getTargetOrgCode()) ;
		ptpWaybillInfoOrgVo.setReceiveMethod(bean.getReceiveMethod().getValueCode()) ;
		ptpWaybillInfoOrgVo.setFibre(bean.getFibre()) ;
		ptpWaybillInfoOrgVo.setMembrane(bean.getMembrane()) ;
		ptpWaybillInfoOrgVo.setPaper(bean.getPaper()) ;
		ptpWaybillInfoOrgVo.setSalver(bean.getSalver()) ;
		ptpWaybillInfoOrgVo.setWood(bean.getWood()) ;
		
		bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
		bean.setPtpWaybillInfoOrgVo(ptpWaybillInfoOrgVo);
		
	}
	
	
}