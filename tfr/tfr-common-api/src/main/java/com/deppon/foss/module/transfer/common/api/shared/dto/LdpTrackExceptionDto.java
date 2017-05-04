package com.deppon.foss.module.transfer.common.api.shared.dto;


/**
 * 上传外发单轨迹记录
 * 落地配公司调用Foss系统异常实体
 * @author ibm-liuzhaowei
 * @date 2012-12-4 下午7:15:36
 */
public class LdpTrackExceptionDto extends LdpExceptionDto{
	
	/**
	 * 轨迹id
	 */
	public String traceId;
	/**
	 * 是否成功（1： 成功 0: 失败）
	 */
	public String success;
	
	/**
	 * 轨迹id
	 */
	public String getTraceId() {
		return traceId;
	}
	/**
	 * 轨迹id
	 */
	public void setTraceId(String traceId) {
		this.traceId = traceId;
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
