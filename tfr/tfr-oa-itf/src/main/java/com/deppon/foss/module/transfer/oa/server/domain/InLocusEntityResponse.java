package com.deppon.foss.module.transfer.oa.server.domain;

import java.io.Serializable;
/**
 * @title: LostCodeEntityResponse
 * @description：内部轨迹字段
 * @author： ZHANGDANDAN
 * @date： 2016-12-16 上午9:31:02
 */
public class InLocusEntityResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 内部轨迹字段
	 */
	//货物状态
	private String currentStatus;
	//操作部门名称
    private String operateOrgName;
    //操作人姓名
	private String operateName;
    //操作类型
	private String operateTypeName;
	//操作时间
	private String operateTime;

	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getOperateOrgName() {
		return operateOrgName;
	}
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getOperateTypeName() {
		return operateTypeName;
	}
	public void setOperateTypeName(String operateTypeName) {
		this.operateTypeName = operateTypeName;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
}
