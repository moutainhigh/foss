package com.deppon.foss.module.pickup.waybill.server.utils.cubc.model;

import java.util.List;
/**
 * 
    * @ClassName: VestResponse
    * @Description: 返回的信息,由于灰度测试不可能返回异常的服务，所以不对请求的相应做状态码判断
    * @author 323098
    * @date 2016年12月31日
    *
 */
public class VestResponse {

	/**
	 * 是否成功（1：成功 ，0：失败）
	 */
	//private String isSuccess;
	/**
	 * 失败原因
	 */
	//private String failReason;
	/**
	 * 返回的结果
	 */
	private List<VestBatchResult> vestBatchResult;

	public List<VestBatchResult> getVestBatchResult() {
		return vestBatchResult;
	}

	public void setVestBatchResult(List<VestBatchResult> vestBatchResult) {
		this.vestBatchResult = vestBatchResult;
	}

	/*public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}*/
	
}
