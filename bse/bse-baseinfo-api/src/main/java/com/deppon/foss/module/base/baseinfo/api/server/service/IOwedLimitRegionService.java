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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOwedLimitRegionService.java
 * 
 * FILE NAME        	: IOwedLimitRegionService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwedLimitDto;


/**
 * 临欠额度区间范围信息Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-25 上午9:39:45 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-25 上午9:39:45
 * @since
 * @version
 */
public interface IOwedLimitRegionService extends IService {
    
    /**
     * <p>添加临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:07:50
     * @param entity
     * @return
     * @see
     */
     int addInfo(OwedLimitRegionEntity entity);
    
    /**
     * <p>作废临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:08:58
     * @param ids 
     * @return
     * @see
     */
     int deleteInfos(List<String> ids,String modifyUser);
    
    /**
     * <p>修改临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:11:13
     * @param entity
     * @return
     * @see
     */
     int updateInfo(OwedLimitRegionEntity entity);
    
    /**
     * <p>分页查询临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:12:47
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<OwedLimitRegionEntity> queryAllInfos(OwedLimitRegionEntity entity,int limit,int start);
    
    /**
     * <p>查询总记录数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:17:05
     * @param entity
     * @return
     * @see
     */
     Long queryRecordCount(OwedLimitRegionEntity entity);
    
    /**
     * <p>批量更新营业部最大临欠额度</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 上午9:31:20
     * @param list 所有营业部最近三个月最大营业收入信息DTO集合
     * @return
     * @see
     */
     int batchUpdateAmountOweds(List<OwedLimitDto> list);

}
