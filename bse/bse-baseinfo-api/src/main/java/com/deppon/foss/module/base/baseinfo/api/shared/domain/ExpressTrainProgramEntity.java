/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 *<p>Title: ExpressTrainProgramEntity</p>
 * <p>Description:快递班车时刻方案实体 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-8
 */
public class ExpressTrainProgramEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -820554460392680368L;
	/**
	 * 方案名称
	 */
	private String programName;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 装卸时间
	 */
	private int handlingTime;
	/**
	 * 车速
	 */
	private double speed;
	/**
	 * 线路条数
	 */
	private int lineCount;
	/**
	 * 车辆数
	 */
	private int vehicleCount;
	/**
	 * 覆盖营业部数
	 */
	private int salesDeptCount;
	/**
	 * 起始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 出发外场
	 */
	private String originOutfieldCode;
	/**
	 * 出发外场名称
	 */
	private String originOutfieldName;
	/**
	 * 方案描述
	 */
	private String programRemarks;
	/**
	 * 是否启用
	 */
	private String active;
	/**
	 * 版本号
	 */
	private long versionNo;
	/**
	 * 修改人名称
	 * 
	 */
	private String modifyUserName;
	
	
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
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
	 * @return the handlingTime
	 */
	public int getHandlingTime() {
		return handlingTime;
	}
	/**
	 * @param handlingTime the handlingTime to set
	 */
	public void setHandlingTime(int handlingTime) {
		this.handlingTime = handlingTime;
	}
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/**
	 * @return the lineCount
	 */
	public int getLineCount() {
		return lineCount;
	}
	/**
	 * @param lineCount the lineCount to set
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	/**
	 * @return the vehicleCount
	 */
	public int getVehicleCount() {
		return vehicleCount;
	}
	/**
	 * @param vehicleCount the vehicleCount to set
	 */
	public void setVehicleCount(int vehicleCount) {
		this.vehicleCount = vehicleCount;
	}
	/**
	 * @return the salesDeptCount
	 */
	public int getSalesDeptCount() {
		return salesDeptCount;
	}
	/**
	 * @param salesDeptCount the salesDeptCount to set
	 */
	public void setSalesDeptCount(int salesDeptCount) {
		this.salesDeptCount = salesDeptCount;
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
	 * @return the originOutfieldCode
	 */
	public String getOriginOutfieldCode() {
		return originOutfieldCode;
	}
	/**
	 * @param originOutfieldCode the originOutfieldCode to set
	 */
	public void setOriginOutfieldCode(String originOutfieldCode) {
		this.originOutfieldCode = originOutfieldCode;
	}
	/**
	 * @return the originOutfieldName
	 */
	public String getOriginOutfieldName() {
		return originOutfieldName;
	}
	/**
	 * @param originOutfieldName the originOutfieldName to set
	 */
	public void setOriginOutfieldName(String originOutfieldName) {
		this.originOutfieldName = originOutfieldName;
	}
	/**
	 * @return the programRemarks
	 */
	public String getProgramRemarks() {
		return programRemarks;
	}
	/**
	 * @param programRemarks the programRemarks to set
	 */
	public void setProgramRemarks(String programRemarks) {
		this.programRemarks = programRemarks;
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
	
	
}
