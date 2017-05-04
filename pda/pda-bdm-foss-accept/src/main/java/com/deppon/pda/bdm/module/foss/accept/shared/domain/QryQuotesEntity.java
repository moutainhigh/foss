package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file QryQuotesEntity.java
 * @description 承运报价返回对象
 * @author ChenLiang
 * @created 2012-12-31 下午3:03:00
 * @version 1.0
 */
public class QryQuotesEntity implements Serializable {

	private static final long serialVersionUID = 2282384726264075754L;

	// 运费折扣费率
	private BigDecimal chargeRebate;
	// 代收货款费率
	private BigDecimal agentGathRate;
	// 保价费率
	private BigDecimal insureDpriceRate;
	// 接货费率
	private BigDecimal receivePriceRate;
	//是否月结
	private String isMonthEnd;
	//申请欠款额度
	private double arrearsAmount;
	public BigDecimal getChargeRebate() {
		return chargeRebate;
	}

	public void setChargeRebate(BigDecimal chargeRebate) {
		this.chargeRebate = chargeRebate;
	}

	public BigDecimal getAgentGathRate() {
		return agentGathRate;
	}

	public void setAgentGathRate(BigDecimal agentGathRate) {
		this.agentGathRate = agentGathRate;
	}

	public BigDecimal getInsureDpriceRate() {
		return insureDpriceRate;
	}

	public void setInsureDpriceRate(BigDecimal insureDpriceRate) {
		this.insureDpriceRate = insureDpriceRate;
	}

	public BigDecimal getReceivePriceRate() {
		return receivePriceRate;
	}

	public void setReceivePriceRate(BigDecimal receivePriceRate) {
		this.receivePriceRate = receivePriceRate;
	}

	public String getIsMonthEnd() {
		return isMonthEnd;
	}

	public void setIsMonthEnd(String isMonthEnd) {
		this.isMonthEnd = isMonthEnd;
	}

	public double getArrearsAmount() {
		return arrearsAmount;
	}

	public void setArrearsAmount(double arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}
	
}
