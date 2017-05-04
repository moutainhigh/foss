package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;

/**
 * <1>
 * @clasaName:PosCardAutoFrozenJob
 * @author: 231438-foss-cjy
 * @description: POS机刷卡长期未使用自动冻结JOB，每天23:00:00执行一次
 * @date:2016年12月9日 下午4:27:21
 */
public class PosCardAutoFrozenJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(PosCardAutoFrozenJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		//POS机刷卡管理Service
		IWSCManageService wscManageService ;
		try{
			LOGGER.info("---------------------POS机刷卡数据长期未使用自动冻结JOB开始：-----------------------------");
			//自动付款推送业务逻辑实现类
			wscManageService = getBean("wscManageService",IWSCManageService.class);
			//启动业务实现类入口
			wscManageService.posCardAutoFrozen();
			LOGGER.info("---------------------POS机刷卡数据长期未使用自动冻结JOB结束：-----------------------------");
		}catch(Exception e){
			LOGGER.error("error", e.getMessage());
			throw new JobExecutionException("POS机刷卡数据长期未使用自动冻结:"+e.getMessage());
		}
	}

}
