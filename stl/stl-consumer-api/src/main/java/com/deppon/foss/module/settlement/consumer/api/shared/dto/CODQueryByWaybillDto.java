package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * 
 * 按运单号查询代收货款DTO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-12-20 下午7:12:37
 */
public class CODQueryByWaybillDto {

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 有效性
	 */
	private String active;

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

}
