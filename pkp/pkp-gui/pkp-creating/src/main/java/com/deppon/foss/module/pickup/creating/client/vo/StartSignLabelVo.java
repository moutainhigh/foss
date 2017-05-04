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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/vo/StartSignLabelVo.java
 * 
 * FILE NAME        	: StartSignLabelVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.creating.client.vo;

import java.util.Date;

/**
 * (整车签收单打印标签VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-30 上午10:19:53,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-30 上午10:19:53
 * @since
 * @version
 */
public class StartSignLabelVo {

	// 运单号
	private String waybillNo;

	// 始发部门
	private String startOrg;

	// 到达部门
	private String reachOrg;
	
	
	
	//批量打印时查询的起始时间和结束时间
	private Date zdEndDate;
	private Date zdStartDate;


	
	
	
	

	public Date getZdStartDate() {
		return zdStartDate;
	}

	public void setZdStartDate(Date zdStartDate) {
		this.zdStartDate = zdStartDate;
	}

	public Date getZdEndDate() {
		return zdEndDate;
	}

	public void setZdEndDate(Date zdEndDate) {
		this.zdEndDate = zdEndDate;
	}

	// 目的站
	private String targetOrg;

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the startOrg
	 */
	public String getStartOrg() {
		return startOrg;
	}

	/**
	 * @param startOrg the startOrg to set
	 */
	public void setStartOrg(String startOrg) {
		this.startOrg = startOrg;
	}

	/**
	 * @return the reachOrg
	 */
	public String getReachOrg() {
		return reachOrg;
	}

	/**
	 * @param reachOrg the reachOrg to set
	 */
	public void setReachOrg(String reachOrg) {
		this.reachOrg = reachOrg;
	}

	/**
	 * @return the targetOrg
	 */
	public String getTargetOrg() {
		return targetOrg;
	}

	/**
	 * @param targetOrg the targetOrg to set
	 */
	public void setTargetOrg(String targetOrg) {
		this.targetOrg = targetOrg;
	}
	
}