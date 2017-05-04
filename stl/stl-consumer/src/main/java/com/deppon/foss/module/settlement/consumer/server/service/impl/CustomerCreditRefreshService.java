/**
 * Copyright 2013 STL TEAM
 */

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditRefreshDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICustomerCreditRefreshService;

/**
 * 客户信用额度还原
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-23 上午11:17:41
 */
public class CustomerCreditRefreshService implements ICustomerCreditRefreshService {

	/**
	 * 客户信用额度还原 Logger
	 */
	private static final Logger logger = LogManager.getLogger(CustomerCreditRefreshService.class);

	private ICustomerCreditRefreshDao customerCreditRefreshDao;
	
	@Override
	public void updateZeroCreditFinanceMark() {
		logger.info("更新客户月结额度为零的客户信用额度时间戳 service 开始");
		try{
			customerCreditRefreshDao.updateZeroCreditFinanceMark();
			logger.info("更新客户月结额度为零的客户信用额度时间戳 service 完成。");
		} catch(Exception e){
			e.printStackTrace();
			logger.info("更新客户月结额度为零的客户信用额度时间戳 service 异常。");
		}
	}

	/**
	 * @return  the customerCreditRefreshDao
	 */
	public ICustomerCreditRefreshDao getCustomerCreditRefreshDao() {
		return customerCreditRefreshDao;
	}

	/**
	 * @param customerCreditRefreshDao the customerCreditRefreshDao to set
	 */
	public void setCustomerCreditRefreshDao(ICustomerCreditRefreshDao customerCreditRefreshDao) {
		this.customerCreditRefreshDao = customerCreditRefreshDao;
	}
	
}
