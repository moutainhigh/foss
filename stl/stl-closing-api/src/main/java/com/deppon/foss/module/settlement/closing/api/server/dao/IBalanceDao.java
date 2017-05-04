package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.Date;

/**
 * 
 * 结账批次DAO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-15 下午3:28:03
 */
public interface IBalanceDao {

	/**
	 * 
	 * 结账
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-18 下午3:53:42
	 */
	void balance(Date date);

	/**
	 * 反结账
	 */
	void unBalance(Date date);

	/**
	 * 结账是否在运行
	 */
	int isBalanceRun();

	/**
	 * 查询当前结账期间
	 * 
	 * @return
	 */
	Date currentBalanceDate();
}
