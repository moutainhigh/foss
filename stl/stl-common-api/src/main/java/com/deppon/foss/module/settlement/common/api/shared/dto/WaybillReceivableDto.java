package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 查询运单的应收金额
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-15 下午5:19:06
 * @since
 * @version
 */
public class WaybillReceivableDto implements Serializable{

	/**
	 * 序列编号
	 */
	private static final long serialVersionUID = 3426861988748831314L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	

	/**
	 * 已核销金额(已收取客户的金额)
	 */
	private BigDecimal verAmount;
	
	/**
	 * 应收金额[客户还欠款金额]
	 */
	private BigDecimal unverAmount;

	/**
	 *代收货款手续费总金额
	 */
	private BigDecimal codAmount;
	/**
	 * 保价费总金额
	 */
	private BigDecimal insuranceAmount;

	/**
	 * 运费总金额
	 */
	private BigDecimal transportAmount;

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	/**
	 * @return  the verAmount
	 */
	public BigDecimal getVerAmount() {
		return verAmount;
	}

	
	/**
	 * @param verAmount the verAmount to set
	 */
	public void setVerAmount(BigDecimal verAmount) {
		this.verAmount = verAmount;
	}

	
	/**
	 * @return  the unverAmount
	 */
	public BigDecimal getUnverAmount() {
		return unverAmount;
	}


	public void setUnverAmount(BigDecimal unverAmount) {
		this.unverAmount = unverAmount;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getTransportAmount() {
		return transportAmount;
	}

	public void setTransportAmount(BigDecimal transportAmount) {
		this.transportAmount = transportAmount;
	}
}
