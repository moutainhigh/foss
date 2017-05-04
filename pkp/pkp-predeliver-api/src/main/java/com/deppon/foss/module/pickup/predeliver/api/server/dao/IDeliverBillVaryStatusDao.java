package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;

/** 
 * @ClassName: IDeliverBillVaryStatusDao 
 * @Description: 派送单状态更新记录表Dao 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-27 下午3:40:45 
 *  
 */
public interface IDeliverBillVaryStatusDao {

	/**
	 * @Title: insertOne
	 * @Description: 添加一个派送单状态更新记录
	 * @param deliverBillVaryStatusEntity 派送单状态更新记录对象
	 * @return 受影响的行数
	 */
	int insertOne(DeliverBillVaryStatusEntity deliverBillVaryStatusEntity);
}
