package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadLinePricePlanDto;
/**
 * 整车线路价格方案服务类
 * @author hehaisu
 * @date 2015-1-23 下午2:04:36
 */
public interface ICarloadLinePricePlanService extends IService {
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
	 * 批量添加整车线路价格方案及明细
	 * @param carloadLinePricePlanDtos
	 * @return
	 */
	int batchInsert(List<CarloadLinePricePlanDto> carloadLinePricePlanDtos);
}
