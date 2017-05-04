/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: ExpressPackageDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 快递包明细
 * @author dp-duyi
 * @date 2013-7-22 上午9:14:27
 */
public class ExpressPackageDetailDto implements Serializable{

	private static final long serialVersionUID = -5437392639463035275L;
	/**包号*/
	private String packageNo;
	/**运单号*/
	private String wayBillNo;
	/**流水号*/
	private List<String> serialNos;
	/**开单件数*/
	private int wayBillQty;
	/**库存件数*/
	private int stockQty;
	/**操作件数*/
	private int operateQty;
	/**重量*/
	private double weight;
	/**体积*/
	private double volume;
	/**货名*/
	private String goodsName;
	/**收货部门*/
	private String receiveOrgCode;
	/**收货部门*/
	private String receiveOrgName;
	/**到达部门*/
	private String reachOrgCode;
	/**到达部门*/
	private String reachOrgName;
	/**运输性质*/
	private String transportTypeName;
	/**运输性质*/
	private String transportTypeCode;
	/**备注*/
	private String notes;
	 /**包装*/
    private String pack;		
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public int getWayBillQty() {
		return wayBillQty;
	}
	public void setWayBillQty(int wayBillQty) {
		this.wayBillQty = wayBillQty;
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getReachOrgCode() {
		return reachOrgCode;
	}
	public void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
	}
	public String getReachOrgName() {
		return reachOrgName;
	}
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	public String getTransportTypeName() {
		return transportTypeName;
	}
	public void setTransportTypeName(String transportTypeName) {
		this.transportTypeName = transportTypeName;
	}
	public String getTransportTypeCode() {
		return transportTypeCode;
	}
	public void setTransportTypeCode(String transportTypeCode) {
		this.transportTypeCode = transportTypeCode;
	}
	public List<String> getSerialNos() {
		return serialNos;
	}
	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
}
