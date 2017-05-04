package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.ArriveOnlineDto;

/**
 * 付款查询相关dao
 * @author 239284
 *
 */
public interface IPaymentSettlementDao {

	/**
	 * 根据单据类型、付款方式查询到达网上支付结果
	 * @param waybillNo 运单号
	 * @param billType 应收单单据类型
	 * @param payType 还款单付款方式
	 * @return
	 */
	public List<ArriveOnlineDto> arriveOnlinePay(String waybillNo, String billType, String payType);
	
}
