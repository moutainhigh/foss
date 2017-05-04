package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

/**
 *  供应商接口的请求（DOP提供）
 * @author 232607 
 * @date 2015-12-21 下午5:25:37
 * @since
 * @version
 */
public class SupplierRequest {
	/**
	 * 供应商id
	 */
	private String supplierId;
	/**
	 * 供应商code
	 */
	private String supplierCode;
	/**
	 * 供应商全称
	 */
	private String supplierFullName;
	/**
	 * 供应商简称
	 */
	private String supplierSimpleName;
	/**
	 * 联系人
	 */
	private String contactPerson;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 联系地址
	 */
	private String contactAddress;
	/**
	 * 家具
	 */
	private String furnitureFlag;
	/**
	 * 家电
	 */
	private String householdFlag;
	/**
	 * 建材
	 */
	private String constructionFlag;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 禁用时间、启动时间
	 */
	private Date cancelTime;
	/**
	 * 供应商地址（目前未使用）
	 */
	private String supplierUrl;
	/**
	 * 是否启用
	 */
	private String activeFlag;
	/**
	 * 与供应商交互appkey秘钥
	 */
	private String appkey;

	/**
	 * 编码格式
	 */
	private String encoding;
	/**
	 * 给供应商推送到达信息URL
	 */
	private String sendArriveInfoUrl;

	/**
	 * 给够供应商
	 */
	private String sendMailInfoUrl;

	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**************账单推送使用******************/
	private Date sendTime; // 推送时间
	private Integer sendStatus; // 失败：0; 成功：1;
	private Integer sendTimes; // 推送次数：累计 5次
//	private String sendResult; // 推送结果：成功：SUCCESS, 失败：FAIL
	private String sendResultMessage; // 推送结果信息（sendResult 为FAIL失败才会有）
	
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierFullName() {
		return supplierFullName;
	}
	public void setSupplierFullName(String supplierFullName) {
		this.supplierFullName = supplierFullName;
	}
	public String getSupplierSimpleName() {
		return supplierSimpleName;
	}
	public void setSupplierSimpleName(String supplierSimpleName) {
		this.supplierSimpleName = supplierSimpleName;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getFurnitureFlag() {
		return furnitureFlag;
	}
	public void setFurnitureFlag(String furnitureFlag) {
		this.furnitureFlag = furnitureFlag;
	}
	public String getHouseholdFlag() {
		return householdFlag;
	}
	public void setHouseholdFlag(String householdFlag) {
		this.householdFlag = householdFlag;
	}
	public String getConstructionFlag() {
		return constructionFlag;
	}
	public void setConstructionFlag(String constructionFlag) {
		this.constructionFlag = constructionFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getSupplierUrl() {
		return supplierUrl;
	}
	public void setSupplierUrl(String supplierUrl) {
		this.supplierUrl = supplierUrl;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getSendArriveInfoUrl() {
		return sendArriveInfoUrl;
	}
	public void setSendArriveInfoUrl(String sendArriveInfoUrl) {
		this.sendArriveInfoUrl = sendArriveInfoUrl;
	}
	public String getSendMailInfoUrl() {
		return sendMailInfoUrl;
	}
	public void setSendMailInfoUrl(String sendMailInfoUrl) {
		this.sendMailInfoUrl = sendMailInfoUrl;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Integer getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}
	public String getSendResultMessage() {
		return sendResultMessage;
	}
	public void setSendResultMessage(String sendResultMessage) {
		this.sendResultMessage = sendResultMessage;
	}
	
	
	
	
	
}
