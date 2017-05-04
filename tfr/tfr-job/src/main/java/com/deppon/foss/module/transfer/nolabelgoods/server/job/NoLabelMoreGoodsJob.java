/**
 * @author foss 257200
 * 2015-6-15
 * 257220
 */
package com.deppon.foss.module.transfer.nolabelgoods.server.job;

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
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.INoLabelGoodsService;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;

/**
 * @author 257220
 *
 */
public class NoLabelMoreGoodsJob  extends BaseStateFulJob{
	
	private static Logger LOGGER = LoggerFactory.getLogger(NoLabelMoreGoodsJob.class);
	private static String EXCEPTION_REMARK = "任务执行失败！";
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.job.BaseStateFulJob#doExecute(org.quartz.JobExecutionContext)
	 */
	
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("---无标签多货上报JOB开始---");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		INoLabelGoodsService noLabelMoreGoodsService = this.getBean("noLabelGoodsService", INoLabelGoodsService.class);
		ITfrCommonService tfrCommonService = this.getBean("tfrCommonService", ITfrCommonService.class);
		try{
			threadNo = Integer.valueOf((String)context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String)context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.NO_LABEL_MOREGOODS_JOB, scheduledFireTime, threadNo, threadCount);
			//job开始执行时间
			jobStartTime = Calendar.getInstance().getTime();
			//执行任务
			noLabelMoreGoodsService.reportQmsNoLabelGoods();
			//job结束执行时间
			jobEndTime = Calendar.getInstance().getTime();
			
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			
		}catch(Exception e){
			LOGGER.error("NoLabelMoreGoodsJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.NO_LABEL_MOREGOODS_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.NO_LABEL_MOREGOODS_JOB.getBizCode());
			jobProcessLogEntity.setRemark(EXCEPTION_REMARK);
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
	}
}
