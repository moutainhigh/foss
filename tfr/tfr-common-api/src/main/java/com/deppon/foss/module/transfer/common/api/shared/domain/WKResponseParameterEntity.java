package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

public class WKResponseParameterEntity implements Serializable {
	
	
	/**
	* @fields serialVersionUID
	* @author 328864-foss-xieyang
	* @update 2016年5月12日 下午1:27:23
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	// 公共实体,可为数组类型
	private Object ResponseEntity;
	
	// 是否成功标志
	private boolean resultFlag;
	
	// 失败原因
	private String failureReason;

	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public Object getResponseEntity() {
		return ResponseEntity;
	}

	public void setResponseEntity(Object responseEntity) {
		ResponseEntity = responseEntity;
	}

}
