package com.deppon.foss.module.settlement.consumer.api.server.dao;

public interface ICustomerCreditRefreshDao {

	/**
	 * <p>更新月结额度为零客户的财务时间标记</p> 
	 * @author 105762
	 * @return 
	 * @date 2014-3-11 下午2:54:20
	 * @see
	 */
	public int updateZeroCreditFinanceMark();

}
