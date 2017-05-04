package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-24 上午9:21:01,content: </p>
 * @author 316759 
 * @date 2017-2-24 上午9:21:01
 * @since
 * @version
 */
public class GeneralPriceDubboDto implements Serializable {
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal heavyRate;
    
    private BigDecimal receiveHeavyRate;
    
    private BigDecimal lightRate;
    
    private BigDecimal receiveLightRate;
    
    private BigDecimal lowestPrice;
    
    private BigDecimal receiveLowestPrice;
    
    private String originatingArea;
    
    private String destinationArea;

	public BigDecimal getHeavyRate() {
		return heavyRate;
	}

	public void setHeavyRate(BigDecimal heavyRate) {
		this.heavyRate = heavyRate;
	}

	public BigDecimal getReceiveHeavyRate() {
		return receiveHeavyRate;
	}

	public void setReceiveHeavyRate(BigDecimal receiveHeavyRate) {
		this.receiveHeavyRate = receiveHeavyRate;
	}

	public BigDecimal getLightRate() {
		return lightRate;
	}

	public void setLightRate(BigDecimal lightRate) {
		this.lightRate = lightRate;
	}

	public BigDecimal getReceiveLightRate() {
		return receiveLightRate;
	}

	public void setReceiveLightRate(BigDecimal receiveLightRate) {
		this.receiveLightRate = receiveLightRate;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public BigDecimal getReceiveLowestPrice() {
		return receiveLowestPrice;
	}

	public void setReceiveLowestPrice(BigDecimal receiveLowestPrice) {
		this.receiveLowestPrice = receiveLowestPrice;
	}

	public String getOriginatingArea() {
		return originatingArea;
	}

	public void setOriginatingArea(String originatingArea) {
		this.originatingArea = originatingArea;
	}

	public String getDestinationArea() {
		return destinationArea;
	}

	public void setDestinationArea(String destinationArea) {
		this.destinationArea = destinationArea;
	}

}
