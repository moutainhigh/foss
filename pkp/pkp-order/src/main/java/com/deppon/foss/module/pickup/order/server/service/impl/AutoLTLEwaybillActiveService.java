package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoLTLEwaybillActiveService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOmsOrderDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessLogEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 零担电子面单激活处理Service
 * @author 325220-foss-liuhui
 * @date 2016年5月10日
 */
public class AutoLTLEwaybillActiveService extends OrderTheadPool implements IAutoLTLEwaybillActiveService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoLTLEwaybillActiveService.class);
	/**
	 * 零担电子面单激活--数据集中处理Service
	 */
	private ILTLEWaybillService lTLEWaybillService;
	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * OMS订单处理DAO
	 */
	private IOmsOrderDao omsOrderDao;
	
	/**
	 * 零担电子面单日志DAO
	 */
	private IWaybillProcessLogDao waybillProcessLogDao;
	
	private ILTLEWaybillProcessService lTLEWaybillProcessService;
	
	public void setlTLEWaybillProcessService(
			ILTLEWaybillProcessService lTLEWaybillProcessService) {
		this.lTLEWaybillProcessService = lTLEWaybillProcessService;
	}

	public void setlTLEWaybillService(ILTLEWaybillService lTLEWaybillService) {
		this.lTLEWaybillService = lTLEWaybillService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setOmsOrderDao(IOmsOrderDao omsOrderDao) {
		this.omsOrderDao = omsOrderDao;
	}

	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}

	@Override
	public void process(List<WaybillProcessEntity> waybillProcessEntityList) {
		pushThreadsPool(waybillProcessEntityList);
	}
	
	@Override
	public void businessExecutor(Object obj) {
		@SuppressWarnings("unchecked")
		List<WaybillProcessEntity> waybillProcessEntityList = (List<WaybillProcessEntity>) obj;
		// 根据运单号批量激活运单
		lTLEWaybillService.batchActiveLTLEWaybillByWaybillJobs(waybillProcessEntityList);
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoLTLEWaybillActiveService|零担电子面单批量激活|线程池满异常处理|开始");
		// 入参转换与校验
		@SuppressWarnings("unchecked")
		List<WaybillProcessEntity> waybillProcessEntityList = (List<WaybillProcessEntity>) obj;
		if (waybillProcessEntityList == null || CollectionUtils.isEmpty(waybillProcessEntityList)) {
			return;
		}
		WaybillProcessEntity waybillProcessEntity = waybillProcessEntityList.get(0);
		String jobId = waybillProcessEntity.getJobId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldJobId", jobId);
		map.put("newJobId", WaybillConstants.UNKNOWN);
		map.put("modifyTime", new Date());
		//将jobId置为N/A
		lTLEWaybillProcessService.updateWaybillProcessByJobId(map);
		// 处理日志
		for(WaybillProcessEntity  processEntity: waybillProcessEntityList){
			// 查询订单是否存在，不存在就不记录失败日志了
			OmsOrderEntity omsOrderEntity = omsOrderDao.queryOmsOrderByWaybillNo(processEntity.getWaybillNo());
			if(omsOrderEntity == null){
				continue;
			}
			// 组装日志记录
			WaybillProcessLogEntity waybillProcessLogEntity = new WaybillProcessLogEntity();
			waybillProcessLogEntity.setId(UUIDUtils.getUUID());
			waybillProcessLogEntity.setWaybillNo(processEntity.getWaybillNo());
			waybillProcessLogEntity.setCreateTime(new Date());
			waybillProcessLogEntity.setModifyTime(new Date());
			waybillProcessLogEntity.setOperateResult("失败");
			waybillProcessLogEntity.setFailResion("线程池满");
			waybillProcessLogEntity.setLogType(WaybillConstants.LTLEWAYBILL_PDA_ACTIVE_PROCESS);
			// 记录日志
			waybillProcessLogDao.addWaybillProcessLogEntity(waybillProcessLogEntity);
		}
		LOGGER.info("AutoLTLEWaybillActiveService|零担电子面单批量激活|线程池满异常处理|结束");
	}

	@Override
	public int getActiveThreads() {
		Integer initThreads = 3;
		String[] codes = new String[] { WaybillConstants.PKP_LTL_EWAYBILL_ACTIVE_COUNT };
		List<ConfigurationParamsEntity> configList = configurationParamsService
				.queryConfigurationParamsBatchByCode(codes);
		if (CollectionUtils.isNotEmpty(configList) && configList.get(0) != null) {
			initThreads = Integer.valueOf(configList.get(0).getConfValue());
		}
		return initThreads;
	}

}
