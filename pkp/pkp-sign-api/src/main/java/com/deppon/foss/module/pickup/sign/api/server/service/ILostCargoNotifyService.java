package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;


/***
 * 
 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.service.ILostCargoNotifyService
 * @author: foss-yuting
 * @description: 丢货差错自动上报job
 * @date:2014年12月3日 下午2:27:21
 */
public interface ILostCargoNotifyService extends IService {
	/***
	 * 丢货差错自动上报 (次日上午8:00发送)
	 */
	void processNotifyLastCargo();
}
