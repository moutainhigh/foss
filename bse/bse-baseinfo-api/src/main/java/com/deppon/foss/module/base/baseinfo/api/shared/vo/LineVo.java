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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LineVo.java
 * 
 * FILE NAME        	: LineVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;

/**
 * (运作到运作线路VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:078838-foss-zhangbin,date:2012-11-05 下午6:15:07
 * </p>
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-05 下午6:15:07
 * @since
 * @version
 */
public class LineVo implements Serializable {

	private static final long serialVersionUID = -3810525836816375070L;
	// 线路实体LIST
	private List<LineEntity> lineEntityList;
	// 线路实体
	private LineEntity lineEntity;
	// 线段实体的LIST
	private List<LineItemEntity> lineItemEntityList;
	// 线段实体
	private LineItemEntity lineItemEntity;
	// 发车标准实体List
	private List<DepartureStandardEntity> departureStandardEntityList;
	// //发车标准实体
	private DepartureStandardEntity departureStandardEntity;
	//线路VirtualCodes
	private List<String> lineVirtualCodes;
	//线路虚拟编码
	private String lineVirtualCode;
	
	 /**
     * 用户部门权限
     */
    private List<String> userOrgList;


	public List<String> getLineVirtualCodes() {
		return lineVirtualCodes;
	}

	public void setLineVirtualCodes(List<String> lineVirtualCodes) {
		this.lineVirtualCodes = lineVirtualCodes;
	}

	public List<LineEntity> getLineEntityList() {
		return lineEntityList;
	}

	public void setLineEntityList(List<LineEntity> lineEntityList) {
		this.lineEntityList = lineEntityList;
	}

	public LineEntity getLineEntity() {
		return lineEntity;
	}

	public void setLineEntity(LineEntity lineEntity) {
		this.lineEntity = lineEntity;
	}

	public List<LineItemEntity> getLineItemEntityList() {
		return lineItemEntityList;
	}

	public void setLineItemEntityList(List<LineItemEntity> lineItemEntityList) {
		this.lineItemEntityList = lineItemEntityList;
	}

	public LineItemEntity getLineItemEntity() {
		return lineItemEntity;
	}

	public void setLineItemEntity(LineItemEntity lineItemEntity) {
		this.lineItemEntity = lineItemEntity;
	}

	public List<DepartureStandardEntity> getDepartureStandardEntityList() {
		return departureStandardEntityList;
	}

	public void setDepartureStandardEntityList(
			List<DepartureStandardEntity> departureStandardEntityList) {
		this.departureStandardEntityList = departureStandardEntityList;
	}

	public DepartureStandardEntity getDepartureStandardEntity() {
		return departureStandardEntity;
	}

	public void setDepartureStandardEntity(
			DepartureStandardEntity departureStandardEntity) {
		this.departureStandardEntity = departureStandardEntity;
	}

	public String getLineVirtualCode() {
		return lineVirtualCode;
	}

	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}

	public List<String> getUserOrgList() {
		return userOrgList;
	}

	public void setUserOrgList(List<String> userOrgList) {
		this.userOrgList = userOrgList;
	}
	
}
