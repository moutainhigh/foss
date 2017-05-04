package com.deppon.foss.module.pickup.expresstime.server.job;


import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaToWicsJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 *快递自动补录PDA开单时时效推送
 * YangXiaolong 220125
 *
 */
public class ExpressTimeSetJob extends GridJob implements StatefulJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressTimeSetJob.class);
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if(false){
			LOGGER.info("快递自动补录PDA开单时时效推送job开始===========================");
				// 快递自动补录PDA开单时时效推送
			IPdaToWicsJMSService pdaToWicsJMSService = getBean("pdaToWicsJMSService", IPdaToWicsJMSService.class);
				// 执行推送任务
			pdaToWicsJMSService.batchTimeSetExpressJobs();
			}
			LOGGER.info("快递自动补录PDA开单时时效推送job结束===========================");	
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("快递自动补录PDA开单时时效推送job异常", e);
		}
	}
	/**
	 *220125 YangXiaolong 
	 * 快递自动补录时效job开关
	 */
	private boolean isOpenJob() {
		ISysConfigService pkpsysConfigService = getBean("pkpsysConfigService", ISysConfigService.class);
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
			//开始测试job时，一定注意在数据库里的脚本配置，且该参数是针对于快递自动补录
			WaybillConstants.EXPRESS_PDA_TIME_SET,FossConstants.ROOT_ORG_CODE);
		if(config != null && StringUtils.isNotEmpty(config.getConfValue()) && 
		      FossConstants.YES.equals(config.getConfValue())){
			return true;
		}   
		return false;
	}


}