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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/vo/UnloadDiffReportVo.java
 *  
 *  FILE NAME          :UnloadDiffReportVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto;

/** 
 * @className: UnloadDiffReportVo
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告Vo类
 * @date: 2012-12-7 下午5:02:45
 * 
 */
public class UnloadDiffReportVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**接收前台传入的卸车差异报告查询条件**/
	private QueryUnloadDiffReportConditionDto queryUnloadDiffReportConditionDto;
	
	/**卸车差异报告查询结果list**/
	private List<UnloadDiffReportEntity> unloadDiffReportEntityList;
	
	/**卸车差异报告编号**/
	private String diffReportNo;
	
	/**卸车差异报告ID**/
	private String diffReportId;
	
	/**运单号**/
	private String waybillNo;
	
	/**流水号**/
	private String serialNo;
	
	/**卸车差异报告基本信息**/
	private UnloadDiffReportEntity baseEntity;
	
	/**差异报告详情列表**/
	private List<UnloadDiffReportDetailEntity> unloadDiffReportDetailEntityList;
	
	/**差异报告详情列表(快递)**/
	private List<UnloadDiffReportDetailEntity> expressEntityList;
	
	public List<UnloadDiffReportDetailEntity> getExpressEntityList() {
		return expressEntityList;
	}

	public void setExpressEntityList(
			List<UnloadDiffReportDetailEntity> expressEntityList) {
		this.expressEntityList = expressEntityList;
	}

	/**备注**/
	private String note;
	
	/**顶级外场**/
	private String superOrgCode;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDiffReportId() {
		return diffReportId;
	}

	public void setDiffReportId(String diffReportId) {
		this.diffReportId = diffReportId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public UnloadDiffReportEntity getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(UnloadDiffReportEntity baseEntity) {
		this.baseEntity = baseEntity;
	}

	public List<UnloadDiffReportDetailEntity> getUnloadDiffReportDetailEntityList() {
		return unloadDiffReportDetailEntityList;
	}

	public void setUnloadDiffReportDetailEntityList(
			List<UnloadDiffReportDetailEntity> unloadDiffReportDetailEntityList) {
		this.unloadDiffReportDetailEntityList = unloadDiffReportDetailEntityList;
	}

	public String getDiffReportNo() {
		return diffReportNo;
	}

	public void setDiffReportNo(String diffReportNo) {
		this.diffReportNo = diffReportNo;
	}

	public List<UnloadDiffReportEntity> getUnloadDiffReportEntityList() {
		return unloadDiffReportEntityList;
	}

	public void setUnloadDiffReportEntityList(
			List<UnloadDiffReportEntity> unloadDiffReportEntityList) {
		this.unloadDiffReportEntityList = unloadDiffReportEntityList;
	}

	public QueryUnloadDiffReportConditionDto getQueryUnloadDiffReportConditionDto() {
		return queryUnloadDiffReportConditionDto;
	}

	public void setQueryUnloadDiffReportConditionDto(
			QueryUnloadDiffReportConditionDto queryUnloadDiffReportConditionDto) {
		this.queryUnloadDiffReportConditionDto = queryUnloadDiffReportConditionDto;
	}

	/**   
	 * superOrgCode   
	 *   
	 * @return  the superOrgCode   
	 */
	
	public String getSuperOrgCode() {
		return superOrgCode;
	}

	/**   
	 * @param superOrgCode the superOrgCode to set
	 * Date:2013-8-2下午2:47:32
	 */
	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}
}