package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *用于存储待叉区货物数据，提供前台页面显示 
 ***/
public class SubForkAreaGoodsEntity {
	/**包号**/
	private String packageNo;
	/**运单号**/
	private String wayBillNo;
	/**开单件数**/
	private BigDecimal billingNum;
	/**已绑定件数**/
	private BigDecimal bindNum;
	/**未扫描件数**/
	private BigDecimal unScanNum;
	/**运输性质**/
	private String transType;
	/**下一目的站**/
	private String nextDestination;
	/**创建人**/
	private String createPerson;
	/**创建人工号**/
	private String createPersonCode;
	/**扫描时间**/
	private Date scanTime;
	/**卸车任务号**/
	private String unloadTaskNo;
	
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
	public BigDecimal getBillingNum() {
		return billingNum;
	}
	public void setBillingNum(BigDecimal billingNum) {
		this.billingNum = billingNum;
	}
	public BigDecimal getBindNum() {
		return bindNum;
	}
	public void setBindNum(BigDecimal bindNum) {
		this.bindNum = bindNum;
	}
	
	public BigDecimal getUnScanNum() {
		return unScanNum;
	}
	public void setUnScanNum(BigDecimal unScanNum) {
		this.unScanNum = unScanNum;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getNextDestination() {
		return nextDestination;
	}
	public void setNextDestination(String nextDestination) {
		this.nextDestination = nextDestination;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreatePersonCode() {
		return createPersonCode;
	}
	public void setCreatePersonCode(String createPersonCode) {
		this.createPersonCode = createPersonCode;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	
	
}
