package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;

public interface ILclDeliveryToCashManagementUnloadingDao {
	/**
	 * 
	 * <p>新增</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:28:42
	 * @param entity
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementUnloadingEntity addlclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity);
    /**
     * 
     * <p>删除</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:28:54
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
     * @date 2016-8-23 下午4:29:42
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
     * @date 2016-8-23 下午4:30:13
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
	 * @date 2016-8-23 下午4:30:29
	 * @param startOrgCode
	 * @param reachOrgCode
	 * @return
	 * @see
	 */
	List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode);
    /**
     * 
     * <p>ID 精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:30:50
     * @param id
     * @return
     * @see
     */
	LclDeliveryToCashManagementUnloadingEntity queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(
			String id);

	/**
	 * 
	 * <p>查询  分页</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:31:48
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
	 * <p>统计条数</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:32:07
	 * @param entity
	 * @return
	 * @see
	 */
	long queryLclDeliveryToCashManagementUnloadingExactByEntityCount(
			LclDeliveryToCashManagementUnloadingEntity entity);
}
