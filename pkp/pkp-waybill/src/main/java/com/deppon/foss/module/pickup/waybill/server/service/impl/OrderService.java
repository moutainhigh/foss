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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/OrderService.java
 * 
 * FILE NAME        	: OrderService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.service.impl
 * FILE    NAME: OrderService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOrderService;
import com.deppon.foss.module.pickup.waybill.shared.define.FossUserContextHelper;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillOrderHandleException;

/**
 * 订单处理相关的服务接口实现类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-12-27 下午8:59:22
 */
public class OrderService implements IOrderService {

	// 日志信息
	public static final Logger LOGGER = LoggerFactory
			.getLogger(OrderService.class);

	/**
	 * CRM订单数据JMS服务接口
	 */
	private ICrmOrderJMSService crmOrderJMSService;

	/**
	 * 人员服务接口
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 组织服务接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 国际化信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	private IMessageBundle messageBundle; 
	
	/**
	 * @param messageBundle the
	 *            messageBundle to set
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}
	/**
	 * 设置 cRM订单数据JMS服务接口.
	 * 
	 * @param crmOrderJMSService
	 *            the new cRM订单数据JMS服务接口
	 */
	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	/**
	 * 设置 人员服务接口.
	 * 
	 * @param employeeService
	 *            the new 人员服务接口
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 根据运单基本信息更订单状态
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午10:02:53
	 */
	@Override
	public void updateOrderInfo(WaybillEntity waybill) {
		try {
			ResultDto res = crmOrderJMSService.sendModifyOrder(gainCrmModifyInfoDto(waybill));
			if("0".equals(res.getCode())){
				LOGGER.error("更新订单状态失败：" + res.getMsg());
				throw new WaybillOrderHandleException(res.getMsg());
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			String msg = e.getMessage();
			if(StringUtils.isEmpty(msg)){
				msg = e.getErrorCode();
			}
			throw new WaybillOrderHandleException(WaybillOrderHandleException.UPDATESTATUS_FAILREASON,new Object[]{messageBundle.getMessage(msg)} );
		}
	}

	/**
	 * 根据运单基本信息封装需要更新的对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午10:02:13
	 */
	private CrmModifyInfoDto gainCrmModifyInfoDto(WaybillEntity waybill) {
		CrmModifyInfoDto dto = new CrmModifyInfoDto();
		// 订单编号
		dto.setOrderNumber(waybill.getOrderNo());
		// 运单号码
		dto.setWaybillNumber(waybill.getWaybillNo());
		// 操作人员
		dto.setOprateUserNum(FossUserContextHelper.getUserCode());
		if(FossUserContext.getCurrentDept()!=null){
			// 操作人员所在部门
			dto.setOprateDeptCode(FossUserContext.getCurrentDept().getUnifiedCode());
		}		
		dto.setWeight(waybill.getGoodsWeightTotal()==null ? 0 : waybill.getGoodsWeightTotal().doubleValue());
		dto.setVolume(waybill.getGoodsVolumeTotal()==null ? 0 : waybill.getGoodsVolumeTotal().doubleValue());
		// 货物状态
		dto.setGoodsStatus(WaybillConstants.CRM_ORDER_WAYBILL);
		// 收入部门标杆编码
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybill.getReceiveOrgCode());
		if (null != org) {
			String unifedCode = StringUtil.defaultIfNull(org.getUnifiedCode());
			dto.setEarningDeptStandardCode(unifedCode);
		}
		// 收入部门名称
		dto.setEarningDeptStandardName(StringUtil.defaultIfNull(waybill.getReceiveOrgName()));
		//判断司机工号是否为空，根据DEFECT-3385进行修改		
		if(StringUtil.isNotEmpty(waybill.getDriverCode())){
			// 员工信息
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(waybill.getDriverCode());
			if (null != emp) {
				// 司机姓名
				dto.setDriverName(emp.getEmpName());
				// 司机手机
				dto.setDriverMobile(emp.getMobilePhone());	
			}
		}	

		return dto;
	}
}