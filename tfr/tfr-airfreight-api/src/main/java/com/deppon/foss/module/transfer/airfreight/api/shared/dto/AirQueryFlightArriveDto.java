package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveEntity;
/**
 * 查询空运到达的Dto
 * @author zwd 200968
 * @date 2015-06-17
 * 
 */
public class AirQueryFlightArriveDto extends AirQueryFlightArriveEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6874512456398218L;
 

	private String airFlightArriveId;
	
	private String airFlightArriveDetailId;
	private String flightArriveTypeName;
	
	public String getFlightArriveTypeName() {
		return flightArriveTypeName;
	}
	public void setFlightArriveTypeName(String flightArriveTypeName) {
		this.flightArriveTypeName = flightArriveTypeName;
	}
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
	
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
    /**
     * 到达开始时间
     */
	private Date beginTime;
	/**
	 * 到达结束时间
	 */
	private Date endTime;
	
	public String getAirFlightArriveId() {
		return airFlightArriveId;
	}
	public void setAirFlightArriveId(String airFlightArriveId) {
		this.airFlightArriveId = airFlightArriveId;
	}
	public String getAirFlightArriveDetailId() {
		return airFlightArriveDetailId;
	}
	public void setAirFlightArriveDetailId(String airFlightArriveDetailId) {
		this.airFlightArriveDetailId = airFlightArriveDetailId;
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
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
