package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;

/***
 * 
 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.service
 * @author: yuting@163.com
 * @description: 批量发送快递相关短信的job
 * @date:2014年7月11日 下午6:42:17
 */
public interface IBatchSendSMSJobService extends IService {

	/***
	 * 批量打包发送短信 (次周一和每天上午12:00发送)
	 */
	void processBatchSendExpress();

}
