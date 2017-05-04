package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;

/** 
 * @ClassName: IDeliverBillVaryStatusService 
 * @Description: 派送单状态更新记录表Service 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-27 下午4:02:06 
 *  
 */
public interface IDeliverBillVaryStatusService extends IService {
	
	/**
	 * @Title: insertDBVaryStatus
	 * @Description: 添加派送单状态更新记录
	 * @param deliverBillVaryStatusEntity 创建派送单状态更新记录对象
	 * @return  返回值为 1：添加成功
	 */
	void insertDBVaryStatus(DeliverBillVaryStatusEntity deliverBillVaryStatusEntity);

}
