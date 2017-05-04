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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IBankDao.java
 * 
 * FILE NAME        	: IBankDao.java
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
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;

/**
 * 快递代理理网点运价方案明细明细Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 下午2:55:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 下午2:55:11
 * @since
 * @version
 */
public interface IOubrPlanDetailDao {
    
    /**
     * 新快递代理网点运价方案明细明细 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param entit快递代理递代理网点运价方案明细明细实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addInfo(OubrPlanDetailEntity entity);
    
    /**
     * 根据cod快递代理快递代理网点运价方案明细 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteInfo(List<String> codes,String modifyUser);
    
    /**
     * <p>根据父快递代理除快递代理网点运价方案明细信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午3:18:15
     * @param parentcode快递代理t 快递代理网点运价ＩＤ
     * @return
     * @see
     */
    int deleteInfoByParentCode(List<String> parentcodeList);
    
    /**
   快递代理 修改快递代理网点运价方案明细
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param 快递代理ity 快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateInfo(OubrPlanDetailEntity entity);
    
    /**
 快递代理 * 激活快递代理网点运价方案明细
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:42:19
     * @para快递代理ntity 快递代理网点运价方案明细实体
     * @return 1：成功；-1：失败
     * @see
     */
    int activate(OubrPlanDetailEntity entity);
    
    /**
     * 根据传入快递代理询符合条件所有快递代理网点运价方案明细信息 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<OubrPlanDetailEntity> queryInfos(OubrPlanDetailEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @pa快递代理 entity 快递代理网点运价方案明细实体
     * @return
     * @see
     */
    Long queryRecordCount(OubrPlanDetailEntity entity);
    
    /**
    快递代理<p>根据ID查询快递代理网点运价方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午5:45:21
     * @param id
     * @return
     * @see
     */
    OubrPlanDetailEntity queryinfoById(String id);
    
    /**
     * <p>根快递代理D查询右区间最大的值快递代理网点运价方案明细信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-1 下午5:45:52
     * @param parentId
     * @return
     * @see
     */
    List<OubrPlanDetailEntity> queryMaxInfoByParentId(String parentId);
    
   
}
