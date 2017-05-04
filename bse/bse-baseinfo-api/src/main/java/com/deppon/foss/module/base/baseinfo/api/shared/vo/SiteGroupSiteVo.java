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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/SiteGroupSiteVo.java
 * 
 * FILE NAME        	: SiteGroupSiteVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;

/**
 * 站点组站点VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 上午9:37:29 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 上午9:37:29
 * @since
 * @version
 */
public class SiteGroupSiteVo implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3875916350859197349L;
    
    /**
     * 站点组站点实体类.
     */
    private SiteGroupSiteEntity entity;
    
    /**
     * 站点组站点信息集合.
     */
    private List<SiteGroupSiteEntity> siteGroupSiteEntities;
    
    /**
     * codes字符串.
     */
    private String codes;
    
    /**
     * 获取 站点组站点实体类.
     *
     * @return  the entity
     */
    public SiteGroupSiteEntity getEntity() {
        return entity;
    }
    
    /**
     * 设置 站点组站点实体类.
     *
     * @param entity the entity to set
     */
    public void setEntity(SiteGroupSiteEntity entity) {
        this.entity = entity;
    }
    
    /**
     * 获取 站点组站点信息集合.
     *
     * @return  the siteGroupSiteEntities
     */
    public List<SiteGroupSiteEntity> getSiteGroupSiteEntities() {
        return siteGroupSiteEntities;
    }
    
    /**
     * 设置 站点组站点信息集合.
     *
     * @param siteGroupSiteEntities the siteGroupSiteEntities to set
     */
    public void setSiteGroupSiteEntities(
    	List<SiteGroupSiteEntity> siteGroupSiteEntities) {
        this.siteGroupSiteEntities = siteGroupSiteEntities;
    }
    
    /**
     * 获取 codes字符串.
     *
     * @return  the codes
     */
    public String getCodes() {
        return codes;
    }
    
    /**
     * 设置 codes字符串.
     *
     * @param codes the codes to set
     */
    public void setCodes(String codes) {
        this.codes = codes;
    }
    
}
