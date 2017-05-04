package com.deppon.foss.module.pickup.ltlewaybill.service.threadpool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoLTLEwaybillActiveService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 零担电子面单激活处理线程操作发起类
 * 
 * @author 325220-foss-liuhui
 * @date 2016年5月10日
 */
public class LTLEwaybillActiveProcess extends OrderThreadPoolCaller{

	private static final Logger LOGGER = LoggerFactory.getLogger(LTLEwaybillActiveProcess.class);

	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 零担电子面单激活线程Service
	 */
	private IAutoLTLEwaybillActiveService autoLTLEwaybillActiveService;
	/**
	 * 零担电子面单线程处理相关Service
	 */
	private ILTLEWaybillProcessService lTLEWaybillProcessService;
	/**
	 * 零担电子面单多线程激活
	 * 
	 * @author 325220-foss-liuhui
	 * @date 2016-05-06
	 */
	@Override
	public int serviceCaller() {
		LOGGER.info("零担电子面单待激活运单多线程激活处理|开始");
		// 查询激活任务的开关配置
		String isExecuteActiveProcess = FossConstants.NO;
		String[] configCode = new String[] { WaybillConstants.PKP_LTL_EWAYBILL_AUTO_ACTIVE };
		List<ConfigurationParamsEntity> configurations = configurationParamsService
				.queryConfigurationParamsBatchByCode(configCode);
		if (CollectionUtils.isNotEmpty(configurations)) {
			isExecuteActiveProcess = configurations.get(0) == null ? FossConstants.NO
					: configurations.get(0).getConfValue();
		}

		// 开关关闭则直接返回
		if (FossConstants.NO.equals(isExecuteActiveProcess)) {
			LOGGER.info("零担电子面单待激活运单多线程激活处理|配置开关未开启");
			return 0;
		}

		int count = 0; //本次执行的条数
		// 更新要做激活处理的运单任务记录，取得本次执行激活记录的jobid
		String currentJobId = updatePreActiveProcessRecords();
		if (StringUtils.isNotBlank(currentJobId)){
			// 根据jobid取得本次要做激活处理的运单号
			List<WaybillProcessEntity> waybillNoList = getWaybillProcessList(currentJobId);
			if (CollectionUtils.isNotEmpty(waybillNoList)) {
				// 激活process
				autoLTLEwaybillActiveService.process(waybillNoList);
				count = waybillNoList.size();
			}
		}
		
		LOGGER.info("零担电子面单待激活运单多线程激活处理|结束");
		return count;
	}

	/**
	 * 更新待激活处理运单的任务记录，返回jobid
	 * @return
	 */
	private String updatePreActiveProcessRecords() {
		String jobId = UUIDUtils.getUUID();
		String rownum = getConfigNum();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		map.put("rownum", rownum);
		map.put("modifyTime", new Date());
		map.put("processType", WaybillConstants.LTLEWAYBILL_PDA_ACTIVE_PROCESS);
		int result = 0;
		result = lTLEWaybillProcessService.updateForJob(map);
		if (result > 0) {
			return jobId;
		}
		return null;
	}

	/**
	 * 取得零担电子运单自动激活一次执行的记录条数
	 * 
	 * @return
	 */
	private String getConfigNum() {
		String num = "500"; // 若没有做配置，默认一次执行500条
		String[] configCode = new String[] { WaybillConstants.PKP_LTL_EWAYBILL_AUTO_ACTIVE_COUNT };
		List<ConfigurationParamsEntity> configurations = this.configurationParamsService
				.queryConfigurationParamsBatchByCode(configCode);
		if (CollectionUtils.isNotEmpty(configurations)) {
			num = configurations.get(0) == null ? num : configurations.get(0).getConfValue();
		}
		return num;
	}

	/**
	 * 根据jobid查询运单激活处理任务列表
	 * @param jobId
	 * @return
	 */
	private List<WaybillProcessEntity> getWaybillProcessList(String jobId) {
		if (StringUtils.isEmpty(jobId)) {
			return null;
		}
		// 根据更新的JOBID获取所有激活运单集合
		List<WaybillProcessEntity> list = lTLEWaybillProcessService.queryWaybillProcessListByJobId(jobId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list;
		}
		return null;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setAutoLTLEwaybillActiveService(
			IAutoLTLEwaybillActiveService autoLTLEwaybillActiveService) {
		this.autoLTLEwaybillActiveService = autoLTLEwaybillActiveService;
	}

	public void setlTLEWaybillProcessService(
			ILTLEWaybillProcessService lTLEWaybillProcessService) {
		this.lTLEWaybillProcessService = lTLEWaybillProcessService;
	}
}
