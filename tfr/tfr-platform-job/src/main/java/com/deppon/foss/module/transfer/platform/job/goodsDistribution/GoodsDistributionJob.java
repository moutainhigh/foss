package com.deppon.foss.module.transfer.platform.job.goodsDistribution;

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
import com.deppon.foss.module.transfer.platform.api.server.service.IGoodsDistributionService;
import com.deppon.foss.module.transfer.platform.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.platform.server.service.impl.GoodsDistributionService;

public class GoodsDistributionJob extends BaseStateFulJob{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsDistributionJob.class);

	/** com.deppon.foss.module.transfer.platform.job.goodsDistribution.GoodsDistributionJob
	 * JOB定时执行转运场货量流动分布统计JOB
	 * 1、按配置时间执行JOB 不用关心上次执行是否成功
	 * 2、更新此JOB的执行时间和状态
	 * 每半个小时执行一次
	 * @author 200978
	 * @date 2015-03-10
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
LOGGER.debug("************************ 转运场货量流动分布统计JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IGoodsDistributionService goodsDistributionService = getBean("goodsDistributionService", GoodsDistributionService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.GOODS_DISTRIBUTION_JOB, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			//2.1、执行预测总方法,预测所有到达货量
			goodsDistributionService.statisticGoodsDistribution(scheduledFireTime);
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
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.GOODS_DISTRIBUTION_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.GOODS_DISTRIBUTION_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.debug("************************ 转运场货量流动分布统计JOB************************");
	}

}
