package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

/**
 * 
 * @author 243921 zhangtingting
 * @date 2017-03-05 15:58:02
 * FOSS接收TPS传送的签收信息
 *
 */
public interface ITPSSignSettleService {
	
	//结清+签收
	public void signSettle(WaybillSignResultEntity signResultEntity);

}
