package com.deppon.foss.module.pickup.pda.api.shared.dto;

/** 
 * @ClassName: PdaFinancialParamDto 
 * @Description: Pda查询代收款和到付款传入参数Dto 
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-29 下午5:54:16 
 *  
 */
public class PdaFinancialParamDto {
	
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 获取waybillNo
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置waybillNo
	 * @param waybillNo 要设置的waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
}
