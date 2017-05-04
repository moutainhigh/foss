package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;
/**
 * 
 * @ClassName: ScanTaskSubmitEntity 
 * @Description: TODO(扫描任务提交参数实体) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午10:15:52 
 *
 */
public class ScanTaskSubmitEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	//任务编号
	private String orderTaskNo;
	//操作人
	private String userCode;
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
	

}
