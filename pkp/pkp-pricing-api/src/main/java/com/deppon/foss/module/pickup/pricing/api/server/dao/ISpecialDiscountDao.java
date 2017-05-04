package com.deppon.foss.module.pickup.pricing.api.server.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.WaybillSpecialDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;


public interface ISpecialDiscountDao {
	
	/**
	 * 查询特殊折扣区域
	 * @author Foss-dongsiwei
	 * @param waybillno
	 * @return
	 */
	WaybillSpecialDiscountEntity querySpecialDiscount(QueryBillCacilateDto queryBillCacilateDto);

}
