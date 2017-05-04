package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 部门财务信息dto
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-26 下午8:43:04
 * @since
 * @version
 */
public class OrgSettlementInfoDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5109083061361280171L;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	
	/**
	 * 应付金额
	 */
    private BigDecimal payableAmount;
    
    
    /**
     * 应收金额
     */
    private BigDecimal receivableAmount;
    
    /**
     * 预收金额
     */
    private BigDecimal depositReceivedAmount;
    
    /**
     * 空运预付金额
     */
    private BigDecimal advancedPaymentAmount;

	
	/**
	 * @return  the type
	 */
	public String getType() {
		return type;
	}

	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	/**
	 * @return  the payableAmount
	 */
	public BigDecimal getPayableAmount() {
		return payableAmount;
	}

	
	/**
	 * @param payableAmount the payableAmount to set
	 */
	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}

	
	/**
	 * @return  the receivableAmount
	 */
	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	
	/**
	 * @param receivableAmount the receivableAmount to set
	 */
	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	
	/**
	 * @return  the depositReceivedAmount
	 */
	public BigDecimal getDepositReceivedAmount() {
		return depositReceivedAmount;
	}

	
	/**
	 * @param depositReceivedAmount the depositReceivedAmount to set
	 */
	public void setDepositReceivedAmount(BigDecimal depositReceivedAmount) {
		this.depositReceivedAmount = depositReceivedAmount;
	}

	
	/**
	 * @return  the advancedPaymentAmount
	 */
	public BigDecimal getAdvancedPaymentAmount() {
		return advancedPaymentAmount;
	}

	
	/**
	 * @param advancedPaymentAmount the advancedPaymentAmount to set
	 */
	public void setAdvancedPaymentAmount(BigDecimal advancedPaymentAmount) {
		this.advancedPaymentAmount = advancedPaymentAmount;
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
}
