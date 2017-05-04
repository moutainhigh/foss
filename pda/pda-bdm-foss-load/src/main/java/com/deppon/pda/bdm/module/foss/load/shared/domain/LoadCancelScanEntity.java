package com.deppon.pda.bdm.module.foss.load.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 取消装车扫描类
 * @author gaojia
 * @date Sep 6,2012 10:00:30 AM
 * @version 1.0
 * @since
 */
public class LoadCancelScanEntity extends ScanMsgEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 重量
	 */
	private double weight;
	/**
	 * 体积
	 */
	private double volume;
	/**
	 * 货名
	 */
	private String cargoName;
	/**
	 * 是否合车
	 */
	private String beJoinCar;
	
	private int stockQty;
	/**
	 * 是否包装
	 */
	private String isWrap;
	/**
	 * 运输性质
	 */
	private String transType;
	/**
	 * 接货部门名称
	 */
	private String acctDeptName;
	/**
	 * 到达部门名称
	 */
	private String arrDeptName;
	private String ifPackage;
	private String waybillCode;
	private String serial;
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
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public String getBeJoinCar() {
		return beJoinCar;
	}
	public void setBeJoinCar(String beJoinCar) {
		this.beJoinCar = beJoinCar;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	public String getIsWrap() {
		return isWrap;
	}
	public void setIsWrap(String isWrap) {
		this.isWrap = isWrap;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getAcctDeptName() {
		return acctDeptName;
	}
	public void setAcctDeptName(String acctDeptName) {
		this.acctDeptName = acctDeptName;
	}
	public String getArrDeptName() {
		return arrDeptName;
	}
	public void setArrDeptName(String arrDeptName) {
		this.arrDeptName = arrDeptName;
	}
	public String getIfPackage() {
		return ifPackage;
	}
	public void setIfPackage(String ifPackage) {
		this.ifPackage = ifPackage;
	}
	public String getWaybillCode() {
		return waybillCode;
	}
	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	

	
}