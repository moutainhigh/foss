package com.deppon.foss.module.transfer.airfreight.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryFlightArriveService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirQueryFlightArriveDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirQueryFlightArriveVo;

/**
 * 查询空运到达相关操作.
 * @author foss-200968-zwd
 * @date 2015-06-04 09:10:14
 */

public class AirQueryFlightArriveAction extends AbstractAction {
	
	/** 序列化. */
	private static final long serialVersionUID = -5343546578617043287L;
	
	/** 日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirQueryFlightArriveAction.class);
	
	/**
	 * @author zwd 200968
	 * 查询空运到达VO
	 */
	private AirQueryFlightArriveVo airQueryFlightArriveVo = new AirQueryFlightArriveVo();
	public AirQueryFlightArriveVo getAirQueryFlightArriveVo() {
		return airQueryFlightArriveVo;
	}
	public void setAirQueryFlightArriveVo(
			AirQueryFlightArriveVo airQueryFlightArriveVo) {
		this.airQueryFlightArriveVo = airQueryFlightArriveVo;
	}
  
	private IAirQueryFlightArriveService airQueryFlightArriveService;
	public void setAirQueryFlightArriveService(
			IAirQueryFlightArriveService airQueryFlightArriveService) {
		this.airQueryFlightArriveService = airQueryFlightArriveService;
	}
	
	
    /*查找空运到达*/      
	public String queryFlightArrive(){
		//异常处理
		try{
		        
			AirQueryFlightArriveDto airQueryFlightArriveDto = airQueryFlightArriveVo.getAirQueryFlightArriveDto();
			//查询所有未作废的航空正单交接单信息
			List<AirQueryFlightArriveDto> airQueryFlightArriveList = airQueryFlightArriveService.queryFlightArrive(airQueryFlightArriveDto,this.getStart(),this.getLimit());
			//获取总记录条数,用于分页
			Long totalCount = airQueryFlightArriveService.getCount(airQueryFlightArriveDto);
			//填充到VO用于界面显示列表
			airQueryFlightArriveVo.setAirQueryFlightArriveDtos(airQueryFlightArriveList);
			//分页总数
			this.setTotalCount(totalCount);
			//返回成功信息
			return returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
	}
	//2015-07-02  新增/修改   空运到达类型:货物到达代理处  根据正单号查询信息
	public String queryAirWaybillNo(){
		//异常处理
		try{
			AirQueryFlightArriveDto airQueryFlightArriveDto = airQueryFlightArriveVo.getAirQueryFlightArriveDto();
			//
			List<AirQueryFlightArriveDto> airQueryFlightArriveList = airQueryFlightArriveService.queryAirWaybillNo(airQueryFlightArriveDto,this.getStart(),this.getLimit());
			//获取总记录条数,用于分页
			Long totalCount = airQueryFlightArriveService.getCountAirWaybillNo(airQueryFlightArriveDto);
			//填充到VO用于界面显示列表
			airQueryFlightArriveVo.setAirQueryFlightArriveDtos(airQueryFlightArriveList);
			//分页总数
			this.setTotalCount(totalCount);
			//返回成功信息
			return returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
	}
	
	
	
	/**
	 * 查询流水明细
	 *
	 */
	public String queryAirWaybillSerialNo(){
		//异常处理
		try{
			AirQueryFlightArriveDto airQueryFlightArriveDto = airQueryFlightArriveVo.getAirQueryFlightArriveDto();
			//查询所有未作废的航空正单交接单信息
			List<AirQueryFlightArriveDto> airQueryFlightArriveList = airQueryFlightArriveService.queryAirWaybillSerialNo(airQueryFlightArriveDto);
			//存放已经保存的流水信息
			List<AirQueryFlightArriveDto> airQueryFlightArriveDtosSelected = airQueryFlightArriveService.queryAirWaybillSerialNoSelected(airQueryFlightArriveDto);
			//填充到VO用于界面显示列表
			airQueryFlightArriveVo.setAirQueryFlightArriveDtos(airQueryFlightArriveList);
			airQueryFlightArriveVo.setAirQueryFlightArriveDtosSelected(airQueryFlightArriveDtosSelected);
			//返回成功信息
			return returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
	}
	
	
	
	/**
	 *  新增/修改操作  空运到达类型:代理到机场提货  根据正单号查询信息
	 */
	public String queryAirFlightArrive(){
		//异常处理
		try{
			AirQueryFlightArriveDto airQueryFlightArriveDto = airQueryFlightArriveVo.getAirQueryFlightArriveDto();
		
			List<AirQueryFlightArriveDto> airQueryFlightArriveList = airQueryFlightArriveService.queryAirFlightArrive(airQueryFlightArriveDto);
			//填充到VO用于界面显示列表
			airQueryFlightArriveVo.setAirQueryFlightArriveDtos(airQueryFlightArriveList);
			//返回成功信息
			return returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
	}
	
	/**
	 * 新增到达录入:代理到机场提货
	 * @return
	 */
	public String addAirFlightArrivePickUp(){
		//异常处理
		try{
			AirQueryFlightArriveDto airQueryFlightArriveDto = airQueryFlightArriveVo.getAirQueryFlightArriveDto();
		    airQueryFlightArriveService.addAirFlightArrivePickUp(airQueryFlightArriveDto);
			//返回成功信息
			return returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
	}
	
	
	
	/**
	 * 新增到达录入:货物到达代理处
	 * @return
	 */
	public String addAirFlightArriveAgency(){
		//异常处理
		try{
			List<AirQueryFlightArriveDto> airQueryFlightArriveDtos = airQueryFlightArriveVo.getAirQueryFlightArriveDtos();
			AirQueryFlightArriveDto airQueryFlightArriveDto = airQueryFlightArriveVo.getAirQueryFlightArriveDto();
			airQueryFlightArriveService.addAirFlightArriveAgency(airQueryFlightArriveDtos,airQueryFlightArriveDto);
			//返回成功信息
			return returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
	}
	
	// 
	
	/**
	 * 根据正单号查找  空运到达主表 , 用于修改 空运到达类型 为 代理到机场提货的信息
	 *
	 */
	public String loadAirFlightArriveInfo(){
		//异常处理
		try{
			AirQueryFlightArriveDto airQueryFlightArriveDto = airQueryFlightArriveVo.getAirQueryFlightArriveDto();
			AirQueryFlightArriveDto airQueryFlightArrive = airQueryFlightArriveService.loadAirFlightArriveInfo(airQueryFlightArriveDto);
			//填充到VO用于界面显示列表
			airQueryFlightArriveVo.setAirQueryFlightArriveDto(airQueryFlightArrive);
			//返回成功信息
			return returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
	}
	
	
	
	
}