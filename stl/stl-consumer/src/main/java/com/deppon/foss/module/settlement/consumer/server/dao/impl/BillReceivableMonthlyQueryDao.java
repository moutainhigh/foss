/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/dao/impl/AirBillPaidCODQueryDao.java
 * 
 * FILE NAME        	: AirBillPaidCODQueryDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivableMonthlyQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

/**
 * 查询应收单月结
 * @author foss-youkun
 * @date 2016/1/8
 */
public class BillReceivableMonthlyQueryDao extends iBatis3DaoImpl implements IBillReceivableMonthlyQueryDao {

	private static final String NAMESPACE = "foss.stl.BillReceivableMonthlyQueryDao.";


	/**
	 * 查询月结应收单
	 * @param limit
	 * @param start
	 * @param billReceivableDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryBillReceivableByData(int start, int limit, BillReceivableDto billReceivableDto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(start,limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryBillReceivableByParam",billReceivableDto,rb);
	}

	/**
	 * 查询应收单月结的总记录数
	 * @param billReceivableDto
	 * @return
	 */
	public Long countBillReceivableByData(BillReceivableDto billReceivableDto) {
		Long count = (Long)this.getSqlSession().selectOne(NAMESPACE + "countBillReceivableByParam",billReceivableDto);
		return  count;
	}

	/**
	 * 查询月结应收单的总金额
	 * @param billReceivableDto
	 * @return
	 */
	public String amountBillReceivableByParam(BillReceivableDto billReceivableDto) {
		return this.getSqlSession().selectOne(NAMESPACE + "amountBillReceivableByParam",billReceivableDto).toString();
	}

	/**
	 * 查询月结的委托派费和始发提成应收单
	 * @param dto
	 * @return
	 */
	public List<BillReceivableEntity> queryBillRecivableByList(BillReceivableDto dto,int start ,int limit) {
		//分页设置
		//RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryBillRecivableByList",dto);
	}

	/**
	 * 查询月结的委托派费和始发提成应收单（不分页）
	 * @param dto
	 * @return
	 */
	public List<BillReceivableEntity> queryNotPageBillRecivableByList(BillReceivableDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryBillRecivableByList",dto);
	}

	/**
	 * 查询月结的委托派费和始发提成应收单总记录数
	 * @param dto
	 * @return
	 */
	public Long countBillRecivableByList(BillReceivableDto dto) {
		Long count = (Long)this.getSqlSession().selectOne(NAMESPACE + "countBillRecivableByList",dto);
		return  count;
	}
	/** 
	 * <p>查询所有的月结应收单</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午10:22:24
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivableMonthlyQueryDao#queryBillReceivableByData(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillReceivableByData(
			BillReceivableDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryBillReceivableJob",dto);
	}

	/** 
	 * <p>插入合伙人月结应收账单数据</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午10:55:02
	 * @param list 
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivableMonthlyQueryDao#insertBillReceivable(java.util.List)
	 */
	@Override
	public void insertBillReceivable(List<BillReceivableEntity> list) {
		this.getSqlSession().insert(NAMESPACE + "insertBillReceivable", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillReceivableEntityListForExport(
			BillReceivableDto billReceivableDto) {
		return getSqlSession().selectList(NAMESPACE + "queryBillReceivableByParam", billReceivableDto);
	}
}
