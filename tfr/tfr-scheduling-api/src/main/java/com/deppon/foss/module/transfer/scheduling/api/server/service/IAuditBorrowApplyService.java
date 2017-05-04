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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IAuditBorrowApplyService.java
 * 
 *  FILE NAME     :IAuditBorrowApplyService.java
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
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;

/**
 * 借车审核log Service
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:39
 */
public interface IAuditBorrowApplyService  extends IService {

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
	 * @return 
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

	/**
	 * 退回借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:39:44
	 * @param auditBorrowApplyEntity
	 */
	void doReturnBorrowVehicleApply(AuditBorrowApplyEntity auditBorrowApplyEntity);
	
	/**
	 * 退回借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:39:44
	 * @param auditBorrowApplyList
	 */
	void doReturnBorrowVehicleApply(List<AuditBorrowApplyEntity> auditBorrowApplyList);

	/**
	 * 拒绝借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午6:48:26
	 * @param auditBorrowApplyEntity
	 */
	void doDismissBorrowVehicleApply(AuditBorrowApplyEntity auditBorrowApplyEntity);
	
	/**
	 * 拒绝借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:39:44
	 * @param auditBorrowApplyList
	 */
	void doDismissBorrowVehicleApply(List<AuditBorrowApplyEntity> auditBorrowApplyList);

	/**
	 * 借车审核log
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午12:09:20
	 * @param borrowNo 借车编号 
	 * @return {@link java.util.List}
	 */
	List<AuditBorrowApplyEntity> queryPassBorrowApplyAndAuditBorrowApplyLog(String borrowNo);

	/**
	 * 审核通过
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午6:59:35
	 * @param auditBorrowApplyEntity
	 * @return 
	 */
	void doAcceptedBorrowVehicleApply(AuditBorrowApplyEntity auditBorrowApplyEntity); 
}