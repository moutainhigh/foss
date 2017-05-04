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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonTransferCenterAction.java
 * 
 * FILE NAME        	: CommonTransferCenterAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTransferCenterService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferCenterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonTransferCenterVo;

/**
 * 公共组件--外场查询Action.
 * 
 * @author panGuangJun
 * @date 2012-12-3 上午9:28:00
 */
public class CommonTransferCenterAction extends AbstractAction implements
		IQueryAction {

	/** serialVersionUID. */
	private static final long serialVersionUID = -2904453217953680626L;

	/**
	 * 外场交互vo
	 */
	private CommonTransferCenterVo transferCenterVo = new CommonTransferCenterVo();

	/**
	 * 外场信息查询Service
	 */
	private ICommonTransferCenterService commonTransferCenterService;

	/** 
	 * 查询外场信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-30 下午5:00:43
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		long totalCount = commonTransferCenterService
				.queryRecordCount(transferCenterVo.getDto());
		if (totalCount > 0) {
			List<TransferCenterEntity> transferCenterList = commonTransferCenterService
					.queryTransferCenterByCondition(transferCenterVo.getDto(),
							limit, start);
			transferCenterVo.setTransferCenterList(transferCenterList);
		}
		setTotalCount(totalCount);
		return returnSuccess();
	}

	public CommonTransferCenterVo getTransferCenterVo() {
		return transferCenterVo;
	}

	public void setTransferCenterVo(CommonTransferCenterVo transferCenterVo) {
		this.transferCenterVo = transferCenterVo;
	}

	public void setCommonTransferCenterService(
			ICommonTransferCenterService commonTransferCenterService) {
		this.commonTransferCenterService = commonTransferCenterService;
	}

}
