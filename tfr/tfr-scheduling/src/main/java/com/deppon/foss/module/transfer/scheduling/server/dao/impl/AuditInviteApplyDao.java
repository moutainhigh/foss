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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/AuditInviteApplyDao.java
 * 
 *  FILE NAME     :AuditInviteApplyDao.java
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
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.dao.impl
 * FILE    NAME: AuditInviteApplyDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAuditInviteApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditInviteApplyEntity;

 /**
 * 外请约车受理log
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:47:47
 */
public class AuditInviteApplyDao extends iBatis3DaoImpl implements IAuditInviteApplyDao {
	
	private static final String NAMESPACE = "foss.scheduling.AuditInviteApply.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 受影响的行数
	 */
	public int addAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity) {
		String statement = NAMESPACE + "addAuditInviteApply";
		return getSqlSession().insert(statement, auditInviteApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity 
	 * @return 受影响的行数
	 */
	public int updateAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity) {
		String statement = NAMESPACE + "updateAuditInviteApply";
		return getSqlSession().update(statement, auditInviteApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<AuditInviteApplyEntity> queryAuditInviteApplyList(AuditInviteApplyEntity auditInviteApplyEntity) {
		String statement = NAMESPACE + "queryAuditInviteApplyList";
		return getSqlSession().selectList(statement, auditInviteApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<AuditInviteApplyEntity> queryAuditInviteApplyForPage(AuditInviteApplyEntity auditInviteApplyEntity, int start, int pageSize) {
		String statement = NAMESPACE + "queryAuditInviteApplyList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, auditInviteApplyEntity, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 行数
	 */
	public Long queryCount(AuditInviteApplyEntity  auditInviteApplyEntity) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, auditInviteApplyEntity);
	}
	
	/**
	 * 根据约车编号查询受理序号
	 * @param inviteNo
	 * @return int auditNo
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午3:52:38
	 */
	public int queryInviteInviteAuditNo(String inviteNo){
		String statement = NAMESPACE + "queryInviteInviteAuditNo";
		return (Integer)getSqlSession().selectOne(statement,inviteNo);
	}
	
	public int updateAuditInviteApplyByInvite(AuditInviteApplyEntity auditInviteApplyEntity){
		String statement = NAMESPACE + "updateAuditInviteApplyByInvite";
		return getSqlSession().update(statement, auditInviteApplyEntity);
	}

}