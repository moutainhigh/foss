package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 *<p>Title: CourierScheduleEntity</p>
 * <p>Description: 快递员排班的实体类</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-16
 */
public class CourierScheduleEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 排班日期
	 */
	 private Date schedulingDate;
	 /**
	  * 收派小区编码
	  */
	 private String expressSmallZoneCode;
	 /**
	  * 收派小区名称
	  */
	 private String expressSmallZoneName;
	 /**
	  * 起始楼层
	  */
	 private int startFloor;
	 /**
	  * 结束楼层
	  */
	 private int endFloor;
	 /**
	  * 快递员编码
	  */
	 private String courierCode;
	 /**
	  * 快递员名称
	  */
	 private String courierName;
	 /**
	  * 快递员属性
	  */
	 private String courierNature;
	 /**
	  * 工作类别
	  */
	 private String planType;
	 /**
	  * 车牌
	  */
	 private String vehicleNo;
	 /**
	  * 车型
	  */
	 private String vehicleLenghtCode;
	 /**
	  * 快递员电话
	  */
	 private String courierPhone;
	 /**
	  * 快递员所属点部
	  */
	 private String expressPartCode;
	 /**
	  * 快递员所属点部名称
	  */
	 private String expressPartName;
	 /**
	  * 开始时间
	  */
	 private Date startTime;
	 /**
	  * 结束时间
	  */
	 private Date endTime;
	 /**
	  * 是否有效
	  */
	 private String active;
	 /**
	  * 版本号
	  */
	 private long versionNo;
	 /**
	  * 日期的数字
	  */
	 private int dateNum;
	 /**
	  * 年月yyyy-mm
	  */
	 private String yearMonth;
	 /**
	  * empCode 当前登录人
	  */
	 private String empCode;
	 /**
	  * 排班 多点部查询报表，部门集合
	  */
	 private List<String> orgCodeList;
	 
	 private String schedulingTime;
	 
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	/**
	 * @return the schedulingDate
	 */
	public Date getSchedulingDate() {
		return schedulingDate;
	}
	/**
	 * @param schedulingDate the schedulingDate to set
	 */
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}
	/**
	 * @return the expressSmallZoneCode
	 */
	public String getExpressSmallZoneCode() {
		return expressSmallZoneCode;
	}
	/**
	 * @param expressSmallZoneCode the expressSmallZoneCode to set
	 */
	public void setExpressSmallZoneCode(String expressSmallZoneCode) {
		this.expressSmallZoneCode = expressSmallZoneCode;
	}
	/**
	 * @return the expressSmallZoneName
	 */
	public String getExpressSmallZoneName() {
		return expressSmallZoneName;
	}
	/**
	 * @param expressSmallZoneName the expressSmallZoneName to set
	 */
	public void setExpressSmallZoneName(String expressSmallZoneName) {
		this.expressSmallZoneName = expressSmallZoneName;
	}
	/**
	 * @return the startFloor
	 */
	public int getStartFloor() {
		return startFloor;
	}
	/**
	 * @param startFloor the startFloor to set
	 */
	public void setStartFloor(int startFloor) {
		this.startFloor = startFloor;
	}
	/**
	 * @return the endFloor
	 */
	public int getEndFloor() {
		return endFloor;
	}
	/**
	 * @param endFloor the endFloor to set
	 */
	public void setEndFloor(int endFloor) {
		this.endFloor = endFloor;
	}
	/**
	 * @return the courierCode
	 */
	public String getCourierCode() {
		return courierCode;
	}
	/**
	 * @param courierCode the courierCode to set
	 */
	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}
	/**
	 * @return the courierName
	 */
	public String getCourierName() {
		return courierName;
	}
	/**
	 * @param courierName the courierName to set
	 */
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	/**
	 * @return the courierNature
	 */
	public String getCourierNature() {
		return courierNature;
	}
	/**
	 * @param courierNature the courierNature to set
	 */
	public void setCourierNature(String courierNature) {
		this.courierNature = courierNature;
	}
	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return planType;
	}
	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the vehicleLenghtCode
	 */
	public String getVehicleLenghtCode() {
		return vehicleLenghtCode;
	}
	/**
	 * @param vehicleLenghtCode the vehicleLenghtCode to set
	 */
	public void setVehicleLenghtCode(String vehicleLenghtCode) {
		this.vehicleLenghtCode = vehicleLenghtCode;
	}
	/**
	 * @return the courierPhone
	 */
	public String getCourierPhone() {
		return courierPhone;
	}
	/**
	 * @param courierPhone the courierPhone to set
	 */
	public void setCourierPhone(String courierPhone) {
		this.courierPhone = courierPhone;
	}
	/**
	 * @return the expressPartCode
	 */
	public String getExpressPartCode() {
		return expressPartCode;
	}
	/**
	 * @param expressPartCode the expressPartCode to set
	 */
	public void setExpressPartCode(String expressPartCode) {
		this.expressPartCode = expressPartCode;
	}
	/**
	 * @return the expressPartName
	 */
	public String getExpressPartName() {
		return expressPartName;
	}
	/**
	 * @param expressPartName the expressPartName to set
	 */
	public void setExpressPartName(String expressPartName) {
		this.expressPartName = expressPartName;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the versionNo
	 */
	public long getVersionNo() {
		return versionNo;
	}
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the dateNum
	 */
	public int getDateNum() {
		return dateNum;
	}
	/**
	 * @param dateNum the dateNum to set
	 */
	public void setDateNum(int dateNum) {
		this.dateNum = dateNum;
	}
	/**
	 * @return the yearMonth
	 */
	public String getYearMonth() {
		return yearMonth;
	}
	/**
	 * @param yearMonth the yearMonth to set
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getSchedulingTime() {
		return schedulingTime;
	}
	public void setSchedulingTime(String schedulingTime) {
		this.schedulingTime = schedulingTime;
	}
	 	 
}
