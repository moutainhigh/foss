package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WoodenStatementDto;

public class WoodenStatementVo {
	/**
	 * 对账单DTO
	 */
	private WoodenStatementDto woodenStatementDto;
	/**
	 * 导出对账单列头英文名称
	 */
	private String[] arrayColumns;
	/**
	 * 导出对账单列头中文名称
	 */
	private String[] arrayColumnNames;

	public WoodenStatementDto getWoodenStatementDto() {
		return woodenStatementDto;
	}

	public void setWoodenStatementDto(
			WoodenStatementDto woodenStatementDto) {
		this.woodenStatementDto = woodenStatementDto;
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
