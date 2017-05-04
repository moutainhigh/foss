package com.deppon.foss.module.base.baseinfo.api.server.dao;

/**
 * 通过仓管组 查询对应开单营业部
 * 
 * @author wusuhua
 * @date   2015-06-1 2:31PM
 *
 */
public interface IStoreBillingMapDao {
	/**
	 * 通过仓管组 查询对应开单营业部
	 * 
	 * @author wusuhua
	 * @date   2015-06-1 2:31PM
	 *
	 */
	String queryBillingByStore(String storeCode);
}
