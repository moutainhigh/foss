package com.deppon.foss.module.pickup.pricing.server.service.impl;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IfibelPaperPackingUnitPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPaperFiberPackingTableService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.google.inject.Inject;

/**
 * 实现IPaperFiberPackingTableService
 * @author:218371-foss-zhaoyanjun
 * #date:2014-11-26下午16:57
 */
public class PaperFiberPackingTableService implements IPaperFiberPackingTableService{
	@Inject
	IfibelPaperPackingUnitPriceDao fibelPaperPackingUnitPriceDao;

	@Override
	/**
	 * 查询纸纤包装各单价基础信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:218371-foss-zhaoyanjun
	 */
	public PriceFibelPaperPackingEntity selectByValuationIdAndCode(
			String criteriaDetailId) {
		return fibelPaperPackingUnitPriceDao.selectByValuationIdAndCode(criteriaDetailId);
	}
	
	public IfibelPaperPackingUnitPriceDao getFibelPaperPackingUnitPriceDao() {
		return fibelPaperPackingUnitPriceDao;
	}

	public void setFibelPaperPackingUnitPriceDao(
			IfibelPaperPackingUnitPriceDao fibelPaperPackingUnitPriceDao) {
		this.fibelPaperPackingUnitPriceDao = fibelPaperPackingUnitPriceDao;
	}
}
