package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirProtocolFlightDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightQueryDto;

public class AirProtocolFlightDao extends iBatis3DaoImpl implements IAirProtocolFlightDao{

	private static final String nameSpace = "foss.airfreight.airProtocolFlight.";

	
	/*
	 * @desc:查询协议航班货量list（分页）
	 * @param queryCondition
	 * @author：foss-105795-wqh
	 * @date:2014-02-18
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition,
			int start,int limit) {
		//开始和结束
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(nameSpace+"queryProtocolFlightList", queryCondition, rowBounds);		
		
	}

	
	/*
	 * @desc:查询协议航班货量list
	 * @param queryCondition
	 * @author：foss-105795-wqh
	 * @date:2014-02-19
	 * */
	@SuppressWarnings("unchecked")
	public List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition){
	   return this.getSqlSession().selectList(nameSpace+"queryProtocolFlightList", queryCondition);

			
	}
	
	/*
	 * @desc:根据用户code查询用户有权限操作的部门code
	 * @param userCode
	 * @author：foss-105795-wqh
	 * @date:2014-02-22
	 * */
	@SuppressWarnings("unchecked")
	public List<String> queryOptAllOrgCodeByUserCode(String userCode){
		return this.getSqlSession().selectList(nameSpace+"queryOptAllOrgCodeByUserCode", userCode);
	}
}
