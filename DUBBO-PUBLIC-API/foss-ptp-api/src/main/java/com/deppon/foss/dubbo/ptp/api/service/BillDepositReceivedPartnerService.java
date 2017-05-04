package com.deppon.foss.dubbo.ptp.api.service;

import com.deppon.foss.dubbo.ptp.api.define.BillDepositReceivedPartnerEntity;

public interface BillDepositReceivedPartnerService {
	
	/** 新增事业合伙人预收单 。调用代码请手动捕获SettlementException异常并获取异常的errCode字段
	 * @author 335284
	 * @since 2016.11.17
	 * @param partnerDepositReceved
	 * @return 结果，请判断isSuccess字段
	 */
	public BillDepositReceivedPartnerEntity addPartnerDepositReceved(BillDepositReceivedPartnerEntity partnerDepositReceved);
}