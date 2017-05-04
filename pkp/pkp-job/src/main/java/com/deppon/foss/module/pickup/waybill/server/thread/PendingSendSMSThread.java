package com.deppon.foss.module.pickup.waybill.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendSMSService;

public class PendingSendSMSThread extends OrderThreadPoolCaller {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PendingSendSMSThread.class);
	
	@Autowired
	private IPendingSendSMSService pendingSendSMSService;

	@Override
	public int serviceCaller() {
		// 短信息通知发货/收货人，每30秒执行一次 任务改线程 
		LOGGER.info("pkp-job-->PendingSendSMSThread-->serviceCaller 短信息通知发货/收货人，每30秒执行一次,线程处理开始!=============================================");

		try {
			pendingSendSMSService.batchjobs();
			//30秒执行一次
			setSleepSeconds(NumberConstants.NUMBER_30);
			return 0;
		} catch (Exception e) {
			LOGGER.info("pkp-job-->PendingSendSMSThread-->serviceCaller 短信息通知发货/收货人，每30秒执行一次,线程一场，原因:"+e.getMessage());
			throw  new BusinessException(e.getMessage());
		}finally{
			// 短信息通知发货/收货人，每30秒执行一次
			LOGGER.info("pkp-job-->PendingSendSMSThread-->serviceCaller 短信息通知发货/收货人，每30秒执行一次,线程处理结束!=============================================");
		}
	}

}
