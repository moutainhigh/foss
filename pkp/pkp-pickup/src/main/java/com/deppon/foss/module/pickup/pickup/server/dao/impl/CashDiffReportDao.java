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
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/dao/impl/CashDiffReportDao.java
 * 
 * FILE NAME        	: CashDiffReportDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pickup.api.server.dao.ICashDiffReportDao;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.CashDiffReportDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtCashDiffReportDto;

/**
 * 现金差异报表Dao
 * @author admin
 *
 */
@SuppressWarnings("unchecked")
public class CashDiffReportDao extends iBatis3DaoImpl implements ICashDiffReportDao {

	//命名空间
	private static final String NAMESPACE = 
			"com.deppon.foss.module.pickup.pickup.api.shared.dto.RtCashDiffReportDto.";
	
	//现金差异报表sql
	private static final String QUERYCSHDIFFREPORT="queryCashDiffReport";
	
	//现金差异报表sql
	private static final String QUERYCSHDIFFREPORTTOTAL="queryCashDiffReportTotal";
		
		
	/**
	 * 现金差异报表query
	 */
	public List<RtCashDiffReportDto> queryCashDiffReport(CashDiffReportDto dto,int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+QUERYCSHDIFFREPORT, dto,rowBounds);
	}
	
	/**
	 * 现金差异报表query
	 */
	public List<RtCashDiffReportDto> queryCashDiffReport(CashDiffReportDto dto) {

		return this.getSqlSession().selectList(NAMESPACE+QUERYCSHDIFFREPORT, dto);
	}
	
	/**
	 * 现金差异报表query
	 */
	public Long queryCashDiffReportTotal(CashDiffReportDto dto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+QUERYCSHDIFFREPORTTOTAL, dto);
	}

}