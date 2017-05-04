package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class SelfProvidedEntity implements Serializable{
	/*
	 * 运单号
	 * */
	private String wayBillCode;
	/*
	 * 包装类型
	 * */
	private String packageType;
	/*
	 * 件数
	 * */	
	private String pieces;
	/*
	 * 重量
	 * */
	private String weight;
	/*
	 * 体积
	 * */
	private String volume;
	/*
	 * 货物尺寸
	 * */
	private String goodsSize;
	/*
	 * 操作时间
	 * */
	private Date operTime;
	/*
	 * 货物状态 已告知 GOOD_STATE_HASINFORM 已撤销GOOD_STATE_REVOCATION
	 * */
	private String status;
	public String getWayBillCode() {
		return wayBillCode;
	}
	public void setWayBillCode(String wayBillCode) {
		this.wayBillCode = wayBillCode;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getPieces() {
		return pieces;
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
