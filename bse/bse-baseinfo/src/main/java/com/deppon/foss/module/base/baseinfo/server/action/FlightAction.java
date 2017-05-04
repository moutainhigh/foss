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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/FlightAction.java
 * 
 * FILE NAME        	: FlightAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.FlightVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 用来响应“航班信息”的Action类：SUC-785 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午3:11:45</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午3:11:45
 * @since
 * @version
 */
public class FlightAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5692509470390340431L;
    
    //"航班信息"参数和结果对象
    private FlightVo flightVo;
    
    //"航班信息"服务
    private IFlightService flightService;

    /**
     * <p>通过车长车型信息来检索一个车长车型信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:22
     * @return
     * @see
     */
    @JSON
    public String queryFlight(){
	FlightEntity flight = getFlightVo().getFlight();
	try {
	    flight = flightService.queryFlightBySelective(flight);
	    getFlightVo().setFlight(flight);
	} catch (FlightException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>无条件检索所"航班信息"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:34
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryFlightList(){
	FlightEntity flight = getFlightVo().getFlight();
	try {
	    PaginationDto paginationDto = flightService.queryFlightDtoListBySelectiveCondition(flight, start, limit);
	    getFlightVo().setFlightList(paginationDto.getPaginationDtos());
	    setTotalCount(paginationDto.getTotalCount());
	} catch (FlightException e) {
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
    public String addFlight(){
	FlightEntity flight = getFlightVo().getFlight();
	String createUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    flightService.addFlight(flight, createUser, false);
	    return returnSuccess(FlightException.FLIGHT_ADD_SUCCESS);
	} catch (FlightException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>删除一个外请车长车型信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:02
     * @return
     * @see
     */
    @JSON
    public String deleteFlight(){
	List<String> ids = getFlightVo().getBatchIds();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    flightService.deleteFlight(ids, modifyUser);
	    return returnSuccess(FlightException.FLIGHT_DEL_SUCCESS);
	} catch (FlightException e) {
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
    public String updateFlight(){
	FlightEntity flight = getFlightVo().getFlight();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    flightService.updateFlight(flight, modifyUser, true);
	    return returnSuccess(FlightException.FLIGHT_UPD_SUCCESS);
	} catch (FlightException e) {
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
     * @return  the flightVo
     */
    public FlightVo getFlightVo() {
	if (null == flightVo) {
	    this.flightVo = new FlightVo();
	}
        return flightVo;
    }
    
    /**
     * @param flightVo the flightVo to set
     */
    public void setFlightVo(FlightVo flightVo) {
        this.flightVo = flightVo;
    }

    /**
     * @param flightService the flightService to set
     */
    public void setFlightService(IFlightService flightService) {
        this.flightService = flightService;
    }
}
