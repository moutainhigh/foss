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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/UserDefinedQueryConditionEntity.java
 * 
 * FILE NAME        	: UserDefinedQueryConditionEntity.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 自定义查询 条件 实体.
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2013-01-21 20:51
 */
public class UserDefinedQueryConditionEntity extends BaseEntity {

    private static final long serialVersionUID = -5097037210563207860L;

    /**
     * 比较条件
     */
    private String compareCondition;

    /**
     * 比较符
     */
    private String compareSign;

    /**
     * 比较值
     */
    private String compareValue;

    /**
     * 逻辑符
     */
    private String logicSign;

    /**
     * 顺序
     */
    private int seq;

    /**
     * 查询方案code
     */
    private String schemeCode;

    /**
     * 是否启用.
     */
    private String active;

    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime
				* result
				+ ((compareCondition == null) ? 0 : compareCondition.hashCode());
		result = prime * result
				+ ((compareSign == null) ? 0 : compareSign.hashCode());
		result = prime * result
				+ ((compareValue == null) ? 0 : compareValue.hashCode());
		result = prime * result
				+ ((logicSign == null) ? 0 : logicSign.hashCode());
		result = prime * result
				+ ((schemeCode == null) ? 0 : schemeCode.hashCode());
		result = prime * result + seq;
		return result;
	} 
    
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass().getPackage() != obj.getClass().getPackage()) {
	    return false;
	}
	UserDefinedQueryConditionEntity userObj = (UserDefinedQueryConditionEntity) obj;
	return StringUtil.equals(this.getActive(), userObj.getActive())
		&& StringUtil.equals(this.getSchemeCode(),
			userObj.getSchemeCode())
		&& StringUtil.equals(this.getCompareCondition(),
			userObj.getCompareCondition())
		&& StringUtil.equals(this.getCompareSign(),
			userObj.getCompareSign())
		&& StringUtil.equals(this.getCompareValue(),
			userObj.getCompareValue())
		&& StringUtil.equals(this.getLogicSign(),
			userObj.getLogicSign());
    }
    

    /* 以下是getter和setter方法 */

    /**
     * @return the active
     */
    public String getActive() {
	return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(String active) {
	this.active = active;
    }

    /**
     * @return the compareCondition
     */
    public String getCompareCondition() {
	return compareCondition;
    }

    /**
     * @param compareCondition
     *            the compareCondition to set
     */
    public void setCompareCondition(String compareCondition) {
	this.compareCondition = compareCondition;
    }

    /**
     * @return the compareSign
     */
    public String getCompareSign() {
	return compareSign;
    }

    /**
     * @param compareSign
     *            the compareSign to set
     */
    public void setCompareSign(String compareSign) {
	this.compareSign = compareSign;
    }

    /**
     * @return the compareValue
     */
    public String getCompareValue() {
	return compareValue;
    }

    /**
     * @param compareValue
     *            the compareValue to set
     */
    public void setCompareValue(String compareValue) {
	this.compareValue = compareValue;
    }

    /**
     * @return the logicSign
     */
    public String getLogicSign() {
	return logicSign;
    }

    /**
     * @param logicSign
     *            the logicSign to set
     */
    public void setLogicSign(String logicSign) {
	this.logicSign = logicSign;
    }

    /**
     * @return the seq
     */
    public int getSeq() {
	return seq;
    }

    /**
     * @param seq
     *            the seq to set
     */
    public void setSeq(int seq) {
	this.seq = seq;
    }

    /**
     * @return the schemeCode
     */
    public String getSchemeCode() {
	return schemeCode;
    }

    /**
     * @param schemeCode
     *            the schemeCode to set
     */
    public void setSchemeCode(String schemeCode) {
	this.schemeCode = schemeCode;
    }
}
