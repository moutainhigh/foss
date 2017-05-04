package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

/**
 * @ClassName ReturnBillingEWaybillEntity.java 
 * @Description 返货开单下拉明细
 * @author 201638
 * @date 2015-3-9
 */
public class ReturnBillingEntity  implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	//目的站
	private String destination;
	//运输类型
    private String transportType;
    //件数
  	private String pieces;	
	//返货信息（类似于备注，字段长一点）
    private String returnGoodsInfo;
    private String wayBillCode;
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getPieces() {
		return pieces;
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getReturnGoodsInfo() {
		return returnGoodsInfo;
	}
	public void setReturnGoodsInfo(String returnGoodsInfo) {
		this.returnGoodsInfo = returnGoodsInfo;
	}
	public String getWayBillCode() {
		return wayBillCode;
	}
	public void setWayBillCode(String wayBillCode) {
		this.wayBillCode = wayBillCode;
	}
    
	
}
