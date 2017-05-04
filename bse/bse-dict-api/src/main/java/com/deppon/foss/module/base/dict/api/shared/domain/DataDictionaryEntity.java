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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/domain/DataDictionaryEntity.java
 * 
 * FILE NAME        	: DataDictionaryEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class DataDictionaryEntity extends BaseEntity {
	
	/**
	 * 声明日志对象
	 */
	private static final long serialVersionUID = -3967231350441121031L;

	/**
	 * 虚拟编码
	 */
	private String virtualCode;

	/**
	 * 词条代码
	 */
	private String termsCode;

	/**
	 * 词条名称
	 */
	private String termsName;

	/**
	 * 词条拼音
	 */
	private String termsPinyin;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 是否启用
	 */
	private String active;

	/**
	 * 子词条的LIST
	 */
	private List<DataDictionaryValueEntity> dataDictionaryValueEntityList;

	/**
	 * @return virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * @param  virtualCode  
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * @return termsCode
	 */
	public String getTermsCode() {
		return termsCode;
	}

	/**
	 * @param  termsCode  
	 */
	public void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	}

	/**
	 * @return termsName
	 */
	public String getTermsName() {
		return termsName;
	}

	/**
	 * @param  termsName  
	 */
	public void setTermsName(String termsName) {
		this.termsName = termsName;
	}

	/**
	 * @return termsPinyin
	 */
	public String getTermsPinyin() {
		return termsPinyin;
	}

	/**
	 * @param  termsPinyin  
	 */
	public void setTermsPinyin(String termsPinyin) {
		this.termsPinyin = termsPinyin;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param  notes  
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return dataDictionaryValueEntityList
	 */
	public List<DataDictionaryValueEntity> getDataDictionaryValueEntityList() {
		return dataDictionaryValueEntityList;
	}

	/**
	 * @param  dataDictionaryValueEntityList  
	 */
	public void setDataDictionaryValueEntityList(
			List<DataDictionaryValueEntity> dataDictionaryValueEntityList) {
		this.dataDictionaryValueEntityList = dataDictionaryValueEntityList;
	}
}