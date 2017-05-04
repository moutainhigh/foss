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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/HandOverBillOptLogEntity.java
 *  
 *  FILE NAME          :HandOverBillOptLogEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/** 
 * @className: HandOverBillOptLogEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 交接单修改日志实体
 * @date: 2012-10-25 上午8:40:59
 * 
 */
public class HandOverBillOptLogEntity extends BaseEntity {

	private static final long serialVersionUID = 3692262285045163720L;
	
	//交接单ID
	private String handOverBillID;
	//交接单号
	private String handOverBillNo;
	//操作类别
	private String optType;
	//操作人工号
	private String operatorCode;
	//操作人姓名
	private String operatorName;
	//操作内容
	private String optContent;
	//操作时间
	private Date optTime;
	//交接时间
	private Date handOverTime;
	
	@DateFormat
	public Date getHandOverTime() {
		return handOverTime;
	}
	@DateFormat
	public void setHandOverTime(Date handOverTime) {
		this.handOverTime = handOverTime;
	}
	public String getHandOverBillID() {
		return handOverBillID;
	}
	public void setHandOverBillID(String handOverBillID) {
		this.handOverBillID = handOverBillID;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOptContent() {
		return optContent;
	}
	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}
	@DateFormat
	public Date getOptTime() {
		return optTime;
	}
	@DateFormat
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	
}