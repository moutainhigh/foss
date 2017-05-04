package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;

/**
 * 此接口为纸纤包装单价信息表服务
 * @author:218371-foss-zhaoyanjun
 * @date:2014-11-26下午16:53
 */
public interface IPaperFiberPackingTableService extends IService {
	/**
	 * 查询纸纤包装各单价基础信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:218371-foss-zhaoyanjun
	 */
	PriceFibelPaperPackingEntity selectByValuationIdAndCode(String criteriaDetailId);
}
