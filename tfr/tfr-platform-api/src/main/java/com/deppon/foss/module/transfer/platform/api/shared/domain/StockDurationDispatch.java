package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class StockDurationDispatch extends BaseEntity {

	private static final long serialVersionUID = -5521855993961160873L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 上一部门编码
	 */
	private String preOrgCode;

	/**
	 * 上一部门名称
	 */
	private String preOrgName;

	/**
	 * 到达时间
	 */
	private Date arrivedTime;

	/**
	 * 卸车任务开始时间
	 */
	private Date unloadTaskBeginTime;

	/**
	 * 卸车扫描时间
	 */
	private Date unloadScanTime;

	/**
	 * 托盘绑定时间
	 */
	private Date trayBindingTime;

	/**
	 * 托盘扫描时间
	 */
	private Date forkliftScanTime;

	/**
	 * 入包装库区时间
	 */
	private Date inPkgAreaTime;

	/**
	 * 出包装库区时间
	 */
	private Date outPkgAreaTime;

	/**
	 * 入派送库区时间
	 */
	private Date inDptAreaTime;

	/**
	 * 派送装车时间
	 */
	private Date dptLoadScanTime;

	/**
	 * 派送装车任务提交时间
	 */
	private Date dptLoadTaskSubmitTime;

	/**
	 * 签收时间
	 */
	private Date signTime;

	/**
	 * 统计日期
	 */
	private Date staDate;

	/**
	 * 统计月份
	 */
	private int staMonth;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getPreOrgCode() {
		return preOrgCode;
	}

	public void setPreOrgCode(String preOrgCode) {
		this.preOrgCode = preOrgCode;
	}

	public String getPreOrgName() {
		return preOrgName;
	}

	public void setPreOrgName(String preOrgName) {
		this.preOrgName = preOrgName;
	}

	public Date getArrivedTime() {
		return arrivedTime;
	}

	public void setArrivedTime(Date arrivedTime) {
		this.arrivedTime = arrivedTime;
	}

	public Date getUnloadTaskBeginTime() {
		return unloadTaskBeginTime;
	}

	public void setUnloadTaskBeginTime(Date unloadTaskBeginTime) {
		this.unloadTaskBeginTime = unloadTaskBeginTime;
	}

	public Date getUnloadScanTime() {
		return unloadScanTime;
	}

	public void setUnloadScanTime(Date unloadScanTime) {
		this.unloadScanTime = unloadScanTime;
	}

	public Date getTrayBindingTime() {
		return trayBindingTime;
	}

	public void setTrayBindingTime(Date trayBindingTime) {
		this.trayBindingTime = trayBindingTime;
	}

	public Date getForkliftScanTime() {
		return forkliftScanTime;
	}

	public void setForkliftScanTime(Date forkliftScanTime) {
		this.forkliftScanTime = forkliftScanTime;
	}

	public Date getInPkgAreaTime() {
		return inPkgAreaTime;
	}

	public void setInPkgAreaTime(Date inPkgAreaTime) {
		this.inPkgAreaTime = inPkgAreaTime;
	}

	public Date getOutPkgAreaTime() {
		return outPkgAreaTime;
	}

	public void setOutPkgAreaTime(Date outPkgAreaTime) {
		this.outPkgAreaTime = outPkgAreaTime;
	}

	public Date getInDptAreaTime() {
		return inDptAreaTime;
	}

	public void setInDptAreaTime(Date inDptAreaTime) {
		this.inDptAreaTime = inDptAreaTime;
	}

	public Date getDptLoadScanTime() {
		return dptLoadScanTime;
	}

	public void setDptLoadScanTime(Date dptLoadScanTime) {
		this.dptLoadScanTime = dptLoadScanTime;
	}

	public Date getDptLoadTaskSubmitTime() {
		return dptLoadTaskSubmitTime;
	}

	public void setDptLoadTaskSubmitTime(Date dptLoadTaskSubmitTime) {
		this.dptLoadTaskSubmitTime = dptLoadTaskSubmitTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public int getStaMonth() {
		return staMonth;
	}

	public void setStaMonth(int staMonth) {
		this.staMonth = staMonth;
	}

}
