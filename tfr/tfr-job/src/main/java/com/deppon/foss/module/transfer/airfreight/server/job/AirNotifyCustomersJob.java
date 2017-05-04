package com.deppon.foss.module.transfer.airfreight.server.job;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirNotifyCustomersService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
/**
 * 
 * @author 200968 zwd 2015-9-19
 *
 */
public class AirNotifyCustomersJob extends BaseStateFulJob{

	@Override
	protected void doExecute(JobExecutionContext context)	throws JobExecutionException {
		LOGGER.trace("**********开始执行通过JOB来定时更新空运通知客户表和实际承运信息表的通知状态JOB *********");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IAirNotifyCustomersService airNotifyCustomersService = getBean("airNotifyCustomersService",IAirNotifyCustomersService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try{
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			//1、取出上次成功执行JOB后的业务截止时间   AIR_NOTIFY_CUSTOMERS_JOB
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.AIR_NOTIFY_CUSTOMERS_JOB, scheduledFireTime, threadNo, threadCount);
            //2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			
			// 业务逻辑处理
			airNotifyCustomersService.airNotifyCustomersJobRun();
			jobEndTime = Calendar.getInstance().getTime();
//			3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			
		}catch(Exception e){
			LOGGER.error("AirNotifyCustomersJOB 定时更新空运通知客户表和实际承运信息表的通知状态  error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.AIR_NOTIFY_CUSTOMERS_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.AIR_NOTIFY_CUSTOMERS_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			        
		}finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		LOGGER.trace("********* 结束通过JOB来定时更新空运通知客户表和实际承运信息表的通知状态JOB ***********");

	}
}
