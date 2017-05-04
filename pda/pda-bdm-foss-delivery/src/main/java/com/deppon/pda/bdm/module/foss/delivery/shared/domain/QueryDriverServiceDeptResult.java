package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 
 * @ClassName: QueryDriverServiceDeptResult 
 * @Description: TODO(司机服务部门返回结果) 
 * @author &268974  wangzhili
 * @date 2016-2-22 上午9:25:27 
 *
 */
public class QueryDriverServiceDeptResult implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	//司机服务部门名称
	private String deptName;
	//司机服务部门编码
	private String deptCode;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	

}
