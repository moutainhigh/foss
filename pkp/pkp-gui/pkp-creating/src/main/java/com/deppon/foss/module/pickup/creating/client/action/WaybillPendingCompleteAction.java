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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/WaybillPendingCompleteAction.java
 * 
 * FILE NAME        	: WaybilPendingCompleteAction.java
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.creating.client.ui.order.PendingCompleteDialog;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;

public class WaybillPendingCompleteAction implements
		IButtonActionListener<WaybillEditUI> {
	// 日志
	public static final Logger LOGGER = LoggerFactory
			.getLogger(WaybillPendingCompleteAction.class);

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(WaybillPendingCompleteAction.class);

	private static final double TWOPOINTFIVE = 2.5;

	// 运单界面
	WaybillEditUI ui;

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// 待补录运单号录入框
			PendingCompleteDialog dialog = new PendingCompleteDialog();
			// 居中显示弹出窗口
			WindowUtil.centerAndShow(dialog);

			WaybillPendingDto waybillDto = dialog.getWaybillPendingDto();
			if (waybillDto == null) {
				return;
			} else {
				/**
				 * BUG-30351运单暂存之后，有代收，直接在界面上点击补录运单输入此运单号，不点击计算总运费，直接点击提交
				 * 报明细跟总运费不一致。。。见图 092444 再次点击计算总运费就不报了
				 */
				Common.setSaveAndSubmitFalse(ui);

				if (waybillDto.getWaybillPending() != null) {
					// 当前组织编码
					UserEntity user = (UserEntity) SessionContext
							.getCurrentUser();
					String currentOrgCode = user.getEmployee().getDepartment()
							.getCode();
					// String createOrgCode =
					// waybillDto.getWaybillPending().getCreateOrgCode();

					/*****合伙人零担暂存运单优化 begin  by 352676  *****/
					//获取收货部门编码  RECEIVE_ORG_CODE
					//判断收货部门是否合伙人部门
					IWaybillHessianRemoting waybillHessianRemoting = DefaultRemoteServiceFactory
							.getService(IWaybillHessianRemoting.class);
			  	  	String isReceiverPartnerOrg="";
			  	  	SaleDepartmentEntity receiverDepartmentEntity = waybillHessianRemoting.querySimpleSaleDepartmentByCodeCache(waybillDto.getWaybillPending().getReceiveOrgCode());
		  	  		if(null != receiverDepartmentEntity && StringUtil.equals(receiverDepartmentEntity.getIsLeagueSaleDept(), FossConstants.YES)){
		  	  			isReceiverPartnerOrg = FossConstants.YES;
		  	  		}else{
		  	  			isReceiverPartnerOrg = FossConstants.NO;
		  	  		}
					//判断当前部门是否合伙人部门
					String isCurrentPartnerOrg="";
					SaleDepartmentEntity currentPartnerDepartmentEntity = waybillHessianRemoting.querySimpleSaleDepartmentByCodeCache(currentOrgCode);
		  	  		if(null != currentPartnerDepartmentEntity && StringUtil.equals(currentPartnerDepartmentEntity.getIsLeagueSaleDept(), FossConstants.YES)){
		  	  			isCurrentPartnerOrg = FossConstants.YES;
		  	  		}else{
		  	  			isCurrentPartnerOrg = FossConstants.NO;
		  	  		}
		  	  		// 若当前登陆账户为加盟网点所属人员，则判断暂存的运单信息中收货部门是否为当前用户所属部门
		  	  		if(StringUtil.equals(isCurrentPartnerOrg, FossConstants.YES)){
		  	  			if(!StringUtil.equals(currentOrgCode, waybillDto.getWaybillPending().getReceiveOrgCode())){
		  	  				throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.notCurrentDeptStagingWaybill"));
		  	  			}
		  	  		}
		  	  		// 若当前登陆账户为直营网点所属人员，判断暂存的运单信息中收货部门是否为合伙人部门，如果是合伙人部门，则查询失败
		  	  		if(StringUtil.equals(isCurrentPartnerOrg, FossConstants.NO)){
		  	  			if(StringUtil.equals(isReceiverPartnerOrg, FossConstants.YES)){
		  	  				throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.notCurrentDeptStagingWaybill"));
		  	  			}
		  	  		}	
					/*****合伙人零担暂存运单优化 end  *****/
					
					// 根据编码查询组织信息
					OrgAdministrativeInfoEntity org = CommonUtils
							.queryOwerDeptByCode(currentOrgCode);
					// 判断是否是开单组
					if (FossConstants.YES.equals(org.getBillingGroup())) {
						// 出发部门编码
						String receiveOrgCode = waybillDto.getWaybillPending()
								.getReceiveOrgCode();
						// 出发部门名称
						String receiveOrgName = CommonUtils
								.getDeptNameByCode(receiveOrgCode);
						// 判断是否属性正确的开单查询组
						if (!Common.isBelongCurrentDept(receiveOrgCode)) {
							throw new WaybillImportException(
									receiveOrgName
											+ i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.notBelongCurrentDept"));
						}
					}

					// TODO 小件添加 快递运单 不能导入的0------------
					if (CommonUtils.directDetermineIsExpressByProductCode(waybillDto.getWaybillPending().getProductCode())) {
						MsgBox.showInfo(i18n
								.get("foss.gui.creating.waybillPendingCompleteAction.MsgBox.express"));

						return;
					}

					/*
					 * if(createOrgCode!=null &&!
					 * createOrgCode.equals(currentOrgCode)){
					 * if(waybillDto.getWaybillPending()!=null){ StringBuffer
					 * str = new StringBuffer(); str.append(i18n.get(
					 * "foss.gui.creating.waybillPendingCompleteAction.exception.notTheSameDept"
					 * )+"\n"); str.append(i18n.get(
					 * "foss.gui.creating.numberPanel.waybillNo.label"
					 * )+":"+waybillDto
					 * .getWaybillPending().getWaybillNo()+"\n");
					 * str.append(i18n.get(
					 * "foss.gui.creating.waybillPendingCompleteAction.MsgBox.createOrgCode"
					 * )+":"+CommonUtils.getDeptNameByCode(createOrgCode)+"\n");
					 * str.append(i18n.get(
					 * "foss.gui.creating.waybillPendingCompleteAction.MsgBox.currentOrgCode"
					 * )+":"+CommonUtils.getDeptNameByCode(currentOrgCode));
					 * throw new WaybillImportException(str.toString()); }
					 * return; }
					 */

				}


				initData(Common.getOrderCustInfo(waybillDto));

				HashMap<String, IBinder<WaybillPanelVo>> map = ui
						.getBindersMap();
				IBinder<WaybillPanelVo> waybillBinder = map
						.get("waybillBinder");
				WaybillPanelVo vo = waybillBinder.getBean();
				setPendingWaybill(vo, waybillDto, ui);
				//精准包裹提示
				Common.validateProductCode(vo);
				
				Common.setOtherPendingData(ui, vo);
				Common.refreshLabeledGood(vo, ui);// zxy 20131118 ISSUE-4391
													// 刷新打木托list
				ui.consignerPanel.getTxtConsignerMobile().requestFocus();
				WaybillPanelVo voimport = new WaybillPanelVo();
				try {// 这个是用于提交对比的对象 不能用同一个对象 要对比
					PropertyUtils.copyProperties(voimport, vo);
				} catch (Exception ee) {
					LOGGER.error("copyProperties异常", ee);
				}
				// 用于在提交的时候比较老新数据的差异
				ui.setImportWaybillPanelVo(voimport);
			}
		} catch (BusinessException er) {
			LOGGER.error("运单补录异常，原因：" + er.getMessage(), er);
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.waybillPendingCompleteAction.MsgBox.waybillPendingException")
					+ er.getMessage());
		}
	}

	/**
	 * 初始化控件数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-20 上午10:58:35
	 */
	private void initData(WaybillPendingDto waybillDto) {
		validateData(waybillDto);
		initCombProductType(waybillDto.getWaybillPending());
	}

	/**
	 * 校验导入的数据是否合法
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-21 上午10:37:51
	 */
	private void validateData(WaybillPendingDto waybillDto) {
		WaybillPendingEntity waybill = waybillDto.getWaybillPending();
		if (null == waybill) {
			LOGGER.error("待处理运单基本信息[WaybillPendingEntity]不能为空！");
			throw new WaybillImportException(
					i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPendingEntity"));
		}
		// 产品类型
		String product = waybill.getProductCode();
		// 是否外发
		String isOutAgent = waybill.getIsOuterBranch();
		// 运单处理类型
		String pendingType = StringUtil.defaultIfNull(waybill.getPendingType());

		// 运单处理类型不能为空
		if ("".equals(pendingType)) {
			throw new WaybillImportException(
					i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPendingType"));
		}

		// 是否外发
		if (BooleanConvertYesOrNo.stringToBoolean(isOutAgent)) {
			// 偏线
			String partialLine = ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE;
			// 空运
			String airFreight = ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT;
			// 判断导入数据是否合法
			if (!partialLine.equals(product) && !airFreight.equals(product)) {
				throw new WaybillImportException(
						i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.errorProductCode"));
			}
		}
	}

	/**
	 * 根据业务规则来初始化下拉框控件 1、若补录运单为外发，则产品只能为空运和偏线 2、....
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-20 上午10:11:48
	 */
	private void initCombProductType(WaybillPendingEntity waybillEntity) {
		boolean isOutAgent = BooleanConvertYesOrNo.stringToBoolean(StringUtil
				.defaultIfNull(waybillEntity.getIsOuterBranch()));
		// 若为外发代理，则产品只能为空运或偏给
		DefaultComboBoxModel combProduct = ui.getProductTypeModel();
		if (isOutAgent) {
			DefaultComboBoxModel newCombProduct = new DefaultComboBoxModel();
			ProductEntityVo product = null;
			int count = combProduct.getSize();
			// 偏线
			String partialLine = ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE;
			// 空运
			String airFreight = ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT;
			for (int i = 0; i < count; i++) {
				product = (ProductEntityVo) combProduct.getElementAt(i);
				if (partialLine.equals(product.getCode())
						|| airFreight.equals(product.getCode())) {
					newCombProduct.addElement(product);
				}
			}
			if (StringUtils.isNotBlank(ui.getPictureWaybillType())
					&& WaybillConstants.WAYBILL_PICTURE.equals(ui
							.getPictureWaybillType())) {
				ui.pictureTransferInfoPanel.getCombProductType().setModel(
						newCombProduct);
			} else {
				ui.transferInfoPanel.getCombProductType().setModel(
						newCombProduct);
			}
		} else {
			// 重新在运单开单点击“新增”按钮
		}
		String productCode = waybillEntity.getProductCode();
		Common.modifyProdctType(combProduct, productCode, ui,
				waybillEntity.getReceiveOrgCode());
	}

	/**
	 * 给运单bean赋值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午5:15:30
	 */
	public void setPendingWaybill(WaybillPanelVo vo, Object object,
			WaybillEditUI ui) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		// 对象非空判断
		if (null == object) {
			return;
		}

		// 已开单运单DTO
		WaybillPendingDto pendingDto = null;
		// 待处理运单DTO
		WaybillDto waybillDto = null;

		// 提货方式
		String receiveMethod = "";

		// 对象类型转换
		if (object instanceof WaybillPendingDto) {
			pendingDto = (WaybillPendingDto) object;
			// 获得基本信息实体
			WaybillPendingEntity pendingEntity = pendingDto.getWaybillPending();
			// 非空判断
			if (null == pendingEntity) {
				// 抛出异常信息至前台
				throw new WaybillImportException(
						i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPending"));
			} else {
				receiveMethod = pendingEntity.getReceiveMethod();
			}
			BigDecimal goodsWeightTotal = pendingDto.getWaybillPending()
					.getGoodsWeightTotal();
			BigDecimal goodsVolumeTotal = pendingDto.getWaybillPending()
					.getGoodsVolumeTotal();
			
			if ( (goodsWeightTotal != null 
					&& goodsVolumeTotal != null) 
					&& (goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0 
							|| goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) > 0) ) {
				vo.setBigTicket(true);
			} else {
				vo.setBigTicket(false);
			}
			// 设置开户银行信息
			// 判断是否有银行帐号信息
			if (StringUtils.isNotEmpty(pendingEntity.getAccountCode())) {
				vo.setOpenBank(Common.queryCusAccountByAccount(
						pendingEntity.getDeliveryCustomerCode(),
						pendingEntity.getAccountCode()));
			} else {
				vo.setOpenBank(pendingDto.getOpenBank());
			}
			// 地址备注
			if (StringUtils.isNotEmpty(pendingEntity
					.getDeliveryCustomerAddressNote())) {
				vo.setDeliveryCustomerAddressNote(pendingEntity
						.getDeliveryCustomerAddressNote());
			}
			if (StringUtils.isNotEmpty(pendingEntity
					.getReceiveCustomerAddressNote())) {
				vo.setReceiveCustomerAddressNote(pendingEntity
						.getReceiveCustomerAddressNote());
			}
			if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getOrderNo())){
				//设置【是否统一结算】 
				vo.setStartCentralizedSettlement(pendingDto.getWaybillPending().getStartCentralizedSettlement());
				vo.setArriveCentralizedSettlement(pendingDto.getWaybillPending().getArriveCentralizedSettlement());
				//【合同部门】
				if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getStartContractOrgCode())){
				vo.setStartContractOrgCode(pendingDto.getWaybillPending().getStartContractOrgCode());
				}else{
				vo.setStartContractOrgCode(null);
				}
				if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getStartContractOrgCode())){
				vo.setStartContractOrgName(CommonUtils.queryContractOrgName(pendingDto.getWaybillPending().getStartContractOrgCode()));
				}else{
				vo.setStartContractOrgName(null);
				}

				if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getArriveContractOrgCode())){
				vo.setArriveContractOrgCode(pendingDto.getWaybillPending().getArriveContractOrgCode());
				}else{
				vo.setArriveContractOrgCode(null);
				}
				if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getArriveContractOrgCode())){
				vo.setArriveContractOrgName(CommonUtils.queryContractOrgName(pendingDto.getWaybillPending().getArriveContractOrgCode()));
				}else{
				vo.setArriveContractOrgName(null);
				}
				//【催款部门编码】
				vo.setStartReminderOrgCode(pendingDto.getWaybillPending().getStartReminderOrgCode());
				vo.setArriveReminderOrgCode(pendingDto.getWaybillPending().getArriveReminderOrgCode());
				}
			String deliveryCustomerCode = pendingEntity.getDeliveryCustomerCode();
			if(StringUtils.isNotEmpty(deliveryCustomerCode)){
				CustomerEntity query = new CustomerEntity();
				query.setCusCode(deliveryCustomerCode);
				CustomerEntity cust =waybillService.queryCustInfoByCustomerEntity(query);
				if(cust!=null){
					//设置客户和行业分群
					String  flabelleavemonth = cust.getFlabelleavemonth();
					if(StringUtils.isEmpty(flabelleavemonth)){
						flabelleavemonth = "NEWCUST";
					}
					Common.fillFlabelleavemonthData(vo, ui, flabelleavemonth);
				}
			}
			Common.fillIndustrySourceCategoryData(vo, ui);
			
		} else if (object instanceof WaybillDto) {
			waybillDto = (WaybillDto) object;
			// 获得基本信息实体
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
			// 非空判断
			if (null == waybillEntity) {
				// 抛出异常信息至前台
				throw new WaybillImportException(
						i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPending"));
			} else {
				receiveMethod = waybillEntity.getReceiveMethod();
			}
			if (waybillDto.getActualFreightEntity() != null) {
				// 地址备注
				if (StringUtils.isNotEmpty(waybillDto.getActualFreightEntity()
						.getDeliveryCustomerAddressNote())) {
					vo.setDeliveryCustomerAddressNote(waybillDto
							.getActualFreightEntity()
							.getDeliveryCustomerAddressNote());
				}
				if (StringUtils.isNotEmpty(waybillDto.getActualFreightEntity()
						.getReceiveCustomerAddressNote())) {
					vo.setReceiveCustomerAddressNote(waybillDto
							.getActualFreightEntity()
							.getReceiveCustomerAddressNote());
				}
				/**
				 * 将查询出的纸纤包装信息赋值到VO中
				 * 
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-12-9下午19:54
				 */
				if (waybillDto.getActualFreightEntity().getPaperBoxTotlePrice() != null) {
					vo.setPaperBoxTotlePrice(waybillDto
							.getActualFreightEntity().getPaperBoxTotlePrice());
				}
				if (waybillDto.getActualFreightEntity().getFibelBagTotlePrice() != null) {
					vo.setFibelBagTotlePrice(waybillDto
							.getActualFreightEntity().getFibelBagTotlePrice());
				}
				if (waybillDto.getActualFreightEntity().getOtherTotle() != null) {
					vo.setOtherTotle(waybillDto.getActualFreightEntity()
							.getOtherTotle());
				}
				if (waybillDto.getActualFreightEntity().getPackingTotle() != null) {
					vo.setPackingTotle(waybillDto.getActualFreightEntity()
							.getPackingTotle());
				}
				//设置客户和行业分群
				String  flabelleavemonth = waybillDto.getActualFreightEntity().getFlabelleavemonth();
				if(StringUtils.isEmpty(flabelleavemonth)){
					flabelleavemonth = "NEWCUST";
				}
				Common.fillFlabelleavemonthData(vo, ui, flabelleavemonth);
				Common.fillIndustrySourceCategoryData(vo, ui);
			}
			BigDecimal goodsVolumeTotal = waybillDto.getWaybillEntity()
					.getGoodsVolumeTotal();
			BigDecimal goodsWeightTotal = waybillDto.getWaybillEntity()
					.getGoodsWeightTotal();
			if (goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0
					|| goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) > 0) {
				vo.setBigTicket(true);
			} else {
				vo.setBigTicket(false);
			}
			// 设置开户银行信息
			// 判断是否有银行帐号信息
			if (StringUtils.isNotEmpty(waybillEntity.getAccountCode())) {
				vo.setOpenBank(Common.queryCusAccountByAccount(
						waybillEntity.getDeliveryCustomerCode(),
						waybillEntity.getAccountCode()));
			} else {
				vo.setOpenBank(waybillDto.getOpenBank());
			}
		} else {
			return;
		}
		// 设置待处理运单基本信息
		setWaybillPending(vo, object, ui);
		// 内部带货
		if (!WaybillConstants.INNER_PICKUP.equals(CommonUtils
				.defaultIfNull(receiveMethod))) {
			// 设置待处理运单费用明细信息
			setWaybillCHDtlPending(vo, object, ui);
			// 设置待处理运单折扣明细
			setWaybillDisDtlPending(object, ui);
			// 设置待处理运单付款明细
			setWaybillPaymentPending(vo, object, ui);
		} else {
			vo.setBigTicket(false);
		}
		// 设置打木架信息
		setWoodenRequirePending(vo, object, ui);

		// 对象类型转换
		if (object instanceof WaybillPendingDto) {
			pendingDto = (WaybillPendingDto) object;
			// 获得基本信息实体
			WaybillPendingEntity pendingEntity = pendingDto.getWaybillPending();
			// 非空判断
			if (null != pendingEntity) {
				// 判断是否PDA导入运单
				if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING
						.equals(pendingEntity.getPendingType())) {
					// 默认上门接货
					ui.basicPanel.getCboReceiveModel().setSelected(true);
				}
			}
		}
		//liding comment for NCI
		/**
		 * 通过付款方式判断“交易流水号”是否可编辑
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-26上午10:40
		 */
//		Common.whetherBankCardPayment(vo, ui);
	}

	/**
	 * 获得银行帐号信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-12 下午7:48:31
	 */
	public CusAccountEntity getCusAccountInfo(String customerCode,
			CusAccountEntity entity) {
		// 对象非空判断
		if (null == entity) {
			return null;
		}

		// 客户编码
		String custCode = StringUtil.defaultIfNull(customerCode);
		// 银行帐号
		String account = StringUtil.defaultIfNull(entity.getAccountNo());

		return Common.queryCusAccountByAccount(custCode, account);
	}

	/**
	 * 给运单基本信息赋值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午5:16:37
	 */
	public void setWaybillPending(WaybillPanelVo vo, Object object,
			WaybillEditUI ui) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		// zxy 20131118 ISSUE-4391 start 新增：调用清掉上一次的打木架信息，否则会拿上一次的数据进行校验
		Common.unsetWaybillPanelVoForWoodenPack(vo, ui);
		Common.unsetStorageMatterForWoodenPack(vo, ui);
		Common.unsetWoodenPackFee(vo);
		Common.refreshLabeledGood(vo, ui); // 刷新标签流水号列表
		// zxy 20131118 ISSUE-4391 end 新增：调用清掉上一次的打木架信息，否则会拿上一次的数据进行校验
		// 定义待处理运单实体
		WaybillPendingEntity waybillPending = null;
		/**
		 * 根据Dmana-10888增加判断参数
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-02下午16:56
		 */
		boolean flag=true;
		// 对象类型判断
		if (object instanceof WaybillDto) {
			WaybillDto waybillDto = (WaybillDto) object;
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
			waybillPending = new WaybillPendingEntity();
			if (waybillDto.getActualFreightEntity() != null) {
				if (waybillDto.getActualFreightEntity().getInvoice() != null) {
					if (waybillDto.getActualFreightEntity().getInvoice()
							.equals(WaybillConstants.INVOICE_01)) {
						vo.setInvoice(WaybillConstants.INVOICE_01);
						vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
					} else {
						vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
						vo.setInvoice(WaybillConstants.INVOICE_02);
					}
					flag=false;
				}
				if (StringUtils.isNotEmpty(waybillDto.getActualFreightEntity()
						.getDeliveryCustomerAddressNote())) {
					vo.setDeliveryCustomerAddressNote(waybillDto
							.getActualFreightEntity()
							.getDeliveryCustomerAddressNote());
				}
				if (StringUtils.isNotEmpty(waybillDto.getActualFreightEntity()
						.getReceiveCustomerAddressNote())) {
					vo.setReceiveCustomerAddressNote(waybillDto
							.getActualFreightEntity()
							.getReceiveCustomerAddressNote());
				}
			}
			try {
				// 拷贝属性值
				PropertyUtils.copyProperties(waybillPending, waybillEntity);
			} catch (Exception e) {
				// 添加异常日志
				LOGGER.error("对象拷贝失败！\n原因：" + e.getMessage());
				// 抛出异常信息
				throw new WaybillImportException(
						i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")
								+ e.getMessage(), e.getMessage());
			}
		} else if (object instanceof WaybillPendingDto) {
			WaybillPendingDto pendingDto = (WaybillPendingDto) object;
			waybillPending = pendingDto.getWaybillPending();
			// 获得发货客户信息
			CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
			if (waybillPending != null) {
				dto.setCustCode(waybillPending.getDeliveryCustomerCode());
			}
			// 发票标记时间
			dto.setInvoiceDate(new Date());
			// 获取客户信息
			List<CustomerQueryConditionDto> cdto = Common
					.getCustomerQueryConditionDto(dto);
			String isWhole = waybillPending.getIsWholeVehicle();
			if (FossConstants.YES.equals(isWhole)) {
				ui.consignerPanel.getTxtInvoice().setEnabled(false);
				ui.consignerPanel.getTxtInvoice().setEditable(false);
				ui.consignerPanel.getTxtInvoice().setVisible(false);
				ui.consignerPanel.getCombInvoiceMode().setEnabled(true);
				// ui.consignerPanel.getCombInvoiceMode().setEditable(true);
				ui.consignerPanel.getCombInvoiceMode().setVisible(true);
			}
			if (cdto != null && !FossConstants.YES.equals(isWhole)) {
				for (CustomerQueryConditionDto ddto : cdto) {
					String invoice = ddto.getInvoiceType();
					if (invoice != null
							&& invoice.equals(WaybillConstants.INVOICE_01)) {
						vo.setInvoice(WaybillConstants.INVOICE_01);
						vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
					} else {
						vo.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
						vo.setInvoice(WaybillConstants.INVOICE_02);
					}
					if (invoice != null) {
						flag=false;
						break;
					}
				}
			}
		} else {
			// 若非WaybillPendingEntity和WaybillEntity类型的对象，则抛出错误信息
			throw new WaybillImportException(
					i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.paramErorr"));
		}

		// 设置运单号
		ui.numberPanel.getLblNumber().setText(waybillPending.getWaybillNo());
		// 设置是PC暂存补录
		vo.setPCPending(true);
		vo.setWaybillNoImported(waybillPending.getWaybillNo());
		// 判断订单编码是否为空
		if (!StringUtil.isEmpty(waybillPending.getOrderNo())) {
			// 设置网单号为可编辑
			ui.numberPanel.getLblOrderNumber().setVisible(true);
			// 设置网单号内容
			ui.numberPanel.getLblOrderNumber().setText(
					waybillPending.getOrderNo());
		}
		// 业务工具类
		BusinessUtils businessUtils = new BusinessUtils();
		// 组织编码
		String orgCode = waybillPending.getReceiveOrgCode();
		// 收货部门
		vo.setReceiveOrgCode(CommonUtils.defaultIfNull(orgCode));
		// 货部门省份编码
		if (StringUtil.isNotEmpty(orgCode)) {
			vo.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(orgCode));
		}
		// 货部门省份编码
		vo.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(orgCode));
		// 非空判断
		if (orgCode != null) {
			SaleDepartmentEntity org = waybillService.querySaleDeptByCode(
					orgCode, waybillPending.getBillTime());
			if (org != null) {
				// 始发网点 name
				vo.setReceiveOrgName(CommonUtils.defaultIfNull(org.getName()));
				// 始发网点 create time
				if (org.getOpeningDate() != null) {
					vo.setReceiveOrgCreateTime(org.getOpeningDate());
				}
			}

			// 是否是开的集中接送货运单
			if (businessUtils.isBillGroup(waybillPending.getCreateOrgCode())) {
				// Common.setSalesDepartmentForCentrial(org, vo, ui);
				if (org != null) {
					// 收货部门编号
					vo.setReceiveOrgCode(org.getCode());
					// 收货部门名称
					vo.setReceiveOrgName(org.getName());
					// 设置创建时间
					vo.setReceiveOrgCreateTime(org.getOpeningDate());
					DefaultComboBoxModel combProduct = ui.getProductTypeModel();
					Common.modifyProdctType(combProduct,
							waybillPending.getProductCode(), ui, org.getCode());
				}

			}
		}

		// 是否是开的集中接送货运单
		if (businessUtils.isBillGroup(waybillPending.getCreateOrgCode())) {
			// 设置集中接送货
			vo.setPickupCentralized(true);
		}

		// 运单基本信息ID
		vo.setId(waybillPending.getId());
		// 运单处理状态（必须放在运单号前面）:之所以将状态的设值放在运单号前是为了在设值运单号后的气泡校验可根据运单状态进行
		vo.setWaybillstatus(waybillPending.getPendingType());
		// 运单号
		vo.setWaybillNo(waybillPending.getWaybillNo());
		// 设置原运单号
		vo.setOldWaybillNo(waybillPending.getWaybillNo());
		vo.setOrderNo(waybillPending.getOrderNo());// 订单号
		vo.setOrderChannel(waybillPending.getOrderChannel());// 订单来源
		vo.setOrderPayment(waybillPending.getOrderPaidMethod());// zxy 20131211
																// DEFECT-509
																// 新增：设置网单付款方式
		vo.setDeliveryCustomerId(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryCustomerId()));// 发货客户ID
		vo.setDeliveryCustomerContactId(waybillPending
				.getDeliveryCustomerContactId());// 发货客户联系人ID
		vo.setDeliveryCustomerCode(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryCustomerCode()));// 发货客户编码
		/**
		 * 根据Dmana-10888获取发票标记
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-21上午09:53
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
		vo.setDeliveryBigCustomer(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryBigCustomer()));// 大客户标记
		// 设置大客户标记
		if (FossConstants.ACTIVE.equals(vo.getDeliveryBigCustomer())) {
			ui.consignerPanel.getLabel2().setIcon(
					CommonUtils.createIcon(ui.consignerPanel.getClass(),
							IconConstants.BIG_CUSTOMER, 1, 1));
		} else {
			ui.consignerPanel.getLabel2().setIcon(
					CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1,
							1));
		}

		vo.setDeliveryCustomerName(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryCustomerName()));// 发货客户名称
		vo.setDeliveryCustomerMobilephone(CommonUtils
				.defaultIfNull(waybillPending.getDeliveryCustomerMobilephone()));// 发货客户手机
		vo.setDeliveryCustomerPhone(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryCustomerPhone()));// 发货客户电话
		vo.setDeliveryCustomerContact(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryCustomerContact()));// 发货客户联系人
		vo.setIsAccuratePackage(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryCustomerIsAccuratePackage()));//发货客户是否精准包裹
		// 发货具体地址
		vo.setDeliveryCustomerAddress(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryCustomerAddress()));

		// 进行地址备注的写入
		vo.setDeliveryCustomerAddressNote(CommonUtils
				.defaultIfNull(waybillPending.getDeliveryCustomerAddressNote()));
		vo.setReceiveCustomerAddressNote(CommonUtils
				.defaultIfNull(waybillPending.getReceiveCustomerAddressNote()));
		/**
		 * 发货客户国家省市区
		 */
		AddressFieldDto consignerAddress = businessUtils.getProvCityCounty(
				waybillPending.getDeliveryCustomerProvCode(),
				waybillPending.getDeliveryCustomerCityCode(),
				waybillPending.getDeliveryCustomerDistCode());
		if (null != consignerAddress) {
			// 发货国家
			vo.setDeliveryCustomerNationCode(consignerAddress.getNationId());
			// 发货省份
			vo.setDeliveryCustomerProvCode(consignerAddress.getProvinceId());
			// 发货市
			vo.setDeliveryCustomerCityCode(consignerAddress.getCityId());
			// 发货区
			vo.setDeliveryCustomerDistCode(consignerAddress.getCountyId());
			// 发货区域
			vo.setDeliveryCustomerAreaDto(consignerAddress);
			vo.setDeliveryCustomerArea(businessUtils
					.getAddressAreaText(consignerAddress));
			/**
			 * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
			 * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
			 */
			ui.getConsignerPanel().getTxtConsignerArea()
					.setAddressFieldDto(consignerAddress);
		}
		
		if(StringUtils.isNotBlank(waybillPending.getDeliveryCustomerCode())){
			// 是否月结客户
			CusBargainEntity cusBargainEntity = Common
					.getCusBargainEntity(waybillPending.getDeliveryCustomerCode());
			if (cusBargainEntity != null) {
				vo.setChargeMode(Common.isChargeMode(cusBargainEntity
						.getChargeType()));
			} else {
				vo.setChargeMode(false);
			}
			if (cusBargainEntity != null) {
				vo.setPreferentialType(cusBargainEntity.getPreferentialType());// 获取优惠类型
			} else {
				vo.setPreferentialType(null);// 获取优惠类型
			}
		}else{
			//巨商汇阿里商城默认月结
			if(WaybillConstants.GIANT_SINK.equals(waybillPending.getOrderChannel())
					||WaybillConstants.ALIBABA.equals(waybillPending.getOrderChannel())){
				vo.setChargeMode(true);// 是否月结
			}
		}
		

		vo.setReceiveCustomerId(CommonUtils.defaultIfNull(waybillPending
				.getReceiveCustomerId()));// 收货客户ID
		vo.setReceiveCustomerContactId(waybillPending
				.getReceiveCustomerContactId());// 收货联系人ID
		vo.setReceiveCustomerCode(CommonUtils.defaultIfNull(waybillPending
				.getReceiveCustomerCode()));// 收货客户编码
		vo.setReceiveBigCustomer(CommonUtils.defaultIfNull(waybillPending
				.getReceiveBigCustomer()));// 大客户标记
		// 设置大客户标记
		if (FossConstants.ACTIVE.equals(vo.getDeliveryBigCustomer())) {
			ui.consigneePanel.getLblNewLabel().setIcon(
					CommonUtils.createIcon(ui.consigneePanel.getClass(),
							IconConstants.BIG_CUSTOMER, 1, 1));
		} else {
			ui.consigneePanel.getLblNewLabel().setIcon(
					CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1,
							1));
		}
		vo.setReceiveCustomerName(CommonUtils.defaultIfNull(waybillPending
				.getReceiveCustomerName()));// 收货客户名称
		vo.setReceiveCustomerMobilephone(CommonUtils
				.defaultIfNull(waybillPending.getReceiveCustomerMobilephone()));// 收货客户手机
		vo.setReceiveCustomerPhone(CommonUtils.defaultIfNull(waybillPending
				.getReceiveCustomerPhone()));// 收货客户电话
		vo.setReceiveCustomerContact(CommonUtils.defaultIfNull(waybillPending
				.getReceiveCustomerContact()));// 收货客户联系人
		// 接送货地址ID
		vo.setContactAddressId(waybillPending.getContactAddressId());
		// 收货具体地址
		vo.setReceiveCustomerAddress(CommonUtils.defaultIfNull(waybillPending
				.getReceiveCustomerAddress()));

		/**
		 * 收货客户省市区
		 */
		AddressFieldDto consigneeAddress = businessUtils.getProvCityCounty(
				waybillPending.getReceiveCustomerProvCode(),
				waybillPending.getReceiveCustomerCityCode(),
				waybillPending.getReceiveCustomerDistCode());
		if (null != consigneeAddress) {
			// 收货国家
			vo.setReceiveCustomerNationCode(consigneeAddress.getNationId());
			// 收货省份
			vo.setReceiveCustomerProvCode(consigneeAddress.getProvinceId());
			// 收货市
			vo.setReceiveCustomerCityCode(consigneeAddress.getCityId());
			// 收货区
			vo.setReceiveCustomerDistCode(consigneeAddress.getCountyId());
			// 收货区域
			vo.setReceiveCustomerAreaDto(consigneeAddress);
			vo.setReceiveCustomerArea(businessUtils
					.getAddressAreaText(consigneeAddress));
			/**
			 * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
			 * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
			 */
			ui.getConsigneePanel().getTxtConsigneeArea()
					.setAddressFieldDto(consigneeAddress);
		}

		// 运输性质
		if (null != waybillPending.getProductCode()) {
			String productCode = waybillPending.getProductCode();
			// 判断是否为整车
			if (FossConstants.YES.equals(waybillPending.getIsWholeVehicle())) {
				// 清空产品，设置为整车产品
				Common.cleanProductToWholeVehicle(ui);
				// 设置产品类型为整车
				Common.setProductCode(
						ui,
						vo,
						CommonUtils
								.defaultIfNull(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE));
				// 因为重新设置了运单性质导致界面没有全部不可编辑 ，需要重新设置全不可编辑
				if (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE
						.equals(waybillPending.getPendingType())
						|| WaybillConstants.WAYBILL_STATUS_PC_ACTIVE
								.equals(waybillPending.getPendingType())) {
					UIUtils.disableUI(ui);
				}
			} else if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG
					.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD
							.equals(productCode)) {
				Common.setProductCode(ui, vo, CommonUtils
						.defaultIfNull(waybillPending.getProductCode()));
				vo.setIsBigGoods(true);
			} else {
				// 设置产品类型
				Common.setProductCode(ui, vo, CommonUtils
						.defaultIfNull(waybillPending.getProductCode()));
			}
			
			if(vo.getSpecialValueAddedServiceType()!=null){
				if(WaybillConstants.FURNITURE_EQUIP.equals(vo.getSpecialValueAddedServiceType().getValueCode())||
				      WaybillConstants.BUILD_MATERIAL_EQUIP.equals(vo.getSpecialValueAddedServiceType().getValueCode())||
				      WaybillConstants.HOME_APPLICATION_EQUIP.equals(vo.getSpecialValueAddedServiceType().getValueCode())){
				   //根据特殊增值服务，改变提货方式
			     	Common.changePickUpModeForSpecial(vo, ui);
				    }
			}else{			
				// 根据运输性质的改变，改变提货方式
				Common.changePickUpMode(vo, ui);
			}
			// 空运、偏线以及中转下线无法选择签收单返单
			Common.setReturnBill(vo, ui);
			// 偏线与空运不能选择预付费保密
			Common.setSecretPrepaid(vo, ui);
		}

		// 提货方式
		vo.setReceiveMethod(Common.getCombBoxValue(ui.getPikcModeModel(),
				waybillPending.getReceiveMethod()));
		// 设置提货网点
		if (waybillPending.getCustomerPickupOrgCode() != null) {
			BranchVo pickup = businessUtils.getCustomerPickupOrg(
					waybillPending.getCustomerPickupOrgCode(),
					waybillPending.getProductCode(),
					waybillPending.getBillTime());
			if (pickup != null) {
				vo.setCustomerPickupOrgCode(pickup);// 提货网点
				vo.setCustomerPickupOrgName(pickup.getName());// 提货网点名称
				vo.setTargetOrgCode(pickup.getTargetOrgName());// 目的站
				// 设置是否可代收货款和是否可到付款标致
				canAgentCollected(pickup, vo);
			}
		}

		/**
		 * 判断返回类型是否有值若无，则给一个默认值 之所以做此判断，是因为导入PDA开单时有可能没有返回类型
		 * 
		 * 由于用到提货网点，所以放在设置提货网点的后面
		 */
		if ("".equals(StringUtil.defaultIfNull(waybillPending
				.getReturnBillType()))) {
			// 返单类别
			vo.setReturnBillType(Common.getCombBoxValue(
					ui.getCombReturnBillTypeModel(),
					WaybillConstants.NOT_RETURN_BILL));
		} else {
			// 返单类别
			vo.setReturnBillType(Common.getCombBoxValue(
					ui.getCombReturnBillTypeModel(),
					waybillPending.getReturnBillType()));
		}

		vo.setLoadMethod(CommonUtils.defaultIfNull(waybillPending
				.getLoadMethod()));// 配载类型
		vo.setPickupToDoor(BooleanConvertYesOrNo.stringToBoolean(waybillPending
				.getPickupToDoor()));// 是否上门接货
		vo.setDriverCode(waybillPending.getDriverCode());// 司机
		vo.setPickupCentralized(BooleanConvertYesOrNo
				.stringToBoolean(waybillPending.getPickupCentralized()));// 是否集中接货
		vo.setLoadLineCode(waybillPending.getLoadLineCode());// 配载线路
		vo.setLoadOrgCode(CommonUtils.defaultIfNull(waybillPending
				.getLoadOrgCode()));// 配载部门
		vo.setLastLoadOrgCode(CommonUtils.defaultIfNull(waybillPending
				.getLastLoadOrgCode()));// 最终配载部门
		vo.setPreDepartureTime(waybillPending.getPreDepartureTime());// 预计出发时间
		vo.setPreCustomerPickupTime(waybillPending.getPreCustomerPickupTime());// 预计派送/提货时间
		vo.setPreCustomerPickupTime(new Date());// 预计派送/提货时间
		vo.setPreDepartureTime(waybillPending.getPreDepartureTime());// 预计派送/提货时间
		vo.setCarDirectDelivery(BooleanConvertYesOrNo
				.stringToBoolean(waybillPending.getCarDirectDelivery()));// 是否大车直送
		vo.setGoodsName(waybillPending.getGoodsName());// 货物名称

		vo.setGoodsQtyTotal(waybillPending.getGoodsQtyTotal());// 货物总件数
		vo.setGoodsWeightTotal(CommonUtils.defaultIfNull(waybillPending
				.getGoodsWeightTotal()));// 货物总重量
		vo.setGoodsVolumeTotal(CommonUtils.defaultIfNull(waybillPending
				.getGoodsVolumeTotal()));// 货物总体积
		vo.setGoodsSize(waybillPending.getGoodsSize());// 货物尺寸
		// 设置货物类型
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
				.equals(waybillPending.getProductCode())) {
			vo.setAirGoodsType(Common.getCombBoxValue(ui.getCombGoodsType(),
					waybillPending.getGoodsTypeCode()));// 货物类型
		} else {
			vo.setGoodsType(waybillPending.getGoodsTypeCode());// 货物类型
		}
		vo.setIsPassDept(BooleanConvertYesOrNo.stringToBoolean(waybillPending
				.getIsPassOwnDepartment()));
		vo.setPreciousGoods(BooleanConvertYesOrNo
				.stringToBoolean(waybillPending.getPreciousGoods()));// 是否贵重物品
		vo.setSpecialShapedGoods(BooleanConvertYesOrNo
				.stringToBoolean(waybillPending.getSpecialShapedGoods()));// 是否异形物品

		DataDictionaryValueVo outerNotes = new DataDictionaryValueVo();
		outerNotes.setValueCode(waybillPending.getOuterNotes());
		vo.setOuterNotes(outerNotes);// 对外备注
		vo.setInnerNotes(waybillPending.getInnerNotes());// 对内备注
		vo.setTransportationRemark(waybillPending.getTransportationRemark());// 储运事项
		vo.setGoodsPackage(waybillPending.getGoodsPackage());// 货物包装
		vo.setPaper(waybillPending.getPaperNum());// 纸
		vo.setWood(waybillPending.getWoodNum());// 木
		vo.setFibre(waybillPending.getFibreNum());// 纤
		vo.setSalver(waybillPending.getSalverNum());// 托
		vo.setMembrane(waybillPending.getMembraneNum());// 膜
		vo.setOtherPackage(waybillPending.getOtherPackage());// 其他
		// 退款类型
		vo.setRefundType(Common.getCombBoxValue(ui.getCombRefundTypeModel(),
				waybillPending.getRefundType()));
		vo.setSecretPrepaid(BooleanConvertYesOrNo
				.stringToBoolean(waybillPending.getSecretPrepaid()));// 预付费保密
		vo.setToPayAmount(CommonUtils.defaultIfNull(waybillPending
				.getToPayAmount()));// 到付金额
		vo.setPrePayAmount(CommonUtils.defaultIfNull(waybillPending
				.getPrePayAmount()));// 预付金额

		// 优惠费用
		vo.setPromotionsFee(CommonUtils.defaultIfNull(waybillPending
				.getPromotionsFee()));
		// 优惠费用画布
		vo.setPromotionsFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getPromotionsFee())));

		// 运费计费类型
		if (StringUtil.isEmpty(waybillPending.getBillingType())) {
			vo.setBillingType(Common.getCombBoxValue(ui.getCombBillingType(),
					WaybillConstants.BILLINGWAY_WEIGHT));
		} else {
			vo.setBillingType(Common.getCombBoxValue(ui.getCombBillingType(),
					waybillPending.getBillingType()));
		}
		// 运费计费费率
		vo.setUnitPrice(CommonUtils.defaultIfNull(waybillPending.getUnitPrice()));

		// 公布价运费
		vo.setTransportFee(CommonUtils.defaultIfNull(waybillPending
				.getTransportFee()));
		// 公布价运费画布
		vo.setTransportFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getTransportFee())));

		vo.setValueAddFee(CommonUtils.defaultIfNull(waybillPending
				.getValueAddFee()));// 增值费用
		// 开单付款方式
		vo.setPaidMethod(Common.getCombBoxValue(ui.getCombPaymentModeModel(),
				waybillPending.getPaidMethod()));
		// 根据设置的付款方式，设置接送货是否可编辑
		Common.selfPickup(vo, ui);
		vo.setArriveType(CommonUtils.defaultIfNull(waybillPending
				.getArriveType()));// 到达类型
		vo.setActive(waybillPending.getActive());// 运单状态
		vo.setWaybillstatus(waybillPending.getPendingType()); // 运单处理状态

		// 总费用
		vo.setTotalFee(CommonUtils.defaultIfNull(waybillPending.getTotalFee()));
		// 总费用画布
		vo.setTotalFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getTotalFee())));

		vo.setForbiddenLine(waybillPending.getForbiddenLine());// 禁行
		vo.setFreightMethod(Common.getCombBoxValue(ui.getFreightMethod(),
				waybillPending.getFreightMethod()));// 合票方式
		// 设置航班类型
		vo.setFlightNumberType(Common.getCombBoxValue(ui.getFlightNumberType(),
				waybillPending.getFlightNumberType()));
		vo.setFlightShift(CommonUtils.defaultIfNull(waybillPending
				.getFlightShift()));// 航班时间
		vo.setPromotionsCode(waybillPending.getPromotionsCode());// 优惠编码
		vo.setCreateTime(new Date());// 创建时间
		vo.setModifyTime(waybillPending.getModifyTime());// 更新时间

		Boolean isPda = WaybillConstants.WAYBILL_STATUS_PDA_PENDING
				.equals(waybillPending.getPendingType()) ? Boolean.TRUE
				: Boolean.FALSE;
		vo.setIsPdaBill(isPda);// 是否为PDA运单
		if (isPda) {
			vo.setBillTime(waybillPending.getCreateTime());// 开单时间
			vo.setPickupCentralized(true);
		} else {
			vo.setBillTime(new Date());// 开单时间
		}

		vo.setCreateUserCode(CommonUtils.defaultIfNull(waybillPending
				.getCreateUserCode()));// 开单人
		vo.setModifyUserCode(CommonUtils.defaultIfNull(waybillPending
				.getModifyUserCode()));// 更新人
		vo.setCreateOrgCode(CommonUtils.defaultIfNull(waybillPending
				.getCreateOrgCode()));// 开单组织
		vo.setModifyOrgCode(CommonUtils.defaultIfNull(waybillPending
				.getModifyOrgCode()));// 更新组织
		vo.setCurrencyCode(waybillPending.getCurrencyCode());// 币种
		vo.setIsWholeVehicle(BooleanConvertYesOrNo
				.stringToBoolean(waybillPending.getIsWholeVehicle()));// 是否整车运单
		// 是否经过营业部
		vo.setIsPassDept(BooleanConvertYesOrNo.stringToBoolean(waybillPending
				.getIsPassOwnDepartment()));
		// 整车约车编号
		vo.setVehicleNumber(StringUtil.defaultIfNull(waybillPending
				.getOrderVehicleNum()));
		vo.setWholeVehicleActualfee(CommonUtils.defaultIfNull(waybillPending
				.getWholeVehicleActualfee()));// 整车开单报价
		vo.setWholeVehicleAppfee(CommonUtils.defaultIfNull(waybillPending
				.getWholeVehicleAppfee()));// 整车约车报价
		vo.setAccountName(waybillPending.getAccountName());// 返款帐户开户名称
		vo.setAccountCode(waybillPending.getAccountCode());// 返款帐户开户账户
		vo.setAccountBank(waybillPending.getAccountBank());// 返款帐户开户银行
		// 计费重量
		vo.setBillWeight(CommonUtils.defaultIfNull(waybillPending
				.getBillWeight()));
		vo.setPreArriveTime(waybillPending.getPreArriveTime());// 预计到达时间
		vo.setAddTime(waybillPending.getAddTime());// 开单时长

		// 送货费
		vo.setDeliveryGoodsFee(CommonUtils.defaultIfNull(waybillPending
				.getDeliveryGoodsFee()));
		// 送货费画布
		vo.setDeliveryGoodsFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getDeliveryGoodsFee())));

		// 其他费用
		vo.setOtherFee(CommonUtils.defaultIfNull(waybillPending.getOtherFee()));
		// 其他费用画布
		vo.setOtherFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getOtherFee())));

		// 包装手续费
		vo.setPackageFee(CommonUtils.defaultIfNull(waybillPending
				.getPackageFee()));
		// 包装手续费画布
		vo.setPackageFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getPackageFee())));

		// 接货费
		vo.setPickupFee(CommonUtils.defaultIfNull(waybillPending.getPickupFee()));
		// 接货费画布
		vo.setPickUpFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getPickupFee())));
		// 保价声明价值
		vo.setInsuranceAmount(CommonUtils.defaultIfNull(waybillPending
				.getInsuranceAmount()));
		// 保价声明价值画布
		vo.setInsuranceAmountCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getInsuranceAmount())));
		// 保价声明价值及保价声明价值画布，对PDA补录保价，当PDA补录运单无订单时，若保价为0或空，FOSS代收置空，有订单时，FOSS代收置0
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillPending
				.getPendingType())
				&& (waybillPending.getInsuranceAmount() == BigDecimal.ZERO || waybillPending
						.getInsuranceAmount() == null)) {
			if ((" ".equals(waybillPending.getOrderNo()) || waybillPending
					.getOrderNo() == null)) {
				vo.setInsuranceAmount(null);
				vo.setInsuranceAmountCanvas(null);
			} else {
				vo.setInsuranceAmount(BigDecimal.ZERO);
				vo.setInsuranceAmountCanvas(BigDecimal.ZERO.toString());
			}
		}
		// 保价费率
		// 将保险费率转换成千分率的格式
		BigDecimal permillageIns = new BigDecimal(WaybillConstants.PERMILLAGE);
		BigDecimal feeRate = CommonUtils.defaultIfNull(
				waybillPending.getInsuranceRate()).multiply(permillageIns);
		vo.setInsuranceRate(feeRate);
		// 保价费
		vo.setInsuranceFee(CommonUtils.defaultIfNull(waybillPending
				.getInsuranceFee()));
		// 代收货款
		vo.setCodAmount(waybillPending.getCodAmount());
		// 代收货款画布
		vo.setCodAmountCanvas(String.valueOf(waybillPending.getCodAmount()));
		// 代收货款及代收货款画布，对PDA补录代收，当PDA补录运单无订单时，若代收为0或空，FOSS代收置空，有订单时，FOSS代收置0
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillPending
				.getPendingType())
				&& (waybillPending.getCodAmount() == BigDecimal.ZERO || waybillPending
						.getCodAmount() == null)) {
			if ((" ".equals(waybillPending.getOrderNo()) || waybillPending
					.getOrderNo() == null)) {
				vo.setCodAmount(null);
				vo.setCodAmountCanvas(null);
			} else {
				vo.setCodAmount(BigDecimal.ZERO);
				vo.setCodAmountCanvas(BigDecimal.ZERO.toString());
			}
		}
		// 代收费率
		// 将代收货款费率转换成千分率的格式
		BigDecimal permillageCod = new BigDecimal(WaybillConstants.PERMILLAGE);
		BigDecimal codRate = CommonUtils.defaultIfNull(
				waybillPending.getCodRate()).multiply(permillageCod);
		// 代收货款费率
		vo.setCodRate(codRate);
		// 代收货款手续费
		vo.setCodFee(CommonUtils.defaultIfNull(waybillPending.getCodFee()));

		// 装卸费
		vo.setServiceFee(CommonUtils.defaultIfNull(waybillPending
				.getServiceFee()));
		// 装卸费画布
		vo.setServiceFeeCanvas(String.valueOf(CommonUtils
				.defaultIfNull(waybillPending.getServiceFee())));

		// 若为PDA导入运单，则设置ChangeColorTxt属性
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillPending
				.getPendingType())) {
			vo.setChangeColorTxt(Common.getChangeColorTxt(ui, vo));
			// pda需要输入 pda计算总金额
			vo.setTotalCountPDA(CommonUtils.defaultIfNull(waybillPending
					.getTotalFee()));
			// pda需要录入手写现付金额 所以输入框可以编辑
			ui.getBillingPayPanel().getTxtHandWriteMoney().setEnabled(true);
		}
		// 公里数
		vo.setKilometer(waybillPending.getKilometer());

		// 是否经济自提件
		vo.setIsEconomyGoods(FossConstants.YES.equals(waybillPending
				.getIsEconomyGoods()));
		// 经济自提件类型
		try {
			vo.setEconomyGoodsType(getEconomyGoodsTypeByCode(
					waybillPending.getEconomyGoodsType(),
					waybillPending.getBillTime()));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// CRM营销活动
		String activeCode = null;
		if (StringUtils.isNotBlank(ui.getPictureWaybillType())
				&& WaybillConstants.WAYBILL_PICTURE.equals(ui
						.getPictureWaybillType())) {
			if (object instanceof WaybillPendingDto) {
				//设置市场营销活动
				setActiveInfo(vo, object, ui);
			} else {
				WaybillDisDtlEntity disEntity = waybillService
						.queryActiveInfoByNoAndType(waybillPending
								.getWaybillNo());
				if (disEntity != null && disEntity.getActiveCode() != null) {
					activeCode = disEntity.getActiveCode();
				}

				vo.setActiveInfo(Common.getCombBoxValue(
						ui.getCombActiveInfoModel(), activeCode));
			}
		} else {
			WaybillDisDtlEntity disEntity = waybillService
					.queryActiveInfoByNoAndType(waybillPending.getWaybillNo());
			if (disEntity != null && disEntity.getActiveCode() != null) {
				activeCode = disEntity.getActiveCode();
			}

			vo.setActiveInfo(Common.getCombBoxValue(
					ui.getCombActiveInfoModel(), activeCode));
		}

		if (vo.getReceiveMethod() != null) {
			/**
			 * 如果非送货时，公里数不可录入，且要清空
			 */
			if (WaybillConstants.SELF_PICKUP.equals(vo.getReceiveMethod()
					.getValueCode())
					|| WaybillConstants.INNER_PICKUP.equals(vo
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_PICKUP_FREE.equals(vo
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIR_SELF_PICKUP.equals(vo
							.getReceiveMethod().getValueCode())
					|| WaybillConstants.AIRPORT_PICKUP.equals(vo
							.getReceiveMethod().getValueCode())) {
				vo.setKilometer(null);
				if (StringUtils.isNotBlank(ui.getPictureWaybillType())
						&& WaybillConstants.WAYBILL_PICTURE.equals(ui
								.getPictureWaybillType())) {
					// 公里数不可编辑
					ui.pictureTransferInfoPanel.getTxtKilometer().setEditable(
							false);
				} else {
					// 公里数不可编辑
					ui.transferInfoPanel.getTxtKilometer().setEditable(false);
				}
			} else {
				// 公里数可编辑
				// ui.transferInfoPanel.getTxtKilometer().setEditable(true);
				if (StringUtils.isNotBlank(ui.getPictureWaybillType())
						&& WaybillConstants.WAYBILL_PICTURE.equals(ui
								.getPictureWaybillType())) {
					// 公里数不可编辑
					ui.pictureTransferInfoPanel.getTxtKilometer().setEditable(
							true);
				} else {
					// 公里数不可编辑
					ui.transferInfoPanel.getTxtKilometer().setEditable(true);
				}
			}
		}
		/**
		 * 将waybillPending里的是否电子发票传值给VO
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-24下午16：50
		 */
		vo.setIsElectronicInvoice(waybillPending.getIsElectronicInvoice());
		/**
		 * 将waybillPending里的发票电话号码给VO
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-24下午16：50
		 */
		vo.setInvoiceMobilePhone(waybillPending.getInvoiceMobilePhone());
		/**
		 * 将waybillPending里的发票邮箱给VO
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-24下午16：50
		 */
		vo.setEmail(waybillPending.getEmail());

		/**
		 * 将特安客户保价上限值存入waybillpanelvo
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-19下午18:26
		 */
		vo.setVipInsuranceAmount(waybillPending.getVipInsuranceAmount());

		/**
		 * 将代收货款上限值存入waybillpanelvo
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-19下午18:26
		 */
		vo.setVipCollectionPaymentLimit(waybillPending
				.getVipCollectionPaymentLimit());
		
		/**
		 * 将交易流水号值带入VO中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-28下午19:18
		 */
		vo.setTransactionSerialNumber(waybillPending.getTransactionSerialNumber());
		
		/**
		 * Dmana-9885将WaybillPendingEntity的CRM运费传入VO中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-06下午16:08
		 */
		vo.setCrmTransportFee(waybillPending.getCrmTransportFee());
		
		/**
		 * Dmana-9885将WaybillPendingEntity的CRM重量传入VO中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-06下午16:08
		 */
		vo.setCrmWeight(waybillPending.getCrmWeight());
		
		/**
		 * Dmana-9885将WaybillPendingEntity的CRM体积传入VO中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-06下午16:08
		 */
		vo.setCrmVolume(waybillPending.getCrmVolume());
		/**
		 * Dmana-9885通过订单来源判断如果是巨商网或者阿里巴巴来源，付款方式不可更改
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:55
		 */
		if(WaybillConstants.GIANT_SINK.equals(waybillPending.getOrderChannel())
				||WaybillConstants.ALIBABA.equals(waybillPending.getOrderChannel())){
			List<DataDictionaryValueEntity> list = waybillService.queryPaymentMode();
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo dvvo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, dvvo);
				if (WaybillConstants.MONTH_PAYMENT.equals(dvvo.getValueCode()))
				{
					vo.setPaidMethod(dvvo);
				}
			}
			ui.incrementPanel.getCombPaymentMode().setEnabled(false);
		}
		
		//设置完所有信息之后设置保价费率范围，从数据库中重新获取
		Common.getInsuranceRateRange(vo);
		
	}

	private void setActiveInfo(WaybillPanelVo vo, Object object,
			WaybillEditUI ui) {
		WaybillPendingDto pendingDto = (WaybillPendingDto) object;
		if (pendingDto.getWaybillDisDtlPending() != null
				&& pendingDto.getWaybillDisDtlPending().size() > 0) {
			for (int i = 0; i < pendingDto.getWaybillDisDtlPending().size(); i++) {
				WaybillDisDtlPendingEntity disDtlEntity = pendingDto
						.getWaybillDisDtlPending().get(i);
				DefaultComboBoxModel comb = ui.getCombActiveInfoModel();
				for (int j = 0; j < comb.getSize(); j++) {
					DataDictionaryValueVo entity = (DataDictionaryValueVo) comb.getElementAt(j);
					if (null != entity
							&& StringUtil.defaultIfNull(
									disDtlEntity.getActiveCode()).equals(
									entity.getValueCode())) {
						vo.setActiveInfo(entity);
					}
				}
			}
		}
	}

	/**
	 * 根据渠道CODE获取渠道类型
	 * 
	 * @param channelCode
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private DataDictionaryValueVo getEconomyGoodsTypeByCode(String channelCode,
			Date billDate) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		DataDictionaryValueVo vo = null;
		if (StringUtil.isNotEmpty(channelCode)) {
			List<MinFeePlanEntity> minFeeList = waybillService
					.getMinFeePlanEntityByDate(billDate);
			if (minFeeList != null && minFeeList.size() > 0) {
				for (MinFeePlanEntity entity : minFeeList) {
					if (entity != null && channelCode != null
							&& channelCode.equals(entity.getChannelCode())) {
						vo = new DataDictionaryValueVo();
						vo.setId(entity.getId());
						vo.setValueCode(entity.getChannelCode());
						vo.setValueName(entity.getPlanName());
						break;
					}
				}
			}
		}
		return vo;
	}

	/**
	 * 
	 * 判断此网点是否可以开代收货款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-7 下午04:07:04
	 */
	private void canAgentCollected(BranchVo branchVo, WaybillPanelVo bean) {
		// 是否可代收货款
		bean.setCanAgentCollected(branchVo.getCanAgentCollected());
		// 是否可货到付款
		bean.setArriveCharge(branchVo.getArriveCharge());
	}

	/**
	 * 给运单费用信息赋值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午5:16:53
	 */
	private void setWaybillCHDtlPending(WaybillPanelVo vo, Object object,
			WaybillEditUI ui) {
		// 非空判断
		if (null == object) {
			return;
		}

		// 待处理运单费用信息中的非其它费用
		List<WaybillCHDtlPendingEntity> chDtlList = null;
		// 运单号
		String waybillNo = "";
		// 运单状态
		String pendingType = "";
		// 从运单或暂存中提取木架信息
		// 仅用于“管理营业部出发运单”模块提取木架信息和木箱信息使用
		// 其他场景如需使用，请谨慎
		WoodenRequirementsEntity woodenReqEntity = null;
		WoodenRequirePendingEntity woodenReqPendingEntity = null;

		// 类型转换
		if (object instanceof WaybillDto) {
			// 获得WaybillDto对象
			WaybillDto waybillDto = (WaybillDto) object;
			waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
			// 获得费用集合
			List<WaybillChargeDtlEntity> chargeList = waybillDto
					.getWaybillChargeDtlEntity();
			List<WaybillCHDtlPendingEntity> chargePendingList = new ArrayList<WaybillCHDtlPendingEntity>();
			// 遍历并转换
			for (WaybillChargeDtlEntity chargeDtl : chargeList) {
				WaybillCHDtlPendingEntity chargePending = copyToWaybillCHDtlPendingEntity(chargeDtl);
				chargePendingList.add(chargePending);
			}
			chDtlList = chargePendingList;
			pendingType = waybillDto.getWaybillEntity().getPendingType();
			woodenReqEntity = waybillDto.getWoodenRequirementsEntity();
		} else if (object instanceof WaybillPendingDto) {
			WaybillPendingDto pendingDto = (WaybillPendingDto) object;
			// 待处理运单基本信息
			WaybillPendingEntity waybillPending = copyToWaybillPendingEntity(object);
			// 运单号
			waybillNo = waybillPending.getWaybillNo();
			chDtlList = pendingDto.getWaybillChargeDtlPending();
			pendingType = waybillPending.getPendingType();
			woodenReqPendingEntity = pendingDto.getWoodenRequirePending();
		} else {
			return;
		}

		/**
		 * BUG-44572:PDA补录，燃油费用和信息费用没有
		 */
		// 若为PDA导入开单时，由于PDA没有费用分录，因此使用初始化的费用分录
		// 获得其它费用集合
		List<OtherChargeVo> otherList = null;
		IWaybillHessianRemoting waybillHessianRemoting = DefaultRemoteServiceFactory
				.getService(IWaybillHessianRemoting.class);
		WaybillPictureEntity pictureEntity = waybillHessianRemoting
				.queryWaybillPictureByWaybillNo(waybillNo);
		if (!WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingType)
				|| pictureEntity != null) {
			if (WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING
					.equals(pendingType)) {
				// 导入离线单，从本地获取数据（zxy）
				otherList = getOtherCharge(waybillNo, object,
						WaybillServiceFactory.getOfflineWaybillService());
			} else
				otherList = getOtherCharge(waybillNo, object, waybillService);
		} else {
			// zxy 20131017 BUG-57425 start 修改：先根据表单数据查询其他费用数据，如果查询不倒才使用初始化默认数据
			otherList = Common.getOtherChargeData(ui, vo);// 其他费用
			if (otherList == null
					|| (otherList != null && otherList.size() <= 0)) {
				WaybillOtherCharge changeInfoDetailTableModel = ((WaybillOtherCharge) ui.incrementPanel
						.getTblOther().getModel());
				otherList = changeInfoDetailTableModel.getData();
			}
			// zxy 20131017 BUG-57425 end 修改：先根据表单数据查询其他费用数据，如果查询不倒才使用初始化默认数据
		}

		// 遍历集合，并设置增值费用
		if (CollectionUtils.isNotEmpty(chDtlList)) {
			for (WaybillCHDtlPendingEntity entity : chDtlList) {
				String code = entity.getPricingEntryCode();
				// 代收货款费用
				if (PriceEntityConstants.PRICING_CODE_HK.equals(code)) {
					vo.setCodCode(code);
					vo.setCodFee(entity.getAmount());
					vo.setCodId(entity.getId());
				}

				// 送货费：它包括送货费+送货XXX费+超远派送费（公里数超过一定范围时才有超远派送费）
				CommonUtils.setDeliverCharge(vo, code, entity);

				// 保险费
				if (PriceEntityConstants.PRICING_CODE_BF.equals(code)) {
					vo.setInsuranceCode(code);
					vo.setInsuranceFee(CommonUtils.defaultIfNull(entity
							.getAmount()));
					vo.setInsuranceId(entity.getId());
				}

				// 包装费
				if (PriceEntityConstants.PRICING_CODE_BZ.equals(code)) {
					vo.setPackageFee(CommonUtils.defaultIfNull(entity
							.getAmount()));
				}

				// 接货费
				if (PriceEntityConstants.PRICING_CODE_JH.equals(code)) {
					vo.setPickupFee(CommonUtils.defaultIfNull(entity
							.getAmount()));
					vo.setPickUpFeeCanvas(String.valueOf(CommonUtils
							.defaultIfNull(entity.getAmount())));
				}

				// 公布价运费
//				if (PriceEntityConstants.PRICING_CODE_FRT.equals(code)) {
//					vo.setTransportFeeCode(code);
//					vo.setTransportFeeId(entity.getId());
//					vo.setTransportFee(CommonUtils.defaultIfNull(entity
//							.getAmount()));
//					vo.setTransportFeeCanvas(String.valueOf(CommonUtils
//							.defaultIfNull(entity.getAmount())));
//				}

				// 木架费
				if (DictionaryValueConstants.PACKAGE_TYPE__FRAME.equals(code)) {
					vo.setStandChargeCode(code);
					vo.setStandChargeId(entity.getId());
					vo.setStandCharge(CommonUtils.defaultIfNull(entity
							.getAmount()));
					if (woodenReqEntity != null) {
						vo.setStandGoodsNum(woodenReqEntity.getStandGoodsNum());
						vo.setStandGoodsVolume(woodenReqEntity
								.getStandGoodsVolume());
					} else if (woodenReqPendingEntity != null) {
						vo.setStandGoodsNum(woodenReqPendingEntity
								.getStandGoodsNum());
						vo.setStandGoodsVolume(woodenReqPendingEntity
								.getStandGoodsVolume());
					}
				}

				// 木箱费
				if (DictionaryValueConstants.PACKAGE_TYPE__BOX.equals(code)) {
					vo.setBoxChargeCode(code);
					vo.setBoxChargeId(entity.getId());
					vo.setBoxCharge(CommonUtils.defaultIfNull(entity
							.getAmount()));
					if (woodenReqEntity != null) {
						vo.setBoxGoodsNum(woodenReqEntity.getBoxGoodsNum());
						vo.setBoxGoodsVolume(woodenReqEntity
								.getBoxGoodsVolume());
					} else if (woodenReqPendingEntity != null) {
						vo.setBoxGoodsNum(woodenReqPendingEntity
								.getBoxGoodsNum());
						vo.setBoxGoodsVolume(woodenReqPendingEntity
								.getBoxGoodsVolume());
					}
				}
			}

			setWaybillDeliverDtl(vo.getDeliverList());
		}
		// 项目缺陷 	POP-546 foss开单后，提交木托、木架、木箱费用发生变化 
		if (woodenReqEntity != null && woodenReqEntity.getStandGoodsNum() != null && vo.getStandGoodsNum() == null) {
			vo.setStandGoodsNum(woodenReqEntity.getStandGoodsNum());
			vo.setStandGoodsVolume(woodenReqEntity
			.getStandGoodsVolume());
			}

			if (woodenReqEntity != null  && woodenReqEntity.getBoxGoodsNum() != null && vo.getBoxGoodsNum() == null) {
			vo.setBoxGoodsNum(woodenReqEntity.getBoxGoodsNum());
			vo.setBoxGoodsVolume(woodenReqEntity
			.getBoxGoodsVolume());
			}

		if (CollectionUtils.isNotEmpty(otherList)) {
			// 计算其它费用合计
			BigDecimal otherChargeSum = BigDecimal.ZERO;
			for (OtherChargeVo other : otherList) {
				BigDecimal money = new BigDecimal(other.getMoney());

				if (PriceEntityConstants.PRICING_CODE_CCDDJS.equals(other
						.getCode())) {
					continue;
				}
				if (PriceEntityConstants.PRICING_CODE_DWTBF.equals(other
						.getCode())) {
					continue;
				}
				if (PriceEntityConstants.PRICING_CODE_KYYFCJ.equals(other
						.getCode())) {
					continue;
				}

				otherChargeSum = otherChargeSum.add(money);

			}
			vo.setOtherFee(CommonUtils.defaultIfNull(otherChargeSum));
			// 画布其他费用表格
			ui.incrementPanel.setChangeDetail(otherList);
		} else {
			// 画布其他费用表格:删除其它费用栏中数据
			ui.incrementPanel.setChangeDetail(null);
		}

		// 设置“详细计价信息”画板中送货费表格的值
		if (CollectionUtils.isNotEmpty(vo.getDeliverList())) {
			ui.getCanvasContentPanel().getOtherCost()
					.setChangeDetail(vo.getDeliverList());
		}
	}

	//
	// /**
	// * 获得初始时的其它费用
	// * @author 026123-foss-lifengteng
	// * @date 2013-7-14 下午3:14:15
	// */
	// public List<OtherChargeVo> getInitOtherList(List<ValueAddDto> list){
	// if(CollectionUtils.isNotEmpty(list)){
	// List<OtherChargeVo> otherChargeList = new ArrayList<OtherChargeVo>();
	// for (ValueAddDto dto : list) {
	// if(PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntityCode())){
	// OtherChargeVo vo = new OtherChargeVo();
	// vo.setCode(dto.getSubType());// 费用编码
	// vo.setChargeName(dto.getSubTypeName());// 名称
	// vo.setType(dto.getBelongToPriceEntityCode());// 归集类别
	// vo.setMoney(String.valueOf(dto.getFee()));// 金额
	// vo.setUpperLimit(dto.getMaxFee().toString());// 上限
	// vo.setLowerLimit(String.valueOf(dto.getMinFee()));// 下限
	// vo.setIsUpdate(BooleanUtils.toBoolean(dto.getCanmodify()));// 是否可修改
	// vo.setIsDelete(BooleanUtils.toBoolean(dto.getCandelete()));// 是否可删除
	// vo.setId(dto.getId());
	// otherChargeList.add(vo);
	// }
	// }
	// return otherChargeList;
	// }else{
	// return null;
	// }
	// }

	// /**
	// * 当导入PDA时，待补录表中查询出费用明细项为空，为防止清空初始值，需要重新查询
	// * @author 026123-foss-lifengteng
	// * @date 2013-7-14 下午2:19:14
	// */
	// public List<WaybillCHDtlPendingEntity>
	// getInitWaybillCHDtlPendingList(List<ValueAddDto> list,String waybillNo){
	// if(CollectionUtils.isNotEmpty(list)){
	// /**
	// * BUG-44572:PDA补录，燃油费用和信息费用没有
	// *
	// * 初始化时，应该将默认的增值服务费用加入到bean当中，这样计算总运费时就正常显示送货费了
	// */
	// // 送货费集合
	// List<WaybillCHDtlPendingEntity> entityList = new
	// ArrayList<WaybillCHDtlPendingEntity>();
	// for (ValueAddDto dto : list) {
	// String code = dto.getSubType();
	// WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
	// chargeEntity.setPricingEntryCode(code);
	// chargeEntity.setAmount(dto.getFee());
	// chargeEntity.setWaybillNo(StringUtil.defaultIfNull(waybillNo));
	// chargeEntity.setPricingCriDetailId(dto.getId());
	// chargeEntity.setActive(FossConstants.YES);
	// chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	// entityList.add(chargeEntity);
	// }
	//
	// return entityList;
	// }else{
	// return null;
	// }
	//
	// }

	/**
	 * 设置“详细计价信息”画板中送货费表格的值
	 * 
	 * 由于WaybillDisDtlPendingEntity表和WaybillDisDtlEntity表中没有费用对应的名称
	 * 所以还需要重新在数据库中再查询 一遍
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 下午4:50:20
	 */
	private void setWaybillDeliverDtl(List<DeliverChargeEntity> deliveryList) {
		// 判断集合非空
		if (CollectionUtils.isEmpty(deliveryList)) {
			return;
		}

		// 费用条目编码集合
		List<String> entryCodes = new ArrayList<String>();
		// 遍历集合
		for (DeliverChargeEntity charge : deliveryList) {
			entryCodes.add(StringUtil.defaultIfNull(charge.getCode()));
		}

		// 获得code对应的name
		Map<String, String> map = waybillService
				.queryPriceEntryNameByEntryCodes(entryCodes);

		// 遍历集合
		for (DeliverChargeEntity disEntity : deliveryList) {
			// 获得名称值
			String name = map.get(disEntity.getCode());
			// 若值不为空，则设置名称
			if (StringUtil.isNotEmpty(name)) {
				disEntity.setName(name);
			}
		}
	}

	/**
	 * 设置运单折扣明细 （主要是设置“详细计价信息”面板中表格的值，因为与折扣相关的值在WaybillPanelVo中已经有了）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-19 上午9:05:06
	 */
	private void setWaybillDisDtlPending(Object object, WaybillEditUI ui) {
		// 非空判断
		if (null == object) {
			return;
		}

		// 获得待处理折扣集合
		List<WaybillDisDtlPendingEntity> disDtlPendingList = null;

		// 类型转换
		if (object instanceof WaybillDto) {
			// 获得WaybillDto对象
			WaybillDto waybillDto = (WaybillDto) object;

			// 获得折扣集合
			List<WaybillDisDtlEntity> disList = waybillDto
					.getWaybillDisDtlEntity();
			// 获得待处理折扣集合
			List<WaybillDisDtlPendingEntity> disPendingList = new ArrayList<WaybillDisDtlPendingEntity>();
			// 遍历并转换
			for (WaybillDisDtlEntity disDtl : disList) {
				WaybillDisDtlPendingEntity disPending = copyToWaybillDisDtlPendingEntity(disDtl);
				disPendingList.add(disPending);
			}

			disDtlPendingList = disPendingList;
		} else if (object instanceof WaybillPendingDto) {
			WaybillPendingDto pendingDto = (WaybillPendingDto) object;
			disDtlPendingList = pendingDto.getWaybillDisDtlPending();
		} else {
			return;
		}

		// 判断集合是否为空
		if (CollectionUtils.isNotEmpty(disDtlPendingList)) {
			List<WaybillDiscountVo> disCountList = getWaybillDiscountVoList(disDtlPendingList);
			if (CollectionUtils.isNotEmpty(disCountList)) {
				ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(
						disCountList);
			}
		}
	}

	/**
	 * 设置运单付款明细
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-19 上午9:05:08
	 */
	private void setWaybillPaymentPending(WaybillPanelVo vo, Object object,
			WaybillEditUI ui) {
		// TODO
	}

	/**
	 * 将WaybillDisDtlPendingEntity对象转换为WaybillDiscountVo
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 上午11:51:48
	 */
	private List<WaybillDiscountVo> getWaybillDiscountVoList(
			List<WaybillDisDtlPendingEntity> disList) {
		// 集合非空判断
		if (CollectionUtils.isEmpty(disList)) {
			return null;
		}

		// 定义转换对象的集合
		List<WaybillDiscountVo> voList = new ArrayList<WaybillDiscountVo>();

		for (WaybillDisDtlPendingEntity disEntity : disList) {
			// 定义转换对象
			WaybillDiscountVo vo = new WaybillDiscountVo();
			// 优惠项目
			vo.setFavorableItemName(disEntity.getPricingEntryName());
			// 优惠项目
			vo.setFavorableItemCode(disEntity.getPricingEntryCode());
			// 优惠类别
			vo.setFavorableTypeName(disEntity.getTypeName());
			// 优惠类别
			vo.setFavorableTypeCode(disEntity.getType());
			// 优惠子类别
			vo.setFavorableSubTypeName(disEntity.getSubTypeName());
			// 优惠子类别
			vo.setFavorableSubTypeCode(disEntity.getSubType());
			// 优惠折扣率

			if (disEntity.getRate() != null) {
				// 优惠折扣率
				vo.setFavorableDiscount(String.valueOf(disEntity.getRate()));
			} else {

				if (StringUtils.isNotBlank(ui.getPictureWaybillType())
						&& WaybillConstants.WAYBILL_PICTURE.equals(ui
								.getPictureWaybillType())) {
					vo.setFavorableDiscount("");
				} else {
					// 优惠折扣率
					/**
					 * modify by 何海粟 2014-12-06 OCB-152
					 * FOSS-FOSS图片开单，包含市场推广活动的运单，在图片查询中点击修改报错
					 */
					vo.setFavorableDiscount("0");
					/**
					 * old-报数字格式化异常
					 */
					// vo.setFavorableDiscount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableDiscount"));

				}

			}
			// 优惠金额
			if (disEntity.getAmount() != null) {
				// 优惠金额
				vo.setFavorableAmount(String.valueOf(disEntity.getAmount()));
			} else {

				if (StringUtils.isNotBlank(ui.getPictureWaybillType())
						&& WaybillConstants.WAYBILL_PICTURE.equals(ui
								.getPictureWaybillType())) {
					vo.setFavorableDiscount("");
				} else {
					// 优惠折扣率
					/**
					 * modify by 何海粟 2014-12-06 OCB-152
					 * FOSS-FOSS图片开单，包含市场推广活动的运单，在图片查询中点击修改报错
					 */
					vo.setFavorableDiscount("0");
					/**
					 * old-报数字格式化异常
					 */
					// vo.setFavorableDiscount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableDiscount"));

				}
			}

			voList.add(vo);
		}

		return voList;
	}

	/**
	 * 将查询出的其它费用转换成VO
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午4:15:18
	 */
	private List<OtherChargeVo> getOtherCharge(String waybillNo, Object object,
			IWaybillService waybillService) {
		IWaybillService wService = waybillService;
		// 非空判断
		if (null == object) {
			return null;
		}

		// 定义其它费用对象集合
		List<WaybillOtherChargeDto> list = null;

		// 类型判断
		if (object instanceof WaybillDto) {
			// 已补录运单类型
			list = wService.queryWaybillOtherChargeByNo(waybillNo);
		} else if (object instanceof WaybillPendingDto) {
			// 待补录运单类型
			list = wService.queryOtherChargeByNo(waybillNo);
			// 手动添加的 超重货操作服务费需重现录入
			WaybillPendingDto dto = (WaybillPendingDto) object;
			List<WaybillCHDtlPendingEntity> charges = dto
					.getWaybillChargeDtlPending();
			if (CollectionUtils.isNotEmpty(charges)) {
				for (WaybillCHDtlPendingEntity charge : charges) {
					String chargecode = charge.getPricingEntryCode();
					if ("CZHCZFWF_SDTJ".equals(chargecode)) {
						WaybillOtherChargeDto chargeDto = new WaybillOtherChargeDto();
						chargeDto.setId(charge.getId());
						chargeDto.setCode(chargecode);
						chargeDto.setAmount(charge.getAmount().toString());
						chargeDto.setChargeName("超重货操作服务费");
						chargeDto.setType("QT");
						list.add(chargeDto);
					}

				}
			}
		} else {
			return null;
		}

		// 集合非空判断
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();
			if (list.size() > 0) {
				for (WaybillOtherChargeDto dto : list) {
					// zxy 20130907 修改 start
					// 开单的时候不能手动增加送货进仓费(过滤掉送货进仓费)
					if (PriceEntityConstants.PRICING_CODE_SHJC.equals(dto
							.getCode())) {
						continue;
					}
					if(StringUtil.isEmpty(dto.getAmount()) || "0".equals(dto.getAmount())){
						continue;
					}
					// zxy 20130907 修改 end
					OtherChargeVo vo = new OtherChargeVo();
					vo.setCode(dto.getCode());// 费用编码
					vo.setChargeName(dto.getChargeName());// 名称
					vo.setType(dto.getType());// 归集类别
					vo.setDescrition(dto.getDescrition());// 描述
					vo.setMoney(dto.getAmount());// 金额
					vo.setUpperLimit(dto.getUpperLimit());// 上限
					vo.setLowerLimit(dto.getLowerLimit());// 下限
					// vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getIsUpdate()));//
					// 是否可修改
					// vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getIsDelete()));//
					// 是否可删除
					// 导入的运单都不可编辑和删除(根据BUG-24709修改)
					vo.setIsUpdate(false);// 是否可修改
					vo.setIsDelete(false);// 是否可删除
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
			return voList;
		}
	}

	/**
	 * 给打木架信息赋值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-6 下午3:23:54
	 */
	private void setWoodenRequirePending(WaybillPanelVo vo, Object object,
			WaybillEditUI ui) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */

		// 获得代打木架信息对象
		WoodenRequirePendingEntity woodenEntity = copyToWoodenRequirementsEntity(object);
		// 对象非空判断
		if (null == woodenEntity) {
			return;
		}
		// 代打木架部门编码
		vo.setPackageOrgCode(woodenEntity.getPackageOrgCode());
		// 打木架货物件数
		vo.setStandGoodsNum(woodenEntity.getStandGoodsNum());
		// 代打木架要求
		vo.setStandRequirement(woodenEntity.getStandRequirement());
		// 打木架货物尺寸
		vo.setStandGoodsSize(woodenEntity.getStandGoodsSize());
		// 打木架货物体积
		vo.setStandGoodsVolume(woodenEntity.getStandGoodsVolume());
		// 打木箱货物件数
		vo.setBoxGoodsNum(woodenEntity.getBoxGoodsNum());
		// 代打木箱要求
		vo.setBoxRequirement(woodenEntity.getBoxRequirement());
		// 打木箱货物尺寸
		vo.setBoxGoodsSize(woodenEntity.getBoxGoodsSize());
		// 打木箱货物尺寸

		// zxy 20131118 ISSUE-4391 start 新增：设置木托属性
		vo.setBoxGoodsVolume(woodenEntity.getBoxGoodsVolume());
		// 打木托件数
		vo.setSalverGoodsNum(woodenEntity.getSalverGoodsNum());
		// 代打木托要求
		vo.setSalverRequirement(woodenEntity.getSalverRequirement());
		// 打木托费用
		vo.setSalverGoodsCharge(woodenEntity.getSalverGoodsAmount());
		// zxy 20131118 ISSUE-4391 end 新增：设置木托属性
	}

	/**
	 * 将WaybillDto内的值拷贝到WaybillPendingDto中
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午6:01:20
	 */
	private WaybillPendingEntity copyToWaybillPendingEntity(Object object) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		// 定义待处理运单实体
		WaybillPendingEntity waybillPending = null;
		// 对象类型判断
		if (object instanceof WaybillDto) {
			WaybillDto waybillDto = (WaybillDto) object;
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
			waybillPending = new WaybillPendingEntity();
			try {
				// 拷贝属性值
				PropertyUtils.copyProperties(waybillPending, waybillEntity);
			} catch (Exception e) {
				// 添加异常日志
				LOGGER.error("对象拷贝失败！\n原因：" + e.getMessage());
				// 抛出异常信息
				throw new WaybillImportException(
						i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")
								+ e.getMessage(), e.getMessage());
			}
		} else if (object instanceof WaybillPendingDto) {
			return ((WaybillPendingDto) object).getWaybillPending();
		} else {
			// 若非WaybillPendingEntity和WaybillEntity类型的对象，则抛出错误信息
			throw new WaybillImportException(
					i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.paramErorr"));
		}

		return waybillPending;
	}

	/**
	 * 将WaybillDto内的值拷贝到WaybillPendingDto中
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 上午11:46:01
	 */
	private WaybillDisDtlPendingEntity copyToWaybillDisDtlPendingEntity(
			WaybillDisDtlEntity disDtl) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		// 非空判断
		if (null == disDtl) {
			return null;
		}

		// 定义对象用例拷贝
		WaybillDisDtlPendingEntity disPending = new WaybillDisDtlPendingEntity();

		// 对象类型判断
		try {
			// 拷贝属性值
			PropertyUtils.copyProperties(disPending, disDtl);
		} catch (Exception e) {
			// 添加异常日志
			LOGGER.error("对象拷贝失败！\n原因：" + e.getMessage());
			// 抛出异常信息
			throw new WaybillImportException(
					i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")
							+ e.getMessage(), e.getMessage());
		}

		return disPending;
	}

	/**
	 * 将WaybillDto内的值拷贝到WaybillPendingDto中
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午6:01:20
	 */
	private WaybillCHDtlPendingEntity copyToWaybillCHDtlPendingEntity(
			WaybillChargeDtlEntity chargeDtl) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		// 非空判断
		if (null == chargeDtl) {
			return null;
		}

		// 定义对象用例拷贝
		WaybillCHDtlPendingEntity chargePending = new WaybillCHDtlPendingEntity();

		// 对象类型判断
		try {
			// 拷贝属性值
			PropertyUtils.copyProperties(chargePending, chargeDtl);
		} catch (Exception e) {
			// 添加异常日志
			LOGGER.error("对象拷贝失败！\n原因：" + e.getMessage());
			// 抛出异常信息
			throw new WaybillImportException(
					i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")
							+ e.getMessage(), e.getMessage());
		}

		return chargePending;
	}

	/**
	 * 将WaybillDto内的值拷贝到WaybillPendingDto中
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午6:01:20
	 */
	private WoodenRequirePendingEntity copyToWoodenRequirementsEntity(
			Object object) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		// 非空判断
		if (null == object) {
			return null;
		}
		// 定义待处理运单实体
		WoodenRequirePendingEntity wooden = null;
		// 对象类型判断
		if (object instanceof WaybillDto) {
			WaybillDto waybillDto = (WaybillDto) object;
			WoodenRequirementsEntity entity = waybillDto
					.getWoodenRequirementsEntity();
			if (null != entity) {
				wooden = new WoodenRequirePendingEntity();
				try {
					// 拷贝属性值
					PropertyUtils.copyProperties(wooden, entity);
				} catch (Exception e) {
					// 添加异常日志
					LOGGER.error("对象拷贝失败！\n原因：" + e.getMessage());
					// 抛出异常信息
					throw new WaybillImportException(
							i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")
									+ e.getMessage(), e.getMessage());
				}
			} else {
				return null;
			}
		} else if (object instanceof WaybillPendingDto) {
			WaybillPendingDto pendingDto = (WaybillPendingDto) object;
			return pendingDto.getWoodenRequirePending();
		} else {
			// 若非WaybillPendingEntity和WaybillEntity类型的对象，则抛出错误信息
			throw new WaybillImportException(
					i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.paramErorr"));
		}

		return wooden;
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}

	public static void main(String[] args) {
		BigDecimal a = BigDecimal.ZERO;
		if (a.compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0
				|| a.compareTo(new BigDecimal(TWOPOINTFIVE)) > 0) {
			System.out.println(1);
		} else {
			System.out.println(2);
		}
	}

}