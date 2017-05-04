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
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity;
/**
 * 汽运价格报表表头信息Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-10 上午8:49:48 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-10 上午8:49:48
 * @since
 * @version
 */
public interface IPriceReportTitleService extends IService{
    
    /**
     * 新增汽运价格报表表头信息
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 上午8:49:48
     * @param entity 汽运价格报表表头信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addInfo(PriceReportTitleEntity entity);
    
    /**
     * 根据code作废汽运价格报表表头信息 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteInfo(List<String> codes,String modifyUser);
    
    /**
     * 修改汽运价格报表表头信息 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param entity 汽运价格报表表头信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateInfo(PriceReportTitleEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有汽运价格报表表头信息信息 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<PriceReportTitleEntity> queryInfos(PriceReportTitleEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param entity 汽运价格报表表头信息实体
     * @return
     * @see
     */
    Long queryRecordCount(PriceReportTitleEntity entity);
    
    /**
     * <p>根据ID查询汽运价格报表表头信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午5:07:43
     * @param id
     * @return
     * @see
     */
    PriceReportTitleEntity queryInfoById(String id);
    
    /**
     * <p>按序号从小到大查询所有可以显示的汽运价格表表头信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-2-13 上午9:57:56
     * @return
     * @see
     */
    List<PriceReportTitleEntity> queryAllInfos();
    
    
    /**
     * <p>按序号从小到大查询所有可以显示的汽运价格表表头信息(新增合伙人标记)</p> 
     * @author 370613-foss-LianHe
     * @date 2016年9月20日 下午2:45:26
     * @param isPartner 是否合伙人
     * @return
     * @see
     */
    List<PriceReportTitleEntity> queryAllInfos(String isPartner);
}
