package com.deppon.foss.module.transfer.platform.job.platformOpeEffi;

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
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformOpeEffiService;
import com.deppon.foss.module.transfer.platform.job.BaseStateFulJob;

/**
 * 
* @ClassName: PlatformOpeEffiJob 
* @Description: 每日凌晨一点统计前一天月台操作效率
* @author 105944
* @date 2015-3-24 下午2:18:28
 */
public class PlatformOpeEffiJob extends BaseStateFulJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformOpeEffiJob.class);
	/**
	 * 每日凌晨一点统计前一天月台操作效率
	 */
	@Override
	protected void doExecute(JobExecutionContext paramJobExecutionContext)
			throws JobExecutionException {
		LOGGER.debug("************************ 开始执行前一天月台操作效率统计JOB ************************");
		//job执行情况实体
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		//任务开始时间
		Date jobStartTime = null;
		//任务结束时间
		Date jobEndTime = null;
		//线程号
		int threadNo = 0;
		//线程数
		int threadCount = 1;
		//获取月台操作效率service
		IPlatformOpeEffiService platformOpeEffiService = getBean("platformOpeEffiService", IPlatformOpeEffiService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try {
			threadNo = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = paramJobExecutionContext.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.PLATFORM_EFFICIENCY, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			platformOpeEffiService.statisticPlatformOpeEffi();
			jobEndTime = Calendar.getInstance().getTime();
//			更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);                           
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("任务执行失败！",e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PLATFORM_EFFICIENCY.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PLATFORM_EFFICIENCY.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.debug("************************ 结束执行前一天月台操作效率统计JOB ************************");
		
	}

}