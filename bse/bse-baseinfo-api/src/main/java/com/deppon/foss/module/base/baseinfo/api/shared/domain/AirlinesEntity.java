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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/AirlinesEntity.java
 * 
 * FILE NAME        	: AirlinesEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“航空公司”的数据库对应实体：SUC-42
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:26:47</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:26:47
 * @since
 * @version
 */
public class AirlinesEntity extends BaseEntity {
    
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -7528989497910898037L;

    /**
     * 航空公司名称.
     */
    private String name;

    /**
     * 航空公司代码.
     */
    private String code;

    /**
     * 航空公司简称.
     */
    private String simpleName;

    /**
     * 航空公司数字前缀.
     */
    private String prifixName;

    /**
     * 航空公司LOGO.
     */
    private String logo;

    /**
     * 是否启用.
     */
    private String active;

    /**
     * 备注.
     */
    private String notes;
    
    /**
     * 公共选择器扩展 查询条件
     */
    private String queryParam;
    
    /**
	 * 查询全部（包含无效的）
	 */   
	private String all;
	
	
	
	
	
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
    /**
     * 获取 航空公司名称.
     *
     * @return  the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置 航空公司名称.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	/**
     * 获取 航空公司代码.
     *
     * @return  the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置 航空公司代码.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 获取 航空公司简称.
     *
     * @return  the simpleName
     */
    public String getSimpleName() {
        return simpleName;
    }
    
    /**
     * 设置 航空公司简称.
     *
     * @param simpleName the simpleName to set
     */
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
    
    /**
     * 获取 航空公司数字前缀.
     *
     * @return  the prifixName
     */
    public String getPrifixName() {
        return prifixName;
    }
    
    /**
     * 设置 航空公司数字前缀.
     *
     * @param prifixName the prifixName to set
     */
    public void setPrifixName(String prifixName) {
        this.prifixName = prifixName;
    }
    
    /**
     * 获取 航空公司LOGO.
     *
     * @return  the logo
     */
    public String getLogo() {
        return logo;
    }
    
    /**
     * 设置 航空公司LOGO.
     *
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 备注.
     *
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * 设置 备注.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
