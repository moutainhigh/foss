package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;

/**
 * 查询最早欠款客户已用额度信息Service接口
 * 
 * @author foss-zhangxiaohui
 * @date Jan 15, 2013 2:17:53 PM
 */
public interface IDebtCreditLimitInfoQueryService extends IService {
	
	/**
	 * 处理
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 15, 2013 2:21:05 PM
	 */
	void process();
}
