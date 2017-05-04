package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;

/**
 * 
 * 理赔支付消息处理服务
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 上午11:21:49
 */
public interface IClaimStatusMsgProcessService extends IService {

	/**
	 * 发送理赔支付消息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:22:25
	 */
	void sendPaymentMsg();
	
	/**
	 * 发送理赔支付消息
	 * @author 231434-foss-bieyexiong
	 * @date 2015-12-29 上午12:55:02
	 */
	void sendClaimPayStatusMsg(ClaimStatusMsgEntity entity);
}
