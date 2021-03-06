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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IMotorcadeService.java
 * 
 * FILE NAME        	: IMotorcadeService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
/**
 * 车队 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:33:23
 */
public interface IMotorcadeService extends IService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    MotorcadeEntity addMotorcade(MotorcadeEntity entity);
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    MotorcadeEntity deleteMotorcade(MotorcadeEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
//    MotorcadeEntity deleteMotorcadeMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    MotorcadeEntity updateMotorcade(MotorcadeEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    MotorcadeEntity queryMotorcadeByCode(String code);	
	
    /**
     * 
     * <p>根据编码查询基本属性</p> 
     * @author foss-zhujunyong
     * @date Mar 1, 2013 4:40:30 PM
     * @param code
     * @return
     * @see
     */
    MotorcadeEntity queryMotorcadeByCodeClean(String code);	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#queryMotorcadeByCode(java.lang.String)
     */
    List<MotorcadeEntity> queryMotorcadeBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    List<MotorcadeEntity> queryMotorcadeExactByEntity(MotorcadeEntity entity, int start, int limit) ;

    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    List<MotorcadeEntity> querySimpleMotorcadeExactByEntity(MotorcadeEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    long queryMotorcadeExactByEntityCount(MotorcadeEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    List<MotorcadeEntity> queryMotorcadeByEntity(
	    MotorcadeEntity entity,int start, int limit);
	
    /**
     * 查询queryMotorcadeByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:33:23
     */
    long queryMotorcadeByEntityCount(MotorcadeEntity entity);
		
    /**
     * 
     * <p>查找所有顶级车队</p> 
     * @author foss-zhujunyong
     * @date Apr 11, 2013 3:20:05 PM
     * @return
     * @see
     */
    List<MotorcadeEntity> queryTopFleetList(String key);
    
   /**
    * 根据外场编号查询顶级车队信息
    */
    MapDto queryTopFleetByTransferCenterCode(String code);
    
    /**
     * 根据编码查询车队
     * 
     * @author 332219-foss
     * @date 2017-03-22
     */
    MotorcadeEntity queryTransMotorcadeByCode(String code);	
}
