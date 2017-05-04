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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/common/Common.java
 * 
 * FILE NAME        	: Common.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.common;

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
import java.util.Set;

import javax.annotation.Resource;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.binding.BindingAssociation;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.IBallValidateWidget;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillBindingListener;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.KeyForWaybillPanel;
import com.deppon.foss.module.pickup.common.client.ui.customer.BankAccountDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.QueryMemberDialog;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.common.client.utils.PropertyFile;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.EconomyVo;
import com.deppon.foss.module.pickup.common.client.vo.IdentityEffectivePlanVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.ShowPickupStationDialogAction;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.customer.EnterPackingInfoDialog;
import com.deppon.foss.module.pickup.creating.client.ui.editui.ConcessionsPanel.WaybillDiscountCanvas;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.CalculateCostsDialog;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 公共方法类，将共用的方法抽取出来统一调用，减少代码copy
 * 
 * @author 025000-FOSS-helong
 * @date 2012-11-28 上午11:00:17
 */
public class Common {

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(Common.class);

	private static final int  NUM_20000 = 20000;

	private static IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
	// 获得远程HessianRemoting接口
	private static IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
			.getService(IWaybillHessianRemoting.class);
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */
	
	//=====创建CustomerService/lianhe/2016年12月19日14:13:35====================
	private static ICustomerService customerService = new CustomerService();
	
	private static final double ZEROPOINTNINESIX = 0.96;
	
	private static final double ONEPOINTZEROFOUR = 1.04;
	
	private static final double ZEROPOINTNINE = 0.9;
	
	private static final double ONEPOINTONE = 1.1;

	private static final int NUM_170 = 170;

	private static final int NUM_65 = 65;

	/**
	 * 数据字典值服务
	 */
	@Resource
	private static IDataDictionaryValueDao pkpdataDictionaryValueDao;
	
	/**
	 * 错误验证
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
				String errors = error.getMessage();
				String[] messages = error.getMessage().split(WaybillConstants.SPLIT);
				if(messages != null) {
					error.setMessage(messages[0]);
				}
				if (WaybillConstants.SUCCESS.equals(error.getMessage()) 
						|| error.getMessage().startsWith("该线路历史时效兑现率低于") ) {
					i++;
				} else {
					//电话号码异常信息
					if(i18n.get("foss.gui.creating.listener.Waybill.telephoneCorrectFormat").equals(errors)){
						MsgBox.showError(errors);
						bool = false;
						break;
					}
					MsgBox.showError(error.getMessage());
					bool = false;
					break;
				}
			} else {
				MsgBox.showError(error.getMessage());
				bool = false;
				break;
			}
		}

		if (i == errorList.size()) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 
	 * 设置保存按钮与提交按钮可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:24:45
	 */
	public static void setSaveAndSubmitTrue(WaybillEditUI ui) {
		if (getOnLineState()) {
			// 暂存
			ui.buttonPanel.getBtnSave().setEnabled(true);
			// 提交
			ui.buttonPanel.getBtnSubmit().setEnabled(true);
			// 提交
			ui.billingPayPanel.getBtnSubmit().setEnabled(true);
			if(null!=ui.getPictureWaybillType() && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
			}
		} else {
			// 暂存
			ui.buttonPanel.getBtnSave().setEnabled(true);
			// 提交
			ui.buttonPanel.getBtnSubmit().setEnabled(false);
			// 提交
			ui.billingPayPanel.getBtnSubmit().setEnabled(false);
		}

	}

	/**
	 * 
	 * 设置保存按钮与提交按钮不可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:24:45
	 */
	public static void setSaveAndSubmitFalse(WaybillEditUI ui) {
		if (getOnLineState()) {
			// 暂存
			ui.buttonPanel.getBtnSave().setEnabled(true);
		} else {
			// 暂存
			ui.buttonPanel.getBtnSave().setEnabled(false);
		}
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
	}

	/**
	 * 
	 * 获取在线状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午01:43:11
	 * @return
	 */
	public static Boolean getOnLineState() {
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 将NULL值或者空值转换成数字0
	 * 
	 * @author 025000-FOSS-helong
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
	 * 
	 * 将NULL值或者空值转换成浮点0
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午06:10:49
	 */
	public static BigDecimal nullOrEmptyToBigDecimal(String str) {
		if (str == null || "".equals(str)) {
			return BigDecimal.ZERO;
		} else {
			return new BigDecimal(str);
		}
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
	 * 
	 * 校验List对象是否为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-28 上午10:45:33
	 */
	public static <E> Boolean notNull(List<E> list) {
		if (list == null) {
			return false;
		} else {
			if (list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 获取产品价格查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public static GuiQueryBillCalculateDto  getQueryParamCollection(WaybillPanelVo bean, String yesOrNo) {
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		if(bean.getFreightMethod() != null )
			queryDto.setCombBillTypeCode(bean.getFreightMethod().getValueCode());
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		if(bean.getCustomerPickupOrgCode() != null){
			queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE			
		}else{
			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.ChooseCustomerPickupOrg"));
		}
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			queryDto.setGoodsCode(bean.getAirGoodsType().getValueCode());// 货物类型CODE
		}

		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
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
		//保价费率
		queryDto.setFeeRate(bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000)));
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setKilom(bean.getKilometer());//设置公里数
		if(bean.getIsEconomyGoods()!=null&&bean.getIsEconomyGoods()){
			queryDto.setEconomySince(FossConstants.ACTIVE);
		}else{
			queryDto.setEconomySince(FossConstants.INACTIVE);
		}
		//最终配载部门(计算偏线中转费时用得到)
		queryDto.setLastOrgCode(bean.getLastLoadOrgCode());
		List<GuiQueryBillCalculateSubDto> priceEntities =new ArrayList<GuiQueryBillCalculateSubDto>();
		GuiQueryBillCalculateSubDto guiQueryBillCalculateSubDto= new GuiQueryBillCalculateSubDto();
		guiQueryBillCalculateSubDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntities.add(guiQueryBillCalculateSubDto);
		queryDto.setPriceEntities(priceEntities);
		/**
		 * 根据Dmana-9885将运单渠道导入queryDto中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-05上午08:45
		 */
		queryDto.setWaybillChannel(bean.getOrderChannel());
		//（FOSS20150818）RFOSS2015052602 保价阶梯费率 -- 206860
		queryDto.setCalDiscount(bean.isCalDiscount());
		return queryDto;
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
	 * 组装航班日期
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-31 上午09:27:36
	 * @return
	 */
	public static String getFlyDate(FlightDto rowDto) {
		// 航班日期
		StringBuffer date = new StringBuffer();
		// 飞行日期
		String flyDate = "";
		if (FossConstants.YES.equals(rowDto.getFlyOnMonday())) {
			flyDate = flyDate + "1";
		}

		if (FossConstants.YES.equals(rowDto.getFlyOnTuesday())) {
			flyDate = flyDate + "2";
		}

		if (FossConstants.YES.equals(rowDto.getFlyOnWednesday())) {
			flyDate = flyDate + "3";
		}

		if (FossConstants.YES.equals(rowDto.getFlyOnThursday())) {
			flyDate = flyDate + "4";
		}

		if (FossConstants.YES.equals(rowDto.getFlyOnFriday())) {
			flyDate = flyDate + "5";
		}

		if (FossConstants.YES.equals(rowDto.getFlyOnSaturday())) {
			flyDate = flyDate + "6";
		}

		if (FossConstants.YES.equals(rowDto.getFlyOnSunday())) {
			flyDate = flyDate + "7";
		}

		if (rowDto.getPlanLeaveTime() != null) {
			date.append(DateUtils.getHourMinute(rowDto.getPlanLeaveTime()));
		}

		if (rowDto.getPlanArriveTime() != null) {
			date.append("|");
			date.append(DateUtils.getHourMinute(rowDto.getPlanArriveTime()));
			date.append("|");
		}

		date.append(flyDate);

		return date.toString();
	}

	/**
	 * 
	 * 整车设置开单界面编辑状态为可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 上午11:48:15
	 */
	public static void setUIEnableTrue(WaybillPanelVo bean,WaybillEditUI ui) {
		UIUtils.enableUI(ui);
		ui.buttonPanel.getBtnPrint().setEnabled(false);// 运单打印
		ui.buttonPanel.getBtnPreview().setEnabled(false);// 运单打印预览
		ui.buttonPanel.getBtnPrintLabel().setEnabled(false);// 打印标签
		setSaveAndSubmitFalse(ui);
		// 司机工号
		ui.basicPanel.getTxtDriverNumber().setEnabled(false);
		// 接货费
		ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
		// 手写输入金额
		ui.billingPayPanel.getTxtHandWriteMoney().setEnabled(false);
		// 收货客户编码
		ui.consigneePanel.getTxtReceiveCustomerCode().setEnabled(false);
		// 发货客户编码
		ui.consignerPanel.getTxtDeliveryCustomerCode().setEnabled(false);
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			// 提货网点
			ui.pictureTransferInfoPanel.getTxtPickBranch().setEnabled(false);
			// 合票方式
			ui.pictureTransferInfoPanel.getCombFreightMethod().setEnabled(false);
			// 航班类别
			ui.pictureTransferInfoPanel.getCombFlightNumberType().setEnabled(false);
			// 航班时间
			ui.pictureTransferInfoPanel.getTxtFlightShift().setEnabled(false);
			// 预配线路
			ui.pictureTransferInfoPanel.getTxtPredictLine().setEnabled(false);

			ui.pictureCargoInfoPanel.getTxtTotalPiece().requestFocus();
			// 货物类型
			//ui.pictureCargoInfoPanel.getRbnA().setEnabled(false);
			// 货物类型
			//ui.pictureCargoInfoPanel.getRbnB().setEnabled(false);
			// 大车直送
			ui.pictureCargoInfoPanel.getChbCarThrough().setEnabled(false);
			// 储运事项
			ui.pictureCargoInfoPanel.getTxtTransportationRemark().setEnabled(false);
			// 整车贵重物品不可编辑
			ui.pictureCargoInfoPanel.getChbValuable().setEnabled(false);
		}else{
			// 提货网点
			ui.transferInfoPanel.getTxtPickBranch().setEnabled(false);
			// 合票方式
			ui.transferInfoPanel.getCombFreightMethod().setEnabled(false);
			// 航班类别
			ui.transferInfoPanel.getCombFlightNumberType().setEnabled(false);
			// 航班时间
			ui.transferInfoPanel.getTxtFlightShift().setEnabled(false);
			// 预配线路
			ui.transferInfoPanel.getTxtPredictLine().setEnabled(false);

			// 货物类型
			//ui.cargoInfoPanel.getRbnA().setEnabled(false);
			// 货物类型
			//ui.cargoInfoPanel.getRbnB().setEnabled(false);
			// 大车直送
			ui.cargoInfoPanel.getChbCarThrough().setEnabled(false);
			// 储运事项
			ui.cargoInfoPanel.getTxtTransportationRemark().setEnabled(false);
			ui.cargoInfoPanel.getChbValuable().setEnabled(false);
		}
		// 收款人
		ui.incrementPanel.getTxtPayee().setEnabled(false);
		// 收款账号
		ui.incrementPanel.getTxtBankAccount().setEnabled(false);
		// 代收货款金额
		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
		// 代收货款类型
		ui.incrementPanel.getCombRefundType().setEnabled(false);
		// 增值服务费
		ui.billingPayPanel.getTxtIncrementCharge().setEnabled(false);
		// 优惠合计
		ui.billingPayPanel.getTxtPromotionTotal().setEnabled(false);
		// 预付金额
		ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);
		// 到付金额
		ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
		// 总费用
		ui.billingPayPanel.getTxtTotalCharge().setEnabled(false);
		// liding add for NCI
		// 交易流水号
		ui.billingPayPanel.getTransactionSerialNumber().setEnabled(false);
		// 整车报价
		ui.billingPayPanel.billingPayBelongPanel.getTxtWholeVehicleAppfee().setEnabled(false);
		// 整车贵重物品不可编辑
		ui.cargoInfoPanel.getChbValuable().setEnabled(false);
		//其他费用
		ui.incrementPanel.getTxtOtherCharge().setEnabled(false);
		
		selfPickup(bean,ui);
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
	 * 
	 * 设置银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午08:11:49
	 */
	public static void setBankInfo(WaybillPanelVo bean, WaybillEditUI ui, IWaybillService waybillService) {
		DataDictionaryValueVo vo = bean.getRefundType();
		if (vo != null && vo.getValueCode()!=null) {
			if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.notDeliveryCustomerCode"));
			} else {
				List<CusAccountEntity> list = queryBankAccount(bean, waybillService);
				if (CollectionUtils.isNotEmpty(list)) {
					BankAccountDialog dialog = new BankAccountDialog(list);
					// 剧中显示弹出窗口
					WindowUtil.centerAndShow(dialog);

					CusAccountEntity entity = dialog.getCusAccountEntity();
					//设置银行账户
					setAccount(bean, entity);
				} else {
					if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())) {
						MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.nullBankAccountForOneDay"));
					}else{
						MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.nullBankAccount"));
					}
				}
			}
		} else {
			cleanBankInfo(bean);
		}
	}

	private static void setAccount(WaybillPanelVo bean, CusAccountEntity entity) {
		if (entity != null) {
			if (entity.getAccountNature() == null || "".equals(entity.getAccountNature())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.common.exception.nullAccountNature"));
			} else {
				bean.setOpenBank(entity);
				// 即日退只能选择对私账户
				if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())) {
					if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT.equals(entity.getAccountNature())) {
						bean.setAccountName(entity.getAccountName());// 开户人名称
						bean.setAccountCode(entity.getAccountNo());// 开户账号
						bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
					} else {
						MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.refundTypeOneDay"));
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
	 * 
	 * 设置银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午08:11:49
	 */
	public static void validateBankInfo(WaybillPanelVo bean, WaybillEditUI ui, IWaybillService waybillService) {
		DataDictionaryValueVo vo = bean.getRefundType();
		if (vo != null && vo.getValueCode() != null) {
			if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.notDeliveryCustomerCode"));
			} else {
					CusAccountEntity entity = bean.getOpenBank();
					if (entity != null) {
						if (entity.getAccountNature() == null || "".equals(entity.getAccountNature())) {
							MsgBox.showInfo(i18n.get("foss.gui.creating.common.exception.nullAccountNature"));
						} else {
							//bean.setOpenBank(entity);
							// 即日退只能选择对私账户
							if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())) {
								if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT.equals(entity.getAccountNature())) {
									bean.setAccountName(entity.getAccountName());// 开户人名称
									bean.setAccountCode(entity.getAccountNo());// 开户账号
									bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
								} else {
									MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.refundTypeOneDay"));
//									bean.setCodAmount(BigDecimal.ZERO);
//									// 判断是否可以开即日退
//									setRefundType(bean, ui);
//									bean.setOpenBank(null);
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

	//DP-FOSS zhaoyiqing 20161026 开单配合CUBC校验银行信息
	public static void validateBankInfoCUBC(WaybillPanelVo bean){
		DataDictionaryValueVo vo = bean.getRefundType();
		if (vo != null && vo.getValueCode() != null) {
			if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.notDeliveryCustomerCode"));
			} else {
				CusAccountEntity entity = bean.getOpenBank();
				if (entity != null) {
					if (entity.getAccountNature() == null || "".equals(entity.getAccountNature())) {
						MsgBox.showInfo(i18n.get("foss.gui.creating.common.exception.nullAccountNature"));
					} else {
						// 即日退验证对私账户已在上个方法验证，这个方法为了校验银行信息是否全
						if (StringUtil.equals(bean.getRefundType().getValueCode(), WaybillConstants.REFUND_TYPE_ONE_DAY)) {
							if(StringUtil.equals(entity.getAccountNature(),DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT)){
								//校验参数
								validateRefundBankInfo(bean, entity);
							}else{
								//无需提示，提示已在上部分给出
								bean.setAccountBank("");
								bean.setAccountCode("");
								bean.setAccountName("");
							}
						}
						else if(StringUtil.equals(bean.getRefundType().getValueCode(), WaybillConstants.REFUND_TYPE_THREE_DAY)){
							validateRefundBankInfo(bean, entity);
						}
						else if(StringUtil.equals(bean.getRefundType().getValueCode(),WaybillConstants.REFUND_TYPE_VALUE)) {
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

	//DP-FOSS zhaoyiqing 343617 20161011 退款方式为当日退或者三日退的校验逻辑逻辑
	private static void validateRefundBankInfo(WaybillPanelVo bean, CusAccountEntity entity) {
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
			bean.setAccountName(entity.getAccountName());// 开户人名称
			bean.setAccountCode(entity.getAccountNo());// 开户账号
			bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
			return;
		}
		bean.setAccountBank("");
		bean.setAccountCode("");
		bean.setAccountName("");
	}

	/**
	 * 
	 * 设置退款类型为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	public static void setRefundType(WaybillPanelVo bean, WaybillEditUI ui) {
		int size = ui.getCombRefundTypeModel().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = null;
			vo = (DataDictionaryValueVo) ui.getCombRefundTypeModel().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				ui.incrementPanel.getCombRefundType().setSelectedItem(vo);
			}
		}
	}

	/**
	 * 
	 * 查询客户银行账号信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:50:56
	 */
	public static List<CusAccountEntity> queryBankAccount(WaybillPanelVo bean, IWaybillService waybillService) {
		List<CusAccountEntity> list = waybillService.queryBankAccountByCode(bean.getDeliveryCustomerCode());

		if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())) {
			if (list != null && list.size() > 0) {
				List<CusAccountEntity> newList = new ArrayList<CusAccountEntity>();
				for (int i = 0; i < list.size(); i++) {
					BankEntity bank = waybillService.queryBankByCode(list.get(i).getBankCode());
					if (bank != null) {
						if (FossConstants.YES.equals(bank.getIntraDayType())) {
							newList.add(list.get(i));
						}
					}
				}
				if (newList.size() == 0) {
					return null;
				} else {
					return newList;
				}
			} else {
				return list;
			}
		} else {
			return list;
		}
	}

	/**
	 * 
	 * 送货进仓
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-15 下午05:55:20
	 */
	public static void deliverStorge(WaybillPanelVo bean, WaybillEditUI ui, IWaybillService waybillService) {
		String code = bean.getReceiveMethod().getValueCode();
		// 判断是否自提
		if (WaybillConstants.DELIVER_STORAGE.equals(code) || WaybillConstants.DELIVER_INGA_AIR.equals(code)) {

			if (bean.getCustomerPickupOrgCode() != null) {
				String remark = bean.getTransportationRemark();
				if(remark==null){
					remark = "";
				}
				bean.setTransportationRemark(remark + WaybillConstants.DELIVER_STORAGE_NAME);

				
				List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryOtherChargeParam(bean, "SHJCF"));
//				OtherChargeVo otherVo = getDeliverStorgeCharge(bean, list);
				// 添加返单费用到其他费用表格
//				String chargeName = addOtherCharge(data, otherVo, bean);
				
				if(list!=null && list.size()>0){
					ValueAddDto dto = list.get(0);
					DeliverChargeEntity deliver = new DeliverChargeEntity();
					
					// 是否激活
					deliver.setActive(FossConstants.ACTIVE);
					dto.getCaculateFee();
					
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
					
					// 费用编码
					deliver.setCode(dto.getSubType());
					//费用名称
					deliver.setName(dto.getSubTypeName());
					
					BigDecimal chargeTotal = BigDecimal.ZERO;
					// 送货费合计 = 送货费+上楼费/进仓费/超远派送费
					chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
					bean.setDeliveryGoodsFee(chargeTotal);
					// 画布-送货费
					bean.setDeliveryGoodsFeeCanvas(chargeTotal.toString());
					// 计算的送货费
					bean.setCalculateDeliveryGoodsFee(chargeTotal);
					
					List<DeliverChargeEntity> delivetList = bean.getDeliverList();
					if (delivetList == null) {
						delivetList = new ArrayList<DeliverChargeEntity>();
					}
					// 将新的派送费添加到派送费集合
					delivetList.add(deliver);
					// 将派送费集合进行更新
					bean.setDeliverList(delivetList);
				}
				
				// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用

			}
		} else {
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
			List<OtherChargeVo> data = model.getData();
			if (data != null && !data.isEmpty()) {
				// 将已有的送货进仓费用从其他费用表格中删除
				deleteOtherCharge(data, bean, bean.getDeliveryStorgeName());
				ui.incrementPanel.setChangeDetail(data);

			}

			String remark = bean.getTransportationRemark();
			if (remark != null) {
				remark = remark.replace(WaybillConstants.DELIVER_STORAGE_NAME, "");
				bean.setTransportationRemark(remark);
			}
		}
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
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());
		if(bean.getGoodsWeightTotal() == null)
		{
			queryDto.setWeight(BigDecimal.ZERO);// 重量
		}else
		{
			queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		}

		if(bean.getGoodsVolumeTotal() == null)
		{
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}else
		{
			queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		}
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(type);
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		if (WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {// 判断有无返单类型
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		
		} else {
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);// 计价条目CODE
			queryDto.setBillTime(bean.getBillTime());// 开单时间															// 签收回单
		}
		queryDto.setPricingEntryName(null);// 计价名称
//		queryDto.setBillTime(bean.getBillTime());// 开单时间
		//定价体系优化项目POP-469
		if(bean.getPickupToDoor() != null && bean.getPickupToDoor()){
			queryDto.setIsReceiveGoods(FossConstants.YES);
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);
		}
		//定价体系优化项目POP-469--因为规则引擎绑定了保费，但是签收回单调用规则引擎时，没有传保费，代收货款
		 Map<String,GuiQueryBillCalculateSubDto> valuedtos=new HashMap<String,GuiQueryBillCalculateSubDto>();
		 //保费
		 GuiQueryBillCalculateSubDto bfDto  = new GuiQueryBillCalculateSubDto();
		 bfDto.setOriginnalCost(bean.getInsuranceAmount());
		 valuedtos.put(PriceEntityConstants.PRICING_CODE_BF, bfDto);
		 //代收
		 GuiQueryBillCalculateSubDto hkDto  = new GuiQueryBillCalculateSubDto();
		 hkDto.setOriginnalCost(bean.getCodAmount());
		 valuedtos.put(PriceEntityConstants.PRICING_CODE_HK, hkDto);
		 queryDto.setValuedtos(valuedtos);
		return queryDto;
	}

	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	public static OtherChargeVo getDeliverStorgeCharge(WaybillPanelVo bean, List<ValueAddDto> list) {
		ValueAddDto dto = new ValueAddDto();
		OtherChargeVo vo = new OtherChargeVo();
		if (list != null) {
			if (!list.isEmpty()) {
				dto = list.get(0);
				// 费用编码
				vo.setCode(dto.getSubType());
				// 名称
				vo.setChargeName(dto.getSubTypeName());
				// 归集类别
				vo.setType(dto.getBelongToPriceEntityName());
				// 描述
				vo.setDescrition(dto.getPriceEntityCode());
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
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.nullValueAddDto"));
			}
		} else {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.nullValueAddDto"));
		}
		return vo;
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
	 * 如果选择的返单类型为无返单，那么需要将之前存在的返单费用删除
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 上午10:44:29
	 */
	public static void deleteOtherCharge(List<OtherChargeVo> data, WaybillPanelVo bean, String name) {
		for (int i = 0; i < data.size(); i++) {
			OtherChargeVo otherVo = data.get(i);
			String chargeName =otherVo.getChargeName() ;
			if(StringUtil.isNotEmpty(chargeName)){
				// 比较费用名称，判断是否存在重复的返单费用
				if (chargeName.equals(name) 	
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
	 * 
	 * 设置装卸费编辑状态-月结客户不允许开装卸费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午07:14:50
	 */
	public static void setServiceChargeEnabled(String preferentialType,
			Boolean bool, WaybillEditUI ui) {
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
				ui.incrementPanel.getTxtServiceCharge().setEnabled(true);
			}

		}

	}

	/**
	 * 设置导入PDA订单时，值改变就设置颜色的控件与值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-31 上午8:41:41
	 */
	public static Map<JTextField, String> getChangeColorTxt(WaybillEditUI ui, WaybillPanelVo vo) {
		// 保存键值对
		Map<JTextField, String> map = new HashMap<JTextField, String>();

		// 手机
		map.put(ui.consigneePanel.getTxtConsigneeMobile(), StringUtil.defaultIfNull(vo.getReceiveCustomerMobilephone()));
		// 电话
		map.put(ui.consigneePanel.getTxtConsigneePhone(), StringUtil.defaultIfNull(vo.getReceiveCustomerPhone()));
		// 客户名称
		map.put(ui.consigneePanel.getTxtReceiveCustomerName(), StringUtil.defaultIfNull(vo.getReceiveCustomerName()));
		// 客户编码
		map.put(ui.consigneePanel.getTxtReceiveCustomerCode(), StringUtil.defaultIfNull(vo.getReceiveCustomerCode()));
		// 联系人名称
		map.put(ui.consigneePanel.getTxtConsigneeLinkMan(), StringUtil.defaultIfNull(vo.getReceiveCustomerContact()));
		// 行政区域
		map.put(ui.consigneePanel.getTxtConsigneeArea(), StringUtil.defaultIfNull(vo.getReceiveCustomerArea()));
		// 详细地址
		map.put(ui.consigneePanel.getTxtConsigneeAddress(), StringUtil.defaultIfNull(vo.getReceiveCustomerAddress()));

		return map;
	}

	/**
	 * 将Dto转换为字符串对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-23 下午3:27:57
	 */
	public static String convertReceiveCustomerArea(AddressFieldDto dto) {
		StringBuffer sb = new StringBuffer();
		if (dto != null) {
			String s = "-";
			sb.append(dto.getProvinceName()).append(s).append(dto.getCityName()).append(s).append(dto.getCountyName());
		}

		return sb.toString();
	}

	/**
	 * 比较控件值，若值有变化则设置控件颜色为红色
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-31 上午8:30:03
	 */
	public static void setForegroundColor(WaybillEditUI ui, WaybillPanelVo bean) {
		// 变更前的键值对
		Map<JTextField, String> last = Common.getChangeColorTxt(ui, bean);
		// 变更后的键值对
		Map<JTextField, String> old = bean.getChangeColorTxt();

		// 遍历键，通过键取值
		Set<JTextField> lastSet = last.keySet();
		for (JTextField lastKey : lastSet) {
			Set<JTextField> oldSet = old.keySet();
			for (JTextField oldKey : oldSet) {
				// 判断前后键是否一致
				if (oldKey.equals(lastKey)) {
					// 判断值是否相等
					if (!StringUtil.defaultIfNull(old.get(oldKey)).equals(lastKey.getText())) {
						// 设置该控件前景色为红色
						lastKey.setForeground(Color.RED);
					} else {
						lastKey.setForeground(Color.BLACK);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 空运提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午08:47:33
	 */
	public static void airReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, WaybillEditUI ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();
		//选择运输性质之前的提货方式 liyongfei-DMANA-2604
		String lastReceiveMethodCode = null;
		if(bean!=null && bean.getReceiveMethod()!=null){
			lastReceiveMethodCode = bean.getReceiveMethod().getValueCode();
		}
		if(lastReceiveMethodCode==null){
			lastReceiveMethodCode = WaybillConstants.AIR_SELF_PICKUP;
		}
		//默认的提货方式
		DataDictionaryValueVo object = new DataDictionaryValueVo();
		//变更前的提货方式
		DataDictionaryValueVo method = new DataDictionaryValueVo();
		//变更前的提货方式在变更后的提货方式列表中是否存在
		boolean isExist = false;
		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsAir();
		//运输性质为精准空运时触发监听--提货方式隐藏免费自提【徐思衍-20160808-空运提货方式优化】
		List<DataDictionaryValueEntity> dellist = new ArrayList<DataDictionaryValueEntity>();
		for(DataDictionaryValueEntity data : list) {
			if(WaybillConstants.DELIVER_FREE.equals(data.getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(data.getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(data.getValueCode())){
				dellist.add(data);
			}
		}
		list.removeAll(dellist);
		//DMANA-4923  FOSS开单提货方式隐藏“免费送货”
		for (int i = 0; i < list.size(); i++) {
			if(WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode())){
				list.remove(list.get(i));
			}
		}
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			/**
			 * 判断合票方式是否为合大票
			 */
			if (bean.getFreightMethod()!=null && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP.equals(bean.getFreightMethod().getValueCode())) {
				/**
				 * 判断提货方式是否为机场自提
				 */
				if(!WaybillConstants.AIRPORT_PICKUP.equals(vo.getValueCode())){
					pikcModeModel.addElement(vo);
				}
			}else{
				pikcModeModel.addElement(vo);
			}			
			// 设置提货方式默认值
			if (WaybillConstants.AIR_SELF_PICKUP.equals(vo.getValueCode())) {
				object = vo;
			}
			if(lastReceiveMethodCode.equals(vo.getValueCode())){
				isExist = true;
				method = vo;
			}
		}
		if(!isExist){//若不存在变更前的提货方式，则把提货方式设置为默认值，否则使用变更前的提货方式
			bean.setReceiveMethod(object);
		}else{
			bean.setReceiveMethod(method);
		}
		//校验提货方式逻辑
		receiveMethodCheck(bean,ui);
	}

	/**
	 * 
	 * 验证只有合同客户才允许有免费送货
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午03:36:46
	 */
	public static void validateDeliverFree(WaybillPanelVo bean, WaybillEditUI ui) {
		if (WaybillConstants.DELIVER_FREE.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(bean.getReceiveMethod().getValueCode())) {
			if (bean.getAuditNo() == null || "".equals(bean.getAuditNo())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.nullAuditNo"));
				if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
						WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
					ui.pictureTransferInfoPanel.getCombPickMode().requestFocus();
				}else{
					ui.transferInfoPanel.getCombPickMode().requestFocus();
				}
				// 强行中断
				throw new WaybillValidateException("");

			} else {
				/**
				 * 现在只根据客户编码查询是否为合同客户，取消部门限制。
				 */				
				CusBargainVo vo=new CusBargainVo();
				vo.setChargeType(WaybillConstants.MONTH_END);
				vo.setCustomerCode(bean.getDeliveryCustomerCode());
				vo.setBillDate(new Date());
				vo.setBillOrgCode(bean.getReceiveOrgCode());			
				boolean  isOk = waybillService.isCanPaidMethod(vo);
				//CusBargainEntity eneity = waybillService.queryCusBargainByParams(bean.getAuditNo(), null);
				if (!isOk) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.nullAuditNo"));
					if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
							WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
						ui.pictureTransferInfoPanel.getCombPickMode().requestFocus();
					}else{
						ui.transferInfoPanel.getCombPickMode().requestFocus();
					}
					// 强行中断
					throw new WaybillValidateException("");
				}
			}
		}
	}
	
	/**
	 * 【新需求】：改为其他
	 * wutao
	 * 如果运输性质为门到门，那么提货方式不能为自提
	 * WaybillPanelVo bean, WaybillEditUI ui
	public static void validateDeliverTransportDTD(WaybillPanelVo bean, WaybillEditUI ui){
		
		if(WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())){
				MsgBox.showInfo(i18n.get("foss.gui.creating.common.exception.TransportTypeIsDTDNOTSelectedOwn"));
				ui.transferInfoPanel.getCombPickMode().requestFocus();
				// 强行中断
				throw new WaybillValidateException("");
			}
		}
	}
	 * @author 200945 - wutao
	 * 运输性质为门到门的时候，上门接货的按钮框是必须选中的；
	 * 运用途径：主要运用为电子运单导入的 时候出现；运输性质为：【门到门】的时候，上门接货的情况没有勾选，进行开单验证
	 * @param bean
	 * @param ui
	public static void validateSubmitDTDSelectedPickUP(WaybillPanelVo bean, WaybillEditUI ui){
		if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())){
			boolean isSelectedPickUp = ui.basicPanel.getCboReceiveModel().isSelected();
			if(isSelectedPickUp==false){
				MsgBox.showInfo(i18n.get("foss.gui.creating.waybillDescriptor.DTDMustSelectedPickUp"));
				ui.basicPanel.getCboReceiveModel().requestFocus();
				// 强行中断
				throw new WaybillValidateException("");
			}
		}
	}
	*/
	
	/**
	 * 
	 * 汽运提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午08:47:33
	 */
	public static void highwaysReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, WaybillEditUI ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();
		//选择运输性质之前的提货方式 liyongfei-DMANA-2604
		String lastReceiveMethodCode = null;
		if(bean!=null && bean.getReceiveMethod()!=null){
			lastReceiveMethodCode = bean.getReceiveMethod().getValueCode();
		}
		if(lastReceiveMethodCode==null){
			lastReceiveMethodCode=WaybillConstants.SELF_PICKUP;
		}
		pikcModeModel.removeAllElements();
		//变更前的提货方式在变更后的提货方式列表中是否存在
		boolean isExist = false;
		//默认的提货方式
		DataDictionaryValueVo object = new DataDictionaryValueVo();
		//变更前的提货方式
		DataDictionaryValueVo method = new DataDictionaryValueVo();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
		ProductEntityVo productVo = bean.getProductCode();
		if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productVo.getCode())){
			for(Iterator<DataDictionaryValueEntity> item=list.iterator() ; item.hasNext() ; ){
				DataDictionaryValueEntity dve = item.next();
				if(WaybillConstants.INNER_PICKUP.equals(dve.getValueCode())||WaybillConstants.DELIVER_INNER_PICKUP.equals(dve.getValueCode())){
					item.remove();
				}
			}
		}
		//DMANA-4923  FOSS开单提货方式隐藏“免费送货” 
		//合伙人开单 去除内部带货自提、送货上楼安装（家居）,内部带货送货 2016年4月5日 09:41:25 葛亮亮
		//重点添加 i-- 因为循环时直接使用 LIST 的大小，在直接REMOVE后，大小是变动的会直接影响到循环测试和GET获值需要将每次REMOVE后将i的值减一 2016年4月5日 10:47:30 葛亮亮
		for (int i = 0; i < list.size(); i++) {
			if(WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode())
			   || ((WaybillConstants.INNER_PICKUP.equals(list.get(i).getValueCode()) 
				    ||WaybillConstants.DELIVER_INNER_PICKUP.equals(list.get(i).getValueCode())
				    ||WaybillConstants.DELIVER_FLOOR.equals(list.get(i).getValueCode()))
				   &&BZPartnersJudge.IS_PARTENER)){
				list.remove(list.get(i));
				i--;
			}
			// 批量开单 提货方式不为 精准卡航、精准城运、精准汽运（长途）、精准汽运（短途） 时 则移除 内部带货送货 --sangwenhao
			if(WaybillConstants.DELIVER_INNER_PICKUP.equals(list.get(i).getValueCode()) && ui.isBatchWaybill() ){
				if(!(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productVo.getCode())
						|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productVo.getCode())
						|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productVo.getCode())
						|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productVo.getCode()))){
					list.remove(list.get(i));
					i--;
				}
			}else if(WaybillConstants.DELIVER_INNER_PICKUP.equals(list.get(i).getValueCode())){
					list.remove(list.get(i));
					i--;
			}
		}
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			pikcModeModel.addElement(vo);
			if (lastReceiveMethodCode.equals(vo.getValueCode()))// 变更前的提货方式在变更后的提货方式列表中存在
			{
				isExist = true;
				method = vo;
			}
			if(WaybillConstants.SELF_PICKUP.equals(vo.getValueCode())){//给默认提货方式赋值
				object = vo;
			}
		}
		if(!isExist){//若变更前的提货方式在变更后提货方式列表中不存在，则使用默认提货方式，否则使用变更前的提货方式
			bean.setReceiveMethod(object);
		}else{
			bean.setReceiveMethod(method);
		}
		//校验提货
		receiveMethodCheck(bean,ui);
	}
	
	/**
	 * 提货方式校验
	 * @author liyongfei-DMANA-2604
	 * @param bean
	 * @param ui
	 */
	public static void receiveMethodCheck(WaybillPanelVo bean, WaybillEditUI ui){
		if (bean.getReceiveMethod() != null) {
			//发票标记
			CommonUtils.setInvoiceType(bean,new Date());
			// 只允许合同客户才可以选择免费送货
			validateDeliverFree(bean, ui);

			// 内部带货
			innerPickup(bean, ui);
			// 各种自提
			selfPickup(bean, ui);
			// 设置其他费用
			calculateOtherCharge(ui, bean);

			// 在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
			validateCustomerPointBySelfPickup(bean, ui);

			// 送货进仓
			// Common.deliverStorge(bean, ui, waybillService);

			setSaveAndSubmitFalse(ui);
			
			/**
			 * 如果非送货时，公里数不可录入，且要清空
			 */
			if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIR_SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || 
					WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
				bean.setKilometer(null);
				if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
						WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
					// 公里数不可编辑
					ui.pictureTransferInfoPanel.getTxtKilometer().setEditable(false);
				}else{
					// 公里数不可编辑
					ui.transferInfoPanel.getTxtKilometer().setEditable(false);
				}
			}else{
				// 公里数可编辑
				//ui.transferInfoPanel.getTxtKilometer().setEditable(true);
				if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
						WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
					// 公里数不可编辑
					ui.pictureTransferInfoPanel.getTxtKilometer().setEditable(true);
				}else{
					// 公里数不可编辑
					ui.transferInfoPanel.getTxtKilometer().setEditable(true);
				}
			}									
			
			if(WaybillConstants.DELIVER_STORAGE.equals(bean.getReceiveMethod().getValueCode())){
				ui.billingPayPanel.getTxtDeliveryCharge().setEditable(true);
				ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);
			}
			
		}
		
		//提货方式改变后，清空送货是否手动修改过标识
		bean.setHandDeliveryFee(null);
	}
	
	/**
	 * 
	 * 整车默认提货方式
	 * 
	 * @author WangQianJin
	 * @date 2013-07-24
	 */
	public static void wholeVehicleReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, WaybillEditUI ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();

		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
		//DMANA-4923  FOSS开单提货方式隐藏“免费送货”
		for (int i = 0; i < list.size(); i++) {
			if(WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode())){
				list.remove(list.get(i));
			}
		}
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			pikcModeModel.addElement(vo);
			if (WaybillConstants.DELIVER_NOUP.equals(vo.getValueCode()))// 提货方式默认为：汽运送货(不含上楼)
			{
				bean.setReceiveMethod(vo);
			}
		}
	}

	/**
	 * 
	 * 清空目的站
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-22 下午04:48:08
	 */
	public static void setTargetEmpty(WaybillPanelVo bean) {
		// 提货网点
		bean.setCustomerPickupOrgCode(null);
		// 提货网点名称
		bean.setCustomerPickupOrgName("");
		// 目的站名称
		bean.setTargetOrgCode("");
		// 长短途
		bean.setLongOrShort(null);
		// 清除配载线路
		bean.setLoadLineName("");
	}

	/**
	 * 
	 * 设置装卸费费率，写入到本地文件
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午03:05:31
	 */
	public static void setServiceFeeReate(BigDecimal serviceFeeRate, String receiveOrgCode) {

		PropertyFile property = new PropertyFile();
		// 根据部门编号获取对应的装卸费率
		String value = property.readData(receiveOrgCode);
		if (StringUtils.isEmpty(value)) {
			// 根据部门编号写入装卸费率
			property.writeData(receiveOrgCode, serviceFeeRate.toString());
		} else {
			BigDecimal local = new BigDecimal(value);
			if (local.compareTo(serviceFeeRate) != 0) {
				// 根据部门编号写入装卸费率
				property.writeData(receiveOrgCode, serviceFeeRate.toString());
			}
		}
	}

	/**
	 * 
	 * 将焦点异动到运单控件
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午02:02:49
	 */
	public static void requestFocus(WaybillEditUI ui) {
		// 为了解决数据修改后，数据改变事件还未进行就已经暂存货提交
		ui.basicPanel.getTxtWaybillNO().requestFocus();
	}

	/**
	 * 离线导入、已开单导入、暂存导入等导入操作时需要重新走一遍设置走货线路的业务逻辑
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午4:49:37
	 */
	public static void setLoadLine(WaybillEditUI ui, WaybillPanelVo vo) {
		// 提货网点处理类
		ShowPickupStationDialogAction action = new ShowPickupStationDialogAction();
		// 设置UI界面
		action.setInjectUI(ui);
		// 设置线路
		action.setLoadLine(vo);
		// 设置空运配载
		action.setAirDeptEnabled(vo);
	}

	/**
	 * 产品类型做了修改
	 * 
	 * @param bean
	 * @param ui
	 */
	public static void innerPickup(WaybillPanelVo bean, WaybillEditUI ui) {
		// 判断是否内部带货自提
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
				//内部带货送货
				|| WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode()) ) {
			//发票标记
			bean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
			bean.setInvoice(WaybillConstants.INVOICE_02);
			// 修改发货联人和收货联系人名称
			ui.consignerPanel.getTxtConsignerLinkMan().setEnabled(false);
			ui.consignerPanel.getLblLinkMan().setText(i18n.get("foss.gui.creating.listener.Waybill.innerPickup.one"));
			ui.consignerPanel.getBtnConsignerDept().setVisible(true);
			//如果为弃货则不清空发货、收货联系人
			if (!WaybillConstants.WAYBILL_STATUS_GOODS_PENDING.equals(bean.getWaybillstatus())) {
				bean.setDeliveryCustomerContact("");// 发货联系人
				bean.setReceiveCustomerContact("");// 收货联系人
			}
			ui.consigneePanel.getTxtConsigneeLinkMan().setEnabled(false);
			ui.consigneePanel.getLblLinkMan().setText(i18n.get("foss.gui.creating.listener.Waybill.innerPickup.two"));
			ui.consigneePanel.getBtnConsigneeDept().setVisible(true);
			// 金额清零
			resetZero(bean, ui);

			bean.setReceiveMethodFlag(FossConstants.YES);
		} else {
			if (FossConstants.YES.equals(bean.getReceiveMethodFlag())) {
				// 修改发货联人和收货联系人名称
				ui.consignerPanel.getTxtConsignerLinkMan().setEnabled(true);
				ui.consignerPanel.getLblLinkMan().setText("*" + i18n.get("foss.gui.creating.listener.Waybill.innerPickup.three"));
				ui.consignerPanel.getBtnConsignerDept().setVisible(false);
				bean.setDeliveryCustomerContact("");// 发货联系人
				ui.consigneePanel.getTxtConsigneeLinkMan().setEnabled(true);
				ui.consigneePanel.getLblLinkMan().setText("*" + i18n.get("foss.gui.creating.listener.Waybill.innerPickup.four"));
				ui.consigneePanel.getBtnConsigneeDept().setVisible(false);
				bean.setReceiveCustomerContact("");// 收货联系人
				// 金额复原（重新生成金额）
				recover(ui);
				bean.setReceiveMethodFlag(FossConstants.NO);
				//清空发货信息
				Common.cleanDeliveryCustomerInfo(ui, bean, "", "");
				bean.setDeliveryCustomerName("");
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
	public static void selfPickup(WaybillPanelVo bean, WaybillEditUI ui) {
	    if(null == bean.getReceiveMethod()){
		return;
	    }
		String code = bean.getReceiveMethod().getValueCode();
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) 
				|| WaybillConstants.DELIVER_INNER_PICKUP.equals(code)
//				|| WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) 
				|| WaybillConstants.AIRPORT_PICKUP.equals(code) 
				|| WaybillConstants.INNER_PICKUP.equals(code)
				|| WaybillConstants.DELIVER_FREE.equals(code) 
				|| WaybillConstants.DELIVER_FREE_AIR.equals(code) 
				//当客户为月结客户时，则送货费可编辑
				|| (WaybillConstants.DELIVER_STORAGE.equals(code) && (bean.getChargeMode() == null || !bean.getChargeMode()))
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(false);
			// 送货费
			bean.setDeliveryGoodsFee(BigDecimal.ZERO);
			bean.setDeliveryGoodsFeeCanvas(BigDecimal.ZERO.toString());
		} else {
			//@author wutao修改
			//DEFECT-5064 ：项目环境：新产品需求，开单提货方式先选择自提，计算费用后，再更改提货方式为送货，此时送货费不能修改了。正常情况应该可以改大的。
			ui.billingPayPanel.getTxtDeliveryCharge().setEditable(true);
			ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);
		}
	}

	/**
	 * 
	 * 空运、偏线以及中转下线无法选择签收单返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:09:08
	 */
	public static void setReturnBill(WaybillPanelVo bean, WaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		if (null == productVo) {
			return;
		}

		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode()) || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			ui.incrementPanel.getCombReturnBillType().setEnabled(false);
			// 设置返单类型默认值
			Common.setReturnBillType(bean, ui.getCombReturnBillTypeModel());
			// 将返单费用设置到其他费用表格中
			Common.setReturnBillCharge(bean, ui);
		} else {
			ui.incrementPanel.getCombReturnBillType().setEnabled(true);
		}
	}

	/**
	 * 
	 * 设置返单费用到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	public static void setReturnBillCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//解决更改运输性质报空指针异常 liyongfei
		if (data == null || data.isEmpty()) {
			data = new ArrayList<OtherChargeVo>();
		}
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {
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
	 * 偏线与空运不能选择预付费保密
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-8 上午08:30:46
	 */
	public static void setSecretPrepaid(WaybillPanelVo bean, WaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		if (null == productVo) {
			return;
		}
		//偏线空运 、开单  付款方式为到付 或临时欠款 预付保密费都不能勾选
		DataDictionaryValueVo dataDictionaryValueVo = (DataDictionaryValueVo)ui.incrementPanel.getCombPaymentMode().getSelectedItem();
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode()) 
				|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())
//				|| (dataDictionaryValueVo != null && WaybillConstants.TEMPORARY_DEBT.equals(dataDictionaryValueVo.getValueCode()))
				|| (dataDictionaryValueVo != null && WaybillConstants.ARRIVE_PAYMENT.equals(dataDictionaryValueVo.getValueCode()))
			) {
			ui.incrementPanel.getChbSecrecy().setSelected(false);
			ui.incrementPanel.getChbSecrecy().setEnabled(false);
		} else {
			ui.incrementPanel.getChbSecrecy().setEnabled(true);
		}
	}

	/**
	 * 
	 * 清空其他费用列表
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午03:19:07
	 */
	public static void cleanOtherCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();
		// 清空表格
		ui.incrementPanel.setChangeDetail(voList);
		// 其他费用
		bean.setOtherFee(BigDecimal.ZERO);
		// 画布其他费用
		bean.setOtherFeeCanvas(BigDecimal.ZERO.toString());
		// 其他费用被清理之后，将返单类型设置为默认值
		setReturnBillType(bean, ui.getCombReturnBillTypeModel());
		//zxy 20130912 start 新增：返单类别内容设置到储运栏
		setTransportationRemark(bean);
		//zxy 20130912 end 新增：返单类别内容设置到储运栏
	}

	/**
	 * 
	 * 删除返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午03:52:12
	 */
	private static void deleteReturnBill(List<OtherChargeVo> data, WaybillPanelVo bean, WaybillEditUI ui) {
		if (data != null && !data.isEmpty()) {
			// 将已有的返单费用从其他费用表格中删除
			Common.deleteOtherCharge(data, bean, bean.getReturnBillChargeName());
			ui.incrementPanel.setChangeDetail(data);
		}
	}

	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private static OtherChargeVo getReturnBillCharge(WaybillPanelVo bean, List<ValueAddDto> list, List<OtherChargeVo> data, WaybillEditUI ui) {
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
	 * 根据运输性质的改变，改变提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午04:49:48
	 */
	public static void changePickUpMode(WaybillPanelVo bean, WaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			// 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
			Common.setAirPropertyToTrue(ui);
			// 空运提货方式
			Common.airReceiveMethod(waybillService, bean, ui);
			/**
			 * 判断合票方式是否为单独开单
			 */
			if(bean.getFreightMethod()!=null){
				/**
				 * 判断合票方式是否为单独开单
				 */
				if(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())){
					/**
					 * 创建提货方式对象
					 */
					DataDictionaryValueVo receiveMethod=new DataDictionaryValueVo();
					receiveMethod.setValueCode(WaybillConstants.AIRPORT_PICKUP);
					receiveMethod.setValueName(i18n.get("foss.gui.creating.transferInfoPanel.airportPickup.label"));
					bean.setReceiveMethod(receiveMethod);
					
					if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
							WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
						ui.pictureTransferInfoPanel.getCombPickMode().setEnabled(false);
					}else{
						ui.transferInfoPanel.getCombPickMode().setEnabled(false);
					}
				}else{
					if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
							WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
						ui.pictureTransferInfoPanel.getCombPickMode().setEnabled(true);
					}else{
						ui.transferInfoPanel.getCombPickMode().setEnabled(true);
					}
				}
			}
		} else {

			// 如果运输性质不为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
			Common.setAirPropertyToFalse(bean, ui);
			// 汽运提货方式
			Common.highwaysReceiveMethod(waybillService, bean, ui);
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				// 除精准卡航，其他运输性质提货网点均可编辑
				ui.pictureTransferInfoPanel.getCombPickMode().setEnabled(true);
			}else{
				// 除精准卡航，其他运输性质提货网点均可编辑
				ui.transferInfoPanel.getCombPickMode().setEnabled(true);
			}
		}
	}
	
	/**
	 * 
	 * 根据运输性质的改变，改变提货方式
	 * 
	 * @author WangQianJin
	 * @date 2013-07-17
	 */
	public static void changePickUpModeForSimple(WaybillPanelVo bean, CalculateCostsDialog ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			// 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
			Common.setAirPropertyToTrue(ui);
			// 空运提货方式
			Common.airReceiveMethod(waybillService, bean, ui);
			/**
			 * 判断合票方式是否为单独开单
			 */
			if(bean.getFreightMethod()!=null){
				/**
				 * 判断合票方式是否为单独开单
				 */
				if(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())){
					/**
					 * 创建提货方式对象
					 */
					DataDictionaryValueVo receiveMethod=new DataDictionaryValueVo();
					receiveMethod.setValueCode(WaybillConstants.AIRPORT_PICKUP);
					receiveMethod.setValueName(i18n.get("foss.gui.creating.transferInfoPanel.airportPickup.label"));
					bean.setReceiveMethod(receiveMethod);
					ui.getCombPickMode().setEnabled(false);
				}else{
					ui.getCombPickMode().setEnabled(true);
				}
			}
		} else {

			// 如果运输性质不为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
			Common.setAirPropertyToFalse(bean, ui);
			// 汽运提货方式
			Common.highwaysReceiveMethod(waybillService, bean, ui);
			// 除精准卡航，其他运输性质提货网点均可编辑
			ui.getCombPickMode().setEnabled(true);
		}
	}
	
	/**
	 * 
	 * 根据合票方式的改变，改变提货方式
	 * 
	 * @author WangQianJin
	 * @date 2013-04-23
	 */
	public static void changePickUpModeByHop(WaybillPanelVo bean, WaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			// 空运提货方式
			Common.airReceiveMethod(waybillService, bean, ui);
		}
	}

	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private static void setAirPropertyToTrue(WaybillEditUI ui) {
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			ui.pictureTransferInfoPanel.getCombFreightMethod().setEnabled(true);
			ui.pictureTransferInfoPanel.getCombFlightNumberType().setEnabled(true);
			//ui.pictureCargoInfoPanel.getRbnA().setVisible(false);
			//ui.pictureCargoInfoPanel.getRbnB().setVisible(false);
			ui.pictureCargoInfoPanel.getCombGoodsType().setVisible(true);
			ui.pictureCargoInfoPanel.getLabel15().setVisible(true);
		}else{
			ui.transferInfoPanel.getCombFreightMethod().setEnabled(true);
			ui.transferInfoPanel.getCombFlightNumberType().setEnabled(true);
			//ui.cargoInfoPanel.getRbnA().setVisible(false);
			//ui.cargoInfoPanel.getRbnB().setVisible(false);
			ui.cargoInfoPanel.getCombGoodsType().setVisible(true);
			ui.cargoInfoPanel.getLabel15().setVisible(true);
		}
	}


	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private static void setAirPropertyToFalse(WaybillPanelVo bean, WaybillEditUI ui) {
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			ui.pictureTransferInfoPanel.getCombFreightMethod().setEnabled(false);
			ui.pictureTransferInfoPanel.getCombFlightNumberType().setEnabled(false);
			//ui.pictureCargoInfoPanel.getRbnA().setVisible(true);
			//ui.pictureCargoInfoPanel.getRbnB().setVisible(true);
			ui.pictureCargoInfoPanel.getCombGoodsType().setVisible(false);
			ui.pictureCargoInfoPanel.getLabel15().setVisible(false);
		}else{
			ui.transferInfoPanel.getCombFreightMethod().setEnabled(false);
			ui.transferInfoPanel.getCombFlightNumberType().setEnabled(false);
			//ui.cargoInfoPanel.getRbnA().setVisible(true);
			//ui.cargoInfoPanel.getRbnB().setVisible(true);
			ui.cargoInfoPanel.getCombGoodsType().setVisible(false);
			ui.cargoInfoPanel.getLabel15().setVisible(false);
		}

		// 航班类型
		Common.setFlightNumberType(bean, ui);
		// 合票方式
		Common.setFreightMethod(bean, ui);
		// 航班时间
		bean.setFlightShift("");
	}
	/**
	 * wutao
	 * 当运输性质为门到门的时候，设置：上门接货的选中并且不可编辑
	 * @param bean
	 * @param ui
	 
	public static void setDTDPickUpToSelectedAndFalseEdit(WaybillPanelVo bean,
			WaybillEditUI ui) {
		if (!bean.getPickupCentralized()) {
			ProductEntityVo productVo = bean.getProductCode();
			if (productVo == null) {
				return;
			}
			if (ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR
					.equals(productVo.getCode())) {
				ui.basicPanel.getCboReceiveModel().setSelected(true);
				ui.basicPanel.getCboReceiveModel().setEnabled(false);
			}
		}
	}
*/
	/**
	 * 
	 * 设置航班类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private static void setFlightNumberType(WaybillPanelVo bean, WaybillEditUI ui) {
		int size = ui.getFlightNumberType().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			vo = (DataDictionaryValueVo) ui.getFlightNumberType().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setFlightNumberType(vo);
			}
		}
	}

	/**
	 * 
	 * 设置合票方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private static void setFreightMethod(WaybillPanelVo bean, WaybillEditUI ui) {
		int size = ui.getFreightMethod().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui.getFreightMethod().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setFreightMethod(vo);
			}
		}
	}

	/**
	 * 在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
	 * 
	 * @param bean
	 */
	public static void validateCustomerPointBySelfPickup(WaybillPanelVo bean, WaybillEditUI ui) {
		// 获得网点
		BranchVo customerPickupOrgCode = bean.getCustomerPickupOrgCode();

		if (customerPickupOrgCode != null && customerPickupOrgCode.getPickupSelf() != null && customerPickupOrgCode.getDelivery() != null) {

			if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {

				// 不支持自提
				if (!FossConstants.YES.equals(customerPickupOrgCode.getPickupSelf())) {

					// 清空目的站
					Common.setTargetEmpty(bean);
				}
			} else if (WaybillConstants.DELIVER_FREE.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_STORAGE.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_NOUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_FREE_AIR.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_NOUP_AIR.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP_AIR.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_INGA_AIR.equals(bean.getReceiveMethod().getValueCode())) {

				// 不支持送货上门
				if (!FossConstants.YES.equals(customerPickupOrgCode.getDelivery())) {

					// 清空目的站
					Common.setTargetEmpty(bean);
				}
			}
		}
	}

	/**
	 * 
	 * 内部带货，需要将金额相关全部清零
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午06:22:39
	 */
	public static void resetZero(WaybillPanelVo bean, WaybillEditUI ui) {
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
//
//		ui.billingPayPanel.getTxtPackCharge().setEnabled(false);// 包装费
//		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);// 代收货款
//		ui.incrementPanel.getTxtServiceCharge().setEnabled(false);// 装卸费
//		ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(false);// 送货费
//		ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);// 接货费
//		ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);// 保价声明价
//		ui.incrementPanel.getCombRefundType().setEnabled(false);// 退款类型
//		ui.incrementPanel.getChbSecrecy().setEnabled(false);// 预付费保密
//		ui.incrementPanel.getCombReturnBillType().setEnabled(false);// 返单类型
//		ui.incrementPanel.getTblOther().setEnabled(false);// 其他费用表格
//		ui.incrementPanel.getBtnNew().setEnabled(false);// 新增其他费用
//		ui.incrementPanel.getBtnDelete().setEnabled(false);// 删除其他费用
//		ui.incrementPanel.getTxtPromotionNumber().setEnabled(false);// 优惠编码
		// 查询会员
		ui.buttonPanel.getBtnSearchMember().setEnabled(false);
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
	 * 
	 * 不是内部带货则恢复编辑状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午08:22:45
	 */
	private static void recover(WaybillEditUI ui) {
//		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);// 包装费
//		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);// 代收货款
//		ui.incrementPanel.getTxtServiceCharge().setEnabled(true);// 装卸费
//		ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);// 送货费
//		ui.billingPayPanel.getTxtPickUpCharge().setEnabled(true);// 接货费
//		ui.incrementPanel.getTxtInsuranceValue().setEnabled(true);// 保价声明价
//		ui.incrementPanel.getCombRefundType().setEnabled(true);// 退款类型
//		ui.incrementPanel.getChbSecrecy().setEnabled(true);// 预付费保密
//		ui.incrementPanel.getCombReturnBillType().setEnabled(true);// 返单类型
//		ui.incrementPanel.getTblOther().setEnabled(true);// 其他费用表格
//		ui.incrementPanel.getBtnNew().setEnabled(true);// 新增其他费用
//		ui.incrementPanel.getBtnDelete().setEnabled(true);// 删除其他费用
//		ui.incrementPanel.getTxtPromotionNumber().setEnabled(true);// 优惠编码

		// 查询会员
		ui.buttonPanel.getBtnSearchMember().setEnabled(true);
		// 查询收货客户
		ui.consigneePanel.getBtnQuery().setEnabled(true);
		// 查询发货客户
		ui.consignerPanel.getBtnQuery().setEnabled(true);
	}

	/**
	 * 将弹出框中查询到的发货客户信息设置到开单界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午7:12:41
	 */
	public static void setQueryDeliveryCustomer(WaybillEditUI ui) {
		// 设置数据据
		IBinder<WaybillPanelVo> binder = ui.getBindersMap().get("waybillBinder");
		// 将获得的值传到界面上
		WaybillPanelVo waybillBean = binder.getBean();
		
		// 定义全局对象，用来判断该窗口是否已创建，已节约资源
		QueryMemberDialog panel = new QueryMemberDialog(waybillBean);
		// 居中显示弹出窗口
		WindowUtil.centerAndShow(panel);
		// 获得VO
		QueryMemberDialogVo memberBean = panel.getMemberVo();
		if (null == memberBean) {
			return;
		}

		//zxy 20131018 BUG-57580 start 新增：当客户发生改变时，设置重新计算送货费标志
		//标识为非手动修改过，这样计算总运费的值才会生效
		waybillBean.setHandDeliveryFee(BigDecimal.valueOf(0));
		//zxy 20131018 BUG-57580 end 新增：当客户发生改变时，设置重新计算送货费标志
		//存贮原发货客户编码
		String oldDeliveryCustomerCode=memberBean.getCustomerCode();
		getCustomerCode(ui,waybillBean, memberBean);
		/**
		 * Dmana-10888根据客户编码查询发票标记
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-06下午13:45
		 */
		selectIsCustCircle(waybillBean, memberBean);
		//获取最新报价费率和报价范围---206860
		/*if(waybillBean.getCustomerPickupOrgCode() != null && waybillBean.getGoodsWeightTotal() != null && waybillBean.getGoodsVolumeTotal() != null){
			Common.getInsuranceCharge(waybillBean, ui);
		}*/
		//将客户编码设置回去
		memberBean.setCustomerCode(oldDeliveryCustomerCode);
		Common.resetDeliverGoodsFee(waybillBean, ui);
		
		

		// 填充发货客户信息
		fillDeliveryCustomerInfo(ui, memberBean, waybillBean);
		//设置提交按钮置灰
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
		//事后阶梯折装卸费不可编辑
		boolean flag = iSLtDiscountBackInfo(waybillBean);
		if(!flag) {
			
			ui.incrementPanel.getTxtServiceCharge().setEnabled(false);
			//给提示装卸费不可编辑
			waybillBean.setServiceFee(BigDecimal.ZERO);
			waybillBean.setServiceFeeCanvas("0");
		}
	}

	private static void getCustomerCode(WaybillEditUI ui,WaybillPanelVo waybillBean,
			QueryMemberDialogVo memberBean) {
		if(StringUtils.isNotBlank(memberBean.getCustomerCode())){
			//判断发货客户是否在客户圈内，如果在就使用主客户的客户编码进行计价
			CustomerCircleNewDto customerCircleDto = waybillService.queryCustomerByCusCode(memberBean.getCustomerCode(), new Date(), "Y");
			//如果发货客户在客户圈就使用主客户的客户编码进行相关计费
			if(customerCircleDto !=null && "Y".equals(customerCircleDto.getIsCustCircle())
					&& customerCircleDto.getCustomerNewEntity() !=null && customerCircleDto.getCusBargainNewEntity() !=null && customerCircleDto.getCustomerCircleEntity() !=null){
				//将查询出来的合同实体插入bean
				waybillBean.setCusBargainNewEntity(customerCircleDto.getCusBargainNewEntity());
				//将查询出来的客户实体加入bean
				waybillBean.setCustomerNewEntity(customerCircleDto.getCustomerNewEntity());
				//将查询出来的客户圈实体加入bean
				waybillBean.setCustomerCircleEntity(customerCircleDto.getCustomerCircleEntity());
				//传入是否在客户圈内
				waybillBean.setIsCustCircle(customerCircleDto.getIsCustCircle() !=null ? customerCircleDto.getIsCustCircle() :"");
				//是否统一结算
				waybillBean.setStartCentralizedSettlement(customerCircleDto.getCustomerCircleEntity().getIsFocusPay() !=null ? customerCircleDto.getCustomerCircleEntity().getIsFocusPay() :"");
				//是否精准包裹
				waybillBean.setIsAccuratePackage(customerCircleDto.getCusBargainNewEntity().getIsAccuratePackage());
				//主客户编码
				waybillBean.setDeliveryCustomerCode(customerCircleDto.getCusBargainNewEntity().getCusCode());
				//获取最新报价费率和报价范围---206860
				if(waybillBean.getCustomerPickupOrgCode() != null && waybillBean.getGoodsWeightTotal() != null && waybillBean.getGoodsVolumeTotal() != null){
					Common.getInsuranceCharge(waybillBean, ui);
				}
			}else{
				//获取最新报价费率和报价范围---206860
				if(waybillBean.getCustomerPickupOrgCode() != null && waybillBean.getGoodsWeightTotal() != null && waybillBean.getGoodsVolumeTotal() != null){
					Common.getInsuranceCharge(waybillBean, ui);
				}
				waybillBean.setIsCustCircle("N");
			}
		}
	}

	private static void selectIsCustCircle(WaybillPanelVo waybillBean,
			QueryMemberDialogVo memberBean) {
		if("Y".equals(waybillBean.getIsCustCircle())){
			//发票标记
			memberBean.setInvoice(CommonUtils.setInvoice(waybillBean.getDeliveryCustomerCode()));
			//大客户标记
			memberBean.setIsBigCustomer(waybillBean.getCustomerNewEntity().getIsLargeCustomers() !=null ? waybillBean.getCustomerNewEntity().getIsLargeCustomers() :"");
			//合同编码
			memberBean.setAuditNo(waybillBean.getCusBargainNewEntity().getBargainCode() !=null ? waybillBean.getCusBargainNewEntity().getBargainCode() :"");
			//客户分群
			memberBean.setFlabelleavemonth(waybillBean.getCustomerNewEntity().getFlabelleavemonth() !=null ? waybillBean.getCustomerNewEntity().getFlabelleavemonth():"");
			//精准包裹权限
			memberBean.setIsAccuratePackage(waybillBean.getIsAccuratePackage() !=null ? waybillBean.getIsAccuratePackage() :"");
			//优惠类型
			memberBean.setPreferentialType(waybillBean.getCusBargainNewEntity().getPreferentialType() !=null ? waybillBean.getCusBargainNewEntity().getPreferentialType() :"");
			//付款方式
			if(StringUtils.isNotBlank(waybillBean.getCusBargainNewEntity().getChargeType()) && "MONTH_END".equals(waybillBean.getCusBargainNewEntity().getChargeType())){
				memberBean.setChargeMode(true);
			}else{
				memberBean.setChargeMode(false);
			}
			//是否统一结算
			memberBean.setCentralizedSettlement(waybillBean.getStartCentralizedSettlement() !=null ? waybillBean.getStartCentralizedSettlement() :"");
			//合同部门的标杆编码
			memberBean.setContractOrgCode(waybillBean.getCusBargainNewEntity().getUnifiedCode() !=null ? waybillBean.getCusBargainNewEntity().getUnifiedCode():"");
			//催款部门的标杆编码
			memberBean.setReminderOrgCode(waybillBean.getCusBargainNewEntity().getHastenfunddeptCode() !=null ? waybillBean.getCusBargainNewEntity().getHastenfunddeptCode():"");
			//合同部门的标杆名称
			memberBean.setContractOrgName(waybillBean.getCusBargainNewEntity().getApplicateOrgName() !=null ? waybillBean.getCusBargainNewEntity().getApplicateOrgName():"");
		}else{
			memberBean.setInvoice(CommonUtils.setInvoice(memberBean
					.getCustomerCode()));
		}
	}
	
	/**
	 * （FOSS20150818）RFOSS2015052602 保价阶梯费率
	 * 
	 * @author foss-206860
	 * 
	 * */
	public static void getInsuranceCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		if(bean.getGoodsWeightTotal() != null && bean.getGoodsVolumeTotal() != null 
				 && bean.getCustomerPickupOrgCode() != null
				 && bean.getProductCode() != null){
			//获取报价费率和报价费率范围
			getInsuranceRateAndRange(bean,ui);
		}
	}
	
	//专门作为保价费率的监听
	public static void getInsuranceRateListener(WaybillPanelVo bean) {
		bean.setCalDiscount(true);
		GuiResultBillCalculateDto gDto=getInsuranceRate(bean);
		bean.setInsuranceFee(gDto.getCaculateFee());
		bean.setCalDiscount(false);
		CalculateFeeTotalUtils.resetCalculateFee(bean);
	}
	
	//专门作为保价费率范围的获取设置
	public static void getInsuranceRateRange(WaybillPanelVo bean) {
		GuiResultBillCalculateDto gDto = null;
		if(bean != null){
			gDto=getInsuranceRate(bean);
		}
		if(bean != null && gDto !=null 
				&& gDto.getMinFeeRate() != null && gDto.getMaxFeeRate() != null){
			/**
			 * （FOSS20150818）RFOSS2015052602 保价阶梯费率
			 * @author foss-206860
			 * 
			 * */
			//默认保价费率
			BigDecimal feeRate = Common.nullBigDecimalToZero(gDto.getActualFeeRate()).setScale(NumberConstants.NUMBER_4,BigDecimal.ROUND_HALF_UP);
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
		}
	}
	/**
	 * （FOSS20150818）RFOSS2015052602 保价阶梯费率 
	 * 
	 * @author foss-206860
	 * 
	 * */
	private static void getInsuranceRateAndRange(WaybillPanelVo bean, WaybillEditUI ui) {
		//获取报价费率
		GuiResultBillCalculateDto gDto=getInsuranceRate(bean);
		setRate(gDto,bean,ui);
		CalculateFeeTotalUtils.resetCalculateFee(bean);
	}

	/**
	 * 填充发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午9:14:31
	 */
	public static void fillDeliveryCustomerInfo(WaybillEditUI ui, QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean) {
		// 对传入对象进行非空判断
		if (ui == null || memberBean == null || waybillBean == null) {
			return;
		}
		// 定义业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 手机号码
		waybillBean.setDeliveryCustomerMobilephone(memberBean.getMobilePhone());
		// 联系人
		waybillBean.setDeliveryCustomerContact(memberBean.getLinkman());
		// 电话
		waybillBean.setDeliveryCustomerPhone(memberBean.getPhone());
		// 地址
		waybillBean.setDeliveryCustomerAddress(memberBean.getAddress());
		// 地址备注
		waybillBean.setDeliveryCustomerAddressNote(memberBean.getCustAddrRemark());
		// 客户名称
		waybillBean.setDeliveryCustomerName(memberBean.getCustomerName());
		// 精准包裹
		waybillBean.setIsAccuratePackage(memberBean.getIsAccuratePackage());
		//大客户标记
		waybillBean.setDeliveryBigCustomer(memberBean.getIsBigCustomer());
		// 客户ID
		waybillBean.setDeliveryCustomerId(memberBean.getCustId());
		// 联系人ID
		waybillBean.setDeliveryCustomerContactId(memberBean.getConsignorId());
		// 合同编号
		waybillBean.setAuditNo(memberBean.getAuditNo());
		// 定义省市区县对象
		AddressFieldDto address = Common.getAddressFieldInfoByCode(ui, memberBean.getProvinceCode(), memberBean.getCityCode(), memberBean.getCountyCode());
		// 发货国家
		waybillBean.setDeliveryCustomerNationCode(address.getNationId());
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
		//设置优惠
		waybillBean.setPreferentialType(memberBean.getPreferentialType());
		//发票 标记
		waybillBean.setInvoice(memberBean.getInvoice());
		// 设置界面"省市区县DTO"对象:适用于通过手机号码等带出的省市区县
		ui.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(address);
		//带出对应的地址备注
		ui.getConsignerPanel().getTxtConsignerAddressNote().setText(waybillBean.getDeliveryCustomerAddressNote());
		if(WaybillConstants.INVOICE_01.equals(memberBean.getInvoice())){
			waybillBean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
			waybillBean.setInvoice(WaybillConstants.INVOICE_01);
		}else{
			waybillBean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
			waybillBean.setInvoice(WaybillConstants.INVOICE_02);
		}
		
		//设置参数到界面上去
		setDeliverySettleAndContactAndRemending(memberBean,waybillBean);
		
		/**
		 * 收货客户的发票标记依赖于收货客户的发票标记 重新设置收货客户信息是否发票标记
		 * @author Foss-278328-hujinyang
		 * @TIME 20160427
		 */
		resetReceiveCustomerInfoCentralizedSettlement(ui, memberBean, waybillBean);
		
		//重新设置付款方式;次做法 暫時有點瑕疵不用此方法 驗證
		//resetCombPaymentMode(waybillBean ,ui);
		
		//是否为大客户标识
		if(FossConstants.ACTIVE.equals(memberBean.getIsBigCustomer())){
			//设置大客户标记
			ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			//取消大客户标记
			ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		}
		//客户分群
		if (null != memberBean.getFlabelleavemonth()) {
			fillFlabelleavemonthData(waybillBean, ui, memberBean.getFlabelleavemonth());
		}

		// 设置装卸费是的编辑状态
		Common.setServiceChargeEnabled(memberBean.getPreferentialType(),memberBean.getChargeMode(), ui);
		// 客户信息发生改变，需要清空上一个客户的代收货款收款信息
		Common.cleanBankInfo(waybillBean);
		//判断发货客户是否是黑名单客户
		if(StringUtils.isNotEmpty(waybillBean.getDeliveryCustomerCode())){
			Common.isBlackList(waybillBean.getDeliveryCustomerCode(),waybillBean.getReceiveCustomerCode(),ui);
		}
		
		// 若客户类型为CRM正式客户，则发货联系不可修改
		if (WaybillConstants.CUSTOMER_TYPE_FORMAL.equals(StringUtil.defaultIfNull(memberBean.getCustomerType()))) {
			ui.getTxtConsignerLinkMan().setEnabled(false);
		} else {
			ui.getTxtConsignerLinkMan().setEnabled(true);
		}
		
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
		// 月结属性
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.GIANT_SINK.equals(waybillBean.getOrderChannel())
			&&!WaybillConstants.ALIBABA.equals(waybillBean.getOrderChannel())){
			waybillBean.setChargeMode(memberBean.getChargeMode());
		}
		//获取最新报价费率和报价范围---206860
		if(waybillBean.getCustomerPickupOrgCode() != null && waybillBean.getGoodsWeightTotal() != null && waybillBean.getGoodsVolumeTotal() != null){
			Common.getInsuranceCharge(waybillBean, ui);
		}
		
//		resetPackageFee(waybillBean,ui);
	}
	
//	/**
//	 * 重新计算包装费
//	 */
//	public static void resetPackageFee(WaybillPanelVo bean,WaybillEditUI ui){
//		//当木托个数不为空时，重新计算木托费
//				if(null != bean){
//					if(null != bean.getWood() && 0 != bean.getWood() && StringUtil.isNotEmpty(ui.getDialog().getTxtSalverGoodsPieces().getText())){
//						ValueAddDto packageCollectionSalver = Common.querySalverBillCalculateDto(bean,ui.getDialog().getTxtSalverGoodsPieces().getText());
//						if(packageCollectionSalver == null){
//							throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.ten"));
//						}else{
//							BigDecimal salverGoodsAmount = packageCollectionSalver.getCaculateFee();
//							ui.getDialog().getTxtSalverGoodsAmount().setText(salverGoodsAmount.toString());
//							bean.setSalverGoodsCharge(salverGoodsAmount);
//							
//							// 重新累加包装费
//							BigDecimal packageFee = BigDecimal.ZERO;
//							//木架费
//							if(bean.getStandCharge()!=null){
//								packageFee=packageFee.add(bean.getStandCharge());		
//							}
//							//木箱费
//							if(bean.getBoxCharge()!=null){
//								packageFee=packageFee.add(bean.getBoxCharge());
//							}
//							//木托
//							if(bean.getSalverGoodsCharge()!=null){
//								packageFee=packageFee.add(bean.getSalverGoodsCharge());
//							}
//							//纸纤
//							if(bean.getPackingTotle()!=null){
//								packageFee=packageFee.add(bean.getPackingTotle());
//							}
//							bean.setPackageFee(packageFee);
//							bean.setPackageFeeCanvas(packageFee.toString());
//							
//							
//							
//								
//						}
//					}
//				}
//		
//	}

	/**
	 * 重新设置收货人的发票标记
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-27
	 * @param ui
	 * @param memberBean
	 * @param waybillBean
	 */
	private static void resetReceiveCustomerInfoCentralizedSettlement(
			WaybillEditUI ui, QueryMemberDialogVo memberBean,
			WaybillPanelVo waybillBean) {
		//是否统一结算
		String arriveCentralizedSettlement = ui.getConsigneePanel().getTxtArriveCentralizedSettlement().getText();
		//合同部门code
//		String arriveContractOrgCode = ui.getConsigneePanel().getTxtArriveContractOrgCode().getText();
		//收货客户编码
		String receiveCustomerCode =waybillBean.getReceiveCustomerCode();
		//发货人发票标记
		String invoice = waybillBean.getInvoice();
		if(!StringUtils.isEmpty(arriveCentralizedSettlement) || !StringUtils.isEmpty(receiveCustomerCode)){ //如果设置了收货客户信息  更新收货客户统一结算状态
			//设置客户信息code
			CustomerQueryConditionDto customer = new CustomerQueryConditionDto();
			customer.setCustCode(receiveCustomerCode);
			
			//获取收货客户信息
			CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(customer);
			
			if (cusBargainEntity != null /*&& WaybillConstants.INVOICE_02.equals(invoice) && WaybillConstants.YES.equals(cusBargainEntity.getAsyntakegoodsCode())*/) {
				
				String settlement = WaybillConstants.YES.equals(cusBargainEntity.getAsyntakegoodsCode())?WaybillConstants.IS_NOT_NULL_FOR_AI: WaybillConstants.IS_NULL_FOR_AI;
				waybillBean.setArriveCentralizedSettlement(settlement);
				waybillBean.setArriveContractOrgCode(cusBargainEntity.getUnifiedCode());
				waybillBean.setArriveContractOrgName(CommonUtils.queryContractOrgName(cusBargainEntity.getUnifiedCode()));
				waybillBean.setArriveReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
			}else{
				waybillBean.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				waybillBean.setArriveContractOrgCode(null);
				waybillBean.setArriveContractOrgName(null);
				waybillBean.setArriveReminderOrgCode(null);
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
	 * @author 200945 - wutao 
	 * 重新设置付款方式:
	 * 业务逻辑：
	 * 若运单上发货客户的"是否统一结算"为【是】，则”付款方式“需限制只可选择【月结】或【临时欠款】。
	 * 否则：为原来默认的付款方式
	 * @param bean
	 */
	public static void resetCombPaymentMode(WaybillPanelVo bean,WaybillEditUI ui){
		List<DataDictionaryValueEntity> list = waybillService
				.queryPaymentMode();
		
		if(bean.getStartCentralizedSettlement().equals(WaybillConstants.IS_NOT_NULL_FOR_AI)){
			ui.getCombPaymentModeModel().removeAllElements();
			List<DataDictionaryValueEntity> list1 = filterPaymentMethod(list);
			for (DataDictionaryValueEntity dataDictionary : list1) {
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					ValueCopy.valueCopy(dataDictionary, vo);
					ui.getCombPaymentModeModel().addElement(vo);
			}
		}else{
			ui.getCombPaymentModeModel().removeAllElements();
			ui.getCombPaymentModeModel().removeAllElements();
			for (DataDictionaryValueEntity dataDictionary : list) {
				if(!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary.getValueCode())
					&& !WaybillConstants.CHECK.equals(dataDictionary.getValueCode())){
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					ValueCopy.valueCopy(dataDictionary, vo);
					ui.getCombPaymentModeModel().addElement(vo);
				}
			}
		}
	}
	/**
	 * 过滤付款方式
	 * @param list
	 * @return
	 */
	public static List<DataDictionaryValueEntity> filterPaymentMethod(List<DataDictionaryValueEntity> list){
		Iterator<DataDictionaryValueEntity> iter = list.iterator();
		while(iter.hasNext()){
			DataDictionaryValueEntity dataDictionary = iter.next();
			if(!WaybillConstants.TEMPORARY_DEBT.equals(dataDictionary.getValueCode()) 
					&&! WaybillConstants.MONTH_PAYMENT.equals(dataDictionary.getValueCode())){
				iter.remove();
			}
		}
		return list;
	}
	

	/**
	 * 将弹出框中查询到的收货客户信息设置到开单界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午7:13:39
	 */
	public static void setQueryReceiveCustomer(WaybillEditUI ui) {
		// 获得绑定对象
		IBinder<WaybillPanelVo> binder = ui.getBindersMap().get("waybillBinder");
		
		// 将获得的值传到界面上
		WaybillPanelVo bean = binder.getBean();
		QueryMemberDialog panel = new QueryMemberDialog(bean);
		// 居中显示弹出窗口
		WindowUtil.centerAndShow(panel);
		// 获得VO
		QueryMemberDialogVo memberBean = panel.getMemberVo();

		// 数据非空判断
		if (memberBean == null) {
			return;
		}
		//设置发票标记
		memberBean.setInvoice(CommonUtils.setInvoice(memberBean.getCustomerCode()));
		// 填充收货客户信息
		fillReceiveCustomerInfo(ui, memberBean, bean);
	}

	/**
	 * 填充收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午9:14:31
	 */
	public static void fillReceiveCustomerInfo(WaybillEditUI ui, QueryMemberDialogVo memberBean, WaybillPanelVo bean) {
		// 对传入对象进行非空判断
		if (ui == null || memberBean == null || bean == null) {
			return;
		}
		// 定义业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 手机号码
		bean.setReceiveCustomerMobilephone(memberBean.getMobilePhone());
		// 联系人
		bean.setReceiveCustomerContact(memberBean.getLinkman());
		// 电话
		bean.setReceiveCustomerPhone(memberBean.getPhone());
		// 地址备注
		bean.setReceiveCustomerAddressNote(memberBean.getCustAddrRemark());
		// 客户名称
		bean.setReceiveCustomerName(memberBean.getCustomerName());
		// 客户编码
		bean.setReceiveCustomerCode(memberBean.getCustomerCode());
		// 大客户标记
		bean.setReceiveBigCustomer(memberBean.getIsBigCustomer());
		// 客户ID
		bean.setReceiveCustomerId(memberBean.getCustId());
		// 联系人ID
		bean.setReceiveCustomerContactId(memberBean.getConsignorId());

		
		// 定义国家省市区县对象
		AddressFieldDto address = Common.getAddressFieldInfoByCode(ui, memberBean.getProvinceCode(), memberBean.getCityCode(), memberBean.getCountyCode());
		// 收货国家
		bean.setReceiveCustomerNationCode(address.getNationId());
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
		// 整车时地址只从约车时到达地址代入
		if(!bean.getIsWholeVehicle()){
		// 设置界面"省市区县DTO"对象:适用于通过手机号码等带出的省市区县
		ui.getConsigneePanel().getTxtConsigneeArea().setAddressFieldDto(address);
		ui.getConsigneePanel().getTxtConsigneeAddressNote().setText(bean.getReceiveCustomerAddressNote());
		// 地址
		bean.setReceiveCustomerAddress(memberBean.getAddress());
		}
		
		/**
		 * 收货客户的发票标记依赖于收货客户的发票标记
		 * @author Foss-278328-hujinyang
		 * @TIME 20160427
		 */
		//得到发货客户的发票标记
		String invoice = bean.getInvoice();
		//设置发货客户的发票标记
		memberBean.setInvoice(invoice);
		
		//设置参数到界面【是否统一结算】，【合同部门】
		setReceiveSettleAndContactAndRemending(memberBean,bean);
		//是否为大客户标识
		if(FossConstants.ACTIVE.equals(memberBean.getIsBigCustomer())){
			//设置大客户标记
			ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			//取消大客户标记
			ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		}

		// 客户信息发生改变，需要清空上一个客户的代收货款收款信息
		Common.cleanBankInfo(bean);
		//判断收货客户是否黑名单客户
		if(StringUtils.isNotEmpty(bean.getDeliveryCustomerCode())){
			Common.isBlackList(bean.getDeliveryCustomerCode(),bean.getReceiveCustomerCode(),ui);
		}
//		// 若客户类型为CRM正式客户，则发货联系不可修改
//		if (WaybillConstants.CUSTOMER_TYPE_FORMAL.equals(StringUtil.defaultIfNull(memberBean.getCustomerType()))) {
//			ui.getTxtConsigneeLinkMan().setEnabled(false);
//		} else {
//			ui.getTxtConsigneeLinkMan().setEnabled(true);
//		}
		
		// 设置接送货地址ID
		bean.setContactAddressId(memberBean.getAddressId());
	}

	/**
	 * 业务逻辑：
	 * 1、收货客户的“是否统一结算”、“合同部门”根据客户编码从客户信息中自动获得，不可手工修改。若未维护此客户信息，则“是否统一结算”默认为“否”、合同部门为空、催款部门”为空；
	 * 2、收货客户的发票标记为01时,只有快递业务可以统一结算:若收货客户为01客户，则必须同时满足运单为快递运单且该客户的 “是否统一结算”为“是”的条件，运单上“收货客户是否统一结算”才能为“是”,”收货客户合同部门”为其对应合同部门、收货客户的“催款部门”为合同对应催款部门为空； 若该客户“是否统一结算”为“否”，则运单上“收货客户是否统一结算”为“否”,”收货客户合同部门”为空、收货客户的“催款部门”为空
	 * 3、收货客户发票标记为02时,该客户的 “是否统一结算”为“是”，运单上“收货客户是否统一结算”为“是”,”收货客户合同部门”为其对应合同部门，收货客户的“催款部门”为合同对应催款部门为空； 若该客户“是否统一结算”为“否”，则运单上“收货客户是否统一结算”为“否”,”收货客户合同部门”为空、收货客户的“催款部门”为空
	 * @param memberBean
	 * @param waybillBean
	 */
	private static void setReceiveSettleAndContactAndRemending(QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean){
		//判断CRM维护了此客户的信息
		if ("".equals(memberBean.getCentralizedSettlement())
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
	 * 设置到达部门的统一结算信息 抽取
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-27
	 * @param memberBean
	 * @param waybillBean
	 */
	private void setArriveCentralizedSettlementInfo(QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean){
		
	}
	
	
	/**
	 * 清空发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午10:32:06
	 */
	public static void cleanDeliveryCustomerInfo(WaybillEditUI ui, WaybillPanelVo bean, String mobile, String phone) {
		// 发货客户ID
		bean.setDeliveryCustomerId("");
		// 发货客户编码
		/**
		 * 根据Dmana-9885修改此处为若是巨商汇或者阿里商城则不清空客户编码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-09上午12:33
		 */
		if(!WaybillConstants.GIANT_SINK.equals(bean.getOrderChannel())
			&&!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setDeliveryCustomerCode("");
		}
		// 大客户标记
		bean.setDeliveryBigCustomer(FossConstants.INACTIVE);
		// 发货联系人ID
		bean.setDeliveryCustomerContactId("");
		// 发货客户手机
		bean.setDeliveryCustomerMobilephone(mobile);
		// 发货客户电话
		bean.setDeliveryCustomerPhone(phone);
		// 发货客户地址
		bean.setDeliveryCustomerAddress("");
		// 发货客户地址备注
		bean.setDeliveryCustomerAddressNote("");
		// 发货客户是否精准包裹
		bean.setIsAccuratePackage(FossConstants.NO);
		// 是否月结
		if(!WaybillConstants.GIANT_SINK.equals(bean.getOrderChannel())
			&&!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setChargeMode(Boolean.valueOf(false));
		}
		// 合同编号
		bean.setAuditNo("");
		// 行政区域
		bean.setDeliveryCustomerArea("");
		bean.setDeliveryCustomerAreaDto(null);
		//清空【是否统一结算】，【合同部门】，【催款部门】
		bean.setStartCentralizedSettlement("");
		bean.setStartContractOrgCode("");
		bean.setStartContractOrgName("");
		bean.setStartReminderOrgCode("");
		//end
		ui.consignerPanel.getTxtConsignerArea().setAddressFieldDto(null);
		ui.consignerPanel.getTxtConsignerAddressNote().setText("");
		// 发货大客户标记
		ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		// 发货联系人编辑状态
		ui.getTxtConsignerLinkMan().setEnabled(true);
		// 设置装卸费编辑状态-月结客户不允许开装卸费，月发月送可以开劳务费
		Common.setServiceChargeEnabled("",false, ui);

		// 发货客户联系人:放在最后，校验通过不过时不会影响编辑状态的修改
		bean.setDeliveryCustomerContact("");
		//客户分群
		bean.setFlabelleavemonth(null);
		ui.consignerPanel.getCombFlabelleavemonth().setSelectedIndex(-1);
		//修正零担开单时修改VIP分群/全网活跃分群发货客户为新增客户时免费接货可选择的BUG 306486 王帅
		ui.basicPanel.getCboFreePickupGoods().setSelected(false);
		ui.basicPanel.getCboFreePickupGoods().setEnabled(false);
		
	}
	
	/**
	 * 当没有根据名字找到发货客户时，清空客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-17 上午9:12:33
	 */
	public static void noDeliveryCustomerInfo(WaybillEditUI ui,WaybillPanelVo bean){
		/**
		 * BUG-45873:开单界面修复发货客户后未找到固定客户后未清空客户信息
		 */
		// 若为删除原客户信息，则清空全部
		if (!"".equals(StringUtils.defaultString(bean.getDeliveryCustomerId()))) {
			// 清空发货客户信息
			Common.cleanDeliveryCustomerInfo(ui, bean, "", "");
		}
		// 客户编码
		bean.setDeliveryCustomerCode("");
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
		// 发货大客户标记
		ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
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
	public static void noReceiveCustomerInfo(WaybillEditUI ui,WaybillPanelVo bean){
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
		// 收货客户是否是大客户
		bean.setReceiveBigCustomer(FossConstants.INACTIVE);
		// 客户名称
		bean.setReceiveCustomerName("");
		//清空【是否统一结算】，【合同部门】，【催款部门】
		bean.setArriveCentralizedSettlement("");
		bean.setArriveContractOrgCode("");
		bean.setArriveContractOrgName("");
		bean.setArriveReminderOrgCode("");
		// 收货大客户标记
		ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
	}

	/**
	 * 清空收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午10:32:06
	 */
	public static void cleanReceiveCustomerInfo(WaybillEditUI ui, WaybillPanelVo bean, String mobile, String phone) {
		// 收货客户联系人
		bean.setReceiveCustomerContact("");
		// 收货客户编码
		bean.setReceiveCustomerCode("");
		// 收货客户是否是大客户
		bean.setReceiveBigCustomer(FossConstants.INACTIVE);
		// 联系人ID
		bean.setReceiveCustomerContactId("");
		// 收货客户手机
		bean.setReceiveCustomerMobilephone(mobile);
		// 收货客户电话
		bean.setReceiveCustomerPhone(phone);
		// 收货客户ID
		bean.setReceiveCustomerId("");
		// 收货客户地址
		bean.setReceiveCustomerAddress("");
		// 收货客户地址
		bean.setReceiveCustomerAddressNote("");
		// 收货人地址字体颜色
		ui.getTxtConsigneeAddress().setForeground(Color.BLACK);
		// 行政区域
		bean.setReceiveCustomerArea("");
		//清空【是否统一结算】，【合同部门】，【催款部门】
		bean.setArriveCentralizedSettlement("");
		bean.setArriveContractOrgCode("");
		bean.setArriveContractOrgName("");
		bean.setArriveReminderOrgCode("");
		//end
		bean.setReceiveCustomerAreaDto(null);
		ui.consigneePanel.getTxtConsigneeArea().setAddressFieldDto(null);
		ui.consigneePanel.getTxtConsigneeAddressNote().setText("");
		// 收货大客户标记
		ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		// 设置联系人为可编辑
		ui.getTxtConsigneeLinkMan().setEnabled(true);
	}

	/**
	 * 获得省市区县地址对象:若dto为空，则从界面中取对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午4:29:58
	 */
	public static AddressFieldDto getAddressFiledInfo(AddressFieldDto dto, WaybillEditUI ui) {
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
	 * 获得省市区县地址对象:直接根据省市区县编码查询DTO
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午4:29:58
	 */
	public static AddressFieldDto getAddressFieldInfoByCode(WaybillEditUI ui, String provCode, String cityCode, String distCode) {
		// 业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 获得省市区县对象
		AddressFieldDto address = bu.getProvCityCounty(provCode, cityCode, distCode);
		// 接收地址对象
		return Common.getAddressFiledInfo(address, ui);
	}

	/**
	 * 设置集中开单收货部门信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午8:25:09
	 */
	public static void setSalesDepartmentForCentrial(SaleDepartmentEntity entity, WaybillPanelVo bean, WaybillEditUI ui) {
		// 对象非空判断
		if (entity != null) {
			// 收货部门编号
			bean.setReceiveOrgCode(entity.getCode());
			// 收货部门名称
			bean.setReceiveOrgName(entity.getName());
			// 设置创建时间
			bean.setReceiveOrgCreateTime(entity.getOpeningDate());
			// 清空现有产品
			ui.getProductTypeModel().removeAllElements();
			// 设置新的产品信息
			ui.setProductTypeModel(entity.getCode());
			// 设置产品默认值
			ui.setProductCode(bean);
			//重新获取保价信息
			if(bean.getGoodsVolumeTotal() == null){
				bean.setGoodsVolumeTotal(BigDecimal.ZERO);
			}
			if(bean.getGoodsWeightTotal() == null){
				bean.setGoodsWeightTotal(BigDecimal.ZERO);
			}
			if(bean.getGoodsWeightTotal() != null && bean.getGoodsVolumeTotal() != null  && bean.getCustomerPickupOrgCode() != null){
				Common.getInsuranceCharge(bean, ui);
			}
		}
	}
	
	/**
	 * 设置简单报价收货部门信息
	 * 
	 * @author WangQianJin
	 * @date 2013-07-17
	 */
	public static void setSalesDepartmentForSimple(SaleDepartmentEntity entity, WaybillPanelVo bean, CalculateCostsDialog ui) {
		// 对象非空判断
		if (entity != null) {
			// 收货部门编号
			bean.setReceiveOrgCode(entity.getCode());
			// 收货部门名称
			bean.setReceiveOrgName(entity.getName());	
			// 设置创建时间
			bean.setReceiveOrgCreateTime(entity.getOpeningDate());
			// 清空现有产品
			ui.getProductTypeModel().removeAllElements();
			// 设置新的产品信息
			ui.setProductTypeModel(entity.getCode());
			// 设置产品默认值
			ui.setProductCode(bean);
		}
	}


	/**
	 * 
	 * 获取代打木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 下午05:21:48
	 */
	public static List<GuiQueryBillCalculateSubDto> getYokeChargeCollection(WaybillPanelVo bean, WaybillEditUI ui) {

		List<GuiQueryBillCalculateSubDto> guiQueryBillCalculateSubDtos = new ArrayList<GuiQueryBillCalculateSubDto>();
		// 获取木架费用
		GuiQueryBillCalculateSubDto standChargeCollection = getStandChargeCollection(bean, ui);
		if (standChargeCollection != null) {
			guiQueryBillCalculateSubDtos.add(standChargeCollection);
		}

		// 获取木箱费用
		GuiQueryBillCalculateSubDto boxChargeCollection = getBoxChargeCollection(bean, ui);
		if (boxChargeCollection != null) {
			guiQueryBillCalculateSubDtos.add(boxChargeCollection);

		}
		
		// 获取木托费用 zxy 20131118 ISSUE-4391
		GuiQueryBillCalculateSubDto salverChargeCollection = getSalverChargeCollection(bean, ui);
		if (salverChargeCollection != null) {
			guiQueryBillCalculateSubDtos.add(salverChargeCollection);
		}
		return guiQueryBillCalculateSubDtos;
	}
	
	
	/**
	 * 
	 * 打木箱费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static GuiQueryBillCalculateSubDto getBoxChargeCollection(WaybillPanelVo bean, WaybillEditUI ui) {
		if (bean.getBoxGoodsNum() != null && bean.getBoxGoodsNum() > 0) {
			// 打木箱
			return getQueryYokeParam(bean, DictionaryValueConstants.PACKAGE_TYPE__BOX, bean.getBoxGoodsVolume());

		} else {
			return null;
		}
	}
	
	/**
	 * 打木托费用
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18
	 * @param bean
	 * @param ui
	 * @return
	 */
	private static GuiQueryBillCalculateSubDto getSalverChargeCollection(WaybillPanelVo bean, WaybillEditUI ui) {
		if (bean.getSalverGoodsNum() != null && bean.getSalverGoodsNum() > 0) {
			// 打木托
			return getQueryYokeParam(bean, DictionaryValueConstants.PACKAGE_TYPE__SALVER, new BigDecimal(bean.getSalverGoodsNum()));

		} else {
			return null;
		}
	}
	
	
	/**
	 * 
	 * 计算完费用后，对打木架进行复制
	 * 
	 * @param bean
	 * @param ui
	 * @param dto
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-16 上午10:16:10
	 */
	public static void setStandChargeCollection(WaybillPanelVo bean, WaybillEditUI ui, GuiResultBillCalculateDto dto) {
		if (bean.getStandGoodsNum() != null && bean.getStandGoodsNum() > 0) {
			if (dto != null) {

				//zxy 20131216 ISSUE-4391 DEFECT-811 start 修改：注释掉这段代码，否则当人工增加费用会减去两次
				// 这个if内的代码为了解决包装费不断累加的问题
//				if (bean.getStandCharge() != null) {
//					BigDecimal packageFee = bean.getPackageFee();
//					// 四舍五入
//					BigDecimal standCharge = bean.getStandCharge();
//					packageFee = packageFee.subtract(standCharge);
//					bean.setPackageFee(packageFee);
//					bean.setCalculatedPackageFee(packageFee);
//					// 设置折扣优惠
//					setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
//				}
				//zxy 20131216 ISSUE-4391 DEFECT-811 end 修改：注释掉这段代码，否则当人工增加费用会减去两次
				// 打木架ID
				bean.setStandChargeId(dto.getId());
				// 打木架编码
				bean.setStandChargeCode(dto.getSubType());
				// 打木架费用
				bean.setStandCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
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
	public static void setBoxChargeCollection(WaybillPanelVo bean, WaybillEditUI ui, GuiResultBillCalculateDto dto) {
		if (bean.getBoxGoodsNum() != null && bean.getBoxGoodsNum() > 0) {
		if (dto != null) {

			//zxy 20131216 ISSUE-4391 DEFECT-811 start 修改：注释掉这段代码，否则当人工增加费用会减去两次
			// 这个if内的代码为了解决每次计算包装费不断累加的问题
//			if (bean.getBoxCharge() != null) {
//				BigDecimal packageFee = bean.getPackageFee();
//				// 四舍五入
//				BigDecimal boxCharge = bean.getBoxCharge();
//				packageFee = packageFee.subtract(boxCharge);
//				bean.setPackageFee(packageFee);
//				bean.setCalculatedPackageFee(packageFee);
//				// 设置折扣优惠
//				setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
//			}
			//zxy 20131216 ISSUE-4391 DEFECT-811 end 修改：注释掉这段代码，否则当人工增加费用会减去两次
			bean.setBoxChargeId(dto.getId());
			bean.setBoxChargeCode(dto.getSubType());
			bean.setBoxCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));

		} else {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
		}
		}
	}
	
	/**
	 * 设置打木托费用
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18 
	 * @param bean
	 * @param ui
	 * @param dto
	 */
	public static void setSalverChargeCollection(WaybillPanelVo bean,
			WaybillEditUI ui, GuiResultBillCalculateDto dto) {
		if (bean.getSalverGoodsNum() != null && bean.getSalverGoodsNum() > 0) {

			// 这个if内的代码为了解决每次计算包装费不断累加的问题
			if (bean.getSalverGoodsCharge() != null) {
				if (dto == null) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenSalverPackageFee"));
				}

				//验证木托费是否小于最小值
				BigDecimal calculateSalverMinFee = dto.getMinFee().multiply(new BigDecimal(bean.getSalverGoodsNum()));
				if (calculateSalverMinFee.compareTo(bean.getSalverGoodsCharge()) > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.woodYokeEnterAction.exception.minSalverGoodsAmount"));
				}
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal salverGoodsCharge = bean.getSalverGoodsCharge();
				if(!bean.isSubPreSalverCharge()){
					BigDecimal preSalverGoodsCharge = bean.getPreSalverGoodsCharge();
					if(preSalverGoodsCharge != null && preSalverGoodsCharge.compareTo(BigDecimal.ZERO) > 0){
						salverGoodsCharge = preSalverGoodsCharge;
					}
					bean.setSubPreSalverCharge(true);	//已减去标志
				}
				packageFee = packageFee.subtract(salverGoodsCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
				// 设置折扣优惠
				// setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);

			}

			// bean.setSalverChargeId(dto.getId());
			// bean.setSalverChargeCode(dto.getSubType());
			// bean.setSalverGoodsCharge(dto.getCaculateFee().setScale(0,
			// BigDecimal.ROUND_HALF_UP));

		}
	}
	
	/**
	 * 获取代打木架
	 * 
	 * @param bean
	 * @param ui
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-15 下午3:42:42
	 */
	public static GuiQueryBillCalculateSubDto getStandChargeCollection(WaybillPanelVo bean, WaybillEditUI ui) {

		if (bean.getStandGoodsNum() != null && bean.getStandGoodsNum() > 0) {
			return getQueryYokeParam(bean, DictionaryValueConstants.PACKAGE_TYPE__FRAME, bean.getStandGoodsVolume());
		} else {
			return null;
		}
	}
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private static GuiQueryBillCalculateSubDto getQueryYokeParam(WaybillPanelVo bean, String subType, BigDecimal volume) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setWoodenVolume(volume);//代打体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BZ);// 计价条目CODE
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		return queryDto;
	}

	
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private static QueryBillCacilateValueAddDto getQueryYokeParamForOld(WaybillPanelVo bean, String subType, BigDecimal volume) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
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
		//封装市场营销活动校验条件
		settterActiveParamInfoValueAdd(queryDto,bean);
		//POP-165 修复包装费计算错误 start
		queryDto.setCaculateType(bean.getCaculateType());//计费类型
	    //POP-165 修复包装费计算错误 end
		queryDto.setBillTime(bean.getBillTime());
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	
	/**
	 * 
	 * 设置折扣优惠
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-27 下午02:33:49
	 */
	public static void setFavorableDiscount(List<GuiResultDiscountDto> discountPrograms, WaybillEditUI ui, WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = null;
			WaybillDiscountCanvas discountTableModel = (WaybillDiscountCanvas) ui.canvasContentPanel.getConcessionsPanel().getTblConcessions().getModel();
			data = discountTableModel.getData();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}
			for (GuiResultDiscountDto dto : discountPrograms) {
				/**
				 * 根据Dmana-9885满足条件的特殊渠道客户（巨商汇、阿里）运费折扣不显示
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-02-05下午14:37
				 */
				if(bean.getSpecialChannelFreight()&&PriceEntityConstants.PRICING_CODE_FRT.equals(dto.getPriceEntryCode())){
					continue;
				}
				CommoForFeeUtils.add(dto, data, bean);
			}
			ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(data);
		}
	}
	
	/**
	 * 
	 * 设置折扣优惠
	 * 
	 * @author wqj
	 * @date 2013-03-28
	 */
	public static void setFavorableDiscount(List<PriceDiscountDto> discountPrograms, CalculateCostsDialog ui, WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = null;
			WaybillDiscountCanvas discountTableModel = (WaybillDiscountCanvas) ui.canvasContentPanel.getConcessionsPanel().getTblConcessions().getModel();
			data = discountTableModel.getData();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}
			for (PriceDiscountDto dto : discountPrograms) {
				CommonUtils.add(dto, data, bean);
			}
			ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(data);
		}
	}

	/**
	 * 设置运输性质为参数指定的类型（现在默认为整车和精准卡航）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午02:43:29
	 */
	public static void setProductCode(WaybillEditUI ui, WaybillPanelVo bean, String product) {
		for (int i = 0; i < ui.getProductTypeModel().getSize(); i++) {
			ProductEntityVo vo = (ProductEntityVo) ui.getProductTypeModel().getElementAt(i);
			// 设置为传入的产品类型
			if (product.equals(vo.getCode())) {
				bean.setProductCode(vo);
			}
		}
	}
	
	
	//精准包裹提示
	public static void validateProductCode(WaybillPanelVo bean){
		//发货客户为空，直接返回
		if(StringUtil.isEmpty(bean.getDeliveryCustomerMobilephone())
				&&StringUtil.isEmpty(bean.getDeliveryCustomerPhone())
				&&StringUtil.isEmpty(bean.getDeliveryCustomerName())){
			return ;
		}
		// 产品对象
		ProductEntityVo product = bean.getProductCode();
		// 精准包裹校验
		if(!WaybillConstants.YES.equals(bean.getIsAccuratePackage())
				&&product!=null&&ProductEntityConstants.PRICING_PRODUCT_PCP.equals(product.getCode())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.isNotAccuratePackageCustomerException"));
		}
	}

	/**
	 * 
	 * 清理产品类型，设置为整车到下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-19 下午06:13:50
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void cleanProductToWholeVehicle(WaybillEditUI ui) {
		// 获取产品数据模型
		DefaultComboBoxModel productTypeModel = ui.getProductTypeModel();
		// 情况下拉框数据模型
		productTypeModel.removeAllElements();
		// 获取当前用户
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 当前用户所在部门
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		// 根据部门编号查询产品数据
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(dept.getCode(), WaybillConstants.TYPE_OF_CARGO, new Date());
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		ProductEntityVo vo = null;
		for (ProductEntity product : list) {
			if (ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode())) {
				vo = new ProductEntityVo();
				// 将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo);
				vo.setDestNetType(product.getDestNetType());
				productTypeModel.addElement(vo);
			}
		}
	}

	/**
	 * 
	 * 清理产品类型，设置为非整车产品类型到下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-19 下午06:13:50
	 */
	public static void cleanProductToOtherType(WaybillEditUI ui) {
		// 获取产品数据模型
		DefaultComboBoxModel productTypeModel = ui.getProductTypeModel();
		// 情况下拉框数据模型
		productTypeModel.removeAllElements();
		// 获取当前用户
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 当前用户所在部门
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		// 根据部门编号查询产品数据
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(dept.getCode(), WaybillConstants.TYPE_OF_CARGO, new Date());
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		ProductEntityVo vo = null;
		for(ProductEntity product : list) {
			if (!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode())) {
				vo = new ProductEntityVo();
				// 将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo);
				vo.setDestNetType(product.getDestNetType());
				productTypeModel.addElement(vo);
			}
		}
	}

	/**
	 * 
	 * 设置开单界面编辑状态为不编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 上午11:48:15
	 */
	public static void setUIEnableFalse(WaybillEditUI ui) {
		UIUtils.disableUI(ui);
		// 是否整车
		ui.basicPanel.getChbWholeVehicle().setEnabled(true);
		// 是否经过营业部(整车不允许编辑是否经过营业部,由约车信息而定,根据MANA-389修改)
		ui.basicPanel.getChbPassDept().setEnabled(false);
		// 整车编号
		ui.basicPanel.getTxtVehicleNumber().setEnabled(true);
		// 导入整车约车编号
		ui.basicPanel.getBtnImportVehicle().setEnabled(true);
	}

	/**
	 * 
	 * 设置经过营业部代收货款编辑状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 上午09:56:37
	 */
	public static void setPassDeptCodEnabled(WaybillEditUI ui, WaybillPanelVo bean) {
		if (bean.getIsWholeVehicle()) {
			// 如果是整车
			if (bean.getIsPassDept()) {
				// 代收货款金额
				ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
				// 代收货款类型
				ui.incrementPanel.getCombRefundType().setEnabled(true);
			} else {
				// 代收货款金额
				ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
				// 代收货款类型
				ui.incrementPanel.getCombRefundType().setEnabled(false);
				// 清理代收货款信息
				cleanCodInfo(ui, bean);

			}
		}
	}

	/**
	 * 
	 * 设置偏线代收货款编辑状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 上午09:56:37
	 */
	public static void setPartialCod(WaybillPanelVo bean, WaybillEditUI ui) {
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode())) {
			// 代收货款金额
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
			// 代收货款类型
			ui.incrementPanel.getCombRefundType().setEnabled(false);
			// 设置代收货款为0
			bean.setCodAmount(BigDecimal.ZERO);
			bean.setCodAmountCanvas("0");
			// 清理代收货款信息
			cleanCodInfo(ui, bean);
		} else {
			// 代收货款金额
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
			// 代收货款类型
			ui.incrementPanel.getCombRefundType().setEnabled(true);
		}
	}

	/**
	 * 
	 * 清理代收货款信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午05:16:21
	 */
	public static void cleanCodInfo(WaybillEditUI ui, WaybillPanelVo bean) {
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
			if (null != entity && StringUtil.defaultIfNull(code).equals(entity.getValueCode())) {
				return entity;
			}
		}
		return vo;
	}

	/**
	 * 获得运单开单界面绑定的运单VO对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-14 下午3:15:41
	 */
	public static WaybillPanelVo getVoFromUI(WaybillEditUI ui) {
		// 从Binders集合中获得绑定对象
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		// 获得与运单绑定的绑定对象
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		// 从绑定对象中获得WaybillPanelVo对象
		return waybillBinder.getBean();
	}
	
	/**
	 * 
	 * 根据运输性质的改变，改变提货方式
	 * 
	 * @author wqj
	 * @date 2013-03-29
	 */
	public static void changePickUpMode(WaybillPanelVo bean, CalculateCostsDialog ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			// 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
			Common.setAirPropertyToTrue(ui);
			// 空运提货方式
			Common.airReceiveMethod(waybillService, bean, ui);
		} else {

			// 如果运输性质不为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
			Common.setAirPropertyToFalse(bean, ui);
			// 汽运提货方式
			Common.highwaysReceiveMethod(waybillService, bean, ui);
		}
	}
	
	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private static void setAirPropertyToTrue(CalculateCostsDialog ui) {
		ui.getCombFreightMethod().setEnabled(true);
		ui.getCombFlightNumberType().setEnabled(true);

//		ui.getRbnA().setVisible(false);
//		ui.getRbnB().setVisible(false);
		
		ui.getCombGoodsType().setVisible(true);
	}
	
	/**
	 * 
	 * 空运提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午08:47:33
	 */
	public static void airReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, CalculateCostsDialog ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();
		pikcModeModel.removeAllElements();
		//提货方式隐藏免费自提【徐思衍-20160808-空运提货方式优化】
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsAir();
		List<DataDictionaryValueEntity> dellist = new ArrayList<DataDictionaryValueEntity>();
		for(DataDictionaryValueEntity data : list) {
			if(WaybillConstants.DELIVER_FREE.equals(data.getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(data.getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(data.getValueCode())){
				dellist.add(data);
			}
		}
		list.removeAll(dellist);
		//DMANA-4923  FOSS开单提货方式隐藏“免费送货”
		for (int i = 0; i < list.size(); i++) {
			if(WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode())){
				list.remove(list.get(i));
			}
		}
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			pikcModeModel.addElement(vo);
			// 设置提货方式默认值
			if (WaybillConstants.AIR_SELF_PICKUP.equals(vo.getValueCode())) {
				bean.setReceiveMethod(vo);
			}
		}
	}
	
	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private static void setAirPropertyToFalse(WaybillPanelVo bean, CalculateCostsDialog ui) {
		ui.getCombFreightMethod().setEnabled(false);
		ui.getCombFlightNumberType().setEnabled(false);

//		ui.getRbnA().setVisible(true);
//		ui.getRbnB().setVisible(true);
		
		ui.getCombGoodsType().setVisible(false);

		// 航班类型
		Common.setFlightNumberType(bean, ui);
		// 合票方式
		Common.setFreightMethod(bean, ui);
		// 航班时间
		bean.setFlightShift("");
	}
	
	/**
	 * 
	 * 设置航班类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private static void setFlightNumberType(WaybillPanelVo bean, CalculateCostsDialog ui) {
		int size = ui.getFlightNumberType().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			vo = (DataDictionaryValueVo) ui.getFlightNumberType().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setFlightNumberType(vo);
			}
		}
	}
	
	/**
	 * 
	 * 设置合票方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private static void setFreightMethod(WaybillPanelVo bean, CalculateCostsDialog ui) {
		int size = ui.getFreightMethod().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui.getFreightMethod().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setFreightMethod(vo);
			}
		}
	}
	
	/**
	 * 
	 * 汽运提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午08:47:33
	 */
	public static void highwaysReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, CalculateCostsDialog ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();

		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
		//DMANA-4923  FOSS开单提货方式隐藏“免费送货”
		for (int i = 0; i < list.size(); i++) {
			if(WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode())){
				list.remove(list.get(i));
			}
		}
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			pikcModeModel.addElement(vo);
			if (WaybillConstants.SELF_PICKUP.equals(vo.getValueCode()))// 设置提货方式默认值
			{
				bean.setReceiveMethod(vo);
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
	public static void getYokeCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		//zxy 20130913 BUG-54776 start 修改：解决 空运和偏线的管理运单查看报错，注掉此段代码，此段代码是BUG-51182加上去的(主要是解决运输性质切换报错的问题,而此问题可以被BUG-54637有效处理,所以可以屏蔽)
//		if (bean.getCustomerPickupOrgCode() == null) { 
//			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullCustomerPickupOrg"));
//		}
		//zxy 20130913 BUG-54776 end 修改：解决 空运和偏线的管理运单查看报错，注掉此段代码，此段代码是BUG-51182加上去的(主要是解决运输性质切换报错的问题,而此问题可以被BUG-54637有效处理,所以可以屏蔽)
		// 获取木架费用
		getStandCharge(bean, ui);
		// 获取木箱费用
		getBoxCharge(bean, ui);
		//zxy 20131118 ISSUE-4391 获取木托费用 
		getSalverCharge(bean, ui);
		
		// ===========LianHe/获取非木包装费/2017年1月10日/start=======
		getNonWoodPackingCharge(bean, ui);
		// ===========LianHe/获取非木包装费/2017年1月10日/end=======
		/**
		 * 获取纸纤包装总价
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-5上午09:43
		 */
		getPackingStandCharge(bean,ui);
	}
	
	/**
	 * LianHe-2017年1月10日11:48:26
	 * 非木包装费
	 * @param bean
	 * @param ui
	 */
	private static void getNonWoodPackingCharge(WaybillPanelVo bean,WaybillEditUI ui) {
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
	 * 打木箱费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static void getBoxCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		ValueAddDto dto = new ValueAddDto();
		if (bean.getBoxGoodsNum() != null && bean.getBoxGoodsNum() > 0) {
			// 打木箱
			List<ValueAddDto> boxList = waybillService.queryValueAddPriceList(getQueryYokeParamForOld(bean, DictionaryValueConstants.PACKAGE_TYPE__BOX, bean.getBoxGoodsVolume()));

			if (boxList != null) {
				if (!boxList.isEmpty()) {
					dto = boxList.get(0);

					// 这个if内的代码为了解决每次计算包装费不断累加的问题
					if (bean.getBoxCharge() != null) {
						BigDecimal packageFee = bean.getPackageFee();
						// 四舍五入
						BigDecimal boxCharge = bean.getBoxCharge();
						packageFee = packageFee.subtract(boxCharge);
						bean.setPackageFee(packageFee);
						bean.setCalculatedPackageFee(packageFee);
						// 设置折扣优惠
						//setFavorableDiscountForOld(dto.getDiscountPrograms(), ui, bean);
					}
					bean.setBoxChargeId(dto.getId());
					bean.setBoxChargeCode(dto.getSubType());
					bean.setBoxCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
				}
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 start 新增：解决删除木箱后费用清不掉的问题
		// @Deprecated(20131212 不能加此段，否则会引起出发运单查看时候的详情面板显示不出木箱费)
		// 在出发运单导入木箱数量后，可以正常使用下面的功能
		else{
			//清除最后一次费用
			if (bean.getBoxCharge() != null && bean.getBoxCharge().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal boxCharge = bean.getBoxCharge();
				packageFee = packageFee.subtract(boxCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
				bean.setBoxCharge(BigDecimal.ZERO);
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 end 新增：解决删除木箱后费用清不掉的问题
	}
	/**
	 * 
	 * 打木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static void getStandCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		ValueAddDto dto = new ValueAddDto();

		if (bean.getStandGoodsNum() != null && bean.getStandGoodsNum() > 0) {
			// 打木架
			List<ValueAddDto> frameList = waybillService.queryValueAddPriceList(getQueryYokeParamForOld(bean, DictionaryValueConstants.PACKAGE_TYPE__FRAME, bean.getStandGoodsVolume()));

			if (frameList != null && !frameList.isEmpty()) {
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
					//setFavorableDiscountForOld(dto.getDiscountPrograms(), ui, bean);
				}
				// 打木架ID
				bean.setStandChargeId(dto.getId());
				// 打木架编码
				bean.setStandChargeCode(dto.getSubType());
				// 打木架费用
				bean.setStandCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenPackageFee"));
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 start 新增：解决删除木架后费用清不掉的问题
		// @Deprecated(20131212 不能加此段，否则会引起出发运单查看时候的详情面板显示不出木箱费)
		// 在出发运单导入木箱数量后，可以正常使用下面的功能
		else{
			//清除最后一次费用
			if (bean.getStandCharge() != null && bean.getStandCharge().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal standCharge = bean.getStandCharge();
				packageFee = packageFee.subtract(standCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
				bean.setStandCharge(BigDecimal.ZERO);
			}
		}
		//zxy 20131210 ISSUE-4391 DEFECT-531 end 新增：解决删除木架后费用清不掉的问题
	}
	
	/**
	 * 获取打木托费用
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 */
	private static void getSalverCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		ValueAddDto dto = new ValueAddDto();

		if (bean.getSalverGoodsNum() != null && bean.getSalverGoodsNum() > 0) {
			// 打木架
			List<ValueAddDto> frameList = waybillService.queryValueAddPriceList(getQueryYokeParamForOld(bean, DictionaryValueConstants.PACKAGE_TYPE__SALVER, new BigDecimal(bean.getSalverGoodsNum())));

			if (frameList != null && !frameList.isEmpty()) {
				dto = frameList.get(0);
				//    POP-534  开单计算运费后更改目的站后木托费没有校验
				if(bean.getSalverGoodsNum() != null && bean.getSalverGoodsNum() > 0){					
					// 验证木托费是否小于最小值
					BigDecimal calculateSalverMinFee = dto.getMinFee().multiply(
							new BigDecimal(bean.getSalverGoodsNum()));
					if (calculateSalverMinFee.compareTo(bean.getSalverGoodsCharge()) > 0) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.woodYokeEnterAction.exception.minSalverGoodsAmount"));
					}
					//验证木托费是否大于最大值
					BigDecimal calculateSalverMaxFee = dto.getMaxFee()
							.multiply(new BigDecimal(bean.getSalverGoodsNum()));
					if(calculateSalverMaxFee!=null && calculateSalverMaxFee.compareTo(BigDecimal.ZERO)>0){						
						if(calculateSalverMaxFee.compareTo(bean.getSalverGoodsCharge())<0){
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.woodYokeEnterAction.exception.maxSalverGoodsAmount"));
						}			
					}
				}
				// 这个if内的代码为了解决包装费不断累加的问题				
				if (bean.getSalverGoodsCharge() != null) {
					BigDecimal packageFee = bean.getPackageFee();
					// 四舍五入
					BigDecimal salverGoodsCharge = bean.getSalverGoodsCharge();
					if(!bean.isSubPreSalverCharge()){
						BigDecimal preSalverGoodsCharge = bean.getPreSalverGoodsCharge();
						if(preSalverGoodsCharge != null && preSalverGoodsCharge.compareTo(BigDecimal.ZERO) >= 0){
							salverGoodsCharge = preSalverGoodsCharge;
						}
						bean.setSubPreSalverCharge(true);//已减去
					}
					packageFee = packageFee.subtract(salverGoodsCharge);
					bean.setPackageFee(packageFee);
					bean.setCalculatedPackageFee(packageFee);
					// 设置折扣优惠
					//setFavorableDiscountForOld(dto.getDiscountPrograms(), ui, bean);
				}
//				// 打木架ID
//				bean.setSalverChargeId(dto.getId());
//				// 打木架编码
//				bean.setSalverChargeCode(dto.getSubType());
//				// 打木架费用
//				bean.setSalverGoodsCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenSalverPackageFee"));
			}
		}else{
			//清除最后一次费用
			if (!bean.isSubPreSalverCharge()) {
				BigDecimal packageFee =
						(bean.getPackageFee() == null ? BigDecimal.ZERO : bean.getPackageFee());
				// 四舍五入
				BigDecimal preSalverGoodsCharge =
						(bean.getPreSalverGoodsCharge() == null ? BigDecimal.ZERO : bean.getPreSalverGoodsCharge());
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
	private static void getPackingStandCharge(WaybillPanelVo bean, WaybillEditUI ui) {
		if (bean.getPaperBoxTotlePrice()!=null || bean.getFibelBagTotlePrice()!=null || bean.getOtherTotle()!=null){
			BigDecimal paperBoxTotlePrice=bean.getPaperBoxTotlePrice()==null?new BigDecimal(0):bean.getPaperBoxTotlePrice();
			BigDecimal fibelBagTotlePrice=bean.getFibelBagTotlePrice()==null?new BigDecimal(0):bean.getFibelBagTotlePrice();
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
		//zxy 20131210 ISSUE-4391 DEFECT-531 end 新增：解决删除木架后费用清不掉的问题
	}
	
	/**
	 * 
	 * 设置折扣优惠
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-27 下午02:33:49
	 */
	public static void setFavorableDiscountForOld(List<PriceDiscountDto> discountPrograms, WaybillEditUI ui, WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = null;
			WaybillDiscountCanvas discountTableModel = (WaybillDiscountCanvas) ui.canvasContentPanel.getConcessionsPanel().getTblConcessions().getModel();
			data = discountTableModel.getData();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}
			for (PriceDiscountDto dto : discountPrograms) {
				CommonUtils.add(dto, data, bean);
			}
			ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(data);
		}
	}
	
	
	/**
	 * 
	 * 获取产品价格查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public static QueryBillCacilateDto getQueryParam(WaybillPanelVo bean, String yesOrNo) {
		QueryBillCacilateDto queryDto = new QueryBillCacilateDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			queryDto.setGoodsCode(bean.getAirGoodsType().getValueCode());// 货物类型CODE
		}

		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		// queryDto.setIsReceiveGoods(BooleanConvertYesOrNo.booleanToString(bean.getPickupToDoor()));//
		// 是否接货
		queryDto.setIsReceiveGoods(yesOrNo);// 是否接货
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		if (bean.getFlightNumberType() == null) {
			queryDto.setFlightShift(null);// 航班号
		} else {
			queryDto.setFlightShift(bean.getFlightNumberType().getValueCode());// 航班号
		}

		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		// 是否经济自提件
		queryDto.setEconomySince(BooleanConvertYesOrNo.booleanToString(bean.getIsEconomyGoods()));
		//最终配载部门(计算偏线中转费时用得到)
		queryDto.setLastOrgCode(bean.getLastLoadOrgCode());
		queryDto.setBillTime(bean.getBillTime());//开单时间
		return queryDto;
	}
	
	/**
	 * 
	 * 清除运单VO中的木架信息
	 * 
	 * @author 025000-FOSS-helong
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-11-5 上午08:24:15
	 */
	public static void unsetWaybillPanelVoForWoodenPack(WaybillPanelVo bean , WaybillEditUI ui) {
		bean.setStandGoodsNum(null);// 打木架货物件数
		bean.setStandRequirement(null);// 代打木架要求
		bean.setStandGoodsSize(null);// 打木架货物尺寸
		bean.setStandGoodsVolume(null);// 打木架货物体积
		bean.setBoxGoodsNum(null);// 打木箱货物件数
		bean.setBoxRequirement(null);// 代打木箱要求
		bean.setBoxGoodsSize(null);// 打木箱货物尺寸
		bean.setBoxGoodsVolume(null);// 打木箱货物体积
		bean.setSalverGoodsNum(null);//打木托件数
		bean.setSalverRequirement(null);//打木托要求
		bean.setSalverGoodsCharge(null);//打木托费用
		bean.setSubPreSalverCharge(true);//设置木托费重置标志
		if(ui.getDialog() != null)
		{
			// 打木架货物件数
			ui.getDialog().getTxtYokeGoodsPieces().setText("");
			// 代打木架要求
			ui.getDialog().getTxtYokeRequire().setText("");
			// 打木架货物尺寸
			ui.getDialog().getTxtYokeGoodsSize().setText("");
			// 打木架货物体积
			ui.getDialog().getTxtYokeGoodsVolume().setText("");
			// 打木箱货物件数
			ui.getDialog().getTxtBoxGoodsPieces().setText("");
			// 代打木箱要求
			ui.getDialog().getTxtBoxRequire().setText("");
			// 打木箱货物尺寸
			ui.getDialog().getTxtBoxGoodsSize().setText("");
			// 打木箱货物体积
			ui.getDialog().getTxtBoxGoodsVolume().setText("");
			//zxy 20131118 ISSUE-4391 start 新增：重置打木托
			// 打木托件数
			ui.getDialog().getTxtSalverGoodsPieces().setText("");
			// 代打木托要求
			ui.getDialog().getTxtSalverRequire().setText("");
			// 打木托费用
			ui.getDialog().getTxtSalverGoodsAmount().setText("");
			//zxy 20131118 ISSUE-4391 end 新增：重置打木托
		}
		//zxy 20131118 ISSUE-4391 重置打木托标签列表
		refreshLabeledGood(bean,ui);
	}
	
	/**
	 * 
	 * 代打木架取消清除储运事项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-2 上午08:44:42
	 */
	public static void unsetStorageMatterForWoodenPack(WaybillPanelVo bean,WaybillEditUI ui) {
			cleanRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"));
			cleanRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"));
			cleanRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.salverGoods"));
			if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
				cleanPackageRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"));
				cleanPackageRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"));
				cleanPackageRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.salverGoods"));
			}
	}
	/**
	 * 
	 * 根据传入参数清理包装备注
	 * @author 025000-FOSS-helong
	 * @date 2013-3-11 下午07:21:56
	 */
	public static void cleanPackageRemark(WaybillPanelVo bean , String key)
	{
		if(StringUtils.isEmpty(bean.getTransportationRemark()))
		{
			return;
		}
		//将储运事项字符串解析成数据组
		String[] remark = bean.getTransportationRemark().split(";");
		StringBuffer transportationRemark = new StringBuffer();
		for(int i=0;i<remark.length;i++)
		{
			//获取储运事项中的某段数据
			String oldData = remark[i].trim();
			if(StringUtils.isNotEmpty(oldData))
			{
				//zxy 20131016 BUG-57397 start 修改：把值相等的过滤掉，以前是indexOf(包含)的都过滤
				//判断储运事项中的这段数据是否存在与传入参数一致的数据，存在则用最新的信息替换
				if(oldData.indexOf(StringUtil.defaultIfNull(key.trim()))!=-1)
				{
					continue;
				}
				//zxy 20131016 BUG-57397 end 修改：把值相等的过滤掉，以前是indexOf(包含)的都过滤
				transportationRemark.append(remark[i]);
				transportationRemark.append(";");
			}
		}
		//设置重新拼装的储运事项
		bean.setTransportationRemark(transportationRemark.toString());
	}

	
	/**
	 * 
	 * 根据传入参数清理储运事项
	 * @author 025000-FOSS-helong
	 * @date 2013-3-11 下午07:21:56
	 */
	public static void cleanRemark(WaybillPanelVo bean , String key)
	{
		if(StringUtils.isEmpty(bean.getTransportationRemark()))
		{
			return;
		}
		//将储运事项字符串解析成数据组
		String[] remark = bean.getTransportationRemark().split(";");
		StringBuffer transportationRemark = new StringBuffer();
		for(int i=0;i<remark.length;i++)
		{
			//获取储运事项中的某段数据
			String oldData = remark[i].trim();
			if(StringUtils.isNotEmpty(oldData))
			{
				//zxy 20131016 BUG-57397 start 修改：把值相等的过滤掉，以前是indexOf(包含)的都过滤
				//判断储运事项中的这段数据是否存在与传入参数一致的数据，存在则用最新的信息替换
				if(oldData.indexOf(StringUtil.defaultIfNull(key.trim()))!=-1)
				{
					continue;
				}
				//zxy 20131016 BUG-57397 end 修改：把值相等的过滤掉，以前是indexOf(包含)的都过滤
				transportationRemark.append(remark[i]);
				transportationRemark.append(";");
			}
		}
		//设置重新拼装的储运事项
		bean.setTransportationRemark(transportationRemark.toString());
	}
	
	/**
	 *将打木架相关费用置为0
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-2 上午08:44:42
	 */
	public static void unsetWoodenPackFee(WaybillPanelVo bean) {
		bean.setPackageFee(BigDecimal.ZERO);
		bean.setCalculatedPackageFee(BigDecimal.ZERO);
		bean.setStandCharge(BigDecimal.ZERO);
		bean.setBoxCharge(BigDecimal.ZERO);
	}
	
	
	/**
	 * 设置界面为整车界面信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-23 下午4:04:14
	 */
	public static void setWholeVehicleData(WaybillEditUI ui,WaybillPanelVo bean){
		// 整车编号
		ui.basicPanel.getTxtVehicleNumber().setVisible(true);
		// 整车编号标签
		ui.basicPanel.getLblVehicleNumber().setVisible(true);
		// 整车编号导入按钮
		ui.basicPanel.getBtnImportVehicle().setVisible(true);
		

		// 开单报价
		ui.billingPayPanel.billingPayBelongPanel.getLblPublicCharge().setText(i18n.get("foss.gui.creating.listener.Waybill.isWholeVehicleListener.two") + "：");
		// 整车约车报价
		ui.billingPayPanel.billingPayBelongPanel.getTxtWholeVehicleAppfee().setVisible(true);
		// 整车约车报价标签
		ui.billingPayPanel.billingPayBelongPanel.getLblWholeVehicleAppfee().setVisible(true);
		// 清空产品，设置为整车产品
		Common.cleanProductToWholeVehicle(ui);

		// 设置运输性质为“整车”
		Common.setProductCode(ui, bean, ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE);

		Common.setUIEnableFalse(ui);

		// 设置经过营业部代收货款编辑状态
		Common.setPassDeptCodEnabled(ui, bean);		
		
	}
	
	/**
	 * 设置界面为整车界面编辑状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-23 下午4:04:14
	 */
	public static void setWholeVehicleEdit(WaybillEditUI ui,WaybillPanelVo bean){
		//设置开单界面编辑状态为可编辑
		Common.setUIEnableTrue(bean,ui);
		//设置整车无贵重物品
		bean.setPreciousGoods(false);
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			//产品类型
			ui.pictureTransferInfoPanel.getCombProductType().setEnabled(false);
		}else{
			//产品类型
			ui.transferInfoPanel.getCombProductType().setEnabled(false);
		}
		//是否经过到达部门
		if(bean.getIsPassDept())
		{
			// 代收货款金额
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
			// 代收货款类型
			ui.incrementPanel.getCombRefundType().setEnabled(true);
			// 经过达到部门的整车,收货人地址不可编辑
			ui.consigneePanel.getTxtConsigneeAddress().setEnabled(false);
		}
		
		//2015/08/17 LiDing add start -->
		// 定义省市区县对象
		// 定义业务工具类
		BusinessUtils bu = new BusinessUtils();
		AddressFieldDto memberBean = bean.getReceiveCustomerAreaDto();

		memberBean = Common.getAddressFieldInfoByCode(ui,
				memberBean.getProvinceId(), memberBean.getCityId(),
				memberBean.getCountyId());

		ui.consigneePanel.getTxtConsigneeArea().setText(
				bu.getAddressAreaText(memberBean));

		ui.consigneePanel.getTxtConsigneeArea().setAddressFieldDto(memberBean);
		// 省市区不可编辑
		ui.consigneePanel.getTxtConsigneeArea().setEnabled(false);
		//2015/08/17 LiDing add end -->
		
		// 根据是否整车设置上门接货和司机工号是否可编辑
		if(bean.getIsWholeVehicle()){
			ui.basicPanel.getCboReceiveModel().setEnabled(false);
			ui.basicPanel.getTxtDriverNumber().setEnabled(false);
		}else{
			ui.basicPanel.getCboReceiveModel().setEnabled(true);
			ui.basicPanel.getTxtDriverNumber().setEnabled(true);
		}
		//整车不能使用优惠券
		ui.incrementPanel.getTxtPromotionNumber().setEnabled(false);
		
		// 是否经过营业部(整车不允许编辑是否经过营业部,由约车信息而定,根据MANA-389修改)
		ui.basicPanel.getChbPassDept().setEnabled(false);
		// 判断提货网点是否为空
		if(bean.getCustomerPickupOrgCode()!=null && StringUtils.isNotEmpty(bean.getCustomerPickupOrgCode().getCode())){
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				//目的站
				ui.pictureTransferInfoPanel.getBtnQueryBranch().setEnabled(false);			
				//提货网点
				ui.pictureTransferInfoPanel.getTxtDestination().setEnabled(false);
			}else{
				//目的站
				ui.transferInfoPanel.getBtnQueryBranch().setEnabled(false);			
				//提货网点
				ui.transferInfoPanel.getTxtDestination().setEnabled(false);
			}
			//整车不能使用电子地图
			ui.buttonPanel.getBtnGIS().setEnabled(false);
			//查询网点
			ui.buttonPanel.getBtnSearchBranch().setEnabled(false);
		}else{
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				//目的站
				ui.pictureTransferInfoPanel.getBtnQueryBranch().setEnabled(true);			
				//提货网点
				/**
				 * 禁用目的站编辑  防止客户校验后修改目的站内容
				 * @author Foss-278328-hujinyang
				 * @time 20151226 13:10
				 */
				ui.pictureTransferInfoPanel.getTxtDestination().setEnabled(false);
			}else{
				//目的站
				ui.transferInfoPanel.getBtnQueryBranch().setEnabled(true);			
				//提货网点
				/**
				 * 禁用目的站编辑  防止客户校验后修改目的站内容
				 * @author Foss-278328-hujinyang
				 * @time 20151226 13:10
				 */
				ui.transferInfoPanel.getTxtDestination().setEnabled(false);
			}
			//电子地图
			ui.buttonPanel.getBtnGIS().setEnabled(true);
			//查询网点
			ui.buttonPanel.getBtnSearchBranch().setEnabled(true);
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
	 * 设置其它待处理信息（提货网点、走货线路、整车信息）
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-25 上午8:59:46
	 */
	public static void setOtherPendingData(WaybillEditUI ui,WaybillPanelVo vo){
		/**
		 * 非离线时补录时，只要重新提货网点 因为其它信息已于在线时查询出来了
		 * （提货网点的线路信息没有保存在数据库中，导入需要重新走业务逻辑）
		 */
		// 提货网点
		ShowPickupStationDialogAction action = new ShowPickupStationDialogAction();
		action.setInjectUI(ui);
		// 整车的提货网点不需要设置线路和空运配载及时效
		if (!ui.basicPanel.getChbWholeVehicle().isSelected()) {
			// 设置线路
			action.setLoadLine(vo);
			// 设置空运配载
			action.setAirDeptEnabled(vo);
		}else{
			/**
			 * BUG-7569
			 * 设置界面可显示
			 */
			Common.setWholeVehicleData(ui, vo);
			Common.setWholeVehicleEdit(ui, vo);
		}
	}
	
	/**
	 * 处理优惠券
	 * @author WangQianJin
	 * @date 2013-5-18 下午6:53:30
	 */
	public static void executeCoupon(WaybillPanelVo bean,WaybillEditUI ui) {
		// 优惠卷是否为空
		CouponInfoDto couponInfoDto = getCouponInfoDto(bean, ui);
		

		if (couponInfoDto != null) {

			CouponInfoResultDto dto = waybillService.validateCoupon(couponInfoDto);
			if (dto != null) {
				if (!dto.isCanUse()) {
					// 不能使用优惠券的原因
					MsgBox.showInfo(dto.getCanNotUseReason());
					bean.setPromotionsCode(null);
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
	private static CouponInfoDto getCouponInfoDto(WaybillPanelVo bean,WaybillEditUI ui) {
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
				// 计价金额
				amountInfo.setValuationAmonut(bean.getDeliveryGoodsFee());
			}else{
				// 计价条目编码-保险费
				amountInfo.setValuationCode(waybillChargeDtlEntity.getPricingEntryCode());
				// 计价金额
				amountInfo.setValuationAmonut(waybillChargeDtlEntity.getAmount());
			}			
			amountInfoList.add(amountInfo);
		}
		waybillInfo.setAmountInfoList(amountInfoList);
		couponInfo.setWaybillInfo(waybillInfo);
		couponInfo.setCouponNumber(bean.getPromotionsCode());
		return couponInfo;
	}
	
	/**
	 * 根据客户编码查询获得客户类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-21 下午3:22:31
	 */
	public static boolean isChargeMode(String chargeType){

		//非空判断
		if(null != chargeType){
			boolean chargeMode = DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(chargeType) ? true : false;
			return chargeMode;
		}else{
			return false;
		}
		
	}
	
	
	/**
	 * 根据客户编码查询获得客户类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-21 下午3:22:31
	 */
	public static CusBargainEntity getCusBargainEntity(String custCode) {
		if (StringUtils.isNotEmpty(custCode)) {
			// 获得合同信息
			CusBargainEntity cusBargain = waybillService
					.queryCusBargainByCustCode(custCode);
			return cusBargain;
		} else {
			return null;
		}
	}
	
    /**
     * TODO 需要进行性能优化
     * 根据运单号、处理类型，返回对应的运单状态
     * @author 026123-foss-lifengteng
     * @date 2013-5-29 下午5:16:19
     */
    public static String getStatusByWaybillNo(String waybillNo,String pendingType,String termsCode){
    	//最终值
    	String active = CommonUtils.getNameFromDict(pendingType,termsCode);
    	//判断是否为已处理
    	if(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(pendingType)){
    		//实体对象
    		ActualFreightEntity entity = waybillService.queryActualFreightByNo(StringUtil.defaultIfNull(waybillNo));
    		if(null == entity){
    			return i18n.get("foss.gui.creating.common.msgBox.noActualFreightRecord");
    		}
    		
    		//运单当前状态：生效、作废、中止
    		String status = StringUtil.defaultIfNull(entity.getStatus());
    		//作废
    		if(WaybillConstants.OBSOLETE.equals(status)){
    			return CommonUtils.getNameFromDict(WaybillRfcConstants.INVALID,DictionaryConstants.WAYBILL_RFC_TYPE_CUSTOMER);
    		}
    		//中止
    		else if(WaybillConstants.ABORTED.equals(status)){
    			return CommonUtils.getNameFromDict(WaybillRfcConstants.ABORT,DictionaryConstants.WAYBILL_RFC_TYPE_INSIDE);
    		}else{
    			return active;
    		}
    	}else{
    		return active;
    	}
    }
	
	/**
	 * 根据客户编码和银行帐号编码查询客户开户银行实体类
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-12 下午7:24:18
	 */
	public static CusAccountEntity queryCusAccountByAccount(String custCode,String account){
		//客户开户银行实体类集合对象
		List<CusAccountEntity> accountList = waybillService.queryBankAccountByCode(custCode);
		CusAccountEntity cusAccount = null;
		//集合非空判断
		if(CollectionUtils.isNotEmpty(accountList)){
			//根据银行帐号进行遍历
			for (CusAccountEntity entity : accountList) {
				//查询银行帐号信息
				if(StringUtil.defaultIfNull(account).equals(entity.getAccountNo())){
					cusAccount = entity;
				}
			}
		}
		return cusAccount;
	}
    
	/**
	 * 根据部门Code判断该部门是否属性于当前集中开单组中
	 * @author update by WangQianJin
	 * @date 2013-08-02
	 */
	public static boolean isBelongCurrentDept(String saleCode) {
		// 获取当前用户
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 当前用户所在部门
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		//根据部门名称进行查询
		//List<SaleDepartmentEntity> list = waybillService.querySaleDepartmentByNameForCentralized(deptName,dept.getCode());
		List<SalesBillingGroupEntity> list= waybillService.querySalesBillGroupByCodeAndBillCode(saleCode,dept.getCode());
		//判断集合是否为空
		if(CollectionUtils.isNotEmpty(list)){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * 查询其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:22:50
	 */
	public static void queryOtherChargeData(WaybillEditUI ui, WaybillPanelVo bean) {
		if(null != ui.getPictureWaybillType() && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			if(bean.getGoodsVolumeTotal() == null){
				bean.setGoodsVolumeTotal(BigDecimal.ZERO);
			}
			if(bean.getGoodsWeightTotal() == null){
				bean.setGoodsWeightTotal(BigDecimal.ZERO);
			}
		}
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(CommonUtils.getQueryOtherChargeParam(bean));

		// CommonUtils.getOtherChargeList中不含更改费
		List<OtherChargeVo> voList = CommonUtils.getOtherChargeList(list);
		if (voList != null) {
			if (!voList.isEmpty()) {
				ui.incrementPanel.setChangeDetail(Common.otherChargeCompare(ui,voList));
			}
		}
	}
	
	/**
	 * 查询其他费用
	 * 		-- zxy 20131017 BUG-57425  新增方法	
	 * @author deppon-157229-zxy
	 * @version 1.0 2013-10-17 下午3:04:07
	 * @param ui
	 * @param bean
	 * @return 返回查询到的结果
	 */
	public static List<OtherChargeVo> getOtherChargeData(WaybillEditUI ui, WaybillPanelVo bean) {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(CommonUtils.getQueryOtherChargeParam(bean));

		// CommonUtils.getOtherChargeList中不含更改费
		List<OtherChargeVo> voList = CommonUtils.getOtherChargeList(list);
		if (voList != null) {
			if (!voList.isEmpty()) {
				return Common.otherChargeCompare(ui,voList);
			}
		}
		return null;
	}

	/**
	 * 
	 * 将原有其他费用与新查询出来其他费用进行比较，然后删除重复的项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午03:03:01
	 */
	public static List<OtherChargeVo> otherChargeCompare(WaybillEditUI ui,List<OtherChargeVo> voList) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();
		if (data != null) {
			if (!data.isEmpty()) {
				for (int i = 0; i < voList.size(); i++) {
					OtherChargeVo queryVo = voList.get(i);
					//判断该其他费用类型是否存在--定价体系优化项目POP-557
					Boolean islive = false;
					for (int j = 0; j < data.size(); j++) {
						OtherChargeVo tableVo = data.get(j);
						if (tableVo.getCode().equals(queryVo.getCode())) {
							data.remove(j);
							data.add(j, queryVo);
							//定价体系优化项目POP-557
							islive = true;
						}
					}
					//定价体系优化项目POP-557
					if(!islive){
						data.add(queryVo);
					}
				}
				return data;
			} else {
				return voList;
			}
		} else {
			return voList;
		}
	}
	
	/**
	 * 
	 * 初始化其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午05:12:36
	 */
	public static void calculateOtherCharge(WaybillEditUI ui, WaybillPanelVo bean) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();

		if (data != null && !data.isEmpty()) {
			BigDecimal otherChargeSum = BigDecimal.ZERO;
			// 其他费用合计
			for (OtherChargeVo vo : data) {
				BigDecimal money = new BigDecimal(vo.getMoney());
				otherChargeSum = otherChargeSum.add(money);
			}
			// 其他费用
			bean.setOtherFee(otherChargeSum);
			// 画布其他费用
			bean.setOtherFeeCanvas(otherChargeSum.toString());
		}
	}
	
	/**
	 * 将返单类别设置到储运事项里
	 * @param bean
	 * 
	 * 版本       	用户			时间 			内容
	 * 0001 	zxy 		20130912 		新增：返单类别内容设置到储运栏
	 */
	public static void setTransportationRemark(WaybillPanelVo bean){
		/**
		 * 将返单类别设置到储运事项里
		 */
		if (bean.getReturnBillType()!=null) {
			String remark = bean.getTransportationRemark();
			if(remark==null){
				remark="";
			}
			List<DataDictionaryValueEntity> list = waybillService.queryReturnBillType();
			/**
			 * 清空所有的返单类别
			 */				
			if(list!=null && list.size()>0){
				for (DataDictionaryValueEntity dataDictionary : list) {
					if(dataDictionary!=null){
						remark = remark.replace(dataDictionary.getValueName()+ ";", "");	
					}					
				}
			}
			if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {				
				bean.setTransportationRemark(bean.getReturnBillType().getValueName()+ ";" + remark);				
			}else{								
				bean.setTransportationRemark(remark);
			}	
		} 
	}
	
	/**
	 * 当送货费被修改并将发货客户修改为月结客户发货客户时，原来在月结客户时手动修改的送货费需要重新进行计算
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-14 下午7:58:22
	 */
	public static void resetDeliverGoodsFee(WaybillPanelVo bean,WaybillEditUI ui){
		if(bean.getReceiveMethod() != null){	//zxy 20140103 MANA-409(邮件bug,非此需求) 增加bean.getReceiveMethod()空判断
			String code = bean.getReceiveMethod().getValueCode();
			//判断：若提货方式为送货进仓时，若为月结客户则送货费可编辑
			if(WaybillConstants.DELIVER_STORAGE.equals(code)){
				//不是月结客户
				if(bean.getChargeMode()==null || !bean.getChargeMode()){
					ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);
					//设置提交为灰，使客户重新计算总运费
					Common.setSaveAndSubmitFalse(ui);
					//标识为非手动修改过，这样点击 计算总运费的值才会重新计算
					bean.setHandDeliveryFee(BigDecimal.valueOf(0));
				}else{
					//月结客户
					ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);
				}
			}
		}
		
	}
	
	/**
	 *  获取时效相关信息
	 */
	public static IdentityEffectivePlanVo identityEffectivePlan(WaybillPanelVo bean) {
		String departDeptCode = null;
		String departDeptName = null;
		IdentityEffectivePlanVo outVo = new IdentityEffectivePlanVo();
		/**
		 * 判断是否是集中开单
		 */
		if (bean.getPickupCentralized() != null && bean.getPickupCentralized()) {
			//判断是否是 异地开单
			String waybillNo =  bean.getWaybillNo();
			String  createOrgCode= bean.getCreateOrgCode();
			if(StringUtil.isNotEmpty(waybillNo)
					&& StringUtil.isNotEmpty(createOrgCode)){
				WaybillPictureEntity entity = new WaybillPictureEntity();
				entity.setWaybillNo(waybillNo);
				entity.setActive(FossConstants.YES);
				entity = waybillService.queryWaybillPictureByEntity(entity);
				if(entity!=null && FossConstants.NO.equals(entity.getLocal())){
					//根据本属开单组去查询
					createOrgCode = entity.getLocalBillGroupCode();
				}
			}
			SaleDepartmentEntity saleDepartment = waybillService.queryPickupCentralizedDeptCode(createOrgCode);
			if (saleDepartment != null) {
				departDeptCode = saleDepartment.getCode();
				departDeptName = saleDepartment.getName();
			}
		} else {
			departDeptCode = bean.getReceiveOrgCode();
			departDeptName = bean.getReceiveOrgName();
		}
		outVo.setDepartDeptCode(departDeptCode);
		outVo.setDepartDeptName(departDeptName);
		outVo.setArriveDetpCode(bean.getCustomerPickupOrgCode().getCode());
		outVo.setArriveDetpName(bean.getCustomerPickupOrgCode().getName());
		outVo.setExist(waybillService.identityEffectivePlan(departDeptCode,bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode(), new Date()));
		return outVo;
	}
	
	/**
	 *  获取偏线时效相关信息
	 */
	public static IdentityEffectivePlanVo identityOuterEffectivePlan(WaybillPanelVo bean) {
		String departDeptCode = null;
		String departDeptName = null;
		IdentityEffectivePlanVo outVo = new IdentityEffectivePlanVo();
		/**
		 * 判断是否是集中开单
		 */
		if (bean.getPickupCentralized() != null && bean.getPickupCentralized()) {
			//判断是否是 异地开单
			String waybillNo =  bean.getWaybillNo();
			String  createOrgCode = bean.getCreateOrgCode();
			if(StringUtil.isNotEmpty(waybillNo)&& StringUtil.isNotEmpty(createOrgCode)){
				WaybillPictureEntity entity = new WaybillPictureEntity();
				entity.setWaybillNo(waybillNo);
				entity.setActive(FossConstants.YES);
				entity = waybillService.queryWaybillPictureByEntity(entity);
				if(entity!=null && FossConstants.NO.equals(entity.getLocal())){
					//根据本属开单组去查询
					createOrgCode = entity.getLocalBillGroupCode();
				}
			}
			SaleDepartmentEntity saleDepartment = waybillService.queryPickupCentralizedDeptCode(createOrgCode);
			if (saleDepartment != null) {
				departDeptCode = saleDepartment.getCode();
				departDeptName = saleDepartment.getName();
			}
		} else {
			departDeptCode = bean.getReceiveOrgCode();
			departDeptName = bean.getReceiveOrgName();
		}
		outVo.setDepartDeptCode(departDeptCode);
		outVo.setDepartDeptName(departDeptName);
		outVo.setArriveDetpCode(bean.getCustomerPickupOrgCode().getCode());
		outVo.setArriveDetpName(bean.getCustomerPickupOrgCode().getName());
		outVo.setExist(waybillService.identityOuterEffectivePlan(departDeptCode,bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode(), new Date()));
		return outVo;
	}
		
	/**
	 * 调用CRM接口 给WaybillPendingDto对象赋值客户信息
	 * @param waybillPendingDto
	 * @return
	 */
	
	public static WaybillPendingDto getOrderCustInfo(WaybillPendingDto waybillPendingDto){
		String orderNo = waybillPendingDto.getWaybillPending().getOrderNo();
		if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillPendingDto.getWaybillPending().getPendingType())){
			
			if(!"".equals(orderNo) && null!=orderNo){
				CrmOrderDetailDto orderDetailVo = waybillService.importOrder(StringUtil.defaultIfNull(orderNo));
				if(null != orderDetailVo){
					//客户信息
					WaybillPendingEntity  waybillPending = waybillPendingDto.getWaybillPending();
				 
					// 发货客户ID
					waybillPending.setDeliveryCustomerId(orderDetailVo.getShipperId());
					 // 发货客户编码
					/**
					 * 将代码进行了修改，代表目的不变
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-11-19下午17:03
					 */
					String shipperNumber=orderDetailVo.getShipperNumber();
					if (StringUtils.isNotEmpty(shipperNumber)) {
						waybillPending.setDeliveryCustomerCode(shipperNumber);
						/**
						 * 通过客户编码查询综合客户信息，取得特安上限值,并赋值给waybillpanelvo的特安上限保价和代收货款保价
						 * @author:218371-foss-zhaoyanjun
						 * @date:2014-11-19下午15:34
						 */
						CustomerDto customerDto = waybillService.queryCustInfoByCodeNew(shipperNumber);
						//查询客户是否精准包裹权限
						CusBargainEntity bargainEntity = waybillService.queryCusBargainByCustCode(shipperNumber);
						if(customerDto!=null&&customerDto.getTeanLimit()!=null){
							BigDecimal vipInsuranceAmount=new BigDecimal(customerDto.getTeanLimit());
							waybillPending.setVipInsuranceAmount(vipInsuranceAmount);
							waybillPending.setVipCollectionPaymentLimit(vipInsuranceAmount);
						}
						if(customerDto != null && CollectionUtils.isNotEmpty(customerDto.getBargainList())){
							CusBargainEntity cusBargainEntity = customerDto.getBargainList().get(0);
							waybillPending.setStartCentralizedSettlement(cusBargainEntity.getAsyntakegoodsCode());
							waybillPending.setStartContractOrgCode(cusBargainEntity.getUnifiedCode());
							//waybillPending.setStartContractOrgName(queryContractOrgName(cusBargainEntity.getUnifiedCode()));
							waybillPending.setStartReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
						}else{
							waybillPending.setStartCentralizedSettlement(FossConstants.NO);
						}
						//设置精准包裹
						if(bargainEntity!=null){
							waybillPending.setDeliveryCustomerIsAccuratePackage(bargainEntity.getIsAccuratePackage());
						}
					}else{
						waybillPending.setDeliveryCustomerCode(orderDetailVo.getShipperId());
					}
					//设置发票标记
					setInvoiceInfo(waybillPending.getDeliveryCustomerCode(),waybillPending,"DELIVER");
				    // 发货客户名称
					waybillPending.setDeliveryCustomerName(orderDetailVo.getShipperName());

				    // 发货客户手机
					waybillPending.setDeliveryCustomerMobilephone(orderDetailVo.getContactMobile());

				    // 发货客户电话
					waybillPending.setDeliveryCustomerPhone(orderDetailVo.getContactPhone());

				    // 发货客户联系人
					waybillPending.setDeliveryCustomerContact(orderDetailVo.getContactName());
				    
				    //发货联系人Id
					waybillPending.setDeliveryCustomerContactId(orderDetailVo.getContactManId());
					

				    String proviceCode = null;
				    if (orderDetailVo.getContactProvince() != null) {
					//proviceCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_PROVINCE, orderDetailVo.getContactProvince(), null);
				    	proviceCode = orderDetailVo.getContactProvinceCode();
				    }
				    // 联系人省份
				    waybillPending.setDeliveryCustomerProvCode(proviceCode);
				    String cityCode = null;
				    if (orderDetailVo.getContactCity() != null && proviceCode != null) {
					//cityCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_CITY, orderDetailVo.getContactCity(), proviceCode);
				    	cityCode = orderDetailVo.getContactCityCode();
				    }
				    // 联系人城市
				    waybillPending.setDeliveryCustomerCityCode(cityCode);

				    String areaCode = null;
				    if (orderDetailVo.getContactArea() != null && cityCode != null) {
					//areaCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_COUNTY, orderDetailVo.getContactArea(), cityCode);
				    	areaCode = orderDetailVo.getContactAreaCode();
				    }
				    // 联系人区县
				    waybillPending.setDeliveryCustomerDistCode(areaCode);
								 
					// 发货具体地址
					waybillPending.setDeliveryCustomerAddress(orderDetailVo.getContactAddress());
					
					//地址备注
					waybillPending.setDeliveryCustomerAddressNote(orderDetailVo.getContactAddrRemark());
					waybillPending.setReceiveCustomerAddressNote(orderDetailVo.getReceiverCustAddrRemark());
					 // 渠道客户ID：官网用户名
					//waybillPending.setChannelCustId(orderDetailVo.getChannelCustId());

				    // 收货客户id
					waybillPending.setReceiveCustomerId(orderDetailVo.getReceiverCustId());

				    // 收货客户编码
					waybillPending.setReceiveCustomerCode(orderDetailVo.getReceiverCustNumber());
					if(StringUtils.isNotEmpty(orderDetailVo.getReceiverCustNumber())){
						CustomerDto customerDto = waybillService.queryCustInfoByCodeNew(orderDetailVo.getReceiverCustNumber());
						if(customerDto != null && CollectionUtils.isNotEmpty(customerDto.getBargainList())){
							CusBargainEntity cusBargainEntity = customerDto.getBargainList().get(0);
							waybillPending.setArriveCentralizedSettlement(cusBargainEntity.getAsyntakegoodsCode());
							waybillPending.setArriveContractOrgCode(cusBargainEntity.getUnifiedCode());
							//waybillPending.setArriveContractOrgName(queryContractOrgName(cusBargainEntity.getUnifiedCode()));
							waybillPending.setArriveReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
						}else{
							waybillPending.setArriveCentralizedSettlement(FossConstants.NO);
						}
					}
					//设置发票标记
					setInvoiceInfo(waybillPending.getReceiveCustomerCode(),waybillPending,"RECIVE");
				    // 收货客户名称
					waybillPending.setReceiveCustomerName(orderDetailVo.getReceiverCustName());

				    // 收货客户手机
					waybillPending.setReceiveCustomerMobilephone(orderDetailVo.getReceiverCustMobile());

				    // 收货客户电话
					waybillPending.setReceiveCustomerPhone(orderDetailVo.getReceiverCustPhone());

				    // 收货客户联系人
					waybillPending.setReceiveCustomerContact(orderDetailVo.getReceiverCustName());
				    
					//收货联系人ID
					waybillPending.setReceiveCustomerContactId("");
					
					//判断PDA省市区是否为空DMANA-4296
					if(isProCityReIsNull(waybillPendingDto.getWaybillPending().getReceiveCustomerProvCode(),
							waybillPendingDto.getWaybillPending().getReceiveCustomerCityCode(),
							waybillPendingDto.getWaybillPending().getReceiveCustomerDistCode())){
						proviceCode = null;// 清空
					    if (orderDetailVo.getReceiverCustProvince() != null) {
						//proviceCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_PROVINCE, orderDetailVo.getReceiverCustProvince(), null);
					    	proviceCode = orderDetailVo.getReceiverCustProvinceCode();
					    	// CRM接货人省份
						    waybillPending.setReceiveCustomerProvCode(proviceCode);
					    }
					    
					    cityCode = null;// 清空
					    if (orderDetailVo.getReceiverCustCity() != null && proviceCode != null) {
						//cityCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_CITY, orderDetailVo.getReceiverCustCity(), proviceCode);
					    	cityCode = orderDetailVo.getReceiverCustCityCode();
					    	//CRM接货人城市
					    	waybillPending.setReceiveCustomerCityCode(cityCode);
					    }
					    
					    areaCode = null;// 清空
					    if (orderDetailVo.getReceiverCustArea() != null && cityCode != null) {
						//areaCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_COUNTY, orderDetailVo.getReceiverCustArea(), cityCode);
					    	areaCode = orderDetailVo.getReceiverCustAreaCode();
					    	//CRM接货人区县
					    	waybillPending.setReceiveCustomerDistCode(areaCode);
					    }
					}else{
						//只要PDA省市区一个不为空，则都取PDA省市区
						// PDA接货人省份
					    waybillPending.setReceiveCustomerProvCode(waybillPendingDto.getWaybillPending().getReceiveCustomerProvCode());
					    
					    //PDA接货人城市
				    	waybillPending.setReceiveCustomerCityCode(waybillPendingDto.getWaybillPending().getReceiveCustomerCityCode());
					    
				    	//PDA接货人区县
				    	waybillPending.setReceiveCustomerDistCode(waybillPendingDto.getWaybillPending().getReceiveCustomerDistCode());
					}
					
				    // 收货具体地址
					waybillPending.setReceiveCustomerAddress(orderDetailVo.getReceiverCustAddress());
					
					// 货物名称
					waybillPending.setGoodsName(orderDetailVo.getGoodsName());								    
				    // 托运货物总重量
					waybillPending.setGoodsWeightTotal(BigDecimal.valueOf(orderDetailVo.getTotalWeight()));	
					 // 保险声明价值
					if(waybillPending.getInsuranceAmount()==null){
						waybillPending.setInsuranceAmount(orderDetailVo.getInsuredAmount());
					}
				   
					//优惠券编码
					waybillPending.setPromotionsCode(orderDetailVo.getCouponNumber());
					if(waybillPending.getCodAmount()==null){
						 // 代收货款金额
					    if(orderDetailVo.getReviceMoneyAmount()!=null){
					    	waybillPending.setCodAmount(orderDetailVo.getReviceMoneyAmount());
					    }else{
					    	waybillPending.setCodAmount(BigDecimal.ZERO);
					    }
					}
					
				    
					if(waybillPending.getValueAddFee()==null){
						waybillPending.setValueAddFee(BigDecimal.ZERO);
					}
					waybillPending.setOrderPaidMethod(orderDetailVo.getPaymentType());//zxy 20131211 DEFECT-509 新增：设置网单付款方式
					/**
					 * 将查询出的是否电子发票传入waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-10-24下午16:18
					 */
					waybillPending.setIsElectronicInvoice(orderDetailVo.getIsElectronicInvoice());
					/**
					 * 将查询出的发票手机号码传入waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-10-24下午16:18
					 */
					waybillPending.setInvoiceMobilePhone(orderDetailVo.getInvoiceMobilePhone());
					/**
					 * 将查询出的发票邮箱传入waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-10-24下午16:18
					 */
					waybillPending.setEmail(orderDetailVo.getEmail());
					/**
					 * Dmana-9885将CRM运费赋值给waybillPending
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-02-06下午15:50
					 */
					waybillPending.setCrmTransportFee(orderDetailVo.getCrmTransportFee());
					/**
					 * Dmana-9885将CRM运费赋值给waybillPending
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-02-06下午15:50
					 */
					waybillPending.setCrmWeight(new BigDecimal(orderDetailVo.getTotalWeight()));
					/**
					 * Dmana-9885将CRM运费赋值给waybillPending
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-02-06下午15:50
					 */
					waybillPending.setCrmVolume(new BigDecimal(orderDetailVo.getTotalVolume()));
					// 设置PDA待处理信息
					waybillPendingDto.setWaybillPending(waybillPending);					
				}
			}
		}
		
		return waybillPendingDto;
	}
	
	/**
	 * 校验是否为空
	 * @param Pro
	 * @param city
	 * @param regi
	 * @return
	 */
	private static boolean isProCityReIsNull(String pro,String city,String regi){
		if(StringUtils.isEmpty(pro)&&
				StringUtils.isEmpty(city)&&
				StringUtils.isEmpty(regi)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 初始化开单界面 的计费类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-31 下午4:57:37
	 */
	public static void initCombBillingType(WaybillEditUI ui) {
		List<DataDictionaryValueEntity> list = waybillService.queryBillingWay();
		DefaultComboBoxModel model = ui.getCombBillingType();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			model.addElement(vo);
		}
		ui.getCanvasContentPanel().getCombBillingType().setModel(model);
	}
	
	/**
	 * 
	 * 设置自提件运输性质
	 * @author WangQianJin
	 * @date 2013-4-24
	 */
	public static void economyGoodsTypeListener(WaybillPanelVo bean,WaybillEditUI ui){
		//如果是自提件业务，则重新获取产品信息
		if(bean.getIsEconomyGoods()!=null && bean.getIsEconomyGoods()){
			if(bean.getEconomyGoodsType()!=null && bean.getEconomyGoodsType().getValueCode()!=null && !"".equals(bean.getEconomyGoodsType().getValueCode())){				
				//根据渠道CODE和当前时间获取产品信息
				List<ProductEntity> productList=waybillService.getProductOfMinFeePlanByChannelCodeAndSpecifiedDate(bean.getEconomyGoodsType().getValueCode(), new Date());
				//设置产品信息
				setProductTypeModel(productList,ui,bean);
			}else{
				//如果是自提件并且自提件类型为空，则删除运输性质
				ui.getProductTypeModel().removeAllElements();				
			}
		}		
	}
	
	/**
	 * 
	 * 设置产品到数据模型
	 * @author WangQianJin
	 * @date 2013-08-16
	 */
	@SuppressWarnings("unchecked")
	public static void setProductTypeModel(List<ProductEntity> productList,WaybillEditUI ui,WaybillPanelVo bean){
		if(CollectionUtils.isEmpty(productList)){
			return;
		}
		ProductEntityVo firstProduct=bean.getProductCode();
		ui.getProductTypeModel().removeAllElements();	
		List<ProductEntity> peLst = new ArrayList<ProductEntity>();
		ProductEntityVo vo = null;
		for (ProductEntity product : productList) {	
			//zxy 20130929 BUG-56426 start 新增：如果是快递包裹，则过滤
			if(CommonUtils.directDetermineIsExpressByProductCode(product.getCode())){
				continue;
			}
			peLst.add(product);
			//zxy 20130929 BUG-56426 end 新增：如果是快递包裹，则过滤
			if(!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode())){
				vo = new ProductEntityVo();
				//将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo);
				vo.setDestNetType(product.getDestNetType());
				ui.getProductTypeModel().addElement(vo);
				if(firstProduct==null){
					firstProduct=vo;
				}
			}
		}
		productList = peLst;
		//如果运输性质为空，则设置查询出的第一个产品为默认产品
		if(firstProduct!=null && bean.getProductCode()==null){
			bean.setProductCode(firstProduct);
		}	
	}
	
	/**
	 * 
	 * 设置自提件提货方式
	 * @author WangQianJin
	 * @date 2013-08-16
	 */
	public static void setEconomyReceiveMethod(WaybillPanelVo bean){	
		if(bean.getProductCode()!=null){
			/**
			 * 创建提货方式对象
			 */
			DataDictionaryValueVo receiveMethod = new DataDictionaryValueVo();
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
				//空运自提
				receiveMethod.setValueCode(WaybillConstants.AIRPORT_PICKUP);
				receiveMethod.setValueName(i18n.get("foss.gui.creating.transferInfoPanel.airportPickup.label"));								
			} else {
				//汽运自提
				receiveMethod.setValueCode(WaybillConstants.SELF_PICKUP);
				receiveMethod.setValueName(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.pickup"));
			}
			bean.setReceiveMethod(receiveMethod);			
		}		
	}
	
	
	/**
	 * 初始化提货方式
	 */
	public static void initCombPickMode(WaybillEditUI ui) {
		ui.getPikcModeModel().removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();		
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			ui.getPikcModeModel().addElement(vo);
		}
	}
	
	/**
	 * 初始化运输性质
	 */
	public static void initCombProductType(WaybillPanelVo bean,WaybillEditUI ui){		
		if(bean.getPickupCentralized()!=null && bean.getPickupCentralized()){
			//集中开单不做处理
		}else{
			//营业部开单
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();			
			//根据当前用户所在部门查询部门所属产品
			List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(dept.getCode(), WaybillConstants.TYPE_OF_CARGO, new Date());
			if(CollectionUtils.isEmpty(list)){
				return;
			}
			//设置产品信息
			setProductTypeModel(list,ui,bean);	
		}
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
			if(bean.getReceiveMethod()!=null && bean.getReceiveMethod().getValueCode()!=null){
				if(bean.getProductCode()!=null){
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
					//空运自提
					if(WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())
							|| WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
						bdelivery=true;
					} 
				}								
			}else{
				bdelivery=true;
			}
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
	
	/**
	 * 设置费率范围
	 * @param bean
	 */
	public static void setRate(GuiResultBillCalculateDto gDto,WaybillPanelVo bean,WaybillEditUI ui){
		ui.incrementPanel.getTxtInsuranceRate().setEnabled(false);
		if(bean != null && gDto !=null 
				&& gDto.getMinFeeRate() != null && gDto.getMaxFeeRate() != null){
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
			bean.setInsuranceFee(gDto.getCaculateFee());

			if(FossConstants.YES.equals(gDto.getCanmodify())){
				ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
			}
			if(ui.pictureCargoInfoPanel!=null){
				String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
				String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
				if(ui.billingPayPanel.getBtnSubmitAndNextSingle() != null && 
						(StringUtils.isBlank(weight) || new BigDecimal(weight).compareTo(new BigDecimal(0)) == 0) &&
						(StringUtils.isBlank(volume) || new BigDecimal(volume).compareTo(new BigDecimal(0)) ==0)){
					ui.billingPayPanel.getBtnSubmit().setEnabled(true);
					ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
				}
			}
		}else{
			if(bean != null){
				bean.setInsuranceRate(BigDecimal.ZERO);
			}
			if(ui.billingPayPanel.getBtnSubmitAndNextSingle() != null){
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);
				ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
			}
			throw new WaybillValidateException(i18n.get("foss.pkp.creating.itserivce.notconfig.default.insurance"));
		}
	}
	
	/**
	 * 获取整车费率范围
	 * @param bean
	 */
	public static GuiResultBillCalculateDto getInsuranceRate(WaybillPanelVo bean) {
		//获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection =null;
		// 上门接货优先读取上门接货的价格
		if (bean.getPickupToDoor() != null && bean.getPickupToDoor()) {
			productPriceDtoCollection=getQueryParamCollection(bean, FossConstants.YES);

		} else {
			productPriceDtoCollection=getQueryParamCollection(bean, FossConstants.NO);
		}
		if(StringUtil.isNotEmpty(bean.getIsCustCircle()) &&
				"Y".equals(bean.getIsCustCircle()) &&
				bean.getCusBargainNewEntity()!=null &&
				StringUtil.isNotEmpty(bean.getCusBargainNewEntity().getCusCode())){
			productPriceDtoCollection.setCustomerCode(bean.getCusBargainNewEntity().getCusCode());
		}
		productPriceDtoCollection.setWaybillNo(bean.getWaybillNo());
		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();
		//获取保价费
		GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceCollection(bean);
		if (insuranceCollection != null) {
			priceEntities.add(insuranceCollection);//加入增值服务
		}
		productPriceDtoCollection.setPriceEntities(priceEntities);
		GuiResultBillCalculateDto gDto=null;
		for(int i=0;i<priceEntities.size();i++){
			GuiQueryBillCalculateSubDto dto =priceEntities.get(i);
			if( PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(dto.getPriceEntityCode())
//					&&
//				PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productCode)	
					){
				
				gDto=waybillService.getProductPriceDtoOfWVHAndBF(productPriceDtoCollection);
			}
		}
		return gDto;
		
	  
	}
	
	/**
	 * 优惠券冲减费用
	 * 对于运费在calculateTotalFee中进行冲减
	 * 对于综合信息费则在collectionForOtherFee中进行冲减
	 * 
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static void offsetCouponFee(WaybillEditUI ui,WaybillPanelVo bean) {
		// 优惠费
		BigDecimal couponFee = CommonUtils.defaultIfNull(bean.getCouponFree());
		// 优惠类型
		String type = CommonUtils.defaultIfNull(bean.getCouponRankType());
		
		// 是否有优惠金额
		if (couponFee.compareTo(BigDecimal.ZERO) > 0) {
			// 校验优惠类型是否符合条件
			CommonUtils.validateCouponType(type);
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
			}else if (PriceEntityConstants.PRICING_CODE_BF.equals(type)) {
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
			
			//重新计算总费用
			CalculateFeeTotalUtils.resetCalculateFee(bean);
		}
	}
	
	
	/**
	 * 检验优惠冲减后的费用是否正确
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static void validateCouponFee(WaybillEditUI ui,String feeName,BigDecimal fee){
		//检验优惠费用是否小于0
		if(BigDecimal.ZERO.compareTo(fee) > 0 ){
			Common.setSaveAndSubmitFalse(ui);
			throw new WaybillSubmitException("对不起，您使用优惠券冲减【"+CommonUtils.convertFeeToName(feeName)+"】后的金额小于零，冲减后的金额为："+fee);
		}
	}
	
	/**
	 * 获取默认综合信息服务费的值
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static BigDecimal getDefaultZhxxFee(WaybillPanelVo bean){
		BigDecimal zhxx=null;
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(CommonUtils.getQueryOtherChargeParam(bean));
		//遍历集合
		if (CollectionUtils.isNotEmpty(list)) {
			for (ValueAddDto dto : list) {
				if (PriceEntityConstants.PRICING_CODE_ZHXX.equals(dto.getSubType())) {
					zhxx = dto.getCaculateFee();
					break;
				}
			}
		}
		return zhxx;
	}
	
	
	 /*
     * 获取查询参数
     */
	public static GuiQueryBillCalculateSubDto getInsuranceCollection(
			WaybillPanelVo bean) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
		queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setWoodenVolume(null);// 木架体积
		queryDto.setInsuranceAmount(bean.getInsuranceAmount());//保险声明价值
		queryDto.setInsuranceRate(bean.getInsuranceRate());//保价费率
		return queryDto;
	}
	
	public static List<CustomerQueryConditionDto> getCustomerQueryConditionDto(CustomerQueryConditionDto dto){
		if(dto.getCustCode()!=null){
			List<CustomerQueryConditionDto> contacts = waybillService.queryCustomerByCondition(dto);
			return contacts;
		}
		return null;
	}
	/**
	 * 重置木托列表
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18 
	 * @param bean
	 */
	public static void refreshLabeledGood(WaybillPanelVo bean,WaybillEditUI ui){
		Integer goodsTotal = bean.getGoodsQtyTotal();
		if(goodsTotal != null && goodsTotal > 0){
			if(bean.getLabeledGoodEntities() != null)
				bean.getLabeledGoodEntities().clear();
			else
				bean.setLabeledGoodEntities(new ArrayList<LabeledGoodEntity>());
			for(int i = 0; i < goodsTotal; i++){
				LabeledGoodEntity labeledGoodEntity = new LabeledGoodEntity();
				String serialNo = StringHandlerUtil.lpad(String.valueOf(i + 1), NumberConstants.NUMBER_4, "0");
				labeledGoodEntity.setSerialNo(serialNo);
	//			labeledGoodEntity.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
				bean.getLabeledGoodEntities().add(labeledGoodEntity);
			}
			if(ui.getDialog() != null)
				ui.getDialog().refreshListModel(bean);
			}
	}
	
	/**
	 * 流水号包装类型置空
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18  
	 * @param bean
	 */
	public static void refreshLabeledPackageType(WaybillPanelVo bean){
		List<LabeledGoodEntity> labeledGoodEntityVoList = bean.getLabeledGoodEntities();
		if(labeledGoodEntityVoList != null){
			for(LabeledGoodEntity vo : labeledGoodEntityVoList){
				vo.setPackageType("");
			}
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
	public static ValueAddDto querySalverBillCalculateDto(WaybillPanelVo bean,String salverGoodsPieces) {
		// 打木托
		List<ValueAddDto> salverList = waybillService
				.queryValueAddPriceList(getQueryYokeParam(bean,
						DictionaryValueConstants.PACKAGE_TYPE__SALVER,
						new BigDecimal(salverGoodsPieces), false));

//		if (salverList == null || salverList.isEmpty()) {
//			salverList = waybillService
//					.queryValueAddPriceList(getQueryYokeParam(bean,
//							DictionaryValueConstants.PACKAGE_TYPE__SALVER,
//							new BigDecimal(salverGoodsPieces), true));
//		}

		if (salverList != null && salverList.size() > 0) {
			return salverList.get(0);
		} else
			return null;
	}
	
	/**
	 * 
	 * 获取查询参数
	 * ISSUE-4391
	 * @author	157229-zxy 
	 * @date 2013-11-18 
	 * 
	 */
	private static QueryBillCacilateValueAddDto getQueryYokeParam(WaybillPanelVo bean,
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
		queryDto.setBillTime(bean.getBillTime());//开单时间
		return queryDto;
	
	}
	
	/**
	 * 校验是否可以享有市场营销活动
	 * @创建时间 2014-4-22 上午9:32:41
	 * @创建人： WangQianJin
	 */
	public static void validateActiveDiscount(WaybillPanelVo bean,boolean isValBillAmount) {
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
			activeDto.setBilllingTime(new Date());
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
	 * 封装营销活动参数信息
	 * @创建时间 2014-4-28 下午8:05:29   
	 * @创建人： WangQianJin
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
	
	/**
	 * 封装营销活动参数信息(增值)
	 * @创建时间 2014-4-28 下午8:05:29   
	 * @创建人： WangQianJin
	 */
	public static void settterActiveParamInfoValueAdd(QueryBillCacilateValueAddDto queryDto,WaybillPanelVo bean){
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
		queryDto.setGoodsWeightTotal(bean.getGoodsWeightTotal());
		queryDto.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());	
		queryDto.setBillTime(bean.getBillTime());
		if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){
			BigDecimal transportFee=bean.getTransportFee();
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos=bean.getGuiResultBillCalculateDtos();
			//推广活动折扣后的综合信息费
			if(CollectionUtils.isNotEmpty(guiResultBillCalculateDtos)){
				for (GuiResultBillCalculateDto guiResultBillCalculateDto : guiResultBillCalculateDtos) {
					if (PriceEntityConstants.PRICING_CODE_FRT.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
						//获取折扣信息
						List<GuiResultDiscountDto> disList=guiResultBillCalculateDto.getDiscountPrograms();						
						BigDecimal discountFee=null;
						if(CollectionUtils.isNotEmpty(disList)){
							for(GuiResultDiscountDto dto:disList){
								if(dto!=null && DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE.equals(dto.getDiscountType())){
									discountFee=dto.getReduceFee();
									break;
								}
							}
						}
						if(discountFee!=null){
							transportFee=transportFee.add(discountFee);							
						}					
					}
				}
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
	 * 校验营销活动是否开启
	 * @创建时间 2014-5-15 下午12:42:49   
	 * @创建人： WangQianJin
	 */
	public static void validateActiveStart(WaybillPanelVo bean){
		//判断营销活动是否为空
		if(bean.getActiveInfo()!=null 
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueName())){
			//是否启用市场营销活动
			boolean isStart=waybillService.isStartCrmActive();
			if(!isStart){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.active.isnotstart"));						
			}
			if(bean.getIsBigGoods()!=null && bean.getIsBigGoods()){
				throw new WaybillValidateException("营销活动不能开精准大票");
			}
		}
	}
	
	/**
	 * 校验优惠券是否开启
	 * @创建时间 2014-06-19
	 * @创建人： WangQianJin
	 */
	public static void validatePromotionsCode(WaybillPanelVo bean){
		//判断优惠券是否为空
		if (StringUtils.isNotEmpty(bean.getPromotionsCode())) {
			//是否启用优惠券
			boolean isStart=waybillService.isStartConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.PROMOTIONS_START,FossConstants.ROOT_ORG_CODE);
			if(!isStart){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.promotions.isnotstart"));						
			}
		}
	}
	
	
	/**
	 * 校验CRM推广活动是否在有效时间范围内
	 * @创建时间 2014-6-12 上午10:31:59   
	 * @创建人： WangQianJin
	 */
	public static void validateCrmActiveInfo(WaybillPanelVo bean){
		if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){
			MarkActivitiesEntity entity=waybillService.queryMarkActivitiesByCode(bean.getActiveInfo().getValueCode());
			if(entity==null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillsubmitaction.exception.validate.activeinfo"));
			}			
			if(entity.getActiveStartTime()!=null && bean.getBillTime().before(entity.getActiveStartTime())){
				//您选择的市场推广活动已失效
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillsubmitaction.exception.validate.failtime"));
			}
			if(entity.getActiveEndTime()!=null && bean.getBillTime().after(entity.getActiveEndTime())){
				//您选择的市场推广活动已失效
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillsubmitaction.exception.validate.failtime"));
			}
		}
		
	}
	
	/**
	 * 根据提货网点CODE设置整车信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-3-6 下午6:51:32
	 */
	public static void setWholeVehicleByCode(String code, WaybillPanelVo bean) {
		//根据CODE获取营业部
		SaleDepartmentEntity saleDepartment = waybillService.querySaleDeptByCode(code);
		BranchVo btanchVo = new BranchVo();
		if(saleDepartment != null){
			btanchVo.setCode(saleDepartment.getCode());
			btanchVo.setName(saleDepartment.getName());			
			//设置提货网点信息
			bean.setCustomerPickupOrgCode(btanchVo);
			//设置提货网点名称
			bean.setCustomerPickupOrgName(btanchVo.getName());
			//设置最终配载部门编号
			bean.setLastLoadOrgCode(btanchVo.getCode());
			//设置最终配载部门名称
			bean.setLastLoadOrgName(btanchVo.getName());
			//设置目的站
			bean.setTargetOrgCode(gainCityNameByCode(btanchVo.getCode()));			
		}
		
	}
	
	/**
	 * 根据部门编码查询所属城市名称
	 * @author WangQianJin
	 * @date 2014-3-6 下午6:51:32
	 */
	public static String gainCityNameByCode(String code){
		OrgAdministrativeInfoEntity orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(code));
		if(orgInfo!=null){
			if(StringUtil.isEmpty(orgInfo.getOrgSimpleName())){
	    		AdministrativeRegionsEntity regions = waybillService.queryAdministrativeRegionsByCode(StringUtil.defaultIfNull(orgInfo.getCityCode()));
	    		if(regions != null){
	    			return regions.getName();
	    		}else{
	    			return null;
	    		}
			}else{
				return orgInfo.getOrgSimpleName();
			}
		}else{
			return null;
		}		
	}
	/**
	 * 启动开始开单时间监听事件
	 * @author WangQianJin
	 * @date 2014-6-14 下午2:58:50
	 * @param bean
	 * @param waybillPanel
	 */
	public static void startKeyPressListener(WaybillPanelVo bean,JPanel waybillPanel){
		//创建键盘监控
		KeyForWaybillPanel keyForWaybillPanel=new KeyForWaybillPanel(bean);
		//获取所有面板数量
		int count = waybillPanel.getComponentCount();
		for (int i = 0; i < count; i++) {
			Object obj = waybillPanel.getComponent(i);
			if (obj instanceof JPanel) {
				JPanel jpanel = (JPanel) obj;
				//获取面板上的所有控件数量
				int count2 = jpanel.getComponentCount();
				for (int j = 0; j < count2; j++) {
					//获取控件
					Object obj2 = jpanel.getComponent(j);	
					/*判断控件类型并进行监控*/
					if (obj2 instanceof JTextField) {
						JTextField text = (JTextField) obj2;
						text.addKeyListener(keyForWaybillPanel);
					}else if(obj2 instanceof JCheckBox){
						JCheckBox cbox = (JCheckBox) obj2;
						cbox.addKeyListener(keyForWaybillPanel);
					}else if(obj2 instanceof JButton){
						JButton btn = (JButton) obj2;
						btn.addKeyListener(keyForWaybillPanel);
					}					
				}				
			}			
		}
	}

	public static void setFavorableQSDiscount(
			List<PriceDiscountDto> discountPrograms, WaybillEditUI ui,
			WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = null;
			WaybillDiscountCanvas discountTableModel = (WaybillDiscountCanvas) ui.canvasContentPanel.getConcessionsPanel().getTblConcessions().getModel();
			data = discountTableModel.getData();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}
		
			for (PriceDiscountDto dto : discountPrograms) {
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
				//vo.setFavorableTypeName(dto.getDiscountTypeName());
				vo.setFavorableTypeName(dto.getTypeName());
				// 优惠折扣类型
				//vo.setFavorableTypeCode(dto.getDiscountType());
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
				if (dto.getReduceFee() != null && dto.getDiscountRate() != null) {
					//优惠金额
					vo.setFavorableAmount(dto.getReduceFee().toString());			
				} else {
					vo.setFavorableAmount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableAmount"));
				}

				data.add(vo);
			}
			ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(data);
		}
		
	}
	/**
	 * 设置提货方式到数据模型
	 * 
	 * @date 2014-08-9
	 */
	public static void modifyPickModel(WaybillEditUI ui,DefaultComboBoxModel pickModel,Boolean falg){
		List<DataDictionaryValueEntity> list = waybillService
				.queryPickUpGoodsHighWays();
		//DMANA-4923  FOSS开单提货方式隐藏“免费送货”
		for (int i = 0; i < list.size(); i++) {
			if(WaybillConstants.DELIVER_FREE.equals(list.get(i).getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(list.get(i).getValueCode())){
				list.remove(list.get(i));
			}
		}
		if(falg){
			for (DataDictionaryValueEntity dataDictionary : list) {
				if(!WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode())
					&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(dataDictionary.getValueCode())){
					DataDictionaryValueVo vo = new DataDictionaryValueVo();
					ValueCopy.valueCopy(dataDictionary, vo);
					pickModel.addElement(vo);
				}
			}
		}else{
			for (DataDictionaryValueEntity dataDictionary : list) {
				//非批量开单 不让开 内部带货送货
				if(!ui.isBatchWaybill() && WaybillConstants.DELIVER_INNER_PICKUP.equals(dataDictionary.getValueCode())){
					continue ;
				}
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				pickModel.addElement(vo);
			}
		}
		
	}
	/**
	 * 设置提货方式默认值
	 * 
	 * @date 2014-8-9
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
	 * 设置产品到数据模型
	 * 
	 * @date 2014-06-30
	 */
	public static void modifyProductTypeModel(DefaultComboBoxModel productTypeModel, String code, Boolean falg) {
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(code,WaybillConstants.TYPE_OF_CARGO, new Date());
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		ProductEntityVo vo = null;
		// 选中精准大票或不选中
		CommonUtils.filterBigGoodsProductEntity(list, falg);
		for (ProductEntity product : list) {
			vo = new ProductEntityVo();
			addProductTypeModel(vo, productTypeModel, product);
		}
	}
	/**
	 * 
	 * 
	 * @date 2014-06-30
	 */
	private static void addProductTypeModel(ProductEntityVo vo,
			DefaultComboBoxModel productTypeModel, ProductEntity product) {
		// 将数据库查询出的产品对象进行转换，转成VO使用的对象
		ValueCopy.entityValueCopy(product, vo);
		vo.setDestNetType(product.getDestNetType());
		productTypeModel.addElement(vo);
	}
	
	/**
	 * 设置运输性质的默认值
	 * 
	 * @date 2014-06-30
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
	
	
	public static void modifyProdctType(DefaultComboBoxModel combProduct,
			String productCode ,WaybillEditUI ui,String receiveOrgCode) {
			boolean falg = false;
			if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(productCode) 
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productCode)){
				falg = true;
			}
			
			//批量开单
			if(ui.isBatchWaybill()){
				UserEntity user = (UserEntity) SessionContext.getCurrentUser();
				OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
				 //通过仓管组 查询对应开单营业部
				String code = waybillService.queryBillingByStore(dept.getCode());
				if(null==code){
					code=dept.getCode();
				}
				combProduct.removeAllElements();
				Common.modifyProductTypeModel(combProduct,code, falg);
			}
			else if (WaybillConstants.WAYBILL_SALE_DEPARTMENT
					.equals(ui.getWaybillType())) {
				//获取当前登陆用户
				UserEntity user = (UserEntity) SessionContext.getCurrentUser();
				//当前登陆用户所在的部门
				OrgAdministrativeInfoEntity dept = user.getEmployee()
						.getDepartment();
				//设置产品到数据模型
				combProduct.removeAllElements();
				Common.modifyProductTypeModel(combProduct, dept.getCode(), falg);
			}else if (WaybillConstants.WAYBILL_FOCUS.equals(ui.getWaybillType())) {
				combProduct.removeAllElements();
				Common.modifyProductTypeModel(combProduct, receiveOrgCode, falg);
				//Common.modifyProductCode(bean,true,combProduct);
				
			}
		
	}
	
	/**
	 * DMANA-4292 开单省市区校验
	 */
	public static void validateCity(WaybillPanelVo bean){
		AddressFieldDto address=bean.getReceiveCustomerAreaDto();
		//判断是否为自提
		//所有的运输性质都必须进行校验 - sangwenhao
		/*
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
		*/
		if(address!=null){
			if(StringUtils.isEmpty(address.getProvinceId()) ||
					StringUtils.isEmpty(address.getCityId()) ||
					StringUtils.isEmpty(address.getCountyId())){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.WaybillValidate.receiverCustomerAddress.provinceNotEmpty"));
			}
			if(StringUtils.isEmpty(bean.getReceiveCustomerAddress())){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.WaybillValidate.receiverCustomerAddress.detailAddressNotEmpty"));
			}
		}else{
			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.WaybillValidate.receiverCustomerAddress.provinceNotEmpty"));
		}
	}
	
	/**
	 * 公共驗證付款方式的方法
	 * @param bean
	 */
	public static void validatePayMethod(WaybillPanelVo bean){
		/**
		 * 获取是否在客户圈标识( 从bean、中获取)
		 */
		if(		StringUtil.isNotBlank(bean.getIsCustCircle()) && StringUtil.equals("Y", bean.getIsCustCircle()) && 
				bean.getCustomerCircleEntity() != null && bean.getCusBargainNewEntity() != null  &&
				bean.getCustomerNewEntity() !=null
				){
			//如果主客户当前有效合同为统一结算，则开单选择结款方式只能为月结和到付；
			//获取 是否统一结算字段 (校验 用合同实体中的是否统一结算字段)
			//客户圈的付款方式校验
			ostPaymentValidate(bean);
		}else{
		/**
		 * 当发货人【是否统一结算】标记为【是】,该客户付款方式只能为【临时欠款】或【月结】或 【到付】
		 */
			paymentValidate(bean);
		}
	}

	private static void paymentValidate(WaybillPanelVo bean) {
		if(bean.getPaidMethod() != null) {
			if (WaybillConstants.IS_NOT_NULL_FOR_AI.equals(bean.getStartCentralizedSettlement())) {
				Boolean isOk = false;
				if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
					isOk= true; 
				}
				if(WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					isOk = true;
				}
				if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					CusBargainVo vo=new CusBargainVo();
					vo.setChargeType(WaybillConstants.MONTH_END);
					vo.setCustomerCode(bean.getDeliveryCustomerCode());
					vo.setBillDate(new Date());
					vo.setBillOrgCode(bean.getReceiveOrgCode());
					isOk =  WaybillServiceFactory.getWaybillService().isCanPaidMethod(vo);
					//DP-FOSS zhaoyiqing 343617 2016-10-18
					//验证月结客户合同部门和催款部门方法
					WaybillServiceFactory.getWaybillService().isCanPaidMethodForUCBC(vo);

				}
				if(!isOk){
					throw new WaybillValidateException(i18n
							.get("foss.gui.chaning.waybillRFCUI.common.alterPaidMethod"));
				}
			 }
		}
	}

	private static void ostPaymentValidate(WaybillPanelVo bean) {
		//设置主客户编码
		bean.setDeliveryCustomerCode(bean.getCustomerCircleEntity().getCustCode());
		//客户圈客户为统一结算只能选择到付和月结
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
					isOk =  WaybillServiceFactory.getWaybillService().isCanPaidMethod(vo);
					//DP-FOSS zhaoyiqing 343617 20161025 配合CUBC结算中心改造，校验合同部门和催款部门编码
//					WaybillServiceFactory.getWaybillService().isCanPaidMethodForUCBC(vo);
				}
				if(!isOk && WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
					bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
					throw new WaybillValidateException("该客户没有月结合同或合同适用部门不匹配，付款方式不能为月结");
					//如果合同绑定部门是当前部门只能为月结和到付
				}else if(!(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode()) || 
						WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode()))){
					bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
					throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillBillder.common.contract"));
				}
				//是否統一結算為是合同不是月結
			}else if(!WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
				bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
				throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillBillder.common.arrive"));
			}
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
				vo.setBillDate(new Date());
				vo.setBillOrgCode(bean.getReceiveOrgCode());
				isOk =  WaybillServiceFactory.getWaybillService().isCanPaidMethod(vo);
			}
			if(!isOk){
				bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
				throw new WaybillValidateException("付款方式不能为月结");
			}
		}
	}
	/**
	 * 对保险声明价值进行验证
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-14下午18:22
	 */
	public static void vfia(WaybillPanelVo bean){
		BigDecimal insuranceAmount=bean.getInsuranceAmount();
		BigDecimal vipInsuranceAmount=bean.getVipInsuranceAmount();
		if(vipInsuranceAmount==null||vipInsuranceAmount.compareTo(BigDecimal.ZERO)==0)
		{
			vipInsuranceAmount=new BigDecimal(NUM_20000);
		}
		if((insuranceAmount.compareTo(vipInsuranceAmount))>0){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.insuranceUp"));
		}
	}
	
	/**
	 * 对代收货款进行验证
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-15下午15:38
	 */
	public static void cpv(WaybillPanelVo bean){
		BigDecimal vipCodAmountAmount=bean.getVipCollectionPaymentLimit();
		BigDecimal codAmount=bean.getCodAmount();
		if(vipCodAmountAmount==null||vipCodAmountAmount.compareTo(BigDecimal.ZERO)==0)
		{
			vipCodAmountAmount=new BigDecimal(NUM_20000);
		}
		if((vipCodAmountAmount!=null)&&((codAmount.compareTo(vipCodAmountAmount))>0)){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.exception.collectionmoneyUp"));
		}
	}
	
	/**
	 * 清除运单VO中的包装信息并将打包装弹窗中的数值清空
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-17上午：11:34
	 */
	public static void unsetWaybillPanelVoForPackingPack(WaybillPanelVo bean,WaybillEditUI waybillEditUI){
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
		EnterPackingInfoDialog dialog=waybillEditUI.getPackDialog();
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
//		dialog.getOtherTotlePriceTextField().setText("");
		dialog.getAllTotlePriceTextField().setText("");
		dialog.getBufferLabel().setText("");
		dialog.getDiscountLabel().setText("");
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

	public static void validateBusinessZoneResidentialDistrict(WaybillEditUI ui,WaybillPanelVo bean){
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){/*
			BranchVo vo = bean.getCustomerPickupOrgCode();
			if(vo != null){
				OrgAdministrativeInfoEntity oaif = waybillService.queryByCode(vo.getCode());
				DataDictionaryValueVo dvo = bean.getReceiveMethod();
				if(oaif != null){
					if(i18n.get("foss.gui.creating.showPickupStationDialogAction.hg.name").equals(oaif.getCityName()) && 
							i18n.get("foss.gui.creating.showPickupStationDialogAction.hg.code").equals(oaif.getCityCode()) && 
							WaybillConstants.DELIVER_UP.equals(dvo.getValueCode())){
						Boolean businessZone = bean.getBusinessZone();
						Boolean residentialDistrict = bean.getResidentialDistrict();
						if(!businessZone && !residentialDistrict){
							throw new WaybillValidateException("提货网点为香港地区并且为送货的必须勾选是商业区或者是住宅区");
						}
					}
				}
			}
			
		*/}
	}
	
	
	//liding comment for NCI
	/**
	 * 校验交易流水号是否符合开单规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23上午11:09
	 */
//	public static void validateTransactionSerialNumber(WaybillPanelVo bean,WaybillEditUI ui){
//		String valueCode=bean.getPaidMethod().getValueCode();
//		String transactionSerialNumber=bean.getTransactionSerialNumber();
//		if(valueCode.equals(WaybillConstants.CREDIT_CARD_PAYMENT)){
//			if(StringUtils.isEmpty(transactionSerialNumber)){
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.transactionSerialNumber"));
//			}
//		}
//	}
	
	
	//liding comment for NCI

	/**
	 * 该方法验证若是银行卡付款，则交易流水号是否可编辑
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23上午08:13
	 */
//	public static void whetherBankCardPayment(WaybillPanelVo bean,WaybillEditUI ui) {
//		// TODO Auto-generated method stub
//		if(bean.getPaidMethod()!=null&&WaybillConstants.CREDIT_CARD_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(true);
//		}else{
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(false);
//			bean.setTransactionSerialNumber(null);
//		}
//	}
	
	/**
	 * Dmana-9885巨商汇或者阿里巴巴的订单，根据重量判断是否符合规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-03下午16:28
	 */
	public static boolean specialChannelFreightWeight(WaybillPanelVo bean){
		BigDecimal minPercent=new BigDecimal(ZEROPOINTNINESIX).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal maxPercent=new BigDecimal(ONEPOINTZEROFOUR).setScale(2, BigDecimal.ROUND_HALF_UP);
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
	public static boolean specialChannelFreightVolume(WaybillPanelVo bean){
		BigDecimal minPercent=new BigDecimal(ZEROPOINTNINE).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal maxPercent=new BigDecimal(ONEPOINTONE).setScale(2, BigDecimal.ROUND_HALF_UP);
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
	public static void specialChannelFreight(WaybillPanelVo bean){
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
	
	//设置发票标记
	public static void setInvoiceInfo(String customerCode,WaybillPendingEntity vo,String type){
		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 判断编码是否为空
		if (StringUtils.isNotEmpty(customerCode)) {
		CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
		dto.setCustCode(customerCode);
		dto.setExactQuery(true);
		dtoList.add(dto);
		}
		CustomerQueryConditionDto customerQueryConditionDtoDev = null;
		List<CustomerQueryConditionDto> customerQueryConditionDtoDevList =  waybillService.queryCustomerByConditionList(dtoList);//customerService.queryCustomerByCondition(customerQueryConditionDtoReceiver);
		if (CollectionUtils.isNotEmpty(customerQueryConditionDtoDevList)) {
		customerQueryConditionDtoDev = customerQueryConditionDtoDevList.get(0);
		if(StringUtils.isNotEmpty(customerCode)){
		CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
		contact.setCustCode(customerCode);
		CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
		String invoice = null;
		invoice=CommonUtils.setInvoice(customerCode);
		if(WaybillConstants.INVOICE_01.equals(invoice)){
		customerQueryConditionDtoDev.setInvoiceType(WaybillConstants.INVOICE_01);
		    }else{
		     customerQueryConditionDtoDev.setInvoiceType(WaybillConstants.INVOICE_02);
		}
		if("DELIVER".equals(type)){
		CommonUtils.setPendingExpDevliveryCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, vo); 
		}else{
		CommonUtils.setPendingExpReciveCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, vo);
		} 
		}
		}
		}
	/**
	 * @需求：大件上楼优化需求
	 * @功能：判断是否有打木架信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02 下午15:50
	 */
	public static boolean whetherMakeWoodYoke(WaybillPanelVo bean){
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
	 * @需求：大件上楼优化需求
	 * @功能：判断大件上楼是否满足开单规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02 下午15:50
	 */
	public static void judgeBeanForLDU(WaybillPanelVo bean){
		if(bean.getReceiveMethod()==null){
			//提货方式不能为空
			throw new WaybillValidateException("提货方式不能为空");
		}else{
			String valueCode=bean.getReceiveMethod().getValueCode();
			if(WaybillConstants.LARGE_DELIVER_UP.equals(valueCode)
				||WaybillConstants.LARGE_DELIVER_UP_AIR.equals(valueCode)){
				BigDecimal goodsWeightTotal=bean.getGoodsWeightTotal();
				Integer goodsQtyTotal=bean.getGoodsQtyTotal();
				if(goodsWeightTotal==null
						||goodsWeightTotal.compareTo(BigDecimal.ZERO)<=0){
					//货物总重量为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总重量不可为空或者小于等于0");
				}else if(goodsQtyTotal==null
						||goodsQtyTotal<=0){
					//货物总件数为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总件数不可为空或者小于等于0");
				}else{
					BigDecimal qty=new BigDecimal(goodsQtyTotal);
					BigDecimal pertotle=(goodsWeightTotal.divide(qty,2,BigDecimal.ROUND_HALF_DOWN));
					if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_50).setScale(0))<0){
						//单件货物重量低于50公斤不可开大件上楼
						throw new WaybillValidateException("单票货物重量小于50公斤不可开大件上楼");
					}else{
						if(!whetherMakeWoodYoke(bean)){
							//没有打木架时，单件货物重量超过130公斤不可开大件上楼
							if(pertotle.compareTo(new BigDecimal(NumberConstants.NUMBER_130).setScale(0))>0){
								throw new WaybillValidateException("单件货物重量大于130公斤不可开大件上楼");
							}
						}else{
							//您选择了打木架，单件货物重量超过170公斤不可开大件上楼
							if(pertotle.compareTo(new BigDecimal(NUM_170).setScale(0))>0){
								throw new WaybillValidateException("有打木架情况下，单件货物重量大于170公斤不可开大件上楼");
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * @需求：大件上楼优化需求
	 * @功能：判断是否满足送货上楼开单
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02上午11:28
	 */
	public static void judgeBeanForDU(WaybillPanelVo bean){
		if(bean.getReceiveMethod()==null){
			//提货方式不能为空
			throw new WaybillValidateException("提货方式不能为空");
		}else{
			String valueCode=bean.getReceiveMethod().getValueCode();
			if(WaybillConstants.DELIVER_UP.equals(valueCode)
				||WaybillConstants.DELIVER_UP_AIR.equals(valueCode)){
				BigDecimal goodsWeightTotal=bean.getGoodsWeightTotal();
				Integer goodsQtyTotal=bean.getGoodsQtyTotal();
				if(goodsWeightTotal==null
						||goodsWeightTotal.compareTo(BigDecimal.ZERO)<=0){
					//货物总重量为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总重量不可为空或者小于等于0");
				}else if(goodsQtyTotal==null
						||goodsQtyTotal<=0){
					//货物总件数为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总件数不可为空或者小于等于0");
				}else{
					BigDecimal qty=new BigDecimal(goodsQtyTotal);
					BigDecimal pertotle=(goodsWeightTotal.divide(qty,2,BigDecimal.ROUND_HALF_DOWN));
					if(!whetherMakeWoodYoke(bean)){
						//没有打木架时，单件货物重量超过50公斤不可开送货上楼
						BigDecimal judgeStandard=new BigDecimal(NumberConstants.NUMBER_50).setScale(0);
						if(pertotle.compareTo(judgeStandard)>0){
							throw new WaybillValidateException("单件货物重量超过50公斤不可开送货上楼");
						}
					}else{
						//您选择了打木架，只有在单件重量超过0kg，小于等于65kg时，允许开单送货上楼
						BigDecimal judgeStandard=new BigDecimal(NUM_65).setScale(0);
						if(pertotle.compareTo(judgeStandard)>0){
							throw new WaybillValidateException("有打木架情况下，单件货物重量超过65公斤不可开送货上楼");
						}
					}
				}
			}
		}
	}

	/**
	 * 根据特殊增值服务改变提货方式 mabinliang-foss-254615
	 * @param bean
	 * @param ui
	 */
	public static void changePickUpModeForSpecial(WaybillPanelVo bean,
			WaybillEditUI ui) {
		DataDictionaryValueVo productVo = bean.getSpecialValueAddedServiceType();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}		
		Common.specialReceiveMethod(waybillService, bean, ui);	
	}

	//根据特殊增值服务改变提货方式
	private static void specialReceiveMethod(IWaybillService waybillService2,
			WaybillPanelVo bean, WaybillEditUI ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();
		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.querySpecialPickUp();
	
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			pikcModeModel.addElement(vo);
		}
		
		//校验提货
		receiveMethodCheck(bean,ui);
	
	}

	/**
	 * 判断是否黑名单客户 延期到8月27号上线
	 * @author foss-218438
	 */
	public static void isBlackList(String deliveryCustomerCode,String receiveCustomerCode,WaybillEditUI ui){
		//只考虑营业部开单的情况
		if(WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(ui.getWaybillType())){
			CustomerEntity deliveryCustomerEntity = new CustomerEntity();
			CustomerEntity receiveCustomerEntity = new CustomerEntity();
			if(StringUtil.isNotBlank(deliveryCustomerCode)){
				deliveryCustomerEntity.setCusCode(deliveryCustomerCode);
				deliveryCustomerEntity = waybillService.queryCustInfoByCustomerEntity(deliveryCustomerEntity);
			}else{
				deliveryCustomerEntity = null;
			}
			if(StringUtil.isNotBlank(receiveCustomerCode)){
				receiveCustomerEntity.setCusCode(receiveCustomerCode);
				receiveCustomerEntity = waybillService.queryCustInfoByCustomerEntity(receiveCustomerEntity);
			}else{
				receiveCustomerEntity = null;
			}
			DataDictionaryValueEntity deliveryDataValueEntity = null;
			DataDictionaryValueEntity receiveDataValueEntity = null;
			//发货客户信息和收货客户信息都不存在时
			if(deliveryCustomerEntity == null && receiveCustomerEntity == null){
				//将提示面板设为不可见
				ui.numberPanel.getLblBlackListLabel().setVisible(false);
			}
			//发货客户信息存在和收货客户信息不存在时
			else if(deliveryCustomerEntity!=null && receiveCustomerEntity == null){
				if(StringUtils.isNotEmpty(deliveryCustomerEntity.getBlackListCategory())){
					deliveryDataValueEntity = waybillService.queryDataDictoryValueByCode(DictionaryConstants.CUST_BLACKLIST_CATEGORY,deliveryCustomerEntity.getBlackListCategory());
					if(deliveryDataValueEntity != null){
						ui.numberPanel.getLblBlackListLabel().setVisible(true);
						ui.numberPanel.getLblBlackListLabel().setText("有"+deliveryDataValueEntity.getValueName()+"记录,请仔细验货");
					}else{
						ui.numberPanel.getLblBlackListLabel().setVisible(true);
						ui.numberPanel.getLblBlackListLabel().setText("有黑名单记录，请仔细验货");
					}
				}else{
					ui.numberPanel.getLblBlackListLabel().setVisible(false);
				}
			}
			//发货客户信息不存在和收货客户信息存在时
			else if(receiveCustomerEntity!=null && deliveryCustomerEntity == null){
				if(StringUtils.isNotEmpty(receiveCustomerEntity.getBlackListCategory())){
					receiveDataValueEntity = waybillService.queryDataDictoryValueByCode(DictionaryConstants.CUST_BLACKLIST_CATEGORY,receiveCustomerEntity.getBlackListCategory());
					if(receiveDataValueEntity != null){
						ui.numberPanel.getLblBlackListLabel().setVisible(true);
						ui.numberPanel.getLblBlackListLabel().setText("有"+receiveDataValueEntity.getValueName()+"记录,请仔细验货");
					}else{
						ui.numberPanel.getLblBlackListLabel().setVisible(true);
						ui.numberPanel.getLblBlackListLabel().setText("有黑名单记录，请仔细验货");
					}
				}else{
					ui.numberPanel.getLblBlackListLabel().setVisible(false);
				}
			}
			//发货客户信息和收货客户信息都存在时
			else {
				
				if(StringUtils.isEmpty(deliveryCustomerEntity.getBlackListCategory()) 
						&& StringUtils.isEmpty(receiveCustomerEntity.getBlackListCategory()) ){
					ui.numberPanel.getLblBlackListLabel().setVisible(false);
				}else{
					deliveryDataValueEntity = waybillService.queryDataDictoryValueByCode(DictionaryConstants.CUST_BLACKLIST_CATEGORY,deliveryCustomerEntity.getBlackListCategory());
					receiveDataValueEntity = waybillService.queryDataDictoryValueByCode(DictionaryConstants.CUST_BLACKLIST_CATEGORY,receiveCustomerEntity.getBlackListCategory());
					if(deliveryDataValueEntity == null && receiveDataValueEntity == null){
						ui.numberPanel.getLblBlackListLabel().setVisible(true);
						ui.numberPanel.getLblBlackListLabel().setText("有黑名单记录，请仔细验货");
					}else{
						if(deliveryDataValueEntity != null){
							ui.numberPanel.getLblBlackListLabel().setVisible(true);
							ui.numberPanel.getLblBlackListLabel().setText("有"+deliveryDataValueEntity.getValueName()+"记录,请仔细验货");
						}
						if(receiveDataValueEntity != null){
							ui.numberPanel.getLblBlackListLabel().setVisible(true);
							ui.numberPanel.getLblBlackListLabel().setText("有"+receiveDataValueEntity.getValueName()+"记录,请仔细验货");
						}
					}
				}
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
	public static void fillFlabelleavemonthData(WaybillPanelVo bean,WaybillEditUI ui, String flabelleavemonth) {
		/**
		 * 营业部开单如果客户分群是 VIP或者全网活跃群 是上门接货，勾选免费接货 ，设置免费接货为可以勾选状态（可以取消）
		 * 非上门接货，取消免费客户勾选，并且置灰
		 */
		ProductEntityVo vo = bean.getProductCode();
		if ("VIP".equals(flabelleavemonth)
				|| "OMNI-ACTIVE".equals(flabelleavemonth)) {
			// 营业部开单并且勾选上门接货
			if (StringUtils.isNotBlank(ui.getWaybillType())
					&& WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(ui
							.getWaybillType())
					&& bean.getPickupToDoor())
			    { 
				if(vo==null||!ProductEntityConstants.PRICING_PRODUCT_PCP.equals(vo.getCode())){
					  //如果是免费接送货，设置UI上默认勾选
					  if(bean.getFreePickupGoods()){
						ui.basicPanel.getCboFreePickupGoods().setSelected(true);
					}
					// 如果满足客户分群以及勾选了上门接货，免费接货可选
					ui.basicPanel.getCboFreePickupGoods().setEnabled(true);
				}
			} else if (StringUtils.isNotBlank(ui.getWaybillType())
					&& WaybillConstants.WAYBILL_FOCUS.equals(ui
							.getWaybillType())
					|| StringUtils.isNotBlank(ui.getWaybillType())
					&& WaybillConstants.WAYBILL_PICTURE.equals(ui
							.getWaybillType())) {
				if(vo==null||!ProductEntityConstants.PRICING_PRODUCT_PCP.equals(vo.getCode())){
					// 免费接货(可以勾选)
					ui.basicPanel.getCboFreePickupGoods().setEnabled(true);
				}

			} else {
				// 取消免费接货勾选
				ui.basicPanel.getCboFreePickupGoods().setSelected(false);
				// 免费接货置灰
				ui.basicPanel.getCboFreePickupGoods().setEnabled(false);
			}

		} else {// 非 VIP或者全网活跃群
				// 取消免费接货勾选
			ui.basicPanel.getCboFreePickupGoods().setSelected(false);
			// 免费接货置灰
			ui.basicPanel.getCboFreePickupGoods().setEnabled(false);
		}
		JComboBox  box = ui.consignerPanel.getCombFlabelleavemonth();
		ComboBoxModel model = box.getModel();
		int size = model.getSize();
		for (int i = 0; i < size; i++) {
			if (((DataDictionaryValueVo)(model.getElementAt(i))).getValueCode().equals(flabelleavemonth)) {
				box.setSelectedIndex(i);
				bean.setFlabelleavemonth(((DataDictionaryValueVo)(model.getElementAt(i))));
				break;
			}
		}
	}
	/**
	 * 免费接货
	 * @param bean
	 * @param ui
	 */
	public static void changeFreePickUpGoods(WaybillPanelVo bean,WaybillEditUI ui){
		ProductEntityVo productVo = bean.getProductCode();
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
			if ((WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(ui.getWaybillType())
					&& bean.getPickupToDoor())
					|| (WaybillConstants.WAYBILL_FOCUS.equals(ui.getWaybillType())
						 	||WaybillConstants.WAYBILL_PICTURE.equals(ui.getWaybillType()))) {
				setFreePickupGoods(ui,productVo);
			}
		}
	}
	
	/**
	 *  
	 * @param ui
	 * @param productVo
	 */
	private static void setFreePickupGoods(WaybillEditUI ui,ProductEntityVo productVo){
		if(ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productVo.getCode())){
			// 取消免费接货勾选
			ui.basicPanel.getCboFreePickupGoods().setSelected(false);
			// 免费接货置灰
			ui.basicPanel.getCboFreePickupGoods().setEnabled(false);
		}else{					
			// 如果满足客户分群以及勾选了上门接货，免费接货可选
			ui.basicPanel.getCboFreePickupGoods().setEnabled(true);
		}
	}
	


	
	/**
	 * 根据货物名称，填充行业货源品类
	 * 
	 * @author zhangchengfu
	 * @date   2015-6-3	
	 * @param bean
	 */
	public static void fillIndustrySourceCategoryData(WaybillPanelVo bean,WaybillEditUI ui) {
		String goodsName = bean.getGoodsName();
		String industrySourceCategory = waybillRemotingService.queryIndustrySourceCategory(goodsName);
		JComboBox  box = null;
		if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())) {
			box = ui.pictureCargoInfoPanel.getTxtIndustrySourceCategory();
		} else {
			box = ui.cargoInfoPanel.getTxtIndustrySourceCategory();
		}
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
	 * dp-foss-dongjialing
	 * 225131
	 * 有事后折阶梯折扣合同不允许开装卸费
	 */
	private static boolean iSLtDiscountBackInfo(WaybillPanelVo bean) {
		List<CusLtDiscountItemDto> list = waybillService.iSLtDiscountBackInfo(bean.getDeliveryCustomerCode(), bean.getBillTime());
		if(null ==list || list.size()==0) {
			return true;
		}
		return false;
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
	 * 验证工号对应的信息和客户信息是否一致；--lianhe--2016年12月19日18:12:45
	 * 判断标准：
	 * 1.当付款方式为现金、银行卡、网上支付、月结时，校验发货客户联系方式、发货联系人是否与OA相同，
	 *   不同则提醒“发货联系人姓名与工号所属员工姓名不符，请核实。”；
	 * 2.当付款方式为到付时，校验发货客户联系方式、收货联系人是否与OA相同，
	 *   不同则提醒“收货联系人姓名与工号所属员工姓名不符，请核实。”
	 * @param bean
	 */
	public static void validatePayEmployeeInfo(WaybillPanelVo bean) {
		//根据工号查询人员信息
		EmployeeEntity employeeEntity = customerService.queryEmployeeByEmpCode(bean.getEmployeeNo());
		//获取页面选择的付款方式
		String paidMethodCode = bean.getPaidMethod().getValueCode();
		//判断工号是否能查出员工信息
		if (employeeEntity == null) {
			//提示错误
			throw new WaybillValidateException(i18n.get("foss.gui.creating.common.msgBox.wrongEmployeeCode"));
		}
		//判断付款方式
		if (StringUtils.equals(paidMethodCode, WaybillConstants.CASH_PAYMENT) ||
				StringUtils.equals(paidMethodCode, WaybillConstants.CREDIT_CARD_PAYMENT) ||
				StringUtils.equals(paidMethodCode, WaybillConstants.ONLINE_PAYMENT) ||
				StringUtils.equals(paidMethodCode, WaybillConstants.MONTH_PAYMENT)
				) {
			//判断发货姓名或者手机号是否与OA不同,不同则提醒“发货联系人姓名与工号所属员工姓名不符，请核实。”；
			if (!StringUtils.equals(employeeEntity.getEmpName(), bean.getDeliveryCustomerName())
					|| !StringUtils.equals(employeeEntity.getMobilePhone(), bean.getDeliveryCustomerMobilephone())) {
				//提醒
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.msgBox.wrongDeliveryInfo"));
			}
			
		} else if (StringUtils.equals(paidMethodCode, WaybillConstants.ARRIVE_PAYMENT)) {
			//收货姓名或者手机号是否与OA不同,不同则提醒“收货联系人姓名与工号所属员工姓名不符，请核实。”
			if (!StringUtils.equals(employeeEntity.getEmpName(), bean.getReceiveCustomerName())
					|| !StringUtils.equals(employeeEntity.getMobilePhone(), bean.getReceiveCustomerMobilephone())) {
				//提醒
				throw new WaybillValidateException(i18n.get("foss.gui.creating.common.msgBox.wrongReceiveInfo"));
			}
		}
	}

	/**
	 * lianhe--置空非木包装费--2017年2月17日15:25:24
	 * @param bean
	 * @param ui
	 */
	public static void setNonWoodPackingAmountNull(WaybillPanelVo bean,
			WaybillEditUI ui) {
		bean.setNonWoodPackingAmount(null);//设置木托费重置标志
		if(ui.getDialog() != null){
			// 打木架货物件数
			ui.getDialog().getNonWoodPackingAmount().setText("");
		}
	}

}
