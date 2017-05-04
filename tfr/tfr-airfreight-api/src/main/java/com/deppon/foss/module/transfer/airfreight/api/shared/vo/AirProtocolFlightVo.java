package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightQueryDto;


/*
 * @desc 协议航班货量统计vo
 * @author foss-105795-wqh
 * @date 2014-02-18
 * 
 * */
public class AirProtocolFlightVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6390962877365020992L;
	//协议航班dto
	private List<AirProtocolFlightDto> airProtocolFlightList;
	
	//协议航班查询dto
	private AirProtocolFlightQueryDto airProtocolFlightQueryDto=new AirProtocolFlightQueryDto();
	
	//get and set
	
	public List<AirProtocolFlightDto> getAirProtocolFlightList() {
		return airProtocolFlightList;
	}
	public void setAirProtocolFlightList(
			List<AirProtocolFlightDto> airProtocolFlightList) {
		this.airProtocolFlightList = airProtocolFlightList;
	}
	public AirProtocolFlightQueryDto getAirProtocolFlightQueryDto() {
		return airProtocolFlightQueryDto;
	}
	public void setAirProtocolFlightQueryDto(
			AirProtocolFlightQueryDto airProtocolFlightQueryDto) {
		this.airProtocolFlightQueryDto = airProtocolFlightQueryDto;
	}
	
	
	
}
