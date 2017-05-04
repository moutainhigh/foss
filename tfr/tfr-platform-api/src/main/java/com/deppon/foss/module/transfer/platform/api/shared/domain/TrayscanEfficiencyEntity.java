package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 托盘绑定效率
 * 
 * @author 200978 2015-01-31
 */
public class TrayscanEfficiencyEntity extends BaseEntity {

	private static final long serialVersionUID = -6632035588764340502L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 组编码
	 */
	private String hqCode;

	/**
	 * 组名称
	 */
	private String hqName;

	/**
	 * 统计日期
	 */
	private Date staDate;

	/**
	 * 卸车任务数
	 */
	private BigDecimal unloadTaskQtyDay;

	/**
	 * 绑定使用数-天
	 */
	private BigDecimal bindingUseQtyDay;

	/**
	 * 绑定使用率-天
	 */
	private BigDecimal bindingUseRateDay;

	/**
	 * 卸车扫描件数-天
	 */
	private BigDecimal unloadScanQtyDay;

	/**
	 * 绑定件数-天
	 */
	private BigDecimal bindingQtyDay;

	/**
	 * 托盘绑定率-天
	 */
	private BigDecimal trayBindingRateDay;

	/**
	 * 叉车扫描件数-天
	 */
	private BigDecimal forkScanQtyDay;

	/**
	 * 叉车扫描率-天
	 */
	private BigDecimal forkScanRateDay;

	/**
	 * 卸车任务数-月
	 */
	private BigDecimal unloadTaskQtyMonth;

	/**
	 * 绑定使用数-月
	 */
	private BigDecimal bindingUseQtyMonth;

	/**
	 * 绑定使用率-月
	 */
	private BigDecimal bindingUseRateMonth;

	/**
	 * 卸车扫描件数-月
	 */
	private BigDecimal unloadScanQtyMonth;

	/**
	 * 绑定件数-月
	 */
	private BigDecimal bindingQtyMonth;

	/**
	 * 托盘绑定率-月
	 */
	private BigDecimal trayBindingRateMonth;

	/**
	 * 叉车扫描件数-月
	 */
	private BigDecimal forkScanQtyMonth;

	/**
	 * 叉车扫描率-月
	 */
	private BigDecimal forkScanRateMonth;

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

	public String getHqCode() {
		return hqCode;
	}

	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}

	public String getHqName() {
		return hqName;
	}

	public void setHqName(String hqName) {
		this.hqName = hqName;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public BigDecimal getUnloadTaskQtyDay() {
		return unloadTaskQtyDay;
	}

	public void setUnloadTaskQtyDay(BigDecimal unloadTaskQtyDay) {
		this.unloadTaskQtyDay = unloadTaskQtyDay;
	}

	public BigDecimal getBindingUseQtyDay() {
		return bindingUseQtyDay;
	}

	public void setBindingUseQtyDay(BigDecimal bindingUseQtyDay) {
		this.bindingUseQtyDay = bindingUseQtyDay;
	}

	public BigDecimal getBindingUseRateDay() {
		return bindingUseRateDay;
	}

	public void setBindingUseRateDay(BigDecimal bindingUseRateDay) {
		this.bindingUseRateDay = bindingUseRateDay;
	}

	public BigDecimal getUnloadScanQtyDay() {
		return unloadScanQtyDay;
	}

	public void setUnloadScanQtyDay(BigDecimal unloadScanQtyDay) {
		this.unloadScanQtyDay = unloadScanQtyDay;
	}

	public BigDecimal getBindingQtyDay() {
		return bindingQtyDay;
	}

	public void setBindingQtyDay(BigDecimal bindingQtyDay) {
		this.bindingQtyDay = bindingQtyDay;
	}

	public BigDecimal getTrayBindingRateDay() {
		return trayBindingRateDay;
	}

	public void setTrayBindingRateDay(BigDecimal trayBindingRateDay) {
		this.trayBindingRateDay = trayBindingRateDay;
	}

	public BigDecimal getForkScanQtyDay() {
		return forkScanQtyDay;
	}

	public void setForkScanQtyDay(BigDecimal forkScanQtyDay) {
		this.forkScanQtyDay = forkScanQtyDay;
	}

	public BigDecimal getForkScanRateDay() {
		return forkScanRateDay;
	}

	public void setForkScanRateDay(BigDecimal forkScanRateDay) {
		this.forkScanRateDay = forkScanRateDay;
	}

	public BigDecimal getUnloadTaskQtyMonth() {
		return unloadTaskQtyMonth;
	}

	public void setUnloadTaskQtyMonth(BigDecimal unloadTaskQtyMonth) {
		this.unloadTaskQtyMonth = unloadTaskQtyMonth;
	}

	public BigDecimal getBindingUseQtyMonth() {
		return bindingUseQtyMonth;
	}

	public void setBindingUseQtyMonth(BigDecimal bindingUseQtyMonth) {
		this.bindingUseQtyMonth = bindingUseQtyMonth;
	}

	public BigDecimal getBindingUseRateMonth() {
		return bindingUseRateMonth;
	}

	public void setBindingUseRateMonth(BigDecimal bindingUseRateMonth) {
		this.bindingUseRateMonth = bindingUseRateMonth;
	}

	public BigDecimal getUnloadScanQtyMonth() {
		return unloadScanQtyMonth;
	}

	public void setUnloadScanQtyMonth(BigDecimal unloadScanQtyMonth) {
		this.unloadScanQtyMonth = unloadScanQtyMonth;
	}

	public BigDecimal getBindingQtyMonth() {
		return bindingQtyMonth;
	}

	public void setBindingQtyMonth(BigDecimal bindingQtyMonth) {
		this.bindingQtyMonth = bindingQtyMonth;
	}

	public BigDecimal getTrayBindingRateMonth() {
		return trayBindingRateMonth;
	}

	public void setTrayBindingRateMonth(BigDecimal trayBindingRateMonth) {
		this.trayBindingRateMonth = trayBindingRateMonth;
	}

	public BigDecimal getForkScanQtyMonth() {
		return forkScanQtyMonth;
	}

	public void setForkScanQtyMonth(BigDecimal forkScanQtyMonth) {
		this.forkScanQtyMonth = forkScanQtyMonth;
	}

	public BigDecimal getForkScanRateMonth() {
		return forkScanRateMonth;
	}

	public void setForkScanRateMonth(BigDecimal forkScanRateMonth) {
		this.forkScanRateMonth = forkScanRateMonth;
	}

}
