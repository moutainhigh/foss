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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/action/DispatchOrderConditionAction.java
 * 
 * FILE NAME        	: DispatchOrderConditionAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ServiceFleetDto;
import com.deppon.foss.module.pickup.order.api.shared.vo.DispatchOrderConditionVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;

/**
 * 调度订单查询条件Action类
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-23 下午2:33:28
 */
public class DispatchOrderConditionAction extends AbstractAction {
	// 序列id
	private static final long serialVersionUID = -2803249319036937847L;
	// 调度订单处理查询条件VO
	private DispatchOrderConditionVo dispatchOrderConditionVo = new DispatchOrderConditionVo();
	// 车队编码
	private String fleetCode;
	// 综合
	// 组织service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	// 组织服务
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	// 综合
	// 外请车型服务
	private ILeasedVehicleTypeService leasedVehicleTypeService;

	/**
	 * 获取车队下组织.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 下午5:43:08
	 */
	@JSON
	public String querySubOrg() {
		if(!StringUtils.isNotEmpty(fleetCode)){
			return returnSuccess();
		}
		// 调用综合组服务获取车队下的组织
		List<OrgAdministrativeInfoEntity> serviceFleets = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByBizType(fleetCode,BizTypeConstants.ORG_TRANS_TEAM);
		List<ServiceFleetDto> serviceFleetDtos = new ArrayList<ServiceFleetDto>();
		if (CollectionUtils.isNotEmpty(serviceFleets)) {
			// 封装DTO
			for (OrgAdministrativeInfoEntity serviceFleet : serviceFleets) {
				ServiceFleetDto serviceFleetDto = new ServiceFleetDto();
				// 名称
				serviceFleetDto.setName(serviceFleet.getName());
				// 编码
				serviceFleetDto.setCode(serviceFleet.getCode());
				serviceFleetDtos.add(serviceFleetDto);
			}
			ServiceFleetDto topFleet = new ServiceFleetDto();
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(fleetCode);
			topFleet.setName(org.getName());
			topFleet.setCode(fleetCode);
			serviceFleetDtos.add(topFleet);
		}
		dispatchOrderConditionVo.getDispatchOrderConditionDto().setServiceFleetList(serviceFleetDtos);
		return returnSuccess();
	}

	/**
	 * 获取组织所在车队.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-12-10 上午11:49:40
	 */
	@JSON
	public String querySuperOrg() {
		// 调用综合组的服务获取当前组织所在的车队
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.getTopFleetByCode(FossUserContextHelper.getOrgCode());
		if (orgAdministrativeInfoEntity != null) {
			// 设置车队的code
			fleetCode = orgAdministrativeInfoEntity.getCode();
		}
		return returnSuccess();
	}

	/**
	 * 查询综合的所有车型.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-12-27 上午11:33:49
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryVehcileType() {
		// 调用综合的接口获取所有的车型
		PaginationDto paginationDto = leasedVehicleTypeService.queryLeasedVehicleTypeListBySelectiveCondition(new VehicleTypeEntity(), 0, Integer.MAX_VALUE);
		List<VehicleTypeEntity> vehicleTypeList = null;
		if(paginationDto != null) {
			vehicleTypeList = paginationDto.getPaginationDtos();
		}
		// vo设置车型
		dispatchOrderConditionVo.setVehicleTypeList(vehicleTypeList);
		return returnSuccess();
	}

	/**
	 * Gets the dispatchOrderConditionVo.
	 * 
	 * @return the dispatchOrderConditionVo
	 */
	public DispatchOrderConditionVo getDispatchOrderConditionVo() {
		return dispatchOrderConditionVo;
	}

	/**
	 * Sets the dispatchOrderConditionVo.
	 * 
	 * @param dispatchOrderConditionVo the dispatchOrderConditionVo to see
	 */
	public void setDispatchOrderConditionVo(DispatchOrderConditionVo dispatchOrderConditionVo) {
		this.dispatchOrderConditionVo = dispatchOrderConditionVo;
	}

	/**
	 * Sets the orgAdministrativeInfoComplexService.
	 * 
	 * @param orgAdministrativeInfoComplexService the
	 *            orgAdministrativeInfoComplexService to see
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * Gets the fleetCode.
	 * 
	 * @return the fleetCode
	 */
	public String getFleetCode() {
		return fleetCode;
	}
	
	/**
	 * Sets the fleetCode.
	 * 
	 * @param fleetCode the fleetCode to see
	 */
	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	/**
	 * Sets the leasedVehicleTypeService.
	 * 
	 * @param leasedVehicleTypeService the leasedVehicleTypeService to set
	 */
	public void setLeasedVehicleTypeService(ILeasedVehicleTypeService leasedVehicleTypeService) {
		this.leasedVehicleTypeService = leasedVehicleTypeService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

}