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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/AbandonedGoodsImpotBillAction.java
 * 
 * FILE NAME        	: AbandonedGoodsImpotBillAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.AbandonedGoodsHandleUI;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;

/**
 * 
 * <p>
 * 弃货计算及提交action
 * </p>
 * @title AbandonedGoodsImpotBillAction.java
 * @package com.deppon.foss.module.pickup.creating.client.action 
 * @author suyujun
 * @version 0.1 2012-12-17
 */
public class AbandonedGoodsImpotBillAction implements IButtonActionListener<AbandonedGoodsHandleUI> {
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(AbandonedGoodsImpotBillAction.class);
	
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(AbandonedGoodsImpotBillAction.class);
	
	/**
	 * ui object
	 */
	private AbandonedGoodsHandleUI ui;
	/**
	 * 开单service object
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	/**
	 * "导入内部开单"按钮事件
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param e
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			List<AbandonedGoodsDto> selectedList = ui.getTableModel().getSelectedValues();
			ImportWaybillEditUIAction waybillUI = new ImportWaybillEditUIAction();//创建导入对象
			waybillUI.importWaybillEditUI(getWaybillPanelVo(selectedList));//根据selectedList结果生成WaybillPanelVo运单对象
		}catch (BusinessException ex) {
			LOGGER.error("导入异常", e);
			MsgBox.showError(i18n.get("foss.gui.creating.abandonedGoodsImpotBillAction.messageDialog.importAbnormal"+"：")
					+MessageI18nUtil.getMessage(ex, i18n));
		}
	}
	/**
	 * 设置ui
	 */
	public void setInjectUI(AbandonedGoodsHandleUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 根据selectedList结果生成WaybillPanelVo运单对象
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param selectedList
	 * @return WaybillPanelVo
	 */
	private WaybillPanelVo getWaybillPanelVo(List<AbandonedGoodsDto> selectedList){
		WaybillPanelVo waybillPanelVo = new WaybillPanelVo();
		//Don't create instances of already existing BigInteger and BigDecimal ZERO
		BigDecimal defaultValue = BigDecimal.ZERO;
		BigDecimal totalWeight = BigDecimal.ZERO;
		BigDecimal totalVolume  = BigDecimal.ZERO;
		//性能 - 方法调用低效的数字构造方法;使用静态valueOf代替   
		Integer totalPieces  = Integer.valueOf(0);
		BigDecimal toPayAmount  = BigDecimal.ZERO;
		BigDecimal prePayAmount  = BigDecimal.ZERO;
		//内部带货储运事项
		StringBuffer notesString = new StringBuffer(i18n.get("foss.gui.creating.abandonedGoodsImpotBillAction.messageDialog.numberOfAbandonedGoods"));
		//获得总体积、重量、件数   循环拼接储运事项
		for(AbandonedGoodsDto dto : selectedList){
			totalPieces += dto.getPieces();
			totalVolume = totalVolume.add(dto.getVolume() == null ? BigDecimal.ZERO:dto.getVolume());
			totalWeight = totalWeight.add(dto.getWeight() == null ? BigDecimal.ZERO:dto.getWeight());
			toPayAmount = toPayAmount.add(dto.getToPayamount() == null ? BigDecimal.ZERO:dto.getToPayamount());
			prePayAmount = prePayAmount.add(dto.getPrePayAmount() == null ? BigDecimal.ZERO:dto.getPrePayAmount());
			notesString.append(dto.getWaybillNo());
			notesString.append(",");
		}
		//判断是否为空
		int index = notesString.lastIndexOf(",");
		if(index>=0){
			notesString.deleteCharAt(index);
		}
		notesString.append(";");
		//设置储运事项
		waybillPanelVo.setTransportationRemark(notesString.toString());
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity deliveryOrgEntity = user.getEmployee().getDepartment();
		/**
		 * 体积、重量 、件数
		 */
		waybillPanelVo.setGoodsVolumeTotal(totalVolume);
		waybillPanelVo.setGoodsQtyTotal(totalPieces);
		waybillPanelVo.setPaper(totalPieces);
		waybillPanelVo.setGoodsWeightTotal(totalWeight);
		waybillPanelVo.setIsWholeVehicle(false);
		waybillPanelVo.setPickupCentralized(false);
		/**
		 * 发货信息
		 */
		if(deliveryOrgEntity!= null){
			waybillPanelVo.setDeliveryCustomerName(deliveryOrgEntity.getName());  //set 发货客户名称
			waybillPanelVo.setDeliveryCustomerPhone(deliveryOrgEntity.getDepTelephone());  // set 发货客户电话
			waybillPanelVo.setDeliveryCustomerContact(deliveryOrgEntity.getName());  //set 发货客户联系人
			waybillPanelVo.setDeliveryCustomerAddress(deliveryOrgEntity.getAddress()); // set 发货具体地址
			waybillPanelVo.setDeliveryCustomerContactId(deliveryOrgEntity.getCode()); // set 发货客户联系人ID
			//收货部门
			waybillPanelVo.setReceiveOrgCode(deliveryOrgEntity.getCode());
			//货部门省份编码
			if(StringUtil.isNotEmpty(deliveryOrgEntity.getCode())){
			waybillPanelVo.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(deliveryOrgEntity.getCode()));
			}
		}
		//到达部门：大区办公室
		OrgAdministrativeInfoEntity receiverOrgEntity = waybillService.queryBigRegionOfCurrDept();
		if(receiverOrgEntity!=null){
			String orgCode = receiverOrgEntity.getCode();
			if(orgCode!=null){
				
				SaleDepartmentEntity org = waybillService.querySaleDeptByCode(orgCode);
				
				if(org!=null){
					//始发网点 create time
					waybillPanelVo.setReceiveOrgName(org.getName());
					if(org.getOpeningDate()!=null){
						waybillPanelVo.setReceiveOrgCreateTime(org.getOpeningDate());
					}
				}
			}
		
			/**
			 * 收货客户信息
			 */
			waybillPanelVo.setReceiveCustomerAddress(receiverOrgEntity.getAddress());
			waybillPanelVo.setReceiveCustomerContact(receiverOrgEntity.getName());//收货部门名称
			waybillPanelVo.setReceiveCustomerName(receiverOrgEntity .getName());//收货部门名称
			//设置目的站  组织名称
			waybillPanelVo.setTargetOrgCode(receiverOrgEntity.getName());
			//设置发货客户ID
			waybillPanelVo.setReceiveCustomerId(receiverOrgEntity.getCode());
			//收货部门电话
			waybillPanelVo.setReceiveCustomerPhone(receiverOrgEntity.getDepTelephone());
			
		}
		//到付金额
		waybillPanelVo.setToPayAmount(toPayAmount);
		//预付金额
		waybillPanelVo.setPrePayAmount(prePayAmount);
		/**
		 * 提货方式
		 */
		DataDictionaryValueVo receiveMethod = new DataDictionaryValueVo();
		receiveMethod.setValueCode(WaybillConstants.INNER_PICKUP);//汽运内部自提
		receiveMethod.setValueName(i18n.get("foss.gui.creating.abandonedGoodsImpotBillAction.messageDialog.receiveMethodValue"));//汽运内部自提
		waybillPanelVo.setReceiveMethod(receiveMethod);//提货方式
		/**
		 * 增值业务信息默认值
		 */
		/*
		waybillPanelVo.setInsuranceAmount(defaultValue);  //保价申明价值
		waybillPanelVo.setPackageFee(defaultValue); //包装费
		waybillPanelVo.setDeliveryGoodsFee(defaultValue); //送货费
		waybillPanelVo.setServiceFee(defaultValue); //装卸费
		waybillPanelVo.setPickupFee(defaultValue); //接货费
		waybillPanelVo.setTransportFee(defaultValue); //公布价运费
		 */		
		waybillPanelVo.setCodAmount(defaultValue);  //代收货款
		
		
		DefaultComboBoxModel pikcModeModel = new DefaultComboBoxModel();
		List<DataDictionaryValueEntity> list = waybillService
				.queryPickUpGoodsHighWays();
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			pikcModeModel.addElement(vo);
		}
		
		for (int i = 0; i < pikcModeModel.getSize(); i++) {
			DataDictionaryValueVo vo =(DataDictionaryValueVo) pikcModeModel.getElementAt(i);
			if (WaybillConstants.SELF_PICKUP.equals(vo.getValueCode())) {
				waybillPanelVo.setReceiveMethod(vo);
			}
		}
		
		UserEntity userEntity = (UserEntity)SessionContext.getCurrentUser();
		
		
		DefaultComboBoxModel productTypeModel= new DefaultComboBoxModel();
		List<ProductEntity> list2 = waybillService.queryTransType(userEntity.getEmployee().getOrgCode());
		for (ProductEntity product : list2) {
			if(!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode()))
			{
				ProductEntityVo vo = new ProductEntityVo();
				//将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo);
				vo.setDestNetType(product.getDestNetType());
				productTypeModel.addElement(vo);
			}
		}
		for (int i = 0; i < productTypeModel.getSize(); i++) {
			ProductEntityVo vo = (ProductEntityVo) productTypeModel.getElementAt(i);
			//默认设置为精准卡航
			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(vo.getCode())) {
				waybillPanelVo.setProductCode(vo);
			}
		}
		List<DataDictionaryValueEntity> returnBillTypes = waybillService
				.queryReturnBillType();
		DefaultComboBoxModel combReturnBillTypeModel = new DefaultComboBoxModel();
		for (DataDictionaryValueEntity dataDictionary : returnBillTypes) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			ValueCopy.valueCopy(dataDictionary, vo);
			combReturnBillTypeModel.addElement(vo);
		}
		for (int i = 0; i < combReturnBillTypeModel.getSize(); i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combReturnBillTypeModel.getElementAt(i);
			if (WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				waybillPanelVo.setReturnBillType(vo);
			}
		}
		
		//弃货处理
		waybillPanelVo.setWaybillstatus(WaybillConstants.WAYBILL_STATUS_GOODS_PENDING);
		return waybillPanelVo;
	}
}