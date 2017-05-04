package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 
 * @ClassName: QueryDriverServiceEntity 
 * @Description: TODO(司机服务部门) 
 * @author &268974  wangzhili
 * @date 2016-2-29 下午5:43:58 
 *
 */
public class QueryDriverServiceEntity implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//部门名称
	private String deptName;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	

}
