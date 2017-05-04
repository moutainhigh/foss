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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISiteGroupSiteDao.java
 * 
 * FILE NAME        	: ISiteGroupSiteDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;

/**
 * 站点组站点站点Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-10-11 下午2:45:39</p>
 * @author dp-xieyantao
 * @date 2012-10-11 下午2:45:39
 * @since
 * @version
 */
public interface ISiteGroupSiteDao {
    
    /**
     * 新增站点组站点 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:45:39
     * @param entity 站点组站点实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addSiteGroupSite(SiteGroupSiteEntity entity) ;
    
    /**
     * 根据code作废站点组站点信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:45:39
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteSiteGroupSiteByCode(String[] codes, String modifyUser);
    
    /**
     * <p>验证所属站点组虚拟编码、站点序号是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-28 上午11:05:08
     * @param entity
     * @return
     * @see
     */
    SiteGroupSiteEntity queryGroupSiteBySeqVirtualCode(SiteGroupSiteEntity entity);
    
    /**
	 * <p>
	 * 验证所属站点组站点是否重复
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-06-04 上午11:05:08
	 * @param entity
	 * @return
	 * @see
	 */
	SiteGroupSiteEntity queryGroupSiteBySiteCode(
			SiteGroupSiteEntity entity);

    
    /**
     * 修改站点组站点信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:45:39
     * @param entity 站点组站点实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateSiteGroupSite(SiteGroupSiteEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有站点组站点信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:45:39
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<SiteGroupSiteEntity> querySiteGroupSites(SiteGroupSiteEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:45:39
     * @param entity 站点组站点实体
     * @return
     * @see
     */
    Long queryRecordCount(SiteGroupSiteEntity entity);
    
    /**
     * <p>根据站点组虚拟编码查询所有站点</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-8 下午2:32:13
     * @param siteGroupCode 站点组虚拟编码
     * @return
     * @see
     */
    List<SiteGroupSiteEntity> queryAllSitesByCode(String siteGroupCode);
    
    
}
