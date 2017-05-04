package com.deppon.foss.module.pickup.expresswaybill.server.job;


import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpressAutoMakeupService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 *快递自动补录生成运单定时任务
 *YangXiaolong 220125
 *
 */
public class ExpressAutoMakeupJob extends GridJob implements StatefulJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressAutoMakeupJob.class);
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			LOGGER.info("生成待补录快递运单job开始===========================");
			if(false){
				// 快递自动补录service
				IExpressAutoMakeupService expressAutoMakeupService = getBean("expressAutoMakeupService", IExpressAutoMakeupService.class);
				// 执行补录逻辑
				expressAutoMakeupService.batchGenerateExpressWaybillJobs();
			}
			LOGGER.info("生成待补录快递运单job结束===========================");	
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("生成待补录快递运单job异常", e);
		}
	}
	
	private boolean isOpenJob() {
		ISysConfigService pkpsysConfigService = getBean("pkpsysConfigService", ISysConfigService.class);
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
			//开始测试job时，一定注意在数据库里的脚本配置，且该参数是针对于快递自动补录
			WaybillConstants.PKP_EXPRESS_AUTO_MKKEUP,FossConstants.ROOT_ORG_CODE);
		if(config != null && StringUtils.isNotEmpty(config.getConfValue()) && 
		      FossConstants.YES.equals(config.getConfValue())){
			return true;
		}   
		return false;
	}

}