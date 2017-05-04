package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;

/**
 * 
 * @ClassName: TaskUpdateEntity 
 * @Description: TODO(任务更新参数实体) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午10:03:57 
 *
 */
public class TaskUpdateEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//任务编号
	private String orderTaskNo;
	
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	

}
