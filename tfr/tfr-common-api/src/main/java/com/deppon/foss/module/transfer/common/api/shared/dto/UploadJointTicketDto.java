/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/UploadJointTicketDto.java
 *  
 *  FILE NAME          :UploadJointTicketDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-common-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.api.shared.dto
 * FILE    NAME: UploadJointTicketDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.Date;

/**
 * 合票清单上传DTO
 * @author 104306-foss-wangLong
 * @date 2012-12-27 下午2:03:11
 */
public class UploadJointTicketDto {
	/**
	 * 上传文件流
	 */
	private byte[] excelBytes;
	
	/** 代理网点编号  */
	private String creatorNumber;
	
	/** 寄件人姓名  */
    private String senderName;
    
    /** 发件日期  */
    private Date sendDate;
    
    /** 主题  */
    private String subject;
    
    /** 附件名称 */
    private String attachmentName;
    
	/**
	 * 获得creatorNumber
	 * @return the creatorNumber
	 */
	public String getCreatorNumber() {
		return creatorNumber;
	}

	/**
	 * 设置creatorNumber
	 * @param creatorNumber the creatorNumber to set
	 */
	public void setCreatorNumber(String creatorNumber) {
		this.creatorNumber = creatorNumber;
	}

	/**
	 * 获得senderName
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * 设置senderName
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * 获得sendDate
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * 设置sendDate
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * 获得subject
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 设置subject
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 获得attachmentName
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * 设置attachmentName
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public byte[] getExcelBytes() {
		return excelBytes;
	}

	public void setExcelBytes(byte[] excelBytes) {
		this.excelBytes = excelBytes;
	}
}