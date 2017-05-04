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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/dto/UserDefinedQueryDto.java
 * 
 * FILE NAME        	: UserDefinedQueryDto.java
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
 * FILE    NAME: QueryingConstant.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDefinedQueryConditionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDefinedQuerySchemeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;

/**
 * 自定义查询 方案 实体.
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2013-01-21 20:51
 */
public class UserDefinedQueryDto extends UserDefinedQuerySchemeEntity {

    private static final long serialVersionUID = 6086826164620633328L;

    /**
     * 自定义查询 返回结果数
     */
    private int returnCount;

    /**
     * 用户实体
     */
    private UserEntity user;

    /**
     * 自定义查询条件 集合
     */
    private List<UserDefinedQueryConditionEntity> userQueryConditions = new ArrayList<UserDefinedQueryConditionEntity>();

    /* 以下是getter和setter方法 */

    /**
     * @return the user
     */
    public UserEntity getUser() {
	return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(UserEntity user) {
	this.user = user;
    }

    /**
     * @return the userQueryConditions
     */
    public List<UserDefinedQueryConditionEntity> getUserQueryConditions() {
	return userQueryConditions;
    }

    /**
     * @param userQueryConditions
     *            the userQueryConditions to set
     */
    public void setUserQueryConditions(
	    List<UserDefinedQueryConditionEntity> userQueryConditions) {
	this.userQueryConditions = userQueryConditions;
    }

    /**
     * @return the returnCount
     */
    public int getReturnCount() {
    	if(returnCount == NumberConstants.ZERO){
    		returnCount = NumberConstants.NUMERAL_HUNDRED;
    	}
    	return returnCount;
    }

    /**
     * @param returnCount
     *            the returnCount to set
     */
    public void setReturnCount(int returnCount) {
	this.returnCount = returnCount;
    }

}
