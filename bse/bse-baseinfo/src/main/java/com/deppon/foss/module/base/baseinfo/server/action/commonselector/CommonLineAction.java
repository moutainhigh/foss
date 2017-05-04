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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonLineAction.java
 * 
 * FILE NAME        	: CommonLineAction.java
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
 * FILE    NAME: CommonLineSeletorAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LineVo;

/**
 * 线路查询Action.
 *
 * @author panGuangJun
 * @date 2012-11-30 下午7:02:56
 */
public class CommonLineAction extends AbstractAction implements IQueryAction{
	
	/** Serializeid. */
	private static final long serialVersionUID = -7558152034668220442L;
	
	/** The common line service. */
	private ICommonLineService commonLineService;
	
	/** The line vo. */
	private LineVo  lineVo = new LineVo();
	
	/**
	 * 公共组件--线路查询.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-11-30 下午7:03:10
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		lineVo.setLineEntityList(commonLineService.queryLineListByCondition(lineVo.getLineEntity(), start, limit));
		totalCount = commonLineService.countLineListByCondition(lineVo.getLineEntity());
		return returnSuccess();
	}
	
	/**
	 * Gets the line vo.
	 *
	 * @return the line vo
	 */
	public LineVo getLineVo() {
		return lineVo;
	}
	
	/**
	 * Sets the line vo.
	 *
	 * @param lineVo the new line vo
	 */
	public void setLineVo(LineVo lineVo) {
		this.lineVo = lineVo;
	}
	
	/**
	 * Sets the common line service.
	 *
	 * @param commonLineService the new common line service
	 */
	public void setCommonLineService(ICommonLineService commonLineService) {
		this.commonLineService = commonLineService;
	}
	
}
