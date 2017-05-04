package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirFlightQueryDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirFlightQueryService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirFlightQueryDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

public class AirFlightQueryService implements IAirFlightQueryService{
	
	private IAirFlightQueryDao airFlightQueryDao;
	
	public void setAirFlightQueryDao(IAirFlightQueryDao airFlightQueryDao) {
		this.airFlightQueryDao = airFlightQueryDao;
	}

	/**
	 * 查询空运航班
	 * @author 105869
	 * @date 2014年10月18日 11:13:08
	 * @param airFlightQueryDto
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirFlightQueryService#queryAirFlights(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirFlightQueryDto)
	 */
	@Override
	public List<AirFlightEntity> queryAirFlights(AirFlightQueryDto airFlightQueryDto,int limit,int start ){
		if(airFlightQueryDto==null){
			throw new TfrBusinessException("请输入查询条件！");
		}else {
			if(airFlightQueryDto.getBeginFligtTime()==null || airFlightQueryDto.getEndFligtTime()==null){
				throw new TfrBusinessException("请输入查询的起始和结束时间！");
			}
		} 
		return airFlightQueryDao.queryAirFlights(airFlightQueryDto,limit,start);
	}

	/**
	* @description 查询空运航班分页总数
	* @param airFlightQueryDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年11月28日 上午11:28:16
	*/
	@Override
	public long queryAirFlightsCount(AirFlightQueryDto airFlightQueryDto) {
		if(airFlightQueryDto==null){
			throw new TfrBusinessException("请输入查询条件！");
		}else {
			if(airFlightQueryDto.getBeginFligtTime()==null || airFlightQueryDto.getEndFligtTime()==null){
				throw new TfrBusinessException("请输入查询的起始和结束时间！");
			}
		} 
		return airFlightQueryDao.queryAirFlightsCount(airFlightQueryDto);
	}
	
	
}
