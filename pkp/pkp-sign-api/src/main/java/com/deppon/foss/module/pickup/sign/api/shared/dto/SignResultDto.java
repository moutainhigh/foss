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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/SignResultDto.java
 * 
 * FILE NAME        	: SignResultDto.java
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
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;

/**
 * 运单变更--运单变更结果--到达联号
 * @author ibm-lizhiguo
 * @date 2012-11-5 上午10:42:50
 */
public class SignResultDto implements Serializable{
	private static final long serialVersionUID = -6854466354558384423L;
	/**
	 * 签收变更
	 */
	private SignRfcEntity signRfcEntity;
	/**
	 * 变更明细
	 */
	private List<SignRfcChangeDetailEntity> changeDetailentity;
	/**
	 * 反签收
	 */
	private RepaymentArrivesheetDto repaymentArrivesheetDto;
	/**
	 * 签收
	 */
	private ChangeDto changeDto;
	/**
	 * 付款修改原因
	 */
	private String repaymentChangeReason;
	/**
	 * 到达联修改原因
	 */
	private String arrivesheetChangeReason;
	/**
	 * 付款ID
	 */
	private String tSrvRepaymentId;
	/**
	 * 到达联ID
	 */
	private String tSrvArrivesheetId;
	/**
	 * 货签表ID
	 * */
	private String tSrvLabelTableId;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 上传RepaymentDto
	 */
	private List<AttachementEntity> repaymentFiles = new ArrayList<AttachementEntity>();
	/**
	 * 上传ArrivesheetDto
	 */
	private List<AttachementEntity> arrivesheetFiles = new ArrayList<AttachementEntity>();
	/**
	 * 上传运单签收结果Dto
	 */
	private List<AttachementEntity> waybillSignResultFiles = new ArrayList<AttachementEntity>();
	/**
	 * 上传反签收Dto
	 */
	private List<AttachementEntity> reverseSignRfcFiles = new ArrayList<AttachementEntity>();
	
	/**
	 * @return reverseSignRfcFiles : return the property reverseSignRfcFiles.
	 */
	public List<AttachementEntity> getReverseSignRfcFiles() {
		return reverseSignRfcFiles;
	}
	/**
	 * @param reverseSignRfcFiles : set the property reverseSignRfcFiles.
	 */
	public void setReverseSignRfcFiles(List<AttachementEntity> reverseSignRfcFiles) {
		this.reverseSignRfcFiles = reverseSignRfcFiles;
	}
	/**
	 * @return waybillSignResultFiles : return the property waybillSignResultFiles.
	 */
	public List<AttachementEntity> getWaybillSignResultFiles() {
		return waybillSignResultFiles;
	}
	/**
	 * @param waybillSignResultFiles : set the property waybillSignResultFiles.
	 */
	public void setWaybillSignResultFiles(List<AttachementEntity> waybillSignResultFiles) {
		this.waybillSignResultFiles = waybillSignResultFiles;
	}
	/**
	 * @return repaymentFiles : return the property repaymentFiles.
	 */
	public List<AttachementEntity> getRepaymentFiles() {
		return repaymentFiles;
	}
	/**
	 * @param repaymentFiles : set the property repaymentFiles.
	 */
	public void setRepaymentFiles(List<AttachementEntity> repaymentFiles) {
		this.repaymentFiles = repaymentFiles;
	}
	/**
	 * @return arrivesheetFiles : return the property arrivesheetFiles.
	 */
	public List<AttachementEntity> getArrivesheetFiles() {
		return arrivesheetFiles;
	}
	/**
	 * @param arrivesheetFiles : set the property arrivesheetFiles.
	 */
	public void setArrivesheetFiles(List<AttachementEntity> arrivesheetFiles) {
		this.arrivesheetFiles = arrivesheetFiles;
	}
	/**
	 * @return signRfcEntity : return the property signRfcEntity.
	 */
	public SignRfcEntity getSignRfcEntity() {
		return signRfcEntity;
	}
	/**
	 * @param signRfcEntity : set the property signRfcEntity.
	 */
	public void setSignRfcEntity(SignRfcEntity signRfcEntity) {
		this.signRfcEntity = signRfcEntity;
	}
	/**
	 * @return changeDetailentity : return the property changeDetailentity.
	 */
	public List<SignRfcChangeDetailEntity> getChangeDetailentity() {
		return changeDetailentity;
	}
	/**
	 * @param changeDetailentity : set the property changeDetailentity.
	 */
	public void setChangeDetailentity(
			List<SignRfcChangeDetailEntity> changeDetailentity) {
		this.changeDetailentity = changeDetailentity;
	}
	/**
	 * @return repaymentArrivesheetDto : return the property repaymentArrivesheetDto.
	 */
	public RepaymentArrivesheetDto getRepaymentArrivesheetDto() {
		return repaymentArrivesheetDto;
	}
	/**
	 * @param repaymentArrivesheetDto : set the property repaymentArrivesheetDto.
	 */
	public void setRepaymentArrivesheetDto(
			RepaymentArrivesheetDto repaymentArrivesheetDto) {
		this.repaymentArrivesheetDto = repaymentArrivesheetDto;
	}
	/**
	 * @return changeDto : return the property changeDto.
	 */
	public ChangeDto getChangeDto() {
		return changeDto;
	}
	/**
	 * @param changeDto : set the property changeDto.
	 */
	public void setChangeDto(ChangeDto changeDto) {
		this.changeDto = changeDto;
	}
	/**
	 * @return repaymentChangeReason : return the property repaymentChangeReason.
	 */
	public String getRepaymentChangeReason() {
		return repaymentChangeReason;
	}
	/**
	 * @param repaymentChangeReason : set the property repaymentChangeReason.
	 */
	public void setRepaymentChangeReason(String repaymentChangeReason) {
		this.repaymentChangeReason = repaymentChangeReason;
	}
	/**
	 * @return arrivesheetChangeReason : return the property arrivesheetChangeReason.
	 */
	public String getArrivesheetChangeReason() {
		return arrivesheetChangeReason;
	}
	/**
	 * @param arrivesheetChangeReason : set the property arrivesheetChangeReason.
	 */
	public void setArrivesheetChangeReason(String arrivesheetChangeReason) {
		this.arrivesheetChangeReason = arrivesheetChangeReason;
	}
	/**
	 * @return tSrvRepaymentId : return the property tSrvRepaymentId.
	 */
	public String gettSrvRepaymentId() {
		return tSrvRepaymentId;
	}
	/**
	 * @param tSrvRepaymentId : set the property tSrvRepaymentId.
	 */
	public void settSrvRepaymentId(String tSrvRepaymentId) {
		this.tSrvRepaymentId = tSrvRepaymentId;
	}
	/**
	 * @return tSrvArrivesheetId : return the property tSrvArrivesheetId.
	 */
	public String gettSrvArrivesheetId() {
		return tSrvArrivesheetId;
	}
	/**
	 * @param tSrvArrivesheetId : set the property tSrvArrivesheetId.
	 */
	public void settSrvArrivesheetId(String tSrvArrivesheetId) {
		this.tSrvArrivesheetId = tSrvArrivesheetId;
	}
	/**
	 * @return signTime : return the property signTime.
	 */
	public Date getSignTime() {
		return signTime;
	}
	/**
	 * @param signTime : set the property signTime.
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String gettSrvLabelTableId() {
		return tSrvLabelTableId;
	}
	public void settSrvLabelTableId(String tSrvLabelTableId) {
		this.tSrvLabelTableId = tSrvLabelTableId;
	}
}