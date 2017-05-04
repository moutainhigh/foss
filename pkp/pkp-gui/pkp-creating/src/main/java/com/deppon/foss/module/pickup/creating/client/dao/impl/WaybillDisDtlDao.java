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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillDisDtlDao.java
 * 
 * FILE NAME        	: WaybillDisDtlDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillDisDtlDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 运单折扣明细数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Bobby,date:2012-10-17 下午7:28:27,
 * </p>
 * 
 * @author Bobby
 * @date 2012-10-17 下午7:28:27
 * @since
 * @version
 */
public class WaybillDisDtlDao implements IWaybillDisDtlDao {

	private static final String NAMESPACE = "foss.pkp.WaybillDisDtlEntityMapper.";

	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int insert(WaybillDisDtlEntity record) {
		return this.sqlSession.insert(NAMESPACE + "insert", record);
	}

	/**
	 * 批量插入对象数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:11:23
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao#addBatch(java.util.List)
	 */
	public void addBatch(List<WaybillDisDtlEntity> waybillDisDtl) {
		for (WaybillDisDtlEntity entity : waybillDisDtl) {
			// 设置UUID
			entity.setId(UUIDUtils.getUUID());
			this.sqlSession.insert(NAMESPACE + "insertSelective", entity);
		}
	}

	@Override
	public WaybillDisDtlEntity queryByPrimaryKey(String id) {
		return (WaybillDisDtlEntity) this.sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 根据运单号查询折扣明细列表
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午5:38:57
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao#queryDisDtlEntityByNo(java.lang.String)
	 */
	@Override
	public List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo) {
		// 封装参数
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);

		List<WaybillDisDtlEntity> list = this.sqlSession.selectList(NAMESPACE + "selectEntityListByNo", args);
		return list;
	}

	@Override
	public int updateByPrimaryKeySelective(WaybillDisDtlEntity record) {
		return this.sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", record);
	}

	/**
	 * 
	 * <p>
	 * 运单折扣明细<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param waybillNo
	 * @return List<WaybillDisDtlEntity>
	 */
	@Override
	public List<WaybillDisDtlEntity> queryNewDisDtlEntityByNo(LastWaybillRfcQueryDto queryDto) {
		return this.sqlSession.selectList(NAMESPACE + "queryNewDisDtlEntityByNo", queryDto);
	}

	@Override
	public int deleteWaybillDisDtlEntityById(String id) {
		return this.sqlSession.delete(NAMESPACE + "deleteByPrimaryKey", id);
	}

}