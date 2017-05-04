package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirFlightQueryDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirFlightQueryDto;

public class AirFlightQueryDao extends iBatis3DaoImpl implements IAirFlightQueryDao {
	
	public static final String NAMESAPCE="foss.airfreight.";
	
	/**
	 * 查询空运航班信息
	 * @author 105869
	 * @date 2014年10月18日 13:58:36
	 * @param AirFlightQueryDto
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirFlightQueryDao#queryAirFlights(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirFlightQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirFlightEntity> queryAirFlights(AirFlightQueryDto dto,int limit,int start){
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESAPCE+"queryAirFlights", dto,rowBounds);
		
	}


	/**
	* @description 查询空运航班总记录数
	* @param dto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年11月28日 上午11:29:16
	*/
	@Override
	public long queryAirFlightsCount(AirFlightQueryDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESAPCE+"queryAirFlightsCount", dto);
	}
	
	
}
