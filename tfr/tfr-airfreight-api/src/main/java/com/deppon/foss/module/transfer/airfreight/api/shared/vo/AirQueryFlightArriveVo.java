package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirQueryFlightArriveDto;

/**
 * 查询空运到达的VO
 * 
 * @author zwd 200968
 * @date 2015-06-09
 */
public class AirQueryFlightArriveVo implements Serializable {

	/**
	 * 序列化 
	 */
	private static final long serialVersionUID = -6279852897147903908L;
	
	private AirQueryFlightArriveDto airQueryFlightArriveDto;
	
	private List<AirQueryFlightArriveDto> airQueryFlightArriveDtos;
	
	private List<AirQueryFlightArriveDto> airQueryFlightArriveDtosSelected;
	
	public List<AirQueryFlightArriveDto> getAirQueryFlightArriveDtos() {
		return airQueryFlightArriveDtos;
	}

	public void setAirQueryFlightArriveDtos(
			List<AirQueryFlightArriveDto> airQueryFlightArriveDtos) {
		this.airQueryFlightArriveDtos = airQueryFlightArriveDtos;
	}

	public AirQueryFlightArriveDto getAirQueryFlightArriveDto() {
		return airQueryFlightArriveDto;
	}

	public void setAirQueryFlightArriveDto(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		this.airQueryFlightArriveDto = airQueryFlightArriveDto;
	}

	public List<AirQueryFlightArriveDto> getAirQueryFlightArriveDtosSelected() {
		return airQueryFlightArriveDtosSelected;
	}

	public void setAirQueryFlightArriveDtosSelected(
			List<AirQueryFlightArriveDto> airQueryFlightArriveDtosSelected) {
		this.airQueryFlightArriveDtosSelected = airQueryFlightArriveDtosSelected;
	}
	
	
	
}
