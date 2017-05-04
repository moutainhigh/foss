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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonCostDeptVo.java
 * 
 * FILE NAME        	: CommonCostDeptVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;

/**
 * 公共选择器--成本中心部门VO
 * 
 * @author foss-WeiXing
 * @date 2014-07-29 下午2:42:10
 */
public class CommonCostDeptVo implements Serializable {

	private static final long serialVersionUID = -4229496524447906306L;

	/**
	 * 成本中心部门Entity
	 */
	private CostCenterDeptEntity costCenterDeptEntity;

	/**
	 * 成本中心部门列表
	 */
	private List<CostCenterDeptEntity> commonCostDeptList;


	public CostCenterDeptEntity getCostCenterDeptEntity() {
		return costCenterDeptEntity;
	}

	public void setCostCenterDeptEntity(CostCenterDeptEntity costCenterDeptEntity) {
		this.costCenterDeptEntity = costCenterDeptEntity;
	}

	public List<CostCenterDeptEntity> getCommonCostDeptList() {
		return commonCostDeptList;
	}

	public void setCommonCostDeptList(List<CostCenterDeptEntity> commonCostDeptList) {
		this.commonCostDeptList = commonCostDeptList;
	}
	
}
