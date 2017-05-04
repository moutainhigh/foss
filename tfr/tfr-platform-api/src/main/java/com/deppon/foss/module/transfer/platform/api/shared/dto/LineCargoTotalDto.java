package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LineCargoTotalDto implements Serializable {

	private static final long serialVersionUID = 8446409711709090145L;

	private int totalVote;

	private int totalQty;

	private BigDecimal totalWeight;

	private BigDecimal totalVolume;

	public int getTotalVote() {
		return totalVote;
	}

	public void setTotalVote(int totalVote) {
		this.totalVote = totalVote;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	@Override
	public String toString() {
		return "LineCargoTotalDto [totalVote=" + totalVote + ", totalQty="
				+ totalQty + ", totalWeight=" + totalWeight + ", totalVolume="
				+ totalVolume + "]";
	}

}
