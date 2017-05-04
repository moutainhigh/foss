/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/ArriveSheetEntity.java
 * 
 * FILE NAME        	: ArriveSheetEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * （到达联管理实体）
 * @author dp-dengtingting
 * @date 2012-10-13 上午10:41:47
 */
public class ArriveSheetEntity extends BaseEntity {
	/**
	 * @Fields serialVersionUID : 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  运单号
	 */
	private String waybillNo;
	
	/**
	 * 到达联编号
	 */
	private String arrivesheetNo;
	private String [] arrivesheetNos;
	
	/**
	 * 提货人名称
	 */
	private String deliverymanName;

	/** 
	 * 证件类型
	 */
	private String identifyType; 

	/**
	 * 证件号码
	 */
	private String identifyCode;

	/**
	 * 签收情况
	 */
	private String situation;

	/**
	 * 到达联件数
	 */
	private Integer arriveSheetGoodsQty;

	/**
	 * 签收件数
	 */
	private Integer signGoodsQty; 

	/**
	 * 签收备注
	 */
	private String signNote;

	/**
	 * 拉回原因优化--添加其他原因情况下的备注信息息306566  DN201601290015 拉回原因优化 
	 */
	private String pullbackNote;
	
	public String getPullbackNote() {
		return pullbackNote;
	}
	public void setPullbackNote(String pullbackNote) {
		this.pullbackNote = pullbackNote;
	}
	/**
	 * 签收时间
	 */
	private Date signTime; 
	
	/***
	 * 是否外包装完好
	 */
	private String packingResult;
	
	/***
	 * 短少量
	 */
	private String alittleShort;
	
	/***
	 * 签收人类型
	 */
	private String deliverymanType;
	

	/**
	 * 是否打印
	 */
	private String isPrinted;  

	/**
	 * 打印次数
	 */
	private Integer printtimes; 

	/**
	 * 创建人
	 */
	private String createUserName; 

	/**
	 * 创建人编码
	 */
	private String createUserCode; 

	/**
	 * 创建部门
	 */
	private String createOrgName; 

	/**
	 * 创建部门编码
	 */
	private String createOrgCode; 

	/**
	 * 创建时间
	 */
	private Date createTime; 

	/**
	 * 到达联状态
	 */
	private String status;

	/**
	 * 是否PDA签到
	 */
	private String isPdaSign;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否必送货
	 */
	private String isSentRequired; 
	
	/**
	 * 是否需要发票
	 */
	private String isNeedInvoice; 
	
	/**
	 * 提前通知内容
	 */
	private String preNoticeContent; 
	
	/**
	 * 送货要求
	 */
	private String deliverRequire; 

	/**
	 * 是否审批中
	 */
	private String isRfcing;

	/**
	 * 标签编号
	 */
	private String tagNumber; 

	/**
	 * 签收操作时间
	 */
	private Date operateTime;
	
	/**
	 *  操作人
	 */
	private String operator;
	
	/**
	 *  操作人编码
	 */
	private String operatorCode;
	
	/**
	 *  操作部门名称
	 */
	private String operateOrgName;
	
	/**
	 *  操作部门编码
	 */
	private String operateOrgCode;
	/**
	 *  是否作废
	 */
	private String destroyed;
	
	/**
	 * 有效日期
	 */
	private Date modifyTime;
	
	/**
	 * 并发控制使用 
	 *			  原到达联件数
	 */
	private Integer oldArriveSheetGoodsQty;
	/**
	 * 并发控制使用 
	 * 			原到达联状态
	 */
	private String oldStatus;
	
	/**
	 * 再次送货时间
	 */
	private Date nextDeliverTime;
	/**
	 * 送货时间
	 */
	private Date deliverDate;
	/**
	 * 派送单状态
	 */
	private String createType;
	
	//提货方式
	private String receiveMethod;

	private String source;
	// 打印时间
	private Date printTime;
	// 打印部门编号
	private String printOrgCode;
	// 打印部门名称
	private String printOrgName;
	// 打印人
	private String printUserName;
	// 打印人编码
	private String printUserCode;
	/**
	 * 合送编码
	 */
	private String togetherSendCode;
	/**
	 * 到达联签收状态 -全部签收、部分签收、未签收
	 */
	private String signStatus;
    /**
     * 是否快递员派送
     */
	private String isCourierDelivery;
	/**
     * 快递员工号
     */
	private String sendExpressEmpCode;
	
	/**
	 * 是否是合作伙伴
	 */
	private String isPartner;
	/**
	 * 伙伴姓名
	 */
	private String partnerName;
	/**
	 * 伙伴手机
	 */
	private String partnerPhone;
	
	/**
	 * 外发流水号，一票多件外发签收使用
	 */
	private String serialNo;
	
	/**
	 * 外发单号
	 */
	private String externalWaybillNo;
	
	/**
	 * 是否合伙人快递
	 */
	private String isPtpExp;
	
	public String getExternalWaybillNo() {
		return externalWaybillNo;
	}
	public void setExternalWaybillNo(String externalWaybillNo) {
		this.externalWaybillNo = externalWaybillNo;
	}
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the isCourierDelivery
	 */
	public String getIsCourierDelivery() {
		return isCourierDelivery;
	}
	/**
	 * @param isCourierDelivery the isCourierDelivery to set
	 */
	public void setIsCourierDelivery(String isCourierDelivery) {
		this.isCourierDelivery = isCourierDelivery;
	}
	/**
	 * @return the sendExpressEmpCode
	 */
	public String getSendExpressEmpCode() {
		return sendExpressEmpCode;
	}
	/**
	 * @param sendExpressEmpCode the sendExpressEmpCode to set
	 */
	public void setSendExpressEmpCode(String sendExpressEmpCode) {
		this.sendExpressEmpCode = sendExpressEmpCode;
	}
	
	/**
	 * @return the isPartner
	 */
	public String getIsPartner() {
		return isPartner;
	}
	/**
	 * @param isPartner the isPartner to set
	 */
	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}
	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}
	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	/**
	 * @return the partnerPhone
	 */
	public String getPartnerPhone() {
		return partnerPhone;
	}
	/**
	 * @param partnerPhone the partnerPhone to set
	 */
	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
	/**
	 * * 有参构造函数
	 * 		（运单号，状态，是否有效,是否作废）.
	 * @param waybillNo
	 *            the waybill no
	 * @param status
	 *            the status
	 * @param active
	 *            the active
	 * @param destroyed
	 *            the destroyed
	 */
	public ArriveSheetEntity(String waybillNo, String status, String active,
			String destroyed) {
		this.waybillNo = waybillNo;
		this.status = status;
		this.active = active;
		this.destroyed = destroyed;
	}

	/**
	 * * 有参构造函数
	 * 			（运单号，状态，是否有效）.
	 * 
	 * @param waybillNo
	 *            the waybill no
	 * @param status
	 *            the status
	 * @param active
	 *            the active
	 */
	public ArriveSheetEntity(String waybillNo, String status, String active) {
		this.waybillNo = waybillNo;
		this.status = status;
		this.active = active;
	}

	/**
	 * * 无参构造函数.
	 */
	public ArriveSheetEntity() {

	}

	/**
	 * Gets the 是否有效.
	 *
	 * @return the 是否有效
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the 是否有效.
	 *
	 * @param active the new 是否有效
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the 到达联件数.
	 *
	 * @return the 到达联件数
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * Sets the 到达联件数.
	 *
	 * @param arriveSheetGoodsQty the new 到达联件数
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	/**
	 * Gets the 签收件数.
	 *
	 * @return the 签收件数
	 */
	public Integer getSignGoodsQty() {
		return signGoodsQty;
	}

	/**
	 * Sets the 签收件数.
	 *
	 * @param signGoodsQty the new 签收件数
	 */
	public void setSignGoodsQty(Integer signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}

	/**
	 * Gets the 是否PDA签到.
	 *
	 * @return the 是否PDA签到
	 */
	public String getIsPdaSign() {
		return isPdaSign;
	}

	/**
	 * Sets the 是否PDA签到.
	 *
	 * @param isPdaSign the new 是否PDA签到
	 */
	public void setIsPdaSign(String isPdaSign) {
		this.isPdaSign = isPdaSign;
	}

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivesheetNo the new 到达联编号
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the 提货人名称.
	 *
	 * @return the 提货人名称
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * Sets the 提货人名称.
	 *
	 * @param deliverymanName the new 提货人名称
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * Gets the 证件类型.
	 *
	 * @return the 证件类型
	 */
	public String getIdentifyType() {
		return identifyType;
	}

	/**
	 * Sets the 证件类型.
	 *
	 * @param identifyType the new 证件类型
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * Gets the 证件号码.
	 *
	 * @return the 证件号码
	 */
	public String getIdentifyCode() {
		return identifyCode;
	}

	/**
	 * Sets the 证件号码.
	 *
	 * @param identifyCode the new 证件号码
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	/**
	 * Gets the 签收情况.
	 *
	 * @return the 签收情况
	 */
	public String getSituation() {
		return situation;
	}

	/**
	 * Sets the 签收情况.
	 *
	 * @param situation the new 签收情况
	 */
	public void setSituation(String situation) {
		this.situation = situation;
	}

	/**
	 * Gets the 签收备注.
	 *
	 * @return the 签收备注
	 */
	public String getSignNote() {
		return signNote;
	}

	/**
	 * Sets the 签收备注.
	 *
	 * @param signNote the new 签收备注
	 */
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	/**
	 * Gets the 签收时间.
	 *
	 * @return the 签收时间
	 */
	@DateFormat(formate = "yyyy-MM-dd hh:mm:ss")
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * Sets the 签收时间.
	 *
	 * @param signTime the new 签收时间
	 */
	@DateFormat(formate = "yyyy-MM-dd hh:mm:ss")
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * Gets the 是否打印.
	 *
	 * @return the 是否打印
	 */
	public String getIsPrinted() {
		return isPrinted;
	}

	/**
	 * Sets the 是否打印.
	 *
	 * @param isPrinted the new 是否打印
	 */
	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}

	/**
	 * Gets the 打印次数.
	 *
	 * @return the 打印次数
	 */
	public Integer getPrinttimes() {
		return printtimes;
	}

	/**
	 * Sets the 打印次数.
	 *
	 * @param printtimes the new 打印次数
	 */
	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	/**
	 * Gets the 创建人.
	 *
	 * @return the 创建人
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the 创建人.
	 *
	 * @param createUserName the new 创建人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the 创建人编码.
	 *
	 * @return the 创建人编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Sets the 创建人编码.
	 *
	 * @param createUserCode the new 创建人编码
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Gets the 创建部门.
	 *
	 * @return the 创建部门
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * Sets the 创建部门.
	 *
	 * @param createOrgName the new 创建部门
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * Gets the 创建部门编码.
	 *
	 * @return the 创建部门编码
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * Sets the 创建部门编码.
	 *
	 * @param createOrgCode the new 创建部门编码
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * Gets the 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the 到达联状态.
	 *
	 * @return the 到达联状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 到达联状态.
	 *
	 * @param status the new 到达联状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the 是否必送货.
	 *
	 * @return the 是否必送货
	 */
	public String getIsSentRequired() {
		return isSentRequired;
	}

	/**
	 * Sets the 是否必送货.
	 *
	 * @param isSentRequired the new 是否必送货
	 */
	public void setIsSentRequired(String isSentRequired) {
		this.isSentRequired = isSentRequired;
	}

	/**
	 * Gets the 是否需要发票.
	 *
	 * @return the 是否需要发票
	 */
	public String getIsNeedInvoice() {
		return isNeedInvoice;
	}

	/**
	 * Sets the 是否需要发票.
	 *
	 * @param isNeedInvoice the new 是否需要发票
	 */
	public void setIsNeedInvoice(String isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	/**
	 * Gets the 提前通知内容.
	 *
	 * @return the 提前通知内容
	 */
	public String getPreNoticeContent() {
		return preNoticeContent;
	}

	/**
	 * Sets the 提前通知内容.
	 *
	 * @param preNoticeContent the new 提前通知内容
	 */
	public void setPreNoticeContent(String preNoticeContent) {
		this.preNoticeContent = preNoticeContent;
	}

	/**
	 * Gets the 送货要求.
	 *
	 * @return the 送货要求
	 */
	public String getDeliverRequire() {
		return deliverRequire;
	}

	/**
	 * Sets the 送货要求.
	 *
	 * @param deliverRequire the new 送货要求
	 */
	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the 是否审批中.
	 *
	 * @return the 是否审批中
	 */
	public String getIsRfcing() {
		return isRfcing;
	}

	/**
	 * Sets the 是否审批中.
	 *
	 * @param isRfcing the new 是否审批中
	 */
	public void setIsRfcing(String isRfcing) {
		this.isRfcing = isRfcing;
	}

	/**
	 * Gets the 标签编号.
	 *
	 * @return the 标签编号
	 */
	public String getTagNumber() {
		return tagNumber;
	}

	/**
	 * Sets the 标签编号.
	 *
	 * @param tagNumber the new 标签编号
	 */
	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	/**
	 * Gets the 签收操作时间.
	 *
	 * @return the 签收操作时间
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * Sets the 签收操作时间.
	 *
	 * @param operateTime the new 签收操作时间
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * Gets the 操作人.
	 *
	 * @return the 操作人
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the 操作人.
	 *
	 * @param operator the new 操作人
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the 操作人编码.
	 *
	 * @return the 操作人编码
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * Sets the 操作人编码.
	 *
	 * @param operatorCode the new 操作人编码
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * Gets the 操作部门名称.
	 *
	 * @return the 操作部门名称
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * Sets the 操作部门名称.
	 *
	 * @param operateOrgName the new 操作部门名称
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * Gets the 操作部门编码.
	 *
	 * @return the 操作部门编码
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * Sets the 操作部门编码.
	 *
	 * @param operateOrgCode the new 操作部门编码
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * Gets the 是否作废.
	 *
	 * @return the 是否作废
	 */
	public String getDestroyed() {
		return destroyed;
	}

	/**
	 * Sets the 是否作废.
	 *
	 * @param destroyed the new 是否作废
	 */
	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * Gets the 有效日期.
	 *
	 * @return the 有效日期
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Sets the 有效日期.
	 *
	 * @param modifyTime the new 有效日期
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * Gets the 并发控制使用   原到达联件数.
	 *
	 * @return the 并发控制使用   原到达联件数
	 */
	public Integer getOldArriveSheetGoodsQty() {
		return oldArriveSheetGoodsQty;
	}

	/**
	 * Sets the 并发控制使用   原到达联件数.
	 *
	 * @param oldArriveSheetGoodsQty the new 并发控制使用   原到达联件数
	 */
	public void setOldArriveSheetGoodsQty(Integer oldArriveSheetGoodsQty) {
		this.oldArriveSheetGoodsQty = oldArriveSheetGoodsQty;
	}

	/**
	 * Gets the 并发控制使用 原到达联状态.
	 *
	 * @return the 并发控制使用 原到达联状态
	 */
	public String getOldStatus() {
		return oldStatus;
	}

	/**
	 * Sets the 并发控制使用 原到达联状态.
	 *
	 * @param oldStatus the new 并发控制使用 原到达联状态
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	/**
	 * @return deliverDate : return the property deliverDate.
	 */
	public Date getDeliverDate() {
		return deliverDate;
	}

	/**
	 * @param deliverDate : set the property deliverDate.
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;

	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public String getPrintOrgCode() {
		return printOrgCode;
	}

	public void setPrintOrgCode(String printOrgCode) {
		this.printOrgCode = printOrgCode;
	}

	public String getPrintOrgName() {
		return printOrgName;
	}

	public void setPrintOrgName(String printOrgName) {
		this.printOrgName = printOrgName;
	}

	public String getPrintUserName() {
		return printUserName;
	}

	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}

	public String getPrintUserCode() {
		return printUserCode;
	}

	public void setPrintUserCode(String printUserCode) {
		this.printUserCode = printUserCode;
	}

	public String[] getArrivesheetNos() {
		return arrivesheetNos;
	}

	public void setArrivesheetNos(String[] arrivesheetNos) {
		this.arrivesheetNos = arrivesheetNos;
	}

	public String getTogetherSendCode() {
		return togetherSendCode;
	}

	public void setTogetherSendCode(String togetherSendCode) {
		this.togetherSendCode = togetherSendCode;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	/**
	 * Gets the 外包装是否完好.
	 *
	 * @return the  外包装是否完好
	 */
	public String getPackingResult() {
		return packingResult;
	}
	/**
	 * Sets the 外包装是否完好.
	 *
	 * @param packingResult the new 外包装是否完好
	 */
	public void setPackingResult(String packingResult) {
		this.packingResult = packingResult;
	}

	/**
	 * Gets the 短少量.
	 *
	 * @return the 短少量
	 */
	public String getAlittleShort() {
		return alittleShort;
	}

	/**
	 * Sets the 短少量.
	 *
	 * @param alittleShort the new 短少量
	 */
	public void setAlittleShort(String alittleShort) {
		this.alittleShort = alittleShort;
	}

	/**
	 * Gets the 签收人类型.
	 *
	 * @return the 签收人类型
	 */
	public String getDeliverymanType() {
		return deliverymanType;
	}

	/**
	 * Sets the 签收人类型.
	 *
	 * @param deliverymanType the new 签收人类型
	 */
	public void setDeliverymanType(String deliverymanType) {
		this.deliverymanType = deliverymanType;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}
	public Date getNextDeliverTime() {
		return nextDeliverTime;
	}
	public void setNextDeliverTime(Date nextDeliverTime) {
		this.nextDeliverTime = nextDeliverTime;
	}
	/**
	 * @return the isPtpExp
	 */
	public String getIsPtpExp() {
		return isPtpExp;
	}
	/**
	 * @param isPtpExp the isPtpExp to set
	 */
	public void setIsPtpExp(String isPtpExp) {
		this.isPtpExp = isPtpExp;
	}
	
}