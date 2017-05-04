package com.deppon.foss.module.transfer.partialline.job;

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
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.partialline.api.server.service.IAutoCheckDpjzSignInService;

public class AutoCheckDpjzSignInJob extends BaseStateFulJob{
	
private static final Logger LOGGER = LoggerFactory.getLogger(AutoCheckDpjzSignInJob.class);
	
	private static final String EXCEPTION_REMARK = "自动审核德邦家装送装签收信息任务执行失败";

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("************************ 开始###执行德邦家装送装签收信息，自动审核 JOB ************************");

		//任务执行实体
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		//自动审核service
		IAutoCheckDpjzSignInService autoCheckDpjzSignInService = getBean("autoCheckDpjzSignInService", IAutoCheckDpjzSignInService.class);
		//提供中转模块中的通用服务
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		//任务执行开始、结束时间
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;

		try {
			//获取
			Date scheduledFireTime = context.getScheduledFireTime();
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
		
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.AUTOCHECK_DPJZ_JOB, scheduledFireTime, threadNo, threadCount);

			//调用具体服务
			autoCheckDpjzSignInService.doAutoCheckSignInInfo();

			//初始化开始时间
			jobStartTime = Calendar.getInstance().getTime();
			//初始化结束时间
			jobEndTime = Calendar.getInstance().getTime();
			// 更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			// 记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.AUTO_CHECK_DPJZMSG.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.AUTO_CHECK_DPJZMSG.getBizCode());
			jobProcessLogEntity.setRemark(EXCEPTION_REMARK);
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
	}
}
