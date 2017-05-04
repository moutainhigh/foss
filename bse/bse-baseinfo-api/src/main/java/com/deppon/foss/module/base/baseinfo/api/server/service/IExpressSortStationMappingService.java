package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSortStationMappingEntity;

/**
 * 快递分拣目的站映射接口
 * @author 130566
 *
 */
public interface IExpressSortStationMappingService {
	/**
	 * 新增映射关系
	 * @param entity
	 * @return
	 */
	int addExpSortStationMappingEntity(ExpressSortStationMappingEntity entity);
	/**
	 * 作废映射关系
	 * @param entity
	 * @return
	 */
	int deleteExpSortStationMappingEntity(ExpressSortStationMappingEntity entity);
	/**
	 * 修改映射关系
	 * @param entity
	 * @return
	 */
	int updateExpSortStationMappingEntity(ExpressSortStationMappingEntity entity);
	/**
	 * 分页查询映射关系
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ExpressSortStationMappingEntity> queryExpressSortStationMappingEntities(ExpressSortStationMappingEntity entity,int start,int limit);
	/**
	 * 查询数量
	 * @param entity
	 * @return
	 */
	long queryMappingCount(ExpressSortStationMappingEntity entity);
	/**
	 * 根据多条件查询映射关系
	 * @param entity
	 * @return
	 */
	List<ExpressSortStationMappingEntity> queryListByCondition(ExpressSortStationMappingEntity entity);
	/**
	 * 删除多条信息
	 * @param entityList
	 * @return
	 */
	int deleteMoreMappingByvirtualCode(
			List<ExpressSortStationMappingEntity> entityList);
	/**
	 * 根据条件查询库区名称
	 * @param destOrgCode
	 * @param TransferCenterCode
	 * @return
	 */
	String queryGoodSAreaByCondition(String destOrgCode,String transferCenterCode);
}
