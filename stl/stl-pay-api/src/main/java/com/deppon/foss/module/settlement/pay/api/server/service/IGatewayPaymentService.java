package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;

/**
 * 网上支付应收单、对账单公共SERVICE接口
 * 
 * @author 306698-foss-youkun
 * @date 2016-05-09 下午2:29:17
 */
public interface IGatewayPaymentService extends IService {

	/**
	 * 网上支付按应收单还款
	 * 
	 * @author 306698-foss-youkun
	 * @date 2016-05-09
	 * 
	 */
	void paymentReceiveBillInfo(BillReceivableOnlineQueryDto queryDto);
}
