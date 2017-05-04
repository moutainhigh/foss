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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/service/impl/FOSSToEDIService.java
 *  
 *  FILE NAME          :FOSSToEDIService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.server.service.impl
 * FILE    NAME: FOSSToEDIService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.air.SumBillRequest;
import com.deppon.foss.framework.shared.util.file.IOUtils;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToEDIService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.UploadJointTicketDto;
import com.deppon.foss.module.transfer.common.server.utils.FTPUtil;

/**
 * EDI应用交互类,调用EDI的相关接口
 * @author 104306-foss-wangLong
 * @date 2012-12-21 上午10:57:57
 * 
 */
public class FOSSToEDIService implements IFOSSToEDIService {
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FOSSToEDIService.class);
	/**
	 * 上传文件目录
	 */
	private String ftpDirectory;
	/**
	 * 上传FTP地址
	 */
	private String ftpHost;
	/**
	 * 上传FTP端口
	 */
	private int ftpPort;
	/**
	 * 上传FTP用户名
	 */
	private String ftpUser;
	/**
	 * 上传FTP密码
	 */
	private String ftpPassword;
	
	/**
	 * 上传合票清单
	 * @author 104306-foss-wangLong
	 * @date 2012-12-27 下午1:57:10
	 * @param uploadJointTicketDto 合票清单上传DTO
	 * @throws IOException 
	 */
	public void uploadJointTicketList(UploadJointTicketDto uploadJointTicketDto) throws IOException {
		LOGGER.info(ToStringBuilder.reflectionToString(uploadJointTicketDto));
		boolean result = false;
		try {
			// 上传合票excel到FTP
			FTPUtil ftpUtil = FTPUtil.initialize(ftpHost, ftpPort, ftpUser, ftpPassword);
			LOGGER.info("上传合大票用户名为[" + ftpUser + "]密码[" + ftpUser + "]"+"主机["+ftpHost+"]");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(uploadJointTicketDto.getExcelBytes());
			result = ftpUtil.upload(ftpDirectory, uploadJointTicketDto.getAttachmentName(), byteArrayInputStream);
			IOUtils.close(byteArrayInputStream);
		} catch (IOException e) {
			LOGGER.info("上传文件到FTP失败. 错误信息: {}", e.getMessage());
			throw new IOException("上传合票清单出错. 错误信息:" + e.getMessage());
		}
		if (!result) {
			throw new IOException("上传合票清单出错.");
		}
		LOGGER.info("合票FTP上传成功!");
		SumBillRequest sumBillRequest = createSumBillRequest(uploadJointTicketDto);
		AccessHeader header = createAccessHeader(uploadJointTicketDto);
		LOGGER.info("开始发送asynReqeust!");
		try {
			ESBJMSAccessor.asynReqeust(header, sumBillRequest);
			LOGGER.info("发送成功asynReqeust!");
		} catch (Exception e) {
			LOGGER.info("发送请求失败. {}", e);
			throw new IOException("上传合票清单出错. 错误信息:" + e);
		}
	}

	/**
	 * 消息体
	 * @author 104306-foss-wangLong
	 * @date 2012-12-27 下午6:13:45
	 * @param uploadJointTicketDto
	 * @return SumBillRequest
	 * @throws IOException 
	 */
	private SumBillRequest createSumBillRequest(UploadJointTicketDto uploadJointTicketDto) throws IOException {
		//创建合票清单请求头
		SumBillRequest sumBillRequest = new SumBillRequest();
		//代理网点编号
	    sumBillRequest.setCreatorNumber(uploadJointTicketDto.getCreatorNumber());
	    //寄件人姓名
	    sumBillRequest.setSenderName(uploadJointTicketDto.getSenderName());
	    //发件日期
	    sumBillRequest.setSendDate(uploadJointTicketDto.getSendDate());
	    //主题
	    sumBillRequest.setSubject(uploadJointTicketDto.getSubject());
	    //附件名称 
	    sumBillRequest.setMailFolderName(TransferConstants.EDI_SUMBILLREQUEST_MAILFOLDERNAME);
	    //提醒标志
	    sumBillRequest.setNoticeFlag(TransferConstants.EDI_SUMBILLREQUEST_NOTICEFLAG);
	    //已读标志
	    sumBillRequest.setReadFlag(TransferConstants.EDI_SUMBILLREQUEST_READFLAG);
	    //邮件标志
	    sumBillRequest.setMailFlag(TransferConstants.EDI_SUMBILLREQUEST_MAILFLAG);
	    //优先级别
	    sumBillRequest.setPriorityLevel(TransferConstants.EDI_SUMBILLREQUEST_PRIORITYLEVEL);
	    //邮件大小
	    sumBillRequest.setMailSize(uploadJointTicketDto.getExcelBytes().length);
	    //附件名称
	    sumBillRequest.setAttachmentName(uploadJointTicketDto.getAttachmentName());
	    //附件链接
	    sumBillRequest.setAttachmentLink(sumBillRequest.getAttachmentName());
		return sumBillRequest;
	}

	/**
	 * 创建包头
	 * @author 104306-foss-wangLong
	 * @date 2012-12-27 下午5:47:28
	 * @return AccessHeader
	 */
	private AccessHeader createAccessHeader(UploadJointTicketDto uploadJointTicketDto) {
		AccessHeader header = new AccessHeader();
		// EDI_FILEUP_SUMBILL
		header.setVersion(TransferConstants.ESB_ACCESSHEADER_VERSION);
		if (uploadJointTicketDto != null) {
			header.setBusinessId(uploadJointTicketDto.getAttachmentName());
		} else {
			header.setBusinessId("uploadJointTicketList");
		}
		header.setBusinessDesc1("upload");
		//ESB服务编码
		header.setEsbServiceCode("ESB_FOSS2ESB_EDI_FILEUP_SUMBILL");
		return header;
	}

	public String getFtpDirectory() {
		return ftpDirectory;
	}

	public void setFtpDirectory(String ftpDirectory) {
		this.ftpDirectory = ftpDirectory;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
}