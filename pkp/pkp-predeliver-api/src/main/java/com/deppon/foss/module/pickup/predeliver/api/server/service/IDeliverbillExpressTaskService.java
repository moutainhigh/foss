package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillExpressTaskDto;

/**
 * 快递派件交接 提交任务后更新派送单数据Service接口
 * @author 243921-foss-zhangtingting
 * @date 2015-04-17 下午5:03:05
 */
public interface IDeliverbillExpressTaskService extends IService {

	/**
	 * 根据派送单号、运单号更新派送单、派送单明细数据及生成新的派送单
	 * @author 243921-foss-zhangtingting
	 * @date 2015-05-11 下午4:12:21
	 * @param deliverbillExpressTaskDto 提交任务后生成派送单dto
	 */
	public DeliverbillExpressTaskDto createDeliverbillExpressTask(DeliverbillExpressTaskDto deliverbillExpressTaskDto);
}
