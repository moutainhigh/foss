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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/UserMenuEntity.java
 * 
 * FILE NAME        	: UserMenuEntity.java
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
 * 用户常用菜单
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-21 下午2:35:52
 */
public class UserMenuEntity  extends BaseEntity {
	
	/**
	 * 序列ID
	 */
    private static final long serialVersionUID = -3967231350569569593L;

    /**
    *显示顺序
    */	
    private Integer displayOrder;
    
    /**
    *用户
    */	
    private String userCode;

    /**
    *权限
    */	
    private String resourceCode;

    /**
    *是否启用
    */	
    private String active;

	/**
	 * @return displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param  displayOrder  
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param  userCode  
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return resourceCode
	 */
	public String getResourceCode() {
		return resourceCode;
	}

	/**
	 * @param  resourceCode  
	 */
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
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
}