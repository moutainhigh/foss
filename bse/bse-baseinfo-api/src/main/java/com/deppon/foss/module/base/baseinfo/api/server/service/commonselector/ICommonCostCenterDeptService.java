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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonCostCenterDeptService.java
 * 
 * FILE NAME        	: ICommonCostCenterDeptService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonCostCenterDeptService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;

/**
 * 公共组件 --成本中心部门查询service接口定义
 * @author WeiXing
 * @date 2014-07-29 上午10:28:02
 */
public interface ICommonCostCenterDeptService {
	/**
	 * 
	 * 根据条件查询符合提交的成本中心部门信息
	 * @param entity:查询条件
	 * @author WeiXing
	 * @return List<CostCenterDeptEntity>
	 * @date 2014-07-29 上午10:28:02
	 */
	List<CostCenterDeptEntity> queryCostDeptByCondition(CostCenterDeptEntity entity, int limit,int start);
	/**
	 * 
	 * 根据条件查询符合提交的成本中心部门信息的总条数
	 * @param Entity:查询条件
	 * @author WeiXing
	 * @return long:成本中心部门信息总数
	 * @date 2014-07-29 上午10:28:02
	 */
	Long countCostDeptByCodition(CostCenterDeptEntity entity);
	
	/**
	 * 把从财务7.0同步过来的成本中心部门数据插入到FOSS表内
	 * @author ShenWeiHua
	 * @param entity
	 * @return
	 * @date 2014-08-08 上午9:28:02
	 */
	int insertCostCenterDeptInfo(CostCenterDeptEntity entity);
	
	/**
	 * 更新从财务7.0同步过来的成本中心数据
	 * @param entity
	 * @author ShenWeiHua
	 * @return
	 * @date 2014-08-08 上午9:29:02
	 */
	int updateCostCenterDeptInfo(CostCenterDeptEntity entity);
	
	/**
	 * 根据部门code验证该部门信息是否已存在于FOSS数据库
	 * @author 132599-FOSS-ShenWeiHua
	 * @param deptCode
	 * @date 2014-08-08 下午5:09:02
	 * @return
	 */
	boolean queryCostCenterDeptInfoByDeptCode(String deptCode,String simpleCode);
}
