package com.deppon.foss.module.transfer.airfreight.server.action;


import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirFlightQueryService;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirFlightVo;


public class AirFlightQueryAction extends AbstractAction{
	private static final long serialVersionUID = -5706543418979482927L;
	
	private IAirFlightQueryService airFlightQueryService;
	
	
	
	private AirFlightVo airFlightVo =new AirFlightVo();

	public AirFlightVo getAirFlightVo() {
		return airFlightVo;
	}

	public void setAirFlightVo(AirFlightVo airFlightVo) {
		this.airFlightVo = airFlightVo;
	}
	
	
	public void setAirFlightQueryService(IAirFlightQueryService airFlightQueryService) {
		this.airFlightQueryService = airFlightQueryService;
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * */
	@JSON
	public String queryAirFlights(){
		try{
			airFlightVo.setAirFlightList(airFlightQueryService.queryAirFlights(airFlightVo.getAirFlightQueryDto(),this.getLimit(),this.getStart()));
			this.setTotalCount(airFlightQueryService.queryAirFlightsCount(airFlightVo.getAirFlightQueryDto()));
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
}
