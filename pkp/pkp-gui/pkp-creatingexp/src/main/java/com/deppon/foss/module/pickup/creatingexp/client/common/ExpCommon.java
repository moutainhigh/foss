/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.common;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.core.binding.BindingAssociation;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.IBallValidateWidget;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.ui.customer.BankAccountDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.ExpQueryConsignerDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.ExpQueryMemberDialog;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.utils.PropertyFile;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpShowPickupStationDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpConcessionsPanel.WaybillDiscountCanvas;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpCalculateCostsDialog;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;


/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpCommon {

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpCommon.class);

	private static IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	private static CustomerService customerService=WaybillServiceFactory.getCustomerService();

	//体积上限
	private static final double VOLUME_UPPER_LIMIT = 0.18;
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
	 * 
	 * 设置保存按钮与提交按钮可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:24:45
	 */
	public static void setSaveAndSubmitTrue(ExpWaybillEditUI ui) {
		if (getOnLineState()) {
			// 暂存
			ui.buttonPanel.getBtnSave().setEnabled(true);
			// 提交
			ui.buttonPanel.getBtnSubmit().setEnabled(true);
			// 提交
			ui.billingPayPanel.getBtnSubmit().setEnabled(true);
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
	public static void setSaveAndSubmitFalse(ExpWaybillEditUI ui) {
		if (getOnLineState()) {
			// 暂存
			ui.buttonPanel.getBtnSave().setEnabled(true);
		} else {
			// 暂存
			ui.buttonPanel.getBtnSave().setEnabled(false);
		}

		// 提交
		ui.buttonPanel.getBtnSubmit().setEnabled(false);
		// 提交
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
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
	public static GuiQueryBillCalculateDto  getQueryParamCollection(WaybillPanelVo bean2, String yesOrNo) {
		ExpWaybillPanelVo bean =( ExpWaybillPanelVo) bean2;
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		//快递优惠活动
		if(bean.getSpecialOffer()!=null&&StringUtil.isNotEmpty(bean.getSpecialOffer().getValueCode()))
		{
			queryDto.setCityMarketCode(bean.getSpecialOffer().getValueCode());
		}
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
		
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		
		if(bean.getCalculateVolumeTotal()!=null && bean.getGoodsVolumeTotal()!=null && 
				Math.abs(bean.getCalculateVolumeTotal().doubleValue() - bean.getGoodsVolumeTotal().doubleValue() )<0.01  ){
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}else{
			/**
			 * 防止监听事件时效和修复工作流计费重量变大的问题
			 * dp-foss-dongjialing
			 * 225131
			 */
			if(StringUtil.isBlank(bean.getGoodsSize())) {
				bean.setGoodsVolumePreviousTotal(null);
			} else {
				checGoodsSize(bean);
			}
			BigDecimal qtyVolumeUpperLimit = BigDecimal.valueOf(VOLUME_UPPER_LIMIT).multiply(BigDecimal.valueOf(bean.getGoodsQtyTotal()));
			if(WaybillConstants.SERVICE_TYPE.equals(bean.getServerType())){
				if ((bean.getGoodsWeightTotal() != null && WaybillConstants.GUOGUO_WIGHT
						.compareTo(bean.getGoodsWeightTotal()) < 0) 
						|| (bean.getGoodsVolumeTotal() != null && WaybillConstants.GUOGUO_VOLUE
						.compareTo(bean.getGoodsVolumeTotal()) < 0)) {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creatingexp.expCalculateAction.guoguoQtyWeightUpperLimit"));
				}
				
			}else if( //zxy 20140103 增加bean.getGoodsWeightTotal() !=null判断
					bean.getGoodsVolumePreviousTotal() != null && qtyVolumeUpperLimit.compareTo(bean.getGoodsVolumePreviousTotal())<0){
				throw new WaybillValidateException(i18n.get("foss.gui.creatingexp.expCalculateAction.qtyWeightUpperLimit"));
			}
			if(bean.getGoodsVolumePreviousTotal()!=null && bean.getGoodsVolumePreviousTotal().doubleValue()>0){
				
				queryDto.setVolume(bean.getGoodsVolumePreviousTotal());// 体积
			}else{
				if(bean.getGoodsVolumePreviousTotal()!=null && bean.getGoodsVolumePreviousTotal().doubleValue()<=0){
					queryDto.setVolume(BigDecimal.ZERO);// 体积
				}else{
					queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
				}
				
			}
			
		}
		if(FossConstants.ACTIVE.equals(bean.getChangeVolume())){
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}
		
		if(queryDto.getVolume()==null){
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}
		
		if (bean.getFlightNumberType() == null) {
			queryDto.setFlightShift(null);// 航班号
		} else {
			queryDto.setFlightShift(bean.getFlightNumberType().getValueCode());// 航班号
		}

		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		// 当serviceType=12时运单为裹裹的单子，将客户编码设置成业务虚拟客户编码"470290159"获取裹裹自有的价格方案
		String servicetype = waybillService.queryOrderByOrderNo(bean
				.getOrderNo());
		if (StringUtil.isNotEmpty(servicetype) && "12".equals(servicetype)) {
			queryDto.setCustomerCode("470290159");
		} else {
			queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		}
		queryDto.setKilom(bean.getKilometer());//设置公里数
		
		
		List<GuiQueryBillCalculateSubDto> priceEntities =new ArrayList<GuiQueryBillCalculateSubDto>();
		GuiQueryBillCalculateSubDto guiQueryBillCalculateSubDto= new GuiQueryBillCalculateSubDto();
		guiQueryBillCalculateSubDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntities.add(guiQueryBillCalculateSubDto);
		queryDto.setPriceEntities(priceEntities);
		
		if(bean.getReceiveMethod()!=null){
			// 提货方式
			String receiveMethod = bean.getReceiveMethod().getValueCode();
			//是否自提
			if (CommonUtils.verdictPickUpSelf(receiveMethod)) {
				//试点城市和快递代理理理城市之间提货方式只能开派送
				queryDto.setIsSelfPickUp(FossConstants.YES);					
			}else{
				queryDto.setIsSelfPickUp(FossConstants.NO);
			}
		}else{
			queryDto.setIsSelfPickUp(FossConstants.YES);
		}
		//封装上门发货条件
		queryDto.setHomeDelivery(bean.isHomeDelivery());
		
		return queryDto;
	}
	
	//根据货物尺寸计算体积
		public static void calculateVolume(ExpWaybillPanelVo bean) {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			try {
				Object result = engine.eval(bean.getGoodsSize());
				BigDecimal bigDecimal = new BigDecimal(result.toString());
				bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_24, BigDecimal.ROUND_HALF_UP);
				BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
				bigDecimal = bigDecimal.divide(m);
				bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_24, BigDecimal.ROUND_HALF_UP);// 四舍五入
				// 四舍五入后如果变为0.00，那么需要给成默认的0.01，以免丢失体积
				/*if (bigDecimal.doubleValue() == 0) {
					bigDecimal = new BigDecimal("0.01");
					//体积为0，按重量计费
					bean.setChangeVolume(FossConstants.ACTIVE);
				}*/
				BigDecimal upLimit = new BigDecimal(WaybillConstants.VOLUME_UPLIMIT);
				if (bigDecimal.compareTo(upLimit) > 0) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.calculateVolume.one") + WaybillConstants.VOLUME_UPLIMIT);
					bean.setGoodsSize("");
				} else if (BigDecimal.ZERO.compareTo(bigDecimal) > 0) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.calculateVolume.two"));
					bean.setGoodsSize("");
				} else {
					bean.setGoodsVolumePreviousTotal(bigDecimal);
					if (bigDecimal.doubleValue() == 0) {
						bean.setGoodsVolumePreviousTotal(null);
				}
				
				}
				
			} catch (ScriptException e) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.one"), e);
			}
		}
		
	 private static void checGoodsSize(ExpWaybillPanelVo bean){
		 if (NumberValidate.checkIsGoodsSize(bean.getGoodsSize())) {

				String size = bean.getGoodsSize();
				String[] addAll = StringUtils.split(size, "+");
				double length = 0;
				double width = 0;
				double height = 0;

				// 需要配置的
				double minizeLength = 9999;
				if (bean.getVolumeLow() != null) {
					minizeLength = bean.getVolumeLow().doubleValue() * NumberConstants.NUMBER_100;
				}

				double maxLength = 99999;

				if (bean.getVolumeUp() != null) {
					maxLength = bean.getVolumeUp().doubleValue() * NumberConstants.NUMBER_100;
				}

				if (addAll != null && addAll.length > 0) {
					boolean haserror = false;
					for (String leng : addAll) {
						leng = StringUtils.substringBefore(leng, "-");
						String[] eachLengAll = StringUtils.split(leng, "*");
						if (eachLengAll.length < 3) {
							continue;
						}
						double minleng = 0;
						double tmplength = Double.parseDouble(eachLengAll[0]);
						String minLen = "length";
						minleng = tmplength;
						double tmpwidth = Double.parseDouble(eachLengAll[1]);
						if (tmpwidth < minleng) {
							minleng = tmpwidth;
							minLen = "width";
						}
						double tmpheight = Double.parseDouble(eachLengAll[2]);
						if (tmpheight < minleng) {
							minleng = tmpheight;
							minLen = "height";
						}
						double count = 1;
						// if(eachLengAll.length>3){
						// count = Double.parseDouble(eachLengAll[3]);
						// }
						if ("length".equals(minLen)) {
							length = length + (minleng * count);
							width = width + tmpwidth;
							height = height + tmpheight;

						}
						if ("width".equals(minLen)) {
							width = width + (minleng * count);
							height = height + tmpheight;
							length = length + tmplength;

						}
						if ("height".equals(minLen)) {
							height = height + (minleng * count);
							length = length + tmplength;
							width = width + tmpwidth;
						}

						/**
						 * KDTE-4618
						 */
						if (tmplength > minizeLength || tmpwidth > minizeLength
								|| tmpheight > minizeLength) {
							MsgBox.showInfo(i18n
									.get("foss.gui.creating.waybillDescriptor.size.ruleExpress")
									+ minizeLength
									+ i18n.get("foss.gui.creating.waybillDescriptor.size.ruleExpress2"));
							bean.setGoodsSize("");
							bean.setGoodsVolumePreviousTotal(null);
							haserror = true;
						} else if ((tmplength + tmpwidth + tmpheight) > maxLength) {
							MsgBox.showInfo(i18n
									.get("foss.gui.creating.waybillDescriptor.size.ruleExpress3")
									+ maxLength
									+ i18n.get("foss.gui.creating.waybillDescriptor.size.ruleExpress2"));
							bean.setGoodsSize("");
							bean.setGoodsVolumePreviousTotal(null);
							haserror = true;
						}

					}

					if (!haserror) {
						calculateVolume(bean);
					}
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
	public static void setUIEnableTrue(WaybillPanelVo bean,ExpWaybillEditUI ui) {
		UIUtils.enableUI(ui);
		ui.buttonPanel.getBtnPrint().setEnabled(false);// 运单打印
		ui.buttonPanel.getBtnPreview().setEnabled(false);// 运单打印预览
		ui.buttonPanel.getBtnPrintLabel().setEnabled(false);// 打印标签
		
		setSaveAndSubmitFalse(ui);
		// 接货费
		ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
		// 手写输入金额
		//ui.billingPayPanel.getTxtHandWriteMoney().setEnabled(false);
		// 收货客户编码
		ui.consigneePanel.getTxtReceiveCustomerCode().setEnabled(false);
		// 发货客户编码
		ui.consignerPanel.getTxtDeliveryCustomerCode().setEnabled(false);
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
		ui.cargoInfoPanel.getRbnA().setEnabled(false);
		// 货物类型
		ui.cargoInfoPanel.getRbnB().setEnabled(false);
		// 大车直送
		ui.cargoInfoPanel.getChbCarThrough().setEnabled(false);
		// 储运事项
		ui.cargoInfoPanel.getTxtTransportationRemark().setEnabled(false);
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
	public static void setBankInfo(WaybillPanelVo bean, ExpWaybillEditUI ui, IWaybillService waybillService) {
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

	/**
	 * 
	 * 设置银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午08:11:49
	 */
	public static void validateBankInfo(WaybillPanelVo bean, ExpWaybillEditUI ui, IWaybillService waybillService) {
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
	
	/**
	 * 
	 * 设置退款类型为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	public static void setRefundType(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
	public static void deliverStorge(WaybillPanelVo bean, ExpWaybillEditUI ui, IWaybillService waybillService) {
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
			// 比较费用名称，判断是否存在重复的返单费用
			if(StringUtil.isNotEmpty(otherVo.getChargeName())){
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
	 * 设置装卸费编辑状态-快递只允许对应部门的月结客户开装卸费
	 * 
	 */
	public static void setServiceChargeEnabled(String preferentialType,
			Boolean bool, ExpWaybillEditUI ui) {
		// 月结客户不允许开装卸费
		/*if (bool) {
			ui.incrementPanel.getTxtServiceCharge().setEnabled(false);
		} else {
			ui.incrementPanel.getTxtServiceCharge().setEnabled(true);
		}

		// 月发月送允许修改装卸费
		if (StringUtils.isNotEmpty(preferentialType)) {
			if (preferentialType
					.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
				ui.incrementPanel.getTxtServiceCharge().setEnabled(true);
			}

		}*/
		
		//通过运单UI对象获取绑定的VO对象
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();
				
		//快递只允许对应部门的月结客户开装卸费
		if (bool) {
			CusBargainVo vo=new CusBargainVo();
			vo.setExpayway(WaybillConstants.MONTH_END);
			vo.setCustomerCode(bean.getDeliveryCustomerCode());
			vo.setBillDate(new Date());
			vo.setBillOrgCode(bean.getReceiveOrgCode());
			boolean  isOk = waybillService.isCanPaidMethodExp(vo);
			if(!isOk){
				ui.incrementPanel.getTxtServiceCharge().setEnabled(false);
				bean.setServiceFee(BigDecimal.ZERO);
				bean.setServiceFeeCanvas(String.valueOf('0'));
			}else{
				ui.incrementPanel.getTxtServiceCharge().setEnabled(true);
			}
		}else{
			ui.incrementPanel.getTxtServiceCharge().setEnabled(false);
			bean.setServiceFee(BigDecimal.ZERO);
			bean.setServiceFeeCanvas(String.valueOf('0'));
		}

	}

	/**
	 * 设置导入PDA订单时，值改变就设置颜色的控件与值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-31 上午8:41:41
	 */
	public static Map<JTextField, String> getChangeColorTxt(ExpWaybillEditUI ui, WaybillPanelVo vo) {
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
	public static void setForegroundColor(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		// 变更前的键值对
		Map<JTextField, String> last = ExpCommon.getChangeColorTxt(ui, bean);
		// 变更后的键值对
		Map<JTextField, String> old = bean.getChangeColorTxt();

		if(old!=null){
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
		
	}

	/**
	 * 
	 * 空运提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午08:47:33
	 */
	public static void airReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, ExpWaybillEditUI ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();

		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsAir();
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
				bean.setReceiveMethod(vo);
			}
		}
	}

	/**
	 * 
	 * 验证只有合同客户才允许有免费送货
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午03:36:46
	 */
	public static void validateDeliverFree(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		if(bean.getReceiveMethod()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.transferInfoPanel.receiveMethod.isNull"));
		}
		
		if (WaybillConstants.DELIVER_FREE.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_FREE_AIR.equals(bean.getReceiveMethod().getValueCode())) {
			if (bean.getAuditNo() == null || "".equals(bean.getAuditNo())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.nullAuditNo"));
				ui.transferInfoPanel.getCombPickMode().requestFocus();
				// 强行中断
				throw new WaybillValidateException("");

			} else {
				/**
				 * 现在只根据客户编码查询是否为合同客户，取消部门限制。
				 */
				CusBargainEntity eneity = waybillService.queryCusBargainByParams(bean.getAuditNo(), null);
				if (eneity == null) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.common.msgBox.nullAuditNo"));
					ui.transferInfoPanel.getCombPickMode().requestFocus();
					// 强行中断
					throw new WaybillValidateException("");
				} else {
					// 表示合同是有效，则不需要进行处理
				}
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
	public static void highwaysReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, ExpWaybillEditUI ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();

		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
		for (DataDictionaryValueEntity dataDictionary : list) {
			if(  //WaybillConstants.INNER_PICKUP.equals(dataDictionary.getValueCode())||
//					WaybillConstants.SELF_PICKUP.equals(dataDictionary.getValueCode())||
					//DMANA-5443 FOSS开单冻结自提功能
					WaybillConstants.DELIVER_UP.equals(dataDictionary.getValueCode())
					){
				DataDictionaryValueVo vo = new DataDictionaryValueVo();	
				ValueCopy.valueCopy(dataDictionary, vo);
				pikcModeModel.addElement(vo);
				
				if (WaybillConstants.DELIVER_UP.equals(vo.getValueCode())){// 设置提货方式默认值
					
					bean.setReceiveMethod(vo);
				}
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
	public static void requestFocus(ExpWaybillEditUI ui) {
		// 为了解决数据修改后，数据改变事件还未进行就已经暂存货提交
		ui.basicPanel.getTxtWaybillNO().requestFocus();
	}

	/**
	 * 离线导入、已开单导入、暂存导入等导入操作时需要重新走一遍设置走货线路的业务逻辑
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午4:49:37
	 */
	public static void setLoadLine(ExpWaybillEditUI ui, WaybillPanelVo vo) {
		// 提货网点处理类
		ExpShowPickupStationDialogAction action = new ExpShowPickupStationDialogAction();
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
	public static void innerPickup(ExpWaybillPanelVo bean, ExpWaybillEditUI ui) {
		// 判断是否内部带货自提
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
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
	public static void selfPickup(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
			ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(false);
			// 送货费
			bean.setDeliveryGoodsFee(BigDecimal.ZERO);
			bean.setDeliveryGoodsFeeCanvas(BigDecimal.ZERO.toString());
		} else {
			ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(  false   );//true);
		}
	}

	/**
	 * 
	 * 空运、偏线以及中转下线无法选择签收单返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:09:08
	 */
	public static void setReturnBill(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		if (null == productVo) {
			return;
		}

		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode()) || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			ui.incrementPanel.getCombReturnBillType().setEnabled(false);
			// 设置返单类型默认值
			ExpCommon.setReturnBillType(bean, ui.getCombReturnBillTypeModel());
			// 将返单费用设置到其他费用表格中
			ExpCommon.setReturnBillCharge(bean, ui);
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
	public static void setReturnBillCharge(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {
			if (data == null || data.isEmpty()) {
				data = new ArrayList<OtherChargeVo>();
			}

			List<ValueAddDto> list = waybillService.queryValueAddPriceList(ExpCommon.getQueryOtherChargeParam(bean, bean.getReturnBillType().getValueCode()));
			OtherChargeVo otherVo = ExpCommon.getReturnBillCharge(bean, list, data, ui);
			// 添加返单费用到其他费用表格
			String chargeName = ExpCommon.addOtherCharge(data, otherVo, bean);
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
	public static void setSecretPrepaid(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		if (null == productVo) {
			return;
		}
		//偏线空运 、开单  付款方式为到付 或临时欠款 预付保密费都不能勾选
		DataDictionaryValueVo dataDictionaryValueVo = (DataDictionaryValueVo)ui.incrementPanel.getCombPaymentMode().getSelectedItem();
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode()) 
				|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())
				|| (dataDictionaryValueVo != null && WaybillConstants.TEMPORARY_DEBT.equals(dataDictionaryValueVo.getValueCode()))
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
	public static void cleanOtherCharge(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();
		// 清空表格
		ui.incrementPanel.setChangeDetail(voList);
		// 其他费用
		bean.setOtherFee(BigDecimal.ZERO);
		// 画布其他费用
		bean.setOtherFeeCanvas(BigDecimal.ZERO.toString());
		// 其他费用被清理之后，将返单类型设置为默认值
		setReturnBillType(bean, ui.getCombReturnBillTypeModel());
	}

	/**
	 * 
	 * 删除返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午03:52:12
	 */
	private static void deleteReturnBill(List<OtherChargeVo> data, WaybillPanelVo bean, ExpWaybillEditUI ui) {
		if (data != null && !data.isEmpty()) {
			// 将已有的返单费用从其他费用表格中删除
			ExpCommon.deleteOtherCharge(data, bean, bean.getReturnBillChargeName());
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
	private static OtherChargeVo getReturnBillCharge(WaybillPanelVo bean, List<ValueAddDto> list, List<OtherChargeVo> data, ExpWaybillEditUI ui) {
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
	public static void changePickUpMode(ExpWaybillPanelVo bean, ExpWaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			// 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
			ExpCommon.setAirPropertyToTrue(ui);
			// 空运提货方式
			ExpCommon.airReceiveMethod(waybillService, bean, ui);
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
					ui.transferInfoPanel.getCombPickMode().setEnabled(false);
				}else{
					ui.transferInfoPanel.getCombPickMode().setEnabled(true);
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
			
		}else {
			// 如果运输性质不为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
			ExpCommon.setAirPropertyToFalse(bean, ui);
			// 汽运提货方式
			ExpCommon.highwaysReceiveMethod(waybillService, bean, ui);
			// 除精准卡航，其他运输性质提货网点均可编辑
			ui.transferInfoPanel.getCombPickMode().setEnabled(true);
		}
	}
	
	/**
	 * 
	 * 根据合票方式的改变，改变提货方式
	 * 
	 * @author WangQianJin
	 * @date 2013-04-23
	 */
	public static void changePickUpModeByHop(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			// 空运提货方式
			ExpCommon.airReceiveMethod(waybillService, bean, ui);
		}
	}

	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private static void setAirPropertyToTrue(ExpWaybillEditUI ui) {
		ui.transferInfoPanel.getCombFreightMethod().setEnabled(true);
		ui.transferInfoPanel.getCombFlightNumberType().setEnabled(true);

		ui.cargoInfoPanel.getRbnA().setVisible(false);
		ui.cargoInfoPanel.getRbnB().setVisible(false);
		ui.cargoInfoPanel.getCombGoodsType().setVisible(true);
	}

	/**
	 * 
	 * 如果运输性质为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午06:21:22
	 */
	private static void setAirPropertyToFalse(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		ui.transferInfoPanel.getCombFreightMethod().setEnabled(false);
		ui.transferInfoPanel.getCombFlightNumberType().setEnabled(false);

		ui.cargoInfoPanel.getRbnA().setVisible(true);
		ui.cargoInfoPanel.getRbnB().setVisible(true);
		ui.cargoInfoPanel.getCombGoodsType().setVisible(false);

		// 航班类型
		ExpCommon.setFlightNumberType(bean, ui);
		// 合票方式
		ExpCommon.setFreightMethod(bean, ui);
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
	private static void setFlightNumberType(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
	private static void setFreightMethod(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
	public static void validateCustomerPointBySelfPickup(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		// 获得网点
		BranchVo customerPickupOrgCode = bean.getCustomerPickupOrgCode();

		if (customerPickupOrgCode != null && customerPickupOrgCode.getPickupSelf() != null && customerPickupOrgCode.getDelivery() != null) {

			if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {

				// 不支持自提
				if (!FossConstants.YES.equals(customerPickupOrgCode.getPickupSelf())) {

					// 清空目的站
					ExpCommon.setTargetEmpty(bean);
				}
			} else if (WaybillConstants.DELIVER_FREE.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_STORAGE.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_NOUP.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_FREE_AIR.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_NOUP_AIR.equals(bean.getReceiveMethod().getValueCode())
					|| WaybillConstants.DELIVER_UP_AIR.equals(bean.getReceiveMethod().getValueCode()) || WaybillConstants.DELIVER_INGA_AIR.equals(bean.getReceiveMethod().getValueCode())) {

				// 不支持送货上门
				if (!FossConstants.YES.equals(customerPickupOrgCode.getDelivery())) {

					// 清空目的站
					ExpCommon.setTargetEmpty(bean);
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
	public static void resetZero(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
		//ui.buttonPanel.getBtnSearchMember().setEnabled(false);
		// 查询收货客户
		//ui.consigneePanel.getBtnQuery().setEnabled(false);
		// 查询发货客户
		//ui.consignerPanel.getBtnQuery().setEnabled(false);

		// 计费付款面板
		//bean.setTransportFee(BigDecimal.ZERO);// 公布价运费
		bean.setValueAddFee(BigDecimal.ZERO);// 增值服务费
		bean.setPromotionsFee(BigDecimal.ZERO);// 优惠合计
		bean.setPrePayAmount(BigDecimal.ZERO);// 预付金额
		bean.setToPayAmount(BigDecimal.ZERO);// 到付金额
		bean.setHandWriteMoney(BigDecimal.ZERO);// 手写现付金额
		bean.setTotalFee(BigDecimal.ZERO);

		// 画布
		//bean.setBillWeight(BigDecimal.ZERO);// 计费重量
		//bean.setUnitPrice(BigDecimal.ZERO);// 费率
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
	 * 
	 * 不是内部带货则恢复编辑状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午08:22:45
	 */
	private static void recover(ExpWaybillEditUI ui) {

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
	public static void setQueryDeliveryCustomer(ExpWaybillEditUI ui) {
		// 设置数据据
		IBinder<ExpWaybillPanelVo> binder = ui.getBindersMap().get("waybillBinder");
		// 将获得的值传到界面上
		WaybillPanelVo waybillBean = binder.getBean();
		
		// 定义全局对象，用来判断该窗口是否已创建，已节约资源
		ExpQueryMemberDialog panel = new ExpQueryMemberDialog(waybillBean);
		// 居中显示弹出窗口
		WindowUtil.centerAndShow(panel);
		// 获得VO
		QueryMemberDialogVo memberBean = panel.getMemberVo();
		if (null == memberBean) {
			return;
		}

		ExpCommon.resetDeliverGoodsFee(waybillBean, ui);

		// 填充发货客户信息
		fillDeliveryCustomerInfo(ui, memberBean, waybillBean);
		
		if(WaybillConstants.YES.equals(memberBean.getIsElectricityToEnjoy())){
		ExpCommon.setProductCode(ui, waybillBean,
				WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
	   }/*else{
		   ExpCommon.setProductCode(ui, waybillBean,
					WaybillConstants.ROUND_COUPON_PACKAGE);
	   }*/
		
		}
	
	
	private static void setCustomerInvoice(String custCode,QueryMemberDialogVo memberBean){		
		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 判断编码是否为空
		if (StringUtils.isNotEmpty(custCode)) {
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setCustCode(custCode);
			dto.setExactQuery(true);
			dtoList.add(dto);
		}
		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = waybillService.queryCustomerByConditionList(dtoList);
		if (CollectionUtils.isNotEmpty(contactList)) {			
			// 获得弹出窗口选择的值
			CustomerQueryConditionDto contact = contactList.get(0);
			if(WaybillConstants.INVOICE_01.equals(contact.getInvoiceType())){
				memberBean.setInvoice(WaybillConstants.INVOICE_01);
			}else{
				memberBean.setInvoice(WaybillConstants.INVOICE_02);
			}
		}
	}
	
	/**
	 * 
	 * 内部带货时，转化内部带货时按钮和输入框状态
	 * 
	 */
	
	public static void transInnerPickupFeeBurdenDeptStat(ExpWaybillEditUI ui,WaybillPanelVo bean,boolean isFillData) {
		//如果不是内部带货，则显示发货联系人，隐藏按钮
		if(!WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())){			
			ui.consignerPanel.getTxtConsignerLinkMan().setEnabled(true);
			if(isFillData){
				ui.consignerPanel.getTxtConsignerLinkMan().setText(bean.getDeliveryCustomerContact());
			}else{
				ui.consignerPanel.getTxtConsignerLinkMan().setText("");
			}
			ui.consignerPanel.getLblLinkMan().setText("*"+i18n.get("foss.gui.creating.consignerPanel.consignerLinkMan.label")+"：");
			ui.consignerPanel.getBtnConsignerDept().setVisible(false);
		}else{
			// 如果是内部带货，则显示内部带货费用承担部门,展示按钮
			ui.consignerPanel.getTxtConsignerLinkMan().setText("");
			ui.consignerPanel.getTxtConsignerLinkMan().setEnabled(false);
			ui.consignerPanel.getLblLinkMan().setText("*"+i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.expInnerPickupFeeBurdenDept.label")+"：");
			ui.consignerPanel.getBtnConsignerDept().setVisible(true);
		}
		bean.setInnerPickupFeeBurdenDept(null);
	}
		

	/**
	 * 填充发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午9:14:31
	 */
	public static void fillDeliveryCustomerInfo(ExpWaybillEditUI ui, QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean) {
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
		//电商尊享
		waybillBean.setIsElectricityToEnjoy(StringUtils.isEmpty(memberBean.getIsElectricityToEnjoy()) ? FossConstants.NO : memberBean.getIsElectricityToEnjoy());
		// 客户编码
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.ALIBABA.equals(waybillBean.getOrderChannel())){
			waybillBean.setDeliveryCustomerCode(memberBean.getCustomerCode());
		}
		
		//获取发票标记
		setCustomerInvoice(memberBean.getCustomerCode(),memberBean);
		// 大客户标记
		//之前的业务逻辑：
		//waybillBean.setDeliveryBigCustomer(memberBean.getIsBigCustomer());
		/**
		 * DMANA-2815:添加大客户标识
		 * @author 200945-wutao
		 * 是否是大客户
		 * @date 2014-10-27
		 * 现在的业务逻辑
		 */
		waybillBean.setDeliveryBigCustomer(memberBean.getIsExpressBigCustomer());
		//end
		
		// 客户是否有精确代收款 liyongfei-DMANA-2352
		waybillBean.setAccurateCollection(memberBean.getAccurateCollection());

		//(参见DEFECT-543)
		waybillBean.setHandInsuranceFee(false);
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
		// 定义省市区县对象
		AddressFieldDto address = ExpCommon.getAddressFieldInfoByCode(ui, memberBean.getProvinceCode(), memberBean.getCityCode(), memberBean.getCountyCode());
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
		// 设置界面"省市区县DTO"对象:适用于通过手机号码等带出的省市区县
		ui.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(address);
		ui.getConsignerPanel().getTxtConsignerAddressNote().setText(waybillBean.getDeliveryCustomerAddressNote());
		// 设置装卸费是的编辑状态
		ExpCommon.setServiceChargeEnabled(memberBean.getPreferentialType(),memberBean.getChargeMode(), ui);
		// 客户信息发生改变，需要清空上一个客户的代收货款收款信息
		ExpCommon.cleanBankInfo(waybillBean);
		// 如果是内部带货，则显示内部带货费用承担部门,展示按钮
		ExpCommon.transInnerPickupFeeBurdenDeptStat(ui,waybillBean,true);
		
		//是否为大客户标识
		//使用快递大客户标识 @author- wutao
		if(FossConstants.ACTIVE.equals(memberBean.getIsExpressBigCustomer())){
			//设置大客户标记
			ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			//取消大客户标记
			ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		}
		/**
		 * @author 200945 - wutao
		 */
		
		setExpDeliverySettleAndContactAndRemending(memberBean,waybillBean);
		//resetExpCombPaymentMode(waybillBean,ui);
		
		
		if(WaybillConstants.DEPPON_CUSTOMER.equals(waybillBean.getDeliveryCustomerCode())){
			waybillBean.setCodAmount(BigDecimal.ZERO);
			waybillBean.setInsuranceFee(BigDecimal.ZERO);
			waybillBean.setInsuranceAmount(BigDecimal.ZERO);
			waybillBean.setInsuranceAmount(BigDecimal.ZERO);// 保险声明价
			waybillBean.setCodAmount(BigDecimal.ZERO);// 代收货款
			waybillBean.setPackageFee(BigDecimal.ZERO);// 包装费
			waybillBean.setDeliveryGoodsFee(BigDecimal.ZERO);// 送货费
			waybillBean.setServiceFee(BigDecimal.ZERO);// 装卸费
			waybillBean.setPickupFee(BigDecimal.ZERO);// 接货费
			waybillBean.setOtherFee(BigDecimal.ZERO);// 其他费用合计
			waybillBean.setAccountName("");// 收款人
			waybillBean.setAccountCode("");// 收款账号
			
			// 计费付款面板
			waybillBean.setTransportFee(BigDecimal.ZERO);// 公布价运费
			waybillBean.setValueAddFee(BigDecimal.ZERO);// 增值服务费
			waybillBean.setPromotionsFee(BigDecimal.ZERO);// 优惠合计
			waybillBean.setPrePayAmount(BigDecimal.ZERO);// 预付金额
			waybillBean.setToPayAmount(BigDecimal.ZERO);// 到付金额
			waybillBean.setHandWriteMoney(BigDecimal.ZERO);// 手写现付金额
			waybillBean.setTotalFee(BigDecimal.ZERO);

			// 画布
			waybillBean.setBillWeight(BigDecimal.ZERO);// 计费重量
			waybillBean.setUnitPrice(BigDecimal.ZERO);// 费率
			waybillBean.setTransportFeeCanvas("0");// 公布价运费
			waybillBean.setInsuranceAmountCanvas("0");// 保价声明
			waybillBean.setInsuranceRate(BigDecimal.ZERO);// 保价费率
			waybillBean.setInsuranceFee(BigDecimal.ZERO);// 保价费

			waybillBean.setCodAmountCanvas("0");// 代收货款
			waybillBean.setCodRate(BigDecimal.ZERO);// 代收费率
			waybillBean.setCodFee(BigDecimal.ZERO);// 代收手续费

			waybillBean.setPickUpFeeCanvas("0");// 接货费
			waybillBean.setDeliveryGoodsFeeCanvas("0");// 送货费
			waybillBean.setPackageFeeCanvas("0");// 包装费
			waybillBean.setServiceFeeCanvas("0");// 装卸费

			waybillBean.setOtherFeeCanvas("0");// 其他费用
			waybillBean.setPromotionsFeeCanvas("0");// 优惠合计
			waybillBean.setTotalFeeCanvas("0");// 总费用
			
			JXTable table = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
			List<OtherChargeVo> data = model.getData();
			if(data!=null){
				data.removeAll(data);
				ui.incrementPanel.setChangeDetail(data);
			}
			
			DefaultComboBoxModel combPaymentModeModel = ui.getCombPaymentModeModel();
			for (int i = 0; i < combPaymentModeModel.getSize(); i++) {
				DataDictionaryValueVo vo = (DataDictionaryValueVo) combPaymentModeModel.getElementAt(i);
				if (WaybillConstants.CASH_PAYMENT.equals(vo.getValueCode())) {
					waybillBean.setPaidMethod(vo);
				}
			}
			
			
			//DefaultComboBoxModel combRefundTypeModel = ui.getCombRefundTypeModel();
			waybillBean.setRefundType(null);
			
			ExpCommon.setReturnBillType(waybillBean,ui.getCombReturnBillTypeModel());
			
			ui.getIncrementPanel().getBtnNew().setEnabled(false);
			ui.getIncrementPanel().getCombReturnBillType().setEditable(false);
			ui.getIncrementPanel().getTxtInsuranceValue().setEditable(false);
			ui.getIncrementPanel().getTxtCashOnDelivery().setEditable(false);
			
			
			ui.getIncrementPanel().getCombRefundType().setEnabled(false);
			ui.getIncrementPanel().getCombReturnBillType().setEnabled(false);
			ui.getIncrementPanel().getCombPaymentMode().setEnabled(false);
			ui.getIncrementPanel().getTxtInsuranceValue().setEnabled(false);
			ui.getIncrementPanel().getTxtCashOnDelivery().setEnabled(false);
			
		}else{
			
			ui.getIncrementPanel().getTxtInsuranceValue().setEditable(true);
			ui.getIncrementPanel().getTxtCashOnDelivery().setEditable(true);
			ui.getIncrementPanel().getCombReturnBillType().setEditable(true);
			
			ui.getIncrementPanel().getCombRefundType().setEnabled(true);
			ui.getIncrementPanel().getCombReturnBillType().setEnabled(true);
			/**
			 * Dmana-9885当订单来源为巨商汇的时候，不修改付款方式
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-02-03下午13:43
			 */
			if(!WaybillConstants.ALIBABA.equals(waybillBean.getOrderChannel())){
				ui.getIncrementPanel().getCombPaymentMode().setEnabled(true);
			}
			ui.getIncrementPanel().getTxtInsuranceValue().setEnabled(true);
			ui.getIncrementPanel().getTxtCashOnDelivery().setEnabled(true);
			
			ui.getIncrementPanel().getBtnNew().setEnabled(true);
		}
		
		ExpWaybillPanelVo vo=new ExpWaybillPanelVo();
		if(waybillBean instanceof ExpWaybillPanelVo){
			vo=(ExpWaybillPanelVo)waybillBean;
		}
		// 判断修改发货人信息之前是否已经勾选了上门发货
		if(vo.isHomeDelivery()){
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
			List<OtherChargeVo> data = model.getData();	
			CusBargainEntity cusBargainEntity=null;
			if(StringUtils.isNotEmpty(vo.getDeliveryCustomerCode())){							
				cusBargainEntity=waybillService.queryCusBargainByCustCode(vo.getDeliveryCustomerCode());
			}
			if(cusBargainEntity !=null){
				vo.setHomeDelivery(false);
				for (int i = 0; i < data.size(); i++) {
					if("ZYSCZJH".equals(data.get(i).getCode())){
						data.remove(i);
					}
				}
				ui.incrementPanel.setChangeDetail(data);
				ExpCommon.calculateOtherCharge(ui, vo);
				ui.basicPanel.getHomeDelivery().setEnabled(false);
			}
		}else{
			
			//校验该客户能否上门发货
			if(calculateHomeDelivery(vo)){
				ui.basicPanel.getHomeDelivery().setEnabled(true);
			}else{
				ui.basicPanel.getHomeDelivery().setEnabled(false);
			}
		}	
		
		/**
		 * 将QueryMemberDialogVo中的vipInsuranceAmount数据填充至WaybillPanelVo的vipInsuranceAmount中
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-11 下午14:06
		 */
		waybillBean.setVipInsuranceAmount(memberBean.getVipInsuranceAmount());
		
		/**
		 * 将QueryMemberDialogVo中的VipCollectionPaymentLimit数据填充至WaybillPanelVo的VipCollectionPaymentLimit中
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-15 上午10:59
		 */
		waybillBean.setVipCollectionPaymentLimit(memberBean.getVipInsuranceAmount());
		ui.consignerPanel.getTxtConsignerLinkMan().setEditable(false);
	}
	/**
	 * 收货客户：【是否统一结算】【合同部门】【催款部门】
	 * @param memberBean
	 * @param waybillBean
	 * 业务逻辑：
	 * ①发货客户的“是否统一结算”、“合同部门”、“催款部门”根据客户编码从客户信息中自动获得，不可手工修改。若未维护此客户信息，则“是否统一结算”默认为“否”、合同部门为空；
	 * ②运单开单发票标记为02时 : 若发货客户统一结算标记为”是”时,则运单上”发货客户是否统一结算”为”是”,”发货客户合同部门”为对应合同部门、“催款部门”为合同对应催款部门; 若发货客户统一结算标记为”否”时,则运单上”发货客户是否统一结算”为”否”,”发货客户合同部门” 发货客户“催款部门”为空
	 * ③01运单发货客户不进行始发统一结算，若运单的发票标记为01,则运单的”发货客户是否统一结算“默认为”否“、合同部门为空、催款部门”为空
	 */
	private static void setExpDeliverySettleAndContactAndRemending(QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean){
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
	public static void resetExpCombPaymentMode(WaybillPanelVo bean,ExpWaybillEditUI ui){
		List<DataDictionaryValueEntity> list = waybillService
				.queryPaymentMode();
		
		if(WaybillConstants.IS_NOT_NULL_FOR_AI.equals(bean.getStartCentralizedSettlement())){
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
	public static void setQueryReceiveCustomer(ExpWaybillEditUI ui) {
		// 获得绑定对象
		IBinder<ExpWaybillPanelVo> binder = ui.getBindersMap().get("waybillBinder");
		
		// 将获得的值传到界面上
		WaybillPanelVo bean = binder.getBean();
		ExpQueryMemberDialog panel = new ExpQueryMemberDialog(bean);
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
	 * 填充收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午9:14:31
	 */
	public static void fillReceiveCustomerInfo(ExpWaybillEditUI ui, QueryMemberDialogVo memberBean, WaybillPanelVo bean) {
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
		// 地址
		bean.setReceiveCustomerAddress(memberBean.getAddress());
		// 地址备注
		bean.setReceiveCustomerAddressNote(memberBean.getCustAddrRemark());
		// 客户名称
		bean.setReceiveCustomerName(memberBean.getCustomerName());
		// 客户编码
		bean.setReceiveCustomerCode(memberBean.getCustomerCode());
		
		//获取发票标记
		setCustomerInvoice(memberBean.getCustomerCode(),memberBean);
		//设置是否统一结算
		setExpReceiveSettleAndContactAndRemending(memberBean,bean);
		// 收货客户是否是大客户
		//之前的业务逻辑
		//bean.setReceiveBigCustomer(memberBean.getIsBigCustomer());
		/**
		 * @author wutao -- 200945
		 * @date 2014-11-28
		 * 设置收货可会是否是大客户
		 */
		bean.setReceiveBigCustomer(memberBean.getIsExpressBigCustomer());
		//end
		// 客户ID
		bean.setReceiveCustomerId(memberBean.getCustId());
		// 联系人ID
		bean.setReceiveCustomerContactId(memberBean.getConsignorId());

		// 定义省市区县对象
		AddressFieldDto address = ExpCommon.getAddressFieldInfoByCode(ui, memberBean.getProvinceCode(), memberBean.getCityCode(), memberBean.getCountyCode());
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
		ui.getConsigneePanel().getTxtConsigneeAddressNote().setText(bean.getReceiveCustomerAddressNote());
		// 客户信息发生改变，需要清空上一个客户的代收货款收款信息
		ExpCommon.cleanBankInfo(bean);
		// 设置接送货地址ID
		bean.setContactAddressId(memberBean.getAddressId());
		
		//wutao
		setExpReceiveSettleAndContactAndRemending(memberBean,bean);
		//是否为大客户标识
		//设置快递大客户标示 @author wutao
		if(FossConstants.ACTIVE.equals(memberBean.getIsExpressBigCustomer())){
			//设置大客户标记
			ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			//取消大客户标记
			ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
	}
	}

	/**
	 * 快递：
	 * 收货客户：
	 * @param memberBean
	 * @param waybillBean
	 * 业务逻辑：
	 * ①	收货客户的【是否统一结算】、【合同部门】根据客户编码从客户信息中自动获得，不可手工修改。若未维护此客户信息，则【是否统一结算】默认为【否】、【合同部门】为【空】、【催款部门】为【空】；
	 * ②	收货客户的发票标记为01时,只有快递业务可以统一结算:若收货客户为01客户，则必须同时满足运单为快递运单且该客户的 “是否统一结算”为“是”的条件，运单上“收货客户是否统一结算”才能为“是”,”收货客户合同部门”为其对应合同部门、收货客户的“催款部门”取合同对应的催款部门；
	 * 		若该客户“是否统一结算”为“否”，则运单上“收货客户是否统一结算”为“否”,”收货客户合同部门”为空、收货客户的“催款部门”为空
	 * ③	收货客户发票标记为02时,该客户的 “是否统一结算”为“是”，运单上“收货客户是否统一结算”为“是”,”收货客户合同部门”为其对应合同部门，收货客户的“催款部门”取合同对应的催款部门； 若该客户“是否统一结算”为“否”，则运单上“收货客户是否统一结算”为“否”,”收货客户合同部门”为空、收货客户的“催款部门”取合同对应的催款部门空
	 */
	private static void setExpReceiveSettleAndContactAndRemending(QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean){
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
				waybillBean
						.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				waybillBean.setArriveContractOrgCode(null);
				waybillBean
						.setArriveContractOrgName(null);
				waybillBean.setArriveReminderOrgCode(null);
			}
			
		}
		//wutao == end 
	}
	/**
	 * 清空发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午10:32:06
	 */
	public static void cleanDeliveryCustomerInfo(ExpWaybillEditUI ui, WaybillPanelVo bean, String mobile, String phone) {
		
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
		// 发货客户地址
		bean.setDeliveryCustomerAddressNote("");
		// 是否是电商尊享
		bean.setIsElectricityToEnjoy("");
		// 是否月结
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户月结
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if(!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())){
			bean.setChargeMode(Boolean.valueOf(false));
		}
		// 合同编号
		bean.setAuditNo("");
		// 行政区域
		bean.setDeliveryCustomerArea("");
		//清空【是否统一结算】【合同部门】【催款部门】
		bean.setStartCentralizedSettlement("");
		bean.setStartContractOrgCode("");
		bean.setStartContractOrgName("");
		bean.setStartReminderOrgCode("");
		//end
		bean.setDeliveryCustomerAreaDto(null);
		// 发货联系人编辑状态
		ui.getTxtConsignerLinkMan().setEnabled(true);
		ui.consignerPanel.getTxtConsignerArea().setAddressFieldDto(null);
		ui.consignerPanel.getTxtConsignerAddressNote().setText("");
		//可编辑XIAOMA
		ExpCommon.noDeliveryCustomerInfo(ui, bean);
		bean.setDeliveryCustomerContact("");
		bean.setDeliveryCustomerContactId("");
		ui.consignerPanel.getTxtConsignerLinkMan().setEditable(true);
		// 发货大客户标记
		ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		// 设置装卸费编辑状态-月结客户允许开装卸费
		ExpCommon.setServiceChargeEnabled("",false, ui);

		// 发货客户联系人:放在最后，校验通过不过时不会影响编辑状态的修改
		bean.setDeliveryCustomerContact("");
		
		//内部带货和非内部带货，控件状态的转化
		ExpCommon.transInnerPickupFeeBurdenDeptStat(ui,bean,false);
		
		/**
		 * 将bean中的vipInsuranceAmount清空
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-11 上午11:12
		 */
		bean.setVipInsuranceAmount(null);
		/**
		 * 将bean中的VipCollectionPaymentLimit清空
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-19下午13;45
		 */
		bean.setVipCollectionPaymentLimit(null);
	}

	/**
	 * 清空收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 上午10:32:06
	 */
	public static void cleanReceiveCustomerInfo(ExpWaybillEditUI ui, WaybillPanelVo bean, String mobile, String phone) {
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
		//清空【是否统一结算】【合同部门】【催款部门】
		bean.setArriveCentralizedSettlement("");
		bean.setArriveContractOrgCode("");
		bean.setArriveContractOrgName("");
		bean.setArriveReminderOrgCode("");
		//end
		// 收货人地址字体颜色
		ui.getTxtConsigneeAddress().setForeground(Color.BLACK);
		ui.consigneePanel.getTxtConsigneeArea().setAddressFieldDto(null);
		ui.consigneePanel.getTxtConsigneeAddressNote().setText("");
		// 行政区域
		bean.setReceiveCustomerArea("");
		bean.setReceiveCustomerAreaDto(null);
		// 设置联系人为可编辑
		ui.getTxtConsigneeLinkMan().setEnabled(true);
		// 收货大客户标记
		ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
	}

	/**
	 * 获得省市区县地址对象:若dto为空，则从界面中取对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午4:29:58
	 */
	public static AddressFieldDto getAddressFiledInfo(AddressFieldDto dto, ExpWaybillEditUI ui) {
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
	public static AddressFieldDto getAddressFieldInfoByCode(ExpWaybillEditUI ui, String provCode, String cityCode, String distCode) {
		// 业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 获得省市区县对象
		AddressFieldDto address = bu.getProvCityCounty(provCode, cityCode, distCode);
		// 接收地址对象
		return ExpCommon.getAddressFiledInfo(address, ui);
	}

	/**
	 * 设置集中开单收货部门信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午8:25:09
	 */
	public static void setSalesDepartmentForCentrial(SaleDepartmentEntity entity, WaybillPanelVo bean, ExpWaybillEditUI ui) {
		// 对象非空判断
		if (entity != null) {
			// 收货部门编号
			bean.setReceiveOrgCode(entity.getCode());
			// 收货部门名称
			bean.setReceiveOrgName(entity.getName());
			// 设置创建时间
			bean.setReceiveOrgCreateTime(entity.getOpeningDate());
			// 清空现有产品
//			ui.getProductTypeModel().removeAllElements();
//			// 设置新的产品信息
//			ui.setProductTypeModel(entity.getCode());
//			// 设置产品默认值
//			ui.setProductCode(bean);
		}
	}


	/**
	 * 
	 * 获取代打木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 下午05:21:48
	 */
	public static List<GuiQueryBillCalculateSubDto> getYokeChargeCollection(WaybillPanelVo bean, ExpWaybillEditUI ui) {

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
		return guiQueryBillCalculateSubDtos;
	}
	
	
	/**
	 * 
	 * 打木箱费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static GuiQueryBillCalculateSubDto getBoxChargeCollection(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		if (bean.getBoxGoodsNum() != null && bean.getBoxGoodsNum() > 0) {
			// 打木箱
			return getQueryYokeParam(bean, DictionaryValueConstants.PACKAGE_TYPE__BOX, bean.getBoxGoodsVolume());

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
	public static void setStandChargeCollection(WaybillPanelVo bean, ExpWaybillEditUI ui, GuiResultBillCalculateDto dto) {
		if (bean.getStandGoodsNum() != null && bean.getStandGoodsNum() > 0) {
			if (dto != null) {

				// 这个if内的代码为了解决包装费不断累加的问题
				if (bean.getStandCharge() != null) {
					BigDecimal packageFee = bean.getPackageFee();
					// 四舍五入
					BigDecimal standCharge = bean.getStandCharge();
					packageFee = packageFee.subtract(standCharge);
					bean.setPackageFee(packageFee);
					bean.setCalculatedPackageFee(packageFee);
					// 设置折扣优惠
					setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
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

	}

	/**
	 * 
	 * 打木箱费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	public static void setBoxChargeCollection(WaybillPanelVo bean, ExpWaybillEditUI ui, GuiResultBillCalculateDto dto) {
		if (bean.getBoxGoodsNum() != null && bean.getBoxGoodsNum() > 0) {
		if (dto != null) {

			// 这个if内的代码为了解决每次计算包装费不断累加的问题
			if (bean.getBoxCharge() != null) {
				BigDecimal packageFee = bean.getPackageFee();
				// 四舍五入
				BigDecimal boxCharge = bean.getBoxCharge();
				packageFee = packageFee.subtract(boxCharge);
				bean.setPackageFee(packageFee);
				bean.setCalculatedPackageFee(packageFee);
				// 设置折扣优惠
				setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
			}
			bean.setBoxChargeId(dto.getId());
			bean.setBoxChargeCode(dto.getSubType());
			bean.setBoxCharge(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));

		} else {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullWoodenBoxPackageFee"));
		}
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
	public static GuiQueryBillCalculateSubDto getStandChargeCollection(WaybillPanelVo bean, ExpWaybillEditUI ui) {

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
		return queryDto;
	}
	
	
	/**
	 * 
	 * 设置折扣优惠
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-27 下午02:33:49
	 */
	public static void setFavorableDiscount(List<GuiResultDiscountDto> discountPrograms, ExpWaybillEditUI ui, WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = null;
			WaybillDiscountCanvas discountTableModel = (WaybillDiscountCanvas) ui.canvasContentPanel.getConcessionsPanel().getTblConcessions().getModel();
			data = discountTableModel.getData();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}
			for (GuiResultDiscountDto dto : discountPrograms) {
				//报价费为0，优惠金额为0
				if ("0".equals(bean.getInsuranceAmount()) || (BigDecimal.ZERO.compareTo(bean.getInsuranceAmount()) == 0)) {
					if(PriceEntityConstants.PRICING_CODE_BF.equals(dto.getPriceEntryCode())){
						dto.setReduceFee(BigDecimal.ZERO);
					}
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
	public static void setFavorableDiscount(List<PriceDiscountDto> discountPrograms, ExpCalculateCostsDialog ui, WaybillPanelVo bean) {
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
	public static void setProductCode(ExpWaybillEditUI ui, WaybillPanelVo bean, String product) {
		for (int i = 0; i < ui.getProductTypeModel().getSize(); i++) {
			ProductEntityVo vo = (ProductEntityVo) ui.getProductTypeModel().getElementAt(i);
			// 设置为传入的产品类型
			if (product.equals(vo.getCode())) {
				bean.setProductCode(vo);
			}
		}
	}

	/**
	 * 
	 * 清理产品类型，设置为整车到下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-19 下午06:13:50
	 */
	public static void cleanProductToWholeVehicle(ExpWaybillEditUI ui) {
		// 获取产品数据模型
		DefaultComboBoxModel productTypeModel = ui.getProductTypeModel();
		// 情况下拉框数据模型
		productTypeModel.removeAllElements();
		// 获取当前用户
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 当前用户所在部门
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		// 根据部门编号查询产品数据
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(dept.getCode(), WaybillConstants.TYPE_OF_EXPRESS, new Date());
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
	public static void cleanProductToOtherType(ExpWaybillEditUI ui) {
		// 获取产品数据模型
		DefaultComboBoxModel productTypeModel = ui.getProductTypeModel();
		// 情况下拉框数据模型
		productTypeModel.removeAllElements();
		// 获取当前用户
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 当前用户所在部门
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		// 根据部门编号查询产品数据
		List<ProductEntity> list = waybillService.getAllProductEntityByDeptCodeForCargoAndExpress(dept.getCode(), WaybillConstants.TYPE_OF_EXPRESS, new Date());
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		for (ProductEntity product : list) {
			if (!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode())) {
				ProductEntityVo vo = new ProductEntityVo();
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
	public static void setUIEnableFalse(ExpWaybillEditUI ui) {
		UIUtils.disableUI(ui);
		// 是否整车
		ui.basicPanel.getChbWholeVehicle().setEnabled(true);
		// 是否经过营业部
		ui.basicPanel.getChbPassDept().setEnabled(true);
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
	public static void setPassDeptCodEnabled(ExpWaybillEditUI ui, WaybillPanelVo bean) {
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
	public static void setPartialCod(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
	public static void cleanCodInfo(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		// 清理银行信息
		ExpCommon.cleanBankInfo(bean);
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
		ExpCommon.setRefundType(bean, ui);
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
	public static ExpWaybillPanelVo getVoFromUI(ExpWaybillEditUI ui) {
		// 从Binders集合中获得绑定对象
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		// 获得与运单绑定的绑定对象
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
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
	public static void changePickUpMode(WaybillPanelVo bean, ExpCalculateCostsDialog ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			// 空运提货方式
			ExpCommon.airReceiveMethod(waybillService, bean, ui);
		} else {

//			// 如果运输性质不为精准空运，则将合票方式和航班类型设置为不可编辑且将下拉框设置为空
//			ExpCommon.setAirPropertyToFalse(bean, ui);
			// 汽运提货方式
			ExpCommon.highwaysReceiveMethod(waybillService, bean, ui);
		}
	}
	
	/**
	 * 
	 * 空运提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午08:47:33
	 */
	public static void airReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, ExpCalculateCostsDialog ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();

		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsAir();
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
	 * 汽运提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午08:47:33
	 */
	public static void highwaysReceiveMethod(IWaybillService waybillService, WaybillPanelVo bean, ExpCalculateCostsDialog ui) {
		DefaultComboBoxModel pikcModeModel = ui.getPikcModeModel();

		pikcModeModel.removeAllElements();
		List<DataDictionaryValueEntity> list = waybillService.queryPickUpGoodsHighWays();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			
			String valueCode = vo.getValueCode();
			//包裹的提货方式只能为：自提、送货上楼、内部带货自提
			//DMANA-5443 FOSS开单冻结自提功能
			if(//WaybillConstants.SELF_PICKUP.equals(valueCode) || 
					WaybillConstants.DELIVER_UP.equals(valueCode) 
					|| WaybillConstants.INNER_PICKUP.equals(valueCode)
					){
				pikcModeModel.addElement(vo);
			}
			
			//默认为送货上楼
			if (//WaybillConstants.SELF_PICKUP.equals(vo.getValueCode())
					WaybillConstants.DELIVER_UP.equals(vo.getValueCode())
					){
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
	public static void getYokeCharge(WaybillPanelVo bean, ExpWaybillEditUI ui) {
		// 获取木架费用
		getStandCharge(bean, ui);
		// 获取木箱费用
		getBoxCharge(bean, ui);
	}
	
	/**
	 * 
	 * 打木箱费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static void getBoxCharge(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
						setFavorableDiscountForOld(dto.getDiscountPrograms(), ui, bean);
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
	}
	/**
	 * 
	 * 打木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午03:29:21
	 */
	private static void getStandCharge(WaybillPanelVo bean, ExpWaybillEditUI ui) {
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
					setFavorableDiscountForOld(dto.getDiscountPrograms(), ui, bean);
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
	}
	/**
	 * 
	 * 设置折扣优惠
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-27 下午02:33:49
	 */
	public static void setFavorableDiscountForOld(List<PriceDiscountDto> discountPrograms, ExpWaybillEditUI ui, WaybillPanelVo bean) {
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
	public static QueryBillCacilateDto getQueryParam(WaybillPanelVo bean) {
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
	public static void unsetWaybillPanelVoForWoodenPack(WaybillPanelVo bean , ExpWaybillEditUI ui) {
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
	 * 代打木架取消清除储运事项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-2 上午08:44:42
	 */
	public static void unsetStorageMatterForWoodenPack(WaybillPanelVo bean) {
		cleanRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"));
		cleanRemark(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"));
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
	public static void setWholeVehicleData(ExpWaybillEditUI ui,WaybillPanelVo bean){
		// 整车编号
		ui.basicPanel.getTxtVehicleNumber().setVisible(true);
		// 整车编号标签
		ui.basicPanel.getLblVehicleNumber().setVisible(true);
		// 整车编号导入按钮
		ui.basicPanel.getBtnImportVehicle().setVisible(true);
		// 经过营业部
		ui.basicPanel.getChbPassDept().setEnabled(true);

		// 开单报价
		ui.billingPayPanel.billingPayBelongPanel.getLblPublicCharge().setText(i18n.get("foss.gui.creating.listener.Waybill.isWholeVehicleListener.two") + "：");
		// 整车约车报价
		ui.billingPayPanel.billingPayBelongPanel.getTxtWholeVehicleAppfee().setVisible(true);
		// 整车约车报价标签
		ui.billingPayPanel.billingPayBelongPanel.getLblWholeVehicleAppfee().setVisible(true);
		// 清空产品，设置为整车产品
		ExpCommon.cleanProductToWholeVehicle(ui);

		// 设置运输性质为“整车”
		ExpCommon.setProductCode(ui, bean, ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE);

		ExpCommon.setUIEnableFalse(ui);

		// 设置经过营业部代收货款编辑状态
		ExpCommon.setPassDeptCodEnabled(ui, bean);		
		
	}
	
	/**
	 * 设置界面为整车界面编辑状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-23 下午4:04:14
	 */
	public static void setWholeVehicleEdit(ExpWaybillEditUI ui,WaybillPanelVo bean){
		//设置开单界面编辑状态为可编辑
		ExpCommon.setUIEnableTrue(bean,ui);
		//设置整车无贵重物品
		bean.setPreciousGoods(false);
		
		//查询网点
		ui.transferInfoPanel.getBtnQueryBranch().setEnabled(false);
		
		//目的站
		ui.transferInfoPanel.getTxtDestination().setEnabled(false);
		//产品类型
		ui.transferInfoPanel.getCombProductType().setEnabled(false);
		
		if(bean.getIsPassDept())
		{
			// 代收货款金额
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
			// 代收货款类型
			ui.incrementPanel.getCombRefundType().setEnabled(true);
		}
		// 根据是否整车设置上门接货和司机工号是否可编辑
		if(bean.getIsWholeVehicle()){
			ui.basicPanel.getCboReceiveModel().setEnabled(false);
		}else{
			ui.basicPanel.getCboReceiveModel().setEnabled(true);
		}
		//整车不能使用优惠券
		ui.incrementPanel.getTxtPromotionNumber().setEnabled(false);
		//整车不能使用电子地图
		ui.buttonPanel.getBtnGIS().setEnabled(false);
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
	public static void setOtherPendingData(ExpWaybillEditUI ui,WaybillPanelVo vo){
		/**
		 * 非离线时补录时，只要重新提货网点 因为其它信息已于在线时查询出来了
		 * （提货网点的线路信息没有保存在数据库中，导入需要重新走业务逻辑）
		 */
		// 提货网点
		ExpShowPickupStationDialogAction action = new ExpShowPickupStationDialogAction();
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
			ExpCommon.setWholeVehicleData(ui, vo);
			ExpCommon.setWholeVehicleEdit(ui, vo);
		}
	}
	
	/**
	 * 处理优惠券
	 * @author WangQianJin
	 * @date 2013-5-18 下午6:53:30
	 */
	public static void executeCoupon(WaybillPanelVo bean,ExpWaybillEditUI ui) {
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
	private static CouponInfoDto getCouponInfoDto(WaybillPanelVo bean,ExpWaybillEditUI ui) {
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
		List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = ExpWaybillDtoFactory.getWaybillChargeDtlEntity(model, bean);
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
	 * 根据部门名称判断该部门是否属性于当前集中开单组中
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-24 下午2:53:22
	 */
	public static boolean isBelongCurrentDept(String deptName) {
		// 获取当前用户
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 当前用户所在部门
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		//根据部门名称进行查询
		List<SaleDepartmentEntity> list = waybillService.querySaleDepartmentByNameForCentralized(deptName,dept.getCode());
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
	public static void queryOtherChargeData(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(CommonUtils.getQueryOtherChargeParam(bean));

		// CommonUtils.getOtherChargeList中不含更改费
		List<OtherChargeVo> voList = CommonUtils.getOtherChargeList(list);
		if (voList != null) {
			if (!voList.isEmpty()) {
				ui.incrementPanel.setChangeDetail(ExpCommon.otherChargeCompare(ui,voList));
			}
		}
	}

	/**
	 * 
	 * 将原有其他费用与新查询出来其他费用进行比较，然后删除重复的项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午03:03:01
	 */
	public static List<OtherChargeVo> otherChargeCompare(ExpWaybillEditUI ui,List<OtherChargeVo> voList) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();
		if (data != null) {
			if (!data.isEmpty()) {
				for (int i = 0; i < voList.size(); i++) {
					OtherChargeVo queryVo = voList.get(i);
					for (int j = 0; j < data.size(); j++) {
						OtherChargeVo tableVo = data.get(j);
						if (tableVo.getChargeName().equals(queryVo.getChargeName())) {
							data.remove(j);
							data.add(j, queryVo);
						}
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
	public static void calculateOtherCharge(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();
		BigDecimal otherChargeSum = BigDecimal.ZERO;
		if (data != null && !data.isEmpty()) {
			// 其他费用合计
			for (OtherChargeVo vo : data) {
				BigDecimal money = new BigDecimal(vo.getMoney());
				otherChargeSum = otherChargeSum.add(money);
			}
			// 其他费用
			bean.setOtherFee(otherChargeSum);
			// 画布其他费用
			bean.setOtherFeeCanvas(otherChargeSum.toString());
		}else{
			// 其他费用
			bean.setOtherFee(otherChargeSum);
			// 画布其他费用
			bean.setOtherFeeCanvas(otherChargeSum.toString());
		}
	}
	
	/**
	 * 当送货费被修改并将发货客户修改为月结客户发货客户时，原来在月结客户时手动修改的送货费需要重新进行计算
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-14 下午7:58:22
	 */
	public static void resetDeliverGoodsFee(WaybillPanelVo bean,ExpWaybillEditUI ui){
		String code = bean.getReceiveMethod().getValueCode();
		//判断：若提货方式为送货进仓时，若为月结客户则送货费可编辑
		if(WaybillConstants.DELIVER_STORAGE.equals(code)){
			//不是月结客户
			if(!bean.getChargeMode()){
				ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(false);
				//设置提交为灰，使客户重新计算总运费
				ExpCommon.setSaveAndSubmitFalse(ui);
				//标识为非手动修改过，这样计算总运费的值才会生效
				bean.setHandDeliveryFee(BigDecimal.valueOf(0));
			}else{
				//月结客户
				ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(false );//  );
			}
		}
		
	}
	

	/**
	 * 设置简单报价收货部门信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-2 下午5:21:01
	 */
	public static void setSalesDepartmentForSimple(SaleDepartmentEntity entity, WaybillPanelVo bean, ExpCalculateCostsDialog ui) {
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
	 * 根据运输性质的改变，改变提货方式
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-2 下午5:33:34
	 */
	public static void changePickUpModeForSimple(WaybillPanelVo bean, ExpCalculateCostsDialog ui) {
		ProductEntityVo productVo = bean.getProductCode();
		// 对象非空判断
		if (null == productVo) {
			// 返回
			return;
		}
		
		// 汽运提货方式
		ExpCommon.highwaysReceiveMethod(waybillService, bean, ui);
	}

	/**
	 * 优惠券冲减费用
	 * 对于运费在calculateTotalFee中进行冲减
	 * 对于综合信息费则在xxx中进行冲减
	 * 
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static void offsetCouponFee(ExpWaybillEditUI ui,ExpWaybillPanelVo bean) {
		// 优惠费
		BigDecimal couponFee = CommonUtils.defaultIfNull(bean.getCouponFree());
		// 优惠类型
		String type = CommonUtils.defaultIfNull(bean.getCouponRankType());
	
		// 是否有优惠金额,是否已冲减
		if (couponFee.compareTo(BigDecimal.ZERO) > 0) {
			// 校验优惠类型是否符合条件
			CommonUtils.validateCouponTypeExpress(type);
			// 冲减费用类型
			if(PriceEntityConstants.PRICING_CODE_JH.equals(type)) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getPickupFee(),type);
				// 冲减接货费
				BigDecimal pickUpFee = CommonUtils.defaultIfNull(bean.getPickupFee());
				processPromotionsFee(bean,ui,pickUpFee,couponFee);
				pickUpFee=pickUpFee.subtract(couponFee);
				pickUpFee = ExpCommon.convertFeeToZero(ui,pickUpFee);
				bean.setPickupFee(pickUpFee);
				bean.setPickUpFeeCanvas(pickUpFee.toString());
			}else if (PriceEntityConstants.PRICING_CODE_SH.equals(bean.getCouponRankType())) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getDeliveryGoodsFee(),type);
				// 冲减送货费
				BigDecimal deliveryGoodsFee = CommonUtils.defaultIfNull(bean.getDeliveryGoodsFee());
				processPromotionsFee(bean,ui,deliveryGoodsFee,couponFee);
				deliveryGoodsFee=deliveryGoodsFee.subtract(couponFee);
				deliveryGoodsFee = ExpCommon.convertFeeToZero(ui,deliveryGoodsFee);
				bean.setDeliveryGoodsFee(deliveryGoodsFee);
				bean.setDeliveryGoodsFeeCanvas(deliveryGoodsFee.toString());
				bean.setCalculateDeliveryGoodsFee(deliveryGoodsFee);
			}else if (PriceEntityConstants.PRICING_CODE_HK.equals(type)) {
				//校验费用是否符合要求
				CommonUtils.validateFeeIsZero(bean.getCodFee(),type);
				// 冲减代收货款手续费
				BigDecimal codFee = CommonUtils.defaultIfNull(bean.getCodFee());
				processPromotionsFee(bean,ui,codFee,couponFee);
				codFee=codFee.subtract(couponFee);
				codFee = ExpCommon.convertFeeToZero(ui,codFee);
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
								processPromotionsFee(bean,ui,zhxxFee,couponFee);
								zhxxFee = zhxxFee.subtract(couponFee);															
								zhxxFee=ExpCommon.convertFeeToZero(ui,zhxxFee);
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
			}else if(PriceEntityConstants.PRICING_CODE_FRT.equals(type)){
				processPromotionsFee(bean,ui,bean.getBeforeProTranFee(),couponFee);
			}		
			//重新计算增值服务费
			CalculateFeeTotalUtils.resetCalculateFee(bean);
		}
	}
	
	//处理优惠券金额
	private static void processPromotionsFee(ExpWaybillPanelVo bean,ExpWaybillEditUI ui,BigDecimal subFee,BigDecimal proFee) {
		if(subFee!=null && proFee!=null){
			//如果优惠券金额大于要冲减的金额，则重新设置优惠券金额为要冲减的金额
			if(proFee.compareTo(subFee) > 0 ){
				JXTable tblConcessions = ui.getCanvasContentPanel().getConcessionsPanel().getTblConcessions();
				WaybillDiscountCanvas discountTableModel = ((WaybillDiscountCanvas) tblConcessions.getModel());
				List<WaybillDiscountVo> waybillDiscountVos = discountTableModel.getData();
				if(waybillDiscountVos!=null && waybillDiscountVos.size()>0){
					//优惠总费用
					BigDecimal totalPromotionsFee = BigDecimal.ZERO;
					for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
						if(bean.getPromotionsCode()!=null && bean.getPromotionsCode().equals(waybillDiscountVo.getDiscountId())){
							waybillDiscountVo.setFavorableAmount(subFee.toString());
						}
						totalPromotionsFee = totalPromotionsFee.add(new BigDecimal(waybillDiscountVo.getFavorableAmount()));
					}
	/**
					 * 设置优惠费用
					 */
					bean.setPromotionsFee(totalPromotionsFee);
					/**
					 * 设置画布的优惠费用
					 */
					bean.setPromotionsFeeCanvas(totalPromotionsFee.toString());
					//设置优惠面板
					ui.getCanvasContentPanel().getConcessionsPanel().setChangeDetail(waybillDiscountVos);
				}
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
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryOtherChargeParam(bean));
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
	
	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private static QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPanelVo bean) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
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
		return queryDto;
	}
	
	/**
	 * 检验优惠冲减后的费用是否正确
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static BigDecimal convertFeeToZero(ExpWaybillEditUI ui,BigDecimal fee){
		BigDecimal value = CommonUtils.defaultIfNull(fee);
		//检验优惠费用是否小于0
		if(BigDecimal.ZERO.compareTo(value) > 0 ){
			return BigDecimal.ZERO;
		}else{
			return value;
		}
	}
	
	
	
	/**
	 * 检验优惠券冲减综合信息服务的合理性
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static void validateZhxxCouponFee(ExpWaybillEditUI ui,ExpWaybillPanelVo bean){
		//获取其他费用
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		
		//是否包含综合信息服务费
		boolean zhxxFee = false;
		//遍历集合
		if(CollectionUtils.isNotEmpty(data)){
			for(OtherChargeVo vo : data){
				if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())){
					zhxxFee = true;
					break;
				}
			}
		}else{
			zhxxFee = false;
		}
		
		// 优惠费
		BigDecimal couponFee = CommonUtils.defaultIfNull(bean.getCouponFree());
		// 优惠类型
		String type = CommonUtils.defaultIfNull(bean.getCouponRankType());
		//若没有综合信息服务费，则不能对其它进行冲减
		if(!zhxxFee && couponFee.compareTo(BigDecimal.ZERO) > 0 && PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){
			throw new WaybillValidateException(CommonUtils.convertFeeToName(PriceEntityConstants.PRICING_CODE_ZHXX)+"为空，不能进行优惠券冲减！");
		}
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
						if(customerDto!=null&&customerDto.getTeanLimit()!=null){
							BigDecimal vipInsuranceAmount=new BigDecimal(customerDto.getTeanLimit());
							waybillPending.setVipInsuranceAmount(vipInsuranceAmount);
							waybillPending.setVipCollectionPaymentLimit(vipInsuranceAmount);
						}
					}else{
						waybillPending.setDeliveryCustomerCode(orderDetailVo.getShipperId());
					}
					//发票信息
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
					
					//发票信息
					setInvoiceInfo(waybillPending.getDeliveryCustomerCode(),waybillPending,"RECEIVE");

				    // 收货客户名称
					if(StringUtils.isNotEmpty(orderDetailVo.getReceiverCustName())){
						waybillPending.setReceiveCustomerName(orderDetailVo.getReceiverCustName());
					}else{
						waybillPending.setReceiveCustomerName(orderDetailVo.getReceiverCustcompany());
					}

				    // 收货客户手机
					waybillPending.setReceiveCustomerMobilephone(orderDetailVo.getReceiverCustMobile());

				    // 收货客户电话
					waybillPending.setReceiveCustomerPhone(orderDetailVo.getReceiverCustPhone());

				    // 收货客户联系人
					waybillPending.setReceiveCustomerContact(orderDetailVo.getReceiverCustName());
				    
					//收货联系人ID
					waybillPending.setReceiveCustomerContactId("");
					proviceCode = null;// 清空
				    if (orderDetailVo.getReceiverCustProvince() != null) {
					//proviceCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_PROVINCE, orderDetailVo.getReceiverCustProvince(), null);
				    	proviceCode = orderDetailVo.getReceiverCustProvinceCode();
				    }
				    // 接货人省份
				    waybillPending.setReceiveCustomerProvCode(proviceCode);
				    cityCode = null;// 清空
				    if (orderDetailVo.getReceiverCustCity() != null && proviceCode != null) {
					//cityCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_CITY, orderDetailVo.getReceiverCustCity(), proviceCode);
				    	cityCode = orderDetailVo.getReceiverCustCityCode();
				    }
				    // 接货人城市
				    waybillPending.setReceiveCustomerCityCode(cityCode);
				    areaCode = null;// 清空
				    if (orderDetailVo.getReceiverCustArea() != null && cityCode != null) {
					//areaCode = waybillService.queryDistrictCode(DictionaryValueConstants.DISTRICT_COUNTY, orderDetailVo.getReceiverCustArea(), cityCode);
				    	areaCode = orderDetailVo.getReceiverCustAreaCode();
				    }
				    // 接货人区县
				    waybillPending.setReceiveCustomerDistCode(areaCode);
				    // 收货具体地址
					waybillPending.setReceiveCustomerAddress(orderDetailVo.getReceiverCustAddress());
					
					// 货物名称
					waybillPending.setGoodsName(orderDetailVo.getGoodsName());								    
				    // 托运货物总重量
					if(waybillPending.getGoodsWeightTotal()==null || waybillPending.getGoodsWeightTotal().doubleValue()<=0){
						waybillPending.setGoodsWeightTotal(BigDecimal.valueOf(orderDetailVo.getTotalWeight()));																		    
					}
				    // 保险声明价值
//					waybillPending.setInsuranceAmount(orderDetailVo.getInsuredAmount());
					 // 代收货款金额
					
//					//TODO CHANGE
//				    if(orderDetailVo.getReviceMoneyAmount()!=null){
//				    	waybillPending.setCodAmount(orderDetailVo.getReviceMoneyAmount());
//				    }else{
//				    	waybillPending.setCodAmount(BigDecimal.ZERO);
//				    }
//					
					/**
					 * 将是否电子发票传至waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-10-24下午18:37
					 */
					waybillPending.setIsElectronicInvoice(orderDetailVo.getIsElectronicInvoice());
					/**
					 * 将发票手机号码传至waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-10-24下午18:37
					 */
					waybillPending.setInvoiceMobilePhone(orderDetailVo.getInvoiceMobilePhone());
					/**
					 * 将发票邮箱传至waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-10-24下午18:37
					 */
					waybillPending.setEmail(orderDetailVo.getEmail());
					// 设置PDA待处理信息
					/**
					 * 根据Dmana-9885将CRM运费传入waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-03-10上午10:02
					 */
					waybillPending.setCrmTransportFee(orderDetailVo.getCrmTransportFee());
					/**
					 * 根据Dmana-9885将CRM重量传入waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-03-10上午10:02
					 */
					waybillPending.setCrmWeight(new BigDecimal(orderDetailVo.getTotalWeight()));
					/**
					 * 根据Dmana-9885将CRM体积传入waybillPending中
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-03-10上午10:02
					 */
					waybillPending.setCrmVolume(new BigDecimal(orderDetailVo.getTotalVolume()));
					waybillPendingDto.setWaybillPending(waybillPending);					
					
				}
			}
		}
		
		return waybillPendingDto;
	}
	
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
				if("DELIVER".equals(type)){
					ExpCommonUtils.setPendingExpDevliveryCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, vo);	
				}else{
					ExpCommonUtils.setPendingExpReciveCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, vo);
				}				
			}
		}
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
     * pgy
     * 返回单折扣
     */
	public static void setReturnBillDiscount(
			List<PriceDiscountDto> discountPrograms, ExpWaybillEditUI ui,
			WaybillPanelVo bean) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = null;
			WaybillDiscountCanvas discountTableModel = (WaybillDiscountCanvas) ui.canvasContentPanel.getConcessionsPanel().getTblConcessions().getModel();
			data = discountTableModel.getData();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}
			for (PriceDiscountDto dto : discountPrograms) {
				CommoForFeeUtils.add(dto, data, bean);
			}
			ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(data);
		}
		
	}
	
	/**
	 * 当没有根据名字找到发货客户时，清空客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-17 上午9:12:33
	 */
	public static void noDeliveryCustomerInfo(ExpWaybillEditUI ui,WaybillPanelVo bean){
		/**
		 * BUG-45873:开单界面修复发货客户后未找到固定客户后未清空客户信息
		 */
		// 若为删除原客户信息，则清空全部
		if (!"".equals(StringUtils.defaultString(bean.getDeliveryCustomerId()))) {
			// 清空发货客户信息
			cleanDeliveryCustomerInfo(ui, bean, "", "");
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
		// 大客户标记
		bean.setDeliveryBigCustomer(FossConstants.INACTIVE);
		// 客户名称
		bean.setDeliveryCustomerName("");
		// 发货大客户标记
		ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		// 发货客户编码被清空，则对应的银行帐号信息也要清空
		Common.cleanBankInfo(bean);
	}
	
	/**
	 * 当没有根据名字找到收货客户时，清空客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-17 上午9:12:33
	 */
	public static void noReceiveCustomerInfo(ExpWaybillEditUI ui,WaybillPanelVo bean){
		/**
		 * BUG-45873:开单界面修复收货客户后未找到固定客户后未清空客户信息
		 */
		// 若为删除原客户信息，则清空全部
		if (!"".equals(StringUtils.defaultString(bean.getReceiveCustomerId()))) {
			// 清空发货客户信息
			cleanReceiveCustomerInfo(ui, bean, "", "");
		}
		// 客户编码
		bean.setReceiveCustomerCode("");
		// 收货客户是否是大客户
		bean.setReceiveBigCustomer(FossConstants.INACTIVE);
		// 客户名称
		bean.setReceiveCustomerName("");
		// 收货大客户标记
		ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
	}
	/**
 	 * @author 200945 - wutao
 	 * 公共驗證付款方式的方法
 	 * @param bean
 	 */
 	public static void validateExpPayMethod(WaybillPanelVo bean){
 		/**
 		 * 当发货人【是否统一结算】标记为【是】,该客户付款方式只能为【临时欠款】或【月结】
 		 * 快递：不允许【临时欠款】
 		 */
 		if(bean.getPaidMethod() != null) { 			
 		if (WaybillConstants.IS_NOT_NULL_FOR_AI.equals(bean
 				.getStartCentralizedSettlement())) {
 			boolean isOK = false;
 			if(WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
 				isOK = true;
 			}
 			if(WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
 				isOK = true;
 			}
 			//判断发货客户是否满足月结的条件
 			if(!isOK){
 				throw new WaybillValidateException(i18n.get("foss.gui.chaning.waybillRFCUI.common.alterExpPaidMethod"));
 			}	
 		}
 		}
 	}
 	//liding comment for NCI
	/**
	 * 校验交易流水号是否符合开单规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23上午11:09
	 */
//	public static void validateTransactionSerialNumber(ExpWaybillPanelVo bean,ExpWaybillEditUI ui){
//		String valueCode=bean.getPaidMethod().getValueCode();
//		String transactionSerialNumber=bean.getTransactionSerialNumber();
//		if(valueCode.equals(WaybillConstants.CREDIT_CARD_PAYMENT)){
//			if(StringUtils.isEmpty(transactionSerialNumber)){
//				throw new WaybillValidateException(i18n.get("foss.gui.creatingexp.listener.Waybill.transactionSerialNumber"));
//			}
//		}
//	}

 	//liding comment for NCI
	/**
	 * 该方法验证若是银行卡付款，则交易流水号是否可编辑
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23上午08:13
	 */
//	public static void whetherBankCardPayment(ExpWaybillPanelVo bean,ExpWaybillEditUI ui) {
//		// TODO Auto-generated method stub
//		if(bean.getPaidMethod()!=null&&WaybillConstants.CREDIT_CARD_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(true);
//		}else{
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(false);
//			bean.setTransactionSerialNumber(null);
//		}
//	}
 	
	/**
	 * 根据下拉框选项的ValueName获取对应的数据字典对象
	 * 
	 * @author 201287
	 * @date 2015年3月12日 10:10:44
	 * @param valueName
	 * @param comboBoxModel
	 * @return
	 */
	public static DataDictionaryValueVo castToDataDictionaryValueVoByValueName(
			String valueName, ComboBoxModel comboBoxModel) {
		DataDictionaryValueVo temp = null;
		DataDictionaryValueVo vo = new DataDictionaryValueVo();
		vo.setValueName(valueName);
		if (null != comboBoxModel) {
			for (int i = 0; i < comboBoxModel.getSize(); i++) {
				Object object = comboBoxModel.getElementAt(i);
				if (object instanceof DataDictionaryValueVo) {
					temp = (DataDictionaryValueVo) object;
					if ((null != temp.getValueName())
							&& (temp.getValueName().equals(valueName))) {
						vo.setValueCode(temp.getValueCode());
						break;
					}
				} else {
					continue;
				}
			}
		}
		return vo;
	}

	/**
	 * 根据下拉框选项的ValueCode获取对应的数据字典对象
	 * 
	 * @author 201287
	 * @date 2015年3月12日 10:10:44
	 * @param valueCode
	 * @param comboBoxModel
	 * @return
	 */
	public static DataDictionaryValueVo castToDataDictionaryValueVoByValueCode(
			String valueCode, ComboBoxModel comboBoxModel) {
		DataDictionaryValueVo temp = null;
		DataDictionaryValueVo vo = new DataDictionaryValueVo();
		vo.setValueCode(valueCode);
		if (null != comboBoxModel) {
			for (int i = 0; i < comboBoxModel.getSize(); i++) {
				Object object = comboBoxModel.getElementAt(i);
				if (object instanceof DataDictionaryValueVo) {
					temp = (DataDictionaryValueVo) object;
					if ((null != temp.getValueCode())
							&& (temp.getValueCode().equals(valueCode))) {
						vo.setValueName(temp.getValueName());
						break;
					}
				} else {
					continue;
				}
			}
		}
		return vo;
	}
	
	/**
	 * 	@author 218459-foss-dongsiwei
	 * 新增 快递送货费优化 
	 */
	public static void deliveryGoodsFeeCalculate(WaybillPanelVo bean){
		if(bean.getDeliveryGoodsFee().compareTo(BigDecimal.ZERO)!=0 
				&&bean.getDeliveryGoodsFee().compareTo(new BigDecimal(180))<0){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.deliveryGoodsCalculate"));
		}
	}
	/**
	 * 校验能否快递上门发货
	 * @author 218459-foss-dongsiwei
	 * @param bean
	 * @return
	 */
	public static boolean calculateHomeDelivery(ExpWaybillPanelVo bean){
		if(bean.getIsPdaBill()|| bean.getReturnType() !=null){
			return false;
		}
		UserEntity user = (UserEntity)SessionContext.getCurrentUser();
		String currentOrgCode = user.getEmployee().getDepartment().getCode();
		CusBargainEntity cusBargainEntity=null;
		SaleDepartmentEntity  saleDepartmentEntity=waybillService.querySaleDeptByCode(currentOrgCode);
		if(WaybillConstants.YES.equals(saleDepartmentEntity.getCanExpressDoorToDoor())){			
		if(StringUtils.isNotEmpty(bean.getDeliveryCustomerCode())){							
			cusBargainEntity=customerService.queryCusBargainByCustCode(bean.getDeliveryCustomerCode());
		}
			if(cusBargainEntity==null){
				return true;
		}else{
			return false;
			}
			
		}else{
			return false;
		}		
	}

}
