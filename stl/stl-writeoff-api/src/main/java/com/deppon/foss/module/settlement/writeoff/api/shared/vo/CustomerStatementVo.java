package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomerStatementDto;

public class CustomerStatementVo {
	/**
	 * 对账单DTO
	 */
	private CustomerStatementDto customerStatementDto;
	/**
	 * 导出对账单列头英文名称
	 */
	private String[] arrayColumns;
	/**
	 * 导出对账单列头中文名称
	 */
	private String[] arrayColumnNames;



	public CustomerStatementDto getCustomerStatementDto() {
		return customerStatementDto;
	}

	public void setCustomerStatementDto(CustomerStatementDto customerStatementDto) {
		this.customerStatementDto = customerStatementDto;
	}

	public String[] getArrayColumns() {
		return arrayColumns;
	}

	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}

}
