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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/AuditBorrowApplyDao.java
 * 
 *  FILE NAME     :AuditBorrowApplyDao.java
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
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAuditBorrowApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;
import com.deppon.foss.util.UUIDUtils;

 /**
 * 借车logDao
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:39
 */
public class AuditBorrowApplyDao extends iBatis3DaoImpl implements IAuditBorrowApplyDao {
	
	private static final String NAMESPACE = "foss.scheduling.AuditBorrowApply.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return 受影响的行数
	 */
	public int addAuditBorrowApply(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		auditBorrowApplyEntity.setId(UUIDUtils.getUUID());
		String statement = NAMESPACE + "addAuditBorrowApply";
		return getSqlSession().insert(statement, auditBorrowApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity 
	 * @return 受影响的行数
	 */
	public int updateAuditBorrowApply(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		String statement = NAMESPACE + "updateAuditBorrowApply";
		return getSqlSession().update(statement, auditBorrowApplyEntity);
	}

	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<AuditBorrowApplyEntity> queryAuditBorrowApplyList(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		String statement = NAMESPACE + "queryAuditBorrowApplyList";
		return getSqlSession().selectList(statement, auditBorrowApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<AuditBorrowApplyEntity> queryAuditBorrowApplyForPage(AuditBorrowApplyEntity auditBorrowApplyEntity, int start, int pageSize) {
		String statement = NAMESPACE + "queryAuditBorrowApplyList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, auditBorrowApplyEntity, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return 行数
	 */
	public Long queryCount(AuditBorrowApplyEntity  auditBorrowApplyEntity) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, auditBorrowApplyEntity);
	}
	
	/**
	 * 查询受理序号
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:22:11
	 * @param borrowNo 借车单号
	 * @return {@link java.lang.Integer}
	 */
	public int queryAuditNo(String queryAuditNo) {
		String statement = NAMESPACE + "queryAuditNo";
		return (Integer) getSqlSession().selectOne(statement, queryAuditNo);
	}
}