package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class DpjzSignInDetialBillEntity extends BaseEntity{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 * dopId
	 */
	private String dopId;
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
	 * 供应商签收信
	 * 签收状态+签收备注+签收件数+供应商签收时间+供应商名称
	 */
	private String signInMsg;
	/**
	 * 供应商name
	 */
	private String suppName;
	/**
	 * 供应商code
	 */
	private String suppCode;
	
	/**
	 * 操作状态
	 * 
	 */
	private String status;
	/**
	 * 最后操作人
	 * 
	 */
	private String lastOperUser;
	/**
	 * 最后操作人编码
	 */
	private String lastOperUserCode;
	/**
	 * 最后操作时间
	 * 
	 */
	private Date lastOperTime;
	/**
	 * 核对意见
	 * 
	 */
	private String checkOpinion;
	/**
	 * 是否有效
	 * 
	 */
	private String active;
	
	/**
	 *  运单号
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
	 * @return the signInMsg
	 */
	public String getSignInMsg() {
		return signInMsg;
	}
	/**
	 * @param signInMsg the signInMsg to set
	 */
	public void setSignInMsg(String signInMsg) {
		this.signInMsg = signInMsg;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the lastOperUser
	 */
	public String getLastOperUser() {
		return lastOperUser;
	}
	/**
	 * @param lastOperUser the lastOperUser to set
	 */
	public void setLastOperUser(String lastOperUser) {
		this.lastOperUser = lastOperUser;
	}
	/**
	 * @return the lastOperTime
	 */
	public Date getLastOperTime() {
		return lastOperTime;
	}
	/**
	 * @param lastOperTime the lastOperTime to set
	 */
	public void setLastOperTime(Date lastOperTime) {
		this.lastOperTime = lastOperTime;
	}
	/**
	 * @return the checkOpinion
	 */
	public String getCheckOpinion() {
		return checkOpinion;
	}
	/**
	 * @param checkOpinion the checkOpinion to set
	 */
	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
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
	 * id
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * id
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the lastOperUserCode
	 */
	public String getLastOperUserCode() {
		return lastOperUserCode;
	}
	/**
	 * @param lastOperUserCode the lastOperUserCode to set
	 */
	public void setLastOperUserCode(String lastOperUserCode) {
		this.lastOperUserCode = lastOperUserCode;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
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
	
}
