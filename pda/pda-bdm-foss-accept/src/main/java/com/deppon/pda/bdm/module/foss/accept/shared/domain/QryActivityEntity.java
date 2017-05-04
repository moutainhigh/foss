package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * CRM获取营销活动 
 * @author 092038
 *
 */
public class QryActivityEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
  
	 /**
	  * 当前时间
	  * */
	 private Date currentDate;
	 
	 /**
	  *  是否是快递 (Y 快递，N 零担)
	  * */
	 private  String isExpress;
	 
	 /**
	  *  出发部门
	  * */
	 private  String departmentCode;
	 

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	 
	 
	 
	 

}