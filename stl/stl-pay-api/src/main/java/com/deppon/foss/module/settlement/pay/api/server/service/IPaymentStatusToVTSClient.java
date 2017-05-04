package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.dto.BillInfoEntity;


/**
 * FOSS向VTS回传字段信息
 * @author 395982
 *
 */
public interface IPaymentStatusToVTSClient {
	public void sendPaymentStatusToVTS(BillInfoEntity billInfoEntity);
}
