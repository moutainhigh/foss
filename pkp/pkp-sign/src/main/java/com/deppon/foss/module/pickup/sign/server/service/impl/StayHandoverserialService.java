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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/StayHandoverserialService.java
 * 
 * FILE NAME        	: StayHandoverserialService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverserialDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto;
import com.deppon.foss.util.define.FossConstants;
/***
 * 交接 流水号service实现
 * @author foss-meiying
 * @date 2012-11-28 上午11:56:12
 * @since
 * @version
 */
public class StayHandoverserialService  implements IStayHandoverserialService {
	/**
	 * 交接流水号Dao
	 */
	private IStayHandoverserialDao stayHandoverserialDao;
	/**
	 * 删除交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:48:14
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteByPrimaryKey(String id) {
		return stayHandoverserialDao.deleteByPrimaryKey(id);
	}
	/**
	 * 添加交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:49:19
	 * @param record
	 * @return
	 * @see
	 */
	@Override
	@Transactional
	public StayHandoverserialEntity add(StayHandoverserialEntity record) {
		return stayHandoverserialDao.add(record);
	}
	/**
	 * 添加交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:50:43
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService#insertSelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity)
	 */
	@Override
	@Transactional
	public int addSelective(StayHandoverserialEntity record) {
		return stayHandoverserialDao.addSelective(record);
	}
	/**
	 * 根据主键查询交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:50:57
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public StayHandoverserialEntity selectByPrimaryKey(String id) {
		return stayHandoverserialDao.selectByPrimaryKey(id);
	}
	/**
	 * 有选择性的修改交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:51:36
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity)
	 */
	@Override
	@Transactional
	public int updateByPrimaryKeySelective(StayHandoverserialEntity record) {
		return stayHandoverserialDao.updateByPrimaryKeySelective(record);
	}
	/**
	 * 修改交接流水号信息
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:52:04
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService#updateByPrimaryKey(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity)
	 */
	@Override
	@Transactional
	public int updateByPrimaryKey(StayHandoverserialEntity record) {
		return stayHandoverserialDao.updateByPrimaryKey(record);
	}

	/**
	 * 根据运单号查询labeled_good_pda里的流水号集合
	 * @author foss-meiying
	 * @date 2013-1-22 下午2:47:06
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService#querySerialNosByWaybillNo(java.lang.String)
	 */
	@Override
	public List<StayHandoverserialEntity> querySerialNosByWaybillNo(String waybillNo) {
		StayHandoverDetailDto dto = new StayHandoverDetailDto();
		//运单号
		dto.setWaybillNo(waybillNo);
		//有效
		dto.setActive(FossConstants.YES);
		return stayHandoverserialDao.querySerialNosByWaybillNo(dto);
	}
	/**
	 * 批量保存交接流水号集合
	 * @author foss-meiying
	 * @date 2013-3-9 下午5:34:07
	 * @param list
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService#addStayHandoverserialList(java.util.List)
	 */
	@Override
	public int addStayHandoverserialList(List<StayHandoverserialEntity> list) {
		return stayHandoverserialDao.addStayHandoverserialList(list);
	}
	
	/**
	 * Gets the 交接流水号Dao.
	 *
	 * @return the 交接流水号Dao
	 */
	public IStayHandoverserialDao getStayHandoverserialDao() {
		return stayHandoverserialDao;
	}

	/**
	 * Sets the 交接流水号Dao.
	 *
	 * @param stayHandoverserialDao the new 交接流水号Dao
	 */
	public void setStayHandoverserialDao(IStayHandoverserialDao stayHandoverserialDao) {
		this.stayHandoverserialDao = stayHandoverserialDao;
	}
	/**
	 * 根据交接明细id 流水号集合删除满足条件的信息
	 * @author foss-meiying
	 * @date 2013-3-17 下午5:18:11
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService#deleteByStayHandoverIdAndSerialNos(com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto)
	 */
	@Override
	public int deleteByStayHandoverIdAndSerialNos(PdaDto dto) {
		return stayHandoverserialDao.deleteByStayHandoverIdAndSerialNos(dto);
	}
	
}