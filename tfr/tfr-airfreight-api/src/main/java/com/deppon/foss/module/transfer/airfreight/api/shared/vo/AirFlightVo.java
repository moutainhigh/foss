package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirFlightQueryDto;

/**
 * 空运航班
 * */
public class AirFlightVo  implements Serializable{

	private static final long serialVersionUID = -4144681777740666151L;
	//查询航班dto
	private AirFlightQueryDto airFlightQueryDto;
	//查询航班集合
	private List<AirFlightEntity> airFlightList;
	
	public AirFlightQueryDto getAirFlightQueryDto() {
		return airFlightQueryDto;
	}
	public void setAirFlightQueryDto(AirFlightQueryDto airFlightQueryDto) {
		this.airFlightQueryDto = airFlightQueryDto;
	}
	public List<AirFlightEntity> getAirFlightList() {
		return airFlightList;
	}
	public void setAirFlightList(List<AirFlightEntity> airFlightList) {
		this.airFlightList = airFlightList;
	}
	
	
}
