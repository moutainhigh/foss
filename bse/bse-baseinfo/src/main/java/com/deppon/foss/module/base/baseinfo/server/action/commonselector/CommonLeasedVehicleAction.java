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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonLeasedVehicleAction.java
 * 
 * FILE NAME        	: CommonLeasedVehicleAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonLeasedVehicleAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedVehicleVo;

/**
 * 公共选择器--外请车.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午8:40:38
 */
public class CommonLeasedVehicleAction extends AbstractAction implements
		IQueryAction {

	/** Serial Version UID. */
	private static final long serialVersionUID = 4059407470760374948L;

	// "外请车"参数和结果对象
	/** The leased vehicle vo. */
	private LeasedVehicleVo leasedVehicleVo = new LeasedVehicleVo();

	// "外请车"服务
	/** The common leased vehicle service. */
	private ICommonLeasedVehicleService commonLeasedVehicleService;

	/**
	 * 外请车查询方法.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-1 上午8:40:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		leasedVehicleVo.setLeasedVehicleList(commonLeasedVehicleService
				.queryLeasedVehicleListCondition(
						leasedVehicleVo.getLeasedVehicle(), start, limit));
		setTotalCount(commonLeasedVehicleService.queryLeasedVehicleRecordCountBySelectiveCondition(leasedVehicleVo.getLeasedVehicle()));
		return returnSuccess();
	}

	/**
	 * Sets the leased vehicle vo.
	 *
	 * @param leasedVehicleVo the new leased vehicle vo
	 */
	public void setLeasedVehicleVo(LeasedVehicleVo leasedVehicleVo) {
		this.leasedVehicleVo = leasedVehicleVo;
	}

	/**
	 * Gets the leased vehicle vo.
	 *
	 * @return the leased vehicle vo
	 */
	public LeasedVehicleVo getLeasedVehicleVo() {
		return leasedVehicleVo;
	}

	/**
	 * Sets the common leased vehicle service.
	 *
	 * @param commonLeasedVehicleService the new common leased vehicle service
	 */
	public void setCommonLeasedVehicleService(
			ICommonLeasedVehicleService commonLeasedVehicleService) {
		this.commonLeasedVehicleService = commonLeasedVehicleService;
	}

}
