package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;

/**
 * 
 * 运单费用明细打印dto
 * @author 038590-foss-wanghui
 * @date 2013-4-16 下午1:47:09
 */
public class WaybillChargeDtlPrintDto extends WaybillChargeDtlEntity {

	/**
	 * seq
	 */
	private static final long serialVersionUID = 1928452789185632584L;

	// 费用名称
	private String pricingEntryName;

	/**
	 * Gets the pricingEntryName.
	 * 
	 * @return the pricingEntryName
	 */
	public String getPricingEntryName() {
		return pricingEntryName;
	}

	/**
	 * Sets the pricingEntryName.
	 * 
	 * @param pricingEntryName the pricingEntryName to see
	 */
	public void setPricingEntryName(String pricingEntryName) {
		this.pricingEntryName = pricingEntryName;
	}
	
}
