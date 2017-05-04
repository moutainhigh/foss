package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirFlightQueryDto;

public interface IAirFlightQueryDao {
	
	/**
	 * 查询空运航班
	 * @author 105869
	 * @date 2014年10月18日 11:30:08
	 * @param AirFlightQueryDto
	 * 
	 * */
	List<AirFlightEntity> queryAirFlights(AirFlightQueryDto dto,int limit,int start);
	
	
	/**
	* @description 查询空运航班总记录数
	* @param dto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年11月28日 上午11:29:16
	*/
	long queryAirFlightsCount(AirFlightQueryDto dto);
	
}
