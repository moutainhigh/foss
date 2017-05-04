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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/WoodYokeEnterAction.java
 * 
 * FILE NAME        	: WoodYokeEnterAction.java
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
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.EnterYokeInfoChangeDialog;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.EnterYokeInfoChangeDialog.LabeledGoodVo;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 打木架信息录入
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午7:43:38
 */
public class WoodYokeEnterAction extends
		AbstractButtonActionListener<EnterYokeInfoChangeDialog> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WoodYokeEnterAction.class);
	// 打木架对话框
	private EnterYokeInfoChangeDialog yoke;

	//更改单Service
	
	/**
	 * 
	 * 录入打木架信息
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午7:43:16
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try
		{
			WaybillRFCUI waybillRFCUI = yoke.getWaybillRFCUI();
			
			// 绑定更改单VO
			WaybillInfoVo bean = waybillRFCUI.getBinderWaybill();
			
			// 数据校验
			validate(bean);
			
			// 录入打木架信息
			woodYokeEnter(bean);
		}catch(WaybillValidateException w)
		{
			MsgBox.showInfo(w.getMessage());
		}

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
		
//		for(LabeledGoodChangeHistoryDto dto : alreadyList2){
//			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())){
//				dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE);
//			}
//			
//			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType())){
//				dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
//			}
//		}
		
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

//				List<LabeledGoodChangeHistoryDto> list2 = bean.getLabeledGoodChangeHistoryDtoList();
//				
//				for(LabeledGoodChangeHistoryDto dto2: list2){
//					if(dto2.getSerialNo().equals(dto.getSerialNo()) || dto2.getLabeledGoodId().equals(dto.getLabeledGoodId())){
//						//原来的导出的打木架
//						dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
//						dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
//					}
//				}
				
			}else{
				List<LabeledGoodChangeHistoryDto> list = bean.getSelectedLabeledGoodEntities();
				for(LabeledGoodChangeHistoryDto dto2: list){
					if(dto2.getSerialNo().equals(dto.getSerialNo()) || dto2.getLabeledGoodId().equals(dto.getLabeledGoodId())){
						//新增的件数打木架
						dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
						dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
						
					}
				}
				
//				List<LabeledGoodChangeHistoryDto> list2 = bean.getLabeledGoodChangeHistoryDtoList();
//				
//				for(LabeledGoodChangeHistoryDto dto2: list2){
//					if(dto2.getSerialNo().equals(dto.getSerialNo()) || dto2.getLabeledGoodId().equals(dto.getLabeledGoodId())){
//						//原来的导出的打木架
//						dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
//						dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
//					}
//				}
				
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
			
//			String param = waybillRfcService.queryGoodsPackingVolume();
//			if (param == null) {
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullGoodsPackingVolume"));
//			} else {
//				BigDecimal volumeParam = new BigDecimal(param);// 参数值
				// 打木架体积+打木箱体积*参数值必须要小于等于开单总体积
				//下面的判断逻辑已经不再需要
				BigDecimal result = yokeGoodsVolume.add(boxGoodsVolume);
				if (volume.compareTo(result) >= 0) {
					setWaybillPanelVo(bean);
					setStorageMatter(bean);
					MsgBox.showInfo(i18n.get("foss.gui.creating.woodYokeEnterAction.msgBox.inputCompleted"));
					yoke.dispose();
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.overYokeGoodsVolume.one"));
				}
//			}
		} else {
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.woodYokeEnterAction.exception.lackWoodPackPieces"));
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
	 * 设置储运事项
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午7:46:55
	 */
	private void setStorageMatter(WaybillInfoVo bean) {

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
	 * UI注入
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:28:30
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#setIInjectUI(java.awt.Container)
	 */
	@Override
	public void setIInjectUI(EnterYokeInfoChangeDialog yoke) {
		this.yoke = yoke;
	}

}