package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ISpecialDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.WaybillSpecialDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;

public class SpecialDiscountDao extends SqlSessionDaoSupport implements ISpecialDiscountDao {

	private static final String NAMESPACE = "foss.pkp.SpecialDiscountEntityMapper.";
	@Override
	public WaybillSpecialDiscountEntity querySpecialDiscount(QueryBillCacilateDto queryBillCacilateDto) {
		return (WaybillSpecialDiscountEntity) this.getSqlSession().selectOne(NAMESPACE+"querySpecialDiscount", queryBillCacilateDto);
	}

}
