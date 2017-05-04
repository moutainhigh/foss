/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/complex/VehicleService.java
 * 
 * FILE NAME        	: VehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.server.service.impl.complex;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
/**
 * 用来提供交互所有关于“车辆（公司、外请）”的数据库对应数据访问复杂的Service接口实现类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-30 下午4:54:45</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-30 下午4:54:45
 * @since
 * @version
 */
public class VehicleService implements IVehicleService {
	
	//“公司车辆（厢式车、挂车、拖头）”Service接口
	private IOwnVehicleService ownVehicleService;
	
	//“外请车辆（厢式车、挂车、拖头）”Service接口
	private ILeasedVehicleService leasedVehicleService;
	
	
	/**
	 * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO</p>
	 * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午4:31:23
	 * @param vehicleNo 车牌号
	 * @return VehicleAssociationDto封装了传送对象
	 * @throws BusinessException
	 * @throws BusinessException 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService#queryVehicleAssociationDtoByVehicleNo(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public VehicleAssociationDto queryVehicleAssociationDtoByVehicleNo(
	        String vehicleNo) throws BusinessException {
	List<VehicleAssociationDto> vehicleAssociationDtos = queryVehicleAssociationDtosByVehicleNos(new String[]{vehicleNo});
	if (CollectionUtils.isEmpty(vehicleAssociationDtos)) {
	    return null;
	}
	return vehicleAssociationDtos.get(0);
	}
	
	/**
	 * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
	 * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-2 上午10:50:33
	 * @param vehicleNos 车牌号集合
	 * @return VehicleAssociationDto封装了传送对象集合
	 * @throws BusinessException
	 * @throws BusinessException 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService#queryVehicleAssociationDtosByVehicleNos(java.lang.String[])
	 */
	@Override
	@Transactional(readOnly = true)
	public List<VehicleAssociationDto> queryVehicleAssociationDtosByVehicleNos(
	        String[] vehicleNos) throws BusinessException {
	List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	List<VehicleAssociationDto> ownVehicleAssociationDtos, leasedVehicleAssociationDtos;
	
	ownVehicleAssociationDtos = ownVehicleService.queryOwnVehicleAssociationDtosByVehicleNos(vehicleNos);
	if (CollectionUtils.isNotEmpty(ownVehicleAssociationDtos)) {
	    vehicleAssociationDtos.addAll(ownVehicleAssociationDtos);
	}
	leasedVehicleAssociationDtos = leasedVehicleService.queryLeasedVehicleAssociationDtosByVehicleNos(vehicleNos);
	if (CollectionUtils.isNotEmpty(leasedVehicleAssociationDtos)) {
		//外请车无对应部门，因打印模版中判断部门为空不能打印，故设置部门名称统一为“外请车”
				for(VehicleAssociationDto dto:leasedVehicleAssociationDtos) {
					dto.setVehicleMotorcadeName("外请车");
					dto.setVehicleOrganizationName("外请车");
				}
	    vehicleAssociationDtos.addAll(leasedVehicleAssociationDtos);
	}
	    return vehicleAssociationDtos;
	}
	
	/**
	 * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO(包括拖头)</p>
	 * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
	 * @author 313353-foss-qiupeng
	 * @date 2016-07-06 上午09:31:23
	 * @param vehicleNo 车牌号
	 * @return VehicleAssociationDto封装了传送对象
	 * @throws BusinessException
	 * @see
	 */
	@Override
	@Transactional(readOnly = true)
	public VehicleAssociationDto queryVehicleByVehicleNoIncludeTractors(
	        String vehicleNo) throws BusinessException {
	List<VehicleAssociationDto> vehicleAssociationDtos = queryVehicleByVehicleNosIncludeTractors(new String[]{vehicleNo});
	if (CollectionUtils.isEmpty(vehicleAssociationDtos)) {
	    return null;
	}
	return vehicleAssociationDtos.get(0);
	}
	
	/**
	 * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合(包括拖头)</p>
	 * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
	 * @author 313353-foss-qiupeng
	 * @date 2016-07-06 上午09:31:23
	 * @param vehicleNos 车牌号集合
	 * @return VehicleAssociationDto封装了传送对象集合
	 * @throws BusinessException
	 * @see
	 */
	@Override
	@Transactional(readOnly = true)
	public List<VehicleAssociationDto> queryVehicleByVehicleNosIncludeTractors(
	        String[] vehicleNos) throws BusinessException {
	List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	List<VehicleAssociationDto> ownVehicleAssociationDtos, leasedVehicleAssociationDtos;
	
	ownVehicleAssociationDtos = ownVehicleService.queryOwnVehicleByVehicleNosIncludeTractors(vehicleNos);
	if (CollectionUtils.isNotEmpty(ownVehicleAssociationDtos)) {
	    vehicleAssociationDtos.addAll(ownVehicleAssociationDtos);
	}
	leasedVehicleAssociationDtos = leasedVehicleService.queryLeasedVehicleByVehicleNosIncludeTractors(vehicleNos);
	if (CollectionUtils.isNotEmpty(leasedVehicleAssociationDtos)) {
		//外请车无对应部门，因打印模版中判断部门为空不能打印，故设置部门名称统一为“外请车”
				for(VehicleAssociationDto dto:leasedVehicleAssociationDtos) {
					dto.setVehicleMotorcadeName("外请车");
					dto.setVehicleOrganizationName("外请车");
				}
	    vehicleAssociationDtos.addAll(leasedVehicleAssociationDtos);
	}
	    return vehicleAssociationDtos;
	}
	
	/**
	 * @param ownVehicleService the ownVehicleService to set
	 */
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
	    this.ownVehicleService = ownVehicleService;
	}
	
	/**
	 * @param leasedVehicleService the leasedVehicleService to set
	 */
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
	    this.leasedVehicleService = leasedVehicleService;
	}
	}
