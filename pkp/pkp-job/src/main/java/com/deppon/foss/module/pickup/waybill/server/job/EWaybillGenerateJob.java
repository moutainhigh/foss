package com.deppon.foss.module.pickup.waybill.server.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 生成待激活电子运单job 
 *
 */
public class EWaybillGenerateJob extends GridJob implements StatefulJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(EWaybillGenerateJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			LOGGER.info("EWaybill JOB generate begin");
			if(isOpenJob()){
				// 电子运单service
				IEWaybillService ewaybill = getBean("ewaybillService", IEWaybillService.class);
				// 执行轮询
				ewaybill.batchGenerateUnActiveEWaybillJobs();
			}
			LOGGER.info("EWaybill JOB generate end");	
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("生成待激活电子运单job异常", e);
		}
	}
	
	private boolean isOpenJob() {
		ISysConfigService pkpsysConfigService = getBean("pkpsysConfigService", ISysConfigService.class);
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.PKP_EWAYBILL_AUTO_SCHEDULE,FossConstants.ROOT_ORG_CODE);
		if(config != null && StringUtils.isNotEmpty(config.getConfValue()) && 
				FossConstants.YES.equals(config.getConfValue())){
			return true;
		}
		return false;
	}

}