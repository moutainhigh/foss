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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonMotorcadeAction.java
 * 
 * FILE NAME        	: CommonMotorcadeAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonMotorcadeVo;

/**
 * 公共查询选择器--车队ACTION.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-11 下午2:32:53
 */
public class CommonMotorcadeAction extends AbstractAction implements
		IQueryAction {

	private static final long serialVersionUID = 4802732411381220040L;

	/**
	 * 车队Service
	 */
	private ICommonMotorcadeService commonMotorcadeService;

	/**
	 * 车队Vo
	 */
	private CommonMotorcadeVo commonMotorcadeVo=new CommonMotorcadeVo();

	/**
	 * 查询车队.
	 * 
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-11 下午2:35:41
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		long total=commonMotorcadeService.queryMotorcadeByConditionCount(commonMotorcadeVo.getCommonMotorcadeDto());
		if(total>0){
			List<MotorcadeEntity> motorcadeEntityList = commonMotorcadeService.queryMotorcadeByCondition(commonMotorcadeVo.getCommonMotorcadeDto(), start, limit);
			commonMotorcadeVo.setMotorcadeEntityList(motorcadeEntityList);
		}
		setTotalCount(total);
		return returnSuccess();
	}

	
	public CommonMotorcadeVo getCommonMotorcadeVo() {
		return commonMotorcadeVo;
	}

	
	public void setCommonMotorcadeVo(CommonMotorcadeVo commonMotorcadeVo) {
		this.commonMotorcadeVo = commonMotorcadeVo;
	}

	
	public void setCommonMotorcadeService(
			ICommonMotorcadeService commonMotorcadeService) {
		this.commonMotorcadeService = commonMotorcadeService;
	}

	 
}
