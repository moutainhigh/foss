package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

import java.util.Date;
import java.util.List;

/**
 * 
  * @ClassName PackageGoodsDetail 
  * @Description TODO 刷新建包货物明细实体
  * @author mt 
  * @date 2013-7-30 上午9:08:16
 */
public class PackageGoodsDetail {
	//运单号
	String wayBillNo;
	//类型
	String type;
	//开单数量
	Integer wayBillQty;
	//库存数量
	Integer stockQty;
	//已扫数量
	Integer operateQty;
	//重量
	double weight;
	//体积
	double volum;
	//目的站名称
	String destOrgNames;
	//入库时间
	Date scanTime;
	//状态
	String state;
	//货物名称
	String goodsName;
	//收货部门编号
	String receiveOrgCode;
	//收货部门名称
	String receiveOrgName;
	//到达部门编码
	String reachOrgCode;
	//到达部门名称
	String reachOrgName;
	//流水号列表
	List<SerialNo> serialNos;
	//运输性质名称
	String transportTypeName;
	//运输性质Code
	String transportTypeCode;
	//流水号
	String serialNo;
	//包号
	String packageNo;
	//包装
	String pack;
	
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public List<SerialNo> getSerialNos() {
		return serialNos;
	}
	public void setSerialNos(List<SerialNo> serialNos) {
		this.serialNos = serialNos;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getWayBillQty() {
		return wayBillQty;
	}
	public void setWayBillQty(Integer wayBillQty) {
		this.wayBillQty = wayBillQty;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	public Integer getOperateQty() {
		return operateQty;
	}
	public void setOperateQty(Integer operateQty) {
		this.operateQty = operateQty;
	}
	public String getDestOrgNames() {
		return destOrgNames;
	}
	public void setDestOrgNames(String destOrgNames) {
		this.destOrgNames = destOrgNames;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolum() {
		return volum;
	}
	public void setVolum(double volum) {
		this.volum = volum;
	}
	
}
