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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonSubSidiaryAction.java
 * 
 * FILE NAME        	: CommonSubSidiaryAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSubSidiaryService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSubSidiaryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonSubSidiaryVo;

/**
 * 公共组件--子公司信息Action
 * @author 101911-foss-zhouChunlai
 * @date 2013-2-2 上午9:43:37
 */
public class CommonSubSidiaryAction extends AbstractAction implements IQueryAction{
	
	private static final long serialVersionUID = 4716395916351397991L;
	
	/**
	 * 前后台交互子公司信息Vo
	 */
	private CommonSubSidiaryVo commonSubSidiaryVo = new CommonSubSidiaryVo();
	
	/**
	 * 子公司信息Service
	 */
	private ICommonSubSidiaryService commonSubSidiaryService;
	
	/** 
	 * 查询子公司信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-2-2 上午10:04:06
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		long totalCount= commonSubSidiaryService.queryRecordCount(commonSubSidiaryVo.getDto());
		if(totalCount>0){
			List<CommonSubSidiaryDto> subSidiaryList=commonSubSidiaryService.querySubSidiaryByCondition(commonSubSidiaryVo.getDto(), limit, start);
			commonSubSidiaryVo.setSubSidiaryList(subSidiaryList);
		}
		setTotalCount(totalCount);
		return returnSuccess();
	}

	
	public CommonSubSidiaryVo getCommonSubSidiaryVo() {
		return commonSubSidiaryVo;
	}

	
	public void setCommonSubSidiaryVo(CommonSubSidiaryVo commonSubSidiaryVo) {
		this.commonSubSidiaryVo = commonSubSidiaryVo;
	}

	
	public void setCommonSubSidiaryService(
			ICommonSubSidiaryService commonSubSidiaryService) {
		this.commonSubSidiaryService = commonSubSidiaryService;
	}

}
