/**  
 * Project Name:tfr-job  
 * File Name:PushForWaybillTrackingsJob.java  
 * Package Name:com.deppon.foss.module.transfer.waybilltracking  
 * Date:2015年4月15日下午4:11:13  
 *  
 */  
  
package com.deppon.foss.module.transfer.trucktask.server.job;  

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.trackings.api.server.service.IPushForWaybillToPTPService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;

/**  
 * ClassName: PushForWaybillTrackingsJob <br/>  
 * Function: 车辆出发到达的正反操作之后，将运单异步存入轨迹表，以便后续推送给快递100. <br/>  
 * date: 2015年4月15日 下午4:11:13 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
   
public class PushForWaybillToPTPJob extends BaseStateFulJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushForWaybillToPTPJob.class);
	
	private static final String EXCEPTION_REMARK = "任务执行失败";
	/**  
	 * 车辆出发到达的正反操作之后，将运单推送到PTP合伙人  
	 * @see com.deppon.foss.module.transfer.job.BaseStateFulJob#doExecute(org.quartz.JobExecutionContext)  
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("************************ 开始###执行异步推送运单号到PTP（到达） JOB ************************");

		//任务执行实体
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		//轨迹存入service                                                  
		IPushForWaybillToPTPService pushForWaybillToPTPService = getBean("pushForWaybillToPTPService", IPushForWaybillToPTPService.class);
		//提供中转模块中的通用服务
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		//任务执行开始、结束时间
		Date jobStartTime = null;
		Date jobEndTime = null;

		try {
			//获取
			Date scheduledFireTime = context.getScheduledFireTime();

			//调用具体服务
			pushForWaybillToPTPService.pushForWaybillTrackings();

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
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_FOR_WAYBILL_PTP_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_FOR_WAYBILL_PTP_JOB.getBizCode());
			jobProcessLogEntity.setRemark(EXCEPTION_REMARK);
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.info("************************ 结束###执行异步推送运单号（到达） JOB  ************************");
		
	}

}