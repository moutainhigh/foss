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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/UnloadDiffReportDto.java
 *  
 *  FILE NAME          :UnloadDiffReportDto.java
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
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;

/**
 * 封装卸车差异报告中的按运单分组的差异明细
 */
public class UnloadDiffReportDto {
	
	/**
	 * 多货List
	 */
	private List<UnloadDiffReportDetailEntity> lessGoodsList = new ArrayList<UnloadDiffReportDetailEntity>();
	/**
	 * 少货List
	 */
	private List<UnloadDiffReportDetailEntity> moreGoodsList = new ArrayList<UnloadDiffReportDetailEntity>();
	/**
	 * 部门编号
	 */
	private String orgCode;
	
	/**
	 * 获取 多货List.
	 *
	 * @return the 多货List
	 */
	public List<UnloadDiffReportDetailEntity> getLessGoodsList() {
		return lessGoodsList;
	}
	
	/**
	 * 设置 多货List.
	 *
	 * @param lessGoodsList the new 多货List
	 */
	public void setLessGoodsList(List<UnloadDiffReportDetailEntity> lessGoodsList) {
		this.lessGoodsList = lessGoodsList;
	}
	
	/**
	 * 获取 少货List.
	 *
	 * @return the 少货List
	 */
	public List<UnloadDiffReportDetailEntity> getMoreGoodsList() {
		return moreGoodsList;
	}
	
	/**
	 * 设置 少货List.
	 *
	 * @param moreGoodsList the new 少货List
	 */
	public void setMoreGoodsList(List<UnloadDiffReportDetailEntity> moreGoodsList) {
		this.moreGoodsList = moreGoodsList;
	}

	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 部门编号.
	 *
	 * @param orgCode the new 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	

}