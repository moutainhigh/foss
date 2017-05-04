package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;

public class PDATrayScanDetailEntity implements Serializable{

	private static final long serialVersionUID = 7690347115821827908L;
	
	/**
	 * id
	 * */
	private String id;
	/***
	 *包号
	 **/
	private String packageNo;
	/**
	 * 绑定托盘的运单号
	 * */
	private String  waybillNo;
	/**
	 * 绑定托盘的流水号
	 * */
	private String  serialNo;
	
	/**
	 * 备注信息
	 * */
	private String waybillInfo;
	
	
	/**
	 * 目的站
	 * */
	private String destDept;
	
	/**
	 * 目的站名称
	 * */
	private String destDeptName;
	
	/**
	 * 开单件数
	 * */
	private int serialCount;
	
	/**
	 * 运输性质
	 * **/
	private String tranProperty ;
	
	/**
	 *库区名称 
	 * */
	private String goodAreaName;
	
	/**库区编码 */
	private String goodAreaCode;
	
	/**行政区域*/
	private String adminiRegions;
	
	
	public String getGoodAreaCode() {
		return goodAreaCode;
	}

	public void setGoodAreaCode(String goodAreaCode) {
		this.goodAreaCode = goodAreaCode;
	}

	public String getAdminiRegions() {
		return adminiRegions;
	}

	public void setAdminiRegions(String adminiRegions) {
		this.adminiRegions = adminiRegions;
	}

	public String getGoodAreaName() {
		return goodAreaName;
	}

	public void setGoodAreaName(String goodAreaName) {
		this.goodAreaName = goodAreaName;
	}

	public int getSerialCount() {
		return serialCount;
	}

	public void setSerialCount(int serialCount) {
		this.serialCount = serialCount;
	}

	public String getTranProperty() {
		return tranProperty;
	}

	public void setTranProperty(String tranProperty) {
		this.tranProperty = tranProperty;
	}

	public String getDestDeptName() {
		return destDeptName;
	}

	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

	/**
	 * 获取目的站.
	 *
	 * @return String 目的站
	 */
	public String getDestDept() {
		return destDept;
	}
	
	/**
	 * 设置 目的站
	 *
	 * @param destDept 目的站
	 */
	public void setDestDept(String destDept) {
		this.destDept = destDept;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	
	
	
	
	/**
	 * 获取 运单备注信息.
	 *
	 * @return the 运单备注信息
	 */
	public String getWaybillInfo() {
		return waybillInfo;
	}
	
	/**
	 * 设置 运单备注信息
	 *
	 * @param waybillInfo the new  运单备注信息
	 */
	public void setWaybillInfo(String waybillInfo) {
		this.waybillInfo = waybillInfo;
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
	 * 设置 运单号
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
	 * 设置流水号
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

}
