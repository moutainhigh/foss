/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/server/dao/IQueryUnpackDao.java
 *  
 *  FILE NAME          :IQueryUnpackDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackResultEntity;

/**
 * 查询营业部代打包装DAO层接口
 * @author 046130-foss-xuduowei
 * @date 2012-10-11 下午6:18:29
 */
public interface IQueryUnpackDao {
	/**
	 * 
	 * 查询未包装的需要包装的货物信息
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 上午8:09:54
	 */
	List<QueryUnpackResultEntity> 
		queryUnpackALL(QueryUnpackConditionEntity queryUnpackConditionEntity,int limit,int start);
	
	/**
	 * 
	 * 查询未包装的需要包装的货物信息，不分页，用于excel
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-22 上午8:09:54
	 */
	List<QueryUnpackResultEntity> 
		queryUnpackALL(QueryUnpackConditionEntity queryUnpackConditionEntity);
	
	/**
	 * 查询每票货物的库存及包装信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 上午8:10:08
	 */
	List<QueryUnpackDetailsEntity> queryUnpackDetails(QueryUnpackConditionEntity unpackCondition);
	/**
	 * 
	 * 查询未包装货物总数
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 上午9:31:02
	 */
	Long queryTotalCount(QueryUnpackConditionEntity queryUnpackConditionEntity);
}