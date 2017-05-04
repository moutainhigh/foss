package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddDto;


public class InvoiceManagementAllVo {
	/**
	 * VO类序列号
	 */
	private static final long serialVersionUID = -1827529959996184637L;
	/**
	 * 制作对账单DTO
	 */
	InvoiceManagementAddDto invoiceManagementAddDto;
	
	/**
	 * 对账单集合
	 */
	List<InvoiceManagementAddDto> list;

	public InvoiceManagementAddDto getInvoiceManagementAddDto() {
		return invoiceManagementAddDto;
	}

	public void setInvoiceManagementAddDto(
			InvoiceManagementAddDto invoiceManagementAddDto) {
		this.invoiceManagementAddDto = invoiceManagementAddDto;
	}

	public List<InvoiceManagementAddDto> getList() {
		return list;
	}

	public void setList(List<InvoiceManagementAddDto> list) {
		this.list = list;
	}
	
}
