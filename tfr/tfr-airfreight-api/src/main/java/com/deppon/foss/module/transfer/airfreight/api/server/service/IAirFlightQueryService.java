package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirFlightQueryDto;

public interface IAirFlightQueryService extends IService{
	
	/**
	 * 查询空运航班
	 * @author 105869
	 * @date 2014年10月18日 11:13:08
	 * @param airFlightQueryDto
	 * 
	 * */
	List<AirFlightEntity> queryAirFlights(AirFlightQueryDto airFlightQueryDto,int limit,int start);
	
	
	/**
	* @description 查询空运航班分页总数
	* @param airFlightQueryDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年11月28日 上午11:28:16
	*/
	long queryAirFlightsCount(AirFlightQueryDto airFlightQueryDto);
	

}
