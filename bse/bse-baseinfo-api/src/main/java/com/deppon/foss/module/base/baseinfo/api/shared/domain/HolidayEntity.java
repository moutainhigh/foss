/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 法定节假日基础资料实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:187862,date:2015-3-19 下午2:44:10,content:TODO </p>
 * @author 187862-dujunhui 
 * @date 2015-3-19 下午2:44:10
 * @since
 * @version
 */
public class HolidayEntity extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 节假日名称
	 */
	private String holidayName;
	/**
	 * 节假日开始时间
	 */
	private Date beginTime;
	/**
	 * 节假日结束时间
	 */
	private Date endTime;
	/**
	 * 备注（冗余字段）
	 */
	private String remark;
	/**
	 * 新增修改标识位
	 */
	private String flag;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 版本号
	 */
	private long versionNo;
	/**
	 * @return  the holidayName
	 */
	public String getHolidayName() {
		return holidayName;
	}
	/**
	 * @param holidayName the holidayName to set
	 */
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	/**
	 * @return  the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * @return  the endTime
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
	 * @return  the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return  the active
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
	 * @return  the versionNo
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
	 * @return  the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
