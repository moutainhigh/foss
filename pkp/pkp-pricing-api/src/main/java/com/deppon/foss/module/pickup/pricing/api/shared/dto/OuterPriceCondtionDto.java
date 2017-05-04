package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;

public class OuterPriceCondtionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//目的站
	private String partialLineCode;
	
	//外发外场
	private String outFieldCode;
	
	//运输类型
	private String productCode;
	
	//数据状态
	private String active;

	public String getPartialLineCode() {
		return partialLineCode;
	}

	public void setPartialLineCode(String partialLineCode) {
		this.partialLineCode = partialLineCode;
	}

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
