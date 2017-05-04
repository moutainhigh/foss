package com.deppon.foss.module.settlement.consumer.api.server.service;



/**
 * 更新月结额度为零客户的财务时间标记
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-3-11 下午3:12:20,content:TODO </p>
 * @author 105762
 * @date 2014-3-11 下午3:12:20
 * @since
 * @version
 */
public interface ICustomerCreditRefreshService {

	/**
	 * <p>更新月结额度为零客户的财务时间标记</p> 
	 * @author 105762
	 * @return 
	 * @date 2014-3-11 下午2:54:20
	 * @see
	 */
	void updateZeroCreditFinanceMark();
}
