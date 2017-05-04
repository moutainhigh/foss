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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IVehicleAgencyCompanyDao.java
 * 
 * FILE NAME        	: IVehicleAgencyCompanyDao.java
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
 * 用来操作交互“偏线代理公司”的数据库对应数据访问DAO接口：SUC-754
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午2:01:13</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 下午2:01:13
 * @since
 * @version
 */
public interface IVehicleAgencyCompanyDao {
    
    /**
     * 新增偏线代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:19:06
     * @param entity 偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addVehicleAgencyCompany(BusinessPartnerEntity entity) ;
    
    /**
     * 根据code作废偏线代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:19:06
     * @param codes code字符串数组
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteVehicleAgencyCompanyByCode(String[] codes,String modifyUser);
    
    /**
     * <p>根据虚拟编码作废空运代理公司</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-29 下午3:48:46
     * @param virtualCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteAgencyCompanyByVirtualCode(String virtualCode,String modifyUser);
    
    /**
     * 修改偏线代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:19:06
     * @param entity 偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateVehicleAgencyCompany(BusinessPartnerEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有偏线代理公司信息 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:19:06
     * @param entity 偏线/空运代理公司实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<BusinessPartnerEntity> queryVehicleAgencyCompanys(BusinessPartnerEntity entity,int limit,int start);
    
    /**
     * <p>下载合作伙伴(代理公司信息) 数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-15 下午3:18:34
     * @param entity
     * @return
     * @see
     */
    List<BusinessPartnerEntity> queryBusinessPartnersForDownload(BusinessPartnerEntity entity);
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:19:06
     * @param entity 偏线/空运代理公司实体
     * @return 符合条件的总记录数
     * @see
     */
    Long queryRecordCount(BusinessPartnerEntity entity);
    
    /**
     * 根据偏线代理公司名称查询代理公司信息 (验证该代理公司是否存在)
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:05:59
     * @param agentCompanyName 偏线代理公司名称
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
    BusinessPartnerEntity queryEntityByName(String agentCompanyName);
    
    /**
     * 根据偏线代理公司简称查询代理公司信息 (验证该代理公司是否存在) 
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:10:56
     * @param simplename 代理公司简称
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
    BusinessPartnerEntity queryEntityBySimplename(String simplename);
    
    /**
     * 根据偏线代理公司编码查询代理公司信息 (验证该代理公司是否存在) 
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:13:16
     * @param agentCompanyCode 代理公司编码
     * @param agencySort 代理公司类型：偏线(PX)、空运（KY）
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
    BusinessPartnerEntity queryEntityByCode(String agentCompanyCode,String agencySort);
    
    /**
     * 根据代理公司虚拟编码查询代理公司信息 
     * @author 094463-foss-xieyantao
     * @date 2012-12-12 下午3:18:28
     * @param virtualCode 代理公司虚拟编码
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
    BusinessPartnerEntity queryEntityByVirtualCode(String virtualCode);
    /**

     * 根据id作废代理公司信息

     *  

     * */

	int deleteVehicleAgencyCompanyById(String id);



    /**

     * 根据偏线代理公司编码查询代理公司信息 (验证该代理公司是否存在) 

     */

	BusinessPartnerEntity queryEntityByCodeAndActive(String agentCompanyCode,

			String active);

	

	/**

     * 根据angentCompanyCode作废代理公司信息

     *  

     * */

	int deleteAgencyCompanyByCompanyCode(String angentCompanyCode);

	

	/**

     * 	根据id更新偏线代理公司 

     *  

     * */

	int updateVehicleAgencyCompanyById(BusinessPartnerEntity entity);

    
}
