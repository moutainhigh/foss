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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/WoodYokeEnterAction.java
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
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.EnterYokeInfoChangeDialog;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.EnterYokeInfoChangeDialog.LabeledGoodVo;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.CollectionUtils;
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
	/**
	 * 更改单服务类统一入口
	 */
	private IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();

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
		int total = 0;
		//获取打木架件数
		String yokeGoodsPieces = yoke.txtYokeGoodsPieces.getText();
		//获取打木架体积
		String yokeGoodsVolume = yoke.txtYokeGoodsVolume.getText();
		//获取打木架要求
		String yokeRequire = yoke.txtYokeRequire.getText();
		
		//判断打木架体积是否为空，如果不为空，则打木架件数和要求也不能为空
		if(!StringUtil.isEmpty(yokeGoodsVolume) && Double.parseDouble(yokeGoodsVolume)>0)
		{
			//打木架件数不能为空或为0
			if(CommonUtils.strToNum(yokeGoodsPieces) == 0){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullYokeGoodsPieces"));
			}
			//打木架要求不能为空
			if("".equals(yokeRequire)){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullYokeGoodsRequire"));
			}
		}
		/**
		 * BUG-58280
		 * 出发部门库存发更改，包装改为代打木架，录入信息完毕后再次点击代打木架弹出界面，
		 * 不做任何修改点击确定录入时却弹出"代打木箱信息不能为空或为0"。但实际并未输入木箱信息
		 */
		//验证木架
		validateYokeNull();
		//验证木箱
		validateBoxNull();
		
		//zxy 20131212 ISSUE-4391 DEFECT-542 start:此代码由BUG-58280删除，重新加回来
		//获取打木箱件数
		String boxGoodsPieces = yoke.txtBoxGoodsPieces.getText();
		//代打木箱要求
//		String boxRequire = yoke.txtBoxRequire.getText();
		//获取大木箱体积
		String boxGoodsVolume = yoke.txtBoxGoodsVolume.getText();
		//您需要打木架，打木架体积不能为空
		if(StringUtil.isNotEmpty(yokeGoodsPieces)){
			int yokePieces = Integer.parseInt(yokeGoodsPieces);
			total += yokePieces;
			if(StringUtil.isEmpty(yokeGoodsVolume)){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullYokeGoodsVolume"));
			}
		}
		
		//您需要打木箱，打木箱体积不能为空
		if(StringUtil.isNotEmpty(boxGoodsPieces)){
			int boxPieces = Integer.parseInt(boxGoodsPieces);
			total += boxPieces;
			if(StringUtil.isEmpty(boxGoodsVolume)){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullBoxGoodsVolume"));
			}
		}
		
		// 流水号
		Object[] selectedValues = yoke.list.getSelectedValues();
		List<LabeledGoodChangeHistoryDto> alreadyList = bean.getSelectedLabeledGoodEntities();
		for(LabeledGoodChangeHistoryDto dto : alreadyList){
			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())){
				dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE);
			}
			
			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType())){
				dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
			}
		}
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
			}else{
				List<LabeledGoodChangeHistoryDto> list = bean.getSelectedLabeledGoodEntities();
				for(LabeledGoodChangeHistoryDto dto2: list){
					if(dto2.getSerialNo().equals(dto.getSerialNo()) || 
							(dto2.getLabeledGoodId() != null && dto2.getLabeledGoodId().equals(dto.getLabeledGoodId()))){
						//新增的件数打木架
						dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
						dto2.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
						
					}
				}
			}
		}
		//zxy 20131212 ISSUE-4391 DEFECT-542 end:此代码由BUG-58280删除，重新加回来
		
		//zxy 20131225  MANA-381 start 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
		//只在收货部门库存加校验
//		if(WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())){
		//在外场还未打包装前才校验
		if(!waybillService.isHasPackaged(bean.getWaybillNo())){
			int intBoxPieces = CommonUtils.strToNum(boxGoodsPieces);
			int intYokePieces = CommonUtils.strToNum(yokeGoodsPieces);
			if((intBoxPieces + intYokePieces) == bean.getGoodsQtyTotal()){
				// 打木架体积
				BigDecimal decYokeGoodsVolume = new BigDecimal(StringUtil.defaultIfEmpty(yoke.txtYokeGoodsVolume.getText(),"0"));
				// 打木箱体积
				BigDecimal decBoxGoodsVolume = new BigDecimal(StringUtil.defaultIfEmpty(yoke.txtBoxGoodsVolume.getText(),"0"));
				BigDecimal decMoodenGoodsValumn = decYokeGoodsVolume.add(decBoxGoodsVolume);
				if(decMoodenGoodsValumn.add(new BigDecimal(NumberConstants.NUMBER_0_01d)).compareTo(bean.getGoodsVolumeTotal()) < 0 
						|| decMoodenGoodsValumn.subtract(new BigDecimal(NumberConstants.NUMBER_0_01d)).compareTo(bean.getGoodsVolumeTotal()) > 0){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.woodYokeEnterAction.exception.notEqualVolumn"));
				}
			}
		}
		//zxy 20131225  MANA-381 end 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
		
		//zxy 20131118 ISSUE-4391 start 新增：打木托数据验证
		//获取打托件数
		String salverGoodsPieces = yoke.txtSalverGoodsPieces.getText()
				.trim();
		// 代打木托要求
		String salverRequire = yoke.txtSalverRequire.getText().trim();
		// 获取打木托费用
		String salverGoodsAmount = StringUtils.defaultIfEmpty(yoke.txtSalverGoodsAmount.getText().trim(),"0");
		// 判断打木托件数是否为空，如果不为空，则打木箱费用和要求也不能为空
		if (!StringUtils.isEmpty(salverGoodsPieces) && CommonUtils.strToNum(salverGoodsPieces) > 0) {
			// 打木托件数不能为空或为0
			if (CommonUtils.strToNum(salverGoodsPieces) <= 0) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.woodYokeEnterAction.exception.nullSalverGoodsPieces"));
			}
			// 打木托费用不能为空或为0
			if ((new BigDecimal(salverGoodsAmount))
					.compareTo(BigDecimal.ZERO) == 0) {
				throw new WaybillValidateException(i18n.get("foss.gui.changing.woodYokeEnterAction.exception.nullSalverGoodsAmount"));
			}
			// 打木托要求不能为空
			if ("".equals(salverRequire)) {
				throw new WaybillValidateException(
						i18n.get("foss.gui.changing.woodYokeEnterAction.exception.nullSalverGoodsRequire"));
			}
		
		//验证是否小于最低一票
			if(!yoke.validSalverMinFee(salverGoodsAmount, salverGoodsPieces)){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.woodYokeEnterAction.exception.minSalverGoodsAmount"));
			}
			
			//验证是否大于最高一票
			if(!yoke.validSalverMaxFee(salverGoodsAmount, salverGoodsPieces)){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.woodYokeEnterAction.exception.maxSalverGoodsAmount"));
			}
		}
		
		// 如果没有录入木托件数，需要将salverGoodsPieces的“”值改为“0”，
		// 否则下面的判断会有NumberFormatException
		if(StringUtils.isEmpty(salverGoodsPieces)){
			salverGoodsPieces = "0";
		}
		
		//打木托件数
		Object[] salverValues = yoke.listSalver.getSelectedValues();
		if(salverValues != null && StringUtils.isNotBlank(salverGoodsPieces) && (new BigDecimal(salverGoodsPieces).compareTo(new BigDecimal(salverValues.length)) > 0)){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.eight"));
		}
		if(salverValues != null && salverValues.length > 0 && (new BigDecimal(salverGoodsPieces).compareTo(BigDecimal.ZERO) <= 0)){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.night"));
		}
		//zxy 20131118 ISSUE-4391 end 新增：打木托数据验证
		
		List<LabeledGoodChangeHistoryDto> list = bean.getSelectedLabeledGoodEntities();
		//木箱统计总数
		int countwood = 0;
		if(CollectionUtils.isNotEmpty(list)){
			boolean bShowConfirm = false;
			StringBuffer sbLabels = new StringBuffer();
			for(LabeledGoodChangeHistoryDto dto: list){
				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())
					||LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType()) ){
					countwood ++ ;
					//zxy 20131118 ISSUE-4391 start 新增：即打木托又打木架温馨提示
					// 如果即打木架又打木托，则温馨提示
					if (salverValues != null) {
						for (int i = 0; i < salverValues.length; i++) {
							LabeledGoodVo vo = (LabeledGoodVo) salverValues[i];
							//温馨提示
							if(vo.getEntity().getSerialNo().equals(dto.getSerialNo())){
								if(sbLabels.length() <= 0){
									sbLabels.append(Integer.parseInt(vo.getEntity().getSerialNo()));
								}else
									sbLabels.append(",").append(Integer.parseInt(vo.getEntity().getSerialNo()));
								if(!bShowConfirm)
									bShowConfirm = true;
							}
						}
					}
					//zxy 20131118 ISSUE-4391 end 新增：即打木托又打木架温馨提示
				}
			}
			String sbLabelsStr=sbLabels.toString();
			//如果重叠的标签太多，则获取前10个
			if(sbLabels.lastIndexOf(",")>= NumberConstants.NUMBER_10){
				System.out.println("索引==========="+sbLabels.lastIndexOf(","));
				int index=CommonUtils.searchStrIndex(sbLabelsStr, ",", NumberConstants.NUMBER_10);
				sbLabelsStr=sbLabelsStr.substring(0, index)+"......";
			}
			//温馨提示
			if(bShowConfirm){
				MsgBox.showInfo(i18n.get("foss.gui.changing.woodYokeEnterAction.msgBox.labelText")+sbLabelsStr+i18n.get("foss.gui.changing.woodYokeEnterAction.msgBox.salverConfirm"));
			}
		}
		//打木架件数和下面勾选的明细总数不一致，
		//验证不通过
		//【抛出异常提示
		if(countwood!=total){
			throw new WaybillValidateException(i18n.get("foss.gui.changing.woodYokeEnterAction.exception.wrongCountWood"));
		}
		
	}
	
	/**
	 * 打木架数据空校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-8-11
	 */
	private void validateYokeNull()
	{
		//获取打木架件数
		String yokeGoodsPieces = yoke.txtYokeGoodsPieces.getText().trim();
		//获取打木架体积
		String yokeGoodsVolume = yoke.txtYokeGoodsVolume.getText().trim();
		//获取打木架尺寸
		String yokeSize = yoke.txtYokeGoodsVolume.getText().trim();
		
		Boolean yokeFlag = false;
		if(StringUtil.isNotEmpty(yokeGoodsPieces))
		{
			yokeFlag = true;
		}
		if(StringUtil.isNotEmpty(yokeGoodsVolume))
		{
			yokeFlag = true;
		}
		
		
		if(StringUtil.isNotEmpty(yokeSize))
		{
			yokeFlag = true;
		}
		
		if(yokeFlag)
		{
			if(StringUtil.isEmpty(yokeGoodsPieces))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullYokeGoodsPieces"));
			}else if(StringUtil.isEmpty(yokeGoodsVolume))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullYokeGoodsVolume"));
			}/*else if(StringUtil.isEmpty(yokeSize))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullYokeGoodsSize"));
			}*/
			
			//验证木架尺寸
			//validateYokeSize();
		}
	}
	
	/**
	 * 打木箱数据空校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-8-11
	 */
	private void validateBoxNull()
	{
		//获取打木箱件数
		String boxGoodsPieces = yoke.txtBoxGoodsPieces.getText().trim();
		//获取打木箱体积
		String boxGoodsVolume = yoke.txtBoxGoodsVolume.getText().trim();
		//获取打木箱尺寸
		String boxSize = yoke.txtBoxGoodsSize.getText().trim();
		
		Boolean boxFlag = false;
		if(StringUtil.isNotEmpty(boxGoodsPieces))
		{
			boxFlag = true;
		}
		
		if(StringUtil.isNotEmpty(boxGoodsVolume))
		{
			boxFlag = true;
		}
		
		if(StringUtil.isNotEmpty(boxSize))
		{
			boxFlag = true;
		}
		
		if(boxFlag)
		{
			if(StringUtil.isEmpty(boxGoodsPieces))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullBoxGoodsPieces"));
			}else if(StringUtil.isEmpty(boxGoodsVolume))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullBoxGoodsVolume"));
			}/*else if(StringUtil.isEmpty(boxSize))
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullBoxGoodsSize"));
			}*/
			//验证木箱尺寸
			//validateBoxSize();
		}
	}

	/**
	 * 
	 * 录入打木架信息
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午7:44:02
	 */
	private void woodYokeEnter(WaybillInfoVo bean) {



//		int yokeGoodsPieces = Integer.parseInt(txtYokeGoodsPieces);// 打木架件数
//		int boxGoodsPieces = Integer.parseInt(txtBoxGoodsPieces);// 打木箱件数
		
//		int woodPackPieces = bean.getWood();// 木包装数
//		if ((yokeGoodsPieces + boxGoodsPieces) <= woodPackPieces) {
			BigDecimal yokeGoodsVolume = new BigDecimal(StringUtil.defaultIfEmpty(yoke.txtYokeGoodsVolume.getText(),"0"));// 打木架体积
			BigDecimal boxGoodsVolume = new BigDecimal(StringUtil.defaultIfEmpty(yoke.txtBoxGoodsVolume.getText(),"0"));// 打木箱体积
			BigDecimal volume = bean.getGoodsVolumeTotal();// 开单总体积
			//zxy 20131118 ISSUE-4391 start 新增：保存打木托列表到bean中
			//打木托件数
//			Object[] salverValues = yoke.listSalver.getSelectedValues();
			ListModel salverModel = yoke.listSalver.getModel();
			//流水号包装类型置空
//			Common.refreshChangeHisLabeledPackageType(bean);
			if(salverModel != null && salverModel.getSize() > 0){
				ListSelectionModel listSelectModel =  yoke.listSalver.getSelectionModel();
				for(int i = 0; i < salverModel.getSize(); i++){
					LabeledGoodVo vo = (LabeledGoodVo)salverModel.getElementAt(i);
					boolean isSelected = listSelectModel.isSelectedIndex(i);
					//如果选中，且之前被删除，则还原SLAVER(库存中)
					if(isSelected  && LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE.equals(vo.getEntity().getPackageSalver())){
						vo.getEntity().setPackageSalver(WaybillConstants.PACKAGE_TYPE_SALVER);
					}
					//如果选中，且之前状态等于PACKAGE_TYPE_SALVER_ADD_NEW(新增)，则标识不变
					else if(isSelected && 
							(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_ADD_NEW.equals(vo.getEntity().getPackageSalver())
									||!WaybillConstants.PACKAGE_TYPE_SALVER.equals(vo.getEntity().getPackageSalver()))){//如果选中，非上述情况，且之前状态不等于SLAVER(库存中)，则标识新增
						vo.getEntity().setPackageSalver(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_ADD_NEW);
					}
					//如果未选中，且当前状态SLAVER(库存中)，则标识删除标志
					else if(!isSelected && WaybillConstants.PACKAGE_TYPE_SALVER.equals(vo.getEntity().getPackageSalver())){
						vo.getEntity().setPackageSalver(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE);
					}
					//如果未选中，且当前状态PACKAGE_TYPE_SALVER_ADD_NEW(新增)，则标识为空
					else if(!isSelected && LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_ADD_NEW.equals(vo.getEntity().getPackageSalver())){
						vo.getEntity().setPackageSalver(StringUtils.EMPTY);
					}
					//如果未选中，且当前状态PACKAGE_TYPE_SALVER_DELETE(新增)，则标识不变
//					else if(!isSelected && LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE.equals(vo.getEntity().getPackageSalver())){
//						vo.getEntity().setPackageSalver(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE);
//					}
				}
			}
			//zxy 20131118 ISSUE-4391 end 新增：保存打木托列表到bean中
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
//		} else {
//			throw new WaybillValidateException(i18n.get("foss.gui.changing.woodYokeEnterAction.exception.lackWoodPackPieces"));
//		}
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
		//zxy 20131118 ISSUE-4391 start 新增：保存打木托信息到bean
		
		// ===========lianhe/非木包装费/2017年1月11日/start=======
		String nonWoodPackingAmount = StringUtil.defaultIfEmpty(yoke.nonWoodPackingAmount.getText(),"0");
		//非木包装费
		bean.setNonWoodPackingAmount(new BigDecimal(nonWoodPackingAmount));
		// ===========lianhe/非木包装费/2017年1月11日/end=======
		
		// 代打木托件数
		bean.setSalverGoodsNum(new Integer(StringUtil.defaultIfEmpty(yoke.txtSalverGoodsPieces.getText(),"0")));
		// 打木托要求
		bean.setSalverRequirement(yoke.txtSalverRequire.getText());
		// 前一次打木托货物费用
		if(bean.isSubPreSalverCharge())
			bean.setPreSalverGoodsCharge(bean.getSalverGoodsCharge());
		// 设置前一次打木托货物费用未减去
		bean.setSubPreSalverCharge(false);
		// 打木托费用
		bean.setSalverGoodsCharge(new BigDecimal(StringUtil.defaultIfEmpty(yoke.txtSalverGoodsAmount.getText(),"0")));
		//zxy 20131118 ISSUE-4391 end 新增：保存打木托信息到bean
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
        //zxy 20131118 ISSUE-4391 start 新增：打木托信息设置到储运信息
		if(StringUtils.isNotBlank(yoke.txtSalverGoodsPieces.getText()))	
		{
			//木托
			StringBuffer salverGoods = new StringBuffer();
			salverGoods.append(i18n.get("foss.gui.changing.woodYokeEnterAction.salverGoods"));
			salverGoods.append("/");
			salverGoods.append(yoke.txtSalverRequire.getText());
			//新增或替换储运事项中打木托
			addOrReplaceData(bean,i18n.get("foss.gui.changing.woodYokeEnterAction.salverGoods"),salverGoods.toString());
		}else{
			addOrReplaceData(bean,i18n.get("foss.gui.changing.woodYokeEnterAction.salverGoods"),"");
		}
		//zxy 20131118 ISSUE-4391 end 新增：打木托信息设置到储运信息
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
				//zxy 20131118 ISSUE-4391 start 修改：增加为空判断，避免无内容只显示分号
				if(StringUtils.isNotBlank(remark[i])){
				transportationRemark.append(remark[i]);
				transportationRemark.append(";");
				}
				//zxy 20131118 ISSUE-4391 end 修改：增加为空判断，避免无内容只显示分号
			}
			//设置重新拼装的储运事项
			bean.setTransportationRemark(transportationRemark.toString());
		}else
		{
			
			//zxy 20131118 ISSUE-4391 start 修改：增加为空判断，避免无内容只显示分号
			if(StringUtils.isNotBlank(data)){
			String transportationRemark = bean.getTransportationRemark();
			//将打木架或者打木箱的信息添加到储运的末尾
			transportationRemark = transportationRemark + data +";";
			//设置重新拼装的储运事项
			bean.setTransportationRemark(transportationRemark);
		}
		//zxy 20131118 ISSUE-4391 end 修改：增加为空判断，避免无内容只显示分号
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