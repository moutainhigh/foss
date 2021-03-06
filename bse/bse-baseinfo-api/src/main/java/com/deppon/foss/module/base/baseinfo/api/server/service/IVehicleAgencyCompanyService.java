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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IVehicleAgencyCompanyService.java
 * 
 * FILE NAME        	: IVehicleAgencyCompanyService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;

/**
 * 偏线代理公司service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-xieyantao,date:2012-10-15 上午10:28:17</p>
 * @author dp-xieyantao
 * @date 2012-10-15 上午10:28:17
 * @since
 * @version
 */
public interface IVehicleAgencyCompanyService extends IService {
    
    /**
     * 新增偏线代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:28:17
     * @param entity 偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addVehicleAgencyCompany(BusinessPartnerEntity entity) ;
    
    /**
     * 根据code作废偏线代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:28:17
     * @param codeStr 虚拟code拼接的字符串
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteVehicleAgencyCompanyByCode(String codeStr,String modifyUser);
    
    /**
     * 修改偏线代理公司 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:28:17
     * @param entity 偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateVehicleAgencyCompany(BusinessPartnerEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有偏线代理公司信息 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:28:17
     * @param entity 偏线/空运代理公司实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<BusinessPartnerEntity> queryVehicleAgencyCompanys(BusinessPartnerEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:28:17
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
     * @return null:不存在  BusinessPartnerEntity:存在
     * @see
     */
     BusinessPartnerEntity queryEntityByCode(String agentCompanyCode);
    
     /**
      * 
      * <p>通过代理公司编码查询代理公司名称（不区分偏线或空运）</p> 
      * @author foss-zhujunyong
      * @date Mar 28, 2013 2:44:21 PM
      * @param code
      * @return
      * @see
      */
     String queryBusinessPartnerNameByCode(String code);     
     
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
     * <p>根据传入的代理编码和代理类型判断代理信息是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-20 下午4:28:43
     * @param agencyCode 代理编码
     * @param agencyType 代理类别：DictionaryValueConstants.AGENCY_TYPE_COM(代理公司)
     *                          DictionaryValueConstants.AGENCY_TYPE_BRANCH（代理网点）
     * @return
     * @see
     */
     boolean queryAgencyIsExistByCodeAndType(String agencyCode,String agencyType);
    
     /**
      * 
      * <p>通过代理公司网点编码查询代理公司编码</p> 
      * @author foss-zhujunyong
      * @date Mar 28, 2013 3:37:28 PM
      * @param siteCode
      * @return
      * @see
      */
     String queryCompanyCodeBySiteCode(String siteCode);
     
     /**
      * <p>根据传入条件 导出查询结果</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-7-10 下午4:43:38
      * @param entity
      * @return
      * @see
      */
     ExportResource exportVehicleAgencyComtList(BusinessPartnerEntity entity);

}
