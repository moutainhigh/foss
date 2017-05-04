/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;

public class PartnerPayStatementVo {
	/**
	 * 对账单DTO
	 */
	private PartnerPayStatementDto partnerPayStatementDto;
	/**
	 * 导出对账单列头英文名称
	 */
	private String[] arrayColumns;
	/**
	 * 导出对账单列头中文名称
	 */
	private String[] arrayColumnNames;
	
	/**
	 * 接受前台传入的对账单实体转化的字符串 主要为导出pdf服务，因为其不能用jsonData传递
	 */
	private String statementofAccountStr;
	/**
	 * 对账单明细实体转化的字符串
	 * @return
	 */
	private String statementofAccountDetailStr;

	public PartnerPayStatementDto getPartnerPayStatementDto() {
		return partnerPayStatementDto;
	}

	public void setPartnerPayStatementDto(
			PartnerPayStatementDto partnerPayStatementDto) {
		this.partnerPayStatementDto = partnerPayStatementDto;
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

	public String getStatementofAccountStr() {
		return statementofAccountStr;
	}

	public void setStatementofAccountStr(String statementofAccountStr) {
		this.statementofAccountStr = statementofAccountStr;
	}

	public String getStatementofAccountDetailStr() {
		return statementofAccountDetailStr;
	}

	public void setStatementofAccountDetailStr(String statementofAccountDetailStr) {
		this.statementofAccountDetailStr = statementofAccountDetailStr;
	}
	
	
}
