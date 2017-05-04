package com.deppon.foss.module.pickup.sign.api.server.service;

/**
 * 
 * @author 243921 zhangtingting
 * @date 2017-03-02 15:58:02
 * VTS自动受理反签收反结清 
 *
 */
public interface ITPSReverseSignSettleService {
	
	//反签收
	public void reverseSign(String waybillNo);
	
	//反结清
	public void reverseSettle(String waybillNo);
    
}
