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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/vo/DataDictionaryVo.java
 * 
 * FILE NAME        	: DataDictionaryVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;

/**
 * Vo
 * @author 087584-dp-lijun
 * @date 2012-10-13 下午8:43:48
 */
public class DataDictionaryVo implements Serializable{
    private static final long serialVersionUID = -3967231350132228718L;
    
    /**
     *业务字典实体对象（查询条件）
     */
    private DataDictionaryEntity dataDictionaryEntity;
    
    /**
     *业务字典实体对象（查询条件）
     */
    private DataDictionaryValueEntity dataDictionaryValueEntity;
    
    /**
     * 词条集合对象
     */
    private List<DataDictionaryEntity> dataDictionaryEntitys;
    
    /**
     * 值集合对象
     */
    private List<DataDictionaryValueEntity> dataDictionaryValueEntityList;
    
    /**
     * 词条代码LIST
     */
    private List<String> termsCodeList;
    /**
     * 词条代码
     */
    private String termsCode;
    
    public DataDictionaryEntity getDataDictionaryEntity() {
        return dataDictionaryEntity;
    }
    
    public void setDataDictionaryEntity(DataDictionaryEntity dataDictionaryEntity) {
        this.dataDictionaryEntity = dataDictionaryEntity;
    }
    
    public List<DataDictionaryEntity> getDataDictionaryEntitys() {
        return dataDictionaryEntitys;
    }
    
    public void setDataDictionaryEntitys(List<DataDictionaryEntity> dataDictionaryEntitys) {
        this.dataDictionaryEntitys = dataDictionaryEntitys;
    }

	public DataDictionaryValueEntity getDataDictionaryValueEntity() {
		return dataDictionaryValueEntity;
	}

	public void setDataDictionaryValueEntity(
			DataDictionaryValueEntity dataDictionaryValueEntity) {
		this.dataDictionaryValueEntity = dataDictionaryValueEntity;
	}

	public List<String> getTermsCodeList() {
		return termsCodeList;
	}

	public void setTermsCodeList(List<String> termsCodeList) {
		this.termsCodeList = termsCodeList;
	}

	public List<DataDictionaryValueEntity> getDataDictionaryValueEntityList() {
		return dataDictionaryValueEntityList;
	}

	public void setDataDictionaryValueEntityList(
			List<DataDictionaryValueEntity> dataDictionaryValueEntityList) {
		this.dataDictionaryValueEntityList = dataDictionaryValueEntityList;
	}

	public String getTermsCode() {
		return termsCode;
	}

	public void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	} 
    
}
