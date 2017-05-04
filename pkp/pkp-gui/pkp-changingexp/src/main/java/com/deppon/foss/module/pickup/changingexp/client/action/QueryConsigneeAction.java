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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/QueryConsigneeAction.java
 * 
 * FILE NAME        	: QueryConsigneeAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-changingexp
 * PACKAGE NAME: com.deppon.foss.module.pickup.changingexp.client.action
 * FILE    NAME: ads.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.ui.customer.QueryMemberDialog;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询会员
 * @author 026123-foss-lifengteng
 * @date 2013-1-18 上午10:29:18
 */
public class QueryConsigneeAction extends AbstractButtonActionListener<WaybillRFCUI> {

	/**
	 * UI OBJECT
	 */
	WaybillRFCUI ui;

	// 定义业务工具类
	BusinessUtils bu = new BusinessUtils();

	/**
	 * 
	 * <p>
	 * 查询收货客户
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// 将获得的值传到界面上
		WaybillInfoVo bean = ui.getBinderWaybill();
		QueryMemberDialog panel = new QueryMemberDialog(bean);
		// 居中显示弹出窗口
		WindowUtil.centerAndShow(panel);
		// 获得VO
		QueryMemberDialogVo memberBean = panel.getMemberVo();
		
		if (memberBean == null) {
			return;
		}
	
		
		bean.setReceiveCustomerMobilephone(memberBean.getMobilePhone());
		bean.setReceiveCustomerContact(memberBean.getLinkman());
		bean.setReceiveCustomerPhone(memberBean.getPhone());
		bean.setReceiveCustomerAddress(memberBean.getAddress());
		bean.setReceiveCustomerAddressNote(memberBean.getAddressRemark());
		bean.setReceiveCustomerName(memberBean.getCustomerName());
		
		AddressFieldDto address =  getAddressFieldInfoByCode(ui, memberBean.getProvinceCode(), memberBean.getCityCode(), memberBean.getCountyCode());
		
		// 收货省份
		bean.setReceiveCustomerProvCode(address.getProvinceId());
		// 收货市
		bean.setReceiveCustomerCityCode(address.getCityId());
		// 收货区
		bean.setReceiveCustomerDistCode(address.getCountyId());
		// 省市区县DTO
		bean.setReceiveCustomerAreaDto(address);
		// 省市区县文本
		bean.setReceiveCustomerArea(bu.getAddressAreaText(address));

    	// 将获得的值传到界面上
    	bean.setReceiveCustomerName(StringUtil.defaultIfNull(memberBean.getCustomerName()));// 收货客户名称
    	bean.setReceiveCustomerMobilephone(StringUtil.defaultIfNull(memberBean.getMobilePhone()));// 收货客户手机
    	bean.setReceiveCustomerContact(StringUtil.defaultIfNull(memberBean.getLinkman()));// 收货客户联系人
    	bean.setReceiveCustomerPhone(StringUtil.defaultIfNull(memberBean.getPhone()));// 收货客户电话
    	bean.setReceiveCustomerAddress(StringUtil.defaultIfNull(memberBean.getAddress()));
    
    	bean.setReceiveCustomerId(StringUtil.defaultIfNull(memberBean.getCustId()));// 收货客户ID
    	bean.setReceiveCustomerCode(StringUtil.defaultIfNull(memberBean.getCustomerCode()));// 收货客户编号
    	bean.setChargeMode(memberBean.getChargeMode());// 是否月结
    	bean.setPreferentialType(memberBean.getPreferentialType());//优惠类型
    	
    	//是否为大客户标识
		if(FossConstants.ACTIVE.equals(memberBean.getIsBigCustomer())){
			//设置大客户标记
			ui.consigneePanel.getLblReceiveCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			//取消大客户标记
			ui.consigneePanel.getLblReceiveCustomerCode().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		}
		//設置收貨客戶信息
		setExpDeliverySettleAndContactAndRemending(memberBean,bean);
	}
	//@author -wutao 
	//設置收貨客戶【是否統一結算】【合同部門】【催款部門】
	private static void setExpDeliverySettleAndContactAndRemending(QueryMemberDialogVo memberBean, WaybillPanelVo waybillBean){
		if (memberBean != null 
				&& "".equals(memberBean.getCentralizedSettlement())
				&& "".equals(memberBean.getContractOrgCode())
				&& "".equals(memberBean.getReminderOrgCode())) {
			waybillBean
					.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			waybillBean.setArriveContractOrgCode(null);
			waybillBean
					.setArriveContractOrgName(null);
			waybillBean.setArriveReminderOrgCode(null);
		} else {
			if (WaybillConstants.INVOICE_02.equals(memberBean.getInvoice()) 
					|| WaybillConstants.INVOICE_01.equals(memberBean.getInvoice())) {
				if (WaybillConstants.YES.equals(memberBean
						.getCentralizedSettlement())) {
					waybillBean
							.setArriveCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
					waybillBean.setArriveContractOrgCode(memberBean
							.getContractOrgCode());
					waybillBean.setArriveContractOrgName(memberBean
							.getContractOrgName());
					waybillBean.setArriveReminderOrgCode(memberBean
							.getReminderOrgCode());
				} else {
					waybillBean
							.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
					waybillBean.setArriveContractOrgCode(null);
					waybillBean
							.setArriveContractOrgName(null);
					waybillBean.setArriveReminderOrgCode(null);
				}
			} else {
				waybillBean
						.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				waybillBean.setArriveContractOrgCode(null);
				waybillBean
						.setArriveContractOrgName(null);
				waybillBean.setArriveReminderOrgCode(null);
			}
		}
		
		//wutao == end 
	}
	

	/**
	 * 获得省市区县地址对象:直接根据省市区县编码查询DTO
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午4:29:58
	 */
	public static AddressFieldDto getAddressFieldInfoByCode(WaybillRFCUI ui, String provCode, String cityCode, String distCode) {
		// 业务工具类
		BusinessUtils bu2 = new BusinessUtils();
		// 获得省市区县对象
		AddressFieldDto address = bu2.getProvCityCounty(provCode, cityCode, distCode);
		// 接收地址对象
		return getAddressFiledInfo( address,  ui);
	}
	
	
	/**
	 * 获得省市区县地址对象:若dto为空，则从界面中取对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午4:29:58
	 */
	public static AddressFieldDto getAddressFiledInfo(AddressFieldDto dto, WaybillRFCUI ui) {
		// 接收地址对象
		AddressFieldDto address = dto;
		// 判断地址对象是否为空
		if (null == address) {
			// 从文本框中获取对象
			address = ui.consigneePanel.getTxtConsigneeArea().getAddressFieldDto();
		}
		return address;
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
	public void setIInjectUI(WaybillRFCUI ui) {
		this.ui = ui;

	}

}