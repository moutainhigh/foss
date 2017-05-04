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
 * PROJECT NAME	: stl-closing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/dao/impl/MvrRfdEntityDao.java
 * 
 * FILE NAME        	: MvrRfdEntityDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpPscDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpPscEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpPscDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 *
 * 合伙人子公司月报表DAO
 *
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */
public class MvrPtpPscEntityDao extends iBatis3DaoImpl implements IMvrPtpPscDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stl.MvrPtpPscEntityDao.";// 命名空间路径

	/**
	 * 查询合伙人子公司月报表总条数
	 * @param dto
	 * @return
	 * @throws SettlementException
     */
	public Long queryMvrParentComCount(MvrPtpPscDto dto) {

		return (Long)this.getSqlSession().selectOne(NAMESPACES+"queryMvrParentComCount",dto);
	}

	/**
	 * 查询合伙人子公司月报表集合
	 * @param dto
	 * @return
	 * @throws SettlementException
     */
	public List<MvrPtpPscEntity> queryMvrPtpPscList(MvrPtpPscDto dto,int offset,int limit) {
		// 分页
		RowBounds rb = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(NAMESPACES+"queryMvrPtpPscList",dto,rb);
	}

	/**
	 * 导出合伙人子公司月报表
	 * @param dto
	 * @return
     */
	public List<MvrPtpPscEntity> exportMvrParentCom(MvrPtpPscDto dto) {
		return this.getSqlSession().selectList(NAMESPACES+"exportMvrParentCom",dto);
	}
}
