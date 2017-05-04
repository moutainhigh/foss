/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILeasedDriverWhitelistAuditService.java
 * 
 * FILE NAME        	: ILeasedDriverWhitelistAuditService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverWhitelistAuditException;

/**
 * 用来操作交互“外请司机白名单”审核（同意、拒绝）的数据库对应数据访问Service接口：SUC-750
 * <p>
 * 需要版本控制
 * </p>
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 上午9:28:52
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 上午9:28:52
 * @since
 * @version
 */
public interface ILeasedDriverWhitelistAuditService extends IService {

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“外请司机白名单”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * <p>
	 * 只包括当前申请不为NULL的数据（申请入库、申请可用、申请不可用）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午3:49:48
	 * @param whitelistAudit
	 *            以“外请司机白名单”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 分页的Action和Service通讯封装对象
	 * @see
	 */
	PaginationDto queryUnauditedLeasedDriverWhitelistsBySelectiveCondition(
			WhitelistAuditEntity whitelistAudit, int offset, int limit)
			throws LeasedDriverWhitelistAuditException;

	/**
	 * <p>
	 * 同意一个“外请司机白名单”的申请
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午3:49:49
	 * @param ids
	 *            记录唯一标识集合
	 * @param auditNotes
	 *            审核意见备注
	 * @param modifyUser
	 *            审核人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int auditArgeeLeasedDriverWhitelists(List<String> ids, String auditNotes,
			String modifyUser) throws LeasedDriverWhitelistAuditException;

	/**
	 * <p>
	 * 拒绝一个“外请司机白名单”的申请
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-25 下午6:21:49
	 * @param ids
	 *            记录唯一标识集合
	 * @param auditNotes
	 *            审核意见备注
	 * @param modifyUser
	 *            审核人
	 * @return 1：成功；-1：失败
	 * @throws LeasedDriverWhitelistAuditException
	 * @see
	 */
	int auditDeclineLeasedDriverWhitelists(List<String> ids, String auditNotes,
			String modifyUser) throws LeasedDriverWhitelistAuditException;
}
