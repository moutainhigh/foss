package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;

public interface IPtpSignPartnerService {
	/**
	 * 合伙人签收时生效应付流水
	 * @param request 更新流水状态和生效时间的请求
	 * @return 是否成功
	 */
   public boolean validBillSaleFlowByAsynESB(List<PartnerUpdateTakeEffectTimeRequest> result);
}
