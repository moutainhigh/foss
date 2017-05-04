package com.deppon.foss.module.pickup.sign.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordQmsUnnormalSignJobService;

/**
 * @author 231434-foss
 * 异常签收自动上报qms，1小时跑一次
 */
public class UnnormalSignNotifyProcessJob extends GridJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UnnormalSignNotifyProcessJob.class);

	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		try {
			LOGGER.info("异常签收线上划责自动上报 start");
			IRecordQmsUnnormalSignJobService recordQmsUnnormalSignJobService = 
					getBean("recordQmsUnnormalSignJobService", IRecordQmsUnnormalSignJobService.class);
			recordQmsUnnormalSignJobService.processRecordQmsUnnormalSign();
			LOGGER.info("异常签收线上划责自动上报  end");
		}catch(Exception e){
			LOGGER.error("error", e);
			throw new JobExecutionException("异常签收线上划责自动上报异常",e);
		}
	}

}
