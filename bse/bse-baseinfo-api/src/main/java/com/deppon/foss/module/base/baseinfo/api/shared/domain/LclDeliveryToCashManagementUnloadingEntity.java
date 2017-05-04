package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class LclDeliveryToCashManagementUnloadingEntity extends BaseEntity {
    /**
	 *  Serial Version UID
	 */
	private static final long serialVersionUID = 1033057562804990855L;
	
	/**
     * 出发部门编码
     */
	private String startOrgCode;
	/**
	 * 出发部门名称
	 */
	private String startOrgName;
	/**
	 *  到达部门编码
	 */
	private String reachOrgCode;
	/**
	 *  到达部门名称
	 */
	private String reachOrgName;
	/**
	 *  班次
	 */
	private String vehicleNumber;
	/**
	 *  车型
	 */
	private String vehicleType;
	/**
	 *  规定净空
	 */
	private String selfVolume;
	/**
	 *  规定出发时间点
	 */
	private String scheduleTime;
	/**
	 *  规定运行时间用时
	 */
	private String scheduleHours;
	/**
	 *  规定运行时间用时用分
	 */
	private String scheduleMins;
	/**
	 *  规定到达时间点
	 */
	private String reachOnTime;
	/**
	 * 规定到达时间点(天)
	 */
	private String reachOnTimeDay;
	/**
	 * 规定卸车时长
	 */
	private String unloadingTimeOut;
	/**
	 *  规定卸出时间点
	 */
	private String unloadingTime;
	/**
	 *  规定卸出时间点（天）
	 */
	private String unloadingTimeDay;
	/**
	 *  备注
	 */
	private String reMark;
	/**
     * 是否启用
     */
    private String active;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String modifyUser;
    /**
     *创建人工号 
     */
    private String createUserCode;
    /**
     * 修改人工号
     */
    private String modifyUserCode;
    
	public String getStartOrgCode() {
		return startOrgCode;
	}
	public void setStartOrgCode(String startOrgCode) {
		this.startOrgCode = startOrgCode;
	}
	public String getStartOrgName() {
		return startOrgName;
	}
	public void setStartOrgName(String startOrgName) {
		this.startOrgName = startOrgName;
	}
	public String getReachOrgCode() {
		return reachOrgCode;
	}
	public void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
	}
	public String getReachOrgName() {
		return reachOrgName;
	}
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getSelfVolume() {
		return selfVolume;
	}
	public void setSelfVolume(String selfVolume) {
		this.selfVolume = selfVolume;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getScheduleHours() {
		return scheduleHours;
	}
	public void setScheduleHours(String scheduleHours) {
		this.scheduleHours = scheduleHours;
	}
	public String getScheduleMins() {
		return scheduleMins;
	}
	public void setScheduleMins(String scheduleMins) {
		this.scheduleMins = scheduleMins;
	}
	public String getReachOnTime() {
		return reachOnTime;
	}
	public void setReachOnTime(String reachOnTime) {
		this.reachOnTime = reachOnTime;
	}
	public String getReachOnTimeDay() {
		return reachOnTimeDay;
	}
	public void setReachOnTimeDay(String reachOnTimeDay) {
		this.reachOnTimeDay = reachOnTimeDay;
	}
	public String getUnloadingTimeOut() {
		return unloadingTimeOut;
	}
	public void setUnloadingTimeOut(String unloadingTimeOut) {
		this.unloadingTimeOut = unloadingTimeOut;
	}
	public String getUnloadingTime() {
		return unloadingTime;
	}
	public void setUnloadingTime(String unloadingTime) {
		this.unloadingTime = unloadingTime;
	}
	public String getUnloadingTimeDay() {
		return unloadingTimeDay;
	}
	public void setUnloadingTimeDay(String unloadingTimeDay) {
		this.unloadingTimeDay = unloadingTimeDay;
	}
	public String getReMark() {
		return reMark;
	}
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
    

}
