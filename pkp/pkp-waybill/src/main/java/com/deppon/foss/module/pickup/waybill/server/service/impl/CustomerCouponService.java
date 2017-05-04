package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ICustomerCouponDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICustomerCouponService;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
@Transactional
public class CustomerCouponService implements ICustomerCouponService {

	private ICustomerCouponDao customerCouponDao;
	
	@Override
	public void insert(CustomerCouponEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setUsed(FossConstants.YES);
		entity.setCreateDate(new Date());
		int result = customerCouponDao.insert(entity);
		if(result == 0){
			throw new BusinessException("保存客户与优惠券出错");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerCouponEntity> queryCustomerCouponList(
			CustomerCouponEntity entity, int start, int size) {
		if(entity == null){
			throw new BusinessException("查询条件为空");
		}
		return customerCouponDao.queryCustomerCouponList(entity, start, size);
	}

	@Override
	public void useCustomerCoupon(String customerCode, String couponCode) {
		if(StringUtils.isEmpty(couponCode) || StringUtils.isEmpty(customerCode)){
			throw new BusinessException("优惠券使用条件为空");
		}
		customerCouponDao.useCustomerCoupon(customerCode, couponCode);
	}

	public ICustomerCouponDao getCustomerCouponDao() {
		return customerCouponDao;
	}

	public void setCustomerCouponDao(ICustomerCouponDao customerCouponDao) {
		this.customerCouponDao = customerCouponDao;
	}
	

}
