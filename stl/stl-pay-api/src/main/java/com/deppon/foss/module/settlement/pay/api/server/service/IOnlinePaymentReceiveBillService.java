package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOADOnlineResultDto;
/**
 * 网上支付应收单、对账单公共SERVICE接口
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-19 下午2:29:17
 */
public interface IOnlinePaymentReceiveBillService extends IService {
	/**
	 * 
	 * 网上支付查询未核销的应收单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	BillSOADOnlineResultDto queryReceiveBillInfo(BillReceivableOnlineQueryDto queryDto);
	
	/**
	 * 网上支付锁定应收单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-22 上午11:55:25
	 */
	void lockReceiveBillInfo(BillReceivableOnlineQueryDto queryDto);
	
	/**
	 * 网上支付按应收单还款
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-22 下午4:39:48
	 */
	void paymentReceiveBillInfo(BillReceivableOnlineQueryDto queryDto);
}
