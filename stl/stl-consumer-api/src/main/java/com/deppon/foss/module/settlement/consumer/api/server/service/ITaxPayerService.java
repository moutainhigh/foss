package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.TaxPayerInfoResponse;

import java.util.List;

/**
 * Created by 322906 on 2016/7/2.
 */
public interface ITaxPayerService  {
	TaxPayerInfoResponse getTaxPayerByCustomerCode(List<String> customerCodes);
}
