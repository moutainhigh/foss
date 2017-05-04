/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/PdaSignDto.java
 * 
 * FILE NAME        	: PdaSignDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;

/**
 * PDA签到DTO
 * 
 * @author 097972-foss-dengtingting
 * @date 2012-12-18 下午9:15:47
 */
public class PdaSignDto extends PdaSignEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 司机手机
	 */
	private String empPhone;
	/**
	 * 车辆区域
	 */
	private String regionName;
	/**
	 * 签到开始时间
	 */
	private Date signBeginTime;
	/**
	 * 签到结束时间
	 */
	private Date signEndTime;
	/**
	 * 原状态
	 */
	private String originStatus;
	/**
	 * 是否有效
	 */
	private String active;
	private List<String> orgCodes;
	/**
	 * 部门ID集合
	 */
	private List<String> orgIdList;
	
	/** 登录人所属快递大区下的所有行政区域（区县一级单位）. */
	private List<String> expressOrderCountyCodes;
	
	/**
	 * 登录人岗位
	 */
	private String memberPosition;

	

	/**
	 * @return the originStatus
	 */
	public String getOriginStatus() {
		return originStatus;
	}

	/**
	 * @param originStatus the originStatus to set
	 */
	public void setOriginStatus(String originStatus) {
		this.originStatus = originStatus;
	}

	/**
	 * @return the signBeginTime
	 */
	public Date getSignBeginTime() {
		return signBeginTime;
	}

	/**
	 * @param signBeginTime the signBeginTime to set
	 */
	@DateFormat
	public void setSignBeginTime(Date signBeginTime) {
		this.signBeginTime = signBeginTime;
	}

	/**
	 * @return the signEndTime
	 */
	public Date getSignEndTime() {
		return signEndTime;
	}

	/**
	 * @param signEndTime the signEndTime to set
	 */
	@DateFormat
	public void setSignEndTime(Date signEndTime) {
		this.signEndTime = signEndTime;
	}

	/**
	 * @return the empPhone
	 */
	public String getEmpPhone() {
		return empPhone;
	}

	/**
	 * @param empPhone the empPhone to set
	 */
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	public List<String> getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}

	public List<String> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<String> orgIdList) {
		this.orgIdList = orgIdList;
	}

	public List<String> getExpressOrderCountyCodes() {
		return expressOrderCountyCodes;
	}

	public void setExpressOrderCountyCodes(List<String> expressOrderCountyCodes) {
		this.expressOrderCountyCodes = expressOrderCountyCodes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMemberPosition() {
		return memberPosition;
	}

	public void setMemberPosition(String memberPosition) {
		this.memberPosition = memberPosition;
	}
}