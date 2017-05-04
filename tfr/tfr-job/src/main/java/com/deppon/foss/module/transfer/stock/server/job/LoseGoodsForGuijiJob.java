package com.deppon.foss.module.transfer.stock.server.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;


/**
* @description 在丢货小组超过28天的运单
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年5月8日 下午3:22:54
*/
public class LoseGoodsForGuijiJob extends GridJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoseGoodsForGuijiJob.class);

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.debug("************************ 开始执行在丢货改善小组超期  JOB ************************");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IStockService stockService = getBean("stockService", IStockService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.LOSEGOODS_FORGUIJI28, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			stockService.dayLoseGoodsForGuiji28();
			
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
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOSEGOODS_FORGUIJI28.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOSEGOODS_FORGUIJI28.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		LOGGER.debug("************************ 结束执行在丢货改善小组超期  JOB ************************");

	}

}
