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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/DiscountOrgDao.java
 * 
 * FILE NAME        	: DiscountOrgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDiscountOrgDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountOrgEntity;

/**
 * 对折扣适用起始目的组织网点的操作类(配合定价项目降价发券方案引用PKP)
 * @author 187862-dujunhui
 * @date 2014-11-11 下午2:26:33
 * @since
 * @version
 */
public class DiscountOrgDao extends SqlSessionDaoSupport implements IDiscountOrgDao {

	private static final String NAME_SPASE = "foss.bse.bse-baseinfo.DiscountOrgMapper.";
	/**
	 * 
	 * @Description: 根据主键删除
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:04:55
	 * @param id
	 * @return
	 * @version V1.0
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return getSqlSession().delete(NAME_SPASE+"deleteByPrimaryKey", id);
	}
	 
	/**
     * 
     * @Description: 插入数据
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:06:25
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public int insertSelective(DiscountOrgEntity record) {
		return getSqlSession().insert(NAME_SPASE+"insertSelective", record);
	}
	
	/**
     * 
     * @Description: 根据主键查询
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:06:45
     * @param id
     * @return
     * @version V1.0
     */
	@Override
	public DiscountOrgEntity selectByPrimaryKey(String id) {
		return (DiscountOrgEntity)getSqlSession().selectOne(NAME_SPASE+"selectByPrimaryKey", id);
	}
	
	/**
     * 
     * @Description: 根据主键修改
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:07:05
     * @param record
     * @return
     * @version V1.0
     */
	@Override
	public int updateByPrimaryKeySelective(DiscountOrgEntity record) {
		return getSqlSession().update(NAME_SPASE+"updateByPrimaryKeySelective", record);
	}
	/**
     * 
     * @Description: 根据计费规则删除
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:05:37
     * @param id
     * @return
     * @version V1.0
     */
	@Override
	public int deleteByPriceValuationId(String id) {
		return getSqlSession().delete(NAME_SPASE+"deleteByPriceValuationId", id);
	}
	/**
     * 
     * @Description: 根据条件查询
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:07:53
     * @param record
     * @return
     * @version V1.0
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountOrgEntity> selectByCondition(DiscountOrgEntity record) {
		return getSqlSession().selectList(NAME_SPASE+"selectByCondition", record);
	}
	

}