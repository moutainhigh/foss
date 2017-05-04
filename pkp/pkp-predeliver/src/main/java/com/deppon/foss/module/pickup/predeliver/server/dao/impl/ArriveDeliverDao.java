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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/AbandonGoodsImportDao.java
 * 
 * FILE NAME        	: AbandonGoodsImportDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArriveDeliverDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverTotalDto;

/**
 * 到达派送dao
 * @author ibm-meiying
 * @date 2013-06-25 上午11:02:07
 */
public class ArriveDeliverDao extends iBatis3DaoImpl implements IArriveDeliverDao {

	// 命名空间
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverQueryDto.";

	/**
	 * 根据条件查询到达派送信息
	 * @author ibm-meiying
	 * @date 2013-06-25 上午11:02:09
	 */
	@SuppressWarnings("unchecked")
	public List<ArriveDeliverDto> queryArriveDeliverByParams(ArriveDeliverQueryDto dto ,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<ArriveDeliverDto>) this.getSqlSession().selectList(NAMESPACE+"selectArriveDeliverListByParams", dto,rowBounds);

	}
	/**
	 * 根据条件查询到达派送信息汇总信息
	 * @author ibm-meiying
	 * @date 2013-06-25 上午11:02:09
	 */
	@Override
	public ArriveDeliverTotalDto queryArriveDeliverTotalByParams(
			ArriveDeliverQueryDto dto) {
		return (ArriveDeliverTotalDto) this.getSqlSession().selectOne(NAMESPACE+"selectArriveDeliverTotalByParams", dto);
	}

	 /**
	 * 根据条件查询到达派送信息合计
	 * 
	 * @author foss-meiying
	 * @date 2013-6-25 下午4:59:27
	 * @param dto
	 * @return
	 */
	@Override
	public 	 Long queryArriveDeliverCountByParams(ArriveDeliverQueryDto dto){
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "selectArriveDeliverCountByParams",dto);
	}
}