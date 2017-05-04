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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillChargeDtlDao.java
 * 
 * FILE NAME        	: WaybillChargeDtlDao.java
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

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillChargeDtlDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 运单费用明细数据持久层
 * 
 * @author Bobby
 * @date 2012-10-17 下午7:27:34
 * @since
 * @version
 */
/**
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title WaybillChargeDtlDao.java
 * @package com.deppon.foss.module.pickup.waybill.server.dao.impl
 * @author suyujun
 * @version 0.1 2012-11-30
 */
public class WaybillChargeDtlDao implements IWaybillChargeDtlDao {

	private static final String NAMESPACE = "foss.pkp.WaybillChargeDtlEntityMapper.";

	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 新增其它费用分录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午7:48:58
	 */
	@Override
	public int insert(WaybillChargeDtlEntity record) {
		return this.sqlSession.insert(NAMESPACE + "insert", record);
	}

	/**
	 * 批量插入实体数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:44:03
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillChargeDtlDao#addBatch(java.util.List)
	 */
	public void addBatch(List<WaybillChargeDtlEntity> list) {
		for (WaybillChargeDtlEntity entity : list) {
			// 设置UUID
			entity.setId(UUIDUtils.getUUID());
			this.sqlSession.insert(NAMESPACE + "insertSelective", entity);
		}

	}

	/**
	 * 根据主键查询实体对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午7:49:13
	 */
	@Override
	public WaybillChargeDtlEntity queryByPrimaryKey(String id) {
		return (WaybillChargeDtlEntity) this.sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 根据运单号查询对应费用明细实体List
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午2:56:18
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillChargeDtlDao#queryChargeDtlEntityByNo(java.lang.String)
	 */
	@Override
	public List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo) {
		// 封装参数
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);
		List<WaybillChargeDtlEntity> list = this.sqlSession.selectList(NAMESPACE + "selectEntityListByNo", args);
		return list;
	}

	/**
	 * 根据主键更新费用信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午7:49:32
	 */
	@Override
	public int updateByPrimaryKeySelective(WaybillChargeDtlEntity record) {
		return this.sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", record);
	}

	/**
	 * 查询新版费用明细列表
	 * 
	 * @author suyujun
	 * @date 2012-12-18 下午7:56:02
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillChargeDtlDao#queryNewChargeDtlEntityByNo(com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto)
	 */
	@Override
	public List<WaybillChargeDtlEntity> queryNewChargeDtlEntityByNo(LastWaybillRfcQueryDto queryDto) {
		return this.sqlSession.selectList(NAMESPACE + "queryNewChargeDtlEntityByNo", queryDto);
	}

	/**
	 * 删除费用
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillChargeDtlDao#deleteWaybillChargeDtlEntityById(java.lang.String)
	 */
	@Override
	public int deleteWaybillChargeDtlEntityById(String id) {
		return this.sqlSession.delete(NAMESPACE + "deleteByPrimaryKey", id);
	}

}