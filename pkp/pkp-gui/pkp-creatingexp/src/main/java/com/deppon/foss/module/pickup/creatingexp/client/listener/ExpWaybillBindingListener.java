/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.listener;

import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.binding.BindingEvent;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.IBindingListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressFieldForExp;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.JComboCheckBox;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.QueryPickupStationDialog;
import com.deppon.foss.module.pickup.common.client.ui.customer.ExpQueryConsignerDialog;
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
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.SalesDepartmentDialog;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpShowPickupStationDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IValuateFoss2EcsWaybillNoService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.GisConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillGisPickupOrgException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpWaybillBindingListener  implements IBindingListener {
	// 0
	private static final String ZERO = "0";
	// 外发保价编码

	// //代收货款
	// private BigDecimal codAmounts = BigDecimal.ZERO;
	// //返货受理 存在最新数据
	// private boolean flg = false;

	// crm返货信息
	// CrmReturnedGoodsDto d = null ;

	// log object
	private static final Log log = LogFactory
			.getLog(ExpWaybillBindingListener.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager
			.getI18n(ExpWaybillBindingListener.class);
	private static final double NUM_2000 = 2000;

	private static final double NUM_300000 = 300000;

	// 运单UI
	ExpWaybillEditUI ui;

	// 绑定对象
	IBinder<ExpWaybillPanelVo> waybillBinder;

	// 运单业务对象通过工场生成离线业务对象和在线业务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	// 工具类获取branchVo对象
	BusinessUtils bu = new BusinessUtils();

	public ExpWaybillBindingListener(ExpWaybillEditUI ui) {
		this.ui = ui;

	}

	/**
	 * 
	 * 绑定对象时间触发器
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-19 下午08:11:33
	 * @param be
	 */
	@Override
	public void bindingTriggered(List<BindingEvent> be) {

		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();
		try {
			for (BindingEvent bindingEvent : be) {
				if ("waybillNo".equals(bindingEvent.getPropertyName())) {// 运单号
					waybillNoListener(bean);
				} else if ("returnType".equals(bindingEvent.getPropertyName())) {
					returnTypeListener(bean);// 返单返货
				} else if ("originalWaybillNo".equals(bindingEvent
						.getPropertyName())) {// 原始运单号
					originalWaybillNoListener(bean);
				} else if ("goodsName".equals(bindingEvent.getPropertyName())) {// 货物名称
					gooodsNameListener(bean);
				} else if ("pickupToDoor"
						.equals(bindingEvent.getPropertyName())) {// 上门接货
					pickupToDoorListener(bean);
				} else if ("deliveryCustomerMobilephone".equals(bindingEvent
						.getPropertyName())) {// 发货人手机号码
					deliveryCustomerMobilephoneListener(bean);
				} else if ("deliveryCustomerPhone".equals(bindingEvent
						.getPropertyName())) {// 发货人电话
					deliveryCustomerPhoneListener(bean);
				}
				// 发货客户名称
				else if ("deliveryCustomerName".equals(bindingEvent
						.getPropertyName())) {
					deliveryCustomerNameListener(bean);
				}
				// 发货联系人
				else if ("deliveryCustomerContact".equals(bindingEvent
						.getPropertyName())) {
					deliveryCustomerContactListener(bean);
				}
				// 内部带货费用承担部门
				else if ("innerPickupFeeBurdenDept".equals(bindingEvent
						.getPropertyName())) {
					innerPickupFeeBurdenDeptListener(bean);
				}
				// 发货省市区县
				else if ("deliveryCustomerArea".equals(bindingEvent
						.getPropertyName())) {
					deliveryCustomerAreaListener(bean);
				} else if ("receiveCustomerMobilephone".equals(bindingEvent
						.getPropertyName())) {// 收货人手机号码
					receiveCustomerMobilephoneListener(bean);
				} else if ("deliveryCustomerAddress"
						.equalsIgnoreCase(bindingEvent.getPropertyName())) {
					deliveryCustomerAddress(bean);// 收货人地址
				} else if ("receiveCustomerPhone".equals(bindingEvent
						.getPropertyName())) {// 收货人电话
					receiveCustomerPhoneListener(bean);
				}
				// 收货部门（收入部门）
				else if ("receiveOrgName"
						.equals(bindingEvent.getPropertyName())) {
					receiveOrgNameListener(bean);
				}
				// 发货客户编码
				else if ("deliveryCustomerCode".equals(bindingEvent
						.getPropertyName())) {
					deliveryCustomerCodeListener(bean);
				}
				// 开单提货方式
				else if ("receiveMethod".equals(bindingEvent.getPropertyName())) {// 提货方式
					receiveMethodListener(bean);
				}
				// 收货客户名称
				else if ("receiveCustomerName".equals(bindingEvent
						.getPropertyName())) {
					receiveCustomerNameListener(bean);
				}
				// 收货区域
				else if ("receiveCustomerArea".equals(bindingEvent
						.getPropertyName())) {
					receiveCustomerAreaListener(bean);
				}
				// 收货联系人
				else if ("receiveCustomerContact".equals(bindingEvent
						.getPropertyName())) {
					receiveCustomerContactListener(bean);
				}
				// 运输性质
				else if ("productCode".equals(bindingEvent.getPropertyName())) {// 运输性质
					productCodeListener(bean);
				} else if ("goodsWeightTotal".equals(bindingEvent
						.getPropertyName())) {// 重量
					goodsWeightTotalListener(bean);
				} else if ("goodsVolumeTotal".equals(bindingEvent
						.getPropertyName())) {// 体积
					goodsVolumeTotalListener(bean);
				} else if ("goodsQtyTotal".equals(bindingEvent
						.getPropertyName())) {// 件数
					goodsQtyTotalListener(bean);
				}
				// 保险申明价值
				else if ("insuranceAmount".equals(bindingEvent
						.getPropertyName())) {// 保险价值
					insuranceAmountListener(bean);
				} else if ("goodsSize".equals(bindingEvent.getPropertyName())) {// 尺寸
					goodsSizeListener(bean);
				} else if ("wood".equals(bindingEvent.getPropertyName())) {// 包装-木
					woodListener(bean);
				} else if ("fibre".equals(bindingEvent.getPropertyName())) {// 包装-纤
					fibreListener(bean);
				} else if ("salver".equals(bindingEvent.getPropertyName())) {// 包装-托
					salverListener(bean);
				} else if ("membrane".equals(bindingEvent.getPropertyName())) {// 包装-膜
					membraneListener(bean);
				} else if ("refundType".equals(bindingEvent.getPropertyName())) {// 退款类型
					refundTypeListener(bean);
				} else if ("returnBillType".equals(bindingEvent
						.getPropertyName())) {// 返单类型
					returnBillTypeListener(ui, bean);
				} else if ("returnType".equals(bindingEvent.getPropertyName())) {
					returnTypeListener(ui, bean);// new add
				}

				// set
				else if ("paidMethod".equals(bindingEvent.getPropertyName())) {
					// 开单付款方式
					paidMethodListener(bean);
				} else if ("isWholeVehicle".equals(bindingEvent
						.getPropertyName())) {// 是否整车
					isWholeVehicleListener(bean);
				} else if ("isPassDept".equals(bindingEvent.getPropertyName())) {// 是否经过营业部
					isPassDeptListener(bean);
				} else if ("pickupFee".equals(bindingEvent.getPropertyName())) {// 接货费
					pickupFeeListener(bean);
				} else if ("packageFee".equals(bindingEvent.getPropertyName())) {// 包装费
					packageFeeListener(bean);
				} else if ("codAmount".equals(bindingEvent.getPropertyName())) {// 代收货款
					codAmountListener(bean);
				} else if ("serviceFee".equals(bindingEvent.getPropertyName())) {// 装卸费
					serviceFeeListener(bean);
				} else if ("deliveryGoodsFee".equals(bindingEvent
						.getPropertyName())) {// 送货费
					deliveryGoodsFeeListener(bean);
				} else if ("carDirectDelivery".equals(bindingEvent
						.getPropertyName())) {// 大车直送
					carDirectDeliveryListener(bean);
				} else if ("preciousGoods".equals(bindingEvent
						.getPropertyName())) {// 贵重物品
					preciousGoodsListener(bean);
				} else if ("flightNumberType".equals(bindingEvent
						.getPropertyName())) {// 航班类型
					flightNumberTypeListener(bean);
				} else if ("freightMethod".equals(bindingEvent
						.getPropertyName())) {// 合票类型
					freightMethodListener(bean);
				} else if ("receiveCustomerAddress"
						.equalsIgnoreCase(bindingEvent.getPropertyName())) {
					receiveCustomerAddressListener(bean);// 收货人地址
				} else if ("prePayAmount".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 预付金额
					prePayAmountListener(bean);
				} else if ("toPayAmount".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 到付金额
					toPayAmountListener(bean);
				} else if ("promotionsCode".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 优惠编码
					promotionsCodeListener(bean);
				} else if ("innerNotes".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 对内备注
					innerNotesListener(bean);// 对内备注
				} else if ("transportFee".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 开单报价
					transportFeeListener(bean, bean);// 开单报价
				} else if ("receiveOrgName".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 收货部门
					receiveOrgName(bean);
				} else if ("insuranceFee".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {
					insuranceFee(bean);// 快递手填保费
				} else if ("kilometer".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 公里数
					kilometerListener();
				} else if ("deliveryEmployeeNo".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 公里数
					deliveryEmployeeNoListener(bean);// 发货人工号
				} else if ("specialOffer".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {// 活动类型
		    		ui.buttonPanel.getBtnSubmit().setEnabled(false);
		    		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
				} else if ("activeInfo".equalsIgnoreCase(bindingEvent
						.getPropertyName())) {
					// 市场推广活动名称
					activeInfoListener(bean);
				}
				//liding comment for NCI Project
				/**
				 * 对交易流水号进行监控
				 * 
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-01-23
				 */
//				else if ("transactionSerialNumber".equals(bindingEvent
//						.getPropertyName())) {
//					verificate(bean.getTransactionSerialNumber());
//				} 
				else if ("internalDeliveryType".equals(bindingEvent
						.getPropertyName())) {
					internalDeliveryListener(bean);
				} else if ("partnerBilling".equals(bindingEvent
						.getPropertyName())) {// 伙伴开单
					partnerBillingListener(bean);
				}
				/**
				 * 对上门发货进行监听
				 */
				else if ("homeDelivery".equals(bindingEvent.getPropertyName())) {
					homehomeDeliveryListener(bean);
				}
				// 如果是修改了活动类型，禁用提交、暂存等按钮
			}
		} catch (BusinessException w) {
			log.error("WaybillValidateException", w);
			if (!"".equals(w.getMessage())) {
				MsgBox.showInfo(w.getMessage());
			}
		}
	}

	/**
	 * 市场推广活动名称监听事件
	 * 
	 * @创建时间 2014-5-20 上午10:46:58
	 * @创建人： WangQianJin
	 */
	private void activeInfoListener(WaybillPanelVo bean) {
		// 提交为不可编辑
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
		ui.buttonPanel.getBtnSubmit().setEnabled(false);
	}

	/**
	 * 发货客户编码监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-12-14 下午3:39:25
	 */
	private void deliveryCustomerCodeListener(ExpWaybillPanelVo bean) {
		// 当客户改变时，即月结客户的发生改变，可能不再适用客户优惠(参见DEFECT-543)
		bean.setHandInsuranceFee(false);
		// gyk
		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);
		bean.setPackageFee(BigDecimal.ZERO);
	}

	/**
	 * 收入部门监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-12-14 下午3:21:44
	 */
	private void receiveOrgNameListener(ExpWaybillPanelVo bean) {
		// 当入部门改变时，即月结客户的绑定部门发生改变，可能不再适用客户优惠(参见DEFECT-543)
		// bean.setHandInsuranceFee(false);
	}

	// 发货人工号
	private void deliveryEmployeeNoListener(ExpWaybillPanelVo bean) {
		if (StringUtils.isNotEmpty(bean.getDeliveryEmployeeNo())) {
			if (!NumberValidate.checkBetweenLength(
					bean.getDeliveryEmployeeNo(), NumberConstants.NUMBER_6, NumberConstants.NUMBER_6)) {
				 	bean.setDeliveryEmployeeNo("");
				throw new WaybillGisPickupOrgException(
						i18n.get("foss.gui.creating.listener.Waybill.exception.deliveryEmpCode"));
			} else if (!NumberValidate.checkIsAllNumber(bean
					.getDeliveryEmployeeNo())
					&&

					!NumberValidate.checkIsAllNumberAndStartEnglish(bean
							.getDeliveryEmployeeNo())) {

				 bean.setDeliveryEmployeeNo("");
				throw new WaybillGisPickupOrgException(
						i18n.get("foss.gui.creating.listener.Waybill.exception.deliveryEmpCode"));
			 }

		}
	}

	/**
	 * @param bean
	 */
	private void deliveryCustomerAddress(ExpWaybillPanelVo bean) {
		if (bean.getDeliveryCustomerAddress() != null
				&& bean.getDeliveryCustomerAddress().length() > NumberConstants.NUMBER_100) {
			throw new WaybillGisPickupOrgException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.adresslength"));
		}
	}

	/**
	 * 快递手填保费
	 * 
	 * @param bean
	 */
	private void insuranceFee(ExpWaybillPanelVo bean) {
		if (bean.getInsuranceFee() != null
				&& bean.getInsuranceFee().doubleValue() < 0) {
			bean.setInsuranceFee(BigDecimal.ZERO);
			bean.setInsuranceRate(BigDecimal.ZERO);
		}

		BigDecimal feeUp = bean.getInsuranceFeeUp();
		if (feeUp != null) {
			if (bean.getInsuranceFee() != null
					&& bean.getInsuranceFee().doubleValue() > feeUp
							.doubleValue()) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.waybillDescriptor.insurrancefee.max"));
				bean.setInsuranceFee(BigDecimal.ZERO);
				bean.setInsuranceRate(BigDecimal.ZERO);
			}
		}

		// 标识保费已手工修改过（参见DEFECT-543）
		bean.setHandInsuranceFee(true);

		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 修改了原始运单号以后会调用
	 * 
	 * @param bean
	 */
	private void originalWaybillNoListener(ExpWaybillPanelVo bean) {

		CrmReturnedGoodsDto d = null;
		if (StringUtils.isEmpty(bean.getOriginalWaybillNo())) {
			return;
		}

		if (bean.getReturnType() == null
				|| StringUtils.isEmpty(bean.getReturnType().getValueCode())) {
			bean.setOriginalWaybillNo(null);
			return;
		}

		if (!NumberValidate.checkBetweenLength(bean.getOriginalWaybillNo(), 10,
				10)) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.waybillDescriptor.waybillNo.lengthexp"));
			bean.setOriginalWaybillNo(null);
			 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
			return;
		}

		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(
				WaybillConstants.LOGIN_TYPE).toString())) {
			// 这里要在线开单才可以

			IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
					.getService(IWaybillHessianRemoting.class);
			WaybillDto dto = waybillRemotingService.queryWaybillByNo(bean
					.getOriginalWaybillNo());

			// 运单号查不到 无效运单号
			if (dto == null || dto.getWaybillEntity() == null) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.notexist"));
				bean.setOriginalWaybillNo(null);
				 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
				return;
			}
			//如果是悟空的单子则不让在FOSS开单 - 272311-sangwenhao
			if(StringUtils.equals(dto.getWaybillEntity().getIsecs(),WaybillConstants.YES)){
				MsgBox.showInfo(i18n
						.get("foss.gui.creatingexp.listener.Waybill.OriginalWaybillNo.isECS"));
				bean.setOriginalWaybillNo(null);
				 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
				return;
			}
			
			if (!CommonUtils.directDetermineIsExpressByProductCode(dto.getWaybillEntity().getProductCode())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.notexpress"));				bean.setOriginalWaybillNo(null);
				return;
			}
			List<StockEntity> stockEntities=waybillRemotingService.queryStockByWaybillNo(bean
					.getOriginalWaybillNo());

			// 没有签收的情况下 不能做返货或者返单
			//FOSS20150106版本作废该校验--改为校验货物是否在到达部门库存
			/*if (!dto.isAllSigned()
					&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(bean.getReturnType().getValueCode())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.notsign"));
				bean.setOriginalWaybillNo(null);
				return;
			}*/
			
			if (validateStock(bean.getOriginalWaybillNo())&&
					WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(bean.getReturnType().getValueCode())) {
				MsgBox.showInfo("此运单当前不在本部门库存，暂不能开返单!");
				bean.setOriginalWaybillNo(null);
				return;
			}

			// 新开单如果是返单 则原来单号必须是原单返回类型
			if (bean.getReturnType() != null
					&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(bean.getReturnType().getValueCode())) {

				// 原来的单不是原件返单 不能开返单
				if (!WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(dto.getWaybillEntity().getReturnBillType())) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.notReturnBillType"));
					bean.setOriginalWaybillNo(null);
					return;
				}

				WaybillDto dto2 = waybillRemotingService.queryWaybillByReturnBillNo(bean.getOriginalWaybillNo());
				if (dto2 != null && dto2.getWaybillEntity() != null) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo.onlyOneValid"));
					bean.setOriginalWaybillNo(null);
					return;
				}

			}
			// 新开单如果是返货，则判断是否进行了返货申请
			if (bean.getReturnType() != null
					&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(bean.getReturnType().getValueCode())) {

				CrmReturnedGoodsDto crmReturnedGoodsDto = new CrmReturnedGoodsDto();
				crmReturnedGoodsDto.setOriWaybill(bean.getOriginalWaybillNo());
				List<CrmReturnedGoodsDto> queryResult = waybillService.queryReturnGoodsWorkOrder(crmReturnedGoodsDto);
				int resultSize = queryResult.size();
				if (resultSize == 1) {
					d = queryResult.get(0);
				} else if (resultSize > 1) {
					// 取最新的数据
					long[] time = new long[resultSize];
					Date date;
					for (int i = 0; i < resultSize; i++) {
						date = queryResult.get(i).getCreateTime();
						if (date != null && !date.toString().equals("")) {
							time[i] = queryResult.get(i).getCreateTime()
									.getTime();
						}
					}
					long max = 0;
					for (int j = 0; j < time.length; j++) {
						if (time[j] > max) {
							max = time[j];
						}
					}
					Date maxDate = new Date(max);
					// 数据创建时间相同个数
					int value = 0;
					for (int i = 0; i < resultSize; i++) {
						date = queryResult.get(i).getCreateTime();
						if (date != null && !date.toString().equals("")) {
							if (maxDate.equals(date)) {
								d = queryResult.get(i);
								value++;
							}
						}
					}
					// 数据创建时间相同个数大于1时，抛出异常
					if (value > 1) {
						bean.setOriginalWaybillNo(null);
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.listener.Waybill.exception.createDateIsSame")
										+ "${user.home}/foss-framework.log");
					}
				}
				if (d != null) {
					if (!((WaybillConstants.RETURNTYPE_OTHER_CITY.equals(d.getReturnType())
							|| WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(d.getReturnType()))
							&& WaybillConstants.ACCEPTSTATUS_HANDLED.equals(d.getReturnStatus()))) {
						throw new WaybillValidateException("原单号返货申请未受理或者返货类型不是非同城转寄或客户拒收");
					}
					// //收货客户手机
					// dto.getWaybillEntity().setReceiveCustomerMobilephone(d.getReturnPhoneReceive());
					// //收货客户电话
					// dto.getWaybillEntity().setReceiveCustomerPhone(d.getReturnTelReceive());
					// //收货客户名称收货客户名称
					// dto.getWaybillEntity().setReceiveCustomerName("");
					// //收货客户编码
					// dto.getWaybillEntity().setReceiveCustomerCode("");
					// //收货联系人
					// dto.getWaybillEntity().setReceiveCustomerContact(d.getReturnManReceive());
					// //行政区域 省、市、区
					// dto.getWaybillEntity().setReceiveCustomerProvCode(d.getReturnProvinceId());
					// dto.getWaybillEntity().setReceiveCustomerCityCode(d.getReturnCityId());
					// dto.getWaybillEntity().setReceiveCustomerDistCode(d.getReturnAreaId());

					// 收货地址（返货单）
					// StringBuilder returnBillReceiveAddress = new
					// StringBuilder();
					// returnBillReceiveAddress.append(d.getReturnProvince());//
					// 省
					// if(returnBillReceiveAddress.length()>0){
					// returnBillReceiveAddress.append("-");
					// }
					// returnBillReceiveAddress.append(d.getReturnCity());// 市
					// if(returnBillReceiveAddress.length()>0){
					// returnBillReceiveAddress.append("-");
					// }
					// returnBillReceiveAddress.append(d.getReturnArea());// 区
					// if(returnBillReceiveAddress.length()>0){
					// returnBillReceiveAddress.append("-");
					// }
					// returnBillReceiveAddress.append(d.getReturnDetailaddress());//
					// 详细地址
					// 具体地址
					// dto.getWaybillEntity().setReceiveCustomerAddress(d.getReturnDetailaddress());
					// codAmounts = d.getReturnMoneyReceive();
					// flg = true;
				}
			}
			// TODO 这里要判断将原单回写到新的运单表中去 同时将很多地方置为灰色 不可编辑
			// 这里

			importWaybillVoData(dto, d);

			if (d != null) {
				// 代收货款设值
				bean.setCodAmount(d.getReturnMoneyReceive());
				// 设置保价的值
				bean.setInsuranceAmount(d.getReturnMoneyInsured());
				// 设置公司原因还是自己原因
				bean.setReturnBillReason(d.getReturnReason());
			}

			if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(bean
					.getReturnType().getValueCode())) {
				setWaybillUiByReturnType(false);
			} else {
				setWaybillUiByReturnType(true);
			}

			// 这里有大量逻辑
		} else {
			// 离线的时候因为无法导入原始单 也无法检查原始单是否被签收等信息 所以是不能开单的
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo"));
			bean.setOriginalWaybillNo(null);
			return;
		}

	}

	
	/**
	 * 校验到达部门库存
	 * @param oriWayBillNo
	 * @return
	 */
	private boolean validateStock(String oriWayBillNo){
		//若原单号存在落地配外发交接单（作废和有效），则返货开单时不校验该货物是否在到达部门库存
		boolean isWaiFa = waybillService
				.queryBeLdpHandOveredByWaybillNo(oriWayBillNo);
		if (!isWaiFa) {
			boolean stock = true;
			if(StringUtil.isNotEmpty(oriWayBillNo)){
				WaybillEntity entity = waybillService
						.queryWaybillByNumber(oriWayBillNo);
				// 单号不在到达部门库存中不能返货开单
				List<StockEntity> stockStatus = waybillService
						.queryStockByWaybillNo(oriWayBillNo);
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
			}
			return stock;
		}
		return false;
	}
	
	/**
	 * @param dto
	 */
	private void importWaybillVoData(WaybillDto value, CrmReturnedGoodsDto d) {

		// 业务工具类
		BusinessUtils businessUtils = new BusinessUtils();
		// 获取绑定bean
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo vo = waybillBinder.getBean();
		// 拷贝数据

		DataDictionaryValueVo valueVo = vo.getReturnType();
		String returnType = null;
		if (valueVo != null) {
			returnType = valueVo.getValueCode();
		}

		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
				.equals(returnType)) {
			if (value.getWaybillExpressEntity() != null
					&& !FossConstants.YES.equals(value
							.getWaybillExpressEntity().getCanReturnCargo())) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.cannotReturnCargo"));
				return;
			}
		}
		// 菜鸟返货折扣
		if (value.getWaybillEntity().getBillTime() != null) {
	    	vo.setReturnbilltime(value.getWaybillEntity().getBillTime());
		} else {
	    	vo.setReturnbilltime(new Date());
	    }
	    vo.setReturnTransportFee(value.getWaybillEntity().getTransportFee());
	    vo.setReturnInsuranceFee(value.getWaybillEntity().getInsuranceFee());
		if (d != null) {
	    	vo.setReceiveCustomerId("");// 发货客户ID
			vo.setReceiveCustomerContactId("");// 发货客户联系人ID
			vo.setReceiveCustomerCode("");// 发货客户编码
			vo.setReceiveBigCustomer("");// 大客户标记
			vo.setReceiveCustomerName("");// 发货客户名称
			vo.setReceiveCustomerMobilephone(CommonUtils.defaultIfNull(d
					.getReturnPhoneReceive()));// 发货客户手机
			vo.setReceiveCustomerPhone(CommonUtils.defaultIfNull(d
					.getReturnTelReceive()));// 发货客户电话
			vo.setReceiveCustomerContact(CommonUtils.defaultIfNull(d
					.getReturnManReceive()));// 发货客户联系人
			// 发货具体地址
			vo.setReceiveCustomerAddress(CommonUtils.defaultIfNull(d
					.getReturnDetailaddress()));
			/**
			 * 发货客户省市区
			 */
			AddressFieldDto consignerAddress = businessUtils.getProvCityCounty(
					d.getReturnProvinceId(), d.getReturnCityId(),
					d.getReturnAreaId());
			if (null != consignerAddress) {
			    // 发货省份
			    vo.setReceiveCustomerProvCode(consignerAddress.getProvinceId());
			    // 发货市
			    vo.setReceiveCustomerCityCode(consignerAddress.getCityId());
			    // 发货区
			    vo.setReceiveCustomerDistCode(consignerAddress.getCountyId());
			    // 发货区域
			    vo.setReceiveCustomerAreaDto(consignerAddress);
				vo.setReceiveCustomerArea(businessUtils
						.getAddressAreaText(consignerAddress));
			    /**
			     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
			     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
			     */
				ui.getConsigneePanel().getTxtConsigneeArea()
						.setAddressFieldDto(consignerAddress);
			}
		} else {
			vo.setReceiveCustomerId(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getDeliveryCustomerId()));// 发货客户ID
			vo.setReceiveCustomerContactId(value.getWaybillEntity()
					.getDeliverCustContactId());// 发货客户联系人ID
			vo.setReceiveCustomerCode(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getDeliveryCustomerCode()));// 发货客户编码
			vo.setReceiveBigCustomer(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getDeliveryBigCustomer()));// 大客户标记
			vo.setReceiveCustomerName(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getDeliveryCustomerName()));// 发货客户名称
			vo.setReceiveCustomerMobilephone(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getDeliveryCustomerMobilephone()));// 发货客户手机
			vo.setReceiveCustomerPhone(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getDeliveryCustomerPhone()));// 发货客户电话
			vo.setReceiveCustomerContact(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getDeliveryCustomerContact()));// 发货客户联系人
			// 发货具体地址
			vo.setReceiveCustomerAddress(value.getWaybillEntity()
					.getDeliveryCustomerAddress());

			/**
			 * 发货客户省市区
			 */
			AddressFieldDto consignerAddress = businessUtils.getProvCityCounty(
					value.getWaybillEntity().getDeliveryCustomerProvCode(),
					value.getWaybillEntity().getDeliveryCustomerCityCode(),
					value.getWaybillEntity().getDeliveryCustomerDistCode());
			if (null != consignerAddress) {
			    // 发货省份
			    vo.setReceiveCustomerProvCode(consignerAddress.getProvinceId());
			    // 发货市
			    vo.setReceiveCustomerCityCode(consignerAddress.getCityId());
			    // 发货区
			    vo.setReceiveCustomerDistCode(consignerAddress.getCountyId());
			    // 发货区域
			    vo.setReceiveCustomerAreaDto(consignerAddress);
				vo.setReceiveCustomerArea(businessUtils
						.getAddressAreaText(consignerAddress));
			    /**
			     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
			     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
			     */
				ui.getConsigneePanel().getTxtConsigneeArea()
						.setAddressFieldDto(consignerAddress);
			}
	    }
		// 对调信息

		vo.setDeliveryCustomerId(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getDeliveryCustomerId()));// 发货客户ID
		vo.setDeliveryCustomerContactId(value.getWaybillEntity()
				.getDeliverCustContactId());// 发货客户联系人ID
		vo.setDeliveryCustomerCode(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getDeliveryCustomerCode()));// 发货客户编码
		vo.setDeliveryCustomerName(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getDeliveryCustomerName()));// 发货客户名称
		vo.setDeliveryCustomerMobilephone(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getDeliveryCustomerMobilephone()));// 发货客户手机
		vo.setDeliveryCustomerPhone(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getDeliveryCustomerPhone()));// 发货客户电话
		vo.setDeliveryCustomerContact(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getDeliveryCustomerContact()));// 发货客户联系人
		// 发货具体地址

		vo.setDeliveryCustomerAddress(value.getWaybillEntity()
				.getDeliveryCustomerAddress());

		/**
		 * 发货客户省市区
		 */
		AddressFieldDto consigneeAddress = businessUtils.getProvCityCounty(
				value.getWaybillEntity().getDeliveryCustomerProvCode(), value
						.getWaybillEntity().getDeliveryCustomerCityCode(),
				value.getWaybillEntity().getDeliveryCustomerDistCode());
		if (null != consigneeAddress) {
		    // 发货省份
		    vo.setDeliveryCustomerProvCode(consigneeAddress.getProvinceId());
		    // 发货市
		    vo.setDeliveryCustomerCityCode(consigneeAddress.getCityId());
		    // 发货区
		    vo.setDeliveryCustomerDistCode(consigneeAddress.getCountyId());
		    // 发货区域
		    vo.setDeliveryCustomerAreaDto(consigneeAddress);
			vo.setDeliveryCustomerArea(businessUtils
					.getAddressAreaText(consigneeAddress));
		    /**
		     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
		     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
		     */
			ui.getConsignerPanel().getTxtConsignerArea()
					.setAddressFieldDto(consigneeAddress);
		}

		// 判断运输实行是否为空，若不为空直接set.为空则默认是标准快递
		if (value.getWaybillEntity() != null
				&& StringUtils.isNotEmpty(value.getWaybillEntity()
						.getProductCode())) {
			ExpCommon.setProductCode(ui, vo, value.getWaybillEntity()
					.getProductCode());
		} else {
			ExpCommon.setProductCode(ui, vo,
					CommonUtils.defaultIfNull(ExpWaybillConstants.PACKAGE));
		}
		// 判定是否电商尊享件，再判定是否月结客户,是否可以开月结单
		if (null != vo.getProductCode()
				&& WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE
						.equals(vo.getProductCode().getCode())) {
			// 判定客户编码是否存在
			if (StringUtils.isNotEmpty(vo.getDeliveryCustomerCode())) {
				// 判定是否月结
				CusBargainEntity cusBargainEntity = waybillService
						.queryCusBargainByCustCode(vo.getDeliveryCustomerCode());
				if (null != cusBargainEntity
						&& DictionaryValueConstants.CLEARING_TYPE_MONTH
								.equals(cusBargainEntity.getExPayWay())) {
					vo.setChargeMode(true);
				} else {
					vo.setChargeMode(false);
				}
				// 判定是否电商尊享
				if (null != cusBargainEntity
						&& FossConstants.YES.equals(cusBargainEntity
								.getIfElecEnjoy())) {
					vo.setIsElectricityToEnjoy(FossConstants.YES);
				} else {
					vo.setIsElectricityToEnjoy(FossConstants.NO);
				}
			}
		}

		// 根据运输性质的改变，改变提货方式
		ExpCommon.changePickUpMode(vo, ui);

		// 空运、偏线以及中转下线无法选择签收单返单
		ExpCommon.setReturnBill(vo, ui);
		// 偏线与空运不能选择预付费保密
		ExpCommon.setSecretPrepaid(vo, ui);

		// 提货方式
		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
				.equals(returnType)) {
			vo.setReceiveMethod(ExpCommon.getCombBoxValue(
					ui.getPikcModeModel(), WaybillConstants.DELIVER_UP));
		} else {
			vo.setReceiveMethod(ExpCommon.getCombBoxValue(
					ui.getPikcModeModel(), value.getWaybillEntity()
							.getReceiveMethod()));
		}
		// 设置提货网点
		if (value.getWaybillEntity().getReceiveOrgCode() != null) {
			BranchVo pickup = businessUtils.getCustomerPickupOrg(value
					.getWaybillEntity().getReceiveOrgCode(), value
					.getWaybillEntity().getProductCode(), value
					.getWaybillEntity().getBillTime());
			if (pickup != null) {
				// 这个是为了判断直接开单返货单与补录返货单的区别
				if (vo.getTargetOrgCode() != null) {
					pickup.setCode(vo.getCustomerPickupOrgCode().getCode());
				} else {
					vo.setCustomerPickupOrgCode(pickup);// 提货网点
					vo.setCustomerPickupOrgName(pickup.getName());// 提货网点名称
					vo.setTargetOrgCode(pickup.getTargetOrgName());// 目的站
				}

				// 是否可代收货款
				vo.setCanAgentCollected(pickup.getCanAgentCollected());
				// 是否可货到付款
				vo.setArriveCharge(pickup.getArriveCharge());
			}

			// TODO

			SalesDepartmentService salesDepartmentService = DownLoadDataServiceFactory
					.getSalesDepartmentService();
			SalesDepartmentCityDto dto  = new SalesDepartmentCityDto();
			dto.setSalesDepartmentCode(pickup.getCode());
			SalesDepartmentCityDto result = salesDepartmentService
					.querySalesDepartmentCityInfo(dto);
			// querySalesDepartmentCityInfo
			SaleDepartmentEntity saleDepartmentEntity = WaybillServiceFactory
					.getWaybillService().querySaleDeptByCode(pickup.getCode());
			if (saleDepartmentEntity != null && result != null) {
				// 营业部是否可以快递接货，如果是的话 就是试点营业部
				result.setTestSalesDepartment(saleDepartmentEntity
						.getCanExpressPickupToDoor());
			}
				if (value.getWaybillEntity() != null
					&& StringUtils.isNotEmpty(value.getWaybillEntity().getReceiveMethod())
					&& CommonUtils.directDetermineIsExpressByProductCode(value.getWaybillEntity().getProductCode())
					&& !CommonUtils.verdictPickUpSelf(value.getWaybillEntity().getReceiveMethod())) {
				if (result != null && StringUtils.isNotEmpty(result.getCityName())) {					vo.setTargetOrgCode(result.getCityName());
				} else {
					// TODO---无试点城市
					vo.setTargetOrgCode("无试点城市");
				}

			} else {

				String simpleName = bu.getSimpleName(pickup.getCode(),
						vo.getBillTime());
				if (!"".equals(simpleName)) {
					vo.setTargetOrgCode(simpleName);
				} else {
					vo.setTargetOrgCode(pickup.getTargetOrgName());
				}

			}
			vo.setTargetSalesDepartmentCityDto(result);

		}

		// 返单类别
		vo.setReturnBillType(ExpCommon.getCombBoxValue(
				ui.getCombReturnBillTypeModel(),
				WaybillConstants.NOT_RETURN_BILL));

		// vo.setLoadMethod(CommonUtils.defaultIfNull(value.getWaybillEntity().getLoadMethod()));//
		// 配载类型
		// vo.setLoadLineCode(value.getWaybillEntity().getLoadLineCode());//
		// 配载线路
		// vo.setLoadOrgCode(CommonUtils.defaultIfNull(value.getWaybillEntity().getLoadOrgCode()));//
		// 配载部门
		// vo.setLastLoadOrgCode(CommonUtils.defaultIfNull(value.getWaybillEntity().getLastLoadOrgCode()));//
		// 最终配载部门

		vo.setPickupToDoor(BooleanConvertYesOrNo.stringToBoolean(value
				.getWaybillEntity().getPickupToDoor()));// 是否上门接货
		vo.setDriverCode(value.getWaybillEntity().getDriverCode());// 司机
		vo.setPickupCentralized(BooleanConvertYesOrNo.stringToBoolean(value
				.getWaybillEntity().getPickupCentralized()));// 是否集中接货

		// vo.setPreDepartureTime(waybillPending.getPreDepartureTime());//
		// 预计出发时间
		// vo.setPreCustomerPickupTime(waybillPending.getPreCustomerPickupTime());//预计派送/提货时间
		// vo.setPreCustomerPickupTime(new Date());// 预计派送/提货时间
		// vo.setPreDepartureTime(waybillPending.getPreDepartureTime());//
		// 预计派送/提货时间
		vo.setCarDirectDelivery(BooleanConvertYesOrNo.stringToBoolean(value
				.getWaybillEntity().getCarDirectDelivery()));// 是否大车直送

		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
				.equals(returnType)) {
			vo.setGoodsName(i18n
					.get("foss.gui.creating.listener.Waybill.returnGo"));// 货物名称
		} else {
			vo.setGoodsName(value.getWaybillEntity().getGoodsName());// 货物名称
		}

		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
				.equals(returnType)) {
			vo.setGoodsQtyTotal(value.getWaybillEntity().getGoodsQtyTotal());// 货物总件数
			vo.setGoodsWeightTotal(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getGoodsWeightTotal()));// 货物总重量
			vo.setGoodsVolumeTotal(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getGoodsVolumeTotal()));// 货物总体积
			vo.setGoodsSize(value.getWaybillEntity().getGoodsSize());// 货物尺寸
			vo.setGoodsType(value.getWaybillEntity().getGoodsTypeCode());// 货物类型

			vo.setPaper(value.getWaybillEntity().getPaperNum());// 纸
			if (vo.getPaper() == null) {
				vo.setPaper(Integer.valueOf(0));
			}
			vo.setWood(value.getWaybillEntity().getWoodNum());// 木
			if (vo.getWood() == null) {
				vo.setWood(Integer.valueOf(0));
			}

			vo.setFibre(value.getWaybillEntity().getFibreNum());// 纤
			if (vo.getFibre() == null) {
				vo.setFibre(Integer.valueOf(0));
			}

			vo.setSalver(value.getWaybillEntity().getSalverNum());// 托
			if (vo.getSalver() == null) {
				vo.setSalver(Integer.valueOf(0));
			}

			vo.setMembrane(value.getWaybillEntity().getMembraneNum());// 膜
			if (vo.getMembrane() == null) {
				vo.setMembrane(Integer.valueOf(0));
			}

			vo.setOtherPackage(value.getWaybillEntity().getOtherPackage());// 其他
			vo.setGoodsPackage(value.getWaybillEntity().getGoodsPackage());// 货物包装

			ui.cargoInfoPanel.getTxtTotalPiece().setEditable(false);
			ui.cargoInfoPanel.getTxtVolume().setEditable(false);
			ui.cargoInfoPanel.getTxtWeight().setEditable(false);
			ui.cargoInfoPanel.getTxtSize().setEditable(false);
			ui.cargoInfoPanel.getTxtFibre().setEditable(false);
			ui.cargoInfoPanel.getTxtMembrane().setEditable(false);
			ui.cargoInfoPanel.getTxtOther().setEditable(false);
			ui.cargoInfoPanel.getTxtPaper().setEditable(false);
			ui.cargoInfoPanel.getTxtSalver().setEditable(false);
		} else {
			vo.setGoodsQtyTotal(Integer.valueOf(1));

			vo.setGoodsWeightTotal(CommonUtils.defaultIfNull(BigDecimal
					.valueOf(0.1)));// 货物总重量
			vo.setGoodsVolumeTotal(CommonUtils.defaultIfNull(BigDecimal
					.valueOf(0.01)));// 货物总体积
			vo.setGoodsSize(null);// 货物尺寸
			vo.setGoodsType(null);// 货物类型

			vo.setPaper(Integer.valueOf(1));// 纸
			vo.setWood(Integer.valueOf(0));// 木
			vo.setFibre(Integer.valueOf(0));// 纤
			vo.setSalver(Integer.valueOf(0));// 托
			vo.setMembrane(Integer.valueOf(0));// 膜
			vo.setOtherPackage(null);// 其他
			vo.setGoodsPackage("1纸");// 货物包装

		}

		// vo.setIsPassDept(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getIsPassOwnDepartment()));
		// vo.setPreciousGoods(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getPreciousGoods()));//
		// 是否贵重物品
		// vo.setSpecialShapedGoods(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getSpecialShapedGoods()));//
		// 是否异形物品

		DataDictionaryValueVo outerNotes = new DataDictionaryValueVo();
		outerNotes.setValueCode(value.getWaybillEntity().getOuterNotes());
		vo.setOuterNotes(outerNotes);// 对外备注
		vo.setInnerNotes(value.getWaybillEntity().getInnerNotes());// 对内备注
		vo.setTransportationRemark(value.getWaybillEntity()
				.getTransportationRemark());// 储运事项
		// 关联单号费用 = 到付金额-代收
		BigDecimal connectionNumFee = value.getWaybillEntity().getToPayAmount()
				.subtract(value.getWaybillEntity().getCodAmount());
		if (connectionNumFee != null
				&& connectionNumFee.compareTo(BigDecimal.ZERO) > 0) {
			vo.setConnectionNumFee(connectionNumFee);// 关联单号费用
		}

		// 退款类型
		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
				.equals(returnType)) {
			vo.setRefundType(ExpCommon.getCombBoxValue(ui
					.getCombRefundTypeModel(), value.getWaybillEntity()
					.getRefundType()));
		} else {
			vo.setRefundType(null);
		}

		// 原单号应收到付款
		BigDecimal receiveablePayAmoout = waybillService.queryFinanceSign(vo
				.getOriginalWaybillNo());
		if (receiveablePayAmoout != null) {
			// 关联单号费用
			vo.setConnectionNumFee(receiveablePayAmoout);
		} else {
			// 关联单号费用
			vo.setConnectionNumFee(BigDecimal.ZERO);
		}

		vo.setSecretPrepaid(BooleanConvertYesOrNo.stringToBoolean(value
				.getWaybillEntity().getSecretPrepaid()));// 预付费保密
		vo.setToPayAmount(BigDecimal.valueOf(0));// 到付金额
		vo.setPrePayAmount(BigDecimal.valueOf(0));// 预付金额

		// 优惠费用
		vo.setPromotionsFee(BigDecimal.valueOf(0));
		// 优惠费用画布
		vo.setPromotionsFeeCanvas(String.valueOf(BigDecimal.valueOf(0)));

		// 运费计费类型
		// if(StringUtil.isEmpty(waybillPending.getBillingType())){
		// vo.setBillingType(ExpCommon.getCombBoxValue(ui.getCombBillingType(),WaybillConstants.BILLINGWAY_WEIGHT));
		// }else{
		// vo.setBillingType(ExpCommon.getCombBoxValue(ui.getCombBillingType(),waybillPending.getBillingType()));
		// }
		// 运费计费费率
		vo.setUnitPrice(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));

		// 公布价运费
		vo.setTransportFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 公布价运费画布
		vo.setTransportFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(BigDecimal.valueOf(0))));

		vo.setValueAddFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));// 增值费用
		// 开单付款方式
		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
				.equals(returnType)) {
			vo.setPaidMethod(ExpCommon.getCombBoxValue(
					ui.getCombPaymentModeModel(),
					WaybillConstants.ARRIVE_PAYMENT));
			// vo.setPaidMethod(ExpCommon.getCombBoxValue(ui.getCombPaymentModeModel(),value.getWaybillEntity().getPaidMethod()));
		} else {
			vo.setPaidMethod(ExpCommon.getCombBoxValue(
					ui.getCombPaymentModeModel(),
					WaybillConstants.ARRIVE_PAYMENT));
		}
		// 根据设置的付款方式，设置接送货是否可编辑
		ExpCommon.selfPickup(vo, ui);
		// vo.setArriveType(CommonUtils.defaultIfNull(waybillPending.getArriveType()));//
		// 到达类型
		// vo.setActive(waybillPending.getActive());// 运单状态
		// vo.setWaybillstatus(waybillPending.getPendingType()); //运单处理状态

		// 总费用
		vo.setTotalFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 总费用画布
		vo.setTotalFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(BigDecimal.valueOf(0))));

		vo.setForbiddenLine(value.getWaybillEntity().getForbiddenLine());// 禁行
		vo.setFreightMethod(ExpCommon.getCombBoxValue(ui.getFreightMethod(),
				value.getWaybillEntity().getFreightMethod()));// 合票方式
		// 设置航班类型
		vo.setFlightNumberType(ExpCommon.getCombBoxValue(ui
				.getFlightNumberType(), value.getWaybillEntity()
				.getFlightNumberType()));
		vo.setFlightShift(CommonUtils.defaultIfNull(value.getWaybillEntity()
				.getFlightShift()));// 航班时间
		vo.setPromotionsCode("");// 优惠编码
		vo.setCreateTime(new Date());// 创建时间
		vo.setModifyTime(new Date());// 更新时间
		vo.setBillTime(new Date());// 开单时间
		// Boolean isPda =
		// WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillPending.getPendingType())
		// ? Boolean.TRUE:Boolean.FALSE;
		// vo.setIsPdaBill(isPda);// 是否为PDA运单
		// if(isPda){
		// vo.setBillTime(waybillPending.getCreateTime());// 开单时间
		// }else{
		//
		// }

		vo.setCurrencyCode(value.getWaybillEntity().getCurrencyCode());// 币种
		vo.setIsWholeVehicle(BooleanConvertYesOrNo.stringToBoolean(value
				.getWaybillEntity().getIsWholeVehicle()));// 是否整车运单
		// 是否经过营业部
		vo.setIsPassDept(BooleanConvertYesOrNo.stringToBoolean(value
				.getWaybillEntity().getIsPassOwnDepartment()));
		// 整车约车编号
		vo.setVehicleNumber(StringUtil.defaultIfNull(value.getWaybillEntity()
				.getOrderVehicleNum()));
		vo.setWholeVehicleActualfee(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getWholeVehicleActualfee()));// 整车开单报价
		vo.setWholeVehicleAppfee(CommonUtils.defaultIfNull(value
				.getWaybillEntity().getWholeVehicleAppfee()));// 整车约车报价
		vo.setAccountName(value.getWaybillEntity().getAccountName());// 返款帐户开户名称
		vo.setAccountCode(value.getWaybillEntity().getAccountCode());// 返款帐户开户账户
		vo.setAccountBank(value.getWaybillEntity().getAccountBank());// 返款帐户开户银行
		// 计费重量
		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
				.equals(returnType)) {
			vo.setBillWeight(CommonUtils.defaultIfNull(value.getWaybillEntity()
					.getBillWeight()));
		}
		// vo.setPreArriveTime(value.getWaybillEntity().getPreArriveTime());//
		// 预计到达时间
		// vo.setAddTime(value.getWaybillEntity().getAddTime());// 开单时长
		if (null != value.getWaybillExpressEntity()
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
						.equals(returnType)
				&& FossConstants.ACTIVE.equals(value.getWaybillExpressEntity()
						.getChangeVolume())) {
			vo.setChangeVolume(FossConstants.ACTIVE);
		}

		// 送货费
		vo.setDeliveryGoodsFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 送货费画布
		vo.setDeliveryGoodsFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(BigDecimal.valueOf(0))));

		// 其他费用
		vo.setOtherFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 其他费用画布
		vo.setOtherFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(BigDecimal.valueOf(0))));

		// 包装手续费
		vo.setPackageFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 包装手续费画布
		vo.setPackageFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(BigDecimal.valueOf(0))));

		// 接货费
		vo.setPickupFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 接货费画布
		vo.setPickUpFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(BigDecimal.valueOf(0))));
		// 保价声明价值
		if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
				.equals(returnType)) {
			vo.setInsuranceAmount(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getInsuranceAmount()));
			// 保价声明价值画布
			vo.setInsuranceAmountCanvas(String.valueOf(CommonUtils
					.defaultIfNull(value.getWaybillEntity()
							.getInsuranceAmount())));
			// 保价费率
			// 将保险费率转换成千分率的格式
			BigDecimal permillageIns = new BigDecimal(
					WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = CommonUtils.defaultIfNull(
					value.getWaybillEntity().getInsuranceRate()).multiply(
					permillageIns);
			vo.setInsuranceRate(feeRate);
			// 保价费
			vo.setInsuranceFee(CommonUtils.defaultIfNull(value
					.getWaybillEntity().getInsuranceFee()));
		} else {
			vo.setInsuranceAmount(CommonUtils.defaultIfNull(BigDecimal
					.valueOf(0)));
			// 保价声明价值画布
			vo.setInsuranceAmountCanvas(String.valueOf(CommonUtils
					.defaultIfNull(BigDecimal.valueOf(0))));
			// 保价费率
			// 将保险费率转换成千分率的格式
			BigDecimal permillageIns = new BigDecimal(
					WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = BigDecimal.valueOf(0);
			vo.setInsuranceRate(feeRate);
			// 保价费
			vo.setInsuranceFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		}
		// 代收货款
		vo.setCodAmount(BigDecimal.valueOf(0));
		// 代收货款画布
		vo.setCodAmountCanvas(String.valueOf(BigDecimal.valueOf(0)));
		// 代收费率
		// 将代收货款费率转换成千分率的格式
		BigDecimal permillageCod = new BigDecimal(WaybillConstants.PERMILLAGE);
		BigDecimal codRate = BigDecimal.valueOf(0);// CommonUtils.defaultIfNull(waybillPending.getCodRate()).multiply(permillageCod);
		// 代收货款费率
		vo.setCodRate(codRate);
		// 代收货款手续费
		vo.setCodFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));

		// 装卸费
		vo.setServiceFee(CommonUtils.defaultIfNull(BigDecimal.valueOf(0)));
		// 装卸费画布
		vo.setServiceFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(BigDecimal.valueOf(0))));

		// 公里数
		vo.setKilometer(value.getWaybillEntity().getKilometer());

		if (vo.getReceiveMethod() != null) {
		/**
		 * 如果非送货时，公里数不可录入，且要清空
		 */
			if (WaybillConstants.SELF_PICKUP.equals(vo.getReceiveMethod()
					.getValueCode())
					|| WaybillConstants.INNER_PICKUP.equals(vo
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(vo
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_SELF_PICKUP.equals(vo
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIRPORT_PICKUP.equals(vo
							.getReceiveMethod().getValueCode())) {
			vo.setKilometer(null);
			// 公里数不可编辑
			ui.transferInfoPanel.getTxtKilometer().setEditable(false);
			} else {
			// 公里数可编辑
			ui.transferInfoPanel.getTxtKilometer().setEditable(true);
		}
		}

		setLoadLine(vo);
		// TODO 小件添加 -------------------------------------
		// vo.setIsAddCode(waybillPending.getIsAddCode());
		// vo.setAddCodeTime(waybillPending.getAddCodeTime());
		// vo.setExpressEmpCode(waybillPending.getExpressEmpCode());
		// vo.setExpressEmpName(waybillPending.getExpressEmpName());
		// vo.setExpressOrgCode(waybillPending.getExpressOrgCode());
		// vo.setExpressOrgName(waybillPending.getExpressOrgName());
		// vo.setPdaSerial(waybillPending.getPdaSerial());
		// vo.setBankTradeSerail(waybillPending.getBankTradeSerail());
		// vo.setOriginalWaybillNo(waybillPending.getOriginalWaybillNo());
		// vo.setReturnType(ExpCommon.getCombBoxValue(ui.getCombWaibillReturnModeModel(),
		// waybillPending.getReturnType()));
		// Z这里需要把工号转化为ExpressOrgCode

	}

	/**
	 * 设置预配线路和预计出发时间与预计到达时间 （摄取方法供GIS地图匹配网点使用）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 上午8:49:17
	 */
	public void setLoadLine(WaybillPanelVo bean) {
		if (bean.getCustomerPickupOrgCode() != null) {
			// 查询始发配载部门、最终配载部门以及线路
			queryLodeDepartmentInfo(bean);
			if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
					.equals(bean.getProductCode().getCode())
					&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
							.equals(bean.getProductCode().getCode())) {
				if(CommonContents.logToggle){
					log.info("运单号："+bean.getWaybillNo()+" 处理时效相关信息 开始...");
				}
				Date leaveTime = getLeaveTime(bean);
				if (leaveTime != null) {
					bean.setPreDepartureTime(leaveTime);// 预计出发时间
					bean.setPreCustomerPickupTime(getPickupDeliveryTime(bean));// 预计派送/自提时间
				} else {
					log.debug("未查询到对应的时效  ");
					MsgBox.showInfo(i18n
							.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.nullRelativeTime"));
				}
				if(CommonContents.logToggle){
					log.info("处理时效相关信息 结束. 运单号："+bean.getWaybillNo());
				}
			}
		}
	}

	/**
	 * 
	 * 获得预计派送/提货时间
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 下午02:17:31
	 */
	private Date getPickupDeliveryTime(WaybillPanelVo bean) {
		// String strOrgCode = "";
		// if(bean.getPickupCentralized()!=null&&bean.getPickupCentralized()){
		// strOrgCode = bean.getPickupCentralizedDeptCode();
		// }else{
		// strOrgCode = bean.getReceiveOrgCode();
		// }
		EffectiveDto effectiveDto = new EffectiveDto();
		if (isPickup(bean)) {
			effectiveDto = waybillService.searchPreSelfPickupTime(bean
					.getCreateOrgCode(), bean.getLastLoadOrgCode(), bean
					.getProductCode().getCode(), bean.getPreDepartureTime(),
					new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getSelfPickupTime();
			} else {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		} else {
			effectiveDto = waybillService.searchPreDeliveryTime(bean
					.getCreateOrgCode(), bean.getLastLoadOrgCode(), bean
					.getProductCode().getCode(), bean.getPreDepartureTime(),
					new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getDeliveryDate();
			} else {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		}
	}

	/**
	 * 
	 * 判断提货方式是否自提
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 上午09:46:55
	 */
	private Boolean isPickup(WaybillPanelVo bean) {
		/**
		 * 判断提货方式是否为空
		 */
		if (bean.getReceiveMethod() != null) {
			String code = bean.getReceiveMethod().getValueCode();
			if (WaybillConstants.SELF_PICKUP.equals(code)
					|| WaybillConstants.INNER_PICKUP.equals(code)
					|| WaybillConstants.AIR_SELF_PICKUP.equals(code)
					|| WaybillConstants.AIR_PICKUP_FREE.equals(code)
					|| WaybillConstants.AIRPORT_PICKUP.equals(code))

			{
				return true;
			} else {
				return false;
			}
		} else {
			return false;
	}
	}

	/**
	 * 
	 * 获得预计出发时间
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 下午02:17:31
	 */
	private Date getLeaveTime(WaybillPanelVo bean) {

		Date leaveTime = waybillService.searchPreLeaveTime(bean
				.getCreateOrgCode(), bean.getLoadOrgCode(), bean
				.getProductCode().getCode(), new Date());
		return leaveTime;
	}

	/**
	 * 
	 * 查询始发配载部门、最终配载部门以及线路
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午03:54:30
	 */
	private void queryLodeDepartmentInfo(WaybillPanelVo bean) {
		if(CommonContents.logToggle){
			log.info("运单号："+bean.getWaybillNo()+" 加载配载部门和线路开始...");
		}
		OrgInfoDto dto = null;
		try {
			// 运输性质非空判断
			if (null == bean.getProductCode()) {
				log.error("运输性质不能为空！");
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.salesDepartmentDialog.exception.transType"));
			}

			if (bean.getPickupCentralized() != null
					&& bean.getPickupCentralized()) {
				dto = waybillService.queryLodeDepartmentInfo(bean
						.getPickupCentralized(), bean.getCreateOrgCode(), bean
						.getCustomerPickupOrgCode().getCode(), bean
						.getProductCode().getCode());
			} else {
				dto = waybillService.queryLodeDepartmentInfo(bean
						.getPickupCentralized(), bean.getCreateOrgCode()/**
				 * 
				 * bean.getReceiveOrgCode()
				 */
				, bean.getCustomerPickupOrgCode().getCode(), bean
						.getProductCode().getCode());
			}
			if (dto == null || dto.getFreightRoute() == null) {
				ui.cargoInfoPanel.getBtnWood().setEnabled(false);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
			} else {
				FreightRouteEntity freightRoute = dto.getFreightRoute();
				log.info("查询走货路径成功。。。");
				bean.setLoadLineName(dto.getRouteLineName());// 配载线路名称
				log.info("配载线路名称:" + dto.getRouteLineName());
				if (freightRoute != null) {
					bean.setLoadLineCode(freightRoute.getVirtualCode() == null ? "NOTEXISTS"
							: freightRoute.getVirtualCode());// 配载线路编码
					log.info("配载线路编码:" + freightRoute.getVirtualCode());
					bean.setPackageOrgCode(freightRoute
							.getPackingOrganizationCode());// 代打木架部门编码
					log.info("代打木架部门编码:"
							+ freightRoute.getPackingOrganizationCode());
					bean.setPackingOrganizationName(freightRoute
							.getPackingOrganizationName());// 代打木架部门名称
					log.info("代打木架部门名称:"
							+ freightRoute.getPackingOrganizationName());
					bean.setDoPacking(freightRoute.getDoPacking());// 是否可以打木架
					log.info("是否可以打木架:" + freightRoute.getDoPacking());
				} else {
					bean.setLoadLineCode("NOTEXISTS");// 配载线路编码
					log.info("配载线路编码:获取到的走货路径实体为空");
					bean.setPackageOrgCode("");// 代打木架部门编码
					log.info("代打木架部门编码:");
					bean.setPackingOrganizationName("");// 代打木架部门名称
					log.info("代打木架部门名称:");
					bean.setDoPacking("");// 是否可以打木架
					log.info("是否可以打木架:");
				}
				bean.setLoadOrgCode(dto.getFirstLoadOrgCode());// 配载部门编号
				log.info("配载部门编号:" + dto.getFirstLoadOrgCode());
				bean.setLoadOrgName(dto.getFirstLoadOrgName());// 配载部门名称
				log.info("配载部门名称:" + dto.getFirstLoadOrgName());
				bean.setLastLoadOrgCode(dto.getLastLoadOrgCode());// 最终配载部门编号
				log.info("最终配载部门编号:" + dto.getLastLoadOrgCode());
				bean.setLastLoadOrgName(dto.getLastLoadOrgName());// 最终配载部门名称
				log.info("最终配载部门名称:" + dto.getLastLoadOrgName());

				bean.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());// 最终配置外场
				log.info("最终配置外场:" + dto.getLastOutLoadOrgCode());
//				bean.setGoodsTypeIsAB(dto.getGoodsTypeIsAB());// 是否AB货
//				log.info("是否可区分AB货:" + dto.getGoodsTypeIsAB());
				// 设置AB货编辑状态

				// 如果路径可以打打木架则设置打木架按钮可点击
				if (FossConstants.YES.equals(bean.getDoPacking())) {
					ui.cargoInfoPanel.getBtnWood().setEnabled(false);// 小件不能打木架
				} else {
					ui.cargoInfoPanel.getBtnWood().setEnabled(false);
				}
				bean.setPickupCentralizedDeptCode(dto
						.getPickupCentralizedDeptCode());
				log.info("集中接货开单组所在外场的驻地营业部编码:"
						+ dto.getPickupCentralizedDeptCode());
			}
		} catch (BaseInfoInvokeException e) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
			 * 
			 * e.getMessage())
			 */
			);
		} catch (BusinessException w) {
			bean.setLoadLineName("");// 配载线路名称
			bean.setLoadLineCode("");// 配载线路编码
			bean.setLoadOrgCode("");// 配载部门编号
			bean.setLoadOrgName("");// 配载部门名称
			bean.setLastLoadOrgCode("");// 最终配载部门编号
			bean.setLastLoadOrgName("");// 最终配载部门名称
			bean.setPackageOrgCode("");// 代打木架部门编码
			bean.setPackingOrganizationName("");// 代打木架部门名称
			bean.setDoPacking("");// 是否可以打木架
			bean.setLastOutLoadOrgCode("");// 最终配置外场

			ui.cargoInfoPanel.getBtnWood().setEnabled(false);// 代打木架按钮不可点击
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
			 * 
			 * e.getMessage())
			 */
			);

			// throw w;
		}
		if(CommonContents.logToggle){
			log.info("加载配载部门和线路完成. 运单号："+bean.getWaybillNo());
		}
	}

	/**
	 * @param bean
	 */
	private void returnTypeListener(WaybillPanelVo bean2) {
		ExpWaybillPanelVo bean = (ExpWaybillPanelVo) bean2;

		if (bean.getReturnType() == null
				|| StringUtils.isEmpty(bean.getReturnType().getValueCode())) {
			bean.setOriginalWaybillNo(null);
			ui.basicPanel.getTxtOriginalWaybillNo().setText(null);
			ui.basicPanel.getTxtOriginalWaybillNo().setBackground(null);
			ui.basicPanel.getTxtOriginalWaybillNo().getBalloonTip()
					.setVisible(false);
			ui.basicPanel.getTxtOriginalWaybillNo().setEnabled(false);

			setCargoUiByReturnType(true);
			setWaybillUiByReturnType(true);
			 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
		} else {

			if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(
					WaybillConstants.LOGIN_TYPE).toString())) {
				ui.basicPanel.getTxtOriginalWaybillNo().setEnabled(true);

				if (!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
						.equals(bean.getReturnType().getValueCode())) {
					setCargoUiByReturnType(false);
					 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
				} else {
					setCargoUiByReturnType(true);
				}

				if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
						.equals(bean.getReturnType().getValueCode())) {
					setWaybillUiByReturnType(false);
					 ui.getBillingPayPanel().getBtnSubmit().setEnabled(false);
					JXTable table = ui.incrementPanel.getTblOther();
					WaybillOtherCharge model = (WaybillOtherCharge) table
							.getModel();
					List<OtherChargeVo> data = model.getData();
					data = new ArrayList<OtherChargeVo>();
					model.setData(data);
				} else {
					setWaybillUiByReturnType(true);

					List<ValueAddDto> list = waybillService
							.queryValueAddPriceList(getQueryParam());

					if (list != null && list.size() > 0) {

						List<ValueAddDto> list2 = new ArrayList<ValueAddDto>();

						for (int i = 0; i < list.size(); i++) {
							ValueAddDto dto = list.get(i);
							if (
							// "SHJCF".equals(dto.getSubType())
							// ||"QT".equals(dto.getSubType())
							// ||"GGF".equals(dto.getSubType())
							ExpWaybillConstants.OTHERFEE_SHJCF.equals(dto
									.getSubType())
									|| WaybillConstants.OTHERFEE_RYFJ
											.equals(dto.getSubType())
									|| WaybillConstants.OTHERFEE_ZHXX
											.equals(dto.getSubType())) {
								continue;
							} else {
								list2.add(dto);
							}
						}

						List<OtherChargeVo> voList = getOtherChargeList(list2);
						if (voList != null) {
							setChangeDetail(voList);
						}
					}

				}
			} else {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.OriginalWaybillNo"));
				ui.basicPanel.getTxtOriginalWaybillNo().setText(null);
				ui.basicPanel.getTxtOriginalWaybillNo().setBackground(null);
				ui.basicPanel.getTxtOriginalWaybillNo().getBalloonTip()
						.setVisible(false);
				ui.basicPanel.getTxtOriginalWaybillNo().setEnabled(false);

				setCargoUiByReturnType(true);
			}

		}
		//校验开返货或者是返单时 有无勾选上门发货
		if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(bean.getReturnType().getValueCode())||
				WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(bean.getReturnType().getValueCode())){
			bean.setHomeDelivery(false);
			homehomeDeliveryListener(bean);
			ui.basicPanel.getHomeDelivery().setEnabled(false);
		}
		//开单界面不可以手动输入原单号
		if(!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(bean.getReturnType().getValueCode())){
			ui.basicPanel.getTxtOriginalWaybillNo().setEnabled(false);
		}		
	}

	public void setChangeDetail(List<OtherChargeVo> changeDetailList2) {
		if (changeDetailList2 == null) {
	    	changeDetailList2 = new ArrayList<OtherChargeVo>();
	    }
		WaybillOtherCharge changeInfoDetailTableModel = ((WaybillOtherCharge) ui.incrementPanel
				.getTblOther().getModel());

		List<OtherChargeVo> changeDetailList = new ArrayList<OtherChargeVo>();

		// TODO 这里是其他费用的添加入口
		for (int i = 0; i < changeDetailList2.size(); i++) {
			OtherChargeVo v = changeDetailList2.get(i);
			if (ExpWaybillConstants.OTHERFEE_SHJCF.equals(v.getCode())
					|| WaybillConstants.OTHERFEE_RYFJ.equals(v.getCode())
					|| WaybillConstants.OTHERFEE_ZHXX.equals(v.getCode())) {
				continue;
			} else {
				changeDetailList.add(v);
			}

		}
	}

	private QueryBillCacilateValueAddDto getQueryParam() {
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(dept.getCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(null);
		// 产品CODE
		queryDto.setProductCode(null);
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setReceiveDate(null);
		// 重量
		queryDto.setWeight(BigDecimal.ZERO);
		// 体积
		queryDto.setVolume(BigDecimal.ZERO);
		// 原始费用
		queryDto.setOriginnalCost(BigDecimal.ZERO);
		// 航班号
		queryDto.setFlightShift(null);
		// 长途 还是短途
		queryDto.setLongOrShort(null);
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(null);
		// 币种
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
		// 计价条目CODE
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);
		// 计价名称
		queryDto.setPricingEntryName(null);
		return queryDto;
	}

	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	private List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if (list != null) {
			for (ValueAddDto dto : list) {
				OtherChargeVo vo = new OtherChargeVo();
				if (dto.getCandelete() != null
						&& !BooleanConvertYesOrNo.stringToBoolean(dto
								.getCandelete())) {
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
					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto
							.getCanmodify()));
					// 是否可删除
					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto
							.getCandelete()));
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
		}
		return voList;
	}

	/**
	 * 
	 */
	private void setWaybillUiByReturnType(boolean editable) {
		ui.incrementPanel.getTxtInsuranceValue().setEditable(editable);
		ui.incrementPanel.getTxtCashOnDelivery().setEditable(editable);
		ui.incrementPanel.getCombRefundType().setEnabled(editable);
		ui.incrementPanel.getCombReturnBillType().setEnabled(editable);
		ui.incrementPanel.getTxtPromotionNumber().setEditable(editable);
		ui.incrementPanel.getTxtInsurCharge().setEditable(editable);

		ui.incrementPanel.getTxtInsuranceValue().setEnabled(editable);
		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(editable);
		ui.incrementPanel.getTxtPromotionNumber().setEnabled(editable);
		ui.incrementPanel.getTxtInsurCharge().setEnabled(editable);

		ui.incrementPanel.getBtnNew().setEnabled(editable);
		ui.incrementPanel.getBtnDelete().setEnabled(editable);

		ui.billingPayPanel.getTxtPackCharge().setEnabled(editable);
	}

	public void setCargoUiByReturnType(boolean editable) {
		//ui.cargoInfoPanel.getTxtTotalPiece().setEditable(editable);//350909  郭倩云  返单总件数不能编辑
		ui.cargoInfoPanel.getTxtVolume().setEditable(editable);

		ui.cargoInfoPanel.getTxtWeight().setEditable(editable);
		ui.cargoInfoPanel.getTxtSize().setEditable(editable);
		ui.cargoInfoPanel.getTxtWood().setEditable(editable);
		ui.cargoInfoPanel.getTxtFibre().setEditable(editable);
		ui.cargoInfoPanel.getTxtMembrane().setEditable(editable);
		ui.cargoInfoPanel.getTxtOther().setEditable(editable);
		ui.cargoInfoPanel.getTxtPaper().setEditable(editable);
		ui.cargoInfoPanel.getTxtSalver().setEditable(editable);

		//ui.cargoInfoPanel.getTxtTotalPiece().setEnabled(editable);//350909  郭倩云  返单总件数不能编辑
		ui.cargoInfoPanel.getTxtVolume().setEnabled(editable);
		ui.cargoInfoPanel.getTxtWeight().setEnabled(editable);
		ui.cargoInfoPanel.getTxtSize().setEnabled(editable);
		// Color c = ui.cargoInfoPanel.getTxtWood().getBackground();
		// ui.cargoInfoPanel.getTxtTotalPiece().setBackground(c);
		// ui.cargoInfoPanel.getTxtVolume().setBackground(c);
		// ui.cargoInfoPanel.getTxtWeight().setBackground(c);
		// ui.cargoInfoPanel.getTxtSize().setBackground(c);

	}

	/**
	 * 
	 * 公里数事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-4-24
	 */
	private void kilometerListener() {
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 设置收货部门
	 * 
	 * @param bean
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-15 下午7:25:09
	 */
	private void receiveOrgName(ExpWaybillPanelVo bean) {
		if (StringUtil.isNotEmpty(bean.getReceiveOrgName())) {

			// 获取当前用户
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			// 当前用户所在部门
			OrgAdministrativeInfoEntity dept = user.getEmployee()
					.getDepartment();
			List<SaleDepartmentEntity> saleDepartmentEntitys = waybillService
					.querySaleDepartmentByNameForCentralized(
							bean.getReceiveOrgName(), dept.getCode());

			if (saleDepartmentEntitys != null
					&& saleDepartmentEntitys.size() > 0) {

				if (saleDepartmentEntitys.size() == 1) {
					// 设置收获部门信息
					ExpCommon.setSalesDepartmentForCentrial(
							saleDepartmentEntitys.get(0), bean, ui);
				} else {
					showDept(bean.getReceiveOrgName(), saleDepartmentEntitys,
							bean);
				}
			} else {
				showDept(bean.getReceiveOrgName(), saleDepartmentEntitys, bean);
			}
		}

		// 清空目的站以及预配线路
		cleanTargetEmpty(bean);
	}

	private void showDept(String orgName,
			List<SaleDepartmentEntity> saleDepartmentEntitys,
			ExpWaybillPanelVo bean) {
		try {
			// 弹出查询收货部门对话框
			SalesDepartmentDialog dialog = new SalesDepartmentDialog(
					bean.getReceiveOrgName(), saleDepartmentEntitys);
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			// 获得收获部门信息
			SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();
			// 设置收获部门信息
			ExpCommon.setSalesDepartmentForCentrial(entity, bean, ui);
			/**
			 * 当导入的PDA运单没有收货部门时，需要手工设置收货部门 当手动设置收货部门时，系统初始化产品类型，
			 * 设置产品类型时，同时需要自动设置提货方式
			 */
			// 根据运输性质的改变，改变提货方式
			ExpCommon.changePickUpMode(bean, ui);

		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}

	/**
	 * 开单报价
	 * 
	 * @param bean
	 */
	private void transportFeeListener(WaybillPanelVo bean,
			ExpWaybillPanelVo vbean) {
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
		/*
		 * String text =
		 * ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge
		 * ().getText(); int parseInt = Integer.parseInt(text);zoushengli
		 */
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
		int intValue2 = vbean.getTransportFee().intValue();
			if (intValue2 != 0) {
				int transportFee = vbean.getTempTransportFee().intValue();
				int maxintValue = (int) (transportFee * maxtemp + 0.5);
				int intValue = (int) (transportFee * temp + 0.5);
				if (intValue2 >= intValue && intValue2 <= maxintValue) {
					Common.cpv(bean);
					ExpCommon.getYokeCharge(bean, ui);
					CalculateFeeTotalUtils.calculateFee(bean);
					ui.billingPayPanel.getBtnSubmit().setEnabled(true);
					ui.buttonPanel.getBtnSubmit().setEnabled(true);
				} else {
					MsgBox.showInfo(i18n
							.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp",intValue,maxintValue));
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);
					ui.buttonPanel.getBtnSubmit().setEnabled(false);
				}
			}else{
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);
				ui.buttonPanel.getBtnSubmit().setEnabled(false);
				throw new WaybillValidateException(i18n
						.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp.ISNOTZERO"));
			}
		}else{
			int intValue2 = vbean.getTransportFee().intValue();
			if (intValue2 != 0) {
				int transportFee = vbean.getTempTransportFee().intValue();
				int intValue = (int) (transportFee * 0.6 + 0.5);
				if (intValue2 >= intValue && intValue2 <= transportFee) {
					Common.cpv(bean);
					ExpCommon.getYokeCharge(bean, ui);
					CalculateFeeTotalUtils.calculateFee(bean);
					ui.billingPayPanel.getBtnSubmit().setEnabled(true);
					ui.buttonPanel.getBtnSubmit().setEnabled(true);
				} else {
					MsgBox.showInfo(i18n
							.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp",intValue,transportFee));
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);
					ui.buttonPanel.getBtnSubmit().setEnabled(false);
				}
			}else{
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);
				ui.buttonPanel.getBtnSubmit().setEnabled(false);
				throw new WaybillValidateException(i18n
						.get("foss.gui.creatingexp.listener.Waybill.transportFeeMsgBoxExp.ISNOTZERO"));
			}
		}

		if (!bean.getIsWholeVehicle()) {
			return;
		}

		if (bean.getTransportFee() == null) {
			return;
		}
		BigDecimal rateUp = bean.getWholeVehicleActualfeeFlowRangeUp();

		BigDecimal rateLow = bean.getWholeVehicleActualfeeFlowRangeLow();
		if (rateLow == null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.listener.Waybill.transportFee.one"));
			rateLow = BigDecimal.valueOf(0.1);
		}
		BigDecimal upLevel = null;
		if (rateUp != null) {
			BigDecimal uprate = BigDecimal.ONE.add(rateUp);
			upLevel = bean.getWholeVehicleAppfee().multiply(uprate);
		}
		BigDecimal lowrate = BigDecimal.ONE.add(rateLow);
		BigDecimal lowLevel = bean.getWholeVehicleAppfee().multiply(lowrate);

		if (upLevel != null && upLevel.doubleValue() > 0) {
			if (bean.getTransportFee().doubleValue() < lowLevel.doubleValue()
					|| bean.getTransportFee().doubleValue() > upLevel
							.doubleValue()) {
    			bean.setTransportFee(bean.getWholeVehicleAppfee());
				bean.setTransportFeeCanvas(bean.getWholeVehicleAppfee()
						.toString());
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.transportFee.two")
						+ lowLevel.doubleValue() + "~" + upLevel.doubleValue());
    		} else {
    			bean.setWholeVehicleActualfee(bean.getTransportFee());
    		}
		} else {
			if (bean.getTransportFee().doubleValue() < lowLevel.doubleValue()) {
    			bean.setTransportFee(bean.getWholeVehicleAppfee());
				bean.setTransportFeeCanvas(bean.getWholeVehicleAppfee()
						.toString());
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.transportFee.three")
						+ lowLevel.doubleValue());
    		} else {
    			bean.setWholeVehicleActualfee(bean.getTransportFee());
    		}
		}
	}

	/**
	 * 
	 * 运单号事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 下午02:54:46
	 */
	private void waybillNoListener(WaybillPanelVo bean) {
		ui.numberPanel.getLblNumber().setText(bean.getWaybillNo());
		/**
		 * 如果不是集中开单验证连续开单的号是否相差太大(大于100)
		 */
		if (!bean.getPickupCentralized()) {
			boolean bool = waybillService.checkWaybillNoInterval(bean
					.getWaybillNo())
					&& waybillService.checkEWaybillNoExceptPDAPending(bean
							.getWaybillNo());
			if (bool) {
				log.info(i18n
						.get("foss.gui.creating.waybillDescriptor.waybillNo.speace"));
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.waybillDescriptor.waybillNo.speace"));
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
	private void gooodsNameListener(WaybillPanelVo bean) {
		// 是否限保物品
		isInsurGoods(bean);
		// 是否贵重物品
		isValuablesGoods(bean);
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
		ui.buttonPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
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
		 * 判断是否限保物品 1. SUC-494-录入货物信息 规则： SR1： 3.
		 * 若货物为限保物品，则系统自动限定保价金额，且不可修改，并提示“货物为限保物品”；
		 */
		LimitedWarrantyItemsEntity entity = waybillService.isInsurGoods(bean
				.getGoodsName());
		if (entity != null) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.listener.Waybill.insurGoods.one"));
			bean.setVirtualCode(entity.getVirtualCode());
			bean.setLimitedAmount(entity.getLimitedAmount());// 限制报价金额
			ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);// 设置为不可编辑
			/**
			 * 如果限保物品保价金额为空时：设置为0，不可编辑
			 */
			if (entity.getLimitedAmount() != null) {
				bean.setInsuranceAmount(entity.getLimitedAmount());
				bean.setInsuranceAmountCanvas(entity.getLimitedAmount()
						.toString());

				if (bean.getGoodsWeightTotal() == null) {
					bean.setGoodsWeightTotal(BigDecimal.ONE);
				}
				insuranceAmountListener((ExpWaybillPanelVo) bean);
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
	private void isValuablesGoods(WaybillPanelVo bean) {
		isValueGoods(bean);
	}

	/**
	 * 
	 * <p>
	 * (是否上门接货事件监听)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 上午09:04:07
	 * @see
	 */
	private void pickupToDoorListener(WaybillPanelVo bean) {
		boolean bool = ui.basicPanel.getCboReceiveModel().isSelected();
		if (bool) {
			// 接货费输入框
			ui.billingPayPanel.getTxtPickUpCharge().setEditable(false);
			// 手写输入金额输入框
			// ui.billingPayPanel.getTxtHandWriteMoney().setEditable(true);
		} else {
			// 司机工号
			bean.setDriverCode("");
			// 接货费
			bean.setPickupFee(BigDecimal.ZERO);
			// 手写输入金额
			bean.setHandWriteMoney(BigDecimal.ZERO);
			// 画布-接货费
			bean.setPickUpFeeCanvas(bean.getPickupFee().toString());
			// 接货费输入框
			ui.billingPayPanel.getTxtPickUpCharge().setEditable(false);
			// 手写输入金额输入框
			// ui.billingPayPanel.getTxtHandWriteMoney().setEditable(false);

			// 重新计算运费
			CalculateFeeTotalUtils.resetCalculateFee(bean);
		}
		// 修改完是否上门接货不能立即提交需要再次计算运费
		ui.buttonPanel.getBtnSubmit().setEnabled(false);
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
	}

	/**
	 * 根据查询条件查询客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-22 下午7:25:50
	 */
	public List<CustomerQueryConditionDto> queryDeliveryCustomer(
			WaybillPanelVo bean) {
		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 手机
		String mobilePhone = StringUtil.defaultIfNull(bean
				.getDeliveryCustomerMobilephone());
		// 电话
		String telePhone = StringUtil.defaultIfNull(bean
				.getDeliveryCustomerPhone());
		// 客户名称
		String custName = StringUtil.defaultIfNull(bean
				.getDeliveryCustomerName());

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
			dtoList.add(dto);
		}
		// 判断手机是否为空
		else if (StringUtils.isNotEmpty(mobilePhone)) {
			mobiles = new ArrayList<String>();
			mobiles.add(mobilePhone);

			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setMobilePhone(mobilePhone);
			dto.setExactQuery(true);
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
					dtoList.add(dto);
				}
			}
			// 电话不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				for (String phone : phones) {
					dto = new CustomerQueryConditionDto();
					dto.setContactPhone(phone);
					dto.setExactQuery(true);
					dtoList.add(dto);
				}
			}
		}

		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = waybillService
				.queryCustomerByConditionList(dtoList);

		// 若CRM中查询无果，则从历史客户中查询
		if (CollectionUtils.isEmpty(contactList)) {
			if (CollectionUtils.isNotEmpty(mobiles)) {
				// 将手机中的号码加入集合进行查询
				mobiles.add(mobilePhone);
				List<CustomerQueryConditionDto> custMobile = waybillService
						.queryHisDeliveryCusByMobileList(mobiles);
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
				List<CustomerQueryConditionDto> custPhone = waybillService
						.queryHisDeliveryCusByPhoneList(phones);
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
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午5:20:32
	 */
	private Map<String, List<String>> resolveMobileAndPhone(String phone) {
		// 存放解析后的手机和电话号码
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		// 分隔字符串
		List<String> strList = StrUtils.resolvePhone(phone);
		// 电话号码
		List<String> phoneList = new ArrayList<String>();
		// 手机号码
		List<String> mobileList = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(strList)) {
			// 将解析出的字符串进行分类
			for (String str : strList) {
				if (StrUtils.isMobileNO(str)) {
					mobileList.add(str);
				} else {
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
	public void fillDeliveryCustomerData(WaybillPanelVo bean, String mobile,
			String phone) {
		// 根据条件查询客户信息
		ProductEntityVo productVo = bean.getProductCode();
		List<CustomerQueryConditionDto> contactList = queryDeliveryCustomer(bean);
		if (CollectionUtils.isNotEmpty(contactList)) {
			// 创建弹出窗口
			ExpQueryConsignerDialog dialog = new ExpQueryConsignerDialog(
					contactList);
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
				ExpCommon.cleanDeliveryCustomerInfo(ui, bean, "", "");
                ui.consignerPanel.getTxtConsignerLinkMan().setEditable(true);
				return;
			}

			// 填充数据
			ExpCommon.fillDeliveryCustomerInfo(ui, memberVo, bean);
			if(WaybillConstants.YES.equals(memberVo.getIsElectricityToEnjoy())){
				ExpCommon.setProductCode(ui, bean,
						WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
			}
			//PDA 开单商务专递运输性质不可修改,2016/3/19,151211
			if(null != productVo && null != productVo.getCode()){
			if(WaybillConstants.DEAP.equals(productVo.getCode())){
				ExpCommon.setProductCode(ui,bean,
						WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
			}}
			/*else{
				   ExpCommon.setProductCode(ui, bean,
							WaybillConstants.ROUND_COUPON_PACKAGE);
			   }*/
			ExpCommon.setServiceChargeEnabled("", true, ui);
		} else {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean
					.getDeliveryCustomerId()))) {
				// 清空发货客户信息
				ExpCommon.cleanDeliveryCustomerInfo(ui, bean, mobile, phone);
				ExpCommon.setServiceChargeEnabled("", false, ui);
			}
			
			//PDA开单运输性质如果是商务专递不可修改
			//其他运输运输性质默认360快递,151211,2016/03/19
			if(null!=productVo && null!=productVo.getCode()){
				if(WaybillConstants.DEAP.equals(productVo.getCode())){
			
			}else{
				 ExpCommon.setProductCode(ui, bean,
							WaybillConstants.ROUND_COUPON_PACKAGE);
			}
				}
			
			ExpCommon.setServiceChargeEnabled("", false, ui);
			ui.consignerPanel.getTxtConsignerLinkMan().setEditable(true);
		}

		// 提交按钮置为不可用
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * s
	 * 
	 * 查询收货客户
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:08:02
	 */
	public List<CustomerQueryConditionDto> queryReceiveCustomer(
			WaybillPanelVo bean) {
		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 手机
		String mobilePhone = StringUtil.defaultIfNull(bean
				.getReceiveCustomerMobilephone());
		// 电话
		String telePhone = StringUtil.defaultIfNull(bean
				.getReceiveCustomerPhone());
		// 客户名称
		String custName = StringUtil.defaultIfNull(bean
				.getReceiveCustomerName());

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
			dtoList.add(dto);
		}
		// 判断手机是否为空
		else if (StringUtils.isNotEmpty(mobilePhone)) {
			mobiles = new ArrayList<String>();
			mobiles.add(mobilePhone);

			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setMobilePhone(mobilePhone);
			dto.setExactQuery(true);
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
					dtoList.add(dto);
				}
			}
			// 电话不为空时
			if (CollectionUtils.isNotEmpty(phones)) {
				for (String phone : phones) {
					dto = new CustomerQueryConditionDto();
					dto.setContactPhone(phone);
					dto.setExactQuery(true);
					dtoList.add(dto);
				}
			}
		}

		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = waybillService
				.queryCustomerByConditionList(dtoList);

		// 若CRM中查询无果，则从历史客户中查询
		if (CollectionUtils.isEmpty(contactList)) {
			if (CollectionUtils.isNotEmpty(mobiles)) {
				// 将手机中的号码加入集合进行查询
				mobiles.add(mobilePhone);
				List<CustomerQueryConditionDto> custMobile = waybillService
						.queryHisReceiveCusByMobileList(mobiles);
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
				List<CustomerQueryConditionDto> custPhone = waybillService
						.queryHisReceiveCusByPhoneList(phones);
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
	 * 填充收货客户数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午3:16:36
	 */
	public void fillReceiveCustomerData(WaybillPanelVo bean, String mobile,
			String phone) {
		// 根据条件查询客户信息
		List<CustomerQueryConditionDto> contactList = queryReceiveCustomer(bean);
		if (CollectionUtils.isEmpty(contactList)) {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean
					.getReceiveCustomerId()))) {
				// 清空收货客户信息
				ExpCommon.cleanReceiveCustomerInfo(ui, bean, mobile, phone);
			}
		} else {
			// 定义VO对象
			QueryMemberDialogVo memberVo = null;

			// 若只能一条记录时，则自动填充
			if (contactList.size() == 1) {
				memberVo = CommonUtils.convertToMemberVo(contactList, WaybillConstants.YES).get(0);
			} else {
				// 创建弹出窗口
				QueryConsigneeDialog dialog = new QueryConsigneeDialog(
						contactList);
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
				ExpCommon.cleanReceiveCustomerInfo(ui, bean, "", "");
				
				if(memberVo != null && WaybillConstants.YES.equals(memberVo.getIsElectricityToEnjoy())){
					ExpCommon.setProductCode(ui, bean,
							WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
				}
				/*else{
					   ExpCommon.setProductCode(ui, bean,
								WaybillConstants.ROUND_COUPON_PACKAGE);
				   }*/
				return;
			}

			// 填充数据
			ExpCommon.fillReceiveCustomerInfo(ui, memberVo, bean);
		}
	}

	/**
	 * 
	 * （发货人手机号码监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 上午10:52:04
	 */
	public void deliveryCustomerMobilephoneListener(ExpWaybillPanelVo bean) {
		// 判断操作
		if (null == bean.getReceiveMethod()) {
			// 增加日志
			log.error("开单提货方式不能为空！");
			// 抛出异常信息
			throw new WaybillSubmitException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.ReceiveMethodNotNull"));
		}

		// 是否内部带货
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			return;
		}

		// 手机号码
		String mobile = StringUtil.defaultIfNull(bean
				.getDeliveryCustomerMobilephone());
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
		} else {
			// 手机号为空，则清空客户信息
			ExpCommon.noDeliveryCustomerInfo(ui, bean);
			bean.setDeliveryCustomerContact("");
			bean.setDeliveryCustomerContactId("");
			ui.consignerPanel.getTxtConsignerLinkMan().setEditable(true);
		}
		// gyk
		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);
		// 判断能否快递上门发货
		if (!bean.isHomeDelivery()) {
			if (ExpCommon.calculateHomeDelivery(bean)) {
				ui.basicPanel.getHomeDelivery().setEnabled(true);
			}
		}
		bean.setPackageFee(BigDecimal.ZERO);
	}
 
	/**
	 * 
	 * （收货人手机号码事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午04:55:34
	 */
	public void receiveCustomerMobilephoneListener(WaybillPanelVo bean) {
		// 判断对像是否为空
		if (null == bean.getReceiveMethod()) {
			// 增加日志
			log.error("开单提货方式不能为空！");
			// 抛出异常信息
			throw new WaybillSubmitException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.ReceiveMethodNotNull"));
		}
		// 判断是否为内部带货
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			return;
		}

		// 手机号码
		String mobile = StringUtil.defaultIfNull(bean
				.getReceiveCustomerMobilephone());
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
		} else {
			// 清空收货客户信息
			ExpCommon.noReceiveCustomerInfo(ui, bean);
			bean.setReceiveCustomerContact("");
			bean.setReceiveCustomerContactId("");
		}

		// 若为PDA补录，则更改收货客户信息后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ExpCommon.setForegroundColor(ui, bean);
		}

	}

	/**
	 * （发货客户电话号码监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午03:54:13
	 */
	public void deliveryCustomerPhoneListener(ExpWaybillPanelVo bean) {
		// 判断是否为内部带货自提
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			return;
		}

		// 发货客户手机是否为空：true为空
		boolean mobilePhone = StringUtil.isEmpty(StringUtil.defaultIfNull(bean
				.getDeliveryCustomerMobilephone()));
		// 发货客户电话号码
		String phone = StringUtil
				.defaultIfNull(bean.getDeliveryCustomerPhone());
		// 发货客户电话是否为空：true为空
		boolean telePhone = StringUtil.isEmpty(phone);
		// 发货客户名称
		boolean custName = StringUtil.isEmpty(StringUtil.defaultIfNull(bean
				.getDeliveryCustomerName()));
		// 手机、客户名称为空时
		if (mobilePhone && !telePhone && custName) {
			// 若未查询出数据，电话号码会被清空，此时需要将其重新设置回来
			fillDeliveryCustomerData(bean, "", phone);
		}
		// gyk
		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);
		// 判断能否快递上门发货
		if (!bean.isHomeDelivery()) {
			if (ExpCommon.calculateHomeDelivery(bean)) {
				ui.basicPanel.getHomeDelivery().setEnabled(true);
			}
		}
		bean.setPackageFee(BigDecimal.ZERO);

	}

	/**
	 * 发货客户焦点失去监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-6 下午2:34:31
	 */
	private void deliveryCustomerNameListener(ExpWaybillPanelVo bean) {
		// 客户名称
		String custName = StringUtil.defaultIfNull(bean
				.getDeliveryCustomerName());
		// 客户名称为空则清空客户编码，设置发货联系人为可修改状态
		if ("".equals(custName)) {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean
					.getDeliveryCustomerId()))) {
				// 清空发货客户信息
				ExpCommon.cleanDeliveryCustomerInfo(ui, bean, "", "");
			}
			// 客户编码
			bean.setDeliveryCustomerCode("");
			// 大客户标记
			bean.setDeliveryBigCustomer(FossConstants.INACTIVE);

		} else {
			// 封装查询条件
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			// 收货客户名称
			dto.setCustName(bean.getDeliveryCustomerName());
			// 精确查询
			dto.setExactQuery(true);
			//发票标记时间
			dto.setInvoiceDate(new Date());
			// 查询客户信息
			List<CustomerQueryConditionDto> contacts = waybillService
					.queryCustomerByCondition(dto);
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

				ExpCommon.fillDeliveryCustomerInfo(ui, memberVo, bean);
				
				if(WaybillConstants.YES.equals(memberVo.getIsElectricityToEnjoy())){
					ExpCommon.setProductCode(ui, bean,
							WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
				}
				/*else{
					   ExpCommon.setProductCode(ui, bean,
								WaybillConstants.ROUND_COUPON_PACKAGE);
				   }*/
			} else {
				// 清空当前客户名称信息
				bean.setDeliveryCustomerName("");
				// 发货客户编号
				/**
				 * Dmana-9885当订单来源为巨商汇的时候，不修改客户编码
				 * 
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-02-03下午13:43
				 */
				if (!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())) {
					bean.setDeliveryCustomerCode("");
				}
				// 大客户标记
				bean.setDeliveryBigCustomer(FossConstants.INACTIVE);
				// 是否月结
				/**
				 * Dmana-9885当订单来源为巨商汇的时候，不修改客户月结
				 * 
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-02-03下午13:43
				 */
				if (!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())) {
					bean.setChargeMode(false);
				}
				// 合同编号
				bean.setAuditNo("");
				// 优惠类型
				bean.setPreferentialType("");
				/**
				 * 将特安客户上限值清空
				 * 
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-11-19下午14:30
				 */
				bean.setVipInsuranceAmount(null);
				/**
				 * 将代收货款的上限值清空
				 * 
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-11-19下午14:31
				 */
				bean.setVipCollectionPaymentLimit(null);

			}
		}
		// gyk
		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);
		// 判断能否快递上门发货
		if (!bean.isHomeDelivery()) {
			if (ExpCommon.calculateHomeDelivery(bean)) {
				ui.basicPanel.getHomeDelivery().setEnabled(true);
			}
		}
		bean.setPackageFee(BigDecimal.ZERO);

		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 监听发货联系人事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-8 下午7:23:37
	 */
	private void deliveryCustomerContactListener(WaybillPanelVo bean) {
		// 是否月结
		/**
		 * Dmana-9885当订单来源为巨商汇的时候，不修改客户月结
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:43
		 */
		if (!WaybillConstants.ALIBABA.equals(bean.getOrderChannel())) {
			bean.setChargeMode(false);
		}
		// 优惠类型
		bean.setPreferentialType("");
		// gyk
		ui.billingPayPanel.getTxtPackCharge().setEnabled(true);
		bean.setPackageFee(BigDecimal.ZERO);
	}

	// 内部带货费用承担部门监听事件
	private void innerPickupFeeBurdenDeptListener(ExpWaybillPanelVo bean) {

	}

	/**
	 * 
	 * （收货客户电话号码监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午03:54:13
	 */
	public void receiveCustomerPhoneListener(WaybillPanelVo bean) {
		if (WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
			return;
		}

		// 判断客户手机或电话是否为空或空字符串
		// 手机是否为空：true为空
		boolean mobilePhone = StringUtil.isEmpty(StringUtil.defaultIfNull(bean
				.getReceiveCustomerMobilephone()));
		// 电话号码
		String phone = StringUtil.defaultIfNull(bean.getReceiveCustomerPhone());
		// 电话号码是否为空：true为空
		boolean telePhone = StringUtil.isEmpty(phone);
		// 客户名称是否为空：true为空
		boolean custName = StringUtil.isEmpty(StringUtil.defaultIfNull(bean
				.getReceiveCustomerName()));
		// 手机为空，电话非空，客户为空
		if (mobilePhone && !telePhone && custName) {
			// 若未查询出数据，电话号码会被清空，此时需要将其重新设置回来
			fillReceiveCustomerData(bean, "", phone);
		}

		// 若为PDA补录，则更改收货客户信息后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ExpCommon.setForegroundColor(ui, bean);
		}

	}

	/**
	 * 
	 * （运输性质事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-22 下午04:48:08
	 */
	private void productCodeListener(ExpWaybillPanelVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		if (productVo != null && productVo.getCode() != null) {
			// 根据运输性质改变提货方式
			ExpCommon.changePickUpMode(bean, ui);
			// 空运、偏线以及中转下线无法选择签收单返单
			ExpCommon.setReturnBill(bean, ui);
			// 偏线与空运不能选择预付费保密
			ExpCommon.setSecretPrepaid(bean, ui);
			// 偏线不能开代收货款
			// Common.setPartialCod(bean, ui);

			// 重新计算打木架费用
			ExpCommon.getYokeCharge(bean, ui);

			// 清空其他费用列表
			ExpCommon.cleanOtherCharge(bean, ui);

			// DMANA-7422补录单不能修改提货网点
			SaleDepartmentEntity sales = waybillService.querySaleDeptByCode(
					bean.getCreateOrgCode(), new Date());
			if (FossConstants.YES.equals(sales.getCanUpdateDestination())) {

			} else {
				// 清空目的站以及预配线路
				cleanTargetEmpty(bean);
			}

		}

		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 清空目的站以及预配线路
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-4 下午06:53:27
	 * @param bean
	 */
	private void cleanTargetEmpty(WaybillPanelVo bean) {
		// 清空提货网点
		bean.setCustomerPickupOrgCode(null);
		// 清空提货网点名称
		bean.setCustomerPickupOrgName("");
		// 清空目的站
		bean.setTargetOrgCode("");
		// 清空长短途
		bean.setLongOrShort(null);
		// 清空预配线路
		bean.setLoadLineName("");
	}

	/**
	 * 
	 * 设置付费方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private void setPaidMethod(WaybillPanelVo bean) {
		int size = ui.getCombPaymentModeModel().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui
					.getCombPaymentModeModel().getElementAt(i);
			if (vo == null || vo.getValueCode() == null) {
				bean.setPaidMethod(vo);
			}
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
	 * 
	 * （提货方式变更监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午01:56:55
	 */
	public void receiveMethodListener(WaybillPanelVo bean) {
		if (bean.getReceiveMethod() != null) {
			// 只允许合同客户才可以选择免费送货
			ExpCommon.validateDeliverFree(bean, ui);

			// 内部带货
			ExpCommon.innerPickup((ExpWaybillPanelVo) bean, ui);
			// 各种自提
			ExpCommon.selfPickup(bean, ui);
			// 设置其他费用
			calculateOtherCharge(ui, bean);

			// DMANA-7422补录单不能修改提货网点
			SaleDepartmentEntity sales = waybillService.querySaleDeptByCode(
					bean.getCreateOrgCode(), new Date());
			if (FossConstants.YES.equals(sales.getCanUpdateDestination())) {
			} else {
					// 清空目的站以及预配线路
					cleanTargetEmpty(bean);
					// 在已经选择了网点的情况下 修改提货方式 需要检查该网点是否支持该提货方式
				ExpCommon.validateCustomerPointBySelfPickup(bean, ui);
				}
			// 清空目的站TODO
			// ExpCommon.setTargetEmpty(bean);
			// 送货进仓
			// Common.deliverStorge(bean, ui, waybillService);

			ExpCommon.setSaveAndSubmitFalse(ui);

			/**
			 * 如果非送货时，公里数不可录入，且要清空
			 */
			if (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod()
					.getValueCode())
					|| WaybillConstants.INNER_PICKUP.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_SELF_PICKUP.equals(bean
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIRPORT_PICKUP.equals(bean
							.getReceiveMethod().getValueCode())) {
				bean.setKilometer(null);
				// 公里数不可编辑
				ui.transferInfoPanel.getTxtKilometer().setEditable(false);
			} else {
				// 公里数可编辑
				ui.transferInfoPanel.getTxtKilometer().setEditable(true);
		}

	}
	}

	/**
	 * 
	 * 其他费用合计
	 * 
	 * @author WangQianJin
	 * @date 2013-05-06
	 */
	private void calculateOtherCharge(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		// 判断是否内部带货自提
		if (!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode())) {
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
	}

	/**
	 * 收货客户名称焦点监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午8:31:07
	 */
	private void receiveCustomerNameListener(WaybillPanelVo bean) {
		// 客户名称
		String custName = StringUtil.defaultIfNull(bean
				.getReceiveCustomerName());
		// 客户名称为空则清空客户编码，设置发货联系人为可修改状态
		if ("".equals(custName)) {
			// 若为删除原客户信息，则清空全部
			if (!"".equals(StringUtils.defaultString(bean
					.getReceiveCustomerId()))) {
				// 删除收货客户信息
				ExpCommon.cleanReceiveCustomerInfo(ui, bean, "", "");
			}

			// 客户编码
			bean.setReceiveCustomerCode("");
			// 大客户标记
			bean.setReceiveBigCustomer(FossConstants.INACTIVE);
		} else {
			// 封装查询条件
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			// 收货客户名称
			dto.setCustName(bean.getReceiveCustomerName());
			// 精确查询
			dto.setExactQuery(true);
			// 查询客户信息
			List<CustomerQueryConditionDto> contacts = waybillService
					.queryCustomerByCondition(dto);
			// 判断集合是否为空
			if (CollectionUtils.isNotEmpty(contacts)) {
				// 定义VO对象
				QueryMemberDialogVo memberVo = null;
				// 若只能一条记录时，则自动填充
				if (contacts.size() == 1) {
					memberVo = CommonUtils.convertToMemberVo(contacts).get(0);
				} else {
					// 创建弹出窗口
					QueryConsignerDialog dialog = new QueryConsignerDialog(
							contacts);
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
				ExpCommon.setQueryReceiveCustomer(ui);
			} else {
				// 清空当前客户名称信息
				bean.setReceiveCustomerName("");
				// 收货客户ID
				bean.setReceiveCustomerId("");
				// 收货客户编号
				bean.setReceiveCustomerCode("");
				// 大客户标记
				bean.setReceiveBigCustomer(FossConstants.INACTIVE);
			}
		}

		// 若为PDA补录，则更改收货客户信息后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ExpCommon.setForegroundColor(ui, bean);
		}
	}

	/**
	 * 
	 * （重量联动监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 下午05:09:48
	 */
	private void goodsWeightTotalListener(ExpWaybillPanelVo bean) {
		// vehicleDirect(bean);
		bean.setServiceFee(BigDecimal.ZERO);// 清空装卸费

		BigDecimal rate = bean.getVolumeRate();
		/**
		 * if(bean.getGoodsWeightTotal()!=null){ if(rate!=null){ BigDecimal
		 * calculateVolume = bean.getGoodsWeightTotal().multiply(rate);
		 * //calculateVolume= BigDecimal.ZERO;
		 * 
		 * bean.setCalculateVolumeTotal(calculateVolume);
		 * 
		 * if(
		 * StringUtils.isEmpty(ui.getCargoInfoPanel().getTxtVolume().getText())
		 * && calculateVolume!=null){ //
		 * if(bean.getGoodsVolumeTotal().doubleValue() //
		 * <calculateVolume.doubleValue()){ //
		 * bean.setGoodsVolumeTotal(calculateVolume); // } }else
		 * if(bean.getGoodsVolumeTotal()==null){
		 * bean.setGoodsVolumeTotal(calculateVolume); }
		 * 
		 * } }
		 */

		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * （体积联动监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 下午05:09:44
	 */
	private void goodsVolumeTotalListener(WaybillPanelVo bean) {
		vehicleDirect(bean);// 判断是否需要大车直送
		isValueGoods(bean);// 判断是否贵重物品
		bean.setServiceFee(BigDecimal.ZERO);// 清空装卸费
		ExpWaybillPanelVo bean2 = (ExpWaybillPanelVo) bean;
		bean2.setGoodsVolumePreviousTotal(null);
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 校验重量体积是否为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-20 上午09:09:00
	 */
	private Boolean validateVolumeWeight(WaybillPanelVo bean) {
		if (bean.getGoodsQtyTotal() == null
				|| bean.getGoodsWeightTotal() == null
				|| bean.getGoodsVolumeTotal() == null
				|| bean.getInsuranceAmount() == null) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * （大车直送）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 下午06:17:25
	 */
	private void vehicleDirect(WaybillPanelVo bean) {
		// if (validateVolumeWeight(bean)) {
		// Boolean bool =
		// waybillService.isVehicleDirect(bean.getGoodsWeightTotal().toString(),
		// bean.getGoodsVolumeTotal().toString());
		// if (bool) {
		// if (!bean.getCarDirectDelivery()) {
		// if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(ui,
		// i18n.get("foss.gui.creating.listener.Waybill.vehicleDirect.one"),
		// i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
		// JOptionPane.YES_NO_OPTION)) {
		// // 大车直送
		// ui.cargoInfoPanel.getChbCarThrough().setSelected(false);
		// ui.cargoInfoPanel.getChbCarThrough().setEnabled(false);
		// } else {
		// // 大车直送
		// ui.cargoInfoPanel.getChbCarThrough().setSelected(false);
		// ui.cargoInfoPanel.getChbCarThrough().setEnabled(false);
		// }
		// }
		// } else {
		// // 大车直送
		// ui.cargoInfoPanel.getChbCarThrough().setSelected(false);
		// ui.cargoInfoPanel.getChbCarThrough().setEnabled(false);
		// }
		// }
		ui.cargoInfoPanel.getChbCarThrough().setSelected(false);
		ui.cargoInfoPanel.getChbCarThrough().setEnabled(false);
	}

	/**
	 * 
	 * （件数事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-25 上午10:14:42
	 */
	private void goodsQtyTotalListener(WaybillPanelVo bean) {
		if (bean.getGoodsQtyTotal() == null) {
			return;
		}

		isValueGoods(bean);// 是否贵重物品
		packPieces(bean);// 设置包装纸默认值

		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * （默认货物多少件就有多少纸包装）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 上午11:08:59
	 */
	private void packPieces(WaybillPanelVo bean) {
		bean.setPaper(bean.getGoodsQtyTotal());
		bean.setWood(Integer.valueOf(0));// 木
		bean.setFibre(Integer.valueOf(0));// 纤
		bean.setSalver(Integer.valueOf(0));// 托
		bean.setMembrane(Integer.valueOf(0));// 膜

		// 任何时候只要setWood 为0 都必须调用下面三个方法
		ExpCommon.unsetWaybillPanelVoForWoodenPack(bean, ui);
		ExpCommon.unsetStorageMatterForWoodenPack(bean);
		ExpCommon.unsetWoodenPackFee(bean);
	}

	/**
	 * 
	 * （保险声明价值监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-25 上午11:12:48
	 */
	private void insuranceAmountListener(ExpWaybillPanelVo bean) {
		// 获取优惠活动信息
		// if(null!=ui.getBasicPanel().getCboSpecialOffer()&&null!=ui.getBasicPanel().getCboSpecialOffer().getModel()){
		// bean.setSpecialOffer((DataDictionaryValueVo)
		// ui.getBasicPanel().getCboSpecialOffer().getModel().getSelectedItem());
		// }
		// isValueGoods(bean);// 是否贵重物品
		/**
		 * DEFECT-7431
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-20
		 */
		if (bean.getInsuranceAmount() != null) {
			BigDecimal upAmount;
			if (bean.getVipInsuranceAmount() != null) {
				upAmount = bean.getVipInsuranceAmount();
				if (upAmount != null
						&& upAmount.doubleValue() > 0
						&& upAmount.doubleValue() < bean.getInsuranceAmount()
								.doubleValue()) {
					BigDecimal tmp = bean.getInsuranceAmount();
					bean.setInsuranceAmount(BigDecimal.ZERO);
					bean.setInsuranceAmountCanvas(BigDecimal.ZERO.toString());
					WaybillValidateException e = new WaybillValidateException(
							i18n.get("foss.gui.creating.common.exception.outInsuranceUp")
									+ upAmount
									+ i18n.get("foss.gui.creating.printUtil.chinese.yuan"));

					throw e;
			}
			} else {

			/**
			 * add远郊保价上限
				 * 
			 * @author:270293-foss-zhangfeng
			 * @date:2015-07-06
			 */
				// 获取提货网点
				String costomerPickupOrgName = bean.getCustomerPickupOrgName();
				// 根据编码code综合取参数对象
				IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
						.getService(IWaybillHessianRemoting.class);
				// 获取快递外发保价申明价值上限
				ConfigurationParamsEntity configentity = waybillRemotingService
						.queryMaxInsuranceAmount(ExpWaybillConstants.EXPRESS_INSURRANCE_FEE_UP);
				if (costomerPickupOrgName != null
						&& (costomerPickupOrgName.indexOf("远郊") >= 0 || costomerPickupOrgName
								.indexOf("外发") >= 0)) {

					if (null != configentity
							&& bean.getInsuranceAmount().doubleValue() > Double
									.valueOf(configentity.getConfValue())) {
						// 保价声明价值设置为0
					bean.setInsuranceAmount(BigDecimal.ZERO);
						// 保价声明价值设置为0
						bean.setInsuranceAmountCanvas(BigDecimal.ZERO
								.toString());
						WaybillValidateException e = new WaybillValidateException(
								i18n.get("foss.gui.creating.common.exception.outInsuranceUp")
										+ configentity.getConfValue()
										+ i18n.get("foss.gui.creating.printUtil.chinese.yuan"));
					throw e;
				}

				} else if (null != costomerPickupOrgName) {
					upAmount = bean.getMaxInsuranceAmount();
					if (bean.getInsuranceAmount().doubleValue()  > Double.valueOf(upAmount
							.doubleValue())) {
				BigDecimal tmp = bean.getInsuranceAmount();
				bean.setInsuranceAmount(BigDecimal.ZERO);
						bean.setInsuranceAmountCanvas(BigDecimal.ZERO
								.toString());
						WaybillValidateException e = new WaybillValidateException(
								i18n.get("foss.gui.creating.common.exception.outInsuranceUp")
										+ upAmount
										+ i18n.get("foss.gui.creating.printUtil.chinese.yuan"));

				throw e;
			}
				} else if (null == costomerPickupOrgName) {
					// 保价声明价值设置为0
				bean.setInsuranceAmount(BigDecimal.ZERO);
					// 保价声明价值设置为0
				bean.setInsuranceAmountCanvas(BigDecimal.ZERO.toString());
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.nullTargetOrgCode"));
				}
			}

			if (bean.getInsuranceAmount().doubleValue() < 0) {
				bean.setInsuranceAmount(BigDecimal.ZERO);
				bean.setInsuranceAmountCanvas(BigDecimal.ZERO.toString());
				return;
			}

			bean.setInsuranceAmountCanvas(bean.getInsuranceAmount().toString());

			// 若为PDA补录，则更改保价后颜色变化以示提醒
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
					.getWaybillstatus())) {
				ui.incrementPanel.getTxtInsuranceValue().setForeground(
						Color.RED);
			}

			// 获取产品价格主参数

			try {
				GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(bean);

				List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection
						.getPriceEntities();

				// 获取保价费
				GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceCollection(bean);
				if (insuranceCollection != null) {
					priceEntities.add(insuranceCollection);// 加入增值服务
				}
				// 快递优惠活动
				if (bean.getSpecialOffer() != null
						&& StringUtil.isNotEmpty(bean.getSpecialOffer()
								.getValueCode())) {
					productPriceDtoCollection.setCityMarketCode(bean
							.getSpecialOffer().getValueCode());
				}
				List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = waybillService
						.queryGuiExpressBillPrice(productPriceDtoCollection);
				// 获取保价费
				GuiResultBillCalculateDto insuranceCollectionResult = getGuiResultBillCalculateDto(
						guiResultBillCalculateDtos,
						PriceEntityConstants.PRICING_CODE_BF, null);
				// 设置保价费
				setInsuranceCollection(bean, insuranceCollectionResult);

			} catch (Exception e) {
				// log.error(e.getMessage(), e);
			}

			ExpCommon.setSaveAndSubmitFalse(ui);
		}
	}

	private void setInsuranceCollection(ExpWaybillPanelVo bean,
			GuiResultBillCalculateDto dto) {
		if (dto != null) {
			setInsurance(bean, dto);// 设置保价费
			// 设置折扣优惠
			ExpCommon.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
		} else {
			// 清空保价费
			setInsurance(bean, null);
		}
	}

	/**
	 * 
	 * 设置保险费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 上午10:05:31
	 */
	private void setInsurance(ExpWaybillPanelVo bean,
			GuiResultBillCalculateDto dto) {
		if (dto == null || dto.getCaculateFee() == null
				|| dto.getCaculateFee().doubleValue() <= 0) {
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
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullInsuranceFee"));
		} else {
			// 保险声明值最大值
			// bean.setMaxInsuranceAmount(dto.getMaxFee());
			// 将保险费率转换成千分率的格式
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = Common.nullBigDecimalToZero(dto
					.getActualFeeRate());
			feeRate = feeRate.multiply(permillage);
			// 保险费率
			bean.setInsuranceRate(feeRate);
			BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			if("0".equals(bean.getInsuranceAmount())
					||
				(BigDecimal.ZERO.compareTo(bean.getInsuranceAmount())==0)
			  ){				// 保险手续费
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

	private GuiQueryBillCalculateSubDto getInsuranceCollection(
			WaybillPanelVo bean) {
		return getInsuranceParam(bean);

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
	 * 获得产品价格详细信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-5 下午04:27:16
	 */
	private GuiQueryBillCalculateDto getProductPriceDtoCollection(
			WaybillPanelVo bean) {
		// 上门接货优先读取上门接货的价格
		if (bean.getPickupToDoor() != null && bean.getPickupToDoor()) {
			return ExpCommon.getQueryParamCollection(bean, FossConstants.YES);

		} else {
			return ExpCommon.getQueryParamCollection(bean, FossConstants.NO);
		}

	}

	/**
	 * 
	 * （根据重量、件数、保价判断是否贵重物品）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-25 上午11:07:50
	 */
	private void isValueGoods(WaybillPanelVo bean) {
		/**
		 * 判断运输性质是否为整车，整车无贵重物品。
		 */
		if (bean != null
				&& bean.getProductCode() != null
				&& ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE
						.equals(bean.getProductCode().getCode())) {
			ui.cargoInfoPanel.getChbValuable().setSelected(false);
			ui.cargoInfoPanel.getChbValuable().setEnabled(false);
		} else {
			// if (validateVolumeWeight(bean)) {
			// Boolean bool = false;
			// bool = waybillService.isValueGoods(bean.getGoodsName(),
			// bean.getGoodsQtyTotal().intValue(),
			// bean.getGoodsVolumeTotal().toString(),
			// bean.getInsuranceAmount().toString());
			// if (bool) {
			// ui.cargoInfoPanel.getChbValuable().setSelected(true);
			// ui.cargoInfoPanel.getChbValuable().setEnabled(false);
			// } else {
			// ui.cargoInfoPanel.getChbValuable().setSelected(false);
			// ui.cargoInfoPanel.getChbValuable().setEnabled(true);
			// }
			// }
			ui.cargoInfoPanel.getChbValuable().setSelected(false);
			ui.cargoInfoPanel.getChbValuable().setEnabled(false);
		}
	}

	/**
	 * 
	 * 贵重物品事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-13 上午11:46:48
	 */
	private void preciousGoodsListener(WaybillPanelVo bean) {
		// Boolean bool = bean.getPreciousGoods();
		// if (bool) {
		// String remark = bean.getTransportationRemark();
		// bean.setTransportationRemark(WaybillConstants.VALUE_GOODS_NAME +
		// remark);
		// ui.cargoInfoPanel.getChbValuable().setSelected(true);
		// } else {
		// String remark = bean.getTransportationRemark();
		// remark = remark.replace(WaybillConstants.VALUE_GOODS_NAME, "");
		// bean.setTransportationRemark(remark);
		// ui.cargoInfoPanel.getChbValuable().setSelected(false);
		// }
	}

	/**
	 * 
	 * 大车直送事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午05:55:20
	 */
	private void carDirectDeliveryListener(WaybillPanelVo bean) {

		StringBuffer sb = new StringBuffer("");

		String woodTxt = "";
		String sandTxt = "";
		String innerWaybillText = "";

		String originalTxt = bean.getTransportationRemark();
		if (originalTxt == null) {
			originalTxt = "";
		}

		String[] remark = originalTxt.split(";");
		for (int i = 0; i < remark.length; i++) {
			String oldData = remark[i];
			if (oldData.indexOf(StringUtil.defaultIfNull(i18n
					.get("foss.gui.creating.woodYokeEnterAction.standGoods"))) != -1) {
				woodTxt = oldData;
			}

			if (oldData.indexOf(StringUtil.defaultIfNull(i18n
					.get("foss.gui.creating.woodYokeEnterAction.boxGoods"))) != -1) {
				sandTxt = oldData;
			}
			// 弃货导入内部开单
			if (oldData
					.contains(StringUtil.defaultIfNull(i18n
							.get("foss.gui.creating.woodYokeEnterAction.innerWaybillText")))) {
				innerWaybillText = oldData;
			}
		}

		if (StringUtils.isNotEmpty(woodTxt)) {
			sb.append(woodTxt).append("; ");
		}

		if (StringUtils.isNotEmpty(sandTxt)) {
			sb.append(sandTxt).append("; ");
		}
		// 加上弃货导入内部开单备注
		if (StringUtils.isNotEmpty(innerWaybillText)) {
			sb.append(innerWaybillText).append("; ");
		}

		/**
		 * 对外备注
		 */
		JComboCheckBox checkbox = (JComboCheckBox) ui.cargoInfoPanel
				.getCombOutSideRemark();
		for (String string : checkbox.getCheckedValues()) {
			sb.append(string).append("; ");
		}

		/**
		 * 对内备注
		 */
		String innerSiderRemark = ui.cargoInfoPanel.getTxtInsideRemark()
				.getText();
		if (innerSiderRemark != null && !"".equals(innerSiderRemark)) {
			sb.append(innerSiderRemark).append("; ");
		}

		/**
		 * 大车直送
		 */
		if (ui.cargoInfoPanel.getChbCarThrough().isSelected()) {
			sb.append(
					i18n.get("foss.gui.creating.waybillEditUI.carThrough.name"))
					.append("; ");
		}

		ui.cargoInfoPanel.getTxtTransportationRemark().setText(sb.toString());

	}

	/**
	 * 
	 * 对内备注事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午05:55:20
	 */
	private void innerNotesListener(WaybillPanelVo bean) {

		if (StringUtils.isNotEmpty(bean.getInnerNotesBack())) {
			// 清除上一次输入的对内备注
			ExpCommon.cleanRemark(bean, bean.getInnerNotesBack());
		}

		if (StringUtils.isNotEmpty(bean.getInnerNotes())) {
			String transportationRemark = bean.getTransportationRemark();
			if (transportationRemark == null) {
				transportationRemark = "";
			}
			transportationRemark = transportationRemark + bean.getInnerNotes()
					+ ";";
			bean.setTransportationRemark(transportationRemark);
			bean.setInnerNotesBack(bean.getInnerNotes());
		}

	}

	/**
	 * 
	 * （尺寸事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 上午08:53:50
	 */
	private void goodsSizeListener(ExpWaybillPanelVo bean) {

		if (bean.getGoodsSize() != null && !"".equals(bean.getGoodsSize())) {
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
						String[] eachLengAll  =  StringUtils.split(leng, "*");
						if (eachLengAll.length < NumberConstants.NUMBER_3) {
							continue;
						}
						double minleng = 0;
						double tmplength = Double.parseDouble(eachLengAll[0]);
						String minLen = "length";
						minleng = tmplength;
						double tmpwidth =  Double.parseDouble(eachLengAll[1]);
						if (tmpwidth < minleng) {
							minleng = tmpwidth;
							minLen = "width";
						}
						double tmpheight =   Double.parseDouble(eachLengAll[2]);
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
							width  = width + tmpwidth;
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
							width  = width + tmpwidth;
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

			} else {
				StringBuffer str = new StringBuffer(
						i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
				str.append("(\n")
						.append(i18n
								.get("foss.gui.creating.waybillDescriptor.example"));
				str.append("：0.5*0.5*0.5*2")
						.append(i18n
								.get("foss.gui.creating.waybillDescriptor.or"))
						.append("\n");
				str.append("0.5*0.5*0.5*2+1*1*1*5")
						.append(i18n
								.get("foss.gui.creating.waybillDescriptor.or"))
						.append("\n");
				str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
				bean.setGoodsVolumePreviousTotal(null);
				MsgBox.showInfo(str.toString());
			}
		} else {
			bean.setGoodsVolumePreviousTotal(null);
		}
	}

	/**
	 * 
	 * 根据传入的尺寸参数计算体积
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午03:53:39
	 */
	private void calculateVolume(WaybillPanelVo bean) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		try {
			Object result = engine.eval(bean.getGoodsSize());
			BigDecimal bigDecimal = new BigDecimal(result.toString());
			bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_3, BigDecimal.ROUND_HALF_UP);
			BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
			bigDecimal = bigDecimal.divide(m);
			bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
			// 四舍五入后如果变为0.00，那么需要给成默认的0.01，以免丢失体积
			if (bigDecimal.doubleValue() == 0) {
				bigDecimal = new BigDecimal("0.01");
			}
			BigDecimal upLimit = new BigDecimal(WaybillConstants.VOLUME_UPLIMIT);
			if (bigDecimal.compareTo(upLimit) > 0) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.calculateVolume.one")
						+ WaybillConstants.VOLUME_UPLIMIT);
				bean.setGoodsSize("");
			} else if (BigDecimal.ZERO.compareTo(bigDecimal) > 0) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.calculateVolume.two"));
				bean.setGoodsSize("");
			} else {
				bean.setGoodsVolumeTotal(bigDecimal);

				/**
				 * ISSUE-4523新加 1.增加一个体积重字段存体积重 2.原来计算体积重是将长宽高厘米单位转换到米，
				 * 计算体积重又转换到厘米，导致精度损失； 故计算体积重直接使用厘米单位，不做上下转换
				 */
				BigDecimal bigDecimal2 = new BigDecimal(result.toString());
				bigDecimal2 = bigDecimal2
						.setScale(10, BigDecimal.ROUND_HALF_UP);
				BigDecimal m2 = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
				bigDecimal2 = bigDecimal2.divide(m2);
				bigDecimal2 = bigDecimal2.setScale(NumberConstants.NUMBER_6, BigDecimal.ROUND_HALF_UP);// 四舍五入
				if (bigDecimal2.doubleValue() == 0) {
					bigDecimal2 = new BigDecimal("0.01");
				}
				ExpWaybillPanelVo bean2 = (ExpWaybillPanelVo) bean;
				bean2.setGoodsVolumePreviousTotal(bigDecimal2);
				/**
				 *  ISSUE-4523新加
				 */
			}

			ExpCommon.setSaveAndSubmitFalse(ui);
		} catch (ScriptException e) {
			log.error("ScriptException", e);
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.one"),
					e);
		}
	}

	/**
	 * 
	 * （包装-木事件监听）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 上午11:48:26
	 */
	private void woodListener(WaybillPanelVo bean) {

		if (bean.getWood() != null) {
			int wood = bean.getWood();
			if (wood > 0) {
				bean.setWood(Integer.valueOf(wood));
				/**
				 * if (FossConstants.YES.equals(bean.getDoPacking())) { if
				 * (bean.getGoodsName() == null ||
				 * "".equals(bean.getGoodsName())) { MsgBox.showInfo(i18n.get(
				 * "foss.gui.creating.listener.Waybill.woodListener.three"));
				 * bean.setWood(0); // 任何时候只要setWood 为0 都必须调用下面三个方法
				 * ExpCommon.unsetWaybillPanelVoForWoodenPack(bean,ui);
				 * ExpCommon.unsetStorageMatterForWoodenPack(bean);
				 * ExpCommon.unsetWoodenPackFee(bean); } else if
				 * (bean.getGoodsQtyTotal() == null ||
				 * "".equals(bean.getGoodsQtyTotal())) {
				 * MsgBox.showInfo(i18n.get
				 * ("foss.gui.creating.listener.Waybill.woodListener.four"));
				 * bean.setWood(0); // 任何时候只要setWood 为0 都必须调用下面三个方法
				 * ExpCommon.unsetWaybillPanelVoForWoodenPack(bean,ui);
				 * ExpCommon.unsetStorageMatterForWoodenPack(bean);
				 * ExpCommon.unsetWoodenPackFee(bean); } else if
				 * (bean.getGoodsWeightTotal() == null ||
				 * "".equals(bean.getGoodsWeightTotal())) {
				 * MsgBox.showInfo(i18n.
				 * get("foss.gui.creating.listener.Waybill.woodListener.five"));
				 * bean.setWood(0); // 任何时候只要setWood 为0 都必须调用下面三个方法
				 * ExpCommon.unsetWaybillPanelVoForWoodenPack(bean,ui);
				 * ExpCommon.unsetStorageMatterForWoodenPack(bean);
				 * ExpCommon.unsetWoodenPackFee(bean); } else if
				 * (bean.getGoodsVolumeTotal() == null ||
				 * "".equals(bean.getGoodsVolumeTotal())) {
				 * MsgBox.showInfo(i18n.
				 * get("foss.gui.creating.listener.Waybill.woodListener.six"));
				 * bean.setWood(0); // 任何时候只要setWood 为0 都必须调用下面三个方法
				 * ExpCommon.unsetWaybillPanelVoForWoodenPack(bean,ui);
				 * ExpCommon.unsetStorageMatterForWoodenPack(bean);
				 * ExpCommon.unsetWoodenPackFee(bean); } else { if
				 * (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(ui,
				 * i18n
				 * .get("foss.gui.creating.listener.Waybill.woodListener.one"),
				 * i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
				 * JOptionPane.YES_NO_OPTION)) { //ui.showWoodYokeDialog();
				 * ExpCommon.setSaveAndSubmitFalse(ui); } } } else {
				 * MsgBox.showInfo(i18n.get(
				 * "foss.gui.creating.listener.Waybill.woodListener.two")); }
				 */
					} else {
				ExpCommon.unsetWaybillPanelVoForWoodenPack(bean, ui);
				ExpCommon.unsetStorageMatterForWoodenPack(bean);
				ExpCommon.unsetWoodenPackFee(bean);
			}
		} else {
			bean.setWood(Integer.valueOf(0));
		}

	}

	/**
	 * 
	 * 包装-纤事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午02:33:41
	 * @param bean
	 */
	private void fibreListener(WaybillPanelVo bean) {
		// 如果数据为空，则置为0
		if (bean.getFibre() == null) {
			bean.setFibre(Integer.valueOf(0));
		}
	}

	/**
	 * 
	 * 包装-托事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午02:33:41
	 * @param bean
	 */
	private void salverListener(WaybillPanelVo bean) {
		// 如果数据为空，则置为0
		if (bean.getSalver() == null) {
			bean.setSalver(Integer.valueOf(0));
		}
	}

	/**
	 * 
	 * 包装-膜事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午02:33:41
	 * @param bean
	 */
	private void membraneListener(WaybillPanelVo bean) {
		// 如果数据为空，则置为0
		if (bean.getMembrane() == null) {
			bean.setMembrane(Integer.valueOf(0));
		}
	}

	/**
	 * 重载showPickupStationDialog方法，传入提货网点对象集合
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午9:45:56
	 */

	private void showPickupStationDialog(ExpWaybillPanelVo bean,
			List<BranchVo> depts) {
		if (CollectionUtils.isNotEmpty(depts)) {
			// 若匹配的值超过1个则弹出选择框
			if (depts.size() > 1) {
				// 创建弹出窗口
				QueryPickupStationDialog dialog = new QueryPickupStationDialog(
						depts, bean);
				// 剧中显示弹出窗口
				WindowUtil.centerAndShow(dialog);
				BranchVo branchVO = dialog.getBranchVO();

				ExpShowPickupStationDialogAction action = new ExpShowPickupStationDialogAction();
				action.setInjectUI(ui);
				action.setDialogData(branchVO, bean);

				// 若惟一匹配则直接将数据填充到运单开单的提货网点中
			} else {
				// gis查询出的部门编码对象（只传过来编码与是否外发）
				BranchVo vo = depts.get(0);
				// 设置提货网点
				bean.setCustomerPickupOrgCode(vo);
				bean.setCustomerPickupOrgName(StringUtil.defaultIfNull(vo
						.getName()));

				// 反写目的站:部门简称
				bean.setTargetOrgCode(vo.getTargetOrgName());

				ExpShowPickupStationDialogAction action = new ExpShowPickupStationDialogAction();
				action.setInjectUI(ui);
				action.setDialogData(vo, bean);
			}
			ExpCommon.setSaveAndSubmitFalse(ui);
		} else {
			// 不做业务处理
		}
	}

	/**
	 * 
	 * 退款类型事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午05:19:11
	 */
	private void refundTypeListener(WaybillPanelVo bean) {
		DataDictionaryValueVo vo = bean.getRefundType();
		if (bean.getDeliveryCustomerCode() == null
				|| "".equals(bean.getDeliveryCustomerCode())) {
			if (vo != null && vo.getValueCode() != null) {
				// Common.cleanCodInfo(ui, bean);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.common.exception.refundType"));
			}
		}

		if (vo == null || vo.getValueCode() == null) {
			// Common.cleanCodInfo(ui, bean);
		} else {
			ExpCommon.setBankInfo(bean, ui, waybillService);
		}
		if (vo != null && vo.getValueCode() != null) {
			ExpCommon.setSaveAndSubmitFalse(ui);
		}
	}

	/**
	 * 
	 * 返单类型事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:30:24
	 */
	public void returnBillTypeListener(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		// 将返单费用设置到其他费用表格中
		if (bean.getCustomerPickupOrgCode() != null) {
			// setReturnBillCharge(ui, bean);
		} else {
			if (!WaybillConstants.NOT_RETURN_BILL.equals(bean
					.getReturnBillType().getValueCode())) {
				setReturnBillType(bean);
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.returnBillTypeListener.one"));
			}
		}
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 设置返单费用到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	private void setReturnBillCharge(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType()
				.getValueCode())
				&& !WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean
						.getReturnBillType().getValueCode())) {
			if (data == null || data.isEmpty()) {
				data = new ArrayList<OtherChargeVo>();
			}

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
			OtherChargeVo otherVo = getReturnBillCharge(bean, list, data);
			// 添加返单费用到其他费用表格
			String chargeName = ExpCommon.addOtherCharge(data, otherVo, bean);
			// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
			bean.setReturnBillChargeName(chargeName);
			ui.incrementPanel.setChangeDetail(data);
		} else {
			// 删除返单
			deleteReturnBill(data, bean);
		}
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
	 * 开单付款方式事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午03:21:41
	 */
	private void paidMethodListener(WaybillPanelVo bean) {
		// 付款方式规则判断
		identityPayment(bean);
		//liding comment for NCI Project
		/**
		 * 该方法验证若是银行卡付款，则交易流水号是否可编辑
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-23上午08:13
		 */
		//whetherBankCardPayment(bean);
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * <p>
	 * 判断付款方式是否符合业务规则
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2013-1-16 下午4:17:51
	 * @param bean
	 * @see
	 */
	private void identityPayment(WaybillPanelVo bean) {
		if (bean.getPaidMethod() != null) {
		// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付
			if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod()
					.getValueCode())) {
				if (!WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean
						.getOrderChannel())
						&& !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE
								.equals(bean.getOrderPayment())) {
					MsgBox.showInfo(i18n
							.get("foss.gui.creating.listener.Waybill.identityPayment.one"));
				setPaidMethod(bean);
			}
		}
		// 付款方式为临时欠款、到付 不允许勾选预付费保密
		// 产品为汽运偏线 不允许勾选预付费保密
			if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod()
					.getValueCode())
					|| WaybillConstants.ARRIVE_PAYMENT.equals(bean
							.getPaidMethod().getValueCode())
					|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
							.equals(((ProductEntityVo) ui.transferInfoPanel
									.getCombProductType().getSelectedItem())
									.getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
							.equals(((ProductEntityVo) ui.transferInfoPanel
									.getCombProductType().getSelectedItem())
									.getCode())) {
			ui.incrementPanel.getChbSecrecy().setEnabled(false);
			ui.incrementPanel.getChbSecrecy().setSelected(false);
		} else {
			ui.incrementPanel.getChbSecrecy().setEnabled(true);
		}

		/**
		 * 临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
		 */
			if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod()
					.getValueCode())) {
				// 判断客户编码是否为空
				if (StringUtil.isEmpty(bean.getDeliveryCustomerCode())) {
					MsgBox.showError(i18n
							.get("foss.gui.creating.listener.Waybill.MsgBox.payment"));
			}
		}
		ExpCommon.validateExpPayMethod(bean);
	}
	}

	/**
	 * 
	 * 整车事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午06:19:55
	 */
	public void isWholeVehicleListener(final WaybillPanelVo bean) {
		// 如果是整车
		if (bean.getIsWholeVehicle()) {
			if (StringUtil.isEmpty(bean.getWaybillNo())) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.listener.Waybill.isWholeVehicleListener.five"));
				// 整车设置为不选中状态
				bean.setIsWholeVehicle(false);
			} else {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
								ui,
								i18n.get("foss.gui.creating.listener.Waybill.isWholeVehicleListener.one"),
								i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
								JOptionPane.YES_NO_OPTION)) {
							ExpCommon.setWholeVehicleData(ui, bean);
							// 当改变为整车时，改变提货方式
							ProductEntityVo productVo = bean.getProductCode();
							if (productVo != null
									&& productVo.getCode() != null) {
								// 汽运提货方式
								ExpCommon.highwaysReceiveMethod(waybillService,
										bean, ui);
							}
							// 设置上门接货和司机工号是不可编辑
							ui.basicPanel.getCboReceiveModel()
									.setEnabled(false);
							// 整车不能使用优惠券
							ui.incrementPanel.getTxtPromotionNumber()
									.setEnabled(false);
							// 整车不能参与营销活动
							setterVehicleActiveInfo(bean, ui);
						} else {
							// 整车设置为不选中状态
							bean.setIsWholeVehicle(false);
							// 经过营业部设置为不选中状态
							bean.setIsPassDept(false);
							// 经过营业部
							ui.basicPanel.getChbPassDept().setEnabled(false);
							// 设置上门接货和司机工号是可编辑
							ui.basicPanel.getCboReceiveModel().setEnabled(true);
							// 非整车可以使用优惠券
							ui.incrementPanel.getTxtPromotionNumber()
									.setEnabled(true);
							// 非整车可以参与营销活动
							ui.incrementPanel.getCombActiveInfo().setEnabled(
									true);
						}

					}
				});
			}

		} else {
			if (!StringUtil.isEmpty(bean.getWaybillNo())) {// 运单编号部位空时再执行
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
								ui,
								i18n.get("foss.gui.creating.listener.Waybill.isWholeVehicleListener.three"),
								i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
								JOptionPane.YES_NO_OPTION)) {

							// 整车约车报价
							ui.billingPayPanel.billingPayBelongPanel
									.getTxtWholeVehicleAppfee().setVisible(
											false);
							// 整车约车报价标签
							ui.billingPayPanel.billingPayBelongPanel
									.getLblWholeVehicleAppfee().setVisible(
											false);
							ui.basicPanel.getTxtVehicleNumber().setVisible(
									false);
							ui.basicPanel.getLblVehicleNumber().setVisible(
									false);
							ui.basicPanel.getBtnImportVehicle().setVisible(
									false);
							ui.billingPayPanel.billingPayBelongPanel
									.getLblPublicCharge()
									.setText(
											i18n.get("foss.gui.creating.listener.Waybill.isWholeVehicleListener.four")
													+ "：");
							// 清空产品，设置为非整车产品类型
							ExpCommon.cleanProductToOtherType(ui);
							// 设置运输性质为“精准卡航”
							ExpCommon.setProductCode(ui, bean,
									ExpWaybillConstants.PACKAGE);

							ExpCommon.setUIEnableTrue(bean, ui);
							// 根据网点是否能开代收货款设置编辑状态
							setCanCodEnabled(bean);

							// 经过营业部设置为不选中状态
							bean.setIsPassDept(false);
							/**
							 * 如果不是整车，设置整车约车编码为空
							 */
							bean.setVehicleNumber(null);
							// 经过营业部
							ui.basicPanel.getChbPassDept().setEnabled(false);
							// 公布价zoushengli
							ui.billingPayPanel.billingPayBelongPanel
									.getTxtPublicCharge().setEnabled(false);
							// 设置上门接货和司机工号是可编辑
							ui.basicPanel.getCboReceiveModel().setEnabled(true);
							// 非整车可以使用优惠券
							ui.incrementPanel.getTxtPromotionNumber()
									.setEnabled(true);
							// 非整车可以参与营销活动
							ui.incrementPanel.getCombActiveInfo().setEnabled(
									true);
						} else {
							// 整车设置为选中状态
							bean.setIsWholeVehicle(true);
							// 经过营业部
							ui.basicPanel.getChbPassDept().setEnabled(true);
							// 设置上门接货和司机工号是不可编辑
							ui.basicPanel.getCboReceiveModel()
									.setEnabled(false);
							// 整车不能使用优惠券
							ui.incrementPanel.getTxtPromotionNumber()
									.setEnabled(false);
							// 整车不能参与营销活动
							setterVehicleActiveInfo(bean, ui);
						}

					}
				});
		}
	}

	}

	/**
	 * 设置整车营销活动不可编辑
	 * 
	 * @创建时间 2014-4-30 下午7:19:50
	 * @创建人： WangQianJin
	 */
	private void setterVehicleActiveInfo(WaybillPanelVo bean,
			ExpWaybillEditUI ui) {
		// 设置整车不能参与营销活动
		DataDictionaryValueVo nullVo = new DataDictionaryValueVo();
		nullVo.setValueName("");
		bean.setActiveInfo(nullVo);
		ui.incrementPanel.getCombActiveInfo().setEnabled(false);
	}

	/**
	 * 
	 * 根据网点是否能开代收货款设置编辑状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 上午10:41:06
	 */
	private void setCanCodEnabled(WaybillPanelVo bean) {
		if (FossConstants.YES.equals(bean.getCanAgentCollected())) {
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
			ExpCommon.cleanCodInfo(ui, bean);
		}
	}

	/**
	 * 
	 * 是否经过营业部事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午06:19:55
	 */
	private void isPassDeptListener(WaybillPanelVo bean) {
		// 设置代收货款设置状态
		ExpCommon.setPassDeptCodEnabled(ui, bean);
	}

	/**
	 * 
	 * 接货费改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-27 下午05:42:49
	 */
	private void pickupFeeListener(WaybillPanelVo bean) {
		BigDecimal pickupFee = BigDecimal.ZERO;
		if (bean.getPickupFee() != null) {
			pickupFee = bean.getPickupFee();
		}
		// 设置画布接货费
		if (bean.getBasePickupFee() != null) {
			if (bean.getAuditNo() == null) {
				if (bean.getBasePickupFee().compareTo(pickupFee) == 1) {
					MsgBox.showInfo(i18n
							.get("foss.gui.creating.listener.Waybill.PickupFeeError"));
					bean.setPickupFee(bean.getBasePickupFee());
				}
			}
		}
		bean.setPickUpFeeCanvas(bean.getPickupFee().toString());
		ExpCommon.getYokeCharge(bean, ui);
		CalculateFeeTotalUtils.calculateFee(bean);
	}

	/**
	 * 
	 * 包装费改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午06:02:44
	 */
	private void packageFeeListener(WaybillPanelVo bean) {

		// 计算运费获得的包装费
		BigDecimal calculatePackageFee = bean.getCalculatedPackageFee();
		// 手写包装费
		BigDecimal packageFee = bean.getPackageFee();

		if((packageFee != null && packageFee.doubleValue()>= NUM_2000)||(calculatePackageFee != null && calculatePackageFee.doubleValue()>= NUM_2000)){
			int n = JOptionPane.showConfirmDialog(null, "确定包装费开单金额为2000以上？", "确认删除框", JOptionPane.YES_NO_OPTION); 
			if (n == JOptionPane.YES_OPTION) { 
        	if((packageFee != null && packageFee.doubleValue()> NUM_300000)||(calculatePackageFee != null && calculatePackageFee.doubleValue()> NUM_300000)){
        	bean.setPackageFee(BigDecimal.ZERO);
        	ExpCommon.getYokeCharge(bean, ui);
			CalculateFeeTotalUtils.calculateFee(bean);
			bean.setPackageFeeCanvas(bean.getPackageFee().toString());
        	throw new WaybillValidateException("超过300000的上限值,请重新输入");
        	}
        
        }else{ 
        	bean.setPackageFee(BigDecimal.ZERO);
        	ExpCommon.getYokeCharge(bean, ui);
			CalculateFeeTotalUtils.calculateFee(bean);
			bean.setPackageFeeCanvas(bean.getPackageFee().toString());
			return;
        }}
		// 打木架
		Integer wood = bean.getWood();

		// 打木架 empty go
		if (wood == null || bean.getWood().intValue() == 0) {

			// 设置保存按钮与提交按钮不可编辑
			ExpCommon.getYokeCharge(bean, ui);
			CalculateFeeTotalUtils.calculateFee(bean);
			bean.setPackageFeeCanvas(bean.getPackageFee().toString());
			return;
		} else {

			// 打木架有值
			if (calculatePackageFee == null || packageFee == null) {

				// 设置保存按钮与提交按钮不可编辑
				ExpCommon.getYokeCharge(bean, ui);
				CalculateFeeTotalUtils.calculateFee(bean);
				bean.setPackageFeeCanvas(bean.getPackageFee().toString());
				return;
			} else {
				// 运费计算值 > 输入值
				if (calculatePackageFee.doubleValue() > packageFee
						.doubleValue()) {

					// 代打木架费情况下, 包装费输入值不能低于运费计算值
					MsgBox.showInfo(i18n
							.get("foss.gui.creating.listener.Waybill.packageFeeListener.one"));

					// 回到包装费焦点
					ui.billingPayPanel.getTxtPackCharge().requestFocus();

					// 把费用设置回去
					// 把费用清0 下面这个步骤会重新计算
					bean.setPackageFee(BigDecimal.ZERO);

					// 设置保存按钮与提交按钮不可编辑
					ExpCommon.getYokeCharge(bean, ui);
					CalculateFeeTotalUtils.calculateFee(bean);
					bean.setPackageFeeCanvas(bean.getPackageFee().toString());

					return;
				} else {
					ExpCommon.getYokeCharge(bean, ui);
					CalculateFeeTotalUtils.calculateFee(bean, false, false);
					bean.setPackageFeeCanvas(bean.getPackageFee().toString());
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
	private void codAmountListener(WaybillPanelVo bean) {
		// 如果发货客户不可以精确代收货款 @author liyongfei 2014-09-18
		if (!FossConstants.YES.equals(bean.getAccurateCollection())) {// 不可以代收货款
			if (bean.getCodAmount() != null
					& bean.getCodAmount().toString().contains(".")) {
				MsgBox.showInfo((i18n
						.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.noSupportAccurate")));
				bean.setCodAmount(BigDecimal.ZERO);
				bean.setCodAmountCanvas(String.valueOf(0));
				bean.setRefundType(null);
				ExpCommon.setSaveAndSubmitFalse(ui);
				cleanBankInfo(bean);
				ui.getIncrementPanel().getTxtCashOnDelivery().requestFocus();
				return;
			}

		}
		if (bean.getCodAmount().compareTo(BigDecimal.ZERO) == 0) {
			ExpCommon.cleanCodInfo(ui, bean);
		} else {
			// 画布代收货款
			bean.setCodAmountCanvas(bean.getCodAmount().toString());
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
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.calculateAction.exception.validateCod.limit"));
						}
					}
				}
			}
//			else if("".equals(bean.getCustomerPickupOrgCode())){
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.limit2"));
//			}
		}

		// 若为PDA补录，则更改代收货款后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ui.incrementPanel.getTxtCashOnDelivery().setForeground(Color.RED);
		}
		/**
		 * DEFECT-7431
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-20上午11:12
		 */
		Common.cpv(bean);

		ExpCommon.getYokeCharge(bean, ui);
		CalculateFeeTotalUtils.calculateFee(bean);
		// 提交
		ui.buttonPanel.getBtnSubmit().setEnabled(false);
		// 提交
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
	}

	/**
	 * 
	 * 清理银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:07:44
	 */
	public void cleanBankInfo(WaybillPanelVo bean) {
		// 收款人名称
		bean.setAccountName("");
		// 收款人开户行
		bean.setAccountBank("");
		// 收款人银行账号
		bean.setAccountCode("");
	}

	/**
	 * 
	 * 装卸费改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午06:10:01
	 */
	private void serviceFeeListener(WaybillPanelVo bean) {

		if (StringUtils.isBlank(ui.getIncrementPanel().getTxtServiceCharge()
				.getText())) {
			bean.setServiceFee(BigDecimal.ZERO);
			bean.setServiceFeeCanvas(String.valueOf('0'));
		}

		bean.setServiceFeeCanvas(bean.getServiceFee().toString());
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 送货费改变事件监听 1. 送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，
	 * 不能下调。当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值，但用户可以上调送货费； 2.3.5
	 * “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也可以向下修改，最小为0） 2.3.6
	 * 除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午06:12:06
	 */
	private void deliveryGoodsFeeListener(WaybillPanelVo bean) {
		// 计算送货费
		BigDecimal calculatedeliveryGoodsFee = bean
				.getCalculateDeliveryGoodsFee();
		// 送货费
		BigDecimal deliveryGoodsFee = bean.getDeliveryGoodsFee();
		// 送货费上限
		// 是否月结
		Boolean chargeMode = bean.getChargeMode();

		// 没有值得情况下 我也当非月结客户处理
		if (chargeMode == null) {
			chargeMode = Boolean.FALSE;
		}

		// 非月结 只能改大
		if (!chargeMode) {
			// 非月结客户,送货费修改金额不能低于系统计算金额
			if (calculatedeliveryGoodsFee.doubleValue() > deliveryGoodsFee
					.doubleValue()) {
				// 将暂存提交按钮设置为不可编辑
				ExpCommon.setSaveAndSubmitFalse(ui);
				bean.setDeliveryGoodsFee(calculatedeliveryGoodsFee);
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.listener.Waybill.deliveryGoodsFeeListener.one"));
			}
		}
		ExpCommon.getYokeCharge(bean, ui);
		CalculateFeeTotalUtils.calculateFee(bean);
		bean.setDeliveryGoodsFeeCanvas(String.valueOf(deliveryGoodsFee));

		/**
		 * @author 218459-foss-dongsiwei、 快递 送货费优化
		 */
		ExpCommon.deliveryGoodsFeeCalculate(bean);
		/**
		 * 优惠编码不为空，才处理优惠券
		 */
		if (bean.getPromotionsCode() != null
				&& !"".equals(bean.getPromotionsCode())) {
			// 处理优惠券
			ExpCommon.executeCoupon(bean, ui);
		}
		// 需要重新计算运费
		CalculateFeeTotalUtils.calculateTotalFee(bean);
	}

	/**
	 * 
	 * 航班类型改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午08:18:39
	 */
	@SuppressWarnings("unchecked")
	private void flightNumberTypeListener(WaybillPanelVo bean) {
		if (bean.getCustomerPickupOrgCode() == null) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullCustomerPickupOrg"));
		}

		if (bean.getFlightNumberType() != null
				&& bean.getFlightNumberType().getValueCode() != null) {
			FlightEntity entity = new FlightEntity();
			// 航班类型（早班、中班、晚班）
			entity.setFlightSort(bean.getFlightNumberType().getValueCode());
			/**
			 * 设置始发站为当前用户所在部门所在城市
			 */
			// UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			// if(user!=null && user.getEmployee()!=null &&
			// user.getEmployee().getDepartment()!=null &&
			// user.getEmployee().getDepartment().getCityCode()!=null){
			// entity.setDepartureAirport(getCityName(user.getEmployee().getDepartment().getCityCode()));
			// }
			// 目的城市
			entity.setDestinationAirport(getCityName(bean
					.getCustomerPickupOrgCode().getCityCode()));
			PaginationDto dto = waybillService
					.queryFlightDtoListBySelectiveCondition(entity, 0,
							Integer.MAX_VALUE);
			if (dto != null) {
				List<FlightDto> flightDto = dto.getPaginationDtos();
				FlightInfoDialog dialog = new FlightInfoDialog(flightDto);
				// 居中显示弹出窗口
				WindowUtil.centerAndShow(dialog);

				FlightDto flight = dialog.getFlightDto();
				if (flight != null) {
					// 设置航班时间
					bean.setFlightShift(ExpCommon.getFlyDate(flight));

					if (flight.getPlanLeaveTime() != null) {
						// 获取空运预计出发时间
						Date leaveTime = waybillService.getAirLeaveTime(bean
								.getReceiveOrgCode(),
								flight.getPlanLeaveTime(), bean
										.getFlightNumberType().getValueCode());
						// 设置航班预计出发时间
						bean.setPreDepartureTime(leaveTime);
						// 设置航班预计到达、自提时间
						bean.setPreCustomerPickupTime(getAirArriveTime(
								flight.getPlanArriveTime(), leaveTime));
					}
				} else {
					// 设置航班时间
					bean.setFlightShift("");
				}
			}
		} else {
			// 设置航班时间
			bean.setFlightShift("");
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
		AdministrativeRegionsEntity entity = waybillService
				.queryAdministrativeRegionsByCode(code);
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
	 * 合票类型改变事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午08:28:57
	 */
	private void freightMethodListener(WaybillPanelVo bean) {
		// 更改提货方式
		ExpCommon.changePickUpModeByHop(bean, ui);
		/**
		 * 判断合票方式和运输性质是否为空
		 */
		if (bean.getFreightMethod() != null && bean.getProductCode() != null) {
			/**
			 * 判断合票方式是否为单独开单和运输性质是否为精准空运
			 */
			if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean
					.getFreightMethod().getValueCode())
					&& ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
							.equals(bean.getProductCode().getCode())) {
				/**
				 * 创建提货方式对象
				 */
				DataDictionaryValueVo receiveMethod = new DataDictionaryValueVo();
				receiveMethod.setValueCode(WaybillConstants.AIRPORT_PICKUP);
				receiveMethod
						.setValueName(i18n
								.get("foss.gui.creating.transferInfoPanel.airportPickup.label"));
				bean.setReceiveMethod(receiveMethod);
				ui.transferInfoPanel.getCombPickMode().setEnabled(false);
			} else {
				ui.transferInfoPanel.getCombPickMode().setEnabled(true);
			}
		}
		// 清空目的站以及预配线路
		cleanTargetEmpty(bean);
	}

	/**
	 * 收货人地址失去焦点事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 下午5:09:13
	 */
	public void receiveCustomerAddressListener(ExpWaybillPanelVo bean) {

		if (bean.getReceiveCustomerAddress() != null
				&& bean.getReceiveCustomerAddress().length() > NumberConstants.NUMBER_100) {
			throw new WaybillGisPickupOrgException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.adresslength"));
		}

		// 非空判断
		if (null == bean.getProductCode()) {
			throw new WaybillGisPickupOrgException(
					i18n.get("foss.gui.creating.listener.Waybill.exception.productTypeNotNull"));
		}
		// 整车不需要自动匹配提货网点
		if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE
				.equals(bean.getProductCode().getCode())) {
			// 若之前有过成功匹配地址的造成地址栏颜色变化时，在清空地址或带出新地址之前应该将地址颜色变回黑色
			ui.getTxtConsigneeAddress().setForeground(Color.BLACK);
			// matchPickupOrg(bean, ui);
		}

		// 若为PDA补录，则更改收货客户信息后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ExpCommon.setForegroundColor(ui, bean);
		}
	}

	/**
	 * 行政区域焦点监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-1 上午10:58:03
	 */
	private void deliveryCustomerAreaListener(WaybillPanelVo bean) {
		// 省市区县变化时重新设置编码和DTO
		String deliveryCustomerArea = ui.getTxtConsignerArea().getText().trim();
		// 判断地址是否为空
		if ("".equals(deliveryCustomerArea)) {
			ui.getTxtConsignerArea().setAddressFieldDto(null);
			// 向bean中设置DTO
			bean.setDeliveryCustomerAreaDto(null);
			// 省份
			bean.setDeliveryCustomerProvCode("");
			// 城市
			bean.setDeliveryCustomerCityCode("");
			// 区县
			bean.setDeliveryCustomerDistCode("");
		} else {
			if (null != ui.getTxtConsignerArea()) {
    			// 获得控件上的DTO
				AddressFieldDto address = ui.getTxtConsignerArea()
						.getAddressFieldDto();
				if (null != address) {
    				// 向bean中设置DTO
    				bean.setDeliveryCustomerAreaDto(address);
    				// 省份
    				bean.setDeliveryCustomerProvCode(address.getProvinceId());
    				// 城市
    				bean.setDeliveryCustomerCityCode(address.getCityId());
    				// 区县
    				bean.setDeliveryCustomerDistCode(address.getCountyId());
    			}
			}
		}
	}

	/**
	 * 行政区域焦点监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午9:49:57
	 */
	private void receiveCustomerAreaListener(ExpWaybillPanelVo bean) {
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
			log.equals("运输性质不能为空！");
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

		// 若为PDA补录，则更改收货客户信息后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ExpCommon.setForegroundColor(ui, bean);
		}
	}

	/**
	 * 匹配提货网点
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午9:52:08
	 */
	private void matchPickupOrg(ExpWaybillPanelVo bean, ExpWaybillEditUI ui) {
		// 地址
		String address = StringUtils.defaultString(bean
				.getReceiveCustomerAddress());
		// 区域
		String area = ui.getTxtConsigneeArea().getText().trim();
		// 判断地址和区域是否为空
		if (!"".equals(address) && !"".equals(area)) {
			// 根据收货人地址获取特殊地址类型
			List<String> remarkTypes = waybillService
					.querySpecialAddressByGis(gainGisPickupOrgDto(bean));
			// 收货人详细地址
			JTextFieldValidate txtAddress = ui.getTxtConsigneeAddress();
			// 设置地址颜色
			setReceiveAddressColor(remarkTypes, txtAddress);

			// 根据收货人地址匹配提货网点
			List<GisDepartmentDto> depts = waybillService
					.queryPickupOrgCodeByGis(gainGisPickupOrgDto(bean));
			// 对象非空判断
			if (depts != null) {
				// 提货网点集合对象
				List<BranchVo> voList = new ArrayList<BranchVo>();
				// 遍历部门信息，通过部门编码和是否代理获得提货网点信息
				for (GisDepartmentDto dto : depts) {
					// 根据组织标杆编码查询部门编码
					OrgAdministrativeInfoEntity org = waybillService
							.queryOrgByUnifiedCode(dto.getDeptNo());
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
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-8 下午7:32:43
	 */
	private void receiveCustomerContactListener(WaybillPanelVo bean) {
		// 若为PDA补录，则更改收货客户信息后颜色变化以示提醒
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(bean
				.getWaybillstatus())) {
			ExpCommon.setForegroundColor(ui, bean);
		}
	}

	/**
	 * 根据特殊地址类型设置
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:59:35
	 */
	private void setReceiveAddressColor(List<String> remarkTypes,
			JTextFieldValidate txtAddress) {
		// 判断是否为空
		if (CollectionUtils.isNotEmpty(remarkTypes)) {
			// 遍历备注类型
			for (String remark : remarkTypes) {
				// 禁行区域：红色
				if (GisConstants.SPECIAL_ADDRESS_FORBID.equals(remark)) {
					txtAddress.setForeground(Color.RED);
				}
				// 进仓区域：黄色
				else if (GisConstants.SPECIAL_ADDRESS_ENTER.equals(remark)) {
					txtAddress.setForeground(Color.YELLOW);
				}
				// 其它：黑色
				else {
					txtAddress.setForeground(Color.BLACK);
				}
			}
		} else {
			// 将已设置的颜色重新设置回来
			txtAddress.setForeground(Color.BLACK);
		}
	}

	/**
	 * 组装到达网点匹配的查询条件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午2:37:06
	 */
	private GisPickupOrgDto gainGisPickupOrgDto(WaybillPanelVo bean) {
		// 定义查询参数
		GisPickupOrgDto dto = new GisPickupOrgDto();
		// 运单号
		dto.setAppNum(bean.getWaybillNo());

		// 获得地址栏VO
		AddressFieldDto address = bu.getProvCityCounty(
				bean.getReceiveCustomerProvCode(),
				bean.getReceiveCustomerCityCode(),
				bean.getReceiveCustomerDistCode());

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

		dto.setTransportway(GisConstants.GIS_TRANS_HIGHWAYS);

		// 提货方式
		String receiveMethod = null;

		if (bean.getReceiveMethod() != null) {
			// 提货方式
			receiveMethod = CommonUtils.covertReceiveMethod(bean
					.getReceiveMethod().getValueCode());
		}

		// gis类型
		String gisType = receiveMethod;
		if (bean.getProductCode() != null && CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode())) {
			if (GisConstants.GIS_MATCH_PICKUP.equals(receiveMethod)) {					gisType = GisConstants.GIS_MATCH_PICKUP_EXPRESS;
			} else if (GisConstants.GIS_MATCH_DELIVER.equals(receiveMethod)) {
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
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午9:24:18
	 */
	private void setPickupOrgData(ExpWaybillEditUI ui, ExpWaybillPanelVo bean,
			List<BranchVo> depts) {
		// 判断返回的标杆编码集合是否为空
		if (CollectionUtils.isNotEmpty(depts)) {
			// 显示网点对象信息
			showPickupStationDialog(bean, depts);
			ExpShowPickupStationDialogAction action = new ExpShowPickupStationDialogAction();
			// 将该UI设置到ShowPickupStationDialogAction类中
			action.setInjectUI(ui);
			// 设置线路信息
			action.setLoadLine(bean);
			action.setAirDeptEnabled(bean);
		} else {
			// 不做业务处理
		}
	}

	/**
	 * 
	 * 预付金额事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:10:48
	 * @param bean
	 */
	private void prePayAmountListener(WaybillPanelVo bean) {
		// 手动修改预付金额，需要自动对到付金额进行计算
		setToPayAmount(bean);
	}

	/**
	 * 
	 * 一半先付一半到付，根据输入的预付金额计算出到付金额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:13:40
	 * @param bean
	 */
	private void setToPayAmount(WaybillPanelVo bean) {
		// 预付金额
		BigDecimal prePayAmount = bean.getPrePayAmount();
		// 到付金额
		BigDecimal toPayAmount = BigDecimal.ZERO;
		// 总金额
		BigDecimal totalAmount = bean.getTotalFee();
		// 代收货款
		BigDecimal codAmount = bean.getCodAmount();
		// 计算除去代收货款的总金额
		totalAmount = totalAmount.subtract(codAmount);
		if (prePayAmount.compareTo(BigDecimal.ZERO) == 0) {
			ui.billingPayPanel.getTxtAdvancesMoney().requestFocus();
			// 将暂存提交按钮设置为不可编辑
			ExpCommon.setSaveAndSubmitFalse(ui);
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.setToPayAmount.one"));
		} else if (prePayAmount.compareTo(totalAmount) > 0) {
			ui.billingPayPanel.getTxtAdvancesMoney().requestFocus();
			// 将暂存提交按钮设置为不可编辑
			ExpCommon.setSaveAndSubmitFalse(ui);
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.setToPayAmount.two"));
		} else {
			// 到付金额 = 总金额 - 预付金额
			toPayAmount = totalAmount.subtract(prePayAmount);
			validateTotalAmount(prePayAmount, toPayAmount, totalAmount, bean);
			// 实际到付金额 = 到付金额+代收货款
			toPayAmount = toPayAmount.add(codAmount);
			bean.setToPayAmount(toPayAmount);
		}

	}

	/**
	 * 
	 * 验证预付金额+到付金额是否=总金额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午02:15:13
	 */
	private void validateTotalAmount(BigDecimal prePayAmount,
			BigDecimal toPayAmount, BigDecimal totalAmount, WaybillPanelVo bean) {
		if (prePayAmount.add(toPayAmount).compareTo(totalAmount) != 0) {
			ExpCommon.setSaveAndSubmitFalse(ui);
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.listener.Waybill.validateTotalAmount.one"));
		}
	}

	/**
	 * 
	 * 到付金额事件监听
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:10:48
	 * @param bean
	 */
	private void toPayAmountListener(WaybillPanelVo bean) {
		// 一半先付一半到付，根据输入的到付金额计算出预付金额
		setPrePayAmount(bean);
	}

	/**
	 * 
	 * 一半先付一半到付，根据输入的到付金额计算出预付金额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 下午12:13:40
	 * @param bean
	 */
	private void setPrePayAmount(WaybillPanelVo bean) {
		// 预付金额
		BigDecimal toPayAmount = bean.getToPayAmount();
		// 到付金额
		BigDecimal prePayAmount = BigDecimal.ZERO;
		// 总金额
		BigDecimal totalAmount = bean.getTotalFee();
		// 代收货款
		BigDecimal codAmount = bean.getCodAmount();
		if (toPayAmount.compareTo(codAmount) < 0) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.listener.Waybill.setPrePayAmount.one"));
			ui.billingPayPanel.getTxtArrivePayment().requestFocus();
			// 将暂存提交按钮设置为不可编辑
			ExpCommon.setSaveAndSubmitFalse(ui);
		} else if (toPayAmount.compareTo(totalAmount) > 0) {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.listener.Waybill.setPrePayAmount.two"));
			ui.billingPayPanel.getTxtArrivePayment().requestFocus();
			// 将暂存提交按钮设置为不可编辑
			ExpCommon.setSaveAndSubmitFalse(ui);
		} else {
			// 到付金额 = 总金额 - 预付金额
			prePayAmount = totalAmount.subtract(toPayAmount);
			validateTotalAmount(prePayAmount, toPayAmount, totalAmount, bean);
			bean.setPrePayAmount(prePayAmount);
		}
	}

	/**
	 * 
	 * 联动后，都需要把提交实体设置为空，保证修改优惠券编号后，都需要用户点击计算，来校验优惠券 和把优惠券的金额减去中运费
	 * 
	 * @author 026113-FOSS-linwensheng
	 * @date 2013-1-4 上午09:52:49
	 */

	private void promotionsCodeListener(WaybillPanelVo bean) {
		bean.setCouponInfoDto(null);
		bean.setCouponFree(null);
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	/**
	 * 
	 * 是否vo.set***也触发该事件类
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-10 上午09:05:30
	 * @return
	 */
	public boolean isFromVoTargetEnable() {
		return false;
	}

	public void returnTypeListener(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		ExpWaybillPanelVo bean2 = (ExpWaybillPanelVo) bean;
		setReturnType(bean2);
		ExpCommon.setSaveAndSubmitFalse(ui);
	}

	private void setReturnType(ExpWaybillPanelVo bean) {
		// int size = ui.getCombWaibillReturnModeModel().getSize();
		// for (int i = 0; i < size; i++) {
		// DataDictionaryValueVo vo = (DataDictionaryValueVo)
		// ui.getCombWaibillReturnModeModel().getElementAt(i);
		// if (vo!= null || StringUtils.isNotBlank(vo.getValueCode())) {
		// ui.basicPanel.getComboBoxReturnType().setSelectedItem(vo);
		// bean.setReturnType(vo);
		// }
		// }
	}

	//liding comment for NCI Project
	/**
	 * 该方法验证若是银行卡付款，则交易流水号是否可编辑
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23上午08:13
	 */
	private void whetherBankCardPayment(WaybillPanelVo bean) {
		// TODO Auto-generated method stub
		if (bean.getPaidMethod() != null
				&& WaybillConstants.CREDIT_CARD_PAYMENT.equals(bean
						.getPaidMethod().getValueCode())) {
			if(BZPartnersJudge.IS_PARTENER){//合伙人项目
				ui.billingPayPanel.getTransactionSerialNumber().setEnabled(false);
			}else{ 
				ui.billingPayPanel.getTransactionSerialNumber().setEnabled(true);
			}
		} else {
			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(false);
			bean.setTransactionSerialNumber(null);
		}
	}
//	private void whetherBankCardPayment(WaybillPanelVo bean) {
//		// TODO Auto-generated method stub
//		if (bean.getPaidMethod() != null
//				&& WaybillConstants.CREDIT_CARD_PAYMENT.equals(bean
//						.getPaidMethod().getValueCode())) {
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(true);
//		} else {
//			ui.billingPayPanel.getTransactionSerialNumber().setEnabled(false);
//			bean.setTransactionSerialNumber(null);
//		}
//	}

	//liding comment for NCI Project
	/**
	 * 对交易流水号进行监控
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-23
	 */
//	private void verificate(String transactionSerialNumber) {
//		// TODO Auto-generated method stub
//		if (StringUtils.isEmpty(transactionSerialNumber)) {
//			MsgBox.showInfo(i18n
//					.get("foss.gui.creatingexp.listener.Waybill.transactionSerialNumber"));
//		}
//	}

	/**
	 * 内部折扣方案改变付款方式监听事件 dp-foss-dongjialing 225131
	 */
	private void internalDeliveryListener(WaybillPanelVo bean) {
		JComboBox internalDeliveryType = ui.basicPanel
				.getInternalDeliveryType();
		JTextFieldValidate txtStaffNumber = ui.basicPanel.getTxtStaffNumber();
		JComboBox comBox = ui.incrementPanel.getCombPaymentMode();
		DefaultComboBoxModel model = ui.getCombPaymentModeModel();
		DefaultComboBoxModel internalDeliveryTypeModel = ui
				.getDeliveryTypeModel();
		CommonUtils.internalDelivery(bean, internalDeliveryType,
				internalDeliveryTypeModel, txtStaffNumber, comBox, model,false);
	}

	/**
	 * 是否伙伴开单
	 * 
	 * @param bean
	 */
	private void partnerBillingListener(ExpWaybillPanelVo bean) {
		boolean bool = ui.basicPanel.getPartnerCheckBox().isSelected();
		if (bool) {
			// bean.setPartnerBillingLogo("Y");
			ui.basicPanel.getPartnerName().setEditable(true);
			ui.basicPanel.getPartnerName().setEnabled(true);
			ui.basicPanel.getPartnerName().setVisible(true);
			ui.basicPanel.getPartnerPhone().setEditable(true);
			ui.basicPanel.getPartnerPhone().setVisible(true);
			ui.basicPanel.getPartnerLabel().setVisible(true);
			ui.basicPanel.getPartnerPhone().setEnabled(true);
			ui.basicPanel.getPhomeLabel().setVisible(true);
			ui.incrementPanel.getTxtPromotionNumber().setEditable(false);
			ui.incrementPanel.getTxtPromotionNumber().setEnabled(false);
			bean.setPromotionsCode("");
			ui.billingPayPanel.getBtnSubmit().setEnabled(false);
			ui.buttonPanel.getBtnSubmit().setEnabled(false);
			ui.incrementPanel.getCombActiveInfo().setEnabled(false);
			ui.incrementPanel.getCombActiveInfo().setEditable(false);
		} else {
			// bean.setPartnerBillingLogo("N");
			bean.setPartnerName("");
			bean.setPartnerPhone("");
			ui.basicPanel.getPartnerName().setEditable(false);
			ui.basicPanel.getPartnerName().setEnabled(false);
			ui.basicPanel.getPartnerName().setVisible(false);
			ui.basicPanel.getPartnerLabel().setVisible(false);
			ui.basicPanel.getPartnerPhone().setEditable(false);
			ui.basicPanel.getPartnerPhone().setEnabled(false);
			ui.basicPanel.getPartnerPhone().setVisible(false);
			ui.basicPanel.getPhomeLabel().setVisible(false);
			ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
					.setEditable(false);
			ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
					.setEnabled(false);//zoushengli
			bean.setTransportFee(BigDecimal.ZERO);
			ui.billingPayPanel.getBtnSubmit().setEnabled(false);
			ui.buttonPanel.getBtnSubmit().setEnabled(false);
			ui.incrementPanel.getTxtPromotionNumber().setEditable(true);
			ui.incrementPanel.getTxtPromotionNumber().setEnabled(true);
			ui.incrementPanel.getCombActiveInfo().setEnabled(true);
			ui.incrementPanel.getCombActiveInfo().setEditable(true);
		}
	}

	/**
	 * 上门发货监听事件
	 * 
	 * @author 218459-foss-dongsiwei
	 */
	private void homehomeDeliveryListener(ExpWaybillPanelVo bean) {
		// 选择了 上门发货之后 提交按钮不可用，需要重新计算总运费
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (bean.isHomeDelivery()) {
			if (StringUtils.isEmpty(bean.getTargetOrgCode())) {
				throw new WaybillValidateException("目的站不能为空");
			}
			boolean flag = false;
			for (OtherChargeVo vo : data) {
				if ("ZYSCZJH".equals(vo.getCode())) {
					flag = true;
				}
			}
			List<ValueAddDto> list = waybillService
					.queryValueAddPriceList(ExpCommon.getQueryOtherChargeParam(
							bean, "ZYSCZJH"));
			List<OtherChargeVo> otherVo = getOtherChargeList(list);
			if (CollectionUtils.isNotEmpty(otherVo) && !flag) {
				data.add(otherVo.get(0));
			}
			ui.incrementPanel.setChangeDetail(data);
			ExpCommon.calculateOtherCharge(ui, bean);
			ui.basicPanel.getTxtCourierNumber().setEnabled(false);
			ui.basicPanel.getBtnQueryCourier().setEnabled(false);
		} else {
			ui.basicPanel.getTxtCourierNumber().setEnabled(true);
			ui.basicPanel.getBtnQueryCourier().setEnabled(true);
			if(CollectionUtils.isEmpty(data)){
				return;
			}
			for (int i = 0; i < data.size(); i++) {
				if ("ZYSCZJH".equals(data.get(i).getCode())) {
					data.remove(i);
				}
			}
			ui.incrementPanel.setChangeDetail(data);
			ExpCommon.calculateOtherCharge(ui, bean);
		}
	}

}
