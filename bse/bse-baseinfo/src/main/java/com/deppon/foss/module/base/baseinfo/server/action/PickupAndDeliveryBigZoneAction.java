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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/PickupAndDeliveryBigZoneAction.java
 * 
 * FILE NAME        	: PickupAndDeliveryBigZoneAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PAndDeliveryZoneRegionVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

/**
 * 接送货大区action.
 *
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 14:10:10
 * @since
 * @version 0.01
 */
public class PickupAndDeliveryBigZoneAction extends AbstractAction {

    /**
     * 下面是声明的属性.
     */
    private static final long serialVersionUID = -802246567875971335L;

    // 接送货大区service接口
    /**
     * 
     */
    private IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService;

    // 接送货小区service接口
    /**
     * 
     */
    private IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService;
    /**
     * 业务锁 Service
     */
    private IBusinessLockService businessLockService;
    /**
     * 部门 复杂查询 service.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    /**
     * 系统配置参数 Service接口.
     */
    private IConfigurationParamsService configurationParamsService;

    // 接送货大区 使用VO
    /**
     * 
     */
    private PAndDeliveryZoneRegionVo objectVo = new PAndDeliveryZoneRegionVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(PickupAndDeliveryBigZoneAction.class);
    
    /**
     * 设置 部门 复杂查询 service.
     *
     * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }
    
    /**
     * 设置 系统配置参数 Service接口.
     *
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }
    
    public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
     * <p>
     * 查询接送货大区
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     */
    @JSON
    public String queryPickupAndDeliveryBigZoneByEntity() {
	//获取当前部门编码
	List<String> deptCodes = FossUserContext
		.getCurrentUserManagerDeptCodes();
	// 查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合
	List<String> deptCodeList = new ArrayList<String>(); 
	BigZoneEntity entityCondition = objectVo.getBigZoneEntity();
	ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(
		DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,
		ConfigurationParamsConstants.BAS_REGIONAL_VEHICLE_SYSTEM_ADMINSTRATOR, FossUserContext.getCurrentDeptCode());
	if (entity != null) {
	    String parmValue = entity.getConfValue();
	    if(StringUtils.isNotBlank(parmValue)){
		if(!StringUtils.equals(FossConstants.YES, parmValue)){
		    if (StringUtils.isNotBlank(entityCondition.getManagement())) {

			if (exists(deptCodeList,
				entityCondition.getManagement())) {
			    deptCodeList.add(entityCondition.getManagement());
			} else {
			    deptCodeList.add("depponNull");
			}

		    } else {
			deptCodeList.add(FossUserContext.getCurrentDeptCode());
		    }
		}else {
		    // 查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合
		    deptCodeList = orgAdministrativeInfoComplexService.queryDispatchTeamDeptCodeListFromTopFleetByCodeList(deptCodes); 
		    if (StringUtils.isNotBlank(entityCondition.getManagement())) {
			 deptCodeList = new ArrayList<String>();
			 deptCodeList.add(entityCondition.getManagement());

		    } else {
			deptCodeList = new ArrayList<String>();
		    }
		}
	    }
	}else {
	    // 查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合
	    deptCodeList = orgAdministrativeInfoComplexService.queryDispatchTeamDeptCodeListFromTopFleetByCodeList(deptCodes); 
	    if (StringUtils.isNotBlank(entityCondition.getManagement())) {

		if (exists(deptCodeList,
			entityCondition.getManagement())) {
		    deptCodeList = new ArrayList<String>();
		    deptCodeList.add(entityCondition.getManagement());
		} else {
		    deptCodeList = new ArrayList<String>();
		    deptCodeList.add("depponNull");
		}

	    } else {
		deptCodeList.add(FossUserContext.getCurrentDeptCode());
	    }
	}
	entityCondition.setManagementCodeList(deptCodeList);
	// 返回的结果显示在表格中：
	objectVo.setBigZoneEntityList(pickupAndDeliveryBigZoneService
		.queryPickupAndDeliveryBigZones(entityCondition, limit, start));
	totalCount = pickupAndDeliveryBigZoneService
		.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * 根据传入的管理部门Code、集中接送货大区的区域类型（接货区、送货区）查询符合条件 的集中接送货小区.
     *
     * @return 集中接送货小区列表
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @see
     */
    public String querySmallZonesByDeptCode() {
	SmallZoneEntity entityCondition = objectVo.getSmallZoneEntity();
	// 返回的结果显示在表格中：
	objectVo.setSmallZoneEntityList(pickupAndDeliverySmallZoneService
		.querySmallZonesByDeptCode(entityCondition.getManagement(),
			entityCondition.getRegionType(),
			entityCondition.getBigzonecode()));
	return returnSuccess();
    }

    /**
     * 作废接送货大区.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @see
     */
    public String deletePickupAndDeliveryBigZone() {
	try {
	    objectVo.setReturnInt(pickupAndDeliveryBigZoneService
		    .deletePickupAndDeliveryBigZoneByCode(
			    objectVo.getCodeStr(), FossUserContext
				    .getCurrentInfo().getEmpCode()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改接送货大区.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @see
     */
    public String updatePickupAndDeliveryBigZone() {
	try {
	    objectVo.setReturnInt(pickupAndDeliveryBigZoneService
		    .updatePickupAndDeliveryBigZone(
			    objectVo.getBigZoneEntity(),
			    objectVo.getAddSmallZoneList(),
			    objectVo.getDelSmallZoneList()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增接送货大区.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @see
     */
    public String addPickupAndDeliveryBigZone() {
	try {
		//大区新增加业务锁
		//大区编码和大区名称非空校验
		if(StringUtils.isNotBlank(objectVo.getBigZoneEntity().getRegionCode())
				&&StringUtils.isNotBlank(objectVo.getBigZoneEntity().getRegionName())){
			MutexElement mutex = new MutexElement(String.valueOf(objectVo.getBigZoneEntity().getRegionCode()), "PICKUP_BIGZONE_CODE",MutexElementType.PICKUP_BIGZONE_CODE);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			}
			objectVo.setReturnInt(pickupAndDeliveryBigZoneService
				    .addPickupAndDeliveryBigZone(objectVo.getBigZoneEntity(),
					    objectVo.getAddSmallZoneList(),
					    objectVo.getDelSmallZoneList()));
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 接送货大区 是否重复
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     */
    public String pickupAndDeliveryBigZoneIsExist() {
	// TODO
	return returnSuccess();
    }
    
    /**
     * <p>判断某个元素在集合中是否存在</p>.
     *
     * @param list 
     * @param element 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-6-28 上午9:38:08
     * @see
     */
    private boolean exists(List<String> list,String element){
	if(CollectionUtils.isNotEmpty(list) && null != element){
	    for (String elment2 : list) {
                if (StringUtils.equals(elment2,element)) {
                    return true;
                }
            }
	}
	return false;
    }
    
    /**
     * <p>
     * 接送货大区 自动生成大区编码
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     */
    public String autoCreateBigZoneNum() {

	String regionCode = pickupAndDeliveryBigZoneService.generateCode(
		objectVo.getBigZoneEntity().getManagement(), objectVo
			.getBigZoneEntity().getType());
	objectVo.setCodeStr(regionCode);
	return returnSuccess();
    }

    /*
     * =================================================================
     * 下面是get,set方法：
     */
    /**
     * 
     *
     * @return 
     */
    public PAndDeliveryZoneRegionVo getObjectVo() {
	return objectVo;
    }

    /**
     * 
     *
     * @param objectVo 
     */
    public void setObjectVo(PAndDeliveryZoneRegionVo objectVo) {
	this.objectVo = objectVo;
    }

    /**
     * 
     *
     * @param pickupAndDeliveryBigZoneService 
     */
    public void setPickupAndDeliveryBigZoneService(
	    IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService) {
	this.pickupAndDeliveryBigZoneService = pickupAndDeliveryBigZoneService;
    }

    /**
     * 
     *
     * @param pickupAndDeliverySmallZoneService 
     */
    public void setPickupAndDeliverySmallZoneService(
	    IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
	this.pickupAndDeliverySmallZoneService = pickupAndDeliverySmallZoneService;
    }

}
