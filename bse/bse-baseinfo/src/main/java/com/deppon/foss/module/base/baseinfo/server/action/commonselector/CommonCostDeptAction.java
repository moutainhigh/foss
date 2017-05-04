/*******************************************************************************
 * Copyright 2014 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonCostDeptAction.java
 * 
 * FILE NAME        	: CommonCostDeptAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCostCenterDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonCostDeptVo;

/**
 * 公共组件--查询成本中心部门.
 * @author foss-WeiXing
 * @date 2014-07-29 下午2:42:10
 */
public class CommonCostDeptAction extends AbstractAction implements IQueryAction {
 
	private static final long serialVersionUID = 1753080455518422617L;
	  
	/**
	 * 成本中心部门Vo  
	 */
	private CommonCostDeptVo commonCostDeptVo=new CommonCostDeptVo();
	 
	/**
	 * 成本中心部门Service
	 */
	private ICommonCostCenterDeptService commonCostCenterDeptService;

	/**
	 * 查询成本中心部门.
	 * @author foss-WeiXing
	 * @param 
	 * @date 2014-07-29 下午2:42:10
	 * @return 
	 */
	@Override
	public String query() { 
		long totalCount=commonCostCenterDeptService.countCostDeptByCodition(commonCostDeptVo.getCostCenterDeptEntity());
		if(totalCount > 0){
			List<CostCenterDeptEntity> allCostDeptList=commonCostCenterDeptService.queryCostDeptByCondition(commonCostDeptVo.getCostCenterDeptEntity(), limit, start);
			commonCostDeptVo.setCommonCostDeptList(allCostDeptList);
		} 
		setTotalCount(totalCount);
		return returnSuccess();
	}
	
	public void setCommonCostDeptVo(CommonCostDeptVo commonCostDeptVo) {
		this.commonCostDeptVo = commonCostDeptVo;
	}

	public CommonCostDeptVo getCommonCostDeptVo() {
		return commonCostDeptVo;
	}

	public void setCommonCostCenterDeptService(
			ICommonCostCenterDeptService commonCostCenterDeptService) {
		this.commonCostCenterDeptService = commonCostCenterDeptService;
	}

	

}
