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
package com.deppon.foss.module.pickup.creating.client.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.listener.WaybillBindingListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.vo.SearchPictureVo;
import com.deppon.foss.util.define.FossConstants;


/**
 * 打开运单新增界面的类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-12-17 下午5:59:06
 */
public class ImportWaybillEditUIAction {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ImportWaybillEditUIAction.class);

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
	private static II18n i18n = I18nManager.getI18n(ImportWaybillEditUIAction.class);

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
			WaybillPanelVo value = null;
			// 待处理运单对象
			WaybillPendingEntity entity = null;
			// 运单已补录对象
			WaybillDto dto = null;

			// 对传入的数据进行转换
			if (object instanceof WaybillPanelVo) {
				/**
				 * 目前，只有从弃货处理界面导入传过来的对象为WaybillPanelVo
				 */
				value = (WaybillPanelVo) object;
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
				//没有配置系统参数:默认保价申明价值，提示一键上报
				if(ex.getMessage()!=null && ex.getMessage().indexOf(i18n.get("foss.pkp.creating.itserivce.notconfig.default.insurance"))>=0){
					MsgBox.showITServiceInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));
				}else{
					MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));	
				}				
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
	 * 图片开单查询-导入图片开单界面
	 * @author hehaisu
	 * @param enable 界面是否只读
	 */
	public void importPictureWaybillUI(Object object, boolean enable, String active, String reMark, String cashPayFlag,  String pictureWaybillId) {
		
		//异常处理
		try {
			if (null == object) {
				throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullObject"));
			}

			// 待处理运单对象
			WaybillPendingEntity entity = null;
			// 运单已补录对象
			WaybillDto dto = null;

			if (object instanceof WaybillPendingEntity) {
				entity = (WaybillPendingEntity) object;
				// 校验数据合法性
				if ("".equals(StringUtil.defaultIfNull(entity.getPendingType()))) {
					throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullWaybillSupType.two"));
				} else {
					importWaybillPicturePendingData(entity, enable,active,reMark,cashPayFlag,pictureWaybillId);
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
					importWaybillPictureData(dto, enable, active, reMark);
				}
			} else {
				throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.errorDataType"));
			}
		} catch (BusinessException ex) {
			LOGGER.error("导入异常", ex);			
			if (StringUtils.isNotEmpty(ex.getMessage())) {
				//没有配置系统参数:默认保价申明价值，提示一键上报
				if(ex.getMessage()!=null && ex.getMessage().indexOf(i18n.get("foss.pkp.creating.itserivce.notconfig.default.insurance"))>=0){
					MsgBox.showITServiceInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));
				}else{
					MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));	
				}				
			}else if(StringUtils.isNotEmpty(ex.getErrorCode())){
			    MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+ex.getErrorCode());
			}
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
		WaybillEditUI ui = null;
		// 获得业务工具类
		BusinessUtils bu = new BusinessUtils();
		//批量开单
		if(FossConstants.ACTIVE.equals(entity.getIsBatchCreateWaybill())){
			ui = this.getBatchWaybill();
		}
		// 是否导入集中接送货开单界面
		else if (bu.isBillGroup(entity.getCreateOrgCode())) {
			//获得集中接送货开单界面 
			ui = this.getFocusWaybillEditUI();
		} else {
			//获得非集中接送货开关界面 
			ui = this.getWaybillEditUI();
		}

		//获得绑定的VO对象
		WaybillPanelVo vo = Common.getVoFromUI(ui);
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(entity.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(entity.getReceiveBigCustomer());
		//是否展会货物
		if(FossConstants.YES.equals(entity.getIsExhibitCargo())){
			vo.setIsExhibitCargo(Boolean.TRUE);
		}
		//设置发货地址备注
		vo.setDeliveryCustomerAddressNote(entity.getDeliveryCustomerAddressNote());
		//设置收货地址备注
		vo.setReceiveCustomerAddressNote(entity.getReceiveCustomerAddressNote());
		/**
		 * @author 200945-wutao
		 * 设置发货客户
		 */
		if(StringUtils.isNotEmpty(entity.getDeliveryCustomerCode())){
			CustomerQueryConditionDto customerQueryConditionDto = new CustomerQueryConditionDto();
			customerQueryConditionDto.setCustCode(entity.getDeliveryCustomerCode());
			CustomerQueryConditionDto customerQueryConditionDtoDev = null;
			List<CustomerQueryConditionDto> customerQueryConditionDtoDevList =  waybillService.queryCustomerByCondition(customerQueryConditionDto);//customerService.queryCustomerByCondition(customerQueryConditionDtoReceiver);
			if (customerQueryConditionDtoDevList.size() > 0) {
				customerQueryConditionDtoDev = customerQueryConditionDtoDevList.get(0);
				if(null!=entity.getReceiveCustomerCode()){
					CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
					contact.setCustCode(entity.getDeliveryCustomerCode());
					CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
					CommonUtils.setDevliveryCusomterSettler(customerQueryConditionDtoDev, cusBargainEntity, vo);
					}
			}
		}
		
		/**
		 * 设置收货客户
		 */
		if(StringUtils.isNotEmpty(entity.getReceiveCustomerCode())){
			CustomerQueryConditionDto customerQueryConditionDtoReceiver = new CustomerQueryConditionDto();
			customerQueryConditionDtoReceiver.setCustCode(entity.getReceiveCustomerCode());
			CustomerQueryConditionDto customerQueryConditionDtoRev = null;
			List<CustomerQueryConditionDto> customerQueryConditionDtoReceivercList =  waybillService.queryCustomerByCondition(customerQueryConditionDtoReceiver);//customerService.queryCustomerByCondition(customerQueryConditionDtoReceiver);
			if (customerQueryConditionDtoReceivercList.size() > 0) {
				customerQueryConditionDtoRev = customerQueryConditionDtoReceivercList.get(0);
				if(null!=entity.getReceiveCustomerCode()){
					CustomerQueryConditionDto contact = new CustomerQueryConditionDto();
					contact.setCustCode(entity.getReceiveCustomerCode());
					CusBargainEntity cusBargainEntity = CommonUtils.queryCusBargainByCustCode(contact);
					CommonUtils.setReciveCusomterSettler(customerQueryConditionDtoRev, cusBargainEntity, vo);
					}
			}
		}
		String productCode = entity.getProductCode();
		DefaultComboBoxModel combProduct = ui.getProductTypeModel();
		Common.modifyProdctType(combProduct, productCode, ui,entity.getReceiveOrgCode());
		
		//获得发货客户信息
		CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
		//发票标记时间
		dto.setInvoiceDate(new Date());
		//客户编码
		dto.setCustCode(entity.getDeliveryCustomerCode());
		//大客户标记
		dto.setIsLargeCustomers(entity.getDeliveryBigCustomer());
		List<CustomerQueryConditionDto> cdto=Common. getCustomerQueryConditionDto(dto);
		String isWhole = entity.getIsWholeVehicle();
		/**
		 * 根据Dmana-10888，增加判定标识
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-02下午15:50
		 */
		boolean flag=true;
		if(cdto!=null && !FossConstants.YES.equals(isWhole)){
			for(CustomerQueryConditionDto ddto:cdto){
				String invoice =ddto.getInvoiceType();
				if(invoice!=null && invoice.equals(WaybillConstants.INVOICE_01)){
					vo.setInvoice(WaybillConstants.INVOICE_01);
					vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
				}else{
					vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
					vo.setInvoice(WaybillConstants.INVOICE_02);
				}
				if(invoice!=null){
				   break;	
				}
			}
			flag=false;
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
			setPendingWaybill(vo, Common.getOrderCustInfo(waybillDto), ui);
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
			
			setPendingWaybill(vo, Common.getOrderCustInfo(waybillDto), ui);
			// 设置其它待处理信息（提货网点、走货线路、整车信息）
			Common.setOtherPendingData(ui, vo);
		}
		//刷新打木托list
		Common.refreshLabeledGood(vo,ui);
		// 创建对象来接收查询出的运单对象
		WaybillPanelVo voimport = new WaybillPanelVo();
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
		}

		//liding comment for NCI
		/**
		 * 通过付款方式判断“交易流水号”是否可编辑
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-26上午10:40
		 */
//		Common.whetherBankCardPayment(vo, ui);
		/**
		 * 根据Dmana-10888修改此段代码，增加发票标记查询
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-20下午19:35
		 */
		if(flag){
			String invoice=CommonUtils.setInvoice(vo.getDeliveryCustomerCode());
			vo.setInvoice(invoice);
			if(WaybillConstants.INVOICE_01.equals(invoice)){
				vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
			}else{
				vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
			}
		}
	}

	private WaybillEditUI getBatchWaybill() {
		IApplication application = ApplicationContext.getApplication();
		WaybillEditUI ui = openBatchWaybill(application);
		ui.setApplication(application);
		return ui;
	}

	private WaybillEditUI openBatchWaybill(IApplication application) {
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillSaleDepartment.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application.openEditorAndRetrun(editConfig, "com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI");

		WaybillEditUI waybillEditUI = (WaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		// 营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		//批量开单
		waybillEditUI.setBatchWaybill(true);
		waybillEditUI.openUI();
		return waybillEditUI;
	}

	/**
	 * 导入已开单的运单数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午2:01:08
	 */
	private void importWaybillData(WaybillDto dto) {
		// 获得运单实体对象
		WaybillEntity entity = dto.getWaybillEntity();
		// 对象非空判断
		if (null == entity) {
			return;
		}

		// 定义开单界面
		WaybillEditUI ui = null;
		// 获得业务工具类
		BusinessUtils bu = new BusinessUtils();
		//批量开单
		if(FossConstants.ACTIVE.equals(dto.getActualFreightEntity().getIsBatchCreateWaybill())){
			ui = this.getBatchWaybill();
		}
		// 是否导入集中接送货开单界面
		else if (bu.isBillGroup(entity.getCreateOrgCode())) {
			ui = this.getFocusWaybillEditUI();
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

		// 获取绑定bean
		WaybillPanelVo vo = Common.getVoFromUI(ui);
		String bigTicketGoods = dto.getActualFreightEntity().getBigTicketGoods();
		if(FossConstants.YES.equals(bigTicketGoods)){
			vo.setBigTicket(true);
		}else{
			vo.setBigTicket(false);
		}
		//vo 设置上门接货   
 		String pickToDoor = dto.getWaybillEntity().getPickupToDoor() ;   
 		if(FossConstants.YES.equals(pickToDoor)){   
 			vo.setPickupToDoor(true);   
 		}else{   
 			vo.setPickupToDoor(false);   
 		}   
 		//vo 设置免费接货 
		String freePickupGoods = dto.getWaybillEntity().getFreePickupGoods() ;
		if(FossConstants.YES.equals(freePickupGoods)){
			vo.setFreePickupGoods(true);
		}else{
			vo.setFreePickupGoods(false);
		}
		
		//定价体系优化项目POP-546--将运费计算类型赋值给计费类型..
		if(vo.getBillingType() != null){
			if(StringUtil.isEmpty(vo.getCaculateType())){
				vo.setCaculateType(vo.getBillingType().getValueCode());
			}
		}
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(entity.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(entity.getReceiveBigCustomer());
		//进行地址备注信息的赋值
		ActualFreightEntity acf = dto.getActualFreightEntity();
		if(acf != null){
			//发货地址备注
			vo.setDeliveryCustomerAddressNote(acf.getDeliveryCustomerAddressNote());
			//收货地址备注
			vo.setReceiveCustomerAddressNote(acf.getReceiveCustomerAddressNote());	
			//是否展会货
			if(FossConstants.YES.equals(acf.getIsExhibitCargo())){
				vo.setIsExhibitCargo(Boolean.TRUE);
			}
			try {
				//行业货源品类
				if (null != acf.getIndustrySourceCategory()) {
					vo.setIndustrySourceCategory(dataDictionaryValueEntityToVo(WaybillConstants.BSE_SOURCE_CATEGORY, acf.getIndustrySourceCategory()));
				}
				//客户分群
				String flabelleavemonth = acf.getFlabelleavemonth();
				if(StringUtils.isEmpty(flabelleavemonth)){
					flabelleavemonth="NEWCUST";
				}
				vo.setFlabelleavemonth(dataDictionaryValueEntityToVo(WaybillConstants.CRM_CUSTOMER_GROUP, flabelleavemonth));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
			//【催款部门编码】
			vo.setStartReminderOrgCode(acf.getStartReminderOrgCode());
			vo.setArriveReminderOrgCode(acf.getArriveReminderOrgCode());
			
			try {
				// 特殊增值服务
				vo.setSpecialValueAddedServiceType(dataDictionaryValueEntityToVo(
						WaybillConstants.SPECIAL_VALUE_ADDED_SERVICE_TYPE,
						acf.getSpecialValueAddedServiceType()));
				
				//内部发货类型和工号
				vo.setInternalDeliveryType(dataDictionaryValueEntityToVo(
						WaybillConstants.INTERNAL_DELIVERY_TYPE,
						acf.getInternalDeliveryType()));
			} catch (Exception e) {
				e.printStackTrace();
			} 
			vo.setEmployeeNo(acf.getEmployeeNo());
		}
		
		
		String productCode = dto.getWaybillEntity().getProductCode();
		DefaultComboBoxModel combProduct = ui.getProductTypeModel();
		Common.modifyProdctType(combProduct, productCode, ui,entity.getReceiveOrgCode());
		
		setWaybillVo(vo, dto, ui);
		
		
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
		
		ui.consignerPanel.getCombInvoiceMode().setVisible(false);
		//if(vo.getIsWholeVehicle()){
			if(dto.getActualFreightEntity().getInvoice().equals(WaybillConstants.INVOICE_01)){
				vo.setInvoice(WaybillConstants.INVOICE_01);
				vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
			}else{
				vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
				vo.setInvoice(WaybillConstants.INVOICE_02);
			}
		//}
	}
	
	/**
	 * 导入图片开单待补录运单VO数据
	 * 
	 */
	private void importWaybillPicturePendingData(WaybillPendingEntity entity, boolean enable, String active, String reMark, String cashPayFlag, String pictureWaybillId) {
		// 对象非空判断
		if (null == entity) {
			return;
		}

		// 定义开单界面
		PictureWaybillEditUI ui = null;
		
		ui = this.getPictureWaybillEditUI();
		try {
			ui.picturePanel.pictureViewComp.loadImageByWaybillNo(entity.getWaybillNo());
		} catch (Exception e1) {
			LOGGER.error("载入图片失败！");
			ui.picturePanel.pictureViewComp.setVisible(false);
			ui.picturePanel.lable1.setVisible(false);
			ui.picturePanel.nextButton.setVisible(false);
			ui.picturePanel.lable2.setVisible(true);
		}
		//获得绑定的VO对象
		WaybillPanelVo vo = Common.getVoFromUI(ui.waybillEdit);
		String serviceType = "";
		String orderNo = entity.getOrderNo();
		if(orderNo != null && !"".equals(orderNo)){
			serviceType	= Common.getServiceType(orderNo);
			if("".equals(serviceType) || serviceType == null){
				IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
				serviceType = waybillService.queryServiceType(entity.getOrderNo());
			}	
		}
		if(!"".equals(serviceType)&& serviceType != null){
			vo.setServerType(serviceType);
		}
		//设置图片开单id
		vo.setPictureWaybillNo(pictureWaybillId);
		/**
		 * 是否现金标记 add by hehaisu
		 * 用于图片开单
		 */
		if (WaybillConstants.YES.equals(cashPayFlag)) {
			vo.setCashPayFlag(true);
		} else {
			vo.setCashPayFlag(false);
		}
		
		//包装备注
		if (StringUtil.isNotBlank(entity.getPackageRemark())) {
			StringBuffer sb = new StringBuffer();
			String packageRemark = entity.getPackageRemark();
			//将包装备注项字符串解析成数据组
			String[] remark = StringUtil.defaultIfNull(packageRemark).split(";");
			//去掉包装备注中的m,
			for(int i = 0 ; i < remark.length ; i++){
				if(!"M".equals(remark[i])){
					sb.append(remark[i]);
				}
			}
			vo.setPackageRemark(sb.toString());
		}
		
		/**
		 * 当为香港的营业部时显示商业区和住宅区
		 */
		if (WaybillConstants.YES.equals(entity.getBusinessZone())) {
			vo.setBusinessZone(true);
		} else {
			vo.setBusinessZone(false);
		}
		if (WaybillConstants.YES.equals(entity.getResidentialDistrict())) {//是否商业区
			vo.setResidentialDistrict(true);
		} else {
			vo.setResidentialDistrict(false);
		}
		
		/**
		 * 设置劳务费费率
		 */
		vo.setServiceRate(entity.getServiceRate());
		vo.setPictureServiceFee(entity.getServiceFee());
		ui.waybillEdit.incrementPanel.getCombServiceRate().setSelectedItem(entity.getServiceRate());
		
		if(StringUtil.isNotBlank(entity.getCustomerPickupOrgCode())){
			OrgAdministrativeInfoEntity oaif = waybillService.queryByCode(entity.getCustomerPickupOrgCode());
			if(oaif !=null){
				if(i18n.get("foss.gui.creating.showPickupStationDialogAction.hg.name").equals(oaif.getCityName()) && 
						i18n.get("foss.gui.creating.showPickupStationDialogAction.hg.code").equals(oaif.getCityCode())){
					ui.waybillEdit.pictureTransferInfoPanel.getJBusinessZone().setVisible(true);
					ui.waybillEdit.pictureTransferInfoPanel.getJResidentialDistrict().setVisible(true);
				}else{
					ui.waybillEdit.pictureTransferInfoPanel.getJBusinessZone().setVisible(false);
					ui.waybillEdit.pictureTransferInfoPanel.getJResidentialDistrict().setVisible(false);
				}
			}
		}
		
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(entity.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(entity.getReceiveBigCustomer());
		
		String productCode = entity.getProductCode();
		DefaultComboBoxModel combProduct = ui.waybillEdit.getProductTypeModel();
		Common.modifyProdctType(combProduct, productCode, ui.waybillEdit,entity.getReceiveOrgCode());
		
		//获得发货客户信息
		CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
		//发票标记时间
		dto.setInvoiceDate(new Date());
		//客户编码
		dto.setCustCode(entity.getDeliveryCustomerCode());
		//大客户标记
		dto.setIsLargeCustomers(entity.getDeliveryBigCustomer());
		List<CustomerQueryConditionDto> cdto=Common. getCustomerQueryConditionDto(dto);
		String isWhole = entity.getIsWholeVehicle();
		if(cdto!=null && !WaybillConstants.YES.equals(isWhole)){
			for(CustomerQueryConditionDto ddto:cdto){
				String invoice =ddto.getInvoiceType();
				if(invoice!=null && invoice.equals(WaybillConstants.INVOICE_01)){
					vo.setInvoice(WaybillConstants.INVOICE_01);
					vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
				}else{
					vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
					vo.setInvoice(WaybillConstants.INVOICE_02);
				}
				if(invoice!=null){
				   break;	
				}
			}
		}
		// 获得运单号
		String waybillNo = StringUtil.defaultIfNull(entity.getWaybillNo());

		/**
		 * 获得完整运单对象信息 PS: 
		 * 1、营业部管理出发运单界面只查询出部分数据，这样提高查询性能，且没有必要全部查询
		 * 2、待要导入开单补录时再将完整待补录运单信息全部查询出来，此需要重新再查询一次 
		 * 3、可以搞高查询速度，减少网络流量
		 * 
		**/
		WaybillPendingDto waybillDto = waybillService.queryPendingWaybill(waybillNo);
		//处理待录入 类型   何波
		if(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(active)){
			waybillDto = new WaybillPendingDto();
			waybillDto.setMixNo(waybillNo);
			waybillDto.setOrderNo(entity.getOrderNo());
			entity.setProductCode(WaybillConstants.FSF_FLIGHT);
			waybillDto.setWaybillPending(entity);
		}else{
			waybillDto = waybillService.queryPendingWaybill(waybillNo);
		}
		ActualFreightEntity acf = waybillService.queryAcfByWaybillNo(waybillNo);
		/**
		 * 内部发货类型和工号
		 */
		if(acf != null) {
			try {
				vo.setInternalDeliveryType(dataDictionaryValueEntityToVo(
						WaybillConstants.INTERNAL_DELIVERY_TYPE,
						acf.getInternalDeliveryType()));
				/*
				 * 新客户标签  zhangchengfu
				 */
				if (null != acf.getIndustrySourceCategory()) {
					vo.setIndustrySourceCategory(dataDictionaryValueEntityToVo(WaybillConstants.BSE_SOURCE_CATEGORY, acf.getIndustrySourceCategory()));
				}
				String flabelleavemonth = acf.getFlabelleavemonth();
				if(StringUtils.isEmpty(flabelleavemonth)){
					flabelleavemonth="NEWCUST";
				}
				vo.setFlabelleavemonth(dataDictionaryValueEntityToVo(WaybillConstants.CRM_CUSTOMER_GROUP, flabelleavemonth));
			} catch (Exception e) {
				e.printStackTrace();
			} 
			vo.setEmployeeNo(acf.getEmployeeNo());
			if(FossConstants.ACTIVE.equals(acf.getIsExhibitCargo())){
				vo.setIsExhibitCargo(true);
			}
			
		}
		
		
		/**
		 * Common.getOrderCustInfo(waybillDto)
		 */
		setPendingWaybill(vo, waybillDto, ui.waybillEdit);
		//精准包裹不能免费接货   zhangwei
		if(ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productCode)){
			ui.waybillEdit.basicPanel.getCboFreePickupGoods().setEnabled(false);
			ui.waybillEdit.basicPanel.getCboFreePickupGoods().setSelected(false);
		}
		// 设置其它待处理信息（提货网点、走货线路、整车信息）
		Common.setOtherPendingData(ui.waybillEdit, vo);
		
		setInvoceFromActualEntity(acf, vo);
		
		/**
		 * add by hehaisu
		 * @Date 2014-12-16
		 * 点击修改，有重量体积，计算运费按钮改为可操作 
		 */
		WaybillEditUI waybillEditUi = ui.waybillEdit;
		String weight = waybillEditUi.pictureCargoInfoPanel.getTxtWeight().getText();
		String volume = waybillEditUi.pictureCargoInfoPanel.getTxtVolume().getText();
		if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
				&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
			waybillEditUi.incrementPanel.getBtnCalculate().setEnabled(true);
			waybillEditUi.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
			waybillEditUi.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
			waybillEditUi.incrementPanel.getJlable().setVisible(false);
			waybillEditUi.incrementPanel.getCombServiceRate().setVisible(false);
		}else{
			waybillEditUi.incrementPanel.getBtnCalculate().setEnabled(false);
			waybillEditUi.billingPayPanel.getBtnSubmit().setEnabled(true);// 提交为不可编辑
			waybillEditUi.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
			waybillEditUi.incrementPanel.getJlable().setVisible(true);
			waybillEditUi.incrementPanel.getCombServiceRate().setVisible(true);
		}
		
		//刷新打木托list
		Common.refreshLabeledGood(vo,ui.waybillEdit);
		// 创建对象来接收查询出的运单对象
		WaybillPanelVo voimport = new WaybillPanelVo();
		try {
			// 这个是用于提交对比的对象 不能用同一个对象 要对比
			PropertyUtils.copyProperties(voimport, vo);
		} catch (Exception e) {
			LOGGER.error("copyProperties异常", e);
		}
		
		// 用于在提交的时候比较老新数据的差异
		ui.waybillEdit.setImportWaybillPanelVo(voimport);
		
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
			ui.waybillEdit.basicPanel.getCboReceiveModel().setSelected(true);
		}
		
		//生成运单异常-修改操作
		if (StringUtils.isNotBlank(reMark)) {
			//1验证异常信息，然后提示
			MsgBox.showInfo(reMark);
			
		}
		//如果導入的數據不是上門接貨的 ，要在勾選點上門接貨
		if(StringUtils.isNotBlank(entity.getPickupToDoor()) && 
				FossConstants.NO.equals(entity.getPickupToDoor())){
			vo.setPickupToDoor(false);
		}
		//根据导入的数据设置免费接货勾选 add by 306486
		if(StringUtils.isNotBlank(entity.getFreePickupGoods())){
			vo.setFreePickupGoods(BooleanConvertYesOrNo.stringToBoolean(entity.getFreePickupGoods()));
		}
		// 若为已开单运单，则设置界面不可编辑
		if (enable == false)
			UIUtils.disableUI(ui);
		
		ui.waybillEdit.buttonPanel.getFullScreen().setEnabled(true);
		ui.picturePanel.topButton.setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbDown().setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbLeft().setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbRight().setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbTop().setEnabled(true);
		
		/**
		 * 该方法验证若是银"到付"、“临时欠款”时，则预付金额和预付费保密是否可编辑
		 * @author:foss-shenshang
		 * @date:2015-06-18上午17:06
		 */
		//如果付款方式为 到付  预付金额是否可编辑
		if(WaybillConstants.ARRIVE_PAYMENT.equals(entity.getOrderPaidMethod())){
			   //预付金额不可编辑
			   ui.waybillEdit.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);		
		  }else{
			//预付金额可编辑
			   ui.waybillEdit.billingPayPanel.getTxtAdvancesMoney().setEnabled(true);		
		  }
		//如果付款方式为 "到付","临时欠款"是， 预付费保密是否可编辑
		if(WaybillConstants.ARRIVE_PAYMENT.equals(entity.getOrderPaidMethod())
//				|| WaybillConstants.TEMPORARY_DEBT.equals(entity.getOrderPaidMethod())	
				){
			 //预付费保密不可编辑
			   ui.waybillEdit.incrementPanel.getChbSecrecy().setEnabled(false);
		}else{
			//预付费保密可编辑
			ui.waybillEdit.incrementPanel.getChbSecrecy().setEnabled(true);
		}
		if(WaybillConstants.TEMPORARY_DEBT.equals(entity.getOrderPaidMethod())){
			ui.waybillEdit.incrementPanel.getChbSecrecy().setEnabled(true);
	}
	}
	
	
	/**
	 * 从实际承运表中获取发票标记信息
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-27
	 * @param acf
	 * @param vo
	 */
	private void setInvoceFromActualEntity(ActualFreightEntity acf, WaybillPanelVo vo){
		if(acf != null){
			/**
			 * 实际承运表中有直接设置
			 * @author Foss-278328-hujinyang
			 * @Time 20160427
			 */
			String invoice = acf.getInvoice();
			String invoiceMobilePhone = acf.getInvoiceMobilePhone();
			String invoiceType = acf.getInvoiceType();
			
			String arriveCentralizedSettlement = acf.getArriveCentralizedSettlement();
			String arriveContractOrgCode  = acf.getArriveContractOrgCode();
			String arriveReminderOrgCode  = acf.getArriveReminderOrgCode();
			
			String startCentralizedSettlement = acf.getStartCentralizedSettlement();
			String startContractOrgCode  = acf.getStartContractOrgCode();
			String startReminderOrgCode  = acf.getStartReminderOrgCode();
			
			String arriveSettlement = WaybillConstants.YES.equals(arriveCentralizedSettlement)?WaybillConstants.IS_NOT_NULL_FOR_AI: WaybillConstants.IS_NULL_FOR_AI;
			String startSettlement = WaybillConstants.YES.equals(startCentralizedSettlement)?WaybillConstants.IS_NOT_NULL_FOR_AI: WaybillConstants.IS_NULL_FOR_AI;
			
			
			/*//客户编码
			String deliveryCustomerCode = "";
			String receiveCustomerCode = "";
			if(obj instanceof WaybillEntity){
				WaybillEntity entity = (WaybillEntity)obj;
				deliveryCustomerCode = entity.getDeliveryCustomerCode();
				receiveCustomerCode = entity.getReceiveCustomerCode();
				
			}else if(obj instanceof WaybillPendingEntity){
				WaybillPendingEntity pending = (WaybillPendingEntity)obj;
				deliveryCustomerCode = pending.getDeliveryCustomerCode();
				receiveCustomerCode = pending.getReceiveCustomerCode();
			}
			
			vo.setDeliveryCustomerCode(deliveryCustomerCode);
			vo.setReceiveCustomerCode(receiveCustomerCode);
			*/
			
			
			vo.setInvoice(invoice);
			vo.setInvoiceTab(invoiceType);
			vo.setInvoiceMobilePhone(invoiceMobilePhone);
			vo.setArriveCentralizedSettlement(arriveSettlement);
			vo.setArriveReminderOrgCode(arriveContractOrgCode);
			vo.setArriveReminderOrgCode(arriveReminderOrgCode);
			//设置机构名
			vo.setArriveContractOrgName(CommonUtils.queryContractOrgName(arriveReminderOrgCode));

			vo.setStartCentralizedSettlement(startSettlement);
			vo.setStartContractOrgCode(startContractOrgCode);
			vo.setStartReminderOrgCode(startReminderOrgCode);
			//设置机构名
			vo.setStartContractOrgName(CommonUtils.queryContractOrgName(startReminderOrgCode));
		}
	}
	
	/**
	 * 导入图片开单已开单的运单数据
	 */
	private void importWaybillPictureData(WaybillDto dto, boolean enable, String active, String reMark) {
		// 获得运单实体对象
		WaybillEntity entity = dto.getWaybillEntity();
		// 对象非空判断
		if (null == entity) {
			return;
		}

		// 定义开单界面
		PictureWaybillEditUI ui = null;
		ui = getPictureWaybillEditUI();
		try {
			ui.picturePanel.pictureViewComp.loadImageByWaybillNo(entity.getWaybillNo());
		} catch (Exception e1) {
			LOGGER.error("载入图片失败！");
			ui.picturePanel.pictureViewComp.setVisible(false);
			ui.picturePanel.lable1.setVisible(false);
			ui.picturePanel.nextButton.setVisible(false);
			ui.picturePanel.lable2.setVisible(true);
		}
		/**
		 * 1)在数据设值之前进行界面控件的编辑状态设置，
		 * 是为了避免在设值时出现异常，导致代码无法走到设置界面编辑状态处
		 * 导致界面数据可编辑
		 * 
		 * 2)见BUG-7159 
		 */
		// 若为已开单运单，则设置界面不可编辑
		if (enable == false)
			UIUtils.disableUI(ui);
		
		// 获取绑定bean
		WaybillPanelVo vo = Common.getVoFromUI(ui.waybillEdit);
		if(StringUtil.isNotBlank(entity.getFreePickupGoods())){// 是否免费接货 add by 306486
			vo.setFreePickupGoods(BooleanConvertYesOrNo.stringToBoolean(entity
					.getFreePickupGoods()));// 是否免费接货 add by 306486
		}
		String bigTicketGoods = dto.getActualFreightEntity().getBigTicketGoods();
		if(WaybillConstants.YES.equals(bigTicketGoods)){
			vo.setBigTicket(true);
		}else{
			vo.setBigTicket(false);
		}
		
		/**
		 * 当为香港的营业部时显示商业区和住宅区
		 */
		if (WaybillConstants.YES.equals(dto.getActualFreightEntity().getBusinessZone())) {//是否商业区
			vo.setBusinessZone(true);
		} else {
			vo.setBusinessZone(false);
		}
		if (WaybillConstants.YES.equals(dto.getActualFreightEntity().getResidentialDistrict())) {//是否商业区
			vo.setResidentialDistrict(true);
		} else {
			vo.setResidentialDistrict(false);
		}
		try {
			vo.setInternalDeliveryType(dataDictionaryValueEntityToVo(
					WaybillConstants.INTERNAL_DELIVERY_TYPE,
					dto.getActualFreightEntity().getInternalDeliveryType()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		vo.setEmployeeNo(dto.getActualFreightEntity().getEmployeeNo());
		WaybillEntity waybillEntity = dto.getWaybillEntity();
		if(waybillEntity != null){
			OrgAdministrativeInfoEntity oaif = waybillService.queryByCode(waybillEntity.getCustomerPickupOrgCode());
			if(oaif != null){
				if(i18n.get("foss.gui.creating.showPickupStationDialogAction.hg.name").equals(oaif.getCityName()) && 
						i18n.get("foss.gui.creating.showPickupStationDialogAction.hg.code").equals(oaif.getCityCode())){
					ui.waybillEdit.pictureTransferInfoPanel.getJBusinessZone().setVisible(true);
					ui.waybillEdit.pictureTransferInfoPanel.getJResidentialDistrict().setVisible(true);
				}else{
					ui.waybillEdit.pictureTransferInfoPanel.getJBusinessZone().setVisible(false);
					ui.waybillEdit.pictureTransferInfoPanel.getJResidentialDistrict().setVisible(false);
				}
			}
		}
		
		/**
		 * 设置劳务费费率
		 * 从图片开单表查询劳务费费率
		 */
		IWaybillHessianRemoting waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		WaybillPictureEntity pictureEntity = waybillHessianRemoting.queryWaybillPictureByWaybillNo(entity.getWaybillNo());
		if (pictureEntity != null) {
			vo.setServiceFeeRate(pictureEntity.getServiceRate());
			ui.waybillEdit.incrementPanel.getCombServiceRate().setSelectedItem(pictureEntity.getServiceRate());
		}
		
		//包装备注
		if (StringUtil.isNotBlank(dto.getActualFreightEntity().getPackageRemark())) {
			StringBuffer sb = new StringBuffer();
			String packageRemark = dto.getActualFreightEntity().getPackageRemark();
			//将包装备注项字符串解析成数据组
			String[] remark = StringUtil.defaultIfNull(packageRemark).split(";");
			//去掉包装备注中的m,
			for(int i = 0 ; i < remark.length ; i++){
				if(!"M".equals(remark[i])){
					sb.append(remark[i]);
				}
			}
			vo.setPackageRemark(sb.toString());
		}
				
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(entity.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(entity.getReceiveBigCustomer());
		
		String productCode = dto.getWaybillEntity().getProductCode();
		DefaultComboBoxModel combProduct = ui.waybillEdit.getProductTypeModel();
		Common.modifyProdctType(combProduct, productCode, ui.waybillEdit,entity.getReceiveOrgCode());
		
		setWaybillVo(vo, dto, ui.waybillEdit);
		
		//2016427 hujinyang 278328 
		ActualFreightEntity acf = waybillService.queryAcfByWaybillNo(entity.getWaybillNo());
		setInvoceFromActualEntity(acf, vo);
		//查看备注
		if (StringUtils.isNotBlank(reMark)) {
			//1验证异常信息，然后提示
			MsgBox.showInfo(reMark);
			
		}
		
		
		/**
		 * 防止在设置值后将编辑状态改变了，故再设置一遍
		 */
		// 若为已开单运单，则设置界面不可编辑
		if (enable == false)
			UIUtils.disableUI(ui);
		
		ui.waybillEdit.buttonPanel.getFullScreen().setEnabled(true);
		ui.picturePanel.topButton.setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbDown().setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbLeft().setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbRight().setEnabled(true);
		ui.picturePanel.pictureViewComp.getPictureRotateComp().getJbTop().setEnabled(true);
	}
	
	/**
     * 设置图片开单界面bean
     * @param vo
     * @param object
     * @param ui
     */
    public void setWaybillPictureEntityToVo (WaybillPanelVo vo, Object object, WaybillEditUI ui) {
    	//图片开单
    	SearchPictureVo dto;
    	if (object instanceof SearchPictureVo) {
    		dto = (SearchPictureVo) object;
    		
    		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
    		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
    		WaybillPanelVo bean = waybillBinder.getBean();
    		//设置运单号
			ui.numberPanel.getLblNumber().setText(dto.getWaybillNo());
			ui.basicPanel.getTxtWaybillNO().setText(dto.getWaybillNo());
			if(FossConstants.YES.equals(dto.getBigGoodsFlag())){
				ui.basicPanel.getChbBigGoods().setSelected(true);
    	    }else{
    	    	ui.basicPanel.getChbBigGoods().setSelected(false);
    	    }
			ui.basicPanel.getTxtDriverNumber().setText(dto.getDriverWorkNo());
			//设置收获部门信息
			SaleDepartmentEntity saleDepartmentEntity = new SaleDepartmentEntity();
			saleDepartmentEntity.setCode(dto.getReceiveOrgCode());
			saleDepartmentEntity.setName(dto.getReceiveOrgName());
			Common.setSalesDepartmentForCentrial(saleDepartmentEntity, bean, ui);
			
    	}
    }

	/**
	 * 导入待补录运单基本数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午2:01:06
	 */
	private void importWaybillVoData(WaybillPanelVo value) {
		// 运单界面
		WaybillEditUI ui = null;
		//判断是事出发部门code是否为空
		if(StringUtils.isEmpty(value.getCreateOrgCode())){
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			if(dept.getCode()!=null){
				//开单部门
				value.setCreateOrgCode(StringUtil.defaultIfNull(dept.getCode()));
			}
		}
		
		
		// 获得业务工具类
		BusinessUtils bu = new BusinessUtils();
		// 是否导入集中接送货开单界面
		if (bu.isBillGroup(value.getCreateOrgCode())) {
			//获得集中接送货开单界面 
			ui = this.getFocusWaybillEditUI();
		} else {
			//获得非集中接送货开关界面 
			ui = this.getWaybillEditUI();
		}
		
		WaybillPanelVo vo = Common.getVoFromUI(ui);
		
		
		/**
		 * 由于是否大客户标记未与界面绑定，vo重新获取界面值时会丢失这部分数据
		 */
		vo.setDeliveryBigCustomer(value.getDeliveryBigCustomer());
		vo.setReceiveBigCustomer(value.getReceiveBigCustomer());
		//发货地址备注
		vo.setDeliveryCustomerAddressNote(value.getDeliveryCustomerAddressNote());
		//收货地址备注
		vo.setReceiveCustomerAddressNote(value.getReceiveCustomerAddressNote());
		/*
		 * 设置【是否统一结算】 
		 * @author -200945  wutao
		 * 
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
		String productCode = value.getPromotionsCode();
		DefaultComboBoxModel combProduct = ui.getProductTypeModel();
		Common.modifyProdctType(combProduct, productCode, ui,value.getReceiveOrgCode());
		
		// 拷贝数据
		try {
			// 这个是用于提交对比的对象 不能用同一个对象 要对比
			PropertyUtils.copyProperties(vo, value);
		} catch (Exception e) {
			LOGGER.error("copyProperties异常", e);
		}
		
		//初始化数据
		initInnerPickupData(ui,vo);
		
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
	 * 初始化内部带货数据
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-31 下午4:24:18
	 */
	private void initInnerPickupData(WaybillEditUI ui,WaybillPanelVo vo){
		//纸
		vo.setPaper(CommonUtils.defaultIfNull(vo.getPaper()));
		//木
		vo.setWood(CommonUtils.defaultIfNull(vo.getWood()));
		//纤
		vo.setFibre(CommonUtils.defaultIfNull(vo.getFibre()));
		//托
		vo.setSalver(CommonUtils.defaultIfNull(vo.getSalver()));
		//膜
		vo.setMembrane(CommonUtils.defaultIfNull(vo.getMembrane()));
		//预付保密费
		vo.setSecretPrepaid(CommonUtils.defaultIfNull(vo.getSecretPrepaid()));
		//整车约车报价
		vo.setWholeVehicleAppfee(CommonUtils.defaultIfNull(vo.getWholeVehicleAppfee()));
		//计费类型
		if(null == vo.getBillingType()){
			Common.initCombBillingType(ui);
			DataDictionaryValueVo value = (DataDictionaryValueVo) ui.getCanvasContentPanel().getCombBillingType().getModel().getSelectedItem();
			vo.setBillingType(value);
		}
		
		//设置提货方式
		setReceiveMethod(ui,vo);
	}
	
	/**
	 * 设置默认的开单提货方式
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-31 下午2:56:47
	 */
	private void setReceiveMethod(WaybillEditUI ui,WaybillPanelVo bean){
		if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
			ComboBoxModel pikcModeModel = ui.pictureTransferInfoPanel.getCombPickMode().getModel();
			//弃货导入开单时，应该测试开单提货方式为内部带货开单，且不可修改
			for (int i = 0; i < pikcModeModel.getSize(); i++) {
				DataDictionaryValueVo vo =(DataDictionaryValueVo) pikcModeModel.getElementAt(i);
				if (WaybillConstants.INNER_PICKUP.equals(vo.getValueCode())) {
					bean.setReceiveMethod(vo);
					ui.pictureTransferInfoPanel.getCombPickMode().setEnabled(false);
				}
			}
		}else{
			ComboBoxModel pikcModeModel = ui.getTransferInfoPanel().getCombPickMode().getModel();
			//弃货导入开单时，应该测试开单提货方式为内部带货开单，且不可修改
			for (int i = 0; i < pikcModeModel.getSize(); i++) {
				DataDictionaryValueVo vo =(DataDictionaryValueVo) pikcModeModel.getElementAt(i);
				if (WaybillConstants.INNER_PICKUP.equals(vo.getValueCode())) {
					bean.setReceiveMethod(vo);
					ui.getTransferInfoPanel().getCombPickMode().setEnabled(false);
				}
			}
		}
	}

	/**
	 * 导入或补录时设置需要触发事件的控件值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-25 上午10:21:46
	 */
	private void setTriggerPanel(WaybillEditUI ui, WaybillPanelVo vo) {
		WaybillBindingListener bindingListener = new WaybillBindingListener(ui);
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
		bindingListener.receiveMethodListener(vo);
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
	public void setPickupStationfo(WaybillEditUI ui, WaybillPanelVo vo) {
		// 判断提货网点是否为空
		if (vo.getCustomerPickupOrgCode() == null) {
			return;
		}
		// 提货网点
		ShowPickupStationDialogAction action = new ShowPickupStationDialogAction();
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
		Common.setReturnBillCharge(vo, ui);
	}

	/**
	 * 开单运单新增界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-17 下午6:02:03
	 */
	public WaybillEditUI getWaybillEditUI() {
		IApplication application = ApplicationContext.getApplication();
		WaybillEditUI ui = openUIActionAndReturn(application);
		ui.setApplication(application);
		return ui;
	}
	/**
	 * 图片开单界面
	 */
	public PictureWaybillEditUI getPictureWaybillEditUI() {
		IApplication application = ApplicationContext.getApplication();
		PictureWaybillEditUI ui = openPictureUIActionAndReturn(application);
		ui.setApplication(application);
		return ui;
	}

	/**
	 * 集中接送货开单界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午6:31:35
	 */
	public WaybillEditUI getFocusWaybillEditUI() {
		IApplication application = ApplicationContext.getApplication();
		WaybillEditUI ui = openFocusUIAction(application);
		ui.setApplication(application);
		return ui;
	}
	
	/**
	 * 新增集中开单界面
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午6:42:28
	 */
	public WaybillEditUI openFocusUIAction(IApplication application){
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.newAddAction.waybillFocus.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI");

		WaybillEditUI waybillEditUI = (WaybillEditUI) editor.getComponent();
		// waybillEditUI.setBillType(BillType.ADDNEW);
		waybillEditUI.setEditor(editor);
		//集中开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_FOCUS);
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
    public WaybillEditUI openUIActionAndReturn(IApplication application) {
	EditorConfig editConfig = new EditorConfig();
	editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillSaleDepartment.label"));
	editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
	IEditor editor = application.openEditorAndRetrun(editConfig, "com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI");

	WaybillEditUI waybillEditUI = (WaybillEditUI) editor.getComponent();
	waybillEditUI.setEditor(editor);
	// 营业部开单
	waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
	waybillEditUI.openUI();
	return waybillEditUI;
    }
    /**
	 * 图片开单界面
	 */
    public PictureWaybillEditUI openPictureUIActionAndReturn(IApplication application) {
		EditorConfig editConfig = new EditorConfig();
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.newAddAction.waybillPicture.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI");
	
		PictureWaybillEditUI prictrueWaybillEditUI = (PictureWaybillEditUI) editor.getComponent();
		prictrueWaybillEditUI.setEditor(editor);
		prictrueWaybillEditUI.setPictureOperateType("VIEWORMODIFY");
		//圖片开单
		prictrueWaybillEditUI.setWaybillType(WaybillConstants.WAYBILL_FOCUS);
		prictrueWaybillEditUI.setPictureWaybillType(WaybillConstants.WAYBILL_PICTURE);
		prictrueWaybillEditUI.openUI();
	
		return prictrueWaybillEditUI;
    }

	/**
	 * 给运单基本信息赋值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午5:16:37
	 */
    private void setWaybillVo(WaybillPanelVo vo, WaybillDto dto, WaybillEditUI ui) {
	// 判空操作
	if (null == dto) {
	    return;
	}
	//设置值
	setPendingWaybill(vo,dto,ui);
	// 提货网点
	ShowPickupStationDialogAction pickupStationAction = new ShowPickupStationDialogAction();
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
    public void setPendingWaybill(WaybillPanelVo vo, Object object, WaybillEditUI ui) {
		// 定义重用方法的对象
		WaybillPendingCompleteAction action = new WaybillPendingCompleteAction();
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
		//界面上的值和画布的字段值不一样所以需要用画布的值重新绑定下 2016年4月5日 18:38:37  葛亮亮
		vo.setSupportFee(vo.getInsuranceFee());//保费
		vo.setCollectingFee(vo.getCodFee()); //代收手续费
		
		//设置发货收货客户信息
		setCustomerInfo(object, vo);
    }
    
    /**
     * 重新设置送货费
     * @author WangQianJin
     * @date 2013-6-5 下午3:45:37
     */
    private void resetDeliveryGoodsFee(WaybillPanelVo vo, Object object){
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
     * 设置客户信息数据
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-28 20:17:25
     * @param object
     * @param vo
     */
    public void setCustomerInfo(Object object, WaybillPanelVo vo) {
    	// 对象类型转换
    	if(object instanceof WaybillPendingDto){
    	    WaybillPendingDto pendingDto = (WaybillPendingDto) object;
    	    if(pendingDto.getWaybillPending() != null && StringUtils.isNotEmpty(pendingDto.getWaybillPending().getDeliveryCustomerCode())){
    	    	vo.setDeliveryCustomerCode(pendingDto.getWaybillPending().getDeliveryCustomerCode());
    	    }
    	    if(pendingDto.getWaybillPending() != null && StringUtils.isNotEmpty(pendingDto.getWaybillPending().getReceiveCustomerCode())){
    	    	vo.setReceiveCustomerCode(pendingDto.getWaybillPending().getReceiveCustomerCode());
    	    }
    	} else if (object instanceof WaybillDto) {
    	    WaybillDto waybillDto = (WaybillDto) object;
    	    if(waybillDto.getWaybillEntity() != null && StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getDeliveryCustomerCode())){
    	    	vo.setDeliveryCustomerCode(waybillDto.getWaybillEntity().getDeliveryCustomerCode());
    	    }
    	    if(waybillDto.getWaybillEntity() != null && StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getReceiveCustomerCode())){
    	    	vo.setReceiveCustomerCode(waybillDto.getWaybillEntity().getReceiveCustomerCode());
    	    }
    	}
    }
    
    /**
     * 返回修改前的包装费
     * @author 026123-foss-lifengteng
     * @date 2013年5月13日 下午04:07:29
     */
    public void setOldPackageFee(WaybillPanelVo vo, Object object, BigDecimal oldFee) {
		BigDecimal packageFee = CommonUtils.defaultIfNull(oldFee);
		// 对象类型转换
		if (object instanceof WaybillPendingDto || object instanceof WaybillDto) {
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