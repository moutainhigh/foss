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
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity;


/**
 * 快递代理理公司运价方案Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 下午2:55:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 下午2:55:11
 * @since
 * @version
 */
public interface IPartbussPlanService extends IService{
    
    /**
     * 新快递代理公司运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param entit快递代理递代理公司运价方案实体
     * @return entity
     * @see
     */
    PartbussPlanEntity addInfo(PartbussPlanEntity entity);
    
    /**
     * 根据cod快递代理快递代理公司运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteInfo(List<String> codes,String modifyUser);
    
    /**
     快递代理改快递代理公司运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param en快递代理y 快递代理公司运价方案实体
     * @return entity
     * @see
     */
    PartbussPlanEntity updateInfo(PartbussPlanEntity entity);
    
    /**
   快递代理 激活快递代理公司运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:48:04
     * @param 快递代理ity 快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
    int activate(PartbussPlanEntity entity);
    
    /**
     * <p>根据传入快递代理ID 激活快递代理公司运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午4:04:45
     * @param idList id集合
     * @return
     * @see
     */
    int activateList(List<String> idList);
    
    /**
     快递代理p>立即激活快递代理公司运价方案 </p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @par快递代理entity 快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
    int immediatelyActivate(PartbussPlanEntity entity);
    
    /**
   快递代理 <p>立即中止快递代理公司运价方案 </p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-23 下午4:50:24
     * @p快递代理m entity 快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
    int immediatelyStop(PartbussPlanEntity entity);
    
    /**
     * 根快递代理对象查询符合条件所有快递代理公司运价方案信息 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<PartbussPlanEntity> queryInfos(PartbussPlanEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * 快递代理ram entity 快递代理公司运价方案实体
     * @return
     * @see
     */
    Long queryRecordCount(PartbussPlanEntity entity);
    
    /**
 快递代理 * <p>根据ID查询快递代理公司运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午4:33:30
     * @param id
     * @return
     * @see
     */
    PartbussPlanEntity queryInfoById(String id);
    
    /**
快递代理  * <p>根据ID复制快递代理运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-29 上午10:50:25
     * @param id
     * @return
     * @see
     */
    PartbussPlanEntity copyInfoById(String id);
    
    /*快递代理     * <p>批量保存快递代理运价方案(导入数据用)</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-10-19 下午4:38:45
     * @param list
     * @return
     * @see
     */
    int batchSave(List<PartbussPlanEntity> list);
    
   
}
