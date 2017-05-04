package com.deppon.foss.module.base.baseinfo.shared.response;

import java.io.Serializable;


/**
 * <p>
 * <h2>简述</h2>
 * 		<ol>对接FOSS系统的保理业务标记推送接口的响应信息实体类</ol>
 * <h2>功能描述</h2>
 * 		<ol>对接FOSS系统的保理业务标记推送接口的响应信息实体类</ol>
 * <h2>修改历史</h2>
 *    <ol>如有修改，添加修改历史</ol>
 * </p>
 * @author Administrator
 * @2016-9-27
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FOSSFactorFlagResponse implements Serializable{

	private String isSuccess;//是否调用成功，1表示调用成功,0表示调用失败
	private String errorMsg;//异常信息，当调用失败，返回失败信息
	
	public FOSSFactorFlagResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FOSSFactorFlagResponse(String isSuccess, String errorMsg) {
		super();
		this.isSuccess = isSuccess;
		this.errorMsg = errorMsg;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
