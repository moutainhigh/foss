/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class PendingTodoThread  extends OrderThreadPoolCaller {

	private static final Logger LOGGER = LoggerFactory.getLogger(PendingTodoThread.class);
	
	@Autowired
	private IWaybillRfcTodoJobService waybillRfcTodoJobService;
	
	@Override
	public int serviceCaller() {
		// 生成更改单待办事项，每30秒执行一次 任务改线程
		LOGGER.info("pkp-job-->PendingTodoThread-->serviceCaller 生成更改单待办事项，每30秒执行一次,线程处理开始!=============================================");

		try {
			//执行任务
			waybillRfcTodoJobService.prepareSendTodo();
			//30秒执行一次
			setSleepSeconds(NumberConstants.NUMBER_30);
			return 0;
		} catch (Exception e) {
			LOGGER.info("pkp-job-->PendingTodoThread-->serviceCaller 生成更改单待办事项，每30秒执行一次,线程异常，原因:"+e.getMessage());
			throw  new BusinessException(e.getMessage());
		}finally{
			// 生成更改单待办事项，每30秒执行一次
			LOGGER.info("pkp-job-->PendingTodoThread-->serviceCaller 生成更改单待办事项，每30秒执行一次,线程处理结束!=============================================");
		}
	}

}
