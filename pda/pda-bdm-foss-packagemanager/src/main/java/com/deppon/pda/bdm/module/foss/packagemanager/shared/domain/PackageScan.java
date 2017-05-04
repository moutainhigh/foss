package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
/**
 * 
  * @ClassName PackageScan 
  * @Description TODO 建包扫描实体
  * @author mt 
  * @date 2013-7-30 下午5:20:15
 */
public class PackageScan extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5379303577613736615L;
	//包号
	String pack;
	//设备号
	String deviceNo;
	//扫描时间
	Date scanTime;
	//类型
	String type;
	//扫描状态
	String scanState;
	//开单体积
	double volum;
	//开单重量
	double weight;
	//商品名称
	String goodsName;
	//库存件数
	Integer goodsQty;
	//运输性质名称
	String transportType;
	//运输性质编码
	String transportTypeCode;
	//收货部门名称
	String receiveOrgName;
	//到达部门名称
	String reachOrgName;
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScanState() {
		return scanState;
	}
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
	public double getVolum() {
		return volum;
	}
	public void setVolum(double volum) {
		this.volum = volum;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsQty() {
		return goodsQty;
	}
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
