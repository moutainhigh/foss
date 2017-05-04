package com.deppon.foss.module.transfer.overweighttoqms.server.job;

import java.util.Calendar;
import java.util.Date;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService;

public class OverWeightToQMSJob extends BaseStateFulJob {
	/**
	 * 将计泡机传过来的数据符合超重超方添加的信息同步至QMS
	 * @author 268084
	 * 
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.trace("************************ 开始执行超方超重上报QMS报告 JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IBCMachSortScanService bCMachSortScanService = getBean("bCMachSortScanService", IBCMachSortScanService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.OVERWEIGHT_VOLUMN_TOQMS, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务开始时间
			jobStartTime = Calendar.getInstance().getTime();
			 //2.1、处理超方超重的运单明细致QMS
			 //2.2、上报完成(成功后将存在jobToDo中的相应数据给删除)
			LOGGER.info("开始执行上报超方超重......");
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+new Date()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			bCMachSortScanService.executeoverWeightToQMS(jobProcess.getBizEndTime(),scheduledFireTime);
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+new Date()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			LOGGER.info("上报超方超重结束.........");
			jobEndTime = Calendar.getInstance().getTime();
			//3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("overweightToQMS error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			//记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.OVERWEIGHT_VOLUMN_TOQMS.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.OVERWEIGHT_VOLUMN_TOQMS.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.trace("************************ 结束执行超方超重上报QMS报告 JOB ************************");
		
	}

}
