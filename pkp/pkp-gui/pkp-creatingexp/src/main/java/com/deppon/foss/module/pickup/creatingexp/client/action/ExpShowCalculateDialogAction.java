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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/ShowPickupStationDialogAction.java
 * 
 * FILE NAME        	: ShowPickupStationDialogAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.QueryPickupStationDialog;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog.ExpCalculateCostsDialog;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

public class ExpShowCalculateDialogAction implements IButtonActionListener<ExpCalculateCostsDialog> {

	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(ExpShowCalculateDialogAction.class);

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpShowCalculateDialogAction.class);

	/**
	 * 主界面
	 */
	private ExpCalculateCostsDialog ui;

	/**
	 * open waybill service
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 营业部服务接口
	 */
	SalesDepartmentService salesDepartmentService = DownLoadDataServiceFactory.getSalesDepartmentService();

	/**
	 * 
	 * 功能：openUIAction
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
			IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
			ExpWaybillPanelVo bean = waybillBinder.getBean();

			if (bean.getProductCode() != null) {
				// 显示提货网点查询窗口
				showPickupStationDialog(bean);
				// 加载走货线路
				setLoadLine(bean);
				
				//修改了目的站以后要重新计算保费
				if(bean.getInsuranceFee()!=null && bean.getInsuranceFee().doubleValue()>0){
					MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.changeInsuranceFee"));
					
					// 保险声明值最大值
					//bean.setMaxInsuranceAmount(BigDecimal.ZERO);
					// 保险费率
					bean.setInsuranceRate(BigDecimal.ZERO);
					// 保险手续费
					bean.setInsuranceFee(BigDecimal.ZERO);
					// 保险费ID
					bean.setInsuranceId("");
					// 保险费CODE
					bean.setInsuranceCode("");
				}
				
				
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.noProduct"));
			}
		} catch (BusinessException w) {
			if (!"".equals(w.getMessage())) {
				MsgBox.showInfo(w.getMessage());
			}
		}
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
			if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode())
					&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
				Date leaveTime = getLeaveTime(bean);
				if (leaveTime != null) {
					bean.setPreDepartureTime(leaveTime);// 预计出发时间
					bean.setPreCustomerPickupTime(getPickupDeliveryTime(bean));// 预计派送/自提时间
				} else {
					LOG.debug("未查询到对应的时效  ");
					MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.nullRelativeTime"));
				}
			}
		}
	}

	/**
	 * 
	 * 查询始发配载部门、最终配载部门以及线路
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午03:54:30
	 */
	private void queryLodeDepartmentInfo(WaybillPanelVo bean) {
		OrgInfoDto dto = null;
		try {
			dto = waybillService.queryLodeDepartmentInfo(bean.getPickupCentralized(), bean.getCreateOrgCode(), bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode()
					.getCode());
			if (dto == null || dto.getFreightRoute() == null) {
				// ui.cargoInfoPanel.getBtnWood().setEnabled(false);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
			} else {
				FreightRouteEntity freightRoute = dto.getFreightRoute();
				LOG.info("查询走货路径成功。。。");
				bean.setLoadLineName(dto.getRouteLineName());// 配载线路名称
				LOG.info("配载线路名称:" + dto.getRouteLineName());
				if (freightRoute != null) {
					bean.setLoadLineCode(freightRoute.getVirtualCode());// 配载线路编码
					LOG.info("配载线路编码:" + freightRoute.getVirtualCode());
					bean.setPackageOrgCode(freightRoute.getPackingOrganizationCode());// 代打木架部门编码
					LOG.info("代打木架部门编码:" + freightRoute.getPackingOrganizationCode());
					bean.setPackingOrganizationName(freightRoute.getPackingOrganizationName());// 代打木架部门名称
					LOG.info("代打木架部门名称:" + freightRoute.getPackingOrganizationName());
					bean.setDoPacking(freightRoute.getDoPacking());// 是否可以打木架
					LOG.info("是否可以打木架:" + freightRoute.getDoPacking());
				} else {
					bean.setLoadLineCode("");// 配载线路编码
					LOG.info("配载线路编码:获取到的走货路径实体为空");
					bean.setPackageOrgCode("");// 代打木架部门编码
					LOG.info("代打木架部门编码:");
					bean.setPackingOrganizationName("");// 代打木架部门名称
					LOG.info("代打木架部门名称:");
					bean.setDoPacking("");// 是否可以打木架
					LOG.info("是否可以打木架:");
				}
				bean.setLoadOrgCode(dto.getFirstLoadOrgCode());// 配载部门编号
				LOG.info("配载部门编号:" + dto.getFirstLoadOrgCode());
				bean.setLoadOrgName(dto.getFirstLoadOrgName());// 配载部门名称
				LOG.info("配载部门名称:" + dto.getFirstLoadOrgName());
				bean.setLastLoadOrgCode(dto.getLastLoadOrgCode());// 最终配载部门编号
				LOG.info("最终配载部门编号:" + dto.getLastLoadOrgCode());
				bean.setLastLoadOrgName(dto.getLastLoadOrgName());// 最终配载部门名称
				LOG.info("最终配载部门名称:" + dto.getLastLoadOrgName());

				bean.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());// 最终配置外场
				LOG.info("最终配置外场:" + dto.getLastOutLoadOrgCode());
//				bean.setGoodsTypeIsAB(dto.getGoodsTypeIsAB());// 是否AB货
//				LOG.info("是否可区分AB货:" + dto.getGoodsTypeIsAB());
			}
		} catch (BaseInfoInvokeException e) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
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

			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
			 * 
			 * 
			 * e.getMessage())
			 */
			);
		}
	}

	/**
	 * 显示提货网点查询窗口
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午07:54:48
	 */
	public void showPickupStationDialog(ExpWaybillPanelVo bean) {
		QueryPickupPointDto dto = new QueryPickupPointDto();
		// 网点类型标志
		dto.setDestNetType(bean.getProductCode().getDestNetType());
		// 提货方式
		dto.setPickUpType(bean.getReceiveMethod().getValueCode());
		// 产品编码
		dto.setTransType(bean.getProductCode().getCode());
		// 目的站（PS：此处获取必须从UI中获取而不应从bean获得，因为ui获得的是新值，而bean获得的是旧值-在清空目的站时bean值未被清空）
		if (ui.getTxtDestination() instanceof JTextField) {
			dto.setOrgSimpleName(ui.getTxtDestination().getText());
		}

		// 出发营业部
		dto.setReceiveOrgCode(bean.getReceiveOrgCode());
		// 设置来源为开单
		dto.setSource(WaybillConstants.WAYBILL);
		dto.setCurDate(new Date());

		// 创建弹出窗口
		QueryPickupStationDialog dialog = new QueryPickupStationDialog(dto);
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
		// 获得提货网点对象
		BranchVo branchVO = dialog.getBranchVO();
		setDialogData(branchVO, bean);
	}

	/**
	 * 给相关控件赋值：设置提货网点、目的站、代收货款、其它费用、装卸费、送货进仓等
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午5:20:07
	 */
	public void setDialogData(BranchVo branchVO, ExpWaybillPanelVo bean) {
		if (branchVO != null) {
			bean.setCustomerPickupOrgCode(branchVO);
			bean.setCustomerPickupOrgName(branchVO.getName());

			// 反写目的站:如果目的站不为空，则选择好网点后将其设置为部门简称
			BusinessUtils bu = new BusinessUtils();

			SalesDepartmentCityDto queryDto = new SalesDepartmentCityDto();
			queryDto.setSalesDepartmentCode(branchVO.getCode());
			SalesDepartmentCityDto dto = salesDepartmentService.querySalesDepartmentCityInfo(queryDto);
			SaleDepartmentEntity saleDepartmentEntity = WaybillServiceFactory.getWaybillService().querySaleDeptByCode(branchVO.getCode());
			if (saleDepartmentEntity != null && dto != null) {
				// 营业部是否可以快递接货，如果是的话 就是试点营业部
				dto.setTestSalesDepartment(saleDepartmentEntity.getCanExpressPickupToDoor());
				bean.setTargetSalesDepartmentCityDto(dto);
			}

			// 这里的targetorgcode不一样了 要显示城市
			ProductEntityVo pev = bean.getProductCode();
			if (null == pev) {
				throw new WaybillValidateException("产品类型类型为空，无法继续！");
			}

			/**
			 * 若为包裹，则判断：1、自提时目的站显示提货网点简称；2、送货时目的站为城市，若城市为空测显示“无试点城市”
			 */
			if (dto != null && 
					(CommonUtils.directDetermineIsExpressByProductCode(bean.getReceiveMethod().getValueCode())) && 
					!CommonUtils.verdictPickUpSelf(bean.getReceiveMethod().getValueCode())) {

				// 城市是否为空
				if (StringUtils.isNotEmpty(dto.getCityName())) {
					// 试点城市
					bean.setTargetOrgCode(dto.getCityName());
				} else {
					// 无试点城市
					bean.setTargetOrgCode("无试点城市");
				}
			}
			/**
			 * 若为零担，则不变（目的站显示为提货网点简称）
			 */
			else {
				String simpleName = bu.getSimpleName(branchVO.getCode(), bean.getBillTime());
				if (StringUtils.isNotEmpty(simpleName)) {
					bean.setTargetOrgCode(simpleName);
				} else {
					bean.setTargetOrgCode(branchVO.getTargetOrgName());
				}
			}
		} else {
			// 提货网点
			bean.setCustomerPickupOrgCode(null);
			// 提货网点名称
			bean.setCustomerPickupOrgName("");
			// 目的站名称
			bean.setTargetOrgCode("");
			// 预配线路
			bean.setLoadLineName("");
			// 把装卸费清空
			bean.setServiceFee(BigDecimal.ZERO);
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
		EffectiveDto effectiveDto = new EffectiveDto();
		if (isPickup(bean)) {
			effectiveDto = waybillService.searchPreSelfPickupTime(bean.getCreateOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(),
					new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getSelfPickupTime();
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		} else {
			effectiveDto = waybillService.searchPreDeliveryTime(bean.getReceiveOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(),
					new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getDeliveryDate();
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
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
		String code = bean.getReceiveMethod().getValueCode();
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code))

		{
			return true;
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
		Date leaveTime = waybillService.searchPreLeaveTime(bean.getCreateOrgCode(), bean.getLoadOrgCode(), bean.getProductCode().getCode(), new Date());
		return leaveTime;
	}

	@Override
	public void setInjectUI(ExpCalculateCostsDialog ui) {
		this.ui = ui;
	}
}