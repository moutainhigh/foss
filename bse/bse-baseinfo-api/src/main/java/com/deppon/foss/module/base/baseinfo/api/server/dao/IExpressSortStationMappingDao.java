package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSortStationMappingEntity;

/**
 * 快递分拣目的站映射Dao接口
 * @author 130566
 *
 */
public interface IExpressSortStationMappingDao {
	/**
	 * 新增快递分拣目的站映射
	 * @param entity
	 * @return
	 */
	int addExpressSortStationMappingEntity(ExpressSortStationMappingEntity entity);
	/**
	 * 通过虚拟编码作废映射关系
	 * @param entity
	 * @return
	 */
	int deleteExpSortStationMappingByVirtualCode(ExpressSortStationMappingEntity entity);
	/**
	 * 修改快递分拣目的站映射
	 * @param entity
	 * @return
	 */
	int updateExpSortStationMapping(ExpressSortStationMappingEntity entity);
	/**
	 * 分页查询映射实体
	 * @param entity
	 * @param start
	 * @param offet
	 * @return
	 */
	List<ExpressSortStationMappingEntity> queryExpSortStationMappingList(ExpressSortStationMappingEntity entity,int start,int offet);
	/**
	 * 查询数量
	 * @param entity
	 * @return
	 */
	long queryCount(ExpressSortStationMappingEntity entity);
	/**
	 * 多条件查询信息
	 * @param entity
	 * @return
	 */
	List<ExpressSortStationMappingEntity> queryMappingListByCondition(
			ExpressSortStationMappingEntity entity);
	
}
