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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ExceptionOperateDto.java
 * 
 * FILE NAME        	: ExceptionOperateDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;

/**
 * 异常处理Dto.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-12-7 下午3:35:34
 */
public class ExceptionOperateDto implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 异常主数据. */
	private ExceptionEntity exceptionEntity;
	
	/** 异常处理明细. */
	private List<ExceptionProcessDetailEntity> exceptionProcessDetailEntityList;

	/**
	 * Gets the exception entity.
	 *
	 * @return the exceptionEntity
	 */
	public ExceptionEntity getExceptionEntity() {
		return exceptionEntity;
	}

	/**
	 * Sets the exception entity.
	 *
	 * @param exceptionEntity the exceptionEntity to see
	 */
	public void setExceptionEntity(ExceptionEntity exceptionEntity) {
		this.exceptionEntity = exceptionEntity;
	}

	/**
	 * Gets the exception process detail entity list.
	 *
	 * @return the exceptionProcessDetailEntityList
	 */
	public List<ExceptionProcessDetailEntity> getExceptionProcessDetailEntityList() {
		return exceptionProcessDetailEntityList;
	}

	/**
	 * Sets the exception process detail entity list.
	 *
	 * @param exceptionProcessDetailEntityList the
	 * exceptionProcessDetailEntityList to see
	 */
	public void setExceptionProcessDetailEntityList(List<ExceptionProcessDetailEntity> exceptionProcessDetailEntityList) {
		this.exceptionProcessDetailEntityList = exceptionProcessDetailEntityList;
	}
}