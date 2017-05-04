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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LeasedVehicleTypeAction.java
 * 
 * FILE NAME        	: LeasedVehicleTypeAction.java
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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleTypeException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedVehicleTypeVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 用来响应“车辆车型”的Action类：SUC-109 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-11 上午9:57:27</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-11 上午9:57:27
 * @since
 * @version
 */
public class LeasedVehicleTypeAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3499788847075929654L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(LeasedVehicleTypeAction.class);
	
    private LeasedVehicleTypeVo leasedVehicleTypeVo;
    
    private ILeasedVehicleTypeService leasedVehicleTypeService;

    /**
     * <p>通过车长车型信息来检索一个车长车型信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:22
     * @return
     * @see
     */
    @JSON
    public String queryLeasedVehicleType(){
	VehicleTypeEntity vehicleType = getLeasedVehicleTypeVo().getLeasedVehicleType();
	try {
	    vehicleType = leasedVehicleTypeService.queryLeasedVehicleType(vehicleType);
	    getLeasedVehicleTypeVo().setLeasedVehicleType(vehicleType);
	} catch (BusinessException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>无条件检索所有车长车型信息列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:34
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryLeasedVehicleTypeList(){
	VehicleTypeEntity vehicleType = getLeasedVehicleTypeVo().getLeasedVehicleType();
	try {
	    PaginationDto pagination = leasedVehicleTypeService.queryLeasedVehicleTypeListBySelectiveCondition(vehicleType, start, limit);
	    getLeasedVehicleTypeVo().setLeasedVehicleTypeList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (BusinessException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>新增一个外请车长车型信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:04
     * @return
     * @see
     */
    @JSON
    public String addLeasedVehicleType(){
	VehicleTypeEntity vehicleType = getLeasedVehicleTypeVo().getLeasedVehicleType();
	String createUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    leasedVehicleTypeService.addLeasedVehicleType(vehicleType, createUser, false);
	    return returnSuccess(LeasedVehicleTypeException.LEASEDVEHICLE_ADD_SUCCESS);
	} catch (BusinessException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>修改一个外请车长车型信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午1:59:59
     * @return
     * @see
     */
    @JSON
    public String updateLeasedVehicleType(){
	VehicleTypeEntity vehicleType = getLeasedVehicleTypeVo().getLeasedVehicleType();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    leasedVehicleTypeService.updateLeasedVehicleType(vehicleType, modifyUser, true);
	    return returnSuccess(LeasedVehicleTypeException.LEASEDVEHICLE_UPD_SUCCESS);
	} catch (BusinessException e) {
		logger.error(e.getMessage(), e);
		 return returnError(e);
	}
    }

    /**
     * <p>获取当前的登录用户</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午5:34:46
     * @return
     * @see
     */
    private UserEntity getCurrentUser(){
	UserEntity user = FossUserContext.getCurrentUser();
	return null == user ? new UserEntity() : user;
    }
    
    /**
     * @param leasedVehicleTypeService the leasedVehicleTypeService to set
     */
    public void setLeasedVehicleTypeService(
    	ILeasedVehicleTypeService leasedVehicleTypeService) {
        this.leasedVehicleTypeService = leasedVehicleTypeService;
    }
    
    /**
     * @return  the leasedVehicleTypeVo
     */
    public LeasedVehicleTypeVo getLeasedVehicleTypeVo() {
	if (null == leasedVehicleTypeVo) {
	    this.leasedVehicleTypeVo = new LeasedVehicleTypeVo();
	}
        return leasedVehicleTypeVo;
    }

    /**
     * @param leasedVehicleTypeVo the leasedVehicleTypeVo to set
     */
    public void setLeasedVehicleTypeVo(LeasedVehicleTypeVo leasedVehicleTypeVo) {
        this.leasedVehicleTypeVo = leasedVehicleTypeVo;
    }
}

