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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonSmallZoneAction.java
 * 
 * FILE NAME        	: CommonSmallZoneAction.java
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
 * FILE    NAME: CommonSmallZoneAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SmallZoneVo;

/**
 * 公共组件--查询集中接送货小区.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午9:40:48
 */
public class CommonSmallZoneAction extends AbstractAction implements
		IQueryAction {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 5259691061042030422L;
	// vo
	/** The small zone vo. */
	private SmallZoneVo smallZoneVo = new SmallZoneVo();
	// serivce
	/** The common small zone service. */
	private ICommonSmallZoneService commonSmallZoneService;

	/**
	 * 查询接送货小区.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-3 上午9:40:48
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		smallZoneVo.setSmallZoneEntities(commonSmallZoneService
				.querySmallZones(smallZoneVo.getEntity(), limit, start));
		setTotalCount(commonSmallZoneService.queryRecordCount(smallZoneVo
				.getEntity()));
		return returnSuccess();
	}

	/**
	 * 查询接送货小区.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-3 上午9:40:48
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	public String queryAllSmallZone() {
		smallZoneVo.setServiceZoneDtos(commonSmallZoneService
				.queryServiceZones(smallZoneVo.getEntity(), start, limit));
		setTotalCount(commonSmallZoneService.queryServiceRecordCount(smallZoneVo
				.getEntity()));
		return returnSuccess();
	}

	/**
	 * Gets the small zone vo.
	 *
	 * @return the small zone vo
	 */
	public SmallZoneVo getSmallZoneVo() {
		return smallZoneVo;
	}

	/**
	 * Sets the small zone vo.
	 *
	 * @param smallZoneVo the new small zone vo
	 */
	public void setSmallZoneVo(SmallZoneVo smallZoneVo) {
		this.smallZoneVo = smallZoneVo;
	}

	/**
	 * Sets the common small zone service.
	 *
	 * @param commonSmallZoneService the new common small zone service
	 */
	public void setCommonSmallZoneService(
			ICommonSmallZoneService commonSmallZoneService) {
		this.commonSmallZoneService = commonSmallZoneService;
	}

}
