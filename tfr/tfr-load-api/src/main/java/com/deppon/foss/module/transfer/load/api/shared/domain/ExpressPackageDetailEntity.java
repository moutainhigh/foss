/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.domain
 * FILE    NAME: ExpressPackageDetailEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ExpressPackageDetailEntity
 * @author dp-duyi
 * @date 2013-7-22 上午11:15:09
 */
public class ExpressPackageDetailEntity implements Serializable{

	private static final long serialVersionUID = 7165333948137964941L;
	/**ID*/
	private String id;
	/**包号*/
	private String packageNo;
	/**运单号*/
	private String wayBillNo;
	/**流水号*/
	private String serialNo;
	/**运输性质*/
	private String transportTypeName;
	/**运输性质*/
	private String transportTypeCode;
	/**总件数*/
	private int goodsQty;
	/**总重量*/
	private double weight;
	/**总体积*/
	private double volume;
	/**收货部门*/
	private String recieveOrgName;
	/**到达部门*/
	private String reachOrgName;
	/**包装*/
	private String pack;
	/**货物名称*/
	private String goodsName;
	/**备注*/
	private String notes;
	/**货物状态*/
	private String goodsState;
	/**扫描状态*/
	private String scanState;
	/**设备编号*/
	private String deviceNo;
	/**扫描时间*/
	private Date scanTime;
	/**创建时间*/
	private Date createTime;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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
	public int getGoodsQty() {
		return goodsQty;
	}
	public void setGoodsQty(int goodsQty) {
		this.goodsQty = goodsQty;
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
	public String getRecieveOrgName() {
		return recieveOrgName;
	}
	public void setRecieveOrgName(String recieveOrgName) {
		this.recieveOrgName = recieveOrgName;
	}
	public String getReachOrgName() {
		return reachOrgName;
	}
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	public String getScanState() {
		return scanState;
	}
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
