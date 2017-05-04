package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.service.IShortGoodsNotifyJMSService
 * @author: foss-yuting
 * @description: 新增内物短少差错-FOSS自动上报	每XX分钟执行一次  </br>
 * @date:2014年12月3日 下午2:27:21
 */
public interface IShortGoodsNotifyJMSService extends IService{
	
	/***
	 * 新增内物短少差错-FOSS自动上报	每XX分钟执行一次  </br>
	 */
	public void processNotifyShortGoods();
	
}
