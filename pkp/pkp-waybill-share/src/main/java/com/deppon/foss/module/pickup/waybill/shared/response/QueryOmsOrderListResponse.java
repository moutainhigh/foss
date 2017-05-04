package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;

public class QueryOmsOrderListResponse implements Serializable {
	
	/**
	 * 
	 * 同步接口返回类
	 * 
	 * @author 323098
	 * @date 2016-4-15 下午1:41:09
	 * @since
	 * @version
	 */

	/*
	 * 请求是否成功,成功 Y，失败 N,
	 */
	private String isSuccess = "N";
	/*
	 * 异常信息
	 */
	private String exceptionMsg;
	/*
	 * 结果信息
	 */
	private QueryOmsOrderListPageModelResponse data;

	/*
	 * 设置成功标志
	 */
	public String getIsSuccess() {
		return isSuccess;
	}

	/*
	 * 获取成功标志
	 */
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	/*
	 * 设置异常信息
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	/*
	 * 获取异常信息
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	/*
	 * 设置返回结果信息
	 */
	public QueryOmsOrderListPageModelResponse getData() {
		return data;
	}

	/*
	 * 获取返回结果信息
	 */
	public void setData(QueryOmsOrderListPageModelResponse data) {
		this.data = data;
	}

}
