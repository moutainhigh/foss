package com.deppon.foss.module.transfer.common.api.shared.vo;

import java.io.Serializable;

/**
*
*@author 268220-chen min
*@date  2016-5-11 下午3:59:15
*/

public class ResultBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 1;

	public static final int FAILURE = 0;

	public static <T> ResultBean<T> successResult() {
	    ResultBean<T> successResult = new ResultBean<T>();
	    successResult.status = SUCCESS;
	    return successResult;
	}

	public static <T> ResultBean<T> failureResult() {
	    ResultBean<T> failureResult = new ResultBean<T>();
	    failureResult.status = FAILURE;
	    return failureResult;
	}
	/**
	 * 结果
	 */
	private int status;
	/**
	* 异常信息
	*/
	private String exMsg;

	private T data;

	public int getStatus() {
	    return status;
	}

	public void setStatus(int status) {
	    this.status = status;
	}

	public String getExMsg() {
	    return exMsg;
	}

	public void setExMsg(String exMsg) {
	    this.exMsg = exMsg;
	}

	public T getData() {
	    return data;
	}

	public void setData(T data) {
	    this.data = data;
	}
	
}
