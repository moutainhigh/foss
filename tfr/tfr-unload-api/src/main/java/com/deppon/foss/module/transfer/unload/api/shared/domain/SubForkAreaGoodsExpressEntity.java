package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

/**
 *用于存储待叉区货物数据，提供前台页面显示 
 ***/
public class SubForkAreaGoodsExpressEntity {

	/**运单/包/笼号**/
	private String waybillNo;
	/**开单件数**/
	private int billingNum;
	/**已绑定件数**/
	private int bindNum;
	/**未扫描件数**/
	private int unScanNum;
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
	
	//返回数据的总数
	private long count;
	
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public int getBillingNum() {
		return billingNum;
	}
	public void setBillingNum(int billingNum) {
		this.billingNum = billingNum;
	}
	public int getBindNum() {
		return bindNum;
	}
	public void setBindNum(int bindNum) {
		this.bindNum = bindNum;
	}
	public int getUnScanNum() {
		return unScanNum;
	}
	public void setUnScanNum(int unScanNum) {
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
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
	
}
