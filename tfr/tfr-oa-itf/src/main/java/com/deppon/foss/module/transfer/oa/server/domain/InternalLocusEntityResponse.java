package com.deppon.foss.module.transfer.oa.server.domain;

import java.io.Serializable;
/**
 * @title: LostCodeEntityResponse
 * @description：按件查询内部轨迹字段
 * @author： ZHANGDANDAN
 * @date： 2016-12-16 上午9:31:02
 */
public class InternalLocusEntityResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 按件查询内部轨迹字段
	 */
	//货物状态
	private String byCurrentStatus;
	//操作部门名称
    private String byOperateOrgName;
    /************* 操作人姓名 ****************/
	private String byOperateName;
    /************* 操作类型****************/
	private String byOperateTypeName;
	/************* 操作时间 ****************/
	private String byOperateTime;

	
	public String getByCurrentStatus() {
		return byCurrentStatus;
	}
	public void setByCurrentStatus(String byCurrentStatus) {
		this.byCurrentStatus = byCurrentStatus;
	}
	public String getByOperateOrgName() {
		return byOperateOrgName;
	}
	public void setByOperateOrgName(String byOperateOrgName) {
		this.byOperateOrgName = byOperateOrgName;
	}
	public String getByOperateName() {
		return byOperateName;
	}
	public void setByOperateName(String byOperateName) {
		this.byOperateName = byOperateName;
	}
	public String getByOperateTypeName() {
		return byOperateTypeName;
	}
	public void setByOperateTypeName(String byOperateTypeName) {
		this.byOperateTypeName = byOperateTypeName;
	}
	public String getByOperateTime() {
		return byOperateTime;
	}
	public void setByOperateTime(String byOperateTime) {
		this.byOperateTime = byOperateTime;
	}
}
