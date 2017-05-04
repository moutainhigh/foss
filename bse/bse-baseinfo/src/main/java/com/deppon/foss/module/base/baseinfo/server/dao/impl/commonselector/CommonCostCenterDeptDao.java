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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonCostCenterDeptDao.java
 * 
 * FILE NAME        	: CommonCostCenterDeptDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonCostCenterDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *公共选择器--成本中心部门Dao
 * @author foss-WeiXing
 * @date 2014-07-29 上午10:28:02
 */
public class CommonCostCenterDeptDao extends SqlSessionDaoSupport implements ICommonCostCenterDeptDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonCostCenterDept.";
	/**
	 * 根据条件查询成本中心部门.
	 * @author foss-WeiXing
	 * @param 
	 * @date 2014-07-29 上午10:28:02
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CostCenterDeptEntity> queryCostDeptByCondition(CostCenterDeptEntity entity,
			int limit, int start) {
		entity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryCostDeptByCondition",entity, rowBounds);
	}
	/**
	 * 根据条件查询成本中心部门总条数.
	 * @author foss-WeiXing
	 * @param 
	 * @date 2014-07-29 上午10:28:02
	 * @return 
	 */
	@Override
	public Long queryRecordCount(CostCenterDeptEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryRecordCount", entity);
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
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACE+"insert",entity);
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
		
		return this.getSqlSession().update(NAMESPACE+"update",entity);
	}
	
	/**
	 * 根据部门code验证该部门信息是否已存在于FOSS数据库
	 * @author 132599-FOSS-ShenWeiHua
	 * @param deptCode
	 * @date 2014-08-08 下午5:09:02
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean queryCostCenterDeptInfoByDeptCode(String deptCode,String simpleCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptCode", deptCode);
		map.put("simpleCode", simpleCode);
		map.put("active", null);
		List<CostCenterDeptEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryCostCenterDeptInfoByDeptCode", map);
		return CollectionUtils.isNotEmpty(list);
	}

}
