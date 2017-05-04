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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonOwnDriverAction.java
 * 
 * FILE NAME        	: CommonOwnDriverAction.java
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
 * FILE    NAME: CommonOwnDriverAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OwnDriverVo;

/**
 * 公共选择器--公司司机查询Action.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午8:34:07
 */
public class CommonOwnDriverAction extends AbstractAction implements
		IQueryAction {

	/** serialVersionUID. */
	private static final long serialVersionUID = 6295175192657260127L;
	// service
	/** The common own driver service. */
	private ICommonOwnDriverService commonOwnDriverService;
	// vo
	/** The driver vo. */
	private OwnDriverVo driverVo = new OwnDriverVo();

	/**
	 * 公司司机查询.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:34:20
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		driverVo.setDriverEntities(commonOwnDriverService
				.queryOwnDriverByCondition(driverVo.getDriverEntity(), start,
						limit));
		setTotalCount(commonOwnDriverService
				.queryOwnDriverRecordByCondition(driverVo.getDriverEntity()));
		return returnSuccess();
	}

	/**
	 * 所有司机查询.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:34:20
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	public String queryAllOwnDriver() {
		driverVo.setDriverDtos(commonOwnDriverService
				.queryDriverByCondition(driverVo, start,
						limit));
		setTotalCount(commonOwnDriverService
				.queryDriverRecordByCondition(driverVo.getDriverEntity()));
		return returnSuccess();
	}

	
	/**
	 * Sets the common own driver service.
	 *
	 * @param commonOwnDriverService the new common own driver service
	 */
	public void setCommonOwnDriverService(
			ICommonOwnDriverService commonOwnDriverService) {
		this.commonOwnDriverService = commonOwnDriverService;
	}

	/**
	 * Gets the driver vo.
	 *
	 * @return the driver vo
	 */
	public OwnDriverVo getDriverVo() {
		return driverVo;
	}

	/**
	 * Sets the driver vo.
	 *
	 * @param driverVo the new driver vo
	 */
	public void setDriverVo(OwnDriverVo driverVo) {
		this.driverVo = driverVo;
	}

}
