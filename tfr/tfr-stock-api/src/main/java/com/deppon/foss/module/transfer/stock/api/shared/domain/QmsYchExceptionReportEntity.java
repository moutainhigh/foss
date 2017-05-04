package com.deppon.foss.module.transfer.stock.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;



/**
* @description 异常货上报实体类
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年6月1日 上午8:50:48
*/
public class QmsYchExceptionReportEntity extends BaseEntity{
	
	/**
	* @fields serialVersionUID
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午9:07:39
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	//是否在丢货小组('Y':是,'N':否)
	private String isInLoseGroup;
	//成功或失败的标识(整数类型，0、失败；1、成功)
	private int isSuccess;
	//失败原因
	private String reason;
	
	
	public String getIsInLoseGroup() {
		return isInLoseGroup;
	}
	public void setIsInLoseGroup(String isInLoseGroup) {
		this.isInLoseGroup = isInLoseGroup;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
