package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 查询空运到达明细实体类
 * @author zwd 200968
 * @date 2015-06-17 
 *
 */
public class AirQueryFlightArriveDetailEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2874569251486531L;

	private String airFlightArriveId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 开单件数
	 */
	private int waybillGoodsQty;
	/**
	 * 开单重量
	 */
	private BigDecimal waybillWeight;
	
	public String getAirFlightArriveId() {
		return airFlightArriveId;
	}
	public void setAirFlightArriveId(String airFlightArriveId) {
		this.airFlightArriveId = airFlightArriveId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public int getWaybillGoodsQty() {
		return waybillGoodsQty;
	}
	public void setWaybillGoodsQty(int waybillGoodsQty) {
		this.waybillGoodsQty = waybillGoodsQty;
	}
	public BigDecimal getWaybillWeight() {
		return waybillWeight;
	}
	public void setWaybillWeight(BigDecimal waybillWeight) {
		this.waybillWeight = waybillWeight;
	}

	
}
