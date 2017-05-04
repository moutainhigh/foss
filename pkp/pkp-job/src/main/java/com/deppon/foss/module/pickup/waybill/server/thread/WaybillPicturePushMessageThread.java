package com.deppon.foss.module.pickup.waybill.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;

/**
 * 图片开单数据推送job
 * @author hehaisu
 * @date 2015-2-7 上午9:45:20
 */
public class WaybillPicturePushMessageThread extends OrderThreadPoolCaller{

	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillPicturePushMessageThread.class);
	
	@Autowired
	private IWaybillPendingService waybillPendingService;
	
	@Override
	public int serviceCaller() {
		// 集中开单数据推送,每30秒执行一次任务改线程
		LOGGER.info("pkp-job-->WaybillPicturePushMessageThread-->serviceCaller 集中开单数据推送,每30秒执行一次,线程处理开始!=============================================");

		try {
			// 执行轮询
			waybillPendingService.batchExecutePicturePushJobs();
			//30秒执行一次
			setSleepSeconds(NumberConstants.NUMBER_30);
			return 0;
		} catch (Exception e) {
			LOGGER.info("pkp-job-->WaybillPicturePushMessageThread-->serviceCaller 集中开单数据推送,每30秒执行一次,线程异常，原因:"+e.getMessage());
			throw  new BusinessException(e.getMessage());
		}finally{
			// 集中开单数据推送,每30秒执行一次
			LOGGER.info("pkp-job-->WaybillPicturePushMessageThread-->serviceCaller 集中开单数据推送,每30秒执行一次,线程处理结束!=============================================");
		}
	}
}