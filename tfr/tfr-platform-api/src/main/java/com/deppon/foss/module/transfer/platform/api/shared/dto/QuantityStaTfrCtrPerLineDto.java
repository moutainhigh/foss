package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class QuantityStaTfrCtrPerLineDto implements Serializable {

	private static final long serialVersionUID = 3011035163594541593L;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

	/**
	 * 从外场出发(到达)线路的下(上)一部门编码
	 */
	private String relevantOrgCode;

	/**
	 * 货量类型
	 */
	private String dataType;

	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;

	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;

	/**
	 * 总票数
	 */
	private Integer qtyTotal;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getRelevantOrgCode() {
		return relevantOrgCode;
	}

	public void setRelevantOrgCode(String relevantOrgCode) {
		this.relevantOrgCode = relevantOrgCode;
	}

	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public Integer getQtyTotal() {
		return qtyTotal;
	}

	public void setQtyTotal(Integer qtyTotal) {
		this.qtyTotal = qtyTotal;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
