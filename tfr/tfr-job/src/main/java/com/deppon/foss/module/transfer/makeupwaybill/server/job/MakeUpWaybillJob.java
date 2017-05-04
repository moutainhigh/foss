/**
 * 
 */
package com.deppon.foss.module.transfer.makeupwaybill.server.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IMakeUpWaybillService;

/**
 * @author niuly
 * @date 2014-02-11 14:16:30
 * @funciton 更新重量或体积
 */
public class MakeUpWaybillJob extends GridJob implements StatefulJob {

	private final static Logger LOGGER = LoggerFactory.getLogger(MakeUpWaybillJob.class);
	
	/**
	 * @author niuly
	 * @date 2014-02-11 14:16:30
	 * @funciton 运单补录或PDA更新运单时，若运单的重量或体积发生变化，则更新交接单、配载单、装卸车工作量管理中该运单的重量或体积
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("---运单补录更新交接单、配载单、装卸车工作量JOB开始---");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IMakeUpWaybillService makeUpWaybillServer = this.getBean("makeUpWaybillService", IMakeUpWaybillService.class);
		ITfrCommonService tfrCommonService = this.getBean("tfrCommonService", ITfrCommonService.class);
		try{
			threadNo = Integer.valueOf((String)context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String)context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.MAKEUP_WAYBILL_JOB, scheduledFireTime, threadNo, threadCount);
			//job开始执行时间
			jobStartTime = Calendar.getInstance().getTime();
			//执行任务
			makeUpWaybillServer.doMakeUpWaybillInfo(threadNo,threadCount);
			//job结束执行时间
			jobEndTime = Calendar.getInstance().getTime();
			
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			
		}catch(Exception e) {
			LOGGER.error("StReportJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.MAKEUP_WAYBILL_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.MAKEUP_WAYBILL_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		LOGGER.info("---运单补录更新交接单、配载单、装卸车工作量JOB结束---");
	}
}
