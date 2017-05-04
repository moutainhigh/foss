package com.deppon.foss.module.settlement.common.api.shared.domain;
/**
 * @218392 张永雪
 * @author 218392
 * @date 2016-06-21 23:08:10
 * 应付单和付款单付款时候，校验VTS运单号和来源单号：
 * 1.合同编码是否存在；2.单号+合同编码一一对应关系；3.运单状态：作废，不允许发起付款。
 *  
 *  校验不通过返回信息
 */
public class ResponsePayableCheckEntity {
	
	/**
	 * 
	 * 处理结果
	 */
	private boolean resultFlag;
	/**
	 * 
	 * 异常信息
	 */
	private String failureReason;
	/**
	 * 
	 *  公共实体,可为数组类型
	 */
	private Object responseEntity;
	
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
		return responseEntity;
	}
	
	public void setResponseEntity(Object responseEntity) {
		this.responseEntity = responseEntity;
	}

	
}
