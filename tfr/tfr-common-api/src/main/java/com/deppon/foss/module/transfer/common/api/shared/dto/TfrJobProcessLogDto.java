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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/domain/TfrJobProcessLogDto.java
 *  
 *  FILE NAME          :TfrJobProcessLogDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * JOB日志查询DTO
 * 
 * @author foss-yuyongxiang
 * @date 2013年5月2日 09:55:11
 */
public class TfrJobProcessLogDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 业务名称 */
	private String bizName;
	/** 业务编号 */
	private String bizCode;
	/** 业务执行相关ID */
	private String execBizId;
	/** 业务执行相关表名 */
	private String execTableName;
	/** 备注 */
	private String remark;
	/** 业务异常信息 */
	private String exceptionInfo;
	/** 创建时间 */
	private Date createTime;

	/** 查询创建日期开始 */
	private Date createTimeFrom;

	/** 查询创建日期结束 */
	private Date createTimeTo;

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getExecBizId() {
		return execBizId;
	}

	public void setExecBizId(String execBizId) {
		this.execBizId = execBizId;
	}

	public String getExecTableName() {
		return execTableName;
	}

	public void setExecTableName(String execTableName) {
		this.execTableName = execTableName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createTimeFrom
	 */
	@DateFormat
	public Date getCreateTimeFrom() {
		return createTimeFrom;
	}

	/**
	 * @param createTimeFrom
	 *            the createTimeFrom to set
	 */
	@DateFormat
	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	/**
	 * @return the createTimeTo
	 */
	@DateFormat
	public Date getCreateTimeTo() {
		return createTimeTo;
	}

	/**
	 * @param createTimeTo
	 *            the createTimeTo to set
	 */
	@DateFormat
	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
}