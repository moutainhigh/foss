/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/ArriveChanSignVo.java
 * 
 * FILE NAME        	: ArriveChanSignVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT private String: bse-querying
 * PACKAGE private String: com.deppon.foss.module.base.querying.shared
 * FILE    private String: ArriveChanSignVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.querying.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 到达更改vo
 * @author 078823-foss-panGuangJun
 * @date 2012-12-26 下午8:37:39
 */
public class ArriveChanSignVo implements Serializable {
	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = 2719605529094163960L;


	// 单据编号
	private String  rfcNo;
	

	// 变更信息
	private String  changeMsg;
	

	// 起草人
	private String  drafter;
	

	// 起草时间
	private Date  draftTime;
	

	// 受理部门
	private String  operateOrgName;
	

	// 受理时间
	private Date  operateTime;
	

	// 受理备注
	private String  notes;
	

	// 变更原因
	private String  reason;
	

	// 受理状态
	private String  status;


	/**
	 *getter
	 */
	public String getRfcNo() {
		return rfcNo;
	}


	/**
	 *setter
	 */
	public void setRfcNo(String rfcNo) {
		this.rfcNo = rfcNo;
	}


	/**
	 *getter
	 */
	public String getChangeMsg() {
		return changeMsg;
	}


	/**
	 *setter
	 */
	public void setChangeMsg(String changeMsg) {
		this.changeMsg = changeMsg;
	}


	/**
	 *getter
	 */
	public String getDrafter() {
		return drafter;
	}


	/**
	 *setter
	 */
	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}


	/**
	 *getter
	 */
	public Date getDraftTime() {
		return draftTime;
	}


	/**
	 *setter
	 */
	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}


	/**
	 *getter
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}


	/**
	 *setter
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}


	/**
	 *getter
	 */
	public Date getOperateTime() {
		return operateTime;
	}


	/**
	 *setter
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


	/**
	 *getter
	 */
	public String getNotes() {
		return notes;
	}


	/**
	 *setter
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}


	/**
	 *getter
	 */
	public String getReason() {
		return reason;
	}


	/**
	 *setter
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}


	/**
	 *getter
	 */
	public String getStatus() {
		return status;
	}


	/**
	 *setter
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
