package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;

/**
 * 现金收入明细报表查询和返回值需要的VO对象
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-7 下午5:10:11
 * @since
 * @version
 */
public class CashIncomeStatementsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4551166436577728577L;

	/**
	 * 查询类型：日期查询/运单号查询集合/对账单号查询集合/预收单号集合/还款单号集合
	 */
	private String queryType;

	/**
	 * 查询使用dto
	 */
	private CashIncomeStatementsQueryDto dto;

	/**
	 * 存放现金收入报表明细集合
	 */
	private List<CashIncomeStatementsDto> list = new ArrayList<CashIncomeStatementsDto>();

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount=BigDecimal.ZERO;

	/**
	 * 银行卡总金额
	 */
	private BigDecimal cdTotalAmount=BigDecimal.ZERO;

	/**
	 * 现金总金额
	 */
	private BigDecimal chTotalAmount=BigDecimal.ZERO;

	/**
	 * 电汇总金额
	 */
	private BigDecimal ttTotalAmount=BigDecimal.ZERO;

	/**
	 * 支票总金额
	 */
	private BigDecimal ntTotalAmount=BigDecimal.ZERO;
	
	/**
	 * 网上支付总金额
	 */
	private BigDecimal olTotalAmount=BigDecimal.ZERO;

	/**
	 * @return the queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType
	 *            the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return the dto
	 */
	public CashIncomeStatementsQueryDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            the dto to set
	 */
	public void setDto(CashIncomeStatementsQueryDto dto) {
		this.dto = dto;
	}

	/**
	 * @return the list
	 */
	public List<CashIncomeStatementsDto> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<CashIncomeStatementsDto> list) {
		this.list = list;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the cdTotalAmount
	 */
	public BigDecimal getCdTotalAmount() {
		return cdTotalAmount;
	}

	/**
	 * @param cdTotalAmount
	 *            the cdTotalAmount to set
	 */
	public void setCdTotalAmount(BigDecimal cdTotalAmount) {
		this.cdTotalAmount = cdTotalAmount;
	}

	/**
	 * @return the chTotalAmount
	 */
	public BigDecimal getChTotalAmount() {
		return chTotalAmount;
	}

	/**
	 * @param chTotalAmount
	 *            the chTotalAmount to set
	 */
	public void setChTotalAmount(BigDecimal chTotalAmount) {
		this.chTotalAmount = chTotalAmount;
	}

	/**
	 * @return the ttTotalAmount
	 */
	public BigDecimal getTtTotalAmount() {
		return ttTotalAmount;
	}

	/**
	 * @param ttTotalAmount
	 *            the ttTotalAmount to set
	 */
	public void setTtTotalAmount(BigDecimal ttTotalAmount) {
		this.ttTotalAmount = ttTotalAmount;
	}

	/**
	 * @return the ntTotalAmount
	 */
	public BigDecimal getNtTotalAmount() {
		return ntTotalAmount;
	}

	/**
	 * @param ntTotalAmount
	 *            the ntTotalAmount to set
	 */
	public void setNtTotalAmount(BigDecimal ntTotalAmount) {
		this.ntTotalAmount = ntTotalAmount;
	}

	/**
	 * @return  the olTotalAmount
	 */
	public BigDecimal getOlTotalAmount() {
		return olTotalAmount;
	}

	/**
	 * @param olTotalAmount the olTotalAmount to set
	 */
	public void setOlTotalAmount(BigDecimal olTotalAmount) {
		this.olTotalAmount = olTotalAmount;
	}


	
}
