package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashPaymentReportDto;

/**
 * 现金支出报表Vo
 * 
 * @author foss-zhangxiaohui
 * @date Dec 11, 2012 2:34:09 PM
 */
public class CashPaymentReportVo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2530876171172364978L;

	/**
	 * dto实例传参
	 */
	private CashPaymentReportDto dto;

	/**
	 * 付款单实体List
	 */
	private List<BillPaymentEntity> billPaymentList;

	/**
	 * 现金总金额
	 */
	private BigDecimal totalCashAmount;

	/**
	 * 电汇总金额
	 */
	private BigDecimal totalTelegraphicAmount;

	/**
	 * 银行卡总金额
	 */
	private BigDecimal totalBankCardAmount;

	/**
	 * 支票总金额
	 */
	private BigDecimal totalChequeAmount;

	/**
	 * 已审核总金额
	 */
	private BigDecimal totalAuditAmount;

	/**
	 * 未审核总金额
	 */
	private BigDecimal totalUnauditAmount;

	/**
	 * @return dto
	 */
	public CashPaymentReportDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 */
	public void setDto(CashPaymentReportDto dto) {
		this.dto = dto;
	}

	/**
	 * @return billPaymentList
	 */
	public List<BillPaymentEntity> getBillPaymentList() {
		return billPaymentList;
	}

	/**
	 * @param billPaymentList
	 */
	public void setBillPaymentList(List<BillPaymentEntity> billPaymentList) {
		this.billPaymentList = billPaymentList;
	}

	/**
	 * @return totalCashAmount
	 */
	public BigDecimal getTotalCashAmount() {
		return totalCashAmount;
	}

	/**
	 * @param totalCashAmount
	 */
	public void setTotalCashAmount(BigDecimal totalCashAmount) {
		this.totalCashAmount = totalCashAmount;
	}

	/**
	 * @return totalTelegraphicAmount
	 */
	public BigDecimal getTotalTelegraphicAmount() {
		return totalTelegraphicAmount;
	}

	/**
	 * @param totalTelegraphicAmount
	 */
	public void setTotalTelegraphicAmount(BigDecimal totalTelegraphicAmount) {
		this.totalTelegraphicAmount = totalTelegraphicAmount;
	}

	/**
	 * @return totalBankCardAmount
	 */
	public BigDecimal getTotalBankCardAmount() {
		return totalBankCardAmount;
	}

	/**
	 * @param totalBankCardAmount
	 */
	public void setTotalBankCardAmount(BigDecimal totalBankCardAmount) {
		this.totalBankCardAmount = totalBankCardAmount;
	}

	/**
	 * @return totalChequeAmount
	 */
	public BigDecimal getTotalChequeAmount() {
		return totalChequeAmount;
	}

	/**
	 * @param totalChequeAmount
	 */
	public void setTotalChequeAmount(BigDecimal totalChequeAmount) {
		this.totalChequeAmount = totalChequeAmount;
	}

	/**
	 * @return totalAuditAmount
	 */
	public BigDecimal getTotalAuditAmount() {
		return totalAuditAmount;
	}

	/**
	 * @param totalAuditAmount
	 */
	public void setTotalAuditAmount(BigDecimal totalAuditAmount) {
		this.totalAuditAmount = totalAuditAmount;
	}

	/**
	 * @return totalUnauditAmount
	 */
	public BigDecimal getTotalUnauditAmount() {
		return totalUnauditAmount;
	}

	/**
	 * @param totalUnauditAmount
	 */
	public void setTotalUnauditAmount(BigDecimal totalUnauditAmount) {
		this.totalUnauditAmount = totalUnauditAmount;
	}

}
