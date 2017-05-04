package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSLogEntity;


/**
 * 待处理发送短信日志接口
 * @author WangQianJin
 * @date 2013-4-11 上午10:27:43
 */
public interface IPendingSendSMSLogDao {

	/**
	 * 添加待处理发送短信日志
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	int addPendingSendmailLogEntity(PendingSendSMSLogEntity record);
}
