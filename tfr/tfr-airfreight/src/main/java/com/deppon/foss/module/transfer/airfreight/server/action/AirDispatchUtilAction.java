/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/AirDispatchUtilAction.java
 * 
 *  FILE NAME          :AirDispatchUtilAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirDispatchVo;

public class AirDispatchUtilAction extends AbstractAction {

	private static final long serialVersionUID = -6535525211252658627L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirDispatchUtilAction.class);
	
	private IAirDispatchUtilService airDispatchUtilService;

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	
	private AirDispatchVo airDispatchVo = new AirDispatchVo();
	
	public AirDispatchVo getAirDispatchVo() {
		return airDispatchVo;
	}

	public void setAirDispatchVo(AirDispatchVo airDispatchVo) {
		this.airDispatchVo = airDispatchVo;
	}

	/**
	 * 查询空运总调
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-30 上午9:52:11
	 */
	@JSON
	public String queryAirDispatch(){
		try{
			String currentDeptCode = airDispatchVo.getDeptCode();
			airDispatchVo.setOrgAdministrativeInfoEntity(airDispatchUtilService.queryAirDispatchDept(currentDeptCode));
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(),e);
			return returnError(e);
		}
	}

}