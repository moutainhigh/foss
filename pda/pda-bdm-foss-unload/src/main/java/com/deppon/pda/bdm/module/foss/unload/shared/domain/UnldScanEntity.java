package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 卸车扫描类
 * @author gaojia
 * @date Sep 6,2012 10:00:30 AM
 * @version 1.0
 * @since
 */
public class UnldScanEntity extends ScanMsgEntity{
	
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
	private String volume;
	/**
	 * 货名
	 */
	private String cargoName;

	private String origOrgCode;            //出发部门编号
	private String destOrgCode;            //到达部门编号
	private String billNo;                 //单据编号
	private String packing;
	private String transType;
	private int rcptPieces;
	/**
	 * 是否分批配载
	 */
	private String bePartial;
	
	private String reachOrgCode;
	
	private String ifPackage;
	/** 运单生效状态 - YES NO*/
	private String wayBillStatus; 
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
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

	

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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

	public String getReachOrgCode() {
		return reachOrgCode;
	}

	public void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
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