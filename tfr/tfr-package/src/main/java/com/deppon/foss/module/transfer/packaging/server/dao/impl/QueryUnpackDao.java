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
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/dao/impl/QueryUnpackDao.java
 *  
 *  FILE NAME          :QueryUnpackDao.java
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
package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryUnpackDao;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackResultEntity;


/**
 * 
 * 查询营业部代打包装的DAO层
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 上午8:08:39
 */
public class QueryUnpackDao extends iBatis3DaoImpl implements IQueryUnpackDao {
	
	/**
	 * 
	 * 查询未包装的需要包装的货物信息
	 * @param queryUnpackConditionEntity 查询条件
	 * @param limit 限制数
	 * @param start 开始数
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 上午8:17:53
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryUnpackDao#queryUnpackALL(com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackConditionEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryUnpackResultEntity> queryUnpackALL(QueryUnpackConditionEntity queryUnpackConditionEntity,
			int limit, int start) {
		//封装分页数据
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectUnpack", 
				queryUnpackConditionEntity,rowBounds);
	}
	
	/**
	 * 
	 * 查询每票货物的库存及包装信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 上午8:18:43
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryUnpackDao#queryUnpackDetails(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryUnpackDetailsEntity> queryUnpackDetails(QueryUnpackConditionEntity queryUnpackConditionEntity) {
		
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectUnpackDetails", 
				queryUnpackConditionEntity);
	}
	
	/**
	 * 
	 * 查询未包装货物总数
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 上午9:31:02
	 */
	@Override
	public Long queryTotalCount(
			QueryUnpackConditionEntity queryUnpackConditionEntity) {
		
		return Long.parseLong(getSqlSession().selectOne(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectUnpackCount", 
				queryUnpackConditionEntity).toString());
	}
	/**
	 * 查询未包装的需要包装的货物信息
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-22 上午8:17:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryUnpackResultEntity> queryUnpackALL(
			QueryUnpackConditionEntity queryUnpackConditionEntity) {
		//返回符合条件的集合
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectUnpack", 
				queryUnpackConditionEntity);
	}
	
	

}