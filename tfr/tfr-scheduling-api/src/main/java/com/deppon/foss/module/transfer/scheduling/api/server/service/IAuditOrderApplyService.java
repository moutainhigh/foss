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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IAuditOrderApplyService.java
 * 
 *  FILE NAME     :IAuditOrderApplyService.java
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
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;

/**
 * 审核约车服务
 * @author 104306-foss-wangLong
 * @date 2012-11-22 下午3:32:32
 */
public interface IAuditOrderApplyService extends IService {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return 受影响的行数 
	 */
	int addAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return 受影响的行数 
	 */
	int updateAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return java.util.List
	 */
	List<AuditOrderApplyEntity> queryAuditOrderApplyList(AuditOrderApplyEntity auditOrderApplyEntity);
	
	/**
	 * 根据约车单号查询 审核记录
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param orderNo  约车单号
	 * @return java.util.List
	 */
	List<AuditOrderApplyEntity> queryAuditOrderApplyListByOrderNo(String orderNo);

	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	List<AuditOrderApplyEntity> queryAuditOrderApplyForPage(AuditOrderApplyEntity auditOrderApplyEntity, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return 
	 */
	Long queryCount(AuditOrderApplyEntity auditOrderApplyEntity);

	/**
	 * 退回约车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午4:49:19
	 * @param auditOrderApplyEntity
	 * @param orderId 约车申请id
	 */
	void doReturnOrderVehicleApply(AuditOrderApplyEntity auditOrderApplyEntity, String orderId);
	
	/**
	 * 查询受理序号 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午8:07:53
	 * @param orderNo
	 * @return 
	 */
	Integer queryAuditNo(String orderNo);

	/**
	 * 拒绝约车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午10:38:24
	 * @param auditOrderApplyEntity
	 * @param orderId
	 */
	void doDismissOrderVehicleApply(AuditOrderApplyEntity auditOrderApplyEntity, String orderId);

	/**
	 * 受理约车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-11-24 上午2:06:55
	 * @param auditOrderApplyEntity
	 */
	void doAcceptedOrderVehicleApply(AuditOrderApplyEntity auditOrderApplyEntity);
}