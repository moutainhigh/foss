/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/DiscountPriorityDao.java
 * 
 * FILE NAME        	: DiscountPriorityDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountPriorityDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;

/**
 * 对折扣优先级的操作类
 * @author sz
 */
public class DiscountPriorityDao extends SqlSessionDaoSupport implements IDiscountPriorityDao {

	private static final String NAME_SPACE = "com.deppon.foss.module.pickup.pricing.api.server.dao.DiscountPriorityMapper.";
	/**
	 * @Description: 根据主键ID删除数据
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:42:36
	 * @param id
	 * @return
	 * @version V1.0
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return getSqlSession().delete(NAME_SPACE+"deleteByPrimaryKey", id);
	}
	/**
     * @Description: 插入记录
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-17 上午11:42:56
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public int insertSelective(DiscountPriorityEntity record) {
		return getSqlSession().insert(NAME_SPACE+"insertSelective", record);
	}
	/**
     * @Description: 根据主键ID查询ID
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-17 上午11:43:07
     * @param id
     * @return
     * @version V1.0
     */
	@Override
	public DiscountPriorityEntity selectByPrimaryKey(String id) {
		return (DiscountPriorityEntity)getSqlSession().selectOne(NAME_SPACE+"selectByPrimaryKey", id);
	}
	
	/**
     * @Description: 根据CODE查询优先级集合
     * @author FOSSDP-sz
     * @date 2013-2-18 下午3:26:25
     * @param record
     * @return
     * @version V1.0
     */
	@SuppressWarnings("unchecked")
	@Override
    public List<DiscountPriorityEntity> selectByCode(String code) {
    	return getSqlSession().selectList(NAME_SPACE+"selectByCode", code);
    }
	/**
     * @Description: 根据主键ID修改记录
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-17 上午11:43:18
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public int updateByPrimaryKeySelective(DiscountPriorityEntity record) {
		return getSqlSession().update(NAME_SPACE+"updateByPrimaryKeySelective", record);
	}
	/**
     * @Description: 根据条件查询优先级集合(分页)
     * Company:IBM
     * @author IBMDP-Administrator
     * @date 2012-12-17 上午11:43:28
     * @param record
     * @param start
     * @param limit
     * @return
     * @version V1.0
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountPriorityEntity> selectPagingByCondition(DiscountPriorityEntity record, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAME_SPACE+"selectByCondition", record, rowBounds);
	}
	/**
     * @Description: 根据条件查询记录总数(分页)
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-17 上午11:43:57
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public Integer countPagingByCondition(DiscountPriorityEntity record) {
		return (Integer)getSqlSession().selectOne(NAME_SPACE+"countByCondition", record);
	}
	/**
     * @Description: 根据条件查询优先级集合
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-17 上午11:43:28
     * @param record
     * @param start
     * @param limit
     * @return
     * @version V1.0
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountPriorityEntity> selectByCondition(DiscountPriorityEntity record) {
		return getSqlSession().selectList(NAME_SPACE+"selectByCondition");
	}
	/**
     * @Description: 查询最末优先级级数
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-17 上午11:43:57
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public Integer selectMaxLevel() {
		return (Integer)getSqlSession().selectOne(NAME_SPACE+"selectMaxLevel");
	}

}