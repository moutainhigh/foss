package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CustInvoiceMarkEntity;



public interface ICustInvoiceMarkQueryService{

	/**
	 * <p>获取客户发票标记</p> 
	 * @author Yang Shushuo
	 * @date 2013-11-19 下午4:30:07
	 * @param custNumber
	 * @return CustInvoiceMarkEntity
	 * @see CustInvoiceMarkEntity
	 */
	CustInvoiceMarkEntity queryCustInvoiceMarkByCustNum(String custNumber);

}
