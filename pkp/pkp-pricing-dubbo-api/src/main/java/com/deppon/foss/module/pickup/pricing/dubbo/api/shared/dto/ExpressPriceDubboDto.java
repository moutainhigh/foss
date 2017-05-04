package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-24 上午9:32:44,content: </p>
 * @author 316759 
 * @date 2017-2-24 上午9:32:44
 * @since
 * @version
 */
public class ExpressPriceDubboDto implements Serializable {
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal groundPrice;
    
    private BigDecimal lowerGround;
    
    private BigDecimal upperGround;
    
    private BigDecimal rateOfStage1;
    
    private BigDecimal lowerOfStage1;
    
    private BigDecimal upperOfStage1;
    
    private BigDecimal rateOfStage2;
    
    private BigDecimal lowerOfStage2;
    
    private BigDecimal upperOfStage2;

	public BigDecimal getGroundPrice() {
		return groundPrice;
	}

	public void setGroundPrice(BigDecimal groundPrice) {
		this.groundPrice = groundPrice;
	}

	public BigDecimal getLowerGround() {
		return lowerGround;
	}

	public void setLowerGround(BigDecimal lowerGround) {
		this.lowerGround = lowerGround;
	}

	public BigDecimal getUpperGround() {
		return upperGround;
	}

	public void setUpperGround(BigDecimal upperGround) {
		this.upperGround = upperGround;
	}

	public BigDecimal getRateOfStage1() {
		return rateOfStage1;
	}

	public void setRateOfStage1(BigDecimal rateOfStage1) {
		this.rateOfStage1 = rateOfStage1;
	}

	public BigDecimal getLowerOfStage1() {
		return lowerOfStage1;
	}

	public void setLowerOfStage1(BigDecimal lowerOfStage1) {
		this.lowerOfStage1 = lowerOfStage1;
	}

	public BigDecimal getUpperOfStage1() {
		return upperOfStage1;
	}

	public void setUpperOfStage1(BigDecimal upperOfStage1) {
		this.upperOfStage1 = upperOfStage1;
	}

	public BigDecimal getRateOfStage2() {
		return rateOfStage2;
	}

	public void setRateOfStage2(BigDecimal rateOfStage2) {
		this.rateOfStage2 = rateOfStage2;
	}

	public BigDecimal getLowerOfStage2() {
		return lowerOfStage2;
	}

	public void setLowerOfStage2(BigDecimal lowerOfStage2) {
		this.lowerOfStage2 = lowerOfStage2;
	}

	public BigDecimal getUpperOfStage2() {
		return upperOfStage2;
	}

	public void setUpperOfStage2(BigDecimal upperOfStage2) {
		this.upperOfStage2 = upperOfStage2;
	}

}
