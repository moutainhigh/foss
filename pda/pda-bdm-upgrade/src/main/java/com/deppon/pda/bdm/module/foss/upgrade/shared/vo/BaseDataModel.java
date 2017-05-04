package com.deppon.pda.bdm.module.foss.upgrade.shared.vo;

import java.util.Date;

  
/**     
 *      
 *    
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-26 下午02:47:24    
 */

public class BaseDataModel {
	/**
	 * 是否激活
	 */
	private String isActive;
	/**
	 * 开始时间毫秒数
	 */
	private String start_version_no;
	/**
	 * 结束时间毫秒数
	 */
	private String end_version_no;
	
	/**
	 * 部门ID
	 */
	private String deptCode;
	
	private String userCode;
	
	/**
	 * 更新标识
	 */
	private String flag;
	
	private Date startTime;
	private Date endTime;
	
	
	
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	public String getStart_version_no() {
		return start_version_no;
	}
	public void setStart_version_no(String start_version_no) {
		this.start_version_no = start_version_no;
	}
	public String getEnd_version_no() {
		return end_version_no;
	}
	public void setEnd_version_no(String end_version_no) {
		this.end_version_no = end_version_no;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
	
}
