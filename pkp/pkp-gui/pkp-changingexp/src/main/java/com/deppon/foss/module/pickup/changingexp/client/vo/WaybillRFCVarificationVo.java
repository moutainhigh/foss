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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/vo/WaybillRFCVarificationVo.java
 * 
 * FILE NAME        	: WaybillRFCVarificationVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.vo;

import java.util.Date;

/**
 * 申请更改查询表格用到的VO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-gengzhe,date:2012-11-28 下午7:36:34
 * </p>
 * 
 * @author foss-gengzhe
 * @date 2012-11-28 下午7:36:34
 * @since
 * @version
 */
public class WaybillRFCVarificationVo {

	/**
	 * 申请时间
	 */
	private Date applyTime;

	/**
	 * 单号
	 */
	private String waybillNo;

	/**
	 * 变更凭证
	 */
	private String changeCertificate;

	/**
	 * 变更类型
	 */
	private String changeType;

	/**
	 * 审核状态
	 */
	private String approveStatus;

	/**
	 * 申请部门
	 */
	private String applyDepartment;

	/**
	 * 申请人
	 */
	private String applyPerson;

	/**
	 * 审核时间
	 */
	private Date approveTime;

	/**
	 * 审核人
	 */
	private Date approvePerson;

	/**
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime
	 *            the applyTime to set
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the changeCertificate
	 */
	public String getChangeCertificate() {
		return changeCertificate;
	}

	/**
	 * @param changeCertificate
	 *            the changeCertificate to set
	 */
	public void setChangeCertificate(String changeCertificate) {
		this.changeCertificate = changeCertificate;
	}

	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType
	 *            the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * @param approveStatus
	 *            the approveStatus to set
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/**
	 * @return the applyDepartment
	 */
	public String getApplyDepartment() {
		return applyDepartment;
	}

	/**
	 * @param applyDepartment
	 *            the applyDepartment to set
	 */
	public void setApplyDepartment(String applyDepartment) {
		this.applyDepartment = applyDepartment;
	}

	/**
	 * @return the applyPerson
	 */
	public String getApplyPerson() {
		return applyPerson;
	}

	/**
	 * @param applyPerson
	 *            the applyPerson to set
	 */
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	/**
	 * @return the approveTime
	 */
	public Date getApproveTime() {
		return approveTime;
	}

	/**
	 * @param approveTime
	 *            the approveTime to set
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	/**
	 * @return the approvePerson
	 */
	public Date getApprovePerson() {
		return approvePerson;
	}

	/**
	 * @param approvePerson
	 *            the approvePerson to set
	 */
	public void setApprovePerson(Date approvePerson) {
		this.approvePerson = approvePerson;
	}

}