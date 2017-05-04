/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.PreferentialService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.CustomerImpOperLogHandler;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.validation.descriptor.WaybillDescriptor;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpWaybillDtoFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpConcessionsPanel.WaybillDiscountCanvas;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */

public class ExpCalculateAction extends
		AbstractButtonActionListener<ExpWaybillEditUI> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(ExpCalculateAction.class);

	
	private static final String TYPE_CODE = "YHJ";

	// 重量
	private static final double FIFTY = 50;

	// 重量上限
	private static final double WEIGHT_UPPER_LIMIT = 50;

	// 体积上限
	private static final double VOLUME_UPPER_LIMIT = 0.3;

	// 日志对象
	protected final static Logger LOG = LoggerFactory
			.getLogger(AbstractButtonActionListener.class.getName());
	
	private Logger logger  = LoggerFactory.getLogger(this.getClass());
	//纯运费
	private static  BigDecimal PURE_FREIGHT = BigDecimal.ZERO;
	/**
	 * 参数配置
	 */
	private IConfigurationParamsService configurationParamsService = GuiceContextFactroy
			.getInjector().getInstance(ConfigurationParamsService.class);

	private IPreferentialService preferentialService = GuiceContextFactroy
			.getInjector().getInstance(PreferentialService.class);

	/**
	 * 运单基础数据服务
	 */
	private static IBaseDataService baseDataService = GuiceContextFactroy
			.getInjector().getInstance(BaseDataService.class);

	// 初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	/**
	 * 订单处理service
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;

	// 运单UI对象
	ExpWaybillEditUI ui;
	 /**
     * 小数点保留位数
     */
	private static int newScale = 2;

    private BigDecimal defaultFirstWeight = BigDecimal.valueOf(0.5);
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
		
		// 通过运单UI对象获取绑定的VO对象
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();
		
		// 校验发货联系人和收货联系人不能过长
		String deliveryCustomerContact = bean.getDeliveryCustomerContact();
		String receiveCustomerContact = bean.getReceiveCustomerContact();
		if (deliveryCustomerContact != null
				&& deliveryCustomerContact.length() >= NumberConstants.NUMBER_80) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.waybillDescriptor.deliveryCustomerContactTolong"));
			return;
		}
		if (receiveCustomerContact != null
				&& receiveCustomerContact.length() >= NumberConstants.NUMBER_80) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.waybillDescriptor.receiveCustomerContactTolong"));
			return;
		}

		if(CommonContents.logToggle){
			logger.info("运单号："+bean.getWaybillNo()+" 计算总运费开始...");
			CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).jszyfBtnStart();
		}
		
		// 在此处校验上门发货有无勾选上
		homeDelivery(bean);

		// 基本信息验证，如果存在一个不满足条件的情况，则不进行运费计算
		List<ValidationError> errorList = waybillBinder.validate();
		// 用来标记是否有异常
		Boolean bool = false;
		if (errorList != null) {
			bool = ExpCommon.validateDescriptor(errorList);
		}
		
		/**
         * 选择目的站为外发虚拟快递营业部且有代收货款时，校验代收货款是否超限制
         * 如果代收货款大于代收上限就给出提示
         * @author:280747-foss-zhuxue
		 * @date 2015-10-301下午04:35:17
         */
		if (bean.getCustomerPickupOrgCode() != null) {
			if (waybillService.querySaleDepartmentByCodeNoCache(bean
					.getCustomerPickupOrgCode().getCode()) != null) {
				// 通过提货网点返回含有代收货款上限的实体
				SaleDepartmentEntity IsaleDepartmentEntity = waybillService
						.querySaleDepartmentByCodeNoCache(bean
								.getCustomerPickupOrgCode().getCode());
				// 获取代收货款
				BigDecimal codAmount = bean.getCodAmount();
				// BigDecimal codeAmount=new BigDecimal();
				if (IsaleDepartmentEntity.getAgentCollectedUpperLimit() != null
						&& codAmount.compareTo(new BigDecimal(
								IsaleDepartmentEntity
										.getAgentCollectedUpperLimit())) > 0) {
					// 获取提货网店名称
					String loadName = bean.getCustomerPickupOrgName();
					if (bean.getCustomerPickupOrgName() != null
							|| "".equals(bean.getCustomerPickupOrgName())
							&& loadName.endsWith("远郊快递营业部")) {
						// 超过该营业部代收上限，请重新选择目的站.
						MsgBox.showInfo(
								i18n.get("foss.gui.creating.calculateAction.exception.validateCod.limit"));
					}
				}
			}
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
				//直营部门开单也需要初始化 2016年3月17日 11:51:00 葛亮亮
//				PtpWaybillOrgVo.init() ;
				
				// 基本校验
				validate(bean);
				boolean isAccurateCost=false;				
				if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					CustomerEntity custinfo =  waybillService.
							queryCustomerInfoByCusCode(bean.getDeliveryCustomerCode());
					if(custinfo!=null&&"Y".equalsIgnoreCase(custinfo.getIsAccuratePrice())){
						isAccurateCost = true;
					}					
				}
				bean.setAccurateCost(isAccurateCost);
				
				String returnType = null;
				if (bean.getReturnType() != null) {
					returnType = bean.getReturnType().getValueCode();
				}

				// 清理所有费用相关的信息
				cleanFee(bean);
				// 获取优惠活动信息
				// if(null!=ui.getBasicPanel().getCboSpecialOffer()&&null!=ui.getBasicPanel().getCboSpecialOffer().getModel()){
				// bean.setSpecialOffer((DataDictionaryValueVo)
				// ui.getBasicPanel().getCboSpecialOffer().getModel().getSelectedItem());
				// }
				// 判断是否内部带货:如果内部带货，不能计算优惠券
				if (!WaybillConstants.DEPPON_CUSTOMER.equals(bean
						.getDeliveryCustomerCode())
						&& !WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
								.equals(returnType)) {

					// 菜鸟新需求返货折扣
					if (null != bean.getOriginalWaybillNo()) {
						WaybillEntity entyty = waybillService
								.queryWaybillByNumber(bean
										.getOriginalWaybillNo());
					if( bean.getPaidMethod()!=null 
						  && StringUtils.isNotEmpty(bean.getReturnType().getValueCode())
						  && StringUtils.isNotEmpty(bean.getReceiveCustomerAddress())
						  && StringUtils.isNotEmpty(bean.getReceiveCustomerDistCode())
						  && StringUtils.isNotEmpty(bean.getReceiveCustomerCityCode())
						  && StringUtils.isNotEmpty(bean.getReceiveCustomerProvCode())
						  && StringUtils.isNotEmpty(bean.getPaidMethod().getValueCode())
						  && StringUtils.isNotEmpty(entyty.getDeliveryCustomerAddress())
						  && StringUtils.isNotEmpty(entyty.getDeliveryCustomerDistCode())
						  && StringUtils.isNotEmpty(entyty.getDeliveryCustomerCityCode())
						  && StringUtils.isNotEmpty(entyty.getDeliveryCustomerProvCode())
						  ){
						if (bean.getReturnType() != null
								&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
										.equals(bean.getReturnType()
												.getValueCode())
								&& bean.getReceiveCustomerAddress().equals(
										entyty.getDeliveryCustomerAddress())
								&& bean.getReceiveCustomerDistCode().equals(
										entyty.getDeliveryCustomerDistCode())
								&& bean.getReceiveCustomerCityCode().equals(
										entyty.getDeliveryCustomerCityCode())
								&& bean.getReceiveCustomerProvCode().equals(
										entyty.getDeliveryCustomerProvCode())
								&& bean.getPaidMethod().getValueCode()
										.equals(WaybillConstants.ARRIVE_PAYMENT)) {
				        	bean.setIsCainiao(true);
				        	bean.setReturnWaybillNo(bean.getOriginalWaybillNo());
							// 原单号发货客户
							bean.setOldreceiveCustomerCode(entyty
									.getDeliveryCustomerCode());
							// 原单号收货部门编码（原出发部门）
							bean.setOriginalReceiveOrgCode(entyty
									.getReceiveOrgCode());
				        	bean.setReturnTransportFee(entyty.getTransportFee());
				    		bean.setReturnInsuranceFee(entyty.getInsuranceFee());

						}
					}
					}

					// 计算各种费用
					calculateFee(bean, false);
					bean.setTempTransportFee(bean.getTransportFee());
					/**
					 * 如果有优惠券编号， 需要计算两次总运费：原因是， 优惠券要求把总运费传到CRM进行校验
					 */
					if (!StringUtils.isEmpty(bean.getPromotionsCode())) {
						/**
						 * 不是整车才处理优惠券，因为整车没有走货路径，获取最终配载部门时会报异常
						 */
						if (!bean.getIsWholeVehicle()) {
							/**
							 * 内部发货不使用优惠券 dp-foss-dongjialing 225131
							 */
							if (bean.getInternalDeliveryType() == null
									|| StringUtil.isBlank(bean
											.getInternalDeliveryType()
											.getValueCode())
									|| StringUtil.isBlank(bean.getEmployeeNo())) {
								// 处理优惠券
								executeCoupon(bean);
								// 设置未冲减优惠券的运费
								bean.setBeforeProTranFee(bean.getTransportFee());
							}
					}
					}
					/**
					 * 设置优惠总费用
					 */
					calcaulatePromotionsFee(bean);

					// 需要重新计算运费
					CalculateFeeTotalUtils.calculateTotalFee(bean, true);
				} else {
					// 计算内部带货公布价运费
					calculateInnerPickupTransFee(bean);
					// 内部带货金额清零
					ExpCommon.resetZero(bean, ui);

				}

				String receiveMethod = null;
				if (bean.getReceiveMethod() != null) {
					receiveMethod = bean.getReceiveMethod().getValueCode();
				}

				// 处理增值优惠券费用
				ExpCommon.offsetCouponFee(ui, bean);
				// 处理完优惠券清空优惠券费用，防止再次冲减
				CalculateFeeTotalUtils.cleanCouponFree(bean);

				if (bean.getTotalFee().compareTo(BigDecimal.ZERO) >= 0) {
					ExpCommon.setSaveAndSubmitTrue(ui);
				}

				// PDA导入开单时，只能提交，不能暂存
				if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
						.getWaybillstatus())) {
					// 不可编辑
					ui.buttonPanel.getBtnSave().setEnabled(false);
				}
				
			} catch (BusinessException w) {
				if (StringUtils.isNotEmpty(w.getMessage())) {
					MsgBox.showInfo(MessageI18nUtil.getMessage(w, i18n));
				} else if (StringUtils.isNotEmpty(w.getErrorCode())) {
				    MsgBox.showInfo(w.getErrorCode());
				}
			}
		}
		if (null != bean.getPartnerBilling() && bean.getPartnerBilling()) {
			if (bean.getPartnerBilling()) {
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEditable(true);
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEnabled(true);
			} else {
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEditable(false);
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEnabled(false);
			}
		}
		//页面其他费用
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> voList = model.getData();
		//保存计算前的费用
		CommoForFeeUtils.keepStandardExpFee(voList, bean);
		//如果是伙伴开单着走下面逻辑
		if(BZPartnersJudge.IS_PARTENER){			
			//合作人 项目 费用字段可编辑
			ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEditable(true);
	        ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEnabled(true);
		}
		if(CommonContents.logToggle){
			logger.info("运单号："+bean.getWaybillNo()+" 计算总运费结束...");
			CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).jszyfBtnEnd();
		}
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
		// 清理木架信息
		cleanStandCharge(bean);
		// 清理木箱信息
		cleanBoxCharge(bean);
		// 清理产品信息
		cleanProductPrice(bean);
		// 清理送货费
		// 快递送货费优化
		if (bean.getWaybillstatus() != null
				&& !WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
						.getWaybillstatus())) {
			cleanDeliverCharge(bean);
		}

		// 画布清理画布派送费
		ui.canvasContentPanel.getOtherCost().setChangeDetail(null);

		// 清理折扣优惠明细
		ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(null);
		
		//清理收到修改提交为0的情况
		cleanChangeVolume(bean);
		
		bean.setIsCainiao(false);
	}

	private void cleanChangeVolume(WaybillPanelVo bean) {
		ExpWaybillPanelVo expbean =(ExpWaybillPanelVo)bean;
		String originalWaybillNo= expbean.getOriginalWaybillNo();
		if(StringUtil.isNotBlank(originalWaybillNo)){
			WaybillExpressEntity exp =waybillService.getWaybillExpressByWaybillNo(originalWaybillNo);
			if(exp==null 
				  ||
			!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
					.equals(expbean.getReturnType()
							.getValueCode())
			      ||
			!FossConstants.ACTIVE.equals(exp.getChangeVolume())){
				bean.setChangeVolume("");
			} 
		}else{
			bean.setChangeVolume("");
		}
		
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
		// 是否手动修改过保费，若否则清空（参见DEFECT-543）
		if (!CommonUtils.defaultIfNull(bean.isHandInsuranceFee())
				.booleanValue()) {
			// 保险声明值最大值
			// bean.setMaxInsuranceAmount(BigDecimal.ZERO);
			// 保险费率
			bean.setInsuranceRate(BigDecimal.ZERO);
			// 保险手续费
			bean.setInsuranceFee(BigDecimal.ZERO);
			// 保险费ID
			bean.setInsuranceId("");
			// 保险费CODE
			bean.setInsuranceCode("");
		}
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
		bean.setStandCharge(BigDecimal.ZERO);
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
		bean.setBoxCharge(BigDecimal.ZERO);
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
		// 画布-送货费
		bean.setDeliveryGoodsFeeCanvas("0");
		// 送货费
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		// 送货费集合
		bean.setDeliverList(null);
	}

	/**
	 * 
	 * 重新设置送货费
	 * 
	 * @author WangQianJin
	 * @date 2013-05-18
	 */
	private void againSetDeliverCharge(WaybillPanelVo bean,
			BigDecimal inputDeliveryGoodsFee) {
		if (inputDeliveryGoodsFee != null && bean.getDeliveryGoodsFee() != null) {
			if (inputDeliveryGoodsFee.doubleValue() > bean
					.getDeliveryGoodsFee().doubleValue()) {
				// 送货费
				bean.setDeliveryGoodsFee(inputDeliveryGoodsFee);
				// 画布-送货费
				bean.setDeliveryGoodsFeeCanvas(inputDeliveryGoodsFee + "");
			}
	}
	}

	/**
	 * 计算优惠总费用
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:09:42
	 */
	private void calcaulatePromotionsFee(ExpWaybillPanelVo bean) {
		JXTable tblConcessions = ui.getCanvasContentPanel()
				.getConcessionsPanel().getTblConcessions();
		WaybillDiscountCanvas discountTableModel = ((WaybillDiscountCanvas) tblConcessions
				.getModel());
		List<WaybillDiscountVo> waybillDiscountVos = discountTableModel
				.getData();

		/**
		 * 如果优惠价格为空或者为0时
		 */
		if (bean.getCouponFree() != null
				&& BigDecimal.ZERO.compareTo(bean.getCouponFree()) != 0) {
			if (waybillDiscountVos == null) {
				waybillDiscountVos = new ArrayList<WaybillDiscountVo>();

			}

			// 判断是否需要添加到优惠费用中
			if (CommonUtils.isAddPromotionsFeeByTypeExpress(bean
					.getCouponRankType())) {
				/**
				 * MANA-1961 营销活动与优惠券编码关联 2014-04-10 026123
				 */
				WaybillDiscountVo waybillDiscountVo = new WaybillDiscountVo();
				// 优惠折扣项目名称
				waybillDiscountVo.setFavorableItemName(CommonUtils
						.convertFeeToName(bean.getCouponRankType()));
				// 优惠折扣项目CODE
				waybillDiscountVo.setFavorableItemCode(CommonUtils
						.defaultIfNull(bean.getCouponRankType()));
				// 优惠类别名称
				waybillDiscountVo.setFavorableTypeName(i18n
						.get("foss.gui.creating.calculateAction.coupon"));
				// 优惠类别CODE
				waybillDiscountVo.setFavorableTypeCode(TYPE_CODE);
				waybillDiscountVo.setFavorableDiscount(BigDecimal.ZERO
						.toString());
				waybillDiscountVo.setFavorableAmount(bean.getCouponFree()
						.toString());
				// 折扣ID
				waybillDiscountVo.setDiscountId(bean.getPromotionsCode());
				// 类型 discount 为公布价折扣 promotion 为增值服务优惠
				waybillDiscountVo
						.setFavorableTypeCode(PricingConstants.VALUATION_TYPE_DISCOUNT);

				waybillDiscountVos.add(waybillDiscountVo);
			}
		}
		// 优惠总费用
		BigDecimal totalPromotionsFee = BigDecimal.ZERO;
		// 是否享有推广活动折扣
		boolean flagActive = false;
		if (waybillDiscountVos != null) {
			for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
				totalPromotionsFee = totalPromotionsFee.add(new BigDecimal(
						waybillDiscountVo.getFavorableAmount()));
				// 是否活动折扣
				if (DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE
						.equals(waybillDiscountVo.getFavorableTypeCode())) {
					flagActive = true;
			}
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
		// 设置优惠面板
		ui.getCanvasContentPanel().getConcessionsPanel()
				.setChangeDetail(waybillDiscountVos);

		// 选择了营销活动但未享受，给予用户提示
		if (bean.getActiveInfo() != null
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())) {
			if (!flagActive) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.expcalculateAction.activeinfo.nohave.actdis"));
			}
		}
	}

	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、 送货费）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	private void calculateFee(WaybillPanelVo bean, boolean needMinusCoupen) {

		// 获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);
		// 是否伙伴开单
		if (null != bean.getPartnerBilling() && bean.getPartnerBilling()) {
			productPriceDtoCollection
					.setPartnerBillingLogo(FossConstants.ACTIVE);
		}
		// 快递优惠活动
		if (bean.getSpecialOffer() != null
				&& StringUtil.isNotEmpty(bean.getSpecialOffer().getValueCode())) {
			productPriceDtoCollection.setCityMarketCode(bean.getSpecialOffer()
					.getValueCode());
		}

		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection
				.getPriceEntities();

		// 打木架/大木箱 计算信息收集
		// List<GuiQueryBillCalculateSubDto> yokeChargeCollections =
		// ExpCommon.getYokeChargeCollection(bean, ui);
		// if (yokeChargeCollections != null &&
		// !yokeChargeCollections.isEmpty()) {
		// priceEntities.addAll(yokeChargeCollections);//
		// }

		// 获取保价费

		// if(bean.getInsuranceAmount()!=null &&
		// bean.getInsuranceAmount().doubleValue()>0){
		// if(bean.getInsuranceFee()==null ||
		// bean.getInsuranceFee().doubleValue()<=0){
				GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceCollection(bean);
				if (insuranceCollection != null) {
			priceEntities.add(insuranceCollection);// 加入增值服务
				}
		//	}

		// }

		// 代收货款手续费

		GuiQueryBillCalculateSubDto codCollection = getCodCollection(bean);
		if (codCollection != null) {
			priceEntities.add(codCollection);// 代收货款手续费
		}

		// 送货费
		// List<GuiQueryBillCalculateSubDto> deliveryFees =
		// getDeliveryFeeCollection(bean);
		//
		// if (deliveryFees != null && !deliveryFees.isEmpty()) {
		// priceEntities.addAll(deliveryFees);
		// }

		// 其他费用
		GuiQueryBillCalculateSubDto otherChargeDataCollection = getOtherChargeDataCollection(bean);
		if (otherChargeDataCollection != null) {
			priceEntities.add(otherChargeDataCollection);// 代收货款手续费
		}

		productPriceDtoCollection.setPriceEntities(priceEntities);
		// 最低一票
		BigDecimal minTransportFee = BigDecimal.ZERO;

		// 营销活动DTO
		productPriceDtoCollection.setActiveDto(bean.getActiveDto());

		// 是否计算市场营销折扣
		productPriceDtoCollection.setCalActiveDiscount(bean
				.isCalActiveDiscount());

		// 封装市场营销活动校验条件
		Common.settterActiveParamInfo(productPriceDtoCollection, bean);

		// 菜鸟新需求返货折扣
		productPriceDtoCollection.setIsCainiao(bean.getIsCainiao());
		productPriceDtoCollection.setReturnWaybillNo(bean.getReturnWaybillNo());
		productPriceDtoCollection.setOldreceiveCustomerCode(bean
				.getOldreceiveCustomerCode());
		productPriceDtoCollection.setReturnbilltime(bean.getReturnbilltime());
		productPriceDtoCollection.setReturnTransportFee(bean
				.getReturnTransportFee());
		productPriceDtoCollection.setReturnInsuranceFee(bean
				.getReturnInsuranceFee());
		productPriceDtoCollection.setOriginalReceiveOrgCode(bean
				.getOriginalReceiveOrgCode());
		// 统一返回的计价值
		// 内部员工折扣条件
		/**
		 * 根据条件查询当前月份的优惠总额
		 */
		BigDecimal amount = null;
		if (StringUtil.isNotBlank(bean.getEmployeeNo())) {
			amount = WaybillServiceFactory.getWaybillService()
					.queryDiscountFeeByEmployeeNo(bean.getEmployeeNo(),
							bean.getBillTime());
		}
		if (bean.getInternalDeliveryType() != null) {
			productPriceDtoCollection.setInternalDeliveryType(bean
					.getInternalDeliveryType().getValueCode());
		}
		productPriceDtoCollection.setEmployeeNo(bean.getEmployeeNo());
		productPriceDtoCollection.setAmount(amount);

		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = null;

		// 德邦客户 金额都是0 ADD 快递返货为公司原因的费用金额为0
		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean
				.getDeliveryCustomerCode())) {
			guiResultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>();
			GuiResultBillCalculateDto dto1 = new GuiResultBillCalculateDto();
			dto1.setActualFeeRate(BigDecimal.ZERO);
			dto1.setCaculateFee(BigDecimal.ZERO);
			dto1.setCaculateType("WEIGHT");
			dto1.setCentralizePickup(FossConstants.NO);
			dto1.setDiscountFee(BigDecimal.ZERO);
			dto1.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_FRT);

			guiResultBillCalculateDtos.add(dto1);

			GuiResultBillCalculateDto dto2 = new GuiResultBillCalculateDto();
			dto2.setActualFeeRate(BigDecimal.ZERO);
			dto2.setCaculateFee(BigDecimal.ZERO);
			dto2.setCaculateType("ORIGINALCOST");
			dto2.setCentralizePickup(FossConstants.NO);
			dto2.setDiscountFee(BigDecimal.ZERO);
			dto2.setFee(BigDecimal.ZERO);
			dto2.setFeeRate(BigDecimal.ZERO);
			dto2.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
			dto2.setLightFeeRate(BigDecimal.ZERO);
			dto2.setMaxFee(BigDecimal.ZERO);
			dto2.setMinFee(BigDecimal.ZERO);
			dto2.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_BF);
			guiResultBillCalculateDtos.add(dto2);

			GuiResultBillCalculateDto dto3 = new GuiResultBillCalculateDto();
			dto3.setActualFeeRate(BigDecimal.ZERO);
			dto3.setCaculateFee(BigDecimal.ZERO);
			dto3.setCaculateType("ORIGINALCOST");
			dto3.setCentralizePickup(FossConstants.NO);
			dto3.setDiscountFee(BigDecimal.ZERO);
			dto3.setFee(BigDecimal.ZERO);
			dto3.setFeeRate(BigDecimal.ZERO);
			dto3.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
			dto3.setLightFeeRate(BigDecimal.ZERO);
			dto3.setMaxFee(BigDecimal.ZERO);
			dto3.setMinFee(BigDecimal.ZERO);
			dto3.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_QT);
			dto3.setPriceEntryName("其他费用");
			dto3.setSubType(PriceEntityConstants.PRICING_CODE_KDBZF);
			dto3.setPriceEntryName("快递包装费");
			guiResultBillCalculateDtos.add(dto3);
		} else {
			guiResultBillCalculateDtos = waybillService
					.queryGuiExpressBillPrice(productPriceDtoCollection);
		}
        if(guiResultBillCalculateDtos!=null&&guiResultBillCalculateDtos.size()!=0){
        	for(GuiResultBillCalculateDto gto:guiResultBillCalculateDtos){
        		if(PriceEntityConstants.PRICING_CODE_FRT.equals(gto.getPriceEntryCode())){
        			PURE_FREIGHT = gto.getPurefreight();
        		}
        	}
        }
		// 设置计价信息
		if (bean.getActiveInfo() != null
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())) {
			bean.setGuiResultBillCalculateDtos(guiResultBillCalculateDtos);
		}

		// 推广活动会对其他费用打折，重新在其他费用面板赋值
		setterOtherFeeByMakActive(productPriceDtoCollection,
				guiResultBillCalculateDtos, bean);

		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos2 = null;
		// 小件 返单计算两次 第二次首重 把总费用加进去
		//签收单返单 需求 要把费用添加到增加费用中，不要添加到公布价运费中
		if (bean.getReturnBillType() != null
				&& WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
						.getReturnBillType().getValueCode())
				&& !WaybillConstants.DEPPON_CUSTOMER.equals(bean
						.getDeliveryCustomerCode())) {

			GuiQueryBillCalculateDto dto2 = new GuiQueryBillCalculateDto();
			try {
				PropertyUtils.copyProperties(dto2, productPriceDtoCollection);
			} catch (Exception e) {

			}

			List<GuiQueryBillCalculateSubDto> priceEntities2 = new ArrayList<GuiQueryBillCalculateSubDto>();

			for (GuiQueryBillCalculateSubDto d : priceEntities) {
				if (PriceEntityConstants.PRICING_CODE_FRT.equals(d
						.getPriceEntityCode())) {
					GuiQueryBillCalculateSubDto d2 = new GuiQueryBillCalculateSubDto();
					try {
						PropertyUtils.copyProperties(d2, d);
					} catch (Exception e) {

					}
					priceEntities2.add(d2);
				}

			}
			dto2.setPriceEntities(priceEntities2);
			dto2.setWeight(defaultFirstWeight);
			dto2.setVolume(BigDecimal.ZERO);
			dto2.setCustomerCode("");
			dto2.setIsSelfPickUp(FossConstants.NO);
			// 根据DMANA-4938修改，标准快递和3.60特惠件以及商务专递的原件签单返回计费统一按照标准快递首重收费
			if (ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(dto2.getProductCode()) ||
					WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(dto2.getProductCode())	) {
				dto2.setProductCode(ExpWaybillConstants.PACKAGE);
			}
			guiResultBillCalculateDtos2 = waybillService
					.queryGuiExpressBillPrice(dto2);

			if (guiResultBillCalculateDtos2 != null
					&& guiResultBillCalculateDtos2.size() > 0) {
				GuiResultBillCalculateDto qsDto = guiResultBillCalculateDtos2
						.get(0);
				if (qsDto != null && qsDto.getCaculateFee() != null) {
					List<ValueAddDto> list = waybillService
							.queryValueAddPriceList(ExpCommon
									.getQueryOtherChargeParam(bean, bean
											.getReturnBillType().getValueCode()));
					// 折扣优惠
					if (CollectionUtils.isNotEmpty(list)) {
						for (ValueAddDto addDto : list) {
							if (PriceEntityConstants.PRICING_CODE_QS
									.equals(addDto.getPriceEntityCode())) {
								// 优惠方案
								List<PriceDiscountDto> disDto = addDto
										.getDiscountPrograms();
								if (CollectionUtils.isNotEmpty(disDto)) {
									PriceDiscountDto dto = disDto.get(0);
									// z折扣费率
									BigDecimal discountRate = dto
											.getDiscountRate();
									if (discountRate != null) {
										// 折扣后费用
										BigDecimal fee = qsDto.getCaculateFee()
												.multiply(discountRate);
										BigDecimal reduceFee = qsDto
												.getCaculateFee().subtract(fee);
										dto.setReduceFee(reduceFee);
										qsDto.setCaculateFee(fee.setScale(0,
												BigDecimal.ROUND_HALF_UP));
									}
								}
								ExpCommon.setReturnBillDiscount(
										addDto.getDiscountPrograms(), ui, bean);
								break;
							}

						}
					}
				}
			}
		}

		// 如果返回的价格为空，抛出业务异常
		if (guiResultBillCalculateDtos == null
				|| guiResultBillCalculateDtos.isEmpty()) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		}

		// 是否整车
		if (!bean.getIsWholeVehicle()) {
			// 获取公布价运费
			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(
					guiResultBillCalculateDtos,
					PriceEntityConstants.PRICING_CODE_FRT, null);

			GuiResultBillCalculateDto dtoJH = getGuiResultBillCalculateDto(
					guiResultBillCalculateDtos,
					PriceEntityConstants.PRICING_CODE_JH, null);
			//原来的逻辑 把签收单返单的费用加入公布价中 
			
			if (guiResultBillCalculateDtos2 != null
					&& guiResultBillCalculateDtos2.size() > 0) {
				GuiResultBillCalculateDto dto2 = guiResultBillCalculateDtos2
						.get(0);
				if (dto2 != null && dto2.getCaculateFee() != null) {
					dto.setCaculateFee(dto.getCaculateFee().add(
							dto2.getCaculateFee()));
				}
			}

			// 返单开单公布价为0
			ExpWaybillPanelVo vo2 = (ExpWaybillPanelVo) bean;
			if (vo2.getReturnType() != null
					&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
							.equals(vo2.getReturnType().getValueCode())
							) {
				dto.setCaculateFee(BigDecimal.ZERO);
			}
			
			/**
			 * PAD快递返货开单FOSS补录，如果上报原因为我司原因不计算公布价运费
			 */
			if(!WaybillConstants.COMPANY_REASON.equals(bean.getReturnBillReason()) && StringUtils.isNotEmpty(vo2.getOriginalWaybillNo()) && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(vo2.getReturnType().getValueCode())){
				ReturnGoodsRequestEntity e = waybillService.queryReturnGoodsRequestEntityByWayBillNo(vo2.getOriginalWaybillNo());
				if(e != null && WaybillConstants.RETURNREASON_COMPANY_REASON.equals(e.getReturnReason())){
					// 公布价运费设为0
					dto.setCaculateFee(BigDecimal.ZERO);
				}
			}

			// 设置公布价运费的费用
			setProductPriceDtoCollection(dto, bean, dtoJH);
			minTransportFee = dto.getMinFee();// 最低一票
			bean.setMinTransportFee(minTransportFee);
		} else {
			bean.setTransportFee(bean.getWholeVehicleActualfee());
			if (bean.getWholeVehicleActualfee() != null) {
				bean.setTransportFeeCanvas(bean.getWholeVehicleActualfee()
						.toString());
		}
		}

		/**
		 * 新添加 (gyk) 若单票包装费字段不为空，计算运费时从CRM中直接读取CRM中的单票包装费的值，且包装费字段置灰，不可修改。
		 */
		PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(bean);
		if (preferentialInfoDto != null) {
			if(preferentialInfoDto.getSinTicketPackCharge() != null) {
					bean.setPackageFee(preferentialInfoDto.getSinTicketPackCharge());
					ui.getBillingPayPanel().getTxtPackCharge().setEnabled(false);
				}
		}

		// 计算增值服务费
		// 返单开单增值服务为0
		ExpWaybillPanelVo vo2 = (ExpWaybillPanelVo) bean;
		if (vo2.getReturnType() != null
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
						.equals(vo2.getReturnType().getValueCode())
						) {

		} else {
			calculateValueAdd(bean, guiResultBillCalculateDtos);
		}
		/**
		 * 计算总运费公共方法
		 */
		ExpCommon.getYokeCharge(bean, ui);
		CalculateFeeTotalUtils.calculateFee(bean, false, needMinusCoupen);
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod()
				.getValueCode())) {
			isBeBebt(bean);// 判断是否可以开临时欠款
		}
		// 设置预付到付金额编辑状态
		setPrePayArriveEditState(bean);

	}

	/**
	 * gyk 调用crm接口查询客户合同信息 同时验证客户是否是部门绑定客户
	 * 
	 * @param bean
	 * @return
	 */
	private PreferentialInfoDto queryPreferentialInfo(WaybillPanelVo bean) {
		String productCodeTemp = ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		PreferentialInfoDto preferentialInfoDto = BaseDataServiceFactory
				.getBaseDataService().queryPreferentialInfo(
						bean.getDeliveryCustomerCode(), null, productCodeTemp);
		boolean isDiscount = false;
		if (preferentialInfoDto != null) {
			String unicode = baseDataService.queryUnifiedCodeByCode(bean
					.getReceiveOrgCode());
			List<String> list = new ArrayList<String>();
			if (unicode != null) {
				list.add(unicode);
			}
			// 验证客户是否是部门绑定客户
			List<BargainAppOrgEntity> bargainAppOrgEntities = baseDataService
					.queryAppOrgByBargainCrmId(
							preferentialInfoDto.getCusBargainId(), list);
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
				for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
					BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
							.get(i);
					String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = baseDataService
							.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
					if (orgAdministrativeInfoEntity == null) {
						continue;
					}
					String orgCode = orgAdministrativeInfoEntity.getCode();
					if (StringUtils.equals(bean.getReceiveOrgCode(), orgCode)) {
						isDiscount = true;
					}
				}
			}
		}
		if (isDiscount) {
			return preferentialInfoDto;
		} else {
			return null;
		}
	}

	/**
	 * 推广活动会对其他费用打折，重新在其他费用面板赋值
	 * 
	 * @创建时间 2014-5-17 上午10:51:57
	 * @创建人： WangQianJin
	 */
	private void setterOtherFeeByMakActive(
			GuiQueryBillCalculateDto productPriceDtoCollection,
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,
			WaybillPanelVo bean) {
		// 判断是否参加了推广活动
		if (bean.getActiveInfo() != null
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())) {
			// 推广活动折扣后的综合信息费
			BigDecimal zhxxDis = null;
			// 推广活动折扣后的快递包装费
			BigDecimal kdbzDis = null;
			// 综合信息费
			for (GuiResultBillCalculateDto guiResultBillCalculateDto : guiResultBillCalculateDtos) {
				if (PriceEntityConstants.PRICING_CODE_QT
						.equals(guiResultBillCalculateDto.getPriceEntryCode())
						&& (PriceEntityConstants.PRICING_CODE_ZHXX
								.equals(guiResultBillCalculateDto.getSubType()) || PriceEntityConstants.PRICING_CODE_KDBZF
								.equals(guiResultBillCalculateDto.getSubType()))) {
					// 获取折扣信息
					List<GuiResultDiscountDto> disList = guiResultBillCalculateDto
							.getDiscountPrograms();
					if (CollectionUtils.isNotEmpty(disList)) {
						for (GuiResultDiscountDto dto : disList) {
							if (dto != null
									&& DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE
											.equals(dto.getDiscountType())) {
								if (PriceEntityConstants.PRICING_CODE_ZHXX
										.equals(guiResultBillCalculateDto
												.getSubType())) {
									// 获取折扣后的综合信息费四舍五入取整
									zhxxDis = guiResultBillCalculateDto
											.getCaculateFee().setScale(0,
													BigDecimal.ROUND_HALF_UP);
								}
								if (PriceEntityConstants.PRICING_CODE_KDBZF
										.equals(guiResultBillCalculateDto
												.getSubType())) {
									// 获取折扣后的快递包装费四舍五入取整
									kdbzDis = guiResultBillCalculateDto
											.getCaculateFee().setScale(0,
													BigDecimal.ROUND_HALF_UP);
								}
						}
					}
				}

				}
			}
			// 获取其他费用,累加综合信息费与快递包装费
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable
					.getModel();
			List<OtherChargeVo> data = model.getData();
			if (CollectionUtils.isNotEmpty(data)) {
				boolean flag = false;
				for (OtherChargeVo vo : data) {
					if (PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo
							.getCode()) && zhxxDis != null) {
						vo.setMoney(zhxxDis.toString());
						flag = true;
					}
					if (PriceEntityConstants.PRICING_CODE_KDBZF.equals(vo
							.getCode()) && kdbzDis != null) {
						vo.setMoney(kdbzDis.toString());
						flag = true;
					}
				}
				if (flag) {
					// 重新获取其他费用
					CommoForFeeUtils.otherPanelSumFee(data, bean);
		}
	}
		}
	}

	/**
	 * 
	 * 计算内部带货公布价运费
	 * 
	*/
	private void calculateInnerPickupTransFee(ExpWaybillPanelVo bean) {
		// 需要直接把费用写进bean里
		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean
				.getDeliveryCustomerCode())) {

			// 获取产品价格主参数
			GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);

			List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection
					.getPriceEntities();

			// 获取GUI价格
			productPriceDtoCollection.setPriceEntities(priceEntities);

			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = null;

			guiResultBillCalculateDtos = waybillService
					.queryGuiExpressBillPrice(productPriceDtoCollection);

			// 如果返回的价格为空，抛出业务异常
			if (guiResultBillCalculateDtos == null
					|| guiResultBillCalculateDtos.isEmpty()) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
			}

			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(
					guiResultBillCalculateDtos,
					PriceEntityConstants.PRICING_CODE_FRT, null);
			GuiResultBillCalculateDto dtoJH = getGuiResultBillCalculateDto(
					guiResultBillCalculateDtos,
					PriceEntityConstants.PRICING_CODE_JH, null);

			// 设置公布价运费的费用
			setProductPriceDtoCollection(dto, bean, dtoJH);

			// 设置最低一票
			BigDecimal minTransportFee = BigDecimal.ZERO;
			minTransportFee = dto.getMinFee();
			bean.setMinTransportFee(minTransportFee);

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
		 * 9. 2012年12月1日 (以后)开业的部门不能开装卸费 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		 * 11.是否可以开装卸费的依据取决于部门的业务属性（ 即部门属性基础资料中增加是否可开装卸费的字段）。
		 * 12.装卸费上限由增值服务配置基础资料实现 （在产品API中体现）。
		 */
		boolean serviceChargeEnabled = true;

		// serviceChargeEnabled = departPropertyServiceFee(bean);
		//
		// // 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
		// if( serviceChargeEnabled) {
		// serviceChargeEnabled = lowestServiceFee(bean);
		// }
		// // 2012年12月1日 (以后)开业的部门不能开装卸费
		// // 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		// // 月结
		// if (serviceChargeEnabled) {
		// serviceChargeEnabled = channelServiceFee(bean);
		// }
		// if (serviceChargeEnabled) {
		// // 月发月送允许修改装卸费
		// if (StringUtils.isNotEmpty(bean.getPreferentialType())) {
		// if (bean.getPreferentialType()
		// .equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
		// serviceChargeEnabled = true;
		// }
		//
		// }
		// }

		// 快递只允许对应部门的月结客户开装卸费
		CusBargainVo vo = new CusBargainVo();
		vo.setExpayway(WaybillConstants.MONTH_END);
		vo.setCustomerCode(bean.getDeliveryCustomerCode());
		vo.setBillDate(new Date());
		vo.setBillOrgCode(bean.getReceiveOrgCode());
		boolean  isOk = waybillService.isCanPaidMethodExp(vo);
		if (!isOk) {
			CustomerEntity custinfo =  waybillService.
					queryCustomerInfoByCusCode(bean.getDeliveryCustomerCode());
			if(custinfo!=null&&StringUtils.isNotBlank(custinfo.getSetProportion())){
				BigDecimal setProportion =  new BigDecimal(custinfo.getSetProportion());
				BigDecimal servicefee = bean.getTransportFee().multiply(setProportion)
						.setScale(0, BigDecimal.ROUND_DOWN);
				if(bean.getServiceFee()==null||bean.getServiceFee()==BigDecimal.ZERO){
					//加收方式
					if("CHARGETYPE".equals(custinfo.getExpHandChargeBusiType())){
						bean.setServiceFee(servicefee);
						bean.setServiceFeeCanvas(String.valueOf(servicefee));
						serviceChargeEnabled = true;
						//折让方式,数据不展示在前台界面 ，存在数据库另一个字段
					}else if("DISCOUNTTYPE".equals(custinfo.getExpHandChargeBusiType())){
						servicefee = bean.getTransportFee().multiply(setProportion)
								.setScale(1, BigDecimal.ROUND_DOWN);
						bean.setServiceFee(BigDecimal.ZERO);
						bean.setServiceFeeCanvas(String.valueOf("0"));
						bean.setDcServicefee(bean.getTransportFee().multiply(setProportion)
								.setScale(1, BigDecimal.ROUND_DOWN));
						//装卸费需要存到数据库,给结算用
			serviceChargeEnabled = false;
					}
				}
			}else{
				if(bean.getServiceFee()==null||bean.getServiceFee()==BigDecimal.ZERO){
			bean.setServiceFee(BigDecimal.ZERO);
				bean.setServiceFeeCanvas(String.valueOf("0"));
				}
				serviceChargeEnabled = false;
			}
		} else {
			//根据客户编码查询到CRM中配置的客户装卸费业务方式和比例值
			CustomerEntity custinfo =  waybillService.
			queryCustomerInfoByCusCode(bean.getDeliveryCustomerCode());
			if(custinfo!=null){
			 if(StringUtils.isNotBlank(custinfo.getSetProportion())){
			//如果为加收方式，则自动计算装卸费，并展示在前台页面。若为折让方式自动计算不在前台展示
		    //加收方式
			if(StringUtils.isNotEmpty(custinfo.getSetProportion())&&
					custinfo.getExpHandChargeBusiType().equals("CHARGETYPE")){
				BigDecimal setProportion =  new BigDecimal(custinfo.getSetProportion());
				BigDecimal servicefee = bean.getTransportFee().multiply(setProportion).setScale(0, BigDecimal.ROUND_DOWN);
				if(bean.getServiceFee()==null||bean.getServiceFee()==BigDecimal.ZERO){
				bean.setServiceFee(servicefee);
				bean.setServiceFeeCanvas(String.valueOf(servicefee));
				}
			serviceChargeEnabled = true;
				//折让方式,数据不展示在前台界面 ，存在数据库另一个字段
			}else if(StringUtils.isNotBlank(custinfo.getSetProportion())&&
					custinfo.getExpHandChargeBusiType().equals("DISCOUNTTYPE")){
				BigDecimal setProportion =  new BigDecimal(custinfo.getSetProportion());
				BigDecimal servicefee = bean.getTransportFee().multiply(setProportion).
						setScale(1, BigDecimal.ROUND_DOWN);
				if(bean.getServiceFee()==null||bean.getServiceFee()==BigDecimal.ZERO){
				bean.setServiceFee(BigDecimal.ZERO);
				bean.setServiceFeeCanvas(String.valueOf("0"));
				//装卸费需要存到数据库,给结算用
				bean.setDcServicefee(bean.getTransportFee().multiply(setProportion)
						.setScale(1, BigDecimal.ROUND_DOWN));
		}
				serviceChargeEnabled = false;
			}
		  }else{
			  //如果crm中没有配置费率，若为月结客户，则读取foss中的费率手动计算，不做逻辑处理，设置为可编辑。
			  serviceChargeEnabled = true;
		  }
	    }
	}
		ui.incrementPanel.getTxtServiceCharge()
				.setEnabled(serviceChargeEnabled);
		return serviceChargeEnabled;
	}

	/**
	 * 否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
	 * 
	 * @param bean
	 * @return
	 */
	private boolean departPropertyServiceFee(WaybillPanelVo bean) {

		// 收货部门
		String orgCode = bean.getReceiveOrgCode();

		// org code is not null;
		if (StringUtils.isNotEmpty(orgCode)) {
			// 根据编码查询
			SaleDepartmentEntity entity = waybillService
					.querySaleDeptByCode(orgCode);
			if (entity != null) {
				// 不允许开装卸费
				if (!FossConstants.YES.equals(entity.getCanPayServiceFee())) {
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
		if (WaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(channel)
				|| WaybillConstants.CRM_ORDER_CHANNEL_ALIBABACXT
						.equals(channel)) {
			return false;
		}
		// 月结
		/*
		 * if (bean.getChargeMode() != null) { if (bean.getChargeMode()) {
		 * return false; } }
		 */

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
				bean.setServiceFee(BigDecimal.ZERO);
				serviceChargeEnabled = false;
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
		// //TEST
		// CouponInfoDto couponInfoDto=new CouponInfoDto();
		// bean.setCouponFree(new BigDecimal(100));
		// bean.setCouponInfoDto(couponInfoDto);
		// bean.setCouponRankType("HK");

		// 优惠卷是否为空
		CouponInfoDto couponInfoDto = getCouponInfoDto(bean);

		if (couponInfoDto != null) {
			CouponInfoResultDto dto = waybillService
					.validateCoupon(couponInfoDto);
			if (dto != null) {
				// 判断：该优惠券是否已被使用
				if (!dto.isCanUse()) {

					dto.getCanNotUseReason();
					// 您的优惠券已使用，不可重复使用！(waybill:110;value:50)
					// waybillNo 110
					// 50

					// 不能使用优惠券的原因
					// MsgBox.showInfo(dto.getCanNotUseReason());
					// bean.setPromotionsCode(null);
					// "您的优惠券已使用，不可重复使用！(waybill:9876543210;value:50)"
					String canNotUseReason = StringUtil.defaultIfNull(dto
							.getCanNotUseReason());
					String waybill = StringUtils.substringBetween(
							canNotUseReason, ":", ";");

					// 判断：该优惠券是否是被该运单使用的（从不可使用原因的字符串中截取使用该优惠券的运单号）
					if (!bean.getWaybillNo().equals(
							StringUtil.defaultIfNull(waybill).trim())) {
						// 不能使用优惠券的原因
						MsgBox.showInfo(canNotUseReason);
						bean.setPromotionsCode(null);
					} else {
						String lastAmount = StringUtils.substringAfterLast(
								canNotUseReason, "value:");
						lastAmount = StringUtils.substringBeforeLast(
								lastAmount, ")");
						lastAmount = StringUtils.substringBeforeLast(
								lastAmount, "}");

						/**
						 * 设置优惠券费用
						 */
						try {
							bean.setCouponFree(new BigDecimal(lastAmount));
						} catch (Exception e) {
							bean.setCouponFree(BigDecimal.ZERO);
						}
						/**
						 * 设置优惠券返回实体
						 */
						bean.setCouponInfoDto(couponInfoDto);

						/**
						 * 设置优惠券冲减类型 MANA-1961 营销活动与优惠券编码关联 2014-04-10 026123
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
						 * 设置优惠券冲减类型 MANA-1961 营销活动与优惠券编码关联 2014-04-10 026123
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
		if (bean.getTotalFee() != null
				&& bean.getTotalFee().compareTo(BigDecimal.ZERO) == 0) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.calculateAction.msgBox.totalFeeNullException"));
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

		OrgAdministrativeInfoEntity receiveOrgAdministrative = BaseDataServiceFactory
				.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(
						receiveOrgCode);

		if (receiveOrgAdministrative == null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.calculateAction.msgBox.nullReceiveOrgAdmin"));
			return null;
		}

		// 发货部门-标杆编码
		waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());

		if (bean.getLastLoadOrgCode() == null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.calculateAction.msgBox.nullLastLoadOrgCode"));
			return null;
		}
		// 最终配载部门-也就是最后一个自有网点
		String lastLoadOrgCode = bean.getLastLoadOrgCode();

		OrgAdministrativeInfoEntity lastLoadOrgAdministrative = BaseDataServiceFactory
				.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(
						lastLoadOrgCode);
		if (lastLoadOrgAdministrative == null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.calculateAction.msgBox.nullLastLoadOrgAdmin"));
			return null;
		}
		if (bean.getLoadOrgCode() == null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.calculateAction.msgBox.nullLoadOrgCode"));
			return null;
		}

		// 始发配载部门
		String firstLoadOutOrgInfoCode = BaseDataServiceFactory
				.getBaseDataService()
				.queryOrgAdministrativeInfoEntityByCode(bean.getLoadOrgCode())
				.getUnifiedCode();
		// 最终配载部门 标杆编码
		String lastLoadOutOrgInfoCode = null;
		if (!StringUtils.isEmpty(bean.getLastOutLoadOrgCode())) {
			// 获取最终配载部门 标杆编码
			lastLoadOutOrgInfoCode = BaseDataServiceFactory
					.getBaseDataService()
					.queryOrgAdministrativeInfoEntityByCode(
							bean.getLastOutLoadOrgCode()).getUnifiedCode();
		} else {

			MsgBox.showInfo(i18n
					.get("foss.gui.creating.calculateAction.msgBox.nullLastOutLoadOrgCode"));
			return null;
		}

		// 到达部门-标杆编码-由于偏线最后到达部门是代理，这里是最后一个只有部门
		waybillInfo.setArrivedDept(lastLoadOrgAdministrative.getUnifiedCode());

		// 发货部门所在外场
		waybillInfo.setLeaveOutDept(firstLoadOutOrgInfoCode);
		// 到达部门所在外场
		waybillInfo.setArrivedOutDept(lastLoadOutOrgInfoCode);

		WaybillOtherCharge model = (WaybillOtherCharge) ui.incrementPanel
				.getTblOther().getModel();
		// 获取费用明细
		List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = ExpWaybillDtoFactory
				.getWaybillChargeDtlEntity(model, bean);
		List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
		for (WaybillChargeDtlEntity waybillChargeDtlEntity : waybillChargeDtlEntitys) { // 计价明细
			AmountInfoDto amountInfo = new AmountInfoDto();

			if (PriceEntityConstants.PRICING_CODE_SH
					.equals(waybillChargeDtlEntity.getPricingEntryCode())
					|| PriceEntityConstants.PRICING_CODE_SHSL
							.equals(waybillChargeDtlEntity
									.getPricingEntryCode())
					|| PriceEntityConstants.PRICING_CODE_SHJC
							.equals(waybillChargeDtlEntity
									.getPricingEntryCode())) {
				// 计价条目编码-送货费
				amountInfo
						.setValuationCode(PriceEntityConstants.PRICING_CODE_SH);
				// 获取通过计算得到的送货费
				amountInfo.setValuationAmonut(bean
						.getCalculateDeliveryGoodsFee());
			} else {
				// 计价条目编码-保险费
				amountInfo.setValuationCode(waybillChargeDtlEntity
						.getPricingEntryCode());
				// 计价金额
				amountInfo.setValuationAmonut(waybillChargeDtlEntity
						.getAmount());
			}
			amountInfoList.add(amountInfo);
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
		// if
		// (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode()))
		// {
		// ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);
		// ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
		// } else {
		// ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(true);
		// ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
		// }

		ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);
		ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);

	}

	/**
	 * 
	 * 保价费用校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午11:09:49
	 */

	private void validate(ExpWaybillPanelVo bean) {
		// PDA运单信息
		validateWaybillNo(bean);

		// 整车约车校验
		validateVehicleNumber(bean);

		// 开单付款方式
		// ExpCommon.validateExpPayMethod(bean);

		// 重量、体积、件数校验
		validateWeightVolume(bean);

		// 目的站校验
		validateDistination(bean);

		// 产品校验
		validateProduct(bean);

		// 包装校验
		validatePack(bean);

		// 客户校验
		validateCustomer(bean);

		// 校验提货网点重量、体积上限
		validateVW(bean);

		// 付款方式校验
		validatePaymentMode(bean);

		// 验证返货开单
		validateReturn(bean);

		// 代收货款校验
		validateCod(bean);

		// 验证空运合票方式和航班类型不能为空
		validateAir(bean);

		// 验证空运货物类型不能为空
		validateAirGoodsType(bean);

		// 只允许合同客户才可以选择免费送货
		ExpCommon.validateDeliverFree(bean, ui);

		ExpCommon.validateExpPayMethod(bean);
		// 验证重量与提货方式
		validateWeightDeliveryMode(bean);

		// 检查试点城市和试点营业部的逻辑
		if (bean.getReturnType() != null
				&& (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
						.equals(bean.getReturnType().getValueCode())
						|| WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE
						.equals(bean.getReturnType().getValueCode()))) {
			// do noting
		} else {
			validateExpressCity(bean);
		}

		// 检查保险声明价值
		validateInsuranceFee(bean);

		// 检查德邦客户和发货人工号及内部带货费用承担部门
		validateDepponExpressEmpCode(bean);

		// 校验营销活动是否开启
		Common.validateActiveStart(bean);

		// 校验优惠券是否开启
		Common.validatePromotionsCode(bean);

		// 校验快递优惠活动与crm推广活动是否同时存在
		validateSpecialAndActive(bean);
		// 校验代收货款精确代收
		codAmountListener(bean);
		CommonUtils.validateLinkMan(bean);
		// 伙伴开单
		partnerBilling(bean);
		//验证货物尺寸
		if(StringUtil.isNotBlank(bean.getGoodsSize())){
			NumberValidate.checkIsGoodsSize(bean.getGoodsSize());
	}
		
		//校验单号
		valiteMes(bean);

	}

	private void partnerBilling(ExpWaybillPanelVo bean) {
		if(!BZPartnersJudge.IS_PARTENER){//合伙人项目
			if (null != bean.getPartnerBilling() && bean.getPartnerBilling()) {
				if (StringUtil.isEmpty(bean.getPartnerName())) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.listener.Waybill.validatePartnerName"));
				}
				if (StringUtil.isEmpty(bean.getPartnerPhone())) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.listener.Waybill.validatePartnerPhome"));
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
	private void codAmountListener(WaybillPanelVo bean) {
			if(!FossConstants.YES.equals(bean.getAccurateCollection())){//不可以代收货款
			if(bean.getCodAmount()!=null && bean.getCodAmount().toString().contains(".")){
				MsgBox.showInfo((i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.noSupportAccurate")));				bean.setCodAmount(BigDecimal.ZERO);
				bean.setCodAmountCanvas("0");
				bean.setRefundType(null);
				ExpCommon.setSaveAndSubmitFalse(ui);
				ui.getIncrementPanel().getTxtCashOnDelivery().requestFocus();
				return;
			}

		}
		if (bean.getCodAmount().compareTo(BigDecimal.ZERO) == 0) {
			ExpCommon.cleanCodInfo(ui, bean);
		} else {
			// 画布代收货款
			bean.setCodAmountCanvas(bean.getCodAmount().toString());
		}

		// 若为PDA补录，则更改代收货款后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ui.incrementPanel.getTxtCashOnDelivery().setForeground(Color.RED);
		}
		ExpCommon.getYokeCharge(bean, ui);
		CalculateFeeTotalUtils.calculateFee(bean);
		// 提交
		ui.buttonPanel.getBtnSubmit().setEnabled(false);
		// 提交
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
	}

	/**
	 * 校验快递优惠活动与crm推广活动是否同时存在
	 * 
	 * @创建时间 2014-5-21 下午12:31:16
	 * @创建人： WangQianJin
	 */
	private void validateSpecialAndActive(ExpWaybillPanelVo bean) {
		DataDictionaryValueVo specialOffer = bean.getSpecialOffer();
		DataDictionaryValueVo activeInfo = bean.getActiveInfo();
		if (specialOffer != null && activeInfo != null
				&& StringUtils.isNotEmpty(specialOffer.getValueCode())
				&& StringUtils.isNotEmpty(activeInfo.getValueCode())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.specialAndActive.isnotexist"));
		}
	}

	/**
	 * 检查德邦客户和发货人工号以及内部带货费用承担部门
	 */
	 private void validateDepponExpressEmpCode(ExpWaybillPanelVo bean) {

		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean
				.getDeliveryCustomerCode())) {
			if (StringUtils.isEmpty(bean.getDeliveryEmployeeNo())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateDeliveryEmployeeNo"));

			 }
		 }

		 validateDepponExpressFeeBurdenOrg(bean);
	}

	 /**
	  * 检查德邦客户内部带货费用承担部门
	  */
	 private void validateDepponExpressFeeBurdenOrg(ExpWaybillPanelVo bean) {

		if (WaybillConstants.DEPPON_CUSTOMER.equals(bean
				.getDeliveryCustomerCode())) {
			if (StringUtils.isBlank(bean.getInnerPickupFeeBurdenDept())) {
				// 内部带货费用承担部门不能为空
				throw new WaybillValidateException(
						i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.expInnerPickupFeeBurdenDept.nullError"));
			 }
		 }
	}

	/**
	 * add  zhangfeng  验证保价申明上限
	 * 
	 * @param bean
	 */
	public static void validateInsuranceFee(ExpWaybillPanelVo bean) {
		BigDecimal insuranceAmount = bean.getInsuranceAmount();
		if (insuranceAmount == null || insuranceAmount.doubleValue() <= 0) {
			if (bean.getInsuranceFee() != null
					&& bean.getInsuranceFee().doubleValue() > 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateInsurranceAmountFee"));
			}
		} else {
			/**
			 * add远郊保价上限
			 * 
			 * @author:270293-foss-zhangfeng
			 * @date:2015-07-06
			 */
				BigDecimal upAmount;
			if (bean.getVipInsuranceAmount() != null) {
				upAmount = bean.getVipInsuranceAmount();
				if (upAmount != null
						&& upAmount.doubleValue() > 0
						&& upAmount.doubleValue() < bean.getInsuranceAmount()
								.doubleValue()) {
					WaybillValidateException e = new WaybillValidateException(
							i18n.get("foss.gui.creating.common.exception.outInsuranceUp")
									+ upAmount
									+ i18n.get("foss.gui.creating.printUtil.chinese.yuan"));
					throw e;
				}
			} else {
				// 根据编码code综合取参数对象
				IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
						.getService(IWaybillHessianRemoting.class);
				ConfigurationParamsEntity configentity = waybillRemotingService
						.queryMaxInsuranceAmount(ExpWaybillConstants.EXPRESS_INSURRANCE_FEE_UP);
				String costomerPickupOrgName = bean.getCustomerPickupOrgName();
				if (null != costomerPickupOrgName
						&& (costomerPickupOrgName.indexOf("远郊") >= 0 || costomerPickupOrgName
								.indexOf("外发") >= 0)) {

					if (null != configentity
							&& bean.getInsuranceAmount().doubleValue() > Double
									.valueOf(configentity.getConfValue())) {
						WaybillValidateException e = new WaybillValidateException(
								i18n.get("foss.gui.creating.common.exception.outInsuranceUp")
										+ configentity.getConfValue()
										+ i18n.get("foss.gui.creating.printUtil.chinese.yuan"));
					throw e;
				}
				} else if (null != costomerPickupOrgName) {
					upAmount = bean.getMaxInsuranceAmount();
					if (bean.getInsuranceAmount().doubleValue()> Double.valueOf(upAmount.doubleValue())) {
						WaybillValidateException e = new WaybillValidateException(
								i18n.get("foss.gui.creating.common.exception.outInsuranceUp")
										+ upAmount
										+ i18n.get("foss.gui.creating.printUtil.chinese.yuan"));
				throw e;
			}
				} else if (null == costomerPickupOrgName) {

					WaybillValidateException e = new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullTargetOrgCode"));
					throw e;
	}
			}
		}
	}

	/**
     * 检查试点城市和试点营业部的逻辑
	 * 
	 * @param bean
	 */
	private void validateExpressCity(ExpWaybillPanelVo bean) {
		// 快递保费

		IBaseDataService baseDataService = GuiceContextFactroy.getInjector()
				.getInstance(BaseDataService.class);

		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		ConfigurationParamsEntity param6 = baseDataService.queryByConfCode(
				ExpWaybillConstants.EXPRESS_CODE_FEE_UP, user.getEmployee()
						.getDepartment().getCode());

		if (param6 != null) {
			bean.setCodAmountMax(new BigDecimal(param6.getConfValue()));
		}

		if (bean.getCodAmountMax() != null
				&& bean.getCodAmount() != null
				&& bean.getCodAmountMax().doubleValue() < bean.getCodAmount()
						.doubleValue()) {

			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateCodMax")
							+ bean.getCodAmountMax());

		}

		// bean.setOrderChannel(ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO);

		if (StringUtils.isEmpty(bean.getReceiveOrgCode())
				|| StringUtils.isEmpty(bean.getReceiveOrgName())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateReceive"));

		}

		if (bean.getReturnType() != null
				&& StringUtils.isNotEmpty(bean.getReturnType().getValueCode())) {
			if (StringUtil.isEmpty(bean.getOriginalWaybillNo())) {
				// 返单或者返货的情况下，必须填写原始单号
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateOriginalWaybillNo"));
			}
		}

		/**
		 * 试点到试点能开即日退和三日退， 试点到非试点、试点到快递代理、非试点到试点、 非试点到非试点只能开单三日退， 非试点到快递代理无代收业务。
		 */

		SalesDepartmentCityDto createDto = bean
				.getCreateSalesDepartmentCityDto();
		SalesDepartmentCityDto endDto =    bean.getTargetSalesDepartmentCityDto();
		if(endDto == null){
			throw new WaybillValidateException("目的站为空");
		}
		SaleDepartmentEntity org = waybillService.querySaleDeptByCode(endDto
				.getSalesDepartmentCode());
		SalesDepartmentCityDto dto  = new SalesDepartmentCityDto();
		dto.setSalesDepartmentCode(endDto.getSalesDepartmentCode());
		SalesDepartmentService salesDepartmentService = DownLoadDataServiceFactory
				.getSalesDepartmentService();
		SalesDepartmentCityDto result = salesDepartmentService
				.querySalesDepartmentCityInfo(dto);

		if (org != null && org != null) {
			// 营业部是否可以快递接货，如果是的话 就是试点营业部
			endDto.setTestSalesDepartment(StringUtil.defaultIfNull(org
					.getCanExpressPickupToDoor()));
			result.setTestSalesDepartment(StringUtil.defaultIfNull(org
					.getCanExpressPickupToDoor()));
			bean.setTargetSalesDepartmentCityDto(result);
			endDto = result;

		}

		if (createDto == null) {
			// 对不起，您所在的部门不能开快递运单
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateCreateDto"));
		} else if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& !FossConstants.YES
						.equals(createDto.getTestSalesDepartment())) {
			// 试点城市 非试点营业部不能开单
//			throw new WaybillValidateException(
//					i18n.get("foss.gui.creating.calculateAction.exception.validateCreateDto2"));
		}

		if (endDto == null) {
			// 请重新选择提货网点
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validateEndDto"));
		}

		// 到达和开始都是试点城市
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto
						.getCityType())) {
			DataDictionaryValueVo vo = bean.getRefundType();
			// if(vo!=null && StringUtils.isNotEmpty(vo.getValueCode()) &&
			// !WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo.getValueCode())){
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }
			bean.setCanReturnCargo(FossConstants.YES);
		}

		// 开始试点城市 到达非试点
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType()) && StringUtils.isEmpty(endDto.getCityType())) {
			bean.setCanReturnCargo(FossConstants.NO);
			if (bean.getReceiveMethod() != null) {
				// 提货方式
				String receiveMethod = bean.getReceiveMethod().getValueCode();
				// 是否自提
				if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
					// 试点城市和非试点城市之间提货方式只能开自提
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoPickupSelf"));
				}
			}

			// 返单类别
			// DataDictionaryValueVo vo = bean.getReturnBillType();
			// if(vo!=null && StringUtils.isNotEmpty(vo.getValueCode())
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())){
			// //试点城市和非试点城市之间不能开返单
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			// }

			// DataDictionaryValueVo vo2 = bean.getRefundType();
			// if(vo2!=null && StringUtils.isNotEmpty(vo2.getValueCode()) &&
			// WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo2.getValueCode())){
			// //试点城市和试点城市之间只能开1日退代收货款
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }

		}

		// 开始试点城市 到达-快递代理
		if (ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto
						.getCityType())) {
			bean.setCanReturnCargo(FossConstants.NO);
			// if(bean.getReceiveMethod()!=null){
			// // 提货方式
			// String receiveMethod = bean.getReceiveMethod().getValueCode();
			// //是否自提
			// if (CommonUtils.verdictPickUpSelf(receiveMethod)) {
			// //试点城市和快递代理城市之间提货方式只能开派送
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSPCityDtoPickupSelf"));
			// }
			// }

			// 返单类别
			// DataDictionaryValueVo vo = bean.getReturnBillType();
			// if(vo!=null && StringUtils.isNotEmpty(vo.getValueCode())
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())){
			// //试点城市和非试点城市之间不能开返单
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			// }

			DataDictionaryValueVo vo2 = bean.getRefundType();
			if (vo2 != null
					&& WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo2
							.getValueCode())) {
				// 试点城市和快递代理城市之间不能开1日退代收货款款
				if (bean.getReceiveMethod() != null) {
					// 提货方式
					String receiveMethod = bean.getReceiveMethod()
							.getValueCode();
					// 是否自提
					if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateSLCityDtoRefund"));
					}
				}

			}

		}

		// 开始-非试点 到达-试点城市
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto
						.getCityType())) {
			bean.setCanReturnCargo(FossConstants.YES);
			String orderChannel = bean.getOrderChannel();
			if (!ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO
					.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA
							.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG
							.equals(orderChannel)) {
				// 非试点城市和其他城市之间只能开淘宝订单
//				throw new WaybillValidateException(
//						i18n.get("foss.gui.creating.calculateAction.exception.validateNSCityDtoChannel"));
			}

			// DataDictionaryValueVo vo = bean.getRefundType();
			// if(vo!=null &&
			// WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo.getValueCode())){
			// //非试点城市和试点城市之间只能开1日退代收货款
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateNSCityDtoRefund"));
			// }

			// 返单类别
			// DataDictionaryValueVo vo2 = bean.getReturnBillType();
			// if(vo2!=null
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())
			// ){
			// //试点城市和非试点城市之间不能开返单
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateSNCityDtoReturnBillType"));
			// }

			// DataDictionaryValueVo vo3 = bean.getRefundType();
			// if(vo3!=null && StringUtils.isNotEmpty(vo3.getValueCode()) &&
			// WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo3.getValueCode())){
			// //试点城市和试点城市之间只能开1日退代收货款
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }
		}

		// 开始-非试点 到达-非试点城市
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType()) && StringUtils.isEmpty(endDto.getCityType())) {
			bean.setCanReturnCargo(FossConstants.NO);
			String orderChannel = bean.getOrderChannel();
			if (!ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO
					.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA
							.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG
							.equals(orderChannel)) {
				// 非试点城市和其他城市之间只能开淘宝订单
//				throw new WaybillValidateException(
//						i18n.get("foss.gui.creating.calculateAction.exception.validateNSCityDtoChannel"));
			}

			if (bean.getReceiveMethod() != null) {
				// 提货方式
				String receiveMethod = bean.getReceiveMethod().getValueCode();
				// 是否自提
				if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
					// 试点城市和非试点城市之间提货方式只能开自提
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validateNNCityDtoPickupSelf"));
				}
			}

			// 返单类别
			// DataDictionaryValueVo vo2 = bean.getReturnBillType();
			// if(vo2!=null
			// && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())){
			// //试点城市和非试点城市之间不能开返单
			// throw new WaybillValidateException(
			// i18n.get("foss.gui.creating.calculateAction.exception.validateNNCityDtoReturnBillType"));
			// }

			// DataDictionaryValueVo vo3 = bean.getRefundType();
			// if(vo3!=null && StringUtils.isNotEmpty(vo3.getValueCode()) &&
			// WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo3.getValueCode())){
			// //试点城市和试点城市之间只能开1日退代收货款
			// throw new
			// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			// }
		}

		// 开始-非试点 到达-快递代理
		if (!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto
				.getCityType())
				&& ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto
						.getCityType())) {

			if (bean.getReceiveMethod() != null) {
			// 提货方式
				String receiveMethod = bean.getReceiveMethod().getValueCode();
				// 是否自提
				if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {
					// 非试点城市和快递代理城市之间不能发快递运单
					// 试点城市和快递代理城市之间提货方式只能开派送
					// throw new WaybillValidateException(
					// i18n.get("foss.gui.creating.calculateAction.exception.validateSPCityDtoPickupSelf"));

					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validateNPCityDtoBusiness"));

				}
			}

		}

		// 到达城市是非试点 不能开返单
		// if(!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto.getCityType())){
		// DataDictionaryValueVo vo2 = bean.getReturnBillType();
		// if(vo2!=null
		// && !WaybillConstants.NOT_RETURN_BILL.equals(vo2.getValueCode())){
		// //试点城市和非试点城市之间不能开返单
		// throw new WaybillValidateException(
		// i18n.get("foss.gui.creating.calculateAction.exception.validateDtoReturnBillType"));
		// }
		//
		// }
		//

		String receiveMethod = bean.getReceiveMethod().getValueCode();
		String code = bean.getCustomerPickupOrgCode().getCode();
		if (bean.getCustomerPickupOrgCode() != null) {

			// 是否自提
			if (!CommonUtils.verdictPickUpSelf(receiveMethod)) {

				if (StringUtils.isNotEmpty(code)) {
					OrgAdministrativeInfoEntity o = waybillService
							.queryByCode(code);
					if (o != null
							&& !FossConstants.YES.equals(o
									.getExpressSalesDepartment())) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateExpressSalesDepartment"));
					}

				}
			} else {
				if (StringUtils.isNotEmpty(code)) {
					OrgAdministrativeInfoEntity o = waybillService
							.queryByCode(code);
					if (o != null
							&& FossConstants.YES.equals(o
									.getExpressSalesDepartment())) {
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateExpressSalesDepartmentVirtual"));
					}

				}

			}
		}

	}

	/**
	 * 验证重量与提货方式
	 * 
	 * @author WangQianJin
	 * @date 2013-5-27 下午4:22:07
	 */
	private void validateWeightDeliveryMode(WaybillPanelVo bean) {
		// 货物总重量
		BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
		Integer goodsNum = bean.getGoodsQtyTotal();
		/**
		 * 单件重量大于50kg
		 */
		if (goodsWeightTotal != null
				&& goodsNum != null
				&& goodsNum != 0
				&& (Double.parseDouble(goodsWeightTotal + "") / goodsNum
						.intValue()) > FIFTY) {
			/**
			 * 如果提货方式是“送货上楼”
			 */
			if (bean.getReceiveMethod() != null
					&& WaybillConstants.DELIVER_UP.equals(bean
							.getReceiveMethod().getValueCode())) {
				// throw new
				// WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.50kgNotDoorToDoor"));
			}
		}
	}

	/**
	 * 校验运单号
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-28 下午3:16:20
	 */
	private void validateWaybillNo(WaybillPanelVo bean) {
		// 导入前的运单号
		String oldWaybill = StringUtil.defaultIfNull(bean.getOldWaybillNo());
		// 导入后的运单号
		String newWaybill = StringUtil.defaultIfNull(bean.getWaybillNo());
		// 运单状态
		String pendingType = StringUtil.defaultIfNull(bean.getWaybillstatus());

		// PDA补录运单不能修改运单号
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingType)
				&& !oldWaybill.equals(newWaybill)) {
			throw new WaybillValidateException(i18n.get(
					"foss.gui.creating.calculateAction.exception.pda.import",
					new Object[] { oldWaybill, newWaybill }));
		}
	}

    /**
     * 
     * 验证返单
	 * 
     * @author 076234
     * @date 2015-2-9
     */
	private void validateReturn(ExpWaybillPanelVo bean) {
		String originalWaybillNo = bean.getOriginalWaybillNo();
		String returnType = bean.getReturnType() != null ? bean.getReturnType()
				.getValueCode() : null;
		if (StringUtils.isNotEmpty(originalWaybillNo)
				&& !WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
						.equals(returnType)) {

			// 原单号已经返货
			WaybillExpressEntity waybillExpressEntity = waybillService
					.queryWaybillByOriginalWaybillNo(originalWaybillNo,
							returnType);
			if (waybillExpressEntity != null) {
				throw new WaybillValidateException("返单号:" + originalWaybillNo
						+ "已经返货开单");
			}
			// 原单号已经返货开单
			waybillExpressEntity = waybillService.queryWaybillByWaybillNo(
					originalWaybillNo, returnType);
			if (waybillExpressEntity != null) {
				if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
						.equals(waybillExpressEntity.getReturnType())) {
					throw new WaybillValidateException("返单号:"
							+ originalWaybillNo + "已经返货开单");
				}
			}

			// 到达更改审核、受理中不能返货开单
			waybillService.checkWayBillRfcStatus(originalWaybillNo);

			WaybillEntity entity = waybillService
					.queryWaybillByNumber(originalWaybillNo);

			// 出发更改审核、受理中不能返货开单
			waybillService.checkWayBillChange(entity.getId());

			// 若原单号存在落地配外发交接单（作废和有效），则返货开单时不校验该货物是否在到达部门库存
			boolean isWaiFa = waybillService
					.queryBeLdpHandOveredByWaybillNo(originalWaybillNo);
			if (!isWaiFa) {
				// 单号不在到达部门库存中不能返货开单
				List<StockEntity> stockStatus = waybillService
						.queryStockByWaybillNo(originalWaybillNo);
				boolean stock = true;
				if (CollectionUtils.isNotEmpty(stockStatus)) {
					String destOrgCode = entity.getCustomerPickupOrgCode();
					// 查询营业部的部门信息
					SaleDepartmentEntity dept = waybillService
							.querySaleDepartmentByCode(destOrgCode);
					// 是不是驻地营业部
					if (dept != null
							&& dept.getStation().equals(FossConstants.ACTIVE)) {
						// 驻地营业部对应外场
						String transferCenter = dept.getTransferCenter();
						for (StockEntity stockStatu : stockStatus) {
							String orgCode = stockStatu.getOrgCode();
							if (transferCenter.equals(orgCode)) {
								stock = false;
							}
						}
					} else {
						for (StockEntity stockStatu : stockStatus) {
							String orgCode = stockStatu.getOrgCode();
							if (destOrgCode.equals(orgCode)) {
								stock = false;
							}
						}
					}

				}
				if (stock) {
				  throw new WaybillValidateException("原单号不在到达部门库存不能返货开单");
			  }
			}

		  // 是否存在付款信息.
			if (!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE
					.equals(returnType)) {
				if (waybillService.isExistRepayment(originalWaybillNo)) {
				  throw new WaybillValidateException("原单号已经结清贷款，无法进行返货开单");
				}
			}
			// 已签收不能返货开单
			if (waybillService
					.queryWaybillSignResultByWaybillNo(originalWaybillNo)) {
			  throw new WaybillValidateException("原单号已经签收不能返货开单");
		  }
		 //代收货款不能返货开单
		  if(entity.getCodAmount()!=null && entity.getCodAmount().compareTo(BigDecimal.ZERO)>0){
			  throw new WaybillValidateException("原单号有代收货款，请起草更改取消掉代收货款才能返货开单");

		  }

			// 网上支付未完成不能返货开单
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE
					.equals(entity.getPaidMethod())) {
			List<String> waybillNos = new ArrayList<String>();
			waybillNos.add(originalWaybillNo);
				List<String> notPayWaybillNo = waybillService
						.unpaidOnlinePay(waybillNos);
				if (CollectionUtils.isNotEmpty(notPayWaybillNo)) {
				throw new WaybillValidateException("原单号网上支付且未支付完不能返货开单");
			}
		  }

			// 返货开单不允许开现付
			if (WaybillConstants.CASH_PAYMENT.equals(bean.getPaidMethod()
					.getValueCode())) {
			  throw new WaybillValidateException("返货开单不允许开现付");
		  }

			// 异常货不允许返货开单
			boolean isExcep = waybillService
					.queryExcepSignResultByWaybillNo(originalWaybillNo);
			if (isExcep) {
			  throw new WaybillValidateException("该运单有丢货或弃货或违禁品签收不允许返货开单");
		  }

		} else {
			// 查询营业部的部门信息
			SaleDepartmentEntity dept = waybillService
					.querySaleDepartmentByCode(bean.getReceiveOrgCode());
			if (dept != null) {
				 // 可快递接货
				String canExpressPickupToDoor = dept
						.getCanExpressPickupToDoor();
				// 可快递派送
			    String canExpressDelivery = dept.getCanExpressDelivery();
				if(bean.getReturnType()!=null &&
						"RETURN_WAYBILL".equals(bean.getReturnType().getValueCode())){
					canExpressPickupToDoor =FossConstants.NO; 
					canExpressDelivery = FossConstants.NO;
			   }
				if (FossConstants.NO.equals(canExpressPickupToDoor)&& 
					FossConstants.YES.equals(canExpressDelivery)) {
					throw new WaybillValidateException("安徽模式营业部只允许返货开单");
			}
	}
		}
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
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo
				.getCode())) {
			if (bean.getAirGoodsType() == null) {
				throw new WaybillValidateException(
						i18n.get(WaybillValidateException.NULL_AIRGOODSTYPE));
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
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo
				.getCode())) {
			if (bean.getFlightNumberType() == null
					|| bean.getFlightNumberType().getValueCode() == null) {
				throw new WaybillValidateException(
						i18n.get(WaybillValidateException.NULL_FLIGHTNUMBERTYPE));
			}

			if (bean.getFreightMethod() == null
					|| bean.getFreightMethod().getValueCode() == null) {
				throw new WaybillValidateException(
						i18n.get(WaybillValidateException.NULL_FREIGHTMETHOD));
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
				throw new WaybillValidateException(
						i18n.get(WaybillValidateException.null_VehicleNumber));
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
		if (bean.getGoodsQtyTotal() == null
				|| bean.getGoodsQtyTotal().intValue() == 0) {
			ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
			throw new WaybillValidateException(
					i18n.get(WaybillValidateException.NULL_GOODSQTYTOTAL));
		}
		BigDecimal bd_oneGoodsQtyTotalWeight = BigDecimal.ZERO;
		BigDecimal bd_oneGoodsQtyTotalVolume = BigDecimal.ZERO;
		//查询数据字典配置参数值(总体积上限,总重量上限,总件数上限,CRM客户信息中是否可以开一票多件属性)
		boolean isoneGoodsQtyTotal = false;
		int isone =  waybillService.queryOneticketornotBycode(bean.getDeliveryCustomerCode());
		if(isone!=-1){
			 if(isone==1){
				 isoneGoodsQtyTotal = true;
			 }else{
				 isoneGoodsQtyTotal = false;
			 }
		}
		String oneGoodsQtyTotalGoods  = 
				waybillService.queryConfValueByCode(WaybillConstants.ONEGOODSQTYTOTAL_GOODS);
		String oneGoodsQtyTotalWeight  = 
				waybillService.queryConfValueByCode(WaybillConstants.ONEGOODSQTYTOTAL_WEIGHT);
		String oneGoodsQtyTotalVolume  = 
				waybillService.queryConfValueByCode(WaybillConstants.ONEGOODSQTYTOTAL_VOLUME);
		if(null!=oneGoodsQtyTotalWeight){
		    bd_oneGoodsQtyTotalWeight = new BigDecimal(oneGoodsQtyTotalWeight);
		}
		if(null!=oneGoodsQtyTotalVolume){
			bd_oneGoodsQtyTotalVolume = new BigDecimal(oneGoodsQtyTotalVolume);
		}
		//设置裹裹订单类型为12
		if(StringUtil.isNotEmpty(bean.getOrderNo())){
			String servicetype = waybillService.queryOrderByOrderNo(bean.getOrderNo());
			bean.setServerType(servicetype);
		}
		// 件数不能大于1
		if (bean.getGoodsQtyTotal().intValue() != 1) {
			
			/**
			 * gyk 只有开返货单时 不需要验证部门是否能够开一票多件
			 */
						ExpWaybillPanelVo bean2 = (ExpWaybillPanelVo)bean;
			if(bean2.getReturnType()!=null){
				if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO_NAME.equals(bean2.getReturnType().getValueName()) ||
						WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE_NAME.equals(bean2.getReturnType().getValueName())	){
					//开返货单时  不需要验证部门是否能够开一票多件 
					//当不是返货单 仍然需要走原来的判断逻辑(即下面的判断逻辑)
				}else if(valdateExpMultiPieceDept(bean)){
					
				}else{
					SaleDepartmentEntity entitry = waybillService.querySaleDeptByCode(bean.getCreateOrgCode());
					//liyongfei 查询创建部门是否有一件多票的属性
					if(FossConstants.YES.equals(entitry.getCanExpressOneMany())
							&&isoneGoodsQtyTotal){
				
					}else{						
						ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
						if(!isoneGoodsQtyTotal){
							throw new WaybillValidateException(
									i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByCustomerCode"));
						}
						throw new WaybillValidateException(
								i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByCustomer"));
					}
					
					// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件bean.getCustomerPickupOrgName().contains("远郊")
					//||远郊判断在后判断
					if (bean.getCustomerPickupOrgName() != null) {
						if ( bean.getCustomerPickupOrgName().contains(
										"出发")) {
							ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
							throw new WaybillValidateException(
									i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByAdress"));
						}
					}
				}
				}else if(valdateExpMultiPieceDept(bean)){
						
			}else{
				 SaleDepartmentEntity entitry = waybillService.querySaleDeptByCode(bean.getCreateOrgCode());
				//liyongfei 查询创建部门是否有一件多票的属性
				if(FossConstants.YES.equals(entitry.getCanExpressOneMany())
						&&isoneGoodsQtyTotal){
					
				}else{					
					ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
					if(!isoneGoodsQtyTotal){
						//此客户非一票多件客户,不能开一票多件！
						throw new WaybillValidateException(
								i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyByCustomer"));
					}
					//该开单部门不能开一票多件
					throw new WaybillValidateException(
							i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByCustomer"));
				}
				// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件bean.getCustomerPickupOrgName().contains("远郊")
				//||远郊判断在后判断
				if (bean.getCustomerPickupOrgName() != null) {
					if (bean.getCustomerPickupOrgName().contains("出发")) {
						ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
						//该目的站不能开一票多件
						throw new WaybillValidateException(
								i18n.get("foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByAdress"));
					}
				}
			}
		}

		if (bean.getGoodsWeightTotal() == null
				|| bean.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
			ui.cargoInfoPanel.getTxtWeight().requestFocus();
			throw new WaybillValidateException(
					i18n.get(WaybillValidateException.NULL_GOODSWEIGHTTOTAL));
		}

		// if (bean.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
		// ui.cargoInfoPanel.getTxtVolume().requestFocus();
		// throw new
		// WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSVOLUMETOTAL));
		// }

		// zxy 20140111 DEFECT-1440 start 添加:增加空校验,否则有些地方会报错(不清楚之前为什么把这个体积校验去掉)
		if (bean.getGoodsVolumeTotal() == null) {
			ui.cargoInfoPanel.getTxtVolume().requestFocus();
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSVOLUMETOTAL1));
		}
		//如果客户是裹裹用户，不管客户是否维护一票多件优先于裹裹渠道过来的需求否则按照正常校验
		if(WaybillConstants.SERVICE_TYPE.equals(bean.getServerType())){
			validateGuoGuoEavyGood(bean);
			
		}else{
		//  265041 - 增加一票多件校验,如果发货客服允许开一票多件,那么需要校验最大体积,重量,件数不超过数据字典的配置常量。
		 if(isoneGoodsQtyTotal&&bean.getGoodsQtyTotal().intValue()>Integer.parseInt(oneGoodsQtyTotalGoods)){
	        	ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
				throw new WaybillValidateException(
						"一票多件货物件数最多为"+oneGoodsQtyTotalGoods+"件,请修改！");
	        }
		 if (isoneGoodsQtyTotal&&bean.getGoodsWeightTotal().compareTo(bd_oneGoodsQtyTotalWeight)==1) {
				ui.cargoInfoPanel.getTxtWeight().requestFocus();
				throw new WaybillValidateException(
						"一票多件货物总重量最多为"+oneGoodsQtyTotalWeight+"kg,请修改！");
			}
		 if(isoneGoodsQtyTotal&&bean.getGoodsVolumeTotal().compareTo(bd_oneGoodsQtyTotalVolume)==1){
			 ui.cargoInfoPanel.getTxtVolume().requestFocus();
				throw new WaybillValidateException(
						"一票多件货物总体积不得超过"+oneGoodsQtyTotalVolume+"m³,请修改！");
		 }
		}
		// zxy 20140111 DEFECT-1440 end
		// 添加:增加空校验,否则有些地方会报错(不清楚之前为什么在descriptor里把这个体积校验去掉)
		// DEFECT-2646,快递允许体积为0
		if (bean.getGoodsVolumeTotal().intValue() != 0) {
			if (bean.getGoodsVolumeTotal() != null
					&& !"0".equals(bean.getGoodsVolumeTotal().toString())
					&& bean.getGoodsWeightTotal() != null
					&& !"0".equals(bean.getGoodsWeightTotal().toString())) {
				boolean bool = waybillService.isWeightByVolume(bean
						.getGoodsWeightTotal().toString(), bean
						.getGoodsVolumeTotal().toString());
				if (!bool) {
					if (JOptionPane.YES_OPTION != JOptionPane
							.showConfirmDialog(
									ui,
									i18n.get("foss.gui.creating.calculateAction.msgBox.confirmWeightVolume"),
									i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"),
									JOptionPane.YES_NO_OPTION)) {
						throw new WaybillValidateException("");
					}
				}
			}
		}

		/**
		 * ISSUE-4421　毛重小于等于50kg，体积小于等于0.3
		 * DMANA-805　毛重小于等于50kg*件数，体积小于等于0.3*件数
		 */
		BigDecimal qtyWeightUpperLimit = BigDecimal.valueOf(WEIGHT_UPPER_LIMIT)
				.multiply(BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		BigDecimal qtyVolumeUpperLimit = BigDecimal.valueOf(VOLUME_UPPER_LIMIT)
				.multiply(BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		// 获取快递公斤段分割点
		ConfigurationParamsEntity EXPRESS_WEIGHT_CUT = getConfigurationParamsByEntity(ConfigurationParamsConstants.PKP_EXPRESS_WEIGHT_CUT);
		BigDecimal qtyWeightCut = BigDecimal.valueOf(
				Double.valueOf(EXPRESS_WEIGHT_CUT.getConfValue())).multiply(
				BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		// 获取快递公斤段分割点时间节点
		ConfigurationParamsEntity EXPRESS_WEIGHT_CUT_TIME = getConfigurationParamsByEntity(ConfigurationParamsConstants.PKP_EXPRESS_WEIGHT_CUT_TIME);
		/**
		 * @author 200945 返回限制的3.60特惠件判断的重量 需改时间：2014-11-28
		 *         修改内容：将代码2016以及2019行的bean
		 *         .getGoodsWeightTotal()修改为：judgeWeight变量;该变量的取值参考方法的注释；
		 */
		BigDecimal judgeWeight = validateExpressTo360Express(bean);
		if (null != EXPRESS_WEIGHT_CUT_TIME) {
			if (StringUtils.isNotEmpty(EXPRESS_WEIGHT_CUT_TIME.getConfValue())) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// 时间节点
				Date weightCutDate = null;
				try {
					weightCutDate = sdf.parse(EXPRESS_WEIGHT_CUT_TIME
							.getConfValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (weightCutDate != null
						&& bean.getBillTime().after(weightCutDate)) {
					if ((WaybillConstants.PACKAGE.equals(bean.getProductCode()
							.getCode()))
							&& judgeWeight != null
							&& qtyWeightCut.compareTo(judgeWeight) <= 0) {
						/*throw new WaybillValidateException(
								i18n.get(
										"计费重量或实际重量为{0}kg及以上货物,请选择【3.60特惠件】",
										new Object[] { EXPRESS_WEIGHT_CUT
												.getConfValue() }));*/
						throw new WaybillValidateException(
								i18n.get(
										"foss.gui.creatingexp.expCalculateAction.qtyBigWeightCut",
										new Object[] { EXPRESS_WEIGHT_CUT
												.getConfValue() }));
		              }
					if (WaybillConstants.ROUND_COUPON_PACKAGE.equals(bean
							.getProductCode().getCode())
							&& judgeWeight != null
							&& qtyWeightCut.compareTo(judgeWeight) > 0) {
						/*throw new WaybillValidateException(
								i18n.get(
										"计费重量或实际重量为{0}kg以下货物,请选择【标准快递】或【商务专递】",
										new Object[] { EXPRESS_WEIGHT_CUT
												.getConfValue() }));*/
						throw new WaybillValidateException(
								i18n.get(
										"foss.gui.creatingexp.expCalculateAction.qtySmallWeightCut",
										new Object[] { EXPRESS_WEIGHT_CUT
												.getConfValue() }));
		             }
		        }
	        }
		}
		//开单为返货且原单号为子母件时，跳过此校验
		boolean isFlag=false;
		if(bean instanceof ExpWaybillPanelVo){
			ExpWaybillPanelVo bean2=(ExpWaybillPanelVo)bean;
			if(StringUtils.isNotEmpty(bean2.getOldWaybillNo()) && 
					null !=bean2.getReturnType()&&(
					WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(bean2.getReturnType().getValueCode())||
					WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(bean2.getReturnType().getValueCode()))){
				TwoInOneWaybillDto waybillDto=waybillService.queryWaybillRelateByWaybillOrOrderNo(bean2.getOldWaybillNo());
				if(null !=waybillDto&&FossConstants.YES.equals(waybillDto.getIsTwoInOne())){
					isFlag=true;
				}
			}
		}
		if(!isFlag){
			//裹裹放开重量和体积限制
			if(WaybillConstants.SERVICE_TYPE.equals(bean.getServerType())){
				validateGuoGuoEavyGood(bean);
				
			}else if ((bean.getGoodsWeightTotal() != null && qtyWeightUpperLimit
					.compareTo(bean.getGoodsWeightTotal()) < 0) // zxy 20140103
					// 增加bean.getGoodsWeightTotal()
					// !=null判断
					|| (bean.getGoodsVolumeTotal() != null && qtyVolumeUpperLimit
					.compareTo(bean.getGoodsVolumeTotal()) < 0)) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creatingexp.expCalculateAction.qtyWeightUpperLimit"));
			}
		}
	}


	/**
	 * 需求号：DMANA-5535： 快递开单计费重量3kg（含3kg）以上限制3.60特惠件，3kg以下限制标准快递 业务逻辑：
	 * 3.60特惠件的3kg区分以计费重量为标准，即体积重量和实际重量两者取大作为判断标准
	 * 
	 * @author 200945 - wutao
	 * @param bean
	 * @return
	 * @date 2014-11-28
	 */
	private BigDecimal validateExpressTo360Express(WaybillPanelVo bean) {
		// 货物总重量
		BigDecimal weightTotal = bean.getGoodsWeightTotal();
		// 货物总体积
		BigDecimal volumeTotal = bean.getGoodsVolumeTotal();
		// 如果体积为【null】,则将体积转化为0
		if (null == volumeTotal) {
			volumeTotal = BigDecimal.ZERO;
		}
		String prarentProductCode = "";
		if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE
				.equals(bean.getProductCode().getCode())
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE
						.equals(bean.getProductCode().getCode())
						|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT
						.equals(bean.getProductCode().getCode())) {
			prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		}
		BigDecimal volumeWeight = ExpCommonUtils.validateWeightBubbleRate(
				bean.getDeliveryCustomerCode(), null, prarentProductCode,
				volumeTotal, bean.getReceiveOrgCode());
		// 判断标准：计费重量和实际总量相比较，取较大的值
		if (volumeWeight.compareTo(weightTotal) < 0) {
			return weightTotal;
		} else {
			return volumeWeight;
		}

	}

	/**
	 * 远程获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	private ConfigurationParamsEntity getConfigurationParamsByEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return waybillService.queryConfigurationParamsByEntity(
				DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type,
				FossConstants.ROOT_ORG_CODE);

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

		if (codAmount == null || codAmount.compareTo(zero) == 0) {

			DataDictionaryValueVo vo = bean.getRefundType();
			if (vo != null && vo.getValueCode() != null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateCod.one")
								+ vo.getValueName()
								+ i18n.get("foss.gui.creating.calculateAction.exception.validateCod.two"));
			}
			/**
			 * 判断是否为整车类型并且提货方式是否为机场自提， 如果不是，才给与没有代收货款提示
			 */
			if (ui.incrementPanel.getCombRefundType().isEnabled()
					&& !ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE
							.equals(bean.getProductCode().getCode())
					&& !WaybillConstants.AIRPORT_PICKUP.equals(bean
							.getReceiveMethod().getValueCode())) {
				if (JOptionPane.YES_OPTION != JOptionPane
						.showConfirmDialog(
								ui,
								i18n.get("foss.gui.creating.calculateAction.msgBox.confirmValidate"),
								i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"),
								JOptionPane.YES_NO_OPTION)) {
					ui.incrementPanel.getTxtCashOnDelivery().requestFocus();
					// 将退款类型设置为空
					ExpCommon.setRefundType(bean, ui);
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validateCod.three"));
				}
			}
		} else {
			if (!bean.getIsWholeVehicle()) {
				if (!FossConstants.YES.equals(bean.getCanAgentCollected())) {
					throw new WaybillValidateException("对不起，您选择的网点不允许开代收货款！");
				}
			}

			ExpCommon.validateBankInfo(bean, ui, waybillService);

			/**
			 * 为退款类型重新赋值，因为当发货客户是非公司会员客户时，第一次选择退款类型会清空，当再次选择此退款类型时，
			 * bean中的退款类型并没有并没有改变，还是空。
			 */
			if (ui.incrementPanel.getCombRefundType().getSelectedItem() != null) {
				DataDictionaryValueVo refundType = (DataDictionaryValueVo) ui.incrementPanel
						.getCombRefundType().getSelectedItem();
				bean.setRefundType(refundType);
			}
			if (bean.getRefundType() == null
					|| bean.getRefundType().getValueCode() == null) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateCod.four"));
			} else {
				if (!WaybillConstants.REFUND_TYPE_VALUE.equals(bean
						.getRefundType().getValueCode())) {
					if (StringUtils.isEmpty(bean.getAccountName())
							|| StringUtils.isEmpty(bean.getAccountCode())
							|| StringUtils.isEmpty(bean.getAccountBank())) {
						if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext
								.get(WaybillConstants.LOGIN_TYPE).toString())) {
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.calculateAction.exception.validateCod.five"));
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
	private void validatePack(WaybillPanelVo bean2) {
		ExpWaybillPanelVo bean = (ExpWaybillPanelVo) bean2;
		Integer qtyTotal = bean.getGoodsQtyTotal();// 总件数
		Integer intPaper = bean.getPaper();
		Integer intWood = bean.getWood();
		Integer intFibre = bean.getFibre();
		Integer intSalver = bean.getSalver();
		Integer intMembrane = bean.getMembrane();

		// 木架信息判空操作
		if (null == intPaper || null == intWood || null == intFibre
				|| null == intSalver || null == intMembrane) {
			// 记录异常日志信息
			LOG.error(i18n
					.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
			// 抛出异常信息
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.waybillSubmitAction.exception.validatePack.packIsNotNull"));
		}

		// 木架信息判空操作
		if (0 == bean.getPaper().intValue() && 0 == bean.getWood().intValue()
				&& 0 == bean.getFibre().intValue()
				&& 0 == bean.getSalver().intValue()
				&& 0 == bean.getMembrane().intValue()
				&& StringUtils.isEmpty(bean.getOtherPackage())) {
			// 记录异常日志信息
			LOG.error(i18n
					.get("foss.gui.creating.caculateAction.validatePack.isZero"));
			// 抛出异常信息
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.caculateAction.validatePack.isZero"));
		}

		Integer paper = Integer.valueOf(bean.getPaper());// 纸
		Integer wood = Integer.valueOf(bean.getWood());// 木
		Integer fibre = Integer.valueOf(bean.getFibre());// 纤
		Integer salver = Integer.valueOf(bean.getSalver());// 托
		Integer membrane = Integer.valueOf(bean.getMembrane());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;

		if (packTotal > qtyTotal) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.validatePack"));

		}
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
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullPaidMethod"));
		}
		
		//校验是否是裹裹的运单，是裹裹的读取FOSS结算裹裹支付信息暂存表，存在费用信息且大于0，只允许网上支付
		if(StringUtils.equals("12", bean.getServerType())){
			boolean greenHandWrapWriteoffService = waybillService.greenHandWrapWriteoffService(bean.getWaybillNo());
			if(greenHandWrapWriteoffService){
				//存在结算数据，只能是网上支付
				if (!WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					throw new WaybillValidateException("裹裹订单已付款，付款方式请选择“网上支付”");
				}
			}
		}

		// 只有月结客户才能开月结
		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod()
				.getValueCode())) {

			if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod()
					.getValueCode())) {
				CusBargainVo vo = new CusBargainVo();
				vo.setExpayway(WaybillConstants.MONTH_END);
				vo.setCustomerCode(bean.getDeliveryCustomerCode());
				vo.setBillDate(new Date());
				vo.setBillOrgCode(bean.getReceiveOrgCode());
				boolean  isOk = waybillService.isCanPaidMethodExp(vo);
				if (!isOk) {
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);
					ui.buttonPanel.getBtnSubmit().setEnabled(false);
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.listener.Waybill.exception.NocanPaidMethod"));
				}
			}

			// 判断客户是否月结
			if (bean.getChargeMode() == null || !bean.getChargeMode()) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.notMonthPayment"));
			}
		}

		// 提货方式为机场自提，付款方式不能选择到付
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.one"));
				}
			}

			if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.two"));
			}
		}

		if (!bean.getIsWholeVehicle()) {
			// 提货网点是否可以货到付款
			if (!FossConstants.YES.equals(bean.getArriveCharge())) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.noArrivedPay"));
				}
			}
		}

		// 空运以及偏线无法选择网上支付
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo
				.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
						.equals(productVo.getCode())) {
			if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
					.getValueCode())) {
				throw new WaybillValidateException(
						productVo.getName()
								+ i18n.get("foss.gui.creating.calculateAction.exception.unableOnlinePayment"));
			}
		}

		/**
		 * 临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
		 */
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod()
				.getValueCode())) {
			// 判断客户编码是否为空
			if (StringUtil.isEmpty(bean.getDeliveryCustomerCode())) {
				throw new WaybillSubmitException(
						i18n.get("foss.gui.creating.calculateAction.exception.paymentLQ"));
			}
		}
		// 校验电商尊享
		// 运输性质为电商尊享
		if (WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(bean
				.getProductCode().getCode())) {
			// 电商尊享客户
			if (!WaybillConstants.YES.equals(bean.getIsElectricityToEnjoy())) {
				throw new WaybillValidateException(
						i18n.get("foss.waybill.eWaybillService.isNotEcommerceCustomerException"));
				// 付款方式为月结
			} else if (null != bean.getPaidMethod()&& !WaybillConstants.ARRIVE_PAYMENT.equals(bean
					.getPaidMethod().getValueCode())
					&& !WaybillConstants.MONTH_PAYMENT.equals(bean
							.getPaidMethod().getValueCode())) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillDescriptor.expWaybillPaidMethodMustBeCT"));
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
				String result = new WaybillDescriptor()
						.validateDeliveryCustomerMobilephone(mobilePhone, bean);
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
				String result = new WaybillDescriptor()
						.validateReceiveCustomerMobilephone(mobilePhone, bean);
				mobilePhone = mobilePhone.substring(0, 1);
				if (!WaybillConstants.SUCCESS.equals(result)) {
					ui.consigneePanel.getTxtConsigneeMobile().requestFocus();
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillDescriptor.receiver.mobile.format.error"));
				}
			}

		}

		if (StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getDeliveryCustomerPhone())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorPhoneOrTel"));
		}

		if (StringUtils.isEmpty(bean.getReceiveCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getReceiveCustomerPhone())) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneePhoneOrTel"));
		}
		
		/**
		 * 已在提交时 校验
		 * @author Foss-272311-sangwenhao 
		 * @time 20160417
		 */
		/*
		// 原件签收单返回
		if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
				.getReturnBillType().getValueCode())) {
			JAddressField jd = ui.consignerPanel.getTxtConsignerArea() ;
			AddressFieldDto consignerAddress = jd.getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText())
					|| consignerAddress == null
					|| StringUtils.isBlank(consignerAddress.getProvinceId())
					|| StringUtils.isBlank(bean.getDeliveryCustomerAddress())) {
				ui.consignerPanel.getTxtConsignerArea().requestFocus();
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
			}
		}

		if (bean.getReceiveMethod() == null) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.transferInfoPanel.receiveMethod.isNull"));
		}
		// 提货方式
		String code = bean.getReceiveMethod().getValueCode();
		if (WaybillConstants.DELIVER_NOUP.equals(code)
				|| WaybillConstants.DELIVER_FREE.equals(code)
				|| WaybillConstants.DELIVER_STORAGE.equals(code)
				|| WaybillConstants.DELIVER_UP.equals(code)
				|| WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			JAddressFieldForExp jd = ui.consigneePanel.getTxtConsigneeArea();
			AddressFieldDto consigneeAddress = jd.getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText())
					|| consigneeAddress == null
					|| StringUtils.isBlank(consigneeAddress.getProvinceId())
					|| StringUtils.isBlank(bean.getReceiveCustomerAddress())) {
				ui.consigneePanel.getTxtConsigneeArea().requestFocus();
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
			}
		}

		// 如果不是导入订单开单,则校验省市区是否规范，因为订单省市区业务规则与FOSS不一致，因此不做校验（MANA-1598）
		if (StringUtils.isEmpty(bean.getOrderNo())) {
			// 判断发货人省市区文本框是否为空
			String regionNerText = ui.consignerPanel.getTxtConsignerArea()
					.getText();
			if (StringUtils.isNotEmpty(regionNerText)) {
				// 判断是否为选择的省市区
				if (regionNerText.indexOf("-") == -1) {
					// 请选择正确的发货人省市区
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.consignerPanel.regionAddress.alert"));
				}
			}
			// 判断收货人省市区文本框是否为空
			String regionNeeText = ui.consigneePanel.getTxtConsigneeArea()
					.getText();
			if (StringUtils.isNotEmpty(regionNeeText)) {
				// 判断是否为选择的省市区
				if (regionNeeText.indexOf("-") == -1) {
					// 请选择正确的收货人省市区
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.consigneePanel.regionAddress.alert"));
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
		BigDecimal goodsWeight = bean.getGoodsWeightTotal();// 总重量
		BigDecimal goodsVolume = bean.getGoodsVolumeTotal();// 总体积

		if (goodsVolume == null) {
			goodsVolume = BigDecimal.ZERO;
		}
		BigDecimal goodsQty = new BigDecimal(bean.getGoodsQtyTotal());// 总件数

		BigDecimal pieceWeight = goodsWeight.divide(goodsQty, 2,
				BigDecimal.ROUND_HALF_EVEN);// 单件重量
		BigDecimal pieceVolume = goodsVolume.divide(goodsQty, 2,
				BigDecimal.ROUND_HALF_EVEN);// 单件体积

		BranchVo selectedSaleDepartmentInfo = bean.getCustomerPickupOrgCode();
		if (selectedSaleDepartmentInfo != null) {
			if (selectedSaleDepartmentInfo.getSinglePieceLimitkg() != null) {
				// 单件重量上限
				BigDecimal singlePieceLimitkg = BigDecimal.valueOf(
						selectedSaleDepartmentInfo.getSinglePieceLimitkg())
						.divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceWeight.compareTo(singlePieceLimitkg) > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.overSinglePieceLimitkg")
									+ singlePieceLimitkg.toString()
									+ i18n.get("foss.gui.creating.waybillEditUI.common.antiBrackets"));
				}
			}

			if (selectedSaleDepartmentInfo.getSinglePieceLimitvol() != null) {
				// 单件体积上限
				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(
						selectedSaleDepartmentInfo.getSinglePieceLimitvol())
						.divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceVolume.compareTo(singlePieceLimitvol) > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.overSinglePieceLimitvol"));
				}
			}

			if (selectedSaleDepartmentInfo.getSingleBillLimitkg() != null) {
				// 单票重量上限
				BigDecimal singleBillLimitkg = BigDecimal.valueOf(
						selectedSaleDepartmentInfo.getSingleBillLimitkg())
						.divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsWeight.compareTo(singleBillLimitkg) > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.overSingleBillLimitkg"));
				}
			}

			if (selectedSaleDepartmentInfo.getSingleBillLimitvol() != null) {
				// 单票体积上限
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(
						selectedSaleDepartmentInfo.getSingleBillLimitvol())
						.divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsVolume.compareTo(singleBillLimitvol) > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.overSingleBillLimitvol"));
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
		if (product == null
				|| "".equals(StringUtil.defaultIfNull(product.getCode()))) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullProductType"));
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
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullCustomerPickupOrg"));
		}else if(("W0000005136").equals(bean.getCustomerPickupOrgCode().getCode())){
			MsgBox.showInfo("目的站为台湾，需提供发货人身份证复印件和收货人身份证号码");
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
		DebitDto dto = waybillService.isBeBebt(bean.getDeliveryCustomerCode(),
				bean.getReceiveOrgCode(), WaybillConstants.TEMPORARY_DEBT,
				money);
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
	private GuiQueryBillCalculateDto getProductPriceDtoCollection(
			WaybillPanelVo bean) {
		// 上门接货优先读取上门接货的价格
		if (bean.getPickupToDoor()) {
			return ExpCommon.getQueryParamCollection(bean, FossConstants.YES);

		} else {
			return ExpCommon.getQueryParamCollection(bean, FossConstants.NO);
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
	private void setProductPriceDtoCollection(GuiResultBillCalculateDto dto,
			WaybillPanelVo bean, GuiResultBillCalculateDto dtoJH) {
		// 如果返货开单 是我司原因 公布价为0
		if (WaybillConstants.COMPANY_REASON.equals(bean.getReturnBillReason())) {
			dto.setCaculateFee(BigDecimal.ZERO);
			dto.setDiscountPrograms(null);
		}
		if (dto == null) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		}
		if (dto.getCaculateFee() == null) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
		}
		BigDecimal transportFee = dto.getCaculateFee();
		if(bean.isAccurateCost()){
			transportFee=transportFee.setScale(2,BigDecimal.ROUND_HALF_UP);// 四舍五入
		}else{
			transportFee=transportFee.setScale(0,BigDecimal.ROUND_HALF_UP);// 四舍五入
		}	
				
		// 设置运费价格ID
		if (StringUtils.isNotEmpty(dto.getId())) {
			bean.setTransportFeeId(dto.getId());
		} else {
			bean.setTransportFeeId("NA");
		}
		// 设置运费价格CODE
		bean.setTransportFeeCode(dto.getPriceEntryCode());
		// 设置运费
		bean.setTransportFee(transportFee);
		// 设置费率
		if (dto.getActualFeeRate() != null) {
			bean.setUnitPrice(dto.getActualFeeRate().divide(
					new BigDecimal(100), newScale, RoundingMode.HALF_EVEN));
		} else {
			if (transportFee != null && bean.getBillWeight() != null
					&& bean.getBillWeight().doubleValue() > 0) {
				bean.setUnitPrice(transportFee.divide(
						bean.getGoodsWeightTotal(), newScale,
						RoundingMode.HALF_EVEN));
			} else if (transportFee != null
					&& bean.getGoodsWeightTotal() != null
					&& bean.getGoodsWeightTotal().doubleValue() > 0) {
				bean.setUnitPrice(transportFee.divide(
						bean.getGoodsWeightTotal(), newScale,
						RoundingMode.HALF_EVEN));
			}

		}

		// 计费重量就是货物重量 和体积没有关系

		// bean.setBillWeight(bean.getGoodsWeightTotal());

		if (bean.getUnitPrice() == null) {
			bean.setUnitPrice(BigDecimal.ZERO);
		}

		// 设置计费类型（重量计费或者体积计费）
		setBillingWay(dto.getCaculateType(), bean);

		// 设置计费重量
		setBillWeight(bean, dto);

		// 画布公布价运费
		bean.setTransportFeeCanvas(dto.getCaculateFee().toString());

		// 设置折扣优惠
		ExpCommon.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);

		// 设置折扣值
		setDiscount(dto.getDiscountPrograms(), bean);

		// 判断如果勾选上门接货,判断是否是用上门接货计算的运费，如果是，设置接货费为0，不可编辑；如果不是，设置接货费为远程获取的费用
		// if(bean.getPickupToDoor())
		// {
		// if (FossConstants.ACTIVE.equals(dto.getCentralizePickup())) {
		// ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
		// bean.setPickupFee(BigDecimal.ZERO);//设置接货费
		// // 画布-接货费
		// bean.setPickUpFeeCanvas(BigDecimal.ZERO.toString());
		// } else {
		// if(dtoJH==null){
		// throw new
		// WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.PickupFeeNull"));
		// }
		// ui.billingPayPanel.getTxtPickUpCharge().setEnabled(true);
		// ui.billingPayPanel.getTxtPickUpCharge().setEditable(true);
		// bean.setPickupFee(dtoJH.getCaculateFee());//设置接货费
		// bean.setBasePickupFee(dtoJH.getCaculateFee());//原始接货费
		// // 画布-接货费
		// bean.setPickUpFeeCanvas(dtoJH.getCaculateFee().toString());
		// }
		// }else{
		// ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
		// bean.setPickupFee(BigDecimal.ZERO);//设置接货费
		// bean.setBasePickupFee(BigDecimal.ZERO);//原始接货费
		// }
		ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
		bean.setPickupFee(BigDecimal.ZERO);// 设置接货费
		bean.setBasePickupFee(BigDecimal.ZERO);// 原始接货费

	}

	/**
	 * 
	 * 设置折扣优惠
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-9 下午01:46:39
	 */
	private void setDiscount(List<GuiResultDiscountDto> discountPrograms,
			WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {// 折扣不是空
			for (GuiResultDiscountDto dto : discountPrograms) {
				// 设置
				bean.setDiscount(dto.getDiscountRate());
				// 设置快递续重折扣率
				bean.setExpressContinuediscount(dto.getRenewalDiscountRate());
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
	private void setBillWeight(WaybillPanelVo bean,
			GuiResultBillCalculateDto fee) {
		// ProductEntityVo productVo = bean.getProductCode();
		// if
		// (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))
		// {//精准空运
		//
		// if
		// (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode()))
		// {//按重量计费
		// bean.setBillWeight(bean.getGoodsWeightTotal()); //设置计费重量
		// } else {
		// // 计费重量
		// if (fee.getVolumeWeight() == null) {
		// bean.setBillWeight(BigDecimal.ZERO);//设置计费重量 = 0
		// } else {
		// bean.setBillWeight(fee.getVolumeWeight()); //设置计费重量
		// }
		// }
		// } else {
		// bean.setBillWeight(BigDecimal.ZERO);//设置计费重量 = 0
		// }

		if (fee.getVolumeWeight() != null) {
			bean.setBillWeight(fee.getVolumeWeight()); // 设置计费重量
		} else {
			bean.setBillWeight(bean.getGoodsWeightTotal());
		}
	}

	/**
	 * 
	 * 计算增值费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午08:58:39
	 */
	public void calculateValueAdd(WaybillPanelVo bean,
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos) {

		// 获取打木架
		GuiResultBillCalculateDto packageCollectionFRAME = getGuiResultBillCalculateDto(
				guiResultBillCalculateDtos,
				PriceEntityConstants.PRICING_CODE_BZ,
				DictionaryValueConstants.PACKAGE_TYPE__FRAME);
		// 设置打木架
		ExpCommon.setStandChargeCollection(bean, ui, packageCollectionFRAME);

		// 获取打木箱
		GuiResultBillCalculateDto packageCollectionBOX = getGuiResultBillCalculateDto(
				guiResultBillCalculateDtos,
				PriceEntityConstants.PRICING_CODE_BZ,
				DictionaryValueConstants.PACKAGE_TYPE__BOX);
		// 设置打木箱
		ExpCommon.setBoxChargeCollection(bean, ui, packageCollectionBOX);

		// 获取保价费
		GuiResultBillCalculateDto insuranceCollection = getGuiResultBillCalculateDto(
				guiResultBillCalculateDtos,
				PriceEntityConstants.PRICING_CODE_BF, null);

		// 设置保价费
		setInsuranceCollection(bean, insuranceCollection);

		// 获取代收货款手续费
		GuiResultBillCalculateDto CodCollection = getGuiResultBillCalculateDto(
				guiResultBillCalculateDtos,
				PriceEntityConstants.PRICING_CODE_HK, null);

		// 设置代收货款手续费
		setCodCollection(bean, CodCollection);

		// 送货费
		setDeliveryFeeCollection(bean, guiResultBillCalculateDtos);
		// 计算装卸费
		calculateServiceFee(bean);

		// 添加其他费用 折扣
		setOtherChargeDataCollection(bean, guiResultBillCalculateDtos);
		// 返单折扣
		setReturnBillCollection(bean,guiResultBillCalculateDtos);
	}

	private void setReturnBillCollection(WaybillPanelVo bean,List<GuiResultBillCalculateDto> guiResultBillCalculateDtos) {

		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//hbhk 快递增值费添加 返单类别  原件签收单返回 费用显示
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType()
				.getValueCode())
				&& !WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
						.getReturnBillType().getValueCode())
						) {
			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(bean
					.getReturnBillType().getValueCode())) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = bean.getReturnBillType().getValueCode();
			}

			List<ValueAddDto> list = waybillService
					.queryValueAddPriceList(ExpCommon.getQueryOtherChargeParam(
							bean, type));
			//签收单返单 计费规则
//			getReturnBillRulePrice(bean, guiResultBillCalculateDtos, list);	

			OtherChargeVo otherVo = getReturnBillCharge(bean, list, data);
			// 添加返单费用到其他费用表格
			String chargeName = ExpCommon.addOtherCharge(data, otherVo, bean);
			// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
			bean.setReturnBillChargeName(chargeName);
			ui.incrementPanel.setChangeDetail(data);

			// 折扣优惠
			if (CollectionUtils.isNotEmpty(list)) {
				for (ValueAddDto addDto : list) {
					if (PriceEntityConstants.PRICING_CODE_QS.equals(addDto
							.getPriceEntityCode())) {
						//防止折扣为空的情况
						if(CollectionUtils.isNotEmpty(addDto.getDiscountPrograms())){
							ExpCommon.setReturnBillDiscount(
									addDto.getDiscountPrograms(), ui, bean);
						}
					}

				}
			}

		} else {
			// 删除返单
			deleteReturnBill(data, bean);
		}

	}
	
	/**
	 * 签收单返单 价格计算规则
	 * @param bean
	 * @param guiResultBillCalculateDtos
	 * @param list
	 * @author 272311 sangwenhao
	 */
	/*
	private void getReturnBillRulePrice(WaybillPanelVo bean,
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,
			List<ValueAddDto> list) {
		if(WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
				.getReturnBillType().getValueCode())&&CollectionUtils.isNotEmpty(guiResultBillCalculateDtos) && CollectionUtils.isNotEmpty(list)){
		//标准快递 首重费用
		BigDecimal standExpFirst = null;
		for(GuiResultBillCalculateDto guiResultBillCalculateDto:guiResultBillCalculateDtos){
			if(PriceEntityConstants.PRICING_CODE_FRT.equals(guiResultBillCalculateDto.getPriceEntryCode())){
				standExpFirst = guiResultBillCalculateDto.getStandExpFirstFee();
			}
		}
		//如果是签收单返单，则要特殊处理
		if(standExpFirst!=null){
			standExpFirst = standExpFirst.divide(BigDecimal.valueOf(100));
			boolean flag = false ;//标记是否有折扣信息
			boolean flag_crm = false ;//标记crm中是否配置了首重折扣
			for(ValueAddDto valueAddDto : list){
				if(PriceEntityConstants.PRICING_CODE_QS.equals(valueAddDto.getPriceEntityCode())){
					//查询CRM 客户合同信息
					PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(bean);
					//判断是否有 签收回单 合同信息 
					if(preferentialInfoDto != null ){
					if(preferentialInfoDto.getfSigleTicketSignfees()!=null || preferentialInfoDto.getSingleSignDiscount()!=null){
						flag = true ;
						//单票签收单手续费
						BigDecimal fSigleTicketSignfees = preferentialInfoDto.getfSigleTicketSignfees();
						//如果单票签收单手续费不为空，则直接去单票手续费
						if (fSigleTicketSignfees!=null) {
							valueAddDto.setFee(fSigleTicketSignfees);
							//将折扣价格赋值原计算后价格
							valueAddDto.setCaculateFee(fSigleTicketSignfees);
							//将折扣价格赋值折扣价格
							valueAddDto.setDiscountFee(fSigleTicketSignfees);
							//保留计价明细ID
//								valueAddDto.setId(discountResultDto.getId());
							//折扣费率
							if(valueAddDto.getActualFeeRate()!=null){
								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
								//保留原有费率
								valueAddDto.setInitFeeRate(actualFeeRate);
							}
							//保留折扣相关信息
							List<PriceDiscountDto> priceDiscountDtos = valueAddDto.getDiscountPrograms() ;
							if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
								for(PriceDiscountDto priceDiscountDto : priceDiscountDtos ){
									priceDiscountDto.setOriginnalCost(standExpFirst);//原始费用
									priceDiscountDto.setReduceFee(standExpFirst.subtract(fSigleTicketSignfees));//设置减免的费用
									priceDiscountDto.setDiscountRate(fSigleTicketSignfees.divide(standExpFirst,2, BigDecimal.ROUND_HALF_UP));//设置折扣率
								}//for end
							}//if end
						}else{//签收单返回单 折扣率 不为空
							BigDecimal singleSignDiscount = preferentialInfoDto.getSingleSignDiscount();
							BigDecimal fee = standExpFirst.multiply(singleSignDiscount).setScale(0, BigDecimal.ROUND_HALF_UP) ;
							valueAddDto.setFee(fee) ;
							//将折扣价格赋值原计算后价格
							valueAddDto.setCaculateFee(fee);
							//将折扣价格赋值折扣价格
							valueAddDto.setDiscountFee(fee);
							//保留计价明细ID
//								valueAddDto.setId(guiResultBillCalculateDtos.get.getCrmId());
							//折扣费率
							if(valueAddDto.getActualFeeRate() != null){
								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
								//保留原有费率
								valueAddDto.setInitFeeRate(actualFeeRate);
								//重新设置费率
								if(actualFeeRate!=null){
									valueAddDto.setActualFeeRate(singleSignDiscount.multiply(actualFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP));
								}
							}
							//设置画布中的折扣信息
							List<PriceDiscountDto> priceDiscountDtos = valueAddDto.getDiscountPrograms() ;
							if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
								for(PriceDiscountDto priceDiscountDto : priceDiscountDtos ){
									priceDiscountDto.setOriginnalCost(standExpFirst);//原始费用
									priceDiscountDto.setReduceFee(standExpFirst.multiply(BigDecimal.ONE.subtract(singleSignDiscount)).setScale(2, BigDecimal.ROUND_HALF_UP)) ;//设置减免的费用
									priceDiscountDto.setDiscountRate(singleSignDiscount);//设置折扣率
								}//for end
							}//if end
						}
					}else{
						//取合同的 首重折扣
						if(preferentialInfoDto != null && preferentialInfoDto.getChargeRebate()!=null){
							flag = true ;
							flag_crm = true ;
						}
					}
				  }else{
					  //CRM中没有配置信息，直接取 折扣方案的 折扣信息
					  ExpressDiscountDto expressDiscountDto = guiResultBillCalculateDtos.get(0).getExpressDiscountDto() ;
					  if(expressDiscountDto != null){
						  //折扣方案中的首重折扣
						  BigDecimal firstDiscountRate = expressDiscountDto.getFirstDiscountRate() ;
						 if(firstDiscountRate!=null ){
							 flag = true ;
							 BigDecimal firstDiscount =  standExpFirst.multiply(firstDiscountRate).setScale(0, BigDecimal.ROUND_HALF_UP) ;
							 valueAddDto.setFee(firstDiscount) ;
								//将折扣价格赋值原计算后价格
								valueAddDto.setCaculateFee(firstDiscount);
								//将折扣价格赋值折扣价格
								valueAddDto.setDiscountFee(firstDiscount);
								//保留计价明细ID
//									valueAddDto.setId(guiResultBillCalculateDtos.get.getCrmId());
								//折扣费率
								if(valueAddDto.getActualFeeRate() != null){
									BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
									//保留原有费率
									valueAddDto.setInitFeeRate(actualFeeRate);
									//重新设置费率
									if(actualFeeRate!=null){
										valueAddDto.setActualFeeRate((firstDiscountRate).multiply(actualFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP));
									}
								}
								//设置画布中的折扣信息
								List<PriceDiscountDto> priceDiscountDtos = valueAddDto.getDiscountPrograms() ;
								if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
									for(PriceDiscountDto priceDiscountDto : priceDiscountDtos ){
										priceDiscountDto.setOriginnalCost(standExpFirst);//原始费用
										priceDiscountDto.setReduceFee(standExpFirst.multiply(BigDecimal.ONE.subtract(firstDiscountRate)).setScale(2, BigDecimal.ROUND_HALF_UP));//设置减免的费用
										priceDiscountDto.setDiscountRate(firstDiscountRate);//设置折扣率
									}//for end
								}//if end
						 };
					  }
				  }
				 if(flag_crm){//CRM中有首重折扣，没有签收单折扣率和单票手续费
					//首重折扣为空
					BigDecimal chargeRebate = preferentialInfoDto.getChargeRebate();//CRM中配置的运费折扣费率.
					BigDecimal fee = standExpFirst.multiply(chargeRebate).setScale(0, BigDecimal.ROUND_HALF_UP) ;
					valueAddDto.setFee(fee) ;
					//将折扣价格赋值原计算后价格
					valueAddDto.setCaculateFee(fee);
					//将折扣价格赋值折扣价格
					valueAddDto.setDiscountFee(fee);
					//保留计价明细ID
//							valueAddDto.setId(guiResultBillCalculateDtos.get.getCrmId());
					//折扣费率
					if(valueAddDto.getActualFeeRate() != null){
						BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
						//保留原有费率
						valueAddDto.setInitFeeRate(actualFeeRate);
						//重新设置费率
						if(actualFeeRate!=null){
							valueAddDto.setActualFeeRate(chargeRebate.multiply(actualFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP));
						}
					}
					//设置画布中的折扣信息
					List<PriceDiscountDto> priceDiscountDtos = valueAddDto.getDiscountPrograms() ;
					if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
						for(PriceDiscountDto priceDiscountDto : priceDiscountDtos ){
							if(PriceEntityConstants.PRICING_CODE_QS.equals(valueAddDto.getPriceEntityCode())){
								priceDiscountDto.setOriginnalCost(standExpFirst);//原始费用
								priceDiscountDto.setReduceFee(standExpFirst.multiply(BigDecimal.ONE.subtract(chargeRebate)).setScale(2, BigDecimal.ROUND_HALF_UP));//设置减免的费用
								priceDiscountDto.setDiscountRate(chargeRebate);//设置折扣率
							}
						}
					}else{
						if (preferentialInfoDto.getChargeRebate() != null) {
							PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
							priceDiscountDto.setOriginnalCost(standExpFirst);// 原始费用
							priceDiscountDto.setReduceFee(standExpFirst.multiply(BigDecimal.ONE.subtract(preferentialInfoDto.getChargeRebate())));
							priceDiscountDto.setDiscountRate(preferentialInfoDto.getChargeRebate());// 设置折扣率
							priceDiscountDto.setType("DISCOUNT");
							priceDiscountDto.setCaculateType("");
							
							//组装计费类型
							priceDiscountDto.setMarketName(valueAddDto.getPriceEntityName());
							//组装打折类型CODE
							priceDiscountDto.setSaleChannelCode(DiscountTypeConstants.DISCOUNT_TYPE__CONTRACT_NORMAL);
							//组装打折类型NAME
							priceDiscountDto.setSaleChannelName(DiscountTypeConstants.DISCOUNT_TYPE__CONTRACT_NORMAL_NAME);
							//组装计价明细ID
							priceDiscountDto.setChargeDetailId(valueAddDto.getId());

							//组装返回的折扣模型
							priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT);
							//组装返回的折扣模型
							priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT_NAME);
							
							//组装计费类型CODE
							priceDiscountDto.setPriceEntryCode(valueAddDto.getPriceEntityCode());
							priceDiscountDto.setPriceEntryName(valueAddDto.getPriceEntityName());
							//组装计费子类型
							priceDiscountDto.setSubType(valueAddDto.getSubType());
							//修复超重货服务费子类型不显示问题
							if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(priceDiscountDto.getSubType())){
								priceDiscountDto.setSubTypeName(valueAddDto.getPriceEntityName());
							}
							*//**
							 * @BUG号：DEFECT-8626
							 * @功能：获取“送货进仓”子类型不显示问题
							 *//*
							if(StringUtils.equals(PriceEntityConstants.PRICING_CODE_SHJC, priceDiscountDto.getSubType())){
								priceDiscountDto.setSubTypeName(valueAddDto.getPriceEntityName());
							}
							//组装用户合同折扣ID
							priceDiscountDto.setDiscountId(preferentialInfoDto.getId());		
							
							priceDiscountDtos = new ArrayList<PriceDiscountDto>();
							priceDiscountDtos.add(priceDiscountDto);
							valueAddDto.setDiscountPrograms(priceDiscountDtos);
							
						}
					}				
			     }else if(!flag){
					  //没有任何折扣信息
					  valueAddDto.setFee(standExpFirst) ;
						//将折扣价格赋值原计算后价格
						valueAddDto.setCaculateFee(standExpFirst);
						//将折扣价格赋值折扣价格
						valueAddDto.setDiscountFee(standExpFirst);
						//保留计价明细ID
//							valueAddDto.setId(guiResultBillCalculateDtos.get.getCrmId());
						//折扣费率
						if(valueAddDto.getActualFeeRate() != null){
							BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
							//保留原有费率
							valueAddDto.setInitFeeRate(actualFeeRate);
						}
						//设置画布中的折扣信息
						List<PriceDiscountDto> priceDiscountDtos = valueAddDto.getDiscountPrograms() ;
						if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
							for(PriceDiscountDto priceDiscountDto : priceDiscountDtos ){
								priceDiscountDto.setOriginnalCost(standExpFirst);//原始费用
								priceDiscountDto.setReduceFee(BigDecimal.ZERO);//设置减免的费用
								priceDiscountDto.setDiscountRate(BigDecimal.ONE);//设置折扣率
							}//for end
					    }
			     }//if end
				}//if end
				
			}//for end
			
		  }
		}
	}
*/
	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getReturnBillCharge(WaybillPanelVo bean,
			List<ValueAddDto> list, List<OtherChargeVo> data) {
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
				vo.setMoney(CalculateFeeTotalUtils.formatNumberInteger(dto
						.getFee().toString()));
				// 上限
				vo.setUpperLimit(dto.getMaxFee().toString());
				// 下限
				vo.setLowerLimit(dto.getMinFee().toString());
				// 是否可修改
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto
						.getCanmodify()));

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
				} else {
					vo.setIsUpdate(false);
				}

				// 是否可删除
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto
						.getCandelete()));
				vo.setId(dto.getId());
			} else {
				// 删除返单
				deleteReturnBill(data, bean);
				setReturnBillType(bean);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
			}
		} else {
			// 删除返单
			deleteReturnBill(data, bean);
			setReturnBillType(bean);
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
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
			ExpCommon.deleteOtherCharge(data, bean,
					bean.getReturnBillChargeName());
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
			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui
					.getCombReturnBillTypeModel().getElementAt(i);
			if (WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				ui.incrementPanel.getCombReturnBillType().setSelectedItem(vo);
				bean.setReturnBillType(vo);
			}
		}
	}

	/**
	 * 获取其他费用查询参数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-22 下午4:03:05
	 */
	private GuiQueryBillCalculateSubDto getQueryOtherChargeParam(
			WaybillPanelVo bean) {
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
	private void setOtherChargeDataCollection(WaybillPanelVo bean,
			List<GuiResultBillCalculateDto> dtos) {
		/* 判断是否有综合信息费与快递包装费 */
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		boolean flagZhxx = false;
		boolean flagKdbz = false;
		if (CollectionUtils.isNotEmpty(data)) {
			for (OtherChargeVo vo : data) {
				if (PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())) {
					flagZhxx = true;
				}
				if (PriceEntityConstants.PRICING_CODE_KDBZF
						.equals(vo.getCode())) {
					flagKdbz = true;
				}
		}
		}
		if (dtos != null && !dtos.isEmpty()) {
			// 设置其他费用的折扣优惠
			for (GuiResultBillCalculateDto valueAddDto : dtos) {
				// 是否为其他费用
				if (PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto
						.getPriceEntryCode())) {
					if (PriceEntityConstants.PRICING_CODE_ZHXX
							.equals(valueAddDto.getSubType())) {
						if (flagZhxx) {
							ExpCommon
									.setFavorableDiscount(
											valueAddDto.getDiscountPrograms(),
											ui, bean);
						}
					} else if (PriceEntityConstants.PRICING_CODE_KDBZF
							.equals(valueAddDto.getSubType())) {
						if (flagKdbz) {
							ExpCommon
									.setFavorableDiscount(
											valueAddDto.getDiscountPrograms(),
											ui, bean);
						}
					} else {
						ExpCommon.setFavorableDiscount(
								valueAddDto.getDiscountPrograms(), ui, bean);
				}
			}
		}
	}
	}

	private GuiQueryBillCalculateSubDto getOtherChargeDataCollection(
			WaybillPanelVo bean) {
		return getQueryOtherChargeParam(bean);
	}

	/**
	 * 
	 * 获取保价费以及最高保额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午01:51:03
	 */
	private GuiQueryBillCalculateSubDto getInsuranceCollection(
			WaybillPanelVo bean) {
		return getInsuranceParam(bean);

	}

	private void setInsuranceCollection(WaybillPanelVo bean,
			GuiResultBillCalculateDto dto) {
		if (dto != null) {
			/**
			 * 新添加gyk 若单票保价手续费字段不为空，保价金额大于0时，保价手续费均按CRM中的单票保价手续费的值计算
			 */
//			PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(bean);
//			if(preferentialInfoDto!=null){
//				if(preferentialInfoDto.getSinTicketSurePriceCharge()!=null){
//					//若单票保价手续费字段不为空，不需要考虑折扣
//					dto.setCaculateFee(preferentialInfoDto.getSinTicketSurePriceCharge());
//				}else{
//					// 设置折扣优惠
//					ExpCommon.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
//				}
//			}else{
				// 设置折扣优惠
					ExpCommon.setFavorableDiscount(dto.getDiscountPrograms(),
							ui, bean);
//			}

			setInsurance(bean, dto);// 设置保价费

		} else {
			// 清空保价费
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
			if (bean.getInsuranceFee() == null
					|| bean.getInsuranceFee().doubleValue() <= 0) {
				// bean.setMaxInsuranceAmount(BigDecimal.ZERO);
				// 保险费率
				bean.setInsuranceRate(BigDecimal.ZERO);
				// 保险手续费
				bean.setInsuranceFee(BigDecimal.ZERO);
				// 保险费ID
				bean.setInsuranceId("");
				// 保险费CODE
				bean.setInsuranceCode("");
				if (bean.getInsuranceAmount() != null
						&& bean.getInsuranceAmount().doubleValue() > 0) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.exception.nullInsuranceFee"));
				}
			}
		} else {
			/**
			 * DEFECT-543快递开单，浦江镇营业部客户40000473开单月结，保费折扣没有计入总运费中
			 * 参见ISSUE-4525快递即日退代收费率折扣、保价费率折扣变更
			 */
			// // 保险声明值最大值
			// bean.setMaxInsuranceAmount(dto.getMaxFee());
			// //将保险费率转换成千分率的格式
			// BigDecimal permillage = new
			// BigDecimal(WaybillConstants.PERMILLAGE);
			// BigDecimal feeRate =
			// Common.nullBigDecimalToZero(dto.getActualFeeRate());
			// feeRate = feeRate.multiply(permillage);
			// // 保险费率
			// bean.setInsuranceRate(feeRate);
			// BigDecimal insuranceFee = dto.getCaculateFee().setScale(0,
			// BigDecimal.ROUND_HALF_UP);// 四舍五入
			// if("0".equals(bean.getInsuranceAmount())
			// ||
			// (new BigDecimal(0).compareTo(bean.getInsuranceAmount())==0)
			// ){
			// // 保险手续费
			// bean.setInsuranceFee(BigDecimal.ZERO);
			// }else{
			// // 保险手续费
			// bean.setInsuranceFee(insuranceFee);
			// }
			//
			// // 保险费ID
			// bean.setInsuranceId(dto.getId());
			// // 保险费CODE
			// bean.setInsuranceCode(dto.getPriceEntryCode());

			/**
			 * 删除原来快递的
			 */
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = BigDecimal.ZERO;
			// 保险声明价值不为0
			if (bean.getInsuranceAmount() != null
					&& bean.getInsuranceAmount().compareTo(BigDecimal.ZERO) != 0) {
				// 保价手续费不为0
				if (bean.getInsuranceFee() != null
						&& bean.getInsuranceFee().compareTo(BigDecimal.ZERO) != 0) {
					feeRate = bean.getInsuranceFee().divide(
							bean.getInsuranceAmount(), NumberConstants.NUMBER_5,
							BigDecimal.ROUND_HALF_UP);
					feeRate = feeRate.multiply(permillage);
					bean.setInsuranceRate(feeRate);
				} else {
					if (dto != null) {
						feeRate = Common.nullBigDecimalToZero(dto
								.getActualFeeRate());
						feeRate = feeRate.multiply(permillage);
						// 保险费率
						bean.setInsuranceRate(feeRate);
						BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
						if (bean.getInsuranceAmount() == null || (BigDecimal.ZERO.compareTo(bean.getInsuranceAmount()) == 0)) {							// 保险手续费
							bean.setInsuranceFee(BigDecimal.ZERO);
						} else {
							// 保险手续费
							bean.setInsuranceFee(insuranceFee);
						}
						// 保险费ID
						bean.setInsuranceId(dto.getId());
						// 保险费CODE
						bean.setInsuranceCode(dto.getPriceEntryCode());
					}
				}
				/**
				 * 新添加gyk 若能够从crm接口中取得数据 ，那么费率按crm的数据重新计算
				 */
				if (dto.getCaculateFee() != null) {
					feeRate = dto.getCaculateFee().divide(
							bean.getInsuranceAmount(), NumberConstants.NUMBER_5,
							BigDecimal.ROUND_HALF_UP);
					feeRate = feeRate.multiply(permillage);
					bean.setInsuranceRate(feeRate);
					BigDecimal insuranceFee = dto.getCaculateFee().setScale(0,
							BigDecimal.ROUND_HALF_UP);
					bean.setInsuranceFee(insuranceFee);
				}
			}
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
		if (codAmount != null && codAmount.compareTo(zero) > 0
				&& bean.getRefundType() != null) {
			return getQueryParam(bean, PriceEntityConstants.PRICING_CODE_HK,
					bean.getCodAmount(), bean.getRefundType().getValueCode());
		} else {
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
	private void setCodCollection(WaybillPanelVo bean,
			GuiResultBillCalculateDto dto) {
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0
				&& bean.getRefundType() != null) {
			if (dto != null) {
				// 来自官网的订单不能能设置
				// if
				// (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getOrderChannel()))
				// {
				// ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);
				// // 保价申明价值
				// ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
				// // 代收货款
				// } else {
				// ui.incrementPanel.getTxtInsuranceValue().setEnabled(true); //
				// 保价申明价值
				// ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true); //
				// 代收货款
				// }
				ui.incrementPanel.getTxtInsuranceValue().setEnabled(true); // 保价申明价值

				ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
				ui.incrementPanel.getCombRefundType().setEnabled(true);
				/**
				 * 新添加gyk 若单票代收手续费字段不为空，代收金额大于0时，代收手续费均按CRM中的单票代收手续费的值计算。
				 */
//				PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(bean);
//				if(preferentialInfoDto!=null){
//					if(preferentialInfoDto.getSinTicketCollCharge()!=null){
//						dto.setCaculateFee(preferentialInfoDto.getSinTicketCollCharge());
//					}else{
//						// 设置折扣优惠
//						ExpCommon.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
//					}
//				}else{
					// 设置折扣优惠
						ExpCommon.setFavorableDiscount(
								dto.getDiscountPrograms(), ui, bean);
//				}
				setCod(bean, dto);

			} else {
				setCod(bean, null);
				throw new WaybillValidateException(
						bean.getRefundType().getValueName()
								+ i18n.get("foss.gui.creating.calculateAction.exception.nullCodAmountFee"));
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
			// 将代收货款费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			/**
			 * 新改动 gyk 若能够从crm接口中取得数据 ，那么费率按crm的数据重新计算
			 */
			BigDecimal feeRate = BigDecimal.ZERO;
			if (dto.getCaculateFee() != null) {
				feeRate = dto.getCaculateFee().divide(bean.getCodAmount(), 5,
						BigDecimal.ROUND_HALF_UP);
				feeRate = feeRate.multiply(permillage);
				bean.setCodRate(feeRate);
			} else {
				feeRate = ExpCommon
						.nullBigDecimalToZero(dto.getActualFeeRate());
				feeRate = feeRate.multiply(permillage);
			}
			// 代收货款费率
			bean.setCodRate(feeRate);
			BigDecimal codFee = dto.getCaculateFee().setScale(0,
					BigDecimal.ROUND_HALF_UP);// 四舍五入

			/**
			 * @author yangkang DMANA-3793 快递代收货款最低一票 与客户合同中读取代收货款手续费最低一票的值比较确定值
			 * 当且仅当当前代收货款手续费等于或者小于增值服务中的最低一票时
			 */
			if (codFee.compareTo(dto.getMinFee()) <= 0) {
				// 根据客户编码和产品类型查询客户优惠信息
				PreferentialInfoDto entityInfo = BaseDataServiceFactory
						.getBaseDataService()
						.queryPreferentialInfo(
								bean.getDeliveryCustomerCode(),
								null,
								ProductEntityConstants.PRICING_PRODUCT_C2_C20005);
				// 实际代收货款手续费,可能小于增值服务中配置的最低一票
				BigDecimal actualCodFee = bean.getCodAmount().multiply(
						bean.getCodRate().divide(permillage));

				if (entityInfo != null
						&& entityInfo.getLowestCharge() != null
						&& entityInfo.getLowestCharge().compareTo(
								BigDecimal.ZERO) >= 0) {
					// 当客户合同中的代收货款最低手续费值小于增值服务中配置的最低一票时，取客户合同中的最低手续费的值
					if (entityInfo.getLowestCharge().compareTo(dto.getMinFee()) < 0) {
						// 设置代收货款手续费为客户合同中的值
						codFee = entityInfo.getLowestCharge();
						// 用客户合同中配置的代收汇款手续费与实际代收货款手续费作比较 取其中的大值
						if (codFee.compareTo(actualCodFee) < 0) {
							codFee = actualCodFee;
						}
					}
				}
			}
			// 代收货款手续费金额
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
	private GuiQueryBillCalculateSubDto getQueryParam(WaybillPanelVo bean,
			String valueAddType, BigDecimal cost, String subType) {
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
	private List<GuiQueryBillCalculateSubDto> getDeliveryFeeCollection(
			WaybillPanelVo bean) {

		List<GuiQueryBillCalculateSubDto> queryBillCacilateValueAddDto = new ArrayList<GuiQueryBillCalculateSubDto>();
		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return null;
		}
		// 提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean,
					PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean,
						PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO,
						null));
			}
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean,
					PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(bean,
					PriceEntityConstants.PRICING_CODE_SHSL, null, null));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean,
						PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO,
						null));
			}
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean,
					PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(bean,
					PriceEntityConstants.PRICING_CODE_QT, null,
					PriceEntityConstants.PRICING_CODE_SHJC));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean,
						PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO,
						null));
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
	private void setDeliveryFeeCollection(WaybillPanelVo bean,
			List<GuiResultBillCalculateDto> dtos) {

		GuiResultBillCalculateDto guiResultBillCalculateDto;
		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return;
		}

		// 提货方式编码
		String code = bean.getReceiveMethod().getValueCode();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {

			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_SH, null);
			/**
			 * 如果是汽运送货费，那么需要判断是否超过最高派送费，如果超过，赋值为派送费最大值
			 */
			if (WaybillConstants.DELIVER_NOUP.equals(code)) {

				BigDecimal maxDeliveFee = null;// 设置最大派送费

				ConfigurationParamsEntity maxDeliverFeeForConfig = getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_DELIVER_NOUP_MAX_FEE);// 最大派送费

				if (maxDeliverFeeForConfig != null
						&& StringUtils.isNotEmpty(maxDeliverFeeForConfig
								.getConfValue())) {
					maxDeliveFee = new BigDecimal(
							maxDeliverFeeForConfig.getConfValue());
				}

				if (guiResultBillCalculateDto != null && maxDeliveFee != null) {
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
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			}

		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {

			// 获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			setDeliverFee(guiResultBillCalculateDto, bean, true, false);
			// 获取上楼费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_SHSL, null);
			// 设置上楼费
			setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			}

		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {

			// 获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			setDeliverFee(guiResultBillCalculateDto, bean, true, false);

			// 获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_QT,
					PriceEntityConstants.PRICING_CODE_SHJC);
			// 设置进仓费
			setDeliverFee(guiResultBillCalculateDto, bean, false, true);
			// 获取超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos,
					PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				setDeliverFee(guiResultBillCalculateDto, bean, false, false);
			}
		}

		// 送货费集合
		List<DeliverChargeEntity> deliverList = bean.getDeliverList();
		if (deliverList != null) {
			ui.canvasContentPanel.getOtherCost().setChangeDetail(deliverList);
		}

	}

	/**
	 * 本地获取系统参数
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
		return BaseDataServiceFactory.getBaseDataService()
				.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type,
						FossConstants.ROOT_ORG_CODE);

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
	private GuiResultBillCalculateDto getGuiResultBillCalculateDto(
			List<GuiResultBillCalculateDto> dtos, String valueAddType,
			String subType) {

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto
						.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {

				if (valueAddType.equals(guiResultBillCalculateDto
						.getPriceEntryCode())
						&& subType.equals(guiResultBillCalculateDto
								.getSubType())) {
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
	 * @param flag
	 *            = true 表示是送货费中的基础送货费
	 * @param isDeliverStorge
	 *            = true 表示送货费中的送货进仓费
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午04:52:25
	 */
	private void setDeliverFee(GuiResultBillCalculateDto dto,
			WaybillPanelVo bean, Boolean flag, Boolean isDeliverStorge) {
		if (dto != null) {

			DeliverChargeEntity deliver = new DeliverChargeEntity();
			// 是否激活
			deliver.setActive(FossConstants.ACTIVE);

			BigDecimal deliveryGoodsFee = dto.getCaculateFee();
			if (deliveryGoodsFee == null) {
				deliveryGoodsFee = BigDecimal.ZERO;
			} else {
				deliveryGoodsFee = deliveryGoodsFee.setScale(0,
						BigDecimal.ROUND_HALF_UP);
			}
			// 金额
			deliver.setAmount(deliveryGoodsFee);
			// 币种
			deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 费用ID
			deliver.setId(dto.getId());
			// 运单号
			deliver.setWaybillNo(bean.getWaybillNo());
			// 提货方式编码
			if (isDeliverStorge)// 送货进仓
			{
				// 费用编码
				deliver.setCode(dto.getSubType());
				// 费用名称
				deliver.setName(dto.getSubTypeName());
			} else {
				// 费用编码
				deliver.setCode(dto.getPriceEntryCode());

				// 费用名称
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
			ExpCommon.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
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
	private GuiQueryBillCalculateSubDto getVeryFarQueryParam(
			WaybillPanelVo bean, String valueAddType, BigDecimal cost,
			String subType) {
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
		DefaultComboBoxModel combBillingType = (DefaultComboBoxModel) ui
				.getCombBillingType();

		int size = combBillingType.getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combBillingType
					.getElementAt(i);
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
					.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_WEIGHT
						.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}

			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
					.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_VOLUME
						.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}
		}
	}

	/**
	 * 
	 * 计算装卸费，输入装卸费之后的费率 = （纯运费+装卸费）/计费重量
	 * 
	 */
	private void calculateServiceFee(WaybillPanelVo bean) {

		// 设置是否允许修改装卸费
		//boolean serviceChargeEnabled = 
		setServiceChargeState(bean);

		// 装卸费无效
		/*if (!serviceChargeEnabled) {
			return;
		}*/

		//装卸费规则校验
		validataServiceFee(bean);

		// 获取装卸费
		BigDecimal serivceFee = bean.getServiceFee();
		if (serivceFee != null && serivceFee.longValue() != 0) {
			// 输入的装卸费不为0的情况下，按照以下规则：
			// 输入装卸费之后的公布价运费 = 纯运费+装卸费
			// 输入装卸费之后的费率 = （纯运费+装卸费）/计费重量

			// 获取运费
			BigDecimal transportFee = bean.getTransportFee();
			// 获取费率
			BigDecimal unitPrice = bean.getUnitPrice();
			// 运费 = 运费+装卸费
			transportFee = transportFee.add(bean.getServiceFee());
			// 设置新的运费
			bean.setTransportFee(transportFee);
			// 费率 = 最新运费（运费+装卸费）/计费重量
			unitPrice = transportFee.divide(bean.getBillWeight(), 2,
					BigDecimal.ROUND_HALF_DOWN);
			// 设置新的费率
			bean.setUnitPrice(unitPrice);
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
		if (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType()
				.getValueCode())) {
			return bean.getGoodsWeightTotal();
		} else {
			// 按体积计费
			ProductEntityVo productVo = bean.getProductCode();
			// 判断是否空运
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
					.equals(productVo.getCode())) {
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
	 * 验证装卸费规则
	 * 
	 */
	private void validataServiceFee(WaybillPanelVo bean) {
		BigDecimal serivceFee = bean.getServiceFee();

		if (serivceFee == null || serivceFee.longValue() == 0) {
			return;
		}

		if (serivceFee.longValue() < 0) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creatingexp.expCalculateAction.serivceFeeExceedNegative"));
		}

		BigDecimal transportFee = bean.getTransportFee();

		// 输入费用不得高于公布价运费*快递装卸费费率的积截断小数点后的值
		BigDecimal serviceFee2 = null;
		CustomerEntity custinfo =  waybillService.queryCustomerInfoByCusCode(bean.getDeliveryCustomerCode());
		if(custinfo!=null&&StringUtils.isNotBlank(custinfo.getSetProportion())){
			BigDecimal setProportion = new BigDecimal(custinfo.getSetProportion());
			serviceFee2 = (transportFee
					.multiply(setProportion)).setScale(0,
					BigDecimal.ROUND_DOWN);
		}else{
			serviceFee2 = (transportFee
				.multiply(getServiceFeeRate(bean))).setScale(0,
				BigDecimal.ROUND_DOWN);
		}
		if (serivceFee.compareTo(serviceFee2) > 0) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creatingexp.expCalculateAction.serivceFeeExceed"));
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
			/*
			 * if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(
			 * productVo.getCode())) { dto =
			 * waybillService.queryConfigurationParamsByOrgCode
			 * (bean.getReceiveOrgCode
			 * (),ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO); }else
			 * { dto = waybillService.queryConfigurationParamsByOrgCode(bean.
			 * getReceiveOrgCode
			 * (),ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO); }
			 */
			if (CommonUtils.directDetermineIsExpressByProductCode(productVo.getCode())) {
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),
								ConfigurationParamsConstants.STL_SERVICE_EXPRESS_FEE_RATIO);
			}

			if (dto == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creatingexp.expCalculateAction.noSerivceFeeRatio"));
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
	 * DMANA-638,对上海奉贤区奉城镇营业部放开快递运单开多件货功能
	 */
	private boolean valdateExpMultiPieceDept(WaybillPanelVo bean) {
		ConfigurationParamsEntity config = waybillService
				.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.EXP_MULTI_PIECE_DEPTCODE,
						FossConstants.ROOT_ORG_CODE);
		if (config != null) {
			if (bean.getCreateOrgCode().equals(config.getConfValue())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * 在重新选择运输性质之后，对上门发货进行处理
	 * 
	 * @author 218459-foss-dongsiwei
	 */
	private void homeDelivery(ExpWaybillPanelVo bean) {
		if (bean.isHomeDelivery()) {
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable
					.getModel();
			List<OtherChargeVo> data = model.getData();
			boolean flag = false;
			for (OtherChargeVo vo : data) {
				if ("ZYSCZJH".equals(vo.getCode())) {
					flag = true;
				}
			}
			if (!flag) {
				List<ValueAddDto> list = waybillService
						.queryValueAddPriceList(ExpCommon
								.getQueryOtherChargeParam(bean, "ZYSCZJH"));
				List<OtherChargeVo> otherVo = CommonUtils
						.getOtherChargeList(list);
				if (CollectionUtils.isNotEmpty(otherVo)) {
					data.add(otherVo.get(0));
				}
				ui.incrementPanel.setChangeDetail(data);
				ExpCommon.calculateOtherCharge(ui, bean);
			}
		}
	}

	@Override
	public void setIInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}
	/**
	 * 校验快速开单一票多件外发
	 * @param bean
	 */
	public void valiteMes(ExpWaybillPanelVo bean) {
		//codAmountCanvas   canArriveExpressOneMany -一件多票 canCashOnDeliveryMany--是否货到付款 CanAgentCollectedMany--是否可代收付款
		String loadCode =bean.getLastLoadOrgCode();
		String loadName =bean.getLastLoadOrgName();
		SaleDepartmentEntity saleDepartmentEntity =waybillService.querySaleDepartmentByCode(loadCode);
		if(StringUtil.isNotEmpty(loadName) && loadName.endsWith("远郊快递营业部")&&bean.getGoodsQtyTotal()>1)
		{
			if(saleDepartmentEntity!=null)
			{
			if("N".equals(saleDepartmentEntity.getCanArriveExpressOneMany())||saleDepartmentEntity.getCanArriveExpressOneMany()==null)
			{
				// 提交
				ui.buttonPanel.getBtnSubmit().setEnabled(false);
				// 提交
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);
				//该目的站不能开一票多件
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.WaybillSubmitAction.picture.vallite"));
			}
			if ("Y".equals(saleDepartmentEntity
						.getCanArriveExpressOneMany())
						&& (saleDepartmentEntity.getCanAgentCollectedMany() == null || "N"
								.equals(saleDepartmentEntity
										.getCanAgentCollectedMany()))
						&& !"0".equals(bean.getCodAmountCanvas()))
			{//该目的站不能到快递一票多件代收货款
				// 提交
				ui.buttonPanel.getBtnSubmit().setEnabled(false);
				// 提交
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.WaybillSubmitAction.picture.vallite1"));
			}
			if("Y".equals(saleDepartmentEntity.getCanArriveExpressOneMany())&&(saleDepartmentEntity.getCanCashOnDeliveryMany()==null||"N".equals(saleDepartmentEntity.getCanCashOnDeliveryMany()))&&"FC".equals(bean.getPaidMethod().getValueCode()))
			{//该目的站不能到快递一票多件货到付款
				// 提交
				ui.buttonPanel.getBtnSubmit().setEnabled(false);
				// 提交
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.WaybillSubmitAction.picture.vallite2"));
			}
			}
		}
	}
	/**
	 * zhangfeng-270293
	 * @param bean
	 */
	private void validateGuoGuoEavyGood(WaybillPanelVo bean) {
		if ((bean.getGoodsWeightTotal() != null && WaybillConstants.GUOGUO_WIGHT
				.compareTo(bean.getGoodsWeightTotal()) < 0) 
				|| (bean.getGoodsVolumeTotal() != null && WaybillConstants.GUOGUO_VOLUE
				.compareTo(bean.getGoodsVolumeTotal()) < 0)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creatingexp.expCalculateAction.guoguoQtyWeightUpperLimit"));
		}
	}
}
