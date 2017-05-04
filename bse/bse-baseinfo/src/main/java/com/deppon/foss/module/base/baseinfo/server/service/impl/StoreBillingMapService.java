package com.deppon.foss.module.base.baseinfo.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IStoreBillingMapDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStoreBillingMapService;

public class StoreBillingMapService implements  IStoreBillingMapService{

	private IStoreBillingMapDao storeBillingMapDao;
	
	public void setStoreBillingMapDao(IStoreBillingMapDao storeBillingMapDao) {
		this.storeBillingMapDao = storeBillingMapDao;
	}

	/**
	 * 通过仓管组 查询对应开单营业部
	 * 
	 * @author wusuhua
	 * @date   2015-06-1 2:31PM
	 *
	 */
	@Override
	public String queryBillingByStore(String storeCode) {
		if(null==storeCode){
			return null;
		}
		return storeBillingMapDao.queryBillingByStore(storeCode);
	}

}
