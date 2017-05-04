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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AirportAction.java
 * 
 * FILE NAME        	: AirportAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirportException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AirportVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 用来响应“机场信息”的Action类：SUC-52 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午2:51:55</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午2:51:55
 * @since
 * @version
 */
public class AirportAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -9216322825669792461L;
    
    //"机场信息"参数和结果对象
    private AirportVo airportVo;
    
    //"机场信息"服务
    private IAirportService airportService;
    
    /**
     * <p>通过"机场信息"来检索一个"机场信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:22
     * @return
     * @see
     */
    @JSON
    public String queryAirport(){
	AirportEntity airport = getAirportVo().getAirport();
	try {
	    airport = airportService.queryAirportBySelective(airport);
	    getAirportVo().setAirport(airport);
	} catch (AirportException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>无条件检索所有"机场信息"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:34
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryAirportList(){
	AirportEntity airport = getAirportVo().getAirport();
	try {
	    PaginationDto paginationDto = airportService.queryAirportListBySelectiveCondition(airport, start, limit);
	    getAirportVo().setAirportList(paginationDto.getPaginationDtos());
	    setTotalCount(paginationDto.getTotalCount());
	} catch (AirportException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>新增一个外请"机场信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:04
     * @return
     * @see
     */
    @JSON
    public String addAirport(){
	AirportEntity airport = getAirportVo().getAirport();
	String createUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    airportService.addAirport(airport, createUser, false);
	    return returnSuccess(AirportException.AIRPORT_ADD_SUCCESS);
	} catch (AirportException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>删除一个外请"机场信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:02
     * @return
     * @see
     */
    @JSON
    public String deleteAirport(){
	List<String> ids = getAirportVo().getBatchIds();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    airportService.deleteAirport(ids, modifyUser);
	    return returnSuccess(AirportException.AIRPORT_DEL_SUCCESS);
	} catch (AirportException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>修改一个外请"机场信息"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午1:59:59
     * @return
     * @see
     */
    @JSON
    public String updateAirport(){
	AirportEntity airport = getAirportVo().getAirport();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    airportService.updateAirport(airport, modifyUser, true);
	    return returnSuccess(AirportException.AIRPORT_UPD_SUCCESS);
	} catch (AirportException e) {
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
     * @return  the airportVo
     */
    public AirportVo getAirportVo() {
	if (null == airportVo) {
	    this.airportVo = new AirportVo();
	}
        return airportVo;
    }

    /**
     * @param airportVo the airportVo to set
     */
    public void setAirportVo(AirportVo airportVo) {
        this.airportVo = airportVo;
    }

    /**
     * @param airportService the airportService to set
     */
    public void setAirportService(IAirportService airportService) {
        this.airportService = airportService;
    }
}
