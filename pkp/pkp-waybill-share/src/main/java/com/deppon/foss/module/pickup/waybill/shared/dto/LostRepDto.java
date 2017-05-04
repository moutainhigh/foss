package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 
 * 查询QMS差错开运单号返回
 * 
 * @author huangwei
 * @date 2015-11-14 上午9:08:55
 */
public class LostRepDto implements Serializable {

	private static final long serialVersionUID = -3453453457344232343L;

	/**
	 * 是否成功
	 */
	private boolean resultType;

	/**
	 * 异常信息
	 */
	private String message;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResultType() {
		return resultType;
	}

	public void setResultType(boolean resultType) {
		this.resultType = resultType;
	}
}