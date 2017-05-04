package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;

public class ReceiveTPSResponseEntity  implements Serializable{

	/**
	 * 序列号 
	 */
	private static final long serialVersionUID = -4276144540602805175L;

	/**
	 * 处理结果
	 */
	private boolean resultFlag;
	/**
	 * 异常信息
	 */
	private String failureReason;
	/**
	 * 公共实体,可为数组类型
	 */ 
	private Object responseEntity;
	
	/**
	 * @return resultFlag
	 */
	public boolean isResultFlag() {
		return resultFlag;
	}
	
	/**
	 * @param resultFlag
	 */
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	
	/**
	 * @return failureReason
	 */
	public String getFailureReason() {
		return failureReason;
	}
	/**
	 * @param failureReason
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	
	/**
	 * @return responseEntity
	 */
	public Object getResponseEntity() {
		return responseEntity;
	}
	
	/**
	 * @param responseEntity
	 */
	public void setResponseEntity(Object responseEntity) {
		this.responseEntity = responseEntity;
	}
	
	
	
}
