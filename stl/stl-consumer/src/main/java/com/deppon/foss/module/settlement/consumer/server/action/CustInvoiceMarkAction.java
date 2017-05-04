package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.Serializable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICustInvoiceMarkQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CustInvoiceMarkEntity;

public class CustInvoiceMarkAction extends AbstractAction implements Serializable {

	/**
	 * 日志
	 */
	public static final Logger LOGGER = LogManager.getLogger(CustInvoiceMarkAction.class);

	private ICustInvoiceMarkQueryService custInvoiceMarkQueryService;

	/**
	 * 发票标记实体
	 */
	private CustInvoiceMarkEntity custInvoiceMarkEntity;

	/**
	 * <p>
	 * 查询发票标记
	 * </p>
	 * 
	 * @author Yang Shushuo
	 * @date 2013-11-19 下午5:33:47
	 */
	@JSON
	public String queryCustInvoiceMark() {
		String custNumber = custInvoiceMarkEntity.getCustNumber();
		this.setCustInvoiceMarkEntity(this.custInvoiceMarkQueryService.queryCustInvoiceMarkByCustNum(custNumber));
		return this.returnSuccess();
	}

	/********** getter and setters **********/

	public void setCustInvoiceMarkQueryService(ICustInvoiceMarkQueryService custInvoiceMarkQueryService) {
		this.custInvoiceMarkQueryService = custInvoiceMarkQueryService;
	}

	public CustInvoiceMarkEntity getCustInvoiceMarkEntity() {
		return custInvoiceMarkEntity;
	}

	public void setCustInvoiceMarkEntity(CustInvoiceMarkEntity custInvoiceMarkEntity) {
		this.custInvoiceMarkEntity = custInvoiceMarkEntity;
	}
}
