package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class QuantityStaTfrCtrTotalDto implements Serializable {

	private static final long serialVersionUID = 3011035163594541593L;

	/**
	 * 外场编码
	 */
	private String transferCenterCode;

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
