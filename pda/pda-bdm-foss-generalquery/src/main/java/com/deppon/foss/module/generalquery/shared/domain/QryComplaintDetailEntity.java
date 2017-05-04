package com.deppon.foss.module.generalquery.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class QryComplaintDetailEntity implements Serializable{
	 /**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	  //用户工号
	  private String empCode;
	  //开始时间
	  private Date startTime;
	  //结束时间
	  private Date endTime;
	  
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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
