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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AircraftTypeAction.java
 * 
 * FILE NAME        	: AircraftTypeAction.java
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

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AircraftTypeException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AircraftTypeVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 用来响应“机型信息”的Action类：SUC-785 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午2:51:08</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午2:51:08
 * @since
 * @version
 */
public class AircraftTypeAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -7845965779421285565L;
    
    //"机型信息"参数和结果对象
    private AircraftTypeVo aircraftTypeVo;
    
    //"机型信息"服务
    private IAircraftTypeService aircraftTypeService;

    /**
     * <p>通过"机型信息"来检索一个"机型信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:22
     * @return
     * @see
     */
    @JSON
    public String queryAircraftType(){
	AircraftTypeEntity aircraftType = getAircraftTypeVo().getAircraftType();
	try {
	    aircraftType = aircraftTypeService.queryAircraftTypeBySelective(aircraftType);
	    getAircraftTypeVo().setAircraftType(aircraftType);
	} catch (AircraftTypeException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>无条件检索所有"机型信息"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:34
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryAircraftTypeList(){
	AircraftTypeEntity aircraftType = getAircraftTypeVo().getAircraftType();
	try {
	    PaginationDto paginationDto = aircraftTypeService.queryAircraftTypeListBySelectiveCondition(aircraftType, start, limit);
	    getAircraftTypeVo().setAircraftTypeList(paginationDto.getPaginationDtos());
	    setTotalCount(paginationDto.getTotalCount());
	} catch (AircraftTypeException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>新增一个外请"机型信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:04
     * @return
     * @see
     */
    @JSON
    public String addAircraftType(){
	AircraftTypeEntity aircraftType = getAircraftTypeVo().getAircraftType();
	String createUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    aircraftTypeService.addAircraftType(aircraftType, createUser, false);
	    return returnSuccess(AircraftTypeException.AIRCRAFTTYPE_ADD_SUCCESS);
	} catch (AircraftTypeException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>删除一个外请"机型信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:02
     * @return
     * @see
     */
    @JSON
    public String deleteAircraftType(){
	List<String> ids = getAircraftTypeVo().getBatchIds();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    aircraftTypeService.deleteAircraftType(ids, modifyUser);
	    return returnSuccess(AircraftTypeException.AIRCRAFTTYPE_DEL_SUCCESS);
	} catch (AircraftTypeException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>修改一个外请"机型信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午1:59:59
     * @return
     * @see
     */
    @JSON
    public String updateAircraftType(){
	AircraftTypeEntity aircraftType = getAircraftTypeVo().getAircraftType();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    aircraftTypeService.updateAircraftType(aircraftType, modifyUser, true);
	    return returnSuccess(AircraftTypeException.AIRCRAFTTYPE_UPD_SUCCESS);
	} catch (AircraftTypeException e) {
	    returnError(e);
	}
	return returnSuccess();
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
     * @return  the aircraftTypeVo
     */
    public AircraftTypeVo getAircraftTypeVo() {
	if (null == aircraftTypeVo) {
	    this.aircraftTypeVo = new AircraftTypeVo();
	}
        return aircraftTypeVo;
    }
    
    /**
     * @param aircraftTypeVo the aircraftTypeVo to set
     */
    public void setAircraftTypeVo(AircraftTypeVo aircraftTypeVo) {
        this.aircraftTypeVo = aircraftTypeVo;
    }

    /**
     * @param aircraftTypeService the aircraftTypeService to set
     */
    public void setAircraftTypeService(IAircraftTypeService aircraftTypeService) {
        this.aircraftTypeService = aircraftTypeService;
    }
}
