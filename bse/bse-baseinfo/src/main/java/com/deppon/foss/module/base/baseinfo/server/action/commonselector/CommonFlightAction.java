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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonFlightAction.java
 * 
 * FILE NAME        	: CommonFlightAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonFlightService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.FlightVo;

/**
 * 公共查询组件--航班信息ACTION.
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-8 下午2:12:50
 */
public class CommonFlightAction extends AbstractAction implements IQueryAction { 
	
	private static final long serialVersionUID = -7536098517185921328L;

	/**
	 * 航班信息VO
	 */
	private FlightVo flightVo=new FlightVo();   
	
	/**
	 * 航班信息Service
	 */
	private ICommonFlightService commonFlightService; 
	 

	 
	/** 
	 * 航班信息查询
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-8 下午3:22:58
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		totalCount=commonFlightService.queryRecordCount(flightVo.getFlightDto());
		if(totalCount > 0){
			List<FlightEntity> flightList = commonFlightService.queryFlight(flightVo.getFlightDto(), start,limit);
			flightVo.setFlightList(flightList);
			setTotalCount(totalCount);	
		}
		return returnSuccess();
	}
	
	public FlightVo getFlightVo() {
		return flightVo;
	}
	
	public void setFlightVo(FlightVo flightVo) {
		this.flightVo = flightVo;
	}
	
	public void setCommonFlightService(ICommonFlightService commonFlightService) {
		this.commonFlightService = commonFlightService;
	} 
}
