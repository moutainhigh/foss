package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanEntity;
/**
 * 整车线路价格方案dao
 * 
 * @author hehaisu
 * @date 2015-1-22 下午3:44:41
 */
public interface ICarloadLinePricePlanDao {

	/**
	 * 分页查询方案
	 * 
	 * @param carloadPricePlanDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<CarloadLinePricePlanEntity> queryCarloadLinePricePlanByEntity(
			CarloadLinePricePlanEntity carloadLinePriceEntity, int start, int limit);

	/**
	 * 查询方案总条数
	 * @param carloadLinePricePlanDto
	 * @return
	 */
	Long queryCarloadLinePricePlanCountByEntity(
			CarloadLinePricePlanEntity carloadLinePriceEntity);
	
	/**
	 * 查询方案明细
	 * @param carloadLinePriceDetailEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<CarloadLinePricePlanDetailEntity> queryCarloadLinePricePlanDetailByEntity(
			CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity, int start, int limit);
	
	/**
	 * 查询方案明细总条数
	 * @param carloadLinePriceDetailEntity
	 * @return
	 */
	Long queryCarloadLinePricePlanDetailCountByEntity(
			CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity);
	/**
	 * 插入价格方案
	 * @param carloadLinePriceEntity
	 * @return
	 */
	int insertCarloadLinePricePlan(CarloadLinePricePlanEntity carloadLinePriceEntity);
	
	/**
	 * 插入价格方案明细
	 * @param carloadLinePriceDetailEntity
	 * @return
	 */
	int insertCarloadLinePricePlanDetail(CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity);

}