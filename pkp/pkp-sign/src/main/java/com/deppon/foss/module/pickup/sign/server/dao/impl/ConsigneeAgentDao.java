/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/ConsigneeAgentDao.java
 * 
 * FILE NAME        	: ConsigneeAgentDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IConsigneeAgentDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ConsigneeAgentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchWaybillSignByOtherDto;

/**
 * 凭证操作Dao类
 * 
 * @author xwshi
 */
public class ConsigneeAgentDao extends iBatis3DaoImpl implements IConsigneeAgentDao {

	//凭证name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.ConsigneeAgentEntity.";

	//插入数据库sql
	private static final String INSERT = "insertSelective";

	//根据id删除凭证sql
	private static final String DELETE = "deleteByPrimaryKey";

	//根据id查询凭证
	private static final String SELECTBYPRIMARYKEY = "selectByPrimaryKey";

	//更新数据库
	private static final String UPDATENOTNULL = "updateByPrimaryKeySelective";

	//更新数据库
	private static final String UPDATE = "updateByPrimaryKey";

	//查询是否有库存
	private static final String SELECTEXISTINSTOCK = "selectExistInStock";

	//查询是否运单存在 并且到达 并且入库
	private static final String SELECTWAYBILLEXISTINSTOCK = "selectWaybillExistInStock";

	/**
	 * 根据id删除凭证
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().insert(NAMESPACE + DELETE, id);
	}

	/**
	 * 加入数据库凭证纪录
	 * 
	 * @date 2012-11-26 下午7:57:00
	 */
	public int insert(ConsigneeAgentEntity record) {
		return this.getSqlSession().insert(NAMESPACE + INSERT, record);

	}

	/**
	 * 选择性加入数据库凭证纪录
	 * 
	 * @date 2012-11-26 下午7:57:00
	 */
	public int insertSelective(ConsigneeAgentEntity record) {
		return this.getSqlSession().insert(NAMESPACE + INSERT, record);
	}

	/**
	 * 根据id查询凭证
	 * 
	 * @param id
	 * @return
	 */
	public ConsigneeAgentEntity selectByPrimaryKey(String id) {
		return (ConsigneeAgentEntity) this.getSqlSession().selectOne(NAMESPACE + SELECTBYPRIMARYKEY, id);
	}

	/*
	 * 根据运单号查询凭证
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsigneeAgentEntity> selectByWayblillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "selectByWaybillNo", waybillNo);
	}

	/**
	 * 更新数据库
	 * 
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(ConsigneeAgentEntity record) {
		return this.getSqlSession().update(NAMESPACE + UPDATENOTNULL, record);
	}

	/**
	 * 更新数据库
	 * 
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKey(ConsigneeAgentEntity record) {
		return this.getSqlSession().update(NAMESPACE + UPDATE, record);
	}

	/**
	 * 查询是否有库存
	 * 
	 * @param waybillNo
	 * @return
	 */
	public long selectExistInStock(String waybillNo, String endStockOrgCode, String goodsAreaCode,String goodsAreaCodeExpress) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("endStockOrgCode", endStockOrgCode);
		map.put("goodsAreaCode", goodsAreaCode);
		map.put("goodsAreaCodeExpress",goodsAreaCodeExpress);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + SELECTEXISTINSTOCK, map);
	}

	/**
	 * 查询是否运单存在 并且到达 并且入库
	 * 
	 * @param waybillNo
	 * @return
	 */
	public long selectWaybillExistInStock(SearchWaybillSignByOtherDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + SELECTWAYBILLEXISTINSTOCK, dto);
	}
}