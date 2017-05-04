package com.deppon.foss.module.transfer.platform.job.forkliftGoodsEfficiency;

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
import com.deppon.foss.module.transfer.platform.api.server.service.IForkliftGoodsEfficiencyService;
import com.deppon.foss.module.transfer.platform.job.BaseStateFulJob;

public class ForkliftGoodsEfficiencyJob extends BaseStateFulJob{


private static final Logger LOGGER = LoggerFactory.getLogger(ForkliftGoodsEfficiencyJob.class);
	
	/**
	 * 每日凌晨2点执行，统计前一天00点到当前00点时间内，托盘绑定效率信息
	 * 1、按配置时间执行JOB 不用关心上次执行是否成功
	 * 2、更新此JOB的执行时间和状态
	 */

	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.debug("************************ 开始执行前一天电叉分货效率统计JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IForkliftGoodsEfficiencyService forkliftGoodsEfficiencyService = getBean("forkliftGoodsEfficiencyService", IForkliftGoodsEfficiencyService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.FORKLIFT_GOODS_EFFICIENCY, scheduledFireTime, threadNo, threadCount);
			//2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			//2.1、执行统计主方法,计算电叉分货效率
			forkliftGoodsEfficiencyService.calculateForkliftGoodsEfficiency();
			jobEndTime = Calendar.getInstance().getTime();
			//更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("任务执行失败！",e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			//记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.FORKLIFT_GOODS_EFFICIENCY.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.FORKLIFT_GOODS_EFFICIENCY.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.debug("************************ 结束执行前一天电叉分货效率统计JOB ************************");
	}
	
}
