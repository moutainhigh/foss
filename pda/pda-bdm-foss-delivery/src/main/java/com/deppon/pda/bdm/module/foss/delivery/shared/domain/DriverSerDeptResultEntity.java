package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 
 * @ClassName: DriverSerDeptResultEntity 
 * @Description: TODO(司机服务部门返回值) 
 * @author &268974  wangzhili
 * @date 2016-1-27 下午2:17:26 
 *
 */
public class DriverSerDeptResultEntity implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(序列化)
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//部门编码
	private String deptriverCode;
	//部门名称
	private String deptName;
	
	public String getDeptriverCode() {
		return deptriverCode;
	}
	public void setDeptriverCode(String deptriverCode) {
		this.deptriverCode = deptriverCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	

}
