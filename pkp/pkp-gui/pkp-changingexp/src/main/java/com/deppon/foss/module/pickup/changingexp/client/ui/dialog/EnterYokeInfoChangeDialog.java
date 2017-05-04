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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/EnterYokeInfoChangeDialog.java
 * 
 * FILE NAME        	: EnterYokeInfoChangeDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-changingexp
 * PACKAGE NAME: com.deppon.foss.module.pickup.changingexp.client.ui.customer
 * FILE    NAME: EnterYokeInfoUI.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.changingexp.client.ui.dialog;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.changingexp.client.action.WoodYokeEnterAction;
import com.deppon.foss.module.pickup.changingexp.client.action.WoodYokeResetAction;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.CheckBoxList;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 录入代打木架信息
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-17 上午9:13:39
 */
public class EnterYokeInfoChangeDialog extends JDialog {

	
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(EnterYokeInfoChangeDialog.class);
	/**
	 * 25
	 */
	private static final int TWENTYFIVE = 25;

	/**
	 * 16
	 */
	private static final int SIXTEEN = 16;

	/**
	 * 12
	 */
	private static final int TWELVE = 12;

	/**
	 * 8
	 */
	private static final int EIGHTR = 8;

	/**
	 * 4
	 */
	private static final int FOUR = 4;

	/**
	 * 10
	 */
	private static final int TEN = 10;
	
	private static final int NUM_680 = 680;
	
	private static final int NUM_280 = 280; 
	
	private static final int THREE = 3;
	
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8802287401540527591L;
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(EnterYokeInfoChangeDialog.class);
	
	/**
	 * 运单基础数据服务
	 */
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
	/**
	 * 变更单UI
	 */
	private WaybillRFCUI waybillRFCUI;

	/**
	 * 代打木架部门
	 */
	public JTextFieldValidate txtWoodDept;

	/**
	 * 打木架货物件数
	 */
	public JTextFieldValidate txtYokeGoodsPieces;

	/**
	 *  代打木架要求
	 */
	public JTextFieldValidate txtYokeRequire;

	/**
	 *  木架货物尺寸
	 */  
	public JTextFieldValidate txtYokeGoodsSize;

	/**
	 *  木架货物体积
	 */
	public JTextFieldValidate txtYokeGoodsVolume;

	/**
	 * 打木箱货物件数
	 */
	public JTextFieldValidate txtBoxGoodsPieces;

	/**
	 * 代打木箱要求
	 */
	public JTextFieldValidate txtBoxRequire;

	/**
	 * 代打木箱货物尺寸
	 */
	public JTextFieldValidate txtBoxGoodsSize;

	/**
	 * 代打木箱货物体积
	 */
	public JTextFieldValidate txtBoxGoodsVolume;

	/**
	 * 重置按钮
	 */
	 @ButtonAction(icon = "preview.png", shortcutKey = "", type =
	 WoodYokeResetAction.class)
	 private JButton btnReset;

	 /**
	  * 提交按钮
	  */
	 @ButtonAction(icon = "preview.png", shortcutKey = "", type =
	 WoodYokeEnterAction.class)
	JButton btnConfirm;

	 /**
	  * 流水号选中列表
	  */
	public CheckBoxList list;

	/**
	 * 流水号列表数据模型
	 */
	private SerialNoListModel listModel;
	
	// 木箱货物体积是否按键修改
	private boolean boxVolumeChanged = false;
	
	/**
	 * 
	 * 实例化打木架对话框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:56:23
	 */
	public EnterYokeInfoChangeDialog(WaybillRFCUI waybillRFCUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillRFCUI = waybillRFCUI;

		init();
		bind();
		initData();
		checkGoodsStatus();
//		EnterYorkContainerListener clistener = new EnterYorkContainerListener(this);
//		this.addWindowListener(clistener);
	}

	class EnterYorkContainerListener implements WindowListener{
	
		private EnterYokeInfoChangeDialog yoke;
		
		public EnterYorkContainerListener(EnterYokeInfoChangeDialog yoke2){
			yoke = yoke2;
		}
	
		public void windowOpened(WindowEvent e) {			
		}

		public void windowClosing(WindowEvent e) {
			
			try{
				WaybillRFCUI waybillRFCUI = yoke.getWaybillRFCUI();
				
				// 绑定更改单VO
				WaybillInfoVo bean = waybillRFCUI.getBinderWaybill();
				
				// 数据校验
				validate(bean);
				
				woodYokeEnter(bean);
			}catch(WaybillValidateException w){
				MsgBox.showInfo(w.getMessage());
			}
			
		}
		
		
		/**
		 * 
		 * 录入打木架信息
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-18 下午7:44:02
		 */
		private void woodYokeEnter(WaybillInfoVo bean) {


			String txtYokeGoodsPieces = StringUtil.defaultIfEmpty(yoke.txtYokeGoodsPieces.getText(),"0");
			String txtBoxGoodsPieces = StringUtil.defaultIfEmpty(yoke.txtBoxGoodsPieces.getText(),"0");

			int yokeGoodsPieces = Integer.parseInt(txtYokeGoodsPieces);// 打木架件数
			int boxGoodsPieces = Integer.parseInt(txtBoxGoodsPieces);// 打木箱件数
			
			int woodPackPieces = bean.getWood();// 木包装数
			if ((yokeGoodsPieces + boxGoodsPieces) <= woodPackPieces) {
				BigDecimal yokeGoodsVolume = new BigDecimal(StringUtil.defaultIfEmpty(yoke.txtYokeGoodsVolume.getText(),"0"));// 打木架体积
				BigDecimal boxGoodsVolume = new BigDecimal(StringUtil.defaultIfEmpty(yoke.txtBoxGoodsVolume.getText(),"0"));// 打木箱体积
				BigDecimal volume = bean.getGoodsVolumeTotal();// 开单总体积
				
//				String param = waybillRfcService.queryGoodsPackingVolume();
//				if (param == null) {
//					throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullGoodsPackingVolume"));
//				} else {
//					BigDecimal volumeParam = new BigDecimal(param);// 参数值
					// 打木架体积+打木箱体积*参数值必须要小于等于开单总体积
					//下面的判断逻辑已经不再需要
					BigDecimal result = yokeGoodsVolume.add(boxGoodsVolume);
					if (volume.compareTo(result) >= 0) {
						setWaybillPanelVo(bean);
						setStorageMatter(bean);
						MsgBox.showInfo(i18n.get("foss.gui.creating.woodYokeEnterAction.msgBox.inputCompleted"));
						
					} else {
						throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.overYokeGoodsVolume.one"));
					}
//				}
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.woodYokeEnterAction.exception.lackWoodPackPieces"));
			}
		}
		
		
		/**
		 * 
		 * 新增或替换储运事项中打木架、大木箱信息
		 * @author 025000-FOSS-helong
		 * @date 2013-3-11 下午07:21:56
		 */
		private void addOrReplaceData(WaybillPanelVo bean , String keyWord , String data)
		{
			//将储运事项字符串解析成数据组
			String[] remark = StringUtil.defaultIfNull(bean.getTransportationRemark()).split(";");
			//用来标识储运事项中是否存在打木架或者大木箱信息
			Boolean flag = false;
			for(int i=0;i<remark.length;i++)
			{
				//获取储运事项中的某段数据
				String oldData = remark[i];
				//判断储运事项中的这段数据是否存在打木架或者大木箱信息，存在则用最新的信息替换
				if(oldData.indexOf(StringUtil.defaultIfNull(keyWord)) != -1)
				{
					//替换掉原先的木架或木箱信息
					remark[i] = data;
					//标记储运事项中存在木架或木箱信息
					flag = true;
					break;
				}
			}
			
			//为true则表示储运事项中存在木架或木箱信息
			if(flag)
			{
				StringBuffer transportationRemark = new StringBuffer();
				//循环内是对储运事项内容的重新拼装，且以分号进行分隔
				for(int i=0;i<remark.length;i++)
				{
					transportationRemark.append(remark[i]);
					transportationRemark.append(";");
				}
				//设置重新拼装的储运事项
				bean.setTransportationRemark(transportationRemark.toString());
			}else
			{
				String transportationRemark = bean.getTransportationRemark();
				//将打木架或者打木箱的信息添加到储运的末尾
				transportationRemark = transportationRemark + data +";";
				//设置重新拼装的储运事项
				bean.setTransportationRemark(transportationRemark);
			}
		}
		
		
		/**
		 * 
		 * 设置储运事项
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-18 下午7:46:55
		 */
		private void setStorageMatter(WaybillInfoVo bean) {

			//清空储运事项
			bean.setTransportationRemark("");
			/**
			 * 判断木架是否为空并且木架件数是否为0
			 */
			if(StringUtils.isNotEmpty(yoke.txtYokeGoodsVolume.getText()) && !"0".equals(yoke.txtYokeGoodsPieces.getText()))
			{
				//木架
				StringBuffer standGoods = new StringBuffer();
				standGoods.append(i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"));
				standGoods.append("/");
				standGoods.append(yoke.txtYokeRequire.getText());
				//新增或替换储运事项中打木架、大木箱信息
				addOrReplaceData(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"),standGoods.toString());
			}
			
			if(StringUtils.isNotEmpty(yoke.txtBoxGoodsVolume.getText()) && !"0".equals(yoke.txtBoxGoodsPieces.getText()))	
			{
				//木箱
				StringBuffer boxGoods = new StringBuffer();
				boxGoods.append(i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"));
				boxGoods.append("/");
				boxGoods.append(yoke.txtBoxRequire.getText());
				//新增或替换储运事项中打木架、大木箱信息
				addOrReplaceData(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"),boxGoods.toString());
			}
		}
		
		/**
		 * 
		 * 设置木架信息到运单VO中
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-18 下午7:46:00
		 */
		private void setWaybillPanelVo(WaybillInfoVo bean){

			String txtYokeGoodsPieces = StringUtil.defaultIfEmpty(yoke.txtYokeGoodsPieces.getText(),"0");
			String txtBoxGoodsPieces = StringUtil.defaultIfEmpty(yoke.txtBoxGoodsPieces.getText(),"0");
			String txtYokeGoodsVolume = StringUtil.defaultIfEmpty(yoke.txtYokeGoodsVolume.getText(),"0");
			String txtBoxGoodsVolume = StringUtil.defaultIfEmpty(yoke.txtBoxGoodsVolume.getText(),"0");

			int yokeGoodsPieces = Integer.parseInt(txtYokeGoodsPieces);// 打木架件数
			int boxGoodsPieces = Integer.parseInt(txtBoxGoodsPieces);// 打木箱件数
			bean.setStandGoodsNum(yokeGoodsPieces);// 打木架货物件数
			bean.setStandRequirement(yoke.txtYokeRequire.getText());// 代打木架要求
			bean.setStandGoodsSize(yoke.txtYokeGoodsSize.getText());// 打木架货物尺寸
			bean.setStandGoodsVolume(new BigDecimal(txtYokeGoodsVolume));// 打木架货物体积
			bean.setBoxGoodsNum(boxGoodsPieces);// 打木箱货物件数
			bean.setBoxRequirement(yoke.txtBoxRequire.getText());// 代打木箱要求
			bean.setBoxGoodsSize(yoke.txtBoxGoodsSize.getText());// 打木箱货物尺寸
			bean.setBoxGoodsVolume(new BigDecimal(txtBoxGoodsVolume));// 打木箱货物体积
			// 流水号
			
		}
		
		/**
		 * 
		 * 打木架
		 * 
		 * 数据校验
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-18 下午7:43:48
		 */
		private void validate(WaybillInfoVo bean){
			String yokeGoodsPieces = yoke.txtYokeGoodsPieces.getText();
			String boxGoodsPieces = yoke.txtBoxGoodsPieces.getText();
			int total = 0;
			//您需要打木架，打木架体积不能为空
			if(StringUtil.isNotEmpty(yokeGoodsPieces)){
				int yokePieces = Integer.parseInt(yokeGoodsPieces);
				total += yokePieces;
				String yokeGoodsVolume = yoke.txtYokeGoodsVolume.getText();
				if(StringUtil.isEmpty(yokeGoodsVolume)){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullYokeGoodsVolume"));
				}
			}
			
			//您需要打木箱，打木箱体积不能为空
			if(StringUtil.isNotEmpty(boxGoodsPieces)){
				int boxPieces = Integer.parseInt(boxGoodsPieces);
				total += boxPieces;
				String boxGoodsVolume = yoke.txtBoxGoodsVolume.getText();
				if(StringUtil.isEmpty(boxGoodsVolume)){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullBoxGoodsVolume"));
				}
			}
			
			// 流水号
			Object[] selectedValues = yoke.list.getSelectedValues();
			List<LabeledGoodChangeHistoryDto> alreadyList = bean.getSelectedLabeledGoodEntities();
			//List<LabeledGoodChangeHistoryDto> alreadyList2 = bean.getLabeledGoodChangeHistoryDtoList();
			
			
			for(LabeledGoodChangeHistoryDto dto : alreadyList){
				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())){
					dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE);
				}
				
				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType())){
					dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
				}
			}
			
//			for(LabeledGoodChangeHistoryDto dto : alreadyList2){
//				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())){
//					dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE);
//				}
//				
//				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType())){
//					dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
//				}
//			}
			
			for(Object obj : selectedValues){
				
				
				LabeledGoodChangeHistoryDto dto = ((LabeledGoodVo)obj).getEntity();
				if(FossConstants.YES.equals(dto.getIsFromService())){
					
					
					List<LabeledGoodChangeHistoryDto> list = bean.getSelectedLabeledGoodEntities();
					
					for(LabeledGoodChangeHistoryDto dto2: list){
						if(dto2.getSerialNo().equals(dto.getSerialNo()) ||
								(dto2.getLabeledGoodId() != null && dto2.getLabeledGoodId().equals(dto.getLabeledGoodId()))){
							//原来的导出的打木架
							dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
							dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
						}
					}

//					List<LabeledGoodChangeHistoryDto> list2 = bean.getLabeledGoodChangeHistoryDtoList();
//					
//					for(LabeledGoodChangeHistoryDto dto2: list2){
//						if(dto2.getSerialNo().equals(dto.getSerialNo()) || dto2.getLabeledGoodId().equals(dto.getLabeledGoodId())){
//							//原来的导出的打木架
//							dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
//							dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
//						}
//					}
					
				}else{
					List<LabeledGoodChangeHistoryDto> list = bean.getSelectedLabeledGoodEntities();
					for(LabeledGoodChangeHistoryDto dto2: list){
						if(dto2.getSerialNo().equals(dto.getSerialNo()) || dto2.getLabeledGoodId().equals(dto.getLabeledGoodId())){
							//新增的件数打木架
							dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
							dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
							
						}
					}
					
//					List<LabeledGoodChangeHistoryDto> list2 = bean.getLabeledGoodChangeHistoryDtoList();
//					
//					for(LabeledGoodChangeHistoryDto dto2: list2){
//						if(dto2.getSerialNo().equals(dto.getSerialNo()) || dto2.getLabeledGoodId().equals(dto.getLabeledGoodId())){
//							//原来的导出的打木架
//							dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
//							dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
//						}
//					}
					
				}
				
				
			}
			

			List<LabeledGoodChangeHistoryDto> list = bean.getSelectedLabeledGoodEntities();
			//木箱统计总数
			int countwood = 0;
			if(list!=null){
				for(LabeledGoodChangeHistoryDto dto: list){
					if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())
						||LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType()) ){
						countwood ++ ;
					}
				}
			}
			
			//打木架件数和下面勾选的明细总数不一致，
			//验证不通过
			//【抛出异常提示
			if(countwood!=total){
				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.woodYokeEnterAction.exception.wrongCountWood"));
			}
			
			
			//打木架件数+打木箱件数之和不能大于总件数
			if(total>bean.getGoodsQtyTotal().intValue()){
				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.woodYokeEnterAction.exception.overGoodsQtyTotal"));
			}
			
		}

	
		public void windowClosed(WindowEvent e) {
			
		}

		
		public void windowIconified(WindowEvent e) {
			
		}

		
		public void windowDeiconified(WindowEvent e) {
			
		}

	
		public void windowActivated(WindowEvent e) {
			
		}

		
		public void windowDeactivated(WindowEvent e) {
			
		}

	}
	
	
	/**
	 * 
	 * 检测库存状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-19 下午6:12:10
	 */
	private void checkGoodsStatus() {
		WaybillInfoVo bean = waybillRFCUI.getBinderWaybill();
		if(!WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())){
			//货物已出开单部门，无法修改打木架信息
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.msgBox.label"));
			UIUtils.disableUI(this);
		}
	}

	/**
	 * 
	 * 将按钮以及控件进行绑定
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-30 上午09:12:46
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}


	
	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:18:23
	 */
	private void init() {
		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("4dlu:grow"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{FOUR, EIGHTR, TWELVE, SIXTEEN}});
		getContentPane().setLayout(formLayout);

		//代打木架信息录入
		JLabel lblTitle = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.title"));
		lblTitle.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		getContentPane().add(lblTitle, "2, 2, 3, 1, left, default");

		//代打木架部门
		JLabel lblWoodDept = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.woodDept.label")+"：");
		getContentPane().add(lblWoodDept, "2, 4, left, default");

		txtWoodDept = new JTextFieldValidate();
		txtWoodDept.setEnabled(false);
		getContentPane().add(txtWoodDept, "4, 4, fill, default");
		txtWoodDept.setColumns(TEN);

		//木架货物件数
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.yokeGoodsPieces.label")+"：");
		getContentPane().add(lblNewLabel2, "2, 6, left, default");

		// 打木架货物件数
		txtYokeGoodsPieces = new JTextFieldValidate();
		getContentPane().add(txtYokeGoodsPieces, "4, 6, fill, default");
		txtYokeGoodsPieces.setColumns(TEN);

		//代打木架要求
		JLabel label2 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.yokeRequire.label")+"：");
		getContentPane().add(label2, "6, 6, left, default");

		// 代打木架要求
		txtYokeRequire = new JTextFieldValidate();
		getContentPane().add(txtYokeRequire, "8, 6, fill, default");
		txtYokeRequire.setColumns(TEN);

		//货物尺寸
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.yokeGoodsSize.label")+"：");
		getContentPane().add(lblNewLabel3, "10, 6, left, default");

		// 木架货物尺寸
		txtYokeGoodsSize = new JTextFieldValidate();
		getContentPane().add(txtYokeGoodsSize, "12, 6, fill, default");
		txtYokeGoodsSize.addFocusListener(new FocusAdapter() {
			
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtYokeGoodsSize.getText())){
					if (!NumberValidate.checkIsGoodsSize(txtYokeGoodsSize.getText())) {
						StringBuffer str = new StringBuffer(i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
						str.append("(\n").append(i18n.get("foss.gui.creating.waybillDescriptor.example"));
						str.append("：0.5*0.5*0.5*2").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
						MsgBox.showInfo(str.toString());
					}else{
						//计算打木架体积
						//将计算出来的体积设置到打木架体积控件中
						txtYokeGoodsVolume.setText(calculateVolume(txtYokeGoodsSize.getText()));
					}
				}
			}
		});
		
		

		
		
		txtYokeGoodsSize.setColumns(TEN);

		//货物体积
		JLabel label4 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.yokeGoodsVolume.label")+"：");
		getContentPane().add(label4, "14, 6, left, default");

		// 木架货物体积
		txtYokeGoodsVolume = new JTextFieldValidate();
		getContentPane().add(txtYokeGoodsVolume, "16, 6, fill, default");
		txtYokeGoodsVolume.setColumns(TEN);

		//木箱货物件数
		JLabel label1 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.boxGoodsPieces.label")+"：");
		getContentPane().add(label1, "2, 8, left, default");

		// 打木箱货物件数
		txtBoxGoodsPieces = new JTextFieldValidate();
		getContentPane().add(txtBoxGoodsPieces, "4, 8, fill, default");
		txtBoxGoodsPieces.setColumns(TEN);

		//代打木箱要求
		JLabel label3 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.boxRequire.label")+"：");
		getContentPane().add(label3, "6, 8, left, default");

		// 代打木箱要求
		txtBoxRequire = new JTextFieldValidate();
		getContentPane().add(txtBoxRequire, "8, 8, fill, default");
		txtBoxRequire.setColumns(TEN);

		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.boxGoodsSize.label")+"：");
		getContentPane().add(lblNewLabel4, "10, 8, left, default");

		// 代打木箱货物尺寸
		txtBoxGoodsSize = new JTextFieldValidate();
		// 木箱尺寸失去焦点监听
		txtBoxGoodsSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtBoxGoodsSize.getText()))
				{
					// 判断尺寸输入格式是否合法
					if (!NumberValidate.checkIsGoodsSize(txtBoxGoodsSize.getText())) {
						StringBuffer str = new StringBuffer(i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
						str.append("(\n").append(i18n.get("foss.gui.creating.waybillDescriptor.example"));
						str.append("：0.5*0.5*0.5*2").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
						str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
						MsgBox.showInfo(str.toString());
					} else {
						//计算打木架体积
						//将计算出来的体积设置到打木架体积控件中
						txtBoxGoodsVolume.setText(calculateVolume(txtBoxGoodsSize.getText()));
					}
				}
			}
		});
		getContentPane().add(txtBoxGoodsSize, "12, 8, fill, default");
		txtBoxGoodsSize.setColumns(TEN);

		JLabel label6 = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.boxGoodsVolume.label")+"：");
		getContentPane().add(label6, "14, 8, left, default");

		// 代打木箱货物体积
		txtBoxGoodsVolume = new JTextFieldValidate();
		getContentPane().add(txtBoxGoodsVolume, "16, 8, fill, default");
		txtBoxGoodsVolume.setColumns(TEN);
		// 木箱体积失去焦点监听
		//代打木架标签
		txtBoxGoodsVolume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(StringUtils.isNotEmpty(txtBoxGoodsVolume.getText())&&boxVolumeChanged){
					if (!NumberValidate.checkIntOrFloat(txtBoxGoodsVolume.getText())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.numberOnly.msg"));
						txtBoxGoodsVolume.setText(null);
					}else{
						txtBoxGoodsVolume.setText(reCalculateVolume(txtBoxGoodsSize.getText(),txtBoxGoodsVolume.getText()));
						boxVolumeChanged = false;
					}
				}
			}
		});
		
		
		// 木箱体积键盘输入监听
		txtBoxGoodsVolume.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				// 有键盘操作设置体积已修改
				boxVolumeChanged = true;
			}
		});
		JLabel label = new JLabel(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.boxSerialNos.label")+"：");
		getContentPane().add(label, "2, 10");
		listModel = new SerialNoListModel();
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "4, 10, 5, 3, fill, fill");
		list = new CheckBoxList();
		list.setBackground(scrollPane.getBackground());
		scrollPane.setViewportView(list);
		list.setModel(listModel);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(TWENTYFIVE);
		getContentPane().add(panel, "10, 12, 7, 1, fill, fill");

		//确定录入
		btnConfirm = new JButton(i18n.get("foss.gui.changingexp.enterYokeInfoDialog.confirm.label"));
		panel.add(btnConfirm);
		
		//重置
		btnReset = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.reset"));
		panel.add(btnReset);
		
		setModal(true);
		
		setSize(NUM_680, NUM_280);

	}
	
	/**
	 * 
	 * 计算木架体积
	 * @author foss-sunrui
	 * @date 2013-3-13 下午5:38:32
	 * @param size
	 * @return
	 * @see
	 */
	private String reCalculateVolume(String size,String volume){
		String volumeParam = getVolumeParam();
		//当没有录入体积的时候才根据录入的体积去乘以配置的体积计算参数
		if(StringUtils.isEmpty(size)&&StringUtils.isNotEmpty(volumeParam)){
			BigDecimal bigDecimal = new BigDecimal(volume.toString());
			bigDecimal = bigDecimal.setScale(THREE, BigDecimal.ROUND_HALF_UP);
			//乘以参数1.4，参数是可以配置的
			bigDecimal = bigDecimal.multiply(new BigDecimal(volumeParam));
			bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
			//四舍五入后如果变为0.00，那么需要给成默认的0.01，以免丢失体积
			if(bigDecimal.doubleValue()==0){
				bigDecimal = new BigDecimal("0.01");
			}
			return bigDecimal.toString();
		}else{
			//如果录入了尺寸则以尺寸计算的体积为准
			return calculateVolume(size);
		}
	}
	
	/**
	 * 
	 * 计算木架体积
	 * @author 025000-FOSS-helong
	 * @date 2013-3-10 下午03:57:07
	 * @param size
	 */
	private String calculateVolume(String size){
		String volumeParam = getVolumeParam();
		if(StringUtils.isNotEmpty(volumeParam)){
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			try {
				Object result = engine.eval(size);
				BigDecimal bigDecimal = new BigDecimal(result.toString());
				bigDecimal = bigDecimal.setScale(THREE, BigDecimal.ROUND_HALF_UP);
				BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
				bigDecimal = bigDecimal.divide(m);
				//乘以参数1.4，参数是可以配置的
				bigDecimal = bigDecimal.multiply(new BigDecimal(volumeParam));
				bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
				//四舍五入后如果变为0.00，那么需要给成默认的0.01，以免丢失体积
				if(bigDecimal.doubleValue()==0){
					bigDecimal = new BigDecimal("0.01");
				}
				return bigDecimal.toString();
			} catch (ScriptException e) {
				LOG.error("ScriptException", e);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.one"), e);
			}
		}else
		{
			return null;
		}
	}
	

	/**
	 * 
	 * 方法详细描述说明、方法参数的具体涵义
	 * @author 025000-FOSS-helong
	 * @date 2013-3-12 下午02:40:57
	 * @return
	 */
	private String getVolumeParam(){
		String param =baseDataService.queryGoodsPackingVolume();
		if (StringUtils.isEmpty(param)) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullGoodsPackingVolume"));
		}
		return param;
	}

	/**
	 * 
	 * 初始化代打木架部门
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 下午03:02:59
	 */
	private void initData() {
		WaybillInfoVo bean = waybillRFCUI.getBinderWaybill();

		txtWoodDept.setText(bean.getPackingOrganizationName());// 代打木架部门
		if (bean.getStandGoodsNum() != null) {
			this.txtYokeGoodsPieces.setText(bean.getStandGoodsNum().toString());// 打木架货物件数
			this.txtYokeRequire.setText(bean.getStandRequirement());// 代打木架要求
			this.txtYokeGoodsSize.setText(bean.getStandGoodsSize());// 打木架货物尺寸
			this.txtYokeGoodsVolume.setText(bean.getStandGoodsVolume()
					.toString());// 打木架货物体积
		}

		if (bean.getBoxGoodsNum() != null) {
			this.txtBoxGoodsPieces.setText(bean.getBoxGoodsNum().toString());// 打木箱货物件数
			this.txtBoxRequire.setText(bean.getBoxRequirement());// 代打木箱要求
			this.txtBoxGoodsSize.setText(bean.getBoxGoodsSize());// 打木箱货物尺寸
			this.txtBoxGoodsVolume.setText(bean.getBoxGoodsVolume().toString());// 打木箱货物体积
		}
		
		List<LabeledGoodChangeHistoryDto> labeledGoodEntities = bean.getLabeledGoodChangeHistoryDtoList();
		if(labeledGoodEntities != null){
			for(LabeledGoodChangeHistoryDto entity : labeledGoodEntities){
				listModel.addElement(new LabeledGoodVo(entity));
			}
		}
		//填充默认选中的流水号
		if(labeledGoodEntities != null){
			int size = listModel.getSize();
			for(int i=0; i<size; i++){
				LabeledGoodChangeHistoryDto entity = labeledGoodEntities.get(i);
				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(entity.getChangeType())
						||LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(entity.getChangeType() )){
					list.addSelectionInterval(i ,i);
				}
			}
		}
	}

	/**
	 * 
	 * 更改单UI
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:58:33
	 */
	public WaybillRFCUI getWaybillRFCUI() {
		return waybillRFCUI;
	}
	
	/**
	 * 
	 * 自定义流水号显示模型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:57:06
	 */
	private class SerialNoListModel extends DefaultListModel{

		private static final long serialVersionUID = -199518416463485592L;

	}
	
	/**
	 * 
	 * 流水号VO
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:57:26
	 */
	public class LabeledGoodVo {
		/**
		 * 流水号实体对象
		 */
		private LabeledGoodChangeHistoryDto entity;
		
		/**
		 * 
		 * 实例化流水号VO对象
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-24 下午9:58:01
		 */
		public LabeledGoodVo(LabeledGoodChangeHistoryDto entity){
			this.entity = entity;
		}
		
		/**
		 * 
		 * 获取流水号实体对象
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-24 下午9:58:13
		 */
		public LabeledGoodChangeHistoryDto getEntity() {
			return entity;
		}


		@Override
		public String toString() {
			if(entity != null)
				return entity.getSerialNo();
			return super.toString();
		}
	}

}