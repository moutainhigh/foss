package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PDAOrderTaskBillEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
    //运单号
	private String waybillNo;
	
	//点单件数
	private BigDecimal orderGoodsQty;
	
	//开单件数
	private BigDecimal createBillQty;
	
	//已配件数
	private BigDecimal alAssembleQty;
	
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	public BigDecimal getCreateBillQty() {
		return createBillQty;
	}
	public void setCreateBillQty(BigDecimal createBillQty) {
		this.createBillQty = createBillQty;
	}
	public BigDecimal getAlAssembleQty() {
		return alAssembleQty;
	}
	public void setAlAssembleQty(BigDecimal alAssembleQty) {
		this.alAssembleQty = alAssembleQty;
	}
	public BigDecimal getOrderGoodsQty() {
		return orderGoodsQty;
	}
	public void setOrderGoodsQty(BigDecimal orderGoodsQty) {
		this.orderGoodsQty = orderGoodsQty;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
