/**   
 * File Name：TransportPathMigrationJob.java   
 *   
 * Version:1.0
 * ：2013-4-27   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.scheduling.server.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Description： 对于已经签收的货物，走货路径需要迁移至备用表
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-27 下午4:33:38
 */

public class TransportPathMigrationJob extends GridJob {

	private static final Logger logger = LoggerFactory.getLogger(TransportPathMigrationJob.class);

	private static final String JOB_FAILURE_REMARK="任务执行失败!";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */

	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		
		logger.trace("************************ 开始执行已经签收的货物走货路径数据迁移JOB ************************");

		//任务执行实体
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		//开始时间
		Date jobStartTime = null;
		//结束时间
		Date jobEndTime = null;
		//线程号
		int threadNo = 0;
		//线程数
		int threadCount = 1;
		//计算&调整走货路径类
		ICalculateTransportPathService calculateTransportPathService = getBean("calculateTransportPathService", ICalculateTransportPathService.class);
		//提供中转模块中的通用服务
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);

		try {
			//获取线程号
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			//获取线程数
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			//获取
			Date scheduledFireTime = context.getScheduledFireTime();

			//取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.MIGRATE_TRANPORT_PATH_DATA_JOB, scheduledFireTime, threadNo, threadCount);

			//初始化开始时间
			jobStartTime = Calendar.getInstance().getTime();
			//迁移走货路径信息(主表以及走货路径详细信息表)
			calculateTransportPathService.migrateTransportPathData();
			//初始化结束时间
			jobEndTime = Calendar.getInstance().getTime();
			// 更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			// 记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.MIGRATE_TRANPORT_PATH_DATA_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.MIGRATE_TRANPORT_PATH_DATA_JOB.getBizCode());
			jobProcessLogEntity.setRemark(JOB_FAILURE_REMARK);
			jobProcessLogEntity.setExceptionInfo(e.getMessage());
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());

			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}

		logger.trace("************************ 结束执行已经签收的货物走货路径数据迁移JOB ************************");
	}

}
