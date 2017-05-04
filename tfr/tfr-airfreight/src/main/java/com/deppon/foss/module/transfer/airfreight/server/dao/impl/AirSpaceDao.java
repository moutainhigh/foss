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
 *  FILE PATH          :/AirSpaceDao.java
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
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirSpaceDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto;
/**
 * 航空舱位DAO
 * @author 038300-foss-pengzhen
 * @date 2012-11-8 下午3:13:06
 */
public class AirSpaceDao extends iBatis3DaoImpl implements IAirSpaceDao {
	/**
	 * 定义局部命名空间
	 */
	public static final String NAMESPACE = "foss.airfreight.airspace.";
	
	/**
	 * 查询航空舱位信息列表DAO
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-16 下午1:59:20
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirSpaceDao#queryAirSpace(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirSpaceDto> queryAirSpace(AirSpaceDto dto, int limit, int start) {
		//查询舱位信息行数
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+ "selectAirSpace", dto, rowBounds);
	}
	
	/**
	 * 录入航空舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	@Override
	public void addAirSpace(AirSpaceDto dto) {
		//录入舱位信息
		this.getSqlSession().insert(NAMESPACE+ "insertAirSpace", dto);
	}
	
	/**
	 * 录入航空舱位明细
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	@Override
	public void addAirSpaceDetail(AirSpaceDetailEntity entity) {
		//查询舱位明细
		this.getSqlSession().insert(NAMESPACE+ "insertAirSpaceDetail", entity);
	}
	
	/**
	 * 判断是否存在同一日期到同一目的地的舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-1 下午8:12:28
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isExistSpace(AirSpaceDto dto) {
		//条件舱位是否存在list
		List<AirSpaceDto> list = this.getSqlSession().selectList(NAMESPACE+ "selectIsExistSpace", dto);
		if(list != null && list.size() > 0){
			//存在返回true
			return true;
		}
		//返回false
		return false;
	}
	

	/**
	 * 删除舱位明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-6 下午1:50:32
	 */
	@Override
	public void deleteAirSpace(AirSpaceDto dto) {
		//执行删除
		this.getSqlSession().delete(NAMESPACE+ "deleteAirSpace", dto);
	}
	
	/**
	 * 根据舱位id删除某条舱位明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-6 下午1:50:32
	 */
	@Override
	public void deleteAirSpaceDetail(String airSpaceId) {
		//执行删除
		this.getSqlSession().delete(NAMESPACE+ "deleteAirSpaceDetail", airSpaceId);
	}
	
	/**
	 * 根据id查询航空公司舱位及明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-8 下午3:13:32
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirSpaceDao#queryAirSpaceById(java.lang.String)
	 */
	@Override
	public AirSpaceDto queryAirSpaceById(String airSpaceId) {
		//根据id查询返回结果
		return (AirSpaceDto) this.getSqlSession().selectOne(NAMESPACE+ "selectAirSpaceById", airSpaceId);
	}
	
	/**
	 * 修改航空舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	@Override
	public void updateAirSpace(AirSpaceDto dto) {
		//修改信息
		this.getSqlSession().update(NAMESPACE+ "updateAirSpace", dto);
	}
	
	/**
	 * 修改航空舱位明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	@Override
	public void updateAirSpaceDetail(AirSpaceDetailEntity entity) {
		//修改信息明细
		this.getSqlSession().update(NAMESPACE+ "updateAirSpaceDetail", entity);
	}
	
	/**
	 * 统计根据条件查询出的结果数目
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-14 上午9:34:22
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirSpaceDao#queryAirSpaceCount(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto)
	 */
	@Override
	public Long queryAirSpaceCount(AirSpaceDto dto) {
		//舱位数
		return (Long)this.getSqlSession().selectOne(NAMESPACE+ "selectAirSpaceCount", dto);
	}
	
	/**
	 * 返回舱位ID 
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-6 下午7:46:12
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirSpaceDao#getAriSpaceId(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAriSpaceId(AirSpaceDto dto) {
		//舱位id  list
		List<AirSpaceDto> list = this.getSqlSession().selectList(NAMESPACE+ "selectIsExistSpace", dto);
		if(list != null && list.size() > 0){
			//不为空
			//返回id信息
			return list.get(0).getId();
		}
		return "";
	}
	
	/**
	 * 根据ID查询舱位类型总数
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-27 下午2:25:34
	 */
	@Override
	public Long queryFlightCountById(AirSpaceDto dto){
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryFlightCountById",dto);
	}
	
	/**
	 * "删除"单条舱位信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-27 下午2:29:00
	 */
	@Override
	public void deleteSingleSpace(AirSpaceDto dto){
		this.getSqlSession().delete(NAMESPACE + "deleteSingleSpace",dto);
	}
}