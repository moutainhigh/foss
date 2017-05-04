package com.deppon.foss.module.pickup.sign.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.sign.api.server.service.IImportantGoodsNotifyJMSService;

/**
 * 零担-重大货物异常自动上报
 * @author 306548-foss-honglujun
 *
 */
public class ImportantGoodsNotifyProcessJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportantGoodsNotifyProcessJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		try {
			LOGGER.info("重大货物异常差错上报 start");
			IImportantGoodsNotifyJMSService importantGoodsNotifyJMSService = getBean("importantGoodsNotifyService",IImportantGoodsNotifyJMSService.class);
			importantGoodsNotifyJMSService.processNotifyImportantGoods();
			LOGGER.info("重大货物异常差错上报  end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("重大货物差错异常",e);
		}
	}

}
