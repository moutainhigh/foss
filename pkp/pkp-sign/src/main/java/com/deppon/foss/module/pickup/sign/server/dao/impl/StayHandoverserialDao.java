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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/StayHandoverserialDao.java
 * 
 * FILE NAME        	: StayHandoverserialDao.java
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
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto;
import com.deppon.foss.util.UUIDUtils;
/**
 * 交接流水号Dao实现
 * @author foss-meiying
 * @date 2012-11-28 下午12:30:00
 * @since
 * @version
 */
public class StayHandoverserialDao extends iBatis3DaoImpl implements IStayHandoverserialDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity.";
	/**
	 * 根据id删除交接流水号信息
	 * @author foss-meiying
	 * @date 2013-3-11 上午11:19:53
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey",id);
	}
	/**
	 * 添加交接单流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:36:14
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#insert(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity)
	 */
	@Override
	public StayHandoverserialEntity add(StayHandoverserialEntity record) {
		//主键id
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insertSelective",record) > 0 ? record : null;
	}
	/**
	 * 有选择性的添加交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:37:01
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#insertSelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity)
	 */
	@Override
	public int addSelective(StayHandoverserialEntity record) {
		//主键id
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insertSelective",record);
			
	}
	/**
	 * 根据主键查询交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:38:07
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public StayHandoverserialEntity selectByPrimaryKey(String id) {
		return (StayHandoverserialEntity)this.getSqlSession().selectOne(NAMESPACE+"selectByPrimaryKey",id);
	}
	/**
	 * 有选择性的修改交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:40:01
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(StayHandoverserialEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective",record);
	}
	/**
	 * 修改交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:39:46
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#updateByPrimaryKey(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity)
	 */
	@Override
	public int updateByPrimaryKey(StayHandoverserialEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKey",record);
		
	}
	/**
	 * 根据运单号,active ='Y'查询labeled_good_pda里的流水号集合
	 * @author foss-meiying
	 * @date 2013-1-22 下午2:45:25
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#querySerialNosByWaybillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StayHandoverserialEntity> querySerialNosByWaybillNo(StayHandoverDetailDto dto) {
		return (List<StayHandoverserialEntity>)this.getSqlSession().selectList(NAMESPACE+"selectSerialNosByWaybillNo",dto);
	}
	/**
	 * 批量添加交接流水号集合
	 * @author foss-meiying
	 * @date 2013-3-9 下午5:35:50
	 * @param list
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#addStayHandoverserialList(java.util.List)
	 */
	@Override
	public int addStayHandoverserialList(List<StayHandoverserialEntity> list) {
		for (StayHandoverserialEntity stayHandoverserialEntity : list) {//循环交接流水号集合
			stayHandoverserialEntity.setId(UUIDUtils.getUUID());//主键
		}
		return this.getSqlSession().insert(NAMESPACE + "addStayHandoverserialList", list);
	}
	/**
	 * 根据交接明细id 流水号集合删除满足条件的信息
	 * @author foss-meiying
	 * @date 2013-3-17 下午5:17:46
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao#deleteByStayHandoverIdAndSerialNos(com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto)
	 */
	@Override
	public int deleteByStayHandoverIdAndSerialNos(PdaDto dto) {
		return this.getSqlSession().delete(NAMESPACE+"deleteByStayHandoverIdAndSerialNos",dto);
	}
	
}