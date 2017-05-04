package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;

public class CommonExpressVehicleService implements
		ICommonExpressVehicleService {

	/**
	 * Dao
	 */
	ICommonExpressVehicleDao  commonExpressVehicleDao;

	public void setCommonExpressVehicleDao(
			ICommonExpressVehicleDao commonExpressVehicleDao) {
		this.commonExpressVehicleDao = commonExpressVehicleDao;
	}

	/**
	 * 查询车牌号（快递）
	 * 
	 * @author WangPeng
	 * @Date   2013-7-29 下午4:46:10
	 * @param expressVeh
	 * @param start
	 * @param limit
	 * @return List<ExpressVehicleDto>
	 *
	 */
	@Override
	public List<ExpressVehicleDto> queryExpressVehicleNoListBySelectiveCondition(
			ExpressVehicleDto expressVeh, int limit, int start) {
		if(null == expressVeh){
			expressVeh = new ExpressVehicleDto();
		}
		return commonExpressVehicleDao.queryExpressVehicleNoListBySelectiveCondition(expressVeh, limit, start);
	}

	/**
	 * 记录行数
	 * 
	 * @author WangPeng
	 * @Date   2013-7-29 下午4:46:10
	 * @param  expressVeh
	 * @param  start
	 * @param  limit
	 * @return Long
	 *
	 */
	@Override
	public long queryExpressVehicleNoRecordCountBySelectiveCondition(
			ExpressVehicleDto expressVeh) {
		if(null == expressVeh){
			expressVeh = new ExpressVehicleDto();
		}
		return commonExpressVehicleDao.queryExpressVehicleNoRecordCountBySelectiveCondition(expressVeh);
	}

	/**
	 * 查询快递员列表
	 * 
	 * @author  WangPeng
	 * @Date    2013-7-30 下午4:28:19
	 * @param   entity
	 * @return List<ExpressVehicleDto>
	 *
	 */
	@Override
	public
	List<ExpressVehicleDto> queryExpressEmployeeListByCondition(ExpressVehicleDto entity) {
		if(null == entity){
			entity = new ExpressVehicleDto();
		}
		return commonExpressVehicleDao.queryExpressEmployeeListByCondition(entity);
	}
}
