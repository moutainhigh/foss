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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/RepaymentArrivesheetDto.java
 * 
 * FILE NAME        	: RepaymentArrivesheetDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 付款和到达联对象.
 *
 * @author ibm-
 * 		lizhiguo
 * @date 2012-11-16 
 * 		下午2:07:50
 */
public class RepaymentArrivesheetDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4418318389982178940L;
	
	/** 付款LIST. */
	private List<RepaymentEntity> repaymentEntityList;
	
	/** 到达联LIST. */
	private List<ArriveSheetEntity> arriveSheetEntityList;
	
	/** 运单. */
	private WaybillEntity waybillEntity;
	
	/** 运单签收结果. */
	private WaybillSignResultEntity waybillSignResultEntity;
	
	/** 付款StringID. */
	private String repaymentIds;
	
	/** 到达联StringID. */
	private String arriveSheetIds;
	
	/** 付款是否可以修改金额. private String isUpdateFlg;*/
	
	/**
	 * 是否审批中的到达联 private String isAuditFlg;
	 */
	/**
	 * 付款审批中
	 */
	private String isAuditingRepaymentFlg;
	
	/** 到达联审批中. */
	private String isAuditingArrivesheetFlg;
	
	/** 放检查消息. */
	private List<String> msgList = new ArrayList<String>();
	
	/**
	 * 是否可以反结清
	 */
	private String isAllReverse;
	
	/**
	 * 是否是合伙人
	 */
	private String partnerBillingLogo;
	
	/**
	 * Gets the msg list.
	 *
	 * @return the msgList
	 */
	public List<String> getMsgList() {
		return msgList;
	}
	
	/**
	 * Sets the msg list.
	 *
	 * @param msgList the msgList to set
	 */
	public void setMsgList(List<String> msgList) {
		this.msgList = msgList;
	}
	
	/**
	 * Gets the is auditing repayment flg.
	 *
	 * @return isAuditingRepaymentFlg : return the property isAuditingRepaymentFlg.
	 */
	public String getIsAuditingRepaymentFlg() {
		return isAuditingRepaymentFlg;
	}
	
	/**
	 * Sets the is auditing repayment flg.
	 *
	 * @param isAuditingRepaymentFlg : set the property isAuditingRepaymentFlg.
	 */
	public void setIsAuditingRepaymentFlg(String isAuditingRepaymentFlg) {
		this.isAuditingRepaymentFlg = isAuditingRepaymentFlg;
	}
	
	/**
	 * Gets the is auditing arrivesheet flg.
	 *
	 * @return isAuditingArrivesheetFlg : return the property isAuditingArrivesheetFlg.
	 */
	public String getIsAuditingArrivesheetFlg() {
		return isAuditingArrivesheetFlg;
	}
	
	/**
	 * Sets the is auditing arrivesheet flg.
	 *
	 * @param isAuditingArrivesheetFlg : set the property isAuditingArrivesheetFlg.
	 */
	public void setIsAuditingArrivesheetFlg(String isAuditingArrivesheetFlg) {
		this.isAuditingArrivesheetFlg = isAuditingArrivesheetFlg;
	}
	
	/**
	 * Gets the repayment entity list.
	 *
	 * @return repaymentEntityList : return the property repaymentEntityList.
	 */
	public List<RepaymentEntity> getRepaymentEntityList() {
		return repaymentEntityList;
	}
	
	/**
	 * Sets the repayment entity list.
	 *
	 * @param repaymentEntityList : set the property repaymentEntityList.
	 */
	public void setRepaymentEntityList(List<RepaymentEntity> repaymentEntityList) {
		this.repaymentEntityList = repaymentEntityList;
	}
	
	/**
	 * Gets the arrive sheet entity list.
	 *
	 * @return arriveSheetEntityList : return the property arriveSheetEntityList.
	 */
	public List<ArriveSheetEntity> getArriveSheetEntityList() {
		return arriveSheetEntityList;
	}
	
	/**
	 * Sets the arrive sheet entity list.
	 *
	 * @param arriveSheetEntityList : set the property arriveSheetEntityList.
	 */
	public void setArriveSheetEntityList(
			List<ArriveSheetEntity> arriveSheetEntityList) {
		this.arriveSheetEntityList = arriveSheetEntityList;
	}
	
	/**
	 * Gets the waybill entity.
	 *
	 * @return waybillEntity : return the property waybillEntity.
	 */
	public WaybillEntity getWaybillEntity() {
		return waybillEntity;
	}
	
	/**
	 * Sets the waybill entity.
	 *
	 * @param waybillEntity : set the property waybillEntity.
	 */
	public void setWaybillEntity(WaybillEntity waybillEntity) {
		this.waybillEntity = waybillEntity;
	}
	
	/**
	 * Gets the repayment ids.
	 *
	 * @return repaymentIds : return the property repaymentIds.
	 */
	public String getRepaymentIds() {
		return repaymentIds;
	}
	
	/**
	 * Sets the repayment ids.
	 *
	 * @param repaymentIds : set the property repaymentIds.
	 */
	public void setRepaymentIds(String repaymentIds) {
		this.repaymentIds = repaymentIds;
	}
	
	/**
	 * Gets the arrive sheet ids.
	 *
	 * @return arriveSheetIds : return the property arriveSheetIds.
	 */
	public String getArriveSheetIds() {
		return arriveSheetIds;
	}
	
	/**
	 * Sets the arrive sheet ids.
	 *
	 * @param arriveSheetIds : set the property arriveSheetIds.
	 */
	public void setArriveSheetIds(String arriveSheetIds) {
		this.arriveSheetIds = arriveSheetIds;
	}
	
	/**
	 * Gets the waybill sign result entity.
	 *
	 * @return waybillSignResultEntity : return the property waybillSignResultEntity.
	 */
	public WaybillSignResultEntity getWaybillSignResultEntity() {
		return waybillSignResultEntity;
	}
	
	/**
	 * Sets the waybill sign result entity.
	 *
	 * @param waybillSignResultEntity : set the property waybillSignResultEntity.
	 */
	public void setWaybillSignResultEntity(
			WaybillSignResultEntity waybillSignResultEntity) {
		this.waybillSignResultEntity = waybillSignResultEntity;
	}

	public String getIsAllReverse() {
		return isAllReverse;
	}

	public void setIsAllReverse(String isAllReverse) {
		this.isAllReverse = isAllReverse;
	}

	public String getPartnerBillingLogo() {
		return partnerBillingLogo;
	}

	public void setPartnerBillingLogo(String partnerBillingLogo) {
		this.partnerBillingLogo = partnerBillingLogo;
	}
	
}