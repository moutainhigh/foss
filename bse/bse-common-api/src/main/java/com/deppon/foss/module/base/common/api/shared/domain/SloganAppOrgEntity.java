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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/SloganAppOrgEntity.java
 * 
 * FILE NAME        	: SloganAppOrgEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 部门短信广告语实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-13 下午4:27:42 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-13 下午4:27:42
 * @since
 * @version
 */
public class SloganAppOrgEntity extends BaseEntity {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 900497952280929645L;
    
    /**
     * 适用部门Code.
     */
    private String orgCode;
    
    /**
     * 适用部门名称（扩展）.
     */
    private String orgName;
    
    /**
     * 广告语内容.
     */
    private String sloganContent;
    
    /**
     * 广告语类型：1：短信广告语 2：单据广告语.
     */
    private String sloganSort;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 所属广告语code.
     */
    private String sloganCode;
  
    /**
     * 获取 适用部门名称（扩展）.
     *
     * @return  the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    
    /**
     * 设置 适用部门名称（扩展）.
     *
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取 适用部门Code.
     *
     * @return the 适用部门Code
     */
    public String getOrgCode() {
        return orgCode;
    }
    
    /**
     * 设置 适用部门Code.
     *
     * @param orgCode the new 适用部门Code
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    /**
     * 获取 广告语内容.
     *
     * @return the 广告语内容
     */
    public String getSloganContent() {
        return sloganContent;
    }
    
    /**
     * 设置 广告语内容.
     *
     * @param sloganContent the new
     */
    public void setSloganContent(String sloganContent) {
        this.sloganContent = sloganContent;
    }
    
    /**
     * 获取 广告语类型：1：短信广告语 2：单据广告语.
     *
     * @return the 广告语类型：1：短信广告语 2：单据广告语
     */
    public String getSloganSort() {
        return sloganSort;
    }
    
    /**
     * 设置 广告语类型：1：短信广告语 2：单据广告语.
     *
     * @param sloganSort the new 广告语类型：1：短信广告语 2：单据广告语
     */
    public void setSloganSort(String sloganSort) {
        this.sloganSort = sloganSort;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return the 是否启用
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the new 是否启用
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 所属广告语code.
     *
     * @return the 所属广告语code
     */
    public String getSloganCode() {
        return sloganCode;
    }
    
    /**
     * 设置 所属广告语code.
     *
     * @param sloganCode the new 所属广告语code
     */
    public void setSloganCode(String sloganCode) {
        this.sloganCode = sloganCode;
    }
    
    
    
    
    
    
}
