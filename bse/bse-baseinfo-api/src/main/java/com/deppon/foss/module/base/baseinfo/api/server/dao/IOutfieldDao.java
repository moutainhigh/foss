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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IOutfieldDao.java
 * 
 * FILE NAME        	: IOutfieldDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
/**
 * 外场 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:56:36
 */
public interface IOutfieldDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    OutfieldEntity addOutfield(OutfieldEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    OutfieldEntity deleteOutfield(OutfieldEntity entity);

    /**
     * 根据CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    OutfieldEntity deleteOutfieldMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    OutfieldEntity updateOutfield(OutfieldEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    OutfieldEntity queryOutfieldByCode(String code);	
	

    /**
     * 通过 标识编码ORG_CODE,是否有效ACTIVE精确查询
     * 
     * 两个参数都可传空，当传空时，不做为查询条件，查询时，取更新时间最近的一条
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @param code 标识编码（组织编码）数组
     * @param active FossConstants.YES:FossConstants.NO
     */
    List<OutfieldEntity> queryOutfieldByOrgCodeActive(List<String> codes,
	    String active);
	
    /**
     * 精确查询
     * 根据多个 ORG_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     * @see com.deppon.foss.module.base.dict.api.server.dao.IOutfieldDao#queryOutfieldByCode(java.lang.String)
     */
    List<OutfieldEntity> queryOutfieldBatchByOrgCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    List<OutfieldEntity> queryOutfieldExactByEntity(
	    OutfieldEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    long queryOutfieldExactByEntityCount(OutfieldEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    List<OutfieldEntity> queryOutfieldByEntity(OutfieldEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryOutfieldByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:56:36
     */
    long queryOutfieldByEntityCount(OutfieldEntity entity);
		
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:1:42
     */
    List<OutfieldEntity> queryOutfieldForDownload(OutfieldEntity entity);
    
    /**
     * 精确查询
     * 根据orgCode 查询
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-11-12 下午2:56:36
     */
    OutfieldEntity queryOutfieldByOrgCode(String orgCode);
		
    /**
     * 通过空运总调查外场
     * 
     * @author foss-zhangxiaohui
     * @date Jan 31, 2013 10:46:29 AM
     */
    String queryTransferCenterByAirDispatchCode(String airDispatchCode);
    
    /**
     * 
     * <p>通过所属的顶级车队编码查询外场实体列表</p> 
     * @author foss-zhujunyong
     * @date May 24, 2013 10:35:24 AM
     * @param motorcadeCode
     * @return
     * @see
     */
    List<OutfieldEntity> queryOutfieldByMotorcadeCode(String motorcadeCode);
    
    /**
     * 
     * <p>查询所有外场组织编码列表</p> 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 2:50:32 PM
     * @return
     * @see
     */
    List<String> queryActiveOrgCodeList();
}
