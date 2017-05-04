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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IBargainAppOrgDao.java
 * 
 * FILE NAME        	: IBargainAppOrgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;


/**
 * 合同适用部门DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:18:15 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:18:15
 * @since
 * @version
 */
public interface IBargainAppOrgDao {
    
    /**
     * 新增合同适用部门
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:18:15
     * @param entity
     *            合同适用部门实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addBargainAppOrg(BargainAppOrgEntity entity);

    /**
     * 根据code作废合同适用部门
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:18:15
     * @param crmId
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteBargainAppOrgByCode(BigDecimal crmId, String modifyUser);

    /**
     * 修改合同适用部门
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:18:15
     * @param entity
     *            合同适用部门实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateBargainAppOrg(BargainAppOrgEntity entity);
    
    /**
     * <p>根据客户合同CRM_ID查询合同适用部门信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-3 上午11:28:02
     * @param bargainCrmId 客户合同CRM_ID
     * @return
     * @see
     */
    List<BargainAppOrgEntity> queryAppOrgByBargainCrmId(BigDecimal bargainCrmId);
    
    /**
     * <p>根据crmId,最后一次修改时间查询合同适用部门是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:15:29
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
    boolean queryBargainAppOrgByCrmId(BigDecimal crmId,Date lastupdatetime);
    
    /**
     * 根据crmId和部门编码获取合同适用部门
     * @param bargainCrmId
     * @param unifiedCode
     * @return
     */
    List<BargainAppOrgEntity> queryAppOrgByBargainCrmIdAndCode(BigDecimal bargainCrmId,String unifiedCode);
    
    /**
     * 根据crmId和部门编码列表获取合同适用部门
     * @param bargainCrmId
     * @param unifiedCodeList
     * @return
     */
    List<BargainAppOrgEntity> queryAppOrgByBargainCrmIdAndCodeList(BigDecimal bargainCrmId,List<String> unifiedCodeList);

}
