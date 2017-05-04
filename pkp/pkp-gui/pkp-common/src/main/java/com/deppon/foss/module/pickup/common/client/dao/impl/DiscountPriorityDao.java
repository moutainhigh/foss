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
package com.deppon.foss.module.pickup.common.client.dao.impl;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import com.deppon.foss.module.pickup.common.client.dao.IDiscountPriorityDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;

/**
 * 折扣优先级Dao

  * @ClassName: DiscountPriorityDao

  * @Description: 20131012下载离线数据 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-12 
  
  *
 */
public class DiscountPriorityDao implements IDiscountPriorityDao{

	private static final String NAME_SPACE = "com.deppon.foss.module.pickup.pricing.api.server.dao.DiscountPriorityMapper.";
	
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	  * Description: 根据主键ID删除数据
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-12
	  * @param id
	  * @return
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return sqlSession.delete(NAME_SPACE+"deleteByPrimaryKey", id);
	}
	
	/**
	  * Description: 新增
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-12 
	  * @param priceRegioOrgnEntity
	  * @return
	 */
	@Override
	public boolean insertSelective(DiscountPriorityEntity record) {
		DiscountPriorityEntity entity = selectByPrimaryKey(record.getId());
		if(entity != null){
			return false;
		}else{
			sqlSession.insert(NAME_SPACE+"insertSelective", record);
			return true;
		}
	}
	
	/**
	  * Description: 查询
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-12
	  * @param id
	  * @return
	 */
	@Override
	public DiscountPriorityEntity selectByPrimaryKey(String id) {
		return (DiscountPriorityEntity)sqlSession.selectOne(NAME_SPACE+"selectByPrimaryKey", id);
	}
	
	 /**
     * @Description: 根据主键ID修改记录
     * @author deppon-157229-zxy
     * @version 1.0 2013-10-12
     * @param record
     * @return
     */
	@Override
	public int updateByPrimaryKeySelective(DiscountPriorityEntity record) {
		return sqlSession.update(NAME_SPACE+"updateByPrimaryKeySelective", record);
	}

}