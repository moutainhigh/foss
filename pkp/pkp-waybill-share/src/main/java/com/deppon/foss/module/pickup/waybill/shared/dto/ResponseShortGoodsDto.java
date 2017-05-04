package com.deppon.foss.module.pickup.waybill.shared.dto;

/***
 * @clasaName:com.deppon.foss.module.pickup.waybill.shared.dto.ResponseShortGoodsDto
 * @author: foss-yuting
 * @description: 新增内物短少差错-oa返回dto</br>
 * @date:2014年12月19日 下午2:27:21
 */
public class ResponseShortGoodsDto {

	/**
	 * 唯一标识
	 */
	private String uniqueid;
	
	/**
	 * 是否成功(0:上报失败；1:上报成功;2:重复上报)
	 */
	private String reportResult;
	
	/**
	 * 失败信息
	 */
	private String dealStatus;
	
	/**
	 * 处理编号
	 */
	private String handlingid;
	
	/**
	 * 失败原因
	 */
	private String failureReason;

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getReportResult() {
		return reportResult;
	}

	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getHandlingid() {
		return handlingid;
	}

	public void setHandlingid(String handlingid) {
		this.handlingid = handlingid;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
}
