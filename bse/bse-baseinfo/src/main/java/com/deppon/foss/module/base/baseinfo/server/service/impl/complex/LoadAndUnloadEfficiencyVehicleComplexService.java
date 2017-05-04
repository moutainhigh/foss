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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/complex/LoadAndUnloadEfficiencyVehicleComplexService.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyVehicleComplexService.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.ILoadAndUnloadEfficiencyVehicleComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.complex.LoadAndUnloadEfficiencyVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyVehicleException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车标准-吨-人天 复杂Service实现
 *  
 * @author 087584-foss-lijun
 * @date 2012-11-2 上午13:49:37
 */
public class LoadAndUnloadEfficiencyVehicleComplexService implements
	ILoadAndUnloadEfficiencyVehicleComplexService {

    /**
     * 下面是service声明及get,set方法
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

    private ILeasedVehicleService leasedVehicleService;

    private ILoadAndUnloadEfficiencyVehicleService loadAndUnloadEfficiencyVehicleService;

    private IOwnVehicleService ownVehicleService;

    /**
     * 
     * @date Mar 12, 2013 2:14:37 PM
     * @param orgAdministrativeInfoComplexService
     * @see
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    /**
     * 
     * @date Mar 12, 2013 2:14:42 PM
     * @param leasedVehicleService
     * @see
     */
    public void setLeasedVehicleService(
	    ILeasedVehicleService leasedVehicleService) {
	this.leasedVehicleService = leasedVehicleService;
    }

    /**
     * 
     * @date Mar 12, 2013 2:14:48 PM
     * @param loadAndUnloadEfficiencyVehicleService
     * @see
     */
    public void setLoadAndUnloadEfficiencyVehicleService(
	    ILoadAndUnloadEfficiencyVehicleService loadAndUnloadEfficiencyVehicleService) {
	this.loadAndUnloadEfficiencyVehicleService = loadAndUnloadEfficiencyVehicleService;
    }

    /**
     * 
     * @date Mar 12, 2013 2:14:54 PM
     * @param ownVehicleService
     * @see
     */
    public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
	this.ownVehicleService = ownVehicleService;
    }

    
    /**
     * 根据车辆的车牌号，部门编码，查询装卸车标准（卸一车需要多长时间）
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     */
    @Override
    public LoadAndUnloadEfficiencyVehicleDto gainLoadAndUnloadEfficiencyVehicle(
	    String plateNumber, String orgCode)
	    throws LoadAndUnloadEfficiencyVehicleException {
	// 检查参数的合法性
	//313353 sonar
    this.sonarSplitOne(plateNumber, orgCode);

	// 是否"非敞篷车"
	String openVehicle = null;
	// 是否"带高栏"
	String ailVehicle = null;
	// 车长编码
	String vehicleLengthCode = null;
	boolean isLeasedTruck = true;

	// 先查是否是外请车
	LeasedTruckEntity leasedTruckEntity = leasedVehicleService
		.queryLeasedVehicleByVehicleNo(plateNumber);

	// 如果车辆信息为空，或者车长编码为空，则返回空
	if (leasedTruckEntity != null
		&& StringUtils
			.isBlank(leasedTruckEntity.getVehicleLengthCode())) {
	    return null;
	}

	/**
	 * 获得车长类型编码
	 */
	if (leasedTruckEntity != null) {
	    vehicleLengthCode = leasedTruckEntity.getVehicleLengthCode();
	    isLeasedTruck = true;
	} else {
	    // 获得公司车的车长编码
	    OwnTruckEntity ownTruckCondition = new OwnTruckEntity();
	    ownTruckCondition.setVehicleNo(plateNumber);
	    ownTruckCondition = ownVehicleService.queryOwnVehicleBySelective(
		    ownTruckCondition, null);
	    if(ownTruckCondition == null || StringUtils.isBlank(ownTruckCondition.getVehcleLengthCode())){
		return null;
	    }
	    vehicleLengthCode = ownTruckCondition.getVehcleLengthCode();
	    isLeasedTruck = false;
	}

	// 根据部门编码，车长类型，查出装卸车标准
	LoadAndUnloadEfficiencyVehicleEntity loadAndUnloadEfficiencyVehicleEntity = loadAndUnloadEfficiencyVehicleService
		.queryLoadAndUnloadEfficiencyVehicleByOrgVehicleLength(orgCode,
			vehicleLengthCode);

	// 如果装卸车标准不存在，直接返回
	if (loadAndUnloadEfficiencyVehicleEntity == null) {
	    return null;
	}

	LoadAndUnloadEfficiencyVehicleDto dto = new LoadAndUnloadEfficiencyVehicleDto();

	if (isLeasedTruck) {
	    /**
	     * 下面处理外请车
	     */
	    // 是否"非敞篷车"
	    openVehicle = leasedTruckEntity.getOpenVehicle();

	    // 是否"带高栏"
	    ailVehicle = leasedTruckEntity.getRailVehicle();

	    if (StringUtils.equals(FossConstants.YES, openVehicle)
		    && StringUtils.equals(FossConstants.YES, ailVehicle)) {
		// 处理"敞篷车"且“带高栏”
		dto.setLoadHour(loadAndUnloadEfficiencyVehicleEntity
			.getGlCpLoadHours());
		dto.setLoadMinute(loadAndUnloadEfficiencyVehicleEntity
			.getGlCpLoadMins());
		dto.setUnloadHour(loadAndUnloadEfficiencyVehicleEntity
			.getGlCpUnloadHours());
		dto.setUnloadMinute(loadAndUnloadEfficiencyVehicleEntity
			.getGlCpUnloadMins());

		return dto;
	    } else if (StringUtils.equals(FossConstants.YES, openVehicle)
		    && StringUtils.equals(FossConstants.NO, ailVehicle)) {
		// 处理"敞篷车"且“不带高栏”
		dto.setLoadHour(loadAndUnloadEfficiencyVehicleEntity
			.getNglCpLoadHours());
		dto.setLoadMinute(loadAndUnloadEfficiencyVehicleEntity
			.getNglCpLoadMins());
		dto.setUnloadHour(loadAndUnloadEfficiencyVehicleEntity
			.getNglCpUnloadHours());
		dto.setUnloadMinute(loadAndUnloadEfficiencyVehicleEntity
			.getNglCpUnloadMins());

		return dto;
	    }
	} else {
	    /**
	     * 下面处理公司车
	     */
	    // 处理"非敞篷车"(公司车)
	    dto.setLoadHour(loadAndUnloadEfficiencyVehicleEntity
		    .getNcpLoadHours());
	    dto.setLoadMinute(loadAndUnloadEfficiencyVehicleEntity
		    .getNcpLoadMins());
	    dto.setUnloadHour(loadAndUnloadEfficiencyVehicleEntity
		    .getNcpUnloadHours());
	    dto.setUnloadMinute(loadAndUnloadEfficiencyVehicleEntity
		    .getNcpUnloadMins());

	    return dto;
	}
	return null;
    }
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(String plateNumber, String orgCode) {
		if (StringUtils.isBlank(plateNumber) || StringUtils.isBlank(orgCode)) {
		    String exceptionDesc = "车牌号或者部门名称不能为空";
		    throw new LoadAndUnloadEfficiencyVehicleException(exceptionDesc,
			    exceptionDesc);
		}
	}
    

    /**
     * 根据车辆的车牌号，部门编码，查询装卸车标准（卸一车需要多长时间）
     * 
     * 如果当前组织没有，向上查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     */
    @Override
    public LoadAndUnloadEfficiencyVehicleDto gainLoadAndUnloadEfficiencyVehicleUp(
	    String plateNumber, String orgCode){
	// 查询此组织的所有上级组织
	List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService
		.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
	
	// 将组织的标杆编码unified_code及实例放在map中，声明map
	Map<String, OrgAdministrativeInfoEntity> map =new HashMap<String, OrgAdministrativeInfoEntity>();
	// 将组织的编码code及实例放在map中，便于获取,先声明map
	Map<String, OrgAdministrativeInfoEntity> mapUnified =new HashMap<String, OrgAdministrativeInfoEntity>();
	for(OrgAdministrativeInfoEntity org:orgs){
	    // 将组织的标杆编码unified_code及实例放在map中，便于获取
	    mapUnified.put(org.getUnifiedCode(), org);
	    // 将组织的编码code及实例放在map中，便于获取
	    map.put(org.getCode(), org);
	}
	
	// 循环获取一个组织的装卸车标准
	String currCode=orgCode;
	for(int i=0,l=orgs.size();i<l;i++){
	    LoadAndUnloadEfficiencyVehicleDto dto = this.gainLoadAndUnloadEfficiencyVehicle(plateNumber, currCode);
	    if(dto!=null){
		return dto;
	    }
	    
	    // 获得当前组织的编码
	    OrgAdministrativeInfoEntity org = map.get(currCode);
	    if(org==null || StringUtils.isBlank(org.getUnifiedCode())){
		// 如果组织不存在，则返回空的装卸车标准
		return null;
	    }
	    
	    // 获得上级组织
	    org = mapUnified.get(org.getUnifiedCode());
	    if(org == null || StringUtils.isBlank(org.getCode())){
		// 如果组织不存在，则返回空的装卸车标准
		return null;
	    }
	    
	    // 将上级组织设置为当前组织
	    currCode=org.getCode();
	}
	
	
	return null;
    }

}
