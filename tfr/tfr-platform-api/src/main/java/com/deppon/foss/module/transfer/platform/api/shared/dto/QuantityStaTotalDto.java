package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class QuantityStaTotalDto implements Serializable {

	private static final long serialVersionUID = 1728511240504071616L;

	/**
	 * 总
	 */
	private BigDecimal weightTotal;
	private BigDecimal volumeTotal;
	private Integer qtyTotal;

	/**
	 * 卡航
	 */
	private BigDecimal weightFlf;
	private BigDecimal volumeFlf;
	private Integer qtyFlf;

	/**
	 * 城运
	 */
	private BigDecimal weightFsf;
	private BigDecimal volumeFsf;
	private Integer qtyFsf;

	/**
	 * 快递
	 */
	private BigDecimal weightExp;
	private BigDecimal volumeExp;
	private Integer qtyExp;

	/**
	 * 上一部门未交接
	 */
	private BigDecimal weightNoHandOver;
	private BigDecimal volumeNoHandOver;
	private Integer qtyNoHandOver;

	/**
	 * 上一部门到当前外场在途
	 */
	private BigDecimal weightOnTheWay;
	private BigDecimal volumeOnTheWay;
	private Integer qtyOnTheWay;

	/**
	 * 在当前外场库存
	 */
	private BigDecimal weightInStock;
	private BigDecimal volumeInStock;
	private Integer qtyInStock;

	/**
	 * 预测未开单
	 */
	private BigDecimal weightFcstNoBilling;
	private BigDecimal volumeFcstNoBilling;
	private Integer qtyFcstNoBilling;

	/**
	 * 预测总
	 */
	private BigDecimal weightFcstTotal;
	private BigDecimal volumeFcstTotal;
	private Integer qtyFcstTotal;

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

	public BigDecimal getWeightFlf() {
		return weightFlf;
	}

	public void setWeightFlf(BigDecimal weightFlf) {
		this.weightFlf = weightFlf;
	}

	public BigDecimal getVolumeFlf() {
		return volumeFlf;
	}

	public void setVolumeFlf(BigDecimal volumeFlf) {
		this.volumeFlf = volumeFlf;
	}

	public Integer getQtyFlf() {
		return qtyFlf;
	}

	public void setQtyFlf(Integer qtyFlf) {
		this.qtyFlf = qtyFlf;
	}

	public BigDecimal getWeightFsf() {
		return weightFsf;
	}

	public void setWeightFsf(BigDecimal weightFsf) {
		this.weightFsf = weightFsf;
	}

	public BigDecimal getVolumeFsf() {
		return volumeFsf;
	}

	public void setVolumeFsf(BigDecimal volumeFsf) {
		this.volumeFsf = volumeFsf;
	}

	public Integer getQtyFsf() {
		return qtyFsf;
	}

	public void setQtyFsf(Integer qtyFsf) {
		this.qtyFsf = qtyFsf;
	}

	public BigDecimal getWeightExp() {
		return weightExp;
	}

	public void setWeightExp(BigDecimal weightExp) {
		this.weightExp = weightExp;
	}

	public BigDecimal getVolumeExp() {
		return volumeExp;
	}

	public void setVolumeExp(BigDecimal volumeExp) {
		this.volumeExp = volumeExp;
	}

	public Integer getQtyExp() {
		return qtyExp;
	}

	public void setQtyExp(Integer qtyExp) {
		this.qtyExp = qtyExp;
	}

	public BigDecimal getWeightNoHandOver() {
		return weightNoHandOver;
	}

	public void setWeightNoHandOver(BigDecimal weightNoHandOver) {
		this.weightNoHandOver = weightNoHandOver;
	}

	public BigDecimal getVolumeNoHandOver() {
		return volumeNoHandOver;
	}

	public void setVolumeNoHandOver(BigDecimal volumeNoHandOver) {
		this.volumeNoHandOver = volumeNoHandOver;
	}

	public Integer getQtyNoHandOver() {
		return qtyNoHandOver;
	}

	public void setQtyNoHandOver(Integer qtyNoHandOver) {
		this.qtyNoHandOver = qtyNoHandOver;
	}

	public BigDecimal getWeightOnTheWay() {
		return weightOnTheWay;
	}

	public void setWeightOnTheWay(BigDecimal weightOnTheWay) {
		this.weightOnTheWay = weightOnTheWay;
	}

	public BigDecimal getVolumeOnTheWay() {
		return volumeOnTheWay;
	}

	public void setVolumeOnTheWay(BigDecimal volumeOnTheWay) {
		this.volumeOnTheWay = volumeOnTheWay;
	}

	public Integer getQtyOnTheWay() {
		return qtyOnTheWay;
	}

	public void setQtyOnTheWay(Integer qtyOnTheWay) {
		this.qtyOnTheWay = qtyOnTheWay;
	}

	public BigDecimal getWeightInStock() {
		return weightInStock;
	}

	public void setWeightInStock(BigDecimal weightInStock) {
		this.weightInStock = weightInStock;
	}

	public BigDecimal getVolumeInStock() {
		return volumeInStock;
	}

	public void setVolumeInStock(BigDecimal volumeInStock) {
		this.volumeInStock = volumeInStock;
	}

	public Integer getQtyInStock() {
		return qtyInStock;
	}

	public void setQtyInStock(Integer qtyInStock) {
		this.qtyInStock = qtyInStock;
	}

	public BigDecimal getWeightFcstNoBilling() {
		return weightFcstNoBilling;
	}

	public void setWeightFcstNoBilling(BigDecimal weightFcstNoBilling) {
		this.weightFcstNoBilling = weightFcstNoBilling;
	}

	public BigDecimal getVolumeFcstNoBilling() {
		return volumeFcstNoBilling;
	}

	public void setVolumeFcstNoBilling(BigDecimal volumeFcstNoBilling) {
		this.volumeFcstNoBilling = volumeFcstNoBilling;
	}

	public Integer getQtyFcstNoBilling() {
		return qtyFcstNoBilling;
	}

	public void setQtyFcstNoBilling(Integer qtyFcstNoBilling) {
		this.qtyFcstNoBilling = qtyFcstNoBilling;
	}

	public BigDecimal getWeightFcstTotal() {
		return weightFcstTotal;
	}

	public void setWeightFcstTotal(BigDecimal weightFcstTotal) {
		this.weightFcstTotal = weightFcstTotal;
	}

	public BigDecimal getVolumeFcstTotal() {
		return volumeFcstTotal;
	}

	public void setVolumeFcstTotal(BigDecimal volumeFcstTotal) {
		this.volumeFcstTotal = volumeFcstTotal;
	}

	public Integer getQtyFcstTotal() {
		return qtyFcstTotal;
	}

	public void setQtyFcstTotal(Integer qtyFcstTotal) {
		this.qtyFcstTotal = qtyFcstTotal;
	}

	@Override
	public String toString() {
		return "QuantityStaTotalDto [weightTotal=" + weightTotal
				+ ", volumeTotal=" + volumeTotal + ", qtyTotal=" + qtyTotal
				+ ", weightFlf=" + weightFlf + ", volumeFlf=" + volumeFlf
				+ ", qtyFlf=" + qtyFlf + ", weightFsf=" + weightFsf
				+ ", volumeFsf=" + volumeFsf + ", qtyFsf=" + qtyFsf
				+ ", weightExp=" + weightExp + ", volumeExp=" + volumeExp
				+ ", qtyExp=" + qtyExp + ", weightNoHandOver="
				+ weightNoHandOver + ", volumeNoHandOver=" + volumeNoHandOver
				+ ", qtyNoHandOver=" + qtyNoHandOver + ", weightOnTheWay="
				+ weightOnTheWay + ", volumeOnTheWay=" + volumeOnTheWay
				+ ", qtyOnTheWay=" + qtyOnTheWay + ", weightInStock="
				+ weightInStock + ", volumeInStock=" + volumeInStock
				+ ", qtyInStock=" + qtyInStock + ", weightFcstNoBilling="
				+ weightFcstNoBilling + ", volumeFcstNoBilling="
				+ volumeFcstNoBilling + ", qtyFcstNoBilling="
				+ qtyFcstNoBilling + ", weightFcstTotal=" + weightFcstTotal
				+ ", volumeFcstTotal=" + volumeFcstTotal + ", qtyFcstTotal="
				+ qtyFcstTotal + "]";
	}
}
