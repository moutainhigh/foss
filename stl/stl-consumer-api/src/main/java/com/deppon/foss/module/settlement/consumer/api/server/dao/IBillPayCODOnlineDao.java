package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.Map;

public interface IBillPayCODOnlineDao {

	/**
	 * 批量插入代收货款付款单明细
	 * @author ddw
	 * @date 2015-10-15
	 */
	int addCodPaymentD(Map<String,Object> codMap);

	/**
	 * 批量插入代收货款付款单
	 * @author ddw
	 * @date 2015-10-15
	 */
	int addCodPayment(Map<String,Object> codMap);

	/**
	 * 批量更新代收货款应付单
	 * @author ddw
	 * @date 2015-10-15
	 */
	int updateCodPayable(Map<String,Object> codMap);

}
