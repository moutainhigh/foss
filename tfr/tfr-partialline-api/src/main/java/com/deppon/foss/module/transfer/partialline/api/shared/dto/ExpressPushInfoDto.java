package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.util.Date;

/**
 * @author gongjp
 * @function 快递100推送消息
 * @date 2015年10月15日上午16:19:59
 */
public class ExpressPushInfoDto {
	// id
	private String id;

	// 运单号
	private String waybillNo;

	// 代理单号
	private String agentWaybillNo;

	// 代理公司编码
	private String agentCompanyCode;

	// 出错原因编码
	private String reasonCode;

	// 出错原因(出错原因概要信息)
	private String reason;

	// 创建时间
	private Date createTime;

	// 阶段(轨迹状态)
	private int trackStage;

	// 打印类型
	private String printType;

	// 备注组
	private String remark;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getAgentWaybillNo() {
		return agentWaybillNo;
	}

	public void setAgentWaybillNo(String agentWaybillNo) {
		this.agentWaybillNo = agentWaybillNo;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	/****
	 * 带参数的构造方法
	 * @param id
	 * @param waybillNo
	 * @param agentWaybillNo
	 * @param agentCompanyCode
	 * @param reasonCode
	 * @param reason
	 * @param createTime
	 * @param trackStage
	 * @param printType
	 * @param remark
	 */
	public ExpressPushInfoDto(String id, String waybillNo,
			String agentWaybillNo, String agentCompanyCode, String reasonCode,
			String reason, int trackStage,
			String printType, String remark) {
		super();
		this.id = id;
		this.waybillNo = waybillNo;
		this.agentWaybillNo = agentWaybillNo;
		this.agentCompanyCode = agentCompanyCode;
		this.reasonCode = reasonCode;
		this.reason = reason;
		this.trackStage = trackStage;
		this.printType = printType;
		this.remark = remark;
	}
	
	public int getTrackStage() {
		return trackStage;
	}

	public void setTrackStage(int trackStage) {
		this.trackStage = trackStage;
	}

	public ExpressPushInfoDto() {
		super();
	}
}
