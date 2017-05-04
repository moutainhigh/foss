/**
 * Copyright 2015 STL TEAM
 */


package com.deppon.foss.module.settlement.pay.api.server.service;

import java.math.BigDecimal;

import com.deppon.foss.framework.service.IService;

/**
 * 接送货查询接口
 */
public interface IPayableQueryForPkpService extends IService {

	public BigDecimal queryClaimPayableBillByWaybill(String waybillNo);

}