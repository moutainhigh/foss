package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

public class SignInfoAuditRequest {
	/**
	 * 唯一请求Id：该请求Id为DOP推送给FOSS中转待审核信息时带的字段值
	 */
	private String uniqueRequestId;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 德邦开单安装明细
	 */
	private String transcargoName;

	/**
	 * 供应商送装明细
	 */
	private String realInstallInfo;

	/**
	 * 供应商反馈时间
	 */
	private Date feedBackTime;

	/**
	 * 供应商名称
	 */
	private String suppName;

	/**
	 * 供应商编码
	 */
	private String suppCode;

	/**
	 * 供应商签收时间
	 */
	private Date signTime;

	/**
	 * 签收状态01正常签收; 02异常-破损; 03异常-潮湿; 04异常-污染; 05异常-内物短少 ;06异常-其他 ;07同票多类异常;
	 * 08货物及费用与运单信息不符;
	 */
	private String signState;

	/**
	 * 签收备注
	 */
	private String signMemo;

	/**
	 * 签收件数
	 */
	private Integer signNumber;

	/**
	 * @return the uniqueRequestId
	 */
	public String getUniqueRequestId() {
		return uniqueRequestId;
	}

	/**
	 * @param uniqueRequestId the uniqueRequestId to set
	 */
	public void setUniqueRequestId(String uniqueRequestId) {
		this.uniqueRequestId = uniqueRequestId;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the transcargoName
	 */
	public String getTranscargoName() {
		return transcargoName;
	}

	/**
	 * @param transcargoName the transcargoName to set
	 */
	public void setTranscargoName(String transcargoName) {
		this.transcargoName = transcargoName;
	}

	/**
	 * @return the realInstallInfo
	 */
	public String getRealInstallInfo() {
		return realInstallInfo;
	}

	/**
	 * @param realInstallInfo the realInstallInfo to set
	 */
	public void setRealInstallInfo(String realInstallInfo) {
		this.realInstallInfo = realInstallInfo;
	}

	/**
	 * @return the feedBackTime
	 */
	public Date getFeedBackTime() {
		return feedBackTime;
	}

	/**
	 * @param feedBackTime the feedBackTime to set
	 */
	public void setFeedBackTime(Date feedBackTime) {
		this.feedBackTime = feedBackTime;
	}

	/**
	 * @return the suppName
	 */
	public String getSuppName() {
		return suppName;
	}

	/**
	 * @param suppName the suppName to set
	 */
	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}

	/**
	 * @return the suppCode
	 */
	public String getSuppCode() {
		return suppCode;
	}

	/**
	 * @param suppCode the suppCode to set
	 */
	public void setSuppCode(String suppCode) {
		this.suppCode = suppCode;
	}

	/**
	 * @return the signTime
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * @param signTime the signTime to set
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * @return the signState
	 */
	public String getSignState() {
		return signState;
	}

	/**
	 * @param signState the signState to set
	 */
	public void setSignState(String signState) {
		this.signState = signState;
	}

	/**
	 * @return the signMemo
	 */
	public String getSignMemo() {
		return signMemo;
	}

	/**
	 * @param signMemo the signMemo to set
	 */
	public void setSignMemo(String signMemo) {
		this.signMemo = signMemo;
	}

	/**
	 * @return the signNumber
	 */
	public Integer getSignNumber() {
		return signNumber;
	}

	/**
	 * @param signNumber the signNumber to set
	 */
	public void setSignNumber(Integer signNumber) {
		this.signNumber = signNumber;
	}
	
}
