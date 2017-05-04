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
 * PROJECT NAME	: bse-job
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/message/server/jobs/CreateInstationMsgJob.java
 * 
 * FILE NAME        	: CreateInstationMsgJob.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.message.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICallProcedureService;

/**
 * 查询组织信息，包含组织的所有上级层级 将组织增量信息添加到bse.t_bas_mv_org_layer表中
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-4-3 上午9:17:36
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-4-3 上午9:17:36
 * @since
 * @version
 */
public class CallOrgProcureJob extends GridJob implements StatefulJob {
    
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CallOrgProcureJob.class);

    @Override
    protected void doExecute(JobExecutionContext context)
	    throws JobExecutionException {

	try {
	    LOGGER.info("调用组织增量信息存储过程处理开始...");

	    ICallProcedureService callProcedureService = getBean("callProcedureService",
		    ICallProcedureService.class);
	    callProcedureService.callOrgProcedure();
	    LOGGER.info("调用组织增量信息存储过程处理结束...");
	} catch (Exception e) {
	    LOGGER.error("调用组织增量信息存储过程出错,存储过程名：PROC_BSE_MV_ORG_LAYER", e);
	    throw new JobExecutionException(e);
	}

    }

}
