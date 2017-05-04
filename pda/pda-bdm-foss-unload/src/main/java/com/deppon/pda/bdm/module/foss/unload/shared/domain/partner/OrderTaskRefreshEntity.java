package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;
/**
 * 
 * @ClassName: OrderTaskRefreshEntity 
 * @Description: TODO(点单任务刷新参数实体) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午10:09:27 
 *
 */
public class OrderTaskRefreshEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	//部门编码
	
	private String deptCode;
	
	//PDA点单人
	private String operator;

	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	

}
