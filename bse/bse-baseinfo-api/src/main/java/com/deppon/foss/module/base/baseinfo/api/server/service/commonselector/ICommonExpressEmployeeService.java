package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;

/**
 * 根据条件查询快递员信息--公共选择器Service
 * 
 * @author WangPeng
 * @date   2013-08-28 9:30 AM
 *
 */
public interface ICommonExpressEmployeeService extends IService {

	/**
	 * 根据条件查询快递员信息
	 * 
	 * @author WangPeng
	 * @Date   2013-8-28 上午9:36:24
	 * @param  entity
	 * @param  start
	 * @param  limit
	 * @return List<EmployeeEntity>
	 * 
	 *
	 */
	List<EmployeeEntity> queryExpressEmployeeByCondition(EmployeeEntity entity,int start,int limit);
	
	/**
	 * 记录行数
	 * 
	 * @author WangPeng
	 * @Date   2013-8-28 上午9:36:24
	 * @param  entity
	 * @return Long
	 * 
	 *
	 */
	Long recordQueryCount(EmployeeEntity entity);
	
	/**
	 * 根据工号查询出此员工是否为快递员
	 * 提供给中转-232608
	 */
	public EmployeeEntity queryCourierByCode(String code);
}
