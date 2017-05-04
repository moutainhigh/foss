/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/AuditOrderApplyDao.java
 * 
 *  FILE NAME     :AuditOrderApplyDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAuditOrderApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;
import com.deppon.foss.util.UUIDUtils;

 /**
 * 审核约车数据访问
 * @author 104306-foss-wangLong
 * @date 2012-11-22 下午3:32:32
 */
public class AuditOrderApplyDao extends iBatis3DaoImpl implements IAuditOrderApplyDao {
	
	private static final String NAMESPACE = "foss.scheduling.AuditOrderApply.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return 受影响的行数
	 */
	public int addAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity) {
		auditOrderApplyEntity.setId(UUIDUtils.getUUID());
		String statement = NAMESPACE + "addAuditOrderApply";
		return getSqlSession().insert(statement, auditOrderApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity 
	 * @return 受影响的行数
	 */
	public int updateAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity) {
		String statement = NAMESPACE + "updateAuditOrderApply";
		return getSqlSession().update(statement, auditOrderApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<AuditOrderApplyEntity> queryAuditOrderApplyList(AuditOrderApplyEntity auditOrderApplyEntity) {
		String statement = NAMESPACE + "queryAuditOrderApplyList";
		return getSqlSession().selectList(statement, auditOrderApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<AuditOrderApplyEntity> queryAuditOrderApplyForPage(AuditOrderApplyEntity auditOrderApplyEntity, int start, int pageSize) {
		String statement = NAMESPACE + "queryAuditOrderApplyList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, auditOrderApplyEntity, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return 行数
	 */
	public Long queryCount(AuditOrderApplyEntity  auditOrderApplyEntity) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, auditOrderApplyEntity);
	}
	
	/**
	 * 查询受理序号 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午8:07:53
	 * @return 
	 */
	public Integer queryAuditNo(String orderNo) {
		String statement = NAMESPACE + "queryAuditNo";
		return (Integer) getSqlSession().selectOne(statement, orderNo);
	}
}