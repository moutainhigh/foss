package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveSerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OppLocusEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirQueryFlightArriveDto;

public interface IAirQueryFlightArriveDao {

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
     * 新增空运到达   代理到机场提货 AGENT_TO_AIRPORT_PICK_UP
     * @param airQueryFlightArriveEntity
     */
	public int addAirQueryFlightArrive(AirQueryFlightArriveEntity airQueryFlightArriveEntity);
	
	/**
	 * 新增空运到达明细   代理到机场提货 AGENT_TO_AIRPORT_PICK_UP
	 * @param airQueryFlightArriveDetailEntity
	 */
    public int addAirQueryFlightArriveDetail(AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity);
    /**
     * 新增空运到达流水   代理到机场提货 AGENT_TO_AIRPORT_PICK_UP
     * @param airQueryFlightArriveSerialEntity
     */
    public int addAirQueryFlightArriveSerial(AirQueryFlightArriveSerialEntity airQueryFlightArriveSerialEntity);

    
    /**
	 * 根据正单号查找  空运到达主表 , 用于修改 空运到达类型 为 代理到机场提货的信息
	 *
	 */
    AirQueryFlightArriveDto loadAirFlightArriveInfo(AirQueryFlightArriveDto airQueryFlightArriveDto);
   
    /*通过正单号去查询空运到达表*/
    AirQueryFlightArriveEntity queryFlightArriveByAirWaybillNo(AirQueryFlightArriveDto airQueryFlightArriveDto);
    
    /*通过空运到达主表Id去查询空运到达明细表*/
    List<AirQueryFlightArriveDetailEntity> queryFlightArriveDetail(AirQueryFlightArriveDto airQueryFlightArriveDto);
    
    /*通过空运到达明细表ID去查询空运到达流水表*/
    List<AirQueryFlightArriveSerialEntity> queryFlightArriveSerial(AirQueryFlightArriveDto airQueryFlightArriveDto);
    
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
      
     /**	查询已经保存的流水信息   */	
 	public List<AirQueryFlightArriveDto> queryAirWaybillSerialNoSelected(
 			AirQueryFlightArriveDto airQueryFlightArriveDto);

 	/**
 	 * 根据正单号+运单号+空运到达类型  查看已到达的流水个数
 	 * @param airQueryFlightArriveDto
 	 * @return
 	 */
	Long getSerialsCount(AirQueryFlightArriveDto airQueryFlightArriveDto);
	/**
 	 * 根据正单号+运单号+空运到达类型  查看备注
 	 * @param airQueryFlightArriveDto
 	 * @return
 	 */
	List<String> getSerialsNote(AirQueryFlightArriveDto airQueryFlightArriveDto);
	
	/**
 	 * 根据正单号+运单号+空运到达类型  查看到达时间
 	 * @param airQueryFlightArriveDto
 	 * @return
 	 */
	List<Date> getSerialsArriveTime(AirQueryFlightArriveDto airQueryFlightArriveDto);

	/**
	 * 
	* @description 根据运单号查询 OPP轨迹
	* @param waybillNo serialNo
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月16日 下午7:28:20
	 */
	List<OppLocusEntity> queryOppLocusByWayBillNo(String waybillNo,String serialNo);

	/**
	 * 
	* @description FOSS保存OPP出发到达 返货轨迹 供FOSS综合查询用
	* @param entity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月16日 下午8:23:21
	 */
	int saveOppLocus(OppLocusEntity entity);
	
	
}