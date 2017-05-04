package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
public class OuterEffectivePlanConditionDto implements Serializable {
	private static final long serialVersionUID = 8867478425054852318L;
	
	private String partialLineCode;
	private String outFieldCode;
	private String productCode;
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
