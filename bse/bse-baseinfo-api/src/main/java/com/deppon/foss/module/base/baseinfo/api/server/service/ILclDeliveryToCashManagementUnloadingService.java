package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyVehicleException;

public interface ILclDeliveryToCashManagementUnloadingService {
	/**
	 * 
	 * <p>新增</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:20:39
	 * @param entity
	 * @return
	 * @throws LoadAndUnloadEfficiencyVehicleException
	 * @see
	 */
	LclDeliveryToCashManagementUnloadingEntity addlclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity)
			throws LoadAndUnloadEfficiencyVehicleException;
     /**
      * 
      * <p>删除</p> 
      * @author 273311 
      * @date 2016-8-23 下午4:21:05
      * @param entity
      * @return
      * @see
      */
	LclDeliveryToCashManagementUnloadingEntity deletelclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity);
     /**
      * 
      * <p>批量删除</p> 
      * @author 273311 
      * @date 2016-8-23 下午4:21:31
      * @param ids
      * @return
      * @see
      */
	LclDeliveryToCashManagementUnloadingEntity deletelclDeliveryToCashManagementUnloadingMore(
			String[] ids);
    /**
     * 
     * <p>更新</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:21:51
     * @param entity
     * @return
     * @see
     */
	LclDeliveryToCashManagementUnloadingEntity updatelclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity);

	/**
	 * 
	 * <p>根据出发部门和到达部门查询</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:22:12
	 * @param startOrgCode
	 * @param reachOrgCode
	 * @return
	 * @see
	 */
	List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode);
     /**
      * 
      * <p>根据ID精确查询</p> 
      * @author 273311 
      * @date 2016-8-23 下午4:22:37
      * @param id
      * @return
      * @see
      */
	LclDeliveryToCashManagementUnloadingEntity queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(
			String id);

	/**
	 * 
	 * <p> 查询  分页</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:23:04
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntityExactByEntity(
			LclDeliveryToCashManagementUnloadingEntity entity, int start,
			int limit);

	/**
	 * 
	 * <p> 统计条数   分页</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:23:25
	 * @param entity
	 * @return
	 * @see
	 */
	long queryLclDeliveryToCashManagementUnloadingExactByEntityCount(
			LclDeliveryToCashManagementUnloadingEntity entity);

}
