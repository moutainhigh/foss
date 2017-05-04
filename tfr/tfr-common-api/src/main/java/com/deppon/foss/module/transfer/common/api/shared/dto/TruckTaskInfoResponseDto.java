package com.deppon.foss.module.transfer.common.api.shared.dto;


import java.io.Serializable;



/**
* @description 快递响应 foss同步装车任务接口实体
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月29日 下午8:13:38
*/
public class TruckTaskInfoResponseDto implements Serializable {

	
	
	/**
	* @fields serialVersionUID
	* @author 283250-foss-liuyi
	* @update 2016年4月29日 下午8:13:30
	* @version V1.0
	*/
	private static final long serialVersionUID = -372472578511403146L;

	// 返回是否成功的标志 true ,false
	private boolean beSuccess;

	// 失败原因
	private String failureReason;

	// 返回接口类型
	private String returnType;

	public boolean getBeSuccess() {
		return beSuccess;
	}

	public void setBeSuccess(boolean beSuccess) {
		this.beSuccess = beSuccess;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	

}
