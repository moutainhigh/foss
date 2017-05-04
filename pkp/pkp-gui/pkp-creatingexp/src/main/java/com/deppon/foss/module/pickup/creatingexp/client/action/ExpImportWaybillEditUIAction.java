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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/ImportWaybillEditUIAction.java
 * 
 * FILE NAME        	: ImportWaybillEditUIAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.listener.ExpWaybillBindingListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 打开运单新增界面的类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-12-17 下午5:59:06
 */
public class ExpImportWaybillEditUIAction {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpImportWaybillEditUIAction.class);

	/**
	 * 运单服务
	 * 提供运单相关的本地服务接口
	 */
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 离线暂存
	 * 提供运单离线暂存相关的本地服务接口
	 */
	private IWaybillOffLinePendingService waybillOffLinePendingService = GuiceContextFactroy.getInjector().getInstance(WaybillOffLinePendingService.class);

	/**
	 * 国际化
	 * 提供i18n国际化相关的服务接口
	 */
	private static II18n i18n = I18nManager.getI18n(ExpImportWaybillEditUIAction.class);

	/**
	 * 导入弃货运单开内部带货运单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-17 下午6:01:39
	 */
	public void importWaybillEditUI(Object object) {
		
		//异常处理
		try {
			if (null == object) {
				throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullObject"));
			}

			// 运单VO对象
			ExpWaybillPanelVo value = null;
			// 待处理运单对象
			WaybillPendingEntity entity = null;
			// 运单已补录对象
			WaybillDto dto = null;

			// 对传入的数据进行转换
			if (object instanceof ExpWaybillPanelVo) {
				value = (ExpWaybillPanelVo) object;
				// 校验数据合法性
				if ("".equals(StringUtil.defaultIfNull(value.getWaybillstatus()))) {
					throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullWaybillSupType.one"));
				} else {
					importWaybillVoData(value);
				}
			} else if (object instanceof WaybillPendingEntity) {
				entity = (WaybillPendingEntity) object;
				// 校验数据合法性
				if ("".equals(StringUtil.defaultIfNull(entity.getPendingType()))) {
					throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullWaybillSupType.two"));
				} else {
					importWaybillPendingData(entity);
				}
			} else if (object instanceof WaybillDto) {// 导入已开运单 信息
				dto = (WaybillDto) object;
				WaybillEntity waybillEntity = dto.getWaybillEntity();
				// 非空判断
				if (null == waybillEntity) {
					throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullWaybillEntity"));
				}
				// 校验数据合法性
				if ("".equals(StringUtil.defaultIfNull(waybillEntity.getPendingType()))) {
					throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullWaybillSupType.two"));
				} else {
					importWaybillData(dto);
				}
			} else {
				throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.errorDataType"));
			}
		} catch (BusinessException ex) {
			LOGGER.error("导入异常", ex);			
			if (StringUtils.isNotEmpty(ex.getMessage())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));
			}else if(StringUtils.isNotEmpty(ex.getErrorCode())){
			    MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+ex.getErrorCode());
			}
		}
		
	}

	/**
	 * 导入弃货运单开内部带货运单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-17 下午6:01:39
	 */
	public void importOFFLINEWaybillEditUI(WaybillPendingEntity pendingEntity) {
		// 捕捉抛出的异常信息
		try { 
			// 传入对象的非空判断
			if (null == pendingEntity) {
				throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullObject"));
			} else {
				// 校验数据合法性
				importWaybillPendingData(pendingEntity);
			}
		} catch (BusinessException e) {
			// 添加异常日志
			LOGGER.error("导入异常", e);
			// 抛出异常信息
			throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.inner")+"\n" + e.getMessage() + "\n" + e.getErrorCode());
		}
	}

	/**
	 * 导入待补录运单VO数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午2:01:08
	 */
	private void importWaybillPendingData(WaybillPendingEntity entity) {
		// 对象非空判断
		if (null == entity) {
			return;
		}

		// 定义开单界面
		ExpWaybillEditUI ui = null;
		// 获得业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 是否导入集中接送货开单界面
		if (bu.isBillGroup(entity.getCreateOrgCode())) {
			//获得集中接送货开单界面 
			ui = this.getFocusWaybillEditUI();
		} else {
			//获得非集中接送货开关界面 
			ui = this.getWaybillEditUI();
		}

		//获得绑定的VO对象
		ExpWaybillPanelVo vo = ExpCommon.getVoFromUI(ui);
		
		if(FossConstants.NO.equals(entity.getIsBig13())){
			vo.setIsNextDayPending("当日补录");
		}else{
			vo.setIsNextDayPending("次日补录");
		}
		
		//设定暂存运单补录时，是否勾选上门发货
		if(WaybillConstants.YES.equals(entity.getHomeDelivery())){
			vo.setHomeDelivery(true);
		}else{
			vo.setHomeDelivery(false);
		}
		
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(entity.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(entity.getReceiveBigCustomer());
		//设置发货地址备注
		vo.setDeliveryCustomerAddressNote(entity.getDeliveryCustomerAddressNote());
		//设置收货地址备注
		vo.setReceiveCustomerAddressNote(entity.getReceiveCustomerAddressNote());
		CrmOrderDetailDto orderDetailVo = null;
		if(StringUtils.isNotEmpty(entity.getOrderNo())){
			orderDetailVo = waybillService.importOrder(StringUtil.defaultIfNull(entity.getOrderNo()));
		}
		
		String deliverCustomerCode = orderDetailVo == null ? entity.getDeliveryCustomerCode() : orderDetailVo.getShipperNumber();
		String receiverCustomerCode = orderDetailVo == null ? entity.getReceiveCustomerCode() : orderDetailVo.getReceiverCustNumber();
		
		/**
		 * @author 200945-wutao
		 * 设置发货客户
		 */
		// 查询条件
		List<CustomerQueryConditionDto> dtoList = new ArrayList<CustomerQueryConditionDto>();
		// 判断编码是否为空
		if (StringUtils.isNotEmpty(deliverCustomerCode)) {
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			dto.setCustCode(deliverCustomerCode);
			dto.setExactQuery(true);
			dtoList.add(dto);
			CustomerQueryConditionDto customerQueryConditionDtoDev = null;
			List<CustomerQueryConditionDto> customerQueryConditionDtoDevList =  waybillService.queryCustomerByConditionList(dtoList);//customerService.queryCustomerByCondition(customerQueryConditionDtoReceiver);
			if (CollectionUtils.isNotEmpty(customerQueryConditionDtoDevList)) {
				customerQueryConditionDtoDev = customerQueryConditionDtoDevList.get(0);
				if(StringUtils.isNotEmpty(deliverCustomerCode)){
					CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
					contact.setCustCode(deliverCustomerCode);
					CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
					ExpCommonUtils.setExpDevliveryCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, vo);
				}
			}
		}
		
		/**
		 * 设置收货客户
		 */
		List<CustomerQueryConditionDto> dtoRecList = new ArrayList<CustomerQueryConditionDto>();
		// 判断编码是否为空
		if (StringUtils.isNotEmpty(receiverCustomerCode)) {
			CustomerQueryConditionDto dtoRec = new CustomerQueryConditionDto();
			dtoRec.setCustCode(receiverCustomerCode);
			dtoRec.setExactQuery(true);
			dtoRecList.add(dtoRec);
			//直接放到里面进行查询，否则查询全公司所有合同
			CustomerQueryConditionDto customerQueryConditionDtoRev = null;
			List<CustomerQueryConditionDto> customerQueryConditionDtoReceivercList =  waybillService.queryCustomerByConditionList(dtoRecList);//customerService.queryCustomerByCondition(customerQueryConditionDtoReceiver);
			if (CollectionUtils.isNotEmpty(customerQueryConditionDtoReceivercList)) {
				customerQueryConditionDtoRev = customerQueryConditionDtoReceivercList.get(0);
				if(StringUtils.isNotEmpty(receiverCustomerCode)){
					CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
					contact.setCustCode(receiverCustomerCode);
					CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
					ExpCommonUtils.setExpReciveCusomterSettler(customerQueryConditionDtoRev, cusBargainEntity, vo);
				}
			}
		}
		
		// 获得运单号
		String waybillNo = StringUtil.defaultIfNull(entity.getWaybillNo());

		// 若是离线导入开单，则根据运单号查询本地离线表中的数据
		if (WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING.equals(entity.getPendingType())) {
			/**
			 * 离线导入时，由于处于在线状态，但查询的是本地离线运单信息，
			 * 故直接调用waybillOffLinePendingService的方法，
			 * 无法使用waybillService.queryPendingWaybill方法
			 */
			WaybillPendingDto waybillDto = waybillOffLinePendingService.queryPendingWaybillByNo(waybillNo);
			setPendingWaybill(vo, ExpCommon.getOrderCustInfo(waybillDto), ui);
			/**
			 * 由于待补录运单是由PDA所开单，故只有提货网点，没走货线路，需要重新生成 设置控件触发事件
			 */
			setTriggerPanel(ui, vo);
		} else {
			/**
			 * 获得完整运单对象信息 PS: 
			 * 1、营业部管理出发运单界面只查询出部分数据，这样提高查询性能，且没有必要全部查询
			 * 2、待要导入开单补录时再将完整待补录运单信息全部查询出来，此需要重新再查询一次 
			 * 3、可以搞高查询速度，减少网络流量
			 */
			WaybillPendingDto waybillDto = waybillService.queryPendingWaybill(waybillNo);
			
			setPendingWaybill(vo, ExpCommon.getOrderCustInfo(waybillDto), ui);
			// 设置其它待处理信息（提货网点、走货线路、整车信息）
			ExpCommon.setOtherPendingData(ui, vo);
		}

		// 创建对象来接收查询出的运单对象
		ExpWaybillPanelVo voimport = new ExpWaybillPanelVo();
		try {
			// 这个是用于提交对比的对象 不能用同一个对象 要对比
			PropertyUtils.copyProperties(voimport, vo);
		} catch (Exception e) {
			LOGGER.error("copyProperties异常", e);
		}
		// 用于在提交的时候比较老新数据的差异
		ui.setImportWaybillPanelVo(voimport);
		//判断是否为离线导入开单
		if(WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING.equals(entity.getPendingType())){
			//设置为可编辑
			ui.buttonPanel.getBtnPrint().setEnabled(true);// 运单打印
			ui.buttonPanel.getBtnPreview().setEnabled(true);// 运单打印预览
			ui.buttonPanel.getBtnPrintLabel().setEnabled(true);// 打印标签
		}
		//判断是否PDA导入运单
		if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(entity.getPendingType())){
			//默认上门接货
			ui.basicPanel.getCboReceiveModel().setSelected(true);
			//快递送货费优化 PDA 补录可手动修改送货费
			ui.getBillingPayPanel().getTxtDeliveryCharge().setEnabled(true);
			ui.getBillingPayPanel().getTxtDeliveryCharge().setEditable(true);
		}
		
		if(vo.getValueAddFee()==null){
			vo.setValueAddFee(BigDecimal.ZERO);
		}
		//liding comment for NCI
		/**
		 * 通过付款方式判断“交易流水号”是否可编辑
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-26上午10:40
		 */
//		ExpCommon.whetherBankCardPayment(vo, ui);
		
		/**
		 * 通过客户编码来检验此客户是否能够上门发货
		 * @author 218459-foss-dongsiwei
		 */
		if(ExpCommon.calculateHomeDelivery(vo)){
			ui.getBasicPanel().getHomeDelivery().setEnabled(true);
		}
	}

	/**
	 * 导入已开单的运单数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午2:01:08
	 */
	private void importWaybillData(WaybillDto dto) {
		if(dto == null){
			return;
		}
		// 获得运单实体对象
		WaybillEntity entity = dto.getWaybillEntity();
		// 对象非空判断
		if (null == entity) {
			return;
		}

		// 定义开单界面
		ExpWaybillEditUI ui = null;
		// 获得业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 是否导入集中接送货开单界面
		if (bu.isBillGroup(entity.getCreateOrgCode())) {
			ui = this.getWaybillEditUI();
		} else {
			ui = this.getWaybillEditUI();
		}
		
		
		/**
		 * 1)在数据设值之前进行界面控件的编辑状态设置，
		 * 是为了避免在设值时出现异常，导致代码无法走到设置界面编辑状态处
		 * 导致界面数据可编辑
		 * 
		 * 2)见BUG-7159 
		 */
		// 若为已开单运单，则设置界面不可编辑
		UIUtils.disableUI(ui);
		ui.buttonPanel.getBtnNew().setEnabled(true);// 新增
		ui.buttonPanel.getBtnPrint().setEnabled(true);// 运单打印
		ui.buttonPanel.getBtnPreview().setEnabled(true);// 运单打印预览
		ui.buttonPanel.getBtnPrintLabel().setEnabled(true);// 打印标签
		ui.buttonPanel.getBtnSearchBranch().setEnabled(false);// 查询网点
		ui.buttonPanel.getBtnGIS().setEnabled(false);//电子地图
		ui.buttonPanel.getBtnSearchPrice().setEnabled(false);// 公布价查询
		ui.getBtnOpenCanvasPanel().setEnabled(true);
		if(null!=dto){
			if(null!=dto.getActualFreightEntity()){
				if(FossConstants.YES.equals(dto.getActualFreightEntity().getPartnerBillingLogo())){
					ui.basicPanel.getPartnerCheckBox().setEnabled(true);
					ui.basicPanel.getPartnerCheckBox().setSelected(true);
					ui.basicPanel.getPartnerName().setEditable(false);
					ui.basicPanel.getPartnerName().setEnabled(false);
					ui.basicPanel.getPartnerName().setVisible(true);
					ui.basicPanel.getPartnerPhone().setEditable(false);
					ui.basicPanel.getPartnerPhone().setEnabled(false);
					ui.basicPanel.getPartnerPhone().setVisible(true);
					ui.basicPanel.getPartnerLabel().setVisible(true);
					ui.basicPanel.getPhomeLabel().setVisible(true);
					if(StringUtil.isNotEmpty(dto.getActualFreightEntity().getPartnerName())){
						ui.basicPanel.getPartnerName().setText(dto.getActualFreightEntity().getPartnerName());
					}
					if(StringUtil.isNotEmpty(dto.getActualFreightEntity().getPartnerPhome())){
						ui.basicPanel.getPartnerPhone().setText(dto.getActualFreightEntity().getPartnerPhome());
					}
				}
			}
		}
		// 获取绑定bean
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo vo = waybillBinder.getBean();
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(entity.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(entity.getReceiveBigCustomer());
		//进行地址备注信息的赋值
		ActualFreightEntity acf = dto.getActualFreightEntity();
		if(acf != null){
			vo.setDeliveryCustomerAddressNote(acf.getDeliveryCustomerAddressNote());
			vo.setReceiveCustomerAddressNote(acf.getReceiveCustomerAddressNote());
			/*
			 * 设置【是否统一结算】 
			 * @author -200945  wutao
			 * 
			 */
			if(acf.getStartCentralizedSettlement()!=null && !"".equals(acf.getStartCentralizedSettlement()) && FossConstants.YES.equals(acf.getStartCentralizedSettlement())){
				vo.setStartCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
			}else{
				vo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			}
			if(acf.getArriveCentralizedSettlement()!=null && !"".equals(acf.getArriveCentralizedSettlement()) && FossConstants.YES.equals(acf.getArriveCentralizedSettlement())){
				vo.setArriveCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
			}else{
				vo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			}
			//【合同部门】
			if(acf.getStartContractOrgCode()!=null && !acf.getStartContractOrgCode().equals("")){
				vo.setStartContractOrgName(CommonUtils.queryContractOrgName(acf.getStartContractOrgCode()));
			}else{
				vo.setStartContractOrgName(null);
			}
			if(acf.getArriveContractOrgCode()!=null && !acf.getArriveContractOrgCode().equals("")){
				vo.setArriveContractOrgName(CommonUtils.queryContractOrgName(acf.getArriveContractOrgCode()));
			}else{
				vo.setArriveContractOrgName(null);
			}
			try {
				vo.setInternalDeliveryType(dataDictionaryValueEntityToVo(
						WaybillConstants.INTERNAL_DELIVERY_TYPE,
						acf.getInternalDeliveryType()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			vo.setEmployeeNo(acf.getEmployeeNo());
			//【催款部门编码】
			vo.setStartReminderOrgCode(acf.getStartReminderOrgCode());
			vo.setArriveReminderOrgCode(acf.getArriveReminderOrgCode());
		}
		setWaybillVo(vo, dto, ui);
		
		//处理内部带货总费用
		processInnerTotal(vo);
		
		/**
		 * 防止在设置值后将编辑状态改变了，故再设置一遍
		 */
		// 若为已开单运单，则设置界面不可编辑
		UIUtils.disableUI(ui);
		ui.buttonPanel.getBtnNew().setEnabled(true);// 新增
		ui.buttonPanel.getBtnPrint().setEnabled(true);// 运单打印
		ui.buttonPanel.getBtnPreview().setEnabled(true);// 运单打印预览
		ui.buttonPanel.getBtnPrintLabel().setEnabled(true);// 打印标签
		ui.buttonPanel.getBtnSearchBranch().setEnabled(false);// 查询网点
		ui.buttonPanel.getBtnGIS().setEnabled(false);//电子地图
		ui.buttonPanel.getBtnSearchPrice().setEnabled(false);// 公布价查询
		ui.getBtnOpenCanvasPanel().setEnabled(true);
	}
	
	//处理内部带货总费用
	private void processInnerTotal(ExpWaybillPanelVo vo){
		//DEFECT-4030 快递内部带货总费用置0
		if(WaybillConstants.DEPPON_CUSTOMER.equals(vo.getDeliveryCustomerCode()) 
				&& vo.getProductCode()!=null && vo.getProductCode().getCode()!= null &&
				(CommonUtils.directDetermineIsExpressByProductCode(vo.getProductCode().getCode()))){
			vo.setTotalFee(BigDecimal.ZERO);
			vo.setTotalFeeCanvas(BigDecimal.ZERO.toString());
		}
	}

	/**
	 * 导入待补录运单基本数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午2:01:06
	 */
	private void importWaybillVoData(ExpWaybillPanelVo value) {
		// 运单界面
		ExpWaybillEditUI ui = this.getWaybillEditUI();
		// 获取绑定bean
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		ExpWaybillPanelVo vo = waybillBinder.getBean();
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(value.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(value.getReceiveBigCustomer());
		//设置发货地址备注
		vo.setDeliveryCustomerAddressNote(value.getDeliveryCustomerAddressNote());
		//设置收货地址备注
		vo.setReceiveCustomerAddressNote(value.getReceiveCustomerAddressNote());
		/**
		 * @author 200945-wutao
		 * 设置发货客户
		 */
		CustomerQueryConditionDto customerQueryConditionDto = new CustomerQueryConditionDto();
		customerQueryConditionDto.setCustCode(value.getReceiveCustomerCode());
		CustomerQueryConditionDto customerQueryConditionDtoDev = null;
		List<CustomerQueryConditionDto> customerQueryConditionDtoDevList =  waybillService.queryCustomerByCondition(customerQueryConditionDto);//customerService.queryCustomerByCondition(customerQueryConditionDtoReceiver);
		if (customerQueryConditionDtoDevList.size() > 0) {
			customerQueryConditionDtoDev = customerQueryConditionDtoDevList.get(0);
			if(null!=value.getReceiveCustomerCode()){
				CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
				contact.setCustCode(value.getReceiveCustomerCode());
				CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
				ExpCommonUtils.setExpReciveCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, vo);
				}
		}
		
		/**
		 * 设置收货客户
		 */
		CustomerQueryConditionDto customerQueryConditionDtoReceiver = new CustomerQueryConditionDto();
		customerQueryConditionDtoReceiver.setCustCode(value.getReceiveCustomerCode());
		CustomerQueryConditionDto customerQueryConditionDtoRev = null;
		List<CustomerQueryConditionDto> customerQueryConditionDtoReceivercList =  waybillService.queryCustomerByCondition(customerQueryConditionDtoReceiver);//customerService.queryCustomerByCondition(customerQueryConditionDtoReceiver);
		if (customerQueryConditionDtoReceivercList.size() > 0) {
			customerQueryConditionDtoRev = customerQueryConditionDtoReceivercList.get(0);
			if(null!=value.getReceiveCustomerCode()){
				CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
				contact.setCustCode(value.getReceiveCustomerCode());
				CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
				ExpCommonUtils.setExpReciveCusomterSettler(customerQueryConditionDtoRev, cusBargainEntity, vo);
				}
		}
		
		// 拷贝数据
		try {
			// 这个是用于提交对比的对象 不能用同一个对象 要对比
			PropertyUtils.copyProperties(vo, value);
		} catch (Exception e) {
			LOGGER.error("copyProperties异常", e);
		}
		// 发货联系人
		String deliveryCustomerContact = vo.getDeliveryCustomerContact();
		// 收货联系人
		String receiveCustomerContact = vo.getReceiveCustomerContact();

		// 设置触发控件
		setTriggerPanel(ui, vo);
		// 添加 发货部门、收货部门联系人
		vo.setDeliveryCustomerContact(deliveryCustomerContact);// 发货联系人
		vo.setReceiveCustomerContact(receiveCustomerContact);// 收货联系人
	}

	/**
	 * 导入或补录时设置需要触发事件的控件值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-25 上午10:21:46
	 */
	private void setTriggerPanel(ExpWaybillEditUI ui, ExpWaybillPanelVo vo) {
		ExpWaybillBindingListener bindingListener = new ExpWaybillBindingListener(ui);
		// 发货人手机
		bindingListener.deliveryCustomerMobilephoneListener(vo);
		// 发货人电话
		bindingListener.deliveryCustomerPhoneListener(vo);
		// 收货人手机
		bindingListener.receiveCustomerMobilephoneListener(vo);
		// 收货人电话
		bindingListener.receiveCustomerPhoneListener(vo);
		// 收货人地址
		bindingListener.receiveCustomerAddressListener(vo);
		// 提货方式监听 因为公用 清空了发货联系人 和收货联系人
		// TODO 小件这里我去掉了  因为会把提货网点清空的
		//bindingListener.receiveMethodListener(vo);
		// 是否开整车
		if (vo.getIsWholeVehicle()) {
		    bindingListener.isWholeVehicleListener(vo);
		}
		// 设置提货网点
		setPickupStationfo(ui, vo);
	}

	/**
	 * 设置提货网点相关信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-1 下午5:48:51
	 */
	public void setPickupStationfo(ExpWaybillEditUI ui, ExpWaybillPanelVo vo) {
		// 判断提货网点是否为空
		if (vo.getCustomerPickupOrgCode() == null) {
			return;
		}
		// 提货网点
		ExpShowPickupStationDialogAction action = new ExpShowPickupStationDialogAction();
		action.setInjectUI(ui);
		BusinessUtils bu = new BusinessUtils();
		BranchVo branchVo = bu.getCustomerPickupOrg(vo.getCustomerPickupOrgCode().getCode(), vo.getProductCode().getCode(),vo.getBillTime());
		action.setDialogData(branchVo, vo);
		// 整车的提货网点不需要设置线路和空运配载及时效
		if (!ui.basicPanel.getChbWholeVehicle().isSelected()) {
			// 设置线路
			action.setLoadLine(vo);
			// 设置空运配载
			action.setAirDeptEnabled(vo);
		}

		/**
		 * 返单费用项在数据字典中，其父类型只对应t_srv_pricing_entry中CODE为VAS，
		 * 而其它费用对应t_srv_pricing_entry中CODE为QT 它们不在同一个表中，但放在一个表格中 故通过事件触发来设置返单费用
		 */
		ExpCommon.setReturnBillCharge(vo, ui);
	}

	/**
	 * 开单运单新增界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-17 下午6:02:03
	 */
	public ExpWaybillEditUI getWaybillEditUI() {
		IApplication application = ApplicationContext.getApplication();
		ExpWaybillEditUI ui = openUIActionAndReturn(application);
		ui.setApplication(application);
		return ui;
	}

	/**
	 * 集中接送货开单界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午6:31:35
	 */
	public ExpWaybillEditUI getFocusWaybillEditUI() {
		IApplication application = ApplicationContext.getApplication();
		ExpWaybillEditUI ui = openFocusUIAction(application);
		ui.setApplication(application);
		return ui;
	}
	
	/**
	 * 新增集中开单界面
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午6:42:28
	 */
	public ExpWaybillEditUI openFocusUIAction(IApplication application){
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.newAddAction.waybillFocus.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");

		ExpWaybillEditUI waybillEditUI = (ExpWaybillEditUI) editor.getComponent();
		// waybillEditUI.setBillType(BillType.ADDNEW);
		waybillEditUI.setEditor(editor);
		//集中开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		waybillEditUI.openUI();
		return waybillEditUI;
	}
	
	/**
	 * 
	 * 营业部开单界面
	 * @author 025000-FOSS-helong
	 * @date 2012-12-11 上午10:51:59
	 * @see com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction#openUIAction()
	 */
    public ExpWaybillEditUI openUIActionAndReturn(IApplication application) {
    	//TODO
    	EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application.openEditorAndRetrun(editConfig,
				"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");
	
		ExpWaybillEditUI waybillEditUI = (ExpWaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		// 营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		waybillEditUI.openUI();
		return waybillEditUI;
    }
    
    /**
     * 给运单基本信息赋值
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-12-6 下午5:16:37
     */
    private void setWaybillVo(ExpWaybillPanelVo vo, WaybillDto waybillDto, ExpWaybillEditUI ui) {
    	// 判空操作
    	if (null == waybillDto) {
    		return;
    	}
    	//设置值
    	setPendingWaybill(vo,waybillDto,ui);
    	// 提货网点
    	ExpShowPickupStationDialogAction pickupStationAction = new ExpShowPickupStationDialogAction();
    	pickupStationAction.setInjectUI(ui);
    	// 整车的提货网点不需要设置线路和空运配载及时效
    	if (!ui.basicPanel.getChbWholeVehicle().isSelected()) {
    		// 设置线路
    		pickupStationAction.setLoadLine(vo);
    		// 设置空运配载
    		pickupStationAction.setAirDeptEnabled(vo);
    	}									
    	
    }
   
    /**
     * 重新设置包装费 当导入待补录或已补录运单时，共用了计算总运费的方法，该方法调用CalculateFeeTotalUtil.
     * calculateIncrement时： 包装费=输入包装费+木架费+木箱费 包装费的显示金额= 计算金额 + 原来输入框里面的手写值
     * 但手写值为保存到数据库中包装费，导致重复计算了包装费，因此需要重新设置包装费及增值费等
     * 
     * 参见：    BUG-10436 运单总运费错误
     * 
     * @author 026123-foss-lifengteng
     * @date 2013年5月13日 下午03:26:51
     */
    public void setPendingWaybill(ExpWaybillPanelVo vo, Object object, ExpWaybillEditUI ui) {
    	// 定义重用方法的对象
    	ExpWaybillPendingCompleteAction action = new ExpWaybillPendingCompleteAction();
    	// 将运单界面设置到响应对象中
    	action.setInjectUI(ui);
    	// 调用值设置方法
    	BigDecimal packageFee = getOldPackageFee(object);
    	action.setPendingWaybill(vo, object, ui);
    	//获取数据库保存的预付与到付
    	BigDecimal prePayAmount=vo.getPrePayAmount();
    	BigDecimal toPayAmount=vo.getToPayAmount();
    	setOldPackageFee(vo,object,packageFee);
		//送货费有误,重新设置送货费,从数据库读取
		resetDeliveryGoodsFee(vo,object);
		//重新设置预付与到付
		vo.setPrePayAmount(prePayAmount);   // 预付金额
		vo.setToPayAmount(toPayAmount);	    // 到付金额	
    }
    
    /**
     * 重新设置送货费
     * @author WangQianJin
     * @date 2013-6-5 下午3:45:37
     */
    private void resetDeliveryGoodsFee(ExpWaybillPanelVo vo, Object object){
    	// 对象类型转换
    	if (object instanceof WaybillDto) {
    		WaybillDto dto = (WaybillDto) object;
    	    // 待处理运单基本信息
    	    WaybillEntity entity = dto.getWaybillEntity();        	
    	    if(entity!=null && vo!=null){
    	    	vo.setDeliveryGoodsFee(entity.getDeliveryGoodsFee());
    	    	vo.setCalculateDeliveryGoodsFee(entity.getDeliveryGoodsFee());
    	    	if(entity.getDeliveryGoodsFee()!=null){
    	    		vo.setDeliveryGoodsFeeCanvas(entity.getDeliveryGoodsFee()+"");	    		
    	    	}	    	
    	    }
    	}    	
    }
    
    /**
     * 返回修改前的包装费
     * @author 026123-foss-lifengteng
     * @date 2013年5月13日 下午04:07:29
     */
    public BigDecimal getOldPackageFee(Object object) {
	// 对象类型转换
	if (object instanceof WaybillPendingDto) {
	    WaybillPendingDto pendingDto = (WaybillPendingDto) object;
	    return CommonUtils.defaultIfNull(pendingDto.getWaybillPending().getPackageFee());
	} else if (object instanceof WaybillDto) {
	    WaybillDto waybillDto = (WaybillDto) object;
	    return CommonUtils.defaultIfNull(waybillDto.getWaybillEntity().getPackageFee());
	} else {
	    return BigDecimal.valueOf(0);
	}
    }
    
    /**
     * 返回修改前的包装费
     * @author 026123-foss-lifengteng
     * @date 2013年5月13日 下午04:07:29
     */
    public void setOldPackageFee(ExpWaybillPanelVo vo, Object object, BigDecimal oldFee) {
    	BigDecimal packageFee = CommonUtils.defaultIfNull(oldFee);
		// 对象类型转换
		if (object instanceof WaybillPendingDto) {
		    // 待处理运单基本信息
		    WaybillPendingEntity waybillPending = ((WaybillPendingDto) object).getWaybillPending();
		    // 包装费
		    vo.setPackageFee(packageFee);
		    // 包装费（画布）
		    vo.setPackageFeeCanvas(String.valueOf(packageFee));	    
		} else if (object instanceof WaybillDto) {
		    // 已处理运单基本信息
		    WaybillEntity waybillEntity = ((WaybillDto) object).getWaybillEntity();
		    // 包装费
		    vo.setPackageFee(packageFee);
		    // 包装费（画布）
		    vo.setPackageFeeCanvas(String.valueOf(packageFee));	   
		    
		    
			
		}
		//重新计算总费用
		CalculateFeeTotalUtils.resetCalculateFee(vo);
    }
	
	/**
	 * 根据营业部编码判断是否为已作废的营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-18 下午7:39:39
	 */
	public boolean isHistoryDept(String code){
		//查询本地部门 
		SaleDepartmentEntity saleDept = waybillService.querySaleDeptByCode(code);
		//数据是否为空
		if(null == saleDept){
			//是，则表示非作废部门
			return true;
		}else{
			//否，则表示是作废部门 
			return false;
		}
	}
	
	public List<WaybillDiscountVo> getWaybillDiscountVo(List<WaybillDisDtlEntity> disDtl,String productCode,String productName){
		//判断对象是否非空
		if(null == disDtl){
			return null;
		}
		

		return null;
	}
	private DataDictionaryValueVo dataDictionaryValueEntityToVo(
			String termsCode, String valueCode) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if (StringUtil.isNotEmpty(valueCode)) {
			DataDictionaryValueEntity entity = waybillService
					.queryDataDictoryValueEntity(termsCode, valueCode);
			if (entity == null){
				return null;
			}
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			PropertyUtils.copyProperties(vo, entity);
			return vo;
		} else {
			return null;
		}
	}
}