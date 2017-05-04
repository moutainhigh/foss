package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;

/**
 * 专门处理纸纤包装表的Dao
 * @author:218371-foss-zhaoyanjun
 * @date:2014-11-26下午18:34
 */
public interface IfibelPaperPackingUnitPriceDao {
	/*
	 * 增
	 */
	void insertPricingFibelPaper(PriceFibelPaperPackingEntity priceFibelPaperPackingEntity);
	
	/*
	 * 改
	 */
	void updatePricingFibelPaper(PriceFibelPaperPackingEntity priceFibelPaperPackingEntity);
	
	/*
	 * 删
	 */
	void deletePricingFibelPaper(List<String> valuationIds);
	
	/*
	 * 查
	 */
	public PriceFibelPaperPackingEntity selectByValuationIdAndCode(String valuationId);
}
