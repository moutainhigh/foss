/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoEWaybillCzmActiveHandleService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author 270293
 *
 */
public class EWaybillCzmGenerateProcess extends OrderThreadPoolCaller {
	private static final Logger LOGGER = LoggerFactory.getLogger(EWaybillCzmGenerateProcess.class);
	
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	private IConfigurationParamsService configurationParamsService;
	
	private IAutoEWaybillCzmActiveHandleService autoEWaybillCzmActiveHandleService;
	@Override
	public int serviceCaller() {
		LOGGER.info("子母件开始执行调度================");
		//更新数据
		String jobId =UUIDUtils.getUUID();
		String queryJobId = WaybillConstants.UNKNOWN;
		GenerateUnActiveEWaybillVo vo=new GenerateUnActiveEWaybillVo();
		vo.setJobId(jobId);
		vo.setQueryNum(readChangeCount());
		vo.setQueryJobId(queryJobId);
		int result=waybillRelateDetailEntityService.updateJobIDTopByRowNum(vo);
		//先更新一条线程查询的电子运单数
		if(StringUtils.isNotEmpty(jobId)){
			LOGGER.info("子母件开始执行调度JOBID==============="+jobId);
			autoEWaybillCzmActiveHandleService.process(jobId);
		}else{
			return 0;
		}
		LOGGER.info("子母件开始执行调度数量==============="+result);
		return result;
	}
	
	/**
	 * 从配置参数中获得零担每个线程自动读取数量
	 * @return
	 */
	private String readChangeCount(){
		String num = "500";
		String[] codes = new String[1];
		codes[0]=WaybillConstants.PKP_EWAYBILL_SCHEDULE_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			num = configurationParamsEntitys.get(0).getConfValue();
		}
		return num;
	}
	
	/**
	 * @param waybillRelateDetailEntityService the waybillRelateDetailEntityService to set
	 */
	public void setWaybillRelateDetailEntityService(IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	/**
	 * @param autoEWaybillCzmActiveHandleService the autoEWaybillCzmActiveHandleService to set
	 */
	public void setAutoEWaybillCzmActiveHandleService(IAutoEWaybillCzmActiveHandleService autoEWaybillCzmActiveHandleService) {
		this.autoEWaybillCzmActiveHandleService = autoEWaybillCzmActiveHandleService;
	}
}
