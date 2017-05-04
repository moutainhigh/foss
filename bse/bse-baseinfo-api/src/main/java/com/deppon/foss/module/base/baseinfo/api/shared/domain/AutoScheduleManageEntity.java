package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 用来管理自动调度的开启和关闭的实体
 * @author 130376-yangkang
 * @date 2014-04-19
 * @since
 * @version
 */
public class AutoScheduleManageEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 部门编码
	 */
	private String deptCode;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private  String endTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 修改人工号
	 */
	private String modifyUserPsncode;
	/**
	 * 是否启用
	 */
	private String active;
	
	/**
     * 版本号.
     */
    private Long versionNo;
    
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModifyUserPsncode() {
		return modifyUserPsncode;
	}
	public void setModifyUserPsncode(String modifyUserPsncode) {
		this.modifyUserPsncode = modifyUserPsncode;
	}
	public Long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
