package com.deppon.foss.module.transfer.stockchecking.server.job;

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
import com.deppon.foss.module.transfer.stock.api.server.service.IErrorLoseStartingService;



/**
* @description 出发部门在库时长超过3天自动上报OA出发丢货
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年12月22日 上午9:25:46
*/
public class LoseStartingErrorJob  extends BaseStateFulJob {
	//private final static Logger LOGGER = LoggerFactory.getLogger(LoseStartingErrorJob.class);
	/**
	* @description JOB定时执行生成出发丢货上报OA
	 * 1、通过业务规则中定义的处理时间查询某一时间段内需处理的库存时长超过3天的运单
	 * 2、上报OA的出发丢货
	 * 3、更新此JOB的执行时间和状态
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.job.BaseStateFulJob#doExecute(org.quartz.JobExecutionContext)
	* @author 14022-foss-songjie
	* @update 2014年12月22日 上午9:49:19
	* @version V1.0
	*/
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
//		LOGGER.info("************************ 开始执行出发丢货上报 JOB ************************");
//		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
//		Date jobStartTime = null;
//		Date jobEndTime = null;
//		int threadNo = 0;
//		int threadCount = 1;
//		IErrorLoseStartingService errorLoseStartingService = getBean("errorLoseStartingService", IErrorLoseStartingService.class);
//		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
//		try {
//			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
//			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
//			
//			Date scheduledFireTime = context.getScheduledFireTime();
////			1、取出上次成功执行JOB后的业务截止时间
//			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR, scheduledFireTime, threadNo, threadCount);
////			2、执行业务任务：创建清仓任务差异报告及差异明细
//			jobStartTime = Calendar.getInstance().getTime();
//			errorLoseStartingService.execStockOvertime(scheduledFireTime, threadNo, threadCount);
//			
//			jobEndTime = Calendar.getInstance().getTime();
////			3、更新任务完成状态及时间
//			jobProcess.setJobStartTime(jobStartTime);
//			jobProcess.setJobEndTime(jobEndTime);
//			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
//			jobProcess.setBizEndTime(scheduledFireTime);
//			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
//		} catch (Exception e) {
//			LOGGER.error("StReportOAErrorJob error", e);
//			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
////			记录出错日志
//			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
//			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizName());
//			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizCode());
//			jobProcessLogEntity.setRemark("任务执行失败！");
//			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
//			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
//			
//			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
//		} finally{
//			tfrCommonService.updateExecutedJob(jobProcess);
//		}
//		LOGGER.info("************************ 结束执行出发丢货上报 JOB ************************");
	}
}
