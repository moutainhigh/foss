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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IAirAgencyCompanyDao.java
 * 
 * FILE NAME        	: IAirAgencyCompanyDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
/**
 * (用来操作交互“空运代理公司”的数据库对应数据访问DAO接口：SUC-786)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午1:57:39</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 下午1:57:39
 * @since
 * @version
 */
public interface IAirAgencyCompanyDao {
    
    /**
     * 新增空运代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:03:18
     * @param entity 偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addAirAgencyCompany(BusinessPartnerEntity entity) ;
    
    /**
     * 根据code作废空运代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:03:18
     * @param codes code字符串数组
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteAirAgencyCompanyByCode(String[] codes,String modifyUser);
    
    /**
     * <p>根据虚拟编码作废空运代理公司</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-29 下午3:50:46
     * @param virtualCode 虚拟编码
     * @param modifyUser
     * @return
     * @see
     */
    int deleteAgencyCompanyByVirtualCode(String virtualCode,String modifyUser);
    
    /**
     * 修改空运代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:03:18
     * @param entity 偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateAirAgencyCompany(BusinessPartnerEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有空运代理公司信息 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:03:18
     * @param entity 偏线/空运代理公司实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<BusinessPartnerEntity> queryAirAgencyCompanys(BusinessPartnerEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:03:18
     * @param entity 偏线/空运代理公司实体
     * @return 符合条件的总记录数
     * @see
     */
    Long queryRecordCount(BusinessPartnerEntity entity);
    
    /**
     * 根据空运代理公司名称查询代理公司信息 (验证该代理公司是否存在)
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:05:59
     * @param agentCompanyName 空运代理公司名称
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
    BusinessPartnerEntity queryEntityByName(String agentCompanyName);
    
    /**
     * 根据空运代理公司简称查询代理公司信息 (验证该代理公司是否存在) 
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:10:56
     * @param simplename 代理公司简称
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
    BusinessPartnerEntity queryEntityBySimplename(String simplename);
    
    /**
     * 根据空运代理公司编码查询代理公司信息 (验证该代理公司是否存在) 
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:13:16
     * @param agentCompanyCode 代理公司编码
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
    BusinessPartnerEntity queryEntityByCode(String agentCompanyCode);
    
    /**
     * <p>通过代理网点的编码查询所属的代理公司信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-15 下午5:05:44
     * @param agencyDeptCode 代理网点编码
     * @return
     * @see
     */
    BusinessPartnerEntity queryBusinessPartnerByAgencyDeptCode(String agencyDeptCode);
    
    
}
