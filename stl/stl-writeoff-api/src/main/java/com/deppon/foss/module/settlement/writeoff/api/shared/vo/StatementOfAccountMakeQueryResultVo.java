package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementToPaymentResultDto;

/**
 * 制作对账单时，返回对账单及对账单明细VO类
 * 
 * @author dp-zhangjiheng
 * @date 2012-10-11 下午6:24:38
 */
public class StatementOfAccountMakeQueryResultVo implements Serializable {

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = -8196100711549354750L;

	/**
	 * 对账单结果集的dto
	 */
	private StatementOfAccountMakeQueryResultDto statementOfAccountMakeQueryResultDto;

	/**
	 * 按对账单还款返回结果的dto
	 */
	private StatementToPaymentResultDto statementToPaymentResultDto;

	/**
	 * 当前操作类型
	 */
	private String confirmStatus;
	
	/**
	 * 发票申请状态
	 */
	private String invoiceStatus;
	
	private String applyInvoice;

	/**
	 * 接受前台传入的对账单实体转化的字符串 主要为导出pdf服务，因为其不能用jsonData传递
	 */
	private String statementofAccountStr;

	/**
	 * 导出pdf对账单明细str
	 */
	private String statementofAccountDetailStr;

	/**
	 * 导出对账单列头英文名称
	 */
	private String[] arrayColumns;

	/**
	 * 导出对账单列头中文名称
	 */
	private String[] arrayColumnNames;

	/**
	 * 导出对账单方式 byStatement byStatementDt
	 */
	private String exportType;

	/**
	 * 导出对账单明细excel
	 */
	private String statementNo;
	
	/**
	 * @return 
		statementOfAccountMakeQueryResultDto
	 */
	public StatementOfAccountMakeQueryResultDto getStatementOfAccountMakeQueryResultDto() {
		return statementOfAccountMakeQueryResultDto;
	}

	
	/**
	 * @param 
		statementOfAccountMakeQueryResultDto
	 */
	public void setStatementOfAccountMakeQueryResultDto(StatementOfAccountMakeQueryResultDto statementOfAccountMakeQueryResultDto) {
		this.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
	}

	
	/**
	 * @return 
		statementToPaymentResultDto
	 */
	public StatementToPaymentResultDto getStatementToPaymentResultDto() {
		return statementToPaymentResultDto;
	}

	
	/**
	 * @param 
		statementToPaymentResultDto
	 */
	public void setStatementToPaymentResultDto(StatementToPaymentResultDto statementToPaymentResultDto) {
		this.statementToPaymentResultDto = statementToPaymentResultDto;
	}

	
	/**
	 * @return 
		confirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}

	
	/**
	 * @param 
		confirmStatus
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	
	/**
	 * @return 
		statementofAccountStr
	 */
	public String getStatementofAccountStr() {
		return statementofAccountStr;
	}

	
	/**
	 * @param 
		statementofAccountStr
	 */
	public void setStatementofAccountStr(String statementofAccountStr) {
		this.statementofAccountStr = statementofAccountStr;
	}

	
	/**
	 * @return 
		statementofAccountDetailStr
	 */
	public String getStatementofAccountDetailStr() {
		return statementofAccountDetailStr;
	}

	
	/**
	 * @param 
		statementofAccountDetailStr
	 */
	public void setStatementofAccountDetailStr(String statementofAccountDetailStr) {
		this.statementofAccountDetailStr = statementofAccountDetailStr;
	}

	
	/**
	 * @return 
		arrayColumns
	 */
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	
	/**
	 * @param 
		arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	
	/**
	 * @return 
		arrayColumnNames
	 */
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	
	/**
	 * @param 
		arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}

	
	/**
	 * @return 
		exportType
	 */
	public String getExportType() {
		return exportType;
	}

	
	/**
	 * @param 
		exportType
	 */
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}


	/**
	 * @GET
	 * @return statementNo
	 */
	public String getStatementNo() {
		/*
		 *@get
		 *@ return statementNo
		 */
		return statementNo;
	}


	/**
	 * @SET
	 * @param statementNo
	 */
	public void setStatementNo(String statementNo) {
		/*
		 *@set
		 *@this.statementNo = statementNo
		 */
		this.statementNo = statementNo;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getApplyInvoice() {
		return applyInvoice;
	}

	public void setApplyInvoice(String applyInvoice) {
		this.applyInvoice = applyInvoice;
	}

}
