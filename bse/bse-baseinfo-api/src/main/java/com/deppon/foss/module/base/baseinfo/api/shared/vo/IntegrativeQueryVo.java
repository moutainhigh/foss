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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/vo/IntegrativeQueryVo.java
 * 
 * FILE NAME        	: IntegrativeQueryVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;

public class IntegrativeQueryVo implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3057730367407783104L;
	/**
	 * 自定义查询 DTO 实体
	 */
	private UserDefinedQueryDto userDefinedQuery;
	/**
	 * 自定义查询 DTO 集合
	 */
	private List<UserDefinedQueryDto> userDefinedQueryList;
	/**
	 * 运单 紧急情况
	 */
	private WaybillMarkEntity waybillMark;
	/**
	 * 运单 紧急情况 开合
	 */
	private List<WaybillMarkEntity> waybillMarkList;
	/**
	 * 运单跟踪
	 */
	private TrackRecordEntity trackRecord;
	/**
	 * 运单跟踪 集合
	 */
	private List<TrackRecordEntity> trackRecordList;
	/**
	 * 自定义查询 实体中对应id字段，字段间以逗号分隔[支持批量删除]
	 */
	private String[] codeStr;
	/**
	 * 字符串查询
	 */
	private String markStatus;
	/**
	 * 返回前台的INT类型属性
	 */
	private int returnInt;
	
	/**
	 * 用来存储客户相关信息的DTO
	 */
	private CustomerDto custDto;
	
	/* 一下为setter和getter方法 */

	
	/**
	 * @return the userDefinedQuery
	 */
	public UserDefinedQueryDto getUserDefinedQuery() {
		return userDefinedQuery;
	}

	/**
	 * @param userDefinedQuery
	 *            the userDefinedQuery to set
	 */
	public void setUserDefinedQuery(UserDefinedQueryDto userDefinedQuery) {
		this.userDefinedQuery = userDefinedQuery;
	}

	/**
	 * @return the userDefinedQueryList
	 */
	public List<UserDefinedQueryDto> getUserDefinedQueryList() {
		return userDefinedQueryList;
	}

	/**
	 * @param userDefinedQueryList
	 *            the userDefinedQueryList to set
	 */
	public void setUserDefinedQueryList(
			List<UserDefinedQueryDto> userDefinedQueryList) {
		this.userDefinedQueryList = userDefinedQueryList;
	}
	
	/**
	 * @return  the custDto
	 */
	public CustomerDto getCustDto() {
	    return custDto;
	}

	
	/**
	 * @param custDto the custDto to set
	 */
	public void setCustDto(CustomerDto custDto) {
	    this.custDto = custDto;
	}

	/**
	 * @return the returnInt
	 */
	public int getReturnInt() {
		return returnInt;
	}

	/**
	 * @param returnInt
	 *            the returnInt to set
	 */
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}

	
	/**
	 * @return  the codeStr
	 */
	public String[] getCodeStr() {
	    return codeStr;
	}

	
	/**
	 * @param codeStr the codeStr to set
	 */
	public void setCodeStr(String[] codeStr) {
	    this.codeStr = codeStr;
	}

	
	/**
	 * @return  the waybillMark
	 */
	public WaybillMarkEntity getWaybillMark() {
	    return waybillMark;
	}

	
	/**
	 * @param waybillMark the waybillMark to set
	 */
	public void setWaybillMark(WaybillMarkEntity waybillMark) {
	    this.waybillMark = waybillMark;
	}

	
	/**
	 * @return  the trackRecord
	 */
	public TrackRecordEntity getTrackRecord() {
	    return trackRecord;
	}

	
	/**
	 * @param trackRecord the trackRecord to set
	 */
	public void setTrackRecord(TrackRecordEntity trackRecord) {
	    this.trackRecord = trackRecord;
	}

	
	/**
	 * @return  the trackRecordList
	 */
	public List<TrackRecordEntity> getTrackRecordList() {
	    return trackRecordList;
	}

	
	/**
	 * @param trackRecordList the trackRecordList to set
	 */
	public void setTrackRecordList(List<TrackRecordEntity> trackRecordList) {
	    this.trackRecordList = trackRecordList;
	}

	
	/**
	 * @return  the waybillMarkList
	 */
	public List<WaybillMarkEntity> getWaybillMarkList() {
	    return waybillMarkList;
	}

	
	/**
	 * @param waybillMarkList the waybillMarkList to set
	 */
	public void setWaybillMarkList(List<WaybillMarkEntity> waybillMarkList) {
	    this.waybillMarkList = waybillMarkList;
	}

	
	/**
	 * @return  the markStatus
	 */
	public String getMarkStatus() {
	    return markStatus;
	}

	
	/**
	 * @param markStatus the markStatus to set
	 */
	public void setMarkStatus(String markStatus) {
	    this.markStatus = markStatus;
	}
}
