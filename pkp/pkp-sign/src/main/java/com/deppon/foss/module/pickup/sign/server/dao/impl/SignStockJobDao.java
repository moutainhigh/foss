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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/StayHandoverDao.java
 * 
 * FILE NAME        	: StayHandoverDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;


import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignStockDto;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 *签收反签收同步改异步库存接口实现
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-22 下午4:23:40
 * @since
 * @version
 */
public class SignStockJobDao extends iBatis3DaoImpl implements ISignStockJobDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity.";
	/**
	 * 添加一条记录
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:30:00
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao#insert(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity)
	 */
	@Override
	public int insert(SignStockEntity record) {
		//主键id
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insert",record);
	}
	/**
	 * 有选择性的添加数据
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:30:07
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao#insertSelective(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity)
	 */
	@Override
	public int insertSelective(SignStockEntity record) {
		//主键id
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insertSelective",record);
	}
	/**
	 * 修改根据相应的条件  
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:30:12
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao#updateByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.SignStockDto)
	 */
	@Override
	public int updateByCondition(SignStockDto record) {
		return this.getSqlSession().update(NAMESPACE+"updateByCondition",record);
	}
	/**
	 * 查询根据相应的条件
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:30:16
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao#queryByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.SignStockDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignStockEntity> queryByCondition(SignStockDto record) {
		return (List<SignStockEntity>)this.getSqlSession().selectList(NAMESPACE+"selectByExample",record);
	}
	/**
	 * 根据主键id删除满足条件的记录
	 * @author foss-meiying
	 * @date 2013-3-22 下午4:30:20
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao#deleteById(java.lang.String)
	 */
	@Override
	public int deleteById(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteById",id);
	}
	/**
	 * 根据状态为-待执行 出入库类型 查询是否有满足的记录数
	 * @author foss-meiying
	 * @date 2013-3-22 下午5:55:20
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao#queryCountbyStatus(java.lang.String)
	 */
	@Override
	public Integer queryCountbyCondition(SignStockEntity record) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"selectCountByStatus",record);
	}
	/**
	 *  根据id修改记录
	 * @author foss-meiying
	 * @date 2013-3-22 下午5:55:24
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao#updateById(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity)
	 */
	@Override
	public int updateById(SignStockEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateById",record);
	}
	
	

}