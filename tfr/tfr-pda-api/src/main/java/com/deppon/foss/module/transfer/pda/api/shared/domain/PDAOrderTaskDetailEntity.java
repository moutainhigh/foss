package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PDAOrderTaskDetailEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//总票数
	private Long totWaybillQty;
		
	//总件数
	private Long totGoodsQty;
		
	//总重量
	private BigDecimal totWeight;
		
	//总体积
	private BigDecimal totVolume;
	
	/**点单明细*/
	private List<PDAOrderTaskBillEntity> bills;

	public Long getTotWaybillQty() {
		return totWaybillQty;
	}

	public void setTotWaybillQty(Long totWaybillQty) {
		this.totWaybillQty = totWaybillQty;
	}

	public Long getTotGoodsQty() {
		return totGoodsQty;
	}

	public void setTotGoodsQty(Long totGoodsQty) {
		this.totGoodsQty = totGoodsQty;
	}

	public BigDecimal getTotWeight() {
		return totWeight;
	}

	public void setTotWeight(BigDecimal totWeight) {
		this.totWeight = totWeight;
	}

	public BigDecimal getTotVolume() {
		return totVolume;
	}

	public void setTotVolume(BigDecimal totVolume) {
		this.totVolume = totVolume;
	}

	public List<PDAOrderTaskBillEntity> getBills() {
		return bills;
	}

	public void setBills(List<PDAOrderTaskBillEntity> bills) {
		this.bills = bills;
	}

}
