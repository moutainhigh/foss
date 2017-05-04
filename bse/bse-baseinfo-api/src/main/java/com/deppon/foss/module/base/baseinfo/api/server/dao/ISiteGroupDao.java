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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISiteGroupDao.java
 * 
 * FILE NAME        	: ISiteGroupDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;

/**
 * 站点组Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-xieyantao,date:2012-10-11 下午2:34:35</p>
 * @author dp-xieyantao
 * @date 2012-10-11 下午2:34:35
 * @since
 * @version
 */
public interface ISiteGroupDao {
    
    /**
     * 新增站点组 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:34:35
     * @param entity 站点组实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addSiteGroup(SiteGroupEntity entity) ;
    
    /**
     * 根据code作废站点组信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:34:35
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteSiteGroupByCode(String[] codes,String modifyUser);
    
    /**
     * 修改站点组信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:34:35
     * @param entity 站点组实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateSiteGroup(SiteGroupEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有站点组信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:34:35
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<SiteGroupEntity> querySiteGroups(SiteGroupEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:34:35
     * @param entity 站点组实体
     * @return
     * @see
     */
    Long queryRecordCount(SiteGroupEntity entity);
    
    /**
     * 根据站点组虚拟编码作废该站点组下所有的站点  
     * @author 094463-foss-xieyantao
     * @date 2012-11-1 上午10:38:21
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteSiteByCode(String[] codes,String modifyUser);
    
    /**
     * <p>根据站点组虚拟编码查询站点组信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-8 下午2:56:24
     * @param siteGroupCode 站点组虚拟编码
     * @return
     * @see
     */
    SiteGroupEntity querySiteGroupByCode(String siteGroupCode);
    
    /**
     * <p>根据站点组站点（外场）的部门编码查询所属的站点组信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-7 下午1:55:31
     * @param deptCode
     * @return
     * @see
     */
    List<SiteGroupEntity> querySiteGroupsBySiteCode(String deptCode);
    
    /**
     * <p>验证站点组名称、所属部门编码、站点组类型组合是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-28 下午2:30:21
     * @param entity 站点组实体
     * @return
     * @see
     */
    SiteGroupEntity querySiteGroupIsExist(SiteGroupEntity entity);
    
}
