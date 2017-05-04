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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IOrgAdministrativeInfoDao.java
 * 
 * FILE NAME        	: IOrgAdministrativeInfoDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoToUUEntity;
/**
 * 配合主数据操作组织中间表 DAO接口
 * @author 187862-dujunhui
 * @date 2015-4-11 下午3:36:16
 */
public interface IOrgAdministrativeInfoToUUDao {
    /**
     * 插入
     * @author 187862-dujunhui
     * @date 2015-4-11 下午3:37:44
     */
    OrgAdministrativeInfoToUUEntity addOrgAdministrativeInfoToUU(OrgAdministrativeInfoToUUEntity entity);
    /**
     * 
     * <p>新增FOSS归属字段小时间链表信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-25 下午2:23:30
     * @param entity
     * @return
     * @see
     */
    OrgAdministrativeInfoToUUEntity addOrgAdministrativeInfoFOSS(OrgAdministrativeInfoToUUEntity entity);
    /**
     * 修改
     * @author 187862-dujunhui
     * @date 2015-4-11 下午3:39:24
     */
    OrgAdministrativeInfoToUUEntity updateOrgAdministrativeInfoToUU(OrgAdministrativeInfoToUUEntity entity);
    /**
     * 根据ID List修改信息
     */
    int updateOrgAdministrativeInfoToUUByIds(List<String> ids,String failCount,String sendStatus);
	
    /**
     * 查询
     * @author 187862-dujunhui
     * @date 2015-4-11 下午3:39:24
     */
    List<OrgAdministrativeInfoToUUEntity> queryOrgAdministrativeInfoByCondition(
    		OrgAdministrativeInfoToUUEntity entity);
    
    /**
     * 根据List查询
     * @author 187862-dujunhui
     * @date 2015-4-20 下午7:19:35
     */
    List<OrgAdministrativeInfoToUUEntity> queryOrgAdministrativeInfoByConditionList(
    		OrgAdministrativeInfoToUUEntity entity,List<String> conditionSendStatusList);

    /**
     * 
     * <p>查询FOSS归属字段小时间链表信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-25 下午3:47:37
     * @param entity
     * @return
     * @see
     */
    List<OrgAdministrativeInfoToUUEntity> queryOrgAdministrativeInfoFOSSByCondition(
			OrgAdministrativeInfoToUUEntity entity);
    /**
     * 
     * <p>查询数据库时间</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-25 下午5:14:38
     * @return
     * @see
     */
    String querySystimestamp();
    /**
     * 
     * <p>配合主数据项目调用FOSS接收UUMS组织信息处理</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-27 下午2:15:52
     * @see
     */
    String callMDMOrgProcedure();
    /**
     * 
     * <p>配合主数据项目新增UUMS至FOSS组织信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-27 下午2:40:05
     * @param entity
     * @return
     * @see
     */
    OrgAdministrativeInfoEntity addUUMSToFOSSMidTable(
			OrgAdministrativeInfoEntity entity);

    /**
     * 
     * <p>根据条件查询UUMS至FOSS组织信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-27 下午4:41:58
     * @return
     * @see
     */
    List<OrgAdministrativeInfoEntity> queryUUMSToFOSSMidTable(OrgAdministrativeInfoEntity entity);
    
    /**
	 * <p>长锁的处理逻辑：判断sending状态的修改时间与当前时间之差是否超过半小时</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-5-9 下午4:55:21
	 * @param entity
	 * @return 
	 */
	List<OrgAdministrativeInfoToUUEntity> querySendingStatusLongTimeEntity();
}
