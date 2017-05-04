/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: ExpressPackageScanDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

/**
 * ExpressPackageScanDetailDto
 * @author dp-duyi
 * @date 2013-7-22 上午9:51:29
 */
public class ExpressPackageScanDetailDto extends PDAScanDetailDto{

	private static final long serialVersionUID = 6978469787490780015L;
	/**备注*/
	private String notes;
	/**运输性质编码*/
	private String transportTypeCode;
	/**发货部门名称*/
	private String receiveOrgName;
	/**收货部门名称*/
	private String reachOrgName;
	/**开单件数*/
	private int goodsQty;
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTransportTypeCode() {
		return transportTypeCode;
	}
	public void setTransportTypeCode(String transportTypeCode) {
		this.transportTypeCode = transportTypeCode;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getReachOrgName() {
		return reachOrgName;
	}
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	public int getGoodsQty() {
		return goodsQty;
	}
	public void setGoodsQty(int goodsQty) {
		this.goodsQty = goodsQty;
	}
}
