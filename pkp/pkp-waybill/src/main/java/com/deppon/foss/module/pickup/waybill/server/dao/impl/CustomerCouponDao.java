package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ICustomerCouponDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;

public class CustomerCouponDao extends iBatis3DaoImpl  implements ICustomerCouponDao{
	private static final String NAMESPACE = "foss.pkp.customerCoupon.";
	
	@Override
	public int insert(CustomerCouponEntity entity) {
		return this.getSqlSession().insert(NAMESPACE+"insert", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCouponEntity> queryCustomerCouponList(
			CustomerCouponEntity entity, int start, int size) {
		RowBounds rowBounds = new RowBounds(start, size);
		return this.getSqlSession().selectList(NAMESPACE+"queryCustomerCouponList",entity,rowBounds);
	}

	@Override
	public int useCustomerCoupon(String customerCode,String couponCode) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("customerCode", customerCode);
		params.put("couponCode", couponCode);
		return this.getSqlSession().update(NAMESPACE+"useCustomerCoupon", params);
	}

}
