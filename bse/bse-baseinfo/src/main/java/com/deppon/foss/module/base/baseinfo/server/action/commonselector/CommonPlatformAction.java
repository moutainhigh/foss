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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonPlatformAction.java
 * 
 * FILE NAME        	: CommonPlatformAction.java
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
 * FILE    NAME: CommonPlatformAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPlatformService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PlatformVo;

/**
 * 公共选择器--月台查询ACTION.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午8:33:55
 */
public class CommonPlatformAction extends AbstractAction implements
		IQueryAction {

	/** serializeId. */
	private static final long serialVersionUID = -9171432476207375630L;

	// 前后台传的参数
	/** The platform vo. */
	private PlatformVo platformVo = new PlatformVo();
	//月台service
	/** The common platform service. */
	private ICommonPlatformService commonPlatformService;
	
	/**
	 * 月台查询.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-1 上午8:34:09
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		this.setTotalCount(commonPlatformService.countPlatformListByCondition(platformVo.getPlatformEntity()));
		List<PlatformEntity> platformEntityList =  commonPlatformService.queryPlatformListByCondition(platformVo.getPlatformEntity(), start, limit);
		platformVo.setPlatformEntityList(platformEntityList);
		return returnSuccess();
	}
	
	/**
	 * Gets the platform vo.
	 *
	 * @return the platform vo
	 */
	public PlatformVo getPlatformVo() {
		return platformVo;
	}
	
	/**
	 * Sets the platform vo.
	 *
	 * @param platformVo the new platform vo
	 */
	public void setPlatformVo(PlatformVo platformVo) {
		this.platformVo = platformVo;
	}
	
	/**
	 * Sets the common platform service.
	 *
	 * @param commonPlatformService the new common platform service
	 */
	public void setCommonPlatformService(
			ICommonPlatformService commonPlatformService) {
		this.commonPlatformService = commonPlatformService;
	}

}
