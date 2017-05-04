package com.deppon.foss.module.transfer.common.api.shared.domain;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 类描述：	WebService接口返回参数实体
 * 创建人：	221464-ZOUYONG
 * 创建时间：	2014-12-19 上午10:13:39
 *
 */
@XmlRootElement(name="ResponseParameterEntity")
public class ResponseParameterEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3761476707437674022L;

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