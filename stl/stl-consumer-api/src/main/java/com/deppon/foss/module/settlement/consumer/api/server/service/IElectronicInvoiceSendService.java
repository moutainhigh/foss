package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.ElectronicInvoiceDto;

/**
 * 
 * 将电子发票数据发送给发票系统
 * 
 * @date 2014-10-29 上午10:38:33
 */
public interface IElectronicInvoiceSendService {

	/**
	 * 
	 * 发电子发票数据到发票系统
	 * 
	 * @date 2014-10-29 上午10:38:33
	 */
	void electronicInvoiceSend(ElectronicInvoiceDto dto) throws Exception;
}
