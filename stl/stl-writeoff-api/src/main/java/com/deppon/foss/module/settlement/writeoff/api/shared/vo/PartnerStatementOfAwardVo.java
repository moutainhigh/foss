package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

public class PartnerStatementOfAwardVo {

	/**
	 * 对账单DTO
	 */
	private PartnerStatementOfAwardDto partnerStatementOfAwardDto;
	/**
	 * 导出对账单列头英文名称
	 */
	private String[] arrayColumns;
	/**
	 * 导出对账单列头中文名称
	 */
	private String[] arrayColumnNames;

	public PartnerStatementOfAwardDto getPartnerStatementOfAwardDto() {
		return partnerStatementOfAwardDto;
	}

	public void setPartnerStatementOfAwardDto(
			PartnerStatementOfAwardDto partnerStatementOfAwardDto) {
		this.partnerStatementOfAwardDto = partnerStatementOfAwardDto;
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
