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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/BillSloganEntity.java
 * 
 * FILE NAME        	: BillSloganEntity.java
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
 * 单据广告语实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 上午11:46:47 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 上午11:46:47
 * @since
 * @version
 */
public class BillSloganEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 6436087276200376461L;

    /**
     * 广告语代码.
     */
    private String sloganCode;

    /**
     * 广告语名称.
     */
    private String sloganName;

    /**
     * 所属子系统.
     */
    private String subSystem;

    /**
     * 子系统功能模块.
     */
    private String subSystemModule;

    /**
     * 广告语内容.
     */
    private String content;

    /**
     * 广告语类别.
     */
    private String sloganSort;

    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;

    
    /**
     * 获取 广告语代码.
     *
     * @return  the sloganCode
     */
    public String getSloganCode() {
        return sloganCode;
    }

    
    /**
     * 设置 广告语代码.
     *
     * @param sloganCode the sloganCode to set
     */
    public void setSloganCode(String sloganCode) {
        this.sloganCode = sloganCode;
    }

    
    /**
     * 获取 广告语名称.
     *
     * @return  the sloganName
     */
    public String getSloganName() {
        return sloganName;
    }

    
    /**
     * 设置 广告语名称.
     *
     * @param sloganName the sloganName to set
     */
    public void setSloganName(String sloganName) {
        this.sloganName = sloganName;
    }

    
    /**
     * 获取 所属子系统.
     *
     * @return  the subSystem
     */
    public String getSubSystem() {
        return subSystem;
    }

    
    /**
     * 设置 所属子系统.
     *
     * @param subSystem the subSystem to set
     */
    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    
    /**
     * 获取 子系统功能模块.
     *
     * @return  the subSystemModule
     */
    public String getSubSystemModule() {
        return subSystemModule;
    }

    
    /**
     * 设置 子系统功能模块.
     *
     * @param subSystemModule the subSystemModule to set
     */
    public void setSubSystemModule(String subSystemModule) {
        this.subSystemModule = subSystemModule;
    }

    
    /**
     * 获取 广告语内容.
     *
     * @return  the content
     */
    public String getContent() {
        return content;
    }

    
    /**
     * 设置 广告语内容.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    
    /**
     * 获取 广告语类别.
     *
     * @return  the sloganSort
     */
    public String getSloganSort() {
        return sloganSort;
    }

    
    /**
     * 设置 广告语类别.
     *
     * @param sloganSort the sloganSort to set
     */
    public void setSloganSort(String sloganSort) {
        this.sloganSort = sloganSort;
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
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    

}
