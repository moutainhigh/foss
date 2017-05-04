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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/CalculateAction.java
 * 
 * FILE NAME        	: CalculateAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.*;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IAddedFeeCalculateService;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.AddedFeeCalculateService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.utils.*;
import com.deppon.foss.module.pickup.common.client.vo.*;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.common.WaybillDtoFactory;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillBindingListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.editui.ConcessionsPanel.WaybillDiscountCanvas;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.creating.client.ui.print.CustomerCouponDialog;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.WaybillDescriptor;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.*;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ICustomerHessianRemoting;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.deppon.foss.base.util.define.NumberConstants.NUMBER_2000;

/**
 * 
 * 运单CalculateAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,
 * date:2012-10-17 上午11:16:43,
 * </p>
 * 
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class CalculateAction extends AbstractButtonActionListener<WaybillEditUI> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(CalculateAction.class);
	
	private static final String TYPE_CODE = "YHJ";

	private static final double TWOPOINTFIVE =  2.5;

	private static final double ZEROPONTZEROONE = 0.01;

	private static final  int NUM_10000 = 10000;

	//clob通过to_char后最多存储的字符不能超过1300个
	private static final int CLOB_LIMIT = 1337;

	// 日志对象
	protected final static Logger LOG = LoggerFactory.getLogger(AbstractButtonActionListener.class.getName());

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 参数配置
	 */
//	private IConfigurationParamsService configurationParamsService =  GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);


	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 运单基础资料服务
	 */
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
	/**
	 * 合伙人加收方案计算service
	 */
	private IAddedFeeCalculateService addedFeeCalculateService = GuiceContextFactroy.getInjector().getInstance(AddedFeeCalculateService.class);
	private ICustomerHessianRemoting customerHessianRemoting;
	
	//运单UI对象
	WaybillEditUI ui;
	
	
	/**
	 * 
	 * <p>
	 * (计算总费用)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//通过运单UI对象获取绑定的VO对象
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		if(bean == null){
			MsgBox.showInfo("运单信息为空！");
			return ;
		}
		//如果发货客户编码不为空 ，我们将发货客户编码保存起来
		if(StringUtil.isNotBlank(bean.getDeliveryCustomerCode())){
			bean.setOldDeliveryCustomerCode(bean.getDeliveryCustomerCode());
		}
		//校验发货联系人和收货联系人不能过长
		String deliveryCustomerContact = bean.getDeliveryCustomerContact();
		String receiveCustomerContact = bean.getReceiveCustomerContact();
		if(deliveryCustomerContact!=null && deliveryCustomerContact.length()>= NumberConstants.NUMBER_80){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.deliveryCustomerContactTolong"));
			return ;
		}
		if(receiveCustomerContact!=null && receiveCustomerContact.length()>= NumberConstants.NUMBER_80){
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.receiveCustomerContactTolong"));
			return ;
		}
		//基本信息验证，如果存在一个不满足条件的情况，则不进行运费计算
		List<ValidationError> errorList = waybillBinder.validate();
		//用来标记是否有异常
		Boolean bool = false;
		if (errorList != null) {
			bool = Common.validateDescriptor(errorList);
		}
		// 如果descrptor校验通过则执行下面的代码
		if (bool) {
			try {
				//伙伴开单的时候把字段置为true
				if(BZPartnersJudge.IS_PARTENER){
					bean.setPartnerBilling(true);					
				}else{
					bean.setPartnerBilling(false);
				}
				
				bean.setPtpWaybillOrgVo(PtpWaybillOrgVo.init());
				//这个类直营开单也需要初始化 2016年3月17日 11:08:01 葛亮亮
				//如果在客户圈 ，将客户编码设置为主客户编码
				if( 	
						StringUtil.isNotBlank(bean.getIsCustCircle()) && 
						StringUtil.equals("Y", bean.getIsCustCircle()) 
								){
					bean.setDeliveryCustomerCode(bean.getCustomerCircleEntity().getCustCode());
				}
				// 基本校验
				validate(bean);
				//验证开月结时额度是否够用
				//validatePaidMethod(bean);
				//如果在客户圈 ，将客户编码设置为主客户编码
				if( 	
						StringUtil.isNotBlank(bean.getIsCustCircle()) && 
						StringUtil.equals("Y", bean.getIsCustCircle()) 
								){
					bean.setDeliveryCustomerCode(bean.getCustomerCircleEntity().getCustCode());
				}
				// 清理所有费用相关的信息
				cleanFee(bean);
				// 判断是否内部带货:如果内部带货，不能计算优惠券
				if (!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
						&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
					/**
					 * FOSS20150818）RFOSS2015052602-梯度保价
					 * 添加取消计算保价折扣的标识符
					 * @author foss-206860
					 * */
					bean.setCalDiscount(true);
					// 计算各种费用
					calculateFee(bean, false);
					bean.setCalDiscount(false);
					bean.setTempTransportFee(bean.getTransportFee());
					/**
					 * 如果有优惠券编号，
					 * 需要计算两次总运费：原因是，
					 * 优惠券要求把总运费传到CRM进行校验
					 * 
					 * 不是整车才处理优惠券，因为整车没有走货路径，获取最终配载部门时会报异常	
					 */
					if (!StringUtils.isEmpty(bean.getPromotionsCode()) 
							&& !bean.getIsWholeVehicle()) {
						// 处理优惠券
						/**
						 * 内部发货不使用优惠券
						 * dp-foss-dongjialing
						 * 225131
						 */
						if(bean.getInternalDeliveryType()==null || StringUtil.isBlank(bean.getInternalDeliveryType().getValueCode())
								|| StringUtil.isBlank(bean.getEmployeeNo())){
							executeCoupon(bean);
						}
					}
					/**
					 * 设置优惠总费用
					 */
					calcaulatePromotionsFee(bean);
					// 需要重新计算运费
					CalculateFeeTotalUtils.calculateTotalFee(bean,true);					
					
					//判断：若提货方式为送货进仓时，若为月结客户则送货费可编辑
					String code = bean.getReceiveMethod().getValueCode();
					//zxy 20131017 BUG-57549 start 修改：增加bean.getChargeMode() != null 判断
					if(WaybillConstants.DELIVER_STORAGE.equals(code) 
							&& (bean.getChargeMode() != null && bean.getChargeMode())){
						ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);
					}
					//zxy 20131017 BUG-57549 end 修改：增加bean.getChargeMode() != null 判断

					//判断是否有手动修改过
					if(CommonUtils.defaultIfNull(bean.getHandDeliveryFee()).compareTo(BigDecimal.valueOf(0)) != 0){
						//有手动修改过送货费，则重新走一次手动修改送货费后所走的业务逻辑
						WaybillBindingListener listener = new WaybillBindingListener(ui);
						listener.deliveryGoodsFeeListener(bean);
					}
					
					//计算了总运费
					bean.setFlagCalFee(FossConstants.YES);
					
					//将其他费用分类归集
					collectionForOtherFee(bean);
					
					// 处理增值优惠券费用							
					Common.offsetCouponFee(ui,bean);
					
					//在优惠券冲减完成后将最终的优惠券金额进行保存  邹胜利 2016年9月24日09:44:32
					bean.setFossToPtpCouponFree(bean.getCouponFree());
					
					// 处理完优惠券清空优惠券费用，防止再次冲减
					CalculateFeeTotalUtils.cleanCouponFree(bean);
					
					//验证开月结时额度是否够用  
					//cubc要求注释掉此处代码
//					validatePaidMethod(bean);
				}else
				{
					// 内部带货金额清零
					Common.resetZero(bean, ui);

				}
				//DEFECT-6639 大票货开单勾选异常
				valideteBigTick(bean);
				
				/**
				 * 判断到达网点是否二级网点以及网点模式，如果为二级网点，且为到达模式、简装模式、代理模式，则根据目的站网点查询加收方案计算加收费用
				 * 20160902 add by xingjun
				 */
				addedFeeCalculateService.guiWaybillCreate(bean);
				
				//页面其他费用
				JXTable otherTable = ui.incrementPanel.getTblOther();
				WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
				List<OtherChargeVo> voList = model.getData();
				//保存计算前的费用
				CommoForFeeUtils.keepStandardFee(voList, bean);
				
				//放在处理费用之后，防止异常时提交按钮没有控制好
				Common.setSaveAndSubmitTrue(ui);
				/**
				 * 家装项目三种情况不能提交
				 * 1到付
				 * 2签收单
				 * 3代收货款
				 * foss-254615-mabinliang
				 */
				if(bean!=null 
						&& bean.getSpecialValueAddedServiceType()!=null 
						&& StringUtils.isNotEmpty(bean.getSpecialValueAddedServiceType().getValueCode()) ){
					    if(bean.getPaidMethod().getValueCode().equals(WaybillConstants.ARRIVE_PAYMENT)
							||bean.getCodAmount().compareTo(BigDecimal.ZERO) > 0
							||bean.getReturnBillType().getValueCode().equals(WaybillConstants.RETURNBILLTYPE_ORIGINAL)
							||bean.getReturnBillType().getValueCode().equals(WaybillConstants.RETURNBILLTYPE_FAX)){
						ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
					}
				}
				//PDA导入开单时，只能提交，不能暂存
				
				if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean.getWaybillstatus())) {
					//不可编辑
					ui.buttonPanel.getBtnSave().setEnabled(false);
					//非PDA导入开单，提示是否展会货
				} else	{
					boolean isExhibitCargo = ui.basicPanel.getChbExhibitCargo().isSelected();
					//展会货复选框没有勾选，则进行匹配
					if(isExhibitCargo==false) {
						if(queryExhibitionByKeyWord(bean).compareTo(BigDecimal.ZERO)>0) {
							//匹配到则自动勾选
							ui.basicPanel.getChbExhibitCargo().setSelected(true);
						}
					}
				}	
				
				//合伙人项目需求
				if( BZPartnersJudge.IS_PARTENER){
					//合作人 项目 费用字段可编辑
					partnerControlEdit(bean) ;
					
				}
				//将从客户编码设置回去
				bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
			} catch (BusinessException w) {
//				if (StringUtils.isNotEmpty(w.getMessage())) {
//					MsgBox.showInfo(MessageI18nUtil.getMessage(w, i18n));
//				}else if(StringUtils.isNotEmpty(w.getErrorCode())){
//				    MsgBox.showInfo(w.getErrorCode());
//				}
				//发生业务异常，需要给发货客户的客户编码重新赋值
				bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
				if (StringUtils.isNotEmpty(w.getMessage())) {
					//在第二次计算总运费的时候抛错
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);
					if("没有找到运费!".equals(w.getMessage())){
						MsgBox.showITServiceInfo(MessageI18nUtil.getMessage(w, i18n));
					}else if(w.getMessage()!=null && w.getMessage().indexOf("没有配置系统参数:")>=0){
						MsgBox.showITServiceInfo(w.getMessage());
					}else{
						MsgBox.showInfo(MessageI18nUtil.getMessage(w, i18n));
					}					
				}else if(StringUtils.isNotEmpty(w.getErrorCode())){
					if("没有找到运费!".equals(w.getErrorCode())){
						MsgBox.showITServiceInfo(w.getErrorCode());
					}else{
						MsgBox.showInfo(w.getErrorCode());
					}				    
				}
			}catch(Exception ee){
				//发生业务异常，需要给发货客户的客户编码重新赋值
				bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
				String message=ee.getMessage();
				if("java.lang.reflect.UndeclaredThrowableException".equals(message)){
					MsgBox.showInfo("找不到对应价格,请配置");
				}else{
					MsgBox.showInfo(message);
				}	
			}
			
		 }
		
	} 
	
	//设置文本框可编辑（合伙人需求）
	private void partnerControlEdit(WaybillPanelVo bean){
		//公布价运费
		ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEditable(true);
		ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEnabled(true);
//		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);// 包装费
		//滕超 合伙人开单送货费不可修改 2016年1月19日 09:56:13 葛亮亮
		ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);// 送货费
		ui.billingPayPanel.getTxtDeliveryCharge().setEditable(false);// 送货费
		//腾超合伙人需求 空运除送货进仓无法修改送货费，其他送货方式可修改送货费 2016-10-24 09:57:01 邹胜利
		if(WaybillConstants.AIR_FLIGHT.equals(bean.getProductCode().getCode())){
			ui.billingPayPanel.getTxtDeliveryCharge().setEditable(true);// 送货费
		}
		//前提为勾选上门接货 2016年1月13日 11:27:59 葛亮亮
		ui.billingPayPanel.getTxtSupportFee().setEnabled(true);//保价费
		ui.billingPayPanel.getTxtCollectingFee().setEnabled(true);//代收手续费
		
		//2016年1月14日 17:04:21 葛亮亮 当计算的包装费大于0时包装费可编辑
		if(bean.getPackageFee() != null && bean.getPackageFee().compareTo(BigDecimal.ZERO) > 0){
		  ui.billingPayPanel.getTxtPackCharge().setEditable(true);
		  ui.billingPayPanel.getTxtPackCharge().setEnabled(true);
		}else{
		  ui.billingPayPanel.getTxtPackCharge().setEditable(false);
		  ui.billingPayPanel.getTxtPackCharge().setEnabled(false);
		}
	}
		
	//DEFECT-6639 大票货开单勾选异常
	private void valideteBigTick(WaybillPanelVo bean){
		if (bean.getGoodsVolumeTotal() != null && !"0".equals(bean.getGoodsVolumeTotal().toString()) && bean.getGoodsWeightTotal() != null && !"0".equals(bean.getGoodsWeightTotal().toString())) {
//			Boolean falg = CommonUtils.isHeavyWeight(bean);
//			if(!falg){
//				if(isOverweightFee()){
//					bean.setBigTicket(false);
//				}else{
//					if(!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
//						BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
//						BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
//						if(goodsWeightTotal.compareTo(new BigDecimal(500)) > 0 || goodsVolumeTotal.compareTo(new BigDecimal(2.5)) > 0){
//							bean.setBigTicket(true);
//						}else{
//							bean.setBigTicket(false);
//						}
//					}else{
//						bean.setBigTicket(false);
//					}
//				}
//				
//			}else{
//				bean.setBigTicket(false);
//			}
			/**
			 * DMANA-11054
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-08 下午15:14
			 */
			BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
			BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
			if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0 || goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) > 0){
				//合伙人的单子大票货置为false
				if(bean.getPartnerBilling()){
					bean.setBigTicket(false);
				}else{
					bean.setBigTicket(true);
				}
			}else{
				bean.setBigTicket(false);
			}
		}
	}
	
	/**
	 * 超重对内备注
	 * @param bean
	 */
	private void setInnerNotes(WaybillPanelVo bean){
		//对内备注
		String innerNs=bean.getInnerNotes();
		//储运事项
		String cysx= bean.getTransportationRemark();
		String[] strs=null;
		String[] cysxStrs=null;
		if(StringUtil.isNotEmpty(innerNs)){
		   strs = innerNs.split(";");
		}
		if(StringUtil.isNotEmpty(cysx)){
			cysxStrs = cysx.split(";");
		}
		
		ProductEntityVo pc =bean.getProductCode();
		if(pc!=null && !"AF".equals(pc.getCode())){
			if(bean.getGoodsWeightTotal()==null
					||
				   bean.getGoodsQtyTotal()==null){
					return;
				}
				BigDecimal zzl=bean.getGoodsWeightTotal();
				int zjs=bean.getGoodsQtyTotal();
				if(zjs==0){
					return;
				}
				
				BigDecimal cz=zzl.divide(new BigDecimal(zjs),1,BigDecimal.ROUND_HALF_UP);
				
				if(cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0){
					if(strs!=null){
						StringBuilder newInnerNotes = new StringBuilder();
						for(String str:strs){
							if(str.equals("超重货")){
								continue;
							 }
							newInnerNotes.append(str).append(";");
						}
						 newInnerNotes.append("超重货").append(";");
					   bean.setInnerNotes(newInnerNotes.toString());
						 
					}else{
						bean.setInnerNotes("超重货;");
					}
					
					if(cysxStrs!=null){
						StringBuilder remark = new StringBuilder();
						for(String str:cysxStrs){
							if(str.equals("超重货")){
								continue;
							 }
							remark.append(str).append(";");
						}
						remark.append("超重货").append(";");
					   bean.setTransportationRemark(remark.toString());
					}else{
						bean.setTransportationRemark("超重货;");
					}
						
				}else{
					if(strs!=null){
						StringBuilder innerNs1 = new StringBuilder();
						for(String str :strs){
							if(str.equals("超重货")){
								continue;
							}
							innerNs1.append(str).append(";");
						}
					  bean.setInnerNotes(innerNs1.toString());
					}
					
					if(cysxStrs!=null){
						StringBuilder cysx1 = new StringBuilder();
						for(String str :cysxStrs){
							if(str.equals("超重货")){
								continue;
							}
							cysx1.append(str).append(";");
						}
					  bean.setTransportationRemark(cysx1.toString());
					}
				}
		}else{
			
			if(strs!=null){
				StringBuilder innerNs2 = new StringBuilder();
				for(String str :strs){
					if(str.equals("超重货")){
						continue;
					}
				   innerNs2.append(str).append(";");
				}
			  bean.setInnerNotes(innerNs2.toString());
			}
			
			if(cysxStrs!=null){
				StringBuilder cysx2 = new StringBuilder();
				for(String str :cysxStrs){
					if(str.equals("超重货")){
						continue;
					}
					cysx2.append(str).append(";");
				}
			  bean.setTransportationRemark(cysx2.toString());
			}
		}
	}
	
	private void otherFeeCaozhaoFee(WaybillPanelVo bean) {
		// 总件数
		int zjs=bean.getGoodsQtyTotal();
		//总总量
		BigDecimal zzl=bean.getGoodsWeightTotal();
		//平均重量
		BigDecimal cz=zzl.divide(new BigDecimal(zjs),1,BigDecimal.ROUND_HALF_UP);
		//获取其他费用
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		boolean flag=true;
		if(null!=data){
			for(int i=0 ; i<data.size();i++){
				OtherChargeVo oth=data.get(i);
				if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(oth.getCode())
				  ||
				  PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(oth.getCode())){
					
					if(cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0
					    &&
					   PriceEntityConstants.QT_CODE_CZHCZFWF.equals(oth.getCode())
					   ){
						data.remove(oth);
					}
					flag=false;
					break;
					
				}
			}
		}
		//获取其他费用集合
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(CommonUtils.getQueryOtherChargeParam(bean));
		//超重费折扣
		//DEFECT-5166 : 059387登陆GUI，选择门到门，发货客户选择月结客户40000473发货，计算总运费，详细计价信息里有2条相同的优惠（超重货操作服务费）
		//修复pop-514 开单-FOSS开单超重货操作服务费调用直接金额的规则引擎时，显示优惠了两次
		if(StringUtil.isNotEmpty(bean.getStartContractOrgCode())){
			if(CollectionUtils.isNotEmpty(list)&& cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0){
				//设置其他费用的折扣优惠
				for (ValueAddDto valueAddDto : list) {
					//是否为其他费用
					if (PriceEntityConstants.QT_CODE_CZHCZFWF.equals(valueAddDto.getSubType())) {
						Common.setFavorableQSDiscount(valueAddDto.getDiscountPrograms(), ui, bean);
						break;
					}
				}
			}
		}
		OtherChargeVo otherCzf=null;
		if(cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0 && flag){
			
			otherCzf = CommonUtils.getOtherChargeListCZFY(list);
			
		}
		
		if(CollectionUtils.isEmpty(data)){
			data=new ArrayList<OtherChargeVo>();
		}
         if(null!=otherCzf && StringUtils.isNotEmpty(otherCzf.getId())){
        	 data.add(otherCzf);
         }   
		ui.incrementPanel.setChangeDetail(data);
		CommonUtils.otherSum(data, bean);
		
		

		
	}

	/**
	 * 验证付款方式
	 * @author WangQianJin
	 * @date 2013-8-10 下午6:54:09
	 */
	private void validatePaidMethod(WaybillPanelVo bean){
		/**
         * 	若为月结合同，则结算方式可以选择月结，注明校验适用营业部时按照主客户合同的适用部门进行校验（主客户月结合同的归属部门和绑定部门）；
         * 	如果当前不存在有效合同或者合同不是月结合同，则结算方式不能选择月结；
         * 	如果主客户当前有效合同为统一结算，则开单选择结款方式只能为月结和到付；
		 */
		if( 	
				StringUtil.isNotBlank(bean.getIsCustCircle()) && 
				StringUtil.equals("Y", bean.getIsCustCircle()) && 
				bean.getCustomerCircleEntity() != null &&
						bean.getCusBargainNewEntity() != null  &&
								bean.getCustomerNewEntity() !=null
						){
			//获取 月结方式 
			String chargeType=bean.getCusBargainNewEntity().getChargeType();
			//如果当前不存在有效合同或者合同不是月结合同，则结算方式不能选择月结；
			if( (StringUtils.isBlank(chargeType) || StringUtils.equals("NOT_MONTH_END", chargeType))){
				if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					// 将暂存提交按钮设置为不可编辑
					Common.setSaveAndSubmitFalse(ui);
				}
			}
		}
		// 判断是否可以开月结
		/*if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {			
			DebitDto dto = waybillService.isBeBebt(bean.getDeliveryCustomerCode(), bean.getReceiveOrgCode(), WaybillConstants.MONTH_PAYMENT, bean.getPrePayAmount());
			if (!dto.isBeBebt()) {
				if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
					String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
					String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
					if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
							&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
						ui.incrementPanel.getBtnCalculate().setEnabled(true);
						ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
						ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
						ui.incrementPanel.getJlable().setVisible(false);
						ui.incrementPanel.getCombServiceRate().setVisible(false);
					}else{
						ui.incrementPanel.getBtnCalculate().setEnabled(false);
						ui.billingPayPanel.getBtnSubmit().setEnabled(true);// 提交为不可编辑
						ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
						ui.incrementPanel.getJlable().setVisible(true);
						ui.incrementPanel.getCombServiceRate().setVisible(true);
					}

				}else{
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
					ui.buttonPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
				}
				throw new WaybillValidateException(dto.getMessage());
			}
		}*/
	}
	
	
	/**
	 * 将其他费用进行分类归集
	 * @author WangQianJin
	 * @date 2013-8-3 下午2:38:46
	 */
	private void collectionForOtherFee(WaybillPanelVo bean) {
		//获取其他费用
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//累计其他费用面板所有费用(因为当点击计算总运费以后，其他费用中已经去除各种特殊归集费用，要把特殊费用再次累加到其他费用，方便下一次归集)
		CommoForFeeUtils.otherPanelSumFee(data,bean);
		//归集费用
		CommoForFeeUtils.feeCollection(data,bean);
		//重新计算运费
		CalculateFeeTotalUtils.resetCalculateFee(bean);
	}

	/***
	 * 
	 * 清理所有与费用相关的信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午10:00:39
	 */
	private void cleanFee(WaybillPanelVo bean) {
		// 清理代收货款
		cleanCod(bean);
		// 清理保价
		cleanInsurance(bean);
		// 清理接货费
		cleanPickupFee(bean);
		// 清理木架信息
		cleanStandCharge(bean);
		// 清理木箱信息
		cleanBoxCharge(bean);
		// 清理产品信息
		cleanProductPrice(bean);
		// 清理送货费
		cleanDeliverCharge(bean);
		//公布价
		bean.setTransportFee(BigDecimal.ZERO);
		//公布价-画布
		bean.setTransportFeeCanvas(BigDecimal.ZERO.toString());
		//增值服务费
		bean.setValueAddFee(BigDecimal.ZERO);
		//优惠费用
		bean.setPromotionsFee(BigDecimal.ZERO);
		//优惠费用 -- 画布
		bean.setPromotionsFeeCanvas(BigDecimal.ZERO.toString());
		
		// 画布清理画布派送费
		ui.canvasContentPanel.getOtherCost().setChangeDetail(null);

		// 清理折扣优惠明细
		ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(null);
		
	}
	

	/**
	 * 
	 * 清理代收贷款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:27:39
	 * @param bean
	 */
	private void cleanCod(WaybillPanelVo bean) {
		// 代收货款费率
		bean.setCodRate(BigDecimal.ZERO);
		// 代收货款金额
		bean.setCodFee(BigDecimal.ZERO);
		// 代收货款编码
		bean.setCodCode("");
		// 代收货款ID
		bean.setCodId("");
	}

	/**
	 * 
	 * 清空保险费相关
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:26:16
	 */
	private void cleanInsurance(WaybillPanelVo bean) {
		// 保险声明值最大值
		bean.setMaxInsuranceAmount(BigDecimal.ZERO);
		// 保险费率
		//bean.setInsuranceRate(BigDecimal.ZERO);
		// 保险手续费
		bean.setInsuranceFee(BigDecimal.ZERO);
		// 保险费ID
		bean.setInsuranceId("");
		// 保险费CODE
		bean.setInsuranceCode("");
	}
	
	/**
	 * MANA-257接货费优化
	 * 
	 * 清空接货费
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-25 下午5:25:40
	 */
	private void cleanPickupFee(WaybillPanelVo bean){
		//接货费
		bean.setPickupFee(BigDecimal.ZERO);
		//接货费（画布）
		bean.setPickUpFeeCanvas("0");
		bean.setBasePickupFee(BigDecimal.ZERO);
		bean.setMaxPickupFee(BigDecimal.ZERO);
		bean.setMinPickupFee(BigDecimal.ZERO);
	}

	/**
	 * 
	 * 清理木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:01:37
	 */
	private void cleanStandCharge(WaybillPanelVo bean) {
		bean.setStandChargeId("");
		bean.setStandChargeCode("");
		// 清除木架费会导致计算总运费时不能正确减去原来的木架费
//		bean.setStandCharge(BigDecimal.ZERO);
	}

	/**
	 * 
	 * 清理木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:01:37
	 */
	private void cleanBoxCharge(WaybillPanelVo bean) {
		bean.setBoxChargeId("");
		bean.setBoxChargeCode("");
		// 清除木箱费会导致计算总运费时不能正确减去原来的木箱费
//		bean.setBoxCharge(BigDecimal.ZERO);
	}

	/**
	 * 
	 * 清理产品价格相关
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午10:55:13
	 */
	private void cleanProductPrice(WaybillPanelVo bean) {
		// 设置运费价格ID
		bean.setTransportFeeId("");
		// 设置运费价格CODE
		bean.setTransportFeeCode("");
		// 设置运费
		if (!bean.getIsWholeVehicle()) {
			bean.setTransportFee(BigDecimal.ZERO);
			// 画布公布价运费
			bean.setTransportFeeCanvas(BigDecimal.ZERO.toString());
		}
		// 设置费率
		bean.setUnitPrice(BigDecimal.ZERO);

		// 计费重量
		bean.setBillWeight(BigDecimal.ZERO);
	}

	/**
	 * 
	 * 清理送货费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午07:41:07
	 */
	private void cleanDeliverCharge(WaybillPanelVo bean) {
		//画布-送货费
		bean.setDeliveryGoodsFeeCanvas("0");
		//送货费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		//送货费集合
		bean.setDeliverList(null);
	}

	/**
	 * 计算优惠总费用
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:09:42
	 */
	private void calcaulatePromotionsFee(WaybillPanelVo bean) {
		JXTable tblConcessions = ui.getCanvasContentPanel().getConcessionsPanel().getTblConcessions();
		WaybillDiscountCanvas discountTableModel = ((WaybillDiscountCanvas) tblConcessions.getModel());
		List<WaybillDiscountVo> waybillDiscountVos = discountTableModel.getData();

		/**
		 * 如果优惠价格为空或者为0时
		 */
		if (bean.getCouponFree() != null && BigDecimal.ZERO.compareTo(bean.getCouponFree()) != 0) {
			if (waybillDiscountVos == null) {
				waybillDiscountVos = new ArrayList<WaybillDiscountVo>();

			}			
			String couponRankType = bean.getCouponRankType();
			//判断是否需要添加到优惠费用中
			if(CommonUtils.isAddPromotionsFeeByType(couponRankType)){
				/**
				 * MANA-1961 营销活动与优惠券编码关联
				 * 2014-04-10 026123
				 */
				WaybillDiscountVo waybillDiscountVo = new WaybillDiscountVo();	
				// 优惠折扣项目名称
				waybillDiscountVo.setFavorableItemName(CommonUtils.convertFeeToName(bean.getCouponRankType()));
				// 优惠折扣项目CODE
				waybillDiscountVo.setFavorableItemCode(CommonUtils.defaultIfNull(bean.getCouponRankType()));
				// 优惠类别名称
				waybillDiscountVo.setFavorableTypeName(i18n.get("foss.gui.creating.calculateAction.coupon"));
				// 优惠类别CODE
				waybillDiscountVo.setFavorableTypeCode(TYPE_CODE);
				waybillDiscountVo.setFavorableDiscount(BigDecimal.ZERO.toString());
				//针对运费-优惠金额 < 最低一票----重新设置优惠金额的值
				if(!FossConstants.YES.equals(bean.getFlagTakeCoupon()) 
						&& bean.getProductCode()!= null 
						&& !CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode()) ){
					//不为快递才判断
						BigDecimal	transportFee=bean.getTransportFee();
						if(transportFee != null){
							/**
							 * 判断最低一票是否为空，如果为空，则设置为0
							 */
							BigDecimal minTransportFee= bean.getMinTransportFee();
							if(minTransportFee==null){
								minTransportFee = BigDecimal.ZERO;
							}
							//hbhk 处理晚到补差价最低一票
							//运费-优惠金额 < 最低一票
							if(CustomerCouponDialog.customerCoupons.contains(bean.getPromotionsCode())){
								if((transportFee.subtract(bean.getCouponFree())).compareTo(minTransportFee) < 0){
									//优惠金额 = 运费 - 最低一票
									bean.setCouponFree(transportFee.subtract(minTransportFee));
								}
							}else{
							//RFOSS2015052801（DN201505260016）  取消优惠券最低一票校验  --206860
							if((transportFee.subtract(bean.getCouponFree())).compareTo(BigDecimal.ZERO) < 0){
								bean.setCouponFree(transportFee.subtract(BigDecimal.ZERO));
							}
						}
					}
				}
				waybillDiscountVo.setFavorableAmount(bean.getCouponFree().toString());
				// 折扣ID
				waybillDiscountVo.setDiscountId(bean.getPromotionsCode());
				// 类型 discount 为公布价折扣 promotion 为增值服务优惠
				waybillDiscountVo.setFavorableTypeCode(PricingConstants.VALUATION_TYPE_DISCOUNT);
				
				waybillDiscountVos.add(waybillDiscountVo);
			}			
		}

		BigDecimal totalPromotionsFee = BigDecimal.ZERO;
		if (waybillDiscountVos != null) {
			for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {				
				totalPromotionsFee = totalPromotionsFee.add(new BigDecimal(waybillDiscountVo.getFavorableAmount()));							
			}
		}
		/**
		 * 设置优惠费用
		 */
		bean.setPromotionsFee(totalPromotionsFee);
		/**
		 * 设置画布的优惠费用
		 */
		bean.setPromotionsFeeCanvas(totalPromotionsFee.toString());

		ui.getCanvasContentPanel().getConcessionsPanel().setChangeDetail(waybillDiscountVos);
	}

	/**
	 * 查询计算价格集合
	 * ISSUE-4391
     * @author	157229-zxy 
     * @date 2013-11-18
	 * @param bean
	 * @return
	 */
	private List<GuiResultBillCalculateDto> queryResultBillCalculate(WaybillPanelVo bean) {
		// 获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);

		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection
				.getPriceEntities();

		// 打木架/大木箱 计算信息收集
		List<GuiQueryBillCalculateSubDto> yokeChargeCollections = Common
				.getYokeChargeCollection(bean, ui);
		if (yokeChargeCollections != null && !yokeChargeCollections.isEmpty()) {
			priceEntities.addAll(yokeChargeCollections);//
		}

		// 获取保价费
		GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceCollection(bean);
		if (insuranceCollection != null) {
			priceEntities.add(insuranceCollection);// 加入增值服务
		}

		// 代收货款手续费
		GuiQueryBillCalculateSubDto codCollection = getCodCollection(bean);
		if (codCollection != null) {
			priceEntities.add(codCollection);// 代收货款手续费
		}

		// 送货费
		List<GuiQueryBillCalculateSubDto> deliveryFees = getDeliveryFeeCollection(bean);
		if (deliveryFees != null && !deliveryFees.isEmpty()) {
			priceEntities.addAll(deliveryFees);
		}

		// 其他费用
		GuiQueryBillCalculateSubDto otherChargeDataCollection = getOtherChargeDataCollection(bean);
		if (otherChargeDataCollection != null) {
			priceEntities.add(otherChargeDataCollection);// 代收货款手续费
		}

		productPriceDtoCollection.setPriceEntities(priceEntities);
		// 最低一票
//		BigDecimal minTransportFee = BigDecimal.ZERO;

		// 是否经济自提件
		productPriceDtoCollection.setEconomySince(BooleanConvertYesOrNo
				.booleanToString(bean.getIsEconomyGoods()));

		// 最终配载部门(计算偏线中转费时用得到)
		productPriceDtoCollection.setLastOrgCode(bean.getLastLoadOrgCode());

		// 统一返回的计价值
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = waybillService
				.queryGuiBillPrice(productPriceDtoCollection);

		return guiResultBillCalculateDtos;

	}

	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、
	 * 送货费）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	private void calculateFee(WaybillPanelVo bean, boolean needMinusCoupen) {
		//获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);
		productPriceDtoCollection.setWaybillNo(bean.getWaybillNo());//设置运单号
		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();
		//伙伴开单标记
		if(null!=bean.getPartnerBilling()&&bean.getPartnerBilling()){
			productPriceDtoCollection.setPartnerBillingLogo("Y");
		}else{
			productPriceDtoCollection.setPartnerBillingLogo("N");
		}
		productPriceDtoCollection.setIsExhibitCargo(bean.getIsExhibitCargo());
		// 打木架/大木箱 计算信息收集
		List<GuiQueryBillCalculateSubDto> yokeChargeCollections = Common.getYokeChargeCollection(bean, ui);
		if (yokeChargeCollections != null && !yokeChargeCollections.isEmpty()) {
			priceEntities.addAll(yokeChargeCollections);//
		}
		
		//获取保价费
		GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceCollection(bean);
		if (insuranceCollection != null) {
			priceEntities.add(insuranceCollection);//加入增值服务
		}

		//代收货款手续费
		GuiQueryBillCalculateSubDto codCollection = getCodCollection(bean);
		if (codCollection != null) {
			priceEntities.add(codCollection);//代收货款手续费
		}
		
		//送货费
		List<GuiQueryBillCalculateSubDto> deliveryFees = getDeliveryFeeCollection(bean);
		if (deliveryFees != null && !deliveryFees.isEmpty()) {
			priceEntities.addAll(deliveryFees);
		}

		//其他费用
		GuiQueryBillCalculateSubDto otherChargeDataCollection = getOtherChargeDataCollection(bean);
		if (otherChargeDataCollection != null) {
			priceEntities.add(otherChargeDataCollection);//代收货款手续费
		}

		productPriceDtoCollection.setPriceEntities(priceEntities);
		// 最低一票
		BigDecimal minTransportFee = BigDecimal.ZERO;
		
		// 是否经济自提件
		productPriceDtoCollection.setEconomySince(BooleanConvertYesOrNo.booleanToString(bean.getIsEconomyGoods()));
		
		//最终配载部门(计算偏线中转费时用得到)
		productPriceDtoCollection.setLastOrgCode(bean.getLastLoadOrgCode());
		
		//营销活动DTO
		productPriceDtoCollection.setActiveDto(bean.getActiveDto());
		
		//是否计算市场营销折扣
		productPriceDtoCollection.setCalActiveDiscount(bean.isCalActiveDiscount());
		
		//封装市场营销活动校验条件
		Common.settterActiveParamInfo(productPriceDtoCollection,bean);
		//封装内部发货条件
		/**
		 * 根据条件查询当前月份的优惠总额
		 */
		BigDecimal amount = null;
		if(StringUtil.isNotBlank(bean.getEmployeeNo())) {
			amount = WaybillServiceFactory.getWaybillService().queryDiscountFeeByEmployeeNo(bean.getEmployeeNo(), bean.getBillTime());
		}
		if(bean.getInternalDeliveryType() != null) {
			productPriceDtoCollection.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		productPriceDtoCollection.setEmployeeNo(bean.getEmployeeNo());
		productPriceDtoCollection.setAmount(amount);
		/**
		 * 封装梯度折扣条件
		 * dp-foss-dongjialing
		 * 225131
		 */
		if(StringUtil.isNotBlank(bean.getDeliveryCustomerCode())) {
			amount = WaybillServiceFactory.getWaybillService().queryTotalFeeByDelevyCode(bean.getDeliveryCustomerCode(),bean.getBillTime());
			productPriceDtoCollection.setTotalAmount(amount);
		}
		if(CommonContents.logToggle){
			logger.info("运单号："+bean.getWaybillNo()+" 计算总运费开始...");
			CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).jszyfBtnStart();
		}
		//统一返回的计价值
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = waybillService.queryGuiBillPrice(productPriceDtoCollection);
		if(CommonContents.logToggle){
			CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).jszyfBtnEnd();
			logger.info("运单号："+bean.getWaybillNo()+" 计算总运费结束...");
		}
		if(guiResultBillCalculateDtos == null){
			throw new WaybillValidateException("统一返回的计价值的实体为空！");
		}
		//降價返券需求：需要在提交的時候再次計算運費，顧將 产品价格主参数 放入bean中，以便計算運費
		bean.setProductPriceDtoCollection(productPriceDtoCollection);
		
		//设置计价信息
		if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){
			bean.setGuiResultBillCalculateDtos(guiResultBillCalculateDtos);
		}
		
		//如果返回的价格为空，抛出业务异常
		if(!bean.getIsWholeVehicle()){
			if (guiResultBillCalculateDtos == null || guiResultBillCalculateDtos.isEmpty()) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
			}
		}
		

		//是否整车
		if (!bean.getIsWholeVehicle()) {
			// 获取公布价运费
			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_FRT, null);
			GuiResultBillCalculateDto dtoJH= getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_JH, null);

			//设置公布价运费的费用--接送费
			setProductPriceDtoCollection(dto, bean,dtoJH);
			minTransportFee = dto.getMinFee();// 最低一票
			bean.setMinTransportFee(minTransportFee);
		} else {
			bean.setTransportFee(bean.getWholeVehicleActualfee());
			if(bean.getWholeVehicleActualfee()!=null){
				bean.setTransportFeeCanvas(bean.getWholeVehicleActualfee().toString());
			}	
		}
		
		//为了超重货操作服务费可以获取到折扣后金额，优先在其他费用版面加入超重操作服务费
		if(!"AF".equals(bean.getProductCode().getCode())){
			//超重费用
			otherFeeCaozhaoFee(bean);
		}
		// 计算增值服务费
		calculateValueAdd(bean, guiResultBillCalculateDtos);
		
		//规则引擎会对其他费用打折，重新再其他费用面板赋值--定价体系优化项目POP-432
		setterOtherFeeByRuleEngine(productPriceDtoCollection,guiResultBillCalculateDtos,bean);
		
		//推广活动会对其他费用打折，重新在其他费用面板赋值
		setterOtherFeeByMakActive(productPriceDtoCollection,guiResultBillCalculateDtos,bean);

		/**
		 * 计算总运费公共方法
		 */
		//POP-165 修复包装费计算错误 start
		if(guiResultBillCalculateDtos!=null){
			//获取计费类型
			bean.setCaculateType(guiResultBillCalculateDtos.get(0).getCaculateType());
			Common.getYokeCharge(bean, ui);
		}
		//POP-165 修复包装费计算错误end
		CalculateFeeTotalUtils.calculateFee(bean, false, needMinusCoupen);
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
			isBeBebt(bean);// 判断是否可以开临时欠款
		}
		// 设置预付到付金额编辑状态
		setPrePayArriveEditState(bean);

	}
	
	//规则引擎会对其他费用打折，重新再其他费用面板赋值--定价体系优化项目POP-432
	private void setterOtherFeeByRuleEngine(GuiQueryBillCalculateDto productPriceDtoCollection,
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,WaybillPanelVo bean){
		
		//获取面板上的其他费用信息
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if(CollectionUtils.isNotEmpty(guiResultBillCalculateDtos)){
			for (GuiResultBillCalculateDto guiResultBillCalculateDto : guiResultBillCalculateDtos) {
				//当类型为其他费用时，重新获取折扣后的费用金额
				if (PriceEntityConstants.PRICING_CODE_QT.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
					//获取折扣信息
					List<GuiResultDiscountDto> discountPrograms = guiResultBillCalculateDto.getDiscountPrograms();
					if(CollectionUtils.isNotEmpty(data)){
								for(OtherChargeVo vo:data){
									//当其他费用面板的code与其他费用子类型一直，才进行更新操作
									//修复pop-449 开单-FOSS开单超远派送加收费用计算错误
									//修复POP-492开单-FOSS开单手动添加的其他费用，计算总运费之后费用明细会改变
									if(StringUtil.equalsIgnoreCase(vo.getCode(),guiResultBillCalculateDto.getSubType())){
										if(PriceEntityConstants.PRICING_CODE_RYFJ.equals(vo.getCode())
												   || PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())
												   || PriceEntityConstants.PRICING_CODE_CY.equals(vo.getCode())
												   || PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())){
													BigDecimal money = guiResultBillCalculateDto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
													//原始默认费用
													BigDecimal oldMoney = new BigDecimal(vo.getMoney());
													if(money != null){
														//是否存在折扣
														Boolean islive = false;
														if(CollectionUtils.isNotEmpty(discountPrograms)){
															for (int i = 0; i < discountPrograms.size(); i++) {
																if(StringUtil.equalsIgnoreCase(vo.getCode(),discountPrograms.get(i).getSubType())){
																	islive = true;
																}
															}
														}
														//在没有折扣的情况下，做如下处理
														if(!islive){
															//其他费用--改大不改小--当原有值小于计算值时，以计算值为主
															if(oldMoney.compareTo(money) < 0){
																vo.setMoney(money.toString());
															}
														}else{
															vo.setMoney(money.toString());
														}
													}
												}
										}
								}
					
					}
				}
			}
		}
		//重新获取其他费用
		CommoForFeeUtils.otherPanelSumFee(data,bean);
	}
	
	/**
	 * 推广活动会对其他费用打折，重新在其他费用面板赋值
	 * @创建时间 2014-5-17 上午10:51:57   
	 * @创建人： WangQianJin
	 */
	private void setterOtherFeeByMakActive(GuiQueryBillCalculateDto productPriceDtoCollection,
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,WaybillPanelVo bean){
		//判断是否参加了推广活动
		if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){
			//推广活动折扣后的综合信息费
			BigDecimal zhxxDis=null;
			if(CollectionUtils.isNotEmpty(guiResultBillCalculateDtos)){
				for (GuiResultBillCalculateDto guiResultBillCalculateDto : guiResultBillCalculateDtos) {
					if (PriceEntityConstants.PRICING_CODE_QT.equals(guiResultBillCalculateDto.getPriceEntryCode()) 
							&& PriceEntityConstants.PRICING_CODE_ZHXX.equals(guiResultBillCalculateDto.getSubType())) {
						//获取折扣信息
						List<GuiResultDiscountDto> disList=guiResultBillCalculateDto.getDiscountPrograms();
						//是否推广活动折扣
						boolean isActDis=false;
						if(CollectionUtils.isNotEmpty(disList)){
							for(GuiResultDiscountDto dto:disList){
								if(dto!=null && DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE.equals(dto.getDiscountType())){
									isActDis=true;
									break;
								}
							}
						}
						if(isActDis){
							//获取折扣后的综合信息费四舍五入取整
							zhxxDis=guiResultBillCalculateDto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);	
						}					
					}					
				}
			}
			if(zhxxDis!=null){
				//获取其他费用
				JXTable otherTable = ui.incrementPanel.getTblOther();
				WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
				List<OtherChargeVo> data = model.getData();				
				if(CollectionUtils.isNotEmpty(data)){
					boolean flag=false;
					for(OtherChargeVo vo : data){
						if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())){
							vo.setMoney(zhxxDis.toString());
							flag=true;								
							break;							
						}
					}
					if(flag){						
						//重新获取其他费用
						CommoForFeeUtils.otherPanelSumFee(data,bean);
					}						
				}
			}
		}
	}

	/**
	 * 设置是否允许修改装卸费
	 * 
	 * @param bean
	 * @param transportFee
	 * @param minTransportFee
	 */
	private boolean setServiceChargeState(WaybillPanelVo bean) {
		// 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
		// （只限制配载类型为专线的，包括月结）；
		/**
		 * 9. 2012年12月1日 (以后)开业的部门不能开装卸费
		 * 10.
		 * 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		 * 11.是否可以开装卸费的依据取决于部门的业务属性（
		 * 即部门属性基础资料中增加是否可开装卸费的字段）。
		 * 12.装卸费上限由增值服务配置基础资料实现
		 * （在产品API中体现）。
		 */
		boolean serviceChargeEnabled = true;
		
		serviceChargeEnabled = departPropertyServiceFee(bean);
		
		//没有开装卸费的权限，因此开单部门也不能开装卸费-update by taodongguo(354805) - 2016-12-8 15:46:37
		if(!serviceChargeEnabled){
			ui.incrementPanel.getTxtServiceCharge().setEnabled(serviceChargeEnabled);
			bean.setServiceFee(BigDecimal.ZERO);
			bean.setServiceFeeCanvas("0");
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFee.noPermission"));
		}
		
		// 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
		if( serviceChargeEnabled) {
			serviceChargeEnabled = lowestServiceFee(bean);
		}
		// 2012年12月1日 (以后)开业的部门不能开装卸费
		// 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		// 月结
		if (serviceChargeEnabled) {
			serviceChargeEnabled = channelServiceFee(bean);
		}
		if (serviceChargeEnabled) {
			// 月发月送允许修改装卸费
			if (StringUtils.isNotEmpty(bean.getPreferentialType())) {
				if (bean.getPreferentialType()
						.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
					serviceChargeEnabled = true;
				}

			}
		}
		/**
		 * dp-foss-dongjialing
		 * 225131
		 * 有事后折阶梯折扣合同不允许开装卸费
		 */
		if(serviceChargeEnabled) {
			serviceChargeEnabled = iSLtDiscountBackInfo(bean);
		}
		ui.incrementPanel.getTxtServiceCharge().setEnabled(serviceChargeEnabled);
		if (!serviceChargeEnabled) {
			bean.setServiceFee(BigDecimal.ZERO);
			bean.setServiceFeeCanvas("0");
			/*MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.notbill"));*/
		}
		return serviceChargeEnabled;
	}
	
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 有事后折阶梯折扣合同不允许开装卸费
	 */
	private boolean iSLtDiscountBackInfo(WaybillPanelVo bean) {
		List<CusLtDiscountItemDto> list = waybillService.iSLtDiscountBackInfo(bean.getDeliveryCustomerCode(), bean.getBillTime());
		if(null ==list || list.size()==0) {
			return true;
		}
		return false;
	}
	/**
	 * 否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
	 * @param bean
	 * @return
	 */
	private boolean departPropertyServiceFee(WaybillPanelVo bean) {
		
		// 收货部门
		String orgCode = bean.getReceiveOrgCode();
		
		// org code is not null;
		if(StringUtils.isNotEmpty(orgCode)){
			// 根据编码查询
			SaleDepartmentEntity entity = waybillService.querySaleDeptByCode(orgCode);
			if(entity!=null){
				//不允许开装卸费
				if(!FossConstants.YES.equals(entity.getCanPayServiceFee())){
					return false;
				}
			}
		}
		return true;
	}
	

	/**
	 * 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * 
	 * @param bean
	 * @return
	 */
	private boolean channelServiceFee(WaybillPanelVo bean) {

		String channel = bean.getOrderChannel();
		// 阿里巴巴和阿里巴巴诚信通
		if (WaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(channel)||WaybillConstants.CRM_ORDER_CHANNEL_ALIBABACXT.equals(channel)) {
			return false;
		}
		// 月结
		/*if (bean.getChargeMode() != null) {
			if (bean.getChargeMode()) {
				return false;
			}
		}*/
	
		
		return true;
	}

	/**
	 * 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
	 * 
	 * @param bean
	 * @param transportFee
	 * @param minTransportFee
	 * @return
	 */
	private boolean lowestServiceFee(WaybillPanelVo bean) {
		BigDecimal minTransportFee = bean.getMinTransportFee();
		BigDecimal transportFee = bean.getTransportFee();
		boolean serviceChargeEnabled = true;
		if (!bean.getIsWholeVehicle() && minTransportFee != null) {
			// 最低一票 装卸费=0
			if (transportFee.compareTo(minTransportFee) == 0) {
				// 存在月发月送客户没有折扣的情况，所以需要在运费和最低一票相等的情况下，额外判断是否月发月送客户
				if (StringUtils.isNotEmpty(bean.getPreferentialType()) &&
					bean.getPreferentialType()
							.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
						serviceChargeEnabled = true;
				}else{
					bean.setServiceFee(BigDecimal.ZERO);
					bean.setServiceFeeCanvas("0");
					serviceChargeEnabled = false;					
				}
			}
		}

		return serviceChargeEnabled;
	}

	/**
	 * 
	 * 优惠劵
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 下午04:33:42
	 * @param bean
	 */
	private void executeCoupon(WaybillPanelVo bean) {
//		//TEST
//		CouponInfoDto couponInfoDto=new CouponInfoDto();
//		bean.setCouponFree(new BigDecimal(2));
//		bean.setCouponInfoDto(couponInfoDto);		
//		bean.setCouponRankType("ZHXX");
		
		// 优惠卷是否为空
		CouponInfoDto couponInfoDto = getCouponInfoDto(bean);

		if (couponInfoDto != null) {
			//验证是否是晚到补差价优惠券 如果 需要判断使用者
			String  couponNumber =  couponInfoDto.getCouponNumber();
			if(StringUtils.isNotEmpty(couponNumber)){
				if(customerHessianRemoting == null){
					customerHessianRemoting = DefaultRemoteServiceFactory.getService(ICustomerHessianRemoting.class);
				}
				CustomerCouponEntity  entity = new CustomerCouponEntity();
				entity.setCouponCode(couponNumber);
				List<CustomerCouponEntity> list = customerHessianRemoting.queryCustomerCouponList(entity);
				if(CollectionUtils.isNotEmpty(list)){
					String  customerCode = bean.getDeliveryCustomerCode();
					if(StringUtils.isEmpty(customerCode)){
						throw new WaybillValidateException("优惠券:"+couponNumber+"为晚到补差价,需要绑定客户才能使用");
					}
					for (CustomerCouponEntity cc : list) {
						if(!customerCode.equals(cc.getCustomerCode())){
							throw new WaybillValidateException("优惠券:"+couponNumber+"为晚到补差价,需要绑定客户:"+cc.getCustomerCode()+"才能使用");
						}
					}
				}
			}
			CouponInfoResultDto dto = waybillService.validateCoupon(couponInfoDto);
			if (dto != null) {
				if (!dto.isCanUse()) {			
					
					String canNotUseReason = StringUtil.defaultIfNull(dto.getCanNotUseReason());
					String waybill = StringUtils.substringBetween(canNotUseReason, ":", ";");

					// 判断：该优惠券是否是被该运单使用的（从不可使用原因的字符串中截取使用该优惠券的运单号）
					if (!bean.getWaybillNo().equals(StringUtil.defaultIfNull(waybill).trim())) {
						// 不能使用优惠券的原因
						MsgBox.showInfo(canNotUseReason);
						bean.setPromotionsCode(null);
					}else{
						String lastAmount = StringUtils.substringAfterLast(canNotUseReason, "value:");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, ")");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, "}");
						
						/**
						 * 设置优惠券费用
						 */
						try{
							bean.setCouponFree(new BigDecimal(lastAmount));							
						}catch(Exception e){
							bean.setCouponFree(BigDecimal.ZERO);
						}
						/**
						 * 设置优惠券返回实体
						 */
						bean.setCouponInfoDto(couponInfoDto);
						
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						bean.setCouponRankType(dto.getDeductibleType());
					}
					
				} else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						/**
						 * 设置优惠券费用
						 */
						bean.setCouponFree(dto.getCouponAmount());
						/**
						 * 设置优惠券返回实体
						 */
						bean.setCouponInfoDto(couponInfoDto);
						
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						bean.setCouponRankType(dto.getDeductibleType());
					} else {
						bean.setPromotionsCode("");
					}
				}
			}
		}
	}

	/**
	 * 
	 * 获取优惠传入参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 上午10:59:57
	 */
	private CouponInfoDto getCouponInfoDto(WaybillPanelVo bean) {
		// 优惠信息DTO
		CouponInfoDto couponInfo = new CouponInfoDto();
		// 运单信息
		CouponWaybillInfoDto waybillInfo = new CouponWaybillInfoDto();
		// 运单号
		waybillInfo.setWaybillNumber(bean.getWaybillNo());
		// 产品号
		waybillInfo.setProduceType(bean.getProductCode().getCode());
		// 订单号
		waybillInfo.setOrderNumber(bean.getOrderNo());
		// 判断总金额是否已有
		if (bean.getTotalFee() != null && bean.getTotalFee().compareTo(BigDecimal.ZERO) == 0) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.totalFeeNullException"));
			return null;
		}

		// 总金额
		waybillInfo.setTotalAmount(bean.getTotalFee());
		// 发货人手机
		waybillInfo.setLeaveMobile(bean.getDeliveryCustomerMobilephone());
		// 发货人电话
		waybillInfo.setLeavePhone(bean.getDeliveryCustomerPhone());
		// 客户编码
		waybillInfo.setCustNumber(bean.getDeliveryCustomerCode());
		// 获取出发部门
		String receiveOrgCode = bean.getReceiveOrgCode();

		OrgAdministrativeInfoEntity receiveOrgAdministrative = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(receiveOrgCode);

		if (receiveOrgAdministrative == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullReceiveOrgAdmin"));
			return null;
		}

		// 发货部门-标杆编码
		waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());

		if (bean.getLastLoadOrgCode() == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLastLoadOrgCode"));
			return null;
		}
		// 最终配载部门-也就是最后一个自有网点
		String lastLoadOrgCode = bean.getLastLoadOrgCode();

		OrgAdministrativeInfoEntity lastLoadOrgAdministrative = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(lastLoadOrgCode);
		if (lastLoadOrgAdministrative == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLastLoadOrgAdmin"));
			return null;
		}
		if (bean.getLoadOrgCode() == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLoadOrgCode"));
			return null;
		}

		// 始发配载部门
		String firstLoadOutOrgInfoCode = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getLoadOrgCode()).getUnifiedCode();
		// 最终配载部门 标杆编码
		String lastLoadOutOrgInfoCode = null;
		if (!StringUtils.isEmpty(bean.getLastOutLoadOrgCode())) {
			// 获取最终配载部门 标杆编码
			lastLoadOutOrgInfoCode = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getLastOutLoadOrgCode()).getUnifiedCode();
		} else {

			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLastOutLoadOrgCode"));
			return null;
		}

		// 到达部门-标杆编码-由于偏线最后到达部门是代理，这里是最后一个只有部门
		waybillInfo.setArrivedDept(lastLoadOrgAdministrative.getUnifiedCode());

		// 发货部门所在外场
		waybillInfo.setLeaveOutDept(firstLoadOutOrgInfoCode);
		// 到达部门所在外场
		waybillInfo.setArrivedOutDept(lastLoadOutOrgInfoCode);

		WaybillOtherCharge model = (WaybillOtherCharge) ui.incrementPanel.getTblOther().getModel();
		// 获取费用明细
		List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = WaybillDtoFactory.getWaybillChargeDtlEntity(model, bean);
		List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
		for (WaybillChargeDtlEntity waybillChargeDtlEntity : waybillChargeDtlEntitys) { // 计价明细
			AmountInfoDto amountInfo = new AmountInfoDto();
						
			if(PriceEntityConstants.PRICING_CODE_SH.equals(waybillChargeDtlEntity.getPricingEntryCode())
					|| PriceEntityConstants.PRICING_CODE_SHSL.equals(waybillChargeDtlEntity.getPricingEntryCode()) 
					|| PriceEntityConstants.PRICING_CODE_SHJC.equals(waybillChargeDtlEntity.getPricingEntryCode())){
				// 计价条目编码-送货费
				amountInfo.setValuationCode(PriceEntityConstants.PRICING_CODE_SH);
				// 获取通过计算得到的送货费
				amountInfo.setValuationAmonut(bean.getCalculateDeliveryGoodsFee());
			}else{
				// 计价条目编码-保险费
				amountInfo.setValuationCode(waybillChargeDtlEntity.getPricingEntryCode());
				// 计价金额
				amountInfo.setValuationAmonut(waybillChargeDtlEntity.getAmount());				
			}
			amountInfoList.add(amountInfo);
		}
		
		/**
		 * 定价优化项目---降价返券需求
		 * 
		 * 降价返券需求的生成优惠券中存在区域线路限制，因此修改优惠券校验接口
		 * 
		 * 且仅针对精准汽运（长途）、精准汽运（短途）、精准卡航、精准城运
		 * @author Foss-206860
		 * 
		 * **/
		//根据优惠券编码查询返券发送日志表，若存在数据，判断是否存在区域线路限制，若不存在，则不处理
		PendingSendCouponLogEntity pendingSendCouponLog = waybillService.queryLineAreaByNum(bean.getPromotionsCode());
		if(pendingSendCouponLog != null){
			if(DictionaryValueConstants.IS_DEPARTURE_CITY.equals(pendingSendCouponLog.getLineArea())){
				//当区域限制时，设置出发区域编码和名称
				waybillInfo.setArrivedCity(pendingSendCouponLog.getReceiveCustomerCityName());
				waybillInfo.setArrivedCityCode(pendingSendCouponLog.getReceiveCustomerCityCode());
			}else if(DictionaryValueConstants.IS_LINE.equals(pendingSendCouponLog.getLineArea())){
				//当线路限制时，设置出发到达区域编码和名称
				waybillInfo.setArrivedCity(pendingSendCouponLog.getReceiveCustomerCityName());
				waybillInfo.setArrivedCityCode(pendingSendCouponLog.getReceiveCustomerCityCode());
				waybillInfo.setLeaveCity(pendingSendCouponLog.getDeliveryCustomerCityName());
				waybillInfo.setLeaveCityCode(pendingSendCouponLog.getDeliveryCustomerCityCode());
			}
		}
		waybillInfo.setAmountInfoList(amountInfoList);
		couponInfo.setWaybillInfo(waybillInfo);
		couponInfo.setCouponNumber(bean.getPromotionsCode());
		return couponInfo;
	}

	/**
	 * 
	 * 设置预付到付金额编辑状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 上午11:15:47
	 */
	private void setPrePayArriveEditState(WaybillPanelVo bean) {
		// 只有到付不允许修改预付和到付金额，其他类型的付款方式均可修改预付和到付金额
		if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
			ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);
			ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
		} else {
			ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(true);
			ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
		}

	}

	/**
	 * 
	 * 保价费用校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午11:09:49
	 */

	private void validate(WaybillPanelVo bean) {
		
		//对内备注
		setInnerNotes(bean);
		//发票标记
		CommonUtils.setInvoiceType(bean,new Date());
		
		/**
		 * 与下面的校验重复
		 * @author Foss-278328-hujinyang
		 * @time 2016011 09:06
		 */
		//统一结算
//		Common.validatePayMethod(bean);
		
		//DMANA-4292   开单省市区校验
		Common.validateCity(bean);
		
		//地址校验
		CommonUtils.checkAddress(bean);
		
		//验证整车 统一结算 --发票标记 与 统一结算 无关联-sangwenhao-272311
//		validateWholeCarSettlement(bean);
		
		
		if(!bean.getIsWholeVehicle()){
			//验证提货方式与合票方式
			validatePickupOrgCode(bean);
		}		
		// PDA运单信息
		validateWaybillNo(bean);
		
		// 整车约车校验
		validateVehicleNumber(bean);

		//打木托校验 zxy 20131118 ISSUE-4391
		validEnterYoke(bean);
		// 重量、体积、件数校验
		validateWeightVolume(bean);

		//是否符合精准大票校验
		validateIsBigGoods(bean);
		
		// 验证新产品开单规则
//		validateIsDTDAndYTY(bean);
		
		// 目的站校验
		validateDistination(bean);
		
		//对目的站为安徽黄山市、宣城绩溪县区域的货物进行开单提醒
		/*if(!WaybillConstants.WAYBILL_FOCUS.equals(ui.getWaybillType())
				&& !WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())) {
			validateDistinationForAnHui(bean);
		}*/
		

		// 产品校验
		validateProduct(bean);

		// 包装校验
		validatePack(bean);

		// 客户校验
		validateCustomer(bean);
		
		//zxy 20140101 MANA-409 start 新增:验证保价声明价值
		validateInsurance(bean);
		//zxy 20140101 MANA-409 end 新增:验证保价声明价值

		// 校验提货网点重量、体积上限
		validateVW(bean);
		
		//提货方式校验
		validateReceiveMethod(bean);

		// 付款方式校验
		validatePaymentMode(bean);
		
		//验证返单
//		validateReturnBill(bean);

		//伙伴开单
		partnerBilling(bean);
		
		// 代收货款校验
		validateCod(bean);

		// 验证空运合票方式和航班类型不能为空
		validateAir(bean);

		// 验证空运货物类型不能为空
		validateAirGoodsType(bean);
		
		// 只允许合同客户才可以选择免费送货
		Common.validateDeliverFree(bean, ui);
		
		//当运输性质为门到门的时候，提货方式不能选自提
		//Common.validateDeliverTransportDTD(bean, ui);
		//当发货客户【是否统一结算】为【是】的时候，付款方式只能为【月结】或者【临欠】
		Common.validatePayMethod(bean);
		
		/**
		 * 目的站必选且不可编辑  并且目的站已经校验了该项
		 * @author Foss-278328-hujinyang
		 * @time 20160111 09:08
		 */
		//校验时效时否正确
//		validateEffectivePlan(bean);
		
		//验证自提件类型
		validateEconomyGoodsType(bean);
		//保价费率
		insuranceRateListener(bean);
		
		//集中开单组和普通开单的检查
		validateCenterDelivery(bean);		
		
		//校验营销活动是否开启
		Common.validateActiveStart(bean);
		
		//校验优惠券是否开启
		Common.validatePromotionsCode(bean);
		
		/**
		 * @author 200945-wutao
		 * @date 2014-08-26
		 * 检验是否进行开箱验货
		 */
		//validateUnpackInspection(bean);
		
		//验证香港地区必须勾选商业区或者住宅区
		//Common.validateBusinessZoneResidentialDistrict(ui, bean);

		// ===========lianhe/增加校验：工号和联系人/2017年1月7日/start=======
		/**
		 * @author 370613
		 * @date 2017年1月7日10:41:32
		 * 1.工号不为空时进入此校验，为空的时候必须置internalDeliveryType为null;
		 * 2.校验工号有效；
		 * 3.校验工号信息和输入的客户信息一致，并赋值‘内部发货internalDeliveryType’信息,故传入参数true；
		 */
		if (StringUtils.isNotBlank(bean.getEmployeeNo())) {
			CommonUtils.validateLinkMan(bean,true);
		}else {
			bean.setInternalDeliveryType(null);
		}
		// ===========lianhe/增加校验：工号和联系人/2017年1月7日/end=======
		
		
	}
	/**
	 * 校验是否伙伴开单
	 * @param bean
	 */
	private void partnerBilling(WaybillPanelVo bean) {
		if(null != bean.getPartnerBilling() && bean.getPartnerBilling()){
		    /*if(StringUtil.isEmpty(bean.getPartnerName())){
		    	throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.validatePartnerName"));
		    }
		    if(StringUtil.isEmpty(bean.getPartnerPhone())){
		    	throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.validatePartnerPhone"));
		    }*/
		    ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEditable(true);
		    ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEnabled(true);
		}else{
			ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEditable(false);
			ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEnabled(false);
		}
		//伙伴开单是否勾选
//		if(ui.basicPanel.getPartnerCheckBox().isSelected()){
//			boolean bool=true;
//			double temp=0;
//			String str="0.14";
//			BigDecimal weight=new BigDecimal(30);
//			BigDecimal Volume=new BigDecimal(str);
//			if(StringUtil.isNotBlank(bean.getGoodsSize())){
//				String[] GoodsSizeList=bean.getGoodsSize().replace("+", "#").replace("-", "#").split("#");
//				if(bool){
//					for (int i = 0; i < GoodsSizeList.length; i++) {
//						String[] sizelist = GoodsSizeList[i].split("\\*");
//						double longs = Double.parseDouble(sizelist[0]);
//						double wide = Double.parseDouble(sizelist[1]);
//						double high = Double.parseDouble(sizelist[2]);
//						temp=longs+wide+high;
//						if((longs<=160&&wide<=160&&high<=160)&&temp<=200){
//							bool=false;
//						}else{
//							return;
//	}
//					}
//				}
//				if(!bool){
//					//校验重量和体积
//					if((bean.getGoodsWeightTotal().compareTo(weight)==-1||bean.getGoodsWeightTotal().compareTo(weight)==0)&&(bean.getGoodsVolumeTotal().compareTo(Volume)==-1||bean.getGoodsVolumeTotal().compareTo(Volume)==0)){
//						throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.weightVolume"));
//					}
//				}
//			}else{
//				//校验重量和体积
//				if((bean.getGoodsWeightTotal().compareTo(weight)==-1||bean.getGoodsWeightTotal().compareTo(weight)==0)&&(bean.getGoodsVolumeTotal().compareTo(Volume)==-1||bean.getGoodsVolumeTotal().compareTo(Volume)==0)){
//					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.weightVolume"));
//				}
//			}
//		}
	}
	/**
	 * @author 200945
	 * @param bean
	 */
	private void validateWholeCarSettlement(WaybillPanelVo bean) {
		// TODO Auto-generated method stub
		if(WaybillConstants.INVOICE_01.equals(bean.getInvoice()) 
				&&  WaybillConstants.IS_NOT_NULL_FOR_AI.equals(bean.getStartCentralizedSettlement())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillRFCUI.alterInvoice"));
		}
	}


	/*
	 * 
	 * 是否包含超重货操作服务费
	 */
	public Boolean isOverweightFee(){
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		Boolean isOverweightFee = false;
		if(CollectionUtils.isNotEmpty(data)){
			for(OtherChargeVo vo : data){
				if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode()) ||
						PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(vo.getCode())){
					isOverweightFee = true;
				}
			}
		}
		return isOverweightFee;
	}
	




	/**
	 *是否符合精准大票校验
	 * 
	 */
	public void validateIsBigGoods(WaybillPanelVo bean) {
		Boolean isBigGoods = bean.getIsBigGoods();
		if ( null!=isBigGoods && isBigGoods) {
			/**
			 * DMANA-7725  
			 * @author YANGKANG
			 * 过滤新产品门到门和场到场  不做超重货或者超重货服务费的校验
			 */
			if(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(bean.getProductCode().getCode())
					||ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())){
				
					BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
					BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
					
					if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0 && goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) <= 0){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.GoodsWeight"));
					}
				
			}else{
				Boolean falg = CommonUtils.isHeavyWeight(bean);
				if (falg) {
					throw new WaybillValidateException("不符合开精准大票条件！");
				}else{
					if(isOverweightFee()){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.isOverweightFee"));
					}else{
						
						BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
						BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
						if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0 && goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) <= 0){
							throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.GoodsWeight"));
						}
						
					}
				}
			}
			}
			
	}
	
	/**
	 * 验证参数的业务规则： 1）单票重量大于500kg且单件重量小于等于500Kg且单件体积小于等于2.5方；
	 * 2）单票体积大于2.5F且单件重量小于等于500Kg且单件体积小于等于2.5方； 判断是否满足门到门，场到场
	 * 
	 * @author 200945-wutao
	 */
	public void validateIsDTDAndYTY(WaybillPanelVo bean) {

		if (ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean
				.getProductCode().getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD
						.equals(bean.getProductCode().getCode())) {
			Boolean isBigGoods = bean.getIsBigGoods();
			if (null != isBigGoods && isBigGoods) {
				Boolean falg = CommonUtils.isHeavyWeight(bean);
				if (falg) {
					throw new WaybillValidateException("不符合开精准大票条件！");
				} else {
					if (isOverweightFee()) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.waybillDescriptor.isOverweightFee"));
					} else {
						
						BigDecimal goodsWeightTotal = bean
								.getGoodsWeightTotal();
						int goodsQtyTotal = bean.getGoodsQtyTotal();
						
						BigDecimal singleWeight = goodsWeightTotal.divide(
								new BigDecimal(goodsQtyTotal), 2,
								BigDecimal.ROUND_HALF_UP);
						if (singleWeight.compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0) {
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.waybillDescriptor.DTDAndYTYGoodsWeight"));
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 * @author 200945-wutao
	 * @param bean
	 * 开箱验货业务逻辑处理
	 */
	

	public void validateUnpackInspection(WaybillPanelVo bean) {
		//此处判断是否是集中开单，如果是集中开单，那么就不执行此验证
		if(bean.getPickupCentralized()!=null && bean.getPickupCentralized()){
			return;
		}
		//此count用于判定当到达区域在基础数据中有数据，提醒后，始发区域在基础数据也有数据，那么，始发区域就不进行提醒，否则，进行提醒
		int count = 0;
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
		// 偏线汽运和精准空运
		if(bean.getProductCode().getCode().equals(WaybillConstants.AIR_FLIGHT) || bean.getProductCode().getCode().equals(WaybillConstants.HIGHWAYS_REFERRALS)){
			//偏线或空运的始发和到达区域查询，此处数据从偏线表中获取
			OuterBranchEntity outerBranchEntityArr = BaseDataServiceFactory.getBaseDataService().queryOuterBranchByBranchCode(bean.getCustomerPickupOrgCode().getCode(),null);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntityStart = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getReceiveOrgCode());
			//先进行到达区域判断，如果到达区域在开箱验货的基础资料中没有找到对应的数据，那么进行始发区域的判断。即走else中的代码
			 if(outerBranchEntityArr != null){
				orgAdministrativeInfoEntity.setProvCode(outerBranchEntityArr.getProvCode());
				orgAdministrativeInfoEntity.setCityCode(outerBranchEntityArr.getCityCode());
				orgAdministrativeInfoEntity.setCountyCode(outerBranchEntityArr.getCountyCode());
				count = unPackInspectionArr(orgAdministrativeInfoEntity);
			}
			 //此处代码进行当前营业部，即收货部门或始发部门。该处的数据从组织表中读取
			 if(count == 0){
				 if(orgAdministrativeInfoEntityStart != null){
						unPackInspectionStart(orgAdministrativeInfoEntityStart);
					} 
			 }	 
		}else{
			//其他的运输类型
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntityArr = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getCustomerPickupOrgCode().getCode());
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntityStart = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getReceiveOrgCode());
			if(orgAdministrativeInfoEntityArr != null){
				count = unPackInspectionArr(orgAdministrativeInfoEntityArr);
			}
			if(count == 0){
				if(orgAdministrativeInfoEntityStart != null){
					unPackInspectionStart(orgAdministrativeInfoEntityStart);
				}
			}	
		}	
	}
	
	/**
	 * 
	 * @author 200945-wutao
	 * @param orgAdministrativeInfoEntity
	 * 提示开箱验货：获取发货部门和收货部门所在的省市区，与基础数据进行匹配如果匹配成功，则提示：开箱验货
	 * @date 2014-09-10
	 * 此方法是到达区域进行开箱验货提醒
	 */
	private int unPackInspectionArr(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		int count = 0;
		BillInspectionRemindEntity entityArrProv = BaseDataServiceFactory
				.getBaseDataService().queryBillInspectionRemindByRegionCode(
						orgAdministrativeInfoEntity,
						WaybillConstants.ARRIVE_REGION_CODE,
						WaybillConstants.DISTRICT_PROVINCE_CODE);
		if (null != entityArrProv) {
			JOptionPane.showMessageDialog(null, entityArrProv.getRegionName()
					+ "到达区域货物,请务必开箱验货！", "提示", JOptionPane.WARNING_MESSAGE);
			count++;
		} else {
			BillInspectionRemindEntity entityArrtCity = BaseDataServiceFactory
					.getBaseDataService()
					.queryBillInspectionRemindByRegionCode(
							orgAdministrativeInfoEntity,
							WaybillConstants.ARRIVE_REGION_CODE,
							WaybillConstants.DISTRICT_CITY_CODE);
			if (null != entityArrtCity) {
				JOptionPane.showMessageDialog(null,
						entityArrtCity.getRegionName() + "到达区域货物,请务必开箱验货！",
						"提示", JOptionPane.WARNING_MESSAGE);
				count++;
			} else {
				BillInspectionRemindEntity entityArrCounty = BaseDataServiceFactory
						.getBaseDataService()
						.queryBillInspectionRemindByRegionCode(
								orgAdministrativeInfoEntity,
								WaybillConstants.ARRIVE_REGION_CODE,
								WaybillConstants.DISTRICT_COUNTY_CODE);
				if (null != entityArrCounty) {
					JOptionPane
							.showMessageDialog(null,
									entityArrCounty.getRegionName()
											+ "到达区域货物,请务必开箱验货！", "提示",
									JOptionPane.WARNING_MESSAGE);
					count++;
				}
			}
		}
		return count;

	}
	/**
	 * 
	 * @author 200945-wutao
	 * @param orgAdministrativeInfoEntity
	 * 提示开箱验货：获取发货部门和收货部门所在的省市区，与基础数据进行匹配如果匹配成功，则提示：开箱验货
	 * @date 2014-09-10
	 * 此方法始发区域进行判断
	 */
	public void unPackInspectionStart(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		// 发货部门（收货部门，始发区域）
		BillInspectionRemindEntity entityStartProv = BaseDataServiceFactory
				.getBaseDataService().queryBillInspectionRemindByRegionCode(
						orgAdministrativeInfoEntity,
						WaybillConstants.START_REGION_CODE,
						WaybillConstants.DISTRICT_PROVINCE_CODE);
		if (null != entityStartProv) {
			JOptionPane.showMessageDialog(null, entityStartProv.getRegionName()
					+ "始发区域货物,请务必开箱验货！", "提示", JOptionPane.WARNING_MESSAGE);
		} else {
			BillInspectionRemindEntity entityStartCity = BaseDataServiceFactory
					.getBaseDataService()
					.queryBillInspectionRemindByRegionCode(
							orgAdministrativeInfoEntity,
							WaybillConstants.START_REGION_CODE,
							WaybillConstants.DISTRICT_CITY_CODE);
			if (null != entityStartCity) {
				JOptionPane.showMessageDialog(null,
						entityStartCity.getRegionName() + "始发区域货物,请务必开箱验货！",
						"提示",

						JOptionPane.WARNING_MESSAGE);
			} else {
				BillInspectionRemindEntity entityStartCounty = BaseDataServiceFactory
						.getBaseDataService()
						.queryBillInspectionRemindByRegionCode(
								orgAdministrativeInfoEntity,
								WaybillConstants.START_REGION_CODE,
								WaybillConstants.DISTRICT_COUNTY_CODE);
				if (null != entityStartCounty) {
					JOptionPane.showMessageDialog(null,
							entityStartCounty.getRegionName()
									+ "始发区域货物,请务必开箱验货！", "提示",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
		
	
	/**
	 * 验证打木托
	 * ISSUE-4391
     * @author	157229-zxy 
     * @date 2013-11-18
	 */
	private void validEnterYoke(WaybillPanelVo bean) {
		//当从未弹出过打木托对话框这个值则是null
		if(ui.getDialog() == null)
			return ;
		// 打木托件数
		BigDecimal salverGoodsPieces = new BigDecimal(
				StringUtils.defaultIfBlank(ui.getDialog()
						.getTxtSalverGoodsPieces().getText(), "0"));
		Object[] salverValues = ui.getDialog().listSalver.getSelectedValues();
		if (salverValues != null
				&& (salverGoodsPieces.compareTo(new BigDecimal(
						salverValues.length)) > 0)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.woodListener.eight"));
		}
		if (salverValues != null && salverValues.length > 0
				&& (salverGoodsPieces.compareTo(BigDecimal.ZERO) <= 0)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.woodListener.night"));
		}
		// 流水号类型置空
		Common.refreshLabeledPackageType(bean);
		if (salverValues != null) {
			for (int i = 0; i < salverValues.length; i++) {
				LabeledGoodEntityVo vo = (LabeledGoodEntityVo) salverValues[i];
				vo.getEntity().setPackageType(
						WaybillConstants.PACKAGE_TYPE_SALVER);
			}
		}
	}
	/**
	 * 集中开单组和普通开单的检查
	 * @param bean
	 */
	private void validateCenterDelivery(WaybillPanelVo bean) {
		if(!ui.isBatchWaybill() 
				&& WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(ui.getWaybillType())){
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			if(!dept.checkSaleDepartment()){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.departmentError"));	
			}
		}
		
		
	}

	/**
     * 调整保价费率范围
     * @param bean
     */
	private void insuranceRateListener(WaybillPanelVo bean) {
		//最低保价费率
		BigDecimal minFeeRate = bean.getMinFeeRate();
		//最高保价费率
		BigDecimal maxFeeRate = bean.getMaxFeeRate();
		//调整保价费率
		BigDecimal insuranceRate = bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000));
		//报价声明价值
		BigDecimal insuranceAmount =bean.getInsuranceAmount();
		//内部带货送货 不校验费用信息 --sangwenhao
		if(bean.getReceiveMethod()==null || !WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			if(minFeeRate!=null && maxFeeRate!=null){
				if(insuranceRate.compareTo(minFeeRate)<0
						||
						insuranceRate.compareTo(maxFeeRate)>0){
					if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
						String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
						String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
						if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
								&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
							ui.incrementPanel.getBtnCalculate().setEnabled(true);
							ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
							ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
							ui.incrementPanel.getJlable().setVisible(false);
							ui.incrementPanel.getCombServiceRate().setVisible(false);
						}else{
							ui.incrementPanel.getBtnCalculate().setEnabled(false);
							ui.billingPayPanel.getBtnSubmit().setEnabled(true);// 提交为不可编辑
							ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
							ui.incrementPanel.getJlable().setVisible(true);
							ui.incrementPanel.getCombServiceRate().setVisible(true);
						}
						
					}else{
						ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
						ui.buttonPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
					}
					minFeeRate=minFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_1000));
					maxFeeRate=maxFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_1000));
					if (!(StringUtil.equals(bean.getProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)
							&& bean.getInsuranceAmount().compareTo(new BigDecimal(NumberConstants.NUMBER_2000)) <= 0))
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.Outrange",new Object[]{insuranceAmount,minFeeRate+"‰",maxFeeRate+"‰"}));
				}
			}
		}
		
	}
	
	/**
	 * 校验是否自提件
	 * @author WangQianJin
	 * @date 2013-08-21
	 */
	private void validateEconomyGoodsType(WaybillPanelVo bean){
		if(bean.getIsEconomyGoods()!=null && bean.getIsEconomyGoods()){
			//如果是自提件，并且自提件类型为空，则给予提示
			if(bean.getEconomyGoodsType()==null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.economygoodstype.isnull"));	
			}else{
				if(bean.getEconomyGoodsType().getValueCode()==null){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.economygoodstype.isnull"));
				}
			}			
		}
	}
	
	/**
	 * 再检验一次时交是否正确
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-18 下午5:50:13
	 */
	private void validateEffectivePlan(WaybillPanelVo bean){
		if (bean.getCustomerPickupOrgCode() != null) {	
			IdentityEffectivePlanVo vo = null;
			//先判定是否开启偏线时效方案的开关
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode())){
				ConfigurationParamsEntity entity = baseDataService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.OUTER_EFFECTIVE_VERIFIED_CODE,FossConstants.ROOT_ORG_CODE);
				if (entity != null) {
					//判断是否开启开关
					if (FossConstants.YES.equals(entity.getConfValue())) {
						vo = Common.identityOuterEffectivePlan(bean);
						if(!vo.isExist()){
							LOG.debug("未查询到产品时效，出发部门："+vo.getDepartDeptName()+"，到达部门："+vo.getArriveDetpName()+"，请确认运输性质是否选择正确！");					
							throw new WaybillValidateException(i18n.get("foss.gui.creating.showpickupstationdialogaction.noproducttime",new Object[]{vo.getDepartDeptName(),vo.getArriveDetpName()}));
						}
					}
				}
			}else if (!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())
					&& !ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(bean.getProductCode().getCode())) {
				vo = Common.identityEffectivePlan(bean);
				if(!vo.isExist()){
					LOG.debug("未查询到产品时效，出发部门："+vo.getDepartDeptName()+"，到达部门："+vo.getArriveDetpName()+"，请确认运输性质是否选择正确！");					
					throw new WaybillValidateException(i18n.get("foss.gui.creating.showpickupstationdialogaction.noproducttime",new Object[]{vo.getDepartDeptName(),vo.getArriveDetpName()}));
				}
			}
		}
	}
	
	/**
	 * 校验运单号
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-28 下午3:16:20
	 */
	private void validateWaybillNo(WaybillPanelVo bean){
		//导入前的运单号
		String oldWaybill = StringUtil.defaultIfNull(bean.getOldWaybillNo());
		//导入后的运单号
		String newWaybill = StringUtil.defaultIfNull(bean.getWaybillNo());
		//运单状态
		String pendingType = StringUtil.defaultIfNull(bean.getWaybillstatus());
		
		
		//PDA补录运单不能修改运单号
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingType) && !oldWaybill.equals(newWaybill)) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.pda.import",new Object[]{oldWaybill,newWaybill}));
		}
	}
	
	/**
	 * 校验提货方式与合票方式等
	 * @author WangQianJin
	 * @date 2013-07-10
	 */
	private void validatePickupOrgCode(WaybillPanelVo bean){
		ValidateVo vo=new ValidateVo();
		if(bean.getCustomerPickupOrgCode()!=null){
			vo.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());	
		}
		if(bean.getProductCode()!=null){
			vo.setProductCode(bean.getProductCode().getCode());
		}
		if(bean.getReceiveMethod()!=null){
			vo.setReceiveMethod(bean.getReceiveMethod().getValueCode());
		}
		if(bean.getFreightMethod()!=null){
			vo.setFreightMethod(bean.getFreightMethod().getValueCode());
		}
		//验证提货网点选择是否正确
		CommonUtils.validatePickupOrgCode(vo);
	}

   
	/**
	 * 
	 * 验证空运货物类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-7 下午05:57:27
	 */
	private void validateAirGoodsType(WaybillPanelVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			if (bean.getAirGoodsType() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_AIRGOODSTYPE));
			}
		}
	}

	/**
	 * 
	 * 验证空运合票方式和航班类型不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-20 下午05:47:47
	 */
	private void validateAir(WaybillPanelVo bean) {

		ProductEntityVo productVo = bean.getProductCode();
		// 只有空运才需要进行校验
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			if (bean.getFlightNumberType() == null || bean.getFlightNumberType().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FLIGHTNUMBERTYPE));
			}

			if (bean.getFreightMethod() == null || bean.getFreightMethod().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FREIGHTMETHOD));
			}
		}

	}

	/**
	 * 
	 * 验证整车业务规则
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-24 下午05:08:10
	 * @param bean
	 */
	private void validateVehicleNumber(WaybillPanelVo bean) {
		if (bean.getIsWholeVehicle()) {
			if (StringUtils.isEmpty(bean.getVehicleNumber())) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.null_VehicleNumber));
			}
		}
	}

	/**
	 * 
	 * 验证重量、体积、件数不能为默认值0
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午08:16:30
	 */
	private void validateWeightVolume(WaybillPanelVo bean) {
		if (bean.getGoodsQtyTotal() == null || bean.getGoodsQtyTotal().intValue() == 0 || bean.getGoodsQtyTotal().intValue()<0 ) {
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				ui.pictureCargoInfoPanel.getTxtTotalPiece().requestFocus();
			}else{
				ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
			}
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSQTYTOTAL));
		}

		if (null==bean.getGoodsWeightTotal() || bean.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				ui.pictureCargoInfoPanel.getTxtWeight().requestFocus();
			}else{
				ui.cargoInfoPanel.getTxtWeight().requestFocus();
			}
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSWEIGHTTOTAL));
		}

		if ( null == bean.getGoodsVolumeTotal() || bean.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				ui.pictureCargoInfoPanel.getTxtVolume().requestFocus();
			}else{
				ui.cargoInfoPanel.getTxtVolume().requestFocus();
			}
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSVOLUMETOTAL));
		}
		
		/**
		 * 根据Dmana-9885，若是巨商汇或者阿里的开单，则需验证重量和体积
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-11
		 */
		Common.specialChannelFreight(bean);
		
		if (bean.getGoodsVolumeTotal() != null && !"0".equals(bean.getGoodsVolumeTotal().toString()) && bean.getGoodsWeightTotal() != null && !"0".equals(bean.getGoodsWeightTotal().toString())) {
			/*Boolean falg = CommonUtils.isHeavyWeight(bean);
			if(!falg){
				if(isOverweightFee()){
					bean.setBigTicket(false);
				}else{
					if(!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
						BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
						BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
						if(goodsWeightTotal.compareTo(new BigDecimal(500)) > 0 || goodsVolumeTotal.compareTo(new BigDecimal(2.5)) > 0){
							bean.setBigTicket(true);
						}else{
							bean.setBigTicket(false);
						}
					}else{
						bean.setBigTicket(false);
					}
				}
			}else{
				bean.setBigTicket(false);
			}*/
			/**
			 * DMANA-11054
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-08 下午15:14
			 */
			BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
			BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
			if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0 || goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) > 0){
				bean.setBigTicket(true);
			}else{
				bean.setBigTicket(false);
			}
			
			boolean bool = waybillService.isWeightByVolume(bean.getGoodsWeightTotal().toString(), bean.getGoodsVolumeTotal().toString());
			if (!bool) {
				if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.creating.calculateAction.msgBox.confirmWeightVolume"), i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"), JOptionPane.YES_NO_OPTION)) {
					throw new WaybillValidateException("");
				}
			}
		}
		
		//zxy 20131225 MANA-381 start 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
		int intBoxPieces = bean.getBoxGoodsNum() == null ? 0:bean.getBoxGoodsNum();
		int intYokePieces = bean.getStandGoodsNum() == null ? 0:bean.getStandGoodsNum();
		if((intBoxPieces + intYokePieces) == bean.getGoodsQtyTotal()){
			// 打木架体积
			BigDecimal decYokeGoodsVolume = bean.getStandGoodsVolume();
			// 打木箱体积
			BigDecimal decBoxGoodsVolume = bean.getBoxGoodsVolume();
			BigDecimal decMoodenGoodsValumn = decYokeGoodsVolume.add(decBoxGoodsVolume);
			if(decMoodenGoodsValumn.add(new BigDecimal(ZEROPONTZEROONE)).compareTo(bean.getGoodsVolumeTotal()) < 0
					|| decMoodenGoodsValumn.subtract(new BigDecimal(ZEROPONTZEROONE)).compareTo(bean.getGoodsVolumeTotal()) > 0){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.notEqualVolumn"));
			}
		}
		//zxy 20131225 MANA-381 end 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
	}

	/**
	 * 
	 * 代收货款业务规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 上午11:35:22
	 */
	private void validateCod(WaybillPanelVo bean) {

		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal codAmount = bean.getCodAmount();

		// 代收货款不能小于0
		if(codAmount != null && codAmount.compareTo(zero) < 0){
			throw new WaybillValidateException("代收货款不能小于0");
		}
		
		if (codAmount == null || codAmount.compareTo(zero) == 0) {

			DataDictionaryValueVo vo = bean.getRefundType();
			if (vo != null && vo.getValueCode() != null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.one") + vo.getValueName() + i18n.get("foss.gui.creating.calculateAction.exception.validateCod.two"));
			}
			/**
			 * 判断是否为整车类型并且提货方式是否为机场自提，
			 * 如果不是，才给与没有代收货款提示
			 */
			if (ui.incrementPanel.getCombRefundType().isEnabled()&&!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(bean.getProductCode().getCode()) && !WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
				if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.creating.calculateAction.msgBox.confirmValidate"), i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"), JOptionPane.YES_NO_OPTION)) {
					ui.incrementPanel.getTxtCashOnDelivery().requestFocus();
					// 将退款类型设置为空
					Common.setRefundType(bean, ui);
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.three"));
				}
			}
		} else {
			if(!bean.getIsWholeVehicle())
			{
				if(!FossConstants.YES.equals(bean.getCanAgentCollected()))
				{
					throw new WaybillValidateException("对不起，您选择的网点不允许开代收货款！");
				}
			}

			Common.validateBankInfo(bean, ui, waybillService);

			//DP-FOSS zhaoyiqing 20161026 开单配合CUBC校验银行信息
			Common.validateBankInfoCUBC(bean);
			
			/**
			 * 为退款类型重新赋值，因为当发货客户是非公司会员客户时，第一次选择退款类型会清空，当再次选择此退款类型时，bean中的退款类型并没有并没有改变，还是空。
			 */
			if(ui.incrementPanel.getCombRefundType().getSelectedItem()!=null){
				DataDictionaryValueVo refundType=(DataDictionaryValueVo)ui.incrementPanel.getCombRefundType().getSelectedItem();
				bean.setRefundType(refundType);
			}
			if (bean.getRefundType() == null || bean.getRefundType().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.four"));
			} else {
				if (!WaybillConstants.REFUND_TYPE_VALUE.equals(bean.getRefundType().getValueCode())) {
					if (StringUtils.isEmpty(bean.getAccountName()) || StringUtils.isEmpty(bean.getAccountCode()) || StringUtils.isEmpty(bean.getAccountBank())) {
						if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
							throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.five"));
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 判断包装件数不能大于开单件数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 上午10:04:36
	 */
	private void validatePack(WaybillPanelVo bean) {
		Integer qtyTotal = bean.getGoodsQtyTotal();// 总件数

		// 木架信息判空操作
		if (null == bean.getPaper() || null == bean.getWood() || null == bean.getFibre() || null == bean.getSalver() || null == bean.getMembrane()) {
			// 记录异常日志信息
			LOG.error(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
			// 抛出异常信息
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
		}

		// 木架信息判空操作
		if (0 == bean.getPaper().intValue() && 0 == bean.getWood().intValue() 
				&& 0 == bean.getFibre().intValue() && 0 == bean.getSalver().intValue() 
				&& 0 == bean.getMembrane().intValue() && StringUtils.isEmpty(bean.getOtherPackage())) {
			// 记录异常日志信息
			LOG.error(i18n.get("foss.gui.creating.caculateAction.validatePack.isZero"));
			// 抛出异常信息
			throw new WaybillValidateException(i18n.get("foss.gui.creating.caculateAction.validatePack.isZero"));
		}

		Integer paper = Integer.valueOf(bean.getPaper());// 纸
		Integer wood = Integer.valueOf(bean.getWood());// 木
		Integer fibre = Integer.valueOf(bean.getFibre());// 纤
		Integer salver = Integer.valueOf(bean.getSalver());// 托
		Integer membrane = Integer.valueOf(bean.getMembrane());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;

		if (packTotal > qtyTotal) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validatePack"));

		}
	}
	
	/**
	 * 
	 * 验证合票方式如果是单独开单，则只能选机场自提
	 * 
	 * @author WangQianJin
	 * @date 2013-07-08 下午03:21:42
	 */
	private void validateReceiveMethod(WaybillPanelVo bean) {		
		/**
		 * 判断合票方式和运输性质和提货方式是否为空
		 */
		if (bean.getFreightMethod() != null && bean.getProductCode() != null && bean.getReceiveMethod()!=null) {
			/**
			 * 判断合票方式是否为单独开单和运输性质是否为精准空运和提货方式是否为机场自提
			 */
			if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())
					&& ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())
					&& !WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
				//空运单独开单，提货方式只能是机场自提！
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.exception.validatereceivemethod"));
			} 
		}
         //自提，送货费不能编辑
		if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			    ui.billingPayPanel.getTxtDeliveryCharge().setEditable(false);
		}
		/**
		 * @需求：大件上楼优化需求
		 * @功能：验证是否满足送货上楼业务要求
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-17上午09:34
		 */
		Common.judgeBeanForDU(bean);
		/**
		 * @需求：大件上楼优化需求
		 * @功能：验证是否满足大件上楼业务要求
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-17上午09:34
		 */
		Common.judgeBeanForLDU(bean);
	}

	/**
	 * 
	 * 验证提货方式为机场自提时，不能开到付
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午03:21:42
	 */
	private void validatePaymentMode(WaybillPanelVo bean) {
		
		if (bean.getPaidMethod() == null) {
			ui.incrementPanel.getCombPaymentMode().requestFocus();
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullPaidMethod"));
		}
		/**
         * 	若为月结合同，则结算方式可以选择月结，注明校验适用营业部时按照主客户合同的适用部门进行校验（主客户月结合同的归属部门和绑定部门)
         * 	如果当前不存在有效合同或者合同不是月结合同，则结算方式不能选择月结；
         * 	如果主客户当前有效合同为统一结算，则开单选择结款方式只能为月结和到付；
		 */
		if( 	
				StringUtil.isNotBlank(bean.getIsCustCircle()) && 
				StringUtil.equals("Y", bean.getIsCustCircle()) && 
				bean.getCustomerCircleEntity() != null &&
						bean.getCusBargainNewEntity() != null  &&
								bean.getCustomerNewEntity() !=null
						){
			//获取 月结方式 
			String chargeType=bean.getCusBargainNewEntity().getChargeType();
			//如果当前不存在有效合同或者合同不是月结合同，则结算方式不能选择月结；
			if(bean.getCusBargainNewEntity()==null || (StringUtils.isBlank(chargeType) || StringUtils.equals("NOT_MONTH_END", chargeType))){
				if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					//给客户提示抛出结算方式不能选择月结
					throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillBillder.common.alterPaidMethod"));
				}
			}
		}
		//获取当前订单类型
		 //  String servicetype = Common.getServiceType(bean.getOrderNo());
		// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付
		if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
			  if (!WaybillConstants.SERVICE_TYPE.equals(bean.getServerType()) && (!WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getOrderChannel()) || !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(bean.getOrderPayment()))) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.identityPayment.one"));
			}
		}
		

		//只有月结客户才能开月结
		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
			// 判断客户是否月结
			if (bean.getChargeMode() == null || !bean.getChargeMode()) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.notMonthPayment"));
			}
			CusBargainVo vo=new CusBargainVo();
			vo.setChargeType(WaybillConstants.MONTH_END);
			vo.setCustomerCode(bean.getDeliveryCustomerCode());
			//判断运单是否是补录
			if(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(bean.getWaybillstatus())
					|| WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(bean.getWaybillstatus())){
				//如果是补录运单，将开单时间设为运单开单时间
				vo.setBillDate(bean.getBillTime());					
			}else{	
				//设置开单时间为当前时间
				vo.setBillDate(new Date());
			}				
			vo.setBillOrgCode(bean.getReceiveOrgCode());
			boolean  isOk = waybillService.isCanPaidMethod(vo);
			if(!isOk){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.NocanPaidMethod"));
			}
		}

		//提货方式为机场自提，付款方式不能选择到付
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.one"));
				}
			}

			if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.two"));
			}
		}
		
		if(!bean.getIsWholeVehicle())
		{
			//提货网点是否可以货到付款
			if(!FossConstants.YES.equals(bean.getArriveCharge()))
			{
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.noArrivedPay"));
				}
			}
		}


		// 空运以及偏线无法选择网上支付
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()) || ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())) {
			if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
				throw new WaybillValidateException(productVo.getName() + i18n.get("foss.gui.creating.calculateAction.exception.unableOnlinePayment"));
			}
		}

		/**
		 * 临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
		 */
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
			//判断客户编码是否为空
			if (StringUtil.isEmpty(bean.getDeliveryCustomerCode())) {
				throw new WaybillSubmitException(i18n.get("foss.gui.creating.calculateAction.exception.paymentLQ"));
			}
		}

	}

	/**
	 * 
	 * 校验手机号码与电话号码必须有一个不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-3 上午11:15:38
	 */
	private void validateCustomer(WaybillPanelVo bean) {
		if (StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())) {
			String mobilePhone = bean.getDeliveryCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				String result = new WaybillDescriptor().validateDeliveryCustomerMobilephone(mobilePhone, bean);
				if (!WaybillConstants.SUCCESS.equals(result)) {
					ui.consignerPanel.getTxtConsignerMobile().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillDescriptor.deliver.mobile.format.error"));
				}
			}

		}

		if (StringUtils.isNotEmpty(bean.getReceiveCustomerMobilephone())) {
			String mobilePhone = bean.getReceiveCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				String result = new WaybillDescriptor().validateDeliveryCustomerMobilephone(mobilePhone, bean);
				if (!WaybillConstants.SUCCESS.equals(result)) {
					ui.consigneePanel.getTxtConsigneeMobile().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillDescriptor.receiver.mobile.format.error"));
				}
			}

		}

		if (StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone()) && StringUtils.isEmpty(bean.getDeliveryCustomerPhone())) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorPhoneOrTel"));
		}

		if (StringUtils.isEmpty(bean.getReceiveCustomerMobilephone()) && StringUtils.isEmpty(bean.getReceiveCustomerPhone())) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneePhoneOrTel"));
		}
		/**
		 * 已在提交时对 原件签收单返回 校验
		 * @author Foss-272311-sangwenhao 
		 * @time 20160417
		 */
		/*
		// 原件签收单返回
		if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
			JAddressField jd = ui.consignerPanel.getTxtConsignerArea() ;
			AddressFieldDto consignerAddress = jd.getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText()) || consignerAddress==null || StringUtils.isEmpty(consignerAddress.getProvinceId()) 
					|| StringUtils.isEmpty(consignerAddress.getProvinceName()) 
					|| StringUtils.isEmpty(bean.getDeliveryCustomerAddress())) {
				ui.consignerPanel.getTxtConsignerArea().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
			}
		}*/
		if(bean.getReceiveMethod()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.transferInfoPanel.receiveMethod.isNull"));
		}

		/**
		 * 已在提交时 校验
		 * @author Foss-272311-sangwenhao 
		 * @time 20160417
		 */
		/*
		// 提货方式
		String code = bean.getReceiveMethod().getValueCode();
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_FREE.equals(code) || WaybillConstants.DELIVER_STORAGE.equals(code) || WaybillConstants.DELIVER_UP.equals(code) || WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code) || WaybillConstants.DELIVER_UP_AIR.equals(code) || WaybillConstants.DELIVER_INGA_AIR.equals(code)|| WaybillConstants.DELIVER_INNER_PICKUP.equals(code)) {
			JAddressField jd = ui.consigneePanel.getTxtConsigneeArea();
			AddressFieldDto consigneeAddress = jd.getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText())||consigneeAddress==null || StringUtils.isEmpty(consigneeAddress.getProvinceId()) 
					|| StringUtils.isEmpty(consigneeAddress.getProvinceName())|| StringUtils.isEmpty(bean.getReceiveCustomerAddress())) {
				ui.consigneePanel.getTxtConsigneeArea().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
			}
		}
		
		//如果不是导入订单开单,则校验省市区是否规范，因为订单省市区业务规则与FOSS不一致，因此不做校验（MANA-1598）
		if(StringUtils.isEmpty(bean.getOrderNo())){
			//判断发货人省市区文本框是否为空
			String regionNerText=ui.consignerPanel.getTxtConsignerArea().getText();		
			if(StringUtils.isNotEmpty(regionNerText)){				
				//判断是否为选择的省市区
				if(regionNerText.indexOf("-")==-1){
					//请选择正确的发货人省市区
					throw new WaybillValidateException(i18n.get("foss.gui.creating.consignerPanel.regionAddress.alert"));
				}
			}
			//判断收货人省市区文本框是否为空			
			String regionNeeText=ui.consigneePanel.getTxtConsigneeArea().getText();			
			if(StringUtils.isNotEmpty(regionNeeText)){				
				//判断是否为选择的省市区
				if(regionNeeText.indexOf("-")==-1){
					//请选择正确的收货人省市区
					throw new WaybillValidateException(i18n.get("foss.gui.creating.consigneePanel.regionAddress.alert"));
				}
			}
		}
		*/
	}

	/**
	 * 
	 * 校验提货网点单票以及单件重量与体积上限
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午08:10:17
	 */
	private void validateVW(WaybillPanelVo bean) {
		/**
		 * BUG-41949:运单储运事项过长，导致综合查询报错，建议在开单时进行限制长度。。。见图。。258000250
		 */
		//对内备注
		int innerNotes = StringUtil.defaultIfNull(bean.getInnerNotes()).length();
		//储运事项 
		int transNotes = StringUtil.defaultIfNull(bean.getTransportationRemark()).length();
		
		if(innerNotes > CLOB_LIMIT){
			///对内备注录入错误：最大字符不能超过1300个！
			StringBuffer sb = new StringBuffer();
			sb.append(i18n.get("foss.gui.creating.cargoInfoPanel.insideRemark.label"));
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.inputDataError"));
			sb.append(CLOB_LIMIT);
			sb.append("\n");
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.currentStringNum"));
			sb.append(innerNotes);
			throw new WaybillValidateException(sb.toString());
		}
		
		if(transNotes > CLOB_LIMIT){
			//储运事项 录入错误：最大字符不能超过1300个！
			StringBuffer sb = new StringBuffer();
			sb.append(i18n.get("foss.gui.creating.cargoInfoPanel.transportationRemark.label"));
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.inputDataError"));
			sb.append(CLOB_LIMIT);
			sb.append("\n");
			sb.append(i18n.get("foss.gui.creating.calculateAction.exception.currentStringNum"));
			sb.append(transNotes);
			throw new WaybillValidateException(sb.toString());
		}
		
		
		BigDecimal goodsWeight = bean.getGoodsWeightTotal();// 总重量
		BigDecimal goodsVolume = bean.getGoodsVolumeTotal();// 总体积
		BigDecimal goodsQty = new BigDecimal(bean.getGoodsQtyTotal());// 总件数

		BigDecimal pieceWeight = goodsWeight.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件重量
		BigDecimal pieceVolume = goodsVolume.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件体积
		
		BranchVo selectedSaleDepartmentInfo = bean.getCustomerPickupOrgCode();
		/**
		 * 整车不校验重量和体积
		 */
		if (selectedSaleDepartmentInfo != null && !bean.getIsWholeVehicle()) {
			if (selectedSaleDepartmentInfo.getSinglePieceLimitkg() != null) {
				// 单件重量上限
				BigDecimal singlePieceLimitkg = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSinglePieceLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceWeight.compareTo(singlePieceLimitkg) > 0) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSinglePieceLimitkg") + singlePieceLimitkg.toString() + i18n.get("foss.gui.creating.waybillEditUI.common.antiBrackets"));
				}
			}

			if (selectedSaleDepartmentInfo.getSinglePieceLimitvol() != null) {
				// 单件体积上限
				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSinglePieceLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceVolume.compareTo(singlePieceLimitvol) > 0) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSinglePieceLimitvol"));
				}
			}

			if (selectedSaleDepartmentInfo.getSingleBillLimitkg() != null) {
				// 单票重量上限
				BigDecimal singleBillLimitkg = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSingleBillLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsWeight.compareTo(singleBillLimitkg) > 0) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSingleBillLimitkg"));
				}
			}

			if (selectedSaleDepartmentInfo.getSingleBillLimitvol() != null) {
				// 单票体积上限
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(selectedSaleDepartmentInfo.getSingleBillLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsVolume.compareTo(singleBillLimitvol) > 0) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSingleBillLimitvol"));
				}
			}
		}
	}

	/**
	 * 
	 * 产品规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:31:32
	 */
	private void validateProduct(WaybillPanelVo bean) {
		// 产品对象
		ProductEntityVo product = bean.getProductCode();
		// 产品对象是否为空或编码是否为空
		if (product == null || "".equals(StringUtil.defaultIfNull(product.getCode()))) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductType"));
		}
		// 精准包裹校验
		if(!WaybillConstants.YES.equals(bean.getIsAccuratePackage())
				&&ProductEntityConstants.PRICING_PRODUCT_PCP.equals(product.getCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.isNotAccuratePackageCustomerException"));
		}
	}

	/**
	 * 
	 * 校验目的站
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:26:27
	 */
	private void validateDistination(WaybillPanelVo bean) {
		if (bean.getCustomerPickupOrgCode() == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullCustomerPickupOrg"));
		}
	}
    private void validateDistinationForAnHui(WaybillPanelVo bean) {
    	//需要校验的字符串
    	String pro = "安徽";
    	String city1 = "黄山";
    	String city2 = "宣城";
    	String dis = "绩溪";
    	boolean flag = false;
    	OrgAdministrativeInfoEntity org =	waybillService.queryByCode(bean.getCustomerPickupOrgCode().getCode());
    	if(org != null && org.getProvName() != null && org.getCityName()!= null && org.getCountyName() != null) {
    		if(org.getProvName().contains(pro)
        			&& (org.getCityName().contains(city1)
        					||(org.getCityName().contains(city2) && org.getCountyName().contains(dis)))) {
        		if(StringUtil.isNotBlank(bean.getOtherPackage())){
        			if(bean.getGoodsName().contains("木") || bean.getWood().intValue()>0 ||
        					bean.getOtherPackage().contains("木")) {
            			flag = true;
            		}
        		} else {
        			if(bean.getGoodsName().contains("木") || bean.getWood().intValue()>0
            				) {
            			flag = true;
            		}
        		}
    	}
    	
    		
    		
    	}
    	if(flag) {
    		JOptionPane.showMessageDialog(null, 
					"安徽黄山市、宣城绩溪县到达区域木制品、木包装货物，需获得《植物检疫证书》", "提示", JOptionPane.WARNING_MESSAGE);
    	}
    }
	/**
	 * 
	 * 临时欠款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:19:55
	 */
	private void isBeBebt(WaybillPanelVo bean) {
		BigDecimal money = bean.getPrePayAmount();
		DebitDto dto = waybillService.isBeBebt(bean.getDeliveryCustomerCode(), bean.getReceiveOrgCode(), WaybillConstants.TEMPORARY_DEBT, money);
		if (!dto.isBeBebt()) {
			throw new WaybillValidateException(dto.getMessage());
		}
	}

	/**
	 * 
	 * 获得产品价格详细信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-5 下午04:27:16
	 */
	private GuiQueryBillCalculateDto getProductPriceDtoCollection(WaybillPanelVo bean) {
		// 上门接货优先读取上门接货的价格
		if (bean.getPickupToDoor() != null && bean.getPickupToDoor()&&bean.getFreePickupGoods() != null&&!bean.getFreePickupGoods()) {
			return Common.getQueryParamCollection(bean, FossConstants.YES);

		} else {
			return Common.getQueryParamCollection(bean, FossConstants.NO);
		}

	}

	/**
	 * 
	 * 设置公布价集合
	 * 
	 * @param dto
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-16 下午5:04:54
	 */
	private void setProductPriceDtoCollection(GuiResultBillCalculateDto dto, WaybillPanelVo bean,GuiResultBillCalculateDto dtoJH) {
		if (dto == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		}
		if (dto.getCaculateFee() == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
		}
		/**
		 * 根据Dmana-9885巨商汇客户的运费和运费折扣执行新的规则
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-05下午14:37
		 */
		BigDecimal transportFee;
		if(bean.getSpecialChannelFreight()){
			transportFee=bean.getCrmTransportFee();
		}else{
			transportFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		// 设置运费价格ID
		bean.setTransportFeeId(dto.getId());
		// 设置运费价格CODE
		bean.setTransportFeeCode(dto.getPriceEntryCode());
		// 设置运费
		bean.setTransportFee(transportFee);
		// 设置费率
		bean.setUnitPrice(dto.getActualFeeRate());
		
		//设置偏线费用 by:352676
		BigDecimal partialTransportFee = null != dto.getPartialTransportFee() ? dto.getPartialTransportFee() : BigDecimal.ZERO;
		partialTransportFee = partialTransportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		bean.setPartialTransportFee(partialTransportFee);
		
		// 设置计费类型（重量计费或者体积计费）
		setBillingWay(dto.getCaculateType(), bean);

		// 设置计费重量
		setBillWeight(bean, dto);

		// 画布公布价运费
		bean.setTransportFeeCanvas(dto.getCaculateFee().toString());

		// 设置折扣优惠
		Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);

		// 设置折扣值
		setDiscount(dto.getDiscountPrograms(), bean);

		//判断如果勾选上门接货,判断是否是用上门接货计算的运费，如果是，设置接货费为0，不可编辑；如果不是，设置接货费为远程获取的费用
		//zxy 20131204 DEFECT-405 修改:if中 增加getPickupToDoor为空判断
		if( bean.getPickupToDoor() != null && bean.getPickupToDoor()&&bean.getFreePickupGoods() != null&&!bean.getFreePickupGoods())
		{
			if (FossConstants.ACTIVE.equals(dto.getCentralizePickup())) {
				ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
				bean.setPickupFee(BigDecimal.ZERO);//设置接货费
				// 画布-接货费
				bean.setPickUpFeeCanvas(BigDecimal.ZERO.toString());
			} else {				
				if(dtoJH==null){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.PickupFeeNull"));
				}
				/**
				 * 设置接货费( MANA-257接货费优化)
				 * 2014-03-03 李凤腾
				 */
				//设置接货费最大值
				bean.setMaxPickupFee(CommonUtils.defaultIfNull(dtoJH.getMaxFee()));
				//设置接货费最小值
				bean.setMinPickupFee(CommonUtils.defaultIfNull(dtoJH.getMinFee()));
				//设置接货费实际值
				setPickupCollection(bean,dtoJH);	
				
//				ui.billingPayPanel.getTxtPickUpCharge().setEnabled(true);
//				ui.billingPayPanel.getTxtPickUpCharge().setEditable(true);
//				bean.setPickupFee(dtoJH.getCaculateFee());//设置接货费
//				bean.setBasePickupFee(dtoJH.getCaculateFee());//原始接货费
//				// 画布-接货费
//				bean.setPickUpFeeCanvas(dtoJH.getCaculateFee().toString());
		    }
				
			/**
			 * 如果带出来的接货费用大于0则接货费用可编辑【0,10000】之间
			 * 2016年1月13日 10:38:43 葛亮亮
			 */
			//合伙人项目需求
			if(BZPartnersJudge.IS_PARTENER){
				BigDecimal pickupFee = bean.getPickupFee() != null ? bean.getPickupFee() : BigDecimal.ZERO;//接货费	
				if(pickupFee.compareTo(BigDecimal.ZERO) > 0){
					//设置接货费最大值
					bean.setMaxPickupFee(new BigDecimal(NUM_10000));
					//设置接货费最小值
					bean.setMinPickupFee(new BigDecimal(0));
					ui.billingPayPanel.getTxtPickUpCharge().setEditable(true);
					ui.billingPayPanel.getTxtPickUpCharge().setEnabled(true);
				}					
			}
		}else{
			ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
			bean.setPickupFee(BigDecimal.ZERO);//设置接货费
			bean.setBasePickupFee(BigDecimal.ZERO);//原始接货费
		}
	}
	
	/**
	 * 设置接货费
	 * MANA-257接货费优化
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 上午8:59:54
	 */
	private void setPickupCollection(WaybillPanelVo bean, GuiResultBillCalculateDto dto) {
		// 封装查询数据
		WaybillEntity queryParm = CommonUtils.getWaybillEntity(bean);
		// 是否为发货人在当前收货部门的当天8时到次日8时间的第一票
		boolean firstWaybill = waybillService.isFirstDeliveryWaybill(queryParm);
		// 判断是否是第一票
		if (firstWaybill) {
			bean.setPickupFee(dto.getCaculateFee());// 设置接货费
			bean.setBasePickupFee(dto.getCaculateFee());// 原始接货费
			// 画布-接货费
			bean.setPickUpFeeCanvas(dto.getCaculateFee().toString());
			//判断最低一票与折扣金额
			if(dto.getMinFee() != null){
				if(null == dto.getFee() && dto.getCaculateFee().compareTo(dto.getMinFee()) < 0){
					bean.setPickupFee(dto.getMinFee());// 设置接货费
					bean.setBasePickupFee(dto.getMinFee());// 原始接货费
					// 画布-接货费
					bean.setPickUpFeeCanvas(dto.getMinFee().toString());
				}
			}
			//判断最高一票与折扣金额
			if(dto.getMaxFee() != null){
				if(null == dto.getFee() && dto.getCaculateFee().compareTo(dto.getMaxFee()) > 0){
					bean.setPickupFee(dto.getMaxFee());// 设置接货费
					bean.setBasePickupFee(dto.getMaxFee());// 原始接货费
					// 画布-接货费
					bean.setPickUpFeeCanvas(dto.getMaxFee().toString());
				}
			}
			//【固定值】
			// 返回的接货费值不为空，则表示有客户编码且有设置客户接货费值
			if (null != dto.getFee()) {
				ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
				ui.billingPayPanel.getTxtPickUpCharge().setEditable(false);
			} 
			//【默认费用，最小值，最大值】
			else{
				//DEFECT-5057 :开单时接货费不要代码限制为0，读取接货费基础资料实现
				ui.billingPayPanel.getTxtPickUpCharge().setEnabled(true);
				ui.billingPayPanel.getTxtPickUpCharge().setEditable(true);
			}
			//设置折扣优惠
			Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
		}
		//【0，最大值】
		// 非第一票,默认为0，接货费只能在0到最大值之间
		else {
			ui.billingPayPanel.getTxtPickUpCharge().setEnabled(true);
			ui.billingPayPanel.getTxtPickUpCharge().setEditable(true);
			bean.setPickupFee(BigDecimal.ZERO);// 设置接货费
			bean.setBasePickupFee(BigDecimal.ZERO);// 原始接货费
			// 画布-接货费
			bean.setPickUpFeeCanvas(BigDecimal.ZERO.toString());
		}
	}
	

	/**
	 * 
	 * 设置折扣优惠
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-9 下午01:46:39
	 */
	private void setDiscount(List<GuiResultDiscountDto> discountPrograms, WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {//折扣不是空
			for (GuiResultDiscountDto dto : discountPrograms) {
				// 设置
				bean.setDiscount(dto.getDiscountRate());
			}
		}
	}

	/**
	 * 
	 * 设置计费重量
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-2 下午12:03:25
	 */
	private void setBillWeight(WaybillPanelVo bean, GuiResultBillCalculateDto fee) {
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {//精准空运

			if (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode())) {//按重量计费
				bean.setBillWeight(bean.getGoodsWeightTotal()); //设置计费重量
			} else {
				// 计费重量
				if (fee.getVolumeWeight() == null) {
					bean.setBillWeight(BigDecimal.ZERO);//设置计费重量 = 0
				} else {
					bean.setBillWeight(fee.getVolumeWeight()); //设置计费重量
				}
			}
		} else {
			bean.setBillWeight(BigDecimal.ZERO);//设置计费重量 = 0
		}

	}

	/**
	 * 
	 * 计算增值费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午08:58:39
	 */
	private void calculateValueAdd(WaybillPanelVo bean, List<GuiResultBillCalculateDto> guiResultBillCalculateDtos) {

		if(guiResultBillCalculateDtos == null){
			throw new WaybillValidateException("guiResultBillCalculateDtos计算结果参数为空");
		}
		//获取打木架
		GuiResultBillCalculateDto packageCollectionFRAME=	 getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__FRAME);
		//设置打木架
		Common.setStandChargeCollection(bean, ui, packageCollectionFRAME);
		
		//获取打木箱
		GuiResultBillCalculateDto packageCollectionBOX=	 getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__BOX);
		//设置打木箱
		Common.setBoxChargeCollection(bean, ui, packageCollectionBOX);
		//zxy 20131118 ISSUE-4391 start 新增：获取打木托 
		// 此段不能加，否则木托费会被减两次,因为木托和木架不一样，费用不清零--20131120 
		// 获取打木托
//		GuiResultBillCalculateDto packageCollectionSalver = getGuiResultBillCalculateDto(
//				guiResultBillCalculateDtos,
//				PriceEntityConstants.PRICING_CODE_BZ,
//				DictionaryValueConstants.PACKAGE_TYPE__SALVER);
//		// 设置打木托
//		Common.setSalverChargeCollection(bean, ui, packageCollectionSalver);
		//zxy 20131118 ISSUE-4391 end 新增：获取打木托
		
		//获取保价费
		GuiResultBillCalculateDto insuranceCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BF, null);
		//设置保价费
		setInsuranceCollection(bean, insuranceCollection);

		//获取代收货款手续费
		GuiResultBillCalculateDto codCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_HK, null);
		//设置代收货款手续费
		setCodCollection(bean, codCollection);
		
		// 送货费
		setDeliveryFeeCollection(bean, guiResultBillCalculateDtos);
		
		// 计算装卸费
		calculateServiceFee(bean);
		
//		if(!"AF".equals(bean.getProductCode().getCode())){
//			//超重费用
//			OtherFeeCaozhaoFee(bean);
//		}
		
		//定价体系优化项目POP-548
		// 查询其他费用
		Common.queryOtherChargeData(ui, bean);
		// 计算其他费用合计
		Common.calculateOtherCharge(ui, bean);
		
		//添加其他费用 折扣
		setOtherChargeDataCollection(bean, guiResultBillCalculateDtos);
		
		//添加包装费折扣
		setPackageFeeDataCollection(bean, guiResultBillCalculateDtos);
		//获取运费的计费类型
		String caculateType = null;
		if(guiResultBillCalculateDtos!=null && guiResultBillCalculateDtos.size()>0){
			caculateType = guiResultBillCalculateDtos.get(0).getCaculateType();
		}
		//设置签收单折扣
		setReturnBillCollection(bean,caculateType);
	}
	/**
	 * 签收单折扣
	 * pgy
	 * @param bean
	 */
	private void setReturnBillCollection(WaybillPanelVo bean,String caculateType) {
		
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {
			if (data == null || data.isEmpty()) {
				data = new ArrayList<OtherChargeVo>();
			}
			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(bean.getReturnBillType().getValueCode())) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = bean.getReturnBillType().getValueCode();
			}
			QueryBillCacilateValueAddDto dto = Common.getQueryOtherChargeParam(bean, type);
			dto.setCaculateType(caculateType);
			List<ValueAddDto> list = waybillService.queryValueAddPriceList(dto);
			
			OtherChargeVo otherVo = getReturnBillCharge(bean, list, data);
			// 添加返单费用到其他费用表格
			String chargeName = Common.addOtherCharge(data, otherVo, bean);
			// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
			bean.setReturnBillChargeName(chargeName);
			ui.incrementPanel.setChangeDetail(data);
			
			if(CollectionUtils.isNotEmpty(list)){
				//设置其他费用的折扣优惠
				for (ValueAddDto valueAddDto : list) {
					//是否为其他费用
					if (PriceEntityConstants.PRICING_CODE_QS.equals(valueAddDto.getPriceEntityCode())) {
						Common.setFavorableQSDiscount(valueAddDto.getDiscountPrograms(), ui, bean);
						break;
					}
				}
			}
		}else{
			// 删除返单
			deleteReturnBill(data, bean);
		}
		
	}

	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getReturnBillCharge(WaybillPanelVo bean, List<ValueAddDto> list, List<OtherChargeVo> data) {
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
//				vo.setMoney(CalculateFeeTotalUtils.formatNumberInteger(dto.getFee().toString()));
				//定价体系优化项目POP-402
				vo.setMoney(CalculateFeeTotalUtils.formatNumberInteger(dto.getCaculateFee().toString()));
				// 上限
				vo.setUpperLimit(dto.getMaxFee().toString());
				// 下限
				vo.setLowerLimit(dto.getMinFee().toString());
				// 是否可修改
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));

				/**
				 * 月结
				 */
				Boolean chargeMode = bean.getChargeMode();
				if (chargeMode == null) {
					// 没有填写的情况下 作为非月结处理
					chargeMode = Boolean.FALSE;
				}
				/**
				 * 返单费用 非月结客户不允许进行编辑
				 */
				if (chargeMode) {
					vo.setIsUpdate(true);
				}else{
					vo.setIsUpdate(false);
				}

				// 是否可删除
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));
				vo.setId(dto.getId());
			} else {
				// 删除返单
				deleteReturnBill(data, bean);
				setReturnBillType(bean);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
			}
		} else {
			// 删除返单
			deleteReturnBill(data, bean);
			setReturnBillType(bean);
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
		}
		return vo;
	}
	
	/**
	 * 
	 * 删除返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午03:52:12
	 */
	private void deleteReturnBill(List<OtherChargeVo> data, WaybillPanelVo bean) {
		if (data != null && !data.isEmpty()) {
			// 将已有的返单费用从其他费用表格中删除
			Common.deleteOtherCharge(data, bean, bean.getReturnBillChargeName());
			ui.incrementPanel.setChangeDetail(data);
		}
	}
	
	/**
	 * 
	 * 设置返单类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private void setReturnBillType(WaybillPanelVo bean) {
		int size = ui.getCombReturnBillTypeModel().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui.getCombReturnBillTypeModel().getElementAt(i);
			if (WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				ui.incrementPanel.getCombReturnBillType().setSelectedItem(vo);
				bean.setReturnBillType(vo);
			}
		}
	}

	/**
	 * 设置包装费折扣
	 * @创建时间 2014-5-17 下午4:09:50   
	 * @创建人： WangQianJin
	 */
	private void setPackageFeeDataCollection(WaybillPanelVo bean, List<GuiResultBillCalculateDto> guiResultBillCalculateDtos) {
		if(CollectionUtils.isNotEmpty(guiResultBillCalculateDtos)){
			for (GuiResultBillCalculateDto guiResultBillCalculateDto : guiResultBillCalculateDtos) {
				if (PriceEntityConstants.PRICING_CODE_BZ.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
					Common.setFavorableDiscount(guiResultBillCalculateDto.getDiscountPrograms(), ui, bean);					
				}
			}
		}
	}
	
	/**
	 * 查询打木托价格计算对象
	 * ISSUE-4391
     * @author	157229-zxy 
     * @date 2013-11-18
	 * @return
	 */
	public GuiResultBillCalculateDto querySalverBillCalculateDto(WaybillEditUI waybillEditUI){
		// 通过运单UI对象获取绑定的VO对象
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		List<GuiResultBillCalculateDto> resultBillCalculateDto = queryResultBillCalculate(bean);
		// 获取打木托
		GuiResultBillCalculateDto packageCollectionSalver = getGuiResultBillCalculateDto(
				resultBillCalculateDto, PriceEntityConstants.PRICING_CODE_BZ,
				DictionaryValueConstants.PACKAGE_TYPE__SALVER);
		return packageCollectionSalver;
	}

	/**
	 * 获取其他费用查询参数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-22 下午4:03:05
	 */
	private GuiQueryBillCalculateSubDto getQueryOtherChargeParam(WaybillPanelVo bean) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();

		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		return queryDto;
	}
	
	/**
	 * 
	 * 查询其他费用折扣
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:22:50
	 */
	private void setOtherChargeDataCollection(WaybillPanelVo bean, List<GuiResultBillCalculateDto> dtos) {

		// 总件数
		int zjs=bean.getGoodsQtyTotal();
		//总总量
		BigDecimal zzl=bean.getGoodsWeightTotal();
		//平均重量
		BigDecimal cz=zzl.divide(new BigDecimal(zjs),1,BigDecimal.ROUND_HALF_UP);
		if (dtos != null && !dtos.isEmpty()) {
			//设置其他费用的折扣优惠
			for (GuiResultBillCalculateDto valueAddDto : dtos) {
				//是否为其他费用
				if (PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto.getPriceEntryCode())) {
					//wutao 
					//DEFECT-5166 :059387登陆GUI，选择门到门，发货客户选择月结客户40000473发货，计算总运费，详细计价信息里有2条相同的优惠（超重货操作服务费）
					if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(valueAddDto.getSubType()) && cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<0){
						continue;
					}
					//获取其他费用
					JXTable otherTable = ui.incrementPanel.getTblOther();
					WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
					List<OtherChargeVo> data = model.getData();				
					getOtherData(bean, valueAddDto, data);
				}
			}
		}
	}
	
	private void getOtherData(WaybillPanelVo bean,
			GuiResultBillCalculateDto valueAddDto, List<OtherChargeVo> data) {
		if(CollectionUtils.isNotEmpty(data)){
				for(OtherChargeVo vo : data){
					//修复pop-449 开单-FOSS开单超远派送加收费用计算错误
					//修复POP-492开单-FOSS开单手动添加的其他费用，计算总运费之后费用明细会改变
					if(valueAddDto.getSubType().equals(vo.getCode())){
						if(PriceEntityConstants.PRICING_CODE_RYFJ.equals(valueAddDto.getSubType())
								|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(valueAddDto.getSubType())
								|| PriceEntityConstants.PRICING_CODE_CY.equals(valueAddDto.getSubType())
								|| PriceEntityConstants.QT_CODE_CZHCZFWF.equals(valueAddDto.getSubType())){
						           Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), ui, bean);
						}
					}
				}
		}
	}

	private GuiQueryBillCalculateSubDto getOtherChargeDataCollection(WaybillPanelVo bean) {
		return getQueryOtherChargeParam(bean);
	}

	/**
	 * 
	 * 获取保价费以及最高保额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午01:51:03
	 */
	private GuiQueryBillCalculateSubDto getInsuranceCollection(WaybillPanelVo bean) {
		return getInsuranceParam(bean);

	}

	private void setInsuranceCollection(WaybillPanelVo bean, GuiResultBillCalculateDto dto) {
		if (dto != null) {
			setInsurance(bean, dto);//设置保价费
			// 设置折扣优惠
			Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
		} else {
			//清空保价费
			setInsurance(bean, null);
		}
	}

	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getInsuranceParam(WaybillPanelVo bean) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
		queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setWoodenVolume(null);// 木架体积
		queryDto.setInsuranceAmount(bean.getInsuranceAmount());//保险声明价值
		queryDto.setMinFeeRate(bean.getMinFeeRate());//最低费率
		queryDto.setMaxFeeRate(bean.getMaxFeeRate());//最高费率
		return queryDto;
	}
	
	
	/**
	 * 
	 * 设置保险费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 上午10:05:31
	 */
	private void setInsurance(WaybillPanelVo bean, GuiResultBillCalculateDto dto) {
		if (dto == null) {
			// 保险声明值最大值
			bean.setMaxInsuranceAmount(BigDecimal.ZERO);
			// 保险费率
			bean.setInsuranceRate(BigDecimal.ZERO);
			// 保险手续费
			bean.setInsuranceFee(BigDecimal.ZERO);
			// 保险费ID
			bean.setInsuranceId("");
			// 保险费CODE
			bean.setInsuranceCode("");

			//三级产品为精准包裹，并且保险费用低于2000，就不抛出异常
			if(!(bean.getProductCode() != null && StringUtil.equals(bean.getProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)
					&& bean.getInsuranceAmount().compareTo(new BigDecimal(NUMBER_2000)) <= 0 ))
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullInsuranceFee"));
		} else {
			// 保险声明值最大值
			bean.setMaxInsuranceAmount(dto.getMaxFee());
			//将保险费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = Common.nullBigDecimalToZero(dto.getActualFeeRate());
			/**
			 * （FOSS20150818）RFOSS2015052602 保价阶梯费率
			 * @author foss-206860
			 * 
			 * */
			//当折扣保价费率不等于0且折扣保价费率与默认保价费率不相等时，做默认保价费率处理和费率区间处理
			if(PriceEntityConstants.PRICING_CODE_BF.equals(dto.getPriceEntryCode()) && CollectionUtils.isNotEmpty(dto.getDiscountPrograms())){
				//合同客户：折后高于该段保价费率最低值则显示【该段保价费率最低值，折后保价费率】；折后低于或等于该段保价费率最低值则直接显示折后保价费率。
				if(feeRate.compareTo(bean.getMinFeeRate()) > 0){
					bean.setMaxFeeRate(Common.nullBigDecimalToZero(feeRate));
				}else{
					bean.setMaxFeeRate(Common.nullBigDecimalToZero(feeRate));
					bean.setMinFeeRate(Common.nullBigDecimalToZero(feeRate));
				}
			}
			String insuranceRateRange = "["+bean.getMinFeeRate()+","+bean.getMaxFeeRate()+"]";
			bean.setInsuranceRateRange(insuranceRateRange);
			bean.setInsuranceRateRangeCanvas(insuranceRateRange);
			feeRate = feeRate.multiply(permillage);
			// 保险费率
			bean.setInsuranceRate(feeRate);
			BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			if(BigDecimal.ZERO.compareTo(bean.getInsuranceAmount())==0){
				// 保险手续费
				bean.setInsuranceFee(BigDecimal.ZERO);
			}else{
				//如果保价费大于最低保价费，则取计算的保价费，如果保价费小于最低保价费，则取最低保价费
				if(insuranceFee.compareTo(dto.getMinFee())<0){
					// 取最低保险手续费
					bean.setInsuranceFee(dto.getMinFee());
				}else{
					// 取计算的保险手续费
					bean.setInsuranceFee(insuranceFee);
				}
			}
			
			// 保险费ID
			bean.setInsuranceId(dto.getId());
			// 保险费CODE
			bean.setInsuranceCode(dto.getPriceEntryCode());
		}

	}

	/**
	 * 
	 * 获取代收货款费率
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午02:23:42
	 */
	private GuiQueryBillCalculateSubDto getCodCollection(WaybillPanelVo bean) {
		
		// 判断是否查询到保险费
				BigDecimal zero = BigDecimal.ZERO;
				// 获得输入的代收货款金额
				BigDecimal codAmount = bean.getCodAmount();
				if (codAmount != null && codAmount.compareTo(zero) > 0 && bean.getRefundType() != null) {
					
					return getQueryParam(bean, PriceEntityConstants.PRICING_CODE_HK, bean.getCodAmount(), bean.getRefundType().getValueCode());
				}else{
					return null;
				}
		

	}

	/**
	 * 
	 * 设置代收货款 如果有代收货款但却找不到价格，提示查询不到价格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 上午10:12:34
	 */
	private void setCodCollection(WaybillPanelVo bean, GuiResultBillCalculateDto dto) {
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0 && bean.getRefundType() != null) {
			if (dto != null) {
				//来自官网的订单不能能设置
				if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getOrderChannel())) {
					ui.incrementPanel.getTxtInsuranceValue().setEnabled(false); // 保价申明价值
					ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false); // 代收货款
				} else {
					ui.incrementPanel.getTxtInsuranceValue().setEnabled(true); // 保价申明价值
					ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true); // 代收货款
				}
				ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
				ui.incrementPanel.getCombRefundType().setEnabled(true);
				setCod(bean, dto);
				// 设置折扣优惠
				Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
			} else {
				setCod(bean, null);
				throw new WaybillValidateException(bean.getRefundType().getValueName() + i18n.get("foss.gui.creating.calculateAction.exception.nullCodAmountFee"));
			}
		}
	}

	/**
	 * 
	 * 设置代收货款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 上午10:12:34
	 */
	private void setCod(WaybillPanelVo bean, GuiResultBillCalculateDto dto) {
		if (dto == null) {
			// 代收货款费率
			bean.setCodRate(BigDecimal.ZERO);
			// 代收货款金额
			bean.setCodFee(BigDecimal.ZERO);
			// 代收货款编码
			bean.setCodCode("");
			// 代收货款ID
			bean.setCodId("");
		} else {
			//将代收货款费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = Common.nullBigDecimalToZero(dto.getActualFeeRate());
			feeRate = feeRate.multiply(permillage);
			// 代收货款费率
			bean.setCodRate(feeRate);

			BigDecimal codFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			// 代收货款金额
			bean.setCodFee(codFee);

			// 代收货款编码
			bean.setCodCode(dto.getPriceEntryCode());
			// 代收货款ID
			bean.setCodId(dto.getId());
		}
	}
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getQueryParam(WaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}

	/**
	 * 
	 * 计算送货费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-17 下午04:00:59
	 */
	private List<GuiQueryBillCalculateSubDto> getDeliveryFeeCollection(WaybillPanelVo bean) {

		List<GuiQueryBillCalculateSubDto> queryBillCacilateValueAddDto = new ArrayList<GuiQueryBillCalculateSubDto>();
		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return null;
		}
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
		/**
		 * 大件上楼费
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-15上午08:32
		 */
		else if (WaybillConstants.LARGE_DELIVER_UP.equals(code)// 大件上楼费
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_DJSL, null, null));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		}
		return queryBillCacilateValueAddDto;
	}

	/**
	 * 
	 * 获取送货费
	 * 
	 * @param bean
	 * @param dto
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-16 下午6:55:53
	 */
	private void setDeliveryFeeCollection(WaybillPanelVo bean, List<GuiResultBillCalculateDto> dtos) {

		GuiResultBillCalculateDto guiResultBillCalculateDto;
		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return;
		}

		//提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {

			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			/**
			 * 如果是汽运送货费，那么需要判断是否超过最高派送费，如果超过，赋值为派送费最大值
			 */
			if (WaybillConstants.DELIVER_NOUP.equals(code)) {

				BigDecimal maxDeliveFee = null;//设置最大派送费
				
				ConfigurationParamsEntity maxDeliverFeeForConfig = getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_DELIVER_NOUP_MAX_FEE);// 最大派送费
				
				if (maxDeliverFeeForConfig != null
						&& StringUtils.isNotEmpty(maxDeliverFeeForConfig
								.getConfValue())) {
					maxDeliveFee = new BigDecimal(
							maxDeliverFeeForConfig.getConfValue());
				}
				
				if (guiResultBillCalculateDto != null && maxDeliveFee!=null)
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
		/**
		 * 封装大件上楼
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-17下午15:46
		 */
		else if (WaybillConstants.LARGE_DELIVER_UP.equals(code)// 大件上楼
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(code)) {

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			setDeliverFee(guiResultBillCalculateDto, bean, true, false);
			//获取大件上楼费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_DJSL, null);
			// 设置大件上楼费
			setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			/**
			 * @需求：大件上楼优化
			 * @功能：当大件上楼计算费用为null时，说明没有查询到相应的大件上楼费用，需要维护相关资料
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-04-16
			 */
			if(guiResultBillCalculateDto==null && !StringUtil.equals(bean.getProductCode().getCode(),ProductEntityConstants.PRICING_PRODUCT_PCP)){
				throw new WaybillValidateException("大件上楼资料未维护，请维护相关资料！");
			}
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			}
		}
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)) {
			bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);
		}

		//送货费集合
		List<DeliverChargeEntity> deliverList = bean.getDeliverList();
		if (deliverList != null) {
			ui.canvasContentPanel.getOtherCost().setChangeDetail(deliverList);
		}

	}

	
	/**
	 * 获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	private ConfigurationParamsEntity getConfigurationParamsEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return BaseDataServiceFactory.getBaseDataService().queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);				

	}
	
	
	
	
	
	/**
	 * 
	 * 获取GuiResultBillCalculateDto
	 * 
	 * @param dtos
	 * @param valueAddType
	 * @param subType
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-16 下午7:43:29
	 */
	public GuiResultBillCalculateDto getGuiResultBillCalculateDto(List<GuiResultBillCalculateDto> dtos, String valueAddType, String subType) {

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
	 * 
	 * 设置送货费、送货进仓费、送货上楼等全部送货费
	 * 
	 * @param flag = true 表示是送货费中的基础送货费
	 * @param isDeliverStorge = true
	 *            表示送货费中的送货进仓费
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午04:52:25
	 */
	private void setDeliverFee(GuiResultBillCalculateDto dto, WaybillPanelVo bean, Boolean flag, Boolean isDeliverStorge) {
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
			// 费用折扣
			Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
		}
	}

	/**
	 * 
	 * 获取超远派送费查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午04:54:59
	 * @param bean
	 * @param valueAddType
	 * @param cost
	 * @param subType
	 * @return
	 */
	private GuiQueryBillCalculateSubDto getVeryFarQueryParam(WaybillPanelVo bean, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}

	/**
	 * 
	 * 设置计费类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-1 上午09:41:19
	 */
	private void setBillingWay(String billingWay, WaybillPanelVo bean) {
		DefaultComboBoxModel combBillingType = (DefaultComboBoxModel) ui.getCombBillingType();

		int size = combBillingType.getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combBillingType.getElementAt(i);
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_WEIGHT.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}

			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_VOLUME.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}
		}
	}

	/**
	 * 
	 * 计算装卸费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午09:18:11
	 */
	private void calculateServiceFee(WaybillPanelVo bean) {
		//获取装卸费，当装卸费大于0的时候，才进行开单部门是否有装卸费权限校验-update by taodongguo-354805-2016-12-8 15:02:51
		if(null != bean.getServiceFee() && bean.getServiceFee().compareTo(BigDecimal.ZERO) == 1){
			// 设置是否允许修改装卸费
			boolean serviceChargeEnabled = setServiceChargeState(bean);
			
			// 装卸费无效
			if (!serviceChargeEnabled) {
				return ;
			}
			validataServiceFee(bean);
		}

		// 获取装卸费
		BigDecimal serviceFee = bean.getServiceFee();
		// 获取运费
		BigDecimal transportFee = bean.getTransportFee();
		// 获取费率
		BigDecimal unitPrice = bean.getUnitPrice();
		// 重量或体积
		BigDecimal weightOrVolume = getWeightOrVolume(bean);
		// 最低一票，没有的话默认为0
		BigDecimal minFee = bean.getMinTransportFee() == null ? BigDecimal.ZERO : bean.getMinTransportFee();
		// 折扣，没有的话默认为1
		BigDecimal discount = bean.getDiscount() == null ? new BigDecimal(1) : bean.getDiscount();
		
		/*
		 * 高于最低一票的情况，按照（费率 = （运费+装卸费）/ 重量或体积）公式重新计算费率
		 * 否则使用原来费率
		 * 运费 = 原运费 + 装卸费
		 * 
		 */
		if (serviceFee != null && serviceFee.longValue() != 0) {
			if(transportFee.compareTo(minFee.multiply(discount)) > 0){
				// 费率 = 最新运费（运费+装卸费）/ 重量或体积
				unitPrice = transportFee.add(serviceFee)
						.divide(weightOrVolume, 2, BigDecimal.ROUND_HALF_UP);
				// 设置新的费率
				bean.setUnitPrice(unitPrice);
			}
			// 运费 = 原运费 + 装卸费
			transportFee = transportFee.add(serviceFee);
			// 设置新的运费
			bean.setTransportFee(transportFee);
		}
	}

	/**
	 * 
	 * 获取重量或者体积
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-9 下午03:29:54
	 */
	private BigDecimal getWeightOrVolume(WaybillPanelVo bean) {
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
	 * 验证是否可以开装卸费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-10 下午03:39:11
	 */
	private void validataServiceFee(WaybillPanelVo bean) {
		BigDecimal serivceFee = bean.getServiceFee();

		if (serivceFee == null || serivceFee.longValue() == 0) {
			return;
		}
		BigDecimal transportFee = bean.getTransportFee();

		BigDecimal serviceFee2 = transportFee.multiply(getServiceFeeRate(bean));
		if (serivceFee.compareTo(serviceFee2) > 0) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFee"));
		}
	}

	/**
	 * 
	 * 获取装卸费费率
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-10 下午03:49:13
	 */
	private BigDecimal getServiceFeeRate(WaybillPanelVo bean) {
		BigDecimal serviceFeeRate = bean.getServiceFeeRate();

		if (serviceFeeRate == null) {
			ProductEntityVo productVo = bean.getProductCode();
			// 调用接口读取装卸费费率
			ServiceFeeRateDto dto = null;
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO);
			}else
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO);
			}

			if (dto == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFeeCalculate="));
			} else {
				// 判断是否存在装卸费费率
				if (dto.getServiceFeeRate() == null) {
					throw new WaybillValidateException(dto.getMessage());
				} else {
					serviceFeeRate = dto.getServiceFeeRate();
				}
			}
		}
		return serviceFeeRate;
	}
	
	/**
	 * 验证保价声明价值-MANA-409
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-1-1 上午11:01:19
	  * @param record
	  * @return
	 */
	private void validateInsurance(WaybillPanelVo bean) {
		//1非月结客户无订单发货,2非月结客户通过内部渠道（400、营业部下单） 保价声明价值等于0则抛出异常
		if (null == bean.getReceiveMethod()) {
			// 抛出异常信息
			throw new WaybillSubmitException(i18n.get("foss.gui.creating.listener.Waybill.exception.ReceiveMethodNotNull"));
		}
		
		/**
		 * 280747
		 * zhuxue
		 */
		//报价声明价值
		BigDecimal insuranceAmount =bean.getInsuranceAmount();
		// 如果内部带货则不用判断保价声明价值
		if (!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
				&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			//保价声明价值小于等于0
			if(insuranceAmount == null || insuranceAmount.compareTo(BigDecimal.ZERO) <= 0){
				//非月结客户
				if(bean.getChargeMode() == null || !bean.getChargeMode()){
					throw new WaybillSubmitException(i18n.get("foss.gui.creating.listener.Waybill.exception.noMonthEndInsuranceZero2"));
				}else{
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.exception.noMonthEndInsuranceZero3"));
				}
			}
		}
	}
	//查询是否为展会货
	public BigDecimal queryExhibitionByKeyWord(WaybillPanelVo bean){
		String cutString = null;
		String exhibition = null;
		List<ExhibitionKeywordEntity> exhibitionKeywordList = null;
		ExhibitionKeywordEntity exhibitionKeyword = new ExhibitionKeywordEntity();
		//收货地址为空，直接返回结果0
		//不为空则优先根据收货地址匹配展馆关键字信息，查询结果为空，则根据收货地址匹配展馆详细地址
		if(StringUtils.isBlank(bean.getReceiveCustomerAddress())) {
			return BigDecimal.ZERO;
		}else {
			//根据收货地址匹配展馆关键字信息
			exhibitionKeywordList=BaseDataServiceFactory.getBaseDataService().
					queryExhibitionKeywordListByTargetOrgName(bean.getReceiveCustomerAddress());
			if(exhibitionKeywordList!=null && exhibitionKeywordList.size()>0){
				BigDecimal bigDecimal = new BigDecimal(exhibitionKeywordList.size());
				return bigDecimal;
			}
			cutString = cutString(bean.getReceiveCustomerArea(),"-");
		}
		if(!StringUtils.isBlank(cutString)) {
			exhibitionKeyword.setExhibitionAddress(cutString+bean.getReceiveCustomerAddress());
		} else {
			return BigDecimal.ZERO;
		}
		//根据收货地址匹配展馆详细地址
		//exhibition = waybillService.isExhibitCargo(exhibitionKeyword);
		if(StringUtil.isEmpty(exhibition)) {
			return BigDecimal.ZERO;
		}
		BigDecimal bigDecimal = new BigDecimal(exhibition);
		return bigDecimal;
		
	}
	//对收货地址进行处理
	public String cutString(String str,String spilt) {
		StringBuffer bStr = new StringBuffer();
		if(!StringUtils.isBlank(spilt)) {
			String[] splitStrings = null;
			splitStrings=str.split(spilt);
			 if(splitStrings!=null && splitStrings.length>0) {
				 for (String s : splitStrings) {
					 bStr.append(s);
				}
			 }else {
				 bStr.append(str);
			 }
		 }
		return bStr.toString();
	}
	@Override
	public void setIInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}
}