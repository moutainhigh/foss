
package com.deppon.foss.module.transfer.unloadgap.server.job;

import java.text.SimpleDateFormat;
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
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;

/**
 * 生成任务车辆job
 * @author dp-duyi
 * @date 2012-11-7 下午4:51:25
 */
public class UpdateTransportPathByHandOverBill  extends GridJob {
	private final static Logger LOGGER = LoggerFactory.getLogger(UpdateTransportPathByHandOverBill.class);
	
	/** 
	 * 生成任务车辆job
	 * @author dp-duyi
	 * @date 2012-11-7 下午4:51:55
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.trace("************************ 开始执行交接单更新走货路径JOB ************************");
		ITruckTaskService truckTaskService = getBean("truckTaskService", ITruckTaskService.class);
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.UPDATE_TRANSPORT_PATH, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			Date queryBeginTime;
			Date bizJobEndTime;
			try{
				queryBeginTime = new Date(jobProcess.getBizStartTime().getTime()-5*60*1000);
			}catch (Exception e) {
				queryBeginTime = jobProcess.getBizStartTime();
			}
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"); 
			LOGGER.error("交接单更新走货路径时间:"+df.format(queryBeginTime)+"-"+df.format(scheduledFireTime));
			bizJobEndTime = truckTaskService.updateTransportPath(queryBeginTime, scheduledFireTime,threadNo,threadCount);
			
			jobEndTime = Calendar.getInstance().getTime();
//			3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(bizJobEndTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("CreateTruckTaskJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UPDATE_TRANSPORT_PATH.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UPDATE_TRANSPORT_PATH.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.trace("************************ 结束交接单更新走货路径JOB ************************");
	}
}