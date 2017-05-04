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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/WaybillRFCUI.java
 * 
 * FILE NAME        	: WaybillRFCUI.java
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
package com.deppon.foss.module.pickup.changing.client.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.focuspolicy.factory.FocusPolicyFactory;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.changing.client.listener.CompareBeforeAfterValueBindingListener;
import com.deppon.foss.module.pickup.changing.client.listener.WaybillInfoBindingListener;
import com.deppon.foss.module.pickup.changing.client.listener.WaybillRfcComponentListener;
import com.deppon.foss.module.pickup.changing.client.listener.WaybillRfcValidationListener;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.controller.IWaybillRfcState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.WaybillRfcStateContext;
import com.deppon.foss.module.pickup.changing.client.ui.internal.BasicPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.BillingPayPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.ButtonPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.CanvasPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.ConsigneePanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.ConsignerPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.DetailCanvasPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.GoodsPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.ImportPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.IncrementPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changing.client.ui.internal.MessagePanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.UploadVoucherPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.WaybillInfoPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.transport.TransferChangedInfoPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.transport.TransferChangedRecordPanel;
import com.deppon.foss.module.pickup.changing.client.validation.descriptor.WaybillInfoDescriptor;
import com.deppon.foss.module.pickup.changing.client.vo.TransportRecordVo;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.ui.QueryPublishPriceUI;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
/**
 * 
 * 运单变更UI
 * 
 * 需要迁移的类有
 * CalculateAction.java
 * DeleteOtherChargeAction.java
 * QueryBankAccountAction.java
 * QuerySalesDepartmentAction.java
 * RfcInfoAction.java
 * WaybillInfoBindingListener.java
 * WaybillInfoDescriptor.java
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-3 上午8:56:13
 */
public class WaybillRFCUI extends JPanel {
	
	private static IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 730606596213006785L;

	/**
	 *  国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRFCUI.class);


	/**
	 * 日志类
	 */
	private static final Logger logger = Logger.getLogger(WaybillRFCUI.class);

	private static final int FOUR = 4;

	private static final int NINE = 9;
	
	/**
	 * 绑定对象
	 */
	private IBinder<WaybillInfoVo> binder;
	
	/**
	 * 基础面板
	 */
	private BasicPanel basicPanel;
	/**
	 * 导入面板
	 */
	private ImportPanel importPanel;

	/**
	 * 运单信息
	 */
	private WaybillInfoPanel waybillInfoPanel;

	/**
	 * 详细计价信息画布
	 */
	private DetailCanvasPanel detailCanvasPanel;

	/**
	 * 变更信息面板
	 */
	private MessagePanel messagePanel;

	/**
	 * 上传凭证面板
	 */
	private UploadVoucherPanel uploadVoucherPanel;

	/**
	 * 按钮面板
	 */
	private ButtonPanel buttonPanel;
	
	/**
	 * 原始导入运单
	 */
	private WaybillInfoVo originWaybill;

	/**
	 * UI绑定的运单
	 */
	private WaybillInfoVo binderWaybill;

	/**
	 * 绑定事件
	 */
	private WaybillInfoBindingListener bindingListener;
	
	/**
	 * 比较变更前后事件
	 */
	private CompareBeforeAfterValueBindingListener compareListener;
	
	/**
	 * 校验事件
	 */
	private WaybillRfcValidationListener validationListener;

	/**
	 * 更改单状态上下文
	 */
	private WaybillRfcStateContext stateContext;

	/**
	 * 增值业务信息米面板
	 */
	public IncrementPanel incrementPanel;
	
	public IncrementPanel getIncrementPanel() {
		return incrementPanel;
	}

	public void setIncrementPanel(IncrementPanel incrementPanel) {
		this.incrementPanel = incrementPanel;
	}

	/**
	 * 计费付款信息面板
	 */
	public BillingPayPanel billingPayPanel;
	
	/**
	 * 画布面板
	 */
	public CanvasPanel canvasPanel;

	/**
	 * 发货客户信息面板 
	 */
	public ConsignerPanel consignerPanel;

	/**
	 * 收货客户信息面板 
	 */
	public ConsigneePanel consigneePanel;
	
	// 计费类型
	private DefaultComboBoxModel combBillingType;
	
	//退款类型
	private DefaultComboBoxModel combRefundTypeModel;
	
	private DefaultComboBoxModel productTypeModel;// 运输性质
	
	private JComboBox specialValueAddedServiceType;//特殊增值服务model
	
	//营销活动
	private DefaultComboBoxModel activeInfoModel;	
	
	/**
	 * 货物信息面板
	 */
	public GoodsPanel cargoInfoPanel;
	
	/**
	 * 系统上线日期（服务器段获取）
	 */
	public static Date goliveDate;
	
	//标示单子 是 批量开单 -- sangwenhao
	private boolean isBatchWaybill = false;
	
	public boolean isBatchWaybill() {
		return isBatchWaybill;
	}

	public void setBatchWaybill(boolean isBatchWaybill) {
		this.isBatchWaybill = isBatchWaybill;
	}

	static{
		goliveDate = waybillService.queryFossGoliveDate();
	}
	
	private QueryPublishPriceUI queryPublishPriceUI;
	
	/**
	 * Create the panel.
	 */
	public WaybillRFCUI() {
		init();
		innerPanelToTop();
		bind();
		/**
		 * 验证是否集中开单并隐藏打包装按钮
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-8下午16:33
		 */
		setBtnPacking();
		createListener();
		updateComponent();
	}
	
	/**
	 * 验证是否集中开单并隐藏打包装按钮
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-12-8下午16:33
	 */
	private void setBtnPacking() {
		// TODO Auto-generated method stub
		if(binderWaybill.getPickupCentralized()!=null&&binderWaybill.getPickupCentralized()){
			this.waybillInfoPanel.getGoodsPanel().getPackingButton().setVisible(false);
		}
	}

	/**
	 * 
	 * 提取内部UI边界
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:49:29
	 */
	private void innerPanelToTop() {
		incrementPanel = waybillInfoPanel.getIncrementPanel();
		billingPayPanel = waybillInfoPanel.getBillingPayPanel();
		cargoInfoPanel = waybillInfoPanel.getGoodsPanel();
		consignerPanel = waybillInfoPanel.getConsignerPanel();
		consigneePanel = waybillInfoPanel.getConsigneePanel();
		combBillingType = detailCanvasPanel.getExtendPanel().getBillingTypeModel();
		combRefundTypeModel = incrementPanel.getRefundTypeModel();
		productTypeModel = waybillInfoPanel.getTransferPanel().getTransportInfoPanel().getProductTypeModel();
		activeInfoModel=incrementPanel.getActiveInfoModel();
		
		specialValueAddedServiceType = waybillInfoPanel.getBasicPanel().getSpecialValueAddedServiceType();
		
		waybillInfoPanel.getTransferPanel().getTransportInfoPanel().getCombPickMode();
		
	}



	/**
	 * 
	 * 界面元素布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:43:20
	 */
	private void init() {
		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("80dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{2, FOUR}});
		setLayout(formLayout);

		buttonPanel = new ButtonPanel();
		add(buttonPanel, "2, 2, 3, 1, fill, fill");
		
		importPanel = new ImportPanel();
		add(importPanel, "2, 4, 5, 1, fill, fill");

		waybillInfoPanel = new WaybillInfoPanel();
		add(waybillInfoPanel, "2, 6, 3, 1, fill, fill");

		detailCanvasPanel = new DetailCanvasPanel();
		add(detailCanvasPanel, "6, 6, fill, fill");

		messagePanel = new MessagePanel();
		add(messagePanel, "2, 8, fill, fill");

		uploadVoucherPanel = new UploadVoucherPanel();
		add(uploadVoucherPanel, "4, 8, fill, fill");

		stateContext = new WaybillRfcStateContext(this);
		stateContext.getCurrentRfcState().performLayout();
		queryPublishPriceUI = new QueryPublishPriceUI();
	}

	/**
	 * 
	 * UI与VO绑定
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:40:15
	 */
	private void bind() {
		//更改单焦点顺序移动绑定
		FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
		// 按钮绑定
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 控件绑定
		binder = BindingFactory.getIntsance().moderateBind(WaybillInfoVo.class, this, new WaybillInfoDescriptor(this), false);
		binderWaybill = binder.getBean();
		

		incrementPanel.setWaybillPanelVo(binderWaybill);
		incrementPanel.setUi(this);
	}

	/**
	 * 
	 * 创建校验、加载、属性变化监听器
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:42:15
	 */
	private void createListener() {
		// 校验出错处理方式
		validationListener = new WaybillRfcValidationListener();
		binder.addValidationListener(validationListener);

		// 运单信息变更事件
		bindingListener = new WaybillInfoBindingListener(this);
		binder.addBindingListener(bindingListener);
		
		// 显示变更前后变化事件
		compareListener = new CompareBeforeAfterValueBindingListener(this);
		binder.addBindingListener(compareListener);

		// 画面初始化完毕加载数据
		addHierarchyListener(new WaybillRfcComponentListener(this));
		

		final JCheckBox cboRfcWaybillNo = waybillInfoPanel.getBasicPanel().getCboRfcWaybillNo();
		final JTextFieldValidate txtRfcWaybillNo = waybillInfoPanel.getBasicPanel().getTxtRfcWaybillNo();
		cboRfcWaybillNo.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				if (cboRfcWaybillNo.isSelected()) {
					txtRfcWaybillNo.setEnabled(true);
				} else {
					binder.getBean().setNewWaybillNo(null);
					txtRfcWaybillNo.setEnabled(false);
				}
			}
		});
		
		addTransportRecordListener();
	}

	
	
	/**
	 * 
	 * 根据变更类型切换UI显示
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:44:40
	 */
	private void updateComponent() {
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				IWaybillRfcState pageState = stateContext.getCurrentRfcState();
				pageState.performLayout();
				pageState.performComponentsState();
			}
			
		});
		
	}
	
	/**
	 * 
	 * 根据变更类型切换UI显示,并清空变更类型、来源、原因
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:44:40
	 */
	public void updateComponentClear() {
		importWaybill(originWaybill, false);
	}

	
	/**
	 * 
	 * 导入运单
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:50:48
	 */
	public void importWaybill(WaybillInfoVo waybillInfoVo, boolean isNewImport) {
		if(waybillInfoVo != null){
			waybillInfoVo.setDeliveryCustomerCode(this.getBinderWaybill().getOldDeliveryCustomerCode());
			//清除绑定事件
			binder.removeBindingListener(bindingListener);
			waybillInfoPanel.getIncrementPanel().removeTableListener(compareListener);
			bindWaybill(waybillInfoVo, isNewImport);
			clearBuffer();
			setNoBindingData();
			//获取手动添加的其他费用
			getManualFeeList(waybillInfoVo);
			//获取导入时是否有空运运费冲减
			getImportKyyfcjFee(waybillInfoVo);
			//如果已经选中更改类型，界面允许发生变更
			updateComponent();
			//导入成功后重新绑定
			binder.addBindingListener(bindingListener);
			waybillInfoPanel.getIncrementPanel().addTableListener(compareListener);
			//当内部带货时，设置发货联系人和收货联系人为发货部门和收货部门
			setDepartmentName(waybillInfoVo);
			//设置查询网点是否可以编辑
			setSearchBranchCanEdit(waybillInfoVo);
			//设置是否自提件是否可编辑
			setEconomyGoodsTypeCanEdit(waybillInfoVo);
			if(!WaybillConstants.AIR_FLIGHT.equals(waybillInfoVo.getProductCode().getCode())){
				waybillInfoPanel.getGoodsPanel().getLblGoodsType().setVisible(false);
			}else{
				waybillInfoPanel.getGoodsPanel().getLblGoodsType().setVisible(true);
			}
			// ===========LianHe/设置客户名称不可编辑/2017年1月9日/start=======
			//设置客户名称不可编辑
			setConsigerNameFalse();
			// ===========LianHe/设置客户名称不可编辑/2017年1月9日/end=======
		}
	}
	
	/**
	 * LianHe/2017年1月9日
	 * 设置客户名称不可编辑
	 */
	private void setConsigerNameFalse() {
		//发货客户名称置为不可更改
		consignerPanel.getTxtConsigerName().setEditable(Boolean.FALSE);
		//收货客户名称置为不可更改
		consigneePanel.getTxtConsigeeName().setEditable(Boolean.FALSE);
	}

	/**
	 * 设置免费接货是否可编辑
	 * @param waybillInfoVo
	 */
	private void setFreePickToDoorEdit(WaybillInfoVo waybillInfoVo){
		DataDictionaryValueVo vo = waybillInfoVo.getFlabelleavemonth() ;
		if(waybillInfoVo.getPickupToDoor() && vo !=null ){
			if(WaybillConstants.VIP.equals(vo.getValueName())
				|| WaybillConstants.OMNI_ACTIVE.equals(vo.getValueName()) ){
				waybillInfoPanel.getBasicPanel().getCboFreePickupGoods().setEnabled(true);
			}else{
				waybillInfoVo.setFreePickupGoods(false);
				waybillInfoPanel.getBasicPanel().getCboFreePickupGoods().setEnabled(false);
			}
		}else{
			waybillInfoVo.setFreePickupGoods(false);
			waybillInfoPanel.getBasicPanel().getCboFreePickupGoods().setEnabled(false);
		}
		
	}
	
/**

	 * 设置是否自提件是否可编辑
	 * @author WangQianJin
	 * @date 2013-08-21
	 * @param waybillInfoVo
	 */
	private void setEconomyGoodsTypeCanEdit(WaybillInfoVo waybillInfoVo){
		if(waybillInfoVo.getIsEconomyGoods()!=null && waybillInfoVo.getIsEconomyGoods()){
			//如果开单时是自提件，则可以编辑			
			waybillInfoVo.setFlagEconomyEdit(FossConstants.YES);
		}else{
			//符合自提件要求并且未出第一外场才可以变更为自提件
			if(isEconomyRequire(waybillInfoVo) && isfirstOutfield(waybillInfoVo)){				
				waybillInfoVo.setFlagEconomyEdit(FossConstants.YES);
			}else{				
				waybillInfoVo.setFlagEconomyEdit(FossConstants.NO);
			}		
		}	
	}
	
	/**
	 * 是否符合自提件要求
	 * @return
	 */
	private boolean isEconomyRequire(WaybillInfoVo waybillInfoVo){
		boolean result=false;
		//订单号不能为空
		if(waybillInfoVo.getOrderNo()!=null && !"".equals(waybillInfoVo.getOrderNo())){
//			//根据开单日期获取自提件类型列表
//			List<MinFeePlanEntity> minFeeList=waybillService.getMinFeePlanEntityByDate(waybillInfoVo.getBillTime());		
//			if(minFeeList!=null && minFeeList.size()>0){				
//				//循环遍历筛选
//				for(MinFeePlanEntity entity:minFeeList){
//					if(entity!=null){	
//						//判断是否能做自提件
//						EconomyVo ecoVo=Common.getIsDefaultSelected(waybillInfoVo,entity);
//						//如果导入的订单符合经济自提件并且是非上门接货，则符合要求
//						if(ecoVo.isResult()){						
//							result=true;
//							break;
//						}
//					}
//				}				
//			}
			//如果是订单，那么是否自提件控件可编辑，勾选的时候有验证
			result=true;
		}
		return result;
	}
	
	/**
	 * 是否出了第一外场
	 * @return
	 */
	private boolean isfirstOutfield(WaybillInfoVo waybillInfoVo){
		boolean result=false;
		//否则，未出第一外场才可以发更改
		if(waybillInfoVo.getGoodsStatus()!=null && waybillInfoVo.getLoadOrgCode()!=null && waybillInfoVo.getStockStatus()!=null){
			String inventory = waybillInfoVo.getGoodsStatus().getValueCode();								
			if (WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)
					|| WaybillRfcConstants.RECEIVE_STOCK_OUT.equals(inventory)
					|| (WaybillRfcConstants.TRANSFER_STOCK.equals(inventory) 
				    && waybillInfoVo.getLoadOrgCode().equals(waybillInfoVo.getStockStatus().getCurrentStockOrgCode()))) {						
				result=true;
			}else{
				result=false;
			}
		}else{
			result=false;
		}
		return result;
	}
	
	/**
	 * 获取导入时是否有空运运费冲减
	 * @author WangQianJin
	 * @date 2013-08-13
	 */
	private void getImportKyyfcjFee(WaybillInfoVo waybillInfoVo){
		//导入时变更来源与变更类型为空
		if(waybillInfoVo.getRfcSource()==null && waybillInfoVo.getRfcType()==null){
			//原来的其他费用列表
			List<OtherChargeVo> oldOtherFeeList=binderWaybill.getOtherChargeVos();
			//其他费用中是否包含空运运费冲减
			boolean haveKyyfcj=CommoForFeeUtils.isResetCalTransportFee(oldOtherFeeList,waybillInfoVo);
			if(haveKyyfcj){
				//如果原来的其他费用中包含空运运费冲减，说明公布价中已包含空运运费冲减
				waybillInfoVo.setFalgKyyfcjFee(FossConstants.YES);
			}else{
				//否则说明公布价中未包含空运运费冲减
				waybillInfoVo.setFalgKyyfcjFee(FossConstants.NO);
			}
		}		
	}
	
	/**
	 * 获取手动添加的其他费用
	 * @author WangQianJin
	 * @date 2013-08-13
	 */
	private void getManualFeeList(WaybillInfoVo waybillInfoVo){
		//首次加载的的时候才获取手动添加的费用
		if(waybillInfoVo.getManualFeeList()==null || waybillInfoVo.getManualFeeList().size()==0){
			//手动添加的其他费用
			List<OtherChargeVo> manualFeeList=new ArrayList<OtherChargeVo>();
			//原来的其他费用列表
			List<OtherChargeVo> oldOtherFeeList=binderWaybill.getOtherChargeVos();
			//查询出的其他费用
			List<OtherChargeVo> queryOtherFeeList=queryOtherChargeData(waybillInfoVo);
			if(oldOtherFeeList != null && !oldOtherFeeList.isEmpty()){			
				for(int i=0;i<oldOtherFeeList.size();i++){
					OtherChargeVo oldVo = oldOtherFeeList.get(i);
					//flag用来表示原来的其他费用中有没有queryVo这个元素，false为没有
					boolean flag=false;
					//循环遍历查询出的其他费用，和原来的其他费用进行比较
					if(queryOtherFeeList != null && !queryOtherFeeList.isEmpty()){
						for(int j=0;j<queryOtherFeeList.size();j++){
							OtherChargeVo queryVo = queryOtherFeeList.get(j);
							if(oldVo.getCode().equals(queryVo.getCode())){
								//true表示原来的其他费用中已有此元素
								flag=true;
								break;
							}
						}
					}else{
						//如果查询出的其他费用为空，则将原来的其他费用赋给手动添加的其他费用
						manualFeeList=oldOtherFeeList;
						break;
					}				
					//如果没有此元素，则添加到allList里
					if(!flag){
						manualFeeList.add(oldVo);
					}
				}
			}
			//设置手动添加的其他费用到VO中
			waybillInfoVo.setManualFeeList(manualFeeList);
		}		
	}
	
	/**
	 * 查询其他费用
	 * @author WangQianJin
	 * @date 2013-8-13 上午9:42:54
	 */
	private List<OtherChargeVo> queryOtherChargeData(WaybillInfoVo bean) {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryOtherChargeParam(bean,false));
		if(list==null||list.isEmpty()){
			list = waybillService.queryValueAddPriceList(getQueryOtherChargeParam(bean,true));					
		}		
		List<OtherChargeVo> voList = getOtherChargeList(list);
		return voList;
	}
	
	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author WangQianJin
	 * @date 2013-08-13
	 */
	private List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if(list != null)
		{
			for (ValueAddDto dto : list) {
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
					vo.setMoney(dto.getCaculateFee().toString());
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
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPanelVo bean2,boolean isGetCurrentPrice) {
		WaybillInfoVo bean = (WaybillInfoVo)bean2;
		
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * 设置查询网点是否可以编辑
	 * @author WangQianJin
	 * @date 2013-07-14
	 */
	private void setSearchBranchCanEdit(WaybillInfoVo waybillInfoVo){
		if(waybillInfoVo.getRfcSource()==null || "".equals(waybillInfoVo.getRfcSource())){
			//变更来源为空时，设置查询网点不可编辑
			buttonPanel.getBtnSearchBranch().setEnabled(false);
		}else{
			/**
			 * 整车不可修改走货路径，因此查询网点按钮要禁用
			 */
			if(waybillInfoVo.getIsWholeVehicle()){
				buttonPanel.getBtnSearchBranch().setEnabled(false);
			}else{
				buttonPanel.getBtnSearchBranch().setEnabled(true);
			}
		}
		//设置运输信息目的站不可编辑，只能查询选择
		waybillInfoPanel.getTransferPanel().getTransportInfoPanel().getTxtDestination().setEnabled(false);
		//设置转货面板目的站不可编辑，只能查询选择
		waybillInfoPanel.getTransferPanel().getTransferTabPanel().getTransferInfoPanel().getTxtTransferDestination().setEnabled(false);
		//设置返货面板目的站不可编辑，只能查询选择
		waybillInfoPanel.getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getTxtReturnDestination().setEnabled(false);
	}
	
	/**
	 * 当内部带货时，设置发货联系人和收货联系人为发货部门和收货部门
	 * @author WangQianJin
	 * @date 2013-6-29 下午7:01:24
	 */
	private void setDepartmentName(WaybillInfoVo waybillInfoVo){
		// 判断是否内部带货自提,内部带货送货
		if (waybillInfoVo.getReceiveMethod()!=null &&(WaybillConstants.INNER_PICKUP.equals(waybillInfoVo.getReceiveMethod().getValueCode())
				||WaybillConstants.DELIVER_INNER_PICKUP.equals(waybillInfoVo.getReceiveMethod().getValueCode()))) {
			// 修改发货联人和收货联系人名称		
			consignerPanel.getLblConsignerLinkMan().setText(i18n.get("foss.gui.changing.waybillInfoBindingListener.consignerDept.label"));
			consignerPanel.getBtnConsignerDept().setVisible(true);
			consignerPanel.getTxtConsignerLinkMan().setEditable(false);
			consigneePanel.getLblConsigneeLinkMan().setText(i18n.get("foss.gui.changing.waybillInfoBindingListener.consigneeDept.label"));
			consigneePanel.getBtnConsigneeDept().setVisible(true);
			consigneePanel.getTxtConsigneeLinkMan().setEditable(false);
			waybillInfoVo.setReceiveMethodFlag(FossConstants.YES);
		}else{
			// 修改发货联人和收货联系人名称			
			consignerPanel.getLblConsignerLinkMan().setText(i18n.get("foss.gui.changing.waybillInfoBindingListener.consignerLinkMan.label"));
			consignerPanel.getBtnConsignerDept().setVisible(false);	
			consignerPanel.getTxtConsignerLinkMan().setEditable(true);
			consigneePanel.getLblConsigneeLinkMan().setText(i18n.get("foss.gui.changing.waybillInfoBindingListener.consigneeLinkMan.label"));
			consigneePanel.getBtnConsigneeDept().setVisible(false);	
			consignerPanel.getTxtConsignerLinkMan().setEditable(true);
			waybillInfoVo.setReceiveMethodFlag(FossConstants.NO);
		}
	}

	/**
	 * 
	 * 绑定导入的运单
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-16 下午3:24:12
	 */
	private void bindWaybill(WaybillInfoVo waybillInfoVo, boolean isNewImport) {
		this.originWaybill = waybillInfoVo;
		if (waybillInfoVo != null) {
			//表头数据回显
			waybillInfoVo.setImportWaybillNo(binderWaybill.getImportWaybillNo());
			if(!isNewImport){
				waybillInfoVo.setRfcSource(binderWaybill.getRfcSource());
				waybillInfoVo.setRfcType(binderWaybill.getRfcType());
				waybillInfoVo.setRfcReason(binderWaybill.getRfcReason());
			}else{
				DefaultComboBoxModel model = importPanel.getRfcTypeComboBoxModel();
				model.removeAllElements();
			}
			//如果路径可以打打木架则设置打木架按钮可点击
			if (FossConstants.YES.equals(waybillInfoVo.getDoPacking())) {
				cargoInfoPanel.getButton().setEnabled(true);
			}else{
				cargoInfoPanel.getButton().setEnabled(false);
			}			
			//VO-->UI绑定BUG，没法实现双向绑定
			try {
				/**
				 * 创建一个新的更改单VO，用于当修改属性值为空后再次导入时，重新为属性赋值，通过不为空验证。
				 */
				WaybillInfoVo newWaybillInfoVo=new WaybillInfoVo();	
				//获取基础接货费
				waybillInfoVo.setBasePickupFee(waybillInfoVo.getPickupFee());
				PropertyUtils.copyProperties(newWaybillInfoVo, waybillInfoVo);
				changePropertiesValue(newWaybillInfoVo);
				PropertyUtils.copyProperties(binderWaybill, newWaybillInfoVo);
				PropertyUtils.copyProperties(binderWaybill, waybillInfoVo);		
				if(waybillInfoVo.getOtherChargeVos()!=null){
					List<OtherChargeVo> destOtherChargeVos=new ArrayList<OtherChargeVo>();
					
					for(OtherChargeVo OtherChargeVo:waybillInfoVo.getOtherChargeVos()){
						OtherChargeVo destOtherChargeVo=new OtherChargeVo();
						destOtherChargeVo.setId(OtherChargeVo.getId());
						destOtherChargeVo.setChargeName(OtherChargeVo.getChargeName());
						destOtherChargeVo.setCode(OtherChargeVo.getCode());
						destOtherChargeVo.setDescrition(OtherChargeVo.getDescrition());
						destOtherChargeVo.setIsDelete(OtherChargeVo.getIsDelete());
						destOtherChargeVo.setIsInit(OtherChargeVo.getIsInit());
						destOtherChargeVo.setIsUpdate(OtherChargeVo.getIsUpdate());
						destOtherChargeVo.setLowerLimit(OtherChargeVo.getLowerLimit());
						destOtherChargeVo.setMoney(OtherChargeVo.getMoney());
						destOtherChargeVo.setType(OtherChargeVo.getType());
						destOtherChargeVo.setUpperLimit(OtherChargeVo.getUpperLimit());
						destOtherChargeVo.setIsEdit(OtherChargeVo.getIsEdit());
						destOtherChargeVo.setOldMoney(OtherChargeVo.getOldMoney());
						destOtherChargeVos.add(destOtherChargeVo);
					}
					
					List<LabeledGoodChangeHistoryDto> historList= new ArrayList<LabeledGoodChangeHistoryDto>();
					
					for(LabeledGoodChangeHistoryDto historyDto : waybillInfoVo.getLabeledGoodChangeHistoryDtoList()){
						LabeledGoodChangeHistoryDto des = new LabeledGoodChangeHistoryDto();
						PropertyUtils.copyProperties(des, historyDto);
						historList.add(des);
					}
					//SelectedLabeledGoodEntities 和   LabeledGoodChangeHistoryDtoList必须是同一个对象 两个引用
					//这样在后来的事件处理中会相当方便， 处理起来操作代码更加简洁 因为件数的改变和打木架数量的改变
					//最后都归结为修改一个list
					//这里用两个list是历史代码遗留的问题  但实际上 这两个引用必须指向一个对象 否则会出现bug
					// xiaowei 添加
					binderWaybill.setLabeledGoodChangeHistoryDtoList(historList);
					binderWaybill.setSelectedLabeledGoodEntities(historList);
					
					binderWaybill.setOtherChargeVos(destOtherChargeVos);
					
					
					if(this.originWaybill.getWaybillDto()!=null){
						 WaybillDto dto = new  WaybillDto();
						 PropertyUtils.copyProperties(dto, this.originWaybill.getWaybillDto());
						 binderWaybill.setWaybillDto(dto);
						 if (this.originWaybill.getWaybillDto().getWoodenRequirementsEntity() !=null  ){
							 WoodenRequirementsEntity newwoodRequirements = new WoodenRequirementsEntity();
							 PropertyUtils.copyProperties(newwoodRequirements, this.originWaybill.getWaybillDto().getWoodenRequirementsEntity());
							 binderWaybill.getWaybillDto().setWoodenRequirementsEntity(newwoodRequirements);
						 }
					}
					
				}
				
				//binderWaybill.setHandDeliveryFee(binderWaybill.getDeliveryGoodsFee());
				
				//是否为发货大客户标识
				if(FossConstants.ACTIVE.equals(binderWaybill.getDeliveryBigCustomer())){
					//设置大客户标记
					this.waybillInfoPanel.getConsignerPanel().getLblCustomerCode().setIcon(CommonUtils.createIcon(this.waybillInfoPanel.getConsignerPanel().getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
				}else{
					//取消大客户标记
					this.waybillInfoPanel.getConsignerPanel().getLblCustomerCode().setIcon(CommonUtils.createIcon(this.waybillInfoPanel.getConsignerPanel().getClass(), "", 1, 1));
				}
				//是否为收货大客户标识
				if(FossConstants.ACTIVE.equals(binderWaybill.getReceiveBigCustomer())){
					//设置大客户标记
					this.waybillInfoPanel.getConsigneePanel().getLblCustomerCode().setIcon(CommonUtils.createIcon(this.waybillInfoPanel.getConsigneePanel().getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
				}else{
					//取消大客户标记
					this.waybillInfoPanel.getConsigneePanel().getLblCustomerCode().setIcon(CommonUtils.createIcon(this.waybillInfoPanel.getConsigneePanel().getClass(), "", 1, 1));
				}
				if(null != waybillInfoVo && null != waybillInfoVo.getWaybillDto() && null != waybillInfoVo.getWaybillDto().getActualFreightEntity()){
					if(FossConstants.YES.equals(waybillInfoVo.getWaybillDto().getActualFreightEntity().getPartnerBillingLogo())){
						this.waybillInfoPanel.getBasicPanel().getPartnerCheckBox().setSelected(true);
						this.waybillInfoPanel.getBasicPanel().getPartnerName().setText(waybillInfoVo.getWaybillDto().getActualFreightEntity().getPartnerName());
						this.waybillInfoPanel.getBasicPanel().getPartnerPhone().setText(waybillInfoVo.getWaybillDto().getActualFreightEntity().getPartnerPhome());
						waybillInfoVo.setTempTransportFee(waybillInfoVo.getTransportFee());
					}
				}
			} catch (IllegalAccessException e) {
				logger.error("PropertyUtils copyProperties", e);
			} catch (InvocationTargetException e) {
				logger.error("PropertyUtils copyProperties", e);
			} catch (NoSuchMethodException e) {
				logger.error("PropertyUtils copyProperties", e);
			}
			
			
			
			
			
			if(logger.isDebugEnabled()){
				logger.debug(binderWaybill);
			}
		}
	}
	
	/**
	 * 变更对象里面属性的值(为了重新导入时重新验证是否为空)
	 * @author WangQianJin
	 * @date 2013-4-22 上午9:41:00
	 */
	private void changePropertiesValue(WaybillInfoVo newWaybillInfoVo){		
		//发货联系人
		if(newWaybillInfoVo.getDeliveryCustomerContact()!=null){
			newWaybillInfoVo.setDeliveryCustomerContact(newWaybillInfoVo.getDeliveryCustomerContact()+"1");
		}
		//收货联系人
		if(newWaybillInfoVo.getReceiveCustomerContact()!=null){
			newWaybillInfoVo.setReceiveCustomerContact(newWaybillInfoVo.getReceiveCustomerContact()+"1");
		}
		//货物名称
		if(newWaybillInfoVo.getGoodsName()!=null){
			newWaybillInfoVo.setGoodsName(newWaybillInfoVo.getGoodsName()+"1");
		}
		//总件数
		if(newWaybillInfoVo.getGoodsQtyTotal()!=null){
			newWaybillInfoVo.setGoodsQtyTotal(newWaybillInfoVo.getGoodsQtyTotal()+1);
		}
		//总重量
		if(newWaybillInfoVo.getGoodsWeightTotal()!=null){
			Double weight=1D;	
			weight=Double.valueOf(newWaybillInfoVo.getGoodsWeightTotal().toString())+weight;
			newWaybillInfoVo.setGoodsWeightTotal(new BigDecimal(weight));
		}
		//总体积
		if(newWaybillInfoVo.getGoodsVolumeTotal()!=null){
			Double volume=1D;
			volume=Double.valueOf(newWaybillInfoVo.getGoodsVolumeTotal().toString())+volume;
			newWaybillInfoVo.setGoodsVolumeTotal(new BigDecimal(volume));
		}
		//保价声明价值
		if(newWaybillInfoVo.getInsuranceAmount()!=null){
			Double insurance=1D;	
			insurance=Double.valueOf(newWaybillInfoVo.getInsuranceAmount().toString())+insurance;
			newWaybillInfoVo.setInsuranceAmount(new BigDecimal(insurance));
		}
		//代收货款
		if(newWaybillInfoVo.getCodAmount()!=null){
			Double cod=1D;
			cod=Double.valueOf(newWaybillInfoVo.getCodAmount().toString())+cod;
			newWaybillInfoVo.setCodAmount(new BigDecimal(cod));
		}
		//包装费
		if(newWaybillInfoVo.getPackageFee()!=null){
			Double packageFee=1D;
			packageFee=Double.valueOf(newWaybillInfoVo.getPackageFee().toString())+packageFee;
			newWaybillInfoVo.setPackageFee(new BigDecimal(packageFee));
		}
		//装卸费
		if(newWaybillInfoVo.getServiceFee()!=null){
			Double serviceFee=1D;
			serviceFee=Double.valueOf(newWaybillInfoVo.getServiceFee().toString())+serviceFee;
			newWaybillInfoVo.setServiceFee(new BigDecimal(serviceFee));
		}
		//到付金额
		if(newWaybillInfoVo.getToPayAmount()!=null){
			Double toPay=1D;
			toPay=Double.valueOf(newWaybillInfoVo.getToPayAmount().toString())+toPay;
			newWaybillInfoVo.setToPayAmount(new BigDecimal(toPay));
		}
		//预付金额
		if(newWaybillInfoVo.getPrePayAmount()!=null){
			Double prePay=1D;
			prePay=Double.valueOf(newWaybillInfoVo.getPrePayAmount().toString())+prePay;
			newWaybillInfoVo.setPrePayAmount(new BigDecimal(prePay));
		}
		//收货客户手机
		if(newWaybillInfoVo.getReceiveCustomerMobilephone()!=null){
			//获取收货客户手机号码
			String receiveMobile=newWaybillInfoVo.getReceiveCustomerMobilephone();
			String changeMobile="";
			/**
			 * 定义临时手机号码，在原来的手机号码最后一位加1，如果大于9，则设置为0
			 */
			if(receiveMobile.length()>0){
				String temp=receiveMobile.substring(receiveMobile.length()-1,receiveMobile.length());
				int intMobile=0;
				/**
				 * 判断temp是否为数字
				 */
				if (Character.isDigit(temp.charAt(0))) { 
					intMobile=Integer.parseInt(temp);
					intMobile=intMobile+1;
					if(intMobile > NINE){
						intMobile=0;
					}
				}				
				changeMobile=receiveMobile.substring(0,receiveMobile.length()-1)+intMobile;
			}	
			//重新设置收货客户手机号码，为了重新导入，需改变手机号码，且符合手机号码的规则
			newWaybillInfoVo.setReceiveCustomerMobilephone(changeMobile);			
		}
	}

	/**
	 * 
	 * 清空缓存数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:51:15
	 */
	private void clearBuffer() {
		// 清空变更详情，否则BindListener只有在鼠标失去焦点才触发，外围触发的变动不会
//		messagePanel.getChangedInfoPanel().setChangeDetail(new ArrayList<WaybillRfcChangeDetailDto>());
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				messagePanel.getInvalidInfoPanel().getLblOperateTime().setText("");
				messagePanel.getAbortInfoPanel().getLblOperateTime().setText("");
			}
		});
		compareListener.clearRfcChangeDetailMap();
	}
	
	/**
	 * 
	 * 转运、返货、附加费 表格数据填充（可以实现绑定该方法可废弃）
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-8 上午11:52:16
	 */
	private void setNoBindingData() {
		 List<TransportRecordVo> transferRecordVos = binderWaybill.getTransferRecordList();
		 waybillInfoPanel.getTransferPanel().getTransferTabPanel().getTransferRecordPanel().setRecordList(transferRecordVos);
		 List<TransportRecordVo> returnRecordVos = binderWaybill.getReturnRecordList();
		 waybillInfoPanel.getTransferPanel().getReturnTabPanel().getReturnRecordPanel().setRecordList(returnRecordVos);
		 List<TransportRecordVo> transportRecordVos = binderWaybill.getTransportRecordList();
		 waybillInfoPanel.getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().setRecordList(transportRecordVos);
		 if(transferRecordVos.size()> 0 || returnRecordVos.size()> 0){
			 waybillInfoPanel.getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().setRowSelectionInterval(0, 0);
		 }
		 
		 waybillInfoPanel.getIncrementPanel().setChangeDetailNoCalculate(binderWaybill.getOtherChargeVos());
		 //发货人地址、收货人地址
		 waybillInfoPanel.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(binderWaybill.getDeliveryCustomerAreaDto());
		 waybillInfoPanel.getConsigneePanel().getTxtConsigneeArea().setAddressFieldDto(binderWaybill.getReceiveCustomerAreaDto());

		 //是否整车
		 if(binderWaybill.getIsWholeVehicle() != null && binderWaybill.getIsWholeVehicle()){
				//开单报价
			 waybillInfoPanel.getBillingPayPanel().getBillingPayBelongPanel().getLblPublicCharge().setText(i18n.get("foss.gui.changing.billingPayBelongPanel.billAppfee.label")+"：");
				//整车面板显示
			 waybillInfoPanel.getBillingPayPanel().getBillingPayBelongPanel().getWholeVehiclePanel().setVisible(true);
		 }else{
			 waybillInfoPanel.getBillingPayPanel().getBillingPayBelongPanel().getLblPublicCharge().setText(i18n.get("foss.gui.changing.billingPayBelongPanel.publicCharge.label")+"：");
			//整车面板隐藏
			 waybillInfoPanel.getBillingPayPanel().getBillingPayBelongPanel().getWholeVehiclePanel().setVisible(false);
		 }

		 //空运货物类型
		ProductEntityVo productVo = binderWaybill.getProductCode();
		if (productVo != null) {
			boolean isAirFreight = ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
					.equals(productVo.getCode());
			//waybillInfoPanel.getGoodsPanel().getRdoA().setVisible(!isAirFreight);
			//waybillInfoPanel.getGoodsPanel().getRdoB().setVisible(!isAirFreight);
			waybillInfoPanel.getGoodsPanel().getCombGoodsType()
					.setVisible(isAirFreight);
		}

		binderWaybill.setOtherChargeChanged(false);
		/**
		 * 先根据特殊增值服务进行匹配，不然就根据运输性质进行匹配
		 * foss-254615-mabinliang
		 */
		if(originWaybill.getSpecialValueAddedServiceType()!=null){
		     bindingListener.specialValueAddedServiceTypeCodeChanged(binderWaybill.getSpecialValueAddedServiceType());
		}else{
		bindingListener.productCodeChanged(binderWaybill.getProductCode());
		}
					

		// 是否AB货
		/*if (binderWaybill.getGoodsTypeIsAB()!= null && binderWaybill.getGoodsTypeIsAB()) {
			getWaybillInfoPanel().getGoodsPanel().getRdoA().setEnabled(true);
			getWaybillInfoPanel().getGoodsPanel().getRdoB().setEnabled(true);
		} else {
			getWaybillInfoPanel().getGoodsPanel().getRdoA().setEnabled(false);
			getWaybillInfoPanel().getGoodsPanel().getRdoB().setEnabled(false);
			getWaybillInfoPanel().getGoodsPanel().getButtonGroup()
					.clearSelection();
		}*/
		
		// 汇总其他费用 把其他的值计算出来付给Listener
		List<OtherChargeVo> otherChargeVoKeeps = calculateOtherFeesTotalList();
		 
		 this.getCompareListener().setOtherChargeVoList(otherChargeVoKeeps);
		 
		 //根据是否历史运单显示“调整费用字段”
		 if(WaybillRFCUI.goliveDate != null && binderWaybill.getBillTime().before(WaybillRFCUI.goliveDate)){
			 //历史运单
				 billingPayPanel.fitComponent();
		 }else{
			 billingPayPanel.clearComponent();
			 
		 }
	}

	/**
	 * 汇总其他费用 把其他的值计算出来付给Listener
	 * 根据不同的code 把不同类型的code的其他金额都相加 汇总
	 * @return List<OtherChargeVo> 汇总 list
	 */
	private List<OtherChargeVo> calculateOtherFeesTotalList() {
		
		List<OtherChargeVo> otherChargeVos  = binderWaybill.getOtherChargeVos();
		if(otherChargeVos==null)
			return null;
		//汇总其他费用  计算每一种类型的其他费用的总金额
		 List<OtherChargeVo> otherChargeVoKeeps = new ArrayList<OtherChargeVo>();
		 
		//binder中的纪录
		 for (Iterator<OtherChargeVo> iterator = otherChargeVos.iterator(); iterator.hasNext();) {
			boolean hasVo = false;
			OtherChargeVo otherChargeVo = iterator.next();
			//新的汇总list遍历
			for(Iterator<OtherChargeVo> iterator2 = otherChargeVoKeeps.iterator(); iterator2.hasNext();){
				//汇总纪录
				OtherChargeVo otherChargeVo2 = iterator2.next();
				//如果code相通 
				if(otherChargeVo2.getCode().equals(otherChargeVo.getCode())){
					//这条记录已经在汇总中存在
					hasVo = true;
					double money2 = 0;
					double money1 = 0;
					//解析导入的历史金额
					try{
						money2=Double.parseDouble(otherChargeVo2.getMoney());
					}catch(Exception e){
						//exception
						logger.error("parsen money", e);
					}
					//解析binder金额
					try{
						money1=Double.parseDouble(otherChargeVo.getMoney());
					}catch(Exception e){
						//exception
						logger.error("parsen money", e);
					}
					//就把金额累计  计算这种code类型总金额
					otherChargeVo2.setMoney(Double.toString(money2+money1));
				}
			}
			//这条记录已经在汇总不存在
			if(!hasVo){
				//创建一条新的纪录 
				OtherChargeVo vo2 = new OtherChargeVo();
				try {
					//复制属性
					BeanUtils.copyProperties(vo2, otherChargeVo);
				} catch (Exception e) {
					//exception
					logger.error("BeanUtils copyProperties", e);
				} 
				//加入列表
				otherChargeVoKeeps.add(vo2);
			}
			
		}
		return otherChargeVoKeeps;
	}

    /**
     * 
     * 更新提交后数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-24 下午5:51:27
     */
	public void setCommited(WaybillRfcEntity rfcEntity) {
		stateContext.commit();
		binderWaybill.setWaybillRfcId(rfcEntity.getId());
		binderWaybill.setDarftTime(rfcEntity.getDraftTime());
		binderWaybill.setDarfter(rfcEntity.getDrafter());
		binderWaybill.setDarftOrgName(rfcEntity.getDraftOrgName());
		String darftTimeStr = DateUtils.getDateString(rfcEntity.getDraftTime(), "yyyy-MM-dd HH:mm:ss");
		messagePanel.getInvalidInfoPanel().getLblOperateTime().setText(darftTimeStr);
		messagePanel.getAbortInfoPanel().getLblOperateTime().setText(darftTimeStr);
	}
	
	
	/**
	 * 
	 * <p>
	 * 原始绑定的运单VO
	 * </p>
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-25 下午2:08:12
	 * @return
	 * @see
	 */
	public WaybillInfoVo getOriginWaybill() {
		return originWaybill;
	}
	
	/**
	 * 
	 * 绑定VO对象
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:51:46
	 */
	public WaybillInfoVo getBinderWaybill() {
		return binderWaybill;
	}

	/**
	 * 
	 * 变更明细面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:51:54
	 */
	public MessagePanel getMessagePanel() {
		return messagePanel;
	}

	/**
	 * 
	 * 详细画布
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:52:09
	 */
	public DetailCanvasPanel getDetailCanvasPanel() {
		return detailCanvasPanel;
	}

	/**
	 * 
	 * 导入面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:52:19
	 */
	public ImportPanel getImportPanel() {
		return importPanel;
	}

	/**
	 * 
	 * 按钮面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:52:29
	 */
	public ButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * 
	 * 运单面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:52:36
	 */
	public WaybillInfoPanel getWaybillInfoPanel() {
		return waybillInfoPanel;
	}
	
	/**
	 * 
	 * 上传凭证面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:52:49
	 */
	public UploadVoucherPanel getUploadVoucherPanel() {
		return uploadVoucherPanel;
	}
	
	/**
	 * 
	 * 绑定策略类
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:53:01
	 */
	public IBinder<WaybillInfoVo> getBinder() {
		return binder;
	}
	
	/**
	 * 
	 * 校验监听器
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:53:18
	 */
	public WaybillRfcValidationListener getValidationListener() {
		return validationListener;
	}

	/**
	 * 
	 * 绑定监听器
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:53:18
	 */
	public WaybillInfoBindingListener getBindingListener() {
		return bindingListener;
	}

	
	/**
	 * @return 收货人地址 .
	 */
	public JTextFieldValidate getTxtConsigneeAddress() {
		return this.getWaybillInfoPanel().getConsigneePanel().getTxtConsigneeAddress();
	}
	
	/**
	 * @return 发货人地址 .
	 */
	public JTextFieldValidate getTxtConsignerAddress() {
		return this.getWaybillInfoPanel().getConsignerPanel().getTxtConsignerAddress();
	}
	
	/**
	 * @return 收货区域 .
	 */
	public JAddressField getTxtConsigneeArea(){
		return this.getWaybillInfoPanel().getConsigneePanel().getTxtConsigneeArea();
	}
	
	/**
	 * @return 发货区域 .
	 */
	public JAddressField getTxtConsignerArea(){
		return this.getWaybillInfoPanel().getConsignerPanel().getTxtConsignerArea();
	}

	/**
	 * @return the compareListener
	 */
	public CompareBeforeAfterValueBindingListener getCompareListener() {
		return compareListener;
	}

	public DefaultComboBoxModel getCombBillingType() {
		return combBillingType;
	}

	public DefaultComboBoxModel getCombRefundTypeModel() {
		return combRefundTypeModel;
	}

	public DefaultComboBoxModel getProductTypeModel() {
		return productTypeModel;
	}
	
	public DefaultComboBoxModel getActiveInfoModel() {
		return activeInfoModel;
	}

	public void setActiveInfoModel(DefaultComboBoxModel activeInfoModel) {
		this.activeInfoModel = activeInfoModel;
	}

	/**
	 * 
	 * 设置产品到数据模型
	 * @author 025000-FOSS-helong
	 * @date 2013-1-16 下午02:18:34
	 */
	public void setProductTypeModel(String deptCode)
	{
		List<ProductEntity> list = waybillService.queryTransType(deptCode);
		for (ProductEntity product : list) {
			if(!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode()) && 
				!CommonUtils.directDetermineIsExpressByProductCode(product.getCode()))
			{
				ProductEntityVo vo = new ProductEntityVo();
				//将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo);
				vo.setDestNetType(product.getDestNetType());
				productTypeModel.addElement(vo);
			}
		}
	}

	/**
	 * 
	 * 设置运输性质默认值
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午02:43:29
	 */
	public void setProductCode(WaybillPanelVo bean) {
		ProductEntityVo productEntityVo = originWaybill.getProductCode();
		if(productEntityVo!= null){
			boolean hasOldProductCode = false;
			for (int i = 0; i < productTypeModel.getSize(); i++) {
				ProductEntityVo vo = (ProductEntityVo) productTypeModel.getElementAt(i);
				//默认设置为精准卡航
				if (productEntityVo.getCode().equals(vo.getCode())) {
					hasOldProductCode = true;
				}
			}
			//原有运输性质不存在，重新根据现有产品查询走货路径
			if(!hasOldProductCode){
				OrgInfoDto dto = waybillService.queryLodeDepartmentInfo(bean.getPickupCentralized(),bean.getReceiveOrgCode(), bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode());
				if (dto == null || dto.getFreightRoute() == null) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
				} else {
					bean.setLoadLineName(dto.getRouteLineName());// 配载线路名称
					bean.setLoadLineCode(dto.getFreightRoute().getVirtualCode());// 配载线路编码
					bean.setLoadOrgCode(dto.getFirstLoadOrgCode());// 配载部门编号
					bean.setLoadOrgName(dto.getFirstLoadOrgName());// 配载部门名称
					bean.setLastLoadOrgCode(dto.getLastLoadOrgCode());// 最终配载部门编号
					bean.setLastLoadOrgName(dto.getLastLoadOrgName());// 最终配载部门名称
					bean.setPackageOrgCode(dto.getFreightRoute().getPackingOrganizationCode());// 代打木架部门编码
					bean.setPackingOrganizationName(dto.getFreightRoute().getPackingOrganizationName());// 代打木架部门名称
					bean.setDoPacking(dto.getFreightRoute().getDoPacking());// 是否可以打木架
					bean.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());//最终配置外场
//					bean.setGoodsTypeIsAB(dto.getGoodsTypeIsAB());//是否AB货
					/*//设置AB货编辑状态
					if(bean.getGoodsTypeIsAB())
					{
						waybillInfoPanel.getGoodsPanel().getRdoA().setEnabled(true);
						waybillInfoPanel.getGoodsPanel().getRdoB().setEnabled(true);
					}else
					{
						waybillInfoPanel.getGoodsPanel().getRdoA().setEnabled(false);
						waybillInfoPanel.getGoodsPanel().getRdoB().setEnabled(false);
						waybillInfoPanel.getGoodsPanel().getButtonGroup().clearSelection();
					}*/
				}
			}else{
				bean.setProductCode(productEntityVo);
			}
		}
		
	}
	
	/**
	 * @return 收货联系人 .
	 */
	public JTextFieldValidate getTxtConsigneeLinkMan(){
		return this.consigneePanel.getTxtConsigneeLinkMan();
	}
	
	/**
	 * @return the consigneePanel
	 */
	public ConsigneePanel getConsigneePanel() {
		return consigneePanel;
	}
	
	/**
	 * @return 发货联系人 .
	 */
	public JTextFieldValidate getTxtConsignerLinkMan(){
		return this.consignerPanel.getTxtConsignerLinkMan();
	}
	
	/**
	 * @return the consignerPanel
	 */
	public ConsignerPanel getConsignerPanel() {
		return consignerPanel;
	}
	
	private void addSpecialValueAddedServiceTypeListener() {
		specialValueAddedServiceType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent evt) {
					Object obj = specialValueAddedServiceType.getSelectedItem();
					System.out.println("addSpecialValueAddedServiceTypeListener----intervalAdded----->obj.............."+obj);
				
			}
		});
	}
	
	/**
	 * 
	 * 变更记录选中后在“运输信息变更”页签修改
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-9 下午5:50:44
	 */
	private void addTransportRecordListener() {
		final TransferChangedRecordPanel transferRecordPanel = waybillInfoPanel.getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel();
		
		final JXTable table = transferRecordPanel.getTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//重置修改后的中转费
				resetTransportFee();
				int row = table.getSelectedRow();
				if(row >= 0){
					//reset data
					List<TransportRecordVo> tableData = transferRecordPanel.getTableModel().getData();
					TransportRecordVo recordVo = tableData.get(row);
					//填充运输性质，提货方式
					String startDeptCode = null;
					if(row == 0){
						//原始第一段
						startDeptCode = binderWaybill.getReceiveOrgCode();
					}else{
						//从getLoadLineCode中获取分段走货的起始营业部,ShowPickupStationDialogAction有设置
						String loadLineCode = binderWaybill.getTransferStartOrgCode();
						
						if(StringUtils.isNotEmpty(loadLineCode)){
							startDeptCode = loadLineCode;
						}else{
							startDeptCode = binderWaybill.getReceiveOrgCode();
						}
//						String[] lineArr = loadLineCode.split("%");
//						if(lineArr.length > 2*row){
//							startDeptCode = lineArr[2*row];
//						}else{
//						
//						}
						
					}
					TransferChangedInfoPanel transferPanel = waybillInfoPanel.getTransferPanel().getTransportRecordTabPanel().getTransferInfoPanel();
					//最后一段
					if(row == table.getRowCount()-1){
						String procuctCode = recordVo.getProductCode().getCode();
						if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(procuctCode)
								|| ProductEntityConstants.PRICING_PRODUCT_PCP.equals(procuctCode)) {
							//运输信息可变更
							UIUtils.disableUI(transferPanel);
							//运输性质
							transferPanel.getCombTransferProductType().setEnabled(true);
							//提货方式
							transferPanel.getCombTransferAllocationType().setEnabled(true);
							//目的站
							transferPanel.getTxtTransferDestination().setEnabled(true);
							transferPanel.getBtnQueryBranch().setEnabled(true);

						} else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(procuctCode)) {
							//运输信息可变更
							UIUtils.disableUI(transferPanel);
							//运输性质
							transferPanel.getCombTransferProductType().setEnabled(true);
							//提货方式
							transferPanel.getCombTransferAllocationType().setEnabled(true);
							//目的站
							transferPanel.getTxtTransferDestination().setEnabled(true);
							transferPanel.getBtnQueryBranch().setEnabled(true);
							//航班类型
							transferPanel.getCombTransferPredictFlight().setEnabled(true);
							//合票方式
							transferPanel.getCombFreightMethod().setEnabled(true);
							
							
						} else if (ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(procuctCode)) {
							//整车不允许修改任何东东
							UIUtils.disableUI(transferPanel);
						}
					}else{
						//运输信息可变更
						UIUtils.disableUI(transferPanel);
						transferPanel.getCombTransferProductType().setEnabled(true);
					}
					
					List<ProductEntity> list = WaybillRfcServiceFactory.getWaybillRfcService().queryTransType(startDeptCode);
					DefaultComboBoxModel productTypeModel = waybillInfoPanel.getTransferPanel().getTransportRecordTabPanel().getTransferInfoPanel().getTransferProductTypeModel();
					productTypeModel.removeAllElements();
					for (ProductEntity dataDictionary : list) {
						ProductEntityVo vo = new ProductEntityVo();
						try {
							if(CommonUtils.directDetermineIsExpressByProductCode(dataDictionary.getCode())){
								continue;
							}
							
							BeanUtils.copyProperties(vo, dataDictionary);
						} catch (IllegalAccessException e1) {
							logger.error(e1.getMessage());
						} catch (InvocationTargetException e1) {
							logger.error(e1.getMessage());
						}
						productTypeModel.addElement(vo);
					}
					
					binderWaybill.setRecordStartOrgCode(startDeptCode);
					binderWaybill.setRecordProductCode(recordVo.getProductCode());
					binderWaybill.setRecordCustomerPickupOrgCode(recordVo.getCustomerPickupOrgCode());
					binderWaybill.setRecordCustomerPickupOrgName(recordVo.getCustomerPickupOrgName());
					binderWaybill.setRecordTargetOrgCode(recordVo.getTargetOrgCode());
					binderWaybill.setRecordReceiveMethod(recordVo.getReceiveMethod());
					binderWaybill.setRecordFlightNumberType(recordVo.getFlightNumberType());
					binderWaybill.setRecordBillingType(recordVo.getBillingType());
					binderWaybill.setRecordUnitPrice(recordVo.getUnitPrice());
					binderWaybill.setRecordFee(recordVo.getTransportFee());
					binderWaybill.setRecordFreightMethod(recordVo.getFreightMethod());
					binderWaybill.setRecordFlightShift(recordVo.getFlightShift());
				}else{
					//clear data
					DefaultComboBoxModel productTypeModel = waybillInfoPanel.getTransferPanel().getTransportRecordTabPanel().getTransferInfoPanel().getTransferProductTypeModel();
					productTypeModel.removeAllElements();
					
					binderWaybill.setRecordStartOrgCode(null);
					binderWaybill.setRecordProductCode(null);
					binderWaybill.setRecordCustomerPickupOrgCode(null);
					binderWaybill.setRecordCustomerPickupOrgName(null);
					binderWaybill.setRecordTargetOrgCode(null);
					binderWaybill.setRecordReceiveMethod(null);
					binderWaybill.setRecordFlightNumberType(null);
					binderWaybill.setRecordBillingType(null);
					binderWaybill.setRecordUnitPrice(null);
					binderWaybill.setRecordFee(null);
					binderWaybill.setRecordFreightMethod(null);
					binderWaybill.setRecordFlightShift(null);
				}
			}
		});
	}
	
	/**
	 * 
	 * 重置运输信息
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-10 下午7:29:01
	 */
	public void resetTransportFee(){
		//没有变更记录
		if(getMessagePanel().getChangedInfoPanel().getTableData().size()==0)
			return;
		JXTable otherTable = incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if(data != null){
			for(OtherChargeVo chargeVo : data){
				if(chargeVo != null){
					if (chargeVo.getIsEdit() == null) {
						chargeVo.setIsEdit(false);
					}
					
					//修改过的中转费重置回来
					if(chargeVo.getIsEdit()){
						BigDecimal oldZZF = new BigDecimal(chargeVo.getOldMoney());
						BigDecimal newZZF = new BigDecimal(chargeVo.getMoney());
						chargeVo.setIsEdit(false);
						chargeVo.setMoney(chargeVo.getOldMoney());
						//增值服务费修改
						binderWaybill.setOtherFee(binderWaybill.getOtherFee().subtract(newZZF).add(oldZZF));
					}
				}
				
				
			}
			incrementPanel.setChangeDetail(data);
			binderWaybill.setOtherChargeChanged(true);
			//重置公布价运费
			// 设置运费
			binderWaybill.setTransportFee(originWaybill.getTransportFee());
			// 设置费率
			binderWaybill.setUnitPrice(originWaybill.getUnitPrice());
			// 设置计费类型（重量计费或者体积计费）
			binderWaybill.setBillingType(originWaybill.getBillingType());
			//设置计费重量
			binderWaybill.setBillWeight(originWaybill.getBillWeight());
			// 画布公布价运费
			binderWaybill.setTransportFeeCanvas(originWaybill.getTransportFeeCanvas());
		}
	
	}

	public QueryPublishPriceUI getQueryPublishPriceUI() {
		return queryPublishPriceUI;
	}

	public void setQueryPublishPriceUI(QueryPublishPriceUI queryPublishPriceUI) {
		this.queryPublishPriceUI = queryPublishPriceUI;
	}

	public BasicPanel getBasicPanel() {
		return basicPanel;
	}

	public void setBasicPanel(BasicPanel basicPanel) {
		this.basicPanel = basicPanel;
	}
	
}