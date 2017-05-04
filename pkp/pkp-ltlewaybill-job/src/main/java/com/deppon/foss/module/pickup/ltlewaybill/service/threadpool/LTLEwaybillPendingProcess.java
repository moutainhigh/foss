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
import com.deppon.foss.module.pickup.order.api.server.service.IAutoLTLEwaybillPendingService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 零担电子面单待补录处理线程操作发起类
 * 
 * @author （323098王鹏涛）
 * @date 2016年8月1日
 */
public class LTLEwaybillPendingProcess extends OrderThreadPoolCaller{

	private static final Logger LOGGER = LoggerFactory.getLogger(LTLEwaybillPendingProcess.class);

	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 零担电子面单待补录线程Service
	 */
	private IAutoLTLEwaybillPendingService autoLTLEwaybillPendingService;
	/**
	 * 零担电子面单线程处理相关Service
	 */
	private ILTLEWaybillProcessService lTLEWaybillProcessService;
	/**
	 * 零担电子面单多线程待补录
	 * 
	 * @author （323098王鹏涛）
	 * @date 2016-08-01
	 */
	@Override
	public int serviceCaller() {
		LOGGER.info("零担电子面单待补录运单多线程待补录处理|开始");
		// 查询待补录任务的开关配置
		String isExecutePendingProcess = FossConstants.NO;
		String[] configCode = new String[] { WaybillConstants.PKP_LTL_EWAYBILL_AUTO_PENDING };
		List<ConfigurationParamsEntity> configurations = configurationParamsService
				.queryConfigurationParamsBatchByCode(configCode);
		if (CollectionUtils.isNotEmpty(configurations)) {
			isExecutePendingProcess = configurations.get(0) == null ? FossConstants.NO
					: configurations.get(0).getConfValue();
		}

		// 开关关闭则直接返回
		if (FossConstants.NO.equals(isExecutePendingProcess)) {
			LOGGER.info("零担电子面单待补录运单多线程待补录处理|配置开关未开启");
			return 0;
		}

		String jobId = UUIDUtils.getUUID();
		int count = 0;
		// 更新要做待补录处理的运单任务记录
		
		LOGGER.info("零担电子面单线程池执行updatePrePendingProcessRecords调用开始:"+jobId);
		count = updatePrePendingProcessRecords(jobId);
		LOGGER.info("零担电子面单线程池执行updatePrePendingProcessRecords调用结束:"+jobId);
		if (count>0){
			//执行程序
			autoLTLEwaybillPendingService.process(jobId);
		}
		
		LOGGER.info("零担电子面单待待补录运单多线程待补录处理|结束");
		return count;
	}

	/**
	 * 更新待待补录处理运单的任务记录，返回jobid
	 * @return
	 */
	private int updatePrePendingProcessRecords(String jobId) {
		LOGGER.info("零担电子面单线程池执行updatePrePendingProcessRecords开始:"+jobId);
		String rownum = getConfigNum();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		map.put("rownum", rownum);
		map.put("modifyTime", new Date());
		map.put("processType", WaybillConstants.LTLEWAYBILL_PDA_PENDING_PROCESS);
		int result = 0;
		result = lTLEWaybillProcessService.updateForJob(map);
		LOGGER.info("零担电子面单线程池执行updatePrePendingProcessRecords结束:"+jobId+"执行记录数"+jobId);
		return result;
	}

	/**
	 * 取得零担电子运单自动待补录一次执行的记录条数
	 * 
	 * @return
	 */
	private String getConfigNum() {
		String num = "500"; // 若没有做配置，默认一次执行500条
		String[] configCode = new String[] { WaybillConstants.PKP_LTL_EWAYBILL_AUTO_PENDING_COUNT };
		List<ConfigurationParamsEntity> configurations = this.configurationParamsService
				.queryConfigurationParamsBatchByCode(configCode);
		if (CollectionUtils.isNotEmpty(configurations)) {
			num = configurations.get(0) == null ? num : configurations.get(0).getConfValue();
		}
		return num;
	}

	/**
	 * 根据jobid查询运单待补录处理任务列表
	 * @param jobId
	 * @return
	 */
	private List<WaybillProcessEntity> getWaybillProcessList(String jobId) {
		if (StringUtils.isEmpty(jobId)) {
			return null;
		}
		// 根据更新的JOBID获取所有待补录运单集合
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

	public void setAutoLTLEwaybillPendingService(
			IAutoLTLEwaybillPendingService autoLTLEwaybillPendingService) {
		this.autoLTLEwaybillPendingService = autoLTLEwaybillPendingService;
	}

	public void setlTLEWaybillProcessService(
			ILTLEWaybillProcessService lTLEWaybillProcessService) {
		this.lTLEWaybillProcessService = lTLEWaybillProcessService;
	}
}
