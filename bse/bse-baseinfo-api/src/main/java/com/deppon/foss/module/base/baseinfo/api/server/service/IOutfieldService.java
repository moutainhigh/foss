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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOutfieldService.java
 * 
 * FILE NAME        	: IOutfieldService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OutfieldException;
/**
 * 外场 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:58:3
 */
public interface IOutfieldService extends IService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    OutfieldEntity addOutfield(OutfieldEntity entity)throws OutfieldException;
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    OutfieldEntity deleteOutfield(OutfieldEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    OutfieldEntity updateOutfield(OutfieldEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    OutfieldEntity queryOutfieldByCode(String code);	
    
    /**
     * 根据行政组织编码查询外场
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-11-12 下午2:58:3
     */
    OutfieldEntity queryOutfieldByOrgCode(String code);	
    /**
     * 
     *<p>根据组织编码查询外场，不走缓存查询</p>
     *@author 130566-zengJunfan
     *@date   2013-8-30上午9:56:06
     * @param code
     * @return
     */
    OutfieldEntity queryOutfieldByOrgCodeNoCache(String code);
	
    /**
     * 
     * <p>根据外场部门编码，查询外场实体</p> 
     * @author foss-zhujunyong
     * @date Apr 10, 2013 3:53:23 PM
     * @param orgCode
     * @return
     * @see
     */
    OutfieldEntity querySimpleOutfieldByOrgCode(String orgCode);
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOutfieldDao#queryOutfieldByCode(java.lang.String)
     */
    List<OutfieldEntity> queryOutfieldBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    List<OutfieldEntity> queryOutfieldExactByEntity(
	    OutfieldEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    long queryOutfieldExactByEntityCount(OutfieldEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    List<OutfieldEntity> queryOutfieldByEntity(
	    OutfieldEntity entity,int start, int limit);
	
    /**
     * 查询queryOutfieldByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:58:3
     */
    long queryOutfieldByEntityCount(OutfieldEntity entity);
		
    /**
     * 通过空运总调查外场
     * 
     * @author foss-zhangxiaohui
     * @date Jan 31, 2013 10:32:29 AM
     */
    String queryTransferCenterByAirDispatchCode(String airDispatchCode);
	
    /**
     * 
     * <p>通过所属的顶级车队编码查询外场实体列表</p> 
     * @author foss-zhujunyong
     * @date May 24, 2013 10:36:59 AM
     * @param motorcadeCode
     * @return
     * @see
     */
    List<OutfieldEntity> queryOutfieldByMotorcadeCode(String motorcadeCode);    
    
    /**
     * 
     * <p>通过空运总调组织编码查询外场实体</p> 
     * @author foss-zhujunyong
     * @date Mar 4, 2013 9:51:05 AM
     * @param airDispatchCode
     * @return
     * @see
     */
    OutfieldEntity queryOutfieldEntityByAirDispatchCode(String airDispatchCode);

    /**
     * 
     * <p>查询所有外场组织编码列表</p> 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 2:50:32 PM
     * @return
     * @see
     */
    List<String> queryActiveOrgCodeList();
    
    /**
     * 不走缓存，只查询实体
     *273296
     * @param orCode
     * @return
     */
	OutfieldEntity querySimpleOutfieldByOrgCodeNCahe(String orCode);
    
}
