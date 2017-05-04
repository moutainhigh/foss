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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IMotorcadeServeSalesAreaService.java
 * 
 * FILE NAME        	: IMotorcadeServeSalesAreaService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;
/**
 * 车队负责的营业区域 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-17 下午8:58:24
 */
public interface IMotorcadeServeSalesAreaService extends IService {
    /**
     * 插入 车队负责的营业区域,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    MotorcadeServeSalesAreaEntity addMotorcadeServeSalesArea(MotorcadeServeSalesAreaEntity entity);
	
    /**
     * 根据CODE删除 车队负责的营业区域,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    MotorcadeServeSalesAreaEntity deleteMotorcadeServeSalesArea(MotorcadeServeSalesAreaEntity entity);
	
    /**
     * 根据CODE批量删除 车队负责的营业区域,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    MotorcadeServeSalesAreaEntity deleteMotorcadeServeSalesAreaMore(String[] codes , String deleteUser);
	
    /**
     * 更新  车队负责的营业区域,
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    MotorcadeServeSalesAreaEntity updateMotorcadeServeSalesArea(MotorcadeServeSalesAreaEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询 车队负责的营业区域,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    MotorcadeServeSalesAreaEntity queryMotorcadeServeSalesAreaByVirtualCode(String code);	
	
	
    /**
     * 精确查询 车队负责的营业区域,
    
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaDao#queryMotorcadeServeSalesAreaByVirtualCode(java.lang.String)
     */
    List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询 车队负责的营业区域,
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaExactByEntity(
	    MotorcadeServeSalesAreaEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    long queryMotorcadeServeSalesAreaExactByEntityCount(MotorcadeServeSalesAreaEntity entity);
	
    /**
     * 精确查询 车队负责的营业区域,返回所有的结果，
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaExactByEntityAll(
	    MotorcadeServeSalesAreaEntity entity) ;
    
    /**
     * 根据entity模糊查询 车队负责的营业区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaByEntity(
	    MotorcadeServeSalesAreaEntity entity,int start, int limit);
	
    /**
     * 查询queryMotorcadeServeSalesAreaByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    long queryMotorcadeServeSalesAreaByEntityCount(MotorcadeServeSalesAreaEntity entity);
    
    /**
     * 根据entity模糊查询 车队负责的营业区域,返回所有的结果，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:58:24
     */
    List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaByEntityAll(
	    MotorcadeServeSalesAreaEntity entity);

    
    
    /**
     * 下面是特殊方法
     */
     
    
    /**
     * 通过MotorcadeCode车队编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */	
    MotorcadeServeSalesAreaEntity deleteMotorcadeServeSalesAreaByMotorcadeCode(MotorcadeServeSalesAreaEntity entity);
    	
    /**
     * 批量插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    List<MotorcadeServeSalesAreaEntity> addMotorcadeServeSalesAreaMore(List<MotorcadeServeSalesAreaEntity> entitys);
    
}
