package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;





/**
 * 路由明细实体类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-09-23
 */
public class RouteDetailEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 路由Code
	 */
	private String routeCode;
	/**
	 * 站点顺序
	 */
	private String stationSer;
	/**
	 * 出发站
	 */
	private String departureDept;
	/**
	 * 到达站
	 */
	private String destDept;
	/**
	 * 库位
	 */
	private String storeNum;
	/**
	 * 是否生效
	 */
	private String isActive;
	/**
	 * 操作标记
	 */
	private String operFlag;
	/**
	 * 上一次更新时间
	 */
	private String updTime;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 生效时间
	 */
	private String activeTime;
	
	public String getDepartureDept() {
		return departureDept;
	}
	public void setDepartureDept(String departureDept) {
		this.departureDept = departureDept;
	}
	public String getDestDept() {
		return destDept;
	}
	public void setDestDept(String destDept) {
		this.destDept = destDept;
	}
	public String getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getStationSer() {
		return stationSer;
	}
	public void setStationSer(String stationSer) {
		this.stationSer = stationSer;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	
	
}
