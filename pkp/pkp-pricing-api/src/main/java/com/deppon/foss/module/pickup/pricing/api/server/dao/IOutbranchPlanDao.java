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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity;

/**
 * 快递代理理网点运价方案Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 下午2:55:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 下午2:55:11
 * @since
 * @version
 */
public interface IOutbranchPlanDao {
    
    /**
     * 新快递代理网点运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param entit快递代理递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addInfo(OutbranchPlanEntity entity);
    
    /**
     * 根据cod快递代理快递代理网点运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteInfo(List<String> codes,String modifyUser);
    
    /**
     * <p>根据父快递代理除快递代理网点运价方案信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-30 下午3:18:15
     * @param parentcode快递代理t 快递代理公司运价ＩＤ
     * @return
     * @see
     */
    int deleteInfoByParentCode(List<String> parentcodeList);
    
    /**
   快递代理 修改快递代理网点运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param 快递代理ity 快递代理网点运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateInfo(OutbranchPlanEntity entity);
    
    /**
    快递代理<p>激活快递代理网点运价方案 </p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:25:30
     * @param entity
     * @return
     * @see
     */
    int activate(OutbranchPlanEntity entity);
    
    /**
     * 根据传入对快递代理符合条件所有快递代理网点运价方案信息 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<OutbranchPlanEntity> queryInfos(OutbranchPlanEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @par快递代理entity 快递代理网点运价方案实体
     * @return
     * @see
     */
    Long queryRecordCount(OutbranchPlanEntity entity);
    
    /**
     快递代理p>根据ID查询快递代理网点运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午5:07:43
     * @param id
     * @return
     * @see
     */
    OutbranchPlanEntity queryInfoById(String id);

    /**
快递代理  * <p>根据快递代理网点查询价格明细</p> 
     * @author zhangdongping
     * @date Jul 30, 2013 9:29:16 AM
     * @param outerBranchCode 代理code
     * @param billDate 开单日期 ，不传默认为当前时间
     * @return OutbranchPlanEntity
     * @see
     */
    OutbranchPlanEntity queryPriceByCode(String outerBranchCode, Date billDate);

     
   
}
