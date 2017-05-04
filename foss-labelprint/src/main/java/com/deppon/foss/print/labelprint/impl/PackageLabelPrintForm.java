package com.deppon.foss.print.labelprint.impl;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author 218392
 *	包打印标签
 */
public class PackageLabelPrintForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 条码
	 */
	private String barCode;
	
	/**
	 * 设置左边距右边距
	 */
	private int left;
	private int top;
	
	/**
	 * 始发地
	 */
	private String leaveCity;
	
	/**
	 * 目的地
	 */
	private String arriveCity;
	
	/**
	 * 件数
	 */
	private int goodQty;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getLeaveCity() {
		return leaveCity;
	}

	public void setLeaveCity(String leaveCity) {
		this.leaveCity = leaveCity;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public int getGoodQty() {
		return goodQty;
	}

	public void setGoodQty(int goodQty) {
		this.goodQty = goodQty;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}

