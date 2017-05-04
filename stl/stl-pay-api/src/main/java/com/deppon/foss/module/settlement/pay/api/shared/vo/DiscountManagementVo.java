package com.deppon.foss.module.settlement.pay.api.shared.vo;

import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountManagementDto;


public class DiscountManagementVo {
	/**
	 * 折扣单DTO
	 */
	private  DiscountManagementDto  discountManagementDto;
	/**
	 * 导出折扣单列头英文名称
	 */
	private String[] arrayColumns;
	/**
	 * 导出折扣单列头中文名称
	 */
	private String[] arrayColumnNames;
	
	public DiscountManagementDto getDiscountManagementDto() {
		return discountManagementDto;
	}
	public void setDiscountManagementDto(DiscountManagementDto discountManagementDto) {
		this.discountManagementDto = discountManagementDto;
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
