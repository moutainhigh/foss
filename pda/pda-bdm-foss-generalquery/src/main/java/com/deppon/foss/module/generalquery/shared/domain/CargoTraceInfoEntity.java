package com.deppon.foss.module.generalquery.shared.domain;

import java.util.List;

/**
 * 货物追踪信息
 * 
 * @author mt
 * @date 2013-01-11 17:00
 * @version 1.0
 * @since
 */
public class CargoTraceInfoEntity {

	/**
	 * 货物名称
	 */
	private String crgName;
	/**
	 * 目的地城市
	 */
	private String destinationCity;
	/**
	 * 到达部门
	 */
	private String arrivalDept;

	/**
	 * 到达部门地址
	 */
	private String arrDeptAddr;

	/**
	 * 到达部门电话
	 */
	private String arrDeptPhone;
	/**
	 * 件数
	 */
	private int crgPieces;
	/**
	 * 提货方式
	 */
	private String takeType;
	/**
	 * 货物追踪信息
	 */
	private List<TraceInfoEntity> traceInfos;
	/**
	 * 承运方式
	 */
	private String transType;
	/**
	 * 体积
	 */
	private double crgVolume;
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 重量
	 */
	private double crgWeight;
	public String getCrgName() {
		return crgName;
	}
	public void setCrgName(String crgName) {
		this.crgName = crgName;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getArrivalDept() {
		return arrivalDept;
	}
	public void setArrivalDept(String arrivalDept) {
		this.arrivalDept = arrivalDept;
	}
	public String getArrDeptAddr() {
		return arrDeptAddr;
	}
	public void setArrDeptAddr(String arrDeptAddr) {
		this.arrDeptAddr = arrDeptAddr;
	}
	public String getArrDeptPhone() {
		return arrDeptPhone;
	}
	public void setArrDeptPhone(String arrDeptPhone) {
		this.arrDeptPhone = arrDeptPhone;
	}
	public int getCrgPieces() {
		return crgPieces;
	}
	public void setCrgPieces(int crgPieces) {
		this.crgPieces = crgPieces;
	}
	public String getTakeType() {
		return takeType;
	}
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}
	public List<TraceInfoEntity> getTraceInfos() {
		return traceInfos;
	}
	public void setTraceInfos(List<TraceInfoEntity> traceInfos) {
		this.traceInfos = traceInfos;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public double getCrgVolume() {
		return crgVolume;
	}
	public void setCrgVolume(double crgVolume) {
		this.crgVolume = crgVolume;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public double getCrgWeight() {
		return crgWeight;
	}
	public void setCrgWeight(double crgWeight) {
		this.crgWeight = crgWeight;
	}
	
}