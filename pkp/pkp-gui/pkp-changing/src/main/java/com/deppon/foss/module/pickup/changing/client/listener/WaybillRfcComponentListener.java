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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/listener/WaybillRfcComponentListener.java
 * 
 * FILE NAME        	: WaybillRfcComponentListener.java
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
package com.deppon.foss.module.pickup.changing.client.listener;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.internal.GoodsPanel;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.ComboCheckBoxEntry;
import com.deppon.foss.module.pickup.common.client.ui.combocheckbox.JComboCheckBox;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 
 * 更改单数据初始化事件
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午6:07:05
 */
public class WaybillRfcComponentListener implements HierarchyListener {
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(WaybillRfcComponentListener.class); 
	// 是否初始化完毕
	private boolean isInit;

	// UI
	private WaybillRFCUI waybillRFCUI;
	
	//更改单Service
	private IWaybillRfcService waybillRfcService = WaybillRfcServiceFactory.getWaybillRfcService();
	
	//日志类
	private static Logger logger = Logger.getLogger(WaybillRfcComponentListener.class);

	/**
	 * 
	 * 传递更改单UI
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-18 下午3:51:53
	 */
	public WaybillRfcComponentListener(WaybillRFCUI waybillRFCUI) {
		this.waybillRFCUI = waybillRFCUI;
	}

	/**
	 * 
	 * 产品类型下拉框
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-31 下午6:29:11
	 */
	private void initCombProductType() {
		//登陆部门
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		//查询产品
		List<ProductEntity> list = waybillRfcService.queryTransType(dept.getCode());
		//下拉框模型
		DefaultComboBoxModel productTypeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getProductTypeModel();
		for (ProductEntity dataDictionary : list) {
			
			
			ProductEntityVo vo = new ProductEntityVo();
			if(CommonUtils.directDetermineIsExpressByProductCode(dataDictionary.getCode())){
				continue;
			}
			try {
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			productTypeModel.addElement(vo);
		}
	}
	
	/**
	 * 
	 * <p>
	 * (初始化行业货源品类)
	 * </p>
	 * 
	 * @author zhangchengfu
	 * @date 2015-6-5
	 * @see
	 */
	private void initTxtIndustrySourceCategory() {
		List<DataDictionaryValueEntity> list =  waybillRfcService.queryIndustrySourceCategory4Dict();
		DefaultComboBoxModel industrySourceCategoryModel = waybillRFCUI.getWaybillInfoPanel().getGoodsPanel().getIndustrySourceCategoryModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			industrySourceCategoryModel.addElement(vo);
		}
	}
	
	/**
	 * 
	 * <p>
	 * (初始化客户分群)
	 * </p>
	 * 
	 * @author zhangchengfu
	 * @date 2015-6-5
	 * @see
	 */
	private void initCombFlabelleavemonth() {
		List<DataDictionaryValueEntity> list =  waybillRfcService.queryFlabelleavemonth4Dict();
		DefaultComboBoxModel flabelleavemonthModel = waybillRFCUI.getConsignerPanel().getFlabelleavemonthModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			flabelleavemonthModel.addElement(vo);
		}
	}

	/**
	 * 
	 * 提货方式下拉框
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-16 下午06:27:17
	 * @see
	 */
	private void initCombPickMode() {
		List<DataDictionaryValueEntity> list = waybillRfcService.queryPickUpGoodsHighWays();
		//下拉框模型
		DefaultComboBoxModel pikcModeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPickModeModel();
		//下拉框模型
		DefaultComboBoxModel transferPikcModeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getPickModeModel();
		//下拉框模型
		DefaultComboBoxModel returnPikcModeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getPickModeModel();
		//下拉框模型
		DefaultComboBoxModel transferRecordPikcModeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferInfoPanel().getPickModeModel();
		transferRecordPikcModeModel.addElement(new DataDictionaryValueVo());
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			pikcModeModel.addElement(vo);
			//客户原因不能选择内部带货、免费自提 
			if (!WaybillConstants.INNER_PICKUP.equals(vo.getValueCode()) 
					&& !WaybillConstants.AIR_PICKUP_FREE.equals(vo.getValueCode()) ) {
				transferPikcModeModel.addElement(vo);
				returnPikcModeModel.addElement(vo);
			}
			transferRecordPikcModeModel.addElement(vo);
		}
	}
	
	/**
	 * 
	 * 初始化对外备注
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	private void initCombOutSideRemark() {
		//查询对外备注
		List<DataDictionaryValueEntity> list = waybillRfcService.queryOuterRemark();
		JComboCheckBox checkbox = (JComboCheckBox)waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombOutSideRemark();
		//初始化下拉框模型
		DefaultComboBoxModel combOutSideRemarkModel = new DefaultComboBoxModel();
		checkbox.setModel(combOutSideRemarkModel);
		for (DataDictionaryValueEntity dataDictionary : list) {
			//构建复选框节点
			ComboCheckBoxEntry entry = new ComboCheckBoxEntry(dataDictionary.getValueCode(),
					dataDictionary.getValueName());
			checkbox.addItem(entry);
		}
		//添加Item变化事件
		checkbox.getRender().addItemListener(new CheckBoxListMouseHandler(waybillRFCUI.getWaybillInfoPanel().getGoodsPanel()));
	
	}
	
	/**
     * CheckBoxListMouseHandler
     * @author shixw
     *
     */
    protected class CheckBoxListMouseHandler implements ItemListener {
    	
    	/**
    	 * panel
    	 */
		private GoodsPanel cargoInfoPanel;
    	
    	/**
    	 * constructor
    	 * @param cargoInfoPanel
    	 */
    	public CheckBoxListMouseHandler(GoodsPanel cargoInfoPanel){
    		this. cargoInfoPanel = cargoInfoPanel;
    	}
    	
    	/**
    	 * itemStateChanged
    	 * @param e
    	 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			StringBuffer sb = new StringBuffer("");
			//打木架
			String woodTxt= "";
			
			//打木箱
			String sandTxt= "";
			//打木托
			String salverTxt = "";
			//在原有内容上添加新内容
			String originalTxt = cargoInfoPanel.getTxtTransportationRemark().getText();
			if(originalTxt==null){
				originalTxt = "";
			}
			
			String[] remark = originalTxt.split(";");
			for(int i=0;i<remark.length;i++){
				String oldData = remark[i];
				//如果有木架信息，标记出
				if(oldData.indexOf(StringUtil.defaultIfNull(i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"))) != -1){
					woodTxt = oldData;
				}
				//如果有木箱信息，标记出
				if(oldData.indexOf(StringUtil.defaultIfNull(i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"))) != -1){
					sandTxt = oldData;
				}
				//zxy 20131118 ISSUE-4391 start 新增：设置打木托到备注中
				//如果有木托信息，标记出
				if(oldData.indexOf(StringUtil.defaultIfNull(i18n.get("foss.gui.changing.woodYokeEnterAction.salverGoods"))) != -1){
					salverTxt = oldData;
				}
				//zxy 20131118 ISSUE-4391 end 新增：设置打木托到备注中
			}
			
			WaybillPanelVo vo = waybillRFCUI.getBinderWaybill();
			
			/* BUG-57555 贵重物品 显示的优先级最高  */
	        if(waybillRFCUI.getWaybillInfoPanel().getGoodsPanel().getChbValuable().isSelected()){
	        	sb.append(i18n.get("foss.gui.changing.waybillInfoBindingListener.goodsPanel.chbValuable")).append("; ");
	        }
	        
	        /**
        	 * 返单类别
        	 */
	        if(vo.getReturnBillType()!=null){
	        	String returnBillType =vo.getReturnBillType().getValueName();
	        	if(returnBillType!=null
	        			&& !"".equals(returnBillType)
	        			&& !WaybillConstants.NOT_RETURN_BILL.equals(vo.getReturnBillType().getValueCode())){
	        		sb.append(returnBillType).append(";");
	        	} 
	        } 

			//原先内容中没有木架信息
        	if(StringUtils.isNotEmpty(woodTxt)){
        		sb.append(woodTxt).append("; ");
        	}
        	//原先内容中没有木箱信息
        	if(StringUtils.isNotEmpty(sandTxt)){
        		sb.append(sandTxt).append("; ");
        	}
        	//原先内容中没有木托信息
        	if(StringUtils.isNotEmpty(salverTxt)){
        		sb.append(salverTxt).append("; ");
        	}
        	
        	DataDictionaryValueVo dv = vo.getOuterNotes();
        	if(dv == null){
        		dv = new DataDictionaryValueVo();
        	}
			/**
			 * 对外备注
			 */
	        JComboCheckBox checkbox = (JComboCheckBox) (JComboCheckBox)waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombOutSideRemark();
	        for (String string : checkbox.getCheckedValues()) {
				sb.append(string).append("; ");
			}
	        dv.setValueCode(sb.toString());
        	vo.setOuterNotes(dv);
	        	
	        /**
	         * 对内备注
	         */
	        String innerSiderRemark = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getTxtInsideRemark().getText();
	        if(innerSiderRemark!=null && !"".equals(innerSiderRemark) ){
	        	sb.append(innerSiderRemark).append("; ");
	        }
	        	
	        /**
	         * 大车直送
	         */
	        if(cargoInfoPanel.getChbCarThrough().isSelected()){
	        	sb.append(i18n.get("foss.gui.changing.waybillInfoBindingListener.chbCarThrough.laber")).append("; ");
	        }	                
	        
	        //自提件类型
        	DataDictionaryValueVo economyType=vo.getEconomyGoodsType();      	
        	if (economyType!=null && economyType.getValueName()!=null) {    			
        		sb.append(economyType.getValueName()+ ";");
    		}
		
        	waybillRFCUI.getBinderWaybill().setTransportationRemark(sb.toString());
		}
    }
	

	/**
	 * 
	 * 初始化退款类型
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-16 下午06:27:17
	 * @see
	 */
	private void initCombRefundType() {
		//查询退款类型
		List<DataDictionaryValueEntity> list = waybillRfcService.queryRefundType();
		//下拉框模型
		DefaultComboBoxModel combRefundTypeModel = waybillRFCUI.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel();
		combRefundTypeModel.addElement(null);
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			combRefundTypeModel.addElement(vo);
		}
	}

	/**
	 * 
	 * 初始化返单类型
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-16 下午06:27:17
	 * @see
	 */
	private void initCombReturnBillType() {
		List<DataDictionaryValueEntity> list = waybillRfcService.queryReturnBillType();
		//下拉框模型
		DefaultComboBoxModel combReturnBillTypeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getReturnBillTypeModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			combReturnBillTypeModel.addElement(vo);
		}
	}

	/**
	 * 
	 * 开单付款方式
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-16 下午06:27:17
	 * @see
	 */
	private void initCombPaymentMode() {
			List<DataDictionaryValueEntity> list = waybillRfcService.queryPaymentMode();
			//下拉框模型
		DefaultComboBoxModel combPaymentModeModel = waybillRFCUI.getWaybillInfoPanel().getBillingPayPanel().getPaymentModeModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			if(!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary.getValueCode())
					&& !WaybillConstants.CHECK.equals(dataDictionary.getValueCode())){
				//合伙人不加载网上支付 2016年3月9日 11:22:37 葛亮亮
				if(!(BZPartnersJudge.IS_PARTENER 
					 && WaybillConstants.ONLINE_PAYMENT.equals(dataDictionary.getValueCode()))){
					//Entity转换为VO
					DataDictionaryValueVo vo = entityToVo(dataDictionary);
					//填充至 数据模型
					combPaymentModeModel.addElement(vo);
				}
			}
		}
	}
	/**
	 * 内部发货类型
	 */
	private void initComDeliveryMode() {
		List<DataDictionaryValueEntity> list = waybillRfcService.queryDeliveryMode();
				
		// 下拉框模型
		DefaultComboBoxModel combDeliveryModeModel = waybillRFCUI
				.getWaybillInfoPanel().getBasicPanel().getInternalDeliveryTypeModel();
		DataDictionaryValueVo nullVo = new DataDictionaryValueVo();
		nullVo.setValueName("");
		combDeliveryModeModel.addElement(nullVo);
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			combDeliveryModeModel.addElement(vo);
		}
	}
	/**
	 * 初始化特殊增值服务类型
	 */
	private void initSpecialValueAddedServiceTypeMode(){
		List<DataDictionaryValueEntity> list =waybillRfcService.querySpecialValueAddedServiceMode();
		
		//下拉框模式
		DefaultComboBoxModel specialValueAddedServiceTypeMode = waybillRFCUI
				.getWaybillInfoPanel().getBasicPanel().getSpecialValueAddedServiceTypeModel();
		DataDictionaryValueVo nullVo = new DataDictionaryValueVo();
		nullVo.setValueName("");
		specialValueAddedServiceTypeMode.addElement(nullVo);
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			specialValueAddedServiceTypeMode.addElement(vo);
		}
	}
	/**
	 * 
	 * 预配航班
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:55:55
	 */
	private void initCombPredictFlight() {
		//查询预配航班
		List<DataDictionaryValueEntity> list = waybillRfcService.queryPredictFlight();
		//下拉框模型
		DefaultComboBoxModel combFlightModeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getPredictFlightModel();
		//下拉框模型
		DefaultComboBoxModel combTransferFlightModeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getPredictFlightModel();
		//下拉框模型
		DefaultComboBoxModel combTransferRecordFlightModeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferInfoPanel().getPredictFlightModel();
		combFlightModeModel.addElement(new DataDictionaryValueVo());
		combTransferFlightModeModel.addElement(new DataDictionaryValueVo());
		combTransferRecordFlightModeModel.addElement(new DataDictionaryValueVo());
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			combFlightModeModel.addElement(vo);
			combTransferFlightModeModel.addElement(vo);
			combTransferRecordFlightModeModel.addElement(vo);
		}

	}

	/**
	 * 
	 * 计费类型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:56:26
	 */
	private void initCombBillingType() {
		//查询计费类型
		List<DataDictionaryValueEntity> list = waybillRfcService.queryBillingWay();
		//下拉框模型
		DefaultComboBoxModel combBillingType = waybillRFCUI.getDetailCanvasPanel().getExtendPanel().getBillingTypeModel();
		//下拉框模型
		DefaultComboBoxModel transferCombBillingType = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getTransferBillingTypeModel();
		//下拉框模型
		DefaultComboBoxModel returnCombBillingType = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getReturnBillingTypeModel();
		//下拉框模型
		DefaultComboBoxModel transferRecordCombBillingType = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferInfoPanel().getTransferBillingTypeModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			combBillingType.addElement(vo);
			transferCombBillingType.addElement(vo);
			returnCombBillingType.addElement(vo);
			transferRecordCombBillingType.addElement(vo);
		}
		if(transferCombBillingType.getSize()>0){
			transferCombBillingType.setSelectedItem(transferCombBillingType.getElementAt(0));
		}
		if(returnCombBillingType.getSize()>0){
			returnCombBillingType.setSelectedItem(returnCombBillingType.getElementAt(0));
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
			//属性拷贝
			BeanUtils.copyProperties(vo, dataDictionary);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		return vo;
	}

	/**
	 * 
	 * 货物状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:56:13
	 */
	private void initCombGoodsStatus() {
		//查询货物状态
		List<DataDictionaryValueEntity> list = waybillRfcService.queryGoodsStatus();
		//下拉框模型
		DefaultComboBoxModel goodsStatusModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getGoodsStatusModel();
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			goodsStatusModel.addElement(vo);
		}

	}
	

	
	/**
	 * 
	 * 初始化空运货物类型
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 下午04:29:41
	 */
	private void initCombGoodsType() {
		//下拉框模型
		DefaultComboBoxModel combGoodsType = waybillRFCUI.getWaybillInfoPanel().getGoodsPanel().getCombGoodsTypeModel();
		//获取空运货物类型
		List<DataDictionaryValueEntity> list = waybillRfcService.queryAirGoodsType();
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			combGoodsType.addElement(vo);
		}
	}
	
	/**
	 * 
	 * <p>
	 * (初始化合票方式)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午06:27:17
	 * @see
	 */
	private void initFreightMethod() {
		//下拉框模型
		DefaultComboBoxModel combFreightMethodModel =waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getCombFreightMethodModel();
		//查询合票方式数据
		List<DataDictionaryValueEntity> list = waybillRfcService
				.queryFreightMethod();
		for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			combFreightMethodModel.addElement(vo);
		}
		
	}

	/**
	 * 
	 * 作废人、中止人
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-17 下午4:43:16
	 */
	private void initUserInfo() {
		//用户名
		String userName = ((UserEntity)SessionContext.getCurrentUser()).getEmployee().getEmpName();
		Date abortTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//设置中止操作人
		waybillRFCUI.getMessagePanel().getInvalidInfoPanel().getLblOperateUser().setText(userName);
		//设置中止操作人
		waybillRFCUI.getMessagePanel().getInvalidInfoPanel().getLblOperateTime().setText(sf.format(abortTime));
		//设置中止操作人
		waybillRFCUI.getMessagePanel().getAbortInfoPanel().getLblOperateUser().setText(userName);
		//设置中止操作人
		waybillRFCUI.getMessagePanel().getAbortInfoPanel().getLblOperateTime().setText(sf.format(abortTime));
	}
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 客户变更原因初始化
	 */
    private void initRfcCusReason() {
    	//变更原因model
    	DefaultComboBoxModel rfcCusReasonComboBoxModel =waybillRFCUI.getImportPanel().getRfcCusReasonComboBoxModel();
    	DataDictionaryValueVo nullVo = new DataDictionaryValueVo();
		nullVo.setValueName("");
		rfcCusReasonComboBoxModel.addElement(nullVo);
    	//查询客户变更原因
    	List<DataDictionaryValueEntity> list = waybillRfcService
    					.queryRfcCusReason();
    	for (DataDictionaryValueEntity dataDictionary : list) {
			//Entity转换为VO
			DataDictionaryValueVo vo = entityToVo(dataDictionary);
			//填充至 数据模型
			rfcCusReasonComboBoxModel.addElement(vo);
		}
    }
	@Override
	public void hierarchyChanged(HierarchyEvent e) {
		if ((HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0 && e.getComponent().isShowing()) {
			//此方法会执行多次，需要判断
			if (!isInit) {
				// 产品类型
				initCombProductType();
				// 提货方式
				initCombPickMode();
				//行业货源品类
				initTxtIndustrySourceCategory();
				//客户分群
				initCombFlabelleavemonth();
				// 退款类型
				initCombRefundType();
				// 对外备注
				initCombOutSideRemark();
				// 返单类型
				initCombReturnBillType();
				// 开单付款方式
				initCombPaymentMode();
				// 预配航班
				initCombPredictFlight();
				// 计费类型
				initCombBillingType();
				//货物状态
				initCombGoodsStatus();
				//作废人、中止人
				initUserInfo();
				// 空运货物类型
				initCombGoodsType();
				//合票方式
				initFreightMethod();
				//内部发货类型
				initComDeliveryMode();
				//特殊增值服务
				initSpecialValueAddedServiceTypeMode();
				//变更原因初始化
				initRfcCusReason();
				// 初始化完毕
				isInit = true;
			}
		}
	}

}