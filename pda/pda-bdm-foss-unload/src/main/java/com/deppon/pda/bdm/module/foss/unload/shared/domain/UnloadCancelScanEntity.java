package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 撤销卸车扫描
 * @author gaojia
 * @date Sep 9,2012 15:07:30 PM
 * @version 1.0
 */
public class UnloadCancelScanEntity extends ScanMsgEntity{
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
	 * 出发部门编号
	 */
	private String origOrgCode;            //出发部门编号
	/**
	 * 到达部门编号
	 */
	private String destOrgCode;            //
	/**
	 * 单据编号
	 */
	private String billNo;      //
	/**
	 * 包装
	 */
	private String packing;
	/**
	 * 运输性质
	 */
	private String transType;
	
	private int rcptPieces;
	
	/**
	 * 是否分批配载
	 */
	private String bePartial;
	
	private String ifPackage;
	
	/** 运单生效状态 - YES NO*/
	private String wayBillStatus; 
	
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
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public int getRcptPieces() {
		return rcptPieces;
	}
	public void setRcptPieces(int rcptPieces) {
		this.rcptPieces = rcptPieces;
	}
	public String getBePartial() {
		return bePartial;
	}
	public void setBePartial(String bePartial) {
		this.bePartial = bePartial;
	}
	public String getIfPackage() {
		return ifPackage;
	}
	public void setIfPackage(String ifPackage) {
		this.ifPackage = ifPackage;
	}
	public String getWayBillStatus() {
		return wayBillStatus;
	}
	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}
	
}
