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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonLeasedDriverAction.java
 * 
 * FILE NAME        	: CommonLeasedDriverAction.java
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
 * FILE    NAME: CommonLeasedDriverAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedDriverVo;

/**
 * 公共选择器--外请司机查询Action.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午8:52:24
 */
public class CommonLeasedDriverAction extends AbstractAction implements
		IQueryAction {

	/** Serial Version UID. */
	private static final long serialVersionUID = 2617986849577074753L;

	// "外请司机"参数和结果对象
	/** The leased driver vo. */
	private LeasedDriverVo leasedDriverVo = new LeasedDriverVo();

	// "外请司机"服务
	/** The common leased driver service. */
	private ICommonLeasedDriverService commonLeasedDriverService;

	/**
	 * 外请司机查询方法.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-1 上午8:52:24
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		List<LeasedDriverEntity> leasedDriverList = commonLeasedDriverService
				.queryLeasedDriverListByCondition(
						leasedDriverVo.getLeasedDriver(), start, limit);
		leasedDriverVo.setLeasedDriverList(leasedDriverList);
		setTotalCount(commonLeasedDriverService
				.countLeasedDriverListByCondition(leasedDriverVo
						.getLeasedDriver()));
		return returnSuccess();
	}

	/**
	 * Gets the leased driver vo.
	 *
	 * @return the leased driver vo
	 */
	public LeasedDriverVo getLeasedDriverVo() {
		return leasedDriverVo;
	}

	/**
	 * Sets the leased driver vo.
	 *
	 * @param leasedDriverVo the new leased driver vo
	 */
	public void setLeasedDriverVo(LeasedDriverVo leasedDriverVo) {
		this.leasedDriverVo = leasedDriverVo;
	}

	/**
	 * Sets the common leased driver service.
	 *
	 * @param commonLeasedDriverService the new common leased driver service
	 */
	public void setCommonLeasedDriverService(
			ICommonLeasedDriverService commonLeasedDriverService) {
		this.commonLeasedDriverService = commonLeasedDriverService;
	}
}
