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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/domain/DataDictionaryValueEntity.java
 * 
 * FILE NAME        	: DataDictionaryValueEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 数据字典实体类
 * 
 * 
 */
public class DataDictionaryValueEntity  extends BaseEntity {
	
	/**
	 * 声明日志对象
	 */
    private static final long serialVersionUID = -3967231350458507218L;

    /**
    *虚拟编码
    */	
    private String virtualCode;

    /**
    *上级词条
    */	
    private String termsCode;
    
    /**
    *上级词条名称-数据字典值不记录，用于显示到前台界面
    */	
    private String termsName;

    /**
    *序号
    */	
    private String valueSeq;

    /**
    *值名称
    */	
    private String valueName;

    /**
    *值代码
    */	
    private String valueCode;

    /**
    *语言
    */	
    private String language;

    /**
    * 数据版本号
    */	
    private Long versionNo;
    
    /**
    *是否启用
    */	
    private String active;
    
    /**
     *  扩展属性1
     */
    private String extAttribute1;
    
    /**
     *  扩展属性2
     */
    private String extAttribute2;

    /**
     * 备注信息
     */
    private String noteInfo;

	

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
	 * @return valueSeq
	 */
	public String getValueSeq() {
		return valueSeq;
	}

	/**
	 * @param  valueSeq  
	 */
	public void setValueSeq(String valueSeq) {
		this.valueSeq = valueSeq;
	}

	/**
	 * @return valueName
	 */
	public String getValueName() {
		return valueName;
	}

	/**
	 * @param  valueName  
	 */
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	/**
	 * @return valueCode
	 */
	public String getValueCode() {
		return valueCode;
	}

	/**
	 * @param  valueCode  
	 */
	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	/**
	 * @return language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param  language  
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
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
	 * @return extAttribute1
	 */
	public String getExtAttribute1() {
		return extAttribute1;
	}

	/**
	 * @param  extAttribute1  
	 */
	public void setExtAttribute1(String extAttribute1) {
		this.extAttribute1 = extAttribute1;
	}

	/**
	 * @return noteInfo
	 */
	public String getNoteInfo() {
		return noteInfo;
	}

	/**
	 * @param  noteInfo  
	 */
	public void setNoteInfo(String noteInfo) {
		this.noteInfo = noteInfo;
	}
	
	public String getExtAttribute2() {
		return extAttribute2;
	}

	public void setExtAttribute2(String extAttribute2) {
		this.extAttribute2 = extAttribute2;
	}
}