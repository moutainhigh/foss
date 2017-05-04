package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.Date;

/**
 * 
 * 期末结账批次服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-15 下午3:43:01
 */
public interface IBalanceService {

	/**
	 * 结账
	 * 
	 * @param date
	 */
	void balance(Date date);

	/**
	 * 反结账
	 * 
	 * @param date
	 */
	void unBalance(Date date);

	/**
	 * 查询结账是否在运行
	 * 
	 * @return
	 */
	boolean isBalanceRun();

	/**
	 * 查询当前结账期间
	 * 
	 * @return
	 */
	Date currentBalanceDate();

}
