package com.deppon.foss.module.transfer.common.api.shared.dto;


/**
 * 落地配公司调用Foss系统异常实体
 * @author ibm-liuzhaowei
 * @date 2012-12-4 下午7:15:36
 */
public class LdpSignUpExceptionDto extends LdpExceptionDto{
	
	/**
	 * 签收流水号
	 */
	public String signUpId;
	/**
	 * 是否成功（1： 成功 0: 失败）
	 */
	public String success;
	
	public String getSignUpId() {
		return signUpId;
	}
	/**
	 * 签收流水号
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
