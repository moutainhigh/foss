package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

/**
 * 合伙人部门预存金开单额度查询接口响应信息实体
 * 2016年1月25日 13:42:23 葛亮亮 
 */
public class SynPartenerPrestoreDeductResponse implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3602823668968141010L;
	/**
	 * 返回失败信息
	 */
	private String errorInfo;	
	/**
	 * 返回是否成功标识
	 */
	private boolean isSuccess;
	
	/**
	 * @return  the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}
