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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/WoodYokeEnterAction.java
 * 
 * FILE NAME        	: WoodYokeEnterAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.vo.LabeledGoodEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.customer.EnterYokeInfoDialog;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * 
 * 运单WoodYokeEnterAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class WoodYokeEnterAction extends
		AbstractButtonActionListener<EnterYokeInfoDialog> {
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(WoodYokeEnterAction.class);
	
	//打木架录入界面
	private EnterYokeInfoDialog yoke;

	private static final double ZEROPOINTZEROONE=0.01;
	/**
	 * 
	 * <p>
	 * 录入打木架信息
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try
		{
			//运单UI
			WaybillEditUI waybillEditUI;
			//获取运单UI
			waybillEditUI = yoke.getWaybillEditUI();
			//通过运单UI对象获取绑定对象集合
			HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI
					.getBindersMap();
			//获取绑定对象
			IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
			//获取到与运单UI绑定的VO对象
			WaybillPanelVo bean = waybillBinder.getBean();
			//基本规则校验
			validate(bean);
			
			woodYokeEnter(bean);
		}catch(WaybillValidateException w)
		{
			MsgBox.showInfo(w.getMessage());
		}

	}
	
	/**
	 * 
	 * 数据校验
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午02:32:06
	 * 版本    时间      用户      内容
	 * 0002   20131225   zxy 	   修改方法参数-体积计算MANA-381
	 */
	private void validate(WaybillPanelVo bean)
	{
		//获取打木架件数
		String yokeGoodsPieces = yoke.getTxtYokeGoodsPieces().getText().trim();
		//获取打木架体积
		String yokeGoodsVolume = yoke.getTxtYokeGoodsVolume().getText().trim();
		//获取打木架要求
		String yokeRequire = yoke.getTxtYokeRequire().getText().trim();
		
		//判断打木架体积是否为空，如果不为空，则打木架件数和要求也不能为空
		if(!StringUtils.isEmpty(yokeGoodsVolume))
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
		
		//获取打木箱件数
		String boxGoodsPieces = yoke.getTxtBoxGoodsPieces().getText().trim();
		//代打木箱要求
		String boxRequire = yoke.getTxtBoxRequire().getText().trim();
		//获取大木箱体积
		String boxGoodsVolume = yoke.getTxtBoxGoodsVolume().getText().trim();
		//判断打木箱体积是否为空，如果不为空，则打木箱件数和要求也不能为空
		if(!StringUtils.isEmpty(boxGoodsVolume))
		{
			//打木箱件数不能为空或为0
			if(CommonUtils.strToNum(boxGoodsPieces) == 0){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullBoxGoodsPieces"));
			}
			//打木箱要求不能为空
			if("".equals(boxRequire)){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullBoxGoodsRequire"));
			}
		}
		
		//zxy 20131225 MANA-381 start 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
		int intBoxPieces = CommonUtils.strToNum(boxGoodsPieces);
		int intYokePieces = CommonUtils.strToNum(yokeGoodsPieces);
		//增加货物总件数的健壮性验证
		int goodsQtyTotal = bean.getGoodsQtyTotal() == null ? 0 : bean.getGoodsQtyTotal();
		if((intBoxPieces + intYokePieces) == goodsQtyTotal){
			// 打木架体积
			BigDecimal decYokeGoodsVolume = new BigDecimal(notNull(yoke.getTxtYokeGoodsVolume().getText()));
			// 打木箱体积
			BigDecimal decBoxGoodsVolume = new BigDecimal(notNull(yoke.getTxtBoxGoodsVolume().getText()));
			BigDecimal decMoodenGoodsValumn = decYokeGoodsVolume.add(decBoxGoodsVolume);
			if(null!=bean.getGoodsVolumeTotal()){
			if(decMoodenGoodsValumn.add(new BigDecimal(ZEROPOINTZEROONE)).compareTo(bean.getGoodsVolumeTotal()) < 0 
					|| decMoodenGoodsValumn.subtract(new BigDecimal(ZEROPOINTZEROONE)).compareTo(bean.getGoodsVolumeTotal()) > 0){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.notEqualVolumn"));
				}
			}
		}
		//zxy 20131225 MANA-381 end 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
		
		//zxy 20131118 ISSUE-4391 start 新增：木托数据验证
		//获取打托件数
		String salverGoodsPieces = yoke.getTxtSalverGoodsPieces().getText().trim();
		// 代打木托要求
		String salverRequire = yoke.getTxtSalverRequire().getText().trim();
		// 获取打木托费用
		String salverGoodsAmount = notNull(yoke.getTxtSalverGoodsAmount().getText().trim());
		// 判断打木托件数是否为空，如果不为空，则打木箱费用和要求也不能为空
		if (!StringUtils.isEmpty(salverGoodsPieces)) {
			// 打木托件数不能为空或为0
			if (CommonUtils.strToNum(salverGoodsPieces) <= 0) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullSalverGoodsPieces"));
			}
			// 打木托费用不能为空或为0
			if (salverGoodsAmount == null || (new BigDecimal(salverGoodsAmount)).compareTo(BigDecimal.ZERO) == 0) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullSalverGoodsAmount"));
			}
			// 打木托要求不能为空
			if ("".equals(salverRequire)) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.nullSalverGoodsRequire"));
			}
			//验证是否小于最低一票
			if(!yoke.validSalverMinFee(salverGoodsAmount, salverGoodsPieces)){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.minSalverGoodsAmount"));
			}
			//验证是否大于最高一票
			if(!yoke.validSalverMaxFee(salverGoodsAmount, salverGoodsPieces)){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.maxSalverGoodsAmount"));
			}
		}
		//zxy 20131118 ISSUE-4391 end 新增：木托数据验证
		
		//验证木架
		validateYokeNull();
		//验证木箱
		validateBoxNull();
		
		//lianhe -- 验证页面是否填写木和托信息,页面输入木和托均为0时,清空木包装信息
		validateWoodAndSalver(bean, yokeGoodsPieces, boxGoodsPieces,
				salverGoodsPieces);
	}

	/**
	 * lianhe -- 验证页面是否填写木和托信息,页面输入木和托均为0时,清空木包装信息
	 */
	private void validateWoodAndSalver(WaybillPanelVo bean,
			String yokeGoodsPieces, String boxGoodsPieces,
			String salverGoodsPieces) {
		if ((bean.getWood() == null||bean.getWood() == 0) && 
				(bean.getSalver() == null || bean.getSalver()== 0)) {
			//确认是否输入了木包装信息
			if (CommonUtils.strToNum(yokeGoodsPieces) > 0 || CommonUtils.strToNum(boxGoodsPieces) > 0
					|| CommonUtils.strToNum(salverGoodsPieces) > 0) {
				//清空木包装信息
				Common.unsetWaybillPanelVoForWoodenPack(bean, yoke.getWaybillEditUI());
				Common.unsetStorageMatterForWoodenPack(bean, yoke.getWaybillEditUI());
				Common.unsetWoodenPackFee(bean);
				//提示
				throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.NoWoodAndSalver"));
			}
			
		}
	}
	
	/**
	 * 验证打木架尺寸输入的规范性
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-8-11
	 */
	private void validateYokeSize()
	{
		//获取打木架尺寸
		String yokeSize = yoke.getTxtYokeGoodsSize().getText().trim();
		if (!NumberValidate.checkIsGoodsSize(yokeSize)) {
			StringBuffer str = new StringBuffer(i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
			str.append("(\n").append(i18n.get("foss.gui.creating.waybillDescriptor.example"));
			str.append("：0.5*0.5*0.5*2").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
			str.append("0.5*0.5*0.5*2+1*1*1*5").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
			str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
			throw new WaybillValidateException(str.toString());
		}
	}
	
	/**
	 * 验证打木架尺寸输入的规范性
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-8-11
	 */
	private void validateBoxSize()
	{
		//获取打木箱尺寸
		String boxSize = yoke.getTxtBoxGoodsSize().getText().trim();
		if (!NumberValidate.checkIsGoodsSize(boxSize)) {
			StringBuffer str = new StringBuffer(i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
			str.append("(\n").append(i18n.get("foss.gui.creating.waybillDescriptor.example"));
			str.append("：0.5*0.5*0.5*2").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
			str.append("0.5*0.5*0.5*2+1*1*1*5").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
			str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
			throw new WaybillValidateException(str.toString());
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
		String yokeGoodsPieces = yoke.getTxtYokeGoodsPieces().getText().trim();
		//获取打木架体积
		String yokeGoodsVolume = yoke.getTxtYokeGoodsVolume().getText().trim();
		//获取打木架尺寸
		String yokeSize = yoke.getTxtYokeGoodsSize().getText().trim();
		
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
		String boxGoodsPieces = yoke.getTxtBoxGoodsPieces().getText().trim();
		//获取打木箱体积
		String boxGoodsVolume = yoke.getTxtBoxGoodsVolume().getText().trim();
		//获取打木箱尺寸
		String boxSize = yoke.getTxtBoxGoodsSize().getText().trim();
		
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
	 * 
	 * @author 025000-FOSS-helong
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-10-30 上午11:53:44
	 */
	private void woodYokeEnter(WaybillPanelVo bean) {
		// 打木架件数
		int yokeGoodsPieces = Common.nullOrEmptyToInt(yoke.getTxtYokeGoodsPieces().getText());
		// 打木箱件数
		int boxGoodsPieces = Common.nullOrEmptyToInt(yoke.getTxtBoxGoodsPieces().getText());
//		// 木包装数
//		int woodPackPieces =  Common.nullOrEmptyToInt(bean.getWood());
		//打木架件数+大木箱件数之和必须大于等于木包装件数
//		if ((yokeGoodsPieces + boxGoodsPieces)<= woodPackPieces) {
			// 打木架体积
			BigDecimal yokeGoodsVolume = new BigDecimal(notNull(yoke.getTxtYokeGoodsVolume().getText()));
			// 打木箱体积
			BigDecimal boxGoodsVolume = new BigDecimal(notNull(yoke.getTxtBoxGoodsVolume().getText()));
			//打木托件数
			BigDecimal salverGoodsPieces = new BigDecimal(notNull(yoke.getTxtSalverGoodsPieces().getText()));
			Object[] salverValues = yoke.listSalver.getSelectedValues();
			// 开单总体积
			BigDecimal volume = bean.getGoodsVolumeTotal();				
			if(yoke.getWaybillEditUI().getPictureWaybillType() != null && 
					WaybillConstants.WAYBILL_PICTURE.equals(yoke.getWaybillEditUI().getPictureWaybillType().trim())){
				if(volume == null){
					volume = BigDecimal.ZERO;
				}
			}else{
				if(volume==null){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.six"));
				}
				}
				//zxy 20131118 ISSUE-4391 start 新增：设置木托数据到bean中
				if(salverValues != null && (salverGoodsPieces.compareTo(new BigDecimal(salverValues.length)) > 0)){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.eight"));
				}
				if(salverValues != null && salverValues.length > 0 && (salverGoodsPieces.compareTo(BigDecimal.ZERO) <= 0)){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.night"));
				}
				//流水号类型置空
				Common.refreshLabeledPackageType(bean);
				if(salverValues != null){
					//打木架+打木箱
					int allGoodsPieces = yokeGoodsPieces + boxGoodsPieces;
					boolean bShowConfirm = false;
					StringBuffer sbLabels = new StringBuffer();
					for(int i = 0; i < salverValues.length; i++){
						LabeledGoodEntityVo vo = (LabeledGoodEntityVo)salverValues[i];
						vo.getEntity().setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
						//温馨提示
						if(Integer.parseInt(vo.getEntity().getSerialNo()) <=  allGoodsPieces){
							if(sbLabels.length() <= 0){
								sbLabels.append(Integer.parseInt(vo.getEntity().getSerialNo()));
							}else
								sbLabels.append(",").append(Integer.parseInt(vo.getEntity().getSerialNo()));
							if(!bShowConfirm)
								bShowConfirm = true;
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
						MsgBox.showInfo(i18n.get("foss.gui.creating.woodYokeEnterAction.msgBox.labelText")+sbLabelsStr+i18n.get("foss.gui.creating.woodYokeEnterAction.msgBox.salverConfirm"));
					}
				}
				//zxy 20131118 ISSUE-4391 end 新增：设置木托数据到bean中
				
				// 打木架体积+打木箱体积*参数值必须要小于等于开单总体积
				BigDecimal result = yokeGoodsVolume.add(boxGoodsVolume);
				if(result!=null){
					if (volume.compareTo(result) >= 0) {
						setWaybillPanelVo(bean);
						setStorageMatter(bean);
						if(yoke.getWaybillEditUI().getPictureWaybillType() != null && 
								WaybillConstants.WAYBILL_PICTURE.equals(yoke.getWaybillEditUI().getPictureWaybillType().trim())){
							setPackageMatter(bean);
						}
						MsgBox.showInfo(i18n.get("foss.gui.creating.woodYokeEnterAction.msgBox.inputCompleted"));
						yoke.dispose();
					} else {
						throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.overYokeGoodsVolume.one"));
					}	
				}			
			
//		} else {
//			throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.overWoodPackPieces"));
//		}
	}

	//设置包装备注
	private void setPackageMatter(WaybillPanelVo bean){
		String yokeRequire = yoke.getTxtYokeRequire().getText();
		String boxRequire = yoke.getTxtBoxRequire().getText();
		String salverRequire = yoke.getTxtSalverRequire().getText();
		String salverGoodsAmount = yoke.getTxtSalverGoodsAmount().getText();
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(yokeRequire.trim()))
		{
			sb.append(i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"));
			sb.append("/");
			sb.append(yokeRequire);
			sb.append(";");
		}
		
		if(StringUtils.isNotBlank(boxRequire.trim()))	
		{
			sb.append(i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"));
			sb.append("/");
			sb.append(boxRequire);
			sb.append(";");
		}
		
		if(StringUtils.isNotBlank(salverRequire.trim()))	
		{
			sb.append(i18n.get("foss.gui.creating.woodYokeEnterAction.salverGoods"));
			sb.append("/");
			sb.append(salverRequire);
			sb.append(";");
		}
		bean.setPackageRemark(sb.toString());
		if(StringUtils.isNotBlank(salverGoodsAmount)){
			bean.setPackageFee(new BigDecimal(salverGoodsAmount));
		}else{
			bean.setPackageFee(BigDecimal.ZERO);
		}
		
	}
	/**
	 * 
	 * 设置木架信息到运单VO中
	 * 
	 * @author 025000-FOSS-helong
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-11-5 上午08:24:15
	 */
	private void setWaybillPanelVo(WaybillPanelVo bean){
		// ===========lianhe/非木包装费/2017年1月4日/start=======
		//非木包装费
		bean.setNonWoodPackingAmount(Common.nullOrEmptyToBigDecimal(yoke.getNonWoodPackingAmount().getText()));
		// ===========lianhe/非木包装费/2017年1月4日/end=======
		// 打木架货物件数
		bean.setStandGoodsNum(Common.nullOrEmptyToInt(yoke.getTxtYokeGoodsPieces().getText()));
		// 代打木架要求
		bean.setStandRequirement(yoke.getTxtYokeRequire().getText());
		// 打木架货物尺寸
		bean.setStandGoodsSize(yoke.getTxtYokeGoodsSize().getText());
		// 打木架货物体积
		bean.setStandGoodsVolume(Common.nullOrEmptyToBigDecimal(yoke.getTxtYokeGoodsVolume().getText()));
		// 打木箱货物件数
		bean.setBoxGoodsNum(Common.nullOrEmptyToInt(yoke.getTxtBoxGoodsPieces().getText()));
		// 代打木箱要求
		bean.setBoxRequirement(yoke.getTxtBoxRequire().getText());
		// 打木箱货物尺寸
		bean.setBoxGoodsSize(yoke.getTxtBoxGoodsSize().getText());
		// 打木箱货物体积
		bean.setBoxGoodsVolume(Common.nullOrEmptyToBigDecimal(yoke.getTxtBoxGoodsVolume().getText()));
		//zxy 20131118 ISSUE-4391 start 新增：设置木托数据到bean中
		// 代打木托件数
		bean.setSalverGoodsNum(Common.nullOrEmptyToInt(yoke.getTxtSalverGoodsPieces().getText()));
		// 打木托要求
		bean.setSalverRequirement(yoke.getTxtSalverRequire().getText());
		//前一次打木托货物费用
		if(bean.isSubPreSalverCharge())
			bean.setPreSalverGoodsCharge(bean.getSalverGoodsCharge());
		//前一次打木托货物费用未减去
		bean.setSubPreSalverCharge(false);
		// 打木托费用
		bean.setSalverGoodsCharge(Common.nullOrEmptyToBigDecimal(yoke.getTxtSalverGoodsAmount().getText()));
		//zxy 20131118 ISSUE-4391 end 新增：设置木托数据到bean中
	}

	
	/**
	 * 
	 * 如果为空则用0替换
	 * @author 025000-FOSS-helong
	 * @date 2012-11-30 下午05:43:43
	 */
	private String notNull(String str)
	{
		if(str == null || "".equals(str))
		{
			return "0";
		}else
		{
			return str;
		}
		
	}
	
	/**
	 * 
	 * 设置储运事项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-2 上午08:44:42
	 */
	private void setStorageMatter(WaybillPanelVo bean){

		/**
		 * 判断木架是否为空并且木架件数是否为0
		 */
		if(StringUtils.isNotEmpty(yoke.getTxtYokeGoodsVolume().getText()) && !"0".equals(yoke.getTxtYokeGoodsPieces().getText()))
		{
			//木架
			StringBuffer standGoods = new StringBuffer();
			standGoods.append(i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"));
			standGoods.append("/");
			standGoods.append(yoke.getTxtYokeRequire().getText());
			//新增或替换储运事项中打木架、大木箱信息
			addOrReplaceData(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods"),standGoods.toString());
		}
		
		if(StringUtils.isNotEmpty(yoke.getTxtBoxGoodsVolume().getText()) && !"0".equals(yoke.getTxtBoxGoodsPieces().getText()))	
		{
			//木箱
			StringBuffer boxGoods = new StringBuffer();
			boxGoods.append(i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"));
			boxGoods.append("/");
			boxGoods.append(yoke.getTxtBoxRequire().getText());
			//新增或替换储运事项中打木架、大木箱信息
			addOrReplaceData(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods"),boxGoods.toString());
		}
		
		//zxy 20131118 ISSUE-4391 start 新增：设置木托数到储运信息
		if(StringUtils.isNotBlank(yoke.getTxtSalverGoodsPieces().getText()))	
		{
			//木托
			StringBuffer salverGoods = new StringBuffer();
			salverGoods.append(i18n.get("foss.gui.creating.woodYokeEnterAction.salverGoods"));
			salverGoods.append("/");
			salverGoods.append(yoke.getTxtSalverRequire().getText());
			//新增或替换储运事项中打木托
			addOrReplaceData(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.salverGoods"),salverGoods.toString());
		}else{
			//新增或替换储运事项中打木托
			addOrReplaceData(bean,i18n.get("foss.gui.creating.woodYokeEnterAction.salverGoods"),"");
		}
		//zxy 20131118 ISSUE-4391 end 新增：设置木托数到储运信息
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
			String transportationRemark = bean.getTransportationRemark();
			//将打木架或者打木箱的信息添加到储运的末尾
			//zxy 20131118 ISSUE-4391 start 修改：增加为空判断，避免无内容只显示分号
			if(StringUtils.isNotBlank(data)){
				if(transportationRemark!=null){
					transportationRemark = transportationRemark + data +";";
				}else{
					transportationRemark = data +";";
				}
			}
			//zxy 20131118 ISSUE-4391 end 修改：增加为空判断，避免无内容只显示分号
			
			//设置重新拼装的储运事项
			bean.setTransportationRemark(transportationRemark);
		}
	}

	/**
	 * 
	 * 设置打木架录入对象
	 * @author 025000-FOSS-helong
	 * @date 2013-3-17 下午07:38:03
	 * @param yoke
	 */
	@Override
	public void setIInjectUI(EnterYokeInfoDialog yoke) {
		this.yoke = yoke;
	}

}