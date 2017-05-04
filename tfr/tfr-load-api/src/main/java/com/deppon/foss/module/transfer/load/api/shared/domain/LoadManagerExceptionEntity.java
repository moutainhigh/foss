package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;

/** 
 * @author: 332209-foss-ruilibao
 * @description: 装车异常实体类
 * @date 2017年3月26日
 */
public class LoadManagerExceptionEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * 装车任务号
	 */
	private String loadTaskNo;
	
	/**
	 * 创建任务开始时间
	 */
	private Date taskCreateTimeStart;
	
	/**
	 * 创建任务开始时间
	 */
	private Date taskCreateTimeEnd;
	
	/**
	 * 任务状态
	 */
	private String taskStatus;
	
	/**
	 * 理货员编码
	 */
	private String loaderCode;
	
	/**
	 * 理货员名称
	 */
	private String loaderName;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 当前登录部门编码
	 */
	private String orgCode;
	
	/**
	 * 返回页面列表
	 */
	private List<LoadTaskDto> returnList;
	
	public List<LoadTaskDto> getReturnList() {
		return returnList;
	}

	public void setReturnList(List<LoadTaskDto> returnList) {
		this.returnList = returnList;
	}

	public String getLoadTaskNo() {
		return loadTaskNo;
	}

	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}

	public Date getTaskCreateTimeStart() {
		return taskCreateTimeStart;
	}

	public void setTaskCreateTimeStart(Date taskCreateTimeStart) {
		this.taskCreateTimeStart = taskCreateTimeStart;
	}

	public Date getTaskCreateTimeEnd() {
		return taskCreateTimeEnd;
	}

	public void setTaskCreateTimeEnd(Date taskCreateTimeEnd) {
		this.taskCreateTimeEnd = taskCreateTimeEnd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getLoaderCode() {
		return loaderCode;
	}

	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	public String getLoaderName() {
		return loaderName;
	}

	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}