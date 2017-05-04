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
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IAuditInviteApplyDao.java
 * 
 *  FILE NAME     :IAuditInviteApplyDao.java
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
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.dao
 * FILE    NAME: IAuditInviteApplyDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditInviteApplyEntity;

/**
 * 外请约车审核log Dao
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:47:47
 */
public interface IAuditInviteApplyDao {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 受影响的行数
	 */
	int addAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity 
	 * @return 受影响的行数
	 */
	int updateAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity);

	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return java.util.List
	 */
	List<AuditInviteApplyEntity> queryAuditInviteApplyList(AuditInviteApplyEntity auditInviteApplyEntity);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	List<AuditInviteApplyEntity> queryAuditInviteApplyForPage(AuditInviteApplyEntity auditInviteApplyEntity, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 行数
	 */
	Long queryCount(AuditInviteApplyEntity auditInviteApplyEntity);
	
	/**
	 * 根据约车编号查询受理序号
	 * @param inviteNo
	 * @return int auditNo
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午3:52:38
	 */
	int queryInviteInviteAuditNo(String inviteNo);
	
	/**
	* @description 通过约车编号更新
	* @param auditInviteApplyEntity
	* @return	int
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年3月19日 上午10:52:33
	*/
	int updateAuditInviteApplyByInvite(AuditInviteApplyEntity auditInviteApplyEntity);

}