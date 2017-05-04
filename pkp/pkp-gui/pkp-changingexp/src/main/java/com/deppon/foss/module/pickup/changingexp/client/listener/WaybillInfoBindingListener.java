package com.deppon.foss.module.pickup.changingexp.client.listener;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.core.binding.BindingEvent;
import com.deppon.foss.framework.client.core.binding.IBindingListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.mainframe.client.utills.ExceptionHandler;
import com.deppon.foss.module.pickup.changingexp.client.action.ShowPickupStationDialogAction;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.ChangeGoodsQtyDialog;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.EnterYokeInfoChangeDialog;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.ReturnInfoPanel;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.TransferInfoPanel;
import com.deppon.foss.module.pickup.changingexp.client.utils.Common;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressFieldForExp;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.JComboCheckBox;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.QueryPickupStationDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.BankAccountDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.QueryConsigneeDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.QueryConsignerDialog;
import com.deppon.foss.module.pickup.common.client.ui.dialog.FlightInfoDialog;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.utils.StrUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;

import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.GisConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillGisPickupOrgException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单信息联动
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-29 上午9:06:00
 */
public class WaybillInfoBindingListener implements IBindingListener {
	

	// 日志Logger
	private static final Logger LOG = Logger.getLogger(WaybillInfoBindingListener.class);

	// 更改单UI
	private WaybillRFCUI ui;

	// 更改单Service
	private IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();
	
	//工具类获取branchVo对象
	private BusinessUtils bu = new BusinessUtils();
	//0
	private static final String ZERO = "0";
	
	//waybill service
	private IWaybillService waybillServicecreate = WaybillServiceFactory.getWaybillService();
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillInfoBindingListener.class); 
	

	
	private IWaybillRfcService rfcService = WaybillRfcServiceFactory
			.getWaybillRfcService();
	
	/**
	 * 构造方法
	 * @param waybillRFCUI
	 */
	public WaybillInfoBindingListener(WaybillRFCUI waybillRFCUI) {
		this.ui = waybillRFCUI;
	}

	/**
	 * 
	 * 设置保存按钮与提交按钮不可编辑
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:24:45
	 */
	public void setSaveAndSubmitFalse(WaybillRFCUI ui)
	{
		//提交按钮不可用
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);
		//计算总运费按钮可用
		ui.getWaybillInfoPanel().getBillingPayPanel().getBtnCalculate().setEnabled(true);
	}
	
	/**
	 * 
	 * 设置保存按钮与提交按钮可编辑
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:24:45
	 */
	public void setSaveAndSubmitTrue(WaybillRFCUI ui)
	{
		//允许提交
		ui.getButtonPanel().getBtnSubmit().setEnabled(true);
	}

	/**
	 * 
	 * 绑定VO属性值发生变化后回调该方法
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午11:51:54
	 * @see com.deppon.foss.framework.client.core.binding.IBindingListener#bindingTriggered(java.util.List)
	 */
	public void bindingTriggered(List<BindingEvent> be) {
		
    		// 绑定VO
    		WaybillInfoVo bean = ui.getBinderWaybill();
    		for (BindingEvent bindingEvent : be) {
    			String propertyName = bindingEvent.getPropertyName();//时间所改变的bean属性的名字
    			
    			if(LOG.isDebugEnabled()){//记录日志
    				LOG.debug("property" + propertyName + " binding triggered with focus lost");
    			}
    			try {
	    			
	    			/**导入面板*/
	    			if ("rfcSource".equals(propertyName)) {
	    				// 变更来源
	    				rfcSourceListener(bean);
	    			} else if ("rfcType".equals(propertyName)) {
	    				// 变更类型
	    				rfcTypeListener(bean);
	    			/**转运信息*/
	    			} else if ("tfrCustomerPickupOrgName".equals(propertyName)) {
	    				// 转运目的站
	    				transferCustomerPickupOrgNameListener(bean);
	    			} else if ("tfrProductCode".equals(propertyName)) {
	    				// 转运运输性质
	    				transferProductCodeListener(bean);
	    			} else if ("tfrReceiveMethod".equals(propertyName)) {
	    				// 转运提货方式
	    				transferReceiveMethodListener(bean);
	    			} else if ("tfrFlightNumberType".equals(propertyName)) {
	    				// 转运航班
	    				transferflightNumberTypeListener(bean);
	    			/**返货信息*/
	    			} else if ("rtnCustomerPickupOrgName".equals(propertyName)) {
	    				// 返货目的站
	    				returnCustomerPickupOrgNameListener(bean);
	    			} else if ("rtnProductCode".equals(propertyName)) {
	    				// 返货运输性质
	    				returnProductCodeListener(bean);
	    			} else if ("rtnReceiveMethod".equals(propertyName)) {
	    				// 返货提货方式
	    				returnReceiveMethodListener(bean);
	    				
	    			/** 货物信息 */
	    			} else if ("goodsName".equals(bindingEvent.getPropertyName())){
	    				// 货物名称
	    				gooodsNameListener(bean);
	    			} else if ("deliveryCustomerMobilephone".equals(bindingEvent.getPropertyName())){
	    				// 发货人手机号码
	    				deliveryCustomerMobilephoneListener(bean);
	    			} else if ("deliveryCustomerPhone".equals(bindingEvent.getPropertyName())){
	    				// 发货人电话
	    				deliveryCustomerPhoneListener(bean);
	    			} else if("deliveryCustomerName".equals(bindingEvent.getPropertyName())){ 
						//发货客户名称
						deliveryCustomerNameListener(bean);
					} else if("deliveryCustomerContact".equals(bindingEvent.getPropertyName())){
						//发货联系人
						deliveryCustomerContactListener(bean);
					} else if ("receiveCustomerMobilephone".equals(bindingEvent.getPropertyName())){
	    				// 收货人手机号码
	    				receiveCustomerMobilephoneListener(bean);
	    			} else if ("receiveCustomerPhone".equals(bindingEvent.getPropertyName())){
	    				// 收货人电话
	    				receiveCustomerPhoneListener(bean);
	    			} else if ("receiveMethod".equals(bindingEvent.getPropertyName())){
	    				// 提货方式
	    				receiveMethodListener(bean);
	    			} else if("receiveCustomerName".equals(bindingEvent.getPropertyName())){
	    				//收货客户名称
						receiveCustomerNameListener(bean);
					} else if("receiveCustomerArea".equals(bindingEvent.getPropertyName())){
						//收货区域
						receiveCustomerAreaListener(bean);
					} else if("receiveCustomerContact".equals(bindingEvent.getPropertyName())){
						//收货联系人
						receiveCustomerContactListener(bean);
					} else if ("productCode".equals(bindingEvent.getPropertyName())){
	    				// 运输性质
	    				productCodeListener(bean);
	    			} else if ("goodsWeightTotal".equals(bindingEvent.getPropertyName())){
	    				// 重量
	    				goodsWeightTotalListener(bean);
	    			} else if ("goodsVolumeTotal".equals(bindingEvent.getPropertyName())){
	    				// 体积
	    				goodsVolumeTotalListener(bean);
	    			} else if ("goodsQtyTotal".equals(bindingEvent.getPropertyName())){
	    				// 件数
	    				goodsQtyTotalListener(bean, bindingEvent.getOldValue(), bindingEvent.getNewValue());
	    			} else if ("insuranceAmount".equals(bindingEvent.getPropertyName())){
	    				// 保险价值
	    				insuranceAmountListener(bean);
	    			} else if ("goodsSize".equals(bindingEvent.getPropertyName())){
	    				// 尺寸
	    				goodsSizeListener(bean);
	    			} else if ("paper".equals(bindingEvent.getPropertyName())){
	    				// 包装-纸
	    				paperListener(bean);
	    			} else if ("wood".equals(bindingEvent.getPropertyName())){
	    				// 包装-木
	    				woodListener(bean);
	    			} else if ("fibre".equals(bindingEvent.getPropertyName())) {
	    				// 包装-纤
						fibreListener(bean);
					} else if ("salver".equals(bindingEvent.getPropertyName())) {
						// 包装-托
						salverListener(bean);
					} else if ("membrane".equals(bindingEvent.getPropertyName())) {
						// 包装-膜
						membraneListener(bean);
	    			} else if ("otherPackage".equals(bindingEvent.getPropertyName())) {
						// 包装-其他
	    				otherPackageListener(bean);
	    			} else if ("refundType".equals(bindingEvent.getPropertyName())){
	    				// 退款类型
	    				refundTypeListener(bean);
	    			} else if ("returnBillType".equals(bindingEvent.getPropertyName())){
	    				// 返单类型
	    				returnBillTypeListener(bean);
	    			} else if ("paidMethod".equals(bindingEvent.getPropertyName())){
	    				// 开单付款方式
	    				paidMethodListener(bean);
	    			} else if ("isWholeVehicle".equals(bindingEvent.getPropertyName())){
	    				// 是否整车
	    				isWholeVehicleListener(bean);
	    			} else if ("isPassDept".equals(bindingEvent.getPropertyName())){
	    				// 是否经过营业部
	    				isPassDeptListener(bean);
	    			} else if ("pickupFee".equals(bindingEvent.getPropertyName())){
	    				// 接货费
	    				pickupFeeListener(bean);
	    			} else if ("packageFee".equals(bindingEvent.getPropertyName())){
	    				// 包装费
	    				packageFeeListener(bean);
	    			} else if ("codAmount".equals(bindingEvent.getPropertyName())){
	    				// 代收货款
	    				codAmountListener(bean);
	    			} else if ("serviceFee".equals(bindingEvent.getPropertyName())){
	    				// 装卸费
	    				serviceFeeListener(bean);
	    			} else if ("deliveryGoodsFee".equals(bindingEvent.getPropertyName())){
	    				// 送货费
	    				deliveryGoodsFeeListener(bean);
	    			} else if ("carDirectDelivery".equals(bindingEvent.getPropertyName())) {
	    				// 大车直送
						carDirectDeliveryListener(bean);
					} else if ("preciousGoods".equals(bindingEvent.getPropertyName())){
	    				// 贵重物品
	    				preciousGoodsListener(bean);
	    			} else if ("flightNumberType".equals(bindingEvent.getPropertyName())){
	    				// 航班类型
	    				flightNumberTypeListener(bean);
	    			} else if ("freightMethod".equals(bindingEvent.getPropertyName())){
	    				// 合票类型
	    				freightMethodListener(bean);
	    			}else if("tfrFreightMethod".equals(bindingEvent.getPropertyName())){
	    				
	    				// 转货合票类型
	    				tfrFreightMethodListener(bean);
	    				
	    			}else if("receiveCustomerAddress".equalsIgnoreCase(bindingEvent.getPropertyName())){
	    				//收货人地址
	    				receiveCustomerAddressListener(bean);
	    			}else if("prePayAmount".equalsIgnoreCase(bindingEvent.getPropertyName())){
	    				//预付金额
	    				prePayAmountListener(bean);
	    			}else if("toPayAmount".equalsIgnoreCase(bindingEvent.getPropertyName())){
	    				//到付金额
	    				toPayAmountListener(bean);
	    			}else if("errorHandlingCode".equalsIgnoreCase(bindingEvent.getPropertyName())){
	        			//差错处理
	        			errorHandling(bean);
        			}else if("innerNotes".equalsIgnoreCase(bindingEvent.getPropertyName())){
	        			// 对内备注
        				innerNotesListener(bean);
        			}else if ("transportFee".equalsIgnoreCase(bindingEvent.getPropertyName())) {
        				// 开单报价
    					transportFeeListener(bean);// 开单报价
    				}else if ("importWaybillNo".equalsIgnoreCase(bindingEvent.getPropertyName())) {
    					// 更改运单号
    					waybillNoListener(bean);// 更改运单号
    				}else if ("promotionsCode".equalsIgnoreCase(bindingEvent.getPropertyName())) {
    					// 优惠编码
    					promotionsCodeListener(bean);
    				}else if ("pickupToDoor".equals(bindingEvent.getPropertyName())) {// 上门接货
    					pickupToDoorListener(bean);
    				}else if("recordProductCode".equals(bindingEvent.getPropertyName()))
                    {

   					recordProductCodeListener(bean);
 			        }else if("activeInfo".equalsIgnoreCase(bindingEvent.getPropertyName())){
 						//市场推广活动名称
 						activeInfoListener(bean);
 					}else if("deliveryCustomerAddressNote".equals(bindingEvent.getPropertyName())){
 						//发货人地址备注
 						if(StringUtils.isNotBlank((String) bindingEvent.getNewValue())){
 							bean.setDeliveryCustomerAddressNote((String) bindingEvent.getNewValue());
 						}
 					}else if("receiveCustomerAddressNote".equals(bindingEvent.getPropertyName())){
 						//收货人地址备注
 						if(StringUtils.isNotBlank((String) bindingEvent.getNewValue())){
 							bean.setReceiveCustomerAddressNote((String) bindingEvent.getNewValue());
 						}
 					}
	    			//liding comment for NCI Project
	    			/**
					 * 对交易流水号进行监控
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-01-23
					 */
//					else if("transactionSerialNumber".equals(bindingEvent.getPropertyName())){
//						verificate(bean.getTransactionSerialNumber());
//					}
    			
    			} catch (Exception e) {//异常
    				LOG.error(e.getMessage(), e);//处理异常记录日志
    				MsgBox.showError(e.getMessage()+"\n");//弹出对话框
    			}
    			
    		}
	}
	
	/**
	 * 市场推广活动名称监听事件
	 * @创建时间 2014-5-20 上午10:46:58   
	 * @创建人： WangQianJin
	 */
	private void activeInfoListener(WaybillInfoVo bean){
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);
	}
	
	/**
	 * 转货
	 * @param bean
	 */
	private void tfrFreightMethodListener(WaybillInfoVo bean) {
		
		/**
		 * 判断合票方式和运输性质是否为空
		 */
		if (bean.getTfrFreightMethod() != null && bean.getTfrProductCode() != null) {
			/**
			 * 判断合票方式是否为单独开单和运输性质是否为精准空运
			 */
			if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getTfrFreightMethod().getValueCode())
					&& ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getTfrProductCode().getCode())) {
				/**
				 * 创建提货方式对象
				 */
				DataDictionaryValueVo receiveMethod = new DataDictionaryValueVo();
				receiveMethod.setValueCode(WaybillConstants.AIRPORT_PICKUP);
				receiveMethod.setValueName(i18n.get("foss.gui.creating.transferInfoPanel.airportPickup.label"));
				bean.setTfrReceiveMethod(receiveMethod);
				ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferAllocationType().setEnabled(false);
			} else {
				ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferAllocationType().setEnabled(true);
			}
		}
	}

	/**
	 * 更改运输性质，如果没有走货路线需要提示，然后设置计算价格按钮为不可编辑，如果有，设置计算价格按钮可以编辑
	 * @param bean
	 */
	private void recordProductCodeListener(WaybillInfoVo bean){
		
		if(!ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferInfoPanel().getBtnQueryBranch().isEnabled()&&bean.getRecordProductCode()!=null)
		{
	try{
			OrgInfoDto	dto =rfcService.queryLodeDepartmentInfo(bean.getPickupCentralized(),bean.getReceiveOrgCode(), bean.getCustomerPickupOrgCode().getCode(), bean.getRecordProductCode().getCode());
		
			if (dto == null || dto.getFreightRoute() == null) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
				ui.getWaybillInfoPanel().getBillingPayPanel().getBtnCalculate().setEnabled(false);
				
			}else{
				ui.getWaybillInfoPanel().getBillingPayPanel().getBtnCalculate().setEnabled(true);
			}
			
	}catch(BusinessException e)
	{
		ui.getWaybillInfoPanel().getBillingPayPanel().getBtnCalculate().setEnabled(false);
		MsgBox.showInfo(e.getMessage());
		return;
	}
			
		}
		
		setSaveAndSubmitFalse(ui);
		
	}
	
	
	/**
	 * 更改运单号
	 * @param bean
	 */
	private void waybillNoListener(WaybillPanelVo bean) {
		//修改运单号
		setSaveAndSubmitFalse(ui);
	}
	
	/**
	 * 开单报价
	 * 
	 * @param bean
	 */
	private void transportFeeListener(WaybillPanelVo bean) {
		ConfigurationParamsEntity configuration= BaseDataServiceFactory.getBaseDataService()
				.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.CREATINGEXP_PARTNER_ORDER_DISCOUNT,
						FossConstants.ROOT_ORG_CODE);
		ConfigurationParamsEntity maxconfiguration= BaseDataServiceFactory.getBaseDataService()
				.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.MAX_TRANSPORTFEE_EXP,
						FossConstants.ROOT_ORG_CODE);
		if(null!=configuration && StringUtils.isNotBlank(configuration.getConfValue())){
			double temp = Double.parseDouble(configuration.getConfValue());
			double maxtemp = Double.parseDouble(maxconfiguration.getConfValue());
			int intValue2 = bean.getTransportFee().intValue();
			if (intValue2 != 0) {
				int transportFee = bean.getTempTransportFee().intValue();
				int maxintValue =(int) (transportFee * maxtemp + 0.5);

				int intValue = (int) (transportFee * temp + 0.5);
				if (intValue2 >= intValue && intValue2 <= maxintValue) {
					CalculateFeeTotalUtils.calculateFee(bean);
					ui.getButtonPanel().getBtnSubmit().setEnabled(true);
				} else {
					MsgBox.showInfo(i18n
							.get("foss.gui.changingexp.listener.Waybill.transportFeeMsgBoxExp",intValue,maxintValue));
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				}
			}else{
				ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				throw new WaybillValidateException(i18n
					.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp.ISNOTZERO"));
			}
		}else{
			int intValue2 = bean.getTransportFee().intValue();
			if (intValue2 != 0) {
				int transportFee = bean.getTempTransportFee().intValue();
				int intValue = (int) (transportFee * 0.6 + 0.5);
				if (intValue2 >= intValue && intValue2 <= transportFee) {
					CalculateFeeTotalUtils.calculateFee(bean);
					ui.getButtonPanel().getBtnSubmit().setEnabled(true);
				} else {
					MsgBox.showInfo(i18n
							.get("foss.gui.changingexp.listener.Waybill.transportFeeMsgBoxExp",intValue,transportFee));
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				}
			}else{
				ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				throw new WaybillValidateException(i18n
					.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp.ISNOTZERO"));
			}
		}
		
		if (!bean.getIsWholeVehicle()) {//整车的情况才有开单报价
			/**
			 * 不是整车 直接返回不操作
			 */
			return;
		}
		/**
		 * 开单报价为null
		 */
		if (bean.getTransportFee() == null) {
			/**
			 * 直接返回不操作
			 */
			return;
		}
		/**
		 * 整车费用浮动上限
		 */
		BigDecimal rate = bean.getWholeVehicleActualfeeFlowRangeUp();

		/**
		 * 整车费用浮动下限
		 */
		BigDecimal ratelow = bean.getWholeVehicleActualfeeFlowRangeLow();
		if (ratelow == null) {//如果没有配置
			/**
			 * 弹出提示
			 */
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.transportFee.one"));
			/**
			 * 浮动上限取默认值0.1
			 */
			ratelow= BigDecimal.valueOf(0.1);
		}
		
		BigDecimal upLevel = null;
		if(rate!=null){
    		/**
    		 * 整车费用浮动上限
    		 */
    		BigDecimal uprate = BigDecimal.ONE.add(rate);
    		/**
    		 * 整车费用浮动上限值
    		 */
    		upLevel = bean.getWholeVehicleAppfee().multiply(uprate);
		}
		/**
		 * 整车费用浮动下限
		 */
		BigDecimal lowrate = BigDecimal.ONE.subtract(ratelow);	
		/**
		 * 整车费用浮动下限值
		 */
		BigDecimal lowLevel = bean.getWholeVehicleAppfee().multiply(lowrate);
		
		if(upLevel!=null&&upLevel.doubleValue()>0){
    		/**
    		 * 运输费用不在浮动返回以内
    		 * 不能用该开单费用
    		 */
    		if (bean.getTransportFee().doubleValue() < lowLevel.doubleValue() || bean.getTransportFee().doubleValue() > upLevel.doubleValue()) {
    			/**
    			 * 提示错误
    			 */
    			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.transportFee.two") + lowLevel.doubleValue() + "~" + upLevel.doubleValue());
    		}else{/**
    			 * 重新设置开单费用
    			 */
    			bean.setWholeVehicleActualfee(bean.getTransportFee());
    		}
		}else{
			/**
    		 * 运输费用不在浮动返回以内
    		 * 不能用该开单费用
    		 */
    		if (bean.getTransportFee().doubleValue() < lowLevel.doubleValue()) {
    			/**
    			 * 提示错误
    			 */
    			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.transportFee.three") + lowLevel.doubleValue());
    		}else{/**
    			 * 重新设置开单费用
    			 */
    			bean.setWholeVehicleActualfee(bean.getTransportFee());
    		}
		}
	}

	/**
	 * 
	 * <p>
	 * (货物品名事件监听)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-19 下午03:09:04
	 * @see
	 */
	private void gooodsNameListener(WaybillInfoVo bean) {
		// 是否限保物品
		isInsurGoods(bean);
		// 是否贵重物品
		isValuablesGoods(bean);
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);// 提交为不可编辑
	}
	
	/**
	 * 
	 * 联动后，都需要把提交实体设置为空，保证修改优惠券编号后，都需要用户点击计算，来校验优惠券 和把优惠券的金额减去中运费
	 * 
	 * @author 026113-FOSS-linwensheng
	 * @date 2013-1-4 上午09:52:49
	 */

	private void promotionsCodeListener(WaybillPanelVo bean) {
		/**
		 * 联动后，都需要把提交实体设置为空
		 */
		bean.setCouponInfoDto(null);
		/**
		 * 联动后，都需要把提交实体设置为空
		 */
		bean.setCouponFree(null);
		//修改运单号
		setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 是否限保物品
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午02:49:13
	 */
	private void isInsurGoods(WaybillPanelVo bean) {
		
		/**
		 *  判断是否限保物品
		 *  1.	SUC-494-录入货物信息
		 *  规则：
		 * SR1： 3.	若货物为限保物品，则系统自动限定保价金额，且不可修改，并提示“货物为限保物品”；
		 */
		LimitedWarrantyItemsEntity entity = waybillService.isInsurGoods(bean.getGoodsName());
		if (entity != null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.insurGoods.one"));
			bean.setVirtualCode(entity.getVirtualCode());
			bean.setLimitedAmount(entity.getLimitedAmount());//限制报价金额
			ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);// 设置为不可编辑
			/**
			 * 如果限保物品保价金额为空时：设置为0，不可编辑
			 */
			if (entity.getLimitedAmount() != null) {
				bean.setInsuranceAmount(entity.getLimitedAmount());
				bean.setInsuranceAmountCanvas(entity.getLimitedAmount().toString());
			} else {
				bean.setInsuranceAmount(BigDecimal.ZERO);
				bean.setInsuranceAmountCanvas(ZERO);
			}
		} else {
			ui.incrementPanel.getTxtInsuranceValue().setEnabled(true);// 设置可编辑
			bean.setVirtualCode("");
		}
	}

	/**
	 * 
	 * 根据名称判断是否贵重物品
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-22 下午03:46:17
	 * @param WaybillBindingListener
	 */
	private void isValuablesGoods(WaybillInfoVo bean) {
		isValueGoods(bean);
	}
	
	/**
	 * 
	 * 对内备注事件监听
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午05:55:20
	 */
	private void innerNotesListener(WaybillInfoVo bean){
		/**
		 *  对内备注
		 */
		StringBuffer sb = new StringBuffer("");
		/**
		 * 木架
		 */
		String woodTxt= "";
		/**
		 * 木箱
		 */
		String sandTxt= "";
		/**
		 *得到对内备注事
		 */
		String originalTxt = bean.getTransportationRemark();
		if(originalTxt==null){//如果为null
			/**
			 * 木架设置为""
			 */
			originalTxt = "";
		}
		
		/**
		 * 对内备注事分解为数组
		 */
		String[] remark = originalTxt.split(";");
		/**
		 * 遍历数组
		 */
		for(int i=0;i<remark.length;i++){
			/**
			 * 查看每个对内备注
			 */
			String oldData = remark[i];
			/**
			 * 如果是木架
			 */
			if(oldData.indexOf(StringUtil.defaultIfNull(i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"))) != -1){
				/**
				 * 木架设置为原始值
				 */
				woodTxt = oldData;
			}
			/**
			 * 如果是木箱
			 */
			if(oldData.indexOf(StringUtil.defaultIfNull(i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"))) != -1){
				/**
				 * 木箱设置为原始值
				 */
				sandTxt = oldData;
			}
		}
		/**
		 * 木架不是空
		 */
		if(StringUtils.isNotEmpty(woodTxt)){
			/**
			 * 木架附上
			 */
    		sb.append(woodTxt).append("; ");
    	}
		/**
		 * 木箱不是空
		 */
    	if(StringUtils.isNotEmpty(sandTxt)){
    		/**
			 * 木箱附上
			 */
    		sb.append(sandTxt).append("; ");
    	}
		
		/**
		 * 对外备注
		 */
        JComboCheckBox checkbox = (JComboCheckBox) (JComboCheckBox)ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombOutSideRemark();
        /**
		 * 遍历数组
		 */
        for (String string : checkbox.getCheckedValues()) {
        	/**
			 * 查看每个对外备注
			 */
			sb.append(string).append("; ");
		}
        	
        /**
         * 对内备注
         */
        String innerSiderRemark = ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getTxtInsideRemark().getText();
        /**
         * 对内备注不是null
         */
        if(innerSiderRemark!=null && !"".equals(innerSiderRemark) ){
        	/**
			 * 查看每个对外备注
			 */
        	sb.append(innerSiderRemark).append("; ");
        }
        	
        /**
         * 大车直送
         */
        if(ui.getWaybillInfoPanel().getGoodsPanel().getChbCarThrough().isSelected()){
        	/**
             * 大车直送备注
             */
        	sb.append(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.chbCarThrough.laber")).append("; ");
        }
        if(ui.getWaybillInfoPanel().getGoodsPanel().getChbValuable().isSelected()){
        	sb.append(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.goodsPanel.chbValuable")).append("; ");
        }
        	
        /**
         * 设置对外备注
         */
        bean.setTransportationRemark(sb.toString());	
	
	}
	
	
	/**
	 * 差错处理编号
	 * 
	 * @author 026113-FOSS-林文升
	 * @param bean
	 */
	private void errorHandling(WaybillInfoVo bean){
		/**
		 * 差错编号是否可以使用为false
		 */
		bean.setErrorCodeIsTrue(false);
		try {
			 /**
		     * 
		     * 查询差错处理结果
		     * 
		     * @author 102246-foss-shaohongliang
		     * @date 2012-11-27 上午10:00:13
		     */
			QueryVirtualResultDto errorHandlingResult = waybillService.queryErrorHandlingResult(bean.getErrorHandlingCode());
			/**
			 * 查询失败原因(查询不到或者其他业务规则原因导致查询失败需要返回原因)
			 */
			bean.setErrorHandlingResult(errorHandlingResult.getFailReason());
			/**
			 * 查询失败原因不是空
			 */
			if(!StringUtils.isEmpty(errorHandlingResult.getFailReason())){
				/**
				 * 弹出报错对话框
				 */
				MsgBox.showError(bean.getErrorHandlingCode()+":"+errorHandlingResult.getFailReason());
				/**
				 * 清空 中止信息面板
				 */
				setAbortInfoPanelMEssage(ui,"","");
				/**
				 * 不能提交
				 */
				ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				/**
				 * 差错编号是否可以使用为true
				 */
				bean.setErrorCodeIsTrue(false);
			}
			/**
			 * 有处理结果
			 */
			if(errorHandlingResult.isHandleResult()){
				/**
				 * 处理结果运单号和该运单号一样
				 */
				if(errorHandlingResult.getWayBillNo().endsWith(bean.getWaybillNo())){
					/**
					 * 弹出报错对话框
					 */
					MsgBox.showError(bean.getErrorHandlingCode()+i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.getErrorHandlingCode"));
					/**
					 * 查询失败原因(查询不到或者其他业务规则原因导致查询失败需要返回原因)
					 */
					bean.setErrorHandlingResult(bean.getErrorHandlingCode()+i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.getAllErrorHandlingCode"));
					/**
					 * 设置 中止信息面板
					 */
					setAbortInfoPanelMEssage(ui,bean.getErrorHandlingCode()+i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.getErrorHandlingCode"),DateUtils.convert(new Date()));
					/**
					 * 差错编号是否可以使用为true
					 */
					bean.setErrorCodeIsTrue(true);
					/**
					 * 设置为fasle
					 */
					ui.getButtonPanel().getBtnSubmit().setEnabled(true);
					
				}else{
					/**
					 * 弹出报错对话框
					 */
					MsgBox.showError(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.notErrorHandlingCode"));
					/**
					 * 清空 中止信息面板
					 */
					setAbortInfoPanelMEssage(ui,"","");
					/**
					 * 差错编号是否可以使用为true
					 */
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
					/**
					 * 差错编号是否可以使用为true
					 */
					bean.setErrorCodeIsTrue(false);
				}
				
			}
		} catch (BusinessException e) {//异常
			ExceptionHandler.alert(e);
			setAbortInfoPanelMEssage(ui,"","");
			bean.setErrorCodeIsTrue(false);
			ui.getButtonPanel().getBtnSubmit().setEnabled(false);
		}
		
	}
	
	/**
	 * 设置 中止信息面板
	 * @param ui
	 * @param lblErrorResult
	 * @param operateTime
	 */
    private void setAbortInfoPanelMEssage(WaybillRFCUI ui,String lblErrorResult,String operateTime){
    	ui.getMessagePanel().getAbortInfoPanel().getLblErrorResult().setText(lblErrorResult);
    	ui.getMessagePanel().getAbortInfoPanel().getLblOperateTime().setText(operateTime);
    	
    }
    
	/**
	 * 
	 * （提货方式变更监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午01:56:55
	 */
	private void returnReceiveMethodListener(WaybillInfoVo bean) {
		if (bean.getRtnReceiveMethod() != null) {//提货方式变 not null
			// 各种自提
			selfRtnPickup(bean);

			setSaveAndSubmitFalse(ui);//不能提交
			//内部带货处理
			Common.innerPickup(bean, ui, bean.getRtnReceiveMethod().getValueCode());
		}
	}
	
	/**
	 * 
	 * 提货方式-自提业务规则处理
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午03:25:42
	 */
	private void selfRtnPickup(WaybillInfoVo bean) {
		String code = bean.getRtnReceiveMethod().getValueCode();
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)) {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(false);
			bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		} else {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);
		}
	}


	/**
	 * 
	 * 返货没有空运类型
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-22 下午04:48:08
	 */
	private void returnProductCodeListener(WaybillInfoVo bean) {

		// 根据运输性质改变提货方式
		changeRtnPickUpMode(bean);
		// 空运、偏线以及中转下线无法选择签收单返单
		Common.setReturnBill(bean, ui);
		// 偏线与空运不能选择预付费保密
		setRtnSecretPrepaid(bean);
		//清掉走货路径
		cleanTranportOrgInfo(bean);
		setSaveAndSubmitFalse(ui);//不能提交
		
	}
	
	
	/**
	 * 清掉走货路径
	 * @param bean
	 */
	private void cleanTranportOrgInfo(WaybillInfoVo bean) {
		
		//变更类型
		String rfcType = bean.getRfcType().getValueCode();
		bean.setOrgInfoDto(null);// 清掉走货路径
		
		if(!WaybillRfcConstants.RETURN.equals(rfcType)  && !WaybillRfcConstants.TRANSFER.equals(rfcType)){
			bean.setLoadLineName("");// 配载线路名称
			bean.setLoadLineCode("");// 配载线路编码
			bean.setLoadOrgCode("");// 配载部门编号
			bean.setLoadOrgName("");// 配载部门名称
		}
		bean.setLastLoadOrgCode("");// 最终配载部门编号
		bean.setLastLoadOrgName("");// 最终配载部门名称
		bean.setPackageOrgCode("");// 代打木架部门编码
		bean.setPackingOrganizationName("");// 代打木架部门名称
		bean.setDoPacking("");// 是否可以打木架
		bean.setLastOutLoadOrgCode("");//最终配置外场
		bean.setTfrTargetOrgCode("");//转运目的站
		bean.setTfrCustomerPickupOrgName("");//转运提货网点
		bean.setTfrCustomerPickupOrgCode(null);//清除最转运终目的站
		bean.setRtnTargetOrgCode("");//返货目的站
		bean.setRtnCustomerPickupOrgName("");//返货提货网点
		bean.setRtnCustomerPickupOrgCode(null);//清除返货最终目的站
	}

	/**
	 * 
	 * 根据运输性质的改变，改变提货方式
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-8 上午9:56:05
	 */
	private void changeRtnPickUpMode(WaybillInfoVo bean) {
		// 清除数据
		DefaultComboBoxModel returnPikcModeModel = ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getPickModeModel();
		if(returnPikcModeModel.getSize()>0){
			ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getCombReturnAllocationType().setSelectedIndex(0);
		}
	}

	/**
	 * 
	 * 空运、偏线以及中转下线无法选择签收单返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:09:08
	 */
	protected void setRtnReturnBill(WaybillInfoVo bean) {
		ProductEntityVo productVo = bean.getRtnProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(false);
		} else {
			ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(true);
		}
	}

	/**
	 * 
	 * 偏线与空运不能选择预付费保密
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-8 上午08:30:46
	 */
	private void setRtnSecretPrepaid(WaybillInfoVo bean) {
		WaybillInfoVo waybillVo = ui.getOriginWaybill();
		//原为预付费保密的可以取消；
		//原为非预付费保密的不可更改为预付费保密
		if(waybillVo.getSecretPrepaid()!= null && waybillVo.getSecretPrepaid()){
			ProductEntityVo productVo = bean.getRtnProductCode();
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setSelected(false);
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setEnabled(false);
			} else {
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setEnabled(true);
			}
		}
	}

	/**
	 * 
	 * 原运输类型 + 原目的站 + 转运目的站 | 原运输类型 + 货物是否出最终配载部门 + 最终配载部门 + 转运目的站 --> 转运运输性质
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 上午9:30:36
	 */
	public void returnCustomerPickupOrgNameListener(WaybillInfoVo bean) {
		//修改返货费率可编辑性
		changeRtnUnitPrice(bean);
	}

	
	/**
	 * 
	 * 修改转运费率是否可编辑
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 下午2:10:52
	 */
	private void changeRtnUnitPrice(WaybillInfoVo bean) {
		boolean isNeedHandEdit = isRtnUnitHandEdit(bean);
		bean.setRtnNeedHandWrite(isNeedHandEdit);
		ReturnInfoPanel returnInfoPanel = ui.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel();
		//返货费率、计价方式可编辑
		returnInfoPanel.getCombReturnBillingType().setEnabled(isNeedHandEdit);
		returnInfoPanel.getTxtReturnUnitPrice().setEnabled(isNeedHandEdit);
	}
	
	/**
	 * 
	 * 是否需要手动修改转运费率
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 下午2:13:10
	 */
	private boolean isRtnUnitHandEdit(WaybillInfoVo bean) {
		if(!isNeedCalcZZF(bean)){
			setTransportOtherCharge(bean);
			return false;
		}
		
		//已出最终配载部门手动编辑
		if(bean.isRtnNeedFilter()){
			return true;
		}
		if(bean.getRtnCustomerPickupOrgCode() == null){
			return false;
		}
		ProductPriceDto dto = null;

		QueryBillCacilateDto cacilateDto = getRtnQueryParam(bean);
		//开单时刻上门接货价
		if(bean.getPickupToDoor()){
			dto = getProductPriceDto(bean, cacilateDto);
		}
		//开单时刻非上门接货价
		if(dto == null){
			cacilateDto.setIsReceiveGoods(FossConstants.NO);//no
			dto = getProductPriceDto(bean, cacilateDto);
		}
		//当前时刻上门接货价
		if(dto == null && bean.getPickupToDoor()){
			cacilateDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
			cacilateDto.setIsReceiveGoods(FossConstants.YES);//no
			dto = getProductPriceDto(bean, cacilateDto);
		}
		//当前时刻非上门接货价
		if(dto == null){
			cacilateDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
			cacilateDto.setIsReceiveGoods(FossConstants.NO);//no
			dto = getProductPriceDto(bean, cacilateDto);
		}

		//找不到公布价手动编辑
		if(dto == null){
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.nullProductPrice"));
			return true;
		}else{
			//设置计费类型
			DataDictionaryValueVo dataDictionaryValueVo = new DataDictionaryValueVo();
			dataDictionaryValueVo.setValueCode(dto.getCaculateType());
			bean.setRtnBillingType(dataDictionaryValueVo);//费率
			bean.setRtnUnitPrice(dto.getActualFeeRate());
			//总费用
			bean.setRtnFee(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));// 四舍五入
			bean.setTfrFee(BigDecimal.ZERO);
			setTransportOtherCharge(bean);
			return false;
		}
	}
	
	/**
	 * 
	 * 是否需要重新计算中转费
	 * 
	 * 现状：FOSS设计：货物离开出发部门库存后，客户要求发起目的站变更均要收取中转费。
	 * 从12月3日至12月18日，中转费投诉424条，最终外场出库前就起草同城更改并收取中转费的投诉有103条，
	 * 其中铂金客户4位，黄金客户8位。一线部门反馈，客户对现有中转费收取规则很不满意。 
	 * 
	 * 方案：1、同城定义：以原到达部门所在地级市为判断依据。更改前后部门在同一地级市行政区域范围内为同城，
	 * 更改前后部门不在同一地级市行政区域范围内为异地。 
	 * 2、货物离开出发部门库存后，客户要求将目的站更改为异地目的站，须收取中转费。
	 * 例如，客户要求将原发往重庆北碚区营业部的货物更改至四川泸州蓝田营业部，虽然是同一外场配载，但分属不同地级市，须收取中转费。 
	 * 3、货物离开出发部门库存后，客户要求将目的站更改为同城目的站： 
	 *  3.1、货物离开出发部门库存后至到达部门上一级外场交接出库前，不收取中转费。
	 *  例如客户要求将原发往上海浦东上南路营业部的货物更改至上海派送部，虽然是不同外场配载，但分属同意地级市，不收取中转费 
	 *  3.2、货物在到达部门（驻地派送部为到达部门）库存中，更改为同城部门，须收取中转费 
	 *  3.3、货物离开到达部门上一级外场库存或有交接记录且交接目的站为原到达部门，须收取中转费 
	 * 4、中转费计算规则不变 
	 * 
	 * 效益：每月货物在最终外场出库前，更改同城目的站的有17855条，公司每月开单量约为300万条，
	 * 规则优化后影响的更改率很小，为0.6%。但是可以提高这些更改运单客户的满意度。
	 * 平均每一条同城目的站更改需要20分钟时间解释，优化后将节省357100分钟、5951小时。
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-12 下午7:00:11
	 */
	private boolean isNeedCalcZZF(WaybillInfoVo bean) {
		boolean isCalcZZF = true;
		
		//界面选择的新的提货网点
		BranchVo branchVo = bean.getFinalCustomerPickupOrgCode();
		
		//导入的时候的提货部门所属的
		BranchVo branchVoOld = bean.getCustomerPickupOrgCode();
		
		if(!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
				.equals(ui.getOriginWaybill().getProductCode().getCode())
				&& !ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
				.equals(ui.getOriginWaybill().getProductCode().getCode())){
			
			if(branchVo!=null && branchVoOld!=null){
				//修改后的提货网点如果是属于同一个城市
				if(branchVo.getCityCode()!=null 
						&&branchVoOld.getCityCode()!=null 
						&& branchVo.getCityCode().equals(branchVoOld.getCityCode())){
					String currentStockOrgCode = bean.getStockStatus().getCurrentStockOrgCode();
					//当前网点如果不是如果不是最后一个提货网点， 并且同城的情况下  不需要收取中转费
					if(!currentStockOrgCode.equals(branchVoOld.getCode())){
						isCalcZZF = false;
					}
				}
			}
		}
		//费率
		DataDictionaryValueVo dataDictionaryValueVo = new DataDictionaryValueVo();
		dataDictionaryValueVo.setValueCode(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		bean.setTfrBillingType(dataDictionaryValueVo);
		bean.setRtnBillingType(dataDictionaryValueVo);
		bean.setTfrUnitPrice(BigDecimal.ZERO);
		bean.setRtnUnitPrice(BigDecimal.ZERO);
		//总费用
		if(bean.getTfrFee()!=null){
			bean.setOtherFee(bean.getOtherFee().subtract(bean.getTfrFee()));
		}
		if(bean.getRtnFee()!=null){
			bean.setOtherFee(bean.getOtherFee().subtract(bean.getRtnFee()));
		}
		
		bean.setTfrFee(BigDecimal.ZERO);// 四舍五入
		bean.setRtnFee(BigDecimal.ZERO);
		return isCalcZZF;
	}
	
	/**
	 * 
	 * 获取产品价格查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateDto getRtnQueryParam(WaybillInfoVo bean) {
		QueryBillCacilateDto queryDto = Common.getQueryParam(bean, BooleanConvertYesOrNo.booleanToString(bean
				.getPickupToDoor()), Common.FHF);
		return queryDto;
	}
	

	/**
	 * 
	 * （提货方式变更监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午01:56:55
	 */
	private void transferReceiveMethodListener(WaybillInfoVo bean) {
		if (bean.getTfrReceiveMethod() != null) {
			// 各种自提
			selfTfrPickup(bean);
			
		

			setSaveAndSubmitFalse(ui);//不能提交
			//内部带货处理
			Common.innerPickup(bean, ui, bean.getTfrReceiveMethod().getValueCode());
		}
	}
	
	/**
	 * 
	 * 提货方式-自提业务规则处理
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午03:25:42
	 */
	private void selfTfrPickup(WaybillInfoVo bean) {
		String code = bean.getTfrReceiveMethod().getValueCode();
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)) {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(false);
			bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		} else {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);
		}
	}

	
	/**
	 * 
	 * 变更类型监听
	 * 
	 * 1、运单无出库记录，不允许起草转运单、返货单；
	 * 客户来源可以选择更改、作废；
	 * 内部来源可以选择更改、中止
	 * 2、运单出库记录，不允许起草作废单，涉及到目的站、提货网点的修改必须到转运信息、返货信息面板修改；如果变更提货方式，由“派送”该为“自提”，原始目的站不支持“自提”，提示“原始目的站不支持自提，请选择转运或返货类型”
	 * 客户来源可以选择更改、转运、返货；
	 * 内部来源可以选择更改、中止
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:21:21
	 */
	private void rfcTypeListener(WaybillInfoVo bean) {
		/**
		 * 根据变更类型切换UI显示,并清空变更类型、来源、原因
		 */
		ui.updateComponentClear();
		// 转运
		if(bean.getRfcType() != null){
			//变更类型
			String rfcType = bean.getRfcType().getValueCode();
			if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
				//初始转运数据
				initTansferComponentData();
				//当更改类型为 - 转运 的时候必须收取更改费
				setChangeOtherCharge(bean);
			}else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
				//初始返货数据
				initReturnComponentData();
				//当更改类型为 - 返货 的时候必须收取更改费
				setChangeOtherCharge(bean);
			}
			
		
				
				
		}
	}

	/**
	 * 
	 * 变更来源监听
	 * 
	 * 1、运单无出库记录，不允许起草转运单、返货单；
	 * 客户来源可以选择更改、作废；
	 * 内部来源可以选择更改、中止
	 * 2、运单出库记录，不允许起草作废单，涉及到目的站、提货网点的修改必须到转运信息、返货信息面板修改；如果变更提货方式，由“派送”该为“自提”，原始目的站不支持“自提”，提示“原始目的站不支持自提，请选择转运或返货类型”
	 * 客户来源可以选择更改、转运、返货；
	 * 内部来源可以选择更改、中止
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:21:42
	 */
	private void rfcSourceListener(WaybillInfoVo bean) {
		String newValue = bean.getRfcSource();
		if (WaybillRfcConstants.CUSTOMER_REQUIRE.equals(newValue)) {
			// 客户要求变更类型"变更"、"转运"、"返货"、"作废"可选择
			DefaultComboBoxModel model = ui.getImportPanel().getRfcTypeComboBoxModel();
			model.removeAllElements();
			List<DataDictionaryValueEntity> list = waybillService.queryCustomerChangeType();
			try {
				for (DataDictionaryValueEntity dataDictionary : list) {
					
					/*	
					//没有出库记录不能选择"转运"与"返货"
					if(bean.getStockStatus() != null
							&& FossConstants.NO.equals(bean.getStockStatus().getStockRecord())){
						if(WaybillRfcConstants.TRANSFER.equals(dataDictionary.getValueCode())
								|| WaybillRfcConstants.RETURN.equals(dataDictionary.getValueCode())){
							continue;
						}
					}else{
						if(WaybillRfcConstants.INVALID.equals(dataDictionary.getValueCode())){
							continue;
						}else if(bean.getIsWholeVehicle()!=null && bean.getIsWholeVehicle()){
							//整车不能选择"转运"与"返货"
							if(WaybillRfcConstants.TRANSFER.equals(dataDictionary.getValueCode())
									|| WaybillRfcConstants.RETURN.equals(dataDictionary.getValueCode())){
								continue;
							}
						}
					}
					*/
					
					if (WaybillRfcConstants.INVALID.equals(dataDictionary.getValueCode()) || WaybillRfcConstants.CUSTOMER_CHANGE.equals(dataDictionary.getValueCode())) {
						if (WaybillRfcConstants.INVALID.equals(dataDictionary.getValueCode())) {
							if (bean.getStockStatus() == null || FossConstants.YES.equals(bean.getStockStatus().getStockRecord())) {
								continue;
							}
						}
						DataDictionaryValueVo dataDictionaryVo = new DataDictionaryValueVo();
						PropertyUtils.copyProperties(dataDictionaryVo, dataDictionary);
						model.addElement(dataDictionaryVo);
					}
				}
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				LOG.error(e.getMessage(), e);
			}
			if(model.getSize()>0){
				ui.getImportPanel().getCboRfcType().setSelectedIndex(0);
			}
			// 产品类型
			initCombProductType(newValue,bean);
		} else if (WaybillRfcConstants.INSIDE_REQUIRE.equals(newValue)) {
			

			// 内部要求变更类型"变更"、"中止"可选择
			DefaultComboBoxModel model = ui.getImportPanel().getRfcTypeComboBoxModel();
			model.removeAllElements();
			List<DataDictionaryValueEntity> list = waybillService.queryInsideChangeType();
			try {
				for (DataDictionaryValueEntity dataDictionary : list) {
					//没有出库记录不能选择"中止"
//					if(bean.getStockStatus() != null
//							&& FossConstants.NO.equals(bean.getStockStatus().getStockRecord())){
//						if(WaybillRfcConstants.ABORT.equals(dataDictionary.getValueCode())){
//							continue;
//						}
//					}
					DataDictionaryValueVo dataDictionaryVo = new DataDictionaryValueVo();
					PropertyUtils.copyProperties(dataDictionaryVo, dataDictionary);
					model.addElement(dataDictionaryVo);
				}
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				LOG.error(e.getMessage(), e);
			}
			if(list.size() > 0){
				ui.getImportPanel().getCboRfcType().setSelectedIndex(0);
			}
			// 产品类型
			initCombProductType(newValue,bean);
		} else {
			DefaultComboBoxModel model = ui.getImportPanel().getRfcTypeComboBoxModel();
			model.removeAllElements();
		}
	}


	/**
	 * 
	 * 产品类型下拉框
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-31 下午6:29:11
	 */
	private void initCombProductType(String newValue,WaybillInfoVo bean) {
		List<ProductEntity> list = new ArrayList<ProductEntity>();
		ProductEntityVo ProductCode360 = null;
		ProductEntityVo ProductCodeDEAP = null;
				// 查询类型为快递的产品
				UserEntity user = (UserEntity) SessionContext.getCurrentUser();
				OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
				if(dept == null){
					return;
				}
				waybillServicecreate =  WaybillServiceFactory.getWaybillService();
			    list = waybillServicecreate.getAllProductEntityByDeptCodeForCargoAndExpress(dept.getCode(), WaybillConstants.TYPE_OF_EXPRESS, new Date());

		DefaultComboBoxModel productTypeModel = ui.getWaybillInfoPanel()
				.getTransferPanel().getTransportInfoPanel()
				.getProductTypeModel();
		productTypeModel.removeAllElements();
		for (ProductEntity dataDictionary : list) {
			ProductEntityVo vo = new ProductEntityVo();
			try {
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);// 异常
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);// 异常
			}
			if (ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE
					.equals(vo.getCode())) {
				ProductCode360 = vo;
			}
			if (ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
					.equals(vo.getCode())) {
				ProductCodeDEAP=vo;
			}
			if (bean.getIsChangeReason() == 2) {
				if (ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT
						.equals(vo.getCode())) {
				} else {
					if (ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
							.equals(ui.getOriginWaybill().getProductCode()
									.getCode())) {
						productTypeModel.addElement(vo);
					} else {
						if(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
						.equals(vo.getCode())) {
							
						}else{productTypeModel.addElement(vo);
						}
					}
				}
			} else {
				if (ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
						.equals(ui.getOriginWaybill().getProductCode()
								.getCode())) {
					productTypeModel.addElement(vo);
				} else {
					if(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
					.equals(vo.getCode())) {
						
					}else{productTypeModel.addElement(vo);
					}
				}
			}
		}
		//如果初始化的值中没有的商务专递，默认为360
		if (bean.getIsChangeReason() == 2) {
			if (ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT
					.equals(ui.getOriginWaybill().getProductCode().getCode())&&ProductCode360!=null) {
				bean.setProductCode(ProductCode360);
			}else if (ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
					.equals(ui.getOriginWaybill().getProductCode()
							.getCode())){
				bean.setProductCode(ProductCodeDEAP);
			}else {
				// 设置默认值
				bean.setProductCode(ui.getOriginWaybill().getProductCode());
			}
		} else {
			if (ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
					.equals(ui.getOriginWaybill().getProductCode()
							.getCode())) {
				bean.setProductCode(ProductCodeDEAP);
			} else {
			// 设置默认值
			bean.setProductCode(ui.getOriginWaybill().getProductCode());
		}}
	}


	/**
	 * 
	 * 运单导入时运输信息对外调用
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-19 下午7:11:53
	 */
	public void productCodeChanged(ProductEntityVo entity) {
		if(entity == null){
			return;
		}// 根据运输性质改变提货方式
		changePickUpMode(entity);
	}

	/**
	 * 
	 * 根据运输性质的改变，改变提货方式
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-8 上午9:56:05
	 */
	private void changePickUpMode(ProductEntityVo entity) {
		
		
		
		
		
		// 清除数据
		DefaultComboBoxModel pikcModeModel = ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPickModeModel();
		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = null;
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getCode())) {
			list = waybillService.queryPickUpGoodsAir();
		} else {
			list = waybillService.queryPickUpGoodsHighWays();
		}
		try {
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo dataDictionaryVo = new DataDictionaryValueVo();
				PropertyUtils.copyProperties(dataDictionaryVo, dataDictionary);
				pikcModeModel.addElement(dataDictionaryVo);
			}
		} catch (IllegalAccessException e) {
			LOG.error(e.getMessage(),e);//异常
		} catch (InvocationTargetException e) {
			LOG.error(e.getMessage(),e);//异常
		} catch (NoSuchMethodException e) {
			LOG.error(e.getMessage(),e);//异常
		}
		if(ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().getItemCount()>0){
			ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().setSelectedIndex(0);
		}
		ui.getBinderWaybill().setReceiveMethod(ui.getOriginWaybill().getReceiveMethod());
	}

	/**
	 * 
	 * 转运运输性质 --> 转运预配航班
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-22 下午04:48:08
	 */
	private void transferProductCodeListener(WaybillInfoVo bean) {
		
		// 根据运输性质改变提货方式
		changeTfrPickUpMode(bean);
		// 空运、偏线以及中转下线无法选择签收单返单
		Common.setReturnBill(bean, ui);
		// 偏线与空运不能选择预付费保密
		setTfrSecretPrepaid(bean);
		//清掉走货路径
		cleanTranportOrgInfo(bean);

		setSaveAndSubmitFalse(ui);
		
	}
	
	
	
	
	
	

	/**
	 * 
	 * 偏线与空运不能选择预付费保密
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-8 上午08:30:46
	 */
	private void setTfrSecretPrepaid(WaybillInfoVo bean) {
		WaybillInfoVo waybillVo = ui.getOriginWaybill();
		//原为预付费保密的可以取消；
		//原为非预付费保密的不可更改为预付费保密
		if(waybillVo.getSecretPrepaid()!= null && waybillVo.getSecretPrepaid()){
			ProductEntityVo productVo = bean.getTfrProductCode();
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setSelected(false);
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setEnabled(false);
			} else {
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setEnabled(true);
			}
		}
	}

	/**
	 * 
	 * 根据运输性质的改变，改变提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午04:49:48
	 */
	private void changeTfrPickUpMode(WaybillInfoVo bean) {
		ProductEntityVo productVo = bean.getTfrProductCode();
		DefaultComboBoxModel pikcModeModel = ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getPickModeModel();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			
			setTfrAirPropertyToTrue();
			

			pikcModeModel.removeAllElements();
			List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsAir();
			for (DataDictionaryValueEntity dataDictionary : list) {
				//客户原因不能选择内部带货、免费自提 
				if (WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode()) 
						|| WaybillConstants.AIR_PICKUP_FREE.equals(dataDictionary.getValueCode())) {
					continue;
				}
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				pikcModeModel.addElement(vo);
				if (WaybillConstants.AIR_SELF_PICKUP.equals(vo.getValueCode())){// 设置提货方式默认值
					bean.setTfrReceiveMethod(vo);
				}
			}
		} else {
			
			setTfrAirPropertyToFalse(bean);
			
			pikcModeModel.removeAllElements();
			List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
			for (DataDictionaryValueEntity dataDictionary : list) {
				//客户原因不能选择内部带货、免费自提 
				if (WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode()) 
						|| WaybillConstants.AIR_PICKUP_FREE.equals(dataDictionary.getValueCode())) {
					continue;
				}
				DataDictionaryValueVo vo = new DataDictionaryValueVo();//创建对象
				ValueCopy.valueCopy(dataDictionary, vo);//copy属性内容
				pikcModeModel.addElement(vo);//加入
				if (WaybillConstants.SELF_PICKUP.equals(vo.getValueCode())){// 设置提货方式默认值
				
					bean.setTfrReceiveMethod(vo);
				}
			}
		}
	}

	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private void setTfrAirPropertyToFalse(WaybillInfoVo bean){
		ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombFreightMethod().setEnabled(false);
		ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferPredictFlight().setEnabled(false);
		//航班类型
		setTfrFlightNumberType(bean);
	}

	/**
	 * 
	 * 设置航班类型
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private void setTfrFlightNumberType(WaybillInfoVo bean){
		int size = ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getPredictFlightModel().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			vo = (DataDictionaryValueVo) ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getPredictFlightModel().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setTfrFlightNumberType(vo);
			}
		}
	}

	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private void setTfrAirPropertyToTrue(){
		//如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
		ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombFreightMethod().setEnabled(true);
		//如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
		ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getCombTransferPredictFlight().setEnabled(true);
	}

	
	/**
	 * 
	 * 原运输类型 + 原目的站 + 转运目的站 | 原运输类型 + 货物是否出最终配载部门 + 最终配载部门 + 转运目的站 --> 转运运输性质
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 上午9:30:36
	 */
	public void transferCustomerPickupOrgNameListener(WaybillInfoVo bean) {
		//修改转运费率可编辑性
		changeTfrUnitPrice(bean);
	}
	
	/**
	 * 
	 * 修改转运费率是否可编辑
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 下午2:10:52
	 */
	private void changeTfrUnitPrice(WaybillInfoVo bean) {
		boolean isNeedHandEdit = isTfrUnitHandEdit(bean);//是否可编辑
		setTfrNeedHandWrite(bean, isNeedHandEdit);//设置是否可编辑
	}
	
	/**
	 * 
	 * 手动编辑
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-11 下午3:09:07
	 */
	private void setTfrNeedHandWrite(WaybillInfoVo bean, boolean isNeedHandEdit) {
		bean.setTfrNeedHandWrite(isNeedHandEdit);//是否可编辑
		TransferInfoPanel transferInfoPanel = ui.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel();
		//转运费率、计价方式可编辑
		transferInfoPanel.getCombTransferBillingType().setEnabled(isNeedHandEdit);
		transferInfoPanel.getTxtTransferUnitPrice().setEnabled(isNeedHandEdit);
	}

	/**
	 * 
	 * 是否需要手动修改转运费率
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 下午2:13:10
	 */
	private boolean isTfrUnitHandEdit(WaybillInfoVo bean) {
		if(!isNeedCalcZZF(bean)){
			setTransportOtherCharge(bean);
			return false;
		}
		
		//已出最终配载部门手动编辑
		if(bean.isTfrNeedFilter()){
			/**
			 * 可以编辑
			 */
			
			return true;
		}
		if(bean.getTfrCustomerPickupOrgCode() == null){
			/**
			 * 不可以编辑
			 */
			return false;
		}
		//产品价格
		ProductPriceDto dto = null;
		//计算dto
		QueryBillCacilateDto cacilateDto = getTfrQueryParam(bean);
		//开单时刻上门接货价
		if(bean.getPickupToDoor()){
			dto = getProductPriceDto(bean, cacilateDto);
		}
		//开单时刻非上门接货价
		if(dto == null){
			cacilateDto.setIsReceiveGoods(FossConstants.NO);//no
			dto = getProductPriceDto(bean, cacilateDto);
		}
		//当前时刻上门接货价
		if(dto == null && bean.getPickupToDoor()){
			cacilateDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
			cacilateDto.setIsReceiveGoods(FossConstants.YES);//no
			dto = getProductPriceDto(bean, cacilateDto);
		}
		//当前时刻非上门接货价
		if(dto == null){
			cacilateDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
			cacilateDto.setIsReceiveGoods(FossConstants.NO);//no
			dto = getProductPriceDto(bean, cacilateDto);
		}
		
		
		//找不到公布价手动编辑
		if(dto == null){
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.nullProductPrice"));
			return true;
		}else{
			//设置计费类型
			DataDictionaryValueVo dataDictionaryValueVo = new DataDictionaryValueVo();
			dataDictionaryValueVo.setValueCode(dto.getCaculateType());
			bean.setTfrBillingType(dataDictionaryValueVo);
			//费率
			bean.setTfrUnitPrice(dto.getActualFeeRate());
			//总费用
			bean.setTfrFee(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));// 四舍五入
			bean.setRtnFee(BigDecimal.ZERO);
			setTransportOtherCharge(bean);
			return false;
		}
	}
	
	/**
	 * 
	 * 获取产品价格查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateDto getTfrQueryParam(WaybillInfoVo bean) {
		QueryBillCacilateDto queryDto = Common.getQueryParam(bean, BooleanConvertYesOrNo.booleanToString(bean
				.getPickupToDoor()), Common.ZYF);
		return queryDto;
	}

	/**
	 * 
	 * 获得产品价格详细信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-5 下午04:27:16
	 */
	private ProductPriceDto getProductPriceDto(WaybillInfoVo bean, QueryBillCacilateDto cacilateDto) {
		List<ProductPriceDto> productPrice = waybillService
				.queryProductPriceList(cacilateDto);
		ProductPriceDto dto = null;
		if (productPrice == null) {
			return null;
		} else {
			for (ProductPriceDto fee : productPrice) {
				dto = fee;
			}
		}
		return dto;
	}
	
	
	/**
	 * 
	 * 初始化转运数据 转运运输性质： 1） 原运输类型为汽运：以原目的站作为转运始发站，与转运目的站匹配运输性质； 2） 原运输类型为偏线或空运： ⅰ
	 * 若货物未出原最终配载部门库存，以原最终配载部门所在城市作为转运始发站，与转运目的站匹配运输性质； ⅱ
	 * 若货物已出原最终配载部门库存，转运运输性质只能为偏线或空运。
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-3 上午9:23:13
	 */
	private void initTansferComponentData() {
		WaybillInfoVo bean = ui.getBinderWaybill();
		
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
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			}
			productTypeModel.addElement(vo);
		}
		if(transferInfoPanel.getCombTransferProductType().getItemCount()>0){
			transferInfoPanel.getCombTransferProductType().setSelectedIndex(0);
		}
		
		//如果货物已出最终配载部门，转运费率手工输入
		if(bean.isTfrNeedFilter()){
			transferInfoPanel.getTxtTransferUnitPrice().setEnabled(true);
		}else{
			transferInfoPanel.getTxtTransferUnitPrice().setEnabled(false);
		}
		
		
		//合票方式初始化
		DefaultComboBoxModel CombFreightMethodModel=(DefaultComboBoxModel) transferInfoPanel.getCombFreightMethodModel();
		List<DataDictionaryValueEntity> freightMethodList = waybillService
				.queryFreightMethod();
		CombFreightMethodModel.removeAllElements();
		for (DataDictionaryValueEntity dataDictionary : freightMethodList) {
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			CombFreightMethodModel.addElement(vo);
		}
	}

	/**
	 * 
	 * 数据字典Entity转换为VO
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 下午12:18:48
	 */
	private DataDictionaryValueVo entityToVo(
			DataDictionaryValueEntity dataDictionary) {
		DataDictionaryValueVo vo = new DataDictionaryValueVo();
		try {
			BeanUtils.copyProperties(vo, dataDictionary);
		} catch (IllegalAccessException e) {
			LOG.error(e.getMessage());
		} catch (InvocationTargetException e) {
			LOG.error(e.getMessage());
		}
		return vo;
	}
	
	/**
	 * 
	 * 初始化返货数据 返货运输性质： 1） 原运输类型为汽运：以原目的站作为返货始发站，与返货目的站匹配运输性质； 2） 原运输类型为偏线或空运： ⅰ
	 * 若货物未出原最终配载部门库存，以原最终配载部门所在城市作为返货始发站，与返货目的站匹配运输性质； ⅱ
	 * 若货物已出原最终配载部门库存，返货运输性质只能为偏线或空运。
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-3 上午9:23:13
	 */
	private void initReturnComponentData() {
		WaybillInfoVo bean = ui.getBinderWaybill();
		
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
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			}
			productTypeModel.addElement(vo);
		}
		if(returnInfoPanel.getCombReturnProductType().getItemCount()>0){
			returnInfoPanel.getCombReturnProductType().setSelectedIndex(0);
		}
		
		//如果货物已出最终配载部门，返货费率手工输入
		if(bean.isTfrNeedFilter()){
			returnInfoPanel.getTxtReturnUnitPrice().setEnabled(true);
		}else{
			returnInfoPanel.getTxtReturnUnitPrice().setEnabled(false);
		}
	}
	


	/**
	 * 根据查询条件查询客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-22 下午7:25:50
	 */
	public List<CustomerQueryConditionDto> queryDeliveryCustomer(WaybillInfoVo bean) {
		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 手机
		String mobilePhone = StringUtil.defaultIfNull(bean.getDeliveryCustomerMobilephone());
		// 电话
		String telePhone = StringUtil.defaultIfNull(bean.getDeliveryCustomerPhone());
		// 客户名称
		String custName = StringUtil.defaultIfNull(bean.getDeliveryCustomerName());

		// 解析多个电话号码的查询
		Map<String, List<String>> map = null;
		// 将电话号码中的手机号码解析出来
		List<String> mobiles = null;
		// 电话集合
		List<String> phones = null;

		// 判断名称是否为空
		if (StringUtils.isNotEmpty(custName)) {
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setCustName(custName);
			dto.setExactQuery(true);
			dto.setInvoiceDate(new Date());
			dtoList.add(dto);
		}
		// 判断手机是否为空
		else if (StringUtils.isNotEmpty(mobilePhone)) {
			mobiles = new ArrayList<String>();
			mobiles.add(mobilePhone);

			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setMobilePhone(mobilePhone);
			dto.setExactQuery(true);
			dto.setInvoiceDate(new Date());
			dtoList.add(dto);
		}
		// 判断电话是否为空
		else if (StringUtils.isNotEmpty(telePhone)) {
			map = resolveMobileAndPhone(telePhone);
			mobiles = map.get("mobiles");
			phones = map.get("phones");

			CustomerQueryConditionDto dto = null;
			// 手机不为空时
			if (CollectionUtils.isNotEmpty(mobiles)) {
				for (String mobile : mobiles) {
					dto = new CustomerQueryConditionDto();
					dto.setMobilePhone(mobile);
					dto.setExactQuery(true);
					dto.setInvoiceDate(new Date());
					dtoList.add(dto);
				}
			}
			// 电话不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				for (String phone : phones) {
					dto = new CustomerQueryConditionDto();
					dto.setContactPhone(phone);
					dto.setExactQuery(true);
					dto.setInvoiceDate(new Date());
					dtoList.add(dto);
				}
			}
		}

		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = waybillService.queryCustomerByConditionList(dtoList);

		// 若CRM中查询无果，则从历史客户中查询
		if (CollectionUtils.isEmpty(contactList)) {
			if (CollectionUtils.isNotEmpty(mobiles)) {
				// 将手机中的号码加入集合进行查询
				mobiles.add(mobilePhone);
				List<CustomerQueryConditionDto> custMobile = waybillService.queryHisDeliveryCusByMobileList(mobiles);
				if (CollectionUtils.isNotEmpty(custMobile)) {
					if (CollectionUtils.isEmpty(contactList)) {
						contactList = custMobile;
					} else {
						contactList.addAll(custMobile);
					}
				}
			}
			// 电话号码不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				List<CustomerQueryConditionDto> custPhone = waybillService.queryHisDeliveryCusByPhoneList(phones);
				if (CollectionUtils.isNotEmpty(custPhone)) {
					if (CollectionUtils.isEmpty(contactList)) {
						contactList = custPhone;
					} else {
						contactList.addAll(custPhone);
					}
				}
			}
		}
		return contactList;
	}
	
	/**
	 * 解析字符中的电话号码和手机号码
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午5:20:32
	 */
	private Map<String,List<String>> resolveMobileAndPhone(String phone){
		//存放解析后的手机和电话号码
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		//分隔字符串
		List<String> strList = StrUtils.resolvePhone(phone);
		//电话号码
		List<String> phoneList = new ArrayList<String>();
		//手机号码
		List<String> mobileList = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(strList)){
			//将解析出的字符串进行分类
			for (String str : strList) {
				if(StrUtils.isMobileNO(str)){
					mobileList.add(str);
				}else{
					phoneList.add(str);
				}
			}
		}
		
		map.put("mobiles", mobileList);
		map.put("phones", phoneList);
		return map;
		
		
	}

	/**
	 * 填充发货客户数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午3:16:36
	 */
	public void fillDeliveryCustomerData(WaybillInfoVo bean, String mobile, String phone) {
		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = queryDeliveryCustomer(bean);
		if (CollectionUtils.isNotEmpty(contactList)) {
			// 创建弹出窗口
			QueryConsignerDialog dialog = new QueryConsignerDialog(contactList);
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			// 获得弹出窗口选择的值
			QueryMemberDialogVo memberVo = dialog.getCustomerVo();
			// 这里判空是为了防止选择一行记录未确定后直接关闭时出现的空对象
			if (memberVo == null) {
				/**
				 * 手机、电话修改后失去焦点，取消弹出对话框 由于手机、电话无法变回原值，为保证不出现张冠李戴的情况
				 * 需要将客户信息全部清空掉，所免造成客户误解
				 */
				// 清空发货客户信息
				Common.cleanDeliveryCustomerInfo(ui, bean, "", "");

				return;
			}

			// 填充数据
			Common.fillDeliveryCustomerInfo(ui, memberVo, bean);
		} else {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean.getDeliveryCustomerId()))) {
				// 清空发货客户信息
				Common.cleanDeliveryCustomerInfo(ui, bean, mobile, phone);
			}
			Common.setServiceChargeEnabled("",false, ui);
		}
	}

	/**
	 * 
	 * 清理银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:07:44
	 */
	public void cleanBankInfo(WaybillInfoVo bean) {
		// 收款人名称
		bean.setAccountName("");
		// 收款人开户行
		bean.setAccountBank("");
		// 收款人银行账号
		bean.setAccountCode("");
	}

	/**
	 * 
	 * 查询收货客户
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:08:02
	 */
	public List<CustomerQueryConditionDto> queryReceiveCustomer(WaybillInfoVo bean) {
		//查询条件
		CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
		//手机
		String mobilePhone = StringUtil.defaultIfNull(bean.getReceiveCustomerMobilephone());
		//电话
		String telePhone = StringUtil.defaultIfNull(bean.getReceiveCustomerPhone());
		//客户名称
		String custName = StringUtil.defaultIfNull(bean.getReceiveCustomerName());
		//判断手机是否为空
		if (StringUtils.isNotEmpty(mobilePhone)) {
			dto.setMobilePhone(mobilePhone);
			dto.setExactQuery(true);
		} 
		//判断电话是否为空
		if(StringUtils.isNotEmpty(telePhone)){
			dto.setContactPhone(telePhone);
			dto.setExactQuery(true);
		}
		//判断名称是否为空
		if(StringUtils.isNotEmpty(custName)){
			dto.setCustName(custName);
			dto.setExactQuery(true);
		}
		
		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = waybillService.queryCustomerByCondition(dto);
		
		//若CRM中查询无果，则从历史客户中查询
		if (CollectionUtils.isEmpty(contactList)) {
			Map<String,List<String>> map = resolveMobileAndPhone(telePhone);
			//将电话号码中的手机号码解析出来
			List<String> mobiles =  map.get("mobiles");
			//电话集合
			List<String> phones =  map.get("phones");
			//将手机中的号码加入集合进行查询
			mobiles.add(mobilePhone);
			if(CollectionUtils.isNotEmpty(mobiles)){
				List<CustomerQueryConditionDto> custMobile = waybillService.queryHisReceiveCusByMobileList(mobiles);
				if(CollectionUtils.isNotEmpty(custMobile)){
    				if(CollectionUtils.isEmpty(contactList)){
    					contactList = custMobile;
    				}else{
    					contactList.addAll(custMobile);
    				}
				}
			}
			//电话号码不为空时
			if(CollectionUtils.isNotEmpty(phones)){
				List<CustomerQueryConditionDto> custPhone = waybillService.queryHisReceiveCusByPhoneList(phones);
				if(CollectionUtils.isNotEmpty(custPhone)){
					if(CollectionUtils.isEmpty(contactList)){
						contactList = custPhone;
					}else{
						contactList.addAll(custPhone);
					}
				}
			}
		}
		return contactList;
	}

	/**
	 * 填充收货客户数据
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午3:16:36
	 */
	public void fillReceiveCustomerData(WaybillInfoVo bean, String mobile, String phone) {
		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = queryReceiveCustomer(bean);
		if (CollectionUtils.isEmpty(contactList)) {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean.getReceiveCustomerId()))) {
				// 清空收货客户信息
				Common.cleanReceiveCustomerInfo(ui, bean, mobile, phone);
			}else{
				bean.setReceiveCustomerCode("");
				ui.getConsigneePanel().getTxtReceiveCustomerCode().setText("");
			
			}
		} else {
			//定义VO对象
			QueryMemberDialogVo memberVo = null;
			
			//若只能一条记录时，则自动填充
			if(contactList.size() == 1){
				memberVo = CommonUtils.convertToMemberVo(contactList, WaybillConstants.YES).get(0);
			}else{
				// 创建弹出窗口
				QueryConsigneeDialog dialog = new QueryConsigneeDialog(contactList);
				// 剧中显示弹出窗口
				WindowUtil.centerAndShow(dialog);
				// 获得弹出窗口选择的值
				memberVo = dialog.getCustomerVo();
			}
			// 数据非空判断
			if (memberVo == null) {
				/**
				 * 手机、电话修改后失去焦点，取消弹出对话框 由于手机、电话无法变回原值，为保证不出现张冠李戴的情况
				 * 需要将客户信息全部清空掉，所免造成客户误解
				 */
				// 清空发货客户信息
				Common.cleanReceiveCustomerInfo(ui, bean, "", "");
				return;
			}

			// 填充数据
			Common.fillReceiveCustomerInfo(ui, memberVo, bean);
		}
	}

	/**
	 * 
	 * （发货人手机号码监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 上午10:52:04
	 */
	private void deliveryCustomerMobilephoneListener(WaybillInfoVo bean) {
		// 判断操作
		if (null == bean.getReceiveMethod()) {
			// 增加日志
			LOG.error("开单提货方式不能为空！");
			// 抛出异常信息
			throw new WaybillSubmitException(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.exception.ReceiveMethodNotNull"));
		}

		// 是否内部带货
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			return;
		}

		// 手机号码
		String mobile = StringUtil.defaultIfNull(bean.getDeliveryCustomerMobilephone());
		// 手机号是否为空
		boolean mobilePhone = StringUtil.isEmpty(mobile);
		// 手机非空时,则只以手机为查询条件，其它查询条件置空（防止其它查询条件引影响手机号码查询）
		if (!mobilePhone) {
			// 电话置空
			bean.setDeliveryCustomerPhone("");
			// 客户名称
			bean.setDeliveryCustomerName("");
			// 当未查询出数据时手机会被清空，此时需要重新设置回来
			fillDeliveryCustomerData(bean, mobile, "");
		}
	}

	/**
	 * 
	 * （收货人手机号码事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午04:55:34
	 */
	private void receiveCustomerMobilephoneListener(WaybillInfoVo bean) {
		// 判断对像是否为空
		if (null == bean.getReceiveMethod()) {
			// 增加日志
			LOG.error("开单提货方式不能为空！");
			// 抛出异常信息
			throw new WaybillSubmitException(i18n.get("foss.gui.changingexp.showPickupStationDialogAction.exception.nullReceiveMethod"));
		}
		// 判断是否为内部带货
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			return;
		}

		// 手机号码
		String mobile = StringUtil.defaultIfNull(bean.getReceiveCustomerMobilephone());
		// 是否为空
		boolean mobilePhone = StringUtil.isEmpty(mobile);
		// 手机非空时,则只以手机为查询条件，其它查询条件置空（防止其它查询条件引影响手机号码查询）
		if (!mobilePhone) {
			// 电话置空
			bean.setReceiveCustomerPhone("");
			// 客户名称
			bean.setReceiveCustomerName("");

			// 若未查询出数据，电话号码会被清空，此时需要将其重新设置回来
			fillReceiveCustomerData(bean, mobile, "");
		}
	}

	/**
	 * 
	 * （发货客户电话号码监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午03:54:13
	 */
	private void deliveryCustomerPhoneListener(WaybillInfoVo bean) {
		// 判断是否为内部带货自提
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			return;
		}

		// 发货客户手机是否为空：true为空
		boolean mobilePhone = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getDeliveryCustomerMobilephone()));
		// 发货客户电话号码
		String phone = StringUtil.defaultIfNull(bean.getDeliveryCustomerPhone());
		// 发货客户电话是否为空：true为空
		boolean telePhone = StringUtil.isEmpty(phone);
		// 发货客户名称
		boolean custName = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getDeliveryCustomerName()));
		// 手机、客户名称为空时
		if (mobilePhone && !telePhone && custName) {
			// 若未查询出数据，电话号码会被清空，此时需要将其重新设置回来
			fillDeliveryCustomerData(bean, "", phone);
		}
	}
	
	/**
	 * 收货客户焦点失去监听事件
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-6 下午2:34:31
	 */
	private void deliveryCustomerNameListener(WaybillInfoVo bean){
		//客户名称
		String custName = StringUtil.defaultIfNull(bean.getDeliveryCustomerName());
		//客户名称为空则清空客户编码，设置发货联系人为可修改状态
		if ("".equals(custName)) {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean.getDeliveryCustomerId()))) {
				// 清空发货客户信息
				Common.cleanDeliveryCustomerInfo(ui, bean, "", "");
			}
			// 客户编码
			/**
			 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-02-03下午13:43
			 */
			if(!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
				bean.setDeliveryCustomerCode("");
			}
		} else {
			// 封装查询条件
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			// 收货客户名称
			dto.setCustName(bean.getDeliveryCustomerName());
			// 精确查询
			dto.setExactQuery(true);
			// 查询客户信息
			List<CustomerQueryConditionDto> contacts = waybillService.queryCustomerByCondition(dto);
			if (CollectionUtils.isNotEmpty(contacts)) {
				// 创建弹出窗口
				QueryConsignerDialog dialog = new QueryConsignerDialog(contacts);
				// 剧中显示弹出窗口
				WindowUtil.centerAndShow(dialog);
				// 获得弹出窗口选择的值
				QueryMemberDialogVo memberVo = dialog.getCustomerVo();
				// 这里判空是为了防止选择一行记录未确定后直接关闭时出现的空对象
				if (memberVo == null) {
					return;
				}

				Common.fillDeliveryCustomerInfo(ui, memberVo, bean);
			} else {
				// 清空当前客户名称信息
				bean.setDeliveryCustomerName("");
				// 发货客户编号
				/**
				 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-02-03下午13:43
				 */
				if(!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
					bean.setDeliveryCustomerCode("");
				}
				// 是否月结
				/**
				 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-02-03下午13:43
				 */
				if(!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
					bean.setChargeMode(false);
				}
				// 合同编号
				bean.setAuditNo("");
				//优惠类型
				bean.setPreferentialType("");

			}
		}
	}
	
	/**
	 * 监听发货联系人事件
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-8 下午7:23:37
	 */
	private void deliveryCustomerContactListener(WaybillInfoVo bean){
		// 是否月结
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setChargeMode(false);
		}
		//优惠类型
		bean.setPreferentialType("");
	}


	/**
	 * 
	 * （收货客户电话号码监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午03:54:13
	 */
	private void receiveCustomerPhoneListener(WaybillInfoVo bean) {
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			return;
		}

		// 判断客户手机或电话是否为空或空字符串
		// 手机是否为空：true为空
		boolean mobilePhone = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getReceiveCustomerMobilephone()));
		// 电话号码
		String phone = StringUtil.defaultIfNull(bean.getReceiveCustomerPhone());
		// 电话号码是否为空：true为空
		boolean telePhone = StringUtil.isEmpty(phone);
		// 客户名称是否为空：true为空
		boolean custName = StringUtil.isEmpty(StringUtil.defaultIfNull(bean.getReceiveCustomerName()));
		// 手机为空，电话非空，客户为空
		if (mobilePhone && !telePhone && custName) {
			// 若未查询出数据，电话号码会被清空，此时需要将其重新设置回来
			fillReceiveCustomerData(bean, "", phone);
		}
	}

	/**
	 * 
	 * （运输性质事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-22 下午04:48:08
	 */
	private void productCodeListener(WaybillInfoVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		if(productVo==null || productVo.getCode()==null){
			return;
		}
		// 根据运输性质改变提货方式
		changePickUpMode(bean);
		// 空运、偏线以及中转下线无法选择签收单返单
		Common.setReturnBill(bean, ui);
		// 偏线与空运不能选择预付费保密
		setSecretPrepaid(bean);

		//清空目的站
		setTargetEmpty(bean);
		
		setSaveAndSubmitFalse(ui);
		
	}
	
	/**
	 * 
	 * 清空目的站
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-22 下午04:48:08
	 */
	private void setTargetEmpty(WaybillInfoVo bean) {
		
		
			// 提货网点
			bean.setCustomerPickupOrgCode(null);
			// 提货网点名称
			bean.setCustomerPickupOrgName("");
			// 目的站名称
			bean.setTargetOrgCode("");
		

		
	}

	/**
	 * 
	 * 偏线与空运不能选择预付费保密
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-8 上午08:30:46
	 */
	private void setSecretPrepaid(WaybillInfoVo bean) {
		WaybillInfoVo waybillVo = ui.getOriginWaybill();
		//原为预付费保密的可以取消；
		//原为非预付费保密的不可更改为预付费保密
		if(waybillVo.getSecretPrepaid()!= null && waybillVo.getSecretPrepaid()){
			ProductEntityVo productVo = bean.getProductCode();
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setSelected(false);
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setEnabled(false);
			} else {
				ui.getWaybillInfoPanel().getBillingPayPanel().getChbSecrecy().setEnabled(true);
			}
		}
	}

	/**
	 * 
	 * 空运、偏线以及中转下线无法选择签收单返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:09:08
	 */
	protected void setReturnBill(WaybillInfoVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(false);
			//设置返单类型默认值
			setReturnBillType(bean,ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getReturnBillTypeModel());
			// 将返单费用设置到其他费用表格中
			setOtherCharge(bean);
		} else {
			ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(true);
		}
	}
	
	/**
	 * 
	 * 设置返单类型默认值
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午02:45:07
	 */
	public void setReturnBillType(WaybillInfoVo bean,DefaultComboBoxModel model) {
		for (int i = 0; i < model.getSize(); i++) {
			DataDictionaryValueVo vo=(DataDictionaryValueVo) model.getElementAt(i);
			if (WaybillConstants.NOT_RETURN_BILL.equals(
					vo.getValueCode())) {
				bean.setReturnBillType(vo);
			}
		}
	}
	

	/**
	 * 
	 * 根据运输性质的改变，改变提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午04:49:48
	 */
	private void changePickUpMode(WaybillInfoVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		
		// 对象非空判断
				if (null == productVo) {
					// 返回
					return;
				}
		//处理运输性质为精准空运的情况
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			//如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
			setAirPropertyToTrue();
			
			DefaultComboBoxModel pikcModeModel = ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPickModeModel();

			pikcModeModel.removeAllElements();
			List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsAir();
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				pikcModeModel.addElement(vo);
				if (WaybillConstants.AIR_SELF_PICKUP.equals(vo.getValueCode()))// 设置提货方式默认值
				{
					bean.setReceiveMethod(vo);
				}
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(productVo.getCode())){
			//设置默认的合票方式和航班类型
			DataDictionaryValueVo flightNumberType=new DataDictionaryValueVo();
			DataDictionaryValueVo freightMethod=new DataDictionaryValueVo();
			flightNumberType.setValueCode("MIDDLE_FLIGHT");
			freightMethod.setValueCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
			bean.setFreightMethod(freightMethod);
			bean.setFlightNumberType(flightNumberType);
		
		} 
		else {//处理非精准空运的情况
			//如果运输性质不为精准空运，则将合票方式和航班类型设置为不可编辑
			setAirPropertyToFalse(bean);
		}
		
	}
	
	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private void setAirPropertyToTrue()
	{
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombFreightMethod().setEnabled(true);
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPredictFlight().setEnabled(true);
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getTxtFlightShift().setEnabled(true);
		
		ui.getWaybillInfoPanel().getGoodsPanel().getRdoA().setVisible(false);
		ui.getWaybillInfoPanel().getGoodsPanel().getRdoB().setVisible(false);
		ui.getWaybillInfoPanel().getGoodsPanel().getCombGoodsType().setVisible(true);
		
	}
	
	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private void setAirPropertyToFalse(WaybillInfoVo bean)
	{
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombFreightMethod().setEnabled(false);
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPredictFlight().setEnabled(false);
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getTxtFlightShift().setEnabled(false);

		
		
		ui.getWaybillInfoPanel().getGoodsPanel().getRdoA().setVisible(true);
		ui.getWaybillInfoPanel().getGoodsPanel().getRdoB().setVisible(true);
		ui.getWaybillInfoPanel().getGoodsPanel().getCombGoodsType().setVisible(false);
		
		// 航班类型
		setFlightNumberType(bean);
		// 合票方式
		setFreightMethod(bean);
		// 航班时间
		bean.setFlightShift("");
	}
	
	/**
	 * 
	 * 设置航班类型
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private void setFlightNumberType(WaybillInfoVo bean)
	{
		int size = ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPredictFlightModel().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			vo = (DataDictionaryValueVo) ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPredictFlightModel().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setFlightNumberType(vo);
			}
		}
	}
	
	
//	/**
//	 * 
//	 * 设置退款类型
//	 * 
//	 * @author 025000-FOSS-helong
//	 * @date 2012-12-19 下午09:16:51
//	 */
//	private void setRefundType(WaybillInfoVo bean) {
//		int size = ui.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel().getSize();
//		for (int i = 0; i < size; i++) {
//			DataDictionaryValueVo vo = new DataDictionaryValueVo();
//			vo = (DataDictionaryValueVo) ui.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel().getElementAt(i);
//			if (vo == null || vo.getValueCode() == null) {
//				bean.setRefundType(vo);
//			}
//		}
//	}
	
	/**
	 * 
	 * 设置合票方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private void setFreightMethod(WaybillInfoVo bean) {
//		int size = ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPredictFlightModel().getSize();
//		for (int i = 0; i < size; i++) {
//			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPredictFlightModel().getElementAt(i);
//			if (vo == null || vo.getValueCode() == null) {
//				bean.setFreightMethod(vo);
//			}
//		}
	}
	

	/**
	 * 
	 * （提货方式变更监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午01:56:55
	 */
	private void receiveMethodListener(WaybillInfoVo bean) {
		if (bean.getReceiveMethod() != null) {
			
			String rfcType = bean.getRfcType().getValueCode();
			
			if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
				if(WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
					ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode()
					.getModel().setSelectedItem(ui.getOriginWaybill().getReceiveMethod());
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.pickupNotSupport"));
					
				}
			}
			
			//内部原因不收取更改费, 客户原因收
			String newValue = bean.getRfcSource();
			if (WaybillRfcConstants.CUSTOMER_REQUIRE.equals(newValue)) {
				//货物状态为营业部库存出库的情况下
				if(bean.getGoodsStatus()!=null && !WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())){
					if(!bean.getReceiveMethod() .equals(ui.getOriginWaybill().getReceiveMethod())){
						setChangeOtherCharge(bean);
					}else{
						removeChangeOtherCharge(bean);
					}
				}
			}
			
		
			
			
			// 内部带货
			innerPickup(bean);
			// 各种自提
			selfPickup(bean);
			
			if(!bean.getIsWholeVehicle()){
				//在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
				validateCustomerPointBySelfPickup(bean);
			}

			
			setSaveAndSubmitFalse(ui);
			
			//重新设置送货费
//			resetDeliveryGoodsFee(bean);
			//内部带货处理
			Common.innerPickup(bean, ui, bean.getReceiveMethod().getValueCode());
		}
		
		//修改了提货网点、提货方式时，送货费需要重新计算
		bean.setHandDeliveryFee(null);
	}

	/**
	 * 在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
	 * @param bean
	 */
	private void validateCustomerPointBySelfPickup(WaybillInfoVo bean) {
		//获得网点
		BranchVo customerPickupOrgCode = bean.getCustomerPickupOrgCode();
		
		if(customerPickupOrgCode!=null){
			
			if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
				
				//不支持自提
				if(!FossConstants.YES.equals(customerPickupOrgCode.getPickupSelf())){
//					bean.setReceiveMethod(ui.getOriginWaybill().getReceiveMethod());
//					ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().getModel().setSelectedItem(bean.getReceiveMethod());
					throw new WaybillValidateException(bean.getCustomerPickupOrgName()+i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.pickupNotSupport"));
					
				}
			}else if (WaybillConstants.DELIVER_FREE.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_STORAGE.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_NOUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_FREE_AIR.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_NOUP_AIR.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP_AIR.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_INGA_AIR.equals(bean.getReceiveMethod().getValueCode())) {
				
				//不支持送货上门
				if(! FossConstants.YES.equals(customerPickupOrgCode.getDelivery())){
//					bean.setReceiveMethod(ui.getOriginWaybill().getReceiveMethod());
//					ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().getModel().setSelectedItem(bean.getReceiveMethod());
					throw new WaybillValidateException(bean.getCustomerPickupOrgName()+i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.pickupNotSupport"));
					
				}
			}
		}
	}
	

	/**
	 * 收货客户名称焦点监听事件
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午8:31:07
	 */
	private void receiveCustomerNameListener(WaybillInfoVo bean) {
		// 客户名称
		String custName = StringUtil.defaultIfNull(bean.getReceiveCustomerName());
		// 客户名称为空则清空客户编码，设置发货联系人为可修改状态
		if ("".equals(custName)) {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean.getReceiveCustomerId()))) {
				// 删除收货客户信息
				Common.cleanReceiveCustomerInfo(ui, bean, "", "");
			}

			// 客户编码
			bean.setReceiveCustomerCode("");
		} else {
			// 封装查询条件
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			// 收货客户名称
			dto.setCustName(bean.getReceiveCustomerName());
			// 精确查询
			dto.setExactQuery(true);
			// 查询客户信息
			List<CustomerQueryConditionDto> contacts = waybillService.queryCustomerByCondition(dto);
			// 判断集合是否为空
			if (CollectionUtils.isNotEmpty(contacts)) {
				// 定义VO对象
				QueryMemberDialogVo memberVo = null;
				// 若只能一条记录时，则自动填充
				if (contacts.size() == 1) {
					memberVo = CommonUtils.convertToMemberVo(contacts).get(0);
				} else {
					// 创建弹出窗口
					QueryConsignerDialog dialog = new QueryConsignerDialog(contacts);
					// 剧中显示弹出窗口
					WindowUtil.centerAndShow(dialog);
					// 获得弹出窗口选择的值
					memberVo = dialog.getCustomerVo();
				}

				// 这里判空是为了防止选择一行记录未确定后直接关闭时出现的空对象
				if (memberVo == null) {
					return;
				}

				// 设置收货客户信息
				Common.setQueryReceiveCustomer(ui);
			} else {
				// 清空当前客户名称信息
				bean.setReceiveCustomerName("");
				// 收货客户ID
				bean.setReceiveCustomerId("");
				// 收货客户编号
				bean.setReceiveCustomerCode("");
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
	private void selfPickup(WaybillInfoVo bean) {
		String code = bean.getReceiveMethod().getValueCode();
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)) {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(false);
			bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		} else {
			ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);
		}
	}

	/**
	 * 
	 * 内部带货业务处理
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午03:23:55
	 */
	private void innerPickup(WaybillInfoVo bean) {

		// 判断是否内部带货自提
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			// 修改发货联人和收货联系人名称
			ui.getWaybillInfoPanel().getConsignerPanel().getTxtConsignerLinkMan().setEnabled(false);
			ui.getWaybillInfoPanel().getConsignerPanel().getLblConsignerLinkMan().setText(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.consignerDept.label"));
			ui.getWaybillInfoPanel().getConsignerPanel().getBtnConsignerDept().setVisible(true);
			bean.setDeliveryCustomerContact("");// 发货联系人
			ui.getWaybillInfoPanel().getConsigneePanel().getTxtConsigneeLinkMan().setEnabled(false);
			ui.getWaybillInfoPanel().getConsigneePanel().getLblConsigneeLinkMan().setText(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.consigneeDept.label"));
			ui.getWaybillInfoPanel().getConsigneePanel().getBtnConsigneeDept().setVisible(true);
			bean.setReceiveCustomerContact("");// 收货联系人
			// 金额清零
			resetZero(bean);
			
			bean.setReceiveMethodFlag(FossConstants.YES);
		} else {
			if(FossConstants.YES.equals(bean.getReceiveMethodFlag()))
			{
				// 修改发货联人和收货联系人名称
				ui.getWaybillInfoPanel().getConsignerPanel().getTxtConsignerLinkMan().setEnabled(true);
				ui.getWaybillInfoPanel().getConsignerPanel().getLblConsignerLinkMan().setText(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.consignerLinkMan.label"));
				ui.getWaybillInfoPanel().getConsignerPanel().getBtnConsignerDept().setVisible(false);
				bean.setDeliveryCustomerContact("");// 发货联系人
				ui.getWaybillInfoPanel().getConsigneePanel().getTxtConsigneeLinkMan().setEnabled(true);
				ui.getWaybillInfoPanel().getConsigneePanel().getLblConsigneeLinkMan().setText(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.consigneeLinkMan.label"));
				ui.getWaybillInfoPanel().getConsigneePanel().getBtnConsigneeDept().setVisible(false);
				bean.setReceiveCustomerContact("");// 收货联系人
				// 金额复原（重新生成金额）
				recover();
				bean.setReceiveMethodFlag(FossConstants.NO);
			}
		}
	
	}

	/**
	 * 
	 * 不是内部带货则恢复编辑状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午08:22:45
	 */
	private void recover() {
		ui.incrementPanel.getTxtPackCharge().setEnabled(true);// 包装费
		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);// 代收货款
		ui.incrementPanel.getTxtServiceCharge().setEnabled(false);// 装卸费
		ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);// 送货费
		ui.incrementPanel.getTxtInsuranceValue().setEnabled(true);// 保价声明价
		ui.incrementPanel.getCombRefundType().setEnabled(true);// 退款类型
		WaybillInfoVo waybillVo = ui.getOriginWaybill();
		//原为预付费保密的可以取消；
		//原为非预付费保密的不可更改为预付费保密
		if(waybillVo.getSecretPrepaid()!= null && waybillVo.getSecretPrepaid()){
			ui.billingPayPanel.getChbSecrecy().setEnabled(true);// 预付费保密
		}
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(true);// 返单类型
		ui.incrementPanel.getTable().setEnabled(true);// 其他费用表格
		ui.incrementPanel.getBtnAdd().setEnabled(true);// 新增其他费用
		ui.incrementPanel.getBtnDelete().setEnabled(true);// 删除其他费用
	}

	/**
	 * 
	 * 内部带货，需要将金额相关全部清零
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午06:22:39
	 */
	private void resetZero(WaybillInfoVo bean) {
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

		ui.incrementPanel.getTxtPackCharge().setEnabled(false);// 包装费
		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);// 代收货款
		ui.incrementPanel.getTxtServiceCharge().setEnabled(false);// 装卸费
		ui.incrementPanel.getTxtDeliveryCharge().setEnabled(false);// 送货费
		ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);// 保价声明价
		ui.incrementPanel.getCombRefundType().setEnabled(false);// 退款类型
		ui.billingPayPanel.getChbSecrecy().setEnabled(false);// 预付费保密
		ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombReturnBillType().setEnabled(false);// 返单类型
		ui.incrementPanel.getTable().setEnabled(false);// 其他费用表格
		ui.incrementPanel.getBtnAdd().setEnabled(false);// 新增其他费用
		ui.incrementPanel.getBtnDelete().setEnabled(false);// 删除其他费用

		
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
	 * （重量联动监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 下午05:09:48
	 */
	private void goodsWeightTotalListener(WaybillInfoVo bean) {
		vehicleDirect(bean);
		setSaveAndSubmitFalse(ui);
		String result=CommonUtils.promptGoodsWeightTotal(bean);
		/**
		 * 如果校验不正常，提示
		 */
		if(!WaybillConstants.SUCCESS.equals(result))
		{
			MsgBox.showInfo(result);
		}
		
	}

	/**
	 * 
	 * （体积联动监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 下午05:09:44
	 */
	private void goodsVolumeTotalListener(WaybillInfoVo bean) {
		vehicleDirect(bean);// 判断是否需要大车直送
		isValueGoods(bean);// 判断是否贵重物品

		setSaveAndSubmitFalse(ui);
		String result=CommonUtils.promptGoodsVolumeTotal(bean);
		/**
		 * 如果校验不正常，提示
		 */
		if(!WaybillConstants.SUCCESS.equals(result))
		{
			MsgBox.showInfo(result);
		}
		
	}

	/**
	 * 
	 * （大车直送）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 下午06:17:25
	 */
	private void vehicleDirect(WaybillInfoVo bean) {

		if (!bean.getCarDirectDelivery()) {
			Boolean bool = waybillService.isVehicleDirect(bean.getGoodsWeightTotal().toString(), bean.getGoodsVolumeTotal().toString());
			if (bool) {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.changingexp.waybillInfoBindingListener.confirmDialog.vehicleDirect"), i18n.get("foss.gui.changingexp.waybillRFCUI.common.query"), JOptionPane.YES_NO_OPTION)) {
					ui.getWaybillInfoPanel().getGoodsPanel().getChbCarThrough().setSelected(true);
					ui.getWaybillInfoPanel().getGoodsPanel().getChbCarThrough().setEnabled(true);
				}
			}
		}
	}

	/**
	 * 
	 * （件数事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-25 上午10:14:42
	 */
	private void goodsQtyTotalListener(WaybillInfoVo bean, Object oldValue, Object newValue) {
		isValueGoods(bean);// 是否贵重物品
		packPieces(bean, 
				oldValue == null?0:Integer.valueOf(oldValue.toString()),
				newValue == null?0:Integer.valueOf(newValue.toString()));// 设置包装纸默认值

		//打开修改件数dialog
		showChangeGoodsQtyDialog(bean);
		setSaveAndSubmitFalse(ui);
		setSaveAndSubmitFalse(ui);
	}

	/**
	 *打开修改件数的窗口
	 */
	private void showChangeGoodsQtyDialog(WaybillInfoVo bean) {
		ui.getOriginWaybill();
		// 创建弹出窗口
		ChangeGoodsQtyDialog dialog = new ChangeGoodsQtyDialog(bean, ui );
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
	}

	/**
	 * 
	 * （默认货物多少件就有多少纸包装）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 上午11:08:59
	 */
	private void packPieces(WaybillInfoVo bean, int oldValue, int newValue) {
		int disValue = newValue - oldValue;
		if(disValue >= 0 || bean.getPaper() >= disValue){
			bean.setPaper(bean.getPaper() + disValue);
		}else{
			bean.setPaper(newValue);
			bean.setWood(Integer.valueOf(0));// 木
			bean.setFibre(Integer.valueOf(0));// 纤
			bean.setSalver(Integer.valueOf(0));// 托
			bean.setMembrane(Integer.valueOf(0));// 膜
			
			//任何时候只要setWood 为0 都必须调用下面三个方法
			unsetWaybillPanelVoForWoodenPack(bean);
			unsetStorageMatterForWoodenPack(bean);
			unsetWoodenPackFee(bean);
		}
		
	}
	/**
	 * 
	 * 清除打木架/木箱的费用
	 * 
	 * @author foss-sunrui
	 * @date 2013-2-1 下午2:31:33
	 * @param bean
	 * @see
	 */
	private void unsetWoodenPackFee(WaybillPanelVo bean) {
		bean.setPackageFee(BigDecimal.ZERO);
		bean.setCalculatedPackageFee(BigDecimal.ZERO);
		bean.setStandCharge(BigDecimal.ZERO);
		bean.setBoxCharge(BigDecimal.ZERO);
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
	private void unsetWaybillPanelVoForWoodenPack(WaybillPanelVo bean) {
		bean.setStandGoodsNum(null);// 打木架货物件数
		bean.setStandRequirement(null);// 代打木架要求
		bean.setStandGoodsSize(null);// 打木架货物尺寸
		bean.setStandGoodsVolume(null);// 打木架货物体积
		bean.setBoxGoodsNum(null);// 打木箱货物件数
		bean.setBoxRequirement(null);// 代打木箱要求
		bean.setBoxGoodsSize(null);// 打木箱货物尺寸
		bean.setBoxGoodsVolume(null);// 打木箱货物体积
	}

	
	/**
	 * 
	 * （保险声明价值监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-25 上午11:12:48
	 */
	private void insuranceAmountListener(WaybillInfoVo bean) {
		isValueGoods(bean);// 是否贵重物品

		bean.setInsuranceAmountCanvas(bean.getInsuranceAmount().toString());
		setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * （根据重量、件数、保价判断是否贵重物品）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-25 上午11:07:50
	 */
	private void isValueGoods(WaybillInfoVo bean) {
		if (!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(bean.getProductCode().getCode())) {
			Boolean bool = false;
			bool = waybillService.isValueGoods(bean.getGoodsName(), bean.getGoodsQtyTotal().intValue(), bean.getGoodsVolumeTotal().toString(), bean.getInsuranceAmount().toString());
			if (bool) {
				ui.getWaybillInfoPanel().getGoodsPanel().getChbValuable().setSelected(true);
				ui.getWaybillInfoPanel().getGoodsPanel().getChbValuable().setEnabled(false);
			} else {
				ui.getWaybillInfoPanel().getGoodsPanel().getChbValuable().setSelected(false);
				ui.getWaybillInfoPanel().getGoodsPanel().getChbValuable().setEnabled(true);
			}
		}
	}
	
	/**
	 * 
	 * 贵重物品事件监听
	 * @author 025000-FOSS-helong
	 * @date 2012-12-13 上午11:46:48
	 */
	private void preciousGoodsListener(WaybillInfoVo bean)
	{
		Boolean bool = bean.getPreciousGoods();
		if (bool) {
			String remark = bean.getTransportationRemark();
			bean.setTransportationRemark(remark + i18n.get("foss.gui.changingexp.waybillInfoBindingListener.valuableGoods.label")+";");
			ui.getWaybillInfoPanel().getGoodsPanel().getChbValuable().setSelected(true);
		}else
		{
			String remark = bean.getTransportationRemark();
			remark = remark.replace(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.valuableGoods.label")+";", "");
			remark = remark.replace(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.valuableGoods.label"), "");
			bean.setTransportationRemark(remark);
			ui.getWaybillInfoPanel().getGoodsPanel().getChbValuable().setSelected(false);
		}
	}

	/**
	 * 
	 * 大车直送事件监听
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午05:55:20
	 */
	private void carDirectDeliveryListener(WaybillInfoVo bean){
		Boolean bool = bean.getCarDirectDelivery();
		if (bool) {
			String remark = bean.getTransportationRemark();
			bean.setTransportationRemark(remark + i18n.get("foss.gui.changingexp.waybillInfoBindingListener.chbCarThrough.laber")+";");
		}else
		{
			String remark = bean.getTransportationRemark();
			remark = remark.replace(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.chbCarThrough.laber")+";", "");
			remark = remark.replace(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.chbCarThrough.laber"), "");
			bean.setTransportationRemark(remark);
		}
	
	}

	/**
	 * 
	 * （尺寸事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 上午08:53:50
	 */
	private void goodsSizeListener(WaybillInfoVo bean) {

		if (bean.getGoodsSize() != null && !"".equals(bean.getGoodsSize())) {
			if (NumberValidate.checkIsGoodsSize(bean.getGoodsSize())) {
				calculateVolume(bean);
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.goodsSizeListener"));
			}
		}
	}

	/**
	 * 
	 * 根据传入的尺寸参数计算体积
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午03:53:39
	 */
	private void calculateVolume(WaybillInfoVo bean) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		try {
			Object result = engine.eval(bean.getGoodsSize());
			BigDecimal bigDecimal = new BigDecimal(result.toString());
			bigDecimal = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP);
			BigDecimal m = new BigDecimal("1000000");// 将厘米转换成米
			bigDecimal = bigDecimal.divide(m);
			BigDecimal upLimit = new BigDecimal(WaybillConstants.VOLUME_UPLIMIT);
			if (bigDecimal.compareTo(upLimit) > 0) {
				MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.overVolumeUpLimit") + WaybillConstants.VOLUME_UPLIMIT);
				bean.setGoodsSize("");
			} else if (BigDecimal.ZERO.compareTo(bigDecimal) > 0) {
				MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.lessVolumeZero"));
				bean.setGoodsSize("");
			} else {
				bean.setGoodsVolumeTotal(bigDecimal);
			}

			setSaveAndSubmitFalse(ui);
		} catch (ScriptException e) {
			LOG.error(e.getMessage(), e);
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.goodsSizeException") + e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * （包装-纸事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 上午11:48:26
	 */
	private void paperListener(WaybillInfoVo bean) {
		getPackage(bean);
	}
	
	/**
	 * 
	 * 代打木架取消清除储运事项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-2 上午08:44:42
	 */
	private void unsetStorageMatterForWoodenPack(WaybillPanelVo bean) {
		cleanRemark(bean,i18n.get("foss.gui.changingexp.woodYokeEnterAction.standGoods"));
		cleanRemark(bean,i18n.get("foss.gui.changingexp.woodYokeEnterAction.boxGoods"));
	}
	
	/**
	 * 
	 * 新增或替换储运事项中打木架、大木箱信息
	 * @author 025000-FOSS-helong
	 * @date 2013-3-11 下午07:21:56
	 */
	private void cleanRemark(WaybillPanelVo bean , String key)
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
				//判断储运事项中的这段数据是否存在与传入参数一致的数据，存在则用最新的信息替换
				if(oldData.indexOf(StringUtil.defaultIfNull(key.trim())) != -1)
				{
					continue;
				}
				transportationRemark.append(remark[i]);
				transportationRemark.append(";");
			}
		}
		//设置重新拼装的储运事项
		bean.setTransportationRemark(transportationRemark.toString());
	}

	/**
	 * 
	 * （包装-木事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 上午11:48:26
	 */
	private void woodListener(WaybillInfoVo bean) {

		if(bean.getWood()!=null)
		{
			int wood = bean.getWood();
			if (wood >= 1) {
				if (FossConstants.YES.equals(bean.getDoPacking())) {
					if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.changingexp.waybillInfoBindingListener.confirmDialog.doPacking"), i18n.get("foss.gui.changingexp.waybillRFCUI.common.query"), JOptionPane.YES_NO_OPTION)) {
						checkPackageLabeledGoodSelected(bean);
						showWoodYokeDialog(bean);
						setSaveAndSubmitFalse(ui);
					}
				} else {
					MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.woodPackingException"));
				}
			}else{
				
				//任何时候只要Wood 为0 都必须调用下面三个方法
				unsetWaybillPanelVoForWoodenPack(bean);
				unsetStorageMatterForWoodenPack(bean);
				unsetWoodenPackFee(bean);
			}
		}else{
			bean.setWood(Integer.valueOf(0));
			
			//任何时候只要setWood 为0 都必须调用下面三个方法
			unsetWaybillPanelVoForWoodenPack(bean);
			unsetStorageMatterForWoodenPack(bean);
			unsetWoodenPackFee(bean);
		}
		getPackage(bean);

	}
	
	/**
	 * 
	 * 包装-纤事件监听
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午02:33:41
	 * @param bean
	 */
	private void fibreListener(WaybillInfoVo bean)
	{
		//如果数据为空，则置为0
		if(bean.getFibre()==null)
		{
			bean.setFibre(Integer.valueOf(0));
		}
		getPackage(bean);
	}
	
	/**
	 * 
	 * 包装-托事件监听
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午02:33:41
	 * @param bean
	 */
	private void salverListener(WaybillInfoVo bean)
	{
		//如果数据为空，则置为0
		if(bean.getSalver()==null)
		{
			bean.setSalver(Integer.valueOf(0));
		}
		getPackage(bean);
	}
	
	/**
	 * 
	 * 包装-膜事件监听
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午02:33:41
	 * @param bean
	 */
	private void membraneListener(WaybillInfoVo bean)
	{
		//如果数据为空，则置为0
		if(bean.getMembrane()==null)
		{
			bean.setMembrane(Integer.valueOf(0));
		}
		getPackage(bean);
	}
	
	/**
	 * 
	 * 包装-其他事件监听
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午02:33:41
	 * @param bean
	 */
	private void otherPackageListener(WaybillInfoVo bean)
	{
		getPackage(bean);
	}
	
	
	/**
	 * 
	 * 获取包装
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 上午11:05:50
	 */
	private String getPackage(WaybillPanelVo vo){
		String pack = "";
		if(vo.getPaper() != null && vo.getPaper().intValue() != 0){
			pack = pack + vo.getPaper()+ i18n.get("foss.gui.changingexp.waybillInfoBindingListener.getPackage.paper");
		}
		
		if(vo.getWood() != null && vo.getWood().intValue() != 0){
			pack = pack + vo.getWood()+ i18n.get("foss.gui.changingexp.waybillInfoBindingListener.getPackage.wood");
		}
		
		if(vo.getFibre() != null && vo.getFibre().intValue() != 0){
			pack = pack + vo.getFibre()+ i18n.get("foss.gui.changingexp.waybillInfoBindingListener.getPackage.fibre");
		}
		
		if(vo.getSalver() != null && vo.getSalver().intValue() != 0){
			pack = pack + vo.getSalver()+ i18n.get("foss.gui.changingexp.waybillInfoBindingListener.getPackage.salver");
		}
		
		if(vo.getMembrane() != null && vo.getMembrane().intValue() != 0){
			pack = pack + vo.getMembrane()+ i18n.get("foss.gui.changingexp.waybillInfoBindingListener.getPackage.membrane");
		}
		
		if(StringUtils.isNotEmpty(vo.getOtherPackage())){
			pack = pack + vo.getOtherPackage();
		}
		
		vo.setGoodsPackage(pack);
		return vo.getGoodsPackage();
	}

	

	/**
	 * 
	 * 默认勾选已打木架件数
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-4 下午2:19:13
	 */
	private void checkPackageLabeledGoodSelected(WaybillInfoVo bean) {
		//代打包装总数量
		int count = bean.getWood();
		List<LabeledGoodChangeHistoryDto> dtos = bean.getLabeledGoodChangeHistoryDtoList();
		
		int hasNo = 0;
		
		for(LabeledGoodChangeHistoryDto d: dtos){
			if(!LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE
					.equals(d.getChangeType())){
				hasNo ++ ;
			}
		}
		
		if(count>hasNo){
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.overWoodPackingCount"));
			count =hasNo;
		}
		
		
		
		bean.setSelectedLabeledGoodEntities(dtos);
		
	}

	/**
	 * 
	 * （显示代打木架窗口）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 下午02:23:24
	 */
	private void showWoodYokeDialog(WaybillInfoVo bean) {

		// 创建弹出窗口
		EnterYokeInfoChangeDialog dialog = new EnterYokeInfoChangeDialog(ui);
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
	}

	/**
	 * 重载showPickupStationDialog方法，传入提货网点对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午9:45:56
	 */
	private void showPickupStationDialog(WaybillInfoVo bean, List<BranchVo> depts) {
		//如果是外部普通更改，不允许弹出
		String rfcType = bean.getRfcType().getValueCode();
		if(!WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)){
			if (CollectionUtils.isNotEmpty(depts)) {
				// 创建弹出窗口
				QueryPickupStationDialog dialog = new QueryPickupStationDialog(depts,bean);
				// 剧中显示弹出窗口
				WindowUtil.centerAndShow(dialog);
				BranchVo branchVO = dialog.getBranchVO();

				if(branchVO != null){
					ShowPickupStationDialogAction action = new ShowPickupStationDialogAction();
					action.setInjectUI(ui);
					action.setDialogData(branchVO, bean);
					
					setSaveAndSubmitFalse(ui);
				}
					
			} else {
				// 不做业务处理
			}
		}
		
	}

	/**
	 * 
	 * 退款类型事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午05:19:11
	 */
	private void refundTypeListener(WaybillInfoVo bean) {
		setBankInfo(bean);
		setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 返单类型事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:30:24
	 */
	private void returnBillTypeListener(WaybillInfoVo bean) {
		// 将返单费用设置到其他费用表格中
		setOtherCharge(bean);
		setSaveAndSubmitFalse(ui);
	}
	
	
	/**
	 * 
	 * 设置更改费到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	public void removeChangeOtherCharge(WaybillInfoVo bean) {
		
		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//更改费Code
		String ggfCode = PriceEntityConstants.PRICING_CODE_GGF;
		//更改费用项
		OtherChargeVo ggfChargeVo = null;
		for(OtherChargeVo chargeVo : data){
			if(ggfCode.equals(chargeVo.getCode())&&chargeVo.getIsInit()!=null && !chargeVo.getIsInit()){
				data.remove(chargeVo);
				ggfChargeVo = chargeVo;
				break;
			}
		}
		if(ggfChargeVo != null){
			//获取更改费用明细
			if( ggfChargeVo.getMoney()==null  ){
				return;
			}
			double vomoeny = 0 ;
			try{
				vomoeny = Double.parseDouble(ggfChargeVo.getMoney());
			}catch(Exception e){
			}
			if(bean.getOtherFee()!=null && vomoeny>0 ){
				bean.setOtherFee(bean.getOtherFee().subtract(BigDecimal.valueOf(vomoeny)));
			}
			List<OtherChargeVo> list = bean.getOtherChargeVos();
			list.remove(ggfChargeVo);
			//设置更改费
			ui.incrementPanel.setChangeDetail(data);

		}
	
		
		
		bean.setOtherChargeChanged(true);
	}
	
	/**
	 * 重新设置送货费
	 * @author WangQianJin
	 * @date 2013-6-18 上午10:26:12
	 */
	public void resetDeliveryGoodsFee(WaybillInfoVo bean) {	
		//获取界面上的费用面板
		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		List<OtherChargeVo> delList=new ArrayList<OtherChargeVo>();
		if(data!=null){
			/**
			 * 删除所有的送货费子项(包括：送货费、送货上楼费、送货进仓费、超远派送费)
			 */
			for(OtherChargeVo chargeVo : data){
				if(PriceEntityConstants.PRICING_CODE_SH.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_SHSL.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_SHJC.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_CY.equals(chargeVo.getCode())){					
					delList.add(chargeVo);
				}
			}
			//将要删除的送货费子项全部删除，子所以要放到delList里面一块删除，是因为遍历的时候不能直接删除，偶尔会报java.util.ConcurrentModificationException
			if(delList.size()>0){
				data.removeAll(delList);
			}
			//获取送货费
			Common.setDeliveryFeeCollection(bean);
			//送货费集合
			List<DeliverChargeEntity> deliverList = bean.getDeliverList();
			if(deliverList!=null && deliverList.size()>0){
				/**
				 * 循环添加送货费子项
				 */
				for(DeliverChargeEntity charge: deliverList){
					if(charge!=null){						
						// 送货费子项
						OtherChargeVo vo = new OtherChargeVo();
						// 费用编码
						vo.setCode(charge.getCode());
						// 名称
						vo.setChargeName(charge.getName());				
						if(charge.getAmount()!=null){
							// 金额
							vo.setMoney(charge.getAmount().toString());
						}else{
							// 金额
							vo.setMoney("0");
						}				
						vo.setId(charge.getId());
						data.add(vo);
					}				
				}		
			}		
			//重新设置界面上的送货费
			ui.incrementPanel.setChangeDetail(data);
		}		
				
	}	
	
	
	/**
	 * 
	 * 设置更改费到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	public void setChangeOtherCharge(WaybillInfoVo bean) {
		
		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//更改费Code
		String ggfCode = PriceEntityConstants.PRICING_CODE_GGF;
		//更改费用项
		OtherChargeVo ggfChargeVo = null;
		for(OtherChargeVo chargeVo : data){
			if(ggfCode.equals(chargeVo.getCode())&&chargeVo.getIsInit()!=null && !chargeVo.getIsInit()){
				ggfChargeVo = chargeVo;
				break;
			}
		}
		if(ggfChargeVo == null){
			//获取更改费用明细
			ggfChargeVo = getChangeOtherCharge(bean);
			if(ggfChargeVo == null || ggfChargeVo.getMoney()==null || ggfChargeVo.getCode()==null ){
				return;
			}
			double vomoeny = 0 ;
			try{
				vomoeny = Double.parseDouble(ggfChargeVo.getMoney());
			}catch(Exception e){
			}
			if(bean.getOtherFee()!=null && vomoeny>0 ){
				bean.setOtherFee(bean.getOtherFee().add(BigDecimal.valueOf(vomoeny)));
			}else if(vomoeny>0){
				bean.setOtherFee(BigDecimal.valueOf(vomoeny));
				bean.setOtherFeeCanvas(bean.getOtherFee().toString());
			}
			data.add(ggfChargeVo);
			//设置更改费
			ui.incrementPanel.setChangeDetail(data);

		}
	
		
		
		bean.setOtherChargeChanged(true);
	}
	
	

	/**
	 * 
	 * 获取转运费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getChangeOtherCharge(WaybillInfoVo bean) {
		OtherChargeVo vo = new OtherChargeVo();
		
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
		// 到达部门CODE
		if(bean.getCustomerPickupOrgCode()!=null){
			queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
		}
		// 产品CODE
		queryDto.setProductCode(bean.getProductCode().getCode());
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		List<ValueAddDto> list = waybillService
				.queryValueAddPriceList(queryDto);
		

		if(list != null){
			for (ValueAddDto dto : list) {
				if(PriceEntityConstants.PRICING_CODE_GGF.equals(dto.getSubType())){
					

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
					
					vo.setIsInit(Boolean.FALSE);
					// 是否可修改
					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto
							.getCanmodify()));
					// 是否可删除
					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto
							.getCandelete()));
					vo.setId(dto.getId());
				}
				
				
			}
		}
		
		
		return vo;
	}
	
//	
//	
//	/**
//	 * 
//	 * 获取转运费用
//	 * 
//	 * @author 025000-FOSS-helong
//	 * @date 2012-11-7 上午11:53:53
//	 */
//	private OtherChargeVo getDeliveryOtherCharge(WaybillInfoVo bean) {
//		OtherChargeVo vo = new OtherChargeVo();
//		
//		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
//		// 出发部门CODE
//		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
//		// 到达部门CODE
//		if(bean.getCustomerPickupOrgCode()!=null){
//			queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
//		}
//		// 产品CODE
//		queryDto.setProductCode(bean.getProductCode().getCode());
//		// 货物类型CODE
//		queryDto.setGoodsTypeCode(null);
//		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
//		queryDto.setWeight(BigDecimal.ZERO);// 重量
//		queryDto.setVolume(BigDecimal.ZERO);// 体积
//		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
//		queryDto.setFlightShift(null);// 航班号
//		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
//		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
//		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
//		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
//		queryDto.setPricingEntryName(null);// 计价名称
//		List<ValueAddDto> list = waybillService
//				.queryValueAddPriceList(queryDto);
//		
//
//		if(list != null){
//			for (ValueAddDto dto : list) {
//				if(PriceEntityConstants.PRICING_CODE_SHJC.equals(dto.getSubType())){
//					
//
//					// 费用编码
//					vo.setCode(dto.getSubType());
//					// 名称
//					vo.setChargeName(dto.getSubTypeName());
//					// 归集类别
//					vo.setType(dto.getBelongToPriceEntityName());
//					// 描述
//					vo.setDescrition(dto.getPriceEntityCode());
//					// 金额
//					vo.setMoney(dto.getFee().toString());
//					// 上限
//					vo.setUpperLimit(dto.getMaxFee().toString());
//					// 下限
//					vo.setLowerLimit(dto.getMinFee().toString());
//					
//					vo.setIsInit(Boolean.FALSE);
//					// 是否可修改
//					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto
//							.getCanmodify()));
//					// 是否可删除
//					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto
//							.getCandelete()));
//					vo.setId(dto.getId());
//				}
//				
//				
//			}
//		}
//		
//		
//		return vo;
//	}

	/**
	 * 
	 * 设置返单费用到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	private void setOtherCharge(WaybillInfoVo bean) {

		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {
			OtherChargeVo otherVo = getReturnBillCharge(bean);
			if(otherVo == null)
				return;
			addReturnBillCharge(data, otherVo, bean);// 添加返单费用到其他费用表格
		} else {
			deleteOtherCharge(data, bean);// 将已有的返单费用从其他费用表格中删除
		}
		ui.incrementPanel.setChangeDetail(data);
		bean.setOtherChargeChanged(true);
	}
	


	/**
	 * 
	 * 设置转运费到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	public void setTransportOtherCharge(WaybillInfoVo bean) {
		bean.setOtherFee(bean.getOtherFee().add(bean.getTfrFee().add(bean.getRtnFee())));
		
		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//中转费Code
		String zzCode = PriceEntityConstants.PRICING_CODE_ZZ;
		//中转费用项
		OtherChargeVo zzfChargeVo = null;
		for(OtherChargeVo chargeVo : data){
			if(zzCode.equals(chargeVo.getCode())&&!chargeVo.getIsInit()){
				zzfChargeVo = chargeVo;
				break;
			}
		}
		if(zzfChargeVo == null){
			//获取中转费用明细
			zzfChargeVo = getTransportFeeCharge(bean);
			if(zzfChargeVo == null){
				return;
			}
			data.add(zzfChargeVo);
		}
		//重新设置中转费
		zzfChargeVo.setMoney(bean.getTfrFee().add(bean.getRtnFee()).toPlainString());
		ui.incrementPanel.setChangeDetail(data);
		bean.setOtherChargeChanged(true);
	}
	

	/**
	 * 
	 * 获取转运费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getTransportFeeCharge(WaybillInfoVo bean) {
		OtherChargeVo vo = new OtherChargeVo();
		PriceEntity dto = waybillService.queryValueAddPriceByCode(PriceEntityConstants.PRICING_CODE_ZZ);
		if(dto != null){
			vo.setChargeName(dto.getName());
			vo.setCode(dto.getCode());
			vo.setId(dto.getId());
		}else{
			vo.setChargeName(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.otherChargeVo.chargeName"));
			
			vo.setCode(PriceEntityConstants.PRICING_CODE_ZZ);
			vo.setId(PriceEntityConstants.PRICING_CODE_ZZ);
		}
		/**
		 * 是否初始化值
		 */
		vo.setIsInit(Boolean.FALSE);
		vo.setIsDelete(false);
		vo.setIsUpdate(false);
		return vo;
	}
	
	
	/**
	 * 
	 * 如果选择的返单类型为无返单，那么需要将之前存在的返单费用删除
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 上午10:44:29
	 */
	private void deleteOtherCharge(List<OtherChargeVo> data, WaybillInfoVo bean) {
		for (int i = 0; i < data.size(); i++) {
			OtherChargeVo otherVo = data.get(i);
			// 比较费用名称，判断是否存在重复的返单费用
			if (otherVo.getChargeName().equals(bean.getReturnBillChargeName())  
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
	 * 对其他费用进行校验，判断是否存在返单费用，不存在则添加返单费用到其他费用，并且进行其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 下午07:06:33
	 */
	private void addReturnBillCharge(List<OtherChargeVo> data, OtherChargeVo returnBillVo, WaybillInfoVo bean) {
		boolean bool = true;
		OtherChargeVo oldVo = null;
		for (int i = 0; i < data.size(); i++) {
			OtherChargeVo otherVo = data.get(i);
			// 比较费用名称，判断是否存在重复的返单费用
			if (otherVo.getChargeName().equals(returnBillVo.getChargeName())) {
				bool = false;
				oldVo = data.get(i);
				data.remove(i);
				data.add(i, returnBillVo);
			}
		}

		// 如果不存在任何返单费用，则直接添加
		if (bool) {
			data.add(returnBillVo);
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal newMoney = new BigDecimal(returnBillVo.getMoney());
			otherChargeSum = otherChargeSum.add(newMoney);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		} else {
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal oldMoney = new BigDecimal(oldVo.getMoney());
			BigDecimal newMoney = new BigDecimal(returnBillVo.getMoney());
			BigDecimal money = newMoney.subtract(oldMoney);
			otherChargeSum = otherChargeSum.add(money);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		}

		// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
		bean.setReturnBillChargeName(returnBillVo.getChargeName());
	}

	/**
	 * 
	 * 获取返单费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getReturnBillCharge(WaybillInfoVo bean) {
		ValueAddDto dto = new ValueAddDto();
		OtherChargeVo vo = new OtherChargeVo();
		String type = "";
		// 到达联传真要转成传真类型
		if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(bean.getReturnBillType().getValueCode())) {
			type = WaybillConstants.RETURNBILLTYPE_FAX;
		} else {
			type = bean.getReturnBillType().getValueCode();
		}
		
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryParam(bean,type));

		if (list != null) {
			if (!list.isEmpty()) {
				dto = list.get(0);
				vo.setChargeName(dto.getPriceEntityName());
				vo.setMoney(dto.getFee().toString());
				vo.setCode(dto.getPriceEntityCode());
				vo.setId(dto.getId());
			}else
			{
				MsgBox.showError(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.nullQueryParam"));
				return null;
			}
		}else
		{
			MsgBox.showError(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.nullQueryParam"));
			return null;
		}
		return vo;
	}

	/**
	 * 
	 * 获取返单查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam(WaybillInfoVo bean, String type) {
		
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		//queryDto.setDestinationOrgCode("00");// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(bean.getGoodsType());// 货物类型CODE
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(type);
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	
	/**
	 * 
	 * 开单付款方式事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午03:21:41
	 */
	private void paidMethodListener(WaybillInfoVo bean) {
		
		
	

		// 货物状态为营业部库存出库的情况下 增加更改费
		// 内部原因不收取更改费, 客户原因收
		String newValue = bean.getRfcSource();
		if (WaybillRfcConstants.CUSTOMER_REQUIRE.equals(newValue)) {
			if (!WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus())) {
				if (!bean.getPaidMethod().equals(ui.getOriginWaybill().getPaidMethod())) {
					setChangeOtherCharge(bean);
				} else {
					removeChangeOtherCharge(bean);
				}
			}
		}
		
		
		//总运费
		BigDecimal totalFee = CommonUtils.defaultIfNull(bean.getTotalFee());
		//代收货款
		BigDecimal codAmout = CommonUtils.defaultIfNull(bean.getCodAmount());
		//付款方式不为空
		if(bean.getPaidMethod()!=null){
			//到付
			if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
				// 到付金额
				bean.setToPayAmount(totalFee);
				// 预付金额
				bean.setPrePayAmount(BigDecimal.ZERO);
				//toPlainString():返回不带指数字段的此 BigDecimal 的字符串表示形式
				//ui.billingPayPanel.getTxtArrivePayment().setText(bean.getToPayAmount().toPlainString());
				//ui.billingPayPanel.getTxtCashpayment().setText(bean.getPrePayAmount().toPlainString());
			} else {
				// 到付金额
				bean.setToPayAmount(codAmout);
				// 预付金额
				bean.setPrePayAmount(totalFee.subtract(codAmout));
				//ui.billingPayPanel.getTxtArrivePayment().setText(bean.getToPayAmount().toPlainString());
				//ui.billingPayPanel.getTxtCashpayment().setText(bean.getPrePayAmount().toPlainString());
			}
		}else{
			// 到付金额
			bean.setToPayAmount(codAmout);
			// 预付金额
			bean.setPrePayAmount(totalFee.subtract(codAmout));
			//ui.billingPayPanel.getTxtArrivePayment().setText(bean.getToPayAmount().toPlainString());
			//ui.billingPayPanel.getTxtCashpayment().setText(bean.getPrePayAmount().toPlainString());
		}
		
		// 付款方式为临时欠款不允许勾选预付费保密
		setSecrecy(bean);
		setSaveAndSubmitFalse(ui);
		//liding comment for NCI Project
		/**
		 * 该方法验证若是银行卡付款，则交易流水号是否可编辑
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-23上午08:13
		 */
		//whetherBankCardPayment(bean);
		Common.validateExpPayMethod(bean);
	}
	
	/**
	 * 
	 * 付款方式为临时欠款不允许勾选预付费保密
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 上午10:49:14
	 */
	private void setSecrecy(WaybillInfoVo bean)
	{
		WaybillInfoVo waybillVo = ui.getOriginWaybill();
		//原为预付费保密的可以取消；
		//原为非预付费保密的不可更改为预付费保密
		if(waybillVo.getSecretPrepaid()!= null && waybillVo.getSecretPrepaid()){
			if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
				ui.billingPayPanel.getChbSecrecy().setEnabled(false);
				ui.billingPayPanel.getChbSecrecy().setSelected(false);
			} else {
				ui.billingPayPanel.getChbSecrecy().setEnabled(true);
			}
		}
	}

	/**
	 * 
	 * 整车事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午06:19:55
	 */
	private void isWholeVehicleListener(WaybillInfoVo bean) {
		// 如果是整车
		if (bean.getIsWholeVehicle()) {
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);// 代收货款金额
			ui.incrementPanel.getCombRefundType().setEnabled(false);// 代收货款类型

			//开单报价
			ui.billingPayPanel.billingPayBelongPanel.getLblPublicCharge().setText(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.billCharge.label"));
			//整车面板显示
			ui.billingPayPanel.billingPayBelongPanel.getWholeVehiclePanel().setVisible(true);
		} else {
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);// 代收货款金额
			ui.incrementPanel.getCombRefundType().setEnabled(true);// 代收货款类型

			//整车面板隐藏
			ui.billingPayPanel.billingPayBelongPanel.getWholeVehiclePanel().setVisible(false);
			ui.billingPayPanel.billingPayBelongPanel.getLblPublicCharge().setText(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.publicCharge.label"));
			//公布价
			ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEnabled(false);
		}
	}

	/**
	 * 
	 * 是否经过营业部事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午06:19:55
	 */
	private void isPassDeptListener(WaybillInfoVo bean) {
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
				// 代收货款金额置为0
				bean.setCodAmount(BigDecimal.ZERO);
				
			}
		}
	}

	/**
	 * 
	 * 接货费改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-27 下午05:42:49
	 */
	private void pickupFeeListener(WaybillInfoVo bean) {
		BigDecimal pickupFee=BigDecimal.ZERO;	
		if(bean.getPickupFee()!=null)
		{
			pickupFee=bean.getPickupFee();
		}
		// 设置画布接货费
		if (bean.getBasePickupFee() != null) {
			if (bean.getAuditNo() == null) {
				if (bean.getBasePickupFee().compareTo(pickupFee) == 1) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.PickupFeeError"));
					bean.setPickupFee(bean.getBasePickupFee());
				}
			}
		}
		bean.setPickUpFeeCanvas(bean.getPickupFee().toString());
		Common.getYokeCharge(bean);
		CalculateFeeTotalUtils.calculateFee(bean);
	}
	

	/**
	 * 
	 * 包装费改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午06:02:44
	 */
	private void packageFeeListener(WaybillInfoVo bean) {
		
		//计算运费获得的包装费
		BigDecimal calculatePackageFee = bean.getCalculatedPackageFee();
		//手写包装费
		BigDecimal packageFee = bean.getPackageFee();
		
		
		//打木架
		Integer wood = bean.getWood();
		
		//打木架 empty go
		if(wood == null || bean.getWood().intValue() == 0){

			//设置保存按钮与提交按钮不可编辑
			CalculateFeeTotalUtils.calculateFee(bean);
			return;
		}else{
			
			//打木架有值
			if(calculatePackageFee==null || packageFee==null){
				
				//设置保存按钮与提交按钮不可编辑
				CalculateFeeTotalUtils.calculateFee(bean);
				return;
			}else{
				
				//运费计算值 > 输入值
				if(calculatePackageFee.doubleValue()>packageFee.doubleValue()){
					
					//代打木架费情况下, 包装费输入值不能低于运费计算值
					MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.lessPackageFee"));
					
					//回到包装费焦点
					ui.incrementPanel.getTxtPackCharge().requestFocus();
					
					//把费用清0 下面这个步骤会重新计算
					bean.setPackageFee(BigDecimal.ZERO);
					
					//设置保存按钮与提交按钮不可编辑
					CalculateFeeTotalUtils.calculateFee(bean);
					return;
				}
				
			}
		}
	}

	/**
	 * 
	 * 代收货款改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午06:08:17
	 */
	private void codAmountListener(WaybillInfoVo bean) {
		//如果发货客户不可以精确代收货款 @author liyongfei 2014-09-18
		if(!FossConstants.YES.equals(bean.getAccurateCollection())){//不可以代收货款
			if(bean.getCodAmount()!=null & bean.getCodAmount().toString().contains(".")){
				MsgBox.showInfo((i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.noSupportAccurate")));
				bean.setCodAmount(BigDecimal.ZERO);
				bean.setCodAmountCanvas("0");
				bean.setRefundType(null);
				cleanBankInfo(bean);
				setSaveAndSubmitFalse(ui);
				ui.getWaybillInfoPanel().getIncrementPanel().getTxtCashOnDelivery().requestFocus();
				return;
			}
			
		}
		//若代收货款改为了0，则退款类型默认为空
		DefaultComboBoxModel model = ui.getCombRefundTypeModel();
		if(BigDecimal.ZERO.compareTo(bean.getCodAmount())==0){
			DataDictionaryValueVo dataVo = Common.getCombBoxValue(model,null);
			bean.setRefundType(dataVo);
		}else{
			DataDictionaryValueVo dataVo = Common.getCombBoxValue(model,ui.getOriginWaybill().getRefundType().getValueCode());
			bean.setRefundType(dataVo);
		}
		
		// 淘宝、阿里巴巴、淘宝商场订单不能修改代收货款
		if (ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(bean.getOrderChannel())// 淘宝
				|| ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(bean.getOrderChannel())// 阿里巴巴
				|| ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG.equals(bean.getOrderChannel())) {// 淘宝商城
			// 淘宝类订单（淘宝、阿里巴巴、淘宝商场）不能修改代收货款
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.expWaybillBindingListener.taoOrderValidate"));
		}

		if (bean.getCodAmount().compareTo(BigDecimal.ZERO) == 0) {
			// 在收货部门库存才清空
			if (WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())) {
				Common.cleanCodInfo(ui, bean);
			}
		} else {
			// 画布代收货款
			bean.setCodAmountCanvas(bean.getCodAmount().toString());
		}
		
		//快递代收货款上限 2016年3月10日 09:00:30 葛亮亮 
		Common.cpv(bean);
		
		Common.getYokeCharge(bean);
		CalculateFeeTotalUtils.calculateFee(bean);
		//提交按钮不可用
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);
	}
	
	/**
	 * 
	 * 设置银行信息
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午08:11:49
	 */
	private void setBankInfo(WaybillInfoVo bean){
		DataDictionaryValueVo vo = bean.getRefundType();
		if(vo != null){
			if (bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode())) {
				
				//Common.cleanCodInfo(ui, bean);
				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.exception.nullDeliveryCustomerCode"));
			} else {
				List<CusAccountEntity> list = queryBankAccount(bean);
				if (list != null) {
					BankAccountDialog dialog = new BankAccountDialog(list);
					// 剧中显示弹出窗口
					WindowUtil.centerAndShow(dialog);

					CusAccountEntity entity = dialog.getCusAccountEntity();
					if (entity != null) {
						//即日退限制选择银行
						if(WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getRefundType().getValueCode())){
							if(entity.getOpeningBankName().contains(WaybillConstants.REFUND_TYPE_ONE_DAY_ABC)
									||entity.getOpeningBankName().contains(WaybillConstants.REFUND_TYPE_ONE_DAY_CCB)
									||entity.getOpeningBankName().contains(WaybillConstants.REFUND_TYPE_ONE_DAY_CMB)
									||entity.getOpeningBankName().contains(WaybillConstants.REFUND_TYPE_ONE_DAY_ICBC)){
								bean.setAccountName(entity.getAccountName());// 开户人名称
	    						bean.setAccountCode(entity.getAccountNo());// 开户账号
	    						bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
							}else{
								MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.errorRefundTypeOneDay"));
								//bean.setCodAmount(BigDecimal.ZERO);
								//判断是否可以开即日退
								//Common.cleanCodInfo(ui, bean);
							}
						}else{
    						bean.setAccountName(entity.getAccountName());// 开户人名称
    						bean.setAccountCode(entity.getAccountNo());// 开户账号
    						bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
						}
					}
				}
			}
		} else {
			//Common.cleanCodInfo(ui, bean);
		}
	}

	/**
	 * 
	 * 查询客户银行账号信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:50:56
	 */
	private List<CusAccountEntity> queryBankAccount(WaybillInfoVo bean) {
		List<CusAccountEntity> list = waybillService.queryBankAccountByCode(bean.getDeliveryCustomerCode());
		return list;
	}
	
	/**
	 * 
	 * 装卸费改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午06:10:01
	 */
	private void serviceFeeListener(WaybillInfoVo bean) {
		setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 送货费改变事件监听
	 * 1.送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，
	 * 不能下调。当送货费取值大于最高送货费（基础资料配置）时，
	 * 送货费自动跳改为最高送货费值，但用户可以上调送货费；
	 * 2.3.5	“月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也可以向下修改，最小为0）
     * 2.3.6	除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改	 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午06:12:06
	 */
	public void deliveryGoodsFeeListener(WaybillInfoVo bean) {
		
		
		// 计算送货费
		BigDecimal calculatedeliveryGoodsFee= bean.getCalculateDeliveryGoodsFee();
		// 送货费
		BigDecimal deliveryGoodsFee = bean.getDeliveryGoodsFee();
		// 送货费上限
		// 是否月结
		Boolean chargeMode = bean.getChargeMode();
		
		//没有值得情况下  我也当非月结客户处理
		if(chargeMode == null){
			chargeMode = Boolean.FALSE;
		}

		//非月结 只能改大
		if(!chargeMode){
			//非月结客户,送货费修改金额不能低于系统计算金额
			if(calculatedeliveryGoodsFee.doubleValue()>deliveryGoodsFee.doubleValue()){
				MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.lessDeliveryGoodsFee"));
				bean.setDeliveryGoodsFee(calculatedeliveryGoodsFee);
			}
		}
		
		/**
		 *BUG-34714: 台州派送部---送货费异常（综合查询是55元，打印到达联显示40元）
		 *2013-07-01 9:00 顾贺要求修改送货费后，计算总运费送货费还是手改的送货费
		 */
    	//判断送货费集合是否为空
    	if(CollectionUtils.isNotEmpty(bean.getDeliverList())){
    		//送货费集合
    		List<DeliverChargeEntity> list = bean.getDeliverList();
    		//计算送货费集中当中，非“SH”费用之和
    		BigDecimal otherDeliveryCharge = BigDecimal.ZERO;
    		for (DeliverChargeEntity entity : list) {
    			if(PriceEntityConstants.PRICING_CODE_SH.equals(entity.getCode())){
    				continue;
    			}else{
    				if(otherDeliveryCharge.compareTo(BigDecimal.valueOf(0)) == 0){
    					otherDeliveryCharge = entity.getAmount();
    				}else{
    					otherDeliveryCharge.add(entity.getAmount());
    				}
    			}
			}
    		
    		//原来的基础送货费
    		BigDecimal shCharge = calculatedeliveryGoodsFee.subtract(otherDeliveryCharge);

    		//手动修改费用
    		BigDecimal modifyFee = null;
    		BigDecimal handDelivery = CommonUtils.defaultIfNull(bean.getHandDeliveryFee());
    		//是否上一次手动修改过
    		if(handDelivery.compareTo(BigDecimal.valueOf(0)) != 0){
    			//判断这次是否手动修改过
    			if(deliveryGoodsFee.compareTo(calculatedeliveryGoodsFee) != 0 ){
    				modifyFee = deliveryGoodsFee.subtract(calculatedeliveryGoodsFee);
    				bean.setHandDeliveryFee(deliveryGoodsFee);
    			//未手动修改过
    			}else{
    				modifyFee = CommonUtils.defaultIfNull(bean.getHandDeliveryFee()).subtract(calculatedeliveryGoodsFee);
    				//取得上次手动修改的送货费
    				deliveryGoodsFee = handDelivery;
    				bean.setDeliveryGoodsFee(handDelivery);
    			}
    		}else{
    			modifyFee = deliveryGoodsFee.subtract(calculatedeliveryGoodsFee);
    			bean.setHandDeliveryFee(deliveryGoodsFee);
    		}
    		
    			
    		//遍历集合
    		for (int i=0; i<list.size(); i++) {
    			DeliverChargeEntity charge = list.get(i);
    			if(PriceEntityConstants.PRICING_CODE_SH.equals(charge.getCode())){
    				BigDecimal sum = modifyFee.add(shCharge);
    				list.get(i).setAmount(sum);
    			}
    		}
    	}
    	
		Common.getYokeCharge(bean);
		CalculateFeeTotalUtils.calculateFee(bean);
		bean.setDeliveryGoodsFeeCanvas(String.valueOf(deliveryGoodsFee));
		// 需要重新计算运费
		CalculateFeeTotalUtils.calculateTotalFee(bean);
	}

	/**
	 * 
	 * 航班类型改变事件监听
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午08:18:39
	 */
	@SuppressWarnings("unchecked")
	private void flightNumberTypeListener(WaybillInfoVo bean) {
		
		if(bean.getFlightNumberType() != null)
		{
			FlightEntity entity = new FlightEntity();
			entity.setFlightSort(bean.getFlightNumberType().getValueCode());/**
			 * 设置始发站为当前用户所在部门所在城市
			 */
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			if(user!=null && user.getEmployee()!=null && user.getEmployee().getDepartment()!=null && user.getEmployee().getDepartment().getCityCode()!=null){				
				entity.setDepartureAirport(getCityName(user.getEmployee().getDepartment().getCityCode()));
			}
			// 目的城市
			entity.setDestinationAirport(getCityName(bean.getCustomerPickupOrgCode().getCityCode()));
			PaginationDto dto = waybillService.queryFlightDtoListBySelectiveCondition(
					entity, 0, Integer.MAX_VALUE);
			List<FlightDto> flightDto = dto.getPaginationDtos();
			FlightInfoDialog dialog = new FlightInfoDialog(flightDto);
			// 居中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			
			FlightDto flight = dialog.getFlightDto();
			if(flight != null)
			{
				//设置航班时间
				bean.setFlightShift(getFlyDate(flight));
				
				if (flight.getPlanLeaveTime() != null) {
					// 获取空运预计出发时间
					Date leaveTime = waybillService.getAirLeaveTime(bean.getReceiveOrgCode(), flight.getPlanLeaveTime(), bean.getFlightNumberType().getValueCode());
					// 设置航班预计出发时间
					bean.setPreDepartureTime(leaveTime);
					// 设置航班预计到达、自提时间
					bean.setPreCustomerPickupTime(getAirArriveTime(flight.getPlanArriveTime(), leaveTime));
				}
			}
		}


	}
	
	/**
	 * 
	 * 通过城市CODE获取城市名称
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-4-12 下午03:07:04
	 * @return
	 */
	private String getCityName(String code) {
		AdministrativeRegionsEntity entity = waybillService.queryAdministrativeRegionsByCode(code);
		return entity.getName();
	}
	
	/**
	 * 
	 * 获得空运到达时间
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-11 上午10:29:14
	 */
	@SuppressWarnings("static-access")
	private Date getAirArriveTime(Date planArriveTime, Date planLeaveTime) {
		if (planArriveTime != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(planArriveTime);
			// 获得时
			int hour = cal.get(cal.HOUR_OF_DAY);
			// 获得分
			int minute = cal.get(cal.MINUTE);

			cal.setTime(planLeaveTime);
			// 年
			int year = cal.get(cal.YEAR);
			// 月
			int month = cal.get(cal.MONTH);
			// 日
			int day = cal.get(cal.DAY_OF_MONTH);

			Date airArriveTime = new Date();
			Calendar current = Calendar.getInstance();
			current.setTime(airArriveTime);
			current.set(current.YEAR, year);
			current.set(current.MONTH, month);
			current.set(current.DAY_OF_MONTH, day);
			current.set(current.HOUR_OF_DAY, hour);
			current.set(current.MINUTE, minute);
			current.set(current.SECOND, 0);
			airArriveTime = current.getTime();

			return airArriveTime;
		}
		return null;
	}
	
	/**
	 * 
	 * 航班类型改变事件监听
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午08:18:39
	 */
	@SuppressWarnings("unchecked")
	private void transferflightNumberTypeListener(WaybillInfoVo bean) {
		
		if(bean.getTfrFlightNumberType() != null)
		{
			FlightEntity entity = new FlightEntity();
			
			entity.setFlightSort(bean.getTfrFlightNumberType().getValueCode());
			PaginationDto dto = waybillService.queryFlightDtoListBySelectiveCondition(
					entity, 0, Integer.MAX_VALUE);
			
			List<FlightDto> flightDto = dto.getPaginationDtos();
			
			FlightInfoDialog dialog = new FlightInfoDialog(flightDto);
			// 居中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			
			FlightDto flight = dialog.getFlightDto();
			if(flight != null)
			{
				//设置航班时间
				bean.setTfrFlightShift(getFlyDate(flight));
			}
		}


	}

	/**
	 * 
	 * 组装航班日期
	 * @author 025000-FOSS-helong
	 * @date 2012-12-31 上午09:27:36
	 * @return
	 */
	public static String getFlyDate(FlightDto rowDto)
	{
		//航班日期
		StringBuffer date = new StringBuffer();
		//飞行日期
		String flyDate = "";
		if(FossConstants.YES.equals(rowDto.getFlyOnMonday()))
		{
			flyDate = flyDate + "1";
		}
		
		if(FossConstants.YES.equals(rowDto.getFlyOnTuesday()))
		{
			flyDate = flyDate + "2";
		}
		
		if(FossConstants.YES.equals(rowDto.getFlyOnWednesday()))
		{
			flyDate = flyDate + "3";
		}
		
		if(FossConstants.YES.equals(rowDto.getFlyOnThursday()))
		{
			flyDate = flyDate + "4";
		}
		
		if(FossConstants.YES.equals(rowDto.getFlyOnFriday()))
		{
			flyDate = flyDate + "5";
		}
		
		if(FossConstants.YES.equals(rowDto.getFlyOnSaturday()))
		{
			flyDate = flyDate + "6";
		}
		
		if(FossConstants.YES.equals(rowDto.getFlyOnSunday()))
		{
			flyDate = flyDate + "7";
		}
		
		if(rowDto.getPlanLeaveTime() != null)
		{
			date.append(rowDto.getPlanLeaveTime().toString());
		}
		
		if(rowDto.getPlanArriveTime() != null)
		{
			date.append("|");
			date.append(rowDto.getPlanLeaveTime().toString());
		}
		
		date.append(flyDate);
		
		return date.toString();
	}
	
	/**
	 * 
	 * 合票类型改变事件监听
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午08:28:57
	 */
	private void freightMethodListener(WaybillInfoVo bean) {
		
		/**
		 * 判断合票方式和运输性质是否为空
		 */
		if (bean.getFreightMethod() != null && bean.getProductCode() != null) {
			/**
			 * 判断合票方式是否为单独开单和运输性质是否为精准空运
			 */
			if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())
					&& ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
				/**
				 * 创建提货方式对象
				 */
				DataDictionaryValueVo receiveMethod = new DataDictionaryValueVo();
				receiveMethod.setValueCode(WaybillConstants.AIRPORT_PICKUP);
				receiveMethod.setValueName(i18n.get("foss.gui.creating.transferInfoPanel.airportPickup.label"));
				bean.setReceiveMethod(receiveMethod);
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().setEnabled(false);
			} else {
				ui.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombPickMode().setEnabled(true);
			}
		}
	}
	
	

	/**
	 * 收货人地址失去焦点事件
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 下午5:09:13
	 */
	private void receiveCustomerAddressListener(WaybillInfoVo bean){
		// 整车不需要自动匹配提货网点
		if (bean.getDeliveryCustomerAddress() != null
				&& bean.getDeliveryCustomerAddress().length() > 100) {
			throw new WaybillGisPickupOrgException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.adresslength"));
		}
		/*if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(bean.getProductCode().getCode())) {
			// 若之前有过成功匹配地址的造成地址栏颜色变化时，在清空地址或带出新地址之前应该将地址颜色变回黑色
			ui.getTxtConsigneeAddress().setForeground(Color.BLACK);
			matchPickupOrg(bean, ui);
		}*/
	}
	
	/**
	 * 行政区域焦点监听事件
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午9:49:57
	 */
	private void receiveCustomerAreaListener(WaybillInfoVo bean){
		// 省市区县变化时重新设置编码和DTO
				JAddressFieldForExp txtConsigneeArea = ui.getTxtConsigneeArea();
				String receiveCustomerArea = txtConsigneeArea.getText().trim();
				// 判断地址是否为空
				if ("".equals(receiveCustomerArea)) {
					ui.getTxtConsigneeArea().setAddressFieldDto(null);
					// 向bean中设置DTO
					bean.setReceiveCustomerAreaDto(null);
					// 省份
					bean.setReceiveCustomerProvCode("");
					// 城市
					bean.setReceiveCustomerCityCode("");
					// 区县
					bean.setReceiveCustomerDistCode("");
				} else {
					if (null != ui.getTxtConsigneeArea()) {
						// 获得控件上的DTO
						AddressFieldDto address = ui.getTxtConsigneeArea()
								.getAddressFieldDto();
						if (null != address) {
							// 向bean中设置DTO
							bean.setReceiveCustomerAreaDto(address);
							// 省份
							bean.setReceiveCustomerProvCode(address.getProvinceId());
							// 城市
							bean.setReceiveCustomerCityCode(address.getCityId());
							// 区县
							bean.setReceiveCustomerDistCode(address.getCountyId());
							// 乡镇
							bean.setReceiveCustomerVillageCode(address
									.getVillageTownId());
						}
					}
				}

				// 判断运输性质是否为空
				if (null == bean.getProductCode()) {
					LOG.equals("运输性质不能为空！");
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.listener.Waybill.exception.transportFeeNotNull")
									+ "${user.home}/foss-framework.log");
				}

				// 整车不需要自动匹配提货网点
				if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE
						.equals(bean.getProductCode().getCode())) {
					// 若之前有过成功匹配地址的造成地址栏颜色变化时，在清空地址或带出新地址之前应该将地址颜色变回黑色
					ui.getTxtConsigneeAddress().setForeground(Color.BLACK);
					// matchPickupOrg(bean, ui);
				}

	}
	
	/**
	 * 匹配提货网点
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午9:52:08
	 */
	private void matchPickupOrg(WaybillInfoVo bean, WaybillRFCUI ui) {
		// 地址
		String address = StringUtils.defaultString(bean.getReceiveCustomerAddress());
		// 区域
		String area = ui.getTxtConsigneeArea().getText().trim();
		// 判断地址和区域是否为空
		if (!"".equals(address) && !"".equals(area)) {
			// 根据收货人地址获取特殊地址类型
			List<String> remarkTypes = waybillService.querySpecialAddressByGis(gainGisPickupOrgDto(bean, ui));
			// 收货人详细地址
			JTextFieldValidate txtAddress = ui.getTxtConsigneeAddress();
			// 设置地址颜色
			setReceiveAddressColor(remarkTypes, txtAddress);

			// 根据收货人地址匹配提货网点
			List<GisDepartmentDto> depts = waybillService.queryPickupOrgCodeByGis(gainGisPickupOrgDto(bean, ui));
			// 对象非空判断
			if (depts != null) {
				// 提货网点集合对象
				List<BranchVo> voList = new ArrayList<BranchVo>();
				// 遍历部门信息，通过部门编码和是否代理获得提货网点信息
				for (GisDepartmentDto dto : depts) {
					// 根据组织标杆编码查询部门编码
					OrgAdministrativeInfoEntity org = waybillService.queryOrgByUnifiedCode(dto.getDeptNo());
					if (null != org) {
						dto.setDeptNo(StringUtil.defaultIfNull(org.getCode()));
					}
					// 查询代理网点
					BranchVo vo = bu.getCustomerPickupOrg(dto);
					if (null != vo) {
						voList.add(vo);
					}
				}

				setPickupOrgData(ui, bean, voList);
			}
		}
	}
	
	/**
	 * 收货联系人监听事件
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-8 下午7:32:43
	 */
	private void receiveCustomerContactListener(WaybillInfoVo bean){
		//
	}
	
	/**
	 * 根据特殊地址类型设置
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:59:35
	 */
	private void setReceiveAddressColor(List<String> remarkTypes,JTextFieldValidate txtAddress){
		//判断是否为空
		if(CollectionUtils.isNotEmpty(remarkTypes)){
			//遍历备注类型
			for (String remark : remarkTypes) {
				//禁行区域：红色
				if(GisConstants.SPECIAL_ADDRESS_FORBID.equals(remark)){
					txtAddress.setForeground(Color.RED);
				}
				//进仓区域：黄色
				else if(GisConstants.SPECIAL_ADDRESS_ENTER.equals(remark)){
					txtAddress.setForeground(Color.YELLOW);
				}
				//禁行和进仓区域：橙色
				else if(GisConstants.SPECIAL_ADDRESS_ENTER.equals(remark) && GisConstants.SPECIAL_ADDRESS_FORBID.equals(remark)){
					txtAddress.setForeground(Color.ORANGE);
				}
				//其它：黑色
				else{
					txtAddress.setForeground(Color.BLACK);
				}
			}
		}else{
			//将已设置的颜色重新设置回来
			txtAddress.setForeground(Color.BLACK);
		}
	}
	
	/**
	 * 组装到达网点匹配的查询条件
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午2:37:06
	 */
	private GisPickupOrgDto gainGisPickupOrgDto(WaybillInfoVo bean, WaybillRFCUI ui){
		// 定义查询参数
		GisPickupOrgDto dto = new GisPickupOrgDto();
		// 运单号
		dto.setAppNum(bean.getWaybillNo());

		// 获得地址栏VO
		AddressFieldDto address = bu.getProvCityCounty(bean.getReceiveCustomerProvCode(), bean.getReceiveCustomerCityCode(), bean.getReceiveCustomerDistCode());

		// 判断省市区地址对象是否为空
		if (null == address) {
			return null;
		}

		// 省份
		dto.setProvince(address.getProvinceName());
		// 城市
		dto.setCity(address.getCityName());
		// 区县
		dto.setCounty(address.getCountyName());

		// 其它地址
		dto.setOtherAddress(bean.getReceiveCustomerAddress());
		// 汽运类型
		dto.setTransportway(CommonUtils.covertProductToGisType(bean.getProductCode().getCode()));
		
		//提货方式
		String receiveMethod = CommonUtils.covertReceiveMethod(bean.getReceiveMethod().getValueCode());
		//gis类型
		String gisType = receiveMethod;
		//判断是快递的自提还是送货
		if(CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode())){
			if(GisConstants.GIS_MATCH_PICKUP.equals(receiveMethod)){
				gisType = GisConstants.GIS_MATCH_PICKUP_EXPRESS;
			}
			else if(GisConstants.GIS_MATCH_DELIVER.equals(receiveMethod)){
				gisType = GisConstants.GIS_MATCH_DELIVER_EXPRESS;
			}
		}
		// 提货方式
		dto.setMatchtypes(gisType);
		// 收货人电话
		dto.setTel(bean.getReceiveCustomerPhone());
		// 收货人手机
		dto.setPhone(bean.getReceiveCustomerMobilephone());

		return dto;
	}
	
	/**
	 * 根据标杆编码查询提货网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午9:24:18
	 */
	private void setPickupOrgData(WaybillRFCUI ui,WaybillInfoVo bean, List<BranchVo> depts){
		// 判断返回的标杆编码集合是否为空
		if (CollectionUtils.isNotEmpty(depts)) {
			// 显示网点对象信息
			showPickupStationDialog(bean, depts);
			ShowPickupStationDialogAction action = new ShowPickupStationDialogAction();
			// 将该UI设置到ShowPickupStationDialogAction类中
			action.setInjectUI(ui);
			// 设置线路信息
			action.setLoadLine(bean);
//			action.setAirDeptEnabled(bean);
		} else {
			// 不做业务处理
		}
	}

	/**
	 * 
	 * 预付金额事件监听
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:10:48
	 * @param bean
	 */
	private void prePayAmountListener(WaybillInfoVo bean){
		//手动修改预付金额，需要自动对到付金额进行计算
		setToPayAmount(bean);
	}
	
	/**
	 * 
	 * 一半先付一半到付，根据输入的预付金额计算出到付金额
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:13:40
	 * @param bean
	 */
	private void setToPayAmount(WaybillInfoVo bean)
	{
		//预付金额
		BigDecimal prePayAmount = bean.getPrePayAmount();
		//到付金额
		BigDecimal toPayAmount = BigDecimal.ZERO;
		//总金额
		BigDecimal totalAmount = bean.getTotalFee();
		//代收货款
		BigDecimal codAmount = bean.getCodAmount();
		//计算除去代收货款的总金额
		totalAmount = totalAmount.subtract(codAmount);
		if(prePayAmount.compareTo(BigDecimal.ZERO) == 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.zeroPrePayAmount"));
			ui.billingPayPanel.getTxtCashpayment().requestFocus();
			//将暂存提交按钮设置为不可编辑
			setSaveAndSubmitFalse(ui);
		}
		else if(prePayAmount.compareTo(totalAmount) > 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.overPrePayAmount"));
			ui.billingPayPanel.getTxtCashpayment().requestFocus();
			//将暂存提交按钮设置为不可编辑
			setSaveAndSubmitFalse(ui);
		}else
		{
			//到付金额 = 总金额 - 预付金额
			toPayAmount = totalAmount.subtract(prePayAmount);
			validateTotalAmount(prePayAmount,toPayAmount,totalAmount);
			//实际到付金额 = 到付金额+代收货款
			toPayAmount = toPayAmount.add(codAmount);
			bean.setToPayAmount(toPayAmount);
		}
	}
	
	/**
	 * 
	 * 验证预付金额+到付金额是否=总金额
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午02:15:13
	 */
	private void validateTotalAmount(BigDecimal prePayAmount,BigDecimal toPayAmount,BigDecimal totalAmount)
	{
		if(prePayAmount.add(toPayAmount).compareTo(totalAmount) != 0)
		{
			setSaveAndSubmitFalse(ui);
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.exception.errorTotalAmount"));
		}
	}
	
	/**
	 * 
	 * 到付金额事件监听
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:10:48
	 * @param bean
	 */
	private void toPayAmountListener(WaybillInfoVo bean){
		//一半先付一半到付，根据输入的到付金额计算出预付金额
		setPrePayAmount(bean);
	}
	
	/**
	 * 
	 * 一半先付一半到付，根据输入的到付金额计算出预付金额
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:13:40
	 * @param bean
	 */
	private void setPrePayAmount(WaybillInfoVo bean)
	{
		//预付金额
		BigDecimal toPayAmount = bean.getToPayAmount();
		//到付金额
		BigDecimal prePayAmount = BigDecimal.ZERO;
		//总金额
		BigDecimal totalAmount = bean.getTotalFee();
		//代收货款
		BigDecimal codAmount = bean.getCodAmount();
		if(toPayAmount.compareTo(codAmount) < 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.lessToPayAmount"));
			ui.billingPayPanel.getTxtArrivePayment().requestFocus();
			//将暂存提交按钮设置为不可编辑
			setSaveAndSubmitFalse(ui);
		}else if(toPayAmount.compareTo(totalAmount) > 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.overToPayAmount"));
			ui.billingPayPanel.getTxtArrivePayment().requestFocus();
			//将暂存提交按钮设置为不可编辑
			setSaveAndSubmitFalse(ui);
		}else
		{
			//到付金额 = 总金额 - 预付金额
			prePayAmount = totalAmount.subtract(toPayAmount);
			validateTotalAmount(prePayAmount,toPayAmount,totalAmount);
			bean.setPrePayAmount(prePayAmount);
		}
	}

	@Override
	public boolean isFromVoTargetEnable() {
		return false;
	}
	
	
	/**
	 * 把三级产品类型转换成始发和到达线路的运输类型(汽运，空运) :
	 * 1、精准卡航、 精准城运 精准汽运（长途）、 精准汽运（短途）、 汽运偏线  => GIS的汽运
	 * 2、精准空运 => GIS的空运
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午5:11:35
	 */
	public static String covertProductToGisType(String productCode) {
		// 根据三级产品判断是否是汽运
		if (StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, productCode)) {
			return GisConstants.GIS_TRANS_HIGHWAYS;
		}
		// 根据三级产品判断是否是空运
		if (StringUtils.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, productCode)) {
			return GisConstants.GIS_TRANS_AIR;
		}
		
		return null;
	}
	
	/**
	 * 1、自提、空运自提、空运免费自提、机场自提 ==> GIS自提
	 * 2、免费派送、送货进仓、免费派送（上楼）==> GIS送货
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午5:41:55
	 */
	public static String covertReceiveMethod(String method){
		String rm = StringUtil.defaultIfNull(method);
		//根据提货方式判断是不是自提
		if(rm.toLowerCase().contains(GisConstants.GIS_MATCH_PICKUP.toLowerCase())){
			return GisConstants.GIS_MATCH_PICKUP;
		}
		
		//根据提货方式判断是不是送货
		if(rm.toLowerCase().contains(GisConstants.GIS_MATCH_DELIVER.toLowerCase())){
			return GisConstants.GIS_MATCH_DELIVER;
		}
		
		return null;
	}
	
	/**
	 * 
	 * <p>
	 * (是否上门接货事件监听)
	 * </p>
	 * 
	 * @author WangQianJin
	 * @date 2013-04-16
	 * @see
	 */
	private void pickupToDoorListener(WaybillPanelVo bean) {
		boolean bool = ui.getWaybillInfoPanel().getBasicPanel().getCboReceiveModel().isSelected();
		if (bool) {
			// 接货费输入框
			ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEditable(false);
		} else {
			// 接货费
			bean.setPickupFee(BigDecimal.ZERO);
			// 重新计算运费
			CalculateFeeTotalUtils.resetCalculateFee(bean);
		}
		//修改完是否上门接货不能立即提交需要再次计算运费
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);
	}
	
	//liding comment for NCI Project
	/**
	 * 该方法验证若是银行卡付款，则交易流水号是否可编辑
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23上午08:13
	 */
//	private void whetherBankCardPayment(WaybillInfoVo bean) {
//		// TODO Auto-generated method stub
//		if(bean.getPaidMethod()!=null&&WaybillConstants.CREDIT_CARD_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(true);
//		}else{
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(false);
//			bean.setTransactionSerialNumber(null);
//		}
//	}
	//liding comment for NCI Project
	/**
	 * 对交易流水号进行监控
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23
	 */
//	private void verificate(String transactionSerialNumber) {
//		// TODO Auto-generated method stub
//		if(StringUtils.isEmpty(transactionSerialNumber)){
//			MsgBox.showInfo(i18n.get("foss.gui.changingexp.listener.Waybill.transactionSerialNumber"));
//		}
//	}
}