package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
/**
 * @Description:首续重价格规则DAO接口
 * @author 348757-cc
 * @date 2016-07-04
 * @version 1.0
 */
public interface IEcGoodsPriceValuationDao {

	/**
	 * 激活增值服务
	 * @param beginTime 
	 */
	int activeValuations(String valuationIds, Date beginTime);

	/**
	 * 修改计费规则
	 * @param stopTime 
	 */
	int updateValuation(String pricePlanId, Date endTime);

	/**
	 * 根据查询条件查询计费规则
	 */
	List<PriceValuationEntity> selectByCodition(PriceValuationEntity record);

	/**
	 * 批量删除计费规则
	 */
	int batchDleleteValuation(List<String> pricePlanIds);
	
}