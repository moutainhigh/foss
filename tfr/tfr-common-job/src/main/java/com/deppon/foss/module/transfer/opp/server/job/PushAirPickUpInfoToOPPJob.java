package com.deppon.foss.module.transfer.opp.server.job;

import java.util.Calendar;
import java.util.Date;

import com.deppon.foss.module.transfer.airfreight.api.server.service.IPushAirPickUpInfoService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
* @description JOB执行 推送合大票清单数据至OPP系统
* @version 1.0
* @author 269701-foss-lln
* @update 2016年4月27日 上午10:42:36
 */
public class PushAirPickUpInfoToOPPJob extends BaseStateFulJob{
	
	//创建日志
	private static final Logger LOGGER = LoggerFactory.getLogger(PushAirPickUpInfoToOPPJob.class);
	//job错误标示
	private static final String EXCEPTION_REMARK = "自动推送合大票清单信息任务执行失败";
	
	/**
	 * @Description: JOB执行 推送合大票清单数据至OPP系统
	 * @date 2016-04-05 下午3:06:04   
	 * @author 269701 
	 */
	@Override
	protected void doExecute(JobExecutionContext context)throws JobExecutionException {
	LOGGER.info("************************ 开始执行推送合大票清单数据至OPP系统，自动推送JOB ************************");
	//任务执行实体
	TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
	//自动审核service
	IPushAirPickUpInfoService pushAirPickUpInfoService = getBean("pushAirPickUpInfoService", IPushAirPickUpInfoService.class);
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
		
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.AUTOPUSH_TOOPP_JOB, scheduledFireTime, threadNo, threadCount);

			//	AUTOCHECK_DPJZ_JOB("AUTOCHECK_DPJZ_JOB","自动推送合大票清单信息",-30,0,0),
			
			//执行具体的业务
			pushAirPickUpInfoService.doPushAirPickUpInfo();
			
			//初始化开始时间
			jobStartTime = Calendar.getInstance().getTime();
			//初始化结束时间
			jobEndTime = Calendar.getInstance().getTime();
			// 更新任务完成状态及时间
			//job实际执行开始时间
			jobProcess.setJobStartTime(jobStartTime);
			//job实际执行结束时间
			jobProcess.setJobEndTime(jobEndTime);
			//业务开始时间
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			//业务结束时间
			jobProcess.setBizEndTime(scheduledFireTime);
			//执行结果
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
	} catch (Exception e) {
			LOGGER.error(e.getMessage());
			//job执行结果
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			// 记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			
			//AUTO_PUSH_APICKUP_MSG("AUTO_PUSH_APICKUP_MSG", "自动推送合大票清单信息", -60, 1, 1),
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_AIRPICK_OPP.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_AIRPICK_OPP.getBizCode());
			//错误信息
			jobProcessLogEntity.setRemark(EXCEPTION_REMARK);
			//具体错误信息捕获
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
	} finally {
			tfrCommonService.updateExecutedJob(jobProcess);
	}
}
}

	