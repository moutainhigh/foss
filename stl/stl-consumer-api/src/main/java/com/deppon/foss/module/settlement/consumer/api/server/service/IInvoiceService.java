package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;

/**
 * 开发发票服务
 * @author ibm-guxinhua
 * @date 2012-11-6 下午5:10:23
 */
public interface IInvoiceService {

	/**
	 * 查询运单的可开票金额
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午5:11:19
	 */
	List<InvoiceDto> queryWaybillAmount(List<String> waybillNoList);
	
	/**
	 * 查询小票开票金额
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午5:11:19
	 */
	List<InvoiceDto> queryOtherRevenueAmount(List<String> otherRevenueNoList);
	
	/**
	 * 标记发票已开
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午5:11:19
	 */
	InvoiceEntity markInvoice(InvoiceDto dto);
	
	/**
	 * 查询开票状态
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午5:11:19
	 */
	InvoiceEntity queryInvoiceState(String sourceBillNo ,String code);
}
