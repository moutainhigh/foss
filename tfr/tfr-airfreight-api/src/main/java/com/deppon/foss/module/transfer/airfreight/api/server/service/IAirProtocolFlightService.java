package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightQueryDto;

/*
 * @desc:协议航班货量统计service
 * @author：foss-105795-wqh
 * @date:2014-02-18
 * */
public interface  IAirProtocolFlightService extends IService{

	
	
	/*
	 * @desc:查询协议航班货量list（分页）
	 * @param queryCondition
	 * @author：foss-105795-wqh
	 * @date:2014-02-18
	 * */
	List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition,int start,int limit);
	/*
	 * @desc:查询协议航班货量list
	 * @param 
	 * @author
	 * */
	List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition);
	
	/**
	 * @author 105795
	 * @date   2014-02-22
	 * @param  conditionDto
	 * @see    导出协议航班货量统计明细
	 * */
	 List exportProtocolFlightDetailExcel(AirProtocolFlightQueryDto queryCondition);
	
	
	/*
	 * @desc:根据用户code查询用户有权限操作的部门code
	 * @param userCode
	 * @author：foss-105795-wqh
	 * @date:2014-02-22
	 * */
	List<String> queryOptAllOrgCodeByUserCode();
}
