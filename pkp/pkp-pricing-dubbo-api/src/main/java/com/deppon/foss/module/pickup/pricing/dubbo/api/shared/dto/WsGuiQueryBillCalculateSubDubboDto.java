package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午3:57:16,content: </p>
 * @author 316759 
 * @date 2017-2-28 下午3:57:16
 * @since
 * @version
 */
public class WsGuiQueryBillCalculateSubDubboDto implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal insuranceAmount = BigDecimal.ZERO;
    
	private BigDecimal insuranceRate = BigDecimal.ZERO;
    
	private BigDecimal originnalCost = BigDecimal.ZERO;
    
	private String priceEntityCode;
    
	private String subType;
    
	private BigDecimal woodenVolume = BigDecimal.ZERO;

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}

	public void setInsuranceRate(BigDecimal insuranceRate) {
		this.insuranceRate = insuranceRate;
	}

	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}

	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}

	public String getPriceEntityCode() {
		return priceEntityCode;
	}

	public void setPriceEntityCode(String priceEntityCode) {
		this.priceEntityCode = priceEntityCode;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public BigDecimal getWoodenVolume() {
		return woodenVolume;
	}

	public void setWoodenVolume(BigDecimal woodenVolume) {
		this.woodenVolume = woodenVolume;
	}

}
