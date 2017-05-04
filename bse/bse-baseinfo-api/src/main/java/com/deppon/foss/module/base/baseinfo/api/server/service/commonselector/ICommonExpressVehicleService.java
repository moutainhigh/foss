package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;

/**
 * 快递车牌查询公共选择器Service接口
 * 
 * @author WangPeng
 * @date   2013-07-29 2:14 PM 
 * 
 */
public interface ICommonExpressVehicleService {

	/**
	 * 根据条件查询记录
	 * 
	 * @author  WangPeng
	 * @Date    2013-7-29 下午2:27:54
	 * @param   expressVeh
	 * @param   limit
	 * @param   start
	 * @return  List<ExpressVehicle>
	 * 
	 */
	List<ExpressVehicleDto> queryExpressVehicleNoListBySelectiveCondition(
			ExpressVehicleDto expressVeh, int limit, int start);

	/**
	 *记录查询返回的记录行数
	 * 
	 * @author  WangPeng
	 * @Date    2013-7-29 下午2:28:49
	 * @param   expressVeh
	 * @return  long
	 * 
	 */
	long queryExpressVehicleNoRecordCountBySelectiveCondition(ExpressVehicleDto expressVeh);
	
	/**
	 * 查询快递员列表
	 * 
	 * @author  WangPeng
	 * @Date    2013-7-30 下午4:28:19
	 * @param   entity
	 * @return List<ExpressVehicleDto>
	 *
	 */
	List<ExpressVehicleDto> queryExpressEmployeeListByCondition(ExpressVehicleDto entity);
}
