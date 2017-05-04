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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonSaleDeptAndOuterBranchAction.java
 * 
 * FILE NAME        	: CommonSaleDeptAndOuterBranchAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDeptAndOuterBranchService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSaleDeptAndOuterBranchDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonSaleDeptAndOuterBranchVo;

/**
 * 公共查询选择器--营业部和偏线代理ACTION
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-28 上午11:30:35
 */
public class CommonSaleDeptAndOuterBranchAction extends AbstractAction implements
		IQueryAction {
	 
	private static final long serialVersionUID = -995576707443843558L;
	/**
	 * 营业部和偏线代理Service
	 */
	private ICommonSaleDeptAndOuterBranchService commonSaleDeptAndOuterBranchService;
	
	/**
	 * 营业部与偏线代理Vo
	 */
	private CommonSaleDeptAndOuterBranchVo vo=new CommonSaleDeptAndOuterBranchVo();
	
	/** 
	 * 查询营业部和偏线代理信息.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-28 上午11:31:38
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		long totalCount=commonSaleDeptAndOuterBranchService.countReocrd(vo.getDto());
		if(totalCount>0){
			List<CommonSaleDeptAndOuterBranchDto> commonSaleDeptAndOuterBranchList= commonSaleDeptAndOuterBranchService.searchSaleDeptAndOuterBranchByCondition(vo.getDto(), start, limit);
			vo.setSaleDeptAndOuterBranchList(commonSaleDeptAndOuterBranchList);
		}
		
		setTotalCount(totalCount);
		return returnSuccess();
	}
	public void setCommonSaleDeptAndOuterBranchService(
			ICommonSaleDeptAndOuterBranchService commonSaleDeptAndOuterBranchService) {
		this.commonSaleDeptAndOuterBranchService = commonSaleDeptAndOuterBranchService;
	}
	public CommonSaleDeptAndOuterBranchVo getVo() {
		return vo;
	}
	public void setVo(CommonSaleDeptAndOuterBranchVo vo) {
		this.vo = vo;
	}

}
