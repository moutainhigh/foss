package com.deppon.foss.module.settlement.dubbo.api.service;

import com.deppon.foss.dubbo.ptp.api.define.BillDepositReceivedPartnerEntity;
import com.deppon.foss.framework.service.IService;

/**
 * 对接合伙人预收单service接口
 * @author foss-Jiang Xun
 * @date 2016-01-07 下午02:37:00
 */
public interface IBillDepositReceivedPayPtpService4dubbo extends IService{
	
	/**
	 * 新增合伙人预收单
	 * @author foss-Jiang Xun
	 * @date 2016-01-07 下午3:01:13
	 * @param 
	 * @param 
	 * @return 
	 * @see
	 */
	BillDepositReceivedPartnerEntity addBillDepositReceivedPay(BillDepositReceivedPartnerEntity billDepositReceivedPartnerDto);

}