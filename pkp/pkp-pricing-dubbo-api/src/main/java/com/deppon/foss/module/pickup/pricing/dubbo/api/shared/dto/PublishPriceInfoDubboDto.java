package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-24 上午9:40:21,content: </p>
 * @author 316759 
 * @date 2017-2-24 上午9:40:21
 * @since
 * @version
 */
public class PublishPriceInfoDubboDto implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String productCode;
	
	private String productName;
	
	private List<LttRateDubboDto> lttRates;
	
	private ProductAgingDubboDto speed;
	
	private GeneralPriceDubboDto generateRate;
	
	private ExpressPriceDubboDto expressRate;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<LttRateDubboDto> getLttRates() {
		if(lttRates == null){
			lttRates = new ArrayList<LttRateDubboDto>();
		}
		return lttRates;
	}

	public void setLttRates(List<LttRateDubboDto> lttRates) {
		this.lttRates = lttRates;
	}

	public ProductAgingDubboDto getSpeed() {
		return speed;
	}

	public void setSpeed(ProductAgingDubboDto speed) {
		this.speed = speed;
	}

	public GeneralPriceDubboDto getGenerateRate() {
		return generateRate;
	}

	public void setGenerateRate(GeneralPriceDubboDto generateRate) {
		this.generateRate = generateRate;
	}

	public ExpressPriceDubboDto getExpressRate() {
		return expressRate;
	}

	public void setExpressRate(ExpressPriceDubboDto expressRate) {
		this.expressRate = expressRate;
	}

}
