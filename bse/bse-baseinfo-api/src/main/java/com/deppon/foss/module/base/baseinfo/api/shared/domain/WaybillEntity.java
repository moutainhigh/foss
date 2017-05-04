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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/WaybillEntity.java
 * 
 * FILE NAME        	: WaybillEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.shared
 * FILE    NAME: WaybillEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

// TODO: Auto-generated Javadoc
/**
 * 运单实体.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-24 下午4:07:28
 */
public class WaybillEntity  extends BaseEntity{
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -4380268074131249928L;
	
	/** The waybill no. */
	private String waybillNo;
	/** 运单紧急程度标记情况 */
	private String mark;

	/**
	 * getter.
	 *
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * setter.
	 *
	 * @param waybillNo the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the mark
	 */
	public String getMark() {
	    return mark;
	}

	
	/**
	 * @param mark the mark to set
	 */
	public void setMark(String mark) {
	    this.mark = mark;
	}
	
}
