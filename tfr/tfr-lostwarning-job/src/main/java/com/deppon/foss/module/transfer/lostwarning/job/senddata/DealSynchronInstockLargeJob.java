package com.deppon.foss.module.transfer.lostwarning.job.senddata;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.lostwarning.job.base.BaseStateFulJob;
import com.deppon.foss.module.transfer.lostwarning.server.service.impl.FindLostGoodsService;

/**
 * 同步入库表到新建的入库同步信息表中
 * 
 * 项目名称：tfr-lostwarning-job
 * 
 * 类名称：DealSynchronInstockLargeJob
 * 
 * 创建人：laq 336785
 * 
 * 创建时间：2017-02-28 
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class DealSynchronInstockLargeJob extends BaseStateFulJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FindLostWarningDataJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext paramJobExecutionContext)
			throws JobExecutionException {
		LOGGER.trace("************************ 开始执行同步入库表到入库同步信息表中JOB ************************");

		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		//丢货预警找到上报模块
		FindLostGoodsService findLostService = getBean("findLostGoodsService", FindLostGoodsService.class);
		//提供中转模块中的通用服务 
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try{
			//获取线程号 
			threadNo = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadNo")).intValue();
			//获取线程数
			threadCount = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = paramJobExecutionContext.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.PUSH_IN_STOCK_DATE_JOB, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务
			
			//初始化开始时间
			jobStartTime = Calendar.getInstance().getTime();
			LOGGER.info("同步入库表到新建临时表job开始");
			/*** 执行丢货找到数据上报业务 ***/
			findLostService.dealInstockSynchron();
			//初始化结束时间
			jobEndTime = new Date();

			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("任务执行失败", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			//记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_IN_STOCK_DATE_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_IN_STOCK_DATE_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}

		LOGGER.trace("************************ 结束执行同步入库表数据JOB ************************");
		
	}

}
