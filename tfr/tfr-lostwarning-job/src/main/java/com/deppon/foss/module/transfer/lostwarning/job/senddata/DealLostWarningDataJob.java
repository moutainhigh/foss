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
import com.deppon.foss.module.transfer.lostwarning.server.service.impl.UploadWarningDataService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;

/**
 * 处理丢货预警数据
 * 
 * 项目名称：tfr-lostwarning-job
 * 
 * 类名称：DealLostWarningDataJob
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-12 下午3:48:25
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class DealLostWarningDataJob extends BaseStateFulJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DealLostWarningDataJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext paramJobExecutionContext)
			throws JobExecutionException {
		LOGGER.trace("************************ 开始执行丢货预警数据上报JOB ************************");

		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		//丢货预警上报模块
		UploadWarningDataService warningService = getBean("uploadWarningDataService", UploadWarningDataService.class);
		//提供中转模块中的通用服务 
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		//库存模块
		IStockService stockService = getBean("stockService",IStockService.class);
		try{
			//获取线程号 
			threadNo = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadNo")).intValue();
			//获取线程数
			threadCount = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = paramJobExecutionContext.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.PUSH_LOSTWARNING_QMS_JOB, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务
			
			//初始化开始时间
			jobStartTime = Calendar.getInstance().getTime();
			LOGGER.info("丢货预警数据上报开始");
			/*** 执行预警数据上报业务 ***/
			warningService.dealWarningData(stockService,tfrCommonService);
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
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_LOSTWARNING_QMS_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_LOSTWARNING_QMS_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}

		LOGGER.trace("************************ 结束丢货预警数据上报JOB ************************");
		
	}

}
