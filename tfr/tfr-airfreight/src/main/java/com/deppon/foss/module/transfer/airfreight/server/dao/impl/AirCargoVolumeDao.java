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
 *  FILE PATH          :/AirCargoVolumeDao.java
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
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargoVolumeQueryEntity;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailVolumeEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeForExportDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDetailVolumeDto;

/**
 * 
 *  实现对空运货量查询操作
 * @author dp-liming
 * @date 2012-10-19 上午10:07:41
 */
@SuppressWarnings("unchecked")
public class AirCargoVolumeDao extends iBatis3DaoImpl implements IAirCargoVolumeDao {
	
	private static final String NAMESPACE = "foss.airfreight.aircargovolume.";
	/**
	 *   分页查询空运货量明细
	 * @author  dp-liming
	 * @date 2012-10-19 上午10:50:47
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao#queryAirCargoVolume(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto, int, int)
	 */
	@Override
	public List<AirCargoVolumeQueryEntity> queryAirCargoVolume(AirCargoVolumeDto airCargoVolumeDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"airCargoVolumeForquery", airCargoVolumeDto, rowBounds);
		
	}

	/**
	 *   查询空运货量明细
	 * @author  dp-liming
	 * @date 2012-10-19 上午11:00:47
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao#queryAirCargoVolume(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto)
	 */
	@Override
	public List<AirCargoVolumeQueryEntity> queryAirCargoVolume(AirCargoVolumeDto airCargoVolumeDto) {
		return this.getSqlSession().selectList(NAMESPACE+"airCargoVolumeQuery", airCargoVolumeDto);
		
	}

	/**
	 *  查询线路舱位明细
	 * @author  dp-liming
	 * @date 2012-10-19 上午10:20:23
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao#queryAirSpace(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailEntity)
	 */
	@Override
	public List<AirSpaceDetailVolumeEntity> queryAirSpace(AirSpaceDetailVolumeDto  airSpaceDetailVolumeDto) {
		return this.getSqlSession().selectList(NAMESPACE+"airSpaceQuery", airSpaceDetailVolumeDto);
		
	}


	  
	/**
	 * 查询某件货物的库存信息
	 * @author  dp-liming
	 * @date 2012-10-19 上午10:20:23
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao#querySerialNumberStock(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailEntity)
	 */
	@Override
	public List<AirCargoVolumeQueryEntity> querySerialNumberStock(AirCargoVolumeDto airCargoVolumeDto) {
		return this.getSqlSession().selectList(NAMESPACE + "querySerialNumberStock", airCargoVolumeDto);
		 
	}

	/**
	 *  查询空运货量总数
	 * @author  dp-liming
	 * @date 2012-10-19 上午11:00:47
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao#queryCount(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto)
	 */
	@Override
	public Long queryCount(AirCargoVolumeDto airCargoVolumeDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryAirCargoVolumeCount", airCargoVolumeDto);
		
	}
	
	/**
	 * 查询货量统计for export
	 * @author  dp-liming
	 * @date 2012-10-19 上午10:50:47
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao#queryAirCargoVolume(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto, int, int)
	 */
	@Override
	public List<AirCargoVolumeForExportDto> queryAirCargoVolumeForExport(AirCargoVolumeDto airCargoVolumeDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"airCargoVolumeForExport", airCargoVolumeDto, rowBounds);
		
	}


}