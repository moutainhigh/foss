package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SendingWayBillInfoResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1383517969860274925L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 交接单号
	 */
	private String handoverBillNo;
	/**
	 * 开单时间
	 */
	private Date billing;
	/**
	 * 开单件数
	 */
	private int waybillQty;
	/* 重量 */
	private BigDecimal weight;
	/* 体积 */
	private BigDecimal volume;
	
	/* 产品类型 */
	private String transportType;
	
	//到达区名称
    private String arriveDistName;
    //出发时间
    private Date departTime;
    //预计达到时间
    private Date arriveTime;
    /**
     * 操作部门编码
     */
    private String operationOrgCode;
    
    
    
    
    
    
    
	public String getOperationOrgCode() {
		return operationOrgCode;
	}
	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getHandoverBillNo() {
		return handoverBillNo;
	}
	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}
	public Date getBilling() {
		return billing;
	}
	public void setBilling(Date billing) {
		this.billing = billing;
	}
	public int getWaybillQty() {
		return waybillQty;
	}
	public void setWaybillQty(int waybillQty) {
		this.waybillQty = waybillQty;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getArriveDistName() {
		return arriveDistName;
	}
	public void setArriveDistName(String arriveDistName) {
		this.arriveDistName = arriveDistName;
	}
	public Date getDepartTime() {
		return departTime;
	}
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
}
