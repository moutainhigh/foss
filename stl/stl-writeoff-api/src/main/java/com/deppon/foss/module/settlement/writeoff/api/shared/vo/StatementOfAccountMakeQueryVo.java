package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;

/**
 * 制作对账单查询VO类，主要负责接受前台给Action传参
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-11 下午4:48:00
 */
public class StatementOfAccountMakeQueryVo implements Serializable {

	/**
	 * VO类序列号
	 */
	private static final long serialVersionUID = -1827529959996184637L;
	/**
	 * 制作对账单查询DTO
	 */
	private StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto;
	
	/**
	 * @return statementOfAccountMakeQueryDto
	 */
	public StatementOfAccountMakeQueryDto getStatementOfAccountMakeQueryDto() {
		return statementOfAccountMakeQueryDto;
	}
	
	/**
	 * @param statementOfAccountMakeQueryDto
	 */
	public void setStatementOfAccountMakeQueryDto(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		this.statementOfAccountMakeQueryDto = statementOfAccountMakeQueryDto;
	}
	

}
