/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 *<p>Title: ExpressLineScheduleEntity</p>
 * <p>Description:快递支线班车线路时刻信息实体 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-8
 */
public class ExpressLineScheduleEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1587943686796776100L;
	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 方案名称
	 */
	private String programName;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 出发部门GIS_id
	 */
	private String originDeptGisId;
	/**
	 * 到达部门GIS_id
	 */
	private String arriveDeptGisId;
	/**
	 * 出发部门
	 */
	private String originDeptCode;
	/**
	 * 出发部门名称
	 */
	private String originDeptName;
	/**
	 * 到达部门
	 */
	private String arriveDeptCode;
	/**
	 * 到达部门名称
	 */
	private String arriveDeptName;
	/**
	 * 出发时间
	 */
	private Date departTime;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 行驶距离
	 */
	private double travelDistance;
	/**
	 * 行驶时间
	 */
	private String travelTime;
	/**
	 * 线段顺序编码
	 */
	private String lineOrderCode;
	
	private String active;
	
	private long versionNo;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}
	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}
	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the originDeptGisId
	 */
	public String getOriginDeptGisId() {
		return originDeptGisId;
	}
	/**
	 * @param originDeptGisId the originDeptGisId to set
	 */
	public void setOriginDeptGisId(String originDeptGisId) {
		this.originDeptGisId = originDeptGisId;
	}
	/**
	 * @return the arriveDeptGisId
	 */
	public String getArriveDeptGisId() {
		return arriveDeptGisId;
	}
	/**
	 * @param arriveDeptGisId the arriveDeptGisId to set
	 */
	public void setArriveDeptGisId(String arriveDeptGisId) {
		this.arriveDeptGisId = arriveDeptGisId;
	}
	/**
	 * @return the originDeptCode
	 */
	public String getOriginDeptCode() {
		return originDeptCode;
	}
	/**
	 * @param originDeptCode the originDeptCode to set
	 */
	public void setOriginDeptCode(String originDeptCode) {
		this.originDeptCode = originDeptCode;
	}
	/**
	 * @return the originDeptName
	 */
	public String getOriginDeptName() {
		return originDeptName;
	}
	/**
	 * @param originDeptName the originDeptName to set
	 */
	public void setOriginDeptName(String originDeptName) {
		this.originDeptName = originDeptName;
	}
	/**
	 * @return the arriveDeptCode
	 */
	public String getArriveDeptCode() {
		return arriveDeptCode;
	}
	/**
	 * @param arriveDeptCode the arriveDeptCode to set
	 */
	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}
	/**
	 * @return the arriveDeptName
	 */
	public String getArriveDeptName() {
		return arriveDeptName;
	}
	/**
	 * @param arriveDeptName the arriveDeptName to set
	 */
	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}
	/**
	 * @return the departTime
	 */
	public Date getDepartTime() {
		return departTime;
	}
	/**
	 * @param departTime the departTime to set
	 */
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	/**
	 * @return the arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	/**
	 * @param arriveTime the arriveTime to set
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}	
	public double getTravelDistance() {
		return travelDistance;
	}
	public void setTravelDistance(double travelDistance) {
		this.travelDistance = travelDistance;
	}
	public String getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}
	/**
	 * @return the lineOrderCode
	 */
	public String getLineOrderCode() {
		return lineOrderCode;
	}
	/**
	 * @param lineOrderCode the lineOrderCode to set
	 */
	public void setLineOrderCode(String lineOrderCode) {
		this.lineOrderCode = lineOrderCode;
	}

	
}
