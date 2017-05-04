package com.deppon.foss.module.transfer.pda.api.shared.dto;
//PDA派送装车运单退回dto
public class PDAWaybillReturnDto {
	//装车任务编号
	private String LoadTaskNo;
	//运单号
	private String waybillNo;
	//退回原因
	private String returnReason;
	//运输性质
	private String transportType;
	//库存件数
	private int stockQty;
	//操作件数
	private int operateQty;
	//退回类型
	private String retreatType;
	//退回内容
	private String retreatReason;
	
	
	public String getRetreatType() {
		return retreatType;
	}
	public void setRetreatType(String retreatType) {
		this.retreatType = retreatType;
	}
	public String getRetreatReason() {
		return retreatReason;
	}
	public void setRetreatReason(String retreatReason) {
		this.retreatReason = retreatReason;
	}
	public String getLoadTaskNo() {
		return LoadTaskNo;
	}
	public void setLoadTaskNo(String loadTaskNo) {
		LoadTaskNo = loadTaskNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	public int getOperateQty() {
		return operateQty;
	}
	public void setOperateQty(int operateQty) {
		this.operateQty = operateQty;
	}
	
	
	
}
