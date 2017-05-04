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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IAuditBorrowApplyDao.java
 * 
 *  FILE NAME     :IAuditBorrowApplyDao.java
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
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;

/**
 * 借车审核log Dao
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:39
 */
public interface IAuditBorrowApplyDao {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return 受影响的行数
	 */
	int addAuditBorrowApply(AuditBorrowApplyEntity auditBorrowApplyEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity 
	 * @return 受影响的行数
	 */
	int updateAuditBorrowApply(AuditBorrowApplyEntity auditBorrowApplyEntity);

	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return java.util.List
	 */
	List<AuditBorrowApplyEntity> queryAuditBorrowApplyList(AuditBorrowApplyEntity auditBorrowApplyEntity);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	List<AuditBorrowApplyEntity> queryAuditBorrowApplyForPage(AuditBorrowApplyEntity auditBorrowApplyEntity, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return 行数
	 */
	Long queryCount(AuditBorrowApplyEntity auditBorrowApplyEntity);

	/**
	 * 查询受理序号
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:22:11
	 * @param borrowNo 借车单号
	 * @return {@link java.lang.Integer}
	 */
	int queryAuditNo(String borrowNo);
}