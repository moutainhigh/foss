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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonCostCenterDeptService.java
 * 
 * FILE NAME        	: CommonCostCenterDeptService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonCostCenterDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCostCenterDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;

/**
 * 公共查询选择器--成本中心部门service.
 * 
 * @author foss-WeiXing
 * @date 2014-07-29 上午10:28:02
 */
public class CommonCostCenterDeptService implements ICommonCostCenterDeptService {

	/**
	 * 成本中心部门Dao
	 */
	private ICommonCostCenterDeptDao commonCostCenterDeptDao;

	/**
	 * 根据条件查询成本中心部门.
	 * 
	 * @author foss-WeiXing
	 * @param  entity:查询条件
	 * @date 2014-07-29 上午10:28:02
	 * @return List<CostCenterDeptEntity>
	 */
	@Override
	public List<CostCenterDeptEntity> queryCostDeptByCondition(CostCenterDeptEntity entity,
			int limit, int start) {
		
		return commonCostCenterDeptDao.queryCostDeptByCondition(entity, limit, start);
	}

	/**
	 * 根据条件查询接成本中心部门总条数.
	 * 
	 * @author foss-WeiXing
	 * @param entity:查询条件
	 * @date 2014-07-29 上午10:28:02
	 * @return long:成本中心部门信息总数
	 */
	@Override
	public Long countCostDeptByCodition(CostCenterDeptEntity entity) {
		
		return commonCostCenterDeptDao.queryRecordCount(entity);
	}
	
	/**
	 * 把从财务7.0同步过来的成本中心部门数据插入到FOSS表内
	 * @author ShenWeiHua
	 * @param entity
	 * @return
	 * @date 2014-08-08 上午9:28:02
	 */
	@Override
	public int insertCostCenterDeptInfo(CostCenterDeptEntity entity) {
		
		return commonCostCenterDeptDao.insertCostCenterDeptInfo(entity);
	}
	
	/**
	 * 更新从财务7.0同步过来的成本中心数据
	 * @param entity
	 * @author ShenWeiHua
	 * @return
	 * @date 2014-08-08 上午9:29:02
	 */
	@Override
	public int updateCostCenterDeptInfo(CostCenterDeptEntity entity) {
		
		return commonCostCenterDeptDao.updateCostCenterDeptInfo(entity);
	}
	
	/**
	 * 根据部门code验证该部门信息是否已存在于FOSS数据库
	 * @author 132599-FOSS-ShenWeiHua
	 * @param deptCode
	 * @date 2014-08-08 下午5:09:02
	 * @return
	 */
	@Override
	public boolean queryCostCenterDeptInfoByDeptCode(String deptCode,String simpleCode) {
		
		return commonCostCenterDeptDao.queryCostCenterDeptInfoByDeptCode(deptCode,simpleCode);
	}

	public void setCommonCostCenterDeptDao(
			ICommonCostCenterDeptDao commonCostCenterDeptDao) {
		this.commonCostCenterDeptDao = commonCostCenterDeptDao;
	}
	
	
	

}
