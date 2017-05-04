package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.util.Date;

/**
 * 家装送装明细及签收确认信息
 * @author 269701
 * @date 2015-12-04
 *
 */
public class DpjzSignInDetailDto {
	/**
	 * ID
	 */
	private String id;
	/**
	 * dpId
	 */
	private String dopId;
	/**
	 * 运单号
	 * 
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
	 * 供应商签收信息
	 */
	private String signInMsg;
	/**
	 * 最后操作人
	 */
	private String lastOperUser;
	/**
	 * 最后操作人编码
	 */
	private String lastOperUserCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后操作时间
	 */
	private Date lastOperTime;
	/**
	 * 核对意见
	 */
	private String checkOpinion;
	/**
	 * 操作状态
	 * 未操作--UNCOMMITTED
	 * 同意--PASS
	 * 不同意--NOTPASS
	 */
	private String status;
	/**
	 * 起止时间--开始时间
	 */
	private String startEndTimeFrom;
	/**
	 * 起止时间--终止时间
	 */
	private String startEndTimeTo;
	/**
	 * 当前登录部门
	 */
	private String currentOrg;
	
	/**
	 * 收货部门code、
	 * 
	 */
	private String receiveOrgCode;
	/**
	 * 收货部门name
	 * 
	 */
	private String receiveOrgName;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the dopId
	 */
	public String getDopId() {
		return dopId;
	}
	/**
	 * @param dopId the dopId to set
	 */
	public void setDopId(String dopId) {
		this.dopId = dopId;
	}
	/**
	 * 运单号
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * 运单号
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * 德邦开单安装明细
	 * @return the transcargoName
	 */
	public String getTranscargoName() {
		return transcargoName;
	}
	/**
	 * 德邦开单安装明细
	 * @param transcargoName the transcargoName to set
	 */
	public void setTranscargoName(String transcargoName) {
		this.transcargoName = transcargoName;
	}
	/**
	 * 供应商送装明细
	 * @return the realInstallInfo
	 */
	public String getRealInstallInfo() {
		return realInstallInfo;
	}
	/**
	 * 供应商送装明细
	 * @param realInstallInfo the realInstallInfo to set
	 */
	public void setRealInstallInfo(String realInstallInfo) {
		this.realInstallInfo = realInstallInfo;
	}
	/**
	 * 供应商反馈时间
	 * @return the feedBackTime
	 */
	public Date getFeedBackTime() {
		return feedBackTime;
	}
	/**
	 * 供应商反馈时间
	 * @param feedBackTime the feedBackTime to set
	 */
	public void setFeedBackTime(Date feedBackTime) {
		this.feedBackTime = feedBackTime;
	}
	/**
	 * 供应商签收信息
	 * @return the signInMsg
	 */
	public String getSignInMsg() {
		return signInMsg;
	}
	/**
	 * 供应商签收信息
	 * @param signInMsg the signInMsg to set
	 */
	public void setSignInMsg(String signInMsg) {
		this.signInMsg = signInMsg;
	}
	/**
	 * 最后操作人
	 * @return the lastOperUser
	 */
	public String getLastOperUser() {
		return lastOperUser;
	}
	
	/**
	 * 最后操作人编码
	 * @return the lastOperUserCode
	 */
	public String getLastOperUserCode() {
		return lastOperUserCode;
	}
	/**
	 * 最后操作人编码
	 * @param lastOperUserCode the lastOperUserCode to set
	 */
	public void setLastOperUserCode(String lastOperUserCode) {
		this.lastOperUserCode = lastOperUserCode;
	}
	/**
	 * 最后操作人
	 * @param lastOperUser the lastOperUser to set
	 */
	public void setLastOperUser(String lastOperUser) {
		this.lastOperUser = lastOperUser;
	}
	/**
	 * 最后操作时间
	 * @return the lastOperTime
	 */
	public Date getLastOperTime() {
		return lastOperTime;
	}
	/**
	 * 最后操作时间
	 * @param lastOperTime the lastOperTime to set
	 */
	public void setLastOperTime(Date lastOperTime) {
		this.lastOperTime = lastOperTime;
	}
	/**
	 * 核对意见
	 * @return the checkOpinion
	 */
	public String getCheckOpinion() {
		return checkOpinion;
	}
	/**
	 * 核对意见
	 * @param checkOpinion the checkOpinion to set
	 */
	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
	/**
	 * 操作状态
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 操作状态
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the startEndTimeFrom
	 */
	public String getStartEndTimeFrom() {
		return startEndTimeFrom;
	}
	/**
	 * @param startEndTimeFrom the startEndTimeFrom to set
	 */
	public void setStartEndTimeFrom(String startEndTimeFrom) {
		this.startEndTimeFrom = startEndTimeFrom;
	}
	/**
	 * @return the startEndTimeTo
	 */
	public String getStartEndTimeTo() {
		return startEndTimeTo;
	}
	/**
	 * @param startEndTimeTo the startEndTimeTo to set
	 */
	public void setStartEndTimeTo(String startEndTimeTo) {
		this.startEndTimeTo = startEndTimeTo;
	}
	/**
	 * 供应商名称
	 * @return the suppName
	 */
	public String getSuppName() {
		return suppName;
	}
	/**
	 * 供应商名称
	 * @param suppName the suppName to set
	 */
	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}
	/**
	 * 供应商编码
	 * @return the suppCode
	 */
	public String getSuppCode() {
		return suppCode;
	}
	/**
	 * 供应商编码
	 * @param suppCode the suppCode to set
	 */
	public void setSuppCode(String suppCode) {
		this.suppCode = suppCode;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCurrentOrg() {
		return currentOrg;
	}
	public void setCurrentOrg(String currentOrg) {
		this.currentOrg = currentOrg;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	
}
