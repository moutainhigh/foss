package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 对接cubc查询代收货款
 * @author 269044
 * @date 2017-04-07
 *
 */
public class CodQueryToCubcResponse implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 代收实体集合
	 */
	private List<CodDO> codAuditList;
	
	/**
	 * 总条数
	 */
	private Long totalNum;
	
	//代收货款总金额
    private BigDecimal totalAmount;
    
    //手续费总金额
    private BigDecimal codFeeTotal;
    
    //应付总金额
    private BigDecimal payableAmountTotal;
    
    //已付总金额
    private BigDecimal payCodAmountTotal;
	

	public List<CodDO> getCodAuditList() {
		return codAuditList;
	}

	public void setCodAuditList(List<CodDO> codAuditList) {
		this.codAuditList = codAuditList;
	}

	/**
	 * @return the totalNum
	 */
	public Long getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the codFeeTotal
	 */
	public BigDecimal getCodFeeTotal() {
		return codFeeTotal;
	}

	/**
	 * @param codFeeTotal the codFeeTotal to set
	 */
	public void setCodFeeTotal(BigDecimal codFeeTotal) {
		this.codFeeTotal = codFeeTotal;
	}

	/**
	 * @return the payableAmountTotal
	 */
	public BigDecimal getPayableAmountTotal() {
		return payableAmountTotal;
	}

	/**
	 * @param payableAmountTotal the payableAmountTotal to set
	 */
	public void setPayableAmountTotal(BigDecimal payableAmountTotal) {
		this.payableAmountTotal = payableAmountTotal;
	}

	/**
	 * @return the payCodAmountTotal
	 */
	public BigDecimal getPayCodAmountTotal() {
		return payCodAmountTotal;
	}

	/**
	 * @param payCodAmountTotal the payCodAmountTotal to set
	 */
	public void setPayCodAmountTotal(BigDecimal payCodAmountTotal) {
		this.payCodAmountTotal = payCodAmountTotal;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
