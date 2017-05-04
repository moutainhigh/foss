package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveSerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirQueryFlightArriveDto;



public interface IAirQueryFlightArriveService extends IService {
	
	List<AirQueryFlightArriveDto> queryFlightArrive(
			AirQueryFlightArriveDto airQueryFlightArriveDto, int start,
			int limit);

	Long getCount(AirQueryFlightArriveDto airQueryFlightArriveDto);
	
	
	List<AirQueryFlightArriveDto> queryAirWaybillNo(
			AirQueryFlightArriveDto airQueryFlightArriveDto, int start,
			int limit);

	Long getCountAirWaybillNo(AirQueryFlightArriveDto airQueryFlightArriveDto);

	List<AirQueryFlightArriveDto> queryAirWaybillSerialNo(
			AirQueryFlightArriveDto airQueryFlightArriveDto);
	
	List<AirQueryFlightArriveDto> queryAirFlightArrive(
			AirQueryFlightArriveDto airQueryFlightArriveDto);
	
	/**
	 * 保存           代理到机场提货 AGENT_TO_AIRPORT_PICK_UP
	 * @param airQueryFlightArriveDto
	 * @return
	 */
	int addAirFlightArrivePickUp(AirQueryFlightArriveDto airQueryFlightArriveDto);
	
	
	/**
	 * 保存           货物到达代理处 
	 * @param airQueryFlightArriveDtos
	 * @return
	 */
    int addAirFlightArriveAgency(List<AirQueryFlightArriveDto> airQueryFlightArriveDtos,AirQueryFlightArriveDto airQueryFlightArriveDto);
	
    
    /**
	 * 根据正单号查找  空运到达主表 , 用于修改 空运到达类型 为 代理到机场提货的信息
	 *
	 */
    AirQueryFlightArriveDto loadAirFlightArriveInfo(AirQueryFlightArriveDto airQueryFlightArriveDto);
    
    /**
     * 修改主表    空运到达类型:代理到机场提货
     */
     int modifyAirFlightArrivePickUp(AirQueryFlightArriveEntity airQueryFlightArriveEntity);
     
     /**
      * 修改明细表    空运到达类型:代理到机场提货
      */
     int modifyAirFlightArriveDetailPickUp(AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity);
      
      /**
       * 修改流水表    空运到达类型:代理到机场提货
       */
     int modifyAirFlightArriveSerialPickUp(AirQueryFlightArriveSerialEntity airQueryFlightArriveSerialEntity);	
       

    /**通过正单号去查询空运到达表*/
     AirQueryFlightArriveEntity queryFlightArriveByAirWaybillNo(AirQueryFlightArriveDto airQueryFlightArriveDto);
     
     /**通过空运到达主表Id去查询空运到达明细表*/
     List<AirQueryFlightArriveDetailEntity> queryFlightArriveDetail(AirQueryFlightArriveDto airQueryFlightArriveDto);
     
     /**通过空运到达明细表ID去查询空运到达流水表*/
     List<AirQueryFlightArriveSerialEntity> queryFlightArriveSerial(AirQueryFlightArriveDto airQueryFlightArriveDto);

	 int modifyAirFlightArrivePickUp(AirQueryFlightArriveDto airQueryFlightArriveDto);
     
     /**	查询已经保存的流水信息   zwd 200968 */	
 	public List<AirQueryFlightArriveDto> queryAirWaybillSerialNoSelected(
 			AirQueryFlightArriveDto airQueryFlightArriveDto);
}