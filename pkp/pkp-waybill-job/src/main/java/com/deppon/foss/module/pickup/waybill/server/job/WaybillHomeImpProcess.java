package com.deppon.foss.module.pickup.waybill.server.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillHomeImpService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillHomeImpEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillHomeImpPushVo;
import com.deppon.foss.util.UUIDUtils;

public class WaybillHomeImpProcess extends OrderThreadPoolCaller {
	private IWaybillHomeImpService waybillHomeImpService;
	
	
	protected final static Logger LOGGER = LoggerFactory
			.getLogger(WaybillHomeImpProcess.class.getName());
	@Override
	public int serviceCaller() {
		LOGGER.info("FOSS向DOP推送家装运单信息 begin");
		// 每次要查询的条数
		String queryNum = "500";
		WaybillHomeImpPushVo vo = new WaybillHomeImpPushVo();
		String jobId = UUIDUtils.getUUID();
		// 更新一定数量的JobId
		try {
			vo = waybillHomeImpService.updateWaybillInfoPushForJobTopNum(jobId, queryNum);
			// 根据JobId查询待处理信息
			List<WaybillHomeImpEntity> waybillList = waybillHomeImpService.queryWaybillHomeInfoPushMessageByJobId(jobId);
			if (CollectionUtils.isNotEmpty(waybillList)) {
				//执行批量推送
				waybillHomeImpService.pushInfo(waybillList);
			}
			LOGGER.info("FOSS向DOP推送推送家装运单信息 end");
			return vo.getResultNum();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	public void setWaybillHomeImpService(
			IWaybillHomeImpService waybillHomeImpService) {
		this.waybillHomeImpService = waybillHomeImpService;
	}
	
}