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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonFreightRouteLineAction.java
 * 
 * FILE NAME        	: CommonFreightRouteLineAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action.commonselector
 * FILE    NAME: CommonAirAgencyCompanyAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonFreightRouteLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonFreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonReightRouteLineCondition;

/**
 * 公共选择器--线路站点ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 上午10:32:34
 */
public class CommonFreightRouteLineAction extends AbstractAction implements
		IQueryAction {
	// service
	/** The common freight route line service. */
	private ICommonFreightRouteLineService commonFreightRouteLineService;
	// search condition
	/** The freight route line. */
	private CommonReightRouteLineCondition freightRouteLine = new CommonReightRouteLineCondition();
	// result

	/** The orgs. */
	private Set<OrgAdministrativeInfoEntity> orgs = new HashSet<OrgAdministrativeInfoEntity>();
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -3383631039625626863L;

	/**
	 * 空运代理公司查询.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午10:32:34
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		// 查询数据
		List<CommonFreightRouteLineDto> commonFreightRouteLineDtos = commonFreightRouteLineService
				.queryFreightRouteLinesByCondtion(freightRouteLine, start,
						limit);
		// 查询结果
		if (null != commonFreightRouteLineDtos
				&& 0 < commonFreightRouteLineDtos.size()) {
			for (CommonFreightRouteLineDto dto : commonFreightRouteLineDtos) {
				if (StringUtil.isEmpty(freightRouteLine.getOgirName())
						&& null != freightRouteLine.getDestName()) {
					orgs.add(dto.getDestOrg());
				} else if (null != freightRouteLine.getOgirName()
						&& StringUtil.isEmpty(freightRouteLine.getDestName())) {
					orgs.add(dto.getOrigOrg());
				}
			}
		}
		setTotalCount(commonFreightRouteLineService
				.countFreightRouteLinesByCondtion(freightRouteLine));
		return returnSuccess();
	}


	/**
	 * setter.
	 *
	 * @param commonFreightRouteLineService the new common freight route line service
	 */
	public void setCommonFreightRouteLineService(
			ICommonFreightRouteLineService commonFreightRouteLineService) {
		this.commonFreightRouteLineService = commonFreightRouteLineService;
	}

	/**
	 * setter.
	 *
	 * @param freightRouteLine the new freight route line
	 */
	public void setFreightRouteLine(
			CommonReightRouteLineCondition freightRouteLine) {
		this.freightRouteLine = freightRouteLine;
	}

	/**
	 * getter.
	 *
	 * @return the freight route line
	 */
	public CommonReightRouteLineCondition getFreightRouteLine() {
		return freightRouteLine;
	}


	/**
	 * getter.
	 *
	 * @return the orgs
	 */
	public Set<OrgAdministrativeInfoEntity> getOrgs() {
		return orgs;
	}
}
