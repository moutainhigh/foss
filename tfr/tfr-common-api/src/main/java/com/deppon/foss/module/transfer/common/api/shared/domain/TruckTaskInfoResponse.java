package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

/**
 * @description 悟空系统返回数据封装类
 * @version 1.0
 * @author 332209-FOSS-ruilibao
 * @update 2016年4月27日 下午5:56:54
 */
public class TruckTaskInfoResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//返回类型
	private String returnType;
	
	//是否成功 true 成功  ， false 失败
	private boolean beSuccess;

	//失败原因
	private String failureReason;

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

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
	
}
