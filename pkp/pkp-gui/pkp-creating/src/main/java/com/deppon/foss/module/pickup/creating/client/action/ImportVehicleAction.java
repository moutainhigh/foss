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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/ImportVehicleAction.java
 * 
 * FILE NAME        	: ImportVehicleAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.action
 * FILE    NAME: WaybillSaveAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.util.define.FossConstants;

   
/**
 * 
 * 导入整车开单
 * @author 025000-FOSS-helong
 * @date 2012-12-4 上午10:09:48
 */
public class ImportVehicleAction implements IButtonActionListener<WaybillEditUI> {

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	IBaseDataService baseDataService=GuiceContextFactroy.getInjector()
			.getInstance(BaseDataService.class);
	// 主界面
	WaybillEditUI ui;
    
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ImportVehicleAction.class);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		try {
			//获取整车编号时用控件的文本获取，这样能获取到最新的，不然Enter键调用此方法时不能获取准确的整车编号
			bean.setVehicleNumber(ui.getBasicPanel().getTxtVehicleNumber().getText());
			validate(bean);
			//查询整车价格
			queryVehicleCharge(bean);
			Common.setWholeVehicleEdit(ui, bean);
			ui.basicPanel.getChbBigGoods().setEnabled(false);
			ui.transferInfoPanel.getTxtDestination().setEnabled(false);
			ui.transferInfoPanel.getBtnQueryBranch().setEnabled(false);
			//获取整车费率范围
			GuiResultBillCalculateDto gDto=Common.getInsuranceRate(bean);
			setRate(gDto,bean);
			// 清空其他费用列表
			Common.cleanOtherCharge(bean, ui);
			// 查询其他费用
			Common.queryOtherChargeData(ui, bean);
			// 计算其他费用合计
			Common.calculateOtherCharge(ui, bean);
		} catch (BusinessException w) {
			if(i18n.get("foss.gui.creating.importVehicleAction.exception.nullOutfieldEntity").equals(w.getMessage())){
				MsgBox.showITServiceInfo(w.getMessage());
			}else{
				MsgBox.showInfo(w.getMessage());
			}			
		}
	}
	
	private void setRate(GuiResultBillCalculateDto gDto,WaybillPanelVo bean){
		ui.incrementPanel.getTxtInsuranceRate().setEnabled(false);
		if(gDto !=null){
			//保价费率是否可修改
			bean.setCanmodify(gDto.getCanmodify());
			//最低保价费率
			bean.setMinFeeRate(gDto.getMinFeeRate());
			//最高保价费率
			bean.setMaxFeeRate(gDto.getMaxFeeRate());
			//默认保价费率
			if(gDto.getFeeRate()!=null){
				bean.setInsuranceRate(gDto.getFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_1000)));
			}
			if(FossConstants.YES.equals(gDto.getCanmodify())){
				ui.incrementPanel.getTxtInsuranceRate().setEnabled(true);
			}
		}
	}
	/**
	 * 
	 * 规则校验
	 * @author 025000-FOSS-helong
	 * @date 2013-1-21 上午09:20:30
	 */
	private void validate(WaybillPanelVo bean)
	{
		if(StringUtils.isEmpty(bean.getVehicleNumber()))
		{
			throw new WaybillValidateException(i18n.get("foss.gui.creating.importVehicleAction.exception.nullVehicleNumber"));
		}
	}


	/**
	 * 
	 * 查询整车费用
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 下午06:46:48
	 * @param bean
	 */
	private void queryVehicleCharge(WaybillPanelVo bean)
	{
		//状态暂时用不上，在此处没有实际作用
		String state = checkInviteNoIsExists(bean);
		if(state == null || "".equals(state))
		{
			throw new WaybillValidateException(i18n.get("foss.gui.creating.importVehicleAction.exception.nullInviteNumber"));
		}else if(bean.getProductCode()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.importVehicleAction.exception.nullProductCode"));
		}else
		{
			InviteVehicleDto dto = waybillService.queryInviteCostByInviteNo(bean.getVehicleNumber());
			if(dto == null)
			{
				throw new WaybillValidateException(i18n.get("foss.gui.creating.importVehicleAction.exception.nullInviteNumber"));
			}else
			{
				//zxy 20140512 DMANA-433 start 新增:判断约车编号是否已达到，未到达的不能进行开单
				if(!InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE.equals(dto.getStatus())){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.importVehicleAction.exception.notRegisteVehicle"));
				}
				//zxy 20140512 DMANA-433 end 新增:判断约车编号是否已达到，未到达的不能进行开单
				//发票标记
				DataDictionaryValueVo mvo =bean.getInvoiceMode();
				if(null!=mvo){
					String invoceType =mvo.getValueCode();
					UserEntity user = (UserEntity) SessionContext.getCurrentUser();
					//调用接口查询整车价格参数波动方案
					CarloadPricePlanDto carloadDto = baseDataService.queryByConfCode(new Date(),invoceType,user.getEmployee().getDepartment().getCode());
					if(null!=carloadDto){
						//整车约车报价波动范围上限
						bean.setWholeVehicleActualfeeFlowRangeUp(carloadDto.getFloatUp());
						//整车约车报价波动范围下限
						bean.setWholeVehicleActualfeeFlowRangeLow(carloadDto.getFloatDown());
						/**
						 *DMANA-3563 整车开单判断是否盈利  新增参数
						 */
						//重量参数
						bean.setWholeVehicleWeightParameter(carloadDto.getWeightParameter());
						//包装费参数
						bean.setWholeVehiclePackageFeeParameter(carloadDto.getPackageFeeParameter());
						//其他成本参数
						bean.setWholeVehicleOtherCostParameter(carloadDto.getOtherCostParameter());
						//人力成本参数
						bean.setWholeVehicleHumanCostParameter(carloadDto.getHumanCostParameter());
						//车价参数
						bean.setWholeVehicleCarCostParameter(carloadDto.getCarCostParameter());
						
					}else{
						throw new WaybillValidateException("没有查询到相应的整车价格参数波动方案");
					}
				}
				// 整车约车报价
				bean.setWholeVehicleAppfee(dto.getInviteCost());
				// 整车开单报价
				bean.setWholeVehicleActualfee(dto.getInviteCost());
				// 开单报价
				bean.setTransportFee(dto.getInviteCost());
				if(dto.getInviteCost()!=null){
					// 画布公布价运费
					bean.setTransportFeeCanvas(dto.getInviteCost().toString());
				}		
				//货物名称
				bean.setGoodsName(dto.getGoodsName());
				//件数
				bean.setGoodsQtyTotal(dto.getGoodsQty());
				//体积
				bean.setGoodsVolumeTotal(dto.getVolume());
				//重量
				BigDecimal weight = dto.getWeight();
				//将吨换算成公斤
				weight = weight.multiply(new BigDecimal(WaybillConstants.TON_KILO));
				bean.setGoodsWeightTotal(weight);
				//客户名称
				bean.setDeliveryCustomerName(dto.getClientName());
				//联系人
				bean.setDeliveryCustomerContact(dto.getClientName());
				//手机号码
				bean.setDeliveryCustomerMobilephone(dto.getClientContactPhone());
				//固定电话
				bean.setDeliveryCustomerPhone(dto.getClientContactPhone());
				//查询提货网点信息
				BranchVo vo = getPickupOrg(dto.getArrivedDeptCode());
				//设置提货网点信息
				bean.setCustomerPickupOrgCode(vo);
				//设置提货网点名称
				bean.setCustomerPickupOrgName(vo.getName());
				//设置最终配载部门编号
				bean.setLastLoadOrgCode(vo.getCode());
				//设置最终配载部门名称
				bean.setLastLoadOrgName(vo.getName());
				
				
				OutfieldEntity outfieldEntity = waybillService.queryDefaultTransCodeDept(bean.getReceiveOrgCode(),bean.getProductCode().getCode());
				if(outfieldEntity == null)
				{
					throw new WaybillValidateException(i18n.get("foss.gui.creating.importVehicleAction.exception.nullOutfieldEntity"));
				}else
				{
					//配载部门编码
					bean.setLoadOrgCode(outfieldEntity.getOrgCode());
					//设置配载部门名称
					bean.setLoadOrgName(outfieldEntity.getName());
				}

				//设置目的站
				bean.setTargetOrgCode(gainCityNameByCode(vo.getCode()));
				//是否经过营业部
				if(FossConstants.YES.equals(dto.getIsPassByArrivedDept()))
				{
					bean.setIsPassDept(true);
				}else{
					bean.setIsPassDept(false);
				}
				
				//LiDing 273279 2015/08/13 add start-->
				//到达地址/收货地址
				bean.setReceiveCustomerAddress(dto.getArrivedAddress());
				AddressFieldDto addrDto = new AddressFieldDto();
				addrDto.setCityId(dto.getCityCode());
				addrDto.setCountyId(dto.getAreaCode());
				addrDto.setProvinceId(dto.getProvinceCode());
				bean.setReceiveCustomerAreaDto(addrDto);
				//LiDing 273279 2015/08/13 add end-->
				
				// 计算其他费用合计
				Common.calculateOtherCharge(ui, bean);
			}
		}
	}
	
	/**
	 * 根据部门编码查询所属城市名称
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 下午4:54:33
	 */
	private String gainCityNameByCode(String code){
		OrgAdministrativeInfoEntity orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(code));
		if(orgInfo!=null){
			if(StringUtil.isEmpty(orgInfo.getOrgSimpleName())){
	    		AdministrativeRegionsEntity regions = waybillService.queryAdministrativeRegionsByCode(StringUtil.defaultIfNull(orgInfo.getCityCode()));
	    		if(regions != null){
	    			return regions.getName();
	    		}else{
	    			return null;
	    		}
			}else{
				return orgInfo.getOrgSimpleName();
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 获得提货网点信息
	 * @author 025000-FOSS-helong
	 * @date 2013-1-5 下午08:09:38
	 * @param code
	 * @return
	 */
	public BranchVo getPickupOrg(String code){
		SaleDepartmentEntity saleDepartment = waybillService.querySaleDeptByCode(code);
		BranchVo btanchVo = new BranchVo();
		if(saleDepartment != null)
		{
			btanchVo.setCode(saleDepartment.getCode());
			btanchVo.setName(saleDepartment.getName());
		}else
		{
			//导入整车时，如果到达部门为空，则需要选择，根据MANA-389修改
//			throw new WaybillValidateException(i18n.get("foss.gui.creating.importVehicleAction.exception.nullSaleDepartment"));
		}

		return btanchVo;
	}
	
	/**
	 * 
	 * 校验约车编号
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 上午10:44:10
	 */
	private  String checkInviteNoIsExists(WaybillPanelVo bean)
	{
		//当前用户信息
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		return waybillService.checkInviteNoIsExists(bean.getVehicleNumber(), dept.getCode());
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}

}