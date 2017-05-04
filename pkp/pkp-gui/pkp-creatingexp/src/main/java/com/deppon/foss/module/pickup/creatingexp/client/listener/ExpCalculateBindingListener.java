/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.listener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.binding.BindingEvent;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.IBindingListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpCalculateAction;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpCalculateCostsDialog;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpCalculateBindingListener implements IBindingListener {

	// log object
	private static final Log log = LogFactory
			.getLog(ExpCalculateBindingListener.class);

	// 保价声明上限
	ExpCalculateAction expAction;

	// 简单计算运费UI
	ExpCalculateCostsDialog ui;

	// 绑定对象
	IBinder<ExpWaybillPanelVo> waybillBinder;

	// 运单业务对象通过工场生成离线业务对象和在线业务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	private IBaseDataService baseDataService = GuiceContextFactroy
			.getInjector().getInstance(BaseDataService.class);

	// 工具类获取branchVo对象
	BusinessUtils bu = new BusinessUtils();

	public ExpCalculateBindingListener(ExpCalculateCostsDialog ui) {
		this.ui = ui;

	}

	@Override
	public void bindingTriggered(List<BindingEvent> be) {
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo bean = waybillBinder.getBean();
		try {
			for (BindingEvent bindingEvent : be) {
				// 运输性质
				if ("productCode".equals(bindingEvent.getPropertyName())) {
					productCodeListener(bean);
				}
				// 总重量
				else if ("goodsWeightTotal".equals(bindingEvent
						.getPropertyName())) {
					goodsWeightTotalListener(bean);
				}
				// 保价声明
				else if ("insuranceAmount".equals(bindingEvent
						.getPropertyName())) {
					insuranceAmountListener(bean);
				}
				// 保价费
				else if ("insuranceFee".equals(bindingEvent.getPropertyName())) {
					insuranceFeeListener(bean);
				}
				// 提货方式
				else if ("receiveMethod".equals(bindingEvent.getPropertyName())) {
					receiveMethodListener(bean);
				}
				// 体积
				else if ("goodsVolumeTotal".equals(bindingEvent
						.getPropertyName())) {
					goodsVolumeTotalListener(bean);
				}
			}
		} catch (BusinessException w) {
			log.error("WaybillValidateException", w);
			if (!"".equals(w.getMessage())) {
				MsgBox.showInfo(w.getMessage());
			}
		}

	}

	/**
	 * 总体积的监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-27 下午6:55:10
	 */
	private void goodsVolumeTotalListener(ExpWaybillPanelVo bean) {
		if (null == bean.getGoodsVolumeTotal()) {
			bean.setGoodsVolumeTotal(BigDecimal.ZERO);
		}

		// //总体积 TODO
		// BigDecimal volumeTotal =
		// CommonUtils.defaultIfNull(bean.getGoodsVolumeTotal());
		// // 经济快递的体积上限
		// BigDecimal volumeUp = CommonUtils.defaultIfNull(bean.getVolumeUp());
		// // 判断是否有上限值
		// if (volumeUp.compareTo(BigDecimal.ZERO) > 0) {
		// // 录入值不能超过经济快递的体积上限
		// if (volumeTotal.compareTo(volumeUp) > 0) {
		// bean.setGoodsVolumeTotal(BigDecimal.ZERO);
		// ui.getTxtVolume().setText("0");
		// MsgBox.showInfo("录入值不能超过经济快递的体积上限值：" + volumeUp.toString());
		// ui.getTxtVolume().requestFocus();
		// return ;
		// }
		// }

	}

	/**
	 * 提货方式改变触发提货网点信息变化
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-16 上午8:38:35
	 */
	private void receiveMethodListener(ExpWaybillPanelVo bean) {
		// 设置目的站为空
		bean.setTargetOrgCode("");
		// 设置提货网点为空
		bean.setCustomerPickupOrgCode(null);
		bean.setCustomerPickupOrgName("");
		bean.setTargetSalesDepartmentCityDto(null);
	}

	/**
	 * 保价费监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-5 上午11:16:39
	 */
	private void insuranceFeeListener(ExpWaybillPanelVo bean) {
		// 获得保价声明
		BigDecimal amount = CommonUtils
				.defaultIfNull(bean.getInsuranceAmount());

		// 判断保险声明价值是否为0
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			bean.setInsuranceFee(BigDecimal.ZERO);
			ui.getTxtInsuranceFee().setText("0");
			MsgBox.showError("保价声明为0，则保价费必须为0！");
			ui.getTxtInsuranceFee().requestFocus();
			return;
		}

		// 获得保价费
		BigDecimal fee = CommonUtils.defaultIfNull(bean.getInsuranceFee());

		// 经济快递的保价费上限
		BigDecimal insuranceFeeUp = CommonUtils.defaultIfNull(bean
				.getInsuranceFeeUp());
		// 判断是否有上限值
		if (insuranceFeeUp.compareTo(BigDecimal.ZERO) > 0) {
			// 录入值不能超过经济快递的保价申明价值上限
			if (fee.compareTo(insuranceFeeUp) > 0) {
				bean.setInsuranceFee(BigDecimal.ZERO);
				ui.getTxtInsuranceFee().setText("0");
				MsgBox.showInfo("录入值不能超过经济快递的保价费上限值："
						+ insuranceFeeUp.toString());
				ui.getTxtInsuranceFee().requestFocus();
				return;
			}
		}
		// expAction.validateInsuranceFee(bean);
		// 计算保价费率
		// calculateInsuranceRate(bean);
	}

	/**
	 * 保价声明监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-5 上午11:16:50
	 */
	private void insuranceAmountListener(ExpWaybillPanelVo bean) {
		/*
		 * // 快递保价申明价值上限 ConfigurationParamsEntity param =
		 * baseDataService.queryByConfCode
		 * (ExpWaybillConstants.EXPRESS_INSURRANCE_UP,
		 * bean.getReceiveOrgCode()); // 快递保价申明价值上限 // if (param != null) { //
		 * bean.setMaxInsuranceAmount(new BigDecimal(param.getConfValue())); //
		 * } // 获得保价费 // BigDecimal fee =
		 * CommonUtils.defaultIfNull(bean.getInsuranceFee()); // 获得保价声明
		 * BigDecimal amount =
		 * CommonUtils.defaultIfNull(bean.getInsuranceAmount());
		 * 
		 * // //判断保险声明价值是否为0 // if(fee.compareTo(BigDecimal.ZERO) != 0 &&
		 * amount.compareTo(BigDecimal.ZERO) == 0){ //
		 * MsgBox.showError("保价声明为0，则保价费必须为0！"); //
		 * ui.getTxtInsuranceAmount().requestFocus(); // return ; // }
		 * 
		 * // 经济快递的保价申明价值上限 BigDecimal insuranceAmountUp =
		 * CommonUtils.defaultIfNull(new BigDecimal(param.getConfValue()));
		 * 
		 * // 判断是否有上限值 if (insuranceAmountUp.compareTo(BigDecimal.ZERO) > 0) {
		 * // 录入值不能超过经济快递的保价申明价值上限 if (amount.compareTo(insuranceAmountUp) > 0)
		 * { bean.setInsuranceAmount(BigDecimal.ZERO);
		 * ui.getTxtInsuranceAmount().setText("0");
		 * MsgBox.showInfo("录入值不能超过经济快递的保价申明价值上限值：" +
		 * insuranceAmountUp.toString());
		 * ui.getTxtInsuranceAmount().requestFocus(); return ; } }
		 */

		// expAction.validateInsuranceFee(bean);

		BigDecimal insuranceAmount = bean.getInsuranceAmount();
		if (insuranceAmount == null || insuranceAmount.doubleValue() <= 0) {
			if (bean.getInsuranceFee() != null
					&& bean.getInsuranceFee().doubleValue() > 0) {
				throw new WaybillValidateException("保险声明价值为0的情况下保费不能大于0");

			}

		} else {
			/**
			 * add远郊保价上限
			 * 
			 * @author:270293-foss-zhangfeng
			 * @date:2015-07-06
			 */
			// 保价不为空
			if (bean.getInsuranceAmount() != null) {
				BigDecimal upAmount;
				// 特安客户保价不会空
				if (bean.getVipInsuranceAmount() != null) {
					// 特安客户保价上限
					upAmount = bean.getVipInsuranceAmount();
					if (upAmount != null
							&& upAmount.doubleValue() > 0
							&& upAmount.doubleValue() < bean
									.getInsuranceAmount().doubleValue()) {
						WaybillValidateException e = new WaybillValidateException(
								"保价金额超过" + upAmount + "元，请重输");
						throw e;
					}
				} else {

					// 根据编码code综合取参数对象
					IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
							.getService(IWaybillHessianRemoting.class);
					ConfigurationParamsEntity configentity = waybillRemotingService
							.queryMaxInsuranceAmount(ExpWaybillConstants.EXPRESS_INSURRANCE_FEE_UP);
					// 提货网点名称
					String costomerPickupOrgName = bean
							.getCustomerPickupOrgName();
					if (costomerPickupOrgName != null
							&& (costomerPickupOrgName.indexOf("远郊") >= 0 || costomerPickupOrgName
									.indexOf("外发") >= 0)) {
						if (null != configentity
								&& bean.getInsuranceAmount().doubleValue() > Double
										.valueOf(configentity.getConfValue())) {
							bean.setInsuranceAmount(BigDecimal.ZERO);
							ui.getTxtInsuranceAmount().setText("0");
							WaybillValidateException e = new WaybillValidateException(
									"保价金额超过" + configentity.getConfValue()
											+ "元，请重输");
							throw e;
						}
					} else if (null != costomerPickupOrgName) {
						// 保价申明价值上限
						upAmount = bean.getMaxInsuranceAmount();
						if (bean.getInsuranceAmount().doubleValue()  > Double
								.valueOf(upAmount.doubleValue())) {
							bean.setInsuranceAmount(BigDecimal.ZERO);
							ui.getTxtInsuranceAmount().setText("0");
							WaybillValidateException e = new WaybillValidateException(
									"保价金额超过" + upAmount + "元，请重输");
							throw e;
						}
					} else if (null == costomerPickupOrgName) {
						bean.setInsuranceAmount(BigDecimal.ZERO);
						ui.getTxtInsuranceAmount().setText("0");
						WaybillValidateException e = new WaybillValidateException(
								"请先填写目的站");
						throw e;

					}
				}
			}
		}

		// 计算保价费率
		// calculateInsuranceRate(bean);
	}

	// /**
	// * 计算保价费率
	// * @author 026123-foss-lifengteng
	// * @date 2013-8-5 上午11:38:26
	// */
	// private void calculateInsuranceRate(ExpWaybillPanelVo bean) {
	// // 获得保价声明
	// BigDecimal amount = CommonUtils.defaultIfNull(bean.getInsuranceAmount());
	// // 获得保价费
	// BigDecimal fee = CommonUtils.defaultIfNull(bean.getInsuranceFee());
	//
	// // 判断保价声明和保价费是否全为0,全不为0时才计算保价费率
	// if (CommonUtils.defaultIfNull(amount).compareTo(BigDecimal.ZERO) != 0 &&
	// CommonUtils.defaultIfNull(amount).compareTo(BigDecimal.ZERO) != 0) {
	// // 取绝对值
	// fee = fee.abs();
	// bean.setInsuranceFee(fee);
	// amount = amount.abs();
	// bean.setInsuranceAmount(amount);
	//
	// // 计算保价费率
	// BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
	// BigDecimal rate = fee.multiply(permillage).divide(amount, 2,
	// BigDecimal.ROUND_HALF_UP);
	// bean.setInsuranceRate(rate);
	// }
	// }

	/**
	 * 总重量监听事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-2 下午4:24:49
	 */
	private void goodsWeightTotalListener(ExpWaybillPanelVo bean) {
		// 总重量
		int totalQty = CommonUtils.defaultIfNull(bean.getGoodsWeightTotal())
				.intValue();
		// 判断总重量是否为0
		if (totalQty != 0) {
			// 当用户输入的货物总重量大于30公斤时，系统提示用户运输性质为快递是否确定货物重量为输入的重量，确认之后可以继续查询
			if (totalQty > NumberConstants.NUMBER_30) {
				// 是否确认补录
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
						null, "是否确认【快递包裹】的货物重量大于30公斤？", "提示",
						JOptionPane.YES_NO_OPTION)) {
					// 是则继续
				} else {
					// 否则重量清零并获得焦点
					bean.setGoodsQtyTotal(Integer.parseInt("0"));
					ui.getTxtWeight().setText("0");
				}
			}
			// 若不超过30KG，则分别设置首重、续重1和续重2
			else {

			}
		}
	}

	/**
	 * 
	 * （运输性质事件监听）
	 * 
	 * @author wqj
	 * @date 2013-03-29
	 */
	private void productCodeListener(ExpWaybillPanelVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		if (productVo != null && productVo.getCode() != null) {
			// 根据运输性质改变提货方式
			ExpCommon.changePickUpMode(bean, ui);

			// 清空目的站以及预配线路
			cleanTargetEmpty(bean);
		}
	}

	/**
	 * 
	 * 清空目的站以及预配线路
	 * 
	 * @author wqj
	 * @date 2013-03-29
	 * @param bean
	 */
	private void cleanTargetEmpty(ExpWaybillPanelVo bean) {
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

	@Override
	public boolean isFromVoTargetEnable() {
		return false;
	}
}
