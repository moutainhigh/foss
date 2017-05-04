package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;

public interface IExpressVehiclesDao {
	
    int addExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity);

	List<ExpressVehiclesEntity> queryInfos(ExpressVehiclesEntity expressVehiclesEntity, int limit, int start);

	Long queryRecordCount(ExpressVehiclesEntity expressVehiclesEntity);

	ExpressVehiclesEntity queryExpressVehiclesById(String id);

	int updateExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity);
	
	Long queryCountByExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity);

	List<ExpressVehiclesEntity> getExpressVehiclesEntitysByEmpcode(
			String empCode);
	/**
	   * 统计按条件查询返回的记录数
	   * 
	   * @author  WangPeng
	   * @Date    2013-8-23 下午1:31:17
	   * @param   enity
	   * @return  Long
	   * 
	   *
	   */
	  Long recordCountByQueryParams(ExpressVehiclesEntity enity);
	  
	  /**
	   * 作废车辆信息
	   * 
	   * @author  WangPeng
	   * @Date    2013-8-23 下午4:52:19
	   * @param   entity
	   * @param   ids
	   * @return  int
	   * 
	   *
	   */
	  int  deleteExpressVehicles(ExpressVehiclesEntity entity,List<String> ids);
	  
	  /**
	   * 根据条件查询快递车辆信息
	   * 
	   * @author  FOSS-ShenWeiHua-132599
	   * @Date    2014-9-24 下午1:31:17
	   * @param   enity
	   * @return  entity
	   *
	   */
	  List<ExpressVehiclesEntity> queryExpressVehiclesByEntity(ExpressVehiclesEntity entity);
}
