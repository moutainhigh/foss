package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class GUIPrintLabelDto implements Serializable {

	 
	private static final long serialVersionUID = 1888582047206487694L;
	
	/** 运单号*/
	private String waybillNo;
	/** 流水号*/
	private String serialNo;
	/** 打印时间*/
	private Date printTime;
	/** 打印人工号*/
	private String printUserCode;
	/** 打印人姓名*/
	private String printUserName;
	/**
	 * 标签类别
	 */
	private int lableType;
	

	public int getLableType() {
		return lableType;
	}

	public void setLableType(int lableType) {
		this.lableType = lableType;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取 打印时间.
	 *
	 * @return the 打印时间
	 */
	public Date getPrintTime() {
		return printTime;
	}

	/**
	 * 设置 打印时间.
	 *
	 * @param printTime the new 打印时间
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	/**
	 * 获取 打印人工号.
	 *
	 * @return the 打印人工号
	 */
	public String getPrintUserCode() {
		return printUserCode;
	}

	/**
	 * 设置 打印人工号.
	 *
	 * @param printUserCode the new 打印人工号
	 */
	public void setPrintUserCode(String printUserCode) {
		this.printUserCode = printUserCode;
	}

	/**
	 * 获取 打印人姓名.
	 *
	 * @return the 打印人姓名
	 */
	public String getPrintUserName() {
		return printUserName;
	}

	/**
	 * 设置 打印人姓名.
	 *
	 * @param printUserName the new 打印人姓名
	 */
	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}

}
