package com.deppon.foss.module.settlement.job.server.jobs;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILaborfeeTakeEffectService;

/**
 * 生效应付单(装卸费)Job
 * 
 * @author foss-zhangxiaohui
 * @date Nov 22, 2012 3:32:34 PM
 */
public class LaborfeeTakeEffectJob extends GridJob implements StatefulJob {

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LaborfeeTakeEffectJob.class);

	/**
	 * 生效装卸费Job--先查询出结果，再一条一条更新
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 4, 2012 5:17:32 PM
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0)
			throws JobExecutionException {
		// 实例化生效装卸费Service
		 ILaborfeeTakeEffectService laborfeeTakeEffectService;
		try {
			//打印Job开始日志
			LOGGER.info("开始查询符合条件的应付单");

			// 给生效装卸费Service实例赋值
			laborfeeTakeEffectService = getBean("laborfeeTakeEffectService", ILaborfeeTakeEffectService.class);
			
			// 更新应付单信息
			laborfeeTakeEffectService.process();

			//打印Job日志
			LOGGER.info("查询应付单装卸费单据完成");
		} 
		//捕获异常
		catch (Exception e) {
			
			//打印错误信息
			LOGGER.error("查询应付单生效装卸费单据发生异常，异常信息：" + e.getMessage());
			throw new JobExecutionException("查询应付单生效装卸费单据发生异常，异常信息：" + e.getMessage(), e);

		}
	}
}
