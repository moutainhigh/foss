package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;

/**
 * 
 * @author 306548-foss-honglujun
 * 零担-重大货物异常自动上报-FOSS自动上报	每XX分钟执行一次  </br>
 * 
 */
public interface IImportantGoodsNotifyJMSService extends IService{
	
	/**
	 * 零担-重大货物异常自动上报-FOSS自动上报	每60分钟执行一次  </br>
	 */
	public void processNotifyImportantGoods();
}
