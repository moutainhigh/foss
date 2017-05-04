package com.deppon.foss.module.transfer.dubbo.api.dao;

import java.util.List;
import java.util.Set;

import com.deppon.foss.module.transfer.dubbo.api.define.PathDetailEntity;

/**
 * 匹配任务接口，为CRM提供的接口， 由crm传进来一个运单号，通过库存状态匹配出任务部门！！！！！
 * @author 200978  xiaobingcheng
 * 2014-10-10
 */

public interface IMatchTaskOrgDao4dubbo {
	
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
	
	/**
	 * 查询路径明细LIST
	 * @author huyue
	 * @date 2012-10-23 下午5:49:53
	 */
	List<PathDetailEntity> queryPathDetailList(PathDetailEntity pathDetailEntity);
	
	
}
