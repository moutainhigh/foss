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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/utils/Common.java
 * 
 * FILE NAME        	: Common.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.utils;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.DefaultComboBoxModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.BindingAssociation;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.IBallValidateWidget;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.ui.customer.BankAccountDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.QueryMemberDialog;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.PropertyFile;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 公共方法类，将共用的方法抽取出来统一调用，减少代码copy
 * 
 * 
 * 迁移开单Common类方法：
 * 	getQueryParam
 * 	setServiceFeeReate
 * 	getYokeCharge
 *	getStandCharge（界面上没有折扣显示部分，去除打木架后设置折扣优惠）
 *	getBoxCharge（同上，去除打木箱后设置折扣优惠）
 *	getQueryYokeParam
 * 
 * @author 025000-FOSS-helong
 * @date 2012-11-28 上午11:00:17
 */
public class Common {

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(Common.class);

	private static final double NUM_ZERO_POINT_FIVE = 0.5;

	private static final int SIX = 6; 
	
	private static IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();
	
	/**
	 * 参数配置
	 */
	private static IConfigurationParamsService configurationParamsService =  GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);
	
	public static String ZYF = "ZYF";
	
	public static String FHF = "FHF";
	
	public static String GBJ = "GBJ";

	/**
	 * 
	 * 获取产品价格查询参数
	 * getFinalStartOrgCode
	 * getFinalCustomerPickupOrgCode
	 * getFinalProductCode
	 * getFinalFlightNumberType
	 * 
	 * @author 025000-FOSS-helong
	 * @param ui 
	 * @date 2012-11-16 上午11:02:10
	 */
	public static QueryBillCacilateDto getQueryParam(WaybillInfoVo bean, String yesOrNo, String type) {
		QueryBillCacilateDto queryDto = new QueryBillCacilateDto();
		if(null!=bean.getPartnerBilling()){
			if(bean.getPartnerBilling()){
				queryDto.setPartnerBillingLogo("Y");
			}else{
				queryDto.setPartnerBillingLogo("N");
			}
		}
		//如果修改变更记录中第一条，影响公布价计算，否则影响中转费
		queryDto.setOriginalOrgCode(bean.getFinalStartOrgCode());// 出发部门CODE
		//如果返货重新计算公布价，目的地取返货起始网店
		if(GBJ.equals(type) && WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode())){
			queryDto.setDestinationOrgCode(bean.getRtnStartOrgCode());// 到达部门CODE
		}else{
			queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
		}
		queryDto.setProductCode(bean.getFinalProductCode().getCode());// 产品CODE
		ProductEntityVo productVo = bean.getFinalProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			
			if(bean.getAirGoodsType()!=null){
			queryDto.setGoodsCode(bean.getAirGoodsType().getValueCode());// 货物类型CODE
			}
			if(bean.getFinalFlightNumberType() == null)
			{
				queryDto.setFlightShift(null);// 航班号
			}else
			{
				queryDto.setFlightShift(bean.getFinalFlightNumberType().getValueCode());// 航班号
			}

		}
		
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		// queryDto.setIsReceiveGoods(BooleanConvertYesOrNo.booleanToString(bean.getPickupToDoor()));//
		// 是否接货
		queryDto.setIsReceiveGoods(yesOrNo);// 是否接货
		//是否自提
		if(WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			queryDto.setIsSelfPickUp(FossConstants.YES);
		}
		if(bean.getGoodsVolumePreviousTotal()!=null && bean.getGoodsWeightTotal()!=null 
				&& bean.getGoodsVolumePreviousTotal().doubleValue()> bean.getGoodsWeightTotal().doubleValue() ){
			queryDto.setWeight(bean.getGoodsVolumePreviousTotal());// 体积重比较大 用体积重来计算
		}else{
			queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		}
		
		//判断是不是手动修改的体积
		if(isHandChangeVolumn(bean.getWaybillNo())){
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}else{
			//有尺寸按照尺寸计算体积
			if(StringUtil.isBlank(bean.getGoodsSize())) {
				if(bean.getGoodsVolumePreviousTotal()!=null && bean.getGoodsWeightTotal()!=null 
						&& bean.getGoodsVolumePreviousTotal().doubleValue()> bean.getGoodsWeightTotal().doubleValue() ){
					queryDto.setVolume(BigDecimal.ZERO);// 体积
				}else{
					queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
				}
			} else {
				//根据尺寸计算体积
				calculateVolume(bean);
				if(bean.getCalculateVolume()!=null && bean.getGoodsVolumeTotal()!=null && 
						Math.abs(bean.getCalculateVolume().doubleValue() - bean.getGoodsVolumeTotal().doubleValue() )<0.01  ){
					queryDto.setVolume(BigDecimal.ZERO);// 体积
				}else{
					
					if(bean.getCalculateVolume()!=null && bean.getCalculateVolume().doubleValue()>0){
						queryDto.setVolume(bean.getCalculateVolume());// 体积
					}else{
						queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
					}
					
				}
			}
			
		}
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		//当serviceType=12时运单为裹裹的单子，将客户编码设置成业务虚拟客户编码"470290159"获取裹裹自有的价格方案
		String servicetype = waybillService.queryOrderByOrderNo(bean.getOrderNo());
		if(StringUtil.isNotEmpty(servicetype) && "12".equals(servicetype)){
			queryDto.setCustomerCode("470290159");
		}else{
			queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		}
		queryDto.setChannelCode(bean.getOrderChannel());//渠道
		
		//如果不是转运与返货，则设置营销活动
		if(!WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode()) &&
				!WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode())){			
			//营销活动DTO
			queryDto.setActiveDto(bean.getActiveDto());				
			//是否计算市场营销折扣
			queryDto.setCalActiveDiscount(bean.isCalActiveDiscount());			
			if(bean.getActiveInfo()!=null){
				queryDto.setActiveCode(bean.getActiveInfo().getValueCode());
			}		
			queryDto.setGoodsName(bean.getGoodsName());
			queryDto.setDeliveryCustomerCode(bean.getDeliveryCustomerCode());
			queryDto.setOrderChannel(bean.getOrderChannel());
			queryDto.setReceiveOrgCode(bean.getReceiveOrgCode());
			queryDto.setLoadOrgCode(bean.getLoadOrgCode());
			queryDto.setLastOutLoadOrgCode(bean.getLastOutLoadOrgCode());
			queryDto.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());
			queryDto.setBillTime(bean.getBillTime());
			queryDto.setTransportFee(bean.getTransportFee());
			queryDto.setGoodsWeightTotal(bean.getGoodsWeightTotal());
			queryDto.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());
		}
		
		//快递优惠活动
		if(bean.getSpecialOffer()!=null)
		{
		queryDto.setCityMarketCode(bean.getSpecialOffer().getValueCode());
		}
		
		
		/***
		 * 菜鸟返货折扣
		 */
		
		WaybillExpressEntity exp = waybillService.queryWaybillByWaybillNo(bean.getWaybillNo(),WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		if(exp!=null && exp.getOriginalWaybillNo()!=null){
			WaybillEntity entyty = waybillService.queryWaybillByNumber(exp.getOriginalWaybillNo());
			if(entyty != null){
				/**
				 * 收货客户省市区
				 */
				AddressFieldDto consigneeAddress = new BusinessUtils().getProvCityCounty(
						entyty.getDeliveryCustomerProvCode(),
						entyty.getDeliveryCustomerCityCode(),
						entyty.getDeliveryCustomerDistCode());
				String address="";
					if(consigneeAddress!=null){
						String provName = consigneeAddress.getProvinceName()!=null? consigneeAddress.getProvinceName():"" ;
						String cityName = consigneeAddress.getCityName()!=null? consigneeAddress.getCityName():"";
						String distName = consigneeAddress.getCountyName()!=null? consigneeAddress.getCountyName():"";
						address=provName+cityName+distName+entyty.getDeliveryCustomerAddress();
						address =StringUtils.substring(address, 0, NumberConstants.NUMBER_100);
					}
				
				 if(bean.getReceiveCustomerAddress().equals(address)
					        &&
					bean.getReceiveCustomerDistCode().equals(entyty.getDeliveryCustomerDistCode())
						    &&
					bean.getReceiveCustomerCityCode().equals(entyty.getDeliveryCustomerCityCode()) 
					        &&
				    bean.getReceiveCustomerProvCode().equals(entyty.getDeliveryCustomerProvCode())
					        &&
					bean.getPaidMethod().getValueCode().equals(WaybillConstants.ARRIVE_PAYMENT)){
		       
		        	queryDto.setIsCainiao(true);
		        	queryDto.setReturnWaybillNo(exp.getOriginalWaybillNo());
		        	queryDto.setOldreceiveCustomerCode(entyty.getDeliveryCustomerCode());
		        	queryDto.setReturnbilltime(entyty.getBillTime());
		        	queryDto.setReturnTransportFee(entyty.getTransportFee());
		        	queryDto.setReturnInsuranceFee(entyty.getInsuranceFee());
		        	queryDto.setOriginalReceiveOrgCode(entyty.getReceiveOrgCode());
			        	
				} 
			}
			
			
		}
		/**
		 * 根据条件查询当前月份的优惠总额
		 * dp-foss-dongjialing
		 * 225131
		 */
		BigDecimal amount = null;
		if(StringUtil.isNotBlank(bean.getEmployeeNo())) {
			amount = waybillService.queryDiscountFeeByEmployeeNo(bean.getEmployeeNo(), bean.getBillTime());
			if(amount == null) {
				amount = BigDecimal.ZERO;
			}
			if(bean.getEmployeeNo().equals(bean.getOriginalEmployeeNo())) {
				//工号没变额度需减去原单金额
				queryDto.setAmount(amount.subtract(bean.getOriginalFee().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100))));
			} else {
				queryDto.setAmount(amount);
			}
		}
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());	
		return queryDto;
	}
	
	/**
	 * 查询是否手动修改过运单体积
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午3:47:04
	 */
	public static boolean isHandChangeVolumn(String waybillNo){
		WaybillExpressEntity  entity = waybillService.queryWaybillExpressByNo(waybillNo);
		if(null != entity && FossConstants.YES.equals(entity.getChangeVolume())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取返货的产品价格查询参数
	 * @author WangQianJin
	 * @date 2013-7-5 下午12:39:49
	 */
	public static QueryBillCacilateDto getQueryParamForReturn(WaybillInfoVo bean, String yesOrNo, String type ,boolean isNow) {
		QueryBillCacilateDto queryDto = getQueryParam(bean,yesOrNo,type);
		/**
		 * 如果是返货，则出发部门设置为收货部门，目的是为了获取月结客户的优惠费用
		 */
		if(WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode())){
			queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
			queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		}
		// 营业部收货日期（null表示当前日期）
		if(isNow){
			queryDto.setReceiveDate(null);
		}
		return queryDto;
	}
	
	/**
	 * 
	 * 获取当前时刻产品价格查询参数
	 * getFinalStartOrgCode
	 * getFinalCustomerPickupOrgCode
	 * getFinalProductCode
	 * getFinalFlightNumberType
	 * 
	 * @author 025000-FOSS-helong
	 * @param ui 
	 * @date 2012-11-16 上午11:02:10
	 */
	public static QueryBillCacilateDto getNowQueryParam(WaybillInfoVo bean, String yesOrNo, String type) {
		QueryBillCacilateDto queryDto = getQueryParam(bean, yesOrNo, type);
		queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		return queryDto;
	}
	
	/**
	 * 
	 * 设置装卸费费率，写入到本地文件
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午03:05:31
	 */
	public static void setServiceFeeReate(BigDecimal serviceFeeRate , String receiveOrgCode)
	{
		
		PropertyFile property = new PropertyFile();
		//根据部门编号获取对应的装卸费率
		String value = property.readData(receiveOrgCode);
		if(StringUtils.isEmpty(value))
		{
			//根据部门编号写入装卸费率
			property.writeData(receiveOrgCode, serviceFeeRate.toString());
		}else
		{
			BigDecimal local = new BigDecimal(value);
			if(local.compareTo(serviceFeeRate) != 0)
			{
				//根据部门编号写入装卸费率
				property.writeData(receiveOrgCode, serviceFeeRate.toString());
			}
		}
	}
	
	/**
	 * 
	 * 验证只有合同客户才允许有免费送货
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午03:36:46
	 */
	public static void validateDeliverFree(WaybillPanelVo bean,WaybillRFCUI ui)
	{
		if(WaybillConstants.DELIVER_FREE.equals(bean.getReceiveMethod().getValueCode())
				|| WaybillConstants.DELIVER_FREE_AIR.equals(bean.getReceiveMethod().getValueCode()))
			{
				//如果提货方式与客户编码都没有改变。不验证
				if(ui.getOriginWaybill().getReceiveMethod().getValueCode().equals(bean.getReceiveMethod().getValueCode())){
					if(StringUtil.isNotEmpty(ui.getOriginWaybill().getDeliveryCustomerCode())){
						if(ui.getOriginWaybill().getDeliveryCustomerCode().equals(bean.getDeliveryCustomerCode())){
							return;
						}
					}
				}
				if(bean.getAuditNo() == null || "".equals(bean.getAuditNo()))
				{
						//强行中断
						throw new WaybillValidateException(i18n.get("foss.gui.creating.common.msgBox.nullAuditNo"));
		
				}else
				{
					CusBargainEntity eneity = waybillService.queryCusBargainByParams(bean.getAuditNo(), bean.getReceiveOrgCode());
					if(eneity == null)
					{
						//强行中断
						throw new WaybillValidateException(i18n.get("foss.gui.creating.common.msgBox.nullAuditNo"));
					}else
					{
						//表示合同是有效，则不需要进行处理
					}
				}
		}
	}
	
	/**
	 * 
	 * 获取代打木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 下午05:21:48
	 */
	public static void getYokeCharge(WaybillInfoVo bean) {
		// 获取木架费用
		getStandCharge(bean);
		// 获取木箱费用
		getBoxCharge(bean);
	}

	/**
	 * 
	 * 打木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static void getStandCharge(WaybillInfoVo bean) {
		ValueAddDto dto = new ValueAddDto();

		if (bean.getStandGoodsNum()!=null && bean.getStandGoodsNum() > 0) {
			// 打木架
			List<ValueAddDto> frameList = waybillService
					.queryValueAddPriceList(getQueryYokeParam(bean,
							DictionaryValueConstants.PACKAGE_TYPE__FRAME,
							bean.getStandGoodsVolume(),false));

			if(frameList==null||frameList.isEmpty()){
				frameList = waybillService
						.queryValueAddPriceList(getQueryYokeParam(bean,
								DictionaryValueConstants.PACKAGE_TYPE__FRAME,
								bean.getStandGoodsVolume(),true));
			}
			
			
			
			if (frameList != null) {
				if (!frameList.isEmpty()) {
					dto = frameList.get(0);
					// 这个if内的代码为了解决包装费不断累加的问题
					if (bean.getStandCharge() != null) {
						BigDecimal packageFee = bean.getPackageFee();
						// 四舍五入
						BigDecimal standCharge = bean.getStandCharge();
						packageFee = packageFee.subtract(standCharge);
						bean.setPackageFee(packageFee);
						bean.setCalculatedPackageFee(packageFee);
						// 设置折扣优惠
						setFavorableDiscount(dto.getDiscountPrograms(), bean);
		
					}

					bean.setStandCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenPackageFee"));
				}
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenPackageFee"));
			}
		}
	}

	/**
	 * 
	 * 打木箱费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static void getBoxCharge(WaybillInfoVo bean) {
		ValueAddDto dto = new ValueAddDto();
		if (bean.getBoxGoodsNum()!=null && bean.getBoxGoodsNum() > 0) {
			// 打木箱
			List<ValueAddDto> boxList = waybillService
					.queryValueAddPriceList(getQueryYokeParam(bean,
							DictionaryValueConstants.PACKAGE_TYPE__BOX,
							bean.getBoxGoodsVolume(),false));
			
			
			
			if(boxList==null||boxList.isEmpty()){
				 boxList = waybillService
							.queryValueAddPriceList(getQueryYokeParam(bean,
									DictionaryValueConstants.PACKAGE_TYPE__BOX,
									bean.getBoxGoodsVolume(),true));
			}

			if (boxList != null) {
				if (!boxList.isEmpty()) {
					dto = boxList.get(0);

					// 这个if内的代码为了解决每次计算包装费不断累加的问题
					if (bean.getBoxCharge() != null) {
						BigDecimal packageFee = bean.getPackageFee();
						//四舍五入
						BigDecimal boxCharge = bean.getBoxCharge();
						packageFee = packageFee.subtract(boxCharge);
						bean.setPackageFee(packageFee);
						bean.setCalculatedPackageFee(packageFee);
					}
					bean.setBoxCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
				}
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
			}
		}
	}
	

	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private static QueryBillCacilateValueAddDto getQueryYokeParam(WaybillInfoVo bean,
			String subType, BigDecimal volume,boolean isGetCurrentPrice) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(volume);// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		//如果不是转运与返货，则设置营销活动
		if(!WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode()) &&
				!WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode())){			
			//营销活动DTO
			queryDto.setActiveDto(bean.getActiveDto());				
			//是否计算市场营销折扣
			queryDto.setCalActiveDiscount(bean.isCalActiveDiscount());	
			if(bean.getActiveInfo()!=null){
				queryDto.setActiveCode(bean.getActiveInfo().getValueCode());
			}		
			queryDto.setGoodsName(bean.getGoodsName());
			queryDto.setDeliveryCustomerCode(bean.getDeliveryCustomerCode());
			queryDto.setOrderChannel(bean.getOrderChannel());
			queryDto.setReceiveOrgCode(bean.getReceiveOrgCode());
			queryDto.setLoadOrgCode(bean.getLoadOrgCode());
			queryDto.setLastOutLoadOrgCode(bean.getLastOutLoadOrgCode());
			queryDto.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());
			queryDto.setBillTime(bean.getBillTime());
			queryDto.setTransportFee(bean.getTransportFee());
			queryDto.setGoodsWeightTotal(bean.getGoodsWeightTotal());
			queryDto.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());
		}
		return queryDto;
	
	}
	
	/**
	 * 
	 * 设置保存按钮与提交按钮可编辑
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:24:45
	 */
	public static void setSaveAndSubmitTrue(WaybillRFCUI ui)
	{
		ui.getButtonPanel().getBtnSubmit().setEnabled(true);
	}

	/**
	 * 
	 * 设置银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午08:11:49
	 */
	public static void setBankInfo(WaybillPanelVo bean, WaybillRFCUI ui, IWaybillRfcService waybillService) {
		DataDictionaryValueVo vo = bean.getRefundType();
		if (vo != null && vo.getValueCode() != null) {
			if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.notDeliveryCustomerCode"));
			} else {
				List<CusAccountEntity> list = queryBankAccount(bean, waybillService);
				if (CollectionUtils.isNotEmpty(list)) {
					BankAccountDialog dialog = new BankAccountDialog(list);
					// 剧中显示弹出窗口
					WindowUtil.centerAndShow(dialog);

					CusAccountEntity entity = dialog.getCusAccountEntity();
					if (entity != null) {
						if(entity.getAccountNature()==null||"".equals(entity.getAccountNature())){
							MsgBox.showInfo(i18n.get("foss.gui.creating.common.exception.nullAccountNature"));
						}else{
    						bean.setOpenBank(entity);
    						//即日退只能选择对私账户
    						if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())) {
    							if(DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT.equals(entity.getAccountNature())){
        								bean.setAccountName(entity.getAccountName());// 开户人名称
        								bean.setAccountCode(entity.getAccountNo());// 开户账号
        								bean.setAccountBank(entity.getOpeningBankName());// 开户行名称		
    							}else{
    								MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.refundTypeOneDay"));
//    								bean.setCodAmount(BigDecimal.ZERO);
//    								// 判断是否可以开即日退
//    								setRefundType(bean, ui);
    								bean.setAccountBank("");
    								bean.setAccountCode("");
    								bean.setAccountName("");
    							}
    						} else {
    							bean.setAccountName(entity.getAccountName());// 开户人名称
    							bean.setAccountCode(entity.getAccountNo());// 开户账号
    							bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
    						}
						}
					}
				}else{
					if(WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.nullBankAccount"));
					}
				}
			}
		} else {
			cleanBankInfo(bean);
		}
	}
	
	
	/**
	 * 
	 * 验证银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午08:11:49
	 */
	public static void validateBankInfo(WaybillPanelVo bean, WaybillRFCUI ui, IWaybillRfcService waybillService) {
		DataDictionaryValueVo vo = bean.getRefundType();
		if (vo != null && vo.getValueCode() != null) {
			if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.notDeliveryCustomerCode"));
			} else {
					CusAccountEntity entity = bean.getOpenBank();
					if (entity != null) {
						if(entity.getAccountNature()==null||"".equals(entity.getAccountNature())){
							MsgBox.showInfo(i18n.get("foss.gui.creating.common.exception.nullAccountNature"));
						}else{
    						bean.setOpenBank(entity);
    						//即日退只能选择对私账户
    						if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())) {
    							if(DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT.equals(entity.getAccountNature())){
        								bean.setAccountName(entity.getAccountName());// 开户人名称
        								bean.setAccountCode(entity.getAccountNo());// 开户账号
        								bean.setAccountBank(entity.getOpeningBankName());// 开户行名称		
    							}else{
    								MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.refundTypeOneDay"));
//    								bean.setCodAmount(BigDecimal.ZERO);
//    								// 判断是否可以开即日退
//    								setRefundType(bean, ui);
    								bean.setAccountBank("");
    								bean.setAccountCode("");
    								bean.setAccountName("");
    							}
    						} else {
    							bean.setAccountName(entity.getAccountName());// 开户人名称
    							bean.setAccountCode(entity.getAccountNo());// 开户账号
    							bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
    						}
						}
					}
			}
		} else {
			cleanBankInfo(bean);
		}
	}
	
	/**
	 * 
	 * 查询客户银行账号信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:50:56
	 */
	public static List<CusAccountEntity> queryBankAccount(WaybillPanelVo bean, IWaybillRfcService waybillService) {
		List<CusAccountEntity> list = waybillService.queryBankAccountByCode(bean.getDeliveryCustomerCode());
		
		if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())){
			if(list!=null&&list.size()>0){
				List<CusAccountEntity> newList = new ArrayList<CusAccountEntity>();
				for(int i=0;i<list.size();i++){
					BankEntity bank = waybillService.queryBankByCode(list.get(i).getBankCode());
					if(bank != null)
					{
						if(FossConstants.YES.equals(bank.getIntraDayType())){
							newList.add(list.get(i));
						}
					}
				}
				if(newList.size()==0){
					return null;
				}else{
					return newList;
				}
			}else{
				return list;
			}
		}else{
			return list;
		}
	}


	/**
	 * 
	 * 设置退款类型为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	public static void setRefundType(WaybillPanelVo bean, WaybillRFCUI ui) {
		int size = ui.getCombRefundTypeModel().getSize();
		ui.incrementPanel.getCombRefundType().setSelectedItem(null);
		bean.setRefundType(null);
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = null;
			vo = (DataDictionaryValueVo) ui.getCombRefundTypeModel().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setRefundType(vo);
			}
		}
	}
	

	/**
	 * 
	 * 清理银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:07:44
	 */
	public static void cleanBankInfo(WaybillPanelVo bean) {
		// 收款人名称
		bean.setAccountName("");
		// 收款人开户行
		bean.setAccountBank("");
		// 收款人银行账号
		bean.setAccountCode("");
		// 收款人银行信息
		bean.setOpenBank(null);
	}

	/**
	 * 设置集中开单收货部门信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午8:25:09
	 */
	public static void setSalesDepartmentForCentrial(SaleDepartmentEntity entity,WaybillPanelVo bean,WaybillRFCUI ui ){
		//对象非空判断
		if (entity != null) {
			//收货部门编号
			bean.setReceiveOrgCode(entity.getCode());
			//货部门省份编码
			if(StringUtil.isNotEmpty(entity.getCode())){
			bean.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(entity.getCode()));
			}
			//收货部门名称
			bean.setReceiveOrgName(entity.getName());
			//设置创建时间
			bean.setReceiveOrgCreateTime(entity.getOpeningDate());
			//清空现有产品
			ui.getProductTypeModel().removeAllElements();
			//设置新的产品信息
			ui.setProductTypeModel(entity.getCode());
			//设置产品默认值
			ui.setProductCode(bean);
		}
	}


	
	
	
	/**
	 * 
	 * 清理代收货款信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午05:16:21
	 */
	public static void cleanCodInfo(WaybillRFCUI ui,WaybillPanelVo bean) {
		// 清理银行信息
		Common.cleanBankInfo(bean);
		// 代收货款金额
		bean.setCodAmount(BigDecimal.ZERO);
		// 代收货款费率
		bean.setCodRate(BigDecimal.ZERO);
		// 代收货款手续费
		bean.setCodFee(BigDecimal.ZERO);
		// 代收货款ID
		bean.setCodId("");
		// 代收货款编码
		bean.setCodCode("");
		// 画布-代收货款金额
		bean.setCodAmountCanvas(BigDecimal.ZERO.toString());
		// 将退款类型设置为空
		Common.setRefundType(bean, ui);
	}
	
	
	/**
	 * 
	 * 设置折扣优惠
	 * @param discountPrograms
	 * @param bean
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-16 下午3:29:55
	 */
	public static void setFavorableDiscount(List<PriceDiscountDto> discountPrograms,WaybillInfoVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = bean.getWaybillDiscountVos();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
				bean.setWaybillDiscountVos(data);
			}
			for (PriceDiscountDto dto : discountPrograms) {

				if ((PriceEntityConstants.PRICING_CODE_BF.equals(dto
						.getPriceEntryCode()))
						&& (!DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE
								.equals(dto.getType()))) {
					if (bean.getInsuranceAmount().compareTo(BigDecimal.ZERO) == 0) {
						continue;
					}
				}
				CommonUtils.add(dto, data, bean);
			}
		}
	}
	
	/**
	 * 清空收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午10:32:06
	 */
	public static void cleanReceiveCustomerInfo(WaybillRFCUI ui, WaybillPanelVo bean, String mobile, String phone) {
		// 收货客户联系人
		bean.setReceiveCustomerContact("");
		ui.consigneePanel.getTxtConsigneeLinkMan().setText("");
		// 收货客户编码
		bean.setReceiveCustomerCode("");
		ui.consigneePanel.getTxtReceiveCustomerCode().setText("");
		// 大客户标记
		bean.setReceiveBigCustomer(FossConstants.INACTIVE);
		// 取消大客户标记
		ui.consigneePanel.getLblReceiveCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		// 联系人ID
		bean.setReceiveCustomerContactId("");
		//wutao
		bean.setArriveCentralizedSettlement("");
		bean.setArriveContractOrgCode("");
		bean.setArriveContractOrgName("");
		bean.setArriveReminderOrgCode("");
		//end
		// 收货客户手机
		bean.setReceiveCustomerMobilephone(mobile);
		ui.consigneePanel.getTxtConsigneeMobile().setText(mobile);
		// 收货客户电话
		bean.setReceiveCustomerPhone(phone);
		ui.consigneePanel.getTxtConsigneePhone().setText(phone);
		// 收货客户ID
		bean.setReceiveCustomerId("");
		// 收货客户地址
		bean.setReceiveCustomerAddress("");
		ui.consigneePanel.getTxtConsigneeAddress().setText("");
		// 收货客户地址
		bean.setReceiveCustomerAddressNote("");
		ui.consigneePanel.getTxtConsigneeAddressNote().setText("");
		// 收货人地址字体颜色
		ui.getTxtConsigneeAddress().setForeground(Color.BLACK);
		// 行政区域
		bean.setReceiveCustomerArea("");
		ui.consigneePanel.getTxtConsigneeArea().setText("");
		bean.setReceiveCustomerAreaDto(null);
		// 设置联系人为可编辑
		ui.getTxtConsigneeLinkMan().setEnabled(true);
		
		
		
	}
	
	/**
	 * 填充收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午9:14:31
	 */
	public static void fillReceiveCustomerInfo(WaybillRFCUI ui, QueryMemberDialogVo memberBean, WaybillPanelVo bean) {
		// 对传入对象进行非空判断
		if (ui == null || memberBean == null || bean == null) {
			return;
		}
		// 定义业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 手机号码
		bean.setReceiveCustomerMobilephone(memberBean.getMobilePhone());
		ui.consigneePanel.getTxtConsigneeMobile().setText(bean.getReceiveCustomerMobilephone());
		// 联系人
		bean.setReceiveCustomerContact(memberBean.getLinkman());
		ui.consigneePanel.getTxtConsigneeLinkMan().setText( bean.getReceiveCustomerContact() );
		// 电话
		bean.setReceiveCustomerPhone(memberBean.getPhone());
		ui.consigneePanel.getTxtConsigneePhone().setText(bean.getReceiveCustomerPhone());
		// 地址
		bean.setReceiveCustomerAddress(memberBean.getAddress());
		ui.consigneePanel.getTxtConsigneeAddress().setText(bean.getReceiveCustomerAddress());
		// 地址备注
		bean.setReceiveCustomerAddressNote(memberBean.getAddressRemark());
		ui.consigneePanel.getTxtConsigneeAddressNote().setText(bean.getReceiveCustomerAddressNote());
		// 客户名称
		bean.setReceiveCustomerName(memberBean.getCustomerName());
		ui.consigneePanel.getTxtConsigeeName().setText(bean.getReceiveCustomerName());
		// 客户编码
		bean.setReceiveCustomerCode(memberBean.getCustomerCode());
		ui.consigneePanel.getTxtReceiveCustomerCode().setText(bean.getReceiveCustomerCode() );
		// 大客户标记
		//bean.setReceiveBigCustomer(memberBean.getIsBigCustomer());
		
		//改为快递的大客户标识 @author wutao
		bean.setReceiveBigCustomer(memberBean.getIsExpressBigCustomer());
		
		// 客户ID
		bean.setReceiveCustomerId(memberBean.getCustId());
		// 联系人ID
		bean.setReceiveCustomerContactId(memberBean.getConsignorId());

		// 定义省市区县对象
		AddressFieldDto address = Common.getAddressFieldInfoByCode(ui, memberBean.getProvinceCode(), memberBean.getCityCode(), memberBean.getCountyCode());
		// 收货省份
		bean.setReceiveCustomerProvCode(address.getProvinceId());
		// 收货市
		bean.setReceiveCustomerCityCode(address.getCityId());
		// 收货区
		bean.setReceiveCustomerDistCode(address.getCountyId());
		// 省市区县DTO
		bean.setReceiveCustomerAreaDto(address);
		// 省市区县文本
		bean.setReceiveCustomerArea(bu.getAddressAreaText(address));
		// 设置界面"省市区县DTO"对象:适用于通过手机号码等带出的省市区县
		ui.getConsigneePanel().getTxtConsigneeArea().setAddressFieldDto(address);

		// 客户信息发生改变，需要清空上一个客户的代收货款收款信息
		Common.cleanBankInfo(bean);
		// 若客户类型为CRM正式客户，则发货联系不可修改
		if (WaybillConstants.CUSTOMER_TYPE_FORMAL.equals(StringUtil.defaultIfNull(memberBean.getCustomerType()))) {
			ui.getTxtConsigneeLinkMan().setEnabled(false);
		} else {
			ui.getTxtConsigneeLinkMan().setEnabled(true);
		}
		
		//是否显示大客户标记
		//改为快递的大客户标识 @author wutao
		if(FossConstants.ACTIVE.equals(memberBean.getIsExpressBigCustomer())){
			ui.consigneePanel.getLblReceiveCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			ui.consigneePanel.getLblReceiveCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		}

		// 设置接送货地址ID
		bean.setContactAddressId(memberBean.getAddressId());
	}
	
	/**
	 * 获得省市区县地址对象:直接根据省市区县编码查询DTO
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午4:29:58
	 */
	public static AddressFieldDto getAddressFieldInfoByCode(WaybillRFCUI ui, String provCode, String cityCode, String distCode) {
		// 业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 获得省市区县对象
		AddressFieldDto address = bu.getProvCityCounty(provCode, cityCode, distCode);
		// 接收地址对象
		return Common.getAddressFiledInfo(address, ui);
	}
	
	/**
	 * 获得省市区县地址对象:若dto为空，则从界面中取对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午4:29:58
	 */
	public static AddressFieldDto getAddressFiledInfo(AddressFieldDto dto, WaybillRFCUI ui) {
		// 接收地址对象
		AddressFieldDto address = dto;
		// 判断地址对象是否为空
		if (null == address) {
			// 从文本框中获取对象
			address = ui.consigneePanel.getTxtConsigneeArea().getAddressFieldDto();
		}
		return address;
	}
	
	/**
	 * 清空发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午10:32:06
	 */
	public static void cleanDeliveryCustomerInfo(WaybillRFCUI ui, WaybillPanelVo bean, String mobile, String phone) {
		// 发货客户ID
		bean.setDeliveryCustomerId("");
		// 发货客户编码
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setDeliveryCustomerCode("");
		}
		ui.consignerPanel.getTxtDeliveryCustomerCode().setText(bean.getDeliveryCustomerCode());
		// 大客户标记
		bean.setDeliveryBigCustomer(FossConstants.INACTIVE);
		// 发货联系人ID
		bean.setDeliveryCustomerContactId("");
		// 发货客户手机
		bean.setDeliveryCustomerMobilephone(mobile);
		ui.consignerPanel.getTxtConsignerMobile().setText(bean.getDeliveryCustomerMobilephone());
		
		// 发货客户电话
		bean.setDeliveryCustomerPhone(phone);
		ui.consignerPanel.getTxtConsignerPhone().setText(bean.getDeliveryCustomerPhone());
		
		// 发货客户地址
		bean.setDeliveryCustomerAddress("");
		ui.consignerPanel.getTxtConsignerAddress().setText(bean.getDeliveryCustomerAddress());
		
		// 发货客户地址备注
		bean.setDeliveryCustomerAddressNote("");
		ui.consignerPanel.getTxtConsignerAddressNote().setText(bean.getDeliveryCustomerAddressNote());
		// 是否月结
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户月结
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setChargeMode(Boolean.valueOf(false));
		}
		//优惠类型
		bean.setPreferentialType("");
		//wutao
		bean.setStartCentralizedSettlement("");
		bean.setStartContractOrgCode("");
		bean.setStartContractOrgName("");
		bean.setStartReminderOrgCode("");
		//end
		// 合同编号
		bean.setAuditNo("");
		// 行政区域
		bean.setDeliveryCustomerArea("");
		ui.consignerPanel.getTxtConsignerArea().setText(bean.getDeliveryCustomerArea());
		bean.setDeliveryCustomerAreaDto(null);
		// 发货联系人编辑状态
		ui.getTxtConsignerLinkMan().setEnabled(true);
		// 设置装卸费编辑状态-月结客户不允许开装卸费
		Common.setServiceChargeEnabled("",false, ui);

		// 发货客户联系人:放在最后，校验通过不过时不会影响编辑状态的修改
		bean.setDeliveryCustomerContact("");
		ui.consignerPanel.getTxtConsignerLinkMan().setText(ui.getBinderWaybill().getDeliveryCustomerContact());
	}
	
	/**
	 * 
	 * 设置装卸费编辑状态-月结客户不允许开装卸费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午07:14:50
	 */
	public static void setServiceChargeEnabled(String preferentialType,Boolean bool, WaybillRFCUI ui) {
		// 月结客户不允许开装卸费
		/*if (bool) {
			ui.incrementPanel.getTxtServiceCharge().setEnabled(false);
		} else {
			ui.incrementPanel.getTxtServiceCharge().setEnabled(true);
		}*/
		// 月发月送允许修改装卸费
//		if (StringUtils.isNotEmpty(preferentialType)) {
//			if (preferentialType
//					.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
//				ui.incrementPanel.getTxtServiceCharge().setEnabled(true);// 装卸费
//			}
//
//		}
	}
	
	/**
	 * 填充发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午9:14:31
	 */
	public static void fillDeliveryCustomerInfo(WaybillRFCUI ui, QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean) {
		// 对传入对象进行非空判断
		if (ui == null || memberBean == null || waybillBean == null) {
			return;
		}
		// 定义业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 手机号码
		waybillBean.setDeliveryCustomerMobilephone(memberBean.getMobilePhone());
		ui.consignerPanel.getTxtConsignerMobile().setText( waybillBean.getDeliveryCustomerMobilephone() );
		// 联系人
		waybillBean.setDeliveryCustomerContact(memberBean.getLinkman());
		ui.consignerPanel.getTxtConsignerLinkMan().setText( waybillBean.getDeliveryCustomerContact());
		// 电话
		waybillBean.setDeliveryCustomerPhone(memberBean.getPhone());
		ui.consignerPanel.getTxtConsignerPhone().setText(waybillBean.getDeliveryCustomerPhone());
		// 地址
		waybillBean.setDeliveryCustomerAddress(memberBean.getAddress());
		ui.consignerPanel.getTxtConsignerAddress().setText(waybillBean.getDeliveryCustomerAddress ());
		
		// 地址备注
		waybillBean.setDeliveryCustomerAddressNote(memberBean.getCustAddrRemark());
		ui.consignerPanel.getTxtConsignerAddressNote().setText(waybillBean.getDeliveryCustomerAddressNote());
		// 客户名称
		waybillBean.setDeliveryCustomerName(memberBean.getCustomerName());
		ui.consignerPanel.getTxtConsigerName().setText( waybillBean.getDeliveryCustomerName() );
		// 客户编码
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.ALIBABA.equals(waybillBean.getOrderChannel())){
			waybillBean.setDeliveryCustomerCode(memberBean.getCustomerCode());
		}
		ui.consignerPanel.getTxtDeliveryCustomerCode().setText( waybillBean.getDeliveryCustomerCode());
		//大客户标记
		//waybillBean.setDeliveryBigCustomer(memberBean.getIsBigCustomer());
		
		//更更改单设置大客户标识：改为快递的大客户标识 @author wutao
		waybillBean.setDeliveryBigCustomer(memberBean.getIsExpressBigCustomer());
		// 客户ID
		waybillBean.setDeliveryCustomerId(memberBean.getCustId());
		// 联系人ID
		waybillBean.setDeliveryCustomerContactId(memberBean.getConsignorId());
		// 合同编号
		waybillBean.setAuditNo(memberBean.getAuditNo());
		// 月结属性
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户月结
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.ALIBABA.equals(waybillBean.getOrderChannel())){
			waybillBean.setChargeMode(memberBean.getChargeMode());
		}

		//优惠类型
		waybillBean.setPreferentialType(memberBean.getPreferentialType());
		// 定义省市区县对象
		AddressFieldDto address = Common.getAddressFieldInfoByCode(ui, memberBean.getProvinceCode(), memberBean.getCityCode(), memberBean.getCountyCode());
		// 发货省份
		waybillBean.setDeliveryCustomerProvCode(address.getProvinceId());
		// 发货市
		waybillBean.setDeliveryCustomerCityCode(address.getCityId());
		// 发货区
		waybillBean.setDeliveryCustomerDistCode(address.getCountyId());
		// 省市区县DTO
		waybillBean.setDeliveryCustomerAreaDto(address);
		// 省市区县文本
		waybillBean.setDeliveryCustomerArea(bu.getAddressAreaText(address));
		
		waybillBean.setPreferentialType(memberBean.getPreferentialType());
		// 设置界面"省市区县DTO"对象:适用于通过手机号码等带出的省市区县
		ui.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(address);

		// 设置装卸费是的编辑状态
		Common.setServiceChargeEnabled(memberBean.getPreferentialType(),memberBean.getChargeMode(), ui);
		
		// 客户信息发生改变，需要清空上一个客户的代收货款收款信息
		Common.cleanBankInfo(waybillBean);

		// 若客户类型为CRM正式客户，则发货联系不可修改
//		if (WaybillConstants.CUSTOMER_TYPE_FORMAL.equals(StringUtil.defaultIfNull(memberBean.getCustomerType()))) {
//			ui.getTxtConsignerLinkMan().setEnabled(false);
//		} else {
//			ui.getTxtConsignerLinkMan().setEnabled(true);
//		}
		
		//设置大客户标记
		//改为快递的大客户标识 @author wutao
		if(FossConstants.ACTIVE.equals(memberBean.getIsExpressBigCustomer())){
			ui.consignerPanel.getLblDeliveryCustomerCode().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			ui.consignerPanel.getLblDeliveryCustomerCode().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		}
		//WUTAO
		//给发货客户设置【是否统一结算】【合同部门】【催款部门】
		setDeliverySettleAndContactAndRemending(memberBean, waybillBean);
	}

	/**
	 * @author 200945-wutao =====  start
	 * 发货客户业务逻辑：
	 * 1.发货客户的“是否统一结算”、“合同部门”、“催款部门”根据客户编码从客户信息中自动获得，不可手工修改。
	 * 若未维护此客户信息，则“是否统一结算”默认为“否”、合同部门为空
	 * 
	 * 2.运单开单发票标记为02时 : 若发货客户统一结算标记为”是”时,则运单上”发货客户是否统一结算”为”是”,”发货客户合同部门”为对应合同部门、“催款部门”为合同对应催款部门; 
	 * 若发货客户统一结算标记为”否”时,则运单上”发货客户是否统一结算”为”否”,”发货客户合同部门” 发货客户“催款部门”为空
	 * 
	 * 3.01运单不进行始发统一结算，若运单的发票标记为01,则运单的”发货客户是否统一结算“默认为”否“、合同部门为空、催款部门”为空
	 */
	private static void setDeliverySettleAndContactAndRemending(QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean){
		if ("".equals(memberBean.getCentralizedSettlement())
				&& "".equals(memberBean.getContractOrgCode())
				&& "".equals(memberBean.getReminderOrgCode())) {
			waybillBean
					.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			waybillBean.setStartContractOrgCode(null);
			waybillBean
					.setStartContractOrgName(null);
			waybillBean.setStartReminderOrgCode(null);
		} else {
			if (WaybillConstants.INVOICE_02.equals(memberBean.getInvoice())) {
				if (WaybillConstants.YES.equals(memberBean
						.getCentralizedSettlement())) {
					waybillBean
							.setStartCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
					waybillBean.setStartContractOrgCode(memberBean
							.getContractOrgCode());
					waybillBean.setStartContractOrgName(memberBean
							.getContractOrgName());
					waybillBean.setStartReminderOrgCode(memberBean
							.getReminderOrgCode());
				} else {
					waybillBean
							.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
					waybillBean.setStartContractOrgCode(null);
					waybillBean
							.setStartContractOrgName(null);
					waybillBean.setStartReminderOrgCode(null);
				}
			} else {
				waybillBean
						.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				waybillBean.setStartContractOrgCode(null);
				waybillBean
						.setStartContractOrgName(null);
				waybillBean.setStartReminderOrgCode(null);
			}
		}
		//wutao == end 
	}
	/**
	 * 
	 * 空运、偏线以及中转下线无法选择签收单返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:09:08
	 */
	public static void setReturnBill(WaybillInfoVo bean, WaybillRFCUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 转运
		if (bean.getRfcType() != null) {
			//变更类型
			String rfcType = bean.getRfcType().getValueCode();
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
				productVo = bean.getTfrProductCode();
			} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
				productVo = bean.getRtnProductCode();
			}
			if (null == productVo) {
				return;
			}

			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode()) || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(false);
				// 设置返单类型默认值
				Common.setReturnBillType(bean, ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getReturnBillTypeModel());
				// 将返单费用设置到其他费用表格中
				Common.setReturnBillCharge(bean, ui);
			} else {
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(true);
			}
		}
	}
	/**
	 * 
	 * 设置返单类型默认值
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午02:45:07
	 */
	public static void setReturnBillType(WaybillPanelVo bean, DefaultComboBoxModel model) {
		for (int i = 0; i < model.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) model.getElementAt(i);
			if (WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				bean.setReturnBillType(vo);
			}
		}
	}
	
	/**
	 * 
	 * 设置返单费用到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	public static void setReturnBillCharge(WaybillPanelVo bean, WaybillRFCUI ui) {
		JXTable otherTable = ui.getWaybillInfoPanel().getIncrementPanel().getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {
			if (data == null || data.isEmpty()) {
				data = new ArrayList<OtherChargeVo>();
			}

			List<ValueAddDto> list = waybillService.queryValueAddPriceList(Common.getQueryOtherChargeParam(bean, bean.getReturnBillType().getValueCode()));
			OtherChargeVo otherVo = Common.getReturnBillCharge(bean, list, data, ui);
			// 添加返单费用到其他费用表格
			String chargeName = Common.addOtherCharge(data, otherVo, bean);
			// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
			bean.setReturnBillChargeName(chargeName);
			ui.incrementPanel.setChangeDetail(data);
		} else {
			// 删除返单
			deleteReturnBill(data, bean, ui);
		}
	}
	/**
	 * 
	 * 如果选择的返单类型为无返单，那么需要将之前存在的返单费用删除
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 上午10:44:29
	 */
	public static void deleteOtherCharge(List<OtherChargeVo> data, WaybillPanelVo bean, String name) {
		for (int i = 0; i < data.size(); i++) {
			OtherChargeVo otherVo = data.get(i);
			// 比较费用名称，判断是否存在重复的返单费用
			if (otherVo.getChargeName().equals(name) 	
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(otherVo.getCode())) {
				data.remove(i);
				// 累计其他费用合计
				BigDecimal otherChargeSum = bean.getOtherFee();
				BigDecimal money = new BigDecimal(otherVo.getMoney());
				otherChargeSum = otherChargeSum.subtract(money);
				bean.setOtherFee(otherChargeSum);
				bean.setOtherFeeCanvas(bean.getOtherFee().toString());
				break;
			}
		}
	}
	/**
	 * 
	 * 删除返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午03:52:12
	 */
	private static void deleteReturnBill(List<OtherChargeVo> data, WaybillPanelVo bean, WaybillRFCUI ui) {
		if (data != null && !data.isEmpty()) {
			// 将已有的返单费用从其他费用表格中删除
			Common.deleteOtherCharge(data, bean, bean.getReturnBillChargeName());
			ui.getWaybillInfoPanel().getIncrementPanel().setChangeDetail(data);
		}
	}
	
	/**
	 * 
	 * 对其他费用进行校验，判断是否存在当前查询的费用，不存在则添加其他费用，并且进行其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 下午07:06:33
	 */
	public static String addOtherCharge(List<OtherChargeVo> data, OtherChargeVo otherChargeVo, WaybillPanelVo bean) {
		boolean bool = true;
		OtherChargeVo oldVo = null;

		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				// 比较费用名称，判断是否存在重复的返单费用
				if (otherVo.getChargeName().equals(otherChargeVo.getChargeName())) {
					bool = false;
					oldVo = data.get(i);
					data.remove(i);
					data.add(i, otherChargeVo);
				}
			}
		}

		// 如果不存在任何费用，则直接添加
		if (bool) {
			if(data != null){
				data.add(otherChargeVo);
			}
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal newMoney = new BigDecimal(otherChargeVo.getMoney());
			otherChargeSum = otherChargeSum.add(newMoney);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		} else {
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal oldMoney = new BigDecimal(oldVo.getMoney());
			BigDecimal newMoney = new BigDecimal(otherChargeVo.getMoney());
			BigDecimal money = newMoney.subtract(oldMoney);
			otherChargeSum = otherChargeSum.add(money);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		}

		return otherChargeVo.getChargeName();
	}
	
	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public static QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPanelVo bean, String type) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());//客户编码
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(type);
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		if (WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {// 判断有无返单类型
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
																				// 其他费用
		} else {
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);// 计价条目CODE
																				// 签收回单
		}
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private static OtherChargeVo getReturnBillCharge(WaybillPanelVo bean, List<ValueAddDto> list, List<OtherChargeVo> data, WaybillRFCUI ui) {
		ValueAddDto dto = new ValueAddDto();
		OtherChargeVo vo = new OtherChargeVo();
		if (list != null) {
			if (!list.isEmpty()) {
				dto = list.get(0);
				// 费用编码
				vo.setCode(dto.getPriceEntityCode());
				// 名称
				vo.setChargeName(dto.getPriceEntityName());
				// 归集类别
				vo.setType(dto.getBelongToPriceEntityName());
				// 金额
				vo.setMoney(dto.getFee().toString());
				// 上限
				vo.setUpperLimit(dto.getMaxFee().toString());
				// 下限
				vo.setLowerLimit(dto.getMinFee().toString());
				// 是否可修改
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));
				// 是否可删除
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));
				vo.setId(dto.getId());
			} else {
				// 删除返单
				deleteReturnBill(data, bean, ui);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
			}
		} else {
			// 删除返单
			deleteReturnBill(data, bean, ui);
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
		}
		return vo;
	}
	
	/**
	 * 
	 * 方法详细描述说明、方法参数的具体涵义
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-23 上午10:10:46
	 */
	public static Boolean validateDescriptor(List<ValidationError> errorList) {
		Boolean bool = false;
		int i = 0;
		for (ValidationError error : errorList) {

			final Component jComponent = ((BindingAssociation) error.getKey()).getComponent();
			if (jComponent instanceof IBallValidateWidget) {
				IBallValidateWidget field = (IBallValidateWidget) jComponent;
				field.verifyWrong(error.getMessage());
				if (WaybillConstants.SUCCESS.equals(error.getMessage())) {
					i++;
				}
			} else {
				MsgBox.showError(error.getMessage());
				bool = false;
			}
		}

		if (i == errorList.size()) {
			bool = true;
		}
		return bool;
	}
	

	/**
	 * 将弹出框中查询到的发货客户信息设置到开单界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午7:12:41
	 */
	public static void setQueryDeliveryCustomer(WaybillRFCUI ui) {
		// 将获得的值传到界面上
		WaybillPanelVo waybillBean = ui.getBinderWaybill();
		// 定义全局对象，用来判断该窗口是否已创建，已节约资源
		QueryMemberDialog panel = new QueryMemberDialog(waybillBean);
		// 居中显示弹出窗口
		WindowUtil.centerAndShow(panel);
		// 获得VO
		QueryMemberDialogVo memberBean = panel.getMemberVo();
		if (null == memberBean) {
			return;
		}


		// 填充发货客户信息
		fillDeliveryCustomerInfo(ui, memberBean, waybillBean);
	}

	/**
	 * 
	 * 判断数据是否为空，如果为空则返回零
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-24 下午2:13:14
	 */
	public static BigDecimal nullBigDecimalToZero(BigDecimal big) {
		if (big == null) {
			return BigDecimal.ZERO;
		} else {
			return big;
		}
	}

	/**
	 * 根据用户编码获得用户
	 * （PS：user对象中name全部编码，故只能从employee中取得名称 ）
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-25 上午8:58:02
	 */
	public static String getEmployeeNameByCode(String code){
		//定义查询集合
		List<String> codes = new ArrayList<String>();
		codes.add(StringUtil.defaultIfNull(code));
		
		//接收返回集合数据
		List<EmployeeEntity> list = waybillService.queryEmployeeByCodeList(codes);
		//判断查询是否为空
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0).getEmpName();
		}else{
			return CommonUtils.getUserNameFromUserService(code);
		}
	}
	
	/**
	 * 
	 * 获取包装
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 上午11:05:50
	 */
	public static String getPackage(WaybillPanelVo vo) {
		String pack = "";
		if (vo.getPaper() != null && vo.getPaper().intValue() != 0) {
			pack = pack + vo.getPaper() + i18n.get("foss.gui.creating.common.packageLaber.paper");
		}

		if (vo.getWood() != null && vo.getWood().intValue() != 0) {
			pack = pack + vo.getWood() + i18n.get("foss.gui.creating.common.packageLaber.wood");
		}

		if (vo.getFibre() != null && vo.getFibre().intValue() != 0) {
			pack = pack + vo.getFibre() + i18n.get("foss.gui.creating.common.packageLaber.fibre");
		}

		if (vo.getSalver() != null && vo.getSalver().intValue() != 0) {
			pack = pack + vo.getSalver() + i18n.get("foss.gui.creating.common.packageLaber.salver");
		}

		if (vo.getMembrane() != null && vo.getMembrane().intValue() != 0) {
			pack = pack + vo.getMembrane() + i18n.get("foss.gui.creating.common.packageLaber.membrane");
		}

		if (StringUtils.isNotEmpty(vo.getOtherPackage())) {
			pack = pack + vo.getOtherPackage();
		}

		vo.setGoodsPackage(pack);
		return vo.getGoodsPackage();
	}
	
	/**
	 * 将弹出框中查询到的收货客户信息设置到开单界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午7:13:39
	 */
	public static void setQueryReceiveCustomer(WaybillRFCUI ui) {
		// 将获得的值传到界面上
		WaybillPanelVo bean = ui.getBinderWaybill();
		QueryMemberDialog panel = new QueryMemberDialog(bean);
		// 居中显示弹出窗口
		WindowUtil.centerAndShow(panel);
		// 获得VO
		QueryMemberDialogVo memberBean = panel.getMemberVo();

		// 数据非空判断
		if (memberBean == null) {
			return;
		}
		// 填充收货客户信息
		fillReceiveCustomerInfo(ui, memberBean, bean);
	}
	
	/**
	 * 设置送货费
	 * @author WangQianJin
	 * @date 2013-6-17 下午5:23:16
	 */ 
	public static void setDeliveryFeeCollection(WaybillInfoVo bean) {
		// 清理送货费
		cleanDeliverCharge(bean);
		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {			
			return;
		}		
		//获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);
		
		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();		
		//送货费
		List<GuiQueryBillCalculateSubDto> deliveryFees = getDeliveryFeeCollection(bean);
		if (deliveryFees != null && !deliveryFees.isEmpty()) {
			priceEntities.addAll(deliveryFees);
		}
		productPriceDtoCollection.setPriceEntities(priceEntities);
		
		//如果不是转运与返货，则设置营销活动
		if(!WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode()) &&
				!WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode())){			
			//营销活动DTO
			productPriceDtoCollection.setActiveDto(bean.getActiveDto());				
			//是否计算市场营销折扣
			productPriceDtoCollection.setCalActiveDiscount(bean.isCalActiveDiscount());	
			if(bean.getActiveInfo()!=null){
				productPriceDtoCollection.setActiveCode(bean.getActiveInfo().getValueCode());
			}		
			productPriceDtoCollection.setGoodsName(bean.getGoodsName());
			productPriceDtoCollection.setDeliveryCustomerCode(bean.getDeliveryCustomerCode());
			productPriceDtoCollection.setOrderChannel(bean.getOrderChannel());
			productPriceDtoCollection.setReceiveOrgCode(bean.getReceiveOrgCode());
			productPriceDtoCollection.setLoadOrgCode(bean.getLoadOrgCode());
			productPriceDtoCollection.setLastOutLoadOrgCode(bean.getLastOutLoadOrgCode());
			productPriceDtoCollection.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());
			productPriceDtoCollection.setBillTime(bean.getBillTime());
			productPriceDtoCollection.setTransportFee(bean.getTransportFee());
			productPriceDtoCollection.setGoodsWeightTotal(bean.getGoodsWeightTotal());
			productPriceDtoCollection.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());
		}
		
		//统一返回的计价值
		List<GuiResultBillCalculateDto> dtos = waybillService.queryGuiBillPrice(productPriceDtoCollection);		

		GuiResultBillCalculateDto guiResultBillCalculateDto;		

		//提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {

			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			/**
			 * 如果是汽运送货费，那么需要判断是否超过最高派送费，如果超过，赋值为派送费最大值
			 */
			if (WaybillConstants.DELIVER_NOUP.equals(code)) {

				BigDecimal maxDeliveFee = new BigDecimal(NumberConstants.NUMBER_100);//设置最大派送费
				try {
					ConfigurationParamsEntity maxDeliverFeeForConfig = getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_DELIVER_NOUP_MAX_FEE);// 最大派送费
				
				if (maxDeliverFeeForConfig != null
						&& StringUtils.isNotEmpty(maxDeliverFeeForConfig
								.getConfValue())) {
					maxDeliveFee = new BigDecimal(
							maxDeliverFeeForConfig.getConfValue());
				}
				} catch (Exception e) {

					System.out.println("没有系统配置"+"DELIVER_NOUP_MAX_FEE");
				}
				if (guiResultBillCalculateDto != null)
				{
					BigDecimal caculateFee = guiResultBillCalculateDto
							.getCaculateFee();
					if (caculateFee.compareTo(maxDeliveFee) > 0) {
						guiResultBillCalculateDto.setCaculateFee(maxDeliveFee);

					}
				}
			}
			// 设置送货费 
			setDeliverFee(guiResultBillCalculateDto, bean, true, false);
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			}
			

		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			setDeliverFee(guiResultBillCalculateDto, bean, true, false);
			//获取上楼费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SHSL, null);
			// 设置上楼费
			setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			}

		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			setDeliverFee(guiResultBillCalculateDto, bean, true, false);

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_QT, PriceEntityConstants.PRICING_CODE_SHJC);
			// 设置进仓费
			setDeliverFee(guiResultBillCalculateDto, bean, false, true);
			// 获取超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			}
		}

		//送货费集合
		List<DeliverChargeEntity> deliverList = bean.getDeliverList();	
		if(deliverList!=null && deliverList.size()>0){
			for(DeliverChargeEntity charge: deliverList){
				System.out.println("送货费明细====CODE："+charge.getCode()+"   "+"NAME："+charge.getName()+"AMOUNT："+charge.getAmount());			
			}		
		}		

	}
	
	/**
	 * 获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	private static ConfigurationParamsEntity getConfigurationParamsEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);

	}
	
	/**
	 * 获取送货费参数
	 * @author WangQianJin
	 * @date 2013-6-17 下午9:34:43
	 */
	private static List<GuiQueryBillCalculateSubDto> getDeliveryFeeCollection(WaybillPanelVo bean) {

		List<GuiQueryBillCalculateSubDto> queryBillCacilateValueAddDto = new ArrayList<GuiQueryBillCalculateSubDto>();
		
		//提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		}
		return queryBillCacilateValueAddDto;
	}
	
	/**
	 * 
	 * 清理送货费
	 * 
	 * @author WangQianJin
	 * @date 2013-6-17 下午9:37:15
	 */
	private static void cleanDeliverCharge(WaybillPanelVo bean) {
		bean.setDeliveryGoodsFeeCanvas("0");
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		bean.setDeliverList(null);
		bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);
		bean.setMaxDeliveryGoodsFee(BigDecimal.ZERO);
	}
	
	/**
	 * 获取查询参数
	 * @author WangQianJin
	 * @date 2013-6-17 下午9:37:15
	 */
	private static GuiQueryBillCalculateSubDto getQueryParam(WaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	
	/**
	 * 获取超远派送费查询参数
	 * @author WangQianJin
	 * @date 2013-6-17 下午9:37:46
	 */
	private static GuiQueryBillCalculateSubDto getVeryFarQueryParam(WaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	
	/**
	 * 
	 * @author WangQianJin
	 * @date 2013-6-17 下午6:24:28
	 */
	public static GuiQueryBillCalculateDto getProductPriceDtoCollection(WaybillPanelVo bean) {
		// 上门接货优先读取上门接货的价格
		if (bean.getPickupToDoor()) {
			return getQueryParamCollection(bean, FossConstants.YES);

		} else {
			return getQueryParamCollection(bean, FossConstants.NO);
		}

	}
	
	/**
	 * 获取产品价格查询参数
	 * @author WangQianJin
	 * @date 2013-6-17 下午6:26:20
	 */
	private static GuiQueryBillCalculateDto getQueryParamCollection(WaybillPanelVo bean, String yesOrNo) {
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			queryDto.setGoodsCode(bean.getAirGoodsType().getValueCode());// 货物类型CODE
		}

		queryDto.setReceiveDate(new java.util.Date());// 更改单设置为当前时间
		// queryDto.setIsReceiveGoods(BooleanConvertYesOrNo.booleanToString(bean.getPickupToDoor()));//
		// 是否接货
		queryDto.setIsReceiveGoods(yesOrNo);// 是否接货
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		if (bean.getFlightNumberType() == null) {
			queryDto.setFlightShift(null);// 航班号
		} else {
			queryDto.setFlightShift(bean.getFlightNumberType().getValueCode());// 航班号
		}

		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setKilom(bean.getKilometer());//设置公里数
		
		
		List<GuiQueryBillCalculateSubDto> priceEntities =new ArrayList<GuiQueryBillCalculateSubDto>();
		GuiQueryBillCalculateSubDto guiQueryBillCalculateSubDto= new GuiQueryBillCalculateSubDto();
		guiQueryBillCalculateSubDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntities.add(guiQueryBillCalculateSubDto);
		queryDto.setPriceEntities(priceEntities);
		return queryDto;
	}
	
	/**
	 * 获取GuiResultBillCalculateDto
	 * @author WangQianJin
	 * @date 2013-6-17 下午5:23:50
	 */
	private static GuiResultBillCalculateDto getGuiResultBillCalculateDto(List<GuiResultBillCalculateDto> dtos, String valueAddType, String subType) {

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {

				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode()) && subType.equals(guiResultBillCalculateDto.getSubType())) {
					return guiResultBillCalculateDto;
				}
			}

		}

		return null;
	}
	
	/**
	 * 设置送货费、送货进仓费、送货上楼等全部送货费
	 * @author WangQianJin
	 * @date 2013-6-17 下午5:25:11
	 */
	private static void setDeliverFee(GuiResultBillCalculateDto dto, WaybillPanelVo bean, Boolean flag, Boolean isDeliverStorge) {
		if (dto != null) {

			DeliverChargeEntity deliver = new DeliverChargeEntity();
			// 是否激活
			deliver.setActive(FossConstants.ACTIVE);

			BigDecimal deliveryGoodsFee = dto.getCaculateFee();
			if (deliveryGoodsFee == null) {
				deliveryGoodsFee = BigDecimal.ZERO;
			} else {
				deliveryGoodsFee = deliveryGoodsFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			// 金额
			deliver.setAmount(deliveryGoodsFee);
			// 币种
			deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 费用ID
			deliver.setId(dto.getId());
			// 运单号
			deliver.setWaybillNo(bean.getWaybillNo());
			//提货方式编码
			if (isDeliverStorge)// 送货进仓
			{
				// 费用编码
				deliver.setCode(dto.getSubType());
				//费用名称
				deliver.setName(dto.getSubTypeName());
			} else {
				// 费用编码
				deliver.setCode(dto.getPriceEntryCode());

				//费用名称
				deliver.setName(dto.getPriceEntryName());
			}
			// 送货费合计
			BigDecimal chargeTotal = BigDecimal.ZERO;
			// 送货费合计 = 送货费+上楼费/进仓费/超远派送费
			chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
			bean.setDeliveryGoodsFee(chargeTotal);
			// 画布-送货费
			bean.setDeliveryGoodsFeeCanvas(chargeTotal.toString());
			// 计算的送货费
			bean.setCalculateDeliveryGoodsFee(chargeTotal);
			// 获取派送费集合
			List<DeliverChargeEntity> delivetList = bean.getDeliverList();
			if (delivetList == null) {
				delivetList = new ArrayList<DeliverChargeEntity>();
			}
			// 将新的派送费添加到派送费集合
			delivetList.add(deliver);
			// 将派送费集合进行更新
			bean.setDeliverList(delivetList);
			// 此标识用来标记是否送货费,如果查询出来是送货费，则将送货费的最大上限设置
			if (flag) {
				bean.setMaxDeliveryGoodsFee(dto.getMaxFee());
			}			
		}
	}
	
	/**
	 * 内部带货，需要将金额相关全部清零
	 * @author WangQianJin
	 * @date 2013-6-18 下午4:52:50
	 */
	public static void resetZero(WaybillPanelVo bean, WaybillRFCUI ui) {
		// 增值服务面板
		bean.setInsuranceAmount(BigDecimal.ZERO);// 保险声明价
		bean.setCodAmount(BigDecimal.ZERO);// 代收货款
		bean.setPackageFee(BigDecimal.ZERO);// 包装费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);// 送货费
		bean.setServiceFee(BigDecimal.ZERO);// 装卸费
		bean.setPickupFee(BigDecimal.ZERO);// 接货费
		bean.setOtherFee(BigDecimal.ZERO);// 其他费用合计
		bean.setAccountName("");// 收款人
		bean.setAccountCode("");// 收款账号

		// 查询会员
		ui.getButtonPanel().getBtnSearchMember().setEnabled(false);
		// 查询收货客户
		ui.consigneePanel.getBtnQuery().setEnabled(false);
		// 查询发货客户
		ui.consignerPanel.getBtnQuery().setEnabled(false);

		// 计费付款面板
		//bean.setTransportFee(BigDecimal.ZERO);// 公布价运费
		bean.setValueAddFee(BigDecimal.ZERO);// 增值服务费
		bean.setPromotionsFee(BigDecimal.ZERO);// 优惠合计
		bean.setPrePayAmount(BigDecimal.ZERO);// 预付金额
		bean.setToPayAmount(BigDecimal.ZERO);// 到付金额
		bean.setHandWriteMoney(BigDecimal.ZERO);// 手写现付金额
		bean.setTotalFee(BigDecimal.ZERO);

		// 画布
		bean.setBillWeight(BigDecimal.ZERO);// 计费重量
		bean.setUnitPrice(BigDecimal.ZERO);// 费率
		//bean.setTransportFeeCanvas("0");// 公布价运费
		bean.setInsuranceAmountCanvas("0");// 保价声明
		bean.setInsuranceRate(BigDecimal.ZERO);// 保价费率
		bean.setInsuranceFee(BigDecimal.ZERO);// 保价费

		bean.setCodAmountCanvas("0");// 代收货款
		bean.setCodRate(BigDecimal.ZERO);// 代收费率
		bean.setCodFee(BigDecimal.ZERO);// 代收手续费

		bean.setPickUpFeeCanvas("0");// 接货费
		bean.setDeliveryGoodsFeeCanvas("0");// 送货费
		bean.setPackageFeeCanvas("0");// 包装费
		bean.setServiceFeeCanvas("0");// 装卸费

		bean.setOtherFeeCanvas("0");// 其他费用
		bean.setPromotionsFeeCanvas("0");// 优惠合计
		bean.setTotalFeeCanvas("0");// 总费用
	}
	
	
	/**
	 * 内部带货，需要将金额相关全部清零
	 * @author WangQianJin
	 * @date 2013-6-18 下午4:52:50
	 */
	public static void innerPickup(WaybillPanelVo bean, WaybillRFCUI ui, String receiveMethod) {
		// 判断是否内部带货自提
		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())) {				
			// 金额清零
			resetZero(bean, ui);
			bean.setReceiveMethodFlag(FossConstants.YES);
		}
	}

	
	/**
	 * 是否有变更记录，从而来做这个判断
	 * @param waybillVo
	 * @return
	 */
	public static boolean isChangeRecordMessage(WaybillInfoVo waybillVo)
	{
		
		if(waybillVo.getTransferRecordList().size()>0 || waybillVo.getReturnRecordList().size()>0){
			
				return true;
			}
			
			return false;
			 
	}
	
	/**
	 * 获得下拉框对应的选项
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 下午9:43:59
	 */
	public static DataDictionaryValueVo getCombBoxValue(DefaultComboBoxModel comb, String code) {
		DataDictionaryValueVo vo = null;
		int count = comb.getSize();
		for (int i = 0; i < count; i++) {
			DataDictionaryValueVo entity = (DataDictionaryValueVo) comb.getElementAt(i);
			if (null != entity && StringUtil.defaultIfNull(code).equals(StringUtil.defaultIfNull(entity.getValueCode()))) {
				return entity;
			}
		}
		return vo;
	}
	
	
	/**
	 * 
	 * 获取产品价格查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public static QueryBillCacilateDto getQueryParam(WaybillInfoVo bean) {
		QueryBillCacilateDto queryDto = new QueryBillCacilateDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
		// 产品CODE
		queryDto.setProductCode(bean.getProductCode().getCode());
		// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setReceiveDate(bean.getBillTime());
		// 重量
		queryDto.setWeight(bean.getGoodsWeightTotal());
		// 体积
		if(bean.getGoodsVolumeTotal()!=null){
			queryDto.setVolume(bean.getGoodsVolumeTotal());
		}else{
			queryDto.setVolume(BigDecimal.ZERO);
		}
		//提货方式:是否自提
		if(WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode()) ){
			queryDto.setIsSelfPickUp(FossConstants.YES);
		}
		//设置CRM渠道
		queryDto.setChannelCode(bean.getOrderChannel());
		// 币种
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
		// 发货客户编码
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());
		//如果不是转运与返货，则设置营销活动
		if(!WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode()) &&
				!WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode())){	
			//封装营销活动参数信息
			settterActiveParamInfoFrt(queryDto,bean);
		}
		/**
		 * 根据条件查询当前月份的优惠总额
		 * dp-foss-dongjialing
		 * 225131
		 */
		BigDecimal amount = null;
		if(StringUtil.isNotBlank(bean.getEmployeeNo())) {
			amount = waybillService.queryDiscountFeeByEmployeeNo(bean.getEmployeeNo(), bean.getBillTime());
			if(amount == null) {
				amount = BigDecimal.ZERO;
			}
			if(bean.getEmployeeNo().equals(bean.getOriginalEmployeeNo())) {
				//工号没变额度需减去原单金额
				queryDto.setAmount(amount.subtract(bean.getOriginalFee().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100))));
			} else {
				queryDto.setAmount(amount);
			}
		}
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
		return queryDto;
	}
	
	/**
	 * 封装营销活动参数信息(运费)
	 * @创建时间 2014-4-28 下午8:05:29   
	 * @创建人： WangQianJin
	 */
	public static void settterActiveParamInfoFrt(QueryBillCacilateDto queryDto,WaybillInfoVo bean){
		if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){			
			queryDto.setActiveDto(bean.getActiveDto());		
			queryDto.setCalActiveDiscount(bean.isCalActiveDiscount());
			queryDto.setActiveCode(bean.getActiveInfo().getValueCode());			
			queryDto.setGoodsName(bean.getGoodsName());
			queryDto.setDeliveryCustomerCode(bean.getDeliveryCustomerCode());
			queryDto.setOrderChannel(bean.getOrderChannel());
			queryDto.setReceiveOrgCode(bean.getReceiveOrgCode());
			queryDto.setLoadOrgCode(bean.getLoadOrgCode());
			queryDto.setLastOutLoadOrgCode(bean.getLastOutLoadOrgCode());
			queryDto.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());
			queryDto.setBillTime(bean.getBillTime());		
			queryDto.setGoodsWeightTotal(bean.getGoodsWeightTotal());
			queryDto.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());			
		}			
	}
	
	/**
	 * 封装营销活动参数信息(增值)
	 * @创建时间 2014-4-28 下午8:05:29   
	 * @创建人： WangQianJin
	 */
	public static void settterActiveParamInfoValueAdd(QueryBillCacilateValueAddDto queryDto,WaybillInfoVo bean){
		if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){
			queryDto.setActiveDto(bean.getActiveDto());		
			queryDto.setCalActiveDiscount(bean.isCalActiveDiscount());
			queryDto.setActiveCode(bean.getActiveInfo().getValueCode());			
			queryDto.setGoodsName(bean.getGoodsName());
			queryDto.setDeliveryCustomerCode(bean.getDeliveryCustomerCode());
			queryDto.setOrderChannel(bean.getOrderChannel());
			queryDto.setReceiveOrgCode(bean.getReceiveOrgCode());
			queryDto.setLoadOrgCode(bean.getLoadOrgCode());
			queryDto.setLastOutLoadOrgCode(bean.getLastOutLoadOrgCode());
			queryDto.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());
			queryDto.setBillTime(bean.getBillTime());		
			queryDto.setGoodsWeightTotal(bean.getGoodsWeightTotal());
			queryDto.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());		
			BigDecimal transportFee=bean.getTransportFee();
			//获取折扣信息
			List<WaybillDiscountVo> waybillDiscountVos =bean.getImportWaybillDiscountVos();			
			BigDecimal discountFee=null;
			if(CollectionUtils.isNotEmpty(waybillDiscountVos)){
				for(WaybillDiscountVo dto:waybillDiscountVos){
					if(dto!=null && dto.getFavorableAmount()!=null
							&& PriceEntityConstants.PRICING_CODE_FRT.equals(dto.getFavorableItemCode())
							&& DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE.equals(dto.getFavorableTypeCode())){
						discountFee=new BigDecimal(dto.getFavorableAmount());
						break;
					}
				}
			}
			if(discountFee!=null){
				//设置处理推广活动前的运费
				transportFee=transportFee.add(discountFee);	
			}			
			//如果有装卸费则减去
			if(bean.getServiceFee()!=null && bean.getServiceFee().doubleValue()>0){
				transportFee=transportFee.subtract(bean.getServiceFee());
			}
			//设置处理推广活动前的运费
			queryDto.setTransportFee(transportFee);
		}			
	}
	
	/**
	 * 优惠券冲减费用
	 * 对于运费在calculateTotalFee中进行冲减
	 * 对于综合信息费则在xxx中进行冲减
	 * 
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static void offsetCouponFee(WaybillRFCUI ui,WaybillPanelVo bean) {
		// 优惠费
		BigDecimal couponFee = CommonUtils.defaultIfNull(bean.getCouponFree());
		// 优惠类型
		String type = CommonUtils.defaultIfNull(bean.getCouponRankType());
		
		// 是否有优惠金额,是否已冲减
		if (couponFee.compareTo(BigDecimal.ZERO) > 0) {			
			// 冲减费用类型
			if (PriceEntityConstants.PRICING_CODE_JH.equals(type)) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getPickupFee(),type);
				//冲减接货费
				BigDecimal pickUpFee = CommonUtils.defaultIfNull(bean.getPickupFee()).subtract(couponFee);
				pickUpFee = Common.convertFeeToZero(ui,pickUpFee);
				bean.setPickupFee(pickUpFee);
				bean.setPickUpFeeCanvas(pickUpFee.toString());
			}else if(PriceEntityConstants.PRICING_CODE_SH.equals(bean.getCouponRankType())) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getDeliveryGoodsFee(),type);
				//冲减送货费
				BigDecimal deliveryGoodsFee = CommonUtils.defaultIfNull(bean.getDeliveryGoodsFee()).subtract(couponFee);
				deliveryGoodsFee = Common.convertFeeToZero(ui,deliveryGoodsFee);
				bean.setDeliveryGoodsFee(deliveryGoodsFee);
				bean.setDeliveryGoodsFeeCanvas(deliveryGoodsFee.toString());
				bean.setCalculateDeliveryGoodsFee(deliveryGoodsFee);
			}else if (PriceEntityConstants.PRICING_CODE_HK.equals(bean.getCouponRankType())) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getCodFee(),type);
				// 冲减代收货款手续费
				BigDecimal codFee = CommonUtils.defaultIfNull(bean.getCodFee()).subtract(couponFee);
				codFee = Common.convertFeeToZero(ui,codFee);
				bean.setCodFee(codFee);
			}else if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){				
				//冲减综合信息服务费
				//获取其他费用
				JXTable otherTable = ui.incrementPanel.getTblOther();
				WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
				List<OtherChargeVo> data = model.getData();
				//校验费用是否符合要求
				CommonUtils.validateOtherFeeIsZero(data,type);
				if(CollectionUtils.isNotEmpty(data)){
					boolean flag=false;
					for(OtherChargeVo vo : data){
						if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())){
							//获取默认的综合信息费，否则会在冲减后基础上冲减
							BigDecimal zhxxFee = getDefaultZhxxFee(bean);
							if(zhxxFee!=null){
								zhxxFee = zhxxFee.subtract(couponFee);								
								//检验优惠费用是否小于0
								zhxxFee=Common.convertFeeToZero(ui,zhxxFee);
								vo.setMoney(zhxxFee.toString());
								flag=true;								
								break;
							}								
						}
					}
					if(flag){
						//重新获取其他费用
						CommoForFeeUtils.otherPanelSumFee(data,bean);
					}						
				}				
			}			
			//重新计算增值服务费
			CalculateFeeTotalUtils.resetCalculateFee(bean);
		}
	}
	
	/**
	 * 获取默认综合信息服务费的值
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static BigDecimal getDefaultZhxxFee(WaybillPanelVo bean){
		BigDecimal zhxx=null;
		//根据开单时间查询
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryOtherChargeParamForActive(bean,false));
		//根据当前时间查询
		if(list==null||list.isEmpty()){
			list = waybillService.queryValueAddPriceList(getQueryOtherChargeParamForActive(bean,true));					
		}
		//遍历集合
		if (CollectionUtils.isNotEmpty(list)) {
			for (ValueAddDto dto : list) {
				if (PriceEntityConstants.PRICING_CODE_ZHXX.equals(dto.getSubType())
					 && PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntityCode())) {
					zhxx = dto.getCaculateFee();
					break;
				}
			}
		}
		return zhxx;
	}
	
	/**
	 * 获取默认快递包装费的值
	 * MANA-1961 营销活动与优惠券编码关联
	 * WangQianJin
	 */
	public static BigDecimal getDefaultKdbzFee(WaybillPanelVo bean){
		BigDecimal kdbz=null;
		//根据开单时间查询
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryOtherChargeParamForActive(bean,false));
		//根据当前时间查询
		if(list==null||list.isEmpty()){
			list = waybillService.queryValueAddPriceList(getQueryOtherChargeParamForActive(bean,true));					
		}
		//遍历集合
		if (CollectionUtils.isNotEmpty(list)) {
			for (ValueAddDto dto : list) {
				if (PriceEntityConstants.PRICING_CODE_KDBZF.equals(dto.getSubType())
					 && PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntityCode())) {
					kdbz = dto.getCaculateFee();
					break;
				}
			}
		}
		return kdbz;
	}
	
	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private static QueryBillCacilateValueAddDto getQueryOtherChargeParamForActive(WaybillPanelVo bean2,boolean isGetCurrentPrice) {
		WaybillInfoVo bean = (WaybillInfoVo)bean2;		
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getFinalProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		//封装营销活动参数信息
		settterActiveParamInfoValueAdd(queryDto,bean);
		return queryDto;
	}
	
	/**
	 * 检验优惠冲减后的费用是否正确
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static BigDecimal convertFeeToZero(WaybillRFCUI ui,BigDecimal fee){
		BigDecimal value = CommonUtils.defaultIfNull(fee);
		//检验优惠费用是否小于0
		if(BigDecimal.ZERO.compareTo(value) > 0 ){
			return BigDecimal.ZERO;
		}else{
			return value;
		}
	}

	
	/**
	 * 设置查询有签收单原件返回时的查询参数
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午6:03:41
	 */
	public QueryBillCacilateDto getQueryReturnParam(WaybillInfoVo bean) {
		// 新的查询条件
		QueryBillCacilateDto dto = new QueryBillCacilateDto();
		try {
			PropertyUtils.copyProperties(dto, Common.getQueryParam(bean));
			// 设置重量：固定值0.5
			dto.setWeight(BigDecimal.valueOf(NUM_ZERO_POINT_FIVE));
			// 设置体积
			dto.setVolume(BigDecimal.ZERO);
			dto.setCustomerCode("");
			dto.setIsSelfPickUp(FossConstants.NO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
	
	/**
	 * 校验是否可以享有市场营销活动
	 * @创建时间 2014-4-22 上午9:32:41
	 * @创建人： WangQianJin
	 */
	public static void validateActiveDiscount(WaybillInfoVo bean,boolean isValBillAmount) {
		DataDictionaryValueVo activeInfo=bean.getActiveInfo();
		//判断市场营销活动是否为空
		if(activeInfo==null || activeInfo.getValueCode()==null || StringUtils.isEmpty(activeInfo.getValueName())){
			bean.setCalActiveDiscount(false);
		}else{
			MarkActivitiesQueryConditionDto activeDto=new MarkActivitiesQueryConditionDto();
			//开单品名
			activeDto.setGoodsName(bean.getGoodsName());
			if(StringUtils.isNotEmpty(bean.getDeliveryCustomerCode())){
				CustomerDto customer=waybillService.queryCustInfoByCodeNew(bean.getDeliveryCustomerCode());
				if(customer!=null){
					//一级行业
					activeDto.setFirstTrade(customer.getOneLevelIndustry());
					//二级行业
					activeDto.setSecondeTrade(customer.getTwoLevelIndustry());
				}
			}
			//订单来源
			activeDto.setOrderResource(bean.getOrderChannel());
			//产品类型
			activeDto.setProductType(bean.getProductCode().getCode());
			//开展部门
			activeDto.setDevelopDeptCode(bean.getReceiveOrgCode());
			//出发外场
			activeDto.setStartOutFieldCode(bean.getLoadOrgCode());
			//到达外场
			activeDto.setArriveOutFieldCode(bean.getLastOutLoadOrgCode());	
			//发货部门
			activeDto.setLeaveAreaCode(bean.getReceiveOrgCode());
			//到达部门
			activeDto.setArriveAreaCode(bean.getCustomerPickupOrgCode().getCode());
			//开单时间
			activeDto.setBilllingTime(bean.getBillTime());
			if(isValBillAmount){
				//开单金额
				activeDto.setBilllingAmount(bean.getTotalFee());
			}			
			//开单重量
			activeDto.setBilllingWeight(bean.getGoodsWeightTotal());
			//开单体积
			activeDto.setBilllingVolumn(bean.getGoodsVolumeTotal());
			//活动编码
			activeDto.setCode(activeInfo.getValueCode());
			//查询活动折扣信息
			MarkActivitiesQueryConditionDto result=waybillService.getActiveDiscountInfo(activeDto);
			if(result!=null && result.getOptionList()!=null && result.getOptionList().size()>0){
				//计算市场营销活动折扣信息
				bean.setCalActiveDiscount(true);
				//将营销活动设置到DTO中
				bean.setActiveDto(result);
			}else{
				bean.setCalActiveDiscount(false);
				//您选择的市场营销活动不符合条件！
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.select.activeinfo.iserror"));
			}
		}		
	}
	
	/**
 	 * @author 200945 - wutao
 	 * 公共驗證付款方式的方法
 	 * @param bean
 	 */
 	public static void validateExpPayMethod(WaybillPanelVo bean){
 		/**
 		 * 当发货人【是否统一结算】标记为【是】,该客户付款方式只能为【临时欠款】或【月结】或【到付】
 		 * 快递：不允许【临时欠款】
 		 */
 		if(bean.getPaidMethod() != null) {
 		if (WaybillConstants.IS_NOT_NULL_FOR_AI.equals(bean
 				.getStartCentralizedSettlement())) {
 			//判断发货客户是否满足月结的条件
 			boolean isOK = false;
 			if(WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
 				isOK = true;
 			}
 			if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
 				isOK = true;
 			}
 			if(!isOK){
 				 throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.alterExpPaidMethod"));
 			}
 		}
 	}
 	}
 	
 	//liding comment for NCI
// 	/**
//	 * 校验交易流水号是否符合开单规则
//	 * @author:218371-foss-zhaoyanjun
//	 * @date:2015-01-23上午11:09
//	 */
//	public static void validateTransactionSerialNumber(WaybillInfoVo bean,WaybillRFCUI ui){
//		String valueCode=bean.getPaidMethod().getValueCode();
//		String transactionSerialNumber=bean.getTransactionSerialNumber();
//		if(valueCode.equals(WaybillConstants.CREDIT_CARD_PAYMENT)){
//			if(StringUtils.isEmpty(transactionSerialNumber)){
//				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.listener.Waybill.transactionSerialNumber"));
//			}
//		}
//	}
	
	/**
	 * 根据付款方式，判断是否可编辑“交易流水号”
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-28下午15:52
	 */
	public static void paidMethodToTransactionSerialNumber(WaybillInfoVo bean,WaybillRFCUI ui){
		String valueCode=bean.getPaidMethod().getValueCode();
		if(valueCode.equals(WaybillConstants.CREDIT_CARD_PAYMENT)&&!BZPartnersJudge.IS_PARTENER){
			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(true);
		}
	}
	//根据货物尺寸计算体积
	public static void calculateVolume(WaybillInfoVo bean) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		try {
			Object result = engine.eval(bean.getGoodsSize());
			BigDecimal bigDecimal = new BigDecimal(result.toString());
			bigDecimal = bigDecimal.setScale(SIX, BigDecimal.ROUND_HALF_UP);
			BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
			bigDecimal = bigDecimal.divide(m);
			bigDecimal = bigDecimal.setScale(SIX, BigDecimal.ROUND_HALF_UP);// 四舍五入
			// 四舍五入后如果变为0.00，那么需要给成默认的0.01，以免丢失体积
			/*if (bigDecimal.doubleValue() == 0) {
				bigDecimal = new BigDecimal("0.01");
			}*/
			BigDecimal upLimit = new BigDecimal(WaybillConstants.VOLUME_UPLIMIT);
			if (bigDecimal.compareTo(upLimit) > 0) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.calculateVolume.one") + WaybillConstants.VOLUME_UPLIMIT);
				bean.setGoodsSize("");
			} else if (BigDecimal.ZERO.compareTo(bigDecimal) > 0) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.calculateVolume.two"));
				bean.setGoodsSize("");
			} else {
				bean.setCalculateVolume(bigDecimal);
			}

			
		
			
		} catch (ScriptException e) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.one"), e);
		}
	}
	
	//快递合伙人上限（从快递开单那边直接拿过来使用） 2016年3月10日 09:01:52 葛亮亮
	public static void cpv(WaybillPanelVo bean){
		BigDecimal vipCodAmountAmount=bean.getVipCollectionPaymentLimit();
		BigDecimal codAmount=bean.getCodAmount();
		if(vipCodAmountAmount==null||vipCodAmountAmount.doubleValue()==0)
		{
			vipCodAmountAmount=new BigDecimal(20000);
		}
		if((vipCodAmountAmount!=null)&&((codAmount.compareTo(vipCodAmountAmount))>0)){
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.common.exception.collectionmoneyUp"));
		}
	}
	/**
	 * 封装营销活动参数信息
	 */
	public static void settterActiveParamInfo(GuiQueryBillCalculateDto productPriceDtoCollection,WaybillPanelVo bean){
		if(bean.getActiveInfo()!=null){
			productPriceDtoCollection.setActiveCode(bean.getActiveInfo().getValueCode());
		}		
		productPriceDtoCollection.setGoodsName(bean.getGoodsName());
		productPriceDtoCollection.setDeliveryCustomerCode(bean.getDeliveryCustomerCode());
		productPriceDtoCollection.setOrderChannel(bean.getOrderChannel());
		productPriceDtoCollection.setReceiveOrgCode(bean.getReceiveOrgCode());
		productPriceDtoCollection.setLoadOrgCode(bean.getLoadOrgCode());
		productPriceDtoCollection.setLastOutLoadOrgCode(bean.getLastOutLoadOrgCode());
		productPriceDtoCollection.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());
		productPriceDtoCollection.setBillTime(bean.getBillTime());
		productPriceDtoCollection.setTransportFee(bean.getTransportFee());
		productPriceDtoCollection.setGoodsWeightTotal(bean.getGoodsWeightTotal());
		productPriceDtoCollection.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());
	}

}