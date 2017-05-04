package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.server.dao.IFreightDiscountDao;
import com.deppon.foss.module.settlement.common.api.server.service.IFreightDiscountService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

public class FreightDiscountService implements IFreightDiscountService{

	private IFreightDiscountDao freightDiscountDao;
	
	/**
	 * 查询客户合同信息
	 * @param customerCode
	 * @return
	 */
	@Override
	public List<Integer> queryCustomerContract(Map<String, Object> contractMap) {
		List<Integer> list = freightDiscountDao.queryCustomerContract(contractMap);
		return list;
	}

	/**
	 * 查询折扣应付
	 * @param contractMap
	 * @return
	 */
	@Override
	public List<BillPayableEntity> queryDiscountPayableBill(Map<String, Object> contractMap) {
		List<BillPayableEntity> list = freightDiscountDao.queryDiscountPayableBill(contractMap);
		return list;
	}

	/**
	 * 查询客户折扣率
	 * @param contractMap
	 * @return
	 */
	@Override
	public List<Integer> queryCustomerDiscountRate(Map<String, Object> contractMap) {
		List<Integer> list = freightDiscountDao.queryCustomerDiscountRate(contractMap);
		return list;
	}
	
	/**
	 * 新增零担事后折应付
	 * @param contractMap
	 */
	@Override
	public void createFreightDiscountPayableBill(Map<String, Object> contractMap) {
		freightDiscountDao.createFreightDiscountPayableBill(contractMap);
	}
	
	public void setFreightDiscountDao(IFreightDiscountDao freightDiscountDao) {
		this.freightDiscountDao = freightDiscountDao;
	}

}
