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
package com.deppon.foss.module.pickup.changing.client.utils;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.apache.commons.beanutils.BeanUtils;
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
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.binding.BindingAssociation;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.IBallValidateWidget;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.EnterPackingInfoDialog;
import com.deppon.foss.module.pickup.changing.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changing.client.ui.internal.transport.ReturnInfoPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.transport.TransferInfoPanel;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
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
import com.deppon.foss.module.pickup.common.client.vo.EconomyVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
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
	
	private static IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();
	
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
	// 获得远程HessianRemoting接口
	private static IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
			.getService(IWaybillHessianRemoting.class);
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */
	
	/**
	 * 参数配置
	 */
	private static IConfigurationParamsService configurationParamsService =  GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);
	
	public static String ZYF = "ZYF";
	
	public static String FHF = "FHF";
	
	public static String GBJ = "GBJ";
	
	/**
	 * 是否已经结清货款（部分结清同样是true）
	 * @param waybillNo
	 * @return
	 * @author 153161-foss-lufeifei
	 */
	public static boolean isExistRepayment(String waybillNo){
		return waybillService.isExistRepayment(waybillNo);
	}

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
		//伙伴开单标识
		if(null!=bean.getPartnerBilling()){
			if(bean.getPartnerBilling()){
				queryDto.setPartnerBillingLogo("Y");
			}else{
				queryDto.setPartnerBillingLogo("N");
			}
		}
		PtpWaybillOrgVo ptpWaybillOrgVo = bean.getPtpWaybillOrgVo();
		if(ptpWaybillOrgVo==null){
			ptpWaybillOrgVo = PtpWaybillOrgVo.init() ;
			bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
		}
		//如果修改变更记录中第一条，影响公布价计算，否则影响中转费
		queryDto.setOriginalOrgCode(bean.getFinalStartOrgCode());// 出发部门CODE
		//传给PTP重新计算运费的出发站 2016年4月12日 14:16:49 葛亮亮
		ptpWaybillOrgVo.setStartOrgCodeCal(bean.getFinalStartOrgCode());
		//如果返货重新计算公布价，目的地取返货起始网店
		if(GBJ.equals(type) && WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode())){
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getFinalProductCode().getCode())){
				queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
				//传给PTP重新计算运费的到达站 2016年4月12日 14:16:49 葛亮亮
				ptpWaybillOrgVo.setDestinationOrgCodeCal(bean.getFinalCustomerPickupOrgCode().getCode());
			}else{
				queryDto.setDestinationOrgCode(bean.getRtnStartOrgCode());// 到达部门CODE
				//传给PTP重新计算运费的到达站 2016年4月12日 14:16:49 葛亮亮
				ptpWaybillOrgVo.setDestinationOrgCodeCal(bean.getRtnStartOrgCode());
			}
		}else{
			queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
			//传给PTP重新计算运费的到达站 2016年4月12日 14:16:49 葛亮亮
			ptpWaybillOrgVo.setDestinationOrgCodeCal(bean.getFinalCustomerPickupOrgCode().getCode());
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
		//065340 liutao 修复pop-213 start
		queryDto.setBillTime(bean.getBillTime());// 设置开单时间
		//065340 liutao 修复pop-213 end
		// queryDto.setIsReceiveGoods(BooleanConvertYesOrNo.booleanToString(bean.getPickupToDoor()));//
		// 是否接货
		queryDto.setIsReceiveGoods(yesOrNo);// 是否接货
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setChannelCode(bean.getOrderChannel());//渠道
		// 是否经济自提件
		queryDto.setEconomySince(BooleanConvertYesOrNo.booleanToString(bean.getIsEconomyGoods()));
		//最终配载部门(计算偏线中转费时用得到)
		queryDto.setLastOrgCode(bean.getLastLoadOrgCode());
		//zxy 20140509 MANA-1253 start 新增
		if(bean.getFreightMethod() != null)
			queryDto.setCombBillTypeCode(bean.getFreightMethod().getValueCode());
		//zxy 20140509 MANA-1253 end 新增
		
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
			amount = WaybillRfcServiceFactory.getWaybillRfcService().queryDiscountFeeByEmployeeNo(bean.getEmployeeNo(), bean.getBillTime());
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
		/**
		 * 封装梯度折扣条件
		 */		
		if(StringUtil.isNotBlank(bean.getDeliveryCustomerCode())) {
			amount = WaybillRfcServiceFactory.getWaybillRfcService().queryTotalFeeByDelevyCode(bean.getDeliveryCustomerCode(),bean.getBillTime());
			if(amount == null) {
				amount = BigDecimal.ZERO;
			}
			if(bean.getDeliveryCustomerCode().equals(bean.getOriginalCode())) {
			
				queryDto.setTotalAmount(amount.subtract(bean.getOriginalTotalFee()));
				//客户编码没有发生改变取更改前的折扣值
				BigDecimal rate = WaybillRfcServiceFactory.getWaybillRfcService().queryDiscountInfo(bean.getImportWaybillNo());
				queryDto.setOriginalRate(rate);
			} else {
				queryDto.setTotalAmount(amount);
			}
		}
		queryDto.setReceiveOrgCode(bean.getReceiveOrgCode());
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * （FOSS20150827）RFOSS2015052405-更改异常
	 * 获取产品价格查询参数
	 * 专门针对更改转运和返货
	 * @author foss-206860
	 * */
	public static QueryBillCacilateDto getQueryParamForZZ(WaybillInfoVo bean, String yesOrNo, String type,WaybillRfcTranferEntity tranferEntity) {
		QueryBillCacilateDto queryDto = new QueryBillCacilateDto();
		//如果修改变更记录中第一条，影响公布价计算，否则影响中转费
		queryDto.setOriginalOrgCode(tranferEntity.getSourceTargerOrgCode());// 出发部门CODE
		//如果返货重新计算公布价，目的地取返货起始网店
		if(GBJ.equals(type) && WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode())){
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getFinalProductCode().getCode())){
				queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
			}else{
				queryDto.setDestinationOrgCode(bean.getRtnStartOrgCode());// 到达部门CODE
			}
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
		//065340 liutao 修复pop-213 start
		queryDto.setBillTime(bean.getBillTime());// 设置开单时间
		//065340 liutao 修复pop-213 end
		// queryDto.setIsReceiveGoods(BooleanConvertYesOrNo.booleanToString(bean.getPickupToDoor()));//
		// 是否接货
		queryDto.setIsReceiveGoods(yesOrNo);// 是否接货
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setChannelCode(bean.getOrderChannel());//渠道
		// 是否经济自提件
		queryDto.setEconomySince(BooleanConvertYesOrNo.booleanToString(bean.getIsEconomyGoods()));
		//最终配载部门(计算偏线中转费时用得到)
		queryDto.setLastOrgCode(bean.getLastLoadOrgCode());
		//zxy 20140509 MANA-1253 start 新增
		if(bean.getFreightMethod() != null)
			queryDto.setCombBillTypeCode(bean.getFreightMethod().getValueCode());
		//zxy 20140509 MANA-1253 end 新增
		
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
			amount = WaybillRfcServiceFactory.getWaybillRfcService().queryDiscountFeeByEmployeeNo(bean.getEmployeeNo(), bean.getBillTime());
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
		/**
		 * 封装梯度折扣条件
		 */		
		if(StringUtil.isNotBlank(bean.getDeliveryCustomerCode())) {
			amount = WaybillRfcServiceFactory.getWaybillRfcService().queryTotalFeeByDelevyCode(bean.getDeliveryCustomerCode(),bean.getBillTime());
			if(amount == null) {
				amount = BigDecimal.ZERO;
			}
			if(bean.getDeliveryCustomerCode().equals(bean.getOriginalCode())) {
			
				queryDto.setTotalAmount(amount.subtract(bean.getOriginalTotalFee()));
				//客户编码没有发生改变取更改前的折扣值
				BigDecimal rate = WaybillRfcServiceFactory.getWaybillRfcService().queryDiscountInfo(bean.getImportWaybillNo());
				queryDto.setOriginalRate(rate);
			} else {
				queryDto.setTotalAmount(amount);
			}
		}
		queryDto.setReceiveOrgCode(bean.getReceiveOrgCode());
		queryDto.setWaybillNo(bean.getWaybillNo());
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
	 * 获取返货的产品价格查询参数
	 * @author WangQianJin
	 * @date 2013-7-5 下午12:39:49
	 */
	public static QueryBillCacilateDto getQueryParamForReturn(WaybillInfoVo bean, String yesOrNo, String type ,boolean isNow) {
		QueryBillCacilateDto queryDto = getQueryParam(bean,yesOrNo,type);
		//标记是否获取开单时的信息
		boolean flag=false;
		/**
		 * 如果是返货或转运或内部要求修改转运或返货，则获取开单时运费折扣信息
		 */
		if(WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode()) || WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode())){			
			flag=true;			
		}else if(WaybillRfcConstants.INSIDE_CHANGE.equals(bean.getRfcType().getValueCode())
	    		&& (bean.getReturnRecordList()!=null || bean.getTransferRecordList()!=null)
	    		&& bean.getTransportRecordList()!=null){
			//有转运或返货记录，并且变更记录里面有记录
			if(((bean.getReturnRecordList().size()> 0 || bean.getTransferRecordList().size()> 0)) && bean.getTransportRecordList().size()>0){				
		    	flag=true;
			}			
	    }
		if(flag){
			WaybillEntity waybill=waybillService.queryWaybillForFirst(bean.getWaybillNo());
			if(waybill!=null){
				//获取并设置开单时提交的信息
				queryDto.setProductCode(waybill.getProductCode());					// 运输性质	
				queryDto.setOriginalOrgCode(waybill.getReceiveOrgCode());			// 出发部门CODE
				queryDto.setDestinationOrgCode(waybill.getCustomerPickupOrgCode());	// 到达部门CODE				
				queryDto.setLastOrgCode(waybill.getLastLoadOrgCode());				// 最终配载部门CODE				
				if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
					queryDto.setFlightShift(waybill.getFlightNumberType());// 航班号					
				}
			}
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
					CusBargainVo vo=new CusBargainVo();
					vo.setChargeType(WaybillConstants.MONTH_END);
					vo.setCustomerCode(bean.getDeliveryCustomerCode());
					vo.setBillDate(bean.getBillTime());
					vo.setBillOrgCode(bean.getReceiveOrgCode());			
					boolean  isOk = waybillService.isCanPaidMethod(vo);					
					//CusBargainEntity eneity = waybillService.queryCusBargainByParams(bean.getAuditNo(), bean.getReceiveOrgCode());
					if(!isOk){
						//强行中断
						throw new WaybillValidateException(i18n.get("foss.gui.creating.common.msgBox.nullAuditNo"));
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
		//定价项目POP-407foss没有将接货金额、保费金额5个字段传给规则引擎（更改单）
		 Map<String,GuiQueryBillCalculateSubDto> valuedtos=new HashMap<String,GuiQueryBillCalculateSubDto>();
		 //保费
		 GuiQueryBillCalculateSubDto bFDto  = new GuiQueryBillCalculateSubDto();
		 bFDto.setOriginnalCost(bean.getInsuranceAmount());
		 valuedtos.put(PriceEntityConstants.PRICING_CODE_BF, bFDto);
		 //代收
		 GuiQueryBillCalculateSubDto hKDto  = new GuiQueryBillCalculateSubDto();
		 hKDto.setOriginnalCost(bean.getCodAmount());
		 valuedtos.put(PriceEntityConstants.PRICING_CODE_HK, hKDto);
		 bean.setValuedtos(valuedtos);
		// 获取木架费用
		getStandCharge(bean);
		// 获取木箱费用
		getBoxCharge(bean);
		// 获取木托费用  zxy 20131118 ISSUE-4391
		getSalverCharge(bean);
		// ===========LianHe/获取非木包装费/2017年1月10日/start=======
		getNonWoodPackingCharge(bean);
		// ===========LianHe/获取非木包装费/2017年1月10日/end=======
		/**
		 * 获取纸纤包装总价
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-5上午09:43
		 */
		getPackingStandCharge(bean);
	}

	/**
	 * LianHe-2017年1月11日19:50:38
	 * 非木包装费
	 * @param bean
	 * @param ui
	 */
	private static void getNonWoodPackingCharge(WaybillInfoVo bean) {
		//健壮性设置
		if (bean.getNonWoodPackingAmount() == null) {
			bean.setNonWoodPackingAmount(BigDecimal.ZERO);
		}
		
		//健壮性设置
		if (bean.getNonWoodPackingCharge() == null) {
			bean.setNonWoodPackingCharge(BigDecimal.ZERO);
		}
		
		//获取当前的包装费;
		BigDecimal packageFee = bean.getPackageFee();
		//获取之前的非木包装费，
		BigDecimal nonWoodPackingCharge = bean.getNonWoodPackingCharge();
		//当前包装费减去之前的非木包装费
		bean.setPackageFee(packageFee.subtract(nonWoodPackingCharge));
		bean.setNonWoodPackingCharge(bean.getNonWoodPackingAmount());
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
						BigDecimal standCharge = bean.getStandCharge().setScale(0, BigDecimal.ROUND_HALF_UP);
						packageFee = packageFee.subtract(standCharge);
						bean.setPackageFee(packageFee);
						bean.setCalculatedPackageFee(packageFee);
					}
					bean.setStandCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					bean.setStandChargeId(dto.getId());
					// 设置折扣优惠
					setFavorableDiscount(dto.getDiscountPrograms(), bean);
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenPackageFee"));
				}
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenPackageFee"));
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 start 新增：解决删除木架后费用清不掉的问题
		else{
			//清除最后一次费用
			if (bean.getStandCharge() != null && bean.getStandCharge().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal standCharge = bean.getStandCharge().setScale(0, BigDecimal.ROUND_HALF_UP);
				packageFee = packageFee.subtract(standCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
				bean.setStandCharge(BigDecimal.ZERO);
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 end 新增：解决删除木架后费用清不掉的问题
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
						BigDecimal boxCharge = bean.getBoxCharge().setScale(0, BigDecimal.ROUND_HALF_UP);
						packageFee = packageFee.subtract(boxCharge);
						bean.setPackageFee(packageFee);
						bean.setCalculatedPackageFee(packageFee);
					}
					bean.setBoxCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					bean.setBoxChargeId(dto.getId());
					// 设置折扣优惠
					setFavorableDiscount(dto.getDiscountPrograms(), bean);
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
				}
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 start 新增：解决删除木箱后费用清不掉的问题
		else{
			//清除最后一次费用
			if (bean.getBoxCharge() != null && bean.getBoxCharge().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal boxCharge = bean.getBoxCharge().setScale(0, BigDecimal.ROUND_HALF_UP);
				packageFee = packageFee.subtract(boxCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
				bean.setBoxCharge(BigDecimal.ZERO);
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 end 新增：解决删除木箱后费用清不掉的问题
	}
	
	/**
	 * 打木托费用
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18 
	 */
	private static void getSalverCharge(WaybillInfoVo bean) {
		ValueAddDto dto = null;
		if (bean.getSalverGoodsNum() != null && bean.getSalverGoodsNum() > 0) {
			dto = querySalverBillCalculateDto(bean,bean.getSalverGoodsNum().toString());
			if (dto == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenSalverPackageFee"));
			}

			// 验证木托费是否小于最小值
			BigDecimal calculateSalverMinFee = dto.getMinFee().multiply(
					new BigDecimal(bean.getSalverGoodsNum()));
			if (calculateSalverMinFee.compareTo(bean.getSalverGoodsCharge()) > 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.woodYokeEnterAction.exception.minSalverGoodsAmount"));
			}

			// 这个if内的代码为了解决每次计算包装费不断累加的问题
			if (bean.getSalverGoodsCharge() != null) {
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal salverGoodsCharge = bean.getSalverGoodsCharge().setScale(0, BigDecimal.ROUND_HALF_UP);
				if(!bean.isSubPreSalverCharge()){
					BigDecimal preSalverGoodsCharge = bean.getPreSalverGoodsCharge();
					if(preSalverGoodsCharge != null && preSalverGoodsCharge.compareTo(BigDecimal.ZERO) > 0){
						salverGoodsCharge = preSalverGoodsCharge;
					}
					bean.setSubPreSalverCharge(true);//已减去
				}
				packageFee = packageFee.subtract(salverGoodsCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
			}
			
			// 这个if内的代码为了解决每次计算包装费不断累加的问题
			if(bean.getPackingTotle()!=null){
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal packingTotle = bean.getPackingTotle().setScale(0, BigDecimal.ROUND_HALF_UP);
				packageFee = packageFee.subtract(packingTotle);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
			}
			
//			bean.setSalverGoodsCharge(dto.getCaculateFee().setScale(0,
//					BigDecimal.ROUND_HALF_UP));
		}else{
			//清除最后一次费用
			if (!bean.isSubPreSalverCharge()) {
				// 四舍五入
				BigDecimal packageFee =(bean.getPackageFee() == null ? BigDecimal.ZERO : bean.getPackageFee());
				// 四舍五入
				BigDecimal preSalverGoodsCharge =(bean.getPreSalverGoodsCharge() == null ? BigDecimal.ZERO : bean.getPreSalverGoodsCharge()).setScale(0, BigDecimal.ROUND_HALF_UP);
				
				packageFee = packageFee.subtract(preSalverGoodsCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
				bean.setSalverGoodsCharge(BigDecimal.ZERO);
				bean.setSubPreSalverCharge(true);//已减去
			}
		}
		
	}
	
	/**
	 * 纸箱纤袋费用
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-12-5上午9:38
	 */
	private static void getPackingStandCharge(WaybillInfoVo bean) {
		if (bean.getPaperBoxTotlePrice()!=null || bean.getFibelBagTotlePrice()!=null || bean.getOtherTotle()!=null){
			BigDecimal paperBoxTotlePrice=bean.getPaperBoxTotlePrice()==null?new BigDecimal(0):bean.getPaperBoxTotlePrice();
			BigDecimal fibelBagTotlePrice=bean.getFibelBagTotlePrice()==null?new BigDecimal(0):bean.getFibelBagTotlePrice();
			//BigDecimal otherTotle=bean.getOtherTotle()==null?new BigDecimal(0):bean.getOtherTotle();				
			BigDecimal bufferPrice=bean.getBufferPrice()==null?new BigDecimal(0):bean.getBufferPrice();
			BigDecimal discountRate=bean.getDiscountRate()==null || bean.getDiscountRate().compareTo(BigDecimal.ZERO)==0 ?new BigDecimal(1):bean.getDiscountRate();			// 这个if内的代码为了解决包装费不断累加的问题
			BigDecimal packageFee = bean.getPackageFee();			
			//2016-03-26,打包装，纸箱纤袋基础资料维护中有小数情况下，多收包装费问题解决
						//packageFee 手动输入包装费与计算的包装费差值，向下取整数 ，151211，杨套红
						packageFee =(packageFee.subtract(bean.getPackingTotle()==null?BigDecimal.ZERO:bean.getPackingTotle())).setScale(0, RoundingMode.FLOOR);
			bean.setPackageFee(packageFee);
			bean.setCalculatedPackageFee(packageFee);
			bean.setPackingTotle(paperBoxTotlePrice.add(fibelBagTotlePrice.add(bufferPrice)).multiply(discountRate));
		}		
	}

	/**
	 * 查询打木托价格计算对象
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 * @param bean
	 * @return
	 */
	public static ValueAddDto querySalverBillCalculateDto(WaybillInfoVo bean, String salverGoodsPieces) {
		// 打木托
		List<ValueAddDto> salverList = waybillService
				.queryValueAddPriceList(getQueryYokeParam(bean,
						DictionaryValueConstants.PACKAGE_TYPE__SALVER,
						new BigDecimal(salverGoodsPieces), false));

		if (salverList == null || salverList.isEmpty()) {
			salverList = waybillService
					.queryValueAddPriceList(getQueryYokeParam(bean,
							DictionaryValueConstants.PACKAGE_TYPE__SALVER,
							new BigDecimal(salverGoodsPieces), true));
		}

		if (salverList != null && salverList.size() > 0) {
			return salverList.get(0);
		} else
			return null;
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
		queryDto.setBillTime(bean.getBillTime());
		queryDto.setValuedtos(bean.getValuedtos());//保费和代收，是否接货（条件折扣）
		//更改单，条件折扣，封装是否接货
		if(bean.getPickupToDoor()!=null&&bean.getPickupToDoor()){//是否上门接货为true，则设置“Y”
			queryDto.setIsReceiveGoods(FossConstants.YES);
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);//否则设置“N”
		}
		queryDto.setCaculateType(bean.getCaculateType());
		//定价体系优化项目POP-489
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		//封装市场营销活动校验条件
		settterActiveParamInfoValueAdd(queryDto,bean);
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	
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
			//获取导入时折扣信息
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
	 * 设置保存按钮与提交按钮不可编辑
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:24:45
	 */
	public static void setSaveAndSubmitFalse(WaybillRFCUI ui){
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);
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
					setBankInfo(bean, entity);
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
					setBankInfo(bean, entity);
			}
		} else {
			cleanBankInfo(bean);
		}
	}

	private static void setBankInfo(WaybillPanelVo bean, CusAccountEntity entity) {
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
	

	/**
	 * 更改单配合CUBC校验银行信息
	 * @author	DP-FOSS zhaoyiqing 343617
	 * @date 	20161025
	 * @param 	bean 		运单属性Vo
	 * @param 	isShowMSG 	是否显示对公、对私消息提醒
	 */
	public static void validateBankInfoCUBC(WaybillPanelVo bean,boolean isShowMSG){
		boolean isCheckInfo = false;
		DataDictionaryValueVo vo = bean.getRefundType();
		if (vo != null && vo.getValueCode() != null) {
			if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.notDeliveryCustomerCode"));
			} else {
				CusAccountEntity entity = bean.getOpenBank();
				if (entity != null) {
					bean.setOpenBank(entity);
					// 即日退只能选择对私账户
					if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())) {
						if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT.equals(entity.getAccountNature())) {
							//校验参数
							isCheckInfo =validateRefundBankInfo(entity);
						} else {
							if(isShowMSG){
								MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.refundTypeOneDay"));
							}
							isCheckInfo = false;
						}
					}
					else if(StringUtil.equals(bean.getRefundType().getValueCode(), WaybillConstants.REFUND_TYPE_THREE_DAY)){
						//校验参数
						isCheckInfo =validateRefundBankInfo(entity);
					}
					else if(StringUtil.equals(bean.getRefundType().getValueCode(),WaybillConstants.REFUND_TYPE_VALUE)) {
						isCheckInfo = true;
					}
					bean.setAccountName(isCheckInfo?entity.getAccountName():"");// 开户人名称
					bean.setAccountCode(isCheckInfo?entity.getAccountNo():"");// 开户账号
					bean.setAccountBank(isCheckInfo?entity.getOpeningBankName():"");// 开户行名称
				}
			}
		} else {
			cleanBankInfo(bean);
		}
	}

	//DP-FOSS zhaoyiqing 343617 20161025 退款方式为当日退或者三日退的校验逻辑逻辑
	private static boolean validateRefundBankInfo(CusAccountEntity entity) {
		//收款人、银行账号、开户行编码、开户行名称、省、市、对公对私标志、支行编码、支行名称都不能为空
		if(StringUtil.isEmpty(entity.getAccountName())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.accountName"));
		}
		else if(StringUtil.isEmpty(entity.getAccountNo())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.accountNo"));
		}
		else if(StringUtil.isEmpty(entity.getBankCode())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.bankCode"));
		}
		else if(StringUtil.isEmpty(entity.getOpeningBankName())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.openingBankName"));
		}
		else if(StringUtil.isEmpty(entity.getProvCode())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.provCode"));
		}
		else if(StringUtil.isEmpty(entity.getCityCode())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.cityCode"));
		}
		else if(StringUtil.isEmpty(entity.getAccountNature())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.accountNature"));
		}
		else if(StringUtil.isEmpty(entity.getBranchBankCode())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.branchBankCode"));
		}
		else if(StringUtil.isEmpty(entity.getBranchBankName())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.customer.bankInfo.null.branchBankName"));
		}
		//验证通过设置
		else {
			return true;
		}
		return false;
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
				CommonUtils.add(dto, data ,bean);
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
		// 大客户标记
		bean.setReceiveBigCustomer(FossConstants.INACTIVE);
		ui.consigneePanel.getTxtReceiveCustomerCode().setText("");
		// 联系人ID
		bean.setReceiveCustomerContactId("");
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
		
		//清空【是否统一结算】，【合同部门】，【催款部门】
		bean.setArriveCentralizedSettlement("");
		bean.setArriveContractOrgCode("");
		bean.setArriveContractOrgName("");
		bean.setArriveReminderOrgCode("");
				//end
		// 收货大客户标记
		ui.consigneePanel.getLblCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		
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
		bean.setReceiveCustomerAddressNote(memberBean.getCustAddrRemark());
		ui.consigneePanel.getTxtConsigneeAddressNote().setText(bean.getReceiveCustomerAddressNote());
		// 客户名称
		bean.setReceiveCustomerName(memberBean.getCustomerName());
		ui.consigneePanel.getTxtConsigeeName().setText(bean.getReceiveCustomerName());
		// 客户编码
		bean.setReceiveCustomerCode(memberBean.getCustomerCode());
		ui.consigneePanel.getTxtReceiveCustomerCode().setText(bean.getReceiveCustomerCode() );
		// 大客户标记
		bean.setReceiveBigCustomer(memberBean.getIsBigCustomer());
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
		
		//是否为大客户标识
		if(FossConstants.ACTIVE.equals(memberBean.getIsBigCustomer())){
			//设置大客户标记
			ui.consigneePanel.getLblCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			//取消大客户标记
			ui.consigneePanel.getLblCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		}

		// 设置接送货地址ID
		bean.setContactAddressId(memberBean.getAddressId());
		//填充【是否统一结算的信息】
		setReceiveSettleAndContactAndRemending(memberBean,bean);
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
		if(!WaybillConstants.GIANT_SINK.equals(bean.getOrderChannel())
			&&!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setDeliveryCustomerCode("");
		}
		bean.setDeliveryCustomerCode("");
		ui.consignerPanel.getTxtDeliveryCustomerCode().setText(bean.getDeliveryCustomerCode());
		// 大客户标记
		bean.setDeliveryBigCustomer("");
		// 发货联系人ID
		bean.setDeliveryCustomerContactId("");
		// 发货客户是否精准包裹
		bean.setIsAccuratePackage(FossConstants.NO);
		// 发货客户手机
		bean.setDeliveryCustomerMobilephone(mobile);
		ui.consignerPanel.getTxtConsignerMobile().setText(bean.getDeliveryCustomerMobilephone());
		
		// 发货客户电话
		bean.setDeliveryCustomerPhone(phone);
		ui.consignerPanel.getTxtConsignerPhone().setText(bean.getDeliveryCustomerPhone());
		
		// 发货客户地址
		bean.setDeliveryCustomerAddress("");
		ui.consignerPanel.getTxtConsignerAddress().setText(bean.getDeliveryCustomerAddress());
		// 发货客户地址
		bean.setDeliveryCustomerAddressNote("");
		ui.consignerPanel.getTxtConsignerAddressNote().setText(bean.getDeliveryCustomerAddressNote());
		// 是否月结
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改月结
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.GIANT_SINK.equals(bean.getOrderChannel())
			&&!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setChargeMode(Boolean.valueOf(false));
		}
		//优惠类型
		bean.setPreferentialType("");
		// 合同编号
		bean.setAuditNo("");
		// 行政区域
		bean.setDeliveryCustomerArea("");
		
		//清空【是否统一结算】，【合同部门】，【催款部门】
		bean.setStartCentralizedSettlement("");
		bean.setStartContractOrgCode("");
		bean.setStartContractOrgName("");
		bean.setStartReminderOrgCode("");
		//end
		ui.consignerPanel.getTxtConsignerArea().setText(bean.getDeliveryCustomerArea());
		bean.setDeliveryCustomerAreaDto(null);
		// 发货大客户标记
		ui.consignerPanel.getLblCustomerCode().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		// 发货联系人编辑状态
		ui.getTxtConsignerLinkMan().setEnabled(true);
		// 设置装卸费编辑状态-月结客户不允许开装卸费
		Common.setServiceChargeEnabled("",false, ui);

		// 发货客户联系人:放在最后，校验通过不过时不会影响编辑状态的修改
		bean.setDeliveryCustomerContact("");
		ui.consignerPanel.getTxtConsignerLinkMan().setText(ui.getBinderWaybill().getDeliveryCustomerContact());
		//客户分群
		bean.setFlabelleavemonth(null);
		ui.consignerPanel.getCombFlabelleavemonth().setSelectedIndex(-1);
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
		if (StringUtils.isNotEmpty(preferentialType)) {
			if (preferentialType
					.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
				ui.incrementPanel.getTxtServiceCharge().setEnabled(true);// 装卸费
			}

		}
	}
	
	/**
	 * 填充发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午9:14:31
	 * @update  2014-11-25
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
		ui.consignerPanel.getTxtConsignerLinkMan().setEnabled(false);
		// 电话
		waybillBean.setDeliveryCustomerPhone(memberBean.getPhone());
		ui.consignerPanel.getTxtConsignerPhone().setText(waybillBean.getDeliveryCustomerPhone());
		// 地址
		waybillBean.setDeliveryCustomerAddress(memberBean.getAddress());
		ui.consignerPanel.getTxtConsignerAddress().setText(waybillBean.getDeliveryCustomerAddress());
		
		//TODO 地址备注
		waybillBean.setDeliveryCustomerAddressNote(memberBean.getCustAddrRemark());
		//ui.consignerPanel.get
		// 客户名称
		waybillBean.setDeliveryCustomerName(memberBean.getCustomerName());
		// 精准包裹
		waybillBean.setIsAccuratePackage(memberBean.getIsAccuratePackage());
		ui.consignerPanel.getTxtConsigerName().setText( waybillBean.getDeliveryCustomerName() );
		// 客户编码
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.GIANT_SINK.equals(waybillBean.getOrderChannel())
			&&!WaybillConstants.ALIBABA.equals(waybillBean.getOrderChannel())){
			waybillBean.setDeliveryCustomerCode(memberBean.getCustomerCode());
		}
		ui.consignerPanel.getTxtDeliveryCustomerCode().setText( waybillBean.getDeliveryCustomerCode());
		// 大客户标记
		waybillBean.setDeliveryBigCustomer(memberBean.getIsBigCustomer());
		// 客户ID
		waybillBean.setDeliveryCustomerId(memberBean.getCustId());
		// 联系人ID
		waybillBean.setDeliveryCustomerContactId(memberBean.getConsignorId());
		// 合同编号
		waybillBean.setAuditNo(memberBean.getAuditNo());
		// 月结属性
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改月结
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.GIANT_SINK.equals(waybillBean.getOrderChannel())
			&&!WaybillConstants.ALIBABA.equals(waybillBean.getOrderChannel())){
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
		//发票标记
		waybillBean.setInvoice(memberBean.getInvoice());
		// 设置界面"省市区县DTO"对象:适用于通过手机号码等带出的省市区县
		ui.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(address);
		//zxy 20131224 DEFECT-905 start 修改：当客户编码发生改变的时候才修改发票标记
		String custCode =ui.getOriginWaybill().getDeliveryCustomerCode();
		String oldInvoice=ui.getOriginWaybill().getInvoice();
		String oldInvoicetab=ui.getOriginWaybill().getInvoiceTab();
		
		setDeliveryCustomerInvoice(memberBean, waybillBean, custCode,oldInvoice, oldInvoicetab);
		
		//WUTAO
		//给发货客户设置【是否统一结算】【合同部门】【催款部门】
		setDeliverySettleAndContactAndRemending(memberBean, waybillBean);
		//是否为大客户标识
		if(FossConstants.ACTIVE.equals(memberBean.getIsBigCustomer())){
			//设置大客户标记
			ui.consignerPanel.getLblCustomerCode().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			//取消大客户标记
			ui.consignerPanel.getLblCustomerCode().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		}
		//客户分群 zhangchengfu
		if (null != memberBean.getFlabelleavemonth()) {
			fillFlabelleavemonthData(waybillBean, ui, memberBean.getFlabelleavemonth());
		}else{//客户分群为空,取消免费接货,并设置不可更改
			ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setSelected(false);
			ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(false);
		}
		//zxy 20131224 DEFECT-905 end 修改：当客户编码发生改变的时候才修改发票标记
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
		
		/**
		 * FOSS20150818）RFOSS2015052602-梯度保价
		 * @author foss-206860
		 * */
		if(waybillBean.getGoodsVolumeTotal() == null){
			waybillBean.setGoodsVolumeTotal(BigDecimal.ZERO);
		 }
		 if(waybillBean.getGoodsWeightTotal() == null){
			 waybillBean.setGoodsWeightTotal(BigDecimal.ZERO);
		 }
//		setIsEnabledInsuranceRate(ui, waybillBean);
	}

//	private static void setIsEnabledInsuranceRate(WaybillRFCUI ui,
//			WaybillPanelVo waybillBean) {
//		if(waybillBean.getGoodsWeightTotal() != null && waybillBean.getGoodsVolumeTotal() != null 
//				&& waybillBean.getCustomerPickupOrgCode() != null
//				&& waybillBean.getProductCode() != null){
//			String isUpdateDeliveryCustomer = validateDeliveryCustomer(waybillBean.getDeliveryCustomerCode(),ui);
//			waybillBean.setIsUpdateDeliveryCustomer(isUpdateDeliveryCustomer);
//			String isYorN = getInsuranceRate(waybillBean,ui);
//			DataDictionaryValueVo goodsStatus = ui.getBinder().getBean().getGoodsStatus();
//			String inventory = goodsStatus.getValueCode();
//			if(FossConstants.YES.equals(isYorN) && WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)){
//				ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
//			}else{
//				if(FossConstants.YES.equals(isUpdateDeliveryCustomer) && isYorN.equals(FossConstants.YES)){
//					ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
//				}else{
//					ui.incrementPanel.getTxtInsuranceRate().setEnabled(false);
//				}	
//			}
//		}
//	}

	private static void setDeliveryCustomerInvoice(
			QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean,
			String custCode, String oldInvoice, String oldInvoicetab) {
		if(StringUtil.isNotEmpty(custCode)){
			if(!custCode.equals(waybillBean.getDeliveryCustomerCode())){
				 if(WaybillConstants.INVOICE_01.equals(memberBean.getInvoice())){
					 waybillBean.setInvoice(WaybillConstants.INVOICE_01);
					 waybillBean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
			     }else{
						waybillBean.setInvoice(WaybillConstants.INVOICE_02);
						waybillBean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
			    }
			}else{
				waybillBean.setInvoice(oldInvoice);
				waybillBean.setInvoiceTab(oldInvoicetab);
			}
		}else{
			if(WaybillConstants.INVOICE_01.equals(memberBean.getInvoice())){
				 waybillBean.setInvoice(WaybillConstants.INVOICE_01);
				 waybillBean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
		     }else{
					waybillBean.setInvoice(WaybillConstants.INVOICE_02);
					waybillBean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
		    }
		}
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
		if (memberBean != null 
				&& "".equals(memberBean.getCentralizedSettlement())
				&& "".equals(memberBean.getContractOrgCode())
				&& "".equals(memberBean.getReminderOrgCode())) {
			waybillBean
					.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			waybillBean.setStartContractOrgCode(null);
			waybillBean
					.setStartContractOrgName(null);
			waybillBean.setStartReminderOrgCode(null);
		} else {
			//发票标记 与 统一结算 无关联 --sangwenhao-272311
//			if (WaybillConstants.INVOICE_02.equals(memberBean.getInvoice())) {
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
			/*} else {
				waybillBean
						.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				waybillBean.setStartContractOrgCode(null);
				waybillBean
						.setStartContractOrgName(null);
				waybillBean.setStartReminderOrgCode(null);
			}*/
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
			String remark = bean.getTransportationRemark();
			if(remark == null){
				remark = "";
			}
			List<DataDictionaryValueEntity> list = waybillService.queryReturnBillType();
			
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode()) || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(false);
				// 设置返单类型默认值
				Common.setReturnBillType(bean, ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getReturnBillTypeModel());
				//清空所有的返单类别
				if(list != null && list.size() > 0){
					for(DataDictionaryValueEntity dataDictionary : list){
						if(dataDictionary != null){
							remark = remark.replace(dataDictionary.getValueName() + ";", "");
							bean.setTransportationRemark(remark);
						}
					}
				}
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
		queryDto.setBillTime(bean.getBillTime());
		queryDto.setWaybillNo(bean.getWaybillNo());
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
		/**
		 * 从客户查询主客户
		 * @author 311417 wangfeng
		 * @date 2017/01/14
		 */
		if (StringUtils.isNotEmpty(memberBean.getCustomerCode())){
			setMainCustomer(ui,memberBean,waybillBean);
		} 

		/**
		 * Dmana-10888根据客户编码查询发票标记
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-06下午13:45
		 */
//		memberBean.setInvoice(CommonUtils.setInvoice(memberBean.getCustomerCode()));
		
		Common.resetDeliverGoodsFee(waybillBean, ui);
		
		//设置实际客户编码
		waybillBean.setDeliveryCustomerCode(memberBean.getCustomerCode());

		// 填充发货客户信息
		fillDeliveryCustomerInfo(ui, memberBean, waybillBean);
		// 提交为不可编辑
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);
		
		//事后阶梯折装卸费不可编辑
		boolean flag = iSLtDiscountBackInfo(waybillBean);
		if(!flag) {			
		ui.incrementPanel.getTxtServiceCharge().setEnabled(false);
		//给提示装卸费不可编辑
		waybillBean.setServiceFee(BigDecimal.ZERO);
		waybillBean.setServiceFeeCanvas("0");
	   }
		
	}
	
	
	/**
	 * 设置客户圈主客户信息
	 * @param memberBean
	 * @param waybillBean
	 * @author wangfeng @date 2017/01/12
	 */
	private static void setMainCustomer(WaybillRFCUI ui,QueryMemberDialogVo memberBean,WaybillPanelVo waybillBean) {
		String mainCustomer;
		CustomerCircleNewDto customerCircleNewDto = waybillService.queryCustomerByCusCode(memberBean.getCustomerCode(), waybillBean.getBillTime() , "N");
		if (customerCircleNewDto != null
				&& ("Y").equals(customerCircleNewDto.getIsCustCircle())
				&& customerCircleNewDto.getCustomerCircleEntity() != null
				&& customerCircleNewDto.getCusBargainNewEntity() != null
				&& customerCircleNewDto.getCustomerNewEntity() != null) {
			mainCustomer = customerCircleNewDto.getCustomerCircleEntity().getCustCode();
			//合同实体
			waybillBean.setCusBargainNewEntity(customerCircleNewDto.getCusBargainNewEntity());
			//客户圈实体
			waybillBean.setCustomerCircleEntity(customerCircleNewDto.getCustomerCircleEntity());
			//客户实体
			waybillBean.setCustomerNewEntity(customerCircleNewDto.getCustomerNewEntity());
			//设置主客户
			waybillBean.setIsMainCustomer("Y");
			//设置是否客户圈
			waybillBean.setIsCircle("Y");
			//设置主客户编码
			waybillBean.setMainCustomerCode(mainCustomer);
			//设置主客户编码
			waybillBean.setDeliveryCustomerCode(mainCustomer);
			//设置实际客户(从客户编码,开单显示从客户编码)
			waybillBean.setActualCustomerCode(memberBean.getCustomerCode());
			//是否精准包裹
			memberBean.setIsAccuratePackage(customerCircleNewDto.getCusBargainNewEntity().getIsAccuratePackage());
			//大客户标记
			memberBean.setIsBigCustomer(customerCircleNewDto.getCustomerNewEntity().getIsLargeCustomers());
			//合同编号
			memberBean.setAuditNo(customerCircleNewDto.getCusBargainNewEntity().getBargainCode());
//			boolean monthEnd;
//			if("MONTH_END".equals(customerCircleNewDto.getCusBargainNewEntity().getChargeType())){
//				monthEnd =true;
//				//月结属性
//				waybillBean.setChargeMode(monthEnd);
//			}
			//优惠类型
			memberBean.setPreferentialType(customerCircleNewDto.getCusBargainNewEntity().getPreferentialType());
			//统一结算
			setDeliverySettleAndContactAndRemending(customerCircleNewDto.getCustomerCircleEntity(),memberBean,customerCircleNewDto.getCusBargainNewEntity());
			//客户分群
			memberBean.setFlabelleavemonth(customerCircleNewDto.getCustomerNewEntity().getFlabelleavemonth());
			//合同部门的标杆名称
			memberBean.setContractOrgName(customerCircleNewDto.getCusBargainNewEntity().getApplicateOrgName() !=null ? customerCircleNewDto.getCusBargainNewEntity().getApplicateOrgName():"");
			//合同部门的标杆编码
			memberBean.setContractOrgCode(customerCircleNewDto.getCusBargainNewEntity().getUnifiedCode() !=null ? customerCircleNewDto.getCusBargainNewEntity().getUnifiedCode():"");
			//催款部门的标杆编码
			memberBean.setReminderOrgCode(customerCircleNewDto.getCusBargainNewEntity().getHastenfunddeptCode() !=null ? customerCircleNewDto.getCusBargainNewEntity().getHastenfunddeptCode():"");
			/**
			 * Dmana-10888根据客户编码查询发票标记
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-06下午13:45
			 */
			queryInvoice(ui, memberBean, waybillBean, mainCustomer);

		}else{
			//设置是否客户圈
			waybillBean.setIsCircle("N");
			//设置主客户
			waybillBean.setIsMainCustomer("N");
			/**
			 * Dmana-10888根据客户编码查询发票标记
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-06下午13:45
			 */
			memberBean.setInvoice(CommonUtils.setInvoice(memberBean.getCustomerCode()));
			//设置实际客户编码
			waybillBean.setDeliveryCustomerCode(memberBean.getCustomerCode());
			if(waybillBean.getGoodsWeightTotal() != null && waybillBean.getGoodsVolumeTotal() != null 
					&& waybillBean.getCustomerPickupOrgCode() != null
					&& waybillBean.getProductCode() != null){
				String isUpdateDeliveryCustomer = validateDeliveryCustomer(waybillBean.getDeliveryCustomerCode(),ui);
				waybillBean.setIsUpdateDeliveryCustomer(isUpdateDeliveryCustomer);
				String isYorN = getInsuranceRate(waybillBean,ui);
				DataDictionaryValueVo goodsStatus = ui.getBinder().getBean().getGoodsStatus();
				String inventory = goodsStatus.getValueCode();
				if(FossConstants.YES.equals(isYorN) && WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)){
					ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
				}else{
					if(FossConstants.YES.equals(isUpdateDeliveryCustomer) && isYorN.equals(FossConstants.YES)){
						ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
					}else{
						ui.incrementPanel.getTxtInsuranceRate().setEnabled(false);
					}	
				}
			}
		}
		
	}

	//根据客户编码查询发票标记
	private static void queryInvoice(WaybillRFCUI ui,
			QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean,
			String mainCustomer) {
		memberBean.setInvoice(CommonUtils.setInvoice(mainCustomer));
		if(waybillBean.getGoodsWeightTotal() != null && waybillBean.getGoodsVolumeTotal() != null 
				&& waybillBean.getCustomerPickupOrgCode() != null
				&& waybillBean.getProductCode() != null){
			String isUpdateDeliveryCustomer = validateDeliveryCustomer(waybillBean.getDeliveryCustomerCode(),ui);
			waybillBean.setIsUpdateDeliveryCustomer(isUpdateDeliveryCustomer);
			String isYorN = getInsuranceRate(waybillBean,ui);
			DataDictionaryValueVo goodsStatus = ui.getBinder().getBean().getGoodsStatus();
			String inventory = goodsStatus.getValueCode();
			if(FossConstants.YES.equals(isYorN) && WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)){
				ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
			}else{
				if(FossConstants.YES.equals(isUpdateDeliveryCustomer) && isYorN.equals(FossConstants.YES)){
					ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
				}else{
					ui.incrementPanel.getTxtInsuranceRate().setEnabled(false);
				}	
			}
		}
	}

	/**
	 * 设置客户圈主客户统一结算,部门名称
	 *
	 * @author wangfeng 311417
	 */
	private static void setDeliverySettleAndContactAndRemending(CustomerCircleEntity customerCircleEntity, QueryMemberDialogVo memberBean,CusBargainNewEntity cusBargainNewEntity) {
		//是否统一结算
		memberBean.setCentralizedSettlement(customerCircleEntity.getIsFocusPay());
		//催款部门标杆编码
		memberBean.setReminderOrgCode(cusBargainNewEntity.getHastenfunddeptCode());
		//合同部门code
		memberBean.setContractOrgCode(cusBargainNewEntity.getUnifiedCode());
		//合同部门标杆名称
		memberBean.setContractOrgName(cusBargainNewEntity.getApplicateOrgName());
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
		//设置是否统一结算
		setReceiveSettleAndContactAndRemending(memberBean,bean);
	}
	
	/**
	 * @author 200945 - wutao 填充收货人【统一结算】【合同部门】【催款部门】
	 * @param memberBean
	 * @param waybillBean
	 */
	private static void setReceiveSettleAndContactAndRemending(QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean){
		//判断CRM维护了此客户的信息
		if (memberBean != null 
				&& "".equals(memberBean.getCentralizedSettlement())
				&& "".equals(memberBean.getContractOrgCode())
				&& "".equals(memberBean.getReminderOrgCode())) {
			waybillBean
					.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			waybillBean.setArriveContractOrgCode(null);
			waybillBean
					.setArriveContractOrgName(null);
			waybillBean.setArriveReminderOrgCode(null);
		} else {
			//发票标记 与 统一结算 无关联 --sangwenhao-272311
			//当发票标记为02时
//			if (WaybillConstants.INVOICE_02.equals(memberBean.getInvoice())) {
				//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
				//【是否统一结算】设置为【是】，【合同部门】设置为查询出来的对应的部门
				if (WaybillConstants.YES.equals(memberBean
						.getCentralizedSettlement())) {
					waybillBean
							.setArriveCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
					waybillBean.setArriveContractOrgCode(memberBean
							.getContractOrgCode());
					waybillBean.setArriveContractOrgName(memberBean
							.getContractOrgName());
					waybillBean.setArriveReminderOrgCode(memberBean
							.getReminderOrgCode());
				} else {
					//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
					//【是否统一结算】设置为【否】，【合同部门】设置为【null】
					waybillBean
							.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
					waybillBean.setArriveContractOrgCode(null);
					waybillBean
							.setArriveContractOrgName(null);
					waybillBean.setArriveReminderOrgCode(null);
				}
			/*} else {
				//当发票标记为1的时候，【是否统一结算】设置【否】，【合同部门】设置为【null】
				waybillBean
						.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				waybillBean.setArriveContractOrgCode(null);
				waybillBean
						.setArriveContractOrgName(null);
				waybillBean.setArriveReminderOrgCode(null);
			}*/
		}
		//wutao == end 
	}
	/**
	 * 设置送货费
	 * @author WangQianJin
	 * @date 2013-6-17 下午5:23:16
	 */ 
	public static void setDeliveryFeeCollection(WaybillPanelVo bean) {
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
		
		//最终配载部门(计算偏线中转费时用得到)
		productPriceDtoCollection.setLastOrgCode(bean.getLastLoadOrgCode());
		
		productPriceDtoCollection.setBillTime(bean.getBillTime());
		//统一返回的计价值
		List<GuiResultBillCalculateDto> dtos = waybillService.queryGuiBillPrice(productPriceDtoCollection);		

		GuiResultBillCalculateDto guiResultBillCalculateDto;		

		//提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {

			setDeliveryFeeDeliverNoup(bean, dtos, code);

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

	private static void setDeliveryFeeDeliverNoup(WaybillPanelVo bean,
			List<GuiResultBillCalculateDto> dtos, String code) {
		GuiResultBillCalculateDto guiResultBillCalculateDto;
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
	private static GuiQueryBillCalculateDto getProductPriceDtoCollection(WaybillPanelVo bean) {
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
		//zxy 20140509 MANA-1253 start 新增
		if(bean.getFreightMethod() != null )
			queryDto.setCombBillTypeCode(bean.getFreightMethod().getValueCode());
		//zxy 20140509 MANA-1253 end 新增
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
		queryDto.setWaybillNo(bean.getWaybillNo());//运单号
		
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
		bean.setTransportFee(BigDecimal.ZERO);// 公布价运费
		bean.setValueAddFee(BigDecimal.ZERO);// 增值服务费
		bean.setPromotionsFee(BigDecimal.ZERO);// 优惠合计
		bean.setPrePayAmount(BigDecimal.ZERO);// 预付金额
		bean.setToPayAmount(BigDecimal.ZERO);// 到付金额
		bean.setHandWriteMoney(BigDecimal.ZERO);// 手写现付金额
		bean.setTotalFee(BigDecimal.ZERO);

		// 画布
		bean.setBillWeight(BigDecimal.ZERO);// 计费重量
		bean.setUnitPrice(BigDecimal.ZERO);// 费率
		bean.setTransportFeeCanvas("0");// 公布价运费
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
		// 判断是否内部带货自提、内部带货送货
		if (WaybillConstants.INNER_PICKUP.equals(receiveMethod)
				|| WaybillConstants.DELIVER_INNER_PICKUP.equals(receiveMethod)) {	
			//发票标记
			bean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
			bean.setInvoice(WaybillConstants.INVOICE_02);
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
	 * 当送货费被修改并将发货客户修改为月结客户发货客户时，原来在月结客户时手动修改的送货费需要重新进行计算
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-14 下午7:58:22
	 */
	public static void resetDeliverGoodsFee(WaybillPanelVo bean,WaybillRFCUI ui){
		String code = bean.getReceiveMethod().getValueCode();
		//标识为非手动修改过，这样计算总运费的值才会生效
		bean.setHandDeliveryFee(BigDecimal.valueOf(0));
		//判断：若提货方式为送货进仓时，若为月结客户则送货费可编辑
		if(WaybillConstants.DELIVER_STORAGE.equals(code)){
			//不是月结客户
			if(!bean.getChargeMode()){
				ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);
				Common.setSaveAndSubmitFalse(ui);
			}else{
				//月结客户
				ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);
			}
		}
	}
	
	/**
	 * 
	 * 提货方式-自提业务规则处理
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午03:25:42
	 */
	public static void selfPickup(WaybillPanelVo bean, WaybillRFCUI ui) {
	    if(null == bean.getReceiveMethod()){
		return;
	    }
		String code = bean.getReceiveMethod().getValueCode();
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) 
				|| WaybillConstants.AIR_SELF_PICKUP.equals(code) 
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) 
				|| WaybillConstants.AIRPORT_PICKUP.equals(code) 
				|| WaybillConstants.INNER_PICKUP.equals(code)
				|| WaybillConstants.DELIVER_FREE.equals(code) 
				|| WaybillConstants.DELIVER_FREE_AIR.equals(code) 
				//当客户为月结客户时，则送货费可编辑
				|| (WaybillConstants.DELIVER_STORAGE.equals(code) && !bean.getChargeMode())
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(false);
			// 送货费
			bean.setDeliveryGoodsFee(BigDecimal.ZERO);
			bean.setDeliveryGoodsFeeCanvas(BigDecimal.ZERO.toString());
		} else {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);
		}
	}

	/**
	 * 当没有根据名字找到发货客户时，清空客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-17 上午9:12:33
	 */
	public static void noDeliveryCustomerInfo(WaybillRFCUI ui,WaybillPanelVo bean){
		/**
		 * BUG-45873:开单界面修复发货客户后未找到固定客户后未清空客户信息
		 */
		// 若为删除原客户信息，则清空全部
		if (!"".equals(StringUtils.defaultString(bean.getDeliveryCustomerId()))) {
			// 清空发货客户信息
			Common.cleanDeliveryCustomerInfo(ui, bean, "", "");
		}
		// 客户编码
		/**
		 * Dmana-9885通过订单来源判断如果是巨商网或者阿里巴巴来源，付款方式不可更改
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:55
		 */
		if(!WaybillConstants.GIANT_SINK.equals(bean.getOrderChannel())
				&&!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setDeliveryCustomerCode("");
		}
		// 大客户标记
		bean.setDeliveryBigCustomer(FossConstants.INACTIVE);
		// 客户名称
		bean.setDeliveryCustomerName("");
		//是否精准包裹
		bean.setIsAccuratePackage(FossConstants.NO);
		//清空【是否统一结算】，【合同部门】，【催款部门】
		bean.setStartCentralizedSettlement("");
		bean.setStartContractOrgCode("");
		bean.setStartContractOrgName("");
		bean.setStartReminderOrgCode("");
		
		//end
		// 发货客户编码被清空，则对应的银行帐号信息也要清空
		Common.cleanBankInfo(bean);
		//客户分群
		bean.setFlabelleavemonth(null);
		ui.consignerPanel.getCombFlabelleavemonth().setSelectedIndex(-1);
	}
	
	/**
	 * 当没有根据名字找到收货客户时，清空客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-17 上午9:12:33
	 */
	public static void noReceiveCustomerInfo(WaybillRFCUI ui,WaybillPanelVo bean){
		/**
		 * BUG-45873:开单界面修复收货客户后未找到固定客户后未清空客户信息
		 */
		// 若为删除原客户信息，则清空全部
		if (!"".equals(StringUtils.defaultString(bean.getReceiveCustomerId()))) {
			// 清空发货客户信息
			Common.cleanReceiveCustomerInfo(ui, bean, "", "");
		}
		// 客户编码
		bean.setReceiveCustomerCode("");
		// 大客户标记
		bean.setReceiveBigCustomer(FossConstants.INACTIVE);
		// 客户名称
		bean.setReceiveCustomerName("");
		//清空【是否统一结算】，【合同部门】，【催款部门】
		bean.setArriveCentralizedSettlement("");
		bean.setArriveContractOrgCode("");
		bean.setArriveContractOrgName("");
		bean.setArriveReminderOrgCode("");
		//end
	}
	
	/**
	 * 从其他费用中删除送货费子项
	 * @author WangQianJin
	 * @date 2013-07-24
	 */
	public static void deleteDeliverFee(List<OtherChargeVo> changeDetailList){		
		List<OtherChargeVo> delList=new ArrayList<OtherChargeVo>();
		if(changeDetailList!=null && changeDetailList.size()>0){
			/**
			 * 删除所有的送货费子项(包括：送货费、送货上楼费、送货进仓费、超远派送费)
			 */
			for(OtherChargeVo chargeVo : changeDetailList){
				if(PriceEntityConstants.PRICING_CODE_SH.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_SHSL.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_SHJC.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_CY.equals(chargeVo.getCode())){					
					delList.add(chargeVo);
				}
			}
			//将要删除的送货费子项全部删除，子所以要放到delList里面一块删除，是因为遍历的时候不能直接删除，偶尔会报java.util.ConcurrentModificationException
			if(delList.size()>0){
				changeDetailList.removeAll(delList);
			}
		}
	}
/**
	 * 
	 * 设置自提件运输性质
	 * @author WangQianJin
	 * @date 2013-4-24
	 */
	public static void economyGoodsTypeListener(WaybillInfoVo bean,WaybillRFCUI ui){
		//如果是自提件业务，则重新获取产品信息
		if(bean.getIsEconomyGoods()!=null && bean.getIsEconomyGoods()){
			if(bean.getEconomyGoodsType()!=null && bean.getEconomyGoodsType().getValueCode()!=null && !"".equals(bean.getEconomyGoodsType().getValueCode())){				
				//根据渠道CODE和当前时间获取产品信息
				List<ProductEntity> productList=waybillService.getProductOfMinFeePlanByChannelCodeAndSpecifiedDate(bean.getEconomyGoodsType().getValueCode(), bean.getBillTime());
				//设置产品信息
				setProductTypeModel(productList,ui,bean);
			}else{
				//如果是自提件并且自提件类型为空，则删除运输性质
				if(bean.getRfcType()!=null){
					String rfcType = bean.getRfcType().getValueCode();
					if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
						ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getTransferProductTypeModel().removeAllElements();
					}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
						ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getReturnProductTypeModel().removeAllElements();
					}else{
						ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getProductTypeModel().removeAllElements();
					}
				}			
			}
		}		
	}
	
	/**
	 * 
	 * 设置产品到数据模型
	 * @author WangQianJin
	 * @date 2013-08-16
	 */
	public static void setProductTypeModel(List<ProductEntity> productList,WaybillRFCUI ui,WaybillInfoVo bean){
		if(bean.getRfcType()!=null){
			ProductEntityVo firstProduct=null;
			//变更类型删除原来的运输性质
			String rfcType = bean.getRfcType().getValueCode();			
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
				firstProduct=bean.getTfrProductCode();
				ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getTransferProductTypeModel().removeAllElements();				
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				firstProduct=bean.getRtnProductCode();
				ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getReturnProductTypeModel().removeAllElements();				
			}else{
				firstProduct=bean.getProductCode();
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getProductTypeModel().removeAllElements();				
			}		
			if(CollectionUtils.isNotEmpty(productList)){
				ProductEntityVo vo = null;
				for (ProductEntity product : productList) {	
					if(!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode()) && !CommonUtils.directDetermineIsExpressByProductCode(product.getCode())){
						vo = new ProductEntityVo();
						//将数据库查询出的产品对象进行转换，转成VO使用的对象
						ValueCopy.entityValueCopy(product, vo);
						vo.setDestNetType(product.getDestNetType());	
						//根据变更类型往对应的MODEL里面添加运输性质
						if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
							ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getTransferProductTypeModel().addElement(vo);
						}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
							ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getReturnProductTypeModel().addElement(vo);
						}else{
							ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getProductTypeModel().addElement(vo);
						}
						if(firstProduct==null){
							firstProduct=vo;
						}
					}
				}
			}			
			//设置查询出的第一个产品为默认产品
			if(firstProduct!=null){
				if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
					bean.setTfrProductCode(firstProduct);
				}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
					bean.setRtnProductCode(firstProduct);
				}else{
					bean.setProductCode(firstProduct);
				}				
			}
		}
			
	}
	
	/**
	 * 
	 * 设置自提件提货方式
	 * @author WangQianJin
	 * @date 2013-08-16
	 */
	public static void setEconomyReceiveMethod(WaybillInfoVo bean,WaybillRFCUI ui){	
		//如果是自提件，并且变更类型不为空
		if(bean.getIsEconomyGoods()!=null && bean.getIsEconomyGoods() && bean.getRfcType()!=null){
			//变更类型
			String rfcType = bean.getRfcType().getValueCode();
			//运输性质
			String productCode=null;
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
				if(bean.getTfrProductCode()!=null){
					productCode=bean.getTfrProductCode().getCode();
				}
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				if(bean.getRtnProductCode()!=null){
					productCode=bean.getRtnProductCode().getCode();
				}
			}else{
				if(bean.getProductCode()!=null){
					productCode=bean.getProductCode().getCode();
				}					
			}				
			/**
			 * 创建提货方式对象
			 */
			DataDictionaryValueVo receiveMethod = new DataDictionaryValueVo();
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
				//空运自提
				receiveMethod.setValueCode(WaybillConstants.AIRPORT_PICKUP);
				receiveMethod.setValueName(i18n.get("foss.gui.creating.transferInfoPanel.airportPickup.label"));								
			} else {
				//汽运自提
				receiveMethod.setValueCode(WaybillConstants.SELF_PICKUP);
				receiveMethod.setValueName(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.pickup"));
			}
			//设置提货方式
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
				bean.setTfrReceiveMethod(receiveMethod);
				ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferAllocationType().setEnabled(false);
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				bean.setRtnReceiveMethod(receiveMethod);
				ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getCombReturnAllocationType().setEnabled(false);
			}else{
				bean.setReceiveMethod(receiveMethod);
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().setEnabled(false);					
			}
		}				
	}
	
	
	/**
	 * 初始化提货方式
	 */
	public static void initCombPickMode(WaybillInfoVo bean,WaybillRFCUI ui) {
		String productCode=null;
		if(bean.getRfcType()!=null){			
			String rfcType = bean.getRfcType().getValueCode();
			//清空提货方式
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
				ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getPickModeModel().removeAllElements();
				if(bean.getTfrProductCode()!=null){
					productCode=bean.getTfrProductCode().getCode();	
				}				
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getPickModeModel().removeAllElements();
				if(bean.getRtnProductCode()!=null){
					productCode=bean.getRtnProductCode().getCode();	
				}
			}else{				
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPickModeModel().removeAllElements();
				if(bean.getProductCode()!=null){
					productCode=bean.getProductCode().getCode();	
				}
			}
			//获取提货方式列表
			List<DataDictionaryValueEntity> list = null;
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
				list = waybillService.queryPickUpGoodsAir();
			} else {
				list = waybillService.queryPickUpGoodsHighWays();
			}
			try {
				//循环添加VO到提货方式控件
				initCombPickModeExp(ui, rfcType, list);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} 
			//设置提货方式是否可编辑
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
				ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferAllocationType().setEnabled(true);
				if(ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferAllocationType().getItemCount()>0){
					ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferAllocationType().setSelectedIndex(0);
				}
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getCombReturnAllocationType().setEnabled(true);
				if(ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getCombReturnAllocationType().getItemCount()>0){
					ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getCombReturnAllocationType().setSelectedIndex(0);
				}
			}else{				
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().setEnabled(true);	
				if(ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().getItemCount()>0){
					ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().setSelectedIndex(0);
				}
			}
		}		
	}

	private static void initCombPickModeExp(WaybillRFCUI ui, String rfcType,
			List<DataDictionaryValueEntity> list)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo dataDictionaryVo = new DataDictionaryValueVo();
			PropertyUtils.copyProperties(dataDictionaryVo, dataDictionary);
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {	
				//转运与返货不能选择内部自提与空运免费自提
				if (!WaybillConstants.INNER_PICKUP.equals(dataDictionaryVo.getValueCode()) 
						&& !WaybillConstants.AIR_PICKUP_FREE.equals(dataDictionaryVo.getValueCode()) ) {
					ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getPickModeModel().addElement(dataDictionaryVo);
				}									
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				//转运与返货不能选择内部自提与空运免费自提
				if (!WaybillConstants.INNER_PICKUP.equals(dataDictionaryVo.getValueCode()) 
						&& !WaybillConstants.AIR_PICKUP_FREE.equals(dataDictionaryVo.getValueCode()) ) {
					ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getPickModeModel().addElement(dataDictionaryVo);
				}						
			}else{				
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPickModeModel().addElement(dataDictionaryVo);
			}					
		}
	}
	
	/**
	 * 初始化运输性质
	 */
	public static void initCombProductType(WaybillInfoVo bean,WaybillRFCUI ui){		
		if(bean.getPickupCentralized()!=null && bean.getPickupCentralized()){
			//集中开单不做处理
		}else{
			//营业部开单
			if(bean.getRfcType()!=null){
				String rfcType = bean.getRfcType().getValueCode();
				if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {				
					initTansferComponentData(bean,ui);
				}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
					initReturnComponentData(bean,ui);
				}else{
					initTranportCombProductType(bean,ui);
				}
			}			
		}
	}
	
	/**
	 * 初始化转运运输性质
	 * @param bean
	 * @param ui
	 */
	public static void initTansferComponentData(WaybillInfoVo bean,WaybillRFCUI ui) {		
		List<ProductEntity> list = waybillService.queryTransType(bean.getTfrStartOrgCode());
		TransferInfoPanel transferInfoPanel = ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel();
		DefaultComboBoxModel productTypeModel = transferInfoPanel.getTransferProductTypeModel();
		productTypeModel.removeAllElements();		
		for (ProductEntity dataDictionary : list) {
			if(bean.isTfrNeedFilter()){
				if(!(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(dataDictionary.getCode()) 
						|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(dataDictionary.getCode()))){
					continue;
				}
			}
			ProductEntityVo vo = new ProductEntityVo();
			try {
				if(CommonUtils.directDetermineIsExpressByProductCode(dataDictionary.getCode())){
					continue;
				}
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				System.out.println(e.getMessage());
			} catch (InvocationTargetException e) {
				System.out.println(e.getMessage());
			}
			productTypeModel.addElement(vo);
		}
		if(transferInfoPanel.getCombTransferProductType().getItemCount()>0){
			transferInfoPanel.getCombTransferProductType().setSelectedIndex(0);
		}
	}
	
	/**
	 * 返货运输性质
	 */
	public static void initReturnComponentData(WaybillInfoVo bean,WaybillRFCUI ui) {		
		List<ProductEntity> list = waybillService.queryTransType(bean.getRtnStartOrgCode());
		ReturnInfoPanel returnInfoPanel = ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel();
		DefaultComboBoxModel productTypeModel = returnInfoPanel.getReturnProductTypeModel();
		productTypeModel.removeAllElements();		
		for (ProductEntity dataDictionary : list) {
			// 返货不能选择空运类型
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(dataDictionary.getCode())){
				continue;
			}			
			if(bean.isRtnNeedFilter()){
				if(!(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(dataDictionary.getCode()) 
						|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(dataDictionary.getCode()))){
					continue;
				}
			}
			ProductEntityVo vo = new ProductEntityVo();
			try {
				if(CommonUtils.directDetermineIsExpressByProductCode(dataDictionary.getCode())){
					continue;
				}
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				System.out.println(e.getMessage());
			} catch (InvocationTargetException e) {
				System.out.println(e.getMessage());
			}
			productTypeModel.addElement(vo);
		}
		if(returnInfoPanel.getCombReturnProductType().getItemCount()>0){
			returnInfoPanel.getCombReturnProductType().setSelectedIndex(0);
		}
	}
	
	/**
	 * 运输信息运输性质
	 * @param bean
	 */
	public static void initTranportCombProductType(WaybillInfoVo bean,WaybillRFCUI ui) {
		String newValue = bean.getRfcSource();
		List<ProductEntity> list = new ArrayList<ProductEntity>();
		if(WaybillRfcConstants.CUSTOMER_REQUIRE.equals(newValue)){
			list = new ArrayList<ProductEntity>();
			//本部门出发产品
			List<ProductEntity> deptList = waybillService.queryTransType(bean.getReceiveOrgCode());
			//原运输性质
			ProductEntityVo productVo = ui.getOriginWaybill().getProductCode();
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())){
				for(ProductEntity productEntity : deptList){
					if(productEntity.getCode().equals(productVo.getCode())){
						list.add(productEntity);
						break;
					}
				}
			}else{
				//目的站到达产品
				List<ProductEntity> arriveList = waybillService.queryByArriveDeptProduct(bean.getCustomerPickupOrgCode().getCode());
				for(ProductEntity fromEntity : deptList){
					for(ProductEntity toEntity : arriveList){
						if(fromEntity.getCode().equals(toEntity.getCode())){
							list.add(fromEntity);
							break;
						}
					}
				}
			}
			//如果
		}else if(WaybillRfcConstants.INSIDE_REQUIRE.equals(newValue)){
			list = waybillService.queryTransType(bean.getReceiveOrgCode());
		}		 
		DefaultComboBoxModel productTypeModel = ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getProductTypeModel();
		if(list!=null && list.size()>0){
			productTypeModel.removeAllElements();
		}
		for (ProductEntity dataDictionary : list) {
			if(CommonUtils.directDetermineIsExpressByProductCode(dataDictionary.getCode())){
				continue;
			}
			ProductEntityVo vo = new ProductEntityVo();
			try {
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				System.out.println(e.getMessage());
			} catch (InvocationTargetException e) {
				System.out.println(e.getMessage());
			}
			productTypeModel.addElement(vo);
		}
		//设置默认值
		bean.setProductCode(ui.getOriginWaybill().getProductCode());
	}
	/**
	 * 设置经济自提件到储运事项里面
	 * @param bean
	 */
	public static void economySetTransportationRemark(WaybillPanelVo bean) {
		String remark = bean.getTransportationRemark();
		if(remark==null){
			remark="";
		}
		//根据开单日期获取自提件类型列表
		List<MinFeePlanEntity> minFeeList=waybillService.getMinFeePlanEntityByDate(bean.getBillTime());
		/**
		 * 清空所有的自提件名称
		 */				
		if(minFeeList!=null && minFeeList.size()>0){
			for (MinFeePlanEntity minFee : minFeeList) {
				if(minFee!=null){
					remark = remark.replace(minFee.getPlanName()+ ";", "");
				}					
			}
		}
		if (bean.getEconomyGoodsType()!=null && bean.getEconomyGoodsType().getValueName()!=null) {			
			bean.setTransportationRemark(bean.getEconomyGoodsType().getValueName()+ ";" + remark);
		} else {
			bean.setTransportationRemark(remark);
		}
	}
	
	/**
	 * 获取是否能默认勾选
	 * @param bean
	 * @param channelCode
	 * @return
	 */
	public static EconomyVo getIsDefaultSelected(WaybillPanelVo bean,MinFeePlanEntity entity) {
		EconomyVo ecoVo=new EconomyVo();
		//能否使用自提件
		boolean result=false;
		//订单渠道
		boolean bchannel=false;
		//运输性质
		boolean bproduct=false;
		//提货方式
		boolean bdelivery=false;
		//上门接货
		boolean bdoor=false;
		//自提件名称
		boolean bchannelName=false;
		if(entity!=null){
			//获取是否是最低一票的渠道
			if(bean.getOrderChannel()!=null && !"".equals(bean.getOrderChannel())){
				if(bean.getOrderChannel().equals(entity.getChannelCode())){
					bchannel=true;
				}
			}
			//获取运输性质是否符合
			if(bean.getProductCode()!=null && bean.getProductCode().getCode()!=null){
				if(bean.getProductCode().getCode().equals(entity.getProductCode())){
					bproduct=true;
				}
			}else{
				bproduct=true;
			}
			//获取提货方式是否符合
			bdelivery = getIsDefaultSelectedReceiveMethod(bean, bdelivery);
			//获取是否上门接货
			if(bean.getPickupToDoor()!=null && bean.getPickupToDoor()){
				bdoor=false;
			}else{
				bdoor=true;
			}
			//自提件名称
			if(entity.getPlanName()!=null && !"".equals(entity.getPlanName())){
				bchannelName=true;
			}
			
			//设置能否使用经济自提件
			if(bchannel && bproduct && bdelivery && bdoor && bchannelName){
				result=true;
			}			
		}
		ecoVo.setBchannel(bchannel);
		ecoVo.setBchannelName(bchannelName);
		ecoVo.setBdelivery(bdelivery);
		ecoVo.setBdoor(bdoor);
		ecoVo.setBproduct(bproduct);
		ecoVo.setResult(result);
		return ecoVo;
	}

	private static boolean getIsDefaultSelectedReceiveMethod(
			WaybillPanelVo bean, boolean bdelivery) {
		if(bean.getReceiveMethod()!=null && bean.getReceiveMethod().getValueCode()!=null){
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
				//空运自提
				if(WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
					bdelivery=true;
				}					
			} else {
				//汽运自提
				if(WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
					bdelivery=true;
				}					
			}				
		}else{
			bdelivery=true;
		}
		return bdelivery;
	}
	
	/**
	 * 获取是否能默认勾选
	 * @param bean
	 * @param channelCode
	 * @return
	 */
	public static EconomyVo getIsDefaultSelectedForRfc(WaybillInfoVo bean,MinFeePlanEntity entity) {
		EconomyVo ecoVo=new EconomyVo();
		boolean result=false;
		//订单渠道
		boolean bchannel=false;
		//运输性质
		boolean bproduct=false;
		//提货方式
		boolean bdelivery=false;
		//上门接货
		boolean bdoor=false;
		//自提件名称
		boolean bchannelName=false;
		if(entity!=null){
			//获取是否是最低一票的渠道
			if(bean.getOrderChannel()!=null && !"".equals(bean.getOrderChannel())){
				if(bean.getOrderChannel().equals(entity.getChannelCode())){
					bchannel=true;
				}
			}
			//获取运输性质是否符合
			if(bean.getFinalProductCode()!=null && bean.getFinalProductCode().getCode()!=null){
				if(bean.getFinalProductCode().getCode().equals(entity.getProductCode())){
					bproduct=true;
				}
			}else{
				bproduct=true;
			}
			//获取提货方式是否符合
			bdelivery = getIsDefaultSelectedForRfcReceiveMethod(bean, bdelivery);
			//获取是否上门接货
			if(bean.getPickupToDoor()!=null && bean.getPickupToDoor()){
				bdoor=false;
			}else{
				bdoor=true;
			}
			//自提件名称
			if(entity.getPlanName()!=null && !"".equals(entity.getPlanName())){
				bchannelName=true;
			}
			
			//设置能否使用经济自提件
			if(bchannel && bproduct && bdelivery && bdoor && bchannelName){
				result=true;
			}			
		}
		ecoVo.setBchannel(bchannel);
		ecoVo.setBchannelName(bchannelName);
		ecoVo.setBdelivery(bdelivery);
		ecoVo.setBdoor(bdoor);
		ecoVo.setBproduct(bproduct);
		ecoVo.setResult(result);
		return ecoVo;
	}

	private static boolean getIsDefaultSelectedForRfcReceiveMethod(
			WaybillInfoVo bean, boolean bdelivery) {
		if(bean.getFinalReceiveMethod()!=null && bean.getFinalReceiveMethod().getValueCode()!=null){
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getFinalProductCode().getCode())) {
				//空运自提
				if(WaybillConstants.AIRPORT_PICKUP.equals(bean.getFinalReceiveMethod().getValueCode())){
					bdelivery=true;
				}					
			} else {
				//汽运自提
				if(WaybillConstants.SELF_PICKUP.equals(bean.getFinalReceiveMethod().getValueCode())){
					bdelivery=true;
				}					
			}				
		}else{
			bdelivery=true;
		}
		return bdelivery;
	}
	
	
	/**
	 * 获取费率范围
	 * @param bean
	 */
	public  static String getInsuranceRate(WaybillInfoVo bean) {
		//货物状态
		String yOrN = FossConstants.NO;
		DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
		String inventory = goodsStatus.getValueCode();
		/**
		 * FOSS20150818）RFOSS2015052602-梯度保价
		 * @author foss-206860
		 * 添加是否有修改客户编码的判断
		 * */
		
			GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
			List<GuiQueryBillCalculateSubDto> subDtos = new ArrayList <GuiQueryBillCalculateSubDto>();
			GuiQueryBillCalculateSubDto  subDto = new GuiQueryBillCalculateSubDto();
			queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
			queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
			queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
			queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
			queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
			queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
			queryDto.setFlightShift(null);// 航班号
			queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
			queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
			queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
			//queryDto.setPricingEntryName(null);// 计价名称
			queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
			queryDto.setFeeRate(bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000)));
			subDto.setSubType(bean.getVirtualCode());
			subDto.setOriginnalCost(bean.getInsuranceAmount());
			subDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);
			subDtos.add(subDto);
			queryDto.setPriceEntities(subDtos);
			queryDto.setBillTime(bean.getBillTime());
			queryDto.setWaybillNo(bean.getWaybillNo());
			GuiResultBillCalculateDto gDto=null;
			/**
			 * FOSS20150818）RFOSS2015052602-梯度保价
			 * 该段业务代码不仅针对整车，针对所有
			 * @author foss-206860
			 * */
//			if(bean.getIsWholeVehicle()){
				gDto =waybillService.getProductPriceDtoOfWVHAndBF(queryDto);
				if(gDto==null){
					queryDto.setReceiveDate(null);
					gDto=waybillService.getProductPriceDtoOfWVHAndBF(queryDto);
				}
//			}
			
			if(gDto !=null && gDto.getMinFeeRate() != null && gDto.getMaxFeeRate() != null){
				//保价费率是否可修改
				bean.setCanmodify(gDto.getCanmodify());
				/**
				 * FOSS20150818）RFOSS2015052602-梯度保价
				 * @author foss-206860
				 * */
				BigDecimal feeRate = Common.nullBigDecimalToZero(gDto.getActualFeeRate()).setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP);
				//实际保价费率
				if(bean.getInsuranceRate() != null 
						&& bean.getMinFeeRate() != null
						&& bean.getMaxFeeRate() != null
						&& bean.getInsuranceRate().compareTo(BigDecimal.ZERO) != 0
						&& gDto.getMinFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP).compareTo(bean.getMinFeeRate()) == 0
						&& gDto.getMaxFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP).compareTo(bean.getMaxFeeRate()) == 0
						&& CollectionUtils.isEmpty(gDto.getDiscountPrograms())){
					bean.setInsuranceRate(bean.getInsuranceRate());
				}else{
					bean.setInsuranceRate(feeRate.setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(NumberConstants.NUMBER_1000)));
				}
				//当折扣保价费率不等于0且折扣保价费率与默认保价费率不相等时，做默认保价费率处理和费率区间处理
				if(PriceEntityConstants.PRICING_CODE_BF.equals(gDto.getPriceEntryCode()) 
						&& CollectionUtils.isNotEmpty(gDto.getDiscountPrograms())){
					//合同客户：折后高于该段保价费率最低值则显示【该段保价费率最低值，折后保价费率】；折后低于或等于该段保价费率最低值则直接显示折后保价费率。
					if(feeRate.compareTo(gDto.getMinFeeRate()) > 0){
						bean.setMinFeeRate(Common.nullBigDecimalToZero(gDto.getMinFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP)));
						bean.setMaxFeeRate(Common.nullBigDecimalToZero(feeRate));
					}else{
						bean.setMaxFeeRate(Common.nullBigDecimalToZero(feeRate));
						bean.setMinFeeRate(Common.nullBigDecimalToZero(feeRate));
					}
				}else{
				//最低保价费率
					bean.setMinFeeRate(gDto.getMinFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP));
				//最高保价费率
					bean.setMaxFeeRate(gDto.getMaxFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP));
				}
				String insuranceRateRange = "["+bean.getMinFeeRate()+","+bean.getMaxFeeRate()+"]";
				bean.setInsuranceRateRange(insuranceRateRange);
				bean.setInsuranceRateRangeCanvas(insuranceRateRange);
				if(WaybillRfcConstants.RECEIVE_STOCK.equals(inventory) || FossConstants.YES.equals(bean.getIsUpdateDeliveryCustomer())){
					if(FossConstants.YES.equals(gDto.getCanmodify())){
						yOrN=FossConstants.YES;
					}
				}
			}else{
				throw new WaybillValidateException(i18n.get("foss.pkp.changing.itserivce.notconfig.default.insurance"));
			}
		
		return yOrN;
	}
	
	/**
	 * 更改单 流水号包装类型置空
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 * @param bean
	 */
	public static void refreshChangeHisLabeledPackageType(WaybillInfoVo bean){
		List<LabeledGoodChangeHistoryDto> labeledGoodEntityVoList = bean.getLabeledGoodChangeHistoryDtoList();
		if(labeledGoodEntityVoList != null){
			for(LabeledGoodChangeHistoryDto vo : labeledGoodEntityVoList){
				//之前被删除，则保持不变
				if(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE.equals(vo.getPackageSalver())
						|| WaybillConstants.PACKAGE_TYPE_SALVER.equals(vo.getPackageSalver())//之前状态等于SLAVER(库存中)，则标识删除
						){
					vo.setPackageSalver(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE);
				}
				//之前状态等于PACKAGE_TYPE_SALVER_ADD_NEW(新增)，则置空
				else if(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_ADD_NEW.equals(vo.getPackageSalver())){
					vo.setPackageSalver(StringUtils.EMPTY);
				}
			}
		}
	}
	
	//精准包裹提示
	public static void validateProductCode(WaybillInfoVo bean){
		// 产品对象
		ProductEntityVo product = bean.getProductCode();
		//获取变更类型
		DataDictionaryValueVo valueVo = bean.getRfcType();
		if(valueVo!=null){			
			String rfcType = valueVo.getValueCode();
			if(WaybillRfcConstants.TRANSFER.equals(rfcType)){
				product = bean.getTfrProductCode();
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				product = bean.getRtnProductCode();
			}
		}
		validateProductCode(bean, product);
	}
	
	//精准包裹提示
	public static void validateProductCode(WaybillInfoVo bean,ProductEntityVo product){
		//发货客户为空，直接返回
		if(StringUtil.isEmpty(bean.getDeliveryCustomerMobilephone())
				&&StringUtil.isEmpty(bean.getDeliveryCustomerPhone())
				&&StringUtil.isEmpty(bean.getDeliveryCustomerName())){
			return ;
		}
		// 精准包裹校验
		if(!WaybillConstants.YES.equals(bean.getIsAccuratePackage())
				&&product!=null&&ProductEntityConstants.PRICING_PRODUCT_PCP.equals(product.getCode())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.isNotAccuratePackageCustomerException"));
		}
	}
	
	/**
	 * 免费接货
	 * @param bean
	 * @param ui
	 */
	public static void changeFreePickUpGoods(WaybillPanelVo bean,WaybillRFCUI ui,ProductEntityVo productVo){
		if (productVo == null || productVo.getCode() == null) {
			return;
		}
		Object flabelLeaveMonth = ui.consignerPanel.getCombFlabelleavemonth().getSelectedItem();
		
		DataDictionaryValueVo valueVo = null;
		if(flabelLeaveMonth==null){
			return;
		}
		valueVo = (DataDictionaryValueVo)flabelLeaveMonth;
		
		if (WaybillConstants.VIP.equals(valueVo.getValueCode())
				||WaybillConstants.OMNI_ACTIVE.equals(valueVo.getValueCode())) {
			// 营业部开单并且勾选上门接货
			if(ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productVo.getCode())){
				// 取消免费接货勾选
				ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setSelected(false);
				// 免费接货置灰
				ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(false);
			}else if(bean.getPickupToDoor()){					
				// 如果满足客户分群以及勾选了上门接货，免费接货可选
				ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(true);
			}
		}
	}
	
	/**
	 * 优惠券冲减费用
	 * 对于运费在calculateTotalFee中进行冲减
	 * 对于综合信息费则在collectionForOtherFee中进行冲减
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
				// 冲减接货费
				BigDecimal pickUpFee = CommonUtils.defaultIfNull(bean.getPickupFee()).subtract(couponFee);
				Common.validateCouponFee(ui,PriceEntityConstants.PRICING_CODE_JH,pickUpFee);
				bean.setPickupFee(pickUpFee);
				bean.setPickUpFeeCanvas(pickUpFee.toString());
			} else if (PriceEntityConstants.PRICING_CODE_SH.equals(bean.getCouponRankType())) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getDeliveryGoodsFee(),type);
				// 冲减送货费
				BigDecimal deliveryGoodsFee = CommonUtils.defaultIfNull(bean.getDeliveryGoodsFee()).subtract(couponFee);
				Common.validateCouponFee(ui,PriceEntityConstants.PRICING_CODE_SH,deliveryGoodsFee);
				bean.setDeliveryGoodsFee(deliveryGoodsFee);
				bean.setDeliveryGoodsFeeCanvas(deliveryGoodsFee.toString());
				bean.setCalculateDeliveryGoodsFee(deliveryGoodsFee);
			}else if(PriceEntityConstants.PRICING_CODE_BF.equals(type)) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getInsuranceFee(),type);
				// 冲减保价费
				BigDecimal insuranceFee = CommonUtils.defaultIfNull(bean.getInsuranceFee()).subtract(couponFee);
				Common.validateCouponFee(ui,PriceEntityConstants.PRICING_CODE_BF,insuranceFee);
				bean.setInsuranceFee(insuranceFee);
			}else if (PriceEntityConstants.PRICING_CODE_BZ.equals(type)) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getPackageFee(),type);
				// 冲减包装费				
				BigDecimal packageFee = CommonUtils.defaultIfNull(bean.getPackageFee()).subtract(couponFee);
				Common.validateCouponFee(ui,PriceEntityConstants.PRICING_CODE_BZ,packageFee);
				bean.setPackageFee(packageFee);
				bean.setPackageFeeCanvas(packageFee.toString());
			}else if (PriceEntityConstants.PRICING_CODE_HK.equals(type)) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getCodFee(),type);
				// 冲减代收货款手续费
				BigDecimal codFee = CommonUtils.defaultIfNull(bean.getCodFee()).subtract(couponFee);
				Common.validateCouponFee(ui,PriceEntityConstants.PRICING_CODE_HK,codFee);
				bean.setCodFee(codFee);
			}else if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){				
				offsetCouponFeeZHXX(ui, bean, couponFee, type);				
			}
			
			//重新计算总费用
			CalculateFeeTotalUtils.resetCalculateFee(bean);
		}
	}

	private static void offsetCouponFeeZHXX(WaybillRFCUI ui,
			WaybillPanelVo bean, BigDecimal couponFee, String type) {
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
						Common.validateCouponFee(ui,PriceEntityConstants.PRICING_CODE_ZHXX,zhxxFee);
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
		//设置计费类型 liyongfei 2015-01-23定价优化新增
		queryDto.setCaculateType(bean.getCaculateType());
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
		queryDto.setBillTime(bean.getBillTime());
		//封装营销活动参数信息
		settterActiveParamInfoValueAdd(queryDto,bean);
		/**
		 * 封装内部发货查询参数
		 */
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * 检验优惠冲减后的费用是否正确
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static void validateCouponFee(WaybillRFCUI ui,String feeName,BigDecimal fee){
		//检验优惠费用是否小于0
		if(BigDecimal.ZERO.compareTo(fee) > 0 ){
			Common.setSaveAndSubmitFalse(ui);
			throw new WaybillSubmitException("对不起，您使用优惠券冲减【"+CommonUtils.convertFeeToName(feeName)+"】后的金额小于零，冲减后的金额为："+fee);
		}
	}
	
	/**
	 * 更改单 流水号打木架木箱标签置空
	 * ISSUE-4391 DEFECT-540
	 * @author	157229-zxy 
	 * @date 2013-12-12 
	 * @param bean
	 */
	public static void refreshChangeHisLabeledWoodenType(WaybillInfoVo bean){
		List<LabeledGoodChangeHistoryDto> alreadyList = bean.getSelectedLabeledGoodEntities();
		for(LabeledGoodChangeHistoryDto dto : alreadyList){
			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())){
				dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE);
			}
			
			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType())){
				dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
			}
		}
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
	 * 设置提货方式到数据模型
	 * 
	 * @date 2014-08-12
	 */
	public static void modifyPickModel(DefaultComboBoxModel pickModel,Boolean falg){
		List<DataDictionaryValueEntity> list = waybillService
				.queryPickUpGoodsHighWays();
		
		if(falg){
			for (DataDictionaryValueEntity dataDictionary : list) {
				//合伙人开的单 去除内部带货自提、送货上楼安装（家居）,内部带货送货 -- sangwenhao
				if(BZPartnersJudge.IS_PARTENER && (WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode()) 
					    ||WaybillConstants.DELIVER_INNER_PICKUP.equals(dataDictionary.getValueCode())
					    ||WaybillConstants.DELIVER_FLOOR.equals(dataDictionary.getValueCode()))){
					continue ;
				}
				if(!WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode())){
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					ValueCopy.valueCopy(dataDictionary, vo);
					pickModel.addElement(vo);
				}
			}
		}else{
			for (DataDictionaryValueEntity dataDictionary : list) {
				//合伙人开的单 去除内部带货自提、送货上楼安装（家居）,内部带货送货 -- sangwenhao
				if(BZPartnersJudge.IS_PARTENER && (WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode()) 
					    ||WaybillConstants.DELIVER_INNER_PICKUP.equals(dataDictionary.getValueCode())
					    ||WaybillConstants.DELIVER_FLOOR.equals(dataDictionary.getValueCode()))){
					continue ;
				}
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				pickModel.addElement(vo);
			}
		}
		
	}
	/**
	 * 设置提货方式到数据模型
	 * 
	 * @date 2014-08-12
	 */
	public static void modifyPickModel(DefaultComboBoxModel pickModel,Boolean falg,WaybillInfoVo bean){
		List<DataDictionaryValueEntity> list = waybillService
				.queryPickUpGoodsHighWays();
		for (int i = 0; i < list.size(); i++) {
			if((WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode()))
					||(BZPartnersJudge.IS_PARTENER && (WaybillConstants.INNER_PICKUP.equals(list.get(i).getValueCode()) //合伙人开的单 去除内部带货自提、送货上楼安装（家居）,内部带货送货 -- sangwenhao
						    ||WaybillConstants.DELIVER_INNER_PICKUP.equals(list.get(i).getValueCode())
						    ||WaybillConstants.DELIVER_FLOOR.equals(list.get(i).getValueCode()))) 
					){
				list.remove(list.get(i));
				i-- ;
			}
		}
		if(falg){
			for (DataDictionaryValueEntity dataDictionary : list) {
				if(!WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode())){
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					ValueCopy.valueCopy(dataDictionary, vo);
					pickModel.addElement(vo);
				}
			}
		}else{
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				pickModel.addElement(vo);
			}
		}
		
	}
	
	/**
	 * 设置运输性质的默认值
	 * 
	 * @date 2014-08-12
	 */
	public static void modifyProductCode(WaybillPanelVo bean, Boolean falg,
			DefaultComboBoxModel productTypeModel) {
		for (int i = 0; i < productTypeModel.getSize(); i++) {
			ProductEntityVo vo = (ProductEntityVo) productTypeModel
					.getElementAt(i);
			if (falg) {
				// 默认设置为-精准大票卡航
				if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG
						.equals(vo.getCode())) {
					bean.setProductCode(vo);
				}
			} else {
				// 默认设置为精准卡航
				if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
						.equals(vo.getCode())) {
					bean.setProductCode(vo);
				}
			}

		}

	}
	
	/**
	 * 设置提货方式默认值
	 * 
	 * @date 2014-8-12
	 */
	public static void modifyReceiveMethod(WaybillPanelVo bean , DefaultComboBoxModel pikcModeModel) {
		for (int i = 0; i < pikcModeModel.getSize(); i++) {
			DataDictionaryValueVo vo =(DataDictionaryValueVo) pikcModeModel.getElementAt(i);
			if (WaybillConstants.SELF_PICKUP.equals(vo.getValueCode())) {
				bean.setReceiveMethod(vo);
			}
		}
	}

	/**
	 *  精准大票提货方式去掉内部带货
	 * 
	 * @date 2014-8-12
	 * 
	 * DMANA-4977:门对门和场对场也需要剔除内部带货
	 */
     public static void delReceiveMethod(ProductEntityVo productVo,List<DataDictionaryValueEntity> list){
    	 if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productVo.getCode())){
				for(Iterator<DataDictionaryValueEntity> item=list.iterator() ; item.hasNext() ; ){
					DataDictionaryValueEntity dve = item.next();
					if(WaybillConstants.INNER_PICKUP.equals(dve.getValueCode())
							|| WaybillConstants.DELIVER_INNER_PICKUP.equals(dve.getValueCode())
							//合伙人开的单 去除[内部带货自提]、送货上楼安装（家居）,[内部带货送货 ]-- sangwenhao
							|| (BZPartnersJudge.IS_PARTENER && (WaybillConstants.DELIVER_FLOOR.equals(dve.getValueCode())))){
						item.remove();
					}
				}
			}
     }  
     /**
      * 更改单的省市区校验 DMANA-4292
      */
     public static void validateCity(WaybillInfoVo vo){
    	 AddressFieldDto address=vo.getReceiveCustomerAreaDto();
    	 String code = vo.getReceiveMethod().getValueCode();
    	//判断是否为自提
 		if (WaybillConstants.SELF_PICKUP.equals(code) 
 				|| WaybillConstants.AIR_SELF_PICKUP.equals(code) 
 				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) 
 				|| WaybillConstants.AIRPORT_PICKUP.equals(code) 
 				|| WaybillConstants.INNER_PICKUP.equals(code)
 				|| WaybillConstants.DELIVER_FREE.equals(code) 
 				|| WaybillConstants.DELIVER_FREE_AIR.equals(code) 
 				){
 			return;
 		}
    	 if(address!=null){
 			if(StringUtils.isEmpty(address.getProvinceId()) ||
 					StringUtils.isEmpty(address.getCityId()) ||
 					StringUtils.isEmpty(address.getCountyId()) ||
 					StringUtils.isEmpty(vo.getReceiveCustomerAddress())){
 				throw new WaybillValidateException("收货省市区不能为空！");
 			}
 		}
     }
     /**
      * @author 200945 - wutao
      * 如果运输性质为门到门的时候剔除提货方式为自提
      * @param productVo
      * @param list
      
     public static void delReceiveMethodDTD(ProductEntityVo productVo,List<DataDictionaryValueEntity> list){
    	 if( ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productVo.getCode())){
				for(Iterator<DataDictionaryValueEntity> item=list.iterator() ; item.hasNext() ; ){
					DataDictionaryValueEntity dve = item.next();
					if(WaybillConstants.SELF_PICKUP.equals(dve.getValueCode())){
						item.remove();
					}
				}
			}
     }
     /**
      * @author 200945 - wutao
      * 起草更改单的时候，当运输性质为门到门的时候，上门接货的勾选框，自动勾选；
      * @param bean
      * @param ui
     public static void setWhenTransportTypeEqDTDSetPickUpSelected(WaybillPanelVo bean,WaybillRFCUI ui){
    	 ProductEntityVo productVo = bean.getProductCode();
 		if(productVo== null){
 			return ;
 		}
 		if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productVo.getCode())){
 			ui.getWaybillInfoPanel().getBasicPanel().getCboReceiveModel().setSelected(true);
 			ui.getWaybillInfoPanel().getBasicPanel().getCboReceiveModel().setEnabled(false);
 		}    	 
     }
     **/
     
     /**
 	 * 清除运单VO中的包装信息并将打包装弹窗中的数值清空
 	 * @author:218371-foss-zhaoyanjun
 	 * @date:2014-10-17上午：11:34
 	 */
 	public static void unsetWaybillPanelVoForPackingPack(WaybillPanelVo bean,EnterPackingInfoDialog dialog){
 		bean.setPaperBoxOne(null);
 		bean.setPaperBoxTwo(null);
 		bean.setPaperBoxThree(null);
 		bean.setPaperBoxFour(null);
 		bean.setFibelBagBlueOne(null);
 		bean.setFibelBagBlueTwo(null);
 		bean.setFibelBagBlueThree(null);
 		bean.setFibelBagBlueFour(null);
 		bean.setFibelBagWhiteOne(null);
 		bean.setFibelBagWhiteTwo(null);
 		bean.setFibelBagWhiteThree(null);
 		bean.setFibelBagWhiteFour(null);
 		bean.setFibelBagWhiteClothFive(null);
 		bean.setFibelBagWhiteClothSix(null);
 		dialog.getPaperBoxOneTextField().setText("");
 		dialog.getPaperBoxTwoTextField().setText("");
 		dialog.getPaperBoxThreeTextField().setText("");
 		dialog.getPaperBoxFourTextField().setText("");
 		dialog.getFibelBagOneBlueTextField().setText("");
 		dialog.getFibelBagTwoBlueTextField().setText("");
 		dialog.getFibelBagThreeBlueTextField().setText("");
 		dialog.getFibelBagFourBlueTextField().setText("");
 		dialog.getFibelBagOneWhiteTextField().setText("");
 		dialog.getFibelBagTwoWhiteTextField().setText("");
 		dialog.getFibelBagThreeWhiteTextField().setText("");
 		dialog.getFibelBagFourWhiteTextField().setText("");
 		dialog.getFibelBagFiveWhiteTextField().setText("");
 		dialog.getFibelBagSixWhiteTextField().setText("");
 		dialog.getPaperBoxTotlePriceTextField().setText("");
 		dialog.getFibelBagTotlePriceTextField().setText("");
 		//dialog.getOtherTotlePriceTextField().setText("");
 		dialog.getAllTotlePriceTextField().setText("");
 	}
  	
  	/**
 	 * 将开单页面和打包装费用有关的费用全部清0
 	 * @param bean
 	 * @author:218371-foss-zhaoyanjun
 	 * @date:2014-11-25上午9:49
 	 */
 	public static void unsetPackingPackFee(WaybillPanelVo bean){
//		bean.setPackingTotle(BigDecimal.ZERO);
		bean.setFibelBagTotlePrice(BigDecimal.ZERO);
		bean.setPaperBoxTotlePrice(BigDecimal.ZERO);
		bean.setOtherTotle(BigDecimal.ZERO);
 	}
 	
 	/**
	 * 
	 * 将NULL值或者空值转换成数字0
	 * 
	 * @author 218371-FOSS-zhaoyanjun
	 * @date 2012-12-12 下午06:01:16
	 */
	public static int nullOrEmptyToInt(Object object) {
		// 若对象为空，则返回0
		if (object == null) {
			return 0;
		} else {
			// 判断是否为字符串对象
			if (object instanceof String) {
				String str = (String) object;
				if ("".equals(str)) {
					return 0;
				} else {
					return Integer.parseInt(str);
				}
				// 判断是否是Integer对象
			} else if (object instanceof Integer) {
				// 直接返回原值
				return (Integer) object;
			}
		}
		// 若为其它类型，则返回0
		return 0;
	}

 	/**
 	 * @author 200945 - wutao
 	 * 公共驗證付款方式的方法
 	 * @param bean
 	 */
 	public static void validatePayMethod(WaybillPanelVo bean){
 		/**
 		 * 客户付款方式只能为【到付】或【月结】
 		 */
 		if(bean.getPaidMethod() != null && StringUtils.equals("Y", bean.getIsCircle())) 
 		{ 		
 			//异地调货付款方式校验
 			checkOstPaidMethod(bean);
 		}else{
 			//非异地调货付款方式校验
 			checkPaidMethod(bean);
 		}
 	}

	private static void checkPaidMethod(WaybillPanelVo bean) {
		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
			// 判断客户是否月结
			if (bean.getChargeMode() == null || !bean.getChargeMode()) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.notMonthPayment"));
			}
		}
		/**
		 * 当发货人【是否统一结算】标记为【是】,该客户付款方式只能为【临时欠款】或【月结】
		 */
		if(bean.getPaidMethod() != null) { 
			
			if (WaybillConstants.IS_NOT_NULL_FOR_AI.equals(bean
					.getStartCentralizedSettlement())) {
				boolean isOk = false;
				if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
					 isOk = true;
				}
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					isOk= true; 
				}
				//判断发货客户是否满足月结的条件
				if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					CusBargainVo vo=new CusBargainVo();
					vo.setChargeType(WaybillConstants.MONTH_END);
					vo.setCustomerCode(bean.getDeliveryCustomerCode());
					vo.setBillDate(new Date());
					vo.setBillOrgCode(bean.getReceiveOrgCode());
					isOk =  WaybillRfcServiceFactory.getWaybillRfcService().isCanPaidMethod(vo);
                    //DP-FOSS zhaoyiqing 343617 20161025 配合CUBC结算中心改造，校验合同部门和催款部门编码
					WaybillRfcServiceFactory.getWaybillRfcService().isCanPaidMethodForCUBC(vo);
				}
				
				if(!isOk){
					throw new WaybillValidateException("当发货人【是否统一结算】标记为【是】,该客户付款方式只能为【临时欠款】或【月结】或【到付】");
				}
			}
		}
	}

	private static void checkOstPaidMethod(WaybillPanelVo bean) {
		bean.setDeliveryCustomerCode(bean.getCustomerCircleEntity().getCustCode());
		//客户圈客户为统一结算只能选择到付和月结-- 311417 
		if(StringUtils.equals(WaybillConstants.IS_NOT_NULL_FOR_AI,bean.getStartCentralizedSettlement())){
			//是否統一結算為是合同是月結
			if(bean.getCusBargainNewEntity() !=null && StringUtil.equals(WaybillConstants.MONTH_END, bean.getCusBargainNewEntity().getChargeType())){
				//月结
				boolean isOk = false;
				//如果合同绑定部门不是当前部门不让开月结
				//判断发货客户是否满足月结的条件
				if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					CusBargainVo vo=new CusBargainVo();
					vo.setChargeType(WaybillConstants.MONTH_END);
					vo.setCustomerCode(bean.getDeliveryCustomerCode());
					vo.setBillDate(bean.getBillTime());
					vo.setBillOrgCode(bean.getReceiveOrgCode());
					isOk =  WaybillRfcServiceFactory.getWaybillRfcService().isCanPaidMethod(vo);
				}
				if(!isOk && WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
					throw new WaybillValidateException("该客户没有月结合同或合同适用部门不匹配，付款方式不能为月结");
					//如果合同绑定部门是当前部门只能为月结和到付
				}else if(!(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode()) || 
						WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode()))){
					bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
					throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.settlementPaidMethod"));
				}
				//是否統一結算為是合同不是月結
			}else if(!WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
				bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
				throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.arrivePaidMethod"));
			}
			//设置实际客户编码
			bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
		}else{
			boolean isOk = false;
			if (!WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
				isOk= true; 
			}
			//判断发货客户是否满足月结的条件
			if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
				CusBargainVo vo=new CusBargainVo();
				vo.setChargeType(WaybillConstants.MONTH_END);
				vo.setCustomerCode(bean.getDeliveryCustomerCode());
				vo.setBillDate(bean.getBillTime());
				vo.setBillOrgCode(bean.getReceiveOrgCode());
				isOk =  WaybillRfcServiceFactory.getWaybillRfcService().isCanPaidMethod(vo);
			}
			if(!isOk){
				bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
				throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.noMonthPaidMethod"));
			}
			//设置实际客户编码
			bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
		}
	}
 	
 	//liding comment
 	//NCI项目,付款方式银行卡时取消校验
 	/**
	 * 校验交易流水号是否符合开单规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23上午11:09
	 */
//	public static void validateTransactionSerialNumber(WaybillInfoVo bean,WaybillRFCUI ui){
//		String valueCode=bean.getPaidMethod().getValueCode();
//		String transactionSerialNumber=bean.getTransactionSerialNumber();
//		if(valueCode.equals(WaybillConstants.CREDIT_CARD_PAYMENT)){
//			if(StringUtils.isEmpty(transactionSerialNumber)){
//				throw new WaybillValidateException(i18n.get("foss.gui.changing.listener.Waybill.transactionSerialNumber"));
//			}
//		}
//	}
	
	/**
	 * 根据付款方式
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-28下午15:52
	 */
	public static void judgePaidMethod(WaybillInfoVo bean,WaybillRFCUI ui){
		//liding comment for NCI
		//判断是否可编辑“交易流水号”
//		String valueCode=bean.getPaidMethod().getValueCode();
		//20160429 liding comment
		// 由于NCI项目--付款方式为银行卡的流水号全部已经置灰所以此处逻辑注释掉
		//合伙人不需要填写交易流水编号  2016年2月22日 09:18:23 葛亮亮
//		if(valueCode.equals(WaybillConstants.CREDIT_CARD_PAYMENT)&&!BZPartnersJudge.IS_PARTENER){
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(true);
//		}
		//判断是否可编辑“付款方式”
//		String orderChannel=bean.getOrderChannel();
		if(WaybillConstants.GIANT_SINK.equals(bean.getOrderChannel())
				||WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			ui.billingPayPanel.getCombPaymentMode().setEnabled(false);
		}
	}

	/**
	 * Dmana-9885巨商汇或者阿里巴巴的订单，根据重量判断是否符合规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-03下午16:28
	 */
	public static boolean specialChannelFreightWeight(WaybillInfoVo bean){
		BigDecimal minPercent=new BigDecimal(NumberConstants.NUMBER_0_96d).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal maxPercent=new BigDecimal(NumberConstants.NUMBER_1_04d).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal crmWeight=bean.getCrmWeight();
		BigDecimal goodsWeightTotal=bean.getGoodsWeightTotal();
		if(crmWeight!=null&&goodsWeightTotal!=null){
			BigDecimal minCalculation=crmWeight.multiply(minPercent);
			BigDecimal maxCalculation=crmWeight.multiply(maxPercent);
			if(goodsWeightTotal.compareTo(minCalculation)>0&&goodsWeightTotal.compareTo(maxCalculation)<0){
				bean.setGoodsWeightTotal(crmWeight);
				if(crmWeight.compareTo(goodsWeightTotal)!=0){
					MsgBox.showInfo("合理误差，以订单渠道重量为准！");
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Dmana-9885巨商汇或者阿里巴巴的订单，根据体积判断是否符合规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-03下午16:28
	 */
	public static boolean specialChannelFreightVolume(WaybillInfoVo bean){
		BigDecimal minPercent=new BigDecimal(NumberConstants.NUMBER_0_9d).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal maxPercent=new BigDecimal(NumberConstants.NUMBER_1_1d).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal crmVolume=bean.getCrmVolume();
		BigDecimal goodsVolumeTotal=bean.getGoodsVolumeTotal();
		if(crmVolume!=null&&goodsVolumeTotal!=null){
			BigDecimal minCalculation=crmVolume.multiply(minPercent);
			BigDecimal maxCalculation=crmVolume.multiply(maxPercent);
			if(goodsVolumeTotal.compareTo(minCalculation)>0&&goodsVolumeTotal.compareTo(maxCalculation)<0){
				bean.setGoodsVolumeTotal(crmVolume);
				if(crmVolume.compareTo(goodsVolumeTotal)!=0){
					MsgBox.showInfo("合理误差，以渠道订单体积为准！");
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据Dmana-9885验证特殊渠道体积，重量是否满足误差范围之内
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-03-11下午15:07
	 */
	public static void specialChannelFreight(WaybillInfoVo bean){
		if(WaybillConstants.GIANT_SINK.equals(bean.getOrderChannel())
			||WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			if(Common.specialChannelFreightWeight(bean)&&Common.specialChannelFreightVolume(bean)){
				bean.setSpecialChannelFreight(true);
			}else{
				bean.setSpecialChannelFreight(false);
			}
		}else{
			bean.setSpecialChannelFreight(false);
		}
	}
	
	/**
	 * @需求：大件上楼优化需求
	 * @功能：判断是否有打木架信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02 下午15:50
	 */
	public static boolean whetherMakeWoodYoke(WaybillInfoVo bean){
		Integer salverGoodsNum=bean.getSalverGoodsNum();
		Integer boxGoodsNum=bean.getBoxGoodsNum();
		Integer standGoodsNum=bean.getStandGoodsNum();
		if((salverGoodsNum==null||salverGoodsNum==0)
				&&(boxGoodsNum==null||boxGoodsNum==0)
				&&(standGoodsNum==null||standGoodsNum==0)){
			return false;
		}else{
			return true;
		}
		
	}
	
	/**
	 * 根据货物名称，填充行业货源品类
	 * 
	 * @author zhangchengfu
	 * @date   2015-6-3	
	 * @param bean
	 */
	public static void fillIndustrySourceCategoryData(WaybillPanelVo bean, WaybillRFCUI ui) {
		String goodsName = bean.getGoodsName();
		String industrySourceCategory = waybillRemotingService.queryIndustrySourceCategory(goodsName);
		JComboBox  box = ui.getWaybillInfoPanel().getGoodsPanel().getTxtIndustrySourceCategory();
		ComboBoxModel model = box.getModel();
		int size = model.getSize();
		for (int i = 0; i < size; i++) {
			if (((DataDictionaryValueVo)(model.getElementAt(i))).getValueCode().equals(industrySourceCategory)) {
				box.setSelectedIndex(i);
				bean.setIndustrySourceCategory(((DataDictionaryValueVo)(model.getElementAt(i))));
				break;
			}
		}
	}
	
	/**
	 * 填充客户分群
	 * 
	 * @author zhangchengfu
	 * @date   2015-6-3	
	 * @param bean
	 */
	public static void fillFlabelleavemonthData(WaybillPanelVo bean,WaybillRFCUI ui, String flabelleavemonth) {
		//运输性质
		ProductEntityVo  productVo = bean.getProductCode();
		/**
		 * 营业部开单如果客户分群是 VIP或者全网活跃群 是上门接货，勾选免费接货 ，设置免费接货为可以勾选状态（可以取消）
		 * 非上门接货，取消免费客户勾选，并且置灰
		 */
		if ("VIP".equals(flabelleavemonth)
				|| "OMNI-ACTIVE".equals(flabelleavemonth)) {
			
			if (ui.getWaybillInfoPanel().getBasicPanel().getCboReceiveModel().isSelected()) {
				
				if(productVo==null||!ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productVo.getCode())){
					// 设置免费接货为可勾选状态
					ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(true);
				}else{
					// 取消免费接货勾选
					ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setSelected(false);
					// 免费接货置灰
					ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(false);
				}
			} else {
				// 取消免费接货勾选
				ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setSelected(false);
				// 免费接货置灰
				ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(false);
			}

		} else {// 非 VIP或者全网活跃群
				// 取消免费接货勾选
			ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setSelected(false);
			// 免费接货置灰
			ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(false);
		}
		
		JComboBox  box = ui.consignerPanel.getCombFlabelleavemonth();
		ComboBoxModel model = box.getModel();
		int size = model.getSize();
		for (int i = 0; i < size; i++) {
			if (((DataDictionaryValueVo)(model.getElementAt(i))).getValueCode().equals(flabelleavemonth)) {
				box.setSelectedIndex(i);
				bean.setFlabelleavemonth(((DataDictionaryValueVo)(model.getElementAt(i))));
				String custGroupCode = bean.getFlabelleavemonth().getValueCode();
				
					//填充客户分群,VIP群/全网活跃群设置免费分群可修改,否则取消免费接货勾选,且设置不可能改
					if (WaybillConstants.VIP.equals(custGroupCode)
							|| WaybillConstants.OMNI_ACTIVE
									.equals(custGroupCode)) {
						if (ui.getWaybillInfoPanel().getBasicPanel().getCboReceiveModel().isEnabled()&&ui.getWaybillInfoPanel().getBasicPanel().getCboReceiveModel().isSelected()
								&&(productVo==null||!ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productVo.getCode()))) {
						ui.getWaybillInfoPanel().getBasicPanel()
								.getCboFreePickupGoods().setEnabled(true);
						}else{
						ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setEnabled(false);
						ui.getWaybillInfoPanel().getBasicPanel().getCboFreePickupGoods().setSelected(false);
						}
					} else {
						ui.getWaybillInfoPanel().getBasicPanel()
								.getCboFreePickupGoods().setSelected(false);
						ui.getWaybillInfoPanel().getBasicPanel()
								.getCboFreePickupGoods().setEnabled(false);
					}

				break;
			}
		}
	}
	
	/**
	 * FOSS20150818）RFOSS2015052602-梯度保价
	 * @author foss-206860
	 * 校验是否修改的客户编码 
	 * */
	public  static String getInsuranceRate(WaybillPanelVo bean,WaybillRFCUI ui) {
		//货物状态
		String yOrN = FossConstants.NO;
		DataDictionaryValueVo goodsStatus = ui.getBinder().getBean().getGoodsStatus();
		String inventory = goodsStatus.getValueCode();
		if(WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)){
			//在库存调用原始方法，不在库存调用新方法
			getInsuranceRate(ui.getBinder().getBean());
		}else{
			GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
			List<GuiQueryBillCalculateSubDto> subDtos = new ArrayList <GuiQueryBillCalculateSubDto>();
			GuiQueryBillCalculateSubDto  subDto = new GuiQueryBillCalculateSubDto();
			GuiResultBillCalculateDto gDto=null;
			
				queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
				queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
				queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
				queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
				queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
				queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
				queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
				queryDto.setFlightShift(null);// 航班号
				queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
				queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
				queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
				queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
				//queryDto.setPricingEntryName(null);// 计价名称
				queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
				queryDto.setFeeRate(bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000)));
				subDto.setSubType(bean.getVirtualCode());
				subDto.setOriginnalCost(bean.getInsuranceAmount());
				subDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);
				subDtos.add(subDto);
				queryDto.setPriceEntities(subDtos);
				queryDto.setBillTime(bean.getBillTime());
				queryDto.setWaybillNo(bean.getWaybillNo());
				gDto =waybillService.getProductPriceDtoOfWVHAndBF(queryDto);
				if(gDto==null){
					queryDto.setReceiveDate(null);
					gDto=waybillService.getProductPriceDtoOfWVHAndBF(queryDto);
				}
				if(gDto !=null && gDto.getMinFeeRate() != null && gDto.getMaxFeeRate() != null){
					//保价费率是否可修改
					bean.setCanmodify(gDto.getCanmodify());
					/**
					 * （FOSS20150818）RFOSS2015052602 保价阶梯费率
					 * @author foss-206860
					 * 
					 * */
					//默认保价费率
					BigDecimal feeRate = Common.nullBigDecimalToZero(gDto.getActualFeeRate()).setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP);
					//实际保价费率
					if(bean.getInsuranceRate() != null
							&& bean.getMinFeeRate() != null
							&& bean.getMaxFeeRate() != null
							&& bean.getInsuranceRate().compareTo(BigDecimal.ZERO) != 0
							&& gDto.getMinFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP).compareTo(bean.getMinFeeRate()) == 0
							&& gDto.getMaxFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP).compareTo(bean.getMaxFeeRate()) == 0
							&& CollectionUtils.isEmpty(gDto.getDiscountPrograms())){
						bean.setInsuranceRate(bean.getInsuranceRate());
					}else{
						bean.setInsuranceRate(feeRate.setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(NumberConstants.NUMBER_1000)));
					}
					//当折扣保价费率不等于0且折扣保价费率与默认保价费率不相等时，做默认保价费率处理和费率区间处理
					if(PriceEntityConstants.PRICING_CODE_BF.equals(gDto.getPriceEntryCode()) 
							&& CollectionUtils.isNotEmpty(gDto.getDiscountPrograms())){
						//合同客户：折后高于该段保价费率最低值则显示【该段保价费率最低值，折后保价费率】；折后低于或等于该段保价费率最低值则直接显示折后保价费率。
						if(feeRate.compareTo(gDto.getMinFeeRate()) > 0){
							bean.setMinFeeRate(Common.nullBigDecimalToZero(gDto.getMinFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP)));
							bean.setMaxFeeRate(Common.nullBigDecimalToZero(feeRate));
						}else{
							bean.setMaxFeeRate(Common.nullBigDecimalToZero(feeRate));
							bean.setMinFeeRate(Common.nullBigDecimalToZero(feeRate));
						}
					}else{
						//最低保价费率
						bean.setMinFeeRate(gDto.getMinFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP));
						//最高保价费率
						bean.setMaxFeeRate(gDto.getMaxFeeRate().setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP));
					}
					String insuranceRateRange = "["+bean.getMinFeeRate()+","+bean.getMaxFeeRate()+"]";
					bean.setInsuranceRateRange(insuranceRateRange);
					bean.setInsuranceRateRangeCanvas(insuranceRateRange);
					if(StringUtil.isNotEmpty(bean.getIsUpdateDeliveryCustomer()) && FossConstants.YES.equals(bean.getIsUpdateDeliveryCustomer())){
						if(FossConstants.YES.equals(gDto.getCanmodify())){
							yOrN=FossConstants.YES;
						}
					}
				}else{
					throw new WaybillValidateException(i18n.get("foss.pkp.changing.itserivce.notconfig.default.insurance"));
				}
//			else{
//				BigDecimal insuranceRate = ui.getOriginWaybill().getWaybillDto().getWaybillEntity().getInsuranceRate();
//				//默认保价费率
//				if(insuranceRate!=null){
//					bean.setInsuranceRate(insuranceRate.multiply(new BigDecimal(1000)));
//				}
//				//将保费费率置空
//				bean.setMaxFeeRate(null);
//				bean.setMinFeeRate(null);
//				bean.setInsuranceRateRange(null);
//			}
		}
		return yOrN;
	}
	
	/**
	 * FOSS20150818）RFOSS2015052602-梯度保价
	 * @author foss-206860
	 * 校验是否修改的客户编码 
	 * */
	public static String  validateDeliveryCustomer(String deliveryCustomerCode,WaybillRFCUI ui){
		String isUpdateDeliveryCustomer = FossConstants.NO;
		WaybillInfoVo originWaybill = ui.getOriginWaybill();
		if(StringUtil.isNotEmpty(originWaybill.getWaybillDto().getWaybillEntity().getDeliveryCustomerCode()) && StringUtil.isNotEmpty(deliveryCustomerCode)){
			if(originWaybill.getWaybillDto().getWaybillEntity().getDeliveryCustomerCode().equals(deliveryCustomerCode)){
				isUpdateDeliveryCustomer = FossConstants.NO;
			}else{
				isUpdateDeliveryCustomer = FossConstants.YES;
			}
		}else{
			isUpdateDeliveryCustomer = FossConstants.NO;
		}
		return isUpdateDeliveryCustomer;
	}
	
	//专门作为保价费率的监听
	public static void getInsuranceRateListener(WaybillPanelVo bean) {
		bean.setCalDiscount(true);
		
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		GuiQueryBillCalculateSubDto  subDto = new GuiQueryBillCalculateSubDto();
		List<GuiQueryBillCalculateSubDto> subDtos = new ArrayList <GuiQueryBillCalculateSubDto>();
		GuiResultBillCalculateDto gDto=null;
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		//queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setFeeRate(bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000)));
		subDto.setSubType(bean.getVirtualCode());
		subDto.setOriginnalCost(bean.getInsuranceAmount());
		subDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);
		subDtos.add(subDto);
		queryDto.setPriceEntities(subDtos);
		queryDto.setBillTime(bean.getBillTime());
		queryDto.setWaybillNo(bean.getWaybillNo());
		gDto =waybillService.getProductPriceDtoOfWVHAndBF(queryDto);
		if(gDto==null){
			queryDto.setReceiveDate(null);
			gDto=waybillService.getProductPriceDtoOfWVHAndBF(queryDto);
		}
		
		bean.setInsuranceFee(gDto.getCaculateFee());
		
		//重新计算总费用
		CalculateFeeTotalUtils.resetCalculateFee(bean);
		
		bean.setCalDiscount(false);
	}
	
	/**
	 * 
	 * 获取返货起始网点
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:35:36
	 */
	public static String getRtnStartOrgCode(WaybillInfoVo bean) {
		//货物状态
		DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
		if(goodsStatus == null){
			return null;
		}
		//返货起始网点默认为原目的站
		String rtnStartOrgCode = bean.getCustomerPickupOrgCode() == null ? null: bean.getCustomerPickupOrgCode().getCode();
		rtnStartOrgCode = waybillService.queryStationDeliverOrgCode(rtnStartOrgCode);

		String inventory = goodsStatus.getValueCode();
		//未出第一外场返货从第一外场开始
		if (WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)
				|| WaybillRfcConstants.RECEIVE_STOCK_OUT.equals(inventory)
				|| (WaybillRfcConstants.TRANSFER_STOCK.equals(inventory) && 
				bean.getLoadOrgCode().equals(bean.getStockStatus().getCurrentStockOrgCode()))) {
			rtnStartOrgCode = waybillService.queryStationDeliverOrgCode(bean.getLoadOrgCode());
		}
		
		// 原运输性质
		String procuctCode = bean.getProductCode().getCode();
		
		//已出最终配载且是空运、偏线运输性质
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(procuctCode) 
				|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(procuctCode)) {
			String dept=null;
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(procuctCode)){
				dept =waybillService.queryTransferCenterByAirDispatchCode(bean.getLastLoadOrgCode());
			}
			if(dept==null){
				rtnStartOrgCode = waybillService.queryStationDeliverOrgCode(bean.getLastLoadOrgCode());
			}else{
				rtnStartOrgCode = waybillService.queryStationDeliverOrgCode(dept);
			}
			if (WaybillRfcConstants.DELIVERY_STOCK_OUT.equals(inventory)) {
				//若货物已出原最终配载部门库存，转运运输性质只能为偏线或空运
				bean.setRtnNeedFilter(true);
			}
		} 
//		rtnStartOrgCode = bean.getCreateOrgCode();
		
		return rtnStartOrgCode;
	}

	/**
	 * 
	 * 获取转运起始网点
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:35:59
	 */
	public static String getTfrStartOrgCode(WaybillInfoVo bean) {
		//货物状态
		DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
		if(goodsStatus == null){
			return null;
		}
		
	String 	tfrStartOrgCode = waybillService.queryStationDeliverOrgCode(bean.getLastLoadOrgCode());
		
		String inventory = goodsStatus.getValueCode();
		// 原运输性质
		String procuctCode = bean.getProductCode().getCode();

		//已出最终配载且是空运、偏线运输性质
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(procuctCode) 
				|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(procuctCode)) {
			String dept=null;
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(procuctCode)){
				dept =waybillService.queryTransferCenterByAirDispatchCode(bean.getLastLoadOrgCode());
			}
			if(dept!=null){
				tfrStartOrgCode = waybillService.queryStationDeliverOrgCode(dept);
			}else{
				tfrStartOrgCode = waybillService.queryStationDeliverOrgCode(bean.getLastLoadOrgCode());
			}
			if (WaybillRfcConstants.DELIVERY_STOCK_OUT.equals(inventory)) {
				//若货物已出原最终配载部门库存，转运运输性质只能为偏线或空运
				bean.setTfrNeedFilter(true);
			} 
		}
//		tfrStartOrgCode = bean.getCreateOrgCode();
		return tfrStartOrgCode;
	}
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 有事后折阶梯折扣合同不允许开装卸费
	 */
	public static boolean iSLtDiscountBackInfo(WaybillPanelVo bean) {
		List<CusLtDiscountItemDto> list = waybillService.queryLtDiscountBackInfo(bean.getDeliveryCustomerCode(), bean.getBillTime());
		if(null ==list || list.size()==0) {
			return true;
		}
		return false;
	}

	public static void modifyspecialPickmodel(DefaultComboBoxModel pickModel) {
		List<DataDictionaryValueEntity> list = waybillService
				.querySpecialPickUp();
		
			for (DataDictionaryValueEntity dataDictionary : list) {
				//合伙人开的单 去除内部带货自提、送货上楼安装（家居）,内部带货送货 -- sangwenhao
				if(BZPartnersJudge.IS_PARTENER && (WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode()) 
					    ||WaybillConstants.DELIVER_INNER_PICKUP.equals(dataDictionary.getValueCode())
					    ||WaybillConstants.DELIVER_FLOOR.equals(dataDictionary.getValueCode()))){
					continue ;
				}
				
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				pickModel.addElement(vo);
		
		}
		
	}
	/**
	 * 查询订单类型
	 * @author 367726-wamgweiliu-2016-08-25
	 * @param orderNo
	 * @return
	 */
	public static String getServiceType(String orderNo) {
		if(orderNo != null  && !"".equals(orderNo)){
			 return waybillService.queryOrderByOrderNo(orderNo);
		}
		return null;
	}
	
	/** 
	 * 	DP348757 
	 * 	20161206 增  
	 * 	需求编号：DN201611010025
	 * 	零担更改单优化-选择‘客户要求’或者‘内部要求’作更改时：如果提货网点不支持返单则置灰返单类别下拉选择框，使其不可操作。
	 */
	public static void setReturnBillTypeUnableSelect(WaybillRFCUI rfcUI){
		SaleDepartmentEntity saleDeprtTemp =waybillService.querySaleDepartmentByCode(rfcUI.getBinderWaybill().getCustomerPickupOrgCode().getCode());
		if(null!= saleDeprtTemp &&FossConstants.NO.equals(saleDeprtTemp.getCanReturnSignBill())){
			rfcUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(false);
		}
	}

}