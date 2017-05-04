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
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.ModalFrameUtil;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchDialog;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 查询目的站  action
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-shixiaowei
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class LoadPickupStationAction  implements IButtonActionListener<WaybillEditUI>{

	
	/**
	 * 日志
	 */
	public static final Logger LOG = LoggerFactory.getLogger(LoadPickupStationAction.class);

	
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(LoadPickupStationAction.class);
	
	 /**
	  * 主界面
	  */
	private WaybillEditUI ui;

	/**
	 * open waybill service
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	public static File currWorkDir = new File(".");
	/**
	 * 
	 * 功能：openUIAction
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void actionPerformed(ActionEvent e) {
		try{
//		String url = getFossGuiAppHomeAbsPath();
//		
//		IChromeBrowserCallBack myCallBack = new IChromeBrowserCallBack() {
//			@Override
//			public boolean doCallBack(String[] params) throws Exception {
//				return true;
//			}
//		};
			
			HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
			IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
			WaybillPanelVo bean = waybillBinder.getBean();
			
			if(bean.getProductCode()!=null){
    			//显示网点查询窗口
				BranchVo vo = showPickupStationDialog(bean);
				if(vo != null && vo.getCode() != null){
					//判断是否为整车
					if(bean.getIsWholeVehicle()!=null && bean.getIsWholeVehicle()){
						Common.setWholeVehicleByCode(vo.getCode(), bean);
						//获取整车费率范围
						GuiResultBillCalculateDto gDto=Common.getInsuranceRate(bean);
						Common.setRate(gDto,bean,ui);
					}else{
						//加载走货线路
		    			setLoadLine(bean);
		    			//根据运输性质是否空运决定配载部门是否可以编辑
		    			setAirDeptEnabled(bean);
					}
				}
			}else{
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.noProduct"));
			}
			
//			double width_ = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//			double height_ = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//			
//			CefChromeBrowserManager.PopChromeBrowserAsDialog(url, (int)width_-60, (int)height_-110, myCallBack ,false);
		}catch (Exception w) {
			if(StringUtils.isNotEmpty(w.getMessage()))
			{				
				if(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute").equals(w.getMessage()) 
						||w.getMessage().indexOf("没有配置系统参数:")>=0){
					MsgBox.showITServiceInfo(w.getMessage());
				}else{
					MsgBox.showInfo(w.getMessage());
				}
			}
		}
		
	}
	
	public String getFossGuiAppHomeAbsPath() throws Exception {
		return currWorkDir.getAbsolutePath()+File.separator+"static\\loadPickupStation.htm";
	}
	
	/**
	 * 设置预配线路和预计出发时间与预计到达时间
	 * （摄取方法供GIS地图匹配网点使用）
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 上午8:49:17
	 */
	public void setLoadLine(WaybillPanelVo bean){
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
			//运输性质非空判断
			if(null == bean.getProductCode()){
				LOG.error("运输性质不能为空！");
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.productNotAllowNull"));
			}
			
			dto = waybillService.queryLodeDepartmentInfo(bean.getPickupCentralized(),bean.getReceiveOrgCode(), bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode());
			if (dto == null || dto.getFreightRoute() == null) {
				if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
						WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
					ui.pictureCargoInfoPanel.getBtnWood().setEnabled(false);
				}else{
					ui.cargoInfoPanel.getBtnWood().setEnabled(false);
					/**
					 * 将打包装按钮与打木架按钮设置同样
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-12-3下午14:51
					 */
					ui.cargoInfoPanel.getBtnPacking().setEnabled(false);
				}
				
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
			} else {
				FreightRouteEntity freightRoute = dto.getFreightRoute();
				LOG.info("查询走货路径成功。。。");
				bean.setLoadLineName(dto.getRouteLineName());// 配载线路名称
				LOG.info("配载线路名称:"+dto.getRouteLineName());
				if(freightRoute!=null){
					bean.setLoadLineCode(freightRoute.getVirtualCode());// 配载线路编码
					LOG.info("配载线路编码:"+freightRoute.getVirtualCode());
					bean.setPackageOrgCode(freightRoute.getPackingOrganizationCode());// 代打木架部门编码
					LOG.info("代打木架部门编码:"+freightRoute.getPackingOrganizationCode());
					bean.setPackingOrganizationName(freightRoute.getPackingOrganizationName());// 代打木架部门名称
					LOG.info("代打木架部门名称:"+freightRoute.getPackingOrganizationName());
					bean.setDoPacking(freightRoute.getDoPacking());// 是否可以打木架
					LOG.info("是否可以打木架:"+freightRoute.getDoPacking());
				}else{
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
				LOG.info("配载部门编号:"+dto.getFirstLoadOrgCode());
				bean.setLoadOrgName(dto.getFirstLoadOrgName());// 配载部门名称
				LOG.info("配载部门名称:"+dto.getFirstLoadOrgName());
				bean.setLastLoadOrgCode(dto.getLastLoadOrgCode());// 最终配载部门编号
				LOG.info("最终配载部门编号:"+dto.getLastLoadOrgCode());
				bean.setLastLoadOrgName(dto.getLastLoadOrgName());// 最终配载部门名称
				LOG.info("最终配载部门名称:"+dto.getLastLoadOrgName());
				
				bean.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());//最终配置外场
				LOG.info("最终配置外场:"+dto.getLastOutLoadOrgCode());
//				bean.setGoodsTypeIsAB(dto.getGoodsTypeIsAB());//是否AB货
//				LOG.info("是否可区分AB货:"+dto.getGoodsTypeIsAB());
				//设置AB货编辑状态
				//setGoodsTypeAB(bean);
				//如果路径可以打打木架则设置打木架按钮可点击
				if (FossConstants.YES.equals(bean.getDoPacking())) {
					if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
							WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
						ui.pictureCargoInfoPanel.getBtnWood().setEnabled(true);
					}else{
						ui.cargoInfoPanel.getBtnWood().setEnabled(true);
						/**
						 * 将打包装按钮与打木架按钮设置同样
						 * @author:218371-foss-zhaoyanjun
						 * @date:2014-12-3下午14:51
						 */
						ui.cargoInfoPanel.getBtnPacking().setEnabled(true);
					}
					
				}else{
					if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
							WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
						ui.pictureCargoInfoPanel.getBtnWood().setEnabled(false);
					}else{
						ui.cargoInfoPanel.getBtnWood().setEnabled(false);
						/**
						 * 将打包装按钮与打木架按钮设置同样
						 * @author:218371-foss-zhaoyanjun
						 * @date:2014-12-3下午14:51
						 */
						ui.cargoInfoPanel.getBtnPacking().setEnabled(false);
					}
					
				}
			}
		} catch(BaseInfoInvokeException e) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**e.getMessage())*/);
		} catch(BusinessException w){
			bean.setLoadLineName("");// 配载线路名称
			bean.setLoadLineCode("");// 配载线路编码
			bean.setLoadOrgCode("");// 配载部门编号
			bean.setLoadOrgName("");// 配载部门名称
			bean.setLastLoadOrgCode("");// 最终配载部门编号
			bean.setLastLoadOrgName("");// 最终配载部门名称
			bean.setPackageOrgCode("");// 代打木架部门编码
			bean.setPackingOrganizationName("");// 代打木架部门名称
			bean.setDoPacking("");// 是否可以打木架
			bean.setLastOutLoadOrgCode("");//最终配置外场
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				ui.pictureCargoInfoPanel.getBtnWood().setEnabled(false);
			}else{
				ui.cargoInfoPanel.getBtnWood().setEnabled(false);//代打木架按钮不可点击
				/**
				 * 将打包装按钮与打木架按钮设置同样
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-12-3下午14:51
				 */
				ui.cargoInfoPanel.getBtnPacking().setEnabled(false);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**e.getMessage())*/);
			}
			
			//throw w;
		}
	}
	
	/**
	 * 
	 * 设置空运配载部门
	 * @author 025000-FOSS-helong
	 * @date 2013-1-22 上午08:35:03
	 */
	public void setAirDeptEnabled(WaybillPanelVo bean)
	{
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo
				.getCode())) {
			ui.canvasContentPanel.getCargoRoutePanel().getBtnQueryAirDept().setEnabled(true);
		}else
		{
			ui.canvasContentPanel.getCargoRoutePanel().getBtnQueryAirDept().setEnabled(false);
		}

	}
	
	/**
	 * 
	 * 设置AB货是否可以编辑
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午03:53:40
	 */
	/*private void setGoodsTypeAB(WaybillPanelVo bean)
	{
		if(bean.getGoodsTypeIsAB())
		{
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				ui.pictureCargoInfoPanel.getRbnA().setEnabled(true);
				ui.pictureCargoInfoPanel.getRbnB().setEnabled(true);
			}else{
			ui.cargoInfoPanel.getRbnA().setEnabled(true);
			ui.cargoInfoPanel.getRbnB().setEnabled(true);
			}
		}else
		{
			if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
					WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
				ui.pictureCargoInfoPanel.getRbnA().setEnabled(false);
				ui.pictureCargoInfoPanel.getRbnB().setEnabled(false);
			}else{
			ui.cargoInfoPanel.getRbnA().setEnabled(false);
			ui.cargoInfoPanel.getRbnB().setEnabled(false);
			}
		}
	}*/

	/**
	 * 显示提货网点查询窗口
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午07:54:48
	 */
	public BranchVo showPickupStationDialog(WaybillPanelVo bean) {
		QueryPickupPointDto dto = new QueryPickupPointDto();
		// 网点类型标志
		dto.setDestNetType(bean.getProductCode().getDestNetType());
		if(bean.getReceiveMethod()!=null){
			// 提货方式
			dto.setPickUpType(bean.getReceiveMethod().getValueCode());
		}		
		// 产品编码
		dto.setTransType(bean.getProductCode().getCode());
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) &&
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			// 目的站（PS：此处获取必须从UI中获取而不应从bean获得，因为ui获得的是新值，而bean获得的是旧值-在清空目的站时bean值未被清空）
			dto.setOrgSimpleName(ui.pictureTransferInfoPanel.getTxtDestination().getText());
		}else{
			// 目的站（PS：此处获取必须从UI中获取而不应从bean获得，因为ui获得的是新值，而bean获得的是旧值-在清空目的站时bean值未被清空）
			dto.setOrgSimpleName(ui.getTransferInfoPanel().getTxtDestination().getText());
		}		
		// 出发营业部
		dto.setReceiveOrgCode(bean.getReceiveOrgCode());
		// 设置来源为开单
		dto.setSource(WaybillConstants.WAYBILL);
		dto.setCurDate(new Date());

		// 创建弹出窗口
		PickupGoodsBranchDialog dialog = ui.getPickupGoodsBranchDialog();
		dialog = new PickupGoodsBranchDialog();
		dialog.setSize(NumberConstants.NUMBER_1000, NumberConstants.NUMBER_700);
		// 剧中显示弹出窗口
		ModalFrameUtil.getInstance().showAsModal(dialog, ApplicationContext.getApplication().getWorkbench().getFrame());
		// 获得选中提货网点对象
		BranchQueryVo branchQueryVo = dialog.getReturnBranchQueryVo();
		BranchVo branchVO = null;
		if(branchQueryVo != null && branchQueryVo.getCode() != null)
		{
			validate(bean,branchQueryVo);
			branchVO = getBranchVo(branchQueryVo);
			setDialogData(branchVO, bean);
		}
		return branchVO;
	}
	
	/**
	 * 验证网点是否有对应的产品类型以及提货方式
	 * @author 025000-FOSS-helong
	 * @date 2013-5-13
	 */
	private void validate(WaybillPanelVo bean,BranchQueryVo branchQueryVo)
	{
		//验证产品
		validateProduct(bean,branchQueryVo);
		//验证提货方式
		validatePickUp(bean,branchQueryVo);
	}
	
	
	/**
	 * 验证网点是否有对应的产品类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-5-14
	 */
	private void validateProduct(WaybillPanelVo bean,BranchQueryVo branchQueryVo)
	{
		ProductEntityVo productVo = bean.getProductCode();
		//精准卡航
		if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productVo.getCode()))
		{
			if(branchQueryVo.getChbFLF() == null || !branchQueryVo.getChbFLF())
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductLongFastFreight"));
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productVo.getCode())){//精准包裹
			if(branchQueryVo.getChbPCP() == null || !branchQueryVo.getChbPCP())
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductPCP"));
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productVo.getCode()))//精准城运
		{
			if(branchQueryVo.getChbFSF() == null || !branchQueryVo.getChbFSF())
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductShortFastFreight"));
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productVo.getCode()))//精准汽运(长途)
		{
			if(branchQueryVo.getChbLRF() == null || !branchQueryVo.getChbLRF())
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductLRF"));
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productVo.getCode()))//精准汽运(短途)
		{
			if(branchQueryVo.getChbSRF() == null || !branchQueryVo.getChbSRF())
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductSRF"));
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode()))//汽运偏线
		{
			if(!WaybillConstants.PX.equals(branchQueryVo.getBranchTypeSeach()))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductPLF"));
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))//精准空运
		{
			if(!WaybillConstants.KY.equals(branchQueryVo.getBranchTypeSeach()))
			{
				if(branchQueryVo.getChbAF() == null || !branchQueryVo.getChbAF())
				{
					throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductAF"));
				}
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productVo.getCode()))//整车
		{
			//导入整车时，如果到达部门为空，则需要选择，根据MANA-389修改
			//throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noProductWVH"));
		}
	}
	
	/**
	 * 验证网点是否有对应的提货方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-5-14
	 */
	private void validatePickUp(WaybillPanelVo bean,BranchQueryVo branchQueryVo)
	{
		String code = bean.getReceiveMethod().getValueCode();
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) 
				|| WaybillConstants.AIR_SELF_PICKUP.equals(code) 
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) 
				|| WaybillConstants.AIRPORT_PICKUP.equals(code) 
				|| WaybillConstants.INNER_PICKUP.equals(code)
				|| WaybillConstants.DELIVER_FREE.equals(code) 
				|| WaybillConstants.DELIVER_FREE_AIR.equals(code) 
				|| WaybillConstants.DELIVER_STORAGE.equals(code) 
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) 
		{
			if(branchQueryVo.getChbPickupUi() == null || !branchQueryVo.getChbPickupUi())
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noSupportSelfPickup"));
			}
		} else {
			if(branchQueryVo.getChbDeliverUi() == null || !branchQueryVo.getChbDeliverUi())
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.LoadPickupStationAction.exception.noSupportDelivery"));
			}
		}
	}
	
	/**
	 * 根据网点信息 组装BranchVo
	 * @author 097972-foss-dengtingting
	 * @date 2013-4-11 下午7:00:24
	 */
	private BranchVo getBranchVo(BranchQueryVo branchQueryVo){
		BranchVo vo = new BranchVo();
		//查专线信息
		if (WaybillConstants.ZX.equals(branchQueryVo.getBranchType())) {
			SaleDepartmentEntity saleDepartment = waybillService.querySaleDeptByCode(branchQueryVo.getCode());
				try {
					PropertyUtils.copyProperties(vo,saleDepartment);
				} catch (Exception e) {
					LOG.error("copyProperties异常", e);
				}
			vo.setCityCode(branchQueryVo.getCity());//所在城市CODE
			if(saleDepartment!=null){
				//是否可货到付款
				vo.setArriveCharge(saleDepartment.getCanCashOnDelivery());
			}		
		}else if (WaybillConstants.PX.equals(branchQueryVo.getBranchType()) 
				|| WaybillConstants.KY.equals(branchQueryVo.getBranchType())) {
			//查询代理信息
			OuterBranchEntity outerBranch = waybillService.queryAgencyBranchInfo(branchQueryVo.getCode());
			//代理编号
			vo.setCode(outerBranch.getAgentDeptCode());
			//代理名称
			vo.setName(outerBranch.getAgentDeptName());
			//目的站
			vo.setTargetOrgName(outerBranch.getSimplename());
			//是否代收货款
			vo.setCanAgentCollected(outerBranch.getHelpCharge());
			//是否可自提
			vo.setPickupSelf(outerBranch.getPickupSelf());
			//是否送货上门
			vo.setDelivery(outerBranch.getPickupToDoor());
			//所属城市code
			vo.setCityCode(outerBranch.getCityCode());
		}
		return vo;
	}
	
	/**
	 * 给相关控件赋值：设置提货网点、目的站、代收货款、其它费用、装卸费、送货进仓等
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午5:20:07
	 */
	public void setDialogData(BranchVo branchVO,WaybillPanelVo bean) {
		if (branchVO != null) {
			bean.setCustomerPickupOrgCode(branchVO);
			bean.setCustomerPickupOrgName(branchVO.getName());
			
			// 反写目的站:如果目的站不为空，则选择好网点后将其设置为部门简称
			BusinessUtils bu = new BusinessUtils();
			String simpleName = bu.getSimpleName(branchVO.getCode(),bean.getBillTime());
			if(!"".equals(simpleName)){
				bean.setTargetOrgCode(simpleName);
			}else{
				bean.setTargetOrgCode(branchVO.getTargetOrgName());
			}
			
			// 判断是否可以开代收货款
			canAgentCollected(branchVO.getCanAgentCollected(), bean);
			//判断是否可以货到付款
			canArriveCharge(branchVO, bean);
			// 清空其他费用列表
			Common.cleanOtherCharge(bean,ui);
			// 查询其他费用
			queryOtherChargeData(ui,bean);
			// 计算其他费用合计
			calculateOtherCharge( ui,bean);
			// 把装卸费清空
			bean.setServiceFee(BigDecimal.ZERO);
			
			//临时使用
			if(bean.getTargetOrgCode() == null || "".equals(bean.getTargetOrgCode()))
			{
				bean.setTargetOrgCode(i18n.get("foss.gui.creating.showPickupStationDialogAction.forShort.label"));
			}
		
		/**
		 * 根据提货网点所在标准城市或收货部门所在标准城市为香港，限制代收货款和装卸费的录入；
		 */
		    setCanAgentCollectedOrServiceFee(branchVO,bean);
			/**
			 * 在网点目的站基础资料中有
			 * '取消到达日期'，如果当前日期在'取消到达日期'之前，那么提示"xx营业部将于xx年xx月xx日临时取消到达，届时货物将转至xx营业部，请做好客户解释工作！"（其中第一个xx营业部，为当前营业部、第二个xx营业部为网点目的站基础资料中的'转货部门'，xx年xx月xx日为'取消到达日期'
			 * ）
			 */
		    if(branchVO.getCancelArrivalDate()!=null&&branchVO.getTransferGoodDept()!=null){
		    	/**
		    	 * 转货部门
		    	 */
		    	
		    	SaleDepartmentEntity saleDepartmentEntity=	WaybillServiceFactory.getWaybillService().querySaleDeptByCode(branchVO.getTransferGoodDept());
		    	
		    	
		    	/**
		    	 * 取消到达日期
		    	 */
		    	String cancelArrivalDate=DateUtils.getChDate(branchVO.getCancelArrivalDate());
		    	/**
		    	 * 转货部门
		    	 */
		    	if(saleDepartmentEntity!=null){
		    	String transferGoodDeptName=saleDepartmentEntity.getName();
		    	 String message=i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.CancelArrive", new Object[]{branchVO.getName(),cancelArrivalDate,transferGoodDeptName});
			     MsgBox.showInfo(message);
		    	}
		    }
		    //zxy 20131130 DEFECT-319 start 新增：设置是否可返回签单
		    canReturnSignBill(branchVO, bean);
		    //zxy 20131130 DEFECT-319 end 新增：设置是否可返回签单
		    
		    Common.setSaveAndSubmitFalse(ui);
		}
		
	}
	
	/**
	 * 
	 * 判断此网点是否可以开货到付款
	 * 
	 * @author WangQianJin
	 * @date 2013-10-31
	 */
	private void canArriveCharge(BranchVo branchVo, WaybillPanelVo bean) {		
		//是否可货到付款
		bean.setArriveCharge(branchVo.getArriveCharge());
	}

	/**
	 * 
	 * 根据提货网点所在标准城市或收货部门所在标准城市为香港，限制代收货款和装卸费的录入；
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-28 上午10:49:05
	 */
	private void setCanAgentCollectedOrServiceFee(BranchVo branchVO,WaybillPanelVo bean) {
		/**
		 * 获取目的站所在城市，如果是香港，设置代收代收货款和装卸费不可录入
		 */
		// 由于发货部门能取到省份，可以用省份来进行判断
		if ("810100".equals(branchVO.getCityCode())
				|| "810000".equals(bean.getReceiveOrgProvCode())) {
			setCanAgentCollectedOrServiceFeeFalse(bean);
		}
	}
	/**
	 * 
	 * 限制代收货款和装卸费的录入
	 * @param bean
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-28 上午11:08:46
	 */
	private void setCanAgentCollectedOrServiceFeeFalse(WaybillPanelVo bean)
	{
		bean.setCodAmountCanvas(BigDecimal.ZERO.toString());//收代收货款
		//bean.setCodAmount(BigDecimal.ZERO);//收代收货款
		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);//收代收货款不可修改
		bean.setServiceFeeCanvas(BigDecimal.ZERO.toString());//装卸费
		bean.setServiceFee(BigDecimal.ZERO);//装卸费
		ui.incrementPanel.getTxtServiceCharge().setEnabled(false);//装卸费不可修改
	}
	
	

	/**
	 * 
	 * 初始化其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午05:12:36
	 */
	private void calculateOtherCharge(WaybillEditUI ui,WaybillPanelVo bean) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();

		if(data != null && !data.isEmpty())
		{
			BigDecimal otherChargeSum = BigDecimal.ZERO;
			// 其他费用合计
			for (OtherChargeVo vo : data) {
				BigDecimal money = new BigDecimal(vo.getMoney());
				otherChargeSum = otherChargeSum.add(money);
			}
			//其他费用
			bean.setOtherFee(otherChargeSum);
			//画布其他费用
			bean.setOtherFeeCanvas(otherChargeSum.toString());
		}
	}

	/**
	 * 
	 * 查询其他费用
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:22:50
	 */
	private void queryOtherChargeData(WaybillEditUI ui,WaybillPanelVo bean) {
		List<ValueAddDto> list = waybillService
				.queryValueAddPriceList(getQueryOtherChargeParam(bean));
		
		List<OtherChargeVo> voList = getOtherChargeList(list);
		if(voList != null)
		{
			if(!voList.isEmpty())
			{
				ui.incrementPanel.setChangeDetail(otherChargeCompare(voList));
			}
		}
	}
	
	/**
	 * 
	 * 将原有其他费用与新查询出来其他费用进行比较，然后删除重复的项
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午03:03:01
	 */
	private List<OtherChargeVo> otherChargeCompare(List<OtherChargeVo> voList)
	{
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();
		if(data != null)
		{
			if(!data.isEmpty())
			{
				for(int i=0;i<voList.size();i++)
				{
					OtherChargeVo queryVo = voList.get(i);
					for(int j=0;j<data.size();j++)
					{
						OtherChargeVo tableVo = data.get(j);
						if(tableVo.getChargeName().equals(queryVo.getChargeName()))
						{
							data.remove(j);
							data.add(j,queryVo);
						}
					}
				}
				return data;
			}else
			{
				return voList;
			}
		}else
		{
			return voList;
		}
	}
	 

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPanelVo bean) {
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
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	private List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if(list != null)
		{
			for (ValueAddDto dto : list) {
				//开单的时候不能增加更改费
				if(PriceEntityConstants.PRICING_CODE_GGF.equals(dto.getSubType())){
					continue;
				}
				
				OtherChargeVo vo = new OtherChargeVo();
				if(dto.getCandelete() != null && !BooleanConvertYesOrNo.stringToBoolean(dto
						.getCandelete()))
				{
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
	 * 判断此网点是否可以开代收货款
	 * @author 025000-FOSS-helong
	 * @date 2013-1-7 下午04:07:04
	 */
	private void canAgentCollected(String canAgentCollected,WaybillPanelVo bean)
	{
		bean.setCanAgentCollected(canAgentCollected);
		if(FossConstants.YES.equals(canAgentCollected))
		{
			// 代收货款金额
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
			// 代收货款类型
			ui.incrementPanel.getCombRefundType().setEnabled(true);
		}else
		{
			// 代收货款金额
			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
			// 代收货款类型
			ui.incrementPanel.getCombRefundType().setEnabled(false);
			// 清理代收货款信息
			cleanCodInfo(ui,bean);
		}
	}
	
	/**
	 * 
	 * 清理代收货款信息
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午05:16:21
	 */
	private void cleanCodInfo(WaybillEditUI ui,WaybillPanelVo bean)
	{
		//清理银行信息
		Common.cleanBankInfo(bean);	
		// 代收货款金额
		bean.setCodAmount(BigDecimal.ZERO);
		// 代收货款费率
		bean.setCodRate(BigDecimal.ZERO);
		// 代收货款手续费
		bean.setCodFee(BigDecimal.ZERO);
		//代收货款ID
		bean.setCodId("");
		//代收货款编码
		bean.setCodCode("");
		// 画布-代收货款金额
		bean.setCodAmountCanvas(BigDecimal.ZERO.toString());
		//将退款类型设置为空
		Common.setRefundType(bean,ui);
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
			effectiveDto = waybillService.searchPreSelfPickupTime(bean.getReceiveOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getSelfPickupTime();
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		} else {
			effectiveDto = waybillService.searchPreDeliveryTime(bean.getReceiveOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(), new Date());
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
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code) || WaybillConstants.AIR_PICKUP_FREE.equals(code)
				|| WaybillConstants.AIRPORT_PICKUP.equals(code))

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
		Date leaveTime = waybillService.searchPreLeaveTime(bean.getReceiveOrgCode(), bean.getLoadOrgCode(), bean.getProductCode().getCode(), new Date());
		return leaveTime;
	}
	
	/**
	 * 功能：set ui
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
		
	}
	
	/**
	 * 设置返单类别是否可编辑 DEFECT-319
	 * @author 157229-zxy 
	 * @date 2013-11-30 
	 */
	private void canReturnSignBill(BranchVo branchVo, WaybillPanelVo bean) {		
		if(FossConstants.YES.equals(branchVo.getCanReturnSignBill())){			
			ui.incrementPanel.getCombReturnBillType().setEnabled(true);
		}else{
			bean.getReturnBillType().setValueCode(WaybillConstants.NOT_RETURN_BILL);
			ui.incrementPanel.getCombReturnBillType().setEnabled(false);
		}		
	}
	
	
}