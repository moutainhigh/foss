package com.deppon.foss.module.settlement.agency.api.shared.vo;

import java.math.BigDecimal;

import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayInputDto;

/**
 * 包装供应商奖罚录入界面 vo
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-5-16 下午3:22:00,content:TODO </p>
 * @author 105762
 * @date 2014-5-16 下午3:22:00
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayInputVo {
	private PackingRecAndPayInputDto packingRecAndPayInputDto;

	private BigDecimal stlPackingDamageMaxAmount;

	private Double standardDamageRate;

	private String customerCode;

	private String deptCode;

	/**
	  * @return  the packingRecAndPayInputDto
	 */
	public PackingRecAndPayInputDto getPackingRecAndPayInputDto() {
		return packingRecAndPayInputDto;
	}

	/**
	 * @param packingRecAndPayInputDto the packingRecAndPayInputDto to set
	 */
	public void setPackingRecAndPayInputDto(PackingRecAndPayInputDto packingRecAndPayInputDto) {
		this.packingRecAndPayInputDto = packingRecAndPayInputDto;
	}

	/**
	  * @return  the stlPackingDamageMaxAmount
	 */
	public BigDecimal getStlPackingDamageMaxAmount() {
		return stlPackingDamageMaxAmount;
	}

	/**
	 * @param stlPackingDamageMaxAmount the stlPackingDamageMaxAmount to set
	 */
	public void setStlPackingDamageMaxAmount(BigDecimal stlPackingDamageMaxAmount) {
		this.stlPackingDamageMaxAmount = stlPackingDamageMaxAmount;
	}

	/**
	  * @return  the standardDamageRate
	 */
	public Double getStandardDamageRate() {
		return standardDamageRate;
	}

	/**
	 * @param standardDamageRate the standardDamageRate to set
	 */
	public void setStandardDamageRate(Double standardDamageRate) {
		this.standardDamageRate = standardDamageRate;
	}

	/**
	  * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	  * @return  the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

}
