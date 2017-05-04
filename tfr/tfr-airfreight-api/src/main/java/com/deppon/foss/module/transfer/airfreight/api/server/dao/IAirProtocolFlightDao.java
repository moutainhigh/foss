package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightQueryDto;

/*
 * @desc:协议航班货量统计dao
 * @author：foss-105795-wqh
 * @date:2014-02-18
 * */
public interface IAirProtocolFlightDao {

	/*
	 * @desc:查询协议航班货量list（分页）
	 * @param queryCondition
	 * @author：foss-105795-wqh
	 * @date:2014-02-18
	 * */
	List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition,int start,int limit);
	
	/*
	 * @desc:查询协议航班货量list
	 * @param queryCondition
	 * @author：foss-105795-wqh
	 * @date:2014-02-18
	 * */
	List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition);

	/*
	 * @desc:根据用户code查询用户有权限操作的部门code
	 * @param userCode
	 * @author：foss-105795-wqh
	 * @date:2014-02-22
	 * */
	List<String> queryOptAllOrgCodeByUserCode(String userCode);

	
	
}
