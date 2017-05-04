package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

/**
 * 部门月结额度查询接口响应信息实体
 * 2016年1月23日 17:05:22 葛亮亮
 */
public class SynPartenerMonthlyLineDeductResponse implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3602823143168141010L;
	/**
	 * 返回失败信息
	 */
	private String errorMsg;
	/**
	 * 返回是否成功标识
	 */
	private boolean isSuccess;
	/**
	 * @return  the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
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

}
