package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.Set;



/**
 * 匹配任务接口，为CRM提供的接口， 由crm传进来一个运单号，通过库存状态匹配出任务部门！！！！！
 * @author 200978  xiaobingcheng
 * 2014-10-10
 */

public interface IMatchTaskOrgDao {
	
	/**
	 * 根据库存部门编码set查询，走货路径中最前面的哪个部门编码
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-22
	 * @param stockSet
	 * @return
	 */
	String queryFirstOrgByStockOrgSet(Set<String> stockSet,String waybillNo);

	/**
	 * 根据运单号 查询该运单是否已经全部签收
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-22
	 * @param waybillNo
	 * @return
	 */
	boolean checkWaybillIsSigned(String waybillNo);
	
	
}
