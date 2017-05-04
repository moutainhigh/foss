/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirTrackFlightDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTrackFlightDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTrackFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto;

/**
 * 空运跟踪DAO
 * @author 038300-foss-pengzhen
 * @date 2012-11-21 下午4:09:09
 */
public class AirTrackFlightDao extends iBatis3DaoImpl implements IAirTrackFlightDao {
	/**
	 * 定义mybatis命名空间
	 */
	private static final String NAMESPACE = "foss.airfreight.trackFlight.";
	/**
	 * 根据条件查询航空正单
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-17 下午5:32:29
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTrackFlightDao#queryAirTrackFlight(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	//查询航空跟踪list定义
	public List<AirTrackFlightDto> queryAirTrackFlight(AirTrackFlightDto dto,
			int start, int limit) {
		//列限制
		//两个参数确定
		RowBounds rowBounds = new RowBounds(start, limit);
		//调用mybatis获取查询结果
		return this.getSqlSession().selectList(NAMESPACE + "selectTrackFlight", dto, rowBounds);
	}
	/**
	 * 新增跟踪信息： 起飞跟踪、过程跟踪、到达跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-20 下午7:47:52
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTrackFlightDao#takeOffTrack(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto)
	 */
	@Override
	public void addAirTrackFlight(List<AirTrackFlightEntity> trackEntityList ) {
		//sql跟踪信息插入
		for(AirTrackFlightEntity airTrackFlightEntity : trackEntityList){
			this.getSqlSession().insert(NAMESPACE + "insertAirTrackFlight", airTrackFlightEntity);
		}
	}
	/** 
	 * 统计查询结果记录总数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 下午4:09:49
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTrackFlightDao#queryTrackFlightCount()
	 */
	@Override
	public Long queryTrackFlightCount(AirTrackFlightDto dto) {
		//返回查询结果总数，定义返回类型为long
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "selectTrackFlightCount", dto);
	}

	/**
	 * 提供给综合查询的接口，根据正单号查询跟踪信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-23 下午2:52:30
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirTrackFlightDto> queryTrackInfoByAirWaybillNo(String airWaybillNo){
		return this.getSqlSession().selectList(NAMESPACE + "queryTrackInfoByAirWaybillNo", airWaybillNo);
	}
	
}