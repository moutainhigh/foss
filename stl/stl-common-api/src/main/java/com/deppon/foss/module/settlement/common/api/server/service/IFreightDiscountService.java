package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

public interface IFreightDiscountService extends IService {

	/**
	 * 查询客户合同信息
	 * @param contractMap
	 * @return
	 */
	List<Integer> queryCustomerContract(Map<String,Object> contractMap);

	/**
	 * 查询折扣应付
	 * @param contractMap
	 * @return
	 */
	List<BillPayableEntity> queryDiscountPayableBill(Map<String, Object> contractMap);

	/**
	 * 查询客户折扣率
	 * @param contractMap
	 * @return
	 */
	List<Integer> queryCustomerDiscountRate(Map<String, Object> contractMap);

	
	/**
	 * 新增零担事后折应付
	 * @param contractMap
	 */
	void createFreightDiscountPayableBill(Map<String, Object> contractMap);
}
