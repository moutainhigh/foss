package com.deppon.foss.module.transfer.common.api.shared.dto;


/**
 * DOP推送供应商以及家装签收信息至FOSS系统服务端
 * 
 * DOP调用Foss系统异常实体
 * @author foss-lln
 * @date 2015-12-03 下午7:15:36
 */
public class DpjzSignInFromDopExceptionDto extends LdpExceptionDto{
	/**
	 * 唯一请求Id：该请求Id为DOP推送给FOSS中转待审核信息时带的字段值
	 */
	private String uniqueRequestId;
	
	/**
	 * 签收运单号
	 */
	public String signUpId;
	/**
	 * 是否成功（1： 成功 0: 失败）
	 */
	public String success;
	
	
	/**
	 * @return the uniqueRequestId
	 */
	public String getUniqueRequestId() {
		return uniqueRequestId;
	}
	/**
	 * @param uniqueRequestId the uniqueRequestId to set
	 */
	public void setUniqueRequestId(String uniqueRequestId) {
		this.uniqueRequestId = uniqueRequestId;
	}
	/**
	 * 
	 * @return signUpId
	 */
	public String getSignUpId() {
		return signUpId;
	}
	/**
	 * 签收运单号
	 */
	public void setSignUpId(String signUpId) {
		this.signUpId = signUpId;
	}
	/**
	 * 是否成功（1： 成功 0: 失败）
	 */
	public String getSuccess() {
		return success;
	}
	/**
	 * 是否成功（1： 成功 0: 失败）
	 */
	public void setSuccess(String success) {
		this.success = success;
	}
}
